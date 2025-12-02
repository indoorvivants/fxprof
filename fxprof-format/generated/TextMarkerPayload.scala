package fxprof

class TextMarkerPayload private (
    private[fxprof] val args: TextMarkerPayloadArgs
) {
  def `type`: TextMarkerPayload_Type.type = args.`type`

  def name: String = args.name

  def cause: Option[CauseBacktrace] = args.cause

  def innerWindowID: Option[Double] = args.innerWindowID

  def `withtype`(value: TextMarkerPayload_Type.type): TextMarkerPayload =
    copy(_.copy(`type` = value))

  def withName(value: String): TextMarkerPayload =
    copy(_.copy(name = value))

  def withCause(value: Option[CauseBacktrace]): TextMarkerPayload =
    copy(_.copy(cause = value))

  def withInnerWindowID(value: Option[Double]): TextMarkerPayload =
    copy(_.copy(innerWindowID = value))

  private def copy(f: TextMarkerPayloadArgs => TextMarkerPayloadArgs) =
    new TextMarkerPayload(f(args))

}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object TextMarkerPayload {

  /** Construct a [[TextMarkerPayload]]
    * @param type
    * @param name
    */
  def apply(
      `type`: TextMarkerPayload_Type.type,
      name: String
  ): TextMarkerPayload =
    new TextMarkerPayload(
      TextMarkerPayloadArgs(
        `type` = `type`,
        name = name,
        cause = None,
        innerWindowID = None
      )
    )
  given JsonValueCodec[TextMarkerPayload] =
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: TextMarkerPayload) =
        new TextMarkerPayload(
          summon[JsonValueCodec[TextMarkerPayloadArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: TextMarkerPayload, out: JsonWriter) =
        summon[JsonValueCodec[TextMarkerPayloadArgs]].encodeValue(x.args, out)

      def nullValue: TextMarkerPayload = null
    }

}
private[fxprof] case class TextMarkerPayloadArgs(
    `type`: TextMarkerPayload_Type.type,
    name: String,
    cause: Option[CauseBacktrace],
    innerWindowID: Option[Double]
)
private[fxprof] object TextMarkerPayloadArgs {
  given JsonValueCodec[TextMarkerPayloadArgs] = JsonCodecMaker.make
}
