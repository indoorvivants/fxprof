package fxprof

class Page private (private[fxprof] val args: PageArgs) {
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

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Page {
  def apply(
    tabID: TabID,
    innerWindowID: InnerWindowID,
    url: String,
    embedderInnerWindowID: Double,
  ): Page = 
    new Page(PageArgs(
      tabID = tabID,
      innerWindowID = innerWindowID,
      url = url,
      embedderInnerWindowID = embedderInnerWindowID,
    ))
  given JsonValueCodec[Page] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: Page) = 
        new Page(summon[JsonValueCodec[PageArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: Page, out: JsonWriter) = 
        summon[JsonValueCodec[PageArgs]].encodeValue(x.args, out)
      
      def nullValue: Page = null
    }
  
}
private[fxprof] case class PageArgs(
  tabID: TabID,
  innerWindowID: InnerWindowID,
  url: String,
  embedderInnerWindowID: Double,
  isPrivateBrowsing: Option[Boolean] = None,
  favicon: Option[Option[String]] = None,
)
private[fxprof] object PageArgs {
  given JsonValueCodec[PageArgs] = JsonCodecMaker.make
}
