package fxprof

/** Markers can include a stack. These are converted to a cause backtrace, which
  * includes the time the stack was taken. Sometimes this cause can be async,
  * and triggered before the marker, or it can be synchronous, and the time is
  * contained within the marker's start and end time.
  */
class CauseBacktrace private (private[fxprof] val args: CauseBacktraceArgs) {

  /** `tid` is optional because older processed profiles may not have it. No
    * upgrader was written for this change.
    */
  def tid: Option[Tid] = args.tid

  def time: Option[Milliseconds] = args.time

  def stack: Option[IndexIntoStackTable] = args.stack

  /** Setter for [[tid]] field
    *
    * `tid` is optional because older processed profiles may not have it. No
    * upgrader was written for this change.
    */
  def withTid(value: Option[Tid]): CauseBacktrace =
    copy(_.copy(tid = value))

  def withTime(value: Option[Milliseconds]): CauseBacktrace =
    copy(_.copy(time = value))

  def withStack(value: Option[IndexIntoStackTable]): CauseBacktrace =
    copy(_.copy(stack = value))

  private def copy(f: CauseBacktraceArgs => CauseBacktraceArgs) =
    new CauseBacktrace(f(args))

  override def equals(o: Any) = o.isInstanceOf[CauseBacktrace] && o
    .asInstanceOf[CauseBacktrace]
    .args
    .equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object CauseBacktrace {

  /** Construct a [[CauseBacktrace]]
    */
  def apply(
  ): CauseBacktrace =
    new CauseBacktrace(
      CauseBacktraceArgs(
        tid = None,
        time = None,
        stack = None
      )
    )
  implicit val codec: JsonValueCodec[CauseBacktrace] =
    new JsonValueCodec[CauseBacktrace] {
      def decodeValue(in: JsonReader, default: CauseBacktrace) =
        new CauseBacktrace(
          implicitly[JsonValueCodec[CauseBacktraceArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: CauseBacktrace, out: JsonWriter) =
        implicitly[JsonValueCodec[CauseBacktraceArgs]].encodeValue(x.args, out)

      def nullValue: CauseBacktrace = null
    }

}
private[fxprof] case class CauseBacktraceArgs(
    tid: Option[Tid],
    time: Option[Milliseconds],
    stack: Option[IndexIntoStackTable]
)
private[fxprof] object CauseBacktraceArgs {
  implicit val codec: JsonValueCodec[CauseBacktraceArgs] =
    JsonCodecMaker.makeWithRequiredCollectionFields

}
