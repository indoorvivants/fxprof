package fxprof

class FileIoPayload private (private[fxprof] val args: FileIoPayloadArgs) {
  def `type`: FileIoPayload_Type.type = args.`type`
  def cause: Option[CauseBacktrace] = args.cause
  def source: String = args.source
  def operation: String = args.operation
  def filename: Option[String] = args.filename
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
  
  def withThreadId(value: Option[Double]): FileIoPayload =
    copy(_.copy(threadId = value))
  

  private def copy(f: FileIoPayloadArgs => FileIoPayloadArgs) = 
    new FileIoPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object FileIoPayload {
  def apply(
    `type`: FileIoPayload_Type.type,
    source: String,
    operation: String,
  ): FileIoPayload = 
    new FileIoPayload(FileIoPayloadArgs(
      `type` = `type`,
      source = source,
      operation = operation,
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
  cause: Option[CauseBacktrace] = None,
  source: String,
  operation: String,
  filename: Option[String] = None,
  threadId: Option[Double] = None,
)
private[fxprof] object FileIoPayloadArgs {
  given JsonValueCodec[FileIoPayloadArgs] = JsonCodecMaker.make
}
