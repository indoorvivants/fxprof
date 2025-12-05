package fxprof

class Category private (private[fxprof] val args: CategoryArgs) {
  /** The category name.
    */
  def name: String = args.name

  /** The category color. Must be picked from the CategoryColor list. At least one
    * category with color "grey" must be present in the category list.
    */
  def color: CategoryColor = args.color

  /** The list of subcategories. Must always have at least one element; subcategory
    * zero must be the "Other" subcategory and is used to refer to the category itself.
    */
  def subcategories: Vector[String] = args.subcategories


  /** Setter for [[name]] field

    * The category name.
    */
  def withName(value: String): Category =
    copy(_.copy(name = value))
  
  /** Setter for [[color]] field

    * The category color. Must be picked from the CategoryColor list. At least one
    * category with color "grey" must be present in the category list.
    */
  def withColor(value: CategoryColor): Category =
    copy(_.copy(color = value))
  
  /** Setter for [[subcategories]] field

    * The list of subcategories. Must always have at least one element; subcategory
    * zero must be the "Other" subcategory and is used to refer to the category itself.
    */
  def withSubcategories(value: Vector[String]): Category =
    copy(_.copy(subcategories = value))
  

  private def copy(f: CategoryArgs => CategoryArgs) = 
    new Category(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Category {
  /** Construct a [[Category]]
      @param nameThe category name.
      @param colorThe category color. Must be picked from the CategoryColor list. At least one
                  category with color "grey" must be present in the category list.
    */
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
  given ConfiguredJsonValueCodec[CategoryArgs] = 
    ConfiguredJsonValueCodec.derived(using CodecMakerConfig.withTransientEmpty(true))
  
}
