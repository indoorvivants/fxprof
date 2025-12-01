package fxprof

class StyleMarkerPayload private (args: StyleMarkerPayloadArgs) {
  def `type`: StyleMarkerPayload_Type.type = args.`type`
  def category: StyleMarkerPayload_Category.type = args.category
  def cause: Option[CauseBacktrace] = args.cause
  def elementsTraversed: Double = args.elementsTraversed
  def elementsStyled: Double = args.elementsStyled
  def elementsMatched: Double = args.elementsMatched
  def stylesShared: Double = args.stylesShared
  def stylesReused: Double = args.stylesReused

  def `withtype`(value: StyleMarkerPayload_Type.type): StyleMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCategory(value: StyleMarkerPayload_Category.type): StyleMarkerPayload =
    copy(_.copy(category = value))
  
  def withCause(value: Option[CauseBacktrace]): StyleMarkerPayload =
    copy(_.copy(cause = value))
  
  def withElementsTraversed(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsTraversed = value))
  
  def withElementsStyled(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsStyled = value))
  
  def withElementsMatched(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsMatched = value))
  
  def withStylesShared(value: Double): StyleMarkerPayload =
    copy(_.copy(stylesShared = value))
  
  def withStylesReused(value: Double): StyleMarkerPayload =
    copy(_.copy(stylesReused = value))
  

  private def copy(f: StyleMarkerPayloadArgs => StyleMarkerPayloadArgs) = 
    new StyleMarkerPayload(f(args))
  
}

private[fxprof] case class StyleMarkerPayloadArgs(
  `type`: StyleMarkerPayload_Type.type,
  category: StyleMarkerPayload_Category.type,
  cause: Option[CauseBacktrace],
  elementsTraversed: Double,
  elementsStyled: Double,
  elementsMatched: Double,
  stylesShared: Double,
  stylesReused: Double,
)
