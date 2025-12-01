package fxprof

class ResourceTable private (private[fxprof] val args: ResourceTableArgs) {
  def length: Double = args.length
  def lib: Vector[Option[IndexIntoLibs]] = args.lib
  def name: Vector[IndexIntoStringTable] = args.name
  def host: Vector[Option[IndexIntoStringTable]] = args.host
  def `type`: Vector[ResourceTypeEnum] = args.`type`

  def withLength(value: Double): ResourceTable =
    copy(_.copy(length = value))
  
  def withLib(value: Vector[Option[IndexIntoLibs]]): ResourceTable =
    copy(_.copy(lib = value))
  
  def withName(value: Vector[IndexIntoStringTable]): ResourceTable =
    copy(_.copy(name = value))
  
  def withHost(value: Vector[Option[IndexIntoStringTable]]): ResourceTable =
    copy(_.copy(host = value))
  
  def `withtype`(value: Vector[ResourceTypeEnum]): ResourceTable =
    copy(_.copy(`type` = value))
  

  private def copy(f: ResourceTableArgs => ResourceTableArgs) = 
    new ResourceTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ResourceTable {
  def apply(
    length: Double,
  ): ResourceTable = 
    new ResourceTable(ResourceTableArgs(
      length = length,
      lib = Vector.empty,
      name = Vector.empty,
      host = Vector.empty,
      `type` = Vector.empty,
    ))
  given JsonValueCodec[ResourceTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ResourceTable) = 
        new ResourceTable(summon[JsonValueCodec[ResourceTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ResourceTable, out: JsonWriter) = 
        summon[JsonValueCodec[ResourceTableArgs]].encodeValue(x.args, out)
      
      def nullValue: ResourceTable = null
    }
  
}
private[fxprof] case class ResourceTableArgs(
  length: Double,
  lib: Vector[Option[IndexIntoLibs]],
  name: Vector[IndexIntoStringTable],
  host: Vector[Option[IndexIntoStringTable]],
  `type`: Vector[ResourceTypeEnum],
)
private[fxprof] object ResourceTableArgs {
  given JsonValueCodec[ResourceTableArgs] = JsonCodecMaker.make
}
