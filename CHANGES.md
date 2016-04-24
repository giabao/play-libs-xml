### Changelogs
##### v1.6.0
+ update sbt 0.13.11, scala 2.11.8, slf4j-api 1.7.21, joda-time 2.9.3
+ split into `scala-soap` & `play-soap`. Only `play-soap` depend on `play-ws`.
+ update play-ws from 2.4.6 to 2.5.2
+ SoapWS11 & SoapWS12 is now deprecated. Use WS11 and inject wsClient instead.
  Ex, see the test class com.sandinh.soap.VerifyEmailWS12:
```scala
@Singleton
class VerifyEmailWS12 @Inject() (protected val wsClient: WSClient) extends WS12[Param, Result] {
  protected def url = VerifyEmail.url
}
```

##### v1.5.0
+ break binary compatibility. You must re-compile your code when update scala-soap to this version.
+ SOAPDate - moved from using java.util.Date (not thread safe) to org.joda.time.DateTime
+ depend on joda-time is now mandatory, not optional transitively from play-ws

##### v1.4.1
+ update play 2.4.6 (optional dependency), scala-xml 1.0.5, slf4j-api 1.7.13

##### v1.4.0
+ update play 2.4.2 (optional dependency). Note play 2.4.x require java 8

##### v1.3.1
+ only update play 2.3.9, scala 2.11.6, scala-xml 1.0.4
+ remove crossBuild for scala 2.10

##### v1.3.0
+ update scala 2.11.5, sbt 0.13.7, slf4j-api 1.7.10, play-ws (optional dependency) 2.3.7
+ add scala-xml 1.0.3 as a dependency for scala-soap _2.11
+ make explicit result type of implicit defs & add `import scala.language.implicitConversions`
+ remove object BasicReaders, SpecialReaders, BasicWriters, SpecialWriters & remove trait DefaultImplicits

##### v1.2.1
+ update scala 2.11.1, play-ws (optional dependency) 2.3.0-RC2

##### v1.2.0
+ cross compile to scala 2.10 & 2.11
+ update optional dependency `play 2.2.2` to `play-ws 2.3.0-RC1`

##### v1.1.2
+ reformat code using scalariform
+ add traits WS11 & WS12
+ update scala 2.10.4

##### v1.1.1
+ Only update play 2.2.1 to 2.2.2

##### v1.0.0
+ Change package to com.sandinh
+ Add com.sandinh.soap.WS. Usage: see [WSSpec](https://github.com/giabao/scala-soap/blob/master/src/test/scala/com/sandinh/soap/WSSpec.scala)
+ Optional depends on "play" (require if you use WS)

##### v0.9.0
First version.
