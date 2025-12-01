package fxprof

class ProfilerConfiguration private (args: ProfilerConfigurationArgs) {
  def threads: Array[String] = args.threads
  def features: Array[String] = args.features
  def capacity: Bytes = args.capacity
  def duration: Option[Double] = args.duration
  def activeTabID: Option[TabID] = args.activeTabID

  def withThreads(value: Array[String]): ProfilerConfiguration =
    copy(_.copy(threads = value))
  
  def withFeatures(value: Array[String]): ProfilerConfiguration =
    copy(_.copy(features = value))
  
  def withCapacity(value: Bytes): ProfilerConfiguration =
    copy(_.copy(capacity = value))
  
  def withDuration(value: Option[Double]): ProfilerConfiguration =
    copy(_.copy(duration = value))
  
  def withActiveTabID(value: Option[TabID]): ProfilerConfiguration =
    copy(_.copy(activeTabID = value))
  

  private def copy(f: ProfilerConfigurationArgs => ProfilerConfigurationArgs) = 
    new ProfilerConfiguration(f(args))
  
}

object ProfilerConfiguration {
  def apply(
    capacity: Bytes,
  ): ProfilerConfiguration = 
    new ProfilerConfiguration(ProfilerConfigurationArgs(
      capacity = capacity,
    ))
}
private[fxprof] case class ProfilerConfigurationArgs(
  threads: Array[String] = Array.empty,
  features: Array[String] = Array.empty,
  capacity: Bytes,
  duration: Option[Double] = None,
  activeTabID: Option[TabID] = None,
)
