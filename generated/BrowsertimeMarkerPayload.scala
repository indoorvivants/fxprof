package fxprof

class BrowsertimeMarkerPayload private (private[fxprof] val args: BrowsertimeMarkerPayloadArgs) {
  def `type`: BrowsertimeMarkerPayload_Type.type = args.`type`
  def percentage: Double = args.percentage

  def `withtype`(value: BrowsertimeMarkerPayload_Type.type): BrowsertimeMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withPercentage(value: Double): BrowsertimeMarkerPayload =
    copy(_.copy(percentage = value))
  

  private def copy(f: BrowsertimeMarkerPayloadArgs => BrowsertimeMarkerPayloadArgs) = 
    new BrowsertimeMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object BrowsertimeMarkerPayload {
  def apply(
    `type`: BrowsertimeMarkerPayload_Type.type,
    percentage: Double,
  ): BrowsertimeMarkerPayload = 
    new BrowsertimeMarkerPayload(BrowsertimeMarkerPayloadArgs(
      `type` = `type`,
      percentage = percentage,
    ))
  given JsonValueCodec[BrowsertimeMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: BrowsertimeMarkerPayload) = 
        new BrowsertimeMarkerPayload(summon[JsonValueCodec[BrowsertimeMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: BrowsertimeMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[BrowsertimeMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: BrowsertimeMarkerPayload = null
    }
  
}
private[fxprof] case class BrowsertimeMarkerPayloadArgs(
  `type`: BrowsertimeMarkerPayload_Type.type,
  percentage: Double,
)
private[fxprof] object BrowsertimeMarkerPayloadArgs {
  given JsonValueCodec[BrowsertimeMarkerPayloadArgs] = JsonCodecMaker.make
}
