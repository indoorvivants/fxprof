package fxprof

class RawCounter private (private[fxprof] val args: RawCounterArgs) {
  def name: String = args.name

  def category: String = args.category

  def description: String = args.description

  def color: Option[GraphColor] = args.color

  def pid: Pid = args.pid

  def mainThreadIndex: ThreadIndex = args.mainThreadIndex

  def samples: RawCounterSamplesTable = args.samples


  def withName(value: String): RawCounter =
    copy(_.copy(name = value))
  
  def withCategory(value: String): RawCounter =
    copy(_.copy(category = value))
  
  def withDescription(value: String): RawCounter =
    copy(_.copy(description = value))
  
  def withColor(value: Option[GraphColor]): RawCounter =
    copy(_.copy(color = value))
  
  def withPid(value: Pid): RawCounter =
    copy(_.copy(pid = value))
  
  def withMainThreadIndex(value: ThreadIndex): RawCounter =
    copy(_.copy(mainThreadIndex = value))
  
  def withSamples(value: RawCounterSamplesTable): RawCounter =
    copy(_.copy(samples = value))
  

  private def copy(f: RawCounterArgs => RawCounterArgs) = 
    new RawCounter(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[RawCounter] && o.asInstanceOf[RawCounter].args.equals(this.args)
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object RawCounter {
  /** Construct a [[RawCounter]]
      @param name
      @param category
      @param description
      @param pid
      @param mainThreadIndex
      @param samples
    */
  def apply(
    name: String,
    category: String,
    description: String,
    pid: Pid,
    mainThreadIndex: ThreadIndex,
    samples: RawCounterSamplesTable,
  ): RawCounter = 
    new RawCounter(RawCounterArgs(
      name = name,
      category = category,
      description = description,
      color = None,
      pid = pid,
      mainThreadIndex = mainThreadIndex,
      samples = samples,
    ))
  implicit val codec: JsonValueCodec[RawCounter] = 
    new JsonValueCodec[RawCounter] {
      def decodeValue(in: JsonReader, default: RawCounter) = 
        new RawCounter(implicitly[JsonValueCodec[RawCounterArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: RawCounter, out: JsonWriter) = 
        implicitly[JsonValueCodec[RawCounterArgs]].encodeValue(x.args, out)
      
      def nullValue: RawCounter = null
    }
  
}
private[fxprof] case class RawCounterArgs(
  name: String,
  category: String,
  description: String,
  color: Option[GraphColor],
  pid: Pid,
  mainThreadIndex: ThreadIndex,
  samples: RawCounterSamplesTable,
)
private[fxprof] object RawCounterArgs {
  implicit val codec: JsonValueCodec[RawCounterArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
