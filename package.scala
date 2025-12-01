package object fxprof {
  type Milliseconds = Double
  type Pid = String

  type IndexIntoStackTable = Double;
  type IndexIntoSamplesTable = Double;
  type IndexIntoRawMarkerTable = Double;
  type IndexIntoFrameTable = Double;
  type IndexIntoStringTable = Double;
  type IndexIntoFuncTable = Double;
  type IndexIntoResourceTable = Double;
  type IndexIntoLibs = Double;
  type IndexIntoNativeSymbolTable = Double;
  type IndexIntoCategoryList = Double;
  type IndexIntoSubcategoryListForCategory = Double;
  type IndexIntoSourceTable = Double;
  type ResourceTypeEnum = Double;
  type ThreadIndex = Double;

  type Address = Double
  type Bytes = Double

  type Nanoseconds = Double
  type Microseconds = Double
  type Seconds = Double

  // Tid is most often a number. However in some cases such as merged profiles
  // ould generate a string.
  sealed trait Tid extends Product with Serializable
  object Tid {
    case class Number(value: Double) extends Tid
    case class String(value: String) extends Tid
  }
  type IndexIntoJsTracerEvents = Double;
  type CounterIndex = Double;
  type TabID = Double;
  type InnerWindowID = Double;

  sealed trait WeightType extends Product with Serializable
  object WeightType {
    case object Samples extends WeightType
    case object TracingMS extends WeightType
    case object Bytes extends WeightType
  }

  sealed trait GraphColor extends Product with Serializable
  object GraphColor {
    case object Blue extends GraphColor
    case object Green extends GraphColor
    case object Grey extends GraphColor
    case object Ink extends GraphColor
    case object Magenta extends GraphColor
    case object Orange extends GraphColor
    case object Purple extends GraphColor
    case object Red extends GraphColor
    case object Teal extends GraphColor
    case object Yellow extends GraphColor

  }

  sealed trait CategoryColor extends Product with Serializable
  object CategoryColor {
    case object Transparent extends CategoryColor
    case object Purple extends CategoryColor
    case object Green extends CategoryColor
    case object Orange extends CategoryColor
    case object Yellow extends CategoryColor
    case object LightBlue extends CategoryColor
    case object Blue extends CategoryColor
    case object Brown extends CategoryColor
    case object Magenta extends CategoryColor
    case object Red extends CategoryColor
    case object LightRed extends CategoryColor
    case object DarkGrey extends CategoryColor
    case object Grey extends CategoryColor
  }
  sealed abstract class MarkerPhase(value: Int)
      extends Product
      with Serializable
  object MarkerPhase {
    case object Instant extends MarkerPhase(0)
    case object Interval extends MarkerPhase(1)
    case object IntervalStart extends MarkerPhase(2)
    case object IntervalEnd extends MarkerPhase(3)
  }

  type ProcessProfilingLog = Map[String, Any]
  type ProfilingLog = Map[Double, ProcessProfilingLog]

  // export type ProcessType =
  //   | 'default'
  //   | 'plugin'
  //   | 'tab'
  //   | 'ipdlunittest'
  //   | 'geckomediaplugin'
  //   | 'gpu'
  //   | 'pdfium'
  //   | 'vr'
  //   // Unknown process type:
  //   // https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/toolkit/xre/nsEmbedFunctions.cpp#232
  //   | 'invalid'
  //   | string;

  sealed abstract class ProcessType(value: String)
      extends Product
      with Serializable
  object ProcessType {
    case object Default extends ProcessType("default")
    case object Plugin extends ProcessType("plugin")
    case object Tab extends ProcessType("tab")
    case object Ipdlunittest extends ProcessType("ipdlunittest")
    case object Geckomediaplugin extends ProcessType("geckomediaplugin")
    case object Gpu extends ProcessType("gpu")
    case object Pdfium extends ProcessType("pdfium")
    case object Vr extends ProcessType("vr")
    case object Invalid extends ProcessType("invalid")
  }

  type PageList = Array[Page]
  type CategoryList = Array[Category]

  sealed abstract class ProfileMeta_Product(value: String)
      extends Product
      with Serializable
  object ProfileMeta_Product {
    case object Firefox extends ProfileMeta_Product("firefox")
    case class Other(value: String) extends ProfileMeta_Product(value)
  }

  sealed abstract class ProfileMeta_Stackwalk(value: Int)
      extends Product
      with Serializable
  object ProfileMeta_Stackwalk {
    case object True extends ProfileMeta_Stackwalk(1)
    case object False extends ProfileMeta_Stackwalk(0)
  }

  // sealed abstract class ProfileMeta_Toolkit(value: String)
  //     extends Product
  //     with Serializable
  // object ProfileMeta_Toolkit {
  //   case object Gtk extends ProfileMeta_Toolkit("gtk")
  //   case object Gtk3 extends ProfileMeta_Toolkit("gtk3")
  //   case object Windows extends ProfileMeta_Toolkit("windows")
  //   case object Cocoa extends ProfileMeta_Toolkit("cocoa")
  //   case object Android extends ProfileMeta_Toolkit("android")
  //   case class Other(value: String) extends ProfileMeta_Toolkit(value)
  // }

  sealed abstract class FrameTable_Address(value: Double)
      extends Product
      with Serializable
  object FrameTable_Address {
    case object None extends FrameTable_Address(-1)
    case class Addr(address: Address) extends FrameTable_Address(address)
  }

  sealed abstract class FuncTable_Resource(value: Double)
      extends Product
      with Serializable
  object FuncTable_Resource {
    case object None extends FuncTable_Resource(-1)
    case class Table(address: IndexIntoResourceTable)
        extends FuncTable_Resource(address)
  }

  // export type NetworkHttpVersion = 'h3' | 'h2' | 'http/1.1' | 'http/1.0';

  // export type NetworkStatus =
  //   | 'STATUS_START'
  //   | 'STATUS_STOP'
  //   | 'STATUS_REDIRECT'
  //   | 'STATUS_CANCEL';

  // export type NetworkRedirectType = 'Permanent' | 'Temporary' | 'Internal';

  sealed abstract class NetworkStatus(value: String)
      extends Product
      with Serializable
  object NetworkStatus {
    case object Start extends NetworkStatus("STATUS_START")
    case object Stop extends NetworkStatus("STATUS_STOP")
    case object Redirect extends NetworkStatus("STATUS_REDIRECT")
    case object Cancel extends NetworkStatus("STATUS_CANCEL")
  }

  sealed abstract class NetworkRedirectType(value: String)
      extends Product
      with Serializable
  object NetworkRedirectType {
    case object Permanent extends NetworkRedirectType("Permanent")
    case object Temporary extends NetworkRedirectType("Temporary")
    case object Internal extends NetworkRedirectType("Internal")
  }

  sealed abstract class NetworkHttpVersion(value: String)
      extends Product
      with Serializable
  object NetworkHttpVersion {
    case object H3 extends NetworkHttpVersion("h3")
    case object H2 extends NetworkHttpVersion("h2")
    case object Http11 extends NetworkHttpVersion("http/1.1")
    case object Http10 extends NetworkHttpVersion("http/1.0")
  }

  sealed trait MarkerPayload extends Product with Serializable
  object MarkerPayload {
    case class FileIO(payload: FileIoPayload) extends MarkerPayload
    case class GPU(payload: GPUMarkerPayload) extends MarkerPayload
    case class Network(payload: NetworkPayload) extends MarkerPayload
    case class UserTiming(payload: UserTimingMarkerPayload)
        extends MarkerPayload
    case class Text(payload: TextMarkerPayload) extends MarkerPayload
    case class Log(payload: LogMarkerPayload) extends MarkerPayload
    case class PaintProfiler(payload: PaintProfilerMarkerTracing)
        extends MarkerPayload
    case class Cc(payload: CcMarkerTracing) extends MarkerPayload
    case class DOMEvent(payload: DOMEventMarkerPayload) extends MarkerPayload
    // case class GCMinor(payload: GCMinorMarkerPayload) extends MarkerPayload
    // case class GCMajor(payload: GCMajorMarkerPayload) extends MarkerPayload
    // case class GCSlice(payload: GCSliceMarkerPayload) extends MarkerPayload
    case class Style(payload: StyleMarkerPayload) extends MarkerPayload
    case class BHR(payload: BHRMarkerPayload) extends MarkerPayload
    case class LongTask(payload: LongTaskMarkerPayload) extends MarkerPayload
    // case class VsyncTimestamp(payload: VsyncTimestampPayload)
    //     extends MarkerPayload
    // case class Screenshot(payload: ScreenshotPayload) extends MarkerPayload
    case class Navigation(payload: NavigationMarkerPayload)
        extends MarkerPayload
    case class Pref(payload: PrefMarkerPayload) extends MarkerPayload
    case class IPC(payload: IPCMarkerPayload) extends MarkerPayload
    case class MediaSample(payload: MediaSampleMarkerPayload)
        extends MarkerPayload
    // case class Jank(payload: JankPayload) extends MarkerPayload
    case class Browsertime(payload: BrowsertimeMarkerPayload)
        extends MarkerPayload
    case class NoPayloadUserData(payload: NoPayloadUserData)
        extends MarkerPayload
    case class Url(payload: UrlMarkerPayload) extends MarkerPayload
    case class HostResolver(payload: HostResolverPayload) extends MarkerPayload
  }

  class StringLiteral(value: String)

}
