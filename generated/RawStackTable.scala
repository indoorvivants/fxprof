package fxprof

/**
  * The stack table stores the tree of stack nodes of a thread.
  * The shape of the tree is encoded in the prefix column: Root stack nodes have
  * null as their prefix, and every non-root stack has the stack index of its
  * "caller" / "parent" as its prefix.
  * Every stack node also has a frame and a category.
  * A "call stack" is a list of frames. Every stack index in the stack table
  * represents such a call stack; the "list of frames" is obtained by walking
  * the path in the tree from the root to the given stack node.
  * *
  * Stacks are used in the thread's samples; each sample refers to a stack index.
  * Stacks can be shared between samples.
  * *
  * With this representation, every sample only needs to store a single integer
  * to identify the sample's stack.
  * We take advantage of the fact that many call stacks in the profile have a
  * shared prefix; storing these stacks as a tree saves a lot of space compared
  * to storing them as actual lists of frames.
  */
class RawStackTable private (private[fxprof] val args: RawStackTableArgs) {
  def frame: Vector[IndexIntoFrameTable] = args.frame

  def prefix: Vector[Option[IndexIntoStackTable]] = args.prefix

  def length: Double = args.length


  def withFrame(value: Vector[IndexIntoFrameTable]): RawStackTable =
    copy(_.copy(frame = value))
  
  def withPrefix(value: Vector[Option[IndexIntoStackTable]]): RawStackTable =
    copy(_.copy(prefix = value))
  
  def withLength(value: Double): RawStackTable =
    copy(_.copy(length = value))
  

  private def copy(f: RawStackTableArgs => RawStackTableArgs) = 
    new RawStackTable(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawStackTable {
  /** Construct a [[RawStackTable]]
      @param length
    */
  def apply(
    length: Double,
  ): RawStackTable = 
    new RawStackTable(RawStackTableArgs(
      frame = Vector.empty,
      prefix = Vector.empty,
      length = length,
    ))
  given JsonValueCodec[RawStackTable] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: RawStackTable) = 
        new RawStackTable(summon[JsonValueCodec[RawStackTableArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawStackTable, out: JsonWriter) = 
        summon[JsonValueCodec[RawStackTableArgs]].encodeValue(x.args, out)
      
      def nullValue: RawStackTable = null
    }
  
}
private[fxprof] case class RawStackTableArgs(
  frame: Vector[IndexIntoFrameTable],
  prefix: Vector[Option[IndexIntoStackTable]],
  length: Double,
)
private[fxprof] object RawStackTableArgs {
  given JsonValueCodec[RawStackTableArgs] = JsonCodecMaker.make
}
