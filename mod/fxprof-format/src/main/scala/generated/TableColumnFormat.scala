package fxprof

class TableColumnFormat private (private[fxprof] val args: TableColumnFormatArgs) {
  /** type for formatting, default is string
    */
  def `type`: Option[MarkerFormatType] = args.`type`

  /** header column label
    */
  def label: Option[String] = args.label


  /** Setter for [[type]] field

    * type for formatting, default is string
    */
  def `withtype`(value: Option[MarkerFormatType]): TableColumnFormat =
    copy(_.copy(`type` = value))
  
  /** Setter for [[label]] field

    * header column label
    */
  def withLabel(value: Option[String]): TableColumnFormat =
    copy(_.copy(label = value))
  

  private def copy(f: TableColumnFormatArgs => TableColumnFormatArgs) = 
    new TableColumnFormat(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[TableColumnFormat] && o.asInstanceOf[TableColumnFormat].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object TableColumnFormat {
  /** Construct a [[TableColumnFormat]]
    */
  def apply(
  ): TableColumnFormat = 
    new TableColumnFormat(TableColumnFormatArgs(
      `type` = None,
      label = None,
    ))
  implicit val codec: JsonValueCodec[TableColumnFormat] = 
    new JsonValueCodec[TableColumnFormat] {
      def decodeValue(in: JsonReader, default: TableColumnFormat) = 
        new TableColumnFormat(implicitly[JsonValueCodec[TableColumnFormatArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: TableColumnFormat, out: JsonWriter) = 
        implicitly[JsonValueCodec[TableColumnFormatArgs]].encodeValue(x.args, out)
      
      def nullValue: TableColumnFormat = null
    }
  
}
private[fxprof] case class TableColumnFormatArgs(
  `type`: Option[MarkerFormatType],
  label: Option[String],
)
private[fxprof] object TableColumnFormatArgs {
  implicit val codec: JsonValueCodec[TableColumnFormatArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
