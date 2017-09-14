lazy val commonSettings = Seq(
  organization := "com.sandinh",
  version := "1.7.0",
  scalaVersion := "2.12.3",
  crossScalaVersions := Seq("2.12.3", "2.11.11"),
  scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8"),
  scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 11)) => Seq("-Ybackend:GenBCode")
    case _ => Nil
  })
)

val slf4jBind = "org.slf4j" % "slf4j-simple" % "1.7.25" % Test

lazy val `scala-soap` = project
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules"  %% "scala-xml"      % "1.0.6",
      "org.slf4j"               % "slf4j-api"       % "1.7.25",
      "org.specs2"              %% "specs2-core"    % "3.9.5" % Test,
      slf4jBind
    )
  )

def play(module: String) = "com.typesafe.play" %% s"play-$module" % "2.6.3"

lazy val `play-soap` = project
  .dependsOn(`scala-soap`)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      play("ahc-ws"),
      play("specs2") % Test,
      slf4jBind
    )
  )

lazy val `scala-soap-root` = (project in file("."))
  .aggregate(`scala-soap`, `play-soap`)
  .settings(commonSettings: _*)
  .settings(publishArtifact := false)
