package fxprof

class RawProfileSharedData private (private[fxprof] val args: RawProfileSharedDataArgs) {
  def stringArray: Vector[String] = args.stringArray
  def sources: SourceTable = args.sources

  def withStringArray(value: Vector[String]): RawProfileSharedData =
    copy(_.copy(stringArray = value))
  
  def withSources(value: SourceTable): RawProfileSharedData =
    copy(_.copy(sources = value))
  

  private def copy(f: RawProfileSharedDataArgs => RawProfileSharedDataArgs) = 
    new RawProfileSharedData(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawProfileSharedData {
  def apply(
    sources: SourceTable,
  ): RawProfileSharedData = 
    new RawProfileSharedData(RawProfileSharedDataArgs(
      sources = sources,
    ))
  given JsonValueCodec[RawProfileSharedData] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawProfileSharedData) = 
        new RawProfileSharedData(summon[JsonValueCodec[RawProfileSharedDataArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawProfileSharedData, out: JsonWriter) = 
        summon[JsonValueCodec[RawProfileSharedDataArgs]].encodeValue(x.args, out)
      
      def nullValue: RawProfileSharedData = null
    }
  
}
private[fxprof] case class RawProfileSharedDataArgs(
  stringArray: Vector[String] = Vector.empty,
  sources: SourceTable,
)
private[fxprof] object RawProfileSharedDataArgs {
  given JsonValueCodec[RawProfileSharedDataArgs] = JsonCodecMaker.make
}
