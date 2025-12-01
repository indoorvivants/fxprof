package fxprof

class ExtensionTable private (args: ExtensionTableArgs) {
  
  private def copy
}

case class ExtensionTableArgs(
    baseURL: Array[String],
    id: Array[String],
    name: Array[String],
    length: Long
)
