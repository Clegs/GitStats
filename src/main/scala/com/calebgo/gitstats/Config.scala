package com.calebgo.gitstats

case class Config(days: Int = 30, delta: Int = 1, repositories: Seq[String] = Seq())
