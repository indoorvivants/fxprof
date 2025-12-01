//> using toolkit default
//> using dep com.indoorvivants::rendition::0.0.4
//

enum ExtraDef:
  case LiteralStr(name: String, value: String)
  case LiteralStrings(name: String, values: List[(String, String)])
  case LiteralNum(name: String, value: Double)
  case Other(hint: String)

enum Type:
  case Optional(t: Type)
  case ArrayOf(t: Type)
  case Number, Str, Bool, Bytes
  case Ref(what: String)
  case LiteralString(name: String, value: String)
  case LiteralNumber(name: String, value: Double)
  case OneOf(name: String, types: Type*)
  case GenericRef(name: String, tpe: String)

  def renderAsScala: (tpe: String, extraDefs: List[ExtraDef]) =
    def go(
        tpe: Type,
        extraDefs: List[ExtraDef]
    ): (tpe: String, extraDefs: List[ExtraDef]) =
      tpe match
        case Optional(t) =>
          val inner = t.renderAsScala
          (s"Option[${inner.tpe}]", extraDefs ++ inner.extraDefs)
        case ArrayOf(t) =>
          val inner = t.renderAsScala
          (s"Array[${inner.tpe}]", extraDefs ++ inner.extraDefs)
        case Number                     => ("Double", extraDefs)
        case Str                        => ("String", extraDefs)
        case Bool                       => ("Boolean", extraDefs)
        case Bytes                      => ("Array[Byte]", extraDefs)
        case Ref(what)                  => (what, extraDefs)
        case LiteralString(name, value) =>
          (name + ".type", ExtraDef.LiteralStr(name, value) :: extraDefs)
        case LiteralNumber(name, value) =>
          (name, ExtraDef.LiteralNum(name, value) :: extraDefs)
        case OneOf(name, types*) =>
          asFlatLiterals(Type.OneOf(name, types*)) match
            case Some(Type.OneOf(name, types*))
                if types.forall(_.isInstanceOf[Type.LiteralString]) =>
              val defs =
                types.toList.collect:
                  case LiteralString(_, value) =>
                    val changed = value.toUpperCase().replace("-", "_")
                    (changed, value)

              (name, ExtraDef.LiteralStrings(name, defs) +: extraDefs)
            case _ =>
              (name, ExtraDef.Other(s"$name = $tpe") :: extraDefs)

        case GenericRef(name, t) =>
          (s"$name[$t]", ExtraDef.Other(s"$name = $tpe") :: extraDefs)

    go(this, Nil)

def asFlatLiterals(oneof: Type.OneOf): Option[Type.OneOf] =
  object Literal:
    def unapply(a: Type): Option[Type] =
      Option.when(
        a.isInstanceOf[Type.LiteralString] || a.isInstanceOf[Type.LiteralNumber]
      )(a)
  def go(t: Type.OneOf): Seq[Type] =
    t.types match
      case Seq(Literal(a), b: Type.OneOf) =>
        a +: go(Type.OneOf(t.name, b.types*))
      case s if s.forall(Literal.unapply(_).isDefined) =>
        t.types
      case other =>
        Seq.empty

  val tpes = go(oneof)
  Option.when(tpes.nonEmpty)(Type.OneOf(oneof.name, tpes*))

def parseType(str: String, name: String): Type =
  str.trim() match
    case s"($something)"      => parseType(something, name)
    case "string"             => Type.Str
    case "number"             => Type.Number
    case s"$something[]"      => Type.ArrayOf(parseType(something, name))
    case s"Array<$something>" => Type.ArrayOf(parseType(something, name))
    case s"$something | null" => Type.Optional(parseType(something, name))
    case s"null | $something" => Type.Optional(parseType(something, name))
    case s"boolean"           => Type.Bool
    case s"$something | $otherThing" =>
      Type.OneOf(
        name,
        parseType(something, name),
        parseType(otherThing, name)
      )
    case s"'$something'" => Type.LiteralString(name, something)
    case num if num.toDoubleOption.isDefined =>
      Type.LiteralNumber(name, num.toDouble)
    case ref if ref.forall(s => s.isLetterOrDigit || s == '_') => Type.Ref(ref)
    case s"$something<$tpe>"                                   =>
      Type.GenericRef(something, tpe)

@main def run =
  val allowed = Map(
    "firefox-profiler/src/types/markers.ts" ->
      List(
        "FileIoPayload",
        "CauseBacktrace",
        "GPUMarkerPayload",
        "NetworkPayload",
        "UserTimingMarkerPayload",
        "TextMarkerPayload",
        "LogMarkerPayload",
        "PaintProfilerMarkerTracing",
        "CcMarkerTracing",
        "DOMEventMarkerPayload",
        // "GCMinorMarkerPayload",
        // "GCMajorMarkerPayload",
        // "GCSliceMarkerPayload",
        "StyleMarkerPayload",
        "BHRMarkerPayload",
        "LongTaskMarkerPayload",
        // "VsyncTimestampPayload",
        // "ScreenshotPayload",
        "NavigationMarkerPayload",
        "PrefMarkerPayload",
        "IPCMarkerPayload",
        "MediaSampleMarkerPayload",
        // "JankPayload",
        "BrowsertimeMarkerPayload",
        "NoPayloadUserData",
        "UrlMarkerPayload",
        "HostResolverPayload"
      ),
    "firefox-profiler/src/types/profile.ts" ->
      List(
        "RawCounter",
        "RawCounterSamplesTable",
        "ProfilerOverheadStats",
        "ProfilerConfiguration",
        "Page",
        "ProfilerOverhead",
        "RawThread",
        "ExtensionTable",
        "ProgressGraphData",
        "VisualMetrics",
        "ProfileMeta",
        "JsAllocationsTable",
        "ResourceTable",
        "RawMarkerTable",
        "RawSamplesTable",
        "ProfilerOverheadSamplesTable",
        "FuncTable",
        "FrameTable",
        "Category",
        "RawProfileSharedData",
        "SourceTable",
        "JsTracerTable",
        "NativeSymbolTable",
        "RawStackTable",
        "PausedRange",
        "UnbalancedNativeAllocationsTable",
        "Lib",
        "Profile",
        "ExtensionTable",
        "UnbalancedNativeAllocationsTable"
      )
  )

  val forbiddenFields = Map(
    "RawThread" -> Set("nativeAllocations"),
    "ProfileMeta" -> Set("markerSchema", "sampleUnits", "extra"),
    // these use `string | void`, no idea what they mean
    "IPCMarkerPayload" -> Set("sendThreadName", "recvThreadName")
  ).withDefaultValue(Set.empty)

  val extraDefs = List.newBuilder[ExtraDef]
  allowed.foreach: (prof, list) =>
    println(s"Processing $prof")
    val lines = os.read.lines(os.pwd / os.RelPath(prof))

    enum State:
      case Start, CollectingFields

    val m = collection.mutable.Map.empty[String, List[(String, Type)]]

    var state = State.Start
    var recordName = ""
    var fields = List.newBuilder[(String, Type)]

    lines.foreach: line =>
      (state, line.trim()) match
        case (_, s"//$anything")                          =>
        case (State.Start, s"export type $something = {") =>
          state = State.CollectingFields
          recordName = something.trim

        case (State.CollectingFields, "};") =>
          state = State.Start
          m.addOne(recordName -> fields.result())
          recordName = ""
          fields.clear()

        case (State.CollectingFields, s"$name: $tpe;$anything") =>
          // println(s"Analysing ${recordName}.$name")
          def parsedType(name: String) =
            parseType(tpe, s"${recordName}_${name.capitalize}")

          name match
            case s"'$lit'?" =>
              fields += s"$lit" -> Type.Optional(parsedType(lit))
            case s"$value?" =>
              if !forbiddenFields(recordName)(value) then
                fields += value -> Type.Optional(parsedType(value))
            case _ =>
              if !forbiddenFields(recordName)(name) then
                fields += name -> parsedType(name)

        case other =>

    list
      .foreach: name =>
        val (rend, extra) = render(name, m(name))
        extraDefs ++= extra
        os.write.over(
          os.pwd / "generated" / s"$name.scala",
          rend,
          createFolders = true
        )

    val remaining = m.keySet -- list
  import rendition.*
  val strings = LineBuilder()
  strings.use:
    line("package fxprof")
    emptyLine()
  extraDefs
    .result()
    .distinct
    .collect:
      case ExtraDef.LiteralStr(name, value) =>
        strings.use:
          line(s"case object $name extends StringLiteral(\"$value\")")
          emptyLine()

      case ExtraDef.LiteralStrings(name, values) =>
        strings.use:
          line(
            "sealed abstract class " + name + "(value: String) extends StringLiteral(value)"
          )

          block(s"object $name {", "}"):
            values
              .foreach((n, v) => line(s"case object $n extends $name(\"$v\")"))
          emptyLine()

  os.write.over(
    os.pwd / "generated" / "strings.scala",
    strings.result,
    createFolders = true
  )

def sanitiseFieldName(n: String) =
  if Set("type")(n) || n.exists(s => !(s.isLetterOrDigit || s == '_')) then
    s"`$n`"
  else n

def makeSetter(n: String) =
  val san = sanitiseFieldName(n)
  if san.startsWith("`") then s"`with${san.stripPrefix("`")}"
  else s"with${n.capitalize}"

def render(struct: String, params: List[(String, Type)]) =
  import rendition.*
  val lb = LineBuilder()

  val (types, extra) =
    params.foldLeft(Map.empty[String, String] -> List.empty[ExtraDef]):
      case (acc, (name, tpe)) =>
        val rend = tpe.renderAsScala
        acc._1.updated(name, rend.tpe) -> (acc._2 ++ rend.extraDefs)

  def curlyBlock(name: String)(f: RenderingContext ?=> Unit)(using
      RenderingContext
  ) =
    block(s"$name {", "}")(f)
  lb.use:
    line("package fxprof")
    emptyLine()
    curlyBlock(s"class $struct private (args: ${struct}Args)"):
      params.foreach: (name, tpe) =>
        line(
          s"def ${sanitiseFieldName(name)}: ${types(name)} = args.${sanitiseFieldName(name)}"
        )
      emptyLine()
      params.foreach: (name, tpe) =>
        block(
          s"def ${{ makeSetter(name) }}(value: ${types(name)}): $struct =",
          ""
        ):
          line(s"copy(_.copy(${sanitiseFieldName(name)} = value))")

      emptyLine()
      block(s"private def copy(f: ${struct}Args => ${struct}Args) = ", ""):
        line(s"new $struct(f(args))")

    emptyLine()
    block(s"private[fxprof] case class ${struct}Args(", ")"):
      params.map: (name, tpe) =>
        line(s"${sanitiseFieldName(name)}: ${tpe.renderAsScala.tpe},")

    lb.result -> extra
