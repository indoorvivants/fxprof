package fxprof

class BHRMarkerPayload private (private[fxprof] val args: BHRMarkerPayloadArgs) {
  def `type`: BHRMarkerPayload_Type.type = args.`type`


  def `withtype`(value: BHRMarkerPayload_Type.type): BHRMarkerPayload =
    copy(_.copy(`type` = value))
  

  private def copy(f: BHRMarkerPayloadArgs => BHRMarkerPayloadArgs) = 
    new BHRMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object BHRMarkerPayload {
  /** Construct a [[BHRMarkerPayload]]
      @param type
    */
  def apply(
    `type`: BHRMarkerPayload_Type.type,
  ): BHRMarkerPayload = 
    new BHRMarkerPayload(BHRMarkerPayloadArgs(
      `type` = `type`,
    ))
  given JsonValueCodec[BHRMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: BHRMarkerPayload) = 
        new BHRMarkerPayload(summon[JsonValueCodec[BHRMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: BHRMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[BHRMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: BHRMarkerPayload = null
    }
  
}
private[fxprof] case class BHRMarkerPayloadArgs(
  `type`: BHRMarkerPayload_Type.type,
)
private[fxprof] object BHRMarkerPayloadArgs {
  given ConfiguredJsonValueCodec[BHRMarkerPayloadArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(false).withTransientNone(false))
  
}
