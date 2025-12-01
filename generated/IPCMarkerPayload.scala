package fxprof

class IPCMarkerPayload private (args: IPCMarkerPayloadArgs) {
  def `type`: IPCMarkerPayload_Type.type = args.`type`
  def startTime: Milliseconds = args.startTime
  def endTime: Milliseconds = args.endTime
  def otherPid: Pid = args.otherPid
  def messageType: String = args.messageType
  def messageSeqno: Double = args.messageSeqno
  def side: IPCMarkerPayload_Side = args.side
  def direction: IPCMarkerPayload_Direction = args.direction
  def phase: Option[IPCMarkerPayload_Phase] = args.phase
  def sync: Boolean = args.sync
  def threadId: Option[Double] = args.threadId
  def sendStartTime: Option[Milliseconds] = args.sendStartTime
  def sendEndTime: Option[Milliseconds] = args.sendEndTime
  def recvEndTime: Option[Milliseconds] = args.recvEndTime
  def sendTid: Option[Tid] = args.sendTid
  def recvTid: Option[Tid] = args.recvTid
  def niceDirection: Option[String] = args.niceDirection

  def `withtype`(value: IPCMarkerPayload_Type.type): IPCMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withStartTime(value: Milliseconds): IPCMarkerPayload =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Milliseconds): IPCMarkerPayload =
    copy(_.copy(endTime = value))
  
  def withOtherPid(value: Pid): IPCMarkerPayload =
    copy(_.copy(otherPid = value))
  
  def withMessageType(value: String): IPCMarkerPayload =
    copy(_.copy(messageType = value))
  
  def withMessageSeqno(value: Double): IPCMarkerPayload =
    copy(_.copy(messageSeqno = value))
  
  def withSide(value: IPCMarkerPayload_Side): IPCMarkerPayload =
    copy(_.copy(side = value))
  
  def withDirection(value: IPCMarkerPayload_Direction): IPCMarkerPayload =
    copy(_.copy(direction = value))
  
  def withPhase(value: Option[IPCMarkerPayload_Phase]): IPCMarkerPayload =
    copy(_.copy(phase = value))
  
  def withSync(value: Boolean): IPCMarkerPayload =
    copy(_.copy(sync = value))
  
  def withThreadId(value: Option[Double]): IPCMarkerPayload =
    copy(_.copy(threadId = value))
  
  def withSendStartTime(value: Option[Milliseconds]): IPCMarkerPayload =
    copy(_.copy(sendStartTime = value))
  
  def withSendEndTime(value: Option[Milliseconds]): IPCMarkerPayload =
    copy(_.copy(sendEndTime = value))
  
  def withRecvEndTime(value: Option[Milliseconds]): IPCMarkerPayload =
    copy(_.copy(recvEndTime = value))
  
  def withSendTid(value: Option[Tid]): IPCMarkerPayload =
    copy(_.copy(sendTid = value))
  
  def withRecvTid(value: Option[Tid]): IPCMarkerPayload =
    copy(_.copy(recvTid = value))
  
  def withNiceDirection(value: Option[String]): IPCMarkerPayload =
    copy(_.copy(niceDirection = value))
  

  private def copy(f: IPCMarkerPayloadArgs => IPCMarkerPayloadArgs) = 
    new IPCMarkerPayload(f(args))
  
}

object IPCMarkerPayload {
  def apply(
    `type`: IPCMarkerPayload_Type.type,
    startTime: Milliseconds,
    endTime: Milliseconds,
    otherPid: Pid,
    messageType: String,
    messageSeqno: Double,
    side: IPCMarkerPayload_Side,
    direction: IPCMarkerPayload_Direction,
    sync: Boolean,
  ): IPCMarkerPayload = 
    new IPCMarkerPayload(IPCMarkerPayloadArgs(
      `type` = `type`,
      startTime = startTime,
      endTime = endTime,
      otherPid = otherPid,
      messageType = messageType,
      messageSeqno = messageSeqno,
      side = side,
      direction = direction,
      sync = sync,
    ))
}
private[fxprof] case class IPCMarkerPayloadArgs(
  `type`: IPCMarkerPayload_Type.type,
  startTime: Milliseconds,
  endTime: Milliseconds,
  otherPid: Pid,
  messageType: String,
  messageSeqno: Double,
  side: IPCMarkerPayload_Side,
  direction: IPCMarkerPayload_Direction,
  phase: Option[IPCMarkerPayload_Phase] = None,
  sync: Boolean,
  threadId: Option[Double] = None,
  sendStartTime: Option[Milliseconds] = None,
  sendEndTime: Option[Milliseconds] = None,
  recvEndTime: Option[Milliseconds] = None,
  sendTid: Option[Tid] = None,
  recvTid: Option[Tid] = None,
  niceDirection: Option[String] = None,
)
