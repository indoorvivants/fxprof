package fxprof

class ProgressGraphData private (args: ProgressGraphDataArgs) {
  def percent: Double = args.percent
  def timestamp: Option[Milliseconds] = args.timestamp

  def withPercent(value: Double): ProgressGraphData =
    copy(_.copy(percent = value))
  
  def withTimestamp(value: Option[Milliseconds]): ProgressGraphData =
    copy(_.copy(timestamp = value))
  

  private def copy(f: ProgressGraphDataArgs => ProgressGraphDataArgs) = 
    new ProgressGraphData(f(args))
  
}

object ProgressGraphData {
  def apply(
    percent: Double,
  ): ProgressGraphData = 
    new ProgressGraphData(ProgressGraphDataArgs(
      percent = percent,
    ))
}
private[fxprof] case class ProgressGraphDataArgs(
  percent: Double,
  timestamp: Option[Milliseconds] = None,
)
