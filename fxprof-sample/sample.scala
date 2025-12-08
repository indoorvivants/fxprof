//> using toolkit default
//> using dep com.indoorvivants::decline-derive::0.3.3
//> using dep org.scala-lang.modules::scala-parallel-collections::1.2.0

import fxprof.*
import com.github.plokhotnyuk.jsoniter_scala.core._
import java.time.Instant
import decline_derive.CommandApplication
import java.util.concurrent.atomic.AtomicInteger
import scala.collection.parallel.CollectionConverters.*
import scala.reflect.ClassTag
import java.util.concurrent.ConcurrentLinkedDeque
import java.util.concurrent.ConcurrentLinkedQueue

val profile = Profile(
  meta = ProfileMeta(
    interval = 1.0,
    startTime = java.time.Instant.now().toEpochMilli,
    processType = 1.0,
    product = ProfileMeta_Product.Other("scala-native"),
    stackwalk = ProfileMeta_Stackwalk.False,
    version = 1.0,
    preprocessedProfileVersion = 58
  ).withInitialVisibleThreads(Some(Vector(0)))
    .withMarkerSchema(
      Vector(
        MarkerSchema("test-marker").withDisplay(
          Vector(MarkerDisplayLocation.MarkerChart)
        )
      )
    )
    .withCategories(
      Some(
        Vector(
          Category("interflow", CategoryColor.Blue),
          Category("lower", CategoryColor.Green),
          Category("emitLLVM", CategoryColor.Red)
        )
      )
    ),
  shared = RawProfileSharedData(SourceTable(0)).withStringArray(
    Vector("hello", "hello-func", "hello-native-symbol")
  )
).withThreads(
  Vector(
    fxprof
      .RawThread(
        processType = ProcessType.Default,
        processStartupTime = 185,
        registerTime = 0,
        name = "thread-1",
        isMainThread = true,
        pid = "1",
        tid = Tid.Str("thread-1"),
        samples = RawSamplesTable(WeightType.Samples, 1)
          .withStack(Vector(Some(0)))
          .withTime(Some(Vector(200)))
          .withWeight(Vector(Some(1))),
        markers = RawMarkerTable(1)
          .withStartTime(Vector(Some(Instant.now().toEpochMilli() - 400)))
          .withEndTime(Vector(Some(Instant.now().toEpochMilli())))
          .withPhase(Vector(MarkerPhase.Instant))
          .withCategory(Vector(0))
          .withData(
            Vector(
              Some(
                MarkerPayload.UserTiming(
                  UserTimingMarkerPayload(
                    `type` = UserTimingMarkerPayload_Type,
                    "hello",
                    entryType = UserTimingMarkerPayload_EntryType.MEASURE
                  )
                )
              )
            )
          )
          .withName(Vector(0)),
        stackTable =
          RawStackTable(1).withFrame(Vector(0)).withPrefix(Vector(None)),
        frameTable = FrameTable(1)
          .withCategory(Vector(Some(0)))
          .withSubcategory(Vector(None))
          .withFunc(Vector(0))
          .withNativeSymbol(Vector(Some(0)))
          .withInlineDepth(Vector(0))
          .withInnerWindowID(Vector(None)),
        funcTable = FuncTable(1)
          .withName(Vector(1))
          .withIsJS(Vector(false))
          .withRelevantForJS(Vector(false))
          .withResource(Vector(FuncTable_Resource.None))
          .withSource(Vector(None))
          .withLineNumber(Vector(None))
          .withColumnNumber(Vector(None)),
        resourceTable =
          ResourceTable(1).withtype(Vector(1)).withName(Vector(1)),
        nativeSymbols = NativeSymbolTable(1).withName(Vector(2))
      )
      .withProcessShutdownTime(Some(450))
  )
)

case class Config(out: String) derives CommandApplication

class Tracer private (meta: ProfileMeta) {
  type ThreadID = Int
  type StringID = Int
  type FunctionId = Int
  type StackID = Int
  type FunctionNameID = Int

  class Interner[K] {
    private val values = java.util.concurrent.ConcurrentHashMap[K, Int]
    private val count = AtomicInteger(0)

    def all(implicit ev: ClassTag[K]) =
      val lb = Array.ofDim[K](values.size())
      values.forEach { (k, id) =>
        lb(id) = k
      }
      lb

    def id(k: K): Int =
      values.computeIfAbsent(k, _ => count.incrementAndGet() - 1)

    // def by[S](f: K => S): Interner[K] =
    //   new Interner[K] {
    //     private val values = java.util.concurrent.ConcurrentHashMap[S, Int]
    //     private val count = AtomicInteger(0)

    //     override def id(k: K): Int =
    //       values.computeIfAbsent(f(k), _ => count.incrementAndGet())
    //   }
  }

  val defaultCategory =
    Category("default", CategoryColor.Grey).withSubcategories(Vector("Other"))
  val categoryMap = meta.categories.toVector.flatten
    .map(c => c.name -> c)
    .toMap
    .updated("default", defaultCategory)

  val strings = new Interner[String]

  case class Function(nameID: FunctionNameID)
  val functions = new Interner[Function]

  val threadIds = new Interner[String]
  val categories = new Interner[String]

  case class Frame(stackID: Option[Int], functionID: Int, categoryID: Int)
  val frames = new Interner[Frame]

  case class Sample(stackID: StackID, start: Double)
  val samples = new Interner[Sample]

  case class Stack(frameID: Int, prefix: Option[StackID])
  val stacks = new Interner[Stack]

  private val currentThread =
    ThreadLocal.withInitial[ThreadID](() =>
      threadIds.id(Thread.currentThread().getName())
    )

  private val currentStackPrefix =
    ThreadLocal.withInitial[Option[StackID]](() => None)

  val processStartupTime = Instant.now().toEpochMilli()

  private val threadStartupTime =
    ThreadLocal.withInitial[Long](() => Instant.now().toEpochMilli())

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

    Profile(
      meta = this.meta.withCategories(
        Some(
          cats
        )
      ),
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
            markers = RawMarkerTable(1)
              .withStartTime(Vector(Some(threadStartupTime.get())))
              .withEndTime(Vector(Some(Instant.now().toEpochMilli())))
              .withPhase(Vector(MarkerPhase.Instant))
              .withCategory(Vector(0))
              .withData(
                Vector(
                  Some(
                    MarkerPayload.UserTiming(
                      UserTimingMarkerPayload(
                        `type` = UserTimingMarkerPayload_Type,
                        "hello",
                        entryType = UserTimingMarkerPayload_EntryType.MEASURE
                      )
                    )
                  )
                )
              )
              .withName(Vector(0)),
            stackTable = stacksTable,
            frameTable = framesTable,
            funcTable = functionsTable,
            resourceTable =
              ResourceTable(1).withtype(Vector(1)).withName(Vector(1)),
            nativeSymbols = NativeSymbolTable(1).withName(Vector(2))
          )
          .withProcessShutdownTime(
            Some(Instant.now().toEpochMilli() - processStartupTime)
          )
      )
    ).withLibs(Vector.empty)
  }

  def span[A](name: String, category: String)(f: => A) = {
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
    println(s"$name -- $frame, $prefix – creating new stackID $stackID")
    currentStackPrefix.set(Some(stackID))
    val start =
      Instant.now().toEpochMilli() // System.nanoTime().toDouble / 1_000_000
    val result = f
    // val duration = System.nanoTime().toDouble / 1_000_000 - start
    currentStackPrefix.set(prefix)

    val sampleID = samples.id(Sample(stackID = stackID, start = start - ts))

  }

}

object Tracer {
  def apply(meta: ProfileMeta) = new Tracer(meta)
}

@main def sampleGenerate(args: String*) =
  val t = Tracer(
    profile.meta.withCategories(
      Some(
        Vector(
          Category("lowering", CategoryColor.Yellow),
          Category("optimising", CategoryColor.Blue),
          Category("emitting", CategoryColor.Green)
        )
      )
    )
  )

  t.span("bla ", "lowering") {
    t.span("bla.constants", "lowering") {
      Thread.sleep(100)
      t.span("bla ", "optimising") {
        Thread.sleep(200)
      }
    }
  }

  t.span("object bla", "emitting") {
    t.span("bla$lzymap", "emitting") {
      Thread.sleep(400)
    }
    t.span("bla.apply(..)", "emitting") {
      Thread.sleep(100)
    }
  }

  val prof = t.build()

  println(prof.shared.stringArray.zipWithIndex)
  println("\nFunc table:")
  println(writeToString(prof.threads(0).funcTable))
  println("\nFrame table:")
  println(writeToString(prof.threads(0).frameTable))
  println("\nStack table:")
  println(writeToString(prof.threads(0).stackTable))
  println("\nSamples table:")
  println(writeToString(prof.threads(0).samples))

  val config = CommandApplication.parseOrExit[Config](args)
  val path = os.Path(config.out, os.pwd)
  val json = writeToString(prof, WriterConfig.withIndentionStep(2))
  os.write.over(path, json)
