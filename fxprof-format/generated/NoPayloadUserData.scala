package fxprof

class NoPayloadUserData private (private[fxprof] val args: NoPayloadUserDataArgs) {
  def `type`: NoPayloadUserData_Type.type = args.`type`

  def innerWindowID: Option[Double] = args.innerWindowID


  def `withtype`(value: NoPayloadUserData_Type.type): NoPayloadUserData =
    copy(_.copy(`type` = value))
  
  def withInnerWindowID(value: Option[Double]): NoPayloadUserData =
    copy(_.copy(innerWindowID = value))
  

  private def copy(f: NoPayloadUserDataArgs => NoPayloadUserDataArgs) = 
    new NoPayloadUserData(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object NoPayloadUserData {
  /** Construct a [[NoPayloadUserData]]
      @param type
    */
  def apply(
    `type`: NoPayloadUserData_Type.type,
  ): NoPayloadUserData = 
    new NoPayloadUserData(NoPayloadUserDataArgs(
      `type` = `type`,
      innerWindowID = None,
    ))
  given JsonValueCodec[NoPayloadUserData] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: NoPayloadUserData) = 
        new NoPayloadUserData(summon[JsonValueCodec[NoPayloadUserDataArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NoPayloadUserData, out: JsonWriter) = 
        summon[JsonValueCodec[NoPayloadUserDataArgs]].encodeValue(x.args, out)
      
      def nullValue: NoPayloadUserData = null
    }
  
}
private[fxprof] case class NoPayloadUserDataArgs(
  `type`: NoPayloadUserData_Type.type,
  innerWindowID: Option[Double],
)
private[fxprof] object NoPayloadUserDataArgs {
  given ConfiguredJsonValueCodec[NoPayloadUserDataArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(false).withTransientNone(false))
  
}
