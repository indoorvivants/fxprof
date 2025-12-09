package fxprof

/**
  * Information about profiler overhead. It includes overhead timings for
  * counters, expired marker cleanings, mutex locking and threads. Also it
  * includes statistics about those individual and overall overhead.
  */
class ProfilerOverhead private (private[fxprof] val args: ProfilerOverheadArgs) {
  def samples: ProfilerOverheadSamplesTable = args.samples

  /** There is no statistics object if there is no sample.
    */
  def statistics: Option[ProfilerOverheadStats] = args.statistics

  def pid: Pid = args.pid

  def mainThreadIndex: ThreadIndex = args.mainThreadIndex


  def withSamples(value: ProfilerOverheadSamplesTable): ProfilerOverhead =
    copy(_.copy(samples = value))
  
  /** Setter for [[statistics]] field

    * There is no statistics object if there is no sample.
    */
  def withStatistics(value: Option[ProfilerOverheadStats]): ProfilerOverhead =
    copy(_.copy(statistics = value))
  
  def withPid(value: Pid): ProfilerOverhead =
    copy(_.copy(pid = value))
  
  def withMainThreadIndex(value: ThreadIndex): ProfilerOverhead =
    copy(_.copy(mainThreadIndex = value))
  

  private def copy(f: ProfilerOverheadArgs => ProfilerOverheadArgs) = 
    new ProfilerOverhead(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[ProfilerOverhead] && o.asInstanceOf[ProfilerOverhead].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerOverhead {
  /** Construct a [[ProfilerOverhead]]
      @param samples
      @param pid
      @param mainThreadIndex
    */
  def apply(
    samples: ProfilerOverheadSamplesTable,
    pid: Pid,
    mainThreadIndex: ThreadIndex,
  ): ProfilerOverhead = 
    new ProfilerOverhead(ProfilerOverheadArgs(
      samples = samples,
      statistics = None,
      pid = pid,
      mainThreadIndex = mainThreadIndex,
    ))
  implicit val codec: JsonValueCodec[ProfilerOverhead] = 
    new JsonValueCodec[ProfilerOverhead] {
      def decodeValue(in: JsonReader, default: ProfilerOverhead) = 
        new ProfilerOverhead(implicitly[JsonValueCodec[ProfilerOverheadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerOverhead, out: JsonWriter) = 
        implicitly[JsonValueCodec[ProfilerOverheadArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfilerOverhead = null
    }
  
}
private[fxprof] case class ProfilerOverheadArgs(
  samples: ProfilerOverheadSamplesTable,
  statistics: Option[ProfilerOverheadStats],
  pid: Pid,
  mainThreadIndex: ThreadIndex,
)
private[fxprof] object ProfilerOverheadArgs {
  implicit val codec: JsonValueCodec[ProfilerOverheadArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
