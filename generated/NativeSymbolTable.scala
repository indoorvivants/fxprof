package fxprof

class NativeSymbolTable private (args: NativeSymbolTableArgs) {
  def libIndex: Array[IndexIntoLibs] = args.libIndex
  def address: Array[Address] = args.address
  def name: Array[IndexIntoStringTable] = args.name
  def functionSize: Array[Option[Bytes]] = args.functionSize
  def length: Double = args.length

  def withLibIndex(value: Array[IndexIntoLibs]): NativeSymbolTable =
    copy(_.copy(libIndex = value))
  
  def withAddress(value: Array[Address]): NativeSymbolTable =
    copy(_.copy(address = value))
  
  def withName(value: Array[IndexIntoStringTable]): NativeSymbolTable =
    copy(_.copy(name = value))
  
  def withFunctionSize(value: Array[Option[Bytes]]): NativeSymbolTable =
    copy(_.copy(functionSize = value))
  
  def withLength(value: Double): NativeSymbolTable =
    copy(_.copy(length = value))
  

  private def copy(f: NativeSymbolTableArgs => NativeSymbolTableArgs) = 
    new NativeSymbolTable(f(args))
  
}

object NativeSymbolTable {
  def apply(
    length: Double,
  ): NativeSymbolTable = 
    new NativeSymbolTable(NativeSymbolTableArgs(
      length = length,
    ))
}
private[fxprof] case class NativeSymbolTableArgs(
  libIndex: Array[IndexIntoLibs] = Array.empty,
  address: Array[Address] = Array.empty,
  name: Array[IndexIntoStringTable] = Array.empty,
  functionSize: Array[Option[Bytes]] = Array.empty,
  length: Double,
)
