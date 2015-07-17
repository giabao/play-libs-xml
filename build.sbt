name := "scala-soap"

organization := "com.sandinh"

version := "1.4.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature",
  "-Xlint", "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //, "-optimise"
)

libraryDependencies ++= Seq(
  "org.slf4j"               % "slf4j-api"       % "1.7.12",
  "org.scala-lang.modules"  %% "scala-xml"      % "1.0.4",
  "com.typesafe.play"       %% "play-ws"        % "2.4.2"   % Optional,
  "com.typesafe.play"       %% "play-test"      % "2.4.2"   % Test
)

//misc - to mute intellij warning when load sbt project
//@see: sbt command> test:whatDependsOn xalan serializer 2.7.1
dependencyOverrides += "xalan" % "serializer" % "2.7.2" % Test
