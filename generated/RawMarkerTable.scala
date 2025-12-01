package fxprof

class RawMarkerTable private (private[fxprof] val args: RawMarkerTableArgs) {
  def data: Vector[Option[MarkerPayload]] = args.data
  def name: Vector[IndexIntoStringTable] = args.name
  def startTime: Vector[Option[Double]] = args.startTime
  def endTime: Vector[Option[Double]] = args.endTime
  def phase: Vector[MarkerPhase] = args.phase
  def category: Vector[IndexIntoCategoryList] = args.category
  def threadId: Option[Vector[Option[Tid]]] = args.threadId
  def length: Double = args.length

  def withData(value: Vector[Option[MarkerPayload]]): RawMarkerTable =
    copy(_.copy(data = value))
  
  def withName(value: Vector[IndexIntoStringTable]): RawMarkerTable =
    copy(_.copy(name = value))
  
  def withStartTime(value: Vector[Option[Double]]): RawMarkerTable =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Vector[Option[Double]]): RawMarkerTable =
    copy(_.copy(endTime = value))
  
  def withPhase(value: Vector[MarkerPhase]): RawMarkerTable =
    copy(_.copy(phase = value))
  
  def withCategory(value: Vector[IndexIntoCategoryList]): RawMarkerTable =
    copy(_.copy(category = value))
  
  def withThreadId(value: Option[Vector[Option[Tid]]]): RawMarkerTable =
    copy(_.copy(threadId = value))
  
  def withLength(value: Double): RawMarkerTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawMarkerTableArgs => RawMarkerTableArgs) = 
    new RawMarkerTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawMarkerTable {
  def apply(
    length: Double,
  ): RawMarkerTable = 
    new RawMarkerTable(RawMarkerTableArgs(
      length = length,
    ))
  given JsonValueCodec[RawMarkerTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawMarkerTable) = 
        new RawMarkerTable(summon[JsonValueCodec[RawMarkerTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawMarkerTable, out: JsonWriter) = 
        summon[JsonValueCodec[RawMarkerTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawMarkerTable = null
    }
  
}
private[fxprof] case class RawMarkerTableArgs(
  data: Vector[Option[MarkerPayload]] = Vector.empty,
  name: Vector[IndexIntoStringTable] = Vector.empty,
  startTime: Vector[Option[Double]] = Vector.empty,
  endTime: Vector[Option[Double]] = Vector.empty,
  phase: Vector[MarkerPhase] = Vector.empty,
  category: Vector[IndexIntoCategoryList] = Vector.empty,
  threadId: Option[Vector[Option[Tid]]] = None,
  length: Double,
)
private[fxprof] object RawMarkerTableArgs {
  given JsonValueCodec[RawMarkerTableArgs] = JsonCodecMaker.make
}
