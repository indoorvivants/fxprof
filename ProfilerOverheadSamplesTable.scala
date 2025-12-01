package fxprof

class ProfilerOverheadSamplesTable private (args: ProfilerOverheadSamplesTableArgs) {
  def counters: Array[Microseconds] = args.counters
  def expiredMarkerCleaning: Array[Microseconds] = args.expiredMarkerCleaning
  def locking: Array[Microseconds] = args.locking
  def threads: Array[Microseconds] = args.threads
  def time: Array[Milliseconds] = args.time
  def length: Double = args.length

  def withCounters(value: Array[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(counters = value))
  
  def withExpiredMarkerCleaning(value: Array[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(expiredMarkerCleaning = value))
  
  def withLocking(value: Array[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(locking = value))
  
  def withThreads(value: Array[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(threads = value))
  
  def withTime(value: Array[Milliseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(time = value))
  
  def withLength(value: Double): ProfilerOverheadSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: ProfilerOverheadSamplesTableArgs => ProfilerOverheadSamplesTableArgs) = 
    new ProfilerOverheadSamplesTable(f(args))
  
}

private[fxprof] case class ProfilerOverheadSamplesTableArgs(
  counters: Array[Microseconds],
  expiredMarkerCleaning: Array[Microseconds],
  locking: Array[Microseconds],
  threads: Array[Microseconds],
  time: Array[Milliseconds],
  length: Double,
)
