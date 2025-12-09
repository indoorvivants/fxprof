package fxprof

/** All of the data for a processed profile.
  */
class Profile private (private[fxprof] val args: ProfileArgs) {
  def meta: ProfileMeta = args.meta

  def libs: Vector[Lib] = args.libs

  def pages: Option[PageList] = args.pages

  /** The counters list is optional only because old profilers may not have
    * them. An upgrader could be written to make this non-optional.
    */
  def counters: Option[Vector[RawCounter]] = args.counters

  /** The profilerOverhead list is optional only because old profilers may not
    * have them. An upgrader could be written to make this non-optional. This is
    * list because there is a profiler overhead per process.
    */
  def profilerOverhead: Option[Vector[ProfilerOverhead]] = args.profilerOverhead

  def shared: RawProfileSharedData = args.shared

  def threads: Vector[RawThread] = args.threads

  def profilingLog: Option[ProfilingLog] = args.profilingLog

  def profileGatheringLog: Option[ProfilingLog] = args.profileGatheringLog

  def withMeta(value: ProfileMeta): Profile =
    copy(_.copy(meta = value))

  def withLibs(value: Vector[Lib]): Profile =
    copy(_.copy(libs = value))

  def withPages(value: Option[PageList]): Profile =
    copy(_.copy(pages = value))

  /** Setter for [[counters]] field
    *
    * The counters list is optional only because old profilers may not have
    * them. An upgrader could be written to make this non-optional.
    */
  def withCounters(value: Option[Vector[RawCounter]]): Profile =
    copy(_.copy(counters = value))

  /** Setter for [[profilerOverhead]] field
    *
    * The profilerOverhead list is optional only because old profilers may not
    * have them. An upgrader could be written to make this non-optional. This is
    * list because there is a profiler overhead per process.
    */
  def withProfilerOverhead(value: Option[Vector[ProfilerOverhead]]): Profile =
    copy(_.copy(profilerOverhead = value))

  def withShared(value: RawProfileSharedData): Profile =
    copy(_.copy(shared = value))

  def withThreads(value: Vector[RawThread]): Profile =
    copy(_.copy(threads = value))

  def withProfilingLog(value: Option[ProfilingLog]): Profile =
    copy(_.copy(profilingLog = value))

  def withProfileGatheringLog(value: Option[ProfilingLog]): Profile =
    copy(_.copy(profileGatheringLog = value))

  private def copy(f: ProfileArgs => ProfileArgs) =
    new Profile(f(args))

  override def equals(o: Any) =
    o.isInstanceOf[Profile] && o.asInstanceOf[Profile].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Profile {

  /** Construct a [[Profile]]
    * @param meta
    * @param shared
    */
  def apply(
      meta: ProfileMeta,
      shared: RawProfileSharedData
  ): Profile =
    new Profile(
      ProfileArgs(
        meta = meta,
        libs = Vector.empty,
        pages = None,
        counters = None,
        profilerOverhead = None,
        shared = shared,
        threads = Vector.empty,
        profilingLog = None,
        profileGatheringLog = None
      )
    )
  implicit val codec: JsonValueCodec[Profile] =
    new JsonValueCodec[Profile] {
      def decodeValue(in: JsonReader, default: Profile) =
        new Profile(
          implicitly[JsonValueCodec[ProfileArgs]].decodeValue(in, default.args)
        )

      def encodeValue(x: Profile, out: JsonWriter) =
        implicitly[JsonValueCodec[ProfileArgs]].encodeValue(x.args, out)

      def nullValue: Profile = null
    }

}
private[fxprof] case class ProfileArgs(
    meta: ProfileMeta,
    libs: Vector[Lib],
    pages: Option[PageList],
    counters: Option[Vector[RawCounter]],
    profilerOverhead: Option[Vector[ProfilerOverhead]],
    shared: RawProfileSharedData,
    threads: Vector[RawThread],
    profilingLog: Option[ProfilingLog],
    profileGatheringLog: Option[ProfilingLog]
)
private[fxprof] object ProfileArgs {
  implicit val codec: JsonValueCodec[ProfileArgs] =
    JsonCodecMaker.makeWithRequiredCollectionFields

}
