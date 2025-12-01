package fxprof

class LogMarkerPayload private (args: LogMarkerPayloadArgs) {
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
  
}

object LogMarkerPayload {
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
}
private[fxprof] case class LogMarkerPayloadArgs(
  `type`: LogMarkerPayload_Type.type,
  name: String,
  module: String,
)
