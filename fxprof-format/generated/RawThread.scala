package fxprof

/**
  * The thread type. Threads are stored in an array in profile.threads.
  * *
  * If a profile contains threads from different OS-level processes, all threads
  * are flattened into the single threads array, and per-process information is
  * duplicated on each thread. In the UI, we recover the process separation based
  * on thread.pid.
  * *
  * There is also a derived `Thread` type, see profile-derived.js.
  */
class RawThread private (private[fxprof] val args: RawThreadArgs) {
  def processType: ProcessType = args.processType

  def processStartupTime: Milliseconds = args.processStartupTime

  def processShutdownTime: Option[Milliseconds] = args.processShutdownTime

  def registerTime: Milliseconds = args.registerTime

  def unregisterTime: Option[Milliseconds] = args.unregisterTime

  def pausedRanges: Vector[PausedRange] = args.pausedRanges

  def showMarkersInTimeline: Option[Boolean] = args.showMarkersInTimeline

  def name: String = args.name

  def isMainThread: Boolean = args.isMainThread

  /** The eTLD+1 of the isolated content process if provided by the back-end.
    * It will be undefined if:
    * - Fission is not enabled.
    * - It's not an isolated content process.
    * - It's a sanitized profile.
    * - It's a profile from an older Firefox which doesn't include this field (introduced in Firefox 80).
    */
  def `eTLD+1`: Option[String] = args.`eTLD+1`

  def processName: Option[String] = args.processName

  def isJsTracer: Option[Boolean] = args.isJsTracer

  def pid: Pid = args.pid

  def tid: Tid = args.tid

  def samples: RawSamplesTable = args.samples

  def jsAllocations: Option[JsAllocationsTable] = args.jsAllocations

  def markers: RawMarkerTable = args.markers

  def stackTable: RawStackTable = args.stackTable

  def frameTable: FrameTable = args.frameTable

  def funcTable: FuncTable = args.funcTable

  def resourceTable: ResourceTable = args.resourceTable

  def nativeSymbols: NativeSymbolTable = args.nativeSymbols

  def jsTracer: Option[JsTracerTable] = args.jsTracer

  /** If present and true, this thread was launched for a private browsing session only.
    * When false, it can still contain private browsing data if the profile was
    * captured in a non-fission browser.
    * It's absent in Firefox 97 and before, or in Firefox 98+ when this thread
    * had no extra attribute at all.
    */
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing

  /** If present and non-0, the number represents the container this thread was loaded in.
    * It's absent in Firefox 97 and before, or in Firefox 98+ when this thread
    * had no extra attribute at all.
    */
  def userContextId: Option[Double] = args.userContextId


  def withProcessType(value: ProcessType): RawThread =
    copy(_.copy(processType = value))
  
  def withProcessStartupTime(value: Milliseconds): RawThread =
    copy(_.copy(processStartupTime = value))
  
  def withProcessShutdownTime(value: Option[Milliseconds]): RawThread =
    copy(_.copy(processShutdownTime = value))
  
  def withRegisterTime(value: Milliseconds): RawThread =
    copy(_.copy(registerTime = value))
  
  def withUnregisterTime(value: Option[Milliseconds]): RawThread =
    copy(_.copy(unregisterTime = value))
  
  def withPausedRanges(value: Vector[PausedRange]): RawThread =
    copy(_.copy(pausedRanges = value))
  
  def withShowMarkersInTimeline(value: Option[Boolean]): RawThread =
    copy(_.copy(showMarkersInTimeline = value))
  
  def withName(value: String): RawThread =
    copy(_.copy(name = value))
  
  def withIsMainThread(value: Boolean): RawThread =
    copy(_.copy(isMainThread = value))
  
  /** Setter for [[eTLD+1]] field

    * The eTLD+1 of the isolated content process if provided by the back-end.
    * It will be undefined if:
    * - Fission is not enabled.
    * - It's not an isolated content process.
    * - It's a sanitized profile.
    * - It's a profile from an older Firefox which doesn't include this field (introduced in Firefox 80).
    */
  def `witheTLD+1`(value: Option[String]): RawThread =
    copy(_.copy(`eTLD+1` = value))
  
  def withProcessName(value: Option[String]): RawThread =
    copy(_.copy(processName = value))
  
  def withIsJsTracer(value: Option[Boolean]): RawThread =
    copy(_.copy(isJsTracer = value))
  
  def withPid(value: Pid): RawThread =
    copy(_.copy(pid = value))
  
  def withTid(value: Tid): RawThread =
    copy(_.copy(tid = value))
  
  def withSamples(value: RawSamplesTable): RawThread =
    copy(_.copy(samples = value))
  
  def withJsAllocations(value: Option[JsAllocationsTable]): RawThread =
    copy(_.copy(jsAllocations = value))
  
  def withMarkers(value: RawMarkerTable): RawThread =
    copy(_.copy(markers = value))
  
  def withStackTable(value: RawStackTable): RawThread =
    copy(_.copy(stackTable = value))
  
  def withFrameTable(value: FrameTable): RawThread =
    copy(_.copy(frameTable = value))
  
  def withFuncTable(value: FuncTable): RawThread =
    copy(_.copy(funcTable = value))
  
  def withResourceTable(value: ResourceTable): RawThread =
    copy(_.copy(resourceTable = value))
  
  def withNativeSymbols(value: NativeSymbolTable): RawThread =
    copy(_.copy(nativeSymbols = value))
  
  def withJsTracer(value: Option[JsTracerTable]): RawThread =
    copy(_.copy(jsTracer = value))
  
  /** Setter for [[isPrivateBrowsing]] field

    * If present and true, this thread was launched for a private browsing session only.
    * When false, it can still contain private browsing data if the profile was
    * captured in a non-fission browser.
    * It's absent in Firefox 97 and before, or in Firefox 98+ when this thread
    * had no extra attribute at all.
    */
  def withIsPrivateBrowsing(value: Option[Boolean]): RawThread =
    copy(_.copy(isPrivateBrowsing = value))
  
  /** Setter for [[userContextId]] field

    * If present and non-0, the number represents the container this thread was loaded in.
    * It's absent in Firefox 97 and before, or in Firefox 98+ when this thread
    * had no extra attribute at all.
    */
  def withUserContextId(value: Option[Double]): RawThread =
    copy(_.copy(userContextId = value))
  

  private def copy(f: RawThreadArgs => RawThreadArgs) = 
    new RawThread(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[RawThread] && o.asInstanceOf[RawThread].args.equals(this.args)
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawThread {
  /** Construct a [[RawThread]]
      @param processType
      @param processStartupTime
      @param registerTime
      @param name
      @param isMainThread
      @param pid
      @param tid
      @param samples
      @param markers
      @param stackTable
      @param frameTable
      @param funcTable
      @param resourceTable
      @param nativeSymbols
    */
  def apply(
    processType: ProcessType,
    processStartupTime: Milliseconds,
    registerTime: Milliseconds,
    name: String,
    isMainThread: Boolean,
    pid: Pid,
    tid: Tid,
    samples: RawSamplesTable,
    markers: RawMarkerTable,
    stackTable: RawStackTable,
    frameTable: FrameTable,
    funcTable: FuncTable,
    resourceTable: ResourceTable,
    nativeSymbols: NativeSymbolTable,
  ): RawThread = 
    new RawThread(RawThreadArgs(
      processType = processType,
      processStartupTime = processStartupTime,
      processShutdownTime = None,
      registerTime = registerTime,
      unregisterTime = None,
      pausedRanges = Vector.empty,
      showMarkersInTimeline = None,
      name = name,
      isMainThread = isMainThread,
      `eTLD+1` = None,
      processName = None,
      isJsTracer = None,
      pid = pid,
      tid = tid,
      samples = samples,
      jsAllocations = None,
      markers = markers,
      stackTable = stackTable,
      frameTable = frameTable,
      funcTable = funcTable,
      resourceTable = resourceTable,
      nativeSymbols = nativeSymbols,
      jsTracer = None,
      isPrivateBrowsing = None,
      userContextId = None,
    ))
  implicit val codec: JsonValueCodec[RawThread] = 
    new JsonValueCodec[RawThread] {
      def decodeValue(in: JsonReader, default: RawThread) = 
        new RawThread(implicitly[JsonValueCodec[RawThreadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawThread, out: JsonWriter) = 
        implicitly[JsonValueCodec[RawThreadArgs]].encodeValue(x.args, out)
      
      def nullValue: RawThread = null
    }
  
}
private[fxprof] case class RawThreadArgs(
  processType: ProcessType,
  processStartupTime: Milliseconds,
  processShutdownTime: Option[Milliseconds],
  registerTime: Milliseconds,
  unregisterTime: Option[Milliseconds],
  pausedRanges: Vector[PausedRange],
  showMarkersInTimeline: Option[Boolean],
  name: String,
  isMainThread: Boolean,
  `eTLD+1`: Option[String],
  processName: Option[String],
  isJsTracer: Option[Boolean],
  pid: Pid,
  tid: Tid,
  samples: RawSamplesTable,
  jsAllocations: Option[JsAllocationsTable],
  markers: RawMarkerTable,
  stackTable: RawStackTable,
  frameTable: FrameTable,
  funcTable: FuncTable,
  resourceTable: ResourceTable,
  nativeSymbols: NativeSymbolTable,
  jsTracer: Option[JsTracerTable],
  isPrivateBrowsing: Option[Boolean],
  userContextId: Option[Double],
)
private[fxprof] object RawThreadArgs {
  implicit val codec: JsonValueCodec[RawThreadArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
