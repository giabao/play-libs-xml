/** @author giabao
  * created: 2013-10-30 11:13
  * (c) 2011-2013 sandinh.com
  */
package com.sandinh.soap

import com.sandinh.xml.Xml
import scala.concurrent.duration._
import play.api.test.{WithApplication, PlaySpecification}
import org.specs2.concurrent.ExecutionEnv
import com.sandinh.soap.DefaultImplicits._

class WSSpec(implicit ee: ExecutionEnv) extends PlaySpecification {
  val timeOut = 2.minutes

  //  def xmlFromEscaped(s: String) = {
  //    import scala.xml.XML
  //    val x = XML.loadString(s"<foo>$s</foo>")
  //    XML.loadString(x.text)
  //  }

  "WS" should {
    "callable Calculator" >> new WithApplication {
      import Calculator._
      val p = Param(0.5f, 6.34f)
      def test(ws: WS[Param, Result]) =
        ws.call(p).map(_.r) must beEqualTo(p.x + p.y).awaitFor(timeOut)

      test(app.injector.instanceOf[CalculatorWS11])
      test(app.injector.instanceOf[CalculatorWS12])

      test(MySoapWS11)
      test(MySoapWS12)
    }

    "callable GetCurrencyByCountry" >> new WithApplication {
      import GetCurrencyByCountry._
      def test(ws: WS[Param, Result]) = ws.call(Param("vietnam"))
        .map(r => scala.xml.XML.loadString(r.GetCurrencyByCountryResult))
        .map(x => Xml.fromXml[String]((x \ "Table").head \ "CurrencyCode")) must beSome("VND").awaitFor(timeOut)

      test(app.injector.instanceOf[CurrencyByCountryWS11])
      test(app.injector.instanceOf[CurrencyByCountryWS12])

      test(MySoapWS11)
      test(MySoapWS12)
    }
  }
}
