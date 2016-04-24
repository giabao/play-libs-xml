package com.sandinh.soap

import javax.inject.{Inject, Singleton}
import com.sandinh.xml.{Xml, XmlReader, XmlWriter}
import play.api.libs.ws.WSClient
import scala.xml.NodeSeq
import com.sandinh.soap.DefaultImplicits._

/** @see [[http://www.service-repository.com/operation/show?operation=add&portType=ICalculator&id=12]] */
object Calculator {
  case class Param(x: Float, y: Float)
  case class Result(r: Float)

  implicit object ParamXmlW extends XmlWriter[Param] {
    def write(t: Param, base: NodeSeq): NodeSeq =
      <ns1:add xmlns:ns1="http://www.parasoft.com/wsdl/calculator/">
        <ns1:x>{ t.x }</ns1:x>
        <ns1:y>{ t.y }</ns1:y>
      </ns1:add>
  }

  implicit object ResultXmlR extends XmlReader[Result] {
    def read(x: NodeSeq): Option[Result] =
      for {
        res <- (x \ "addResponse").headOption
        r <- Xml.fromXml[Float](res \ "Result")
      } yield Result(r)
  }

  val url = "http://ws1.parasoft.com/glue/calculator"

  object MySoapWS11 extends SoapWS11[Param, Result](
    url,
    "add"
  )

  object MySoapWS12 extends SoapWS12[Param, Result](url)
}

import Calculator._

@Singleton
class CalculatorWS11 @Inject() (protected val wsClient: WSClient) extends WS11[Param, Result] {
  protected def url = Calculator.url
  protected def action = MySoapWS11.action
}

@Singleton
class CalculatorWS12 @Inject() (protected val wsClient: WSClient) extends WS12[Param, Result] {
  protected def url = Calculator.url
}
