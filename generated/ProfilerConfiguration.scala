package fxprof

class ProfilerConfiguration private (private[fxprof] val args: ProfilerConfigurationArgs) {
  def threads: Vector[String] = args.threads
  def features: Vector[String] = args.features
  def capacity: Bytes = args.capacity
  def duration: Option[Double] = args.duration
  def activeTabID: Option[TabID] = args.activeTabID

  def withThreads(value: Vector[String]): ProfilerConfiguration =
    copy(_.copy(threads = value))
  
  def withFeatures(value: Vector[String]): ProfilerConfiguration =
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

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerConfiguration {
  def apply(
    capacity: Bytes,
  ): ProfilerConfiguration = 
    new ProfilerConfiguration(ProfilerConfigurationArgs(
      capacity = capacity,
    ))
  given JsonValueCodec[ProfilerConfiguration] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfilerConfiguration) = 
        new ProfilerConfiguration(summon[JsonValueCodec[ProfilerConfigurationArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerConfiguration, out: JsonWriter) = 
        summon[JsonValueCodec[ProfilerConfigurationArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfilerConfiguration = null
    }
  
}
private[fxprof] case class ProfilerConfigurationArgs(
  threads: Vector[String] = Vector.empty,
  features: Vector[String] = Vector.empty,
  capacity: Bytes,
  duration: Option[Double] = None,
  activeTabID: Option[TabID] = None,
)
private[fxprof] object ProfilerConfigurationArgs {
  given JsonValueCodec[ProfilerConfigurationArgs] = JsonCodecMaker.make
}
