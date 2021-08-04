package nequi.circe

import io.circe.parser.decode
import io.circe.{Decoder, Encoder}
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serdes, Serializer}

package object kafka {

  private val stringSerde = Serdes.String

  import stringSerde._

  implicit def encoderToSerializer[A: Encoder]: Serializer[A] = { (topic: String, data: A) =>
    serializer.serialize(topic, Encoder[A].apply(data).noSpaces)
  }

  implicit def decoderToDeserializer[A: Decoder]: Deserializer[A] = { (topic: String, data: Array[Byte]) =>
    decode[A](deserializer.deserialize(topic, data)).fold(error => throw new SerializationException(error), identity)
  }

  implicit def serializerAndDeserializerToSerde[A: Encoder : Decoder]: Serde[A] = {
    Serdes.serdeFrom(implicitly, implicitly)
  }
}
