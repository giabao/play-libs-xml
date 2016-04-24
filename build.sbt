import scalariform.formatter.preferences._

lazy val formatSetting = scalariformPreferences := scalariformPreferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(MultilineScaladocCommentsStartOnFirstLine, true)
  //  .setPreference(ScaladocCommentsStopOnLastLine, true)
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
  .setPreference(SpacesAroundMultiImports, false)

lazy val commonSettings = formatSetting +: Seq(
  organization := "com.sandinh",
  version := "1.6.0-SNAPSHOT",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-feature", "-target:jvm-1.8", "-Ybackend:GenBCode")
)

val slf4jBind = "org.slf4j" % "slf4j-simple" % "1.7.21"

lazy val `scala-soap` = project
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang.modules"  %% "scala-xml"      % "1.0.5",
      "org.slf4j"               % "slf4j-api"       % "1.7.21",
      "joda-time"               % "joda-time"       % "2.9.3",
      "org.joda"                % "joda-convert"    % "1.8.1",
      "org.specs2"              %% "specs2-core"    % "3.6.6" % Test,
      slf4jBind % Test
    )
  )

def play(module: String) = "com.typesafe.play" %% s"play-$module" % "2.5.2"

lazy val `play-soap` = project
  .dependsOn(`scala-soap`)
  .settings(commonSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      play("ws"), play("specs2") % Test,
      slf4jBind % Test
    )
  )

lazy val root = (project in file("."))
  .aggregate(`scala-soap`, `play-soap`)
