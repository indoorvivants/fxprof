package fxprof

class ProfilerOverheadSamplesTable private (private[fxprof] val args: ProfilerOverheadSamplesTableArgs) {
  def counters: Vector[Microseconds] = args.counters
  def expiredMarkerCleaning: Vector[Microseconds] = args.expiredMarkerCleaning
  def locking: Vector[Microseconds] = args.locking
  def threads: Vector[Microseconds] = args.threads
  def time: Vector[Milliseconds] = args.time
  def length: Double = args.length

  def withCounters(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(counters = value))
  
  def withExpiredMarkerCleaning(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(expiredMarkerCleaning = value))
  
  def withLocking(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(locking = value))
  
  def withThreads(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(threads = value))
  
  def withTime(value: Vector[Milliseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(time = value))
  
  def withLength(value: Double): ProfilerOverheadSamplesTable =
    copy(_.copy(length = value))
  

  private def copy(f: ProfilerOverheadSamplesTableArgs => ProfilerOverheadSamplesTableArgs) = 
    new ProfilerOverheadSamplesTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerOverheadSamplesTable {
  def apply(
    length: Double,
  ): ProfilerOverheadSamplesTable = 
    new ProfilerOverheadSamplesTable(ProfilerOverheadSamplesTableArgs(
      length = length,
    ))
  given JsonValueCodec[ProfilerOverheadSamplesTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ProfilerOverheadSamplesTable) = 
        new ProfilerOverheadSamplesTable(summon[JsonValueCodec[ProfilerOverheadSamplesTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: ProfilerOverheadSamplesTable, out: JsonWriter) = 
        summon[JsonValueCodec[ProfilerOverheadSamplesTableArgs]].encodeValue(x.args, out)
      
      def nullValue: ProfilerOverheadSamplesTable = null
    }
  
}
private[fxprof] case class ProfilerOverheadSamplesTableArgs(
  counters: Vector[Microseconds] = Vector.empty,
  expiredMarkerCleaning: Vector[Microseconds] = Vector.empty,
  locking: Vector[Microseconds] = Vector.empty,
  threads: Vector[Microseconds] = Vector.empty,
  time: Vector[Milliseconds] = Vector.empty,
  length: Double,
)
private[fxprof] object ProfilerOverheadSamplesTableArgs {
  given JsonValueCodec[ProfilerOverheadSamplesTableArgs] = JsonCodecMaker.make
}
