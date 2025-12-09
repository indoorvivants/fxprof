val Versions = new {
  val Scala3 = "3.7.4"
  val Scala213 = "2.13.18"
  val Scala212 = "2.12.20"
  val allScala = Seq(Scala3, Scala213, Scala212)
  val onlyScala3 = Seq(Scala3)

  val DeclineDerive = "0.3.3"
  val Rendition = "0.0.4"
  val OsLib = "0.11.6"
  val MUnit = "1.2.1"

}
lazy val root = project
  .aggregate(fxprofFormat.projectRefs *)
  .aggregate(fxprofSample.projectRefs *)
  .aggregate(fxprofTracer.projectRefs *)
  .aggregate(fxprofCodegen)
  .settings(noPublish)

lazy val fxprofFormat = projectMatrix
  .in(file("mod/fxprof-format"))
  .jvmPlatform(Versions.allScala)
  .nativePlatform(Versions.allScala)
  .jsPlatform(Versions.allScala)
  .settings(
    name := "fxprof-format",
    libraryDependencies ++= Seq(
      "com.github.plokhotnyuk.jsoniter-scala" %%% "jsoniter-scala-core" % "2.38.5",
      "com.github.plokhotnyuk.jsoniter-scala" %%% "jsoniter-scala-macros" % "2.38.5" % "compile-internal"
    )
  )

lazy val fxprofTracer = projectMatrix
  .in(file("mod/fxprof-tracer"))
  .dependsOn(fxprofFormat)
  .jvmPlatform(Versions.allScala)
  .nativePlatform(Versions.allScala)
  .jsPlatform(Versions.allScala)
  .settings(
    name := "fxprof-tracer",
    scalacOptions += "-Xsource:3"
  )
  .settings(
    snapshotsPackageName := "fxprof_snapshots",
    snapshotsIntegrations += SnapshotIntegration.MUnit, // if using MUnit
    snapshotsForceOverwrite := !sys.env.contains("CI"),
    libraryDependencies += "org.scalameta" %%% "munit" % Versions.MUnit % Test,
    Test / scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .enablePlugins(SnapshotsPlugin)

lazy val fxprofCodegen = project
  .in(file("mod/fxprof-codegen"))
  .settings(
    scalaVersion := Versions.Scala3,
    libraryDependencies ++= Seq(
      "com.indoorvivants" %%% "decline-derive" % Versions.DeclineDerive,
      "com.indoorvivants" %%% "rendition" % Versions.Rendition,
      "com.lihaoyi" %%% "os-lib" % Versions.OsLib
    ),
    run / fork := true,
    noPublish
  )

val codegen = inputKey[Unit]("")
Global / codegen := Def.inputTaskDyn {
  val task = InputKey[Unit]("scalafmtAll")
  val out =
    (fxprofFormat.jvm(
      Versions.Scala3
    ) / Compile / sourceDirectory).value / "scala" / "generated"

  val in =
    (ThisBuild / baseDirectory).value / "firefox-profiler"

  Def
    .sequential(
      Def.taskDyn {
        (fxprofCodegen / Compile / run)
          .toTask(
            s" --out $out --firefoxProfiler $in"
          )
      }
    )
}.evaluated

lazy val fxprofSample = projectMatrix
  .in(file("mod/fxprof-sample"))
  .dependsOn(fxprofTracer)
  .jvmPlatform(Versions.onlyScala3)
  .settings(
    noPublish,
    libraryDependencies ++= Seq(
      "com.indoorvivants" %%% "decline-derive" % Versions.DeclineDerive,
      "com.lihaoyi" %%% "os-lib" % Versions.OsLib
    )
  )

val buildSample = inputKey[Unit]("")
Global / buildSample := Def.inputTaskDyn {
  val out =
    (ThisBuild / baseDirectory).value / "sample-profile.json"

  Def
    .sequential(
      Def.taskDyn {
        (fxprofSample.jvm(Versions.Scala3) / Compile / run)
          .toTask(
            s" --out $out"
          )
      },
      Def.task {
        sLog.value.info(s"Sample built successfully in $out")
      }
    )
}.evaluated

inThisBuild(
  Seq(
    organization := "com.indoorvivants",
    organizationName := "Anton Sviridov",
    homepage := Some(
      url("https://github.com/indoorvivants/fxprof")
    ),
    startYear := Some(2022),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "keynmol",
        "Anton Sviridov",
        "velvetbaldmime@protonmail.com",
        url("https://blog.indoorvivants.com")
      )
    )
  )
)

lazy val noPublish = Seq(
  publish / skip := true,
  publishLocal / skip := true
)
