package com.sandinh.soap

import java.time.{LocalDate, LocalDateTime, ZoneId, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class SOAPDate(date: ZonedDateTime, dateFormatter: DateTimeFormatter) {
  override def toString: String = dateFormatter.format(date)
  def toDate: ZonedDateTime = date
}

object SOAPDate {
  def apply(date: ZonedDateTime) = new SOAPDate(date, ISO_LOCAL_DATE_TIME)
  def apply(dateText: String) = new SOAPDate(textToDate(dateText), ISO_LOCAL_DATE_TIME)

  def textToDate(dateText: String): ZonedDateTime = {
    if (dateText.length == 10) //"yyyy-MM-dd".length
      LocalDate.parse(dateText).atStartOfDay(ZoneId.systemDefault())
    else
      LocalDateTime.parse(dateText).atZone(ZoneId.systemDefault())
  }
}
