//> using toolkit default
//> using dep com.indoorvivants::rendition::0.0.4

enum Type:
  case Optional(t: Type)
  case ArrayOf(t: Type)
  case Number, Str, Bool, Bytes
  case Ref(what: String)
  case LiteralString(value: String)
  case LiterlNumber(value: Double)
  case OneOf(name: String, types: Type*)

  def renderAsScala: (tpe: String, extraDefs: List[String]) =
    def go(
        tpe: Type,
        extraDefs: List[String]
    ): (tpe: String, extraDefs: List[String]) =
      tpe match
        case Optional(t) =>
          val inner = t.renderAsScala
          (s"Option[${inner.tpe}]", extraDefs ++ inner.extraDefs)
        case ArrayOf(t) =>
          val inner = t.renderAsScala
          (s"Array[${inner.tpe}]", extraDefs ++ inner.extraDefs)
        case Number               => ("Double", extraDefs)
        case Str                  => ("String", extraDefs)
        case Bool                 => ("Boolean", extraDefs)
        case Bytes                => ("Array[Byte]", extraDefs)
        case Ref(what)            => (what, extraDefs)
        case LiteralString(value) =>
          (s"Option[Any] /* literal string '$value' */", extraDefs)
        case LiterlNumber(value) =>
          (s"Option[Any] /* literal number $value */", extraDefs)
        case OneOf(name, types*) => (name, s"$name = $tpe" :: extraDefs)

    go(this, Nil)

def parseType(str: String, name: String): Type =
  str.trim() match
    case "string"             => Type.Str
    case "number"             => Type.Number
    case s"$something[]"      => Type.ArrayOf(parseType(something, name))
    case s"Array<$something>" => Type.ArrayOf(parseType(something, name))
    case s"$something | null" => Type.Optional(parseType(something, name))
    case s"null | $something" => Type.Optional(parseType(something, name))
    case s"boolean"           => Type.Bool
    case s"'$something'"      => Type.LiteralString(something)
    case s"($something)"      => parseType(something, name)
    case s"$something | $otherThing" =>
      Type.OneOf(
        name,
        parseType(something, name),
        parseType(otherThing, name)
      )
    case num if num.toDoubleOption.isDefined  => Type.LiterlNumber(num.toDouble)
    case ref if ref.forall(_.isLetterOrDigit) => Type.Ref(ref)

@main def run(prof: String, names: String) =
  val lines = os.read.lines(os.pwd / prof)

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
        val parsedType = parseType(tpe, s"$recordName.${name.capitalize}")
        name match
          case s"'$lit'?" => fields += s"`$lit`" -> Type.Optional(parsedType)
          case s"$value?" => fields += value -> Type.Optional(parsedType)
          case _          => fields += name -> parsedType

      case other =>

  val extraDefs = List.newBuilder[String]
  names
    .split(",")
    .foreach: name =>
      val (rend, extra) = render(name, m(name))
      extraDefs ++= extra
      os.write.over(os.pwd / s"$name.scala", rend)

  val remaining = m.keySet -- names.split(",").toSet

  println(s"Remaining:")
  remaining.foreach(s => println("  " + s))

  println("Need to by added by hand:")
  extraDefs.result().foreach(s => println("  " + s))

def render(struct: String, params: List[(String, Type)]) =
  import rendition.*
  val lb = LineBuilder()

  val (types, extra) =
    params.foldLeft(Map.empty[String, String] -> List.empty[String]):
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
        line(s"def $name: ${types(name)} = args.$name")
      emptyLine()
      params.foreach: (name, tpe) =>
        block(
          s"def with${name.capitalize}(value: ${types(name)}): $struct =",
          ""
        ):
          line(s"copy(_.copy($name = value))")

      emptyLine()
      block(s"private def copy(f: ${struct}Args => ${struct}Args) = ", ""):
        line(s"new $struct(f(args))")

    emptyLine()
    block(s"private[fxprof] case class ${struct}Args(", ")"):
      params.map: (name, tpe) =>
        line(s"$name: ${tpe.renderAsScala.tpe},")

    lb.result -> extra
