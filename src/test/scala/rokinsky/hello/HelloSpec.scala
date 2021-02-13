package rokinsky.hello

import org.scalacheck.Arbitrary._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import rokinsky.hello.Hello._

class HelloSpec extends AnyFlatSpec with ScalaCheckDrivenPropertyChecks {
  "helloMethod" should "work for all strings" in {
    forAll { x: String =>
      helloMethod(x) shouldEqual s"Hello, $x!"
    }
  }

  "helloFunction" should "work for all strings" in {
    forAll { x: String =>
      helloFunction(x) shouldEqual s"Hello, $x!"
    }
  }
}
