import fxprof.*, tracer.Tracer
import decline_derive.CommandApplication

case class Config(out: String) derives CommandApplication

@main def sampleGenerate(args: String*) =
  // Setup the metadata for the profile
  val meta = ProfileMeta(
    interval = 1.0,
    startTime = System.currentTimeMillis(),
    processType = 1.0,
    product = ProfileMeta_Product.Other("scala-native-2"),
    stackwalk = ProfileMeta_Stackwalk.False,
    version = 1.0,
    preprocessedProfileVersion = 58
  )
    // Categories defined below can be used to start spans
    // if a span is started with a category that is not defined here, "default" will be used
    // "default" is added implicitly to the profile when it's serialised
    .withCategories(
      Some(
        Vector(
          Category("lowering", CategoryColor.Blue),
          Category("emitting", CategoryColor.Green),
          Category("optimising", CategoryColor.Red)
        )
      )
    )

  val t = Tracer(meta)

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

  import com.github.plokhotnyuk.jsoniter_scala.core._
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
