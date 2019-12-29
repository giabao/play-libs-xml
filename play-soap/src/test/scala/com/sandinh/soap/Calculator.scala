package com.sandinh.soap

import javax.inject.{Inject, Singleton}
import com.sandinh.xml.{Xml, XmlReader, XmlWriter}
import play.api.libs.ws.WSClient
import scala.xml.NodeSeq
import com.sandinh.soap.DefaultImplicits._

/** @see [[http://www.dneonline.com/calculator.asmx?op=Add]] */
object Calculator {
  case class Param(x: Int, y: Int)
  case class Result(r: Int)

  implicit object ParamXmlW extends XmlWriter[Param] {
    def write(t: Param, base: NodeSeq): NodeSeq =
      <Add xmlns="http://tempuri.org/">
        <intA>{t.x}</intA>
        <intB>{t.y}</intB>
      </Add>
  }

  implicit object ResultXmlR extends XmlReader[Result] {
    def read(x: NodeSeq): Option[Result] =
      for {
        res <- (x \ "AddResponse").headOption
        r <- Xml.fromXml[Int](res \ "AddResult")
      } yield Result(r)
  }

  val url = "http://www.dneonline.com/calculator.asmx"
}

import Calculator._

@Singleton
class CalculatorWS11 @Inject() (protected val wsClient: WSClient) extends WS11[Param, Result] {
  protected def url = Calculator.url
  protected def action = "http://tempuri.org/Add"
}

@Singleton
class CalculatorWS12 @Inject() (protected val wsClient: WSClient) extends WS12[Param, Result] {
  protected def url = Calculator.url
}
