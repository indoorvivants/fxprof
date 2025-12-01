package fxprof

class UnbalancedNativeAllocationsTable private (args: UnbalancedNativeAllocationsTableArgs) {
  def time: Array[Milliseconds] = args.time
  def weight: Array[Bytes] = args.weight
  def weightType: UnbalancedNativeAllocationsTable_WeightType.type = args.weightType
  def stack: Array[Option[IndexIntoStackTable]] = args.stack
  def length: Double = args.length

  def withTime(value: Array[Milliseconds]): UnbalancedNativeAllocationsTable =
    copy(_.copy(time = value))
  
  def withWeight(value: Array[Bytes]): UnbalancedNativeAllocationsTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: UnbalancedNativeAllocationsTable_WeightType.type): UnbalancedNativeAllocationsTable =
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
  weightType: UnbalancedNativeAllocationsTable_WeightType.type,
  stack: Array[Option[IndexIntoStackTable]],
  length: Double,
)
