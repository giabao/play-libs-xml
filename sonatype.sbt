publishMavenStyle := true

publishTo in Global := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

pomExtra in Global := <url>https://github.com/ohze/scala-soap</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:ohze/scala-soap.git</url>
    <connection>scm:git:git@github.com:ohze/scala-soap.git</connection>
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
  </developers>
