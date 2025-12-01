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

private[fxprof] case class ExtensionTableArgs(
  baseURL: Array[String],
  id: Array[String],
  name: Array[String],
  length: Double,
)
