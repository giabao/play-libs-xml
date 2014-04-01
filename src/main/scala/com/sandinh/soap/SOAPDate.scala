package com.sandinh.soap

import java.util.Date
import java.text.{ParseException, SimpleDateFormat, DateFormat}

class SOAPDate(date: Date, dateFormatter: DateFormat) {
  override def toString = dateFormatter.format(date)
  def toDate = date
}

object SOAPDate {
  val shortFormatTemplate = "yyyy-MM-dd"
  val longFormatTemplate = "yyyy-MM-dd'T'HH:mm:ss"
  val shortDateFormatter: DateFormat = new SimpleDateFormat(shortFormatTemplate)
  val longDateFormatter: DateFormat = new SimpleDateFormat(longFormatTemplate)

  def apply(date: Date) = new SOAPDate(date, longDateFormatter)
  def apply(dateText: String) = new SOAPDate(textToDate(dateText), longDateFormatter)

  def textToDate(dateText: String) = {
    try {
      if (dateText.size == shortFormatTemplate.size)
        shortDateFormatter.parse(dateText)
      else
        longDateFormatter.parse(dateText)
    } catch {
      case e: ParseException =>
        throw new IllegalArgumentException(
          s"Expected $dateText to be in supported date format ($longFormatTemplate or $shortFormatTemplate)"
        )
    }
  }
}
