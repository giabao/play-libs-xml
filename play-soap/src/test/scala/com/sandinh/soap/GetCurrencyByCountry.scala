package com.sandinh.soap

import javax.inject.{Inject, Singleton}
import com.sandinh.xml.{Xml, XmlReader, XmlWriter}
import play.api.libs.ws.WSClient
import scala.xml.NodeSeq
import com.sandinh.soap.DefaultImplicits._

object GetCurrencyByCountry {
  case class Param(CountryName: String)
  case class Result(GetCurrencyByCountryResult: String)

  implicit object ParamXmlW extends XmlWriter[Param] {
    def write(t: Param, base: NodeSeq): NodeSeq =
      <GetCurrencyByCountry xmlns="http://www.webserviceX.NET">
        <CountryName>{ t.CountryName }</CountryName>
      </GetCurrencyByCountry>
  }

  implicit object ResultXmlR extends XmlReader[Result] {
    def read(x: NodeSeq): Option[Result] =
      for {
        r <- (x \ "GetCurrencyByCountryResponse").headOption
        s <- Xml.fromXml[String](r \ "GetCurrencyByCountryResult")
      } yield Result(s)
  }
}

import GetCurrencyByCountry._

@Singleton
class CurrencyByCountryWS11 @Inject() (protected val wsClient: WSClient) extends WS11[Param, Result] {
  protected def url = "http://www.webservicex.net/country.asmx"
  protected def action = "http://www.webserviceX.NET/GetCurrencyByCountry"
}

@Singleton
class CurrencyByCountryWS12 @Inject() (protected val wsClient: WSClient) extends WS12[Param, Result] {
  protected def url = "http://www.webservicex.net/country.asmx"
}
