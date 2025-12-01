package fxprof

class FileIoPayload private (args: FileIoPayloadArgs) {
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
}
private[fxprof] case class FileIoPayloadArgs(
  `type`: FileIoPayload_Type.type,
  cause: Option[CauseBacktrace] = None,
  source: String,
  operation: String,
  filename: Option[String] = None,
  threadId: Option[Double] = None,
)
