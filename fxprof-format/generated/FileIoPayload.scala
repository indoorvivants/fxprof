package fxprof

class FileIoPayload private (private[fxprof] val args: FileIoPayloadArgs) {
  def `type`: FileIoPayload_Type.type = args.`type`

  def cause: Option[CauseBacktrace] = args.cause

  def source: String = args.source

  def operation: String = args.operation

  def filename: Option[String] = args.filename

  /** FileIO markers that are happening on the current thread don't have a threadId,
    * but they have threadId field if the markers belong to a different (potentially
    * non-profiled) thread.
    * This field is added on Firefox 78, but this is backwards compatible because
    * previous FileIO markers were also belonging to the threads they are in only.
    * We still don't serialize this field if the marker belongs to the thread they
    * are being captured.
    */
  def threadId: Option[Double] = args.threadId


  def `withtype`(value: FileIoPayload_Type.type): FileIoPayload =
    copy(_.copy(`type` = value))
  
  def withCause(value: Option[CauseBacktrace]): FileIoPayload =
    copy(_.copy(cause = value))
  
  def withSource(value: String): FileIoPayload =
    copy(_.copy(source = value))
  
  def withOperation(value: String): FileIoPayload =
    copy(_.copy(operation = value))
  
  def withFilename(value: Option[String]): FileIoPayload =
    copy(_.copy(filename = value))
  
  /** Setter for [[threadId]] field

    * FileIO markers that are happening on the current thread don't have a threadId,
    * but they have threadId field if the markers belong to a different (potentially
    * non-profiled) thread.
    * This field is added on Firefox 78, but this is backwards compatible because
    * previous FileIO markers were also belonging to the threads they are in only.
    * We still don't serialize this field if the marker belongs to the thread they
    * are being captured.
    */
  def withThreadId(value: Option[Double]): FileIoPayload =
    copy(_.copy(threadId = value))
  

  private def copy(f: FileIoPayloadArgs => FileIoPayloadArgs) = 
    new FileIoPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object FileIoPayload {
  /** Construct a [[FileIoPayload]]
      @param type
      @param source
      @param operation
    */
  def apply(
    `type`: FileIoPayload_Type.type,
    source: String,
    operation: String,
  ): FileIoPayload = 
    new FileIoPayload(FileIoPayloadArgs(
      `type` = `type`,
      cause = None,
      source = source,
      operation = operation,
      filename = None,
      threadId = None,
    ))
  given JsonValueCodec[FileIoPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: FileIoPayload) = 
        new FileIoPayload(summon[JsonValueCodec[FileIoPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: FileIoPayload, out: JsonWriter) = 
        summon[JsonValueCodec[FileIoPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: FileIoPayload = null
    }
  
}
private[fxprof] case class FileIoPayloadArgs(
  `type`: FileIoPayload_Type.type,
  cause: Option[CauseBacktrace],
  source: String,
  operation: String,
  filename: Option[String],
  threadId: Option[Double],
)
private[fxprof] object FileIoPayloadArgs {
  given ConfiguredJsonValueCodec[FileIoPayloadArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
