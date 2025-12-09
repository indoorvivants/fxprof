package fxprof

/**
  * Information about the shared libraries that were loaded into the processes in
  * the profile. This information is needed during symbolication. Most importantly,
  * the symbolication API requires a debugName + breakpadId for each set of
  * unsymbolicated addresses, to know where to obtain symbols for those addresses.
  */
class Lib private (private[fxprof] val args: LibArgs) {
  def arch: String = args.arch

  def name: String = args.name

  def path: String = args.path

  def debugName: String = args.debugName

  def debugPath: String = args.debugPath

  def breakpadId: String = args.breakpadId

  /** The codeId is currently always null.
    * In the future, it will have the following values:
    * - On macOS, it will still be null.
    * - On Linux / Android, it will have the full GNU build id. (The breakpadId
    * is also based on the build id, but truncates some information.)
    * This lets us obtain unstripped system libraries on Linux distributions
    * which have a "debuginfod" server, and we can use those unstripped binaries
    * for symbolication.
    * - On Windows, it will be the codeId for the binary (.exe / .dll), as used
    * by Windows symbol servers. This will allow us to get assembly code for
    * Windows system libraries for profiles which were captured on another machine.
    */
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
  
  /** Setter for [[codeId]] field

    * The codeId is currently always null.
    * In the future, it will have the following values:
    * - On macOS, it will still be null.
    * - On Linux / Android, it will have the full GNU build id. (The breakpadId
    * is also based on the build id, but truncates some information.)
    * This lets us obtain unstripped system libraries on Linux distributions
    * which have a "debuginfod" server, and we can use those unstripped binaries
    * for symbolication.
    * - On Windows, it will be the codeId for the binary (.exe / .dll), as used
    * by Windows symbol servers. This will allow us to get assembly code for
    * Windows system libraries for profiles which were captured on another machine.
    */
  def withCodeId(value: Option[String]): Lib =
    copy(_.copy(codeId = value))
  

  private def copy(f: LibArgs => LibArgs) = 
    new Lib(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[Lib] && o.asInstanceOf[Lib].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Lib {
  /** Construct a [[Lib]]
      @param arch
      @param name
      @param path
      @param debugName
      @param debugPath
      @param breakpadId
    */
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
      codeId = None,
    ))
  implicit val codec: JsonValueCodec[Lib] = 
    new JsonValueCodec[Lib] {
      def decodeValue(in: JsonReader, default: Lib) = 
        new Lib(implicitly[JsonValueCodec[LibArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: Lib, out: JsonWriter) = 
        implicitly[JsonValueCodec[LibArgs]].encodeValue(x.args, out)
      
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
  codeId: Option[String],
)
private[fxprof] object LibArgs {
  implicit val codec: JsonValueCodec[LibArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
