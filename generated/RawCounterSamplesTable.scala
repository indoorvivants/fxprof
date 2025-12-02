package fxprof

class RawCounterSamplesTable private (private[fxprof] val args: RawCounterSamplesTableArgs) {
  def time: Option[Vector[Milliseconds]] = args.time

  def timeDeltas: Option[Vector[Milliseconds]] = args.timeDeltas

  /** The number of times the Counter's "number" was changed since the previous sample.
    * This property was mandatory until the format version 42, it was made optional in 43.
    */
  def number: Option[Vector[Double]] = args.number

  /** The count of the data, for instance for memory this would be bytes.
    */
  def count: Vector[Double] = args.count

  def length: Double = args.length


  def withTime(value: Option[Vector[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(time = value))
  
  def withTimeDeltas(value: Option[Vector[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  /** Setter for [[$name]] field

    * The number of times the Counter's "number" was changed since the previous sample.
    * This property was mandatory until the format version 42, it was made optional in 43.
    */
  def withNumber(value: Option[Vector[Double]]): RawCounterSamplesTable =
    copy(_.copy(number = value))
  
  /** Setter for [[$name]] field

    * The count of the data, for instance for memory this would be bytes.
    */
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
  /** Construct a [[RawCounterSamplesTable]]
      @param length
    */
  def apply(
    length: Double,
  ): RawCounterSamplesTable = 
    new RawCounterSamplesTable(RawCounterSamplesTableArgs(
      time = None,
      timeDeltas = None,
      number = None,
      count = Vector.empty,
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
  time: Option[Vector[Milliseconds]],
  timeDeltas: Option[Vector[Milliseconds]],
  number: Option[Vector[Double]],
  count: Vector[Double],
  length: Double,
)
private[fxprof] object RawCounterSamplesTableArgs {
  given JsonValueCodec[RawCounterSamplesTableArgs] = JsonCodecMaker.make
}
