package fxprof

/**
  * Visual progress describes the visual progression during page load. A sample is generated
  * everytime the visual completeness of the webpage changes.
  */
class ProgressGraphData private (private[fxprof] val args: ProgressGraphDataArgs) {
  /** A percentage that describes the visual completeness of the webpage, ranging from 0% - 100%
    */
  def percent: Double = args.percent

  /** The time in milliseconds which the sample was taken.
    * This can be null due to https://github.com/sitespeedio/browsertime/issues/1746.
    */
  def timestamp: Option[Milliseconds] = args.timestamp


  /** Setter for [[percent]] field

    * A percentage that describes the visual completeness of the webpage, ranging from 0% - 100%
    */
  def withPercent(value: Double): ProgressGraphData =
    copy(_.copy(percent = value))
  
  /** Setter for [[timestamp]] field

    * The time in milliseconds which the sample was taken.
    * This can be null due to https://github.com/sitespeedio/browsertime/issues/1746.
    */
  def withTimestamp(value: Option[Milliseconds]): ProgressGraphData =
    copy(_.copy(timestamp = value))
  

  private def copy(f: ProgressGraphDataArgs => ProgressGraphDataArgs) = 
    new ProgressGraphData(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProgressGraphData {
  /** Construct a [[ProgressGraphData]]
      @param percentA percentage that describes the visual completeness of the webpage, ranging from 0% - 100%
    */
  def apply(
    percent: Double,
  ): ProgressGraphData = 
    new ProgressGraphData(ProgressGraphDataArgs(
      percent = percent,
      timestamp = None,
    ))
  implicit val codec: JsonValueCodec[ProgressGraphData] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProgressGraphData) = 
        new ProgressGraphData(summon[JsonValueCodec[ProgressGraphDataArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProgressGraphData, out: JsonWriter) = 
        implicitly[JsonValueCodec[ProgressGraphDataArgs]].encodeValue(x.args, out)
      
      def nullValue: ProgressGraphData = null
    }
  
}
private[fxprof] case class ProgressGraphDataArgs(
  percent: Double,
  timestamp: Option[Milliseconds],
)
private[fxprof] object ProgressGraphDataArgs {
  implicit val codec: ConfiguredJsonValueCodec[ProgressGraphDataArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
