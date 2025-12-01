package fxprof

class JsAllocationsTable private (args: JsAllocationsTableArgs) {
  def time: Array[Milliseconds] = args.time
  def className: Array[String] = args.className
  def typeName: Array[String] = args.typeName
  def coarseType: Array[String] = args.coarseType
  def weight: Array[Bytes] = args.weight
  def weightType: Option[Any] /* literal string 'bytes' */ = args.weightType
  def inNursery: Array[Boolean] = args.inNursery
  def stack: Array[Option[IndexIntoStackTable]] = args.stack
  def length: Double = args.length

  def withTime(value: Array[Milliseconds]): JsAllocationsTable =
    copy(_.copy(time = value))
  
  def withClassName(value: Array[String]): JsAllocationsTable =
    copy(_.copy(className = value))
  
  def withTypeName(value: Array[String]): JsAllocationsTable =
    copy(_.copy(typeName = value))
  
  def withCoarseType(value: Array[String]): JsAllocationsTable =
    copy(_.copy(coarseType = value))
  
  def withWeight(value: Array[Bytes]): JsAllocationsTable =
    copy(_.copy(weight = value))
  
  def withWeightType(value: Option[Any] /* literal string 'bytes' */): JsAllocationsTable =
    copy(_.copy(weightType = value))
  
  def withInNursery(value: Array[Boolean]): JsAllocationsTable =
    copy(_.copy(inNursery = value))
  
  def withStack(value: Array[Option[IndexIntoStackTable]]): JsAllocationsTable =
    copy(_.copy(stack = value))
  
  def withLength(value: Double): JsAllocationsTable =
    copy(_.copy(length = value))
  

  private def copy(f: JsAllocationsTableArgs => JsAllocationsTableArgs) = 
    new JsAllocationsTable(f(args))
  
}

private[fxprof] case class JsAllocationsTableArgs(
  time: Array[Milliseconds],
  className: Array[String],
  typeName: Array[String],
  coarseType: Array[String],
  weight: Array[Bytes],
  weightType: Option[Any] /* literal string 'bytes' */,
  inNursery: Array[Boolean],
  stack: Array[Option[IndexIntoStackTable]],
  length: Double,
)
