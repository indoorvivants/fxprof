package fxprof

class RawSamplesTable private (args: RawSamplesTableArgs) {
  def responsiveness: Option[Array[Option[Milliseconds]]] = args.responsiveness
  def eventDelay: Option[Array[Option[Milliseconds]]] = args.eventDelay
  def stack: Array[Option[IndexIntoStackTable]] = args.stack
  def time: Option[Array[Milliseconds]] = args.time
  def timeDeltas: Option[Array[Milliseconds]] = args.timeDeltas
  def weight: Array[Option[Double]] = args.weight
  def weightType: WeightType = args.weightType
  def threadCPUDelta: Option[Array[Option[Double]]] = args.threadCPUDelta
  def threadId: Option[Array[Tid]] = args.threadId
  def length: Double = args.length

  def withResponsiveness(value: Option[Array[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(responsiveness = value))
  
  def withEventDelay(value: Option[Array[Option[Milliseconds]]]): RawSamplesTable =
    copy(_.copy(eventDelay = value))
  
  def withStack(value: Array[Option[IndexIntoStackTable]]): RawSamplesTable =
    copy(_.copy(stack = value))
  
  def withTime(value: Option[Array[Milliseconds]]): RawSamplesTable =
    copy(_.copy(time = value))
  
  def withTimeDeltas(value: Option[Array[Milliseconds]]): RawSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  def withWeight(value: Array[Option[Double]]): RawSamplesTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: WeightType): RawSamplesTable =
    copy(_.copy(weightType = value))
  
  def withThreadCPUDelta(value: Option[Array[Option[Double]]]): RawSamplesTable =
    copy(_.copy(threadCPUDelta = value))
  
  def withThreadId(value: Option[Array[Tid]]): RawSamplesTable =
    copy(_.copy(threadId = value))
  
  def withLength(value: Double): RawSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawSamplesTableArgs => RawSamplesTableArgs) = 
    new RawSamplesTable(f(args))
  
}

private[fxprof] case class RawSamplesTableArgs(
  responsiveness: Option[Array[Option[Milliseconds]]],
  eventDelay: Option[Array[Option[Milliseconds]]],
  stack: Array[Option[IndexIntoStackTable]],
  time: Option[Array[Milliseconds]],
  timeDeltas: Option[Array[Milliseconds]],
  weight: Array[Option[Double]],
  weightType: WeightType,
  threadCPUDelta: Option[Array[Option[Double]]],
  threadId: Option[Array[Tid]],
  length: Double,
)
