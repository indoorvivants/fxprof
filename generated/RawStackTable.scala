package fxprof

class RawStackTable private (args: RawStackTableArgs) {
  def frame: Array[IndexIntoFrameTable] = args.frame
  def prefix: Array[Option[IndexIntoStackTable]] = args.prefix
  def length: Double = args.length

  def withFrame(value: Array[IndexIntoFrameTable]): RawStackTable =
    copy(_.copy(frame = value))
  
  def withPrefix(value: Array[Option[IndexIntoStackTable]]): RawStackTable =
    copy(_.copy(prefix = value))
  
  def withLength(value: Double): RawStackTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawStackTableArgs => RawStackTableArgs) = 
    new RawStackTable(f(args))
  
}

object RawStackTable {
  def apply(
    length: Double,
  ): RawStackTable = 
    new RawStackTable(RawStackTableArgs(
      length = length,
    ))
}
private[fxprof] case class RawStackTableArgs(
  frame: Array[IndexIntoFrameTable] = Array.empty,
  prefix: Array[Option[IndexIntoStackTable]] = Array.empty,
  length: Double,
)
