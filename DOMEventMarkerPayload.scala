package fxprof

class DOMEventMarkerPayload private (args: DOMEventMarkerPayloadArgs) {
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

private[fxprof] case class DOMEventMarkerPayloadArgs(
  `type`: DOMEventMarkerPayload_Type.type,
  latency: Option[Milliseconds],
  eventType: String,
  innerWindowID: Option[Double],
)
