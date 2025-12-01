package fxprof

class ProfilerOverheadStats private (private[fxprof] val args: ProfilerOverheadStatsArgs) {
  def maxCleaning: Microseconds = args.maxCleaning
  def maxCounter: Microseconds = args.maxCounter
  def maxInterval: Microseconds = args.maxInterval
  def maxLockings: Microseconds = args.maxLockings
  def maxOverhead: Microseconds = args.maxOverhead
  def maxThread: Microseconds = args.maxThread
  def meanCleaning: Microseconds = args.meanCleaning
  def meanCounter: Microseconds = args.meanCounter
  def meanInterval: Microseconds = args.meanInterval
  def meanLockings: Microseconds = args.meanLockings
  def meanOverhead: Microseconds = args.meanOverhead
  def meanThread: Microseconds = args.meanThread
  def minCleaning: Microseconds = args.minCleaning
  def minCounter: Microseconds = args.minCounter
  def minInterval: Microseconds = args.minInterval
  def minLockings: Microseconds = args.minLockings
  def minOverhead: Microseconds = args.minOverhead
  def minThread: Microseconds = args.minThread
  def overheadDurations: Microseconds = args.overheadDurations
  def overheadPercentage: Microseconds = args.overheadPercentage
  def profiledDuration: Microseconds = args.profiledDuration
  def samplingCount: Microseconds = args.samplingCount

  def withMaxCleaning(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxCleaning = value))
  
  def withMaxCounter(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxCounter = value))
  
  def withMaxInterval(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxInterval = value))
  
  def withMaxLockings(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxLockings = value))
  
  def withMaxOverhead(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxOverhead = value))
  
  def withMaxThread(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(maxThread = value))
  
  def withMeanCleaning(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanCleaning = value))
  
  def withMeanCounter(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanCounter = value))
  
  def withMeanInterval(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanInterval = value))
  
  def withMeanLockings(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanLockings = value))
  
  def withMeanOverhead(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanOverhead = value))
  
  def withMeanThread(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(meanThread = value))
  
  def withMinCleaning(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minCleaning = value))
  
  def withMinCounter(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minCounter = value))
  
  def withMinInterval(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minInterval = value))
  
  def withMinLockings(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minLockings = value))
  
  def withMinOverhead(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minOverhead = value))
  
  def withMinThread(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(minThread = value))
  
  def withOverheadDurations(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(overheadDurations = value))
  
  def withOverheadPercentage(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(overheadPercentage = value))
  
  def withProfiledDuration(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(profiledDuration = value))
  
  def withSamplingCount(value: Microseconds): ProfilerOverheadStats =
    copy(_.copy(samplingCount = value))
  

  private def copy(f: ProfilerOverheadStatsArgs => ProfilerOverheadStatsArgs) = 
    new ProfilerOverheadStats(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerOverheadStats {
  def apply(
    maxCleaning: Microseconds,
    maxCounter: Microseconds,
    maxInterval: Microseconds,
    maxLockings: Microseconds,
    maxOverhead: Microseconds,
    maxThread: Microseconds,
    meanCleaning: Microseconds,
    meanCounter: Microseconds,
    meanInterval: Microseconds,
    meanLockings: Microseconds,
    meanOverhead: Microseconds,
    meanThread: Microseconds,
    minCleaning: Microseconds,
    minCounter: Microseconds,
    minInterval: Microseconds,
    minLockings: Microseconds,
    minOverhead: Microseconds,
    minThread: Microseconds,
    overheadDurations: Microseconds,
    overheadPercentage: Microseconds,
    profiledDuration: Microseconds,
    samplingCount: Microseconds,
  ): ProfilerOverheadStats = 
    new ProfilerOverheadStats(ProfilerOverheadStatsArgs(
      maxCleaning = maxCleaning,
      maxCounter = maxCounter,
      maxInterval = maxInterval,
      maxLockings = maxLockings,
      maxOverhead = maxOverhead,
      maxThread = maxThread,
      meanCleaning = meanCleaning,
      meanCounter = meanCounter,
      meanInterval = meanInterval,
      meanLockings = meanLockings,
      meanOverhead = meanOverhead,
      meanThread = meanThread,
      minCleaning = minCleaning,
      minCounter = minCounter,
      minInterval = minInterval,
      minLockings = minLockings,
      minOverhead = minOverhead,
      minThread = minThread,
      overheadDurations = overheadDurations,
      overheadPercentage = overheadPercentage,
      profiledDuration = profiledDuration,
      samplingCount = samplingCount,
    ))
  given JsonValueCodec[ProfilerOverheadStats] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfilerOverheadStats) = 
        new ProfilerOverheadStats(summon[JsonValueCodec[ProfilerOverheadStatsArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerOverheadStats, out: JsonWriter) = 
        summon[JsonValueCodec[ProfilerOverheadStatsArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfilerOverheadStats = null
    }
  
}
private[fxprof] case class ProfilerOverheadStatsArgs(
  maxCleaning: Microseconds,
  maxCounter: Microseconds,
  maxInterval: Microseconds,
  maxLockings: Microseconds,
  maxOverhead: Microseconds,
  maxThread: Microseconds,
  meanCleaning: Microseconds,
  meanCounter: Microseconds,
  meanInterval: Microseconds,
  meanLockings: Microseconds,
  meanOverhead: Microseconds,
  meanThread: Microseconds,
  minCleaning: Microseconds,
  minCounter: Microseconds,
  minInterval: Microseconds,
  minLockings: Microseconds,
  minOverhead: Microseconds,
  minThread: Microseconds,
  overheadDurations: Microseconds,
  overheadPercentage: Microseconds,
  profiledDuration: Microseconds,
  samplingCount: Microseconds,
)
private[fxprof] object ProfilerOverheadStatsArgs {
  given JsonValueCodec[ProfilerOverheadStatsArgs] = JsonCodecMaker.make
}
