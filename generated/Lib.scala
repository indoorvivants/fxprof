package fxprof

class Lib private (private[fxprof] val args: LibArgs) {
  def arch: String = args.arch
  def name: String = args.name
  def path: String = args.path
  def debugName: String = args.debugName
  def debugPath: String = args.debugPath
  def breakpadId: String = args.breakpadId
  def codeId: Option[String] = args.codeId

  def withArch(value: String): Lib =
    copy(_.copy(arch = value))
  
  def withName(value: String): Lib =
    copy(_.copy(name = value))
  
  def withPath(value: String): Lib =
    copy(_.copy(path = value))
  
  def withDebugName(value: String): Lib =
    copy(_.copy(debugName = value))
  
  def withDebugPath(value: String): Lib =
    copy(_.copy(debugPath = value))
  
  def withBreakpadId(value: String): Lib =
    copy(_.copy(breakpadId = value))
  
  def withCodeId(value: Option[String]): Lib =
    copy(_.copy(codeId = value))
  

  private def copy(f: LibArgs => LibArgs) = 
    new Lib(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Lib {
  def apply(
    arch: String,
    name: String,
    path: String,
    debugName: String,
    debugPath: String,
    breakpadId: String,
  ): Lib = 
    new Lib(LibArgs(
      arch = arch,
      name = name,
      path = path,
      debugName = debugName,
      debugPath = debugPath,
      breakpadId = breakpadId,
    ))
  given JsonValueCodec[Lib] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: Lib) = 
        new Lib(summon[JsonValueCodec[LibArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: Lib, out: JsonWriter) = 
        summon[JsonValueCodec[LibArgs]].encodeValue(x.args, out)
      
      def nullValue: Lib = null
    }
  
}
private[fxprof] case class LibArgs(
  arch: String,
  name: String,
  path: String,
  debugName: String,
  debugPath: String,
  breakpadId: String,
  codeId: Option[String] = None,
)
private[fxprof] object LibArgs {
  given JsonValueCodec[LibArgs] = JsonCodecMaker.make
}
