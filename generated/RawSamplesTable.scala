package fxprof

class RawSamplesTable private (private[fxprof] val args: RawSamplesTableArgs) {
  def responsiveness: Option[Vector[Option[Milliseconds]]] = args.responsiveness
  def eventDelay: Option[Vector[Option[Milliseconds]]] = args.eventDelay
  def stack: Vector[Option[IndexIntoStackTable]] = args.stack
  def time: Option[Vector[Milliseconds]] = args.time
  def timeDeltas: Option[Vector[Milliseconds]] = args.timeDeltas
  def weight: Vector[Option[Double]] = args.weight
  def weightType: WeightType = args.weightType
  def threadCPUDelta: Option[Vector[Option[Double]]] = args.threadCPUDelta
  def threadId: Option[Vector[Tid]] = args.threadId
  def length: Double = args.length

  def withResponsiveness(value: Option[Vector[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(responsiveness = value))
  
  def withEventDelay(value: Option[Vector[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(eventDelay = value))
  
  def withStack(value: Vector[Option[IndexIntoStackTable]]): RawSamplesTable =
    copy(_.copy(stack = value))
  
  def withTime(value: Option[Vector[Milliseconds]]): RawSamplesTable =
    copy(_.copy(time = value))
  
  def withTimeDeltas(value: Option[Vector[Milliseconds]]): RawSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  def withWeight(value: Vector[Option[Double]]): RawSamplesTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: WeightType): RawSamplesTable =
    copy(_.copy(weightType = value))
  
  def withThreadCPUDelta(value: Option[Vector[Option[Double]]]): RawSamplesTable =
    copy(_.copy(threadCPUDelta = value))
  
  def withThreadId(value: Option[Vector[Tid]]): RawSamplesTable =
    copy(_.copy(threadId = value))
  
  def withLength(value: Double): RawSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawSamplesTableArgs => RawSamplesTableArgs) = 
    new RawSamplesTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawSamplesTable {
  def apply(
    weightType: WeightType,
    length: Double,
  ): RawSamplesTable = 
    new RawSamplesTable(RawSamplesTableArgs(
      responsiveness = None,
      eventDelay = None,
      stack = Vector.empty,
      time = None,
      timeDeltas = None,
      weight = Vector.empty,
      weightType = weightType,
      threadCPUDelta = None,
      threadId = None,
      length = length,
    ))
  given JsonValueCodec[RawSamplesTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawSamplesTable) = 
        new RawSamplesTable(summon[JsonValueCodec[RawSamplesTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawSamplesTable, out: JsonWriter) = 
        summon[JsonValueCodec[RawSamplesTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawSamplesTable = null
    }
  
}
private[fxprof] case class RawSamplesTableArgs(
  responsiveness: Option[Vector[Option[Milliseconds]]],
  eventDelay: Option[Vector[Option[Milliseconds]]],
  stack: Vector[Option[IndexIntoStackTable]],
  time: Option[Vector[Milliseconds]],
  timeDeltas: Option[Vector[Milliseconds]],
  weight: Vector[Option[Double]],
  weightType: WeightType,
  threadCPUDelta: Option[Vector[Option[Double]]],
  threadId: Option[Vector[Tid]],
  length: Double,
)
private[fxprof] object RawSamplesTableArgs {
  given JsonValueCodec[RawSamplesTableArgs] = JsonCodecMaker.make
}
