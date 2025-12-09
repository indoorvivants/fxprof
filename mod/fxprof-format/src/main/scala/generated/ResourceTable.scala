package fxprof

/**
  * The ResourceTable holds additional information about functions. It tends to contain
  * sparse arrays. Multiple functions can point to the same resource.
  */
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
  

  override def equals(o: Any) = o.isInstanceOf[ResourceTable] && o.asInstanceOf[ResourceTable].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ResourceTable {
  /** Construct a [[ResourceTable]]
      @param length
    */
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
  implicit val codec: JsonValueCodec[ResourceTable] = 
    new JsonValueCodec[ResourceTable] {
      def decodeValue(in: JsonReader, default: ResourceTable) = 
        new ResourceTable(implicitly[JsonValueCodec[ResourceTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ResourceTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[ResourceTableArgs]].encodeValue(x.args, out)
      
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
  implicit val codec: JsonValueCodec[ResourceTableArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
