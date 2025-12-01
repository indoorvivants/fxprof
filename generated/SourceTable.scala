package fxprof

class SourceTable private (args: SourceTableArgs) {
  def length: Double = args.length
  def uuid: Array[Option[String]] = args.uuid
  def filename: Array[IndexIntoStringTable] = args.filename

  def withLength(value: Double): SourceTable =
    copy(_.copy(length = value))
  
  def withUuid(value: Array[Option[String]]): SourceTable =
    copy(_.copy(uuid = value))
  
  def withFilename(value: Array[IndexIntoStringTable]): SourceTable =
    copy(_.copy(filename = value))
  

  private def copy(f: SourceTableArgs => SourceTableArgs) = 
    new SourceTable(f(args))
  
}

private[fxprof] case class SourceTableArgs(
  length: Double,
  uuid: Array[Option[String]],
  filename: Array[IndexIntoStringTable],
)
