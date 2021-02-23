package com.evolutiongaming.bulkysources

import sbt.Keys._
import sbt._
import sbt.complete.DefaultParsers._
import sbt.complete._

object BulkySourcesPlugin extends AutoPlugin {
  type BulkySource = (Int, File)

  object autoImport {
    lazy val bulkySources          = inputKey[Seq[BulkySource]]("Gives a list of bulky sources with threshold.")
    lazy val bulkyThresholdInLines = settingKey[Int]("default value for bulky sources")
  }
  import autoImport._

  private val bulkyThresholdParser: Def.Initialize[Parser[Int]] =
    Def.setting {
      Space ~> token(NatBasic, "<threshold>") ?? bulkyThresholdInLines.value
    }

  private def bulkySourcesTask(configuration: Configuration): Def.Initialize[InputTask[Seq[BulkySource]]] =
    Def.inputTaskDyn {
      val threshold = bulkyThresholdParser.parsed
      val files     = (configuration / sources).value

      Def.task {
        files
          .map(file => (IO.readLines(file).size + 1, file))
          .filter { case (numberOfLines, _) => numberOfLines >= threshold }
          .sortWith(Ordering[BulkySource].gt)
      }
    }

  override def projectSettings: Seq[Setting[_]] = Seq(
    Test / bulkySources    := bulkySourcesTask(Test).evaluated,
    Compile / bulkySources := bulkySourcesTask(Compile).evaluated,
    bulkyThresholdInLines  := 100
  )

  override def requires: Plugins       = Plugins.empty
  override def trigger:  PluginTrigger = allRequirements
}
