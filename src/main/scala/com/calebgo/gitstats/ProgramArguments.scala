package com.calebgo.gitstats

import scala.collection.mutable
import scala.sys.process._

/**
 * Verify and parse the arguments sent to the application.
 */
class ProgramArguments(args: Array[String]) {
  private val _switches = new mutable.HashMap[String, String]()
  
  var switch: String = null
  for (arg <- args) {
    if (arg startsWith "-") {
      switch = arg
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
    _switches.getOrElse("repo", "pwd".!!)
  }
}
