package fxprof

class ExtensionTable private (private[fxprof] val args: ExtensionTableArgs) {
  def baseURL: Vector[String] = args.baseURL

  def id: Vector[String] = args.id

  def name: Vector[String] = args.name

  def length: Double = args.length

  def withBaseURL(value: Vector[String]): ExtensionTable =
    copy(_.copy(baseURL = value))

  def withId(value: Vector[String]): ExtensionTable =
    copy(_.copy(id = value))

  def withName(value: Vector[String]): ExtensionTable =
    copy(_.copy(name = value))

  def withLength(value: Double): ExtensionTable =
    copy(_.copy(length = value))

  private def copy(f: ExtensionTableArgs => ExtensionTableArgs) =
    new ExtensionTable(f(args))

}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object ExtensionTable {

  /** Construct a [[ExtensionTable]]
    * @param length
    */
  def apply(
      length: Double
  ): ExtensionTable =
    new ExtensionTable(
      ExtensionTableArgs(
        baseURL = Vector.empty,
        id = Vector.empty,
        name = Vector.empty,
        length = length
      )
    )
  given JsonValueCodec[ExtensionTable] =
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: ExtensionTable) =
        new ExtensionTable(
          summon[JsonValueCodec[ExtensionTableArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: ExtensionTable, out: JsonWriter) =
        summon[JsonValueCodec[ExtensionTableArgs]].encodeValue(x.args, out)

      def nullValue: ExtensionTable = null
    }

}
private[fxprof] case class ExtensionTableArgs(
    baseURL: Vector[String],
    id: Vector[String],
    name: Vector[String],
    length: Double
)
private[fxprof] object ExtensionTableArgs {
  given JsonValueCodec[ExtensionTableArgs] = JsonCodecMaker.make
}
