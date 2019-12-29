/** @author giabao
  * created: 2013-10-30 11:13
  * (c) 2011-2013 sandinh.com
  */
package com.sandinh.soap

import org.specs2.concurrent.ExecutionEnv
import play.api.test.{PlaySpecification, WithApplication}
import scala.concurrent.duration._

class WSSpec(implicit ee: ExecutionEnv) extends PlaySpecification {
  "WS" should {
    val timeOut = 2.minutes
    import Calculator._
    val p = Param(5, 6)
    def test(ws: WS[Param, Result]) =
      ws.call(p).map(_.r) must beEqualTo(p.x + p.y).awaitFor(timeOut)

    "able to call Calculator soap service using soap 1.1" >> new WithApplication {
      test(app.injector.instanceOf[CalculatorWS11])
    }
    "able to call Calculator soap service using soap 1.2" >> new WithApplication {
      test(app.injector.instanceOf[CalculatorWS12])
    }
  }
}
