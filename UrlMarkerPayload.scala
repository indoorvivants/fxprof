package fxprof

class UrlMarkerPayload private (args: UrlMarkerPayloadArgs) {
  def `type`: UrlMarkerPayload_Type.type = args.`type`
  def url: String = args.url

  def `withtype`(value: UrlMarkerPayload_Type.type): UrlMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withUrl(value: String): UrlMarkerPayload =
    copy(_.copy(url = value))
  

  private def copy(f: UrlMarkerPayloadArgs => UrlMarkerPayloadArgs) = 
    new UrlMarkerPayload(f(args))
  
}

private[fxprof] case class UrlMarkerPayloadArgs(
  `type`: UrlMarkerPayload_Type.type,
  url: String,
)
