package com.calebgo.gitstats

import com.github.nscala_time.time.Imports._

import com.calebgo.gitstats.generator.{NormalGenerator, PostGenerator, Generator}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * Hold the data retrieved from git.
 */
class ValueTable(generators: Array[NormalGenerator], postGenerators: Array[PostGenerator])(implicit arguments: ProgramArguments) {
  /**
   * Store all of the data in the table by generator.
   */
  val table = new mutable.HashMap[Generator, ListBuffer[Array[String]]]()

  val allGenerators = generators ++ postGenerators

  /**
   * Calculate the values of the value table.
   */
  def calculate() = {
    // Do normal processing
    for (generator <- generators; daysBack <- 1 to arguments.days) {
      val dateTime = DateTime.now - daysBack.days
      val value = generator valueForDate dateTime

      val genList = table.getOrElseUpdate(generator, new ListBuffer[Array[String]])
      genList += value
    }

    // Calculated columns on the value table
    for (generator <- postGenerators; daysBack <- 1 to arguments.days) {
      val value = generator valueForIndex(daysBack - 1, this)

      val genList = table.getOrElseUpdate(generator, new ListBuffer[Array[String]])
      genList += value
    }
  }

  /**
   * Print the value table to standard output.
   * @param separator The separator to use between entries.
   */
  def print(separator: String) {
    // Print the headers.
    val headers = new mutable.MutableList[String]
    for (generator <- allGenerators) headers += generator.header mkString separator
    println(headers mkString separator)

    // Print the value table
    for (daysBack <- 1 to arguments.days) {
      val data = new ListBuffer[String]

      for (generator <- allGenerators) {
        val genList = table.getOrElseUpdate(generator, new ListBuffer[Array[String]])
        if (daysBack <= arguments.days) {
          data += genList(daysBack - 1) mkString separator
        }
        else {
          data += "0"
        }
      }

      println(data mkString separator)
    }
  }

  /**
   * Get the value of the given header at the specified index.
   * @param header The header to get the value for.0,0
   * @param index The index in the list to get.
   * @return The value stored in the index at the specified header. "0" if there is nothing stored
   *         at the given index.
   */
  def getValue(header: String, index: Int): String = {
    try {
      // Find the header
      for (generator <- allGenerators) {
        val headers = generator.header
        val headerIndex = headers indexOf header
        if (headerIndex != -1) {
          // We found the header so get the index.
          return table(generator)(index)(headerIndex)
        }
      }
    } catch {
      case e: Exception => return "0"
    }

    "0"
  }
}