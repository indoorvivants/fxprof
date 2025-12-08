package fxprof

/**
  * The nativeSymbols table stores the addresses and symbol names for all symbols
  * that were encountered by frame addresses in this thread. This table can
  * contain symbols from multiple libraries, and the symbols are in arbitrary
  * order.
  * Note: Despite the similarity in name, this table is not what's usually
  * considered a "symbol table" - normally, a "symbol table" is something that
  * contains *all* symbols of a given library. But this table only contains a
  * subset of those symbols, and mixes symbols from multiple libraries.
  */
class NativeSymbolTable private (private[fxprof] val args: NativeSymbolTableArgs) {
  /** The library that this native symbol is in.
    */
  def libIndex: Vector[IndexIntoLibs] = args.libIndex

  /** The library-relative offset of this symbol.
    */
  def address: Vector[Address] = args.address

  /** The symbol name, demangled.
    */
  def name: Vector[IndexIntoStringTable] = args.name

  /** The size of the function's machine code (if known), in bytes.
    */
  def functionSize: Vector[Option[Bytes]] = args.functionSize

  def length: Double = args.length


  /** Setter for [[libIndex]] field

    * The library that this native symbol is in.
    */
  def withLibIndex(value: Vector[IndexIntoLibs]): NativeSymbolTable =
    copy(_.copy(libIndex = value))
  
  /** Setter for [[address]] field

    * The library-relative offset of this symbol.
    */
  def withAddress(value: Vector[Address]): NativeSymbolTable =
    copy(_.copy(address = value))
  
  /** Setter for [[name]] field

    * The symbol name, demangled.
    */
  def withName(value: Vector[IndexIntoStringTable]): NativeSymbolTable =
    copy(_.copy(name = value))
  
  /** Setter for [[functionSize]] field

    * The size of the function's machine code (if known), in bytes.
    */
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
  /** Construct a [[NativeSymbolTable]]
      @param length
    */
  def apply(
    length: Double,
  ): NativeSymbolTable = 
    new NativeSymbolTable(NativeSymbolTableArgs(
      libIndex = Vector.empty,
      address = Vector.empty,
      name = Vector.empty,
      functionSize = Vector.empty,
      length = length,
    ))
  implicit val codec: JsonValueCodec[NativeSymbolTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: NativeSymbolTable) = 
        new NativeSymbolTable(summon[JsonValueCodec[NativeSymbolTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NativeSymbolTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[NativeSymbolTableArgs]].encodeValue(x.args, out)
      
      def nullValue: NativeSymbolTable = null
    }
  
}
private[fxprof] case class NativeSymbolTableArgs(
  libIndex: Vector[IndexIntoLibs],
  address: Vector[Address],
  name: Vector[IndexIntoStringTable],
  functionSize: Vector[Option[Bytes]],
  length: Double,
)
private[fxprof] object NativeSymbolTableArgs {
  implicit val codec: ConfiguredJsonValueCodec[NativeSymbolTableArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
