package fxprof

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
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object UserTimingMarkerPayload {
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
  given JsonValueCodec[UserTimingMarkerPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: UserTimingMarkerPayload) = 
        new UserTimingMarkerPayload(summon[JsonValueCodec[UserTimingMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: UserTimingMarkerPayload, out: JsonWriter) = 
        summon[JsonValueCodec[UserTimingMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: UserTimingMarkerPayload = null
    }
  
}
private[fxprof] case class UserTimingMarkerPayloadArgs(
  `type`: UserTimingMarkerPayload_Type.type,
  name: String,
  entryType: UserTimingMarkerPayload_EntryType,
)
private[fxprof] object UserTimingMarkerPayloadArgs {
  given JsonValueCodec[UserTimingMarkerPayloadArgs] = JsonCodecMaker.make
}
