import fxprof.*, tracer.Tracer
import com.github.plokhotnyuk.jsoniter_scala.core._
import decline_derive.CommandApplication
import java.util.concurrent.atomic.AtomicInteger
import scala.reflect.ClassTag
import java.util.concurrent.atomic.AtomicBoolean

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

case class Config(out: String) derives CommandApplication

@main def sampleGenerate(args: String*) =
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
      Thread.sleep(100)
      t.span("bla ", "optimising") {
        Thread.sleep(200)
      }
    }
  }

  t.span("object bla", "emitting") {
    t.span("bla$lzymap", "emitting") {
      Thread.sleep(400)
    }
    t.span("bla.apply(..)", "emitting") {
      Thread.sleep(100)
    }
  }

  t.span("hello") {
    println("yes")
  }

  t.close()

  val prof = t.build()

  println(prof.shared.stringArray.zipWithIndex)
  println("\nFunc table:")
  println(writeToString(prof.threads(0).funcTable))
  println("\nFrame table:")
  println(writeToString(prof.threads(0).frameTable))
  println("\nStack table:")
  println(writeToString(prof.threads(0).stackTable))
  println("\nSamples table:")
  println(writeToString(prof.threads(0).samples))

  val config = CommandApplication.parseOrExit[Config](args)
  val path = os.Path(config.out, os.pwd)
  val json = writeToString(prof, WriterConfig.withIndentionStep(2))
  os.write.over(path, json)
