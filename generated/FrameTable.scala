package fxprof

class FrameTable private (private[fxprof] val args: FrameTableArgs) {
  def address: Vector[FrameTable_Address] = args.address
  def inlineDepth: Vector[Double] = args.inlineDepth
  def category: Vector[Option[IndexIntoCategoryList]] = args.category
  def subcategory: Vector[Option[IndexIntoSubcategoryListForCategory]] = args.subcategory
  def func: Vector[IndexIntoFuncTable] = args.func
  def nativeSymbol: Vector[Option[IndexIntoNativeSymbolTable]] = args.nativeSymbol
  def innerWindowID: Vector[Option[InnerWindowID]] = args.innerWindowID
  def line: Vector[Option[Double]] = args.line
  def column: Vector[Option[Double]] = args.column
  def length: Double = args.length

  def withAddress(value: Vector[FrameTable_Address]): FrameTable =
    copy(_.copy(address = value))
  
  def withInlineDepth(value: Vector[Double]): FrameTable =
    copy(_.copy(inlineDepth = value))
  
  def withCategory(value: Vector[Option[IndexIntoCategoryList]]): FrameTable =
    copy(_.copy(category = value))
  
  def withSubcategory(value: Vector[Option[IndexIntoSubcategoryListForCategory]]): FrameTable =
    copy(_.copy(subcategory = value))
  
  def withFunc(value: Vector[IndexIntoFuncTable]): FrameTable =
    copy(_.copy(func = value))
  
  def withNativeSymbol(value: Vector[Option[IndexIntoNativeSymbolTable]]): FrameTable =
    copy(_.copy(nativeSymbol = value))
  
  def withInnerWindowID(value: Vector[Option[InnerWindowID]]): FrameTable =
    copy(_.copy(innerWindowID = value))
  
  def withLine(value: Vector[Option[Double]]): FrameTable =
    copy(_.copy(line = value))
  
  def withColumn(value: Vector[Option[Double]]): FrameTable =
    copy(_.copy(column = value))
  
  def withLength(value: Double): FrameTable =
    copy(_.copy(length = value))
  

  private def copy(f: FrameTableArgs => FrameTableArgs) = 
    new FrameTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object FrameTable {
  def apply(
    length: Double,
  ): FrameTable = 
    new FrameTable(FrameTableArgs(
      address = Vector.empty,
      inlineDepth = Vector.empty,
      category = Vector.empty,
      subcategory = Vector.empty,
      func = Vector.empty,
      nativeSymbol = Vector.empty,
      innerWindowID = Vector.empty,
      line = Vector.empty,
      column = Vector.empty,
      length = length,
    ))
  given JsonValueCodec[FrameTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: FrameTable) = 
        new FrameTable(summon[JsonValueCodec[FrameTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: FrameTable, out: JsonWriter) = 
        summon[JsonValueCodec[FrameTableArgs]].encodeValue(x.args, out)
      
      def nullValue: FrameTable = null
    }
  
}
private[fxprof] case class FrameTableArgs(
  address: Vector[FrameTable_Address],
  inlineDepth: Vector[Double],
  category: Vector[Option[IndexIntoCategoryList]],
  subcategory: Vector[Option[IndexIntoSubcategoryListForCategory]],
  func: Vector[IndexIntoFuncTable],
  nativeSymbol: Vector[Option[IndexIntoNativeSymbolTable]],
  innerWindowID: Vector[Option[InnerWindowID]],
  line: Vector[Option[Double]],
  column: Vector[Option[Double]],
  length: Double,
)
private[fxprof] object FrameTableArgs {
  given JsonValueCodec[FrameTableArgs] = JsonCodecMaker.make
}
