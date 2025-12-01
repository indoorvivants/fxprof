package fxprof

class ProfilerOverhead private (args: ProfilerOverheadArgs) {
  def samples: ProfilerOverheadSamplesTable = args.samples
  def statistics: Option[ProfilerOverheadStats] = args.statistics
  def pid: Pid = args.pid
  def mainThreadIndex: ThreadIndex = args.mainThreadIndex

  def withSamples(value: ProfilerOverheadSamplesTable): ProfilerOverhead =
    copy(_.copy(samples = value))
  
  def withStatistics(value: Option[ProfilerOverheadStats]): ProfilerOverhead =
    copy(_.copy(statistics = value))
  
  def withPid(value: Pid): ProfilerOverhead =
    copy(_.copy(pid = value))
  
  def withMainThreadIndex(value: ThreadIndex): ProfilerOverhead =
    copy(_.copy(mainThreadIndex = value))
  

  private def copy(f: ProfilerOverheadArgs => ProfilerOverheadArgs) = 
    new ProfilerOverhead(f(args))
  
}

private[fxprof] case class ProfilerOverheadArgs(
  samples: ProfilerOverheadSamplesTable,
  statistics: Option[ProfilerOverheadStats],
  pid: Pid,
  mainThreadIndex: ThreadIndex,
)
