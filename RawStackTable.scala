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

private[fxprof] case class RawStackTableArgs(
  frame: Array[IndexIntoFrameTable],
  prefix: Array[Option[IndexIntoStackTable]],
  length: Double,
)
