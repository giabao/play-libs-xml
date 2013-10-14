import sbt._
import Keys._
import org.sbtidea.SbtIdeaPlugin._

object ApplicationBuild extends Build {

  val appName         = "play-libs-xml"
  val appVersion      = "0.4.0-SNAPSHOT"
  val appOrganization = "com.bluecatcode.play.libs.xml"

  val appDependencies = Seq(
    "org.specs2" %% "specs2" % "2.2.3" % "test"
  )

  val appSettings = Defaults.defaultSettings ++ Seq(
    name := appName,
    version := appVersion,
    organization := appOrganization,
    scalaVersion := "2.10.3",
    scalacOptions := Seq(
      "-deprecation",
      "-unchecked",
      "-feature"
    ),
    libraryDependencies ++= appDependencies,
    ideaExcludeFolders := ".idea" :: ".idea_modules" :: Nil
  )

  val myProject = Project(appName, file("."), settings = appSettings)
}
