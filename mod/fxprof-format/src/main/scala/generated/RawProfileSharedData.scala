package fxprof

class RawProfileSharedData private (private[fxprof] val args: RawProfileSharedDataArgs) {
  /** Strings for profiles are collected into a single table, and are referred to by
    * their index by other tables.
    */
  def stringArray: Vector[String] = args.stringArray

  /** Optional sources table for JS source UUID to URL mapping.
    * Added for UUID-based source fetching.
    */
  def sources: SourceTable = args.sources


  /** Setter for [[stringArray]] field

    * Strings for profiles are collected into a single table, and are referred to by
    * their index by other tables.
    */
  def withStringArray(value: Vector[String]): RawProfileSharedData =
    copy(_.copy(stringArray = value))
  
  /** Setter for [[sources]] field

    * Optional sources table for JS source UUID to URL mapping.
    * Added for UUID-based source fetching.
    */
  def withSources(value: SourceTable): RawProfileSharedData =
    copy(_.copy(sources = value))
  

  private def copy(f: RawProfileSharedDataArgs => RawProfileSharedDataArgs) = 
    new RawProfileSharedData(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[RawProfileSharedData] && o.asInstanceOf[RawProfileSharedData].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawProfileSharedData {
  /** Construct a [[RawProfileSharedData]]
      @param sourcesOptional sources table for JS source UUID to URL mapping.
                    Added for UUID-based source fetching.
    */
  def apply(
    sources: SourceTable,
  ): RawProfileSharedData = 
    new RawProfileSharedData(RawProfileSharedDataArgs(
      stringArray = Vector.empty,
      sources = sources,
    ))
  implicit val codec: JsonValueCodec[RawProfileSharedData] = 
    new JsonValueCodec[RawProfileSharedData] {
      def decodeValue(in: JsonReader, default: RawProfileSharedData) = 
        new RawProfileSharedData(implicitly[JsonValueCodec[RawProfileSharedDataArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawProfileSharedData, out: JsonWriter) = 
        implicitly[JsonValueCodec[RawProfileSharedDataArgs]].encodeValue(x.args, out)
      
      def nullValue: RawProfileSharedData = null
    }
  
}
private[fxprof] case class RawProfileSharedDataArgs(
  stringArray: Vector[String],
  sources: SourceTable,
)
private[fxprof] object RawProfileSharedDataArgs {
  implicit val codec: JsonValueCodec[RawProfileSharedDataArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
