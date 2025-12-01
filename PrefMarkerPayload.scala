package fxprof

class PrefMarkerPayload private (args: PrefMarkerPayloadArgs) {
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

private[fxprof] case class PrefMarkerPayloadArgs(
  `type`: PrefMarkerPayload_Type.type,
  prefAccessTime: Milliseconds,
  prefName: String,
  prefKind: String,
  prefType: String,
  prefValue: String,
)
