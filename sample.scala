import fxprof.*
import com.github.plokhotnyuk.jsoniter_scala.core._
import java.time.Instant

val profile = Profile(
  meta = ProfileMeta(
    interval = 1.0,
    startTime = java.time.Instant.now().toEpochMilli,
    processType = 1.0,
    product = ProfileMeta_Product.Other("scala-native"),
    stackwalk = ProfileMeta_Stackwalk.False,
    version = 1.0,
    preprocessedProfileVersion = 58
  ),
  shared = RawProfileSharedData(SourceTable(1)).withStringArray(Vector("hello"))
).withThreads(
  Vector(
    fxprof.RawThread(
      processType = ProcessType.Default,
      processStartupTime = 0,
      registerTime = 0,
      name = "thread-1",
      isMainThread = true,
      pid = "1",
      tid = Tid.Str("thread-1"),
      samples = RawSamplesTable(WeightType.Samples, 0),
      markers = RawMarkerTable(1)
        .withStartTime(Vector(Some(Instant.now().toEpochMilli() - 400)))
        .withEndTime(Vector(Some(Instant.now().toEpochMilli())))
        .withPhase(Vector(MarkerPhase.Instant))
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
      stackTable = RawStackTable(0),
      frameTable = FrameTable(0.0),
      funcTable = FuncTable(0),
      resourceTable = ResourceTable(0),
      nativeSymbols = NativeSymbolTable(0)
    )
  )
)

@main def sampleGenerate(out: String) =
  val path = os.Path(out, os.pwd)
  val json = writeToString(profile, WriterConfig.withIndentionStep(2))
  println(json)
  os.write.over(path, json)
