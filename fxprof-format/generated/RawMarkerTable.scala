package fxprof

/**
  * Markers represent arbitrary events that happen within the browser. They have a
  * name, time, and potentially a JSON data payload. These can come from all over the
  * system. For instance Paint markers instrument the rendering and layout process.
  * Engineers can easily add arbitrary markers to their code without coordinating with
  * profiler.firefox.com to instrument their code.
  * *
  * In the profile, these markers are raw and unprocessed. In the marker selectors, we
  * can run them through a processing pipeline to match up start and end markers to
  * create markers with durations, or even take a string-only marker and parse
  * it into a structured marker.
  */
class RawMarkerTable private (private[fxprof] val args: RawMarkerTableArgs) {
  def data: Vector[Option[MarkerPayload]] = args.data

  def name: Vector[IndexIntoStringTable] = args.name

  def startTime: Vector[Option[Double]] = args.startTime

  def endTime: Vector[Option[Double]] = args.endTime

  def phase: Vector[MarkerPhase] = args.phase

  def category: Vector[IndexIntoCategoryList] = args.category

  /** This property isn't present in normal threads. However it's present for
    * merged threads, so that we know the origin thread for these markers.
    */
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
  
  /** Setter for [[threadId]] field

    * This property isn't present in normal threads. However it's present for
    * merged threads, so that we know the origin thread for these markers.
    */
  def withThreadId(value: Option[Vector[Option[Tid]]]): RawMarkerTable =
    copy(_.copy(threadId = value))
  
  def withLength(value: Double): RawMarkerTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawMarkerTableArgs => RawMarkerTableArgs) = 
    new RawMarkerTable(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[RawMarkerTable] && o.asInstanceOf[RawMarkerTable].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawMarkerTable {
  /** Construct a [[RawMarkerTable]]
      @param length
    */
  def apply(
    length: Double,
  ): RawMarkerTable = 
    new RawMarkerTable(RawMarkerTableArgs(
      data = Vector.empty,
      name = Vector.empty,
      startTime = Vector.empty,
      endTime = Vector.empty,
      phase = Vector.empty,
      category = Vector.empty,
      threadId = None,
      length = length,
    ))
  implicit val codec: JsonValueCodec[RawMarkerTable] = 
    new JsonValueCodec[RawMarkerTable] {
      def decodeValue(in: JsonReader, default: RawMarkerTable) = 
        new RawMarkerTable(implicitly[JsonValueCodec[RawMarkerTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawMarkerTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[RawMarkerTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawMarkerTable = null
    }
  
}
private[fxprof] case class RawMarkerTableArgs(
  data: Vector[Option[MarkerPayload]],
  name: Vector[IndexIntoStringTable],
  startTime: Vector[Option[Double]],
  endTime: Vector[Option[Double]],
  phase: Vector[MarkerPhase],
  category: Vector[IndexIntoCategoryList],
  threadId: Option[Vector[Option[Tid]]],
  length: Double,
)
private[fxprof] object RawMarkerTableArgs {
  implicit val codec: JsonValueCodec[RawMarkerTableArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
