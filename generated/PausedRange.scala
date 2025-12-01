package fxprof

class PausedRange private (private[fxprof] val args: PausedRangeArgs) {
  def startTime: Option[Milliseconds] = args.startTime
  def endTime: Option[Milliseconds] = args.endTime
  def reason: PausedRange_Reason = args.reason

  def withStartTime(value: Option[Milliseconds]): PausedRange =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Option[Milliseconds]): PausedRange =
    copy(_.copy(endTime = value))
  
  def withReason(value: PausedRange_Reason): PausedRange =
    copy(_.copy(reason = value))
  

  private def copy(f: PausedRangeArgs => PausedRangeArgs) = 
    new PausedRange(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object PausedRange {
  def apply(
    reason: PausedRange_Reason,
  ): PausedRange = 
    new PausedRange(PausedRangeArgs(
      reason = reason,
    ))
  given JsonValueCodec[PausedRange] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: PausedRange) = 
        new PausedRange(summon[JsonValueCodec[PausedRangeArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: PausedRange, out: JsonWriter) = 
        summon[JsonValueCodec[PausedRangeArgs]].encodeValue(x.args, out)
      
      def nullValue: PausedRange = null
    }
  
}
private[fxprof] case class PausedRangeArgs(
  startTime: Option[Milliseconds] = None,
  endTime: Option[Milliseconds] = None,
  reason: PausedRange_Reason,
)
private[fxprof] object PausedRangeArgs {
  given JsonValueCodec[PausedRangeArgs] = JsonCodecMaker.make
}
