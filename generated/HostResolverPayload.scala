package fxprof

class HostResolverPayload private (args: HostResolverPayloadArgs) {
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
  
}

object HostResolverPayload {
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
}
private[fxprof] case class HostResolverPayloadArgs(
  `type`: HostResolverPayload_Type.type,
  host: String,
  originSuffix: String,
  flags: String,
)
