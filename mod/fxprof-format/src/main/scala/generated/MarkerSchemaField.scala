package fxprof

class MarkerSchemaField private (private[fxprof] val args: MarkerSchemaFieldArgs) {
  /** The property key of the marker data property that carries the field value.
    */
  def key: String = args.key

  /** An optional user-facing label.
    * If no label is provided, the key is displayed instead.
    */
  def label: Option[String] = args.label

  /** The format / type of this field. This affects how the field's value is
    * displayed and determines which types of values are accepted for this field.
    */
  def format: MarkerFormatType = args.format

  /** If present and set to true, this field will not be shown in the list
    * of fields in the tooltip or in the sidebar. Such fields can still be
    * used inside labels and their values are matched when searching.
    */
  def hidden: Option[Boolean] = args.hidden


  /** Setter for [[key]] field

    * The property key of the marker data property that carries the field value.
    */
  def withKey(value: String): MarkerSchemaField =
    copy(_.copy(key = value))
  
  /** Setter for [[label]] field

    * An optional user-facing label.
    * If no label is provided, the key is displayed instead.
    */
  def withLabel(value: Option[String]): MarkerSchemaField =
    copy(_.copy(label = value))
  
  /** Setter for [[format]] field

    * The format / type of this field. This affects how the field's value is
    * displayed and determines which types of values are accepted for this field.
    */
  def withFormat(value: MarkerFormatType): MarkerSchemaField =
    copy(_.copy(format = value))
  
  /** Setter for [[hidden]] field

    * If present and set to true, this field will not be shown in the list
    * of fields in the tooltip or in the sidebar. Such fields can still be
    * used inside labels and their values are matched when searching.
    */
  def withHidden(value: Option[Boolean]): MarkerSchemaField =
    copy(_.copy(hidden = value))
  

  private def copy(f: MarkerSchemaFieldArgs => MarkerSchemaFieldArgs) = 
    new MarkerSchemaField(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[MarkerSchemaField] && o.asInstanceOf[MarkerSchemaField].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object MarkerSchemaField {
  /** Construct a [[MarkerSchemaField]]
      @param keyThe property key of the marker data property that carries the field value.
      @param formatThe format / type of this field. This affects how the field's value is
                   displayed and determines which types of values are accepted for this field.
    */
  def apply(
    key: String,
    format: MarkerFormatType,
  ): MarkerSchemaField = 
    new MarkerSchemaField(MarkerSchemaFieldArgs(
      key = key,
      label = None,
      format = format,
      hidden = None,
    ))
  implicit val codec: JsonValueCodec[MarkerSchemaField] = 
    new JsonValueCodec[MarkerSchemaField] {
      def decodeValue(in: JsonReader, default: MarkerSchemaField) = 
        new MarkerSchemaField(implicitly[JsonValueCodec[MarkerSchemaFieldArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: MarkerSchemaField, out: JsonWriter) = 
        implicitly[JsonValueCodec[MarkerSchemaFieldArgs]].encodeValue(x.args, out)
      
      def nullValue: MarkerSchemaField = null
    }
  
}
private[fxprof] case class MarkerSchemaFieldArgs(
  key: String,
  label: Option[String],
  format: MarkerFormatType,
  hidden: Option[Boolean],
)
private[fxprof] object MarkerSchemaFieldArgs {
  implicit val codec: JsonValueCodec[MarkerSchemaFieldArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
