package fxprof

/** Measurement for how long draw calls take for the compositor.
  */
class GPUMarkerPayload private (
    private[fxprof] val args: GPUMarkerPayloadArgs
) {
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

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object GPUMarkerPayload {

  /** Construct a [[GPUMarkerPayload]]
    * @param type
    * @param cpustart
    * @param cpuend
    * @param gpustart
    * @param gpuend
    */
  def apply(
      `type`: GPUMarkerPayload_Type.type,
      cpustart: Milliseconds,
      cpuend: Milliseconds,
      gpustart: Milliseconds,
      gpuend: Milliseconds
  ): GPUMarkerPayload =
    new GPUMarkerPayload(
      GPUMarkerPayloadArgs(
        `type` = `type`,
        cpustart = cpustart,
        cpuend = cpuend,
        gpustart = gpustart,
        gpuend = gpuend
      )
    )
  given JsonValueCodec[GPUMarkerPayload] =
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: GPUMarkerPayload) =
        new GPUMarkerPayload(
          summon[JsonValueCodec[GPUMarkerPayloadArgs]]
            .decodeValue(in, default.args)
        )

      def encodeValue(x: GPUMarkerPayload, out: JsonWriter) =
        summon[JsonValueCodec[GPUMarkerPayloadArgs]].encodeValue(x.args, out)

      def nullValue: GPUMarkerPayload = null
    }

}
private[fxprof] case class GPUMarkerPayloadArgs(
    `type`: GPUMarkerPayload_Type.type,
    cpustart: Milliseconds,
    cpuend: Milliseconds,
    gpustart: Milliseconds,
    gpuend: Milliseconds
)
private[fxprof] object GPUMarkerPayloadArgs {
  given JsonValueCodec[GPUMarkerPayloadArgs] = JsonCodecMaker.make
}
