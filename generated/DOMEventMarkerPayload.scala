package fxprof

class DOMEventMarkerPayload private (private[fxprof] val args: DOMEventMarkerPayloadArgs) {
  def `type`: DOMEventMarkerPayload_Type.type = args.`type`
  def latency: Option[Milliseconds] = args.latency
  def eventType: String = args.eventType
  def innerWindowID: Option[Double] = args.innerWindowID

  def `withtype`(value: DOMEventMarkerPayload_Type.type): DOMEventMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withLatency(value: Option[Milliseconds]): DOMEventMarkerPayload =
    copy(_.copy(latency = value))
  
  def withEventType(value: String): DOMEventMarkerPayload =
    copy(_.copy(eventType = value))
  
  def withInnerWindowID(value: Option[Double]): DOMEventMarkerPayload =
    copy(_.copy(innerWindowID = value))
  

  private def copy(f: DOMEventMarkerPayloadArgs => DOMEventMarkerPayloadArgs) = 
    new DOMEventMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object DOMEventMarkerPayload {
  def apply(
    `type`: DOMEventMarkerPayload_Type.type,
    eventType: String,
  ): DOMEventMarkerPayload = 
    new DOMEventMarkerPayload(DOMEventMarkerPayloadArgs(
      `type` = `type`,
      eventType = eventType,
    ))
  given JsonValueCodec[DOMEventMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: DOMEventMarkerPayload) = 
        new DOMEventMarkerPayload(summon[JsonValueCodec[DOMEventMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: DOMEventMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[DOMEventMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: DOMEventMarkerPayload = null
    }
  
}
private[fxprof] case class DOMEventMarkerPayloadArgs(
  `type`: DOMEventMarkerPayload_Type.type,
  latency: Option[Milliseconds] = None,
  eventType: String,
  innerWindowID: Option[Double] = None,
)
private[fxprof] object DOMEventMarkerPayloadArgs {
  given JsonValueCodec[DOMEventMarkerPayloadArgs] = JsonCodecMaker.make
}
