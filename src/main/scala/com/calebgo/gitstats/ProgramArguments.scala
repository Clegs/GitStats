package com.calebgo.gitstats

import scala.collection.mutable
import scala.sys.process._

/**
 * Verify and parse the arguments sent to the application.
 */
class ProgramArguments(args: Array[String]) {
  private val _switches = new mutable.HashMap[String, String]()

  private val _switchesWithArgument = List("-d")

  var switch: String = null
  for (arg <- args) {
    // Check to see that we are dealing with a switch
    if (arg startsWith "-") {
      if (_switchesWithArgument contains arg) {
        // Our switch should be followed by more data.
        switch = arg
      }
      else {
        val kv = (switch, "")
        // Our switch is just an on/off switch.
        _switches += kv
      }
    }
    else if (switch != null) {
      val kv = (switch, arg)
      _switches += kv
      switch = null
    }
    else {
      val kv = ("repo", arg)
      _switches += kv
    }
  }

  def days = {
    _switches.getOrElse("-d", "30").toInt
  }

  def repo = {
    _switches.getOrElse("repo", "pwd".!!.trim)
  }

  def tag = {
    _switches contains "-t"
  }
}
