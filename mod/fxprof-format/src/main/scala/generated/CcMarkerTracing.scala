package fxprof

class CcMarkerTracing private (private[fxprof] val args: CcMarkerTracingArgs) {
  def `type`: CcMarkerTracing_Type.type = args.`type`

  def category: CcMarkerTracing_Category.type = args.category

  def first: Option[String] = args.first

  def desc: Option[String] = args.desc

  def second: Option[String] = args.second


  def `withtype`(value: CcMarkerTracing_Type.type): CcMarkerTracing =
    copy(_.copy(`type` = value))
  
  def withCategory(value: CcMarkerTracing_Category.type): CcMarkerTracing =
    copy(_.copy(category = value))
  
  def withFirst(value: Option[String]): CcMarkerTracing =
    copy(_.copy(first = value))
  
  def withDesc(value: Option[String]): CcMarkerTracing =
    copy(_.copy(desc = value))
  
  def withSecond(value: Option[String]): CcMarkerTracing =
    copy(_.copy(second = value))
  

  private def copy(f: CcMarkerTracingArgs => CcMarkerTracingArgs) = 
    new CcMarkerTracing(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[CcMarkerTracing] && o.asInstanceOf[CcMarkerTracing].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object CcMarkerTracing {
  /** Construct a [[CcMarkerTracing]]
      @param type
      @param category
    */
  def apply(
    `type`: CcMarkerTracing_Type.type,
    category: CcMarkerTracing_Category.type,
  ): CcMarkerTracing = 
    new CcMarkerTracing(CcMarkerTracingArgs(
      `type` = `type`,
      category = category,
      first = None,
      desc = None,
      second = None,
    ))
  implicit val codec: JsonValueCodec[CcMarkerTracing] = 
    new JsonValueCodec[CcMarkerTracing] {
      def decodeValue(in: JsonReader, default: CcMarkerTracing) = 
        new CcMarkerTracing(implicitly[JsonValueCodec[CcMarkerTracingArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: CcMarkerTracing, out: JsonWriter) = 
        implicitly[JsonValueCodec[CcMarkerTracingArgs]].encodeValue(x.args, out)
      
      def nullValue: CcMarkerTracing = null
    }
  
}
private[fxprof] case class CcMarkerTracingArgs(
  `type`: CcMarkerTracing_Type.type,
  category: CcMarkerTracing_Category.type,
  first: Option[String],
  desc: Option[String],
  second: Option[String],
)
private[fxprof] object CcMarkerTracingArgs {
  implicit val codec: JsonValueCodec[CcMarkerTracingArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
