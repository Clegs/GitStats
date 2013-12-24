package com.calebgo.gitstats

import java.io.File

object GitStats {
  def main(args: Array[String]) {
    if (checkArgs(args)) return

    // The arguments are fine so load any options.
    val options = new CommandOptions(args)


    val git = new Git(args(0))

  }

  def checkArgs(args: Array[String]): Boolean = {
    if (args.length < 2) {
      println("Wrong number of arguments.")
      println("Usage: GitStats git-repository out.csv")
      return false
    }

    // Check to see if the file exists.
    if(!new File(args(args length - 2)).exists()) {
      println("The git directory does not exist at the given location.")
      return false
    }

    // Everything looks good. Continue to get statistics.
    true
  }
}
