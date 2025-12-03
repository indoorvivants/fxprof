package fxprof

case object GPUMarkerPayload_Type extends StringLiteral("gpu_timer_query")

case object PaintProfilerMarkerTracing_Type extends StringLiteral("tracing")

case object PaintProfilerMarkerTracing_Category extends StringLiteral("Paint")

case object CcMarkerTracing_Type extends StringLiteral("tracing")

case object CcMarkerTracing_Category extends StringLiteral("CC")

case object NetworkPayload_Type extends StringLiteral("Network")

case object FileIoPayload_Type extends StringLiteral("FileIO")

case object UserTimingMarkerPayload_Type extends StringLiteral("UserTiming")

sealed abstract class UserTimingMarkerPayload_EntryType(value: String) extends StringLiteral(value)
object UserTimingMarkerPayload_EntryType {
  case object MEASURE extends UserTimingMarkerPayload_EntryType("measure")
  case object MARK extends UserTimingMarkerPayload_EntryType("mark")
}

case object TextMarkerPayload_Type extends StringLiteral("Text")

case object LogMarkerPayload_Type extends StringLiteral("Log")

case object DOMEventMarkerPayload_Type extends StringLiteral("DOMEvent")

case object PrefMarkerPayload_Type extends StringLiteral("PreferenceRead")

case object NavigationMarkerPayload_Type extends StringLiteral("tracing")

case object NavigationMarkerPayload_Category extends StringLiteral("Navigation")

case object StyleMarkerPayload_Type extends StringLiteral("Styles")

case object StyleMarkerPayload_Category extends StringLiteral("Paint")

case object BHRMarkerPayload_Type extends StringLiteral("BHR-detected hang")

case object LongTaskMarkerPayload_Type extends StringLiteral("MainThreadLongTask")

case object LongTaskMarkerPayload_Category extends StringLiteral("LongTask")

case object IPCMarkerPayload_Type extends StringLiteral("IPC")

sealed abstract class IPCMarkerPayload_Side(value: String) extends StringLiteral(value)
object IPCMarkerPayload_Side {
  case object PARENT extends IPCMarkerPayload_Side("parent")
  case object CHILD extends IPCMarkerPayload_Side("child")
}

sealed abstract class IPCMarkerPayload_Direction(value: String) extends StringLiteral(value)
object IPCMarkerPayload_Direction {
  case object SENDING extends IPCMarkerPayload_Direction("sending")
  case object RECEIVING extends IPCMarkerPayload_Direction("receiving")
}

sealed abstract class IPCMarkerPayload_Phase(value: String) extends StringLiteral(value)
object IPCMarkerPayload_Phase {
  case object ENDPOINT extends IPCMarkerPayload_Phase("endpoint")
  case object TRANSFERSTART extends IPCMarkerPayload_Phase("transferStart")
  case object TRANSFEREND extends IPCMarkerPayload_Phase("transferEnd")
}

case object MediaSampleMarkerPayload_Type extends StringLiteral("MediaSample")

case object BrowsertimeMarkerPayload_Type extends StringLiteral("VisualMetricProgress")

case object NoPayloadUserData_Type extends StringLiteral("NoPayloadUserData")

case object UrlMarkerPayload_Type extends StringLiteral("Url")

case object HostResolverPayload_Type extends StringLiteral("HostResolver")

case object JsAllocationsTable_WeightType extends StringLiteral("bytes")

case object UnbalancedNativeAllocationsTable_WeightType extends StringLiteral("bytes")

sealed abstract class PausedRange_Reason(value: String) extends StringLiteral(value)
object PausedRange_Reason {
  case object PROFILER_PAUSED extends PausedRange_Reason("profiler-paused")
  case object COLLECTING extends PausedRange_Reason("collecting")
}

sealed abstract class ProfileMeta_Toolkit(value: String) extends StringLiteral(value)
object ProfileMeta_Toolkit {
  case object GTK extends ProfileMeta_Toolkit("gtk")
  case object GTK3 extends ProfileMeta_Toolkit("gtk3")
  case object WINDOWS extends ProfileMeta_Toolkit("windows")
  case object COCOA extends ProfileMeta_Toolkit("cocoa")
}

