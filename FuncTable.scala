package fxprof

class FuncTable private (args: FuncTableArgs) {
  def name: Array[IndexIntoStringTable] = args.name
  def isJS: Array[Boolean] = args.isJS
  def relevantForJS: Array[Boolean] = args.relevantForJS
  def resource: Array[FuncTable_Resource] = args.resource
  def source: Array[Option[IndexIntoSourceTable]] = args.source
  def lineNumber: Array[Option[Double]] = args.lineNumber
  def columnNumber: Array[Option[Double]] = args.columnNumber
  def length: Double = args.length

  def withName(value: Array[IndexIntoStringTable]): FuncTable =
    copy(_.copy(name = value))
  
  def withIsJS(value: Array[Boolean]): FuncTable =
    copy(_.copy(isJS = value))
  
  def withRelevantForJS(value: Array[Boolean]): FuncTable =
    copy(_.copy(relevantForJS = value))
  
  def withResource(value: Array[FuncTable_Resource]): FuncTable =
    copy(_.copy(resource = value))
  
  def withSource(value: Array[Option[IndexIntoSourceTable]]): FuncTable =
    copy(_.copy(source = value))
  
  def withLineNumber(value: Array[Option[Double]]): FuncTable =
    copy(_.copy(lineNumber = value))
  
  def withColumnNumber(value: Array[Option[Double]]): FuncTable =
    copy(_.copy(columnNumber = value))
  
  def withLength(value: Double): FuncTable =
    copy(_.copy(length = value))
  

  private def copy(f: FuncTableArgs => FuncTableArgs) = 
    new FuncTable(f(args))
  
}

private[fxprof] case class FuncTableArgs(
  name: Array[IndexIntoStringTable],
  isJS: Array[Boolean],
  relevantForJS: Array[Boolean],
  resource: Array[FuncTable_Resource],
  source: Array[Option[IndexIntoSourceTable]],
  lineNumber: Array[Option[Double]],
  columnNumber: Array[Option[Double]],
  length: Double,
)
