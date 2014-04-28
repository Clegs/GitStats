package com.calebgo.gitstats

import scala.sys.process._
import com.calebgo.gitstats.generator.{DifferenceGenerator, LineGenerator, DateGenerator}

/**
 * Git statics generator. This program will generate statistics.
 *
 * Program Usage:
 * GitStats [-d days] [repository]
 *
 * If no repository is given then the statistics will be generated off of the current
 * working directory.
 */
object GitStats {
  def main(args: Array[String]) {
    if (!gitInstalled) {
      System.err.println("Git is not installed. Exiting.")
      System exit 1
    }

    implicit val arguments = new ProgramArguments(args)

    val dateGenerator = new DateGenerator
    val lineGenerator = new LineGenerator
    val differenceGenerator = new DifferenceGenerator

    val valueTable = new ValueTable(Array(dateGenerator, lineGenerator), Array(differenceGenerator))

    valueTable.calculate()

    valueTable print ","
  }

  /**
   * Verify that git is installed on the system.
   * @return True if git is installed and accessible through the PATH. False otherwise.
   */
  def gitInstalled = "which git" ! ProcessLogger(line => ()) == 0
}
