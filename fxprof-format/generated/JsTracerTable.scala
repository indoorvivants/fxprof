package fxprof

class JsTracerTable private (private[fxprof] val args: JsTracerTableArgs) {
  def events: Vector[IndexIntoStringTable] = args.events

  def timestamps: Vector[Microseconds] = args.timestamps

  def durations: Vector[Option[Microseconds]] = args.durations

  def line: Vector[Option[Double]] = args.line

  def column: Vector[Option[Double]] = args.column

  def length: Double = args.length


  def withEvents(value: Vector[IndexIntoStringTable]): JsTracerTable =
    copy(_.copy(events = value))
  
  def withTimestamps(value: Vector[Microseconds]): JsTracerTable =
    copy(_.copy(timestamps = value))
  
  def withDurations(value: Vector[Option[Microseconds]]): JsTracerTable =
    copy(_.copy(durations = value))
  
  def withLine(value: Vector[Option[Double]]): JsTracerTable =
    copy(_.copy(line = value))
  
  def withColumn(value: Vector[Option[Double]]): JsTracerTable =
    copy(_.copy(column = value))
  
  def withLength(value: Double): JsTracerTable =
    copy(_.copy(length = value))
  

  private def copy(f: JsTracerTableArgs => JsTracerTableArgs) = 
    new JsTracerTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object JsTracerTable {
  /** Construct a [[JsTracerTable]]
      @param length
    */
  def apply(
    length: Double,
  ): JsTracerTable = 
    new JsTracerTable(JsTracerTableArgs(
      events = Vector.empty,
      timestamps = Vector.empty,
      durations = Vector.empty,
      line = Vector.empty,
      column = Vector.empty,
      length = length,
    ))
  given JsonValueCodec[JsTracerTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: JsTracerTable) = 
        new JsTracerTable(summon[JsonValueCodec[JsTracerTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: JsTracerTable, out: JsonWriter) = 
        summon[JsonValueCodec[JsTracerTableArgs]].encodeValue(x.args, out)
      
      def nullValue: JsTracerTable = null
    }
  
}
private[fxprof] case class JsTracerTableArgs(
  events: Vector[IndexIntoStringTable],
  timestamps: Vector[Microseconds],
  durations: Vector[Option[Microseconds]],
  line: Vector[Option[Double]],
  column: Vector[Option[Double]],
  length: Double,
)
private[fxprof] object JsTracerTableArgs {
  given ConfiguredJsonValueCodec[JsTracerTableArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
