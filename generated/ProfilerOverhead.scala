package fxprof

class ProfilerOverhead private (private[fxprof] val args: ProfilerOverheadArgs) {
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

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerOverhead {
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
  given JsonValueCodec[ProfilerOverhead] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfilerOverhead) = 
        new ProfilerOverhead(summon[JsonValueCodec[ProfilerOverheadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerOverhead, out: JsonWriter) = 
        summon[JsonValueCodec[ProfilerOverheadArgs]].encodeValue(x.args, out)
      
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
  given JsonValueCodec[ProfilerOverheadArgs] = JsonCodecMaker.make
}
