package nequi.circe.kafka

import io.circe.generic.auto._
import org.apache.kafka.common.serialization._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.wordspec.AnyWordSpec

import java.nio.charset.StandardCharsets.UTF_8

class KafkaSpec extends AnyWordSpec {

  "The JSON codec-to-serde adapter" should {
    "support round-trip serialization and deserialization" in {
      val tests = Table(
        "test",
        Test(None, "null"),
        Test(1, "1"),
        Test("string", """"string""""),
        Test(Seq.empty[Boolean], """[]"""),
        Test(Seq(None), """[null]"""),
        Test(Seq(None, Some(Entity(1))), """[null,{"id":1}]"""),
        Test(Seq(1), """[1]"""),
      )
      forAll(tests)(_.run())
    }
  }
}

private final case class Test[A](data: A, json: String)(implicit serde: Serde[A]) {

  import serde._

  def run(): Unit = {
    val bytes = serializer.serialize("topic", data)
    new String(bytes, UTF_8) shouldBe json
    val clone = deserializer.deserialize("topic", bytes)
    clone shouldBe data
  }
}

private final case class Entity(id: Int)
