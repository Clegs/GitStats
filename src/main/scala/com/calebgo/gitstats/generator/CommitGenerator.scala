package com.calebgo.gitstats.generator

import scala.sys.process._
import org.joda.time.DateTime
import com.github.nscala_time.time.StaticDateTimeFormat

/**
 * Calculate the total number of commits.
 */
class CommitGenerator extends NormalGenerator {
  override def header = Array("Commits")

  override def valueForDate(date: DateTime, repository: String) = {
    val formatter = StaticDateTimeFormat forPattern "yyyy-MM-dd HH:MM:SS"
    val dateString = formatter print date

    val commits = (Seq("git", "-C", repository, "log", "--before=\"" + dateString + "\"", "--pretty=format:'%h'") #| "wc -l").!!.trim

    Array(commits)
  }
}
