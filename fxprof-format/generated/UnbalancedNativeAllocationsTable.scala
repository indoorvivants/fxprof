package fxprof

/**
  * This variant is the original version of the table, before the memory address
  * and threadId were added.
  */
class UnbalancedNativeAllocationsTable private (private[fxprof] val args: UnbalancedNativeAllocationsTableArgs) {
  def time: Vector[Milliseconds] = args.time

  /** "weight" is used here rather than "bytes", so that this type will match the
    * SamplesLikeTableShape.
    */
  def weight: Vector[Bytes] = args.weight

  def weightType: UnbalancedNativeAllocationsTable_WeightType.type = args.weightType

  def stack: Vector[Option[IndexIntoStackTable]] = args.stack

  def length: Double = args.length


  def withTime(value: Vector[Milliseconds]): UnbalancedNativeAllocationsTable =
    copy(_.copy(time = value))
  
  /** Setter for [[weight]] field

    * "weight" is used here rather than "bytes", so that this type will match the
    * SamplesLikeTableShape.
    */
  def withWeight(value: Vector[Bytes]): UnbalancedNativeAllocationsTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: UnbalancedNativeAllocationsTable_WeightType.type): UnbalancedNativeAllocationsTable =
    copy(_.copy(weightType = value))
  
  def withStack(value: Vector[Option[IndexIntoStackTable]]): UnbalancedNativeAllocationsTable =
    copy(_.copy(stack = value))
  
  def withLength(value: Double): UnbalancedNativeAllocationsTable =
    copy(_.copy(length = value))
  

  private def copy(f: UnbalancedNativeAllocationsTableArgs => UnbalancedNativeAllocationsTableArgs) = 
    new UnbalancedNativeAllocationsTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object UnbalancedNativeAllocationsTable {
  /** Construct a [[UnbalancedNativeAllocationsTable]]
      @param weightType
      @param length
    */
  def apply(
    weightType: UnbalancedNativeAllocationsTable_WeightType.type,
    length: Double,
  ): UnbalancedNativeAllocationsTable = 
    new UnbalancedNativeAllocationsTable(UnbalancedNativeAllocationsTableArgs(
      time = Vector.empty,
      weight = Vector.empty,
      weightType = weightType,
      stack = Vector.empty,
      length = length,
    ))
  implicit val codec: JsonValueCodec[UnbalancedNativeAllocationsTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: UnbalancedNativeAllocationsTable) = 
        new UnbalancedNativeAllocationsTable(summon[JsonValueCodec[UnbalancedNativeAllocationsTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: UnbalancedNativeAllocationsTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[UnbalancedNativeAllocationsTableArgs]].encodeValue(x.args, out)
      
      def nullValue: UnbalancedNativeAllocationsTable = null
    }
  
}
private[fxprof] case class UnbalancedNativeAllocationsTableArgs(
  time: Vector[Milliseconds],
  weight: Vector[Bytes],
  weightType: UnbalancedNativeAllocationsTable_WeightType.type,
  stack: Vector[Option[IndexIntoStackTable]],
  length: Double,
)
private[fxprof] object UnbalancedNativeAllocationsTableArgs {
  implicit val codec: ConfiguredJsonValueCodec[UnbalancedNativeAllocationsTableArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
