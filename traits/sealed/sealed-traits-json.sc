sealed trait Json
case class Null() extends Json
case class Bool(value: Boolean) extends Json
case class Str(value: String) extends Json
case class Num(value: Double) extends Json
case class Arr(value: Seq[Json]) extends Json
case class Dict(value: Map[String, Json]) extends Json

def parse(token: Json) = token match {
  case Null() => "Null"
  case Bool(value) => s"Bool: $value"
  case Str(value: String) => s"Str: $value"
  case Num(value: Double) => s"Num: $value"
  case Arr(value: Seq[Json]) => s"Arr: $value"
  case Dict(value: Map[String, Json]) => s"Map: $value"
}

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  val tokens: Array[Json] = Array(
    Null(), 
    Bool(true),
    Str("fred"),
    Num(11),

    Arr(Array(
      Null(),
      Bool(true)
    )),

    Dict(Map(
      "key1" -> Null(),
      "key2" -> Bool(true),
      "key3" -> Str("fred"),
      "key4" -> Num(11),
      "key5" -> Arr(Array(
        Null(),
        Bool(true)
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

} // main