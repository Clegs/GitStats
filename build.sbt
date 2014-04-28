import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.packager.Keys._

name := "gitstats"

version := "0.1"

scalaVersion := "2.11.0"

// Setup parameters for Java Application
packageArchetype.java_application

packageDescription in Debian := "Statistics on Git Repository"

maintainer in Debian := "Clegs"

mainClass := Some("com.calebgo.gitstats.GitStats")

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.0.0"
