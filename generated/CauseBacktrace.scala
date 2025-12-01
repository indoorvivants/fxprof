package fxprof

class CauseBacktrace private (args: CauseBacktraceArgs) {
  def tid: Option[Tid] = args.tid
  def time: Option[Milliseconds] = args.time
  def stack: Option[IndexIntoStackTable] = args.stack

  def withTid(value: Option[Tid]): CauseBacktrace =
    copy(_.copy(tid = value))
  
  def withTime(value: Option[Milliseconds]): CauseBacktrace =
    copy(_.copy(time = value))
  
  def withStack(value: Option[IndexIntoStackTable]): CauseBacktrace =
    copy(_.copy(stack = value))
  

  private def copy(f: CauseBacktraceArgs => CauseBacktraceArgs) = 
    new CauseBacktrace(f(args))
  
}

object CauseBacktrace {
  def apply(
  ): CauseBacktrace = 
    new CauseBacktrace(CauseBacktraceArgs(
    ))
}
private[fxprof] case class CauseBacktraceArgs(
  tid: Option[Tid] = None,
  time: Option[Milliseconds] = None,
  stack: Option[IndexIntoStackTable] = None,
)
