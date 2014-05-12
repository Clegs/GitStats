package com.calebgo.gitstats

import scala.sys.process._
import com.calebgo.gitstats.generator._

/**
 * Git statics generator. This program will generate statistics off of a given repository.
 *
 * Program Usage:
 * gitstats [options] [repositories...]
 * For help: gitstats --help
 *
 * If no repository is given then the statistics will be generated off of the current
 * working directory.
 */
object GitStats {
  /**
   * Main entry point to the program.
   * @param args Command line arguments. (See gitstats --help)
   */
  def main(args: Array[String]) {
    if (!gitInstalled) {
      System.err.println("Git is not installed. Exiting.")
      System exit 1
    }

    implicit val config = parseArguments(args)

    val repositoryNameGenerator = new RepositoryNameGenerator
    val dateGenerator = new DateGenerator
    val lineGenerator = new LineGenerator
    val commitGenerator = new CommitGenerator
    val differenceGenerator = new DifferenceGenerator
    val commitDifferenceGenerator = new CommitDifferenceGenerator

    var headerPrinted = false
    for (repository <- config.repositories) {
      val valueTable = new ValueTable(repository, Array(repositoryNameGenerator, dateGenerator, lineGenerator, commitGenerator), Array(differenceGenerator, commitDifferenceGenerator))

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
      head("https://github.com/Clegs/GitStats")

      opt[Int]('d', "days") optional() action { (x, c) => c.copy(days = x) } validate { x => if (x > 0) success else failure("--days must be positive.") } text "Number of days back to generate statistics on. (default = 30)"
      opt[Int]('c', "delta") optional() action { (x, c) => c.copy(delta = x) } validate { x => if (x > 0) success else failure("--delta must be positive.") } text "Number of days to go back by."
      opt[Unit]('t', "today") optional() action { (_, c) => c.copy(today = true) } text "Include today's date. (Defaults to starting yesterday.)"
      opt[Unit]('a', "ascending") optional() action { (_, c) => c.copy(sortAscending = true) } text "Sort from oldest to newest."
      arg[String]("<repository>...") unbounded() optional() action { (x, c) => c.copy(repositories = c.repositories :+ x) } text "Repositories to generate statistics on. The current directory will be used if none is specified."

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
