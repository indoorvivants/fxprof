package fxprof

class RawCounter private (args: RawCounterArgs) {
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
  
}

object RawCounter {
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
      pid = pid,
      mainThreadIndex = mainThreadIndex,
      samples = samples,
    ))
}
private[fxprof] case class RawCounterArgs(
  name: String,
  category: String,
  description: String,
  color: Option[GraphColor] = None,
  pid: Pid,
  mainThreadIndex: ThreadIndex,
  samples: RawCounterSamplesTable,
)
