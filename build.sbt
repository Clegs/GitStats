import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.packager.Keys._

name := "gitstats"

version := "0.2"

scalaVersion := "2.11.0"

// Setup parameters for Java Application
packageArchetype.java_application

packageDescription in Debian := "Statistics on a Git Repository"

maintainer in Debian := "Clegs"

mainClass := Some("com.calebgo.gitstats.GitStats")

libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "1.0.0"

// Used for command line parsing.
libraryDependencies += "com.github.scopt" %% "scopt" % "3.2.0"

resolvers += Resolver.sonatypeRepo("public")
