package fxprof

class MediaSampleMarkerPayload private (args: MediaSampleMarkerPayloadArgs) {
  def `type`: MediaSampleMarkerPayload_Type.type = args.`type`
  def sampleStartTimeUs: Microseconds = args.sampleStartTimeUs
  def sampleEndTimeUs: Microseconds = args.sampleEndTimeUs

  def `withtype`(value: MediaSampleMarkerPayload_Type.type): MediaSampleMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withSampleStartTimeUs(value: Microseconds): MediaSampleMarkerPayload =
    copy(_.copy(sampleStartTimeUs = value))
  
  def withSampleEndTimeUs(value: Microseconds): MediaSampleMarkerPayload =
    copy(_.copy(sampleEndTimeUs = value))
  

  private def copy(f: MediaSampleMarkerPayloadArgs => MediaSampleMarkerPayloadArgs) = 
    new MediaSampleMarkerPayload(f(args))
  
}

object MediaSampleMarkerPayload {
  def apply(
    `type`: MediaSampleMarkerPayload_Type.type,
    sampleStartTimeUs: Microseconds,
    sampleEndTimeUs: Microseconds,
  ): MediaSampleMarkerPayload = 
    new MediaSampleMarkerPayload(MediaSampleMarkerPayloadArgs(
      `type` = `type`,
      sampleStartTimeUs = sampleStartTimeUs,
      sampleEndTimeUs = sampleEndTimeUs,
    ))
}
private[fxprof] case class MediaSampleMarkerPayloadArgs(
  `type`: MediaSampleMarkerPayload_Type.type,
  sampleStartTimeUs: Microseconds,
  sampleEndTimeUs: Microseconds,
)
