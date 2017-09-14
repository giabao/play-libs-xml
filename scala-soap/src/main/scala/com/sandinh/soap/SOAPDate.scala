package com.sandinh.soap

import java.time.{LocalDate, LocalDateTime}
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.{ISO_DATE, ISO_LOCAL_DATE_TIME}

class SOAPDate(date: LocalDateTime, dateFormatter: DateTimeFormatter) {
  override def toString: String = date.format(dateFormatter)
  def toDate: LocalDateTime = date
}

object SOAPDate {
  def apply(date: LocalDateTime) = new SOAPDate(date, ISO_LOCAL_DATE_TIME)
  def apply(dateText: String) = new SOAPDate(textToDate(dateText), ISO_LOCAL_DATE_TIME)

  def textToDate(dateText: String): LocalDateTime = {
    if (dateText.length == 10) //"yyyy-MM-dd".length
      LocalDate.parse(dateText, ISO_DATE).atStartOfDay()
    else
      LocalDateTime.parse(dateText, ISO_LOCAL_DATE_TIME)
  }
}
