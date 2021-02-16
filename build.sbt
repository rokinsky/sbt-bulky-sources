import ScriptedPlugin.autoImport._

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    name               := "sbt-bulky-sources",
    organization       := "com.evolutiongaming",
    version            := "0.1-SNAPSHOT",
    scriptedLaunchOpts := Seq("-Xmx1G", s"-Dplugin.version=${version.value}"),
    scriptedBufferLog  := false
  )

addCommandAlias("build", ";clean; coverage; test; scripted")
