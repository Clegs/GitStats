package com.calebgo.gitstats

import scala.sys.process._
import com.calebgo.gitstats.generator.{RepositoryNameGenerator, DifferenceGenerator, LineGenerator, DateGenerator}

/**
 * Git statics generator. This program will generate statistics.
 *
 * Program Usage:
 * GitStats [-d days] [-t] [repository]
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

    implicit val config = parseArguments(args)

    val repositoryNameGenerator = new RepositoryNameGenerator
    val dateGenerator = new DateGenerator
    val lineGenerator = new LineGenerator
    val differenceGenerator = new DifferenceGenerator

    var headerPrinted = false
    for (repository <- config.repositories) {
      val valueTable = new ValueTable(repository, Array(repositoryNameGenerator, dateGenerator, lineGenerator), Array(differenceGenerator))

      if (!headerPrinted) {
        valueTable printHeader ","
        headerPrinted = true
      }

      valueTable.calculate()

      valueTable print ","
    }
  }

  /**
   * Verify that git is installed on the system.
   * @return True if git is installed and accessible through the PATH. False otherwise.
   */
  def gitInstalled = "which git" ! ProcessLogger(line => ()) == 0

  /**
   * Parse the arguments and return the configuration.
   * @param args The arguments passed to the application.
   * @return The parsed configuration for the application.
   */
  def parseArguments(args: Array[String]) = {
    val parser = new scopt.OptionParser[Config]("GitStats") {
      head("GitStats", getClass.getPackage.getImplementationVersion)
      opt[Int]('d', "days") optional() action { (x, c) => c.copy(days = x) } validate { x => if (x > 0) success else failure("--days must be positive.") } text "Number of days back to generate statistics on. (default = 30)"
      opt[Int]("delta") optional() action { (x, c) => c.copy(delta = x) } validate { x => if (x > 0) success else failure("--delta must be positive.") } text "Number of days to go back by."
      opt[Unit]("today") optional() action { (_, c) => c.copy(today = true) } text "Include today's date. (Defaults to starting yesterday.)"
      arg[String]("<repository>...") unbounded() optional() action { (x, c) => c.copy(repositories = c.repositories :+ x) } text "Repositories generate statistics on."
      help("help") text "Prints this usage text."
    }

    parser.parse(args, Config()) map { config =>
      if (config.repositories.length == 0) config.copy(repositories = config.repositories :+ "pwd".!!.trim)
      else config
    } getOrElse {
      System exit 2
      null
    }
  }
}
