package global.namespace.circe.kafka

import io.circe.generic.auto._
import org.apache.kafka.common.serialization._
import org.scalatest.matchers.should.Matchers._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.wordspec.AnyWordSpec

final class KafkaSpec extends AnyWordSpec {

  "The JSON codec-to-serde adapter" should {
    "support round-trip serialization and deserialization" in {
      val tests = Table(
        "test",
        Test(None, """null"""),
        Test("string", """"string""""),
        Test(1.23, """1.23"""),
        Test(true, """true"""),
        Test(false, """false"""),
        Test(Entity(1), """{"id":1}"""),

        Test(Seq.empty[Unit], """[]"""),

        Test(Seq(None), """[null]"""),
        Test(Seq("string"), """["string"]"""),
        Test(Seq(1.23), """[1.23]"""),
        Test(Seq(true), """[true]"""),
        Test(Seq(false), """[false]"""),
        Test(Seq(Entity(1)), """[{"id":1}]"""),

        Test(Seq(None, Some(Entity(1))), """[null,{"id":1}]"""),
      )
      forAll(tests)(_.run())
    }
  }
}

private final case class Test[A](data: A, json: String)(implicit serde: Serde[A]) {

  import serde._

  def run(): Unit = {
    val bytes = serializer.serialize("some-topic", data)
    val clone = deserializer.deserialize("some-topic", bytes)
    new String(bytes, "UTF-8") shouldBe json
    clone shouldBe data
  }
}

private final case class Entity(id: Long)
