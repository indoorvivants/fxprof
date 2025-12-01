package fxprof

class Page private (args: PageArgs) {
  def tabID: TabID = args.tabID
  def innerWindowID: InnerWindowID = args.innerWindowID
  def url: String = args.url
  def embedderInnerWindowID: Double = args.embedderInnerWindowID
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing
  def favicon: Option[Option[String]] = args.favicon

  def withTabID(value: TabID): Page =
    copy(_.copy(tabID = value))
  
  def withInnerWindowID(value: InnerWindowID): Page =
    copy(_.copy(innerWindowID = value))
  
  def withUrl(value: String): Page =
    copy(_.copy(url = value))
  
  def withEmbedderInnerWindowID(value: Double): Page =
    copy(_.copy(embedderInnerWindowID = value))
  
  def withIsPrivateBrowsing(value: Option[Boolean]): Page =
    copy(_.copy(isPrivateBrowsing = value))
  
  def withFavicon(value: Option[Option[String]]): Page =
    copy(_.copy(favicon = value))
  

  private def copy(f: PageArgs => PageArgs) = 
    new Page(f(args))
  
}

private[fxprof] case class PageArgs(
  tabID: TabID,
  innerWindowID: InnerWindowID,
  url: String,
  embedderInnerWindowID: Double,
  isPrivateBrowsing: Option[Boolean],
  favicon: Option[Option[String]],
)
