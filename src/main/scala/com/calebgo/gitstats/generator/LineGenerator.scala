package com.calebgo.gitstats.generator

import scala.sys.process._
import com.calebgo.gitstats.ProgramArguments
import org.joda.time.DateTime
import com.github.nscala_time.time.StaticDateTimeFormat

/**
 * Generator for calculating the number of lines in a program.
 */
class LineGenerator(implicit arguments: ProgramArguments) extends NormalGenerator {
  override def header: Array[String] = Array("Lines", "Files")

  override def valueForDate(date: DateTime): Array[String] = {
    // Get the data from the git repository.
    val formatter = StaticDateTimeFormat forPattern "yyyy-MM-dd HH:MM:SS"
    val dateString = formatter print date
    val hash = ("git -C " + arguments.repo + " hash-object -t tree /dev/null").!!.trim
    val dateHash = Seq("git", "-C", arguments.repo, "rev-list", "-n1", "--before=\"" + dateString + "\"", "master").!!.trim
    val diffOutput = (Seq("git", "-C", arguments.repo, "diff", "--stat", hash, dateHash) #| Seq("tail", "-1")).!!.trim

    val responseRegex = """(\d+).*, (\d+).*""".r
    try {
      val responseRegex(files, lines) = diffOutput

      Array(lines, files)
    }
    catch {
      case e: Exception => Array("0", "0") // There is no data for this day.
    }
  }
}
