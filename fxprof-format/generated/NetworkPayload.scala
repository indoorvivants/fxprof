package fxprof

/**
  * Network http/https loads - one marker for each load that reaches the
  * STOP state that occurs, plus one for the initial START of the load, with
  * the URI and the status.  A unique ID is included to allow these to be linked.
  * Note that the 'name' field currently also has the id ("Load N") so that
  * marker.js will not merge separate loads of the same URI.  Note also that
  * URI is not necessarily included in later network markers for a specific
  * load to avoid having to use cycles during collection to access, allocate
  * and copy the URI.  Markers using the same ID are all for the same load.
  * *
  * Most of the fields only are included on STOP, and not all of them may
  * be included depending on what states happen during the load.  Also note
  * that redirects are logged as well.
  */
class NetworkPayload private (private[fxprof] val args: NetworkPayloadArgs) {
  def `type`: NetworkPayload_Type.type = args.`type`

  def innerWindowID: Option[Double] = args.innerWindowID

  def URI: String = args.URI

  def RedirectURI: Option[String] = args.RedirectURI

  def id: Double = args.id

  def pri: Double = args.pri

  def count: Option[Double] = args.count

  /** See all possible values in tools/profiler/core/platform.cpp
    */
  def status: NetworkStatus = args.status

  /** The following property is present only when this marker is for a
    * redirection. Note it is present since Gecko v91 only.
    */
  def redirectType: Option[NetworkRedirectType] = args.redirectType

  /** The following property is present only when this marker is for a
    * redirection. Note it is present since Gecko v91 only.
    */
  def isHttpToHttpsRedirect: Option[Boolean] = args.isHttpToHttpsRedirect

  /** When present in a redirect marker, this is the id of the next request,
    * started because of the redirection. Note it is present since Gecko v91
    * only.
    */
  def redirectId: Option[Double] = args.redirectId

  def cache: Option[String] = args.cache

  def cause: Option[CauseBacktrace] = args.cause

  /** contentType is the value of the Content-Type header from the HTTP
    * response. An empty string means the response had no content type,
    * while a value of null means no HTTP response was received. If
    * this property is absent then it means this profiler came from an
    * older version of the Gecko profiler without content type support.
    */
  def contentType: Option[Option[String]] = args.contentType

  /** If present and true, this network marker originated from a request in a
    * private browsing session.
    * Most markers tied to a window also have a innerWindowID property, but
    * that's not always the case, especially for the requests for the top level
    * navigation. That's why this property is needed in addition to the
    * innerWindowID property that we also have.
    * It's always absent in Firefox < 98 because we couldn't capture private
    * browsing data back then.
    */
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing

  def httpVersion: Option[NetworkHttpVersion] = args.httpVersion

  /** Used to express class dependencies and characteristics.
    * Possible flags: Leader, Follower, Speculative, Background, Unblocked,
    * Throttleable, UrgentStart, DontThrottle, Tail, TailAllowed, and
    * TailForbidden. Multiple flags can be set, separated by '|',
    * or we use 'Unset' if no flag is set.
    */
  def classOfService: Option[String] = args.classOfService

  /** Used to show the request status (nsresult nsIRequest::status)
    */
  def requestStatus: Option[String] = args.requestStatus

  /** Used to show the HTTP response status code
    */
  def responseStatus: Option[Double] = args.responseStatus

  /** startTime is when the channel opens. This happens on the process' main
    * thread.
    */
  def startTime: Milliseconds = args.startTime

  /** endTime is the time when the response is sent back to the caller, this
    * happens on the process' main thread.
    */
  def endTime: Milliseconds = args.endTime

  /** fetchStart doesn't exist directly in raw markers. This is added in the
    * deriving process and represents the junction between START and END markers.
    * This is the same value as the start marker's endTime and the end marker's
    * startTime (which are the same values).
    * We don't expose it directly but this is useful for debugging.
    */
  def fetchStart: Option[Milliseconds] = args.fetchStart

  /** The following properties are present only in non-START markers.
    * domainLookupStart, if present, should be the first timestamp for an event
    * happening on the socket thread. However it's not present for persisted
    * connections. This is also the case for `domainLookupEnd`, `connectStart`,
    * `tcpConnectEnd`, `secureConnectionStart`, and `connectEnd`.
    * NOTE: If you add a new property, don't forget to adjust its timestamp in
    * `adjustMarkerTimestamps` in `process-profile.js`.
    */
  def domainLookupStart: Option[Milliseconds] = args.domainLookupStart

  def domainLookupEnd: Option[Milliseconds] = args.domainLookupEnd

  def connectStart: Option[Milliseconds] = args.connectStart

  def tcpConnectEnd: Option[Milliseconds] = args.tcpConnectEnd

  def secureConnectionStart: Option[Milliseconds] = args.secureConnectionStart

  def connectEnd: Option[Milliseconds] = args.connectEnd

  /** `requestStart`, `responseStart` and `responseEnd` should always be present
    * for STOP markers.
    */
  def requestStart: Option[Milliseconds] = args.requestStart

  def responseStart: Option[Milliseconds] = args.responseStart

  /** responseEnd is when we received the response from the server, this happens
    * on the socket thread.
    */
  def responseEnd: Option[Milliseconds] = args.responseEnd


  def `withtype`(value: NetworkPayload_Type.type): NetworkPayload =
    copy(_.copy(`type` = value))
  
  def withInnerWindowID(value: Option[Double]): NetworkPayload =
    copy(_.copy(innerWindowID = value))
  
  def withURI(value: String): NetworkPayload =
    copy(_.copy(URI = value))
  
  def withRedirectURI(value: Option[String]): NetworkPayload =
    copy(_.copy(RedirectURI = value))
  
  def withId(value: Double): NetworkPayload =
    copy(_.copy(id = value))
  
  def withPri(value: Double): NetworkPayload =
    copy(_.copy(pri = value))
  
  def withCount(value: Option[Double]): NetworkPayload =
    copy(_.copy(count = value))
  
  /** Setter for [[status]] field

    * See all possible values in tools/profiler/core/platform.cpp
    */
  def withStatus(value: NetworkStatus): NetworkPayload =
    copy(_.copy(status = value))
  
  /** Setter for [[redirectType]] field

    * The following property is present only when this marker is for a
    * redirection. Note it is present since Gecko v91 only.
    */
  def withRedirectType(value: Option[NetworkRedirectType]): NetworkPayload =
    copy(_.copy(redirectType = value))
  
  /** Setter for [[isHttpToHttpsRedirect]] field

    * The following property is present only when this marker is for a
    * redirection. Note it is present since Gecko v91 only.
    */
  def withIsHttpToHttpsRedirect(value: Option[Boolean]): NetworkPayload =
    copy(_.copy(isHttpToHttpsRedirect = value))
  
  /** Setter for [[redirectId]] field

    * When present in a redirect marker, this is the id of the next request,
    * started because of the redirection. Note it is present since Gecko v91
    * only.
    */
  def withRedirectId(value: Option[Double]): NetworkPayload =
    copy(_.copy(redirectId = value))
  
  def withCache(value: Option[String]): NetworkPayload =
    copy(_.copy(cache = value))
  
  def withCause(value: Option[CauseBacktrace]): NetworkPayload =
    copy(_.copy(cause = value))
  
  /** Setter for [[contentType]] field

    * contentType is the value of the Content-Type header from the HTTP
    * response. An empty string means the response had no content type,
    * while a value of null means no HTTP response was received. If
    * this property is absent then it means this profiler came from an
    * older version of the Gecko profiler without content type support.
    */
  def withContentType(value: Option[Option[String]]): NetworkPayload =
    copy(_.copy(contentType = value))
  
  /** Setter for [[isPrivateBrowsing]] field

    * If present and true, this network marker originated from a request in a
    * private browsing session.
    * Most markers tied to a window also have a innerWindowID property, but
    * that's not always the case, especially for the requests for the top level
    * navigation. That's why this property is needed in addition to the
    * innerWindowID property that we also have.
    * It's always absent in Firefox < 98 because we couldn't capture private
    * browsing data back then.
    */
  def withIsPrivateBrowsing(value: Option[Boolean]): NetworkPayload =
    copy(_.copy(isPrivateBrowsing = value))
  
  def withHttpVersion(value: Option[NetworkHttpVersion]): NetworkPayload =
    copy(_.copy(httpVersion = value))
  
  /** Setter for [[classOfService]] field

    * Used to express class dependencies and characteristics.
    * Possible flags: Leader, Follower, Speculative, Background, Unblocked,
    * Throttleable, UrgentStart, DontThrottle, Tail, TailAllowed, and
    * TailForbidden. Multiple flags can be set, separated by '|',
    * or we use 'Unset' if no flag is set.
    */
  def withClassOfService(value: Option[String]): NetworkPayload =
    copy(_.copy(classOfService = value))
  
  /** Setter for [[requestStatus]] field

    * Used to show the request status (nsresult nsIRequest::status)
    */
  def withRequestStatus(value: Option[String]): NetworkPayload =
    copy(_.copy(requestStatus = value))
  
  /** Setter for [[responseStatus]] field

    * Used to show the HTTP response status code
    */
  def withResponseStatus(value: Option[Double]): NetworkPayload =
    copy(_.copy(responseStatus = value))
  
  /** Setter for [[startTime]] field

    * startTime is when the channel opens. This happens on the process' main
    * thread.
    */
  def withStartTime(value: Milliseconds): NetworkPayload =
    copy(_.copy(startTime = value))
  
  /** Setter for [[endTime]] field

    * endTime is the time when the response is sent back to the caller, this
    * happens on the process' main thread.
    */
  def withEndTime(value: Milliseconds): NetworkPayload =
    copy(_.copy(endTime = value))
  
  /** Setter for [[fetchStart]] field

    * fetchStart doesn't exist directly in raw markers. This is added in the
    * deriving process and represents the junction between START and END markers.
    * This is the same value as the start marker's endTime and the end marker's
    * startTime (which are the same values).
    * We don't expose it directly but this is useful for debugging.
    */
  def withFetchStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(fetchStart = value))
  
  /** Setter for [[domainLookupStart]] field

    * The following properties are present only in non-START markers.
    * domainLookupStart, if present, should be the first timestamp for an event
    * happening on the socket thread. However it's not present for persisted
    * connections. This is also the case for `domainLookupEnd`, `connectStart`,
    * `tcpConnectEnd`, `secureConnectionStart`, and `connectEnd`.
    * NOTE: If you add a new property, don't forget to adjust its timestamp in
    * `adjustMarkerTimestamps` in `process-profile.js`.
    */
  def withDomainLookupStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(domainLookupStart = value))
  
  def withDomainLookupEnd(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(domainLookupEnd = value))
  
  def withConnectStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(connectStart = value))
  
  def withTcpConnectEnd(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(tcpConnectEnd = value))
  
  def withSecureConnectionStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(secureConnectionStart = value))
  
  def withConnectEnd(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(connectEnd = value))
  
  /** Setter for [[requestStart]] field

    * `requestStart`, `responseStart` and `responseEnd` should always be present
    * for STOP markers.
    */
  def withRequestStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(requestStart = value))
  
  def withResponseStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(responseStart = value))
  
  /** Setter for [[responseEnd]] field

    * responseEnd is when we received the response from the server, this happens
    * on the socket thread.
    */
  def withResponseEnd(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(responseEnd = value))
  

  private def copy(f: NetworkPayloadArgs => NetworkPayloadArgs) = 
    new NetworkPayload(f(args))
  

  override def equals(o: Any) = o.isInstanceOf[NetworkPayload] && o.asInstanceOf[NetworkPayload].args.equals(this.args)
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object NetworkPayload {
  /** Construct a [[NetworkPayload]]
      @param type
      @param URI
      @param id
      @param pri
      @param statusSee all possible values in tools/profiler/core/platform.cpp
      @param startTimestartTime is when the channel opens. This happens on the process' main
                      thread.
      @param endTimeendTime is the time when the response is sent back to the caller, this
                    happens on the process' main thread.
    */
  def apply(
    `type`: NetworkPayload_Type.type,
    URI: String,
    id: Double,
    pri: Double,
    status: NetworkStatus,
    startTime: Milliseconds,
    endTime: Milliseconds,
  ): NetworkPayload = 
    new NetworkPayload(NetworkPayloadArgs(
      `type` = `type`,
      innerWindowID = None,
      URI = URI,
      RedirectURI = None,
      id = id,
      pri = pri,
      count = None,
      status = status,
      redirectType = None,
      isHttpToHttpsRedirect = None,
      redirectId = None,
      cache = None,
      cause = None,
      contentType = None,
      isPrivateBrowsing = None,
      httpVersion = None,
      classOfService = None,
      requestStatus = None,
      responseStatus = None,
      startTime = startTime,
      endTime = endTime,
      fetchStart = None,
      domainLookupStart = None,
      domainLookupEnd = None,
      connectStart = None,
      tcpConnectEnd = None,
      secureConnectionStart = None,
      connectEnd = None,
      requestStart = None,
      responseStart = None,
      responseEnd = None,
    ))
  implicit val codec: JsonValueCodec[NetworkPayload] = 
    new JsonValueCodec[NetworkPayload] {
      def decodeValue(in: JsonReader, default: NetworkPayload) = 
        new NetworkPayload(implicitly[JsonValueCodec[NetworkPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NetworkPayload, out: JsonWriter) = 
        implicitly[JsonValueCodec[NetworkPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: NetworkPayload = null
    }
  
}
private[fxprof] case class NetworkPayloadArgs(
  `type`: NetworkPayload_Type.type,
  innerWindowID: Option[Double],
  URI: String,
  RedirectURI: Option[String],
  id: Double,
  pri: Double,
  count: Option[Double],
  status: NetworkStatus,
  redirectType: Option[NetworkRedirectType],
  isHttpToHttpsRedirect: Option[Boolean],
  redirectId: Option[Double],
  cache: Option[String],
  cause: Option[CauseBacktrace],
  contentType: Option[Option[String]],
  isPrivateBrowsing: Option[Boolean],
  httpVersion: Option[NetworkHttpVersion],
  classOfService: Option[String],
  requestStatus: Option[String],
  responseStatus: Option[Double],
  startTime: Milliseconds,
  endTime: Milliseconds,
  fetchStart: Option[Milliseconds],
  domainLookupStart: Option[Milliseconds],
  domainLookupEnd: Option[Milliseconds],
  connectStart: Option[Milliseconds],
  tcpConnectEnd: Option[Milliseconds],
  secureConnectionStart: Option[Milliseconds],
  connectEnd: Option[Milliseconds],
  requestStart: Option[Milliseconds],
  responseStart: Option[Milliseconds],
  responseEnd: Option[Milliseconds],
)
private[fxprof] object NetworkPayloadArgs {
  implicit val codec: JsonValueCodec[NetworkPayloadArgs] = 
    JsonCodecMaker.make(CodecMakerConfig.withTransientEmpty(true))
  
}
