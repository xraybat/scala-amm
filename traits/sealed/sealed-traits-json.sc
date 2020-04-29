sealed trait Json
case class Null() extends Json
case class Bool(value: Boolean) extends Json
case class Str(value: String) extends Json
case class Num(value: Double) extends Json
case class Arr(value: Seq[Json]) extends Json
case class Dict(value: Map[String, Json]) extends Json

//======================================
import ammonite.ops._

@main
def main(args: String*) = {

} // main