package fxprof

class FrameTable private (args: FrameTableArgs) {
  def address: Array[FrameTable_Address] = args.address
  def inlineDepth: Array[Double] = args.inlineDepth
  def category: Array[Option[IndexIntoCategoryList]] = args.category
  def subcategory: Array[Option[IndexIntoSubcategoryListForCategory]] = args.subcategory
  def func: Array[IndexIntoFuncTable] = args.func
  def nativeSymbol: Array[Option[IndexIntoNativeSymbolTable]] = args.nativeSymbol
  def innerWindowID: Array[Option[InnerWindowID]] = args.innerWindowID
  def line: Array[Option[Double]] = args.line
  def column: Array[Option[Double]] = args.column
  def length: Double = args.length

  def withAddress(value: Array[FrameTable_Address]): FrameTable =
    copy(_.copy(address = value))
  
  def withInlineDepth(value: Array[Double]): FrameTable =
    copy(_.copy(inlineDepth = value))
  
  def withCategory(value: Array[Option[IndexIntoCategoryList]]): FrameTable =
    copy(_.copy(category = value))
  
  def withSubcategory(value: Array[Option[IndexIntoSubcategoryListForCategory]]): FrameTable =
    copy(_.copy(subcategory = value))
  
  def withFunc(value: Array[IndexIntoFuncTable]): FrameTable =
    copy(_.copy(func = value))
  
  def withNativeSymbol(value: Array[Option[IndexIntoNativeSymbolTable]]): FrameTable =
    copy(_.copy(nativeSymbol = value))
  
  def withInnerWindowID(value: Array[Option[InnerWindowID]]): FrameTable =
    copy(_.copy(innerWindowID = value))
  
  def withLine(value: Array[Option[Double]]): FrameTable =
    copy(_.copy(line = value))
  
  def withColumn(value: Array[Option[Double]]): FrameTable =
    copy(_.copy(column = value))
  
  def withLength(value: Double): FrameTable =
    copy(_.copy(length = value))
  

  private def copy(f: FrameTableArgs => FrameTableArgs) = 
    new FrameTable(f(args))
  
}

private[fxprof] case class FrameTableArgs(
  address: Array[FrameTable_Address],
  inlineDepth: Array[Double],
  category: Array[Option[IndexIntoCategoryList]],
  subcategory: Array[Option[IndexIntoSubcategoryListForCategory]],
  func: Array[IndexIntoFuncTable],
  nativeSymbol: Array[Option[IndexIntoNativeSymbolTable]],
  innerWindowID: Array[Option[InnerWindowID]],
  line: Array[Option[Double]],
  column: Array[Option[Double]],
  length: Double,
)
