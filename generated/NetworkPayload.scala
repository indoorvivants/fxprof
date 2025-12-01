package fxprof

class NetworkPayload private (private[fxprof] val args: NetworkPayloadArgs) {
  def `type`: NetworkPayload_Type.type = args.`type`
  def innerWindowID: Option[Double] = args.innerWindowID
  def URI: String = args.URI
  def RedirectURI: Option[String] = args.RedirectURI
  def id: Double = args.id
  def pri: Double = args.pri
  def count: Option[Double] = args.count
  def status: NetworkStatus = args.status
  def redirectType: Option[NetworkRedirectType] = args.redirectType
  def isHttpToHttpsRedirect: Option[Boolean] = args.isHttpToHttpsRedirect
  def redirectId: Option[Double] = args.redirectId
  def cache: Option[String] = args.cache
  def cause: Option[CauseBacktrace] = args.cause
  def contentType: Option[Option[String]] = args.contentType
  def isPrivateBrowsing: Option[Boolean] = args.isPrivateBrowsing
  def httpVersion: Option[NetworkHttpVersion] = args.httpVersion
  def classOfService: Option[String] = args.classOfService
  def requestStatus: Option[String] = args.requestStatus
  def responseStatus: Option[Double] = args.responseStatus
  def startTime: Milliseconds = args.startTime
  def endTime: Milliseconds = args.endTime
  def fetchStart: Option[Milliseconds] = args.fetchStart
  def domainLookupStart: Option[Milliseconds] = args.domainLookupStart
  def domainLookupEnd: Option[Milliseconds] = args.domainLookupEnd
  def connectStart: Option[Milliseconds] = args.connectStart
  def tcpConnectEnd: Option[Milliseconds] = args.tcpConnectEnd
  def secureConnectionStart: Option[Milliseconds] = args.secureConnectionStart
  def connectEnd: Option[Milliseconds] = args.connectEnd
  def requestStart: Option[Milliseconds] = args.requestStart
  def responseStart: Option[Milliseconds] = args.responseStart
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
  
  def withStatus(value: NetworkStatus): NetworkPayload =
    copy(_.copy(status = value))
  
  def withRedirectType(value: Option[NetworkRedirectType]): NetworkPayload =
    copy(_.copy(redirectType = value))
  
  def withIsHttpToHttpsRedirect(value: Option[Boolean]): NetworkPayload =
    copy(_.copy(isHttpToHttpsRedirect = value))
  
  def withRedirectId(value: Option[Double]): NetworkPayload =
    copy(_.copy(redirectId = value))
  
  def withCache(value: Option[String]): NetworkPayload =
    copy(_.copy(cache = value))
  
  def withCause(value: Option[CauseBacktrace]): NetworkPayload =
    copy(_.copy(cause = value))
  
  def withContentType(value: Option[Option[String]]): NetworkPayload =
    copy(_.copy(contentType = value))
  
  def withIsPrivateBrowsing(value: Option[Boolean]): NetworkPayload =
    copy(_.copy(isPrivateBrowsing = value))
  
  def withHttpVersion(value: Option[NetworkHttpVersion]): NetworkPayload =
    copy(_.copy(httpVersion = value))
  
  def withClassOfService(value: Option[String]): NetworkPayload =
    copy(_.copy(classOfService = value))
  
  def withRequestStatus(value: Option[String]): NetworkPayload =
    copy(_.copy(requestStatus = value))
  
  def withResponseStatus(value: Option[Double]): NetworkPayload =
    copy(_.copy(responseStatus = value))
  
  def withStartTime(value: Milliseconds): NetworkPayload =
    copy(_.copy(startTime = value))
  
  def withEndTime(value: Milliseconds): NetworkPayload =
    copy(_.copy(endTime = value))
  
  def withFetchStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(fetchStart = value))
  
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
  
  def withRequestStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(requestStart = value))
  
  def withResponseStart(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(responseStart = value))
  
  def withResponseEnd(value: Option[Milliseconds]): NetworkPayload =
    copy(_.copy(responseEnd = value))
  

  private def copy(f: NetworkPayloadArgs => NetworkPayloadArgs) = 
    new NetworkPayload(f(args))
  
}

import com.github.plokhotnyuk.jsoniter_scala.macros._
import com.github.plokhotnyuk.jsoniter_scala.core._

object NetworkPayload {
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
      URI = URI,
      id = id,
      pri = pri,
      status = status,
      startTime = startTime,
      endTime = endTime,
    ))
  given JsonValueCodec[NetworkPayload] = 
    new JsonValueCodec {
      def decodeValue(in: JsonReader, default: NetworkPayload) = 
        new NetworkPayload(summon[JsonValueCodec[NetworkPayloadArgs]].decodeValue(in, default.args))
      
      def encodeValue(x: NetworkPayload, out: JsonWriter) = 
        summon[JsonValueCodec[NetworkPayloadArgs]].encodeValue(x.args, out)
      
      def nullValue: NetworkPayload = null
    }
  
}
private[fxprof] case class NetworkPayloadArgs(
  `type`: NetworkPayload_Type.type,
  innerWindowID: Option[Double] = None,
  URI: String,
  RedirectURI: Option[String] = None,
  id: Double,
  pri: Double,
  count: Option[Double] = None,
  status: NetworkStatus,
  redirectType: Option[NetworkRedirectType] = None,
  isHttpToHttpsRedirect: Option[Boolean] = None,
  redirectId: Option[Double] = None,
  cache: Option[String] = None,
  cause: Option[CauseBacktrace] = None,
  contentType: Option[Option[String]] = None,
  isPrivateBrowsing: Option[Boolean] = None,
  httpVersion: Option[NetworkHttpVersion] = None,
  classOfService: Option[String] = None,
  requestStatus: Option[String] = None,
  responseStatus: Option[Double] = None,
  startTime: Milliseconds,
  endTime: Milliseconds,
  fetchStart: Option[Milliseconds] = None,
  domainLookupStart: Option[Milliseconds] = None,
  domainLookupEnd: Option[Milliseconds] = None,
  connectStart: Option[Milliseconds] = None,
  tcpConnectEnd: Option[Milliseconds] = None,
  secureConnectionStart: Option[Milliseconds] = None,
  connectEnd: Option[Milliseconds] = None,
  requestStart: Option[Milliseconds] = None,
  responseStart: Option[Milliseconds] = None,
  responseEnd: Option[Milliseconds] = None,
)
private[fxprof] object NetworkPayloadArgs {
  given JsonValueCodec[NetworkPayloadArgs] = JsonCodecMaker.make
}
