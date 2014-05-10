package com.calebgo.gitstats

case class Config(days: Int = 30, delta: Int = 1, today: Boolean = false, repositories: Seq[String] = Seq())
