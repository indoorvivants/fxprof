package fxprof

class RawStackTable private (private[fxprof] val args: RawStackTableArgs) {
  def frame: Vector[IndexIntoFrameTable] = args.frame
  def prefix: Vector[Option[IndexIntoStackTable]] = args.prefix
  def length: Double = args.length

  def withFrame(value: Vector[IndexIntoFrameTable]): RawStackTable =
    copy(_.copy(frame = value))
  
  def withPrefix(value: Vector[Option[IndexIntoStackTable]]): RawStackTable =
    copy(_.copy(prefix = value))
  
  def withLength(value: Double): RawStackTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawStackTableArgs => RawStackTableArgs) = 
    new RawStackTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawStackTable {
  def apply(
    length: Double,
  ): RawStackTable = 
    new RawStackTable(RawStackTableArgs(
      length = length,
    ))
  given JsonValueCodec[RawStackTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawStackTable) = 
        new RawStackTable(summon[JsonValueCodec[RawStackTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawStackTable, out: JsonWriter) = 
        summon[JsonValueCodec[RawStackTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawStackTable = null
    }
  
}
private[fxprof] case class RawStackTableArgs(
  frame: Vector[IndexIntoFrameTable] = Vector.empty,
  prefix: Vector[Option[IndexIntoStackTable]] = Vector.empty,
  length: Double,
)
private[fxprof] object RawStackTableArgs {
  given JsonValueCodec[RawStackTableArgs] = JsonCodecMaker.make
}
