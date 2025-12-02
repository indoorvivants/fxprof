package fxprof

/**
  * Frames contain the context information about the function execution at the moment in
  * time. The caller/callee relationship between frames is defined by the StackTable.
  */
class FrameTable private (private[fxprof] val args: FrameTableArgs) {
  /** If this is a frame for native code, the address is the address of the frame's
    * assembly instruction,  relative to the native library that contains it.
    * 
    * For frames obtained from stack walking, the address points into the call instruction.
    * It is not a return address, it is a "nudged" return address (i.e. return address
    * minus one byte). This is different from the Gecko profile format. The conversion
    * is performed at the end of profile processing. See the big comment above
    * nudgeReturnAddresses for more details.
    * 
    * The library which this address is relative to is given by the frame's nativeSymbol:
    * frame -> nativeSymbol -> lib.
    */
  def address: Vector[FrameTable_Address] = args.address

  /** The inline depth for this frame. If there is an inline stack at an address,
    * we create multiple frames with the same address, one for each depth.
    * The outermost frame always has depth 0.
    * 
    * Example:
    * If the raw stack is 0x10 -> 0x20 -> 0x30, and symbolication adds two inline frames
    * for 0x10, no inline frame for 0x20, and one inline frame for 0x30, then the
    * symbolicated stack will be the following:
    * 
    * func:        outer1 -> inline1a -> inline1b -> outer2 -> outer3 -> inline3a
    * address:     0x10   -> 0x10     -> 0x10     -> 0x20   -> 0x30   -> 0x30
    * inlineDepth:    0   ->    1     ->    2     ->    0   ->    0   ->    1
    * 
    * Background:
    * When a compiler performs an inlining optimization, it removes a call to a function
    * and instead generates the code for the called function directly into the outer
    * function. But it remembers which instructions were the result of this inlining,
    * so that information about the inlined function can be recovered from the debug
    * information during symbolication, based on the instruction address.
    * The compiler can choose to do inlining multiple levels deep: An instruction can
    * be the result of a whole "inline stack" of functions.
    * Before symbolication, all frames have depth 0. During symbolication, we resolve
    * addresses to inline stacks, and create extra frames with non-zero depths as needed.
    * 
    * The frames of an inline stack at an address all have the same address and the same
    * nativeSymbol, but each has a different func and line.
    */
  def inlineDepth: Vector[Double] = args.inlineDepth

  /** The category of the frame. This is used to calculate the category of the stack nodes
    * which use this frame:
    * - If the frame has a null category, the stack node inherits its parent node's category
    * and subcategory. If there is no parent node, we use the "default category" (see ProfileMeta.categories).
    * - If the frame has a non-null category, this category and subcategory is used for the stack node.
    */
  def category: Vector[Option[IndexIntoCategoryList]] = args.category

  /** The subcategory of a frame. This is used to calculate the subcategory of the stack nodes
    * which use this frame.
    * Must be non-null if the frame's category is non-null.
    * Ignored if the frame's category is null.
    * 0 is always a valid value and refers to the "Other" subcategory (see Category.subcategories).
    */
  def subcategory: Vector[Option[IndexIntoSubcategoryListForCategory]] = args.subcategory

  /** The frame's function.
    */
  def func: Vector[IndexIntoFuncTable] = args.func

  /** The symbol index (referring into this thread's nativeSymbols table) corresponding
    * to symbol that covers the frame address of this frame. Only non-null for native
    * frames (e.g. C / C++ / Rust code). Null before symbolication.
    */
  def nativeSymbol: Vector[Option[IndexIntoNativeSymbolTable]] = args.nativeSymbol

  /** Inner window ID of JS frames. JS frames can be correlated to a Page through this value.
    * It's used to determine which JS frame belongs to which web page so we can display
    * that information and filter for single tab profiling.
    * `0` for non-JS frames and the JS frames that failed to get the ID. `0` means "null value"
    * because that's what Firefox platform DOM side assigns when it fails to get the ID or
    * something bad happens during that process. It's not `null` or `-1` because that information
    * is being stored as `uint64_t` there.
    */
  def innerWindowID: Vector[Option[InnerWindowID]] = args.innerWindowID

  def line: Vector[Option[Double]] = args.line

  def column: Vector[Option[Double]] = args.column

  def length: Double = args.length


  /** Setter for [[$name]] field

    * If this is a frame for native code, the address is the address of the frame's
    * assembly instruction,  relative to the native library that contains it.
    * 
    * For frames obtained from stack walking, the address points into the call instruction.
    * It is not a return address, it is a "nudged" return address (i.e. return address
    * minus one byte). This is different from the Gecko profile format. The conversion
    * is performed at the end of profile processing. See the big comment above
    * nudgeReturnAddresses for more details.
    * 
    * The library which this address is relative to is given by the frame's nativeSymbol:
    * frame -> nativeSymbol -> lib.
    */
  def withAddress(value: Vector[FrameTable_Address]): FrameTable =
    copy(_.copy(address = value))
  
  /** Setter for [[$name]] field

    * The inline depth for this frame. If there is an inline stack at an address,
    * we create multiple frames with the same address, one for each depth.
    * The outermost frame always has depth 0.
    * 
    * Example:
    * If the raw stack is 0x10 -> 0x20 -> 0x30, and symbolication adds two inline frames
    * for 0x10, no inline frame for 0x20, and one inline frame for 0x30, then the
    * symbolicated stack will be the following:
    * 
    * func:        outer1 -> inline1a -> inline1b -> outer2 -> outer3 -> inline3a
    * address:     0x10   -> 0x10     -> 0x10     -> 0x20   -> 0x30   -> 0x30
    * inlineDepth:    0   ->    1     ->    2     ->    0   ->    0   ->    1
    * 
    * Background:
    * When a compiler performs an inlining optimization, it removes a call to a function
    * and instead generates the code for the called function directly into the outer
    * function. But it remembers which instructions were the result of this inlining,
    * so that information about the inlined function can be recovered from the debug
    * information during symbolication, based on the instruction address.
    * The compiler can choose to do inlining multiple levels deep: An instruction can
    * be the result of a whole "inline stack" of functions.
    * Before symbolication, all frames have depth 0. During symbolication, we resolve
    * addresses to inline stacks, and create extra frames with non-zero depths as needed.
    * 
    * The frames of an inline stack at an address all have the same address and the same
    * nativeSymbol, but each has a different func and line.
    */
  def withInlineDepth(value: Vector[Double]): FrameTable =
    copy(_.copy(inlineDepth = value))
  
  /** Setter for [[$name]] field

    * The category of the frame. This is used to calculate the category of the stack nodes
    * which use this frame:
    * - If the frame has a null category, the stack node inherits its parent node's category
    * and subcategory. If there is no parent node, we use the "default category" (see ProfileMeta.categories).
    * - If the frame has a non-null category, this category and subcategory is used for the stack node.
    */
  def withCategory(value: Vector[Option[IndexIntoCategoryList]]): FrameTable =
    copy(_.copy(category = value))
  
  /** Setter for [[$name]] field

    * The subcategory of a frame. This is used to calculate the subcategory of the stack nodes
    * which use this frame.
    * Must be non-null if the frame's category is non-null.
    * Ignored if the frame's category is null.
    * 0 is always a valid value and refers to the "Other" subcategory (see Category.subcategories).
    */
  def withSubcategory(value: Vector[Option[IndexIntoSubcategoryListForCategory]]): FrameTable =
    copy(_.copy(subcategory = value))
  
  /** Setter for [[$name]] field

    * The frame's function.
    */
  def withFunc(value: Vector[IndexIntoFuncTable]): FrameTable =
    copy(_.copy(func = value))
  
  /** Setter for [[$name]] field

    * The symbol index (referring into this thread's nativeSymbols table) corresponding
    * to symbol that covers the frame address of this frame. Only non-null for native
    * frames (e.g. C / C++ / Rust code). Null before symbolication.
    */
  def withNativeSymbol(value: Vector[Option[IndexIntoNativeSymbolTable]]): FrameTable =
    copy(_.copy(nativeSymbol = value))
  
  /** Setter for [[$name]] field

    * Inner window ID of JS frames. JS frames can be correlated to a Page through this value.
    * It's used to determine which JS frame belongs to which web page so we can display
    * that information and filter for single tab profiling.
    * `0` for non-JS frames and the JS frames that failed to get the ID. `0` means "null value"
    * because that's what Firefox platform DOM side assigns when it fails to get the ID or
    * something bad happens during that process. It's not `null` or `-1` because that information
    * is being stored as `uint64_t` there.
    */
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
  /** Construct a [[FrameTable]]
      @param length
    */
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
