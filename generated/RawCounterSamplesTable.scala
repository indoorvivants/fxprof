package fxprof

class RawCounterSamplesTable private (private[fxprof] val args: RawCounterSamplesTableArgs) {
  def time: Option[Vector[Milliseconds]] = args.time
  def timeDeltas: Option[Vector[Milliseconds]] = args.timeDeltas
  def number: Option[Vector[Double]] = args.number
  def count: Vector[Double] = args.count
  def length: Double = args.length

  def withTime(value: Option[Vector[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(time = value))
  
  def withTimeDeltas(value: Option[Vector[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  def withNumber(value: Option[Vector[Double]]): RawCounterSamplesTable =
    copy(_.copy(number = value))
  
  def withCount(value: Vector[Double]): RawCounterSamplesTable =
    copy(_.copy(count = value))
  
  def withLength(value: Double): RawCounterSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawCounterSamplesTableArgs => RawCounterSamplesTableArgs) = 
    new RawCounterSamplesTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawCounterSamplesTable {
  def apply(
    length: Double,
  ): RawCounterSamplesTable = 
    new RawCounterSamplesTable(RawCounterSamplesTableArgs(
      length = length,
    ))
  given JsonValueCodec[RawCounterSamplesTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawCounterSamplesTable) = 
        new RawCounterSamplesTable(summon[JsonValueCodec[RawCounterSamplesTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawCounterSamplesTable, out: JsonWriter) = 
        summon[JsonValueCodec[RawCounterSamplesTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawCounterSamplesTable = null
    }
  
}
private[fxprof] case class RawCounterSamplesTableArgs(
  time: Option[Vector[Milliseconds]] = None,
  timeDeltas: Option[Vector[Milliseconds]] = None,
  number: Option[Vector[Double]] = None,
  count: Vector[Double] = Vector.empty,
  length: Double,
)
private[fxprof] object RawCounterSamplesTableArgs {
  given JsonValueCodec[RawCounterSamplesTableArgs] = JsonCodecMaker.make
}
