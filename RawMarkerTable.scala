package fxprof

class RawMarkerTable private (args: RawMarkerTableArgs) {
  def data: Array[Option[MarkerPayload]] = args.data
  def name: Array[IndexIntoStringTable] = args.name
  def startTime: Array[Option[Double]] = args.startTime
  def endTime: Array[Option[Double]] = args.endTime
  def phase: Array[MarkerPhase] = args.phase
  def category: Array[IndexIntoCategoryList] = args.category
  def threadId: Option[Array[Option[Tid]]] = args.threadId
  def length: Double = args.length

  def withData(value: Array[Option[MarkerPayload]]): RawMarkerTable =
    copy(_.copy(data = value))

  def withName(value: Array[IndexIntoStringTable]): RawMarkerTable =
    copy(_.copy(name = value))

  def withStartTime(value: Array[Option[Double]]): RawMarkerTable =
    copy(_.copy(startTime = value))

  def withEndTime(value: Array[Option[Double]]): RawMarkerTable =
    copy(_.copy(endTime = value))

  def withPhase(value: Array[MarkerPhase]): RawMarkerTable =
    copy(_.copy(phase = value))

  def withCategory(value: Array[IndexIntoCategoryList]): RawMarkerTable =
    copy(_.copy(category = value))

  def withThreadId(value: Option[Array[Option[Tid]]]): RawMarkerTable =
    copy(_.copy(threadId = value))

  def withLength(value: Double): RawMarkerTable =
    copy(_.copy(length = value))

  private def copy(f: RawMarkerTableArgs => RawMarkerTableArgs) =
    new RawMarkerTable(f(args))

}

private[fxprof] case class RawMarkerTableArgs(
    data: Array[Option[MarkerPayload]],
    name: Array[IndexIntoStringTable],
    startTime: Array[Option[Double]],
    endTime: Array[Option[Double]],
    phase: Array[MarkerPhase],
    category: Array[IndexIntoCategoryList],
    threadId: Option[Array[Option[Tid]]],
    length: Double
)
