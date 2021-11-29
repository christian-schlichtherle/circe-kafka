import sbt.Developer
import ReleaseTransformations._

crossScalaVersions := Seq("2.12.15", "2.13.7", "3.1.0")
developers := List(
  Developer(
    "NeQuissimus",
    "Tim Steinbach",
    "steinbach.tim@gmail.com",
    url("http://nequissimus.com/")
  ),
  Developer(
    "christian-schlichtherle",
    "Christian Schlichtherle",
    "christian AT schlichtherle DOT de",
    url("https://schlichtherle.de")
  )
)
homepage := Some(url("https://github.com/christian-schlichtherle/circe-kafka"))
libraryDependencies ++= Seq(
  "io.circe" %% "circe-generic" % "0.14.1" % Test,
  "io.circe" %% "circe-parser" % "0.14.1",
  "org.apache.kafka" % "kafka-clients" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.2.10" % Test,
)
licenses := Seq("Apache License, Version 2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
name := "Circe Kafka"
normalizedName := "circe-kafka"
organization := "global.namespace.circe-kafka"
pomExtra := {
  <issueManagement>
    <system>Github</system>
    <url>https://github.com/christian-schlichtherle/circe-kafka/issues</url>
  </issueManagement>
}
publishTo := sonatypePublishToBundle.value
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("+test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges,
)
sonatypeProfileName := "global.namespace"
scalacOptions := Seq("-deprecation", "-encoding", "UTF-8", "-feature")
scalaVersion := crossScalaVersions.value.last
scmInfo := Some(
  ScmInfo(url("https://github.com/christian-schlichtherle/circe-kafka"), "scm:git:git@github.com:christian-schlichtherle/circe-kafka.git")
)
scmInfo := Some(ScmInfo(
  browseUrl = url("https://github.com/christian-schlichtherle/circe-kafka"),
  connection = "scm:git:git@github.com/christian-schlichtherle/circe-kafka.git",
))
versionScheme := Some("semver-spec")

// http://www.scalatest.org/user_guide/using_scalatest_with_sbt
Test / logBuffered := false
