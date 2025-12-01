package fxprof

class CcMarkerTracing private (args: CcMarkerTracingArgs) {
  def `type`: CcMarkerTracing_Type.type = args.`type`
  def category: CcMarkerTracing_Category.type = args.category
  def first: Option[String] = args.first
  def desc: Option[String] = args.desc
  def second: Option[String] = args.second

  def `withtype`(value: CcMarkerTracing_Type.type): CcMarkerTracing =
    copy(_.copy(`type` = value))
  
  def withCategory(value: CcMarkerTracing_Category.type): CcMarkerTracing =
    copy(_.copy(category = value))
  
  def withFirst(value: Option[String]): CcMarkerTracing =
    copy(_.copy(first = value))
  
  def withDesc(value: Option[String]): CcMarkerTracing =
    copy(_.copy(desc = value))
  
  def withSecond(value: Option[String]): CcMarkerTracing =
    copy(_.copy(second = value))
  

  private def copy(f: CcMarkerTracingArgs => CcMarkerTracingArgs) = 
    new CcMarkerTracing(f(args))
  
}

private[fxprof] case class CcMarkerTracingArgs(
  `type`: CcMarkerTracing_Type.type,
  category: CcMarkerTracing_Category.type,
  first: Option[String],
  desc: Option[String],
  second: Option[String],
)
