import org.specs2.mutable._
import com.sandinh.soap.SOAPDate
import java.time.LocalDateTime
import java.time.Instant.EPOCH
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeParseException

class SOAPDateSpec extends Specification {

  "SOAPDate" should {

    "parse SOAP format datetime yyyy-MM-dd'T'HH:mm:ss" in {
      SOAPDate("1970-01-01T00:00:01").toString === "1970-01-01T00:00:01"
      SOAPDate("1970-02-01T00:00:00").toString === "1970-02-01T00:00:00"
      SOAPDate("1970-01-01T00:00:00").toString === "1970-01-01T00:00:00"
    }

    "parse SOAP format date yyyy-MM-dd only" in {
      SOAPDate("1970-01-01").toString === "1970-01-01T00:00:00"
    }

    "parse SOAP format LocalDateTime from UNIX 0" in {
      SOAPDate(LocalDateTime.ofInstant(EPOCH, UTC)).toString === "1970-01-01T00:00:00"
    }

    "throw DateTimeParseException on invalid format" in {
      SOAPDate("bzdura") must throwA[DateTimeParseException]
    }
  }
}
