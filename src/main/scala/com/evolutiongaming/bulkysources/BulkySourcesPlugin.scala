package com.evolutiongaming.bulkysources

import sbt.Keys._
import sbt._
import sbt.complete.DefaultParsers._
import sbt.complete._

object BulkySourcesPlugin extends AutoPlugin {
  object autoImport {
    lazy val bulkySources          = inputKey[Seq[(Int, File)]]("Gives a list of bulky sources with threshold.")
    lazy val bulkyThresholdInLines = SettingKey[Int]("default value for bulky sources")
  }
  import autoImport._

  private val bulkyThresholdParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      // When parsing a single unsigned int, bulkyThresholdInLines is used as a fallback for empty input
      Space ~> token(NatBasic, "<threshold>") ?? bulkyThresholdInLines.value
    }

  private def bulkySourcesTask(configuration: Configuration): Def.Initialize[InputTask[Seq[(Int, File)]]] =
    Def.inputTaskDyn {
      val threshold = bulkyThresholdParser.parsed
      val files     = (configuration / sources).value

      Def.task {
        files
          .map(file => (IO.readLines(file).size + 1, file))
          .filter { case (loc, _) => loc >= threshold }
          .sorted
          .reverse
      }
    }

  override def projectSettings: Seq[Setting[_]] = Seq(
    Test / bulkySources    := bulkySourcesTask(Test).evaluated,
    Compile / bulkySources := bulkySourcesTask(Compile).evaluated,
    bulkyThresholdInLines  := 100
  )

  override def requires = plugins.JvmPlugin
  override def trigger  = allRequirements
}
