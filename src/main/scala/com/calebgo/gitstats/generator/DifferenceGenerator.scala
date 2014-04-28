package com.calebgo.gitstats.generator

import com.calebgo.gitstats.{ValueTable, ProgramArguments}

/**
 * Generate file and line differences on a daily basis.
 */
class DifferenceGenerator(implicit arguments: ProgramArguments) extends PostGenerator {
  override def header: Array[String] = Array("LineDifference", "FileDifference")

  override def valueForIndex(index: Int, valueTable: ValueTable): Array[String] = {
    val currentLineValue = valueTable.getValue("Lines", index).toInt
    val lastLineValue = valueTable.getValue("Lines", index + 1).toInt

    val lineDifference = currentLineValue - lastLineValue

    val currentFileValue = valueTable.getValue("Files", index).toInt
    val lastFileValue = valueTable.getValue("Files", index + 1).toInt

    val fileDifference = currentFileValue - lastFileValue

    Array(lineDifference.toString, fileDifference.toString)
  }
}
