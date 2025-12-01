package fxprof

class ProfileMeta private (args: ProfileMetaArgs) {
  def interval: Milliseconds = args.interval
  def startTime: Milliseconds = args.startTime
  def startTimeAsClockMonotonicNanosecondsSinceBoot: Option[Double] = args.startTimeAsClockMonotonicNanosecondsSinceBoot
  def startTimeAsMachAbsoluteTimeNanoseconds: Option[Double] = args.startTimeAsMachAbsoluteTimeNanoseconds
  def startTimeAsQueryPerformanceCounterValue: Option[Double] = args.startTimeAsQueryPerformanceCounterValue
  def endTime: Option[Milliseconds] = args.endTime
  def profilingStartTime: Option[Milliseconds] = args.profilingStartTime
  def profilingEndTime: Option[Milliseconds] = args.profilingEndTime
  def processType: Double = args.processType
  def extensions: Option[ExtensionTable] = args.extensions
  def categories: Option[CategoryList] = args.categories
  def product: ProfileMeta_Product = args.product
  def stackwalk: ProfileMeta_Stackwalk = args.stackwalk
  def debug: Option[Boolean] = args.debug
  def version: Double = args.version
  def preprocessedProfileVersion: Double = args.preprocessedProfileVersion
  def abi: Option[String] = args.abi
  def misc: Option[String] = args.misc
  def oscpu: Option[String] = args.oscpu
  def mainMemory: Option[Bytes] = args.mainMemory
  def toolkit: Option[ProfileMeta_Toolkit] = args.toolkit
  def appBuildID: Option[String] = args.appBuildID
  def arguments: Option[String] = args.arguments
  def sourceURL: Option[String] = args.sourceURL
  def physicalCPUs: Option[Double] = args.physicalCPUs
  def logicalCPUs: Option[Double] = args.logicalCPUs
  def CPUName: Option[String] = args.CPUName
  def symbolicated: Option[Boolean] = args.symbolicated
  def symbolicationNotSupported: Option[Boolean] = args.symbolicationNotSupported
  def visualMetrics: Option[VisualMetrics] = args.visualMetrics
  def configuration: Option[ProfilerConfiguration] = args.configuration
  def device: Option[String] = args.device
  def importedFrom: Option[String] = args.importedFrom
  def fileName: Option[String] = args.fileName
  def fileSize: Option[Bytes] = args.fileSize
  def usesOnlyOneStackType: Option[Boolean] = args.usesOnlyOneStackType
  def sourceCodeIsNotOnSearchfox: Option[Boolean] = args.sourceCodeIsNotOnSearchfox
  def initialVisibleThreads: Option[Array[ThreadIndex]] = args.initialVisibleThreads
  def initialSelectedThreads: Option[Array[ThreadIndex]] = args.initialSelectedThreads
  def keepProfileThreadOrder: Option[Boolean] = args.keepProfileThreadOrder
  def gramsOfCO2ePerKWh: Option[Double] = args.gramsOfCO2ePerKWh

  def withInterval(value: Milliseconds): ProfileMeta =
    copy(_.copy(interval = value))
  
  def withStartTime(value: Milliseconds): ProfileMeta =
    copy(_.copy(startTime = value))
  
  def withStartTimeAsClockMonotonicNanosecondsSinceBoot(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsClockMonotonicNanosecondsSinceBoot = value))
  
  def withStartTimeAsMachAbsoluteTimeNanoseconds(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsMachAbsoluteTimeNanoseconds = value))
  
  def withStartTimeAsQueryPerformanceCounterValue(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsQueryPerformanceCounterValue = value))
  
  def withEndTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(endTime = value))
  
  def withProfilingStartTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(profilingStartTime = value))
  
  def withProfilingEndTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(profilingEndTime = value))
  
  def withProcessType(value: Double): ProfileMeta =
    copy(_.copy(processType = value))
  
  def withExtensions(value: Option[ExtensionTable]): ProfileMeta =
    copy(_.copy(extensions = value))
  
  def withCategories(value: Option[CategoryList]): ProfileMeta =
    copy(_.copy(categories = value))
  
  def withProduct(value: ProfileMeta_Product): ProfileMeta =
    copy(_.copy(product = value))
  
  def withStackwalk(value: ProfileMeta_Stackwalk): ProfileMeta =
    copy(_.copy(stackwalk = value))
  
  def withDebug(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(debug = value))
  
  def withVersion(value: Double): ProfileMeta =
    copy(_.copy(version = value))
  
  def withPreprocessedProfileVersion(value: Double): ProfileMeta =
    copy(_.copy(preprocessedProfileVersion = value))
  
  def withAbi(value: Option[String]): ProfileMeta =
    copy(_.copy(abi = value))
  
  def withMisc(value: Option[String]): ProfileMeta =
    copy(_.copy(misc = value))
  
  def withOscpu(value: Option[String]): ProfileMeta =
    copy(_.copy(oscpu = value))
  
  def withMainMemory(value: Option[Bytes]): ProfileMeta =
    copy(_.copy(mainMemory = value))
  
  def withToolkit(value: Option[ProfileMeta_Toolkit]): ProfileMeta =
    copy(_.copy(toolkit = value))
  
  def withAppBuildID(value: Option[String]): ProfileMeta =
    copy(_.copy(appBuildID = value))
  
  def withArguments(value: Option[String]): ProfileMeta =
    copy(_.copy(arguments = value))
  
  def withSourceURL(value: Option[String]): ProfileMeta =
    copy(_.copy(sourceURL = value))
  
  def withPhysicalCPUs(value: Option[Double]): ProfileMeta =
    copy(_.copy(physicalCPUs = value))
  
  def withLogicalCPUs(value: Option[Double]): ProfileMeta =
    copy(_.copy(logicalCPUs = value))
  
  def withCPUName(value: Option[String]): ProfileMeta =
    copy(_.copy(CPUName = value))
  
  def withSymbolicated(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(symbolicated = value))
  
  def withSymbolicationNotSupported(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(symbolicationNotSupported = value))
  
  def withVisualMetrics(value: Option[VisualMetrics]): ProfileMeta =
    copy(_.copy(visualMetrics = value))
  
  def withConfiguration(value: Option[ProfilerConfiguration]): ProfileMeta =
    copy(_.copy(configuration = value))
  
  def withDevice(value: Option[String]): ProfileMeta =
    copy(_.copy(device = value))
  
  def withImportedFrom(value: Option[String]): ProfileMeta =
    copy(_.copy(importedFrom = value))
  
  def withFileName(value: Option[String]): ProfileMeta =
    copy(_.copy(fileName = value))
  
  def withFileSize(value: Option[Bytes]): ProfileMeta =
    copy(_.copy(fileSize = value))
  
  def withUsesOnlyOneStackType(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(usesOnlyOneStackType = value))
  
  def withSourceCodeIsNotOnSearchfox(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(sourceCodeIsNotOnSearchfox = value))
  
  def withInitialVisibleThreads(value: Option[Array[ThreadIndex]]): ProfileMeta =
    copy(_.copy(initialVisibleThreads = value))
  
  def withInitialSelectedThreads(value: Option[Array[ThreadIndex]]): ProfileMeta =
    copy(_.copy(initialSelectedThreads = value))
  
  def withKeepProfileThreadOrder(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(keepProfileThreadOrder = value))
  
  def withGramsOfCO2ePerKWh(value: Option[Double]): ProfileMeta =
    copy(_.copy(gramsOfCO2ePerKWh = value))
  

  private def copy(f: ProfileMetaArgs => ProfileMetaArgs) = 
    new ProfileMeta(f(args))
  
}

object ProfileMeta {
  def apply(
    interval: Milliseconds,
    startTime: Milliseconds,
    processType: Double,
    product: ProfileMeta_Product,
    stackwalk: ProfileMeta_Stackwalk,
    version: Double,
    preprocessedProfileVersion: Double,
  ): ProfileMeta = 
    new ProfileMeta(ProfileMetaArgs(
      interval = interval,
      startTime = startTime,
      processType = processType,
      product = product,
      stackwalk = stackwalk,
      version = version,
      preprocessedProfileVersion = preprocessedProfileVersion,
    ))
}
private[fxprof] case class ProfileMetaArgs(
  interval: Milliseconds,
  startTime: Milliseconds,
  startTimeAsClockMonotonicNanosecondsSinceBoot: Option[Double] = None,
  startTimeAsMachAbsoluteTimeNanoseconds: Option[Double] = None,
  startTimeAsQueryPerformanceCounterValue: Option[Double] = None,
  endTime: Option[Milliseconds] = None,
  profilingStartTime: Option[Milliseconds] = None,
  profilingEndTime: Option[Milliseconds] = None,
  processType: Double,
  extensions: Option[ExtensionTable] = None,
  categories: Option[CategoryList] = None,
  product: ProfileMeta_Product,
  stackwalk: ProfileMeta_Stackwalk,
  debug: Option[Boolean] = None,
  version: Double,
  preprocessedProfileVersion: Double,
  abi: Option[String] = None,
  misc: Option[String] = None,
  oscpu: Option[String] = None,
  mainMemory: Option[Bytes] = None,
  toolkit: Option[ProfileMeta_Toolkit] = None,
  appBuildID: Option[String] = None,
  arguments: Option[String] = None,
  sourceURL: Option[String] = None,
  physicalCPUs: Option[Double] = None,
  logicalCPUs: Option[Double] = None,
  CPUName: Option[String] = None,
  symbolicated: Option[Boolean] = None,
  symbolicationNotSupported: Option[Boolean] = None,
  visualMetrics: Option[VisualMetrics] = None,
  configuration: Option[ProfilerConfiguration] = None,
  device: Option[String] = None,
  importedFrom: Option[String] = None,
  fileName: Option[String] = None,
  fileSize: Option[Bytes] = None,
  usesOnlyOneStackType: Option[Boolean] = None,
  sourceCodeIsNotOnSearchfox: Option[Boolean] = None,
  initialVisibleThreads: Option[Array[ThreadIndex]] = None,
  initialSelectedThreads: Option[Array[ThreadIndex]] = None,
  keepProfileThreadOrder: Option[Boolean] = None,
  gramsOfCO2ePerKWh: Option[Double] = None,
)
