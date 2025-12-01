package fxprof

class ResourceTable private (args: ResourceTableArgs) {
  def length: Double = args.length
  def lib: Array[Option[IndexIntoLibs]] = args.lib
  def name: Array[IndexIntoStringTable] = args.name
  def host: Array[Option[IndexIntoStringTable]] = args.host
  def type: Array[ResourceTypeEnum] = args.type

  def withLength(value: Double): ResourceTable =
    copy(_.copy(length = value))
  
  def withLib(value: Array[Option[IndexIntoLibs]]): ResourceTable =
    copy(_.copy(lib = value))
  
  def withName(value: Array[IndexIntoStringTable]): ResourceTable =
    copy(_.copy(name = value))
  
  def withHost(value: Array[Option[IndexIntoStringTable]]): ResourceTable =
    copy(_.copy(host = value))
  
  def withType(value: Array[ResourceTypeEnum]): ResourceTable =
    copy(_.copy(type = value))
  

  private def copy(f: ResourceTableArgs => ResourceTableArgs) = 
    new ResourceTable(f(args))
  
}

private[fxprof] case class ResourceTableArgs(
  length: Double,
  lib: Array[Option[IndexIntoLibs]],
  name: Array[IndexIntoStringTable],
  host: Array[Option[IndexIntoStringTable]],
  type: Array[ResourceTypeEnum],
)
