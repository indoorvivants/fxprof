package fxprof

class SourceTable private (private[fxprof] val args: SourceTableArgs) {
  def length: Double = args.length
  def uuid: Vector[Option[String]] = args.uuid
  def filename: Vector[IndexIntoStringTable] = args.filename

  def withLength(value: Double): SourceTable =
    copy(_.copy(length = value))
  
  def withUuid(value: Vector[Option[String]]): SourceTable =
    copy(_.copy(uuid = value))
  
  def withFilename(value: Vector[IndexIntoStringTable]): SourceTable =
    copy(_.copy(filename = value))
  

  private def copy(f: SourceTableArgs => SourceTableArgs) = 
    new SourceTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object SourceTable {
  def apply(
    length: Double,
  ): SourceTable = 
    new SourceTable(SourceTableArgs(
      length = length,
    ))
  given JsonValueCodec[SourceTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: SourceTable) = 
        new SourceTable(summon[JsonValueCodec[SourceTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: SourceTable, out: JsonWriter) = 
        summon[JsonValueCodec[SourceTableArgs]].encodeValue(x.args, out)
      
      def nullValue: SourceTable = null
    }
  
}
private[fxprof] case class SourceTableArgs(
  length: Double,
  uuid: Vector[Option[String]] = Vector.empty,
  filename: Vector[IndexIntoStringTable] = Vector.empty,
)
private[fxprof] object SourceTableArgs {
  given JsonValueCodec[SourceTableArgs] = JsonCodecMaker.make
}
