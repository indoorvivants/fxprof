package fxprof

class ResourceTable private (args: ResourceTableArgs) {
  def length: Double = args.length
  def lib: Array[Option[IndexIntoLibs]] = args.lib
  def name: Array[IndexIntoStringTable] = args.name
  def host: Array[Option[IndexIntoStringTable]] = args.host
  def `type`: Array[ResourceTypeEnum] = args.`type`

  def withLength(value: Double): ResourceTable =
    copy(_.copy(length = value))
  
  def withLib(value: Array[Option[IndexIntoLibs]]): ResourceTable =
    copy(_.copy(lib = value))
  
  def withName(value: Array[IndexIntoStringTable]): ResourceTable =
    copy(_.copy(name = value))
  
  def withHost(value: Array[Option[IndexIntoStringTable]]): ResourceTable =
    copy(_.copy(host = value))
  
  def `withtype`(value: Array[ResourceTypeEnum]): ResourceTable =
    copy(_.copy(`type` = value))
  

  private def copy(f: ResourceTableArgs => ResourceTableArgs) = 
    new ResourceTable(f(args))
  
}

object ResourceTable {
  def apply(
    length: Double,
  ): ResourceTable = 
    new ResourceTable(ResourceTableArgs(
      length = length,
    ))
}
private[fxprof] case class ResourceTableArgs(
  length: Double,
  lib: Array[Option[IndexIntoLibs]] = Array.empty,
  name: Array[IndexIntoStringTable] = Array.empty,
  host: Array[Option[IndexIntoStringTable]] = Array.empty,
  `type`: Array[ResourceTypeEnum] = Array.empty,
)
