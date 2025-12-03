package fxprof

class PrefMarkerPayload private (private[fxprof] val args: PrefMarkerPayloadArgs) {
  def `type`: PrefMarkerPayload_Type.type = args.`type`

  def prefAccessTime: Milliseconds = args.prefAccessTime

  def prefName: String = args.prefName

  def prefKind: String = args.prefKind

  def prefType: String = args.prefType

  def prefValue: String = args.prefValue


  def `withtype`(value: PrefMarkerPayload_Type.type): PrefMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withPrefAccessTime(value: Milliseconds): PrefMarkerPayload =
    copy(_.copy(prefAccessTime = value))
  
  def withPrefName(value: String): PrefMarkerPayload =
    copy(_.copy(prefName = value))
  
  def withPrefKind(value: String): PrefMarkerPayload =
    copy(_.copy(prefKind = value))
  
  def withPrefType(value: String): PrefMarkerPayload =
    copy(_.copy(prefType = value))
  
  def withPrefValue(value: String): PrefMarkerPayload =
    copy(_.copy(prefValue = value))
  

  private def copy(f: PrefMarkerPayloadArgs => PrefMarkerPayloadArgs) = 
    new PrefMarkerPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object PrefMarkerPayload {
  /** Construct a [[PrefMarkerPayload]]
      @param type
      @param prefAccessTime
      @param prefName
      @param prefKind
      @param prefType
      @param prefValue
    */
  def apply(
    `type`: PrefMarkerPayload_Type.type,
    prefAccessTime: Milliseconds,
    prefName: String,
    prefKind: String,
    prefType: String,
    prefValue: String,
  ): PrefMarkerPayload = 
    new PrefMarkerPayload(PrefMarkerPayloadArgs(
      `type` = `type`,
      prefAccessTime = prefAccessTime,
      prefName = prefName,
      prefKind = prefKind,
      prefType = prefType,
      prefValue = prefValue,
    ))
  given JsonValueCodec[PrefMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: PrefMarkerPayload) = 
        new PrefMarkerPayload(summon[JsonValueCodec[PrefMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: PrefMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[PrefMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: PrefMarkerPayload = null
    }
  
}
private[fxprof] case class PrefMarkerPayloadArgs(
  `type`: PrefMarkerPayload_Type.type,
  prefAccessTime: Milliseconds,
  prefName: String,
  prefKind: String,
  prefType: String,
  prefValue: String,
)
private[fxprof] object PrefMarkerPayloadArgs {
  given ConfiguredJsonValueCodec[PrefMarkerPayloadArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(false).withTransientNone(false))
  
}
