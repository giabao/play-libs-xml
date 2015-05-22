name := "scala-soap"

organization := "com.sandinh"

version := "1.3.1"

scalaVersion := "2.11.6"

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature",
  "-Xlint", "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //, "-optimise"
)

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "org.slf4j"               % "slf4j-api"       % "1.7.12",
  "org.scala-lang.modules"  %% "scala-xml"      % "1.0.4",
  "com.typesafe.play"       %% "play-ws"        % "2.3.9"   % "optional",
  "com.typesafe.play"       %% "play-test"      % "2.3.9"   % "test"
)
