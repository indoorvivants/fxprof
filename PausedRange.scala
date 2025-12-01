package fxprof

class PausedRange private (args: PausedRangeArgs) {
  def startTime: Option[Milliseconds] = args.startTime
  def endTime: Option[Milliseconds] = args.endTime
  def reason: PausedRange_Reason = args.reason

  def withStartTime(value: Option[Milliseconds]): PausedRange =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Option[Milliseconds]): PausedRange =
    copy(_.copy(endTime = value))
  
  def withReason(value: PausedRange_Reason): PausedRange =
    copy(_.copy(reason = value))
  

  private def copy(f: PausedRangeArgs => PausedRangeArgs) = 
    new PausedRange(f(args))
  
}

private[fxprof] case class PausedRangeArgs(
  startTime: Option[Milliseconds],
  endTime: Option[Milliseconds],
  reason: PausedRange_Reason,
)
