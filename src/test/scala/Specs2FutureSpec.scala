import org.specs2.concurrent.ExecutionEnv
import org.specs2.mutable.Specification
import scala.concurrent.Future

class Specs2FutureSpec(implicit ee: ExecutionEnv) extends Specification {
  "Specs2" should {
    "fail normal match" >> {
      0 must greaterThan(1) //the test must failed
    }
    "fail future match" >> {
      //the test must failed but NOT in specs2 3.6
      //so we update to specs2 3.6.6 - see build.sbt
      Future.successful(0) must greaterThan(1).await
    }
  }
}
