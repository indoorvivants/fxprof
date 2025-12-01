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

}
