package com.sandinh.soap

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME

class SOAPDate(date: LocalDateTime, dateFormatter: DateTimeFormatter) {
  override def toString: String = dateFormatter.format(date)
  def toDate: LocalDateTime = date
}

object SOAPDate {
  def apply(date: LocalDateTime) = new SOAPDate(date, ISO_LOCAL_DATE_TIME)
  def apply(dateText: String) = new SOAPDate(textToDate(dateText), ISO_LOCAL_DATE_TIME)

  def textToDate(dateText: String): LocalDateTime = {
    if (dateText.length == 10) //"yyyy-MM-dd".length
      LocalDate.parse(dateText).atStartOfDay()
    else
      LocalDateTime.parse(dateText)
  }
}
