package fxprof

/**
  * The payload for the UserTimings API. These are added through performance.measure()
  * and performance.mark(). https://developer.mozilla.org/en-US/docs/Web/API/Performance
  */
class UserTimingMarkerPayload private (private[fxprof] val args: UserTimingMarkerPayloadArgs) {
  def `type`: UserTimingMarkerPayload_Type.type = args.`type`

  def name: String = args.name

  def entryType: UserTimingMarkerPayload_EntryType = args.entryType


  def `withtype`(value: UserTimingMarkerPayload_Type.type): UserTimingMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withName(value: String): UserTimingMarkerPayload =
    copy(_.copy(name = value))
  
  def withEntryType(value: UserTimingMarkerPayload_EntryType): UserTimingMarkerPayload =
    copy(_.copy(entryType = value))
  

  private def copy(f: UserTimingMarkerPayloadArgs => UserTimingMarkerPayloadArgs) = 
    new UserTimingMarkerPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[UserTimingMarkerPayload] && o.asInstanceOf[UserTimingMarkerPayload].args.equals(this.args)
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object UserTimingMarkerPayload {
  /** Construct a [[UserTimingMarkerPayload]]
      @param type
      @param name
      @param entryType
    */
  def apply(
    `type`: UserTimingMarkerPayload_Type.type,
    name: String,
    entryType: UserTimingMarkerPayload_EntryType,
  ): UserTimingMarkerPayload = 
    new UserTimingMarkerPayload(UserTimingMarkerPayloadArgs(
      `type` = `type`,
      name = name,
      entryType = entryType,
    ))
  implicit val codec: JsonValueCodec[UserTimingMarkerPayload] = 
    new JsonValueCodec[UserTimingMarkerPayload] {
      def decodeValue(in: JsonReader, default: UserTimingMarkerPayload) = 
        new UserTimingMarkerPayload(implicitly[JsonValueCodec[UserTimingMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: UserTimingMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[UserTimingMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: UserTimingMarkerPayload = null
    }
  
}
private[fxprof] case class UserTimingMarkerPayloadArgs(
  `type`: UserTimingMarkerPayload_Type.type,
  name: String,
  entryType: UserTimingMarkerPayload_EntryType,
)
private[fxprof] object UserTimingMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[UserTimingMarkerPayloadArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
