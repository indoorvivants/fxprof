package fxprof

class Category private (private[fxprof] val args: CategoryArgs) {
  def name: String = args.name
  def color: CategoryColor = args.color
  def subcategories: Vector[String] = args.subcategories

  def withName(value: String): Category =
    copy(_.copy(name = value))
  
  def withColor(value: CategoryColor): Category =
    copy(_.copy(color = value))
  
  def withSubcategories(value: Vector[String]): Category =
    copy(_.copy(subcategories = value))
  

  private def copy(f: CategoryArgs => CategoryArgs) = 
    new Category(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Category {
  def apply(
    name: String,
    color: CategoryColor,
  ): Category = 
    new Category(CategoryArgs(
      name = name,
      color = color,
      subcategories = Vector.empty,
    ))
  given JsonValueCodec[Category] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: Category) = 
        new Category(summon[JsonValueCodec[CategoryArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: Category, out: JsonWriter) = 
        summon[JsonValueCodec[CategoryArgs]].encodeValue(x.args, out)
      
      def nullValue: Category = null
    }
  
}
private[fxprof] case class CategoryArgs(
  name: String,
  color: CategoryColor,
  subcategories: Vector[String],
)
private[fxprof] object CategoryArgs {
  given JsonValueCodec[CategoryArgs] = JsonCodecMaker.make
}
