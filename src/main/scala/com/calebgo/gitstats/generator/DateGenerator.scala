package com.calebgo.gitstats.generator

import org.joda.time.DateTime
import com.github.nscala_time.time.StaticDateTimeFormat

/**
 * Generator for creating dates.
 */
class DateGenerator extends NormalGenerator {
  override def header: Array[String] = Array("Date")

  override def valueForDate(date: DateTime, repository: String): Array[String] = {
    val formatter = StaticDateTimeFormat.forPattern("dd/MM/yyyy")
    Array(formatter print date)
  }
}
