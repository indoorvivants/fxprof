package fxprof

class NavigationMarkerPayload private (args: NavigationMarkerPayloadArgs) {
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

object NavigationMarkerPayload {
  def apply(
    `type`: NavigationMarkerPayload_Type.type,
    category: NavigationMarkerPayload_Category.type,
  ): NavigationMarkerPayload = 
    new NavigationMarkerPayload(NavigationMarkerPayloadArgs(
      `type` = `type`,
      category = category,
    ))
}
private[fxprof] case class NavigationMarkerPayloadArgs(
  `type`: NavigationMarkerPayload_Type.type,
  category: NavigationMarkerPayload_Category.type,
  eventType: Option[String] = None,
  innerWindowID: Option[Double] = None,
)
