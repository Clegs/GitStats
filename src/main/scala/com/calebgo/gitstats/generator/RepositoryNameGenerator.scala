package com.calebgo.gitstats.generator

import org.joda.time.DateTime

/**
 * Output the name of the repository.
 */
class RepositoryNameGenerator extends NormalGenerator{
  override def header: Array[String] = Array("Repository")

  override def valueForDate(date: DateTime, repository: String): Array[String] = {
    val truncatedRepository = if (repository endsWith "/") repository.substring(0, repository.length - 1)
                              else repository

    Array(truncatedRepository.substring((truncatedRepository lastIndexOf '/') + 1))
  }
}
