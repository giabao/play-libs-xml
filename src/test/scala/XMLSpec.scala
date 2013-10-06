import org.specs2.mutable._
import play2.tools.xml._
import play2.tools.xml.DefaultImplicits._

class XMLSpec extends Specification {
  case class Foo(id: Long, name: String, age: Int, amount: Float, isX: Boolean, opt: Option[Double], numbers: List[Int], map: Map[String, Short])

  implicit object FooXMLF extends XMLConverter[Foo] {
    def read(x: xml.NodeSeq): Option[Foo] = {
      for( id <- XML.fromXML[Long](x \ "id");
        name <- XML.fromXML[String](x \ "name");
        age <- XML.fromXML[Int](x \ "age");
        amount <- XML.fromXML[Float](x \ "amount");
        isX <- XML.fromXML[Boolean](x \ "isX");
        opt <- XML.fromXML[Option[Double]](x \ "opt");
        numbers <- XML.fromXML[List[Int]](x \ "numbers" \ "nb");
        map <- XML.fromXML[Map[String, Short]](x \ "map" \ "item")
      ) yield Foo(id, name, age, amount, isX, opt, numbers, map)
    }

    def write(f: Foo, base: xml.NodeSeq): xml.NodeSeq = {
      <foo>
        <id>{ f.id }</id>
        <name>{ f.name }</name>
        <age>{ f.age }</age>
        <amount>{ f.amount }</amount>
        <isX>{ f.isX }</isX>
        { XML(f.opt, <opt/>) }
        <numbers>{ XML(f.numbers, <nb/>) }</numbers>
        <map>{ XML(f.map, <item/>) }</map>
      </foo>
    }
  }

  "XML" should {
    "serialize XML" in {
        XML(Foo(1234L, "albert", 23, 123.456F, isX = true, None, List(123, 57), Map("alpha" -> 23.toShort, "beta" -> 87.toShort))) must beEqualTo(
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
      XML.fromXML[Foo](<foo>
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
      XML.fromXML[Foo](<foo>
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
      XML.fromXML[Foo](<foo>
            <id>1234</id>
            <name>123</name>
            <age>fd</age>
            <amount>float</amount>
            <isX>true</isX>
          </foo>) must equalTo(None)
    }

    "deserialize Int accordingly to Some or None" in {
      XML.fromXML[Int](<ab>123</ab>) must equalTo(Some(123))
      XML.fromXML[Int](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Int](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Short accordingly to Some or None" in {
      XML.fromXML[Short](<ab>123</ab>) must equalTo(Some(123))
      XML.fromXML[Short](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Short](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Long accordingly to Some or None" in {
      XML.fromXML[Long](<ab>123</ab>) must equalTo(Some(123))
      XML.fromXML[Long](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Long](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    
    "deserialize Float accordingly to Some or None" in {
      XML.fromXML[Float](<ab>123</ab>) must equalTo(Some(123))
      XML.fromXML[Float](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Float](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    "deserialize Double accordingly to Some or None" in {
      XML.fromXML[Double](<ab>123</ab>) must equalTo(Some(123))
      XML.fromXML[Double](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Double](<ab>12</ab> \\ "tag") must equalTo(None)
    }
    "deserialize Boolean accordingly to Some or None" in {
      XML.fromXML[Boolean](<ab>true</ab>) must equalTo(Some(true))
      XML.fromXML[Boolean](<ab>abc</ab>) must equalTo(None)
      XML.fromXML[Boolean](<ab>12</ab> \\ "tag") must equalTo(None)
    }
  }
}
