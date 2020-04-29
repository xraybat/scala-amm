sealed trait Json
case class Null() extends Json
case class Bool(value: Boolean) extends Json
case class Str(value: String) extends Json
case class Num(value: Double) extends Json
case class Arr(value: Seq[Json]) extends Json
case class Dict(value: Map[String, Json]) extends Json

def parse(token: Json) = token match {
  case Null() => "Null"
  case Bool(value: Boolean) => s"Bool: $value"
  case Str(value: String) => s"Str: $value"
  case Num(value: Double) => s"Num: $value"
  case Arr(value: Seq[Json]) => s"Arr: $value"
  case Dict(value: Map[String, Json]) => s"Map: $value"
}

// a new method (easy)
def stringify(token: Json): String = token match {
  case Null() => "null"
  case Bool(value: Boolean) => value.toString
  case Str(value: String) => value.toString
  case Num(value: Double) => value.toString
  case Arr(value: Seq[Json]) => {
    var str: String = ""
    for (elem <- value) str + ", " + stringify(elem)
    str
  }
  case Dict(value: Map[String, Json]) => {
    var str: String = ""
    for ((key,elem) <- value) str + ", " + stringify(Str(key)) + ": " + stringify(elem)
    str
  }
}

// sealed traits make adding methods easy (but new subclasses more difficult
// since all methods need to be extended with the new subclass). open traits are
// the opposite, adding new subclasses is easy, just implement exusting trait interface
// methods, but adding methods is more difficult as all subclasses need to be
// extended with the new method.

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  val tokens: Array[Json] = Array(
    Null(), 
    Bool(true),
    Str("fred"),
    Num(11),

    Arr(Seq(
      Null(),
      Bool(true),
      Str("fred"),
      Num(11)
    )),

    Dict(Map(
      "key1" -> Null(),
      "key2" -> Bool(true),
      "key3" -> Str("fred"),
      "key4" -> Num(11),
      "key5" -> Arr(Seq(
        Null(),
        Bool(true),
        Str("fred"),
        Num(11)
      )),
      "key6" -> Dict(Map(
        "key6.1" -> Null(),
        "key6.2" -> Bool(true),
        "key6.3" -> Str("fred"),
        "key6.4" -> Num(11)
      ))
    ))
  ) // tokens

  for (t <- tokens)
    println(s"$t: " + parse(t))

  println 
  for (t <- tokens)
    println(s"$t: " + stringify(t))

} // main