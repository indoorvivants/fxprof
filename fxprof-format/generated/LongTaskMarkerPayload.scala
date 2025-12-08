package fxprof

class LongTaskMarkerPayload private (private[fxprof] val args: LongTaskMarkerPayloadArgs) {
  def `type`: LongTaskMarkerPayload_Type.type = args.`type`

  def category: LongTaskMarkerPayload_Category.type = args.category


  def `withtype`(value: LongTaskMarkerPayload_Type.type): LongTaskMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCategory(value: LongTaskMarkerPayload_Category.type): LongTaskMarkerPayload =
    copy(_.copy(category = value))
  

  private def copy(f: LongTaskMarkerPayloadArgs => LongTaskMarkerPayloadArgs) = 
    new LongTaskMarkerPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[LongTaskMarkerPayload] && o.asInstanceOf[LongTaskMarkerPayload].args.equals(this.args)
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object LongTaskMarkerPayload {
  /** Construct a [[LongTaskMarkerPayload]]
      @param type
      @param category
    */
  def apply(
    `type`: LongTaskMarkerPayload_Type.type,
    category: LongTaskMarkerPayload_Category.type,
  ): LongTaskMarkerPayload = 
    new LongTaskMarkerPayload(LongTaskMarkerPayloadArgs(
      `type` = `type`,
      category = category,
    ))
  implicit val codec: JsonValueCodec[LongTaskMarkerPayload] = 
    new JsonValueCodec[LongTaskMarkerPayload] {
      def decodeValue(in: JsonReader, default: LongTaskMarkerPayload) = 
        new LongTaskMarkerPayload(implicitly[JsonValueCodec[LongTaskMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: LongTaskMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[LongTaskMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: LongTaskMarkerPayload = null
    }
  
}
private[fxprof] case class LongTaskMarkerPayloadArgs(
  `type`: LongTaskMarkerPayload_Type.type,
  category: LongTaskMarkerPayload_Category.type,
)
private[fxprof] object LongTaskMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[LongTaskMarkerPayloadArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
