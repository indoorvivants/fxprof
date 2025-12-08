package fxprof

class NavigationMarkerPayload private (private[fxprof] val args: NavigationMarkerPayloadArgs) {
  def `type`: NavigationMarkerPayload_Type.type = args.`type`

  def category: NavigationMarkerPayload_Category.type = args.category

  def eventType: Option[String] = args.eventType

  def innerWindowID: Option[Double] = args.innerWindowID


  def `withtype`(value: NavigationMarkerPayload_Type.type): NavigationMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCategory(value: NavigationMarkerPayload_Category.type): NavigationMarkerPayload =
    copy(_.copy(category = value))
  
  def withEventType(value: Option[String]): NavigationMarkerPayload =
    copy(_.copy(eventType = value))
  
  def withInnerWindowID(value: Option[Double]): NavigationMarkerPayload =
    copy(_.copy(innerWindowID = value))
  

  private def copy(f: NavigationMarkerPayloadArgs => NavigationMarkerPayloadArgs) = 
    new NavigationMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object NavigationMarkerPayload {
  /** Construct a [[NavigationMarkerPayload]]
      @param type
      @param category
    */
  def apply(
    `type`: NavigationMarkerPayload_Type.type,
    category: NavigationMarkerPayload_Category.type,
  ): NavigationMarkerPayload = 
    new NavigationMarkerPayload(NavigationMarkerPayloadArgs(
      `type` = `type`,
      category = category,
      eventType = None,
      innerWindowID = None,
    ))
  implicit val codec: JsonValueCodec[NavigationMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: NavigationMarkerPayload) = 
        new NavigationMarkerPayload(summon[JsonValueCodec[NavigationMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NavigationMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[NavigationMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: NavigationMarkerPayload = null
    }
  
}
private[fxprof] case class NavigationMarkerPayloadArgs(
  `type`: NavigationMarkerPayload_Type.type,
  category: NavigationMarkerPayload_Category.type,
  eventType: Option[String],
  innerWindowID: Option[Double],
)
private[fxprof] object NavigationMarkerPayloadArgs {
  implicit val codec: ConfiguredJsonValueCodec[NavigationMarkerPayloadArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
