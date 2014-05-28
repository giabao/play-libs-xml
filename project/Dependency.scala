import sbt._

object Dependency {
  object V {
    val slf4j   = "1.7.6"
    val specs   = "2.3.12"
    val play    = "2.3.0-RC2"
  }

  private val compile = Seq(
    "org.slf4j"           % "slf4j-api"       % V.slf4j
  )

  private val optional = Seq(
    "com.typesafe.play"   %% "play-ws"        % V.play
  ).map(_ % "optional")

  private val test = Seq(
    "com.typesafe.play"   %% "play-test"      % V.play,
    "org.specs2"          %% "specs2"         % V.specs
  ).map(_ % "test")

  val all = compile ++ optional ++ test
}
