package fxprof

class VisualMetrics private (args: VisualMetricsArgs) {
  def FirstVisualChange: Double = args.FirstVisualChange
  def LastVisualChange: Double = args.LastVisualChange
  def SpeedIndex: Double = args.SpeedIndex
  def VisualProgress: Array[ProgressGraphData] = args.VisualProgress
  def ContentfulSpeedIndex: Option[Double] = args.ContentfulSpeedIndex
  def ContentfulSpeedIndexProgress: Option[Array[ProgressGraphData]] = args.ContentfulSpeedIndexProgress
  def PerceptualSpeedIndex: Option[Double] = args.PerceptualSpeedIndex
  def PerceptualSpeedIndexProgress: Option[Array[ProgressGraphData]] = args.PerceptualSpeedIndexProgress
  def VisualReadiness: Double = args.VisualReadiness
  def VisualComplete85: Double = args.VisualComplete85
  def VisualComplete95: Double = args.VisualComplete95
  def VisualComplete99: Double = args.VisualComplete99

  def withFirstVisualChange(value: Double): VisualMetrics =
    copy(_.copy(FirstVisualChange = value))
  
  def withLastVisualChange(value: Double): VisualMetrics =
    copy(_.copy(LastVisualChange = value))
  
  def withSpeedIndex(value: Double): VisualMetrics =
    copy(_.copy(SpeedIndex = value))
  
  def withVisualProgress(value: Array[ProgressGraphData]): VisualMetrics =
    copy(_.copy(VisualProgress = value))
  
  def withContentfulSpeedIndex(value: Option[Double]): VisualMetrics =
    copy(_.copy(ContentfulSpeedIndex = value))
  
  def withContentfulSpeedIndexProgress(value: Option[Array[ProgressGraphData]]): VisualMetrics =
    copy(_.copy(ContentfulSpeedIndexProgress = value))
  
  def withPerceptualSpeedIndex(value: Option[Double]): VisualMetrics =
    copy(_.copy(PerceptualSpeedIndex = value))
  
  def withPerceptualSpeedIndexProgress(value: Option[Array[ProgressGraphData]]): VisualMetrics =
    copy(_.copy(PerceptualSpeedIndexProgress = value))
  
  def withVisualReadiness(value: Double): VisualMetrics =
    copy(_.copy(VisualReadiness = value))
  
  def withVisualComplete85(value: Double): VisualMetrics =
    copy(_.copy(VisualComplete85 = value))
  
  def withVisualComplete95(value: Double): VisualMetrics =
    copy(_.copy(VisualComplete95 = value))
  
  def withVisualComplete99(value: Double): VisualMetrics =
    copy(_.copy(VisualComplete99 = value))
  

  private def copy(f: VisualMetricsArgs => VisualMetricsArgs) = 
    new VisualMetrics(f(args))
  
}

object VisualMetrics {
  def apply(
    FirstVisualChange: Double,
    LastVisualChange: Double,
    SpeedIndex: Double,
    VisualReadiness: Double,
    VisualComplete85: Double,
    VisualComplete95: Double,
    VisualComplete99: Double,
  ): VisualMetrics = 
    new VisualMetrics(VisualMetricsArgs(
      FirstVisualChange = FirstVisualChange,
      LastVisualChange = LastVisualChange,
      SpeedIndex = SpeedIndex,
      VisualReadiness = VisualReadiness,
      VisualComplete85 = VisualComplete85,
      VisualComplete95 = VisualComplete95,
      VisualComplete99 = VisualComplete99,
    ))
}
private[fxprof] case class VisualMetricsArgs(
  FirstVisualChange: Double,
  LastVisualChange: Double,
  SpeedIndex: Double,
  VisualProgress: Array[ProgressGraphData] = Array.empty,
  ContentfulSpeedIndex: Option[Double] = None,
  ContentfulSpeedIndexProgress: Option[Array[ProgressGraphData]] = None,
  PerceptualSpeedIndex: Option[Double] = None,
  PerceptualSpeedIndexProgress: Option[Array[ProgressGraphData]] = None,
  VisualReadiness: Double,
  VisualComplete85: Double,
  VisualComplete95: Double,
  VisualComplete99: Double,
)
