/** @author giabao
  * created: 2013-10-30 10:13
  * (c) 2011-2013 sandinh.com */
package com.sandinh.soap

import com.sandinh.xml.{XmlReader, XmlWriter}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws.{WS => PlayWS}
import play.api.http.HeaderNames._
import scala.xml.NamespaceBinding

trait WS[P, R] {
  protected def url: String

  def call(param: P)(implicit w: XmlWriter[P], r: XmlReader[R]): Future[R]

  protected final def call(param: P, ns: NamespaceBinding, hdrs: (String, String)*)   // format: OFF
                          (implicit w: XmlWriter[P], r: XmlReader[R]): Future[R] = { // format: ON
    val s = SOAP.toSoap(param, ns).buildString(stripComments = true)
    //println(s"request: $s")
    val data = ("<?xml version='1.0' encoding='UTF-8'?>" + s).getBytes("UTF-8")
    PlayWS.url(url).
      withHeaders(hdrs :+ (CONTENT_LENGTH -> data.length.toString): _*).
      post(data).
      map { res =>
        //println(s"response: ${res.body}")

        //if we use `val x = res.xml` then `testOnly com.sandinh.soap.WSSpec` sometimes throw
        //ClassCastException: : org.apache.xerces.parsers.XIncludeAwareParserConfiguration cannot be cast to org.apache.xerces.xni.parser.XMLParserConfiguration  (null:-1)
        //@see http://www.ibm.com/developerworks/websphere/library/techarticles/0310_searle/searle.html
        //@see http://xerces.apache.org/xerces2-j/faq-general.html#faq-5
        val x = xml.XML.loadString(res.body)
        SOAP.fromSOAP[R](x).get
      }
  }
}

class SoapWS11[P, R](val url: String, action: String) extends WS[P, R] {
  @inline private def ct = CONTENT_TYPE -> "text/xml; charset=utf-8"
  @inline private def actionHeader = ("SOAPAction", "\"" + action + "\"")

  def call(param: P)(implicit w: XmlWriter[P], r: XmlReader[R]): Future[R] =
    call(param, SOAP.SoapNS, ct, actionHeader)
}

class SoapWS12[P, R](val url: String) extends WS[P, R] {
  @inline private def ct = CONTENT_TYPE -> "application/soap+xml; charset=utf-8"

  def call(param: P)(implicit w: XmlWriter[P], r: XmlReader[R]): Future[R] =
    call(param, SOAP.SoapNS12, ct)
}
