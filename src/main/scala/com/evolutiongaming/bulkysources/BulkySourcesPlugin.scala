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

  private def bulkySourcesTask: Def.Initialize[InputTask[Seq[BulkySource]]] =
    Def.inputTask {
      val threshold = bulkyThresholdParser.parsed
      val files     = sources.value

      files
        .map(file => (IO.readLines(file).size + 1, file))
        .filter { case (numberOfLines, _) => numberOfLines >= threshold }
        .sortWith(Ordering[BulkySource].gt)
    }

  override def projectSettings: Seq[Setting[_]] =
    inConfig(Compile)(bulkySourcesSettings) ++ inConfig(Test)(bulkySourcesSettings)

  private lazy val bulkySourcesSettings: Seq[Setting[_]] = Seq(
    bulkyThresholdInLines := 100,
    bulkySources          := bulkySourcesTask.evaluated,
  )

  override def requires: Plugins       = Plugins.empty
  override def trigger:  PluginTrigger = allRequirements
}
