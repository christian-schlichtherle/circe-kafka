[![Build Status](https://travis-ci.org/NeQuissimus/circe-kafka.svg?branch=master)](https://travis-ci.org/NeQuissimus/circe-kafka)
![Maven Central](https://maven-badges.herokuapp.com/maven-central/global.namespace.circe-kafka/circe-kafka_2.13/badge.svg)

# Circe-Kafka

Provides an implicit conversion from Circe's `Encoder`, `Decoder` and `Codec` types to Kafka's `Serializer`,
`Deserializer` and `Serde` types in order to serialize all data in [JSON format](https://json.org).

> This is a fork of a repository originally provided by Tim Steinbach - kudos to you!
> It has been primarily created to add support for value classes (i.e. Serde[A] forSome { type A  <: AnyVal }).
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
