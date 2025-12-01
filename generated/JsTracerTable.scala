package fxprof

class JsTracerTable private (args: JsTracerTableArgs) {
  def events: Array[IndexIntoStringTable] = args.events
  def timestamps: Array[Microseconds] = args.timestamps
  def durations: Array[Option[Microseconds]] = args.durations
  def line: Array[Option[Double]] = args.line
  def column: Array[Option[Double]] = args.column
  def length: Double = args.length

  def withEvents(value: Array[IndexIntoStringTable]): JsTracerTable =
    copy(_.copy(events = value))
  
  def withTimestamps(value: Array[Microseconds]): JsTracerTable =
    copy(_.copy(timestamps = value))
  
  def withDurations(value: Array[Option[Microseconds]]): JsTracerTable =
    copy(_.copy(durations = value))
  
  def withLine(value: Array[Option[Double]]): JsTracerTable =
    copy(_.copy(line = value))
  
  def withColumn(value: Array[Option[Double]]): JsTracerTable =
    copy(_.copy(column = value))
  
  def withLength(value: Double): JsTracerTable =
    copy(_.copy(length = value))
  

  private def copy(f: JsTracerTableArgs => JsTracerTableArgs) = 
    new JsTracerTable(f(args))
  
}

object JsTracerTable {
  def apply(
    length: Double,
  ): JsTracerTable = 
    new JsTracerTable(JsTracerTableArgs(
      length = length,
    ))
}
private[fxprof] case class JsTracerTableArgs(
  events: Array[IndexIntoStringTable] = Array.empty,
  timestamps: Array[Microseconds] = Array.empty,
  durations: Array[Option[Microseconds]] = Array.empty,
  line: Array[Option[Double]] = Array.empty,
  column: Array[Option[Double]] = Array.empty,
  length: Double,
)
