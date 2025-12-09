package fxprof

class PaintProfilerMarkerTracing private (private[fxprof] val args: PaintProfilerMarkerTracingArgs) {
  def `type`: PaintProfilerMarkerTracing_Type.type = args.`type`

  def category: PaintProfilerMarkerTracing_Category.type = args.category

  def cause: Option[CauseBacktrace] = args.cause


  def `withtype`(value: PaintProfilerMarkerTracing_Type.type): PaintProfilerMarkerTracing =
    copy(_.copy(`type` = value))
  
  def withCategory(value: PaintProfilerMarkerTracing_Category.type): PaintProfilerMarkerTracing =
    copy(_.copy(category = value))
  
  def withCause(value: Option[CauseBacktrace]): PaintProfilerMarkerTracing =
    copy(_.copy(cause = value))
  

  private def copy(f: PaintProfilerMarkerTracingArgs => PaintProfilerMarkerTracingArgs) = 
    new PaintProfilerMarkerTracing(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[PaintProfilerMarkerTracing] && o.asInstanceOf[PaintProfilerMarkerTracing].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object PaintProfilerMarkerTracing {
  /** Construct a [[PaintProfilerMarkerTracing]]
      @param type
      @param category
    */
  def apply(
    `type`: PaintProfilerMarkerTracing_Type.type,
    category: PaintProfilerMarkerTracing_Category.type,
  ): PaintProfilerMarkerTracing = 
    new PaintProfilerMarkerTracing(PaintProfilerMarkerTracingArgs(
      `type` = `type`,
      category = category,
      cause = None,
    ))
  implicit val codec: JsonValueCodec[PaintProfilerMarkerTracing] = 
    new JsonValueCodec[PaintProfilerMarkerTracing] {
      def decodeValue(in: JsonReader, default: PaintProfilerMarkerTracing) = 
        new PaintProfilerMarkerTracing(implicitly[JsonValueCodec[PaintProfilerMarkerTracingArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: PaintProfilerMarkerTracing, out: JsonWriter) = 
        implicitly[JsonValueCodec[PaintProfilerMarkerTracingArgs]].encodeValue(x.args, out)
      
      def nullValue: PaintProfilerMarkerTracing = null
    }
  
}
private[fxprof] case class PaintProfilerMarkerTracingArgs(
  `type`: PaintProfilerMarkerTracing_Type.type,
  category: PaintProfilerMarkerTracing_Category.type,
  cause: Option[CauseBacktrace],
)
private[fxprof] object PaintProfilerMarkerTracingArgs {
  implicit val codec: JsonValueCodec[PaintProfilerMarkerTracingArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
