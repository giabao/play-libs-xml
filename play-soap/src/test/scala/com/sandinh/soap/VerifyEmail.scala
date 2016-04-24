package com.sandinh.soap

import javax.inject.{Inject, Singleton}
import com.sandinh.xml.{Xml, XmlReader, XmlWriter}
import play.api.libs.ws.WSClient
import scala.xml.NodeSeq
import com.sandinh.soap.DefaultImplicits._

/** @see [[http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx?op=VerifyEmail]] */
object VerifyEmail {
  case class Param(email: String, timeout: Int, LicenseKey: String)
  case class Result(ResponseText: String, ResponseCode: Int, LastMailServer: String, GoodEmail: Boolean)

  implicit object ParamXmlW extends XmlWriter[Param] {
    def write(t: Param, base: NodeSeq): NodeSeq =
      <AdvancedVerifyEmail xmlns="http://ws.cdyne.com/">
        <email>{ t.email }</email>
        <timeout>{ t.timeout }</timeout>
        <LicenseKey>{ t.LicenseKey }</LicenseKey>
      </AdvancedVerifyEmail>
  }

  implicit object ResultXmlR extends XmlReader[Result] {
    def read(x: NodeSeq): Option[Result] =
      for {
        r <- (x \ "AdvancedVerifyEmailResponse" \ "AdvancedVerifyEmailResult").headOption
        txt <- Xml.fromXml[String](r \ "ResponseText")
        code <- Xml.fromXml[Int](r \ "ResponseCode")
        srv <- Xml.fromXml[String](r \ "LastMailServer")
        good <- Xml.fromXml[Boolean](r \ "GoodEmail")
      } yield Result(txt, code, srv, good)
  }

  val url = "http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx"

  object MySoapWS11 extends SoapWS11[Param, Result](
    url,
    "http://ws.cdyne.com/AdvancedVerifyEmail"
  )

  object MySoapWS12 extends SoapWS12[Param, Result](url)
}

import VerifyEmail._

@Singleton
class VerifyEmailWS11 @Inject() (protected val wsClient: WSClient) extends WS11[Param, Result] {
  protected def url = VerifyEmail.url
  protected def action = MySoapWS11.action
}

@Singleton
class VerifyEmailWS12 @Inject() (protected val wsClient: WSClient) extends WS12[Param, Result] {
  protected def url = VerifyEmail.url
}
