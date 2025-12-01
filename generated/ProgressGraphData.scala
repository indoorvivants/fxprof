package fxprof

class ProgressGraphData private (private[fxprof] val args: ProgressGraphDataArgs) {
  def percent: Double = args.percent
  def timestamp: Option[Milliseconds] = args.timestamp

  def withPercent(value: Double): ProgressGraphData =
    copy(_.copy(percent = value))
  
  def withTimestamp(value: Option[Milliseconds]): ProgressGraphData =
    copy(_.copy(timestamp = value))
  

  private def copy(f: ProgressGraphDataArgs => ProgressGraphDataArgs) = 
    new ProgressGraphData(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProgressGraphData {
  def apply(
    percent: Double,
  ): ProgressGraphData = 
    new ProgressGraphData(ProgressGraphDataArgs(
      percent = percent,
      timestamp = None,
    ))
  given JsonValueCodec[ProgressGraphData] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProgressGraphData) = 
        new ProgressGraphData(summon[JsonValueCodec[ProgressGraphDataArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProgressGraphData, out: JsonWriter) = 
        summon[JsonValueCodec[ProgressGraphDataArgs]].encodeValue(x.args, out)
      
      def nullValue: ProgressGraphData = null
    }
  
}
private[fxprof] case class ProgressGraphDataArgs(
  percent: Double,
  timestamp: Option[Milliseconds],
)
private[fxprof] object ProgressGraphDataArgs {
  given JsonValueCodec[ProgressGraphDataArgs] = JsonCodecMaker.make
}
