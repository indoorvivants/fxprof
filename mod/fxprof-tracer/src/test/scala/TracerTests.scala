package fxprof_tracer_tests

import com.indoorvivants.snapshots.munit_integration._
import munit._

import fxprof.*, tracer.*

import com.github.plokhotnyuk.jsoniter_scala.core._

class TracerTests extends FunSuite with MunitSnapshotsIntegration {
  test("snapshot") {

    val fixedTime = java.time.Instant.parse("2025-01-01T00:00:00Z")

    val meta = ProfileMeta(
      interval = 1.0,
      startTime = fixedTime.toEpochMilli,
      processType = 1.0,
      product = ProfileMeta_Product.Other("scala-native"),
      stackwalk = ProfileMeta_Stackwalk.False,
      version = 1.0,
      preprocessedProfileVersion = 58
    ).withInitialVisibleThreads(Some(Vector(0)))
      .withMarkerSchema(
        Vector(
          MarkerSchema("test-marker").withDisplay(
            Vector(MarkerDisplayLocation.MarkerChart)
          )
        )
      )
      .withCategories(
        Some(
          Vector(
            Category("interflow", CategoryColor.Blue),
            Category("lower", CategoryColor.Green),
            Category("emitLLVM", CategoryColor.Red)
          )
        )
      )

    val clock = new ClockSample {

      override def processStartupTime(): Long = fixedTime.toEpochMilli()

      override def processShutdownTime(): Long = fixedTime.toEpochMilli() + 500

      override def threadStartupTime(name: String): Long =
        fixedTime.toEpochMilli() + 220

      override def threadShutdownTime(name: String): Long =
        fixedTime.toEpochMilli() + 400

      override def beforeMillis(marker: String, labels: String): Long = {
        var t = fixedTime.toEpochMilli() + 220
        (marker, labels) match {
          case ("bla", "lowering") =>
            t + 20
          case ("bla.constants", "lowering") =>
            t + 25
          case ("bla", "optimising") =>
            t + 30
          case ("object bla", "emitting") =>
            t + 100
          case ("bla$lzymap", "emitting") =>
            t + 120
          case ("bla.apply(..)", "emitting") =>
            t + 150
          case ("hello", "default") =>
            t + 170
        }
      }

      override def afterMillis(marker: String, labels: String): Long = ???

    }

    val t = Tracer(
      meta.withCategories(
        Some(
          Vector(
            Category("lowering", CategoryColor.Yellow),
            Category("optimising", CategoryColor.Blue),
            Category("emitting", CategoryColor.Green)
          )
        )
      )
    ).withClock(clock)

    t.span("bla", "lowering") {
      t.span("bla.constants", "lowering") {
        t.span("bla", "optimising") {}
      }
    }

    t.span("object bla", "emitting") {
      t.span("bla$lzymap", "emitting") {}
      t.span("bla.apply(..)", "emitting") {}
    }

    t.span("hello") {
      println("yes")
    }

    t.close()

    val prof = t.build()

    assertSnapshot(
      "tracer sample",
      writeToString(prof, WriterConfig.withIndentionStep(3))
    )
  }
}
