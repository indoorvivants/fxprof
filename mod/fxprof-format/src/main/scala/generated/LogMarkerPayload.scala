package fxprof

/**
  * Gecko includes rich log information. This marker payload is used to mirror that
  * log information in the profile.
  */
class LogMarkerPayload private (private[fxprof] val args: LogMarkerPayloadArgs) {
  def `type`: LogMarkerPayload_Type.type = args.`type`

  def name: String = args.name

  def module: String = args.module


  def `withtype`(value: LogMarkerPayload_Type.type): LogMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withName(value: String): LogMarkerPayload =
    copy(_.copy(name = value))
  
  def withModule(value: String): LogMarkerPayload =
    copy(_.copy(module = value))
  

  private def copy(f: LogMarkerPayloadArgs => LogMarkerPayloadArgs) = 
    new LogMarkerPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[LogMarkerPayload] && o.asInstanceOf[LogMarkerPayload].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object LogMarkerPayload {
  /** Construct a [[LogMarkerPayload]]
      @param type
      @param name
      @param module
    */
  def apply(
    `type`: LogMarkerPayload_Type.type,
    name: String,
    module: String,
  ): LogMarkerPayload = 
    new LogMarkerPayload(LogMarkerPayloadArgs(
      `type` = `type`,
      name = name,
      module = module,
    ))
  implicit val codec: JsonValueCodec[LogMarkerPayload] = 
    new JsonValueCodec[LogMarkerPayload] {
      def decodeValue(in: JsonReader, default: LogMarkerPayload) = 
        new LogMarkerPayload(implicitly[JsonValueCodec[LogMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: LogMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[LogMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: LogMarkerPayload = null
    }
  
}
private[fxprof] case class LogMarkerPayloadArgs(
  `type`: LogMarkerPayload_Type.type,
  name: String,
  module: String,
)
private[fxprof] object LogMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[LogMarkerPayloadArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
