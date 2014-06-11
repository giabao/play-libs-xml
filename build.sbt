name := "scala-soap"

organization := "com.sandinh"

version := "1.2.2"

scalaVersion := "2.11.1"

crossScalaVersions := Seq("2.11.1", "2.10.4")

scalacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-deprecation", "-unchecked", "-feature",
  "-Xlint",
  "-Yinline-warnings", // "-optimise"
  "-Xmigration", //"–Xverify", "-Xcheck-null", "-Ystatistics",
  "-Ywarn-dead-code", "-Ydead-code"
)

javacOptions ++= Seq(
  "-encoding", "UTF-8",
  "-source", "1.7",
  "-target", "1.7",
  "-Xlint:unchecked", "-Xlint:deprecation"
)

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Dependency.all
