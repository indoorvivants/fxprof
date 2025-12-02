//> using toolkit default
//> using dep com.indoorvivants::rendition::0.0.4
//
import rendition.RenderingContext

enum ExtraDef:
  case LiteralStr(name: String, value: String)
  case LiteralStrings(name: String, values: List[(String, String)])
  case LiteralNum(name: String, value: Double)
  case Other(hint: String)

case class Record(name: String, comment: List[String])
case class Field(name: String, comment: List[String])

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
          (s"Vector[${inner.tpe}]", extraDefs ++ inner.extraDefs)
        case Number                     => ("Double", extraDefs)
        case Str                        => ("String", extraDefs)
        case Bool                       => ("Boolean", extraDefs)
        case Bytes                      => ("Vector[Byte]", extraDefs)
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
        "HostResolverPayload",
        "MarkerSchema",
        "MarkerGraph",
        "MarkerSchemaField",
        "TableColumnFormat"
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
    "ProfileMeta" -> Set("sampleUnits", "extra"),
    // these use `string | void`, no idea what they mean
    "IPCMarkerPayload" -> Set("sendThreadName", "recvThreadName")
  ).withDefaultValue(Set.empty)

  val extraDefs = List.newBuilder[ExtraDef]
  allowed.foreach:
    (prof, list) =>
      println(s"Processing $prof")
      val lines = os.read.lines(os.pwd / os.RelPath(prof)).toArray

      enum State:
        case Start, CollectingFields

      val m = collection.mutable.Map.empty[Record, List[(Field, Type)]]
      var state = State.Start
      var recordName = ""
      var recordComment = List.empty[String]
      var fields = List.newBuilder[(Field, Type)]

      def walkBackForDocComment(from: Int) =
        val commentLines = List.newBuilder[String]
        if from > 0 && lines(from).trim == "*/" then
          var idx = from - 1
          def continue = idx > 0 && lines(idx).trim != "/**"
          while continue do
            commentLines += lines(idx).trim.stripPrefix("* ")
            idx -= 1
          commentLines.result().reverse
        else Nil

      def walkBackForLineComments(from: Int) =
        val commentLines = List.newBuilder[String]
        if from > 0 && lines(from).trim.startsWith("//") then
          var idx = from
          def continue = idx > 0 && lines(idx).trim.startsWith("//")
          while continue do
            commentLines += lines(idx).trim.stripPrefix("//").trim
            idx -= 1
          commentLines.result().reverse
        else Nil

      lines.zipWithIndex.foreach: (line, index) =>
        (state, line.trim()) match
          case (_, s"//$anything")                          =>
          case (State.Start, s"export type $something = {") =>
            state = State.CollectingFields
            recordName = something.trim
            recordComment = walkBackForDocComment(index - 1)

          case (State.Start, s"type $something = {") =>
            state = State.CollectingFields
            recordName = something.trim
            recordComment = walkBackForDocComment(index - 1)

          case (State.CollectingFields, "};") =>
            state = State.Start
            m.addOne(Record(recordName, recordComment) -> fields.result())
            recordName = ""
            recordComment = Nil
            fields.clear()

          case (State.CollectingFields, s"$name: $tpe;$anything") =>
            def parsedType(name: String) =
              parseType(tpe, s"${recordName}_${name.capitalize}")

            val comments = walkBackForLineComments(index - 1)

            name match
              case s"'$lit'?" =>
                fields += Field(s"$lit", comments) -> Type.Optional(
                  parsedType(lit)
                )
              case s"$value?" =>
                if !forbiddenFields(recordName)(value) then
                  fields += Field(value, comments) -> Type.Optional(
                    parsedType(value)
                  )
              case _ =>
                if !forbiddenFields(recordName)(name) then
                  fields += Field(name, comments) -> parsedType(name)

          case other =>

        val s = list.toSet

        m.foreach: (record, fields) =>
          if s.contains(record.name) then
            val (rend, extra) = render(record, fields)
            extraDefs ++= extra
            os.write.over(
              os.pwd / "generated" / s"${record.name}.scala",
              rend,
              createFolders = true
            )
      // val remaining = m.keySet -- list
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

def render(struct: Record, params: List[(Field, Type)]) =
  import rendition.*
  val lb = LineBuilder()

  val structName = struct.name
  val structArgsName = struct.name + "Args"

  val (types, extra) =
    params.foldLeft(Map.empty[String, String] -> List.empty[ExtraDef]):
      case (acc, (Field(name, _), tpe)) =>
        val rend = tpe.renderAsScala
        acc._1.updated(name, rend.tpe) -> (acc._2 ++ rend.extraDefs)

  def curlyBlock(name: String)(f: RenderingContext ?=> Unit)(using
      RenderingContext
  ) =
    block(s"$name {", "}")(f)
  lb.use:
    line("package fxprof")
    emptyLine()
    if struct.comment.nonEmpty then
      block("/**", "  */"):
        struct.comment.foreach(l => line("* " + l))
    curlyBlock(
      s"class ${structName} private (private[fxprof] val args: ${structArgsName})"
    ):
      params.foreach:
        case (Field(name, comments), tpe) =>
          if comments.nonEmpty then
            block("/** " + comments.head, "  */"):
              comments.tail.foreach(l => line("* " + l))
          line(
            s"def ${sanitiseFieldName(name)}: ${types(name)} = args.${sanitiseFieldName(name)}"
          )
          emptyLine()
      emptyLine()
      params.foreach:
        case (Field(name, comments), tpe) =>
          if comments.nonEmpty then
            block(s"/** Setter for [[$name]] field", "  */"):
              emptyLine()
              comments.foreach(l => line("* " + l))
          end if
          block(
            s"def ${{ makeSetter(name) }}(value: ${types(name)}): $structName =",
            ""
          ):
            line(s"copy(_.copy(${sanitiseFieldName(name)} = value))")

      emptyLine()
      block(
        s"private def copy(f: ${structArgsName} => ${structArgsName}) = ",
        ""
      ):
        line(s"new $structName(f(args))")

    emptyLine()

    line("import com.github.plokhotnyuk.jsoniter_scala.macros._")
    line("import com.github.plokhotnyuk.jsoniter_scala.core._")
    emptyLine()
    block(s"object $structName {", "}"):
      block(s"/** Construct a [[$structName]]", "  */"):
        params.foreach:
          case (Field(name, comments), tpe) =>
            val hasDefault = defaultScalaValue(tpe).nonEmpty
            if !hasDefault then gutterBlock(s"  @param ${name}", comments)

      line("def apply(")
      nest:
        params.foreach:
          case (Field(name, comments), tpe) =>
            val hasDefault = defaultScalaValue(tpe).nonEmpty
            if !hasDefault then
              line(s"${sanitiseFieldName(name)}: ${types(name)},")
      line(s"): $structName = ")
      nest:
        block(s"new $structName(${structArgsName}(", "))"):
          params.foreach:
            case (Field(name, comments), tpe) =>
              defaultScalaValue(tpe) match
                case None =>
                  line(
                    s"${sanitiseFieldName(name)} = ${sanitiseFieldName(name)},"
                  )
                case Some(value) =>
                  line(
                    s"${sanitiseFieldName(name)} = ${value},"
                  )

      block(s"given JsonValueCodec[$structName] = ", ""):
        block("new JsonValueCodec {", "}"):
          block(
            s"def decodeValue(in: JsonReader, default: $structName) = ",
            ""
          ):
            line(
              s"new $structName(summon[JsonValueCodec[${structArgsName}]].decodeValue(in, default.args))"
            )

          block(
            s"def encodeValue(x: $structName, out: JsonWriter) = ",
            ""
          ):
            line(
              s"summon[JsonValueCodec[${structArgsName}]].encodeValue(x.args, out)"
            )

          line(s"def nullValue: $structName = null")

    block(s"private[fxprof] case class ${structArgsName}(", ")"):
      params.foreach:
        case (Field(name, comments), tpe) =>
          line(s"${sanitiseFieldName(name)}: ${types(name)},")

    block(s"private[fxprof] object ${structArgsName} {", "}"):
      line(s"given JsonValueCodec[${structArgsName}] = JsonCodecMaker.make")

    lb.result -> extra

def defaultScalaValue(tpe: Type) =
  tpe match
    case Type.Optional(t) => Some("None")
    case Type.ArrayOf(t)  => Some("Vector.empty")
    case _                => None

def gutterBlock(gutter: String, lines: List[String])(using RenderingContext) =
  import rendition.*
  lines match
    case head :: next =>
      val gutterLength = gutter.length
      line(gutter + head)
      next.foreach: l =>
        line(" " * gutterLength + l)
    case Nil => line(gutter)
