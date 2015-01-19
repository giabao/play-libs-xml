name := "scala-soap"

organization := "com.sandinh"

version := "1.3.0"

scalaVersion := "2.11.5"

crossScalaVersions := Seq("2.11.5", "2.10.4")

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature",
  "-Xlint", "-Ywarn-dead-code", "-Ydead-code", "-Yinline-warnings" //, "-optimise"
)

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "org.slf4j"           % "slf4j-api"       % "1.7.10",
  "com.typesafe.play"   %% "play-ws"        % "2.3.7"   % "optional",
  "com.typesafe.play"   %% "play-test"      % "2.3.7"   % "test",
  "org.specs2"          %% "specs2"         % "2.3.13"  % "test"
)

libraryDependencies ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.3")
    case _ => Nil
  }
}
