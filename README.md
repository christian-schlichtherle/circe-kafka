[![Release Notes](https://img.shields.io/github/release/christian-schlichtherle/circe-kafka.svg)](https://github.com/christian-schlichtherle/circe-kafka/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/global.namespace.circe-kafka/circe-kafka_3)](https://search.maven.org/artifact/global.namespace.circe-kafka/circe-kafka_3)
[![Apache License 2.0](https://img.shields.io/github/license/christian-schlichtherle/circe-kafka.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Test Workflow](https://github.com/christian-schlichtherle/circe-kafka/workflows/test/badge.svg)](https://github.com/christian-schlichtherle/circe-kafka/actions?query=workflow%3Atest)

# Circe-Kafka

Provides an implicit conversion from Circe's `Encoder`, `Decoder` and `Codec` types to Kafka's `Serializer`,
`Deserializer` and `Serde` types in order to serialize all data in [JSON format](https://json.org).

> This fork is based on [original work by Tim Steinbach](https://github.com/NeQuissimus/circe-kafka) - kudos to you!
> It has been primarily created to add support for value classes, e.g. `Serde[A] forSome { type A  <: AnyVal }`.
> It also updates dependencies, improves test coverage and is cross-compiled for Scala 2.12, 2.13 and 3.

## Artifact

```scala
libraryDependencies ++= "global.namespace.circe-kafka" %% "circe-kafka" % "2.8.0"
```

Note that this library attempts to match the Kafka version.

## Usage

```scala
// Given:

import io.circe.generic.auto._
import org.apache.kafka.common.serialization._

case class Entity(id: Long)

// When:

import global.namespace.circe.kafka._

val serde: Serde[Foo] = implicitly

import serde._

val bytes = serializer.serialize("some-topic", Entity(1))
val clone = deserializer.deserialize("some-topic", bytes)

// Then:

assert(new String(bytes, "UTF-8") == """{"id":1}""") // look Ma', JSON!
assert(clone == Entity(1))
```
