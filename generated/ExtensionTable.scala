package fxprof

class ExtensionTable private (args: ExtensionTableArgs) {
  def baseURL: Array[String] = args.baseURL
  def id: Array[String] = args.id
  def name: Array[String] = args.name
  def length: Double = args.length

  def withBaseURL(value: Array[String]): ExtensionTable =
    copy(_.copy(baseURL = value))
  
  def withId(value: Array[String]): ExtensionTable =
    copy(_.copy(id = value))
  
  def withName(value: Array[String]): ExtensionTable =
    copy(_.copy(name = value))
  
  def withLength(value: Double): ExtensionTable =
    copy(_.copy(length = value))
  

  private def copy(f: ExtensionTableArgs => ExtensionTableArgs) = 
    new ExtensionTable(f(args))
  
}

object ExtensionTable {
  def apply(
    length: Double,
  ): ExtensionTable = 
    new ExtensionTable(ExtensionTableArgs(
      length = length,
    ))
}
private[fxprof] case class ExtensionTableArgs(
  baseURL: Array[String] = Array.empty,
  id: Array[String] = Array.empty,
  name: Array[String] = Array.empty,
  length: Double,
)
