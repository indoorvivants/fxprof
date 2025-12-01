package fxprof

class BrowsertimeMarkerPayload private (args: BrowsertimeMarkerPayloadArgs) {
  def `type`: BrowsertimeMarkerPayload_Type.type = args.`type`
  def percentage: Double = args.percentage

  def `withtype`(value: BrowsertimeMarkerPayload_Type.type): BrowsertimeMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withPercentage(value: Double): BrowsertimeMarkerPayload =
    copy(_.copy(percentage = value))
  

  private def copy(f: BrowsertimeMarkerPayloadArgs => BrowsertimeMarkerPayloadArgs) = 
    new BrowsertimeMarkerPayload(f(args))
  
}

object BrowsertimeMarkerPayload {
  def apply(
    `type`: BrowsertimeMarkerPayload_Type.type,
    percentage: Double,
  ): BrowsertimeMarkerPayload = 
    new BrowsertimeMarkerPayload(BrowsertimeMarkerPayloadArgs(
      `type` = `type`,
      percentage = percentage,
    ))
}
private[fxprof] case class BrowsertimeMarkerPayloadArgs(
  `type`: BrowsertimeMarkerPayload_Type.type,
  percentage: Double,
)
