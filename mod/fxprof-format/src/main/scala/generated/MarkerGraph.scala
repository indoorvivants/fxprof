package fxprof

class MarkerGraph private (private[fxprof] val args: MarkerGraphArgs) {
  def key: String = args.key

  def `type`: MarkerGraphType = args.`type`

  def color: Option[GraphColor] = args.color


  def withKey(value: String): MarkerGraph =
    copy(_.copy(key = value))
  
  def `withtype`(value: MarkerGraphType): MarkerGraph =
    copy(_.copy(`type` = value))
  
  def withColor(value: Option[GraphColor]): MarkerGraph =
    copy(_.copy(color = value))
  

  private def copy(f: MarkerGraphArgs => MarkerGraphArgs) = 
    new MarkerGraph(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[MarkerGraph] && o.asInstanceOf[MarkerGraph].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object MarkerGraph {
  /** Construct a [[MarkerGraph]]
      @param key
      @param type
    */
  def apply(
    key: String,
    `type`: MarkerGraphType,
  ): MarkerGraph = 
    new MarkerGraph(MarkerGraphArgs(
      key = key,
      `type` = `type`,
      color = None,
    ))
  implicit val codec: JsonValueCodec[MarkerGraph] = 
    new JsonValueCodec[MarkerGraph] {
      def decodeValue(in: JsonReader, default: MarkerGraph) = 
        new MarkerGraph(implicitly[JsonValueCodec[MarkerGraphArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: MarkerGraph, out: JsonWriter) = 
        implicitly[JsonValueCodec[MarkerGraphArgs]].encodeValue(x.args, out)
      
      def nullValue: MarkerGraph = null
    }
  
}
private[fxprof] case class MarkerGraphArgs(
  key: String,
  `type`: MarkerGraphType,
  color: Option[GraphColor],
)
private[fxprof] object MarkerGraphArgs {
  implicit val codec: JsonValueCodec[MarkerGraphArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
