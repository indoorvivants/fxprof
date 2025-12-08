package fxprof

/**
  * This object represents the configuration of the profiler when the profile was recorded.
  */
class ProfilerConfiguration private (private[fxprof] val args: ProfilerConfigurationArgs) {
  def threads: Vector[String] = args.threads

  def features: Vector[String] = args.features

  def capacity: Bytes = args.capacity

  def duration: Option[Double] = args.duration

  /** Optional because that field is introduced in Firefox 72.
    * Active Tab ID indicates a Firefox tab.
    * `0` means null value. Firefox only outputs `0` and not null, that's why we
    * should take care of this case while we are consuming it. If it's `0`, we
    * should revert back to the full view since there isn't enough data to show
    * the active tab view.
    */
  def activeTabID: Option[TabID] = args.activeTabID


  def withThreads(value: Vector[String]): ProfilerConfiguration =
    copy(_.copy(threads = value))
  
  def withFeatures(value: Vector[String]): ProfilerConfiguration =
    copy(_.copy(features = value))
  
  def withCapacity(value: Bytes): ProfilerConfiguration =
    copy(_.copy(capacity = value))
  
  def withDuration(value: Option[Double]): ProfilerConfiguration =
    copy(_.copy(duration = value))
  
  /** Setter for [[activeTabID]] field

    * Optional because that field is introduced in Firefox 72.
    * Active Tab ID indicates a Firefox tab.
    * `0` means null value. Firefox only outputs `0` and not null, that's why we
    * should take care of this case while we are consuming it. If it's `0`, we
    * should revert back to the full view since there isn't enough data to show
    * the active tab view.
    */
  def withActiveTabID(value: Option[TabID]): ProfilerConfiguration =
    copy(_.copy(activeTabID = value))
  

  private def copy(f: ProfilerConfigurationArgs => ProfilerConfigurationArgs) = 
    new ProfilerConfiguration(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerConfiguration {
  /** Construct a [[ProfilerConfiguration]]
      @param capacity
    */
  def apply(
    capacity: Bytes,
  ): ProfilerConfiguration = 
    new ProfilerConfiguration(ProfilerConfigurationArgs(
      threads = Vector.empty,
      features = Vector.empty,
      capacity = capacity,
      duration = None,
      activeTabID = None,
    ))
  implicit val codec: JsonValueCodec[ProfilerConfiguration] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfilerConfiguration) = 
        new ProfilerConfiguration(summon[JsonValueCodec[ProfilerConfigurationArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerConfiguration, out: JsonWriter) = 
        implicitly[JsonValueCodec[ProfilerConfigurationArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfilerConfiguration = null
    }
  
}
private[fxprof] case class ProfilerConfigurationArgs(
  threads: Vector[String],
  features: Vector[String],
  capacity: Bytes,
  duration: Option[Double],
  activeTabID: Option[TabID],
)
private[fxprof] object ProfilerConfigurationArgs {
  implicit val codec: ConfiguredJsonValueCodec[ProfilerConfigurationArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
