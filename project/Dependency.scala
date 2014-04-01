import sbt._

object Dependency {
  object V {
    val slf4j   = "1.7.5"
    val specs   = "2.3.10"
    val play    = "2.2.2"
  }

  private val compile = Seq(
    "org.slf4j"           % "slf4j-api"       % V.slf4j
  )

  private val optional = Seq(
    "com.typesafe.play"   %% "play"           % V.play
  ).map(_ % "optional")

  private val test = Seq(
    "org.specs2"          %% "specs2"         % V.specs
  ).map(_ % "test")

  val all = compile ++ optional ++ test
}
