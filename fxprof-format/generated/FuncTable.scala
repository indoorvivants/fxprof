package fxprof

/**
  * The funcTable stores the functions that were called in the profile.
  * These can be native functions (e.g. C / C++ / rust), JavaScript functions, or
  * "label" functions. Multiple frames can have the same function: The frame
  * represents which part of a function was being executed at a given moment, and
  * the function groups all frames that occurred inside that function.
  * Concretely, for native code, each encountered instruction address is a separate
  * frame, and the function groups all instruction addresses which were symbolicated
  * with the same function name.
  * For JS code, each encountered line/column in a JS file is a separate frame, and
  * the function represents an entire JS function which can span multiple lines.
  * *
  * Funcs that are orphaned, i.e. funcs that no frame refers to, do not have
  * meaningful values in their fields. Symbolication will cause many funcs that
  * were created upfront to become orphaned, as the frames that originally referred
  * to them get reassigned to the canonical func for their actual function.
  */
class FuncTable private (private[fxprof] val args: FuncTableArgs) {
  /** The function name.
    */
  def name: Vector[IndexIntoStringTable] = args.name

  /** isJS and relevantForJS describe the function type. Non-JavaScript functions
    * can be marked as "relevant for JS" so that for example DOM API label functions
    * will show up in any JavaScript stack views.
    * It may be worth combining these two fields into one:
    * https://github.com/firefox-devtools/profiler/issues/2543
    */
  def isJS: Vector[Boolean] = args.isJS

  def relevantForJS: Vector[Boolean] = args.relevantForJS

  /** The resource describes "Which bag of code did this function come from?".
    * For JS functions, the resource is of type addon, webhost, otherhost, or url.
    * For native functions, the resource is of type library.
    * For labels and for other unidentified functions, we set the resource to -1.
    */
  def resource: Vector[FuncTable_Resource] = args.resource

  /** These are non-null for JS functions only. The line and column describe the
    * location of the *start* of the JS function. As for the information about which
    * which lines / columns inside the function were actually hit during execution,
    * that information is stored in the frameTable, not in the funcTable.
    */
  def source: Vector[Option[IndexIntoSourceTable]] = args.source

  def lineNumber: Vector[Option[Double]] = args.lineNumber

  def columnNumber: Vector[Option[Double]] = args.columnNumber

  def length: Double = args.length


  /** Setter for [[name]] field

    * The function name.
    */
  def withName(value: Vector[IndexIntoStringTable]): FuncTable =
    copy(_.copy(name = value))
  
  /** Setter for [[isJS]] field

    * isJS and relevantForJS describe the function type. Non-JavaScript functions
    * can be marked as "relevant for JS" so that for example DOM API label functions
    * will show up in any JavaScript stack views.
    * It may be worth combining these two fields into one:
    * https://github.com/firefox-devtools/profiler/issues/2543
    */
  def withIsJS(value: Vector[Boolean]): FuncTable =
    copy(_.copy(isJS = value))
  
  def withRelevantForJS(value: Vector[Boolean]): FuncTable =
    copy(_.copy(relevantForJS = value))
  
  /** Setter for [[resource]] field

    * The resource describes "Which bag of code did this function come from?".
    * For JS functions, the resource is of type addon, webhost, otherhost, or url.
    * For native functions, the resource is of type library.
    * For labels and for other unidentified functions, we set the resource to -1.
    */
  def withResource(value: Vector[FuncTable_Resource]): FuncTable =
    copy(_.copy(resource = value))
  
  /** Setter for [[source]] field

    * These are non-null for JS functions only. The line and column describe the
    * location of the *start* of the JS function. As for the information about which
    * which lines / columns inside the function were actually hit during execution,
    * that information is stored in the frameTable, not in the funcTable.
    */
  def withSource(value: Vector[Option[IndexIntoSourceTable]]): FuncTable =
    copy(_.copy(source = value))
  
  def withLineNumber(value: Vector[Option[Double]]): FuncTable =
    copy(_.copy(lineNumber = value))
  
  def withColumnNumber(value: Vector[Option[Double]]): FuncTable =
    copy(_.copy(columnNumber = value))
  
  def withLength(value: Double): FuncTable =
    copy(_.copy(length = value))
  

  private def copy(f: FuncTableArgs => FuncTableArgs) = 
    new FuncTable(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[FuncTable] && o.asInstanceOf[FuncTable].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object FuncTable {
  /** Construct a [[FuncTable]]
      @param length
    */
  def apply(
    length: Double,
  ): FuncTable = 
    new FuncTable(FuncTableArgs(
      name = Vector.empty,
      isJS = Vector.empty,
      relevantForJS = Vector.empty,
      resource = Vector.empty,
      source = Vector.empty,
      lineNumber = Vector.empty,
      columnNumber = Vector.empty,
      length = length,
    ))
  implicit val codec: JsonValueCodec[FuncTable] = 
    new JsonValueCodec[FuncTable] {
      def decodeValue(in: JsonReader, default: FuncTable) = 
        new FuncTable(implicitly[JsonValueCodec[FuncTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: FuncTable, out: JsonWriter) = 
        implicitly[JsonValueCodec[FuncTableArgs]].encodeValue(x.args, out)
      
      def nullValue: FuncTable = null
    }
  
}
private[fxprof] case class FuncTableArgs(
  name: Vector[IndexIntoStringTable],
  isJS: Vector[Boolean],
  relevantForJS: Vector[Boolean],
  resource: Vector[FuncTable_Resource],
  source: Vector[Option[IndexIntoSourceTable]],
  lineNumber: Vector[Option[Double]],
  columnNumber: Vector[Option[Double]],
  length: Double,
)
private[fxprof] object FuncTableArgs {
  implicit val codec: JsonValueCodec[FuncTableArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
