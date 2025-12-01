package fxprof

class Category private (args: CategoryArgs) {
  def name: String = args.name
  def color: CategoryColor = args.color
  def subcategories: Array[String] = args.subcategories

  def withName(value: String): Category =
    copy(_.copy(name = value))
  
  def withColor(value: CategoryColor): Category =
    copy(_.copy(color = value))
  
  def withSubcategories(value: Array[String]): Category =
    copy(_.copy(subcategories = value))
  

  private def copy(f: CategoryArgs => CategoryArgs) = 
    new Category(f(args))
  
}

private[fxprof] case class CategoryArgs(
  name: String,
  color: CategoryColor,
  subcategories: Array[String],
)
