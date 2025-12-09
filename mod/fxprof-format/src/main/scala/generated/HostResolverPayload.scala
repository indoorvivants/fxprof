package fxprof

class HostResolverPayload private (private[fxprof] val args: HostResolverPayloadArgs) {
  def `type`: HostResolverPayload_Type.type = args.`type`

  def host: String = args.host

  def originSuffix: String = args.originSuffix

  def flags: String = args.flags


  def `withtype`(value: HostResolverPayload_Type.type): HostResolverPayload =
    copy(_.copy(`type` = value))
  
  def withHost(value: String): HostResolverPayload =
    copy(_.copy(host = value))
  
  def withOriginSuffix(value: String): HostResolverPayload =
    copy(_.copy(originSuffix = value))
  
  def withFlags(value: String): HostResolverPayload =
    copy(_.copy(flags = value))
  

  private def copy(f: HostResolverPayloadArgs => HostResolverPayloadArgs) = 
    new HostResolverPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[HostResolverPayload] && o.asInstanceOf[HostResolverPayload].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object HostResolverPayload {
  /** Construct a [[HostResolverPayload]]
      @param type
      @param host
      @param originSuffix
      @param flags
    */
  def apply(
    `type`: HostResolverPayload_Type.type,
    host: String,
    originSuffix: String,
    flags: String,
  ): HostResolverPayload = 
    new HostResolverPayload(HostResolverPayloadArgs(
      `type` = `type`,
      host = host,
      originSuffix = originSuffix,
      flags = flags,
    ))
  implicit val codec: JsonValueCodec[HostResolverPayload] = 
    new JsonValueCodec[HostResolverPayload] {
      def decodeValue(in: JsonReader, default: HostResolverPayload) = 
        new HostResolverPayload(implicitly[JsonValueCodec[HostResolverPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: HostResolverPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[HostResolverPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: HostResolverPayload = null
    }
  
}
private[fxprof] case class HostResolverPayloadArgs(
  `type`: HostResolverPayload_Type.type,
  host: String,
  originSuffix: String,
  flags: String,
)
private[fxprof] object HostResolverPayloadArgs {
  implicit val codec: JsonValueCodec[HostResolverPayloadArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
