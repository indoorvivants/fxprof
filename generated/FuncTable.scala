package fxprof

class FuncTable private (private[fxprof] val args: FuncTableArgs) {
  def name: Vector[IndexIntoStringTable] = args.name
  def isJS: Vector[Boolean] = args.isJS
  def relevantForJS: Vector[Boolean] = args.relevantForJS
  def resource: Vector[FuncTable_Resource] = args.resource
  def source: Vector[Option[IndexIntoSourceTable]] = args.source
  def lineNumber: Vector[Option[Double]] = args.lineNumber
  def columnNumber: Vector[Option[Double]] = args.columnNumber
  def length: Double = args.length

  def withName(value: Vector[IndexIntoStringTable]): FuncTable =
    copy(_.copy(name = value))
  
  def withIsJS(value: Vector[Boolean]): FuncTable =
    copy(_.copy(isJS = value))
  
  def withRelevantForJS(value: Vector[Boolean]): FuncTable =
    copy(_.copy(relevantForJS = value))
  
  def withResource(value: Vector[FuncTable_Resource]): FuncTable =
    copy(_.copy(resource = value))
  
  def withSource(value: Vector[Option[IndexIntoSourceTable]]): FuncTable =
    copy(_.copy(source = value))
  
  def withLineNumber(value: Vector[Option[Double]]): FuncTable =
    copy(_.copy(lineNumber = value))
  
  def withColumnNumber(value: Vector[Option[Double]]): FuncTable =
    copy(_.copy(columnNumber = value))
  
  def withLength(value: Double): FuncTable =
    copy(_.copy(length = value))
  

  private def copy(f: FuncTableArgs => FuncTableArgs) = 
    new FuncTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object FuncTable {
  def apply(
    length: Double,
  ): FuncTable = 
    new FuncTable(FuncTableArgs(
      length = length,
    ))
  given JsonValueCodec[FuncTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: FuncTable) = 
        new FuncTable(summon[JsonValueCodec[FuncTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: FuncTable, out: JsonWriter) = 
        summon[JsonValueCodec[FuncTableArgs]].encodeValue(x.args, out)
      
      def nullValue: FuncTable = null
    }
  
}
private[fxprof] case class FuncTableArgs(
  name: Vector[IndexIntoStringTable] = Vector.empty,
  isJS: Vector[Boolean] = Vector.empty,
  relevantForJS: Vector[Boolean] = Vector.empty,
  resource: Vector[FuncTable_Resource] = Vector.empty,
  source: Vector[Option[IndexIntoSourceTable]] = Vector.empty,
  lineNumber: Vector[Option[Double]] = Vector.empty,
  columnNumber: Vector[Option[Double]] = Vector.empty,
  length: Double,
)
private[fxprof] object FuncTableArgs {
  given JsonValueCodec[FuncTableArgs] = JsonCodecMaker.make
}
