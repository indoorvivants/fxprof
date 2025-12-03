//> using toolkit default
//> using dep com.indoorvivants::decline-derive::0.3.2
import fxprof.*
import com.github.plokhotnyuk.jsoniter_scala.core._
import java.time.Instant
import decline_derive.CommandApplication
import java.util.concurrent.atomic.AtomicInteger

val profile = Profile(
  meta = ProfileMeta(
    interval = 1.0,
    startTime = java.time.Instant.now().toEpochMilli,
    processType = 1.0,
    product = ProfileMeta_Product.Other("scala-native"),
    stackwalk = ProfileMeta_Stackwalk.False,
    version = 1.0,
    preprocessedProfileVersion = 58
  ).withMarkerSchema(
    Vector(
      MarkerSchema("test-marker").withDisplay(
        Vector(MarkerDisplayLocation.MarkerChart)
      )
    )
  ).withCategories(
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
        markers = RawMarkerTable(0),
        // .withStartTime(Vector(Some(Instant.now().toEpochMilli() - 400)))
        // .withEndTime(Vector(Some(Instant.now().toEpochMilli())))
        // .withPhase(Vector(MarkerPhase.Instant))
        // .withCategory(Vector(0))
        // .withData(
        //   Vector(
        //     Some(
        //       MarkerPayload.UserTiming(
        //         UserTimingMarkerPayload(
        //           `type` = UserTimingMarkerPayload_Type,
        //           "hello",
        //           entryType = UserTimingMarkerPayload_EntryType.MEASURE
        //         )
        //       )
        //     )
        //   )
        // )
        // .withName(Vector(0)),
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
  private val strings = java.util.concurrent.ConcurrentHashMap[String, Int]
  private val stringsCount = AtomicInteger(0)
  private val threads = java.util.concurrent.ConcurrentHashMap[String, Int]
  private val threadsCount = AtomicInteger(0)

  private def stringID(s: String) =
    strings.computeIfAbsent(s, _ => stringsCount.incrementAndGet())

  private def threadID(s: Thread) =
    threads.computeIfAbsent(s.getName, _ => threadsCount.incrementAndGet())

}

@main def sampleGenerate(args: String*) =
  val config = CommandApplication.parseOrExit[Config](args)
  val path = os.Path(config.out, os.pwd)
  val json = writeToString(profile, WriterConfig.withIndentionStep(2))
  println(json)
  os.write.over(path, json)
