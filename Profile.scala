package fxprof

class Profile private (args: ProfileArgs) {
  def meta: ProfileMeta = args.meta
  def libs: Array[Lib] = args.libs
  def pages: Option[PageList] = args.pages
  def counters: Option[Array[RawCounter]] = args.counters
  def profilerOverhead: Option[Array[ProfilerOverhead]] = args.profilerOverhead
  def shared: RawProfileSharedData = args.shared
  def threads: Array[RawThread] = args.threads
  def profilingLog: Option[ProfilingLog] = args.profilingLog
  def profileGatheringLog: Option[ProfilingLog] = args.profileGatheringLog

  def withMeta(value: ProfileMeta): Profile =
    copy(_.copy(meta = value))
  
  def withLibs(value: Array[Lib]): Profile =
    copy(_.copy(libs = value))
  
  def withPages(value: Option[PageList]): Profile =
    copy(_.copy(pages = value))
  
  def withCounters(value: Option[Array[RawCounter]]): Profile =
    copy(_.copy(counters = value))
  
  def withProfilerOverhead(value: Option[Array[ProfilerOverhead]]): Profile =
    copy(_.copy(profilerOverhead = value))
  
  def withShared(value: RawProfileSharedData): Profile =
    copy(_.copy(shared = value))
  
  def withThreads(value: Array[RawThread]): Profile =
    copy(_.copy(threads = value))
  
  def withProfilingLog(value: Option[ProfilingLog]): Profile =
    copy(_.copy(profilingLog = value))
  
  def withProfileGatheringLog(value: Option[ProfilingLog]): Profile =
    copy(_.copy(profileGatheringLog = value))
  

  private def copy(f: ProfileArgs => ProfileArgs) = 
    new Profile(f(args))
  
}

private[fxprof] case class ProfileArgs(
  meta: ProfileMeta,
  libs: Array[Lib],
  pages: Option[PageList],
  counters: Option[Array[RawCounter]],
  profilerOverhead: Option[Array[ProfilerOverhead]],
  shared: RawProfileSharedData,
  threads: Array[RawThread],
  profilingLog: Option[ProfilingLog],
  profileGatheringLog: Option[ProfilingLog],
)
