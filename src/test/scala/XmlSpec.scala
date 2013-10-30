import org.specs2.mutable._
import com.sandinh.xml.{XmlConverter, Xml}
import com.sandinh.soap.DefaultImplicits._

class XmlSpec extends Specification {
  case class Foo(id: Long, name: String, age: Int, amount: Float, isX: Boolean, opt: Option[Double], numbers: List[Int], map: Map[String, Short])

  implicit object FooXmlF extends XmlConverter[Foo] {
    def read(x: xml.NodeSeq): Option[Foo] = {
      for( id <- Xml.fromXml[Long](x \ "id");
        name <- Xml.fromXml[String](x \ "name");
        age <- Xml.fromXml[Int](x \ "age");
        amount <- Xml.fromXml[Float](x \ "amount");
        isX <- Xml.fromXml[Boolean](x \ "isX");
        opt <- Xml.fromXml[Option[Double]](x \ "opt");
        numbers <- Xml.fromXml[List[Int]](x \ "numbers" \ "nb");
        map <- Xml.fromXml[Map[String, Short]](x \ "map" \ "item")
      ) yield Foo(id, name, age, amount, isX, opt, numbers, map)
    }

    def write(f: Foo, base: xml.NodeSeq): xml.NodeSeq = {
      <foo>
        <id>{ f.id }</id>
        <name>{ f.name }</name>
        <age>{ f.age }</age>
        <amount>{ f.amount }</amount>
        <isX>{ f.isX }</isX>
        { Xml.toXml(f.opt, <opt/>) }
        <numbers>{ Xml.toXml(f.numbers, <nb/>) }</numbers>
        <map>{ Xml.toXml(f.map, <item/>) }</map>
      </foo>
    }
  }

  "Xml" should {
    "serialize XML" in {
        Xml.toXml(Foo(1234L, "albert", 23, 123.456F, isX = true, None, List(123, 57), Map("alpha" -> 23.toShort, "beta" -> 87.toShort))) must beEqualTo(
          <foo>
            <id>1234</id>
            <name>albert</name>
            <age>23</age>
            <amount>123.456</amount>
            <isX>true</isX>
            <opt xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:nil="true" />
            <numbers>
              <nb>123</nb>
              <nb>57</nb>
            </numbers>
            <map>
              <item><key>alpha</key><value>23</value></item>
              <item><key>beta</key><value>87</value></item>
            </map>
          </foo>
        ).ignoreSpace
    }

    "deserialize XML with option nil=true" in {
      Xml.fromXml[Foo](<foo>
            <id>1234</id>
            <name>albert</name>
            <age>23</age>
            <amount>123.456</amount>
            <isX>true</isX>
            <opt xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:nil="true" />
            <numbers>
              <nb>123</nb>
              <nb>57</nb>
            </numbers>
            <map>
              <item><key>alpha</key><value>23</value></item>
              <item><key>beta</key><value>87</value></item>
            </map>
          </foo>) must equalTo(Some(Foo(1234L, "albert", 23, 123.456F, isX = true, None, List(123, 57), Map("alpha" -> 23.toShort, "beta" -> 87.toShort))))
    }

    "deserialize XML" in {
      Xml.fromXml[Foo](<foo>
            <id>1234</id>
            <name>albert</name>
            <age>23</age>
            <amount>123.456</amount>
            <isX>true</isX>
            <numbers>
              <nb>123</nb>
              <nb>57</nb>
            </numbers>
            <map>
              <item><key>alpha</key><value>23</value></item>
              <item><key>beta</key><value>87</value></item>
            </map>
          </foo>) must equalTo(Some(Foo(1234L, "albert", 23, 123.456F, isX = true, None, List(123, 57), Map("alpha" -> 23.toShort, "beta" -> 87.toShort))))
    }

    "deserialize XML to None if error" in {
      Xml.fromXml[Foo](<foo>
            <id>1234</id>
            <name>123</name>
            <age>fd</age>
            <amount>float</amount>
            <isX>true</isX>
          </foo>) must equalTo(None)
    }

    "deserialize Int accordingly to Some or None" in {
      Xml.fromXml[Int](<ab>123</ab>) must equalTo(Some(123))
      Xml.fromXml[Int](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Int](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Short accordingly to Some or None" in {
      Xml.fromXml[Short](<ab>123</ab>) must equalTo(Some(123))
      Xml.fromXml[Short](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Short](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Long accordingly to Some or None" in {
      Xml.fromXml[Long](<ab>123</ab>) must equalTo(Some(123))
      Xml.fromXml[Long](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Long](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Float accordingly to Some or None" in {
      Xml.fromXml[Float](<ab>123</ab>) must equalTo(Some(123))
      Xml.fromXml[Float](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Float](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    "deserialize Double accordingly to Some or None" in {
      Xml.fromXml[Double](<ab>123</ab>) must equalTo(Some(123))
      Xml.fromXml[Double](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Double](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    "deserialize Boolean accordingly to Some or None" in {
      Xml.fromXml[Boolean](<ab>true</ab>) must equalTo(Some(true))
      Xml.fromXml[Boolean](<ab>abc</ab>) must equalTo(None)
      Xml.fromXml[Boolean](<ab>12</ab> \\ "tag") must equalTo(None)
    }
  }
}
