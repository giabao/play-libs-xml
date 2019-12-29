lazy val commonSettings = Seq(
  organization := "com.sandinh",
  version := "1.9.0-SNAPSHOT",
  scalaVersion := "2.13.1",
  crossScalaVersions := Seq("2.13.1", "2.12.10"),
  scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature"),
  scalacOptions ++= (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, 12)) => Seq("-target:jvm-1.8")
    case _ => Nil
  })
)

lazy val `scala-soap` = project
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules"  %% "scala-xml"      % "1.2.0",
      "org.slf4j"               % "slf4j-api"       % "1.7.29",
      "org.specs2"              %% "specs2-core"    % "4.8.1" % Test,
      "ch.qos.logback"          % "logback-classic" % "1.2.3" % Test
    ),
    // Adds a `src/main/scala-2.13+` source directory for Scala 2.13 and newer
    // and a `src/main/scala-2.13-` source directory for Scala version older than 2.13
    unmanagedSourceDirectories in Compile += {
      val sourceDir = (sourceDirectory in Compile).value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
        case _                       => sourceDir / "scala-2.13-"
      }
    }
  )

def play(module: String) = "com.typesafe.play" %% s"play-$module" % "2.8.0"

lazy val `play-soap` = project
  .dependsOn(`scala-soap`)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
//      "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.2",
      play("ahc-ws"),
      play("specs2") % Test,
    )
  )

lazy val `scala-soap-root` = (project in file("."))
  .aggregate(`scala-soap`, `play-soap`)
  .settings(commonSettings: _*)
  .settings(publishArtifact := false)
