name := "scala-soap"

organization := "com.sandinh"

version := "1.1.0"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", "-Xlint", "-Yinline-warnings"/*, "-optimise"*/)

javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint:unchecked", "-Xlint:deprecation")

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases"

libraryDependencies ++= Seq(
  "org.slf4j"           % "slf4j-api"       % "1.7.5",
  "com.typesafe.play"   %% "play"           % "2.2.0"   % "optional",
  "org.specs2"          %% "specs2"         % "2.2.3"   % "test"
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/giabao/scala-soap</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:giabao/scala-soap.git</url>
    <connection>scm:git:git@github.com:giabao/scala-soap.git</connection>
  </scm>
  <developers>
    <developer>
      <id>giabao</id>
      <name>Gia Bảo</name>
      <email>giabao@sandinh.net</email>
      <organization>Sân Đình</organization>
      <organizationUrl>http://sandinh.com</organizationUrl>
    </developer>
    <developer>
      <id>pawelprazak</id>
      <name>Paweł Prażak</name>
    </developer>
    <developer>
      <id>mandubian</id>
      <name>Pascal Voitot</name>
      <email>pascal.voitot.dev@gmail.com</email>
      <url>http://www.mandubian.com</url>
    </developer>
    <developer>
      <name>Étienne Vallette d'Osia</name>
    </developer>
  </developers>)
