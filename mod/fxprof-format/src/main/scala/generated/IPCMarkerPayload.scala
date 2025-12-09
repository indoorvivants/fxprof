package fxprof

class IPCMarkerPayload private (private[fxprof] val args: IPCMarkerPayloadArgs) {
  def `type`: IPCMarkerPayload_Type.type = args.`type`

  def startTime: Milliseconds = args.startTime

  def endTime: Milliseconds = args.endTime

  /** otherPid is a string in the processed format.
    */
  def otherPid: Pid = args.otherPid

  def messageType: String = args.messageType

  def messageSeqno: Double = args.messageSeqno

  def side: IPCMarkerPayload_Side = args.side

  def direction: IPCMarkerPayload_Direction = args.direction

  /** Phase is not present in older profiles (in this case the phase is "endpoint").
    */
  def phase: Option[IPCMarkerPayload_Phase] = args.phase

  def sync: Boolean = args.sync

  /** `tid` of the thread that this marker is originated from. It is undefined
    * when the IPC marker is originated from the same thread. Also, this field is
    * added in Firefox 100. It will always be undefined for the older profiles.
    */
  def threadId: Option[Double] = args.threadId

  /** These fields are added in the deriving process from `IPCSharedData`, and
    * correspond to data from all the markers associated with a particular IPC
    * message.
    * We mark these fields as optional because we represent non-derived markers
    * and derived markers with the same type. These fields are always present on
    * derived markers and never present on non-derived markers.
    */
  def sendStartTime: Option[Milliseconds] = args.sendStartTime

  def sendEndTime: Option[Milliseconds] = args.sendEndTime

  def recvEndTime: Option[Milliseconds] = args.recvEndTime

  def sendTid: Option[Tid] = args.sendTid

  def recvTid: Option[Tid] = args.recvTid

  /** This field is a nicely formatted field for the direction.
    */
  def niceDirection: Option[String] = args.niceDirection


  def `withtype`(value: IPCMarkerPayload_Type.type): IPCMarkerPayload =
    copy(_.copy(`type` = value))
  
  def withStartTime(value: Milliseconds): IPCMarkerPayload =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Milliseconds): IPCMarkerPayload =
    copy(_.copy(endTime = value))
  
  /** Setter for [[otherPid]] field

    * otherPid is a string in the processed format.
    */
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
  
  /** Setter for [[phase]] field

    * Phase is not present in older profiles (in this case the phase is "endpoint").
    */
  def withPhase(value: Option[IPCMarkerPayload_Phase]): IPCMarkerPayload =
    copy(_.copy(phase = value))
  
  def withSync(value: Boolean): IPCMarkerPayload =
    copy(_.copy(sync = value))
  
  /** Setter for [[threadId]] field

    * `tid` of the thread that this marker is originated from. It is undefined
    * when the IPC marker is originated from the same thread. Also, this field is
    * added in Firefox 100. It will always be undefined for the older profiles.
    */
  def withThreadId(value: Option[Double]): IPCMarkerPayload =
    copy(_.copy(threadId = value))
  
  /** Setter for [[sendStartTime]] field

    * These fields are added in the deriving process from `IPCSharedData`, and
    * correspond to data from all the markers associated with a particular IPC
    * message.
    * We mark these fields as optional because we represent non-derived markers
    * and derived markers with the same type. These fields are always present on
    * derived markers and never present on non-derived markers.
    */
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
  
  /** Setter for [[niceDirection]] field

    * This field is a nicely formatted field for the direction.
    */
  def withNiceDirection(value: Option[String]): IPCMarkerPayload =
    copy(_.copy(niceDirection = value))
  

  private def copy(f: IPCMarkerPayloadArgs => IPCMarkerPayloadArgs) = 
    new IPCMarkerPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[IPCMarkerPayload] && o.asInstanceOf[IPCMarkerPayload].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object IPCMarkerPayload {
  /** Construct a [[IPCMarkerPayload]]
      @param type
      @param startTime
      @param endTime
      @param otherPidotherPid is a string in the processed format.
      @param messageType
      @param messageSeqno
      @param side
      @param direction
      @param sync
    */
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
      phase = None,
      sync = sync,
      threadId = None,
      sendStartTime = None,
      sendEndTime = None,
      recvEndTime = None,
      sendTid = None,
      recvTid = None,
      niceDirection = None,
    ))
  implicit val codec: JsonValueCodec[IPCMarkerPayload] = 
    new JsonValueCodec[IPCMarkerPayload] {
      def decodeValue(in: JsonReader, default: IPCMarkerPayload) = 
        new IPCMarkerPayload(implicitly[JsonValueCodec[IPCMarkerPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: IPCMarkerPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[IPCMarkerPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: IPCMarkerPayload = null
    }
  
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
  phase: Option[IPCMarkerPayload_Phase],
  sync: Boolean,
  threadId: Option[Double],
  sendStartTime: Option[Milliseconds],
  sendEndTime: Option[Milliseconds],
  recvEndTime: Option[Milliseconds],
  sendTid: Option[Tid],
  recvTid: Option[Tid],
  niceDirection: Option[String],
)
private[fxprof] object IPCMarkerPayloadArgs {
  implicit val codec: JsonValueCodec[IPCMarkerPayloadArgs] = 
    JsonCodecMaker.makeWithRequiredCollectionFields
  
}
