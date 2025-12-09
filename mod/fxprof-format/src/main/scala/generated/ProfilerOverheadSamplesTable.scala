package fxprof

/** Gecko Profiler records profiler overhead samples of specific tasks that take
  * time. counters: Time spent during collecting counter samples.
  * expiredMarkerCleaning: Time spent during expired marker cleanup lockings:
  * Time spent during acquiring locks. threads: Time spent during threads
  * sampling and marker collection.
  */
class ProfilerOverheadSamplesTable private (
    private[fxprof] val args: ProfilerOverheadSamplesTableArgs
) {
  def counters: Vector[Microseconds] = args.counters

  def expiredMarkerCleaning: Vector[Microseconds] = args.expiredMarkerCleaning

  def locking: Vector[Microseconds] = args.locking

  def threads: Vector[Microseconds] = args.threads

  def time: Vector[Milliseconds] = args.time

  def length: Double = args.length

  def withCounters(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(counters = value))

  def withExpiredMarkerCleaning(
      value: Vector[Microseconds]
  ): ProfilerOverheadSamplesTable =
    copy(_.copy(expiredMarkerCleaning = value))

  def withLocking(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(locking = value))

  def withThreads(value: Vector[Microseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(threads = value))

  def withTime(value: Vector[Milliseconds]): ProfilerOverheadSamplesTable =
    copy(_.copy(time = value))

  def withLength(value: Double): ProfilerOverheadSamplesTable =
    copy(_.copy(length = value))

  private def copy(
      f: ProfilerOverheadSamplesTableArgs => ProfilerOverheadSamplesTableArgs
  ) =
    new ProfilerOverheadSamplesTable(f(args))

  override def equals(o: Any) =
    o.isInstanceOf[ProfilerOverheadSamplesTable] && o
      .asInstanceOf[ProfilerOverheadSamplesTable]
      .args
      .equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ProfilerOverheadSamplesTable {

  /** Construct a [[ProfilerOverheadSamplesTable]]
    * @param length
    */
  def apply(
      length: Double
  ): ProfilerOverheadSamplesTable =
    new ProfilerOverheadSamplesTable(
      ProfilerOverheadSamplesTableArgs(
        counters = Vector.empty,
        expiredMarkerCleaning = Vector.empty,
        locking = Vector.empty,
        threads = Vector.empty,
        time = Vector.empty,
        length = length
      )
    )
  implicit val codec: JsonValueCodec[ProfilerOverheadSamplesTable] =
    new JsonValueCodec[ProfilerOverheadSamplesTable] {
      def decodeValue(in: JsonReader, default: ProfilerOverheadSamplesTable) =
        new ProfilerOverheadSamplesTable(
          implicitly[JsonValueCodec[ProfilerOverheadSamplesTableArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: ProfilerOverheadSamplesTable, out: JsonWriter) =
        implicitly[JsonValueCodec[ProfilerOverheadSamplesTableArgs]]
          .encodeValue(x.args, out)

      def nullValue: ProfilerOverheadSamplesTable = null
    }

}
private[fxprof] case class ProfilerOverheadSamplesTableArgs(
    counters: Vector[Microseconds],
    expiredMarkerCleaning: Vector[Microseconds],
    locking: Vector[Microseconds],
    threads: Vector[Microseconds],
    time: Vector[Milliseconds],
    length: Double
)
private[fxprof] object ProfilerOverheadSamplesTableArgs {
  implicit val codec: JsonValueCodec[ProfilerOverheadSamplesTableArgs] =
    JsonCodecMaker.makeWithRequiredCollectionFields

}
