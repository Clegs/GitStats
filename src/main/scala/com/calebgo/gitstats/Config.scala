package com.calebgo.gitstats

/**
 * Store application configuration.
 * @param days The number of days to go back.
 * @param delta The amount of days to skip. (Example: 7 would generate on a weekly basis.)
 * @param today Include today in the statistics.
 * @param sortAscending Put the most recent dates on the bottom.
 * @param repositories A sequence of repository directories to generate statistics on.
 */
case class Config(days: Int = 30,
                  delta: Int = 1,
                  today: Boolean = false,
                  sortAscending: Boolean = false,
                  repositories: Seq[String] = Seq()
                 )
