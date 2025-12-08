package fxprof

/**
  * A Page describes the page the browser profiled. In Firefox, TabIDs represent the
  * ID that is shared between multiple frames in a single tab. The Inner Window IDs
  * represent JS `window` objects in each Document. And they are unique for each frame.
  * That's why it's enough to keep only inner Window IDs inside marker payloads.
  * 0 means null(no embedder) for Embedder Window ID.
  * *
  * The unique field for a page is innerWindowID.
  */
class Page private (private[fxprof] val args: PageArgs) {
  /** Tab ID of the page. This ID is the same for all the pages inside a tab's
    * session history.
    */
  def tabID: TabID = args.tabID

  /** ID of the JS `window` object in a `Document`. It's unique for every page.
    */
  def innerWindowID: InnerWindowID = args.innerWindowID

  /** Url of this page.
    */
  def url: String = args.url

  /** Each page describes a frame in websites. A frame can either be the top-most
    * one or inside of another one. For the children frames, `embedderInnerWindowID`
    * points to the innerWindowID of the parent (embedder). It's `0` if there is
    * no embedder, which means that it's the top-most frame. That way all pages
    * can create a tree of pages that can be navigated.
    */
  def embedderInnerWindowID: Double = args.embedderInnerWindowID

  /** If true, this page has been opened in a private browsing window.
    * It's optional because it appeared in Firefox 98, and is absent before when
    * capturing was disabled when a private browsing window was open.
    * The property is always present in Firefox 98+.
    */
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing

  /** Favicon data of the page if it was successfully retrieved from Firefox.
    * It's a base64 encoded URI string when available.
    * It's null when Firefox can't get the favicon.
    * This is added in Firefox 134, earlier profiles will not have it.
    */
  def favicon: Option[Option[String]] = args.favicon


  /** Setter for [[tabID]] field

    * Tab ID of the page. This ID is the same for all the pages inside a tab's
    * session history.
    */
  def withTabID(value: TabID): Page =
    copy(_.copy(tabID = value))
  
  /** Setter for [[innerWindowID]] field

    * ID of the JS `window` object in a `Document`. It's unique for every page.
    */
  def withInnerWindowID(value: InnerWindowID): Page =
    copy(_.copy(innerWindowID = value))
  
  /** Setter for [[url]] field

    * Url of this page.
    */
  def withUrl(value: String): Page =
    copy(_.copy(url = value))
  
  /** Setter for [[embedderInnerWindowID]] field

    * Each page describes a frame in websites. A frame can either be the top-most
    * one or inside of another one. For the children frames, `embedderInnerWindowID`
    * points to the innerWindowID of the parent (embedder). It's `0` if there is
    * no embedder, which means that it's the top-most frame. That way all pages
    * can create a tree of pages that can be navigated.
    */
  def withEmbedderInnerWindowID(value: Double): Page =
    copy(_.copy(embedderInnerWindowID = value))
  
  /** Setter for [[isPrivateBrowsing]] field

    * If true, this page has been opened in a private browsing window.
    * It's optional because it appeared in Firefox 98, and is absent before when
    * capturing was disabled when a private browsing window was open.
    * The property is always present in Firefox 98+.
    */
  def withIsPrivateBrowsing(value: Option[Boolean]): Page =
    copy(_.copy(isPrivateBrowsing = value))
  
  /** Setter for [[favicon]] field

    * Favicon data of the page if it was successfully retrieved from Firefox.
    * It's a base64 encoded URI string when available.
    * It's null when Firefox can't get the favicon.
    * This is added in Firefox 134, earlier profiles will not have it.
    */
  def withFavicon(value: Option[Option[String]]): Page =
    copy(_.copy(favicon = value))
  

  private def copy(f: PageArgs => PageArgs) = 
    new Page(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[Page] && o.asInstanceOf[Page].args.equals(this.args)
  override def hashCode() = this.args.hashCode()
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object Page {
  /** Construct a [[Page]]
      @param tabIDTab ID of the page. This ID is the same for all the pages inside a tab's
                  session history.
      @param innerWindowIDID of the JS `window` object in a `Document`. It's unique for every page.
      @param urlUrl of this page.
      @param embedderInnerWindowIDEach page describes a frame in websites. A frame can either be the top-most
                                  one or inside of another one. For the children frames, `embedderInnerWindowID`
                                  points to the innerWindowID of the parent (embedder). It's `0` if there is
                                  no embedder, which means that it's the top-most frame. That way all pages
                                  can create a tree of pages that can be navigated.
    */
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
      isPrivateBrowsing = None,
      favicon = None,
    ))
  implicit val codec: JsonValueCodec[Page] = 
    new JsonValueCodec[Page] {
      def decodeValue(in: JsonReader, default: Page) = 
        new Page(implicitly[JsonValueCodec[PageArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: Page, out: JsonWriter) = 
        implicitly[JsonValueCodec[PageArgs]].encodeValue(x.args, out)
      
      def nullValue: Page = null
    }
  
}
private[fxprof] case class PageArgs(
  tabID: TabID,
  innerWindowID: InnerWindowID,
  url: String,
  embedderInnerWindowID: Double,
  isPrivateBrowsing: Option[Boolean],
  favicon: Option[Option[String]],
)
private[fxprof] object PageArgs {
  implicit val codec: JsonValueCodec[PageArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
