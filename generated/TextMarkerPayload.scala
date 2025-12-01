package fxprof

class TextMarkerPayload private (args: TextMarkerPayloadArgs) {
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

object TextMarkerPayload {
  def apply(
    `type`: TextMarkerPayload_Type.type,
    name: String,
  ): TextMarkerPayload = 
    new TextMarkerPayload(TextMarkerPayloadArgs(
      `type` = `type`,
      name = name,
    ))
}
private[fxprof] case class TextMarkerPayloadArgs(
  `type`: TextMarkerPayload_Type.type,
  name: String,
  cause: Option[CauseBacktrace] = None,
  innerWindowID: Option[Double] = None,
)
