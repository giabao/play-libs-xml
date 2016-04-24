package com.sandinh.soap

import play.api.libs.ws.{WSClient, WS => PlayWS}
import play.api.Play.current

@deprecated("use WS11 and inject wsClient", "1.6.0")
class SoapWS11[P, R](val url: String, val action: String) extends WS11[P, R] {
  protected def wsClient: WSClient = PlayWS.client
}

@deprecated("use WS12 and inject wsClient", "1.6.0")
class SoapWS12[P, R](val url: String) extends WS12[P, R] {
  protected def wsClient: WSClient = PlayWS.client
}
