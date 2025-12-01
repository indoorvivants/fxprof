package fxprof

class CauseBacktrace private (private[fxprof] val args: CauseBacktraceArgs) {
  def tid: Option[Tid] = args.tid
  def time: Option[Milliseconds] = args.time
  def stack: Option[IndexIntoStackTable] = args.stack

  def withTid(value: Option[Tid]): CauseBacktrace =
    copy(_.copy(tid = value))
  
  def withTime(value: Option[Milliseconds]): CauseBacktrace =
    copy(_.copy(time = value))
  
  def withStack(value: Option[IndexIntoStackTable]): CauseBacktrace =
    copy(_.copy(stack = value))
  

  private def copy(f: CauseBacktraceArgs => CauseBacktraceArgs) = 
    new CauseBacktrace(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object CauseBacktrace {
  def apply(
  ): CauseBacktrace = 
    new CauseBacktrace(CauseBacktraceArgs(
    ))
  given JsonValueCodec[CauseBacktrace] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: CauseBacktrace) = 
        new CauseBacktrace(summon[JsonValueCodec[CauseBacktraceArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: CauseBacktrace, out: JsonWriter) = 
        summon[JsonValueCodec[CauseBacktraceArgs]].encodeValue(x.args, out)
      
      def nullValue: CauseBacktrace = null
    }
  
}
private[fxprof] case class CauseBacktraceArgs(
  tid: Option[Tid] = None,
  time: Option[Milliseconds] = None,
  stack: Option[IndexIntoStackTable] = None,
)
private[fxprof] object CauseBacktraceArgs {
  given JsonValueCodec[CauseBacktraceArgs] = JsonCodecMaker.make
}
