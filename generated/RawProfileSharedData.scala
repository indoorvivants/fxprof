package fxprof

class RawProfileSharedData private (args: RawProfileSharedDataArgs) {
  def stringArray: Array[String] = args.stringArray
  def sources: SourceTable = args.sources

  def withStringArray(value: Array[String]): RawProfileSharedData =
    copy(_.copy(stringArray = value))
  
  def withSources(value: SourceTable): RawProfileSharedData =
    copy(_.copy(sources = value))
  

  private def copy(f: RawProfileSharedDataArgs => RawProfileSharedDataArgs) = 
    new RawProfileSharedData(f(args))
  
}

object RawProfileSharedData {
  def apply(
    sources: SourceTable,
  ): RawProfileSharedData = 
    new RawProfileSharedData(RawProfileSharedDataArgs(
      sources = sources,
    ))
}
private[fxprof] case class RawProfileSharedDataArgs(
  stringArray: Array[String] = Array.empty,
  sources: SourceTable,
)
