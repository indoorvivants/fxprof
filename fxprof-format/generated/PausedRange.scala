package fxprof

/**
  * Information about a period of time during which no samples were collected.
  */
class PausedRange private (private[fxprof] val args: PausedRangeArgs) {
  /** null if the profiler was already paused at the beginning of the period of
    * time that was present in the profile buffer
    */
  def startTime: Option[Milliseconds] = args.startTime

  /** null if the profiler was still paused when the profile was captured
    */
  def endTime: Option[Milliseconds] = args.endTime

  def reason: PausedRange_Reason = args.reason


  /** Setter for [[startTime]] field

    * null if the profiler was already paused at the beginning of the period of
    * time that was present in the profile buffer
    */
  def withStartTime(value: Option[Milliseconds]): PausedRange =
    copy(_.copy(startTime = value))
  
  /** Setter for [[endTime]] field

    * null if the profiler was still paused when the profile was captured
    */
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
  /** Construct a [[PausedRange]]
      @param reason
    */
  def apply(
    reason: PausedRange_Reason,
  ): PausedRange = 
    new PausedRange(PausedRangeArgs(
      startTime = None,
      endTime = None,
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
  startTime: Option[Milliseconds],
  endTime: Option[Milliseconds],
  reason: PausedRange_Reason,
)
private[fxprof] object PausedRangeArgs {
  given ConfiguredJsonValueCodec[PausedRangeArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
