package fxprof

class StyleMarkerPayload private (private[fxprof] val args: StyleMarkerPayloadArgs) {
  def `type`: StyleMarkerPayload_Type.type = args.`type`

  def category: StyleMarkerPayload_Category.type = args.category

  def cause: Option[CauseBacktrace] = args.cause

  /** Counts
    */
  def elementsTraversed: Double = args.elementsTraversed

  def elementsStyled: Double = args.elementsStyled

  def elementsMatched: Double = args.elementsMatched

  def stylesShared: Double = args.stylesShared

  def stylesReused: Double = args.stylesReused


  def `withtype`(value: StyleMarkerPayload_Type.type): StyleMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCategory(value: StyleMarkerPayload_Category.type): StyleMarkerPayload =
    copy(_.copy(category = value))
  
  def withCause(value: Option[CauseBacktrace]): StyleMarkerPayload =
    copy(_.copy(cause = value))
  
  /** Setter for [[elementsTraversed]] field

    * Counts
    */
  def withElementsTraversed(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsTraversed = value))
  
  def withElementsStyled(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsStyled = value))
  
  def withElementsMatched(value: Double): StyleMarkerPayload =
    copy(_.copy(elementsMatched = value))
  
  def withStylesShared(value: Double): StyleMarkerPayload =
    copy(_.copy(stylesShared = value))
  
  def withStylesReused(value: Double): StyleMarkerPayload =
    copy(_.copy(stylesReused = value))
  

  private def copy(f: StyleMarkerPayloadArgs => StyleMarkerPayloadArgs) = 
    new StyleMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object StyleMarkerPayload {
  /** Construct a [[StyleMarkerPayload]]
      @param type
      @param category
      @param elementsTraversedCounts
      @param elementsStyled
      @param elementsMatched
      @param stylesShared
      @param stylesReused
    */
  def apply(
    `type`: StyleMarkerPayload_Type.type,
    category: StyleMarkerPayload_Category.type,
    elementsTraversed: Double,
    elementsStyled: Double,
    elementsMatched: Double,
    stylesShared: Double,
    stylesReused: Double,
  ): StyleMarkerPayload = 
    new StyleMarkerPayload(StyleMarkerPayloadArgs(
      `type` = `type`,
      category = category,
      cause = None,
      elementsTraversed = elementsTraversed,
      elementsStyled = elementsStyled,
      elementsMatched = elementsMatched,
      stylesShared = stylesShared,
      stylesReused = stylesReused,
    ))
  given JsonValueCodec[StyleMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: StyleMarkerPayload) = 
        new StyleMarkerPayload(summon[JsonValueCodec[StyleMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: StyleMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[StyleMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: StyleMarkerPayload = null
    }
  
}
private[fxprof] case class StyleMarkerPayloadArgs(
  `type`: StyleMarkerPayload_Type.type,
  category: StyleMarkerPayload_Category.type,
  cause: Option[CauseBacktrace],
  elementsTraversed: Double,
  elementsStyled: Double,
  elementsMatched: Double,
  stylesShared: Double,
  stylesReused: Double,
)
private[fxprof] object StyleMarkerPayloadArgs {
  given ConfiguredJsonValueCodec[StyleMarkerPayloadArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(false).withTransientNone(false))
  
}
