package fxprof

class GPUMarkerPayload private (args: GPUMarkerPayloadArgs) {
  def `type`: GPUMarkerPayload_Type.type = args.`type`
  def cpustart: Milliseconds = args.cpustart
  def cpuend: Milliseconds = args.cpuend
  def gpustart: Milliseconds = args.gpustart
  def gpuend: Milliseconds = args.gpuend

  def `withtype`(value: GPUMarkerPayload_Type.type): GPUMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withCpustart(value: Milliseconds): GPUMarkerPayload =
    copy(_.copy(cpustart = value))
  
  def withCpuend(value: Milliseconds): GPUMarkerPayload =
    copy(_.copy(cpuend = value))
  
  def withGpustart(value: Milliseconds): GPUMarkerPayload =
    copy(_.copy(gpustart = value))
  
  def withGpuend(value: Milliseconds): GPUMarkerPayload =
    copy(_.copy(gpuend = value))
  

  private def copy(f: GPUMarkerPayloadArgs => GPUMarkerPayloadArgs) = 
    new GPUMarkerPayload(f(args))
  
}

private[fxprof] case class GPUMarkerPayloadArgs(
  `type`: GPUMarkerPayload_Type.type,
  cpustart: Milliseconds,
  cpuend: Milliseconds,
  gpustart: Milliseconds,
  gpuend: Milliseconds,
)
