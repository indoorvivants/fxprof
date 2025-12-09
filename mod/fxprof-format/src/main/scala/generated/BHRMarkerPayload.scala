package fxprof

class BHRMarkerPayload private (
    private[fxprof] val args: BHRMarkerPayloadArgs
) {
  def `type`: BHRMarkerPayload_Type.type = args.`type`

  def `withtype`(value: BHRMarkerPayload_Type.type): BHRMarkerPayload =
    copy(_.copy(`type` = value))

  private def copy(f: BHRMarkerPayloadArgs => BHRMarkerPayloadArgs) =
    new BHRMarkerPayload(f(args))

  override def equals(o: Any) = o.isInstanceOf[BHRMarkerPayload] && o
    .asInstanceOf[BHRMarkerPayload]
    .args
    .equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object BHRMarkerPayload {

  /** Construct a [[BHRMarkerPayload]]
    * @param type
    */
  def apply(
      `type`: BHRMarkerPayload_Type.type
  ): BHRMarkerPayload =
    new BHRMarkerPayload(
      BHRMarkerPayloadArgs(
        `type` = `type`
      )
    )
  implicit val codec: JsonValueCodec[BHRMarkerPayload] =
    new JsonValueCodec[BHRMarkerPayload] {
      def decodeValue(in: JsonReader, default: BHRMarkerPayload) =
        new BHRMarkerPayload(
          implicitly[JsonValueCodec[BHRMarkerPayloadArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: BHRMarkerPayload, out: JsonWriter) =
        implicitly[JsonValueCodec[BHRMarkerPayloadArgs]]
          .encodeValue(x.args, out)

      def nullValue: BHRMarkerPayload = null
    }

}
private[fxprof] case class BHRMarkerPayloadArgs(
    `type`: BHRMarkerPayload_Type.type
)
private[fxprof] object BHRMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[BHRMarkerPayloadArgs] =
    JsonCodecMaker.makeCirceLike

}
