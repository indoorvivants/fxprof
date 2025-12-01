package fxprof

class UnbalancedNativeAllocationsTable private (args: UnbalancedNativeAllocationsTableArgs) {
  def time: Array[Milliseconds] = args.time
  def weight: Array[Bytes] = args.weight
  def weightType: Option[Any] /* literal string 'bytes' */ = args.weightType
  def stack: Array[Option[IndexIntoStackTable]] = args.stack
  def length: Double = args.length

  def withTime(value: Array[Milliseconds]): UnbalancedNativeAllocationsTable =
    copy(_.copy(time = value))
  
  def withWeight(value: Array[Bytes]): UnbalancedNativeAllocationsTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: Option[Any] /* literal string 'bytes' */): UnbalancedNativeAllocationsTable =
    copy(_.copy(weightType = value))
  
  def withStack(value: Array[Option[IndexIntoStackTable]]): UnbalancedNativeAllocationsTable =
    copy(_.copy(stack = value))
  
  def withLength(value: Double): UnbalancedNativeAllocationsTable =
    copy(_.copy(length = value))
  

  private def copy(f: UnbalancedNativeAllocationsTableArgs => UnbalancedNativeAllocationsTableArgs) = 
    new UnbalancedNativeAllocationsTable(f(args))
  
}

private[fxprof] case class UnbalancedNativeAllocationsTableArgs(
  time: Array[Milliseconds],
  weight: Array[Bytes],
  weightType: Option[Any] /* literal string 'bytes' */,
  stack: Array[Option[IndexIntoStackTable]],
  length: Double,
)
