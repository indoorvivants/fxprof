package fxprof

class MediaSampleMarkerPayload private (private[fxprof] val args: MediaSampleMarkerPayloadArgs) {
  def `type`: MediaSampleMarkerPayload_Type.type = args.`type`

  def sampleStartTimeUs: Microseconds = args.sampleStartTimeUs

  def sampleEndTimeUs: Microseconds = args.sampleEndTimeUs


  def `withtype`(value: MediaSampleMarkerPayload_Type.type): MediaSampleMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withSampleStartTimeUs(value: Microseconds): MediaSampleMarkerPayload =
    copy(_.copy(sampleStartTimeUs = value))
  
  def withSampleEndTimeUs(value: Microseconds): MediaSampleMarkerPayload =
    copy(_.copy(sampleEndTimeUs = value))
  

  private def copy(f: MediaSampleMarkerPayloadArgs => MediaSampleMarkerPayloadArgs) = 
    new MediaSampleMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object MediaSampleMarkerPayload {
  /** Construct a [[MediaSampleMarkerPayload]]
      @param type
      @param sampleStartTimeUs
      @param sampleEndTimeUs
    */
  def apply(
    `type`: MediaSampleMarkerPayload_Type.type,
    sampleStartTimeUs: Microseconds,
    sampleEndTimeUs: Microseconds,
  ): MediaSampleMarkerPayload = 
    new MediaSampleMarkerPayload(MediaSampleMarkerPayloadArgs(
      `type` = `type`,
      sampleStartTimeUs = sampleStartTimeUs,
      sampleEndTimeUs = sampleEndTimeUs,
    ))
  given JsonValueCodec[MediaSampleMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: MediaSampleMarkerPayload) = 
        new MediaSampleMarkerPayload(summon[JsonValueCodec[MediaSampleMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: MediaSampleMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[MediaSampleMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: MediaSampleMarkerPayload = null
    }
  
}
private[fxprof] case class MediaSampleMarkerPayloadArgs(
  `type`: MediaSampleMarkerPayload_Type.type,
  sampleStartTimeUs: Microseconds,
  sampleEndTimeUs: Microseconds,
)
private[fxprof] object MediaSampleMarkerPayloadArgs {
  given JsonValueCodec[MediaSampleMarkerPayloadArgs] = JsonCodecMaker.make
}
