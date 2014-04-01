/** @author giabao
  * created: 2013-10-30 11:13
  * (c) 2011-2013 sandinh.com */
package com.sandinh.soap

import org.specs2.mutable._
import com.sandinh.xml.{Xml, XmlReader, XmlWriter}
import com.sandinh.soap.DefaultImplicits._
import scala.xml.NodeSeq
import scala.concurrent.Await
import scala.concurrent.duration._

class WSSpec extends Specification {

  /** @see [[http://www.webservicex.net/CurrencyConvertor.asmx?op=ConversionRate]] */
  object CurrencyConvertor {
    case class Param(FromCurrency: String, ToCurrency: String)
    case class Result(ConversionRateResult: Double)

    implicit object ParamXmlW extends XmlWriter[Param] {
      def write(t: Param, base: NodeSeq): NodeSeq =
        <ConversionRate xmlns="http://www.webserviceX.NET/">
          <FromCurrency>{ t.FromCurrency }</FromCurrency>
          <ToCurrency>{ t.ToCurrency }</ToCurrency>
        </ConversionRate>
    }

    implicit object ResultXmlR extends XmlReader[Result] {
      def read(x: NodeSeq): Option[Result] =
        for (
          r <- (x \ "ConversionRateResponse").headOption;
          rate <- Xml.fromXml[Double](r \ "ConversionRateResult")
        ) yield Result(rate)
    }

    object WS11 extends SoapWS11[Param, Result](
      "http://www.webservicex.net/CurrencyConvertor.asmx",
      "http://www.webserviceX.NET/ConversionRate"
    )

    object WS12 extends SoapWS12[Param, Result]("http://www.webservicex.net/CurrencyConvertor.asmx")
  }

  "WS" should {
    import CurrencyConvertor._

    val param = Param("USD", "VND")

    val timeOut = Duration(2, MINUTES)

    "callable" in {
      //      Try{ Await.result(WS11.call(param), timeOut) } should beSuccessfulTry.which(_.ConversionRateResult > 10000)
      Await.result(WS11.call(param), timeOut).ConversionRateResult should greaterThan(10000.toDouble)
      Await.result(WS12.call(param), timeOut).ConversionRateResult should greaterThan(10000.toDouble)
    }
  }
}
