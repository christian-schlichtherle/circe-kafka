import sbt.Developer

val mainScala = "2.12.14"

crossScalaVersions := Seq(mainScala, "2.13.6")
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
homepage := Some(url("http://nequissimus.com/"))
libraryDependencies ++= Seq(
  "io.circe" %% "circe-generic" % "0.14.1" % Test,
  "io.circe" %% "circe-parser" % "0.14.1",
  "org.apache.kafka" % "kafka-clients" % "2.8.0",
  "org.scalatest" %% "scalatest" % "3.2.9" % Test,
)
licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
name := "circe-kafka"
organization := "com.nequissimus"
scalacOptions := Seq("-deprecation", "-encoding", "UTF-8", "-feature")
scalaVersion := mainScala
scmInfo := Some(
  ScmInfo(url("https://github.com/NeQuissimus/circe-kafka/"), "scm:git:git@github.com:NeQuissimus/circe-kafka.git")
)

// http://www.scalatest.org/user_guide/using_scalatest_with_sbt
Test / logBuffered := false
Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
