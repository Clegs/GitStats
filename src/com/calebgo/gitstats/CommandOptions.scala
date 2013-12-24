package com.calebgo.gitstats

/**
 * The command options passed through the command line.
 */
class CommandOptions(args: Array[String]) {
  processArguments(args)

  // Field Variables
  private var _maxDays = 30

  def maxDays = _maxDays

  protected def processArguments(args: Array[String]) {
    for (arg <- args) {
      if (arg startsWith "--") {
        // The argument is a name.
        val keyVal = processKeyValueArgument(arg)
        if ((keyVal length) >= 1) {
          // There is a key.
          val key = keyVal(0)
          val value =  if ((keyVal length) >= 1) keyVal(1) else null

          // Iterate through all of the possible key/value combinations.

          if ((key equals "max-days") && value != null) {
            _maxDays = value.toInt
          }
        }
      }
      else if (arg startsWith "-") {
        // the argument is a character flag.
      }
    }
  }

  /**
   * Process an argument that starts with double dashes --
   * @param arg Argument that starts with two dashes.
   * @return An array with either one or two values. The first is the key and the
   */
  protected def processKeyValueArgument(arg: String) : Array[String] = {
    val trimmed = arg substring 2 // Remove the leading --
    trimmed split '='
  }
}
