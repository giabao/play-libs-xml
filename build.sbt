name := "scala-soap"

organization := "com.sandinh"

version := "2.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8", "-Ybackend:GenBCode")

resolvers += Resolver.bintrayRepo("scalaz", "releases")

libraryDependencies ++= Seq(
  "org.slf4j"               % "slf4j-api"       % "1.7.13",
  "org.scala-lang.modules"  %% "scala-xml"      % "1.0.5",
  "com.typesafe.play"       %% "play-ws"        % "2.4.6"   % Optional,
  "org.specs2"              %% "specs2-junit"   % "3.6.6"   % Test, //see Specs2FutureSpec
  "org.specs2"              %% "specs2-mock"    % "3.6.6"   % Test, //see Specs2FutureSpec
  "com.typesafe.play"       %% "play-specs2"    % "2.4.6"   % Test
)

//misc - to mute intellij warning when load sbt project
//@see: sbt command> test:whatDependsOn xalan serializer 2.7.1
dependencyOverrides ++= Set(
  "xalan" % "serializer" % "2.7.2",
  "org.scala-lang.modules"  %% "scala-parser-combinators" % "1.0.4",
  "org.scala-lang.modules"  %% "scala-xml" % "1.0.5",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

testOptions in Test += Tests.Exclude(Seq("Specs2FutureSpec"))
