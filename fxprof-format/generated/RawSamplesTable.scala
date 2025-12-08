package fxprof

/**
  * The Gecko Profiler records samples of what function was currently being executed, and
  * the callstack that is associated with it. This is done at a fixed but configurable
  * rate, e.g. every 1 millisecond. This table represents the minimal amount of
  * information that is needed to represent that sampled function. Most of the entries
  * are indices into other tables.
  */
class RawSamplesTable private (private[fxprof] val args: RawSamplesTableArgs) {
  /** Responsiveness is the older version of eventDelay. It injects events every 16ms.
    * This is optional because newer profiles don't have that field anymore.
    */
  def responsiveness: Option[Vector[Option[Milliseconds]]] = args.responsiveness

  /** Event delay is the newer version of responsiveness. It allow us to get a finer-grained
    * view of jank by inferring what would be the delay of a hypothetical input event at
    * any point in time. It requires a pre-processing to be able to visualize properly.
    * This is optional because older profiles didn't have that field.
    */
  def eventDelay: Option[Vector[Option[Milliseconds]]] = args.eventDelay

  def stack: Vector[Option[IndexIntoStackTable]] = args.stack

  def time: Option[Vector[Milliseconds]] = args.time

  /** If the `time` column is not present, then the `timeDeltas` column must be present.
    */
  def timeDeltas: Option[Vector[Milliseconds]] = args.timeDeltas

  /** An optional weight array. If not present, then the weight is assumed to be 1.
    * See the WeightType type for more information.
    */
  def weight: Vector[Option[Double]] = args.weight

  def weightType: WeightType = args.weightType

  /** CPU usage value of the current thread. Its values are null only if the back-end
    * fails to get the CPU usage from operating system.
    * It's landed in Firefox 86, and it is optional because older profile
    * versions may not have it or that feature could be disabled. No upgrader was
    * written for this change because it's a completely new data source.
    * The first value is ignored - it's not meaningful because there is no previous
    * sample.
    */
  def threadCPUDelta: Option[Vector[Option[Double]]] = args.threadCPUDelta

  /** This property isn't present in normal threads. However it's present for
    * merged threads, so that we know the origin thread for these samples.
    */
  def threadId: Option[Vector[Tid]] = args.threadId

  def length: Double = args.length


  /** Setter for [[responsiveness]] field

    * Responsiveness is the older version of eventDelay. It injects events every 16ms.
    * This is optional because newer profiles don't have that field anymore.
    */
  def withResponsiveness(value: Option[Vector[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(responsiveness = value))
  
  /** Setter for [[eventDelay]] field

    * Event delay is the newer version of responsiveness. It allow us to get a finer-grained
    * view of jank by inferring what would be the delay of a hypothetical input event at
    * any point in time. It requires a pre-processing to be able to visualize properly.
    * This is optional because older profiles didn't have that field.
    */
  def withEventDelay(value: Option[Vector[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(eventDelay = value))
  
  def withStack(value: Vector[Option[IndexIntoStackTable]]): RawSamplesTable =
    copy(_.copy(stack = value))
  
  def withTime(value: Option[Vector[Milliseconds]]): RawSamplesTable =
    copy(_.copy(time = value))
  
  /** Setter for [[timeDeltas]] field

    * If the `time` column is not present, then the `timeDeltas` column must be present.
    */
  def withTimeDeltas(value: Option[Vector[Milliseconds]]): RawSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  /** Setter for [[weight]] field

    * An optional weight array. If not present, then the weight is assumed to be 1.
    * See the WeightType type for more information.
    */
  def withWeight(value: Vector[Option[Double]]): RawSamplesTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: WeightType): RawSamplesTable =
    copy(_.copy(weightType = value))
  
  /** Setter for [[threadCPUDelta]] field

    * CPU usage value of the current thread. Its values are null only if the back-end
    * fails to get the CPU usage from operating system.
    * It's landed in Firefox 86, and it is optional because older profile
    * versions may not have it or that feature could be disabled. No upgrader was
    * written for this change because it's a completely new data source.
    * The first value is ignored - it's not meaningful because there is no previous
    * sample.
    */
  def withThreadCPUDelta(value: Option[Vector[Option[Double]]]): RawSamplesTable =
    copy(_.copy(threadCPUDelta = value))
  
  /** Setter for [[threadId]] field

    * This property isn't present in normal threads. However it's present for
    * merged threads, so that we know the origin thread for these samples.
    */
  def withThreadId(value: Option[Vector[Tid]]): RawSamplesTable =
    copy(_.copy(threadId = value))
  
  def withLength(value: Double): RawSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawSamplesTableArgs => RawSamplesTableArgs) = 
    new RawSamplesTable(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[RawSamplesTable] && o.asInstanceOf[RawSamplesTable].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawSamplesTable {
  /** Construct a [[RawSamplesTable]]
      @param weightType
      @param length
    */
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
  implicit val codec: JsonValueCodec[RawSamplesTable] = 
    new JsonValueCodec[RawSamplesTable] {
      def decodeValue(in: JsonReader, default: RawSamplesTable) = 
        new RawSamplesTable(implicitly[JsonValueCodec[RawSamplesTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawSamplesTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[RawSamplesTableArgs]].encodeValue(x.args, out)
      
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
  implicit val codec: JsonValueCodec[RawSamplesTableArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
