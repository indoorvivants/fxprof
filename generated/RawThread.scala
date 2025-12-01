package fxprof

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
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing
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
  
  def withIsPrivateBrowsing(value: Option[Boolean]): RawThread =
    copy(_.copy(isPrivateBrowsing = value))
  
  def withUserContextId(value: Option[Double]): RawThread =
    copy(_.copy(userContextId = value))
  

  private def copy(f: RawThreadArgs => RawThreadArgs) = 
    new RawThread(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawThread {
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
      registerTime = registerTime,
      name = name,
      isMainThread = isMainThread,
      pid = pid,
      tid = tid,
      samples = samples,
      markers = markers,
      stackTable = stackTable,
      frameTable = frameTable,
      funcTable = funcTable,
      resourceTable = resourceTable,
      nativeSymbols = nativeSymbols,
    ))
  given JsonValueCodec[RawThread] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawThread) = 
        new RawThread(summon[JsonValueCodec[RawThreadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawThread, out: JsonWriter) = 
        summon[JsonValueCodec[RawThreadArgs]].encodeValue(x.args, out)
      
      def nullValue: RawThread = null
    }
  
}
private[fxprof] case class RawThreadArgs(
  processType: ProcessType,
  processStartupTime: Milliseconds,
  processShutdownTime: Option[Milliseconds] = None,
  registerTime: Milliseconds,
  unregisterTime: Option[Milliseconds] = None,
  pausedRanges: Vector[PausedRange] = Vector.empty,
  showMarkersInTimeline: Option[Boolean] = None,
  name: String,
  isMainThread: Boolean,
  `eTLD+1`: Option[String] = None,
  processName: Option[String] = None,
  isJsTracer: Option[Boolean] = None,
  pid: Pid,
  tid: Tid,
  samples: RawSamplesTable,
  jsAllocations: Option[JsAllocationsTable] = None,
  markers: RawMarkerTable,
  stackTable: RawStackTable,
  frameTable: FrameTable,
  funcTable: FuncTable,
  resourceTable: ResourceTable,
  nativeSymbols: NativeSymbolTable,
  jsTracer: Option[JsTracerTable] = None,
  isPrivateBrowsing: Option[Boolean] = None,
  userContextId: Option[Double] = None,
)
private[fxprof] object RawThreadArgs {
  given JsonValueCodec[RawThreadArgs] = JsonCodecMaker.make
}
