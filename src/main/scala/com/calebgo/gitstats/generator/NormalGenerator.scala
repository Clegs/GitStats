package com.calebgo.gitstats.generator

import org.joda.time.DateTime

/**
 * Generator that creates data outside of the value table
 */
trait NormalGenerator extends Generator {
  def valueForDate(date: DateTime, repository: String): Array[String]
}
