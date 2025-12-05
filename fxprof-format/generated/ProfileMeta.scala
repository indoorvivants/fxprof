package fxprof

/**
  * Meta information associated for the entire profile.
  */
class ProfileMeta private (private[fxprof] val args: ProfileMetaArgs) {
  /** The interval at which the threads are sampled.
    */
  def interval: Milliseconds = args.interval

  /** When the main process started. Timestamp expressed in milliseconds since
    * midnight January 1, 1970 GMT.
    */
  def startTime: Milliseconds = args.startTime

  def startTimeAsClockMonotonicNanosecondsSinceBoot: Option[Double] = args.startTimeAsClockMonotonicNanosecondsSinceBoot

  def startTimeAsMachAbsoluteTimeNanoseconds: Option[Double] = args.startTimeAsMachAbsoluteTimeNanoseconds

  def startTimeAsQueryPerformanceCounterValue: Option[Double] = args.startTimeAsQueryPerformanceCounterValue

  /** The number of milliseconds since midnight January 1, 1970 GMT.
    */
  def endTime: Option[Milliseconds] = args.endTime

  /** When the recording started (in milliseconds after startTime).
    */
  def profilingStartTime: Option[Milliseconds] = args.profilingStartTime

  /** When the recording ended (in milliseconds after startTime).
    */
  def profilingEndTime: Option[Milliseconds] = args.profilingEndTime

  /** The process type where the Gecko profiler was started. This is the raw enum
    * numeric value as defined here:
    * https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/xpcom/build/nsXULAppAPI.h#365
    */
  def processType: Double = args.processType

  /** The extensions property landed in Firefox 60, and is only optional because older
    * processed profile versions may not have it. No upgrader was written for this change.
    */
  def extensions: Option[ExtensionTable] = args.extensions

  /** The list of categories used in this profile. If present, it must contain at least the
    * "default category" which is defined as the first category whose color is "grey" - this
    * category usually has the name "Other".
    * If meta.categories is not present, a default list is substituted.
    */
  def categories: Option[CategoryList] = args.categories

  /** The name of the product, most likely "Firefox".
    */
  def product: ProfileMeta_Product = args.product

  /** This value represents a boolean, but for some reason is written out as an int value.
    * It's 0 for the stack walking feature being turned off, and 1 for stackwalking being
    * turned on.
    */
  def stackwalk: ProfileMeta_Stackwalk = args.stackwalk

  /** A boolean flag indicating whether the profiled application is using a debug build.
    * It's false for opt builds, and true for debug builds.
    * This property is optional because older processed profiles don't have this but
    * this property was added to Firefox a long time ago. It should work on older Firefox
    * versions without any problem.
    */
  def debug: Option[Boolean] = args.debug

  /** This is the Gecko profile format version (the unprocessed version received directly
    * from the browser.)
    */
  def version: Double = args.version

  /** This is the processed profile format version.
    */
  def preprocessedProfileVersion: Double = args.preprocessedProfileVersion

  /** The XPCOM ABI (Application Binary Interface) name, taking the form:
    * {CPU_ARCH}-{TARGET_COMPILER_ABI} e.g. "x86_64-gcc3"
    * See https://developer.mozilla.org/en-US/docs/Mozilla/Tech/XPCOM/XPCOM_ABI
    */
  def abi: Option[String] = args.abi

  /** The "misc" value of the browser's user agent, typically the revision of the browser.
    * e.g. "rv:63.0", which would be Firefox 63.0
    * See https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/netwerk/protocol/http/nsHttpHandler.h#543
    */
  def misc: Option[String] = args.misc

  /** The OS and CPU. e.g. "Intel Mac OS X"
    */
  def oscpu: Option[String] = args.oscpu

  /** The size of the main memory in bytes
    */
  def mainMemory: Option[Bytes] = args.mainMemory

  /** The widget toolkit used for GUI rendering.
    * Older versions of Firefox for Linux had the 2 flavors gtk2/gtk3, and so
    * we could find the value "gtk3".
    */
  def toolkit: Option[ProfileMeta_Toolkit] = args.toolkit

  /** The build ID/date of the application.
    */
  def appBuildID: Option[String] = args.appBuildID

  /** Arguments to the program (currently only used for imported profiles)
    */
  def arguments: Option[String] = args.arguments

  /** The URL to the source revision for this build of the application.
    */
  def sourceURL: Option[String] = args.sourceURL

  /** The physical number of CPU cores for the machine.
    */
  def physicalCPUs: Option[Double] = args.physicalCPUs

  /** The amount of logically available CPU cores for the program.
    */
  def logicalCPUs: Option[Double] = args.logicalCPUs

  /** The name of the CPU (typically a string of up to 48 characters).
    */
  def CPUName: Option[String] = args.CPUName

  /** A boolean flag indicating whether we symbolicated this profile. If this is
    * false we'll start a symbolication process when the profile is loaded.
    * A missing property means that it's an older profile, it stands for an
    * "unknown" state.  For now we don't do much with it but we may want to
    * propose a manual symbolication in the future.
    */
  def symbolicated: Option[Boolean] = args.symbolicated

  /** A boolean flag indicating that symbolication is not supported
    * Used for imported profiles that cannot be symbolicated
    */
  def symbolicationNotSupported: Option[Boolean] = args.symbolicationNotSupported

  /** Visual metrics contains additional performance metrics such as Speed Index,
    * Perceptual Speed Index, and ContentfulSpeedIndex. This is optional because only
    * profiles generated by browsertime will have this property. Source code for
    * browsertime can be found at https://github.com/sitespeedio/browsertime.
    */
  def visualMetrics: Option[VisualMetrics] = args.visualMetrics

  /** The configuration of the profiler at the time of recording. Optional since older
    * versions of Firefox did not include it.
    */
  def configuration: Option[ProfilerConfiguration] = args.configuration

  /** Markers are displayed in the UI according to a schema definition. See the
    * MarkerSchema type for more information.
    */
  def markerSchema: Vector[MarkerSchema] = args.markerSchema

  /** Information of the device that profile is captured from.
    * Currently it's only present for Android devices and it includes brand and
    * model names of that device.
    * It's optional because profiles from non-Android devices and from older
    * Firefox versions may not have it.
    * This property landed in Firefox 88.
    */
  def device: Option[String] = args.device

  /** Profile importers can optionally add information about where they are imported from.
    * They also use the "product" field in the meta information, but this is somewhat
    * ambiguous. This field, if present, is unambiguous that it was imported.
    */
  def importedFrom: Option[String] = args.importedFrom

  /** For size profiles, the name of the file being profiled.
    */
  def fileName: Option[String] = args.fileName

  /** For size profiles, the total size of the file in bytes.
    */
  def fileSize: Option[Bytes] = args.fileSize

  /** Do not distinguish between different stack types?
    */
  def usesOnlyOneStackType: Option[Boolean] = args.usesOnlyOneStackType

  /** Hide the "Look up the function name on Searchfox" menu entry?
    */
  def sourceCodeIsNotOnSearchfox: Option[Boolean] = args.sourceCodeIsNotOnSearchfox

  /** Indexes of the threads that are initially visible in the UI.
    * This is useful for imported profiles for which the internal visibility score
    * ranking does not make sense.
    */
  def initialVisibleThreads: Option[Vector[ThreadIndex]] = args.initialVisibleThreads

  /** Indexes of the threads that are initially selected in the UI.
    * This is also most useful for imported profiles where just using the first thread
    * of each process might not make sense.
    */
  def initialSelectedThreads: Option[Vector[ThreadIndex]] = args.initialSelectedThreads

  /** Keep the defined thread order
    */
  def keepProfileThreadOrder: Option[Boolean] = args.keepProfileThreadOrder

  /** Grams of CO2 equivalent per kWh. Used to display power track tooltips.
    * Will fallback to the global average if this is missing.
    */
  def gramsOfCO2ePerKWh: Option[Double] = args.gramsOfCO2ePerKWh


  /** Setter for [[interval]] field

    * The interval at which the threads are sampled.
    */
  def withInterval(value: Milliseconds): ProfileMeta =
    copy(_.copy(interval = value))
  
  /** Setter for [[startTime]] field

    * When the main process started. Timestamp expressed in milliseconds since
    * midnight January 1, 1970 GMT.
    */
  def withStartTime(value: Milliseconds): ProfileMeta =
    copy(_.copy(startTime = value))
  
  def withStartTimeAsClockMonotonicNanosecondsSinceBoot(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsClockMonotonicNanosecondsSinceBoot = value))
  
  def withStartTimeAsMachAbsoluteTimeNanoseconds(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsMachAbsoluteTimeNanoseconds = value))
  
  def withStartTimeAsQueryPerformanceCounterValue(value: Option[Double]): ProfileMeta =
    copy(_.copy(startTimeAsQueryPerformanceCounterValue = value))
  
  /** Setter for [[endTime]] field

    * The number of milliseconds since midnight January 1, 1970 GMT.
    */
  def withEndTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(endTime = value))
  
  /** Setter for [[profilingStartTime]] field

    * When the recording started (in milliseconds after startTime).
    */
  def withProfilingStartTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(profilingStartTime = value))
  
  /** Setter for [[profilingEndTime]] field

    * When the recording ended (in milliseconds after startTime).
    */
  def withProfilingEndTime(value: Option[Milliseconds]): ProfileMeta =
    copy(_.copy(profilingEndTime = value))
  
  /** Setter for [[processType]] field

    * The process type where the Gecko profiler was started. This is the raw enum
    * numeric value as defined here:
    * https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/xpcom/build/nsXULAppAPI.h#365
    */
  def withProcessType(value: Double): ProfileMeta =
    copy(_.copy(processType = value))
  
  /** Setter for [[extensions]] field

    * The extensions property landed in Firefox 60, and is only optional because older
    * processed profile versions may not have it. No upgrader was written for this change.
    */
  def withExtensions(value: Option[ExtensionTable]): ProfileMeta =
    copy(_.copy(extensions = value))
  
  /** Setter for [[categories]] field

    * The list of categories used in this profile. If present, it must contain at least the
    * "default category" which is defined as the first category whose color is "grey" - this
    * category usually has the name "Other".
    * If meta.categories is not present, a default list is substituted.
    */
  def withCategories(value: Option[CategoryList]): ProfileMeta =
    copy(_.copy(categories = value))
  
  /** Setter for [[product]] field

    * The name of the product, most likely "Firefox".
    */
  def withProduct(value: ProfileMeta_Product): ProfileMeta =
    copy(_.copy(product = value))
  
  /** Setter for [[stackwalk]] field

    * This value represents a boolean, but for some reason is written out as an int value.
    * It's 0 for the stack walking feature being turned off, and 1 for stackwalking being
    * turned on.
    */
  def withStackwalk(value: ProfileMeta_Stackwalk): ProfileMeta =
    copy(_.copy(stackwalk = value))
  
  /** Setter for [[debug]] field

    * A boolean flag indicating whether the profiled application is using a debug build.
    * It's false for opt builds, and true for debug builds.
    * This property is optional because older processed profiles don't have this but
    * this property was added to Firefox a long time ago. It should work on older Firefox
    * versions without any problem.
    */
  def withDebug(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(debug = value))
  
  /** Setter for [[version]] field

    * This is the Gecko profile format version (the unprocessed version received directly
    * from the browser.)
    */
  def withVersion(value: Double): ProfileMeta =
    copy(_.copy(version = value))
  
  /** Setter for [[preprocessedProfileVersion]] field

    * This is the processed profile format version.
    */
  def withPreprocessedProfileVersion(value: Double): ProfileMeta =
    copy(_.copy(preprocessedProfileVersion = value))
  
  /** Setter for [[abi]] field

    * The XPCOM ABI (Application Binary Interface) name, taking the form:
    * {CPU_ARCH}-{TARGET_COMPILER_ABI} e.g. "x86_64-gcc3"
    * See https://developer.mozilla.org/en-US/docs/Mozilla/Tech/XPCOM/XPCOM_ABI
    */
  def withAbi(value: Option[String]): ProfileMeta =
    copy(_.copy(abi = value))
  
  /** Setter for [[misc]] field

    * The "misc" value of the browser's user agent, typically the revision of the browser.
    * e.g. "rv:63.0", which would be Firefox 63.0
    * See https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/netwerk/protocol/http/nsHttpHandler.h#543
    */
  def withMisc(value: Option[String]): ProfileMeta =
    copy(_.copy(misc = value))
  
  /** Setter for [[oscpu]] field

    * The OS and CPU. e.g. "Intel Mac OS X"
    */
  def withOscpu(value: Option[String]): ProfileMeta =
    copy(_.copy(oscpu = value))
  
  /** Setter for [[mainMemory]] field

    * The size of the main memory in bytes
    */
  def withMainMemory(value: Option[Bytes]): ProfileMeta =
    copy(_.copy(mainMemory = value))
  
  /** Setter for [[toolkit]] field

    * The widget toolkit used for GUI rendering.
    * Older versions of Firefox for Linux had the 2 flavors gtk2/gtk3, and so
    * we could find the value "gtk3".
    */
  def withToolkit(value: Option[ProfileMeta_Toolkit]): ProfileMeta =
    copy(_.copy(toolkit = value))
  
  /** Setter for [[appBuildID]] field

    * The build ID/date of the application.
    */
  def withAppBuildID(value: Option[String]): ProfileMeta =
    copy(_.copy(appBuildID = value))
  
  /** Setter for [[arguments]] field

    * Arguments to the program (currently only used for imported profiles)
    */
  def withArguments(value: Option[String]): ProfileMeta =
    copy(_.copy(arguments = value))
  
  /** Setter for [[sourceURL]] field

    * The URL to the source revision for this build of the application.
    */
  def withSourceURL(value: Option[String]): ProfileMeta =
    copy(_.copy(sourceURL = value))
  
  /** Setter for [[physicalCPUs]] field

    * The physical number of CPU cores for the machine.
    */
  def withPhysicalCPUs(value: Option[Double]): ProfileMeta =
    copy(_.copy(physicalCPUs = value))
  
  /** Setter for [[logicalCPUs]] field

    * The amount of logically available CPU cores for the program.
    */
  def withLogicalCPUs(value: Option[Double]): ProfileMeta =
    copy(_.copy(logicalCPUs = value))
  
  /** Setter for [[CPUName]] field

    * The name of the CPU (typically a string of up to 48 characters).
    */
  def withCPUName(value: Option[String]): ProfileMeta =
    copy(_.copy(CPUName = value))
  
  /** Setter for [[symbolicated]] field

    * A boolean flag indicating whether we symbolicated this profile. If this is
    * false we'll start a symbolication process when the profile is loaded.
    * A missing property means that it's an older profile, it stands for an
    * "unknown" state.  For now we don't do much with it but we may want to
    * propose a manual symbolication in the future.
    */
  def withSymbolicated(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(symbolicated = value))
  
  /** Setter for [[symbolicationNotSupported]] field

    * A boolean flag indicating that symbolication is not supported
    * Used for imported profiles that cannot be symbolicated
    */
  def withSymbolicationNotSupported(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(symbolicationNotSupported = value))
  
  /** Setter for [[visualMetrics]] field

    * Visual metrics contains additional performance metrics such as Speed Index,
    * Perceptual Speed Index, and ContentfulSpeedIndex. This is optional because only
    * profiles generated by browsertime will have this property. Source code for
    * browsertime can be found at https://github.com/sitespeedio/browsertime.
    */
  def withVisualMetrics(value: Option[VisualMetrics]): ProfileMeta =
    copy(_.copy(visualMetrics = value))
  
  /** Setter for [[configuration]] field

    * The configuration of the profiler at the time of recording. Optional since older
    * versions of Firefox did not include it.
    */
  def withConfiguration(value: Option[ProfilerConfiguration]): ProfileMeta =
    copy(_.copy(configuration = value))
  
  /** Setter for [[markerSchema]] field

    * Markers are displayed in the UI according to a schema definition. See the
    * MarkerSchema type for more information.
    */
  def withMarkerSchema(value: Vector[MarkerSchema]): ProfileMeta =
    copy(_.copy(markerSchema = value))
  
  /** Setter for [[device]] field

    * Information of the device that profile is captured from.
    * Currently it's only present for Android devices and it includes brand and
    * model names of that device.
    * It's optional because profiles from non-Android devices and from older
    * Firefox versions may not have it.
    * This property landed in Firefox 88.
    */
  def withDevice(value: Option[String]): ProfileMeta =
    copy(_.copy(device = value))
  
  /** Setter for [[importedFrom]] field

    * Profile importers can optionally add information about where they are imported from.
    * They also use the "product" field in the meta information, but this is somewhat
    * ambiguous. This field, if present, is unambiguous that it was imported.
    */
  def withImportedFrom(value: Option[String]): ProfileMeta =
    copy(_.copy(importedFrom = value))
  
  /** Setter for [[fileName]] field

    * For size profiles, the name of the file being profiled.
    */
  def withFileName(value: Option[String]): ProfileMeta =
    copy(_.copy(fileName = value))
  
  /** Setter for [[fileSize]] field

    * For size profiles, the total size of the file in bytes.
    */
  def withFileSize(value: Option[Bytes]): ProfileMeta =
    copy(_.copy(fileSize = value))
  
  /** Setter for [[usesOnlyOneStackType]] field

    * Do not distinguish between different stack types?
    */
  def withUsesOnlyOneStackType(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(usesOnlyOneStackType = value))
  
  /** Setter for [[sourceCodeIsNotOnSearchfox]] field

    * Hide the "Look up the function name on Searchfox" menu entry?
    */
  def withSourceCodeIsNotOnSearchfox(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(sourceCodeIsNotOnSearchfox = value))
  
  /** Setter for [[initialVisibleThreads]] field

    * Indexes of the threads that are initially visible in the UI.
    * This is useful for imported profiles for which the internal visibility score
    * ranking does not make sense.
    */
  def withInitialVisibleThreads(value: Option[Vector[ThreadIndex]]): ProfileMeta =
    copy(_.copy(initialVisibleThreads = value))
  
  /** Setter for [[initialSelectedThreads]] field

    * Indexes of the threads that are initially selected in the UI.
    * This is also most useful for imported profiles where just using the first thread
    * of each process might not make sense.
    */
  def withInitialSelectedThreads(value: Option[Vector[ThreadIndex]]): ProfileMeta =
    copy(_.copy(initialSelectedThreads = value))
  
  /** Setter for [[keepProfileThreadOrder]] field

    * Keep the defined thread order
    */
  def withKeepProfileThreadOrder(value: Option[Boolean]): ProfileMeta =
    copy(_.copy(keepProfileThreadOrder = value))
  
  /** Setter for [[gramsOfCO2ePerKWh]] field

    * Grams of CO2 equivalent per kWh. Used to display power track tooltips.
    * Will fallback to the global average if this is missing.
    */
  def withGramsOfCO2ePerKWh(value: Option[Double]): ProfileMeta =
    copy(_.copy(gramsOfCO2ePerKWh = value))
  

  private def copy(f: ProfileMetaArgs => ProfileMetaArgs) = 
    new ProfileMeta(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfileMeta {
  /** Construct a [[ProfileMeta]]
      @param intervalThe interval at which the threads are sampled.
      @param startTimeWhen the main process started. Timestamp expressed in milliseconds since
                      midnight January 1, 1970 GMT.
      @param processTypeThe process type where the Gecko profiler was started. This is the raw enum
                        numeric value as defined here:
                        https://searchfox.org/mozilla-central/rev/819cd31a93fd50b7167979607371878c4d6f18e8/xpcom/build/nsXULAppAPI.h#365
      @param productThe name of the product, most likely "Firefox".
      @param stackwalkThis value represents a boolean, but for some reason is written out as an int value.
                      It's 0 for the stack walking feature being turned off, and 1 for stackwalking being
                      turned on.
      @param versionThis is the Gecko profile format version (the unprocessed version received directly
                    from the browser.)
      @param preprocessedProfileVersionThis is the processed profile format version.
    */
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
      startTimeAsClockMonotonicNanosecondsSinceBoot = None,
      startTimeAsMachAbsoluteTimeNanoseconds = None,
      startTimeAsQueryPerformanceCounterValue = None,
      endTime = None,
      profilingStartTime = None,
      profilingEndTime = None,
      processType = processType,
      extensions = None,
      categories = None,
      product = product,
      stackwalk = stackwalk,
      debug = None,
      version = version,
      preprocessedProfileVersion = preprocessedProfileVersion,
      abi = None,
      misc = None,
      oscpu = None,
      mainMemory = None,
      toolkit = None,
      appBuildID = None,
      arguments = None,
      sourceURL = None,
      physicalCPUs = None,
      logicalCPUs = None,
      CPUName = None,
      symbolicated = None,
      symbolicationNotSupported = None,
      visualMetrics = None,
      configuration = None,
      markerSchema = Vector.empty,
      device = None,
      importedFrom = None,
      fileName = None,
      fileSize = None,
      usesOnlyOneStackType = None,
      sourceCodeIsNotOnSearchfox = None,
      initialVisibleThreads = None,
      initialSelectedThreads = None,
      keepProfileThreadOrder = None,
      gramsOfCO2ePerKWh = None,
    ))
  given JsonValueCodec[ProfileMeta] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfileMeta) = 
        new ProfileMeta(summon[JsonValueCodec[ProfileMetaArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfileMeta, out: JsonWriter) = 
        summon[JsonValueCodec[ProfileMetaArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfileMeta = null
    }
  
}
private[fxprof] case class ProfileMetaArgs(
  interval: Milliseconds,
  startTime: Milliseconds,
  startTimeAsClockMonotonicNanosecondsSinceBoot: Option[Double],
  startTimeAsMachAbsoluteTimeNanoseconds: Option[Double],
  startTimeAsQueryPerformanceCounterValue: Option[Double],
  endTime: Option[Milliseconds],
  profilingStartTime: Option[Milliseconds],
  profilingEndTime: Option[Milliseconds],
  processType: Double,
  extensions: Option[ExtensionTable],
  categories: Option[CategoryList],
  product: ProfileMeta_Product,
  stackwalk: ProfileMeta_Stackwalk,
  debug: Option[Boolean],
  version: Double,
  preprocessedProfileVersion: Double,
  abi: Option[String],
  misc: Option[String],
  oscpu: Option[String],
  mainMemory: Option[Bytes],
  toolkit: Option[ProfileMeta_Toolkit],
  appBuildID: Option[String],
  arguments: Option[String],
  sourceURL: Option[String],
  physicalCPUs: Option[Double],
  logicalCPUs: Option[Double],
  CPUName: Option[String],
  symbolicated: Option[Boolean],
  symbolicationNotSupported: Option[Boolean],
  visualMetrics: Option[VisualMetrics],
  configuration: Option[ProfilerConfiguration],
  markerSchema: Vector[MarkerSchema],
  device: Option[String],
  importedFrom: Option[String],
  fileName: Option[String],
  fileSize: Option[Bytes],
  usesOnlyOneStackType: Option[Boolean],
  sourceCodeIsNotOnSearchfox: Option[Boolean],
  initialVisibleThreads: Option[Vector[ThreadIndex]],
  initialSelectedThreads: Option[Vector[ThreadIndex]],
  keepProfileThreadOrder: Option[Boolean],
  gramsOfCO2ePerKWh: Option[Double],
)
private[fxprof] object ProfileMetaArgs {
  given ConfiguredJsonValueCodec[ProfileMetaArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
