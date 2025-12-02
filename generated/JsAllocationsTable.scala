package fxprof

/**
  * JS allocations are recorded as a marker payload, but in profile processing they
  * are moved to the Thread. This allows them to be part of the stack processing pipeline.
  */
class JsAllocationsTable private (private[fxprof] val args: JsAllocationsTableArgs) {
  def time: Vector[Milliseconds] = args.time

  def className: Vector[String] = args.className

  def typeName: Vector[String] = args.typeName

  def coarseType: Vector[String] = args.coarseType

  /** "weight" is used here rather than "bytes", so that this type will match the
    * SamplesLikeTableShape.
    */
  def weight: Vector[Bytes] = args.weight

  def weightType: JsAllocationsTable_WeightType.type = args.weightType

  def inNursery: Vector[Boolean] = args.inNursery

  def stack: Vector[Option[IndexIntoStackTable]] = args.stack

  def length: Double = args.length


  def withTime(value: Vector[Milliseconds]): JsAllocationsTable =
    copy(_.copy(time = value))
  
  def withClassName(value: Vector[String]): JsAllocationsTable =
    copy(_.copy(className = value))
  
  def withTypeName(value: Vector[String]): JsAllocationsTable =
    copy(_.copy(typeName = value))
  
  def withCoarseType(value: Vector[String]): JsAllocationsTable =
    copy(_.copy(coarseType = value))
  
  /** Setter for [[$name]] field

    * "weight" is used here rather than "bytes", so that this type will match the
    * SamplesLikeTableShape.
    */
  def withWeight(value: Vector[Bytes]): JsAllocationsTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: JsAllocationsTable_WeightType.type): JsAllocationsTable =
    copy(_.copy(weightType = value))
  
  def withInNursery(value: Vector[Boolean]): JsAllocationsTable =
    copy(_.copy(inNursery = value))
  
  def withStack(value: Vector[Option[IndexIntoStackTable]]): JsAllocationsTable =
    copy(_.copy(stack = value))
  
  def withLength(value: Double): JsAllocationsTable =
    copy(_.copy(length = value))
  

  private def copy(f: JsAllocationsTableArgs => JsAllocationsTableArgs) = 
    new JsAllocationsTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object JsAllocationsTable {
  /** Construct a [[JsAllocationsTable]]
      @param weightType
      @param length
    */
  def apply(
    weightType: JsAllocationsTable_WeightType.type,
    length: Double,
  ): JsAllocationsTable = 
    new JsAllocationsTable(JsAllocationsTableArgs(
      time = Vector.empty,
      className = Vector.empty,
      typeName = Vector.empty,
      coarseType = Vector.empty,
      weight = Vector.empty,
      weightType = weightType,
      inNursery = Vector.empty,
      stack = Vector.empty,
      length = length,
    ))
  given JsonValueCodec[JsAllocationsTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: JsAllocationsTable) = 
        new JsAllocationsTable(summon[JsonValueCodec[JsAllocationsTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: JsAllocationsTable, out: JsonWriter) = 
        summon[JsonValueCodec[JsAllocationsTableArgs]].encodeValue(x.args, out)
      
      def nullValue: JsAllocationsTable = null
    }
  
}
private[fxprof] case class JsAllocationsTableArgs(
  time: Vector[Milliseconds],
  className: Vector[String],
  typeName: Vector[String],
  coarseType: Vector[String],
  weight: Vector[Bytes],
  weightType: JsAllocationsTable_WeightType.type,
  inNursery: Vector[Boolean],
  stack: Vector[Option[IndexIntoStackTable]],
  length: Double,
)
private[fxprof] object JsAllocationsTableArgs {
  given JsonValueCodec[JsAllocationsTableArgs] = JsonCodecMaker.make
}
