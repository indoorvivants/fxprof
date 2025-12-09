package fxprof_tracer_tests

import com.indoorvivants.snapshots.munit_integration._
import munit._

import fxprof.*, tracer.*

import com.github.plokhotnyuk.jsoniter_scala.core._

class TracerTests extends FunSuite with MunitSnapshotsIntegration {
  test("snapshot") {

    val meta = ProfileMeta(
      interval = 1.0,
      startTime = java.time.Instant.now().toEpochMilli,
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
    )

    t.span("bla ", "lowering") {
      t.span("bla.constants", "lowering") {
        Thread.sleep(50)
        t.span("bla ", "optimising") {
          Thread.sleep(20)
        }
      }
    }

    t.span("object bla", "emitting") {
      t.span("bla$lzymap", "emitting") {
        Thread.sleep(100)
      }
      t.span("bla.apply(..)", "emitting") {
        Thread.sleep(150)
      }
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
