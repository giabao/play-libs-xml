import com.bluecatcode.play.libs.soap.{SoapFault, SOAP, DefaultImplicits}
import com.bluecatcode.play.libs.xml.{XmlConverter, XmlReader, Xml}
import org.specs2.mutable._
import DefaultImplicits._
import scala.xml.NamespaceBinding

class SOAPSpec extends Specification {

  case class Foo(id: Long, name: String, age: Int, amount: Float, isX: Boolean, opt: Option[Double], abs: Option[String], numbers: List[Int], map: Map[String, Short])

  implicit object FooXmlF extends XmlConverter[Foo] {
    def read(x: xml.NodeSeq): Option[Foo] = {
      for(
        foo     <- (x \ "foo").headOption;
        id      <- Xml.fromXml[Long](foo \ "id");
        name    <- Xml.fromXml[String](foo \ "name");
        age     <- Xml.fromXml[Int](foo \ "age");
        amount  <- Xml.fromXml[Float](foo \ "amount");
        isX     <- Xml.fromXml[Boolean](foo \ "isX");
        opt     <- Xml.fromXml[Option[Double]](foo \ "opt");
        abs     <- Xml.fromXml[Option[String]](foo \ "abs");
        numbers <- Xml.fromXml[List[Int]](foo \ "numbers" \ "nb");
        map     <- Xml.fromXml[Map[String, Short]](foo \ "map" \ "item")
      ) yield Foo(id, name, age, amount, isX, opt, abs, numbers, map)
    }

    def write(f: Foo, base: xml.NodeSeq): xml.NodeSeq = {
      <foo>
        <id>{ f.id }</id>
        <name>{ f.name }</name>
        <age>{ f.age }</age>
        <amount>{ f.amount }</amount>
        <isX>{ f.isX }</isX>
        { Xml.toXml(f.opt, <opt/>) }
        { Xml.toXml(f.abs, <abs/>) }
        <numbers>{ Xml.toXml(f.numbers, <nb/>) }</numbers>
        <map>{ Xml.toXml(f.map, <item/>) }</map>
      </foo>
    }
  }

  val testObject = Foo(
    1234L, "albert", 23, 123.456F, isX = true, Some(987.654), None, List(123, 57), Map("alpha" -> 23.toShort, "beta" -> 87.toShort)
  )

  val testNs = NamespaceBinding(prefix = "test", uri = "http://test.com/", parent = SOAP.SoapNS)

  val testSoapMessage =
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:test="http://test.com/">
      <soapenv:Header/>
      <soapenv:Body>
        <foo>
          <id>1234</id>
          <name>albert</name>
          <age>23</age>
          <amount>123.456</amount>
          <isX>true</isX>
          <opt>987.654</opt>
          <abs xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:nil="true"/>
          <numbers>
            <nb>123</nb>
            <nb>57</nb>
          </numbers>
          <map>
            <item><key>alpha</key><value>23</value></item>
            <item><key>beta</key><value>87</value></item>
          </map>
        </foo>
      </soapenv:Body>
    </soapenv:Envelope>

  "SOAP" should {
    "serialize SOAP" in {
      SOAP.toSoap(
        testObject, testNs
      ) must beEqualTo(
        testSoapMessage
      ).ignoreSpace
    }

    "deserialize SOAP" in {
      SOAP.fromSOAP[Foo](
        testSoapMessage
      ) must equalTo(Some(testObject)
      ).ignoreSpace
    }

    "deserialize SOAP to None if error" in {
      SOAP.fromSOAP[Foo](<foo>
            <id>1234</id>
            <name>123</name>
            <age>fd</age>
            <amount>float</amount>
            <isX>true</isX>
          </foo>
      ) must equalTo(None)
    }

    "deserialize SOAP fault that it previously generated" in {
      val message = SOAP.toSoap(
        SoapFault(
          faultcode = SoapFault.FaultCode.Server,
          faultstring = "Super error",
          faultactor = "http://uriToError.com",
          detail = "erreur"
          )
        )
      val fault = SOAP.fromSOAP[SoapFault[String]](message)
      fault must beSome
      fault.get.faultcode must equalTo(SoapFault.FaultCode.Server)
      fault.get.faultstring must equalTo("Super error")
    }

    "return None if faultcode parameter is missing" in {
      val soapMessage = <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://interfacedepot.acsia.gip_info_retraite.fr/types/">
          <soapenv:Body>
            <soapenv:Fault>
              <soapenv:faultstring xml:lang="?">2</soapenv:faultstring>
              <soapenv:faultactor>3</soapenv:faultactor>
              <detail>Message</detail>
            </soapenv:Fault>
          </soapenv:Body>
        </soapenv:Envelope>
      val res = SOAP.fromSOAP[SoapFault[String]](soapMessage)
      res must beNone
    }
    "return None if faultstring parameter is missing" in {
      val soapMessage = <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://interfacedepot.acsia.gip_info_retraite.fr/types/">
          <soapenv:Body>
            <soapenv:Fault>
              <soapenv:faultcode>1</soapenv:faultcode>
              <soapenv:faultactor>3</soapenv:faultactor>
              <detail>Message</detail>
            </soapenv:Fault>
          </soapenv:Body>
        </soapenv:Envelope>
      val res = SOAP.fromSOAP[SoapFault[String]](soapMessage)
      res must beNone
    }
    "return None if faultactor parameter is missing" in {
      val soapMessage = <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://interfacedepot.acsia.gip_info_retraite.fr/types/">
          <soapenv:Body>
            <soapenv:Fault>
              <soapenv:faultcode>1</soapenv:faultcode>
              <soapenv:faultstring xml:lang="?">2</soapenv:faultstring>
              <detail>
                Message
              </detail>
            </soapenv:Fault>
          </soapenv:Body>
        </soapenv:Envelope>
      val res = SOAP.fromSOAP[SoapFault[String]](soapMessage)
      res must beNone
    }
    "return None if detail parameter is missing" in {
      val soapMessage = <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://interfacedepot.acsia.gip_info_retraite.fr/types/">
          <soapenv:Body>
            <soapenv:Fault>
              <soapenv:faultcode>1</soapenv:faultcode>
              <soapenv:faultstring xml:lang="?">2</soapenv:faultstring>
              <soapenv:faultactor>3</soapenv:faultactor>
            </soapenv:Fault>
          </soapenv:Body>
        </soapenv:Envelope>
      val res = SOAP.fromSOAP[SoapFault[String]](soapMessage)
      res must beNone
    }

    "deserialize SOAP fault generated by third party" in {
      case class ComplexObject(param1 : String, param2: String)
      implicit object ComplexObjectReader extends XmlReader[ComplexObject] {
        def read(x : xml.NodeSeq) : Option[ComplexObject] = {
          for(
            msg <- (x \ "message").headOption;
            param1 <- Xml.fromXml[String](msg \ "param1");
            param2 <- Xml.fromXml[String](msg \ "param2")
          ) yield ComplexObject(param1, param2)
        }        
      }

      val soapMessage = <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:typ="http://interfacedepot.acsia.gip_info_retraite.fr/types/">
          <soapenv:Body>
            <soapenv:Fault>
              <soapenv:faultcode>1</soapenv:faultcode>
              <soapenv:faultstring xml:lang="?">2</soapenv:faultstring>
              <soapenv:faultactor>3</soapenv:faultactor>
              <detail>
                <message>
                  <param1>Textual information</param1>
                  <param2>More information</param2>
                </message>
              </detail>
            </soapenv:Fault>
          </soapenv:Body>
        </soapenv:Envelope>

      val fault = SOAP.fromSOAP[SoapFault[ComplexObject]](soapMessage)
      fault must beSome
      fault.get.faultcode must equalTo("1")
      fault.get.detail.param1 must equalTo("Textual information")
      fault.get.detail.param2 must equalTo("More information")
    }
  }
}
