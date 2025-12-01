package fxprof

class RawCounterSamplesTable private (args: RawCounterSamplesTableArgs) {
  def time: Option[Array[Milliseconds]] = args.time
  def timeDeltas: Option[Array[Milliseconds]] = args.timeDeltas
  def number: Option[Array[Double]] = args.number
  def count: Array[Double] = args.count
  def length: Double = args.length

  def withTime(value: Option[Array[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(time = value))
  
  def withTimeDeltas(value: Option[Array[Milliseconds]]): RawCounterSamplesTable =
    copy(_.copy(timeDeltas = value))
  
  def withNumber(value: Option[Array[Double]]): RawCounterSamplesTable =
    copy(_.copy(number = value))
  
  def withCount(value: Array[Double]): RawCounterSamplesTable =
    copy(_.copy(count = value))
  
  def withLength(value: Double): RawCounterSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawCounterSamplesTableArgs => RawCounterSamplesTableArgs) = 
    new RawCounterSamplesTable(f(args))
  
}

object RawCounterSamplesTable {
  def apply(
    length: Double,
  ): RawCounterSamplesTable = 
    new RawCounterSamplesTable(RawCounterSamplesTableArgs(
      length = length,
    ))
}
private[fxprof] case class RawCounterSamplesTableArgs(
  time: Option[Array[Milliseconds]] = None,
  timeDeltas: Option[Array[Milliseconds]] = None,
  number: Option[Array[Double]] = None,
  count: Array[Double] = Array.empty,
  length: Double,
)
