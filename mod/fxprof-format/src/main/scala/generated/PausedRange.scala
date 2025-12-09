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
  

  override def equals(o: Any) = o.isInstanceOf[PausedRange] && o.asInstanceOf[PausedRange].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
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
  implicit val codec: JsonValueCodec[PausedRange] = 
    new JsonValueCodec[PausedRange] {
      def decodeValue(in: JsonReader, default: PausedRange) = 
        new PausedRange(implicitly[JsonValueCodec[PausedRangeArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: PausedRange, out: JsonWriter) = 
        implicitly[JsonValueCodec[PausedRangeArgs]].encodeValue(x.args, out)
      
      def nullValue: PausedRange = null
    }
  
}
private[fxprof] case class PausedRangeArgs(
  startTime: Option[Milliseconds],
  endTime: Option[Milliseconds],
  reason: PausedRange_Reason,
)
private[fxprof] object PausedRangeArgs {
  implicit val codec: JsonValueCodec[PausedRangeArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
