package fxprof

class UrlMarkerPayload private (private[fxprof] val args: UrlMarkerPayloadArgs) {
  def `type`: UrlMarkerPayload_Type.type = args.`type`

  def url: String = args.url


  def `withtype`(value: UrlMarkerPayload_Type.type): UrlMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withUrl(value: String): UrlMarkerPayload =
    copy(_.copy(url = value))
  

  private def copy(f: UrlMarkerPayloadArgs => UrlMarkerPayloadArgs) = 
    new UrlMarkerPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[UrlMarkerPayload] && o.asInstanceOf[UrlMarkerPayload].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object UrlMarkerPayload {
  /** Construct a [[UrlMarkerPayload]]
      @param type
      @param url
    */
  def apply(
    `type`: UrlMarkerPayload_Type.type,
    url: String,
  ): UrlMarkerPayload = 
    new UrlMarkerPayload(UrlMarkerPayloadArgs(
      `type` = `type`,
      url = url,
    ))
  implicit val codec: JsonValueCodec[UrlMarkerPayload] = 
    new JsonValueCodec[UrlMarkerPayload] {
      def decodeValue(in: JsonReader, default: UrlMarkerPayload) = 
        new UrlMarkerPayload(implicitly[JsonValueCodec[UrlMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: UrlMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[UrlMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: UrlMarkerPayload = null
    }
  
}
private[fxprof] case class UrlMarkerPayloadArgs(
  `type`: UrlMarkerPayload_Type.type,
  url: String,
)
private[fxprof] object UrlMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[UrlMarkerPayloadArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
