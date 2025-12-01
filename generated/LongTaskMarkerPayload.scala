package fxprof

class LongTaskMarkerPayload private (args: LongTaskMarkerPayloadArgs) {
  def `type`: LongTaskMarkerPayload_Type.type = args.`type`
  def category: LongTaskMarkerPayload_Category.type = args.category

  def `withtype`(value: LongTaskMarkerPayload_Type.type): LongTaskMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCategory(value: LongTaskMarkerPayload_Category.type): LongTaskMarkerPayload =
    copy(_.copy(category = value))
  

  private def copy(f: LongTaskMarkerPayloadArgs => LongTaskMarkerPayloadArgs) = 
    new LongTaskMarkerPayload(f(args))
  
}

private[fxprof] case class LongTaskMarkerPayloadArgs(
  `type`: LongTaskMarkerPayload_Type.type,
  category: LongTaskMarkerPayload_Category.type,
)
