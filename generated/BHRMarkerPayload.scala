package fxprof

class BHRMarkerPayload private (args: BHRMarkerPayloadArgs) {
  def `type`: BHRMarkerPayload_Type.type = args.`type`

  def `withtype`(value: BHRMarkerPayload_Type.type): BHRMarkerPayload =
    copy(_.copy(`type` = value))

  private def copy(f: BHRMarkerPayloadArgs => BHRMarkerPayloadArgs) =
    new BHRMarkerPayload(f(args))

}

object BHRMarkerPayload {
  def apply(
      `type`: BHRMarkerPayload_Type.type
  ): BHRMarkerPayload =
    new BHRMarkerPayload(
      BHRMarkerPayloadArgs(
        `type` = `type`
      )
    )
}
private[fxprof] case class BHRMarkerPayloadArgs(
    `type`: BHRMarkerPayload_Type.type
)
