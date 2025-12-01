package fxprof

class PaintProfilerMarkerTracing private (args: PaintProfilerMarkerTracingArgs) {
  def `type`: PaintProfilerMarkerTracing_Type.type = args.`type`
  def category: PaintProfilerMarkerTracing_Category.type = args.category
  def cause: Option[CauseBacktrace] = args.cause

  def `withtype`(value: PaintProfilerMarkerTracing_Type.type): PaintProfilerMarkerTracing =
    copy(_.copy(`type` = value))
  
  def withCategory(value: PaintProfilerMarkerTracing_Category.type): PaintProfilerMarkerTracing =
    copy(_.copy(category = value))
  
  def withCause(value: Option[CauseBacktrace]): PaintProfilerMarkerTracing =
    copy(_.copy(cause = value))
  

  private def copy(f: PaintProfilerMarkerTracingArgs => PaintProfilerMarkerTracingArgs) = 
    new PaintProfilerMarkerTracing(f(args))
  
}

object PaintProfilerMarkerTracing {
  def apply(
    `type`: PaintProfilerMarkerTracing_Type.type,
    category: PaintProfilerMarkerTracing_Category.type,
  ): PaintProfilerMarkerTracing = 
    new PaintProfilerMarkerTracing(PaintProfilerMarkerTracingArgs(
      `type` = `type`,
      category = category,
    ))
}
private[fxprof] case class PaintProfilerMarkerTracingArgs(
  `type`: PaintProfilerMarkerTracing_Type.type,
  category: PaintProfilerMarkerTracing_Category.type,
  cause: Option[CauseBacktrace] = None,
)
