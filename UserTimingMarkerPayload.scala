package fxprof

class UserTimingMarkerPayload private (args: UserTimingMarkerPayloadArgs) {
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

private[fxprof] case class UserTimingMarkerPayloadArgs(
  `type`: UserTimingMarkerPayload_Type.type,
  name: String,
  entryType: UserTimingMarkerPayload_EntryType,
)
