import sbt._
import sbt.Keys._
import org.sbtidea.SbtIdeaPlugin._

object ApplicationBuild extends Build {

  val AppName         = "play-libs-xml"
  val AppVersion      = "0.4.0-SNAPSHOT"
  val AppOrganization = "com.bluecatcode.play.libs.xml"

  val ScalaVersion = "2.10.3"
  val ScalacOptions = Seq(
    "-deprecation",
    "-unchecked",
    "-feature",
    "-Xlint",
    "-encoding", "UTF-8"
  )

  val appDependencies = Seq(
    "com.typesafe" %% "scalalogging-slf4j" % "1.0.1" % "compile",
    "org.specs2" %% "specs2" % "2.2.3" % "test"
  )

  val appSettings = Defaults.defaultSettings ++ Seq(
    name := AppName,
    version := AppVersion,
    organization := AppOrganization,
    scalaVersion := ScalaVersion,
    scalacOptions := ScalacOptions,
    libraryDependencies := appDependencies,
    ideaExcludeFolders := ".idea" :: ".idea_modules" :: Nil,
    licenses := Seq("Apache 2.0 License" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))
  )

  val root = Project(AppName, file("."), settings = appSettings)
}
