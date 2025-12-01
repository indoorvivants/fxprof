package fxprof

class NativeSymbolTable private (private[fxprof] val args: NativeSymbolTableArgs) {
  def libIndex: Vector[IndexIntoLibs] = args.libIndex
  def address: Vector[Address] = args.address
  def name: Vector[IndexIntoStringTable] = args.name
  def functionSize: Vector[Option[Bytes]] = args.functionSize
  def length: Double = args.length

  def withLibIndex(value: Vector[IndexIntoLibs]): NativeSymbolTable =
    copy(_.copy(libIndex = value))
  
  def withAddress(value: Vector[Address]): NativeSymbolTable =
    copy(_.copy(address = value))
  
  def withName(value: Vector[IndexIntoStringTable]): NativeSymbolTable =
    copy(_.copy(name = value))
  
  def withFunctionSize(value: Vector[Option[Bytes]]): NativeSymbolTable =
    copy(_.copy(functionSize = value))
  
  def withLength(value: Double): NativeSymbolTable =
    copy(_.copy(length = value))
  

  private def copy(f: NativeSymbolTableArgs => NativeSymbolTableArgs) = 
    new NativeSymbolTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object NativeSymbolTable {
  def apply(
    length: Double,
  ): NativeSymbolTable = 
    new NativeSymbolTable(NativeSymbolTableArgs(
      length = length,
    ))
  given JsonValueCodec[NativeSymbolTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: NativeSymbolTable) = 
        new NativeSymbolTable(summon[JsonValueCodec[NativeSymbolTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NativeSymbolTable, out: JsonWriter) = 
        summon[JsonValueCodec[NativeSymbolTableArgs]].encodeValue(x.args, out)
      
      def nullValue: NativeSymbolTable = null
    }
  
}
private[fxprof] case class NativeSymbolTableArgs(
  libIndex: Vector[IndexIntoLibs] = Vector.empty,
  address: Vector[Address] = Vector.empty,
  name: Vector[IndexIntoStringTable] = Vector.empty,
  functionSize: Vector[Option[Bytes]] = Vector.empty,
  length: Double,
)
private[fxprof] object NativeSymbolTableArgs {
  given JsonValueCodec[NativeSymbolTableArgs] = JsonCodecMaker.make
}
