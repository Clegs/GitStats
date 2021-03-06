package com.calebgo.gitstats.generator

import com.calebgo.gitstats.ValueTable

/**
 * Generator run on the ValueTable itself.
 */
trait PostGenerator extends Generator{
  /**
   * Get the headers generated by this generator.
   * @return The headers created by the generator.
   */
  def header: Array[String]
  def valueForIndex(index: Int, valueTable: ValueTable): Array[String]
}
