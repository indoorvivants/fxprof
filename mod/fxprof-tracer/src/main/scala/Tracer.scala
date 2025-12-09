package fxprof.tracer

import fxprof.*
import scala.reflect.ClassTag
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

trait ClockSample {
  def processStartupTime(): Long
  def processShutdownTime(): Long
  def threadStartupTime(name: String): Long
  def threadShutdownTime(name: String): Long
  def beforeMillis(marker: String, labels: String): Long
  def afterMillis(marker: String, labels: String): Long
}

object ClockSample {
  object Default extends ClockSample {
    override def processStartupTime(): Long =
      System.currentTimeMillis()
    override def processShutdownTime(): Long =
      System.currentTimeMillis()
    override def threadStartupTime(name: String): Long =
      System.currentTimeMillis()
    override def threadShutdownTime(name: String): Long =
      System.currentTimeMillis()
    override def beforeMillis(marker: String, labels: String): Long =
      System.currentTimeMillis()
    override def afterMillis(marker: String, labels: String): Long =
      System.currentTimeMillis()
  }
}

class Tracer private (meta: ProfileMeta, clock: ClockSample) {
  type ThreadID = Int
  type StringID = Int
  type FunctionId = Int
  type StackID = Int
  type FunctionNameID = Int

  def withClock(clock: ClockSample): Tracer =
    new Tracer(meta, clock)

  def withMeta(meta: ProfileMeta): Tracer =
    new Tracer(meta, clock)

  private class Interner[K] {
    private val values = new java.util.concurrent.ConcurrentHashMap[K, Int]
    private val count = new AtomicInteger(0)

    def all(implicit ev: ClassTag[K]) = {
      val lb = Array.ofDim[K](values.size())
      values.forEach { (k, id) =>
        lb(id) = k
      }
      lb
    }

    def id(k: K): Int =
      values.computeIfAbsent(k, _ => count.incrementAndGet() - 1)
  }

  private val defaultCategory =
    Category("default", CategoryColor.Grey).withSubcategories(Vector("Other"))

  private val categoryMap = meta.categories.toVector.flatten
    .map(c => c.name -> c)
    .toMap
    .updated("default", defaultCategory)

  private val strings = new Interner[String]

  private case class Function(nameID: FunctionNameID)
  private val functions = new Interner[Function]

  private val threadIds = new Interner[String]
  private val categories = new Interner[String]

  private case class Frame(
      stackID: Option[Int],
      functionID: Int,
      categoryID: Int
  )
  private val frames = new Interner[Frame]

  private case class Sample(stackID: StackID, start: Double)
  private val samples = new Interner[Sample]

  private case class Stack(frameID: Int, prefix: Option[StackID])
  private val stacks = new Interner[Stack]

  private val currentThread =
    ThreadLocal.withInitial[ThreadID](() =>
      threadIds.id(Thread.currentThread().getName())
    )

  private val currentStackPrefix =
    ThreadLocal.withInitial[Option[StackID]](() => None)

  private val processStartupTime = clock.processStartupTime()

  private val threadStartupTime =
    ThreadLocal.withInitial[Long](() =>
      clock.threadStartupTime(Thread.currentThread().getName())
    )

  private val isClosed = new AtomicBoolean()

  def checkOpen() = {
    if (isClosed.get()) throw new IllegalStateException("Profile is closed")
  }

  def close() = {
    isClosed.setRelease(true)
  }

  def build() = this.synchronized {
    val sources = SourceTable(0)
    val stringArray = strings.all.toVector
    val cats = categories.all.toVector
      .flatMap(categoryMap.get)
      .map(_.withSubcategories(Vector("Other")))

    val framesTable = {
      val (categories, functions) =
        frames.all.toVector
          .map(f => (f.categoryID.toDouble, f.functionID.toDouble))
          .unzip

      FrameTable(categories.length)
        .withCategory(categories.map(Some(_)))
        .withSubcategory(Vector.fill(categories.length)(None))
        .withFunc(functions)
        .withNativeSymbol(Vector.fill(categories.length)(None))
        .withInlineDepth(Vector.fill(categories.length)(0))
        .withInnerWindowID(Vector.fill(categories.length)(None))
        .withLine(Vector.fill(categories.length)(None))
        .withColumn(Vector.fill(categories.length)(None))
        .withAddress(Vector.fill(categories.length)(FrameTable_Address.None))
    }

    val functionsTable = {
      val funcs = functions.all
      FuncTable(funcs.length)
        .withName(funcs.toVector.map(_.nameID.toDouble))
        .withIsJS(Vector.fill(funcs.length)(false))
        .withRelevantForJS(Vector.fill(funcs.length)(false))
        .withResource(Vector.fill(funcs.length)(FuncTable_Resource.None))
        .withSource(Vector.fill(funcs.length)(None))
        .withLineNumber(Vector.fill(funcs.length)(None))
        .withColumnNumber(Vector.fill(funcs.length)(None))
    }

    val stacksTable = {
      val stack = stacks.all
      RawStackTable(stack.length)
        .withFrame(stack.map(_.frameID.toDouble).toVector)
        .withPrefix(stack.map(_.prefix.map(_.toDouble)).toVector)
    }

    val samplesTable = {
      val sampl = samples.all.sortBy(_.start)
      RawSamplesTable(WeightType.Samples, sampl.length)
        .withStack(sampl.map(s => Some(s.stackID.toDouble)).toVector)
        .withTime(Some(sampl.map(_.start.toDouble).toVector))
        .withWeight(Vector.fill(sampl.length)(Some(1.0)))
    }

    isClosed.setRelease(true)

    Profile(
      meta = this.meta.withCategories(Some(cats)),
      shared = RawProfileSharedData(sources).withStringArray(stringArray)
    ).withThreads(
      Vector(
        fxprof
          .RawThread(
            processType = ProcessType.Default,
            processStartupTime = threadStartupTime.get() - processStartupTime,
            registerTime = 0,
            name = "thread-1",
            isMainThread = true,
            pid = "1",
            tid = Tid.Str("thread-1"),
            samples = samplesTable,
            markers = RawMarkerTable(0),
            stackTable = stacksTable,
            frameTable = framesTable,
            funcTable = functionsTable,
            resourceTable = ResourceTable(0),
            nativeSymbols = NativeSymbolTable(0)
          )
          .withProcessShutdownTime(
            Some(clock.threadShutdownTime("thread-1") - processStartupTime)
          )
      )
    ).withLibs(Vector.empty)
  }

  def span[A](name: String)(f: => A): A = span(name, "default")(f)

  def span[A](name: String, category: String)(f: => A): A = {
    checkOpen()
    val catName = categoryMap.getOrElse(category, categoryMap("default")).name
    val ts = threadStartupTime.get()
    val functionNameID = strings.id(name)
    val func = Function(functionNameID)
    val funcID = functions.id(func)
    val categoryID = categories.id(catName)
    val prefix = currentStackPrefix.get()
    val frame = Frame(prefix, funcID, categoryID)
    val frameID = frames.id(frame)
    val stackID = stacks.id(Stack(frameID, prefix))
    currentStackPrefix.set(Some(stackID))
    val start =
      clock.beforeMillis(name, category)
    val result = f
    currentStackPrefix.set(prefix)

    val sampleID = samples.id(Sample(stackID = stackID, start = start - ts))

    result
  }

}

object Tracer {
  def apply(meta: ProfileMeta) = new Tracer(meta, ClockSample.Default)
}
