package fxprof

/**
  * Table containing source code file references in the processed profile format.
  * Maps UUIDs to filenames for JavaScript sources, while native code sources
  * have null UUIDs. Native code sources are added during the symbolication process.
  * This allows the profiler to display source code context when viewing frames.
  * This is an expanded version of GeckoSourceTable that includes native code sources
  * in addition to JavaScript sources.
  */
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
  

  override def equals(o: Any) = o.isInstanceOf[SourceTable] && o.asInstanceOf[SourceTable].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object SourceTable {
  /** Construct a [[SourceTable]]
      @param length
    */
  def apply(
    length: Double,
  ): SourceTable = 
    new SourceTable(SourceTableArgs(
      length = length,
      uuid = Vector.empty,
      filename = Vector.empty,
    ))
  implicit val codec: JsonValueCodec[SourceTable] = 
    new JsonValueCodec[SourceTable] {
      def decodeValue(in: JsonReader, default: SourceTable) = 
        new SourceTable(implicitly[JsonValueCodec[SourceTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: SourceTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[SourceTableArgs]].encodeValue(x.args, out)
      
      def nullValue: SourceTable = null
    }
  
}
private[fxprof] case class SourceTableArgs(
  length: Double,
  uuid: Vector[Option[String]],
  filename: Vector[IndexIntoStringTable],
)
private[fxprof] object SourceTableArgs {
  implicit val codec: JsonValueCodec[SourceTableArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
