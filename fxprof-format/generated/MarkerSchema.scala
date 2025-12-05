package fxprof

class MarkerSchema private (private[fxprof] val args: MarkerSchemaArgs) {
  /** The unique identifier for this marker.
    */
  def name: String = args.name

  /** The label of how this marker should be displayed in the UI.
    * If none is provided, then the name is used.
    */
  def tooltipLabel: Option[String] = args.tooltipLabel

  /** This is how the marker shows up in the Marker Table description.
    * If none is provided, then the name is used.
    */
  def tableLabel: Option[String] = args.tableLabel

  /** This is how the marker shows up in the Marker Chart, where it is drawn
    * on the screen as a bar.
    * If none is provided, then the name is used.
    */
  def chartLabel: Option[String] = args.chartLabel

  /** The locations to display
    */
  def display: Vector[MarkerDisplayLocation] = args.display

  /** The fields that can be present on markers of this type.
    * Not all listed fields have to be present on every marker (they're all optional).
    */
  def fields: Vector[MarkerSchemaField] = args.fields

  /** An optional description for markers of this type.
    * Will be displayed to the user.
    */
  def description: Option[String] = args.description

  /** if present, give the marker its own local track
    */
  def graphs: Option[Vector[MarkerGraph]] = args.graphs

  /** If present, specifies the key of a marker field that contains the marker's color.
    * The field should contain one of the GraphColor values.
    * This allows individual markers to have different colors based on their data.
    */
  def colorField: Option[String] = args.colorField

  /** If set to true, markers of this type are assumed to be well-nested with all
    * other stack-based markers on the same thread. Stack-based markers may
    * be displayed in a different part of the marker chart than non-stack-based
    * markers.
    * Instant markers are always well-nested.
    * For interval markers, or for intervals defined by a start and an end marker,
    * well-nested means that, for all marker-defined timestamp intervals A and B,
    * A either fully encompasses B or is fully encompassed by B - there is no
    * partial overlap.
    */
  def isStackBased: Option[Boolean] = args.isStackBased


  /** Setter for [[name]] field

    * The unique identifier for this marker.
    */
  def withName(value: String): MarkerSchema =
    copy(_.copy(name = value))
  
  /** Setter for [[tooltipLabel]] field

    * The label of how this marker should be displayed in the UI.
    * If none is provided, then the name is used.
    */
  def withTooltipLabel(value: Option[String]): MarkerSchema =
    copy(_.copy(tooltipLabel = value))
  
  /** Setter for [[tableLabel]] field

    * This is how the marker shows up in the Marker Table description.
    * If none is provided, then the name is used.
    */
  def withTableLabel(value: Option[String]): MarkerSchema =
    copy(_.copy(tableLabel = value))
  
  /** Setter for [[chartLabel]] field

    * This is how the marker shows up in the Marker Chart, where it is drawn
    * on the screen as a bar.
    * If none is provided, then the name is used.
    */
  def withChartLabel(value: Option[String]): MarkerSchema =
    copy(_.copy(chartLabel = value))
  
  /** Setter for [[display]] field

    * The locations to display
    */
  def withDisplay(value: Vector[MarkerDisplayLocation]): MarkerSchema =
    copy(_.copy(display = value))
  
  /** Setter for [[fields]] field

    * The fields that can be present on markers of this type.
    * Not all listed fields have to be present on every marker (they're all optional).
    */
  def withFields(value: Vector[MarkerSchemaField]): MarkerSchema =
    copy(_.copy(fields = value))
  
  /** Setter for [[description]] field

    * An optional description for markers of this type.
    * Will be displayed to the user.
    */
  def withDescription(value: Option[String]): MarkerSchema =
    copy(_.copy(description = value))
  
  /** Setter for [[graphs]] field

    * if present, give the marker its own local track
    */
  def withGraphs(value: Option[Vector[MarkerGraph]]): MarkerSchema =
    copy(_.copy(graphs = value))
  
  /** Setter for [[colorField]] field

    * If present, specifies the key of a marker field that contains the marker's color.
    * The field should contain one of the GraphColor values.
    * This allows individual markers to have different colors based on their data.
    */
  def withColorField(value: Option[String]): MarkerSchema =
    copy(_.copy(colorField = value))
  
  /** Setter for [[isStackBased]] field

    * If set to true, markers of this type are assumed to be well-nested with all
    * other stack-based markers on the same thread. Stack-based markers may
    * be displayed in a different part of the marker chart than non-stack-based
    * markers.
    * Instant markers are always well-nested.
    * For interval markers, or for intervals defined by a start and an end marker,
    * well-nested means that, for all marker-defined timestamp intervals A and B,
    * A either fully encompasses B or is fully encompassed by B - there is no
    * partial overlap.
    */
  def withIsStackBased(value: Option[Boolean]): MarkerSchema =
    copy(_.copy(isStackBased = value))
  

  private def copy(f: MarkerSchemaArgs => MarkerSchemaArgs) = 
    new MarkerSchema(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object MarkerSchema {
  /** Construct a [[MarkerSchema]]
      @param nameThe unique identifier for this marker.
    */
  def apply(
    name: String,
  ): MarkerSchema = 
    new MarkerSchema(MarkerSchemaArgs(
      name = name,
      tooltipLabel = None,
      tableLabel = None,
      chartLabel = None,
      display = Vector.empty,
      fields = Vector.empty,
      description = None,
      graphs = None,
      colorField = None,
      isStackBased = None,
    ))
  given JsonValueCodec[MarkerSchema] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: MarkerSchema) = 
        new MarkerSchema(summon[JsonValueCodec[MarkerSchemaArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: MarkerSchema, out: JsonWriter) = 
        summon[JsonValueCodec[MarkerSchemaArgs]].encodeValue(x.args, out)
      
      def nullValue: MarkerSchema = null
    }
  
}
private[fxprof] case class MarkerSchemaArgs(
  name: String,
  tooltipLabel: Option[String],
  tableLabel: Option[String],
  chartLabel: Option[String],
  display: Vector[MarkerDisplayLocation],
  fields: Vector[MarkerSchemaField],
  description: Option[String],
  graphs: Option[Vector[MarkerGraph]],
  colorField: Option[String],
  isStackBased: Option[Boolean],
)
private[fxprof] object MarkerSchemaArgs {
  given ConfiguredJsonValueCodec[MarkerSchemaArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
