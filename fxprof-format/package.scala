import com.github.plokhotnyuk.jsoniter_scala.core.JsonValueCodec
import com.github.plokhotnyuk.jsoniter_scala.core.JsonWriter
import com.github.plokhotnyuk.jsoniter_scala.core.JsonReader
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
    case class Str(value: String) extends Tid

    given JsonValueCodec[Tid] = new JsonValueCodec[Tid] {
      override def encodeValue(x: Tid, out: JsonWriter): Unit =
        x match
          case Number(value) => out.writeVal(value)
          case Str(value)    => out.writeVal(value)

      override def decodeValue(in: JsonReader, default: Tid): Tid = ???
      override def nullValue: Tid = ???

    }
  }
  type IndexIntoJsTracerEvents = Double;
  type CounterIndex = Double;
  type TabID = Double;
  type InnerWindowID = Double;

  sealed abstract class WeightType(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object WeightType {
    case object Samples extends WeightType("samples")
    case object TracingMS extends WeightType("tracing-ms")
    case object Bytes extends WeightType("bytes")

    given JsonValueCodec[WeightType] = new JsonValueCodec[WeightType] {
      override def encodeValue(x: WeightType, out: JsonWriter): Unit =
        out.writeVal(x.value)

      override def decodeValue(
          in: JsonReader,
          default: WeightType
      ): WeightType = ???
      override def nullValue: WeightType = ???
    }
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
  sealed abstract class MarkerPhase(val value: Int)
      extends Product
      with Serializable
  object MarkerPhase {
    case object Instant extends MarkerPhase(0)
    case object Interval extends MarkerPhase(1)
    case object IntervalStart extends MarkerPhase(2)
    case object IntervalEnd extends MarkerPhase(3)

    given JsonValueCodec[MarkerPhase] = new JsonValueCodec[MarkerPhase] {
      override def encodeValue(x: MarkerPhase, out: JsonWriter): Unit =
        out.writeVal(x.value)

      override def decodeValue(
          in: JsonReader,
          default: MarkerPhase
      ): MarkerPhase = ???
      override def nullValue: MarkerPhase = ???

    }
  }

  import com.github.plokhotnyuk.jsoniter_scala.core._

  // figure out how to deal with this
  case class ProcessProfilingLog(m: Map[String, Array[Byte]]):
    def add[T: JsonValueCodec](key: String, t: T) =
      copy(m = m.updated(key, writeToArray(t)))

  object ProcessProfilingLog {
    def empty: ProcessProfilingLog = ProcessProfilingLog(Map.empty)

    given JsonValueCodec[ProcessProfilingLog] =
      new JsonValueCodec[ProcessProfilingLog] {
        override def decodeValue(
            in: JsonReader,
            default: ProcessProfilingLog
        ): ProcessProfilingLog =
          in.objectStartOrNullError()
          // TODO: do this

          in.objectEndOrCommaError()
        override def encodeValue(
            x: ProcessProfilingLog,
            out: JsonWriter
        ): Unit = ???
        override def nullValue: ProcessProfilingLog = empty
      }
  }
  type ProfilingLog = Map[Double, ProcessProfilingLog]

  sealed abstract class ProcessType(val value: String)
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

    given JsonValueCodec[ProcessType] =
      new JsonValueCodec[ProcessType] {
        def decodeValue(in: JsonReader, default: ProcessType): ProcessType = ???

        def encodeValue(x: ProcessType, out: JsonWriter): Unit =
          out.writeVal(x.value)

        override def nullValue: ProcessType = null
      }
  }

  type PageList = Array[Page]
  type CategoryList = Array[Category]

  sealed abstract class ProfileMeta_Product(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object ProfileMeta_Product {
    case object Firefox extends ProfileMeta_Product("firefox")
    case class Other(v: String) extends ProfileMeta_Product(v)

    given JsonValueCodec[ProfileMeta_Product] =
      new JsonValueCodec[ProfileMeta_Product] {
        def decodeValue(
            in: JsonReader,
            default: ProfileMeta_Product
        ): ProfileMeta_Product = ???

        def encodeValue(x: ProfileMeta_Product, out: JsonWriter): Unit =
          out.writeVal(x.value)

        override def nullValue: ProfileMeta_Product = null
      }
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

    given JsonValueCodec[FuncTable_Resource] =
      new JsonValueCodec[FuncTable_Resource] {
        override def encodeValue(x: FuncTable_Resource, out: JsonWriter): Unit =
          x match
            case None           => out.writeVal(-1)
            case Table(address) => out.writeVal(address)

        override def decodeValue(
            in: JsonReader,
            default: FuncTable_Resource
        ): FuncTable_Resource =
          in.readInt() match
            case -1      => None
            case address => Table(address)

        override def nullValue: FuncTable_Resource = ???
      }

  }

  // export type NetworkHttpVersion = 'h3' | 'h2' | 'http/1.1' | 'http/1.0';

  // export type NetworkStatus =
  //   | 'STATUS_START'
  //   | 'STATUS_STOP'
  //   | 'STATUS_REDIRECT'
  //   | 'STATUS_CANCEL';

  // export type NetworkRedirectType = 'Permanent' | 'Temporary' | 'Internal';

  sealed abstract class NetworkStatus(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object NetworkStatus {
    case object Start extends NetworkStatus("STATUS_START")
    case object Stop extends NetworkStatus("STATUS_STOP")
    case object Redirect extends NetworkStatus("STATUS_REDIRECT")
    case object Cancel extends NetworkStatus("STATUS_CANCEL")
  }

  sealed abstract class NetworkRedirectType(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object NetworkRedirectType {
    case object Permanent extends NetworkRedirectType("Permanent")
    case object Temporary extends NetworkRedirectType("Temporary")
    case object Internal extends NetworkRedirectType("Internal")
  }

  sealed abstract class NetworkHttpVersion(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object NetworkHttpVersion {
    case object H3 extends NetworkHttpVersion("h3")
    case object H2 extends NetworkHttpVersion("h2")
    case object Http11 extends NetworkHttpVersion("http/1.1")
    case object Http10 extends NetworkHttpVersion("http/1.0")
  }

  sealed abstract class MarkerDisplayLocation(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object MarkerDisplayLocation {
    case object MarkerChart extends MarkerDisplayLocation("marker-chart")
    case object MarkerTable extends MarkerDisplayLocation("marker-table")
    case object TimelineOverview
        extends MarkerDisplayLocation("timeline-overview")
    case object TimelineIPC extends MarkerDisplayLocation("timeline-ipc")
    case object TimelineFileIO extends MarkerDisplayLocation("timeline-fileio")
    case object StackChart extends MarkerDisplayLocation("stack-chart")
  }

  sealed abstract class MarkerGraphType(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object MarkerGraphType {
    case object Bar extends MarkerGraphType("bar")
    case object Line extends MarkerGraphType("line")
    case object LineFilled extends MarkerGraphType("line-filled")
  }

  /**  export type MarkerFormatType =
    *    // ----------------------------------------------------
    *    // String types.
    *
    *    // Show the URL, and handle PII sanitization
    *    | 'url'
    *    // Show the file path, and handle PII sanitization.
    *    | 'file-path'
    *    // Show regular string, and handle PII sanitization.
    *    | 'sanitized-string'
    *    // Important, do not put URL or file path information here, as it will not be
    *    // sanitized. Please be careful with including other types of PII here as well.
    *    // e.g. "Label: Some String"
    *    | 'string'
    *    /// An index into a (currently) thread-local string table, aka StringTable
    *    /// This is effectively an integer, so wherever we need to display this value, we
    *    /// must first perform a lookup into the appropriate string table.
    *    | 'unique-string'
    *
    *    // ----------------------------------------------------
    *    // Flow types.
    *    // A flow ID is a u64 identifier that's unique across processes. In the current
    *    // implementation, we represent them as hex strings, as string table indexes.
    *    // A terminating flow ID is a flow ID that, when used in a marker with timestamp T,
    *    // makes it so that if the same flow ID is used in a marker whose timestamp is
    *    // after T, that flow ID is considered to refer to a different flow.
    *    | 'flow-id'
    *    | 'terminating-flow-id'
    */

  sealed trait MarkerFormatType extends Product with Serializable
  object MarkerFormatType {
    case class Str(value: MarkerFormatTypeString) extends MarkerFormatType
    // case class Table(
    //     `type`: MarkerFormatType
    //     // columns: Vector[TableColumnFormat] // TODO: fix this
    // ) extends MarkerFormatType

  }

  sealed abstract class MarkerFormatTypeString(value: String)
      extends StringLiteral(value)
      with Product
      with Serializable
  object MarkerFormatTypeString {
    case object FlowId extends MarkerFormatTypeString("flow-id")
    case object TerminatingFlowId
        extends MarkerFormatTypeString("terminating-flow-id")
    case object Url extends MarkerFormatTypeString("url")
    case object FilePath extends MarkerFormatTypeString("file-path")
    case object SanitizedString
        extends MarkerFormatTypeString("sanitized-string")
    case object String extends MarkerFormatTypeString("string")
    case object UniqueString extends MarkerFormatTypeString("unique-string")
    case object Duration extends MarkerFormatTypeString("duration")
    case object Time extends MarkerFormatTypeString("time")
    case object Seconds extends MarkerFormatTypeString("seconds")
    case object Milliseconds extends MarkerFormatTypeString("milliseconds")
    case object Microseconds extends MarkerFormatTypeString("microseconds")
    case object Nanoseconds extends MarkerFormatTypeString("nanoseconds")

    case object Bytes extends MarkerFormatTypeString("bytes")
    case object Percentage extends MarkerFormatTypeString("percentage")
    case object Integer extends MarkerFormatTypeString("integer")
    case object Decimal extends MarkerFormatTypeString("decimal")
    case object Pid extends MarkerFormatTypeString("pid")
    case object Tid extends MarkerFormatTypeString("tid")
    case object List extends MarkerFormatTypeString("list")

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

    given JsonValueCodec[MarkerPayload] = new JsonValueCodec[MarkerPayload] {

      override def encodeValue(x: MarkerPayload, out: JsonWriter): Unit =
        x match {
          // TODO: add all cases
          case UserTiming(payload) =>
            summon[JsonValueCodec[UserTimingMarkerPayload]]
              .encodeValue(payload, out)
        }

      override def decodeValue(
          in: JsonReader,
          default: MarkerPayload
      ): MarkerPayload = ???
      override def nullValue: MarkerPayload = null
    }

  }

  class StringLiteral(val value: String)
  object StringLiteral {
    given JsonValueCodec[StringLiteral] =
      new JsonValueCodec[StringLiteral] {
        override def encodeValue(x: StringLiteral, out: JsonWriter): Unit =
          out.writeVal(x.value)

        override def decodeValue(
            in: JsonReader,
            default: StringLiteral
        ): StringLiteral = new StringLiteral(in.readString(default.value))

        override def nullValue: StringLiteral = null
      }
  }

}
