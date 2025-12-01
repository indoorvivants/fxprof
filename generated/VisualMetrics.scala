package fxprof

class VisualMetrics private (private[fxprof] val args: VisualMetricsArgs) {
  def FirstVisualChange: Double = args.FirstVisualChange
  def LastVisualChange: Double = args.LastVisualChange
  def SpeedIndex: Double = args.SpeedIndex
  def VisualProgress: Vector[ProgressGraphData] = args.VisualProgress
  def ContentfulSpeedIndex: Option[Double] = args.ContentfulSpeedIndex
  def ContentfulSpeedIndexProgress: Option[Vector[ProgressGraphData]] = args.ContentfulSpeedIndexProgress
  def PerceptualSpeedIndex: Option[Double] = args.PerceptualSpeedIndex
  def PerceptualSpeedIndexProgress: Option[Vector[ProgressGraphData]] = args.PerceptualSpeedIndexProgress
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
  
  def withVisualProgress(value: Vector[ProgressGraphData]): VisualMetrics =
    copy(_.copy(VisualProgress = value))
  
  def withContentfulSpeedIndex(value: Option[Double]): VisualMetrics =
    copy(_.copy(ContentfulSpeedIndex = value))
  
  def withContentfulSpeedIndexProgress(value: Option[Vector[ProgressGraphData]]): VisualMetrics =
    copy(_.copy(ContentfulSpeedIndexProgress = value))
  
  def withPerceptualSpeedIndex(value: Option[Double]): VisualMetrics =
    copy(_.copy(PerceptualSpeedIndex = value))
  
  def withPerceptualSpeedIndexProgress(value: Option[Vector[ProgressGraphData]]): VisualMetrics =
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

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

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
      VisualProgress = Vector.empty,
      ContentfulSpeedIndex = None,
      ContentfulSpeedIndexProgress = None,
      PerceptualSpeedIndex = None,
      PerceptualSpeedIndexProgress = None,
      VisualReadiness = VisualReadiness,
      VisualComplete85 = VisualComplete85,
      VisualComplete95 = VisualComplete95,
      VisualComplete99 = VisualComplete99,
    ))
  given JsonValueCodec[VisualMetrics] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: VisualMetrics) = 
        new VisualMetrics(summon[JsonValueCodec[VisualMetricsArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: VisualMetrics, out: JsonWriter) = 
        summon[JsonValueCodec[VisualMetricsArgs]].encodeValue(x.args, out)
      
      def nullValue: VisualMetrics = null
    }
  
}
private[fxprof] case class VisualMetricsArgs(
  FirstVisualChange: Double,
  LastVisualChange: Double,
  SpeedIndex: Double,
  VisualProgress: Vector[ProgressGraphData],
  ContentfulSpeedIndex: Option[Double],
  ContentfulSpeedIndexProgress: Option[Vector[ProgressGraphData]],
  PerceptualSpeedIndex: Option[Double],
  PerceptualSpeedIndexProgress: Option[Vector[ProgressGraphData]],
  VisualReadiness: Double,
  VisualComplete85: Double,
  VisualComplete95: Double,
  VisualComplete99: Double,
)
private[fxprof] object VisualMetricsArgs {
  given JsonValueCodec[VisualMetricsArgs] = JsonCodecMaker.make
}
