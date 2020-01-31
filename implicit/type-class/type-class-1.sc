
sealed trait Json
object Json {
  case class Str(s: String) extends Json
  case class Num(value: Double) extends Json
  // ...many more definitions
}

trait Jsonable[T] {
  def serialize(t: T): Json
}
object Jsonable{  // companion object
  implicit object StringJsonable extends Jsonable[String] {
    def serialize(t: String) = Json.Str(t)
  }
  implicit object DoubleJsonable extends Jsonable[Double] {
    def serialize(t: Double) = Json.Num(t)
  }
  implicit object IntJsonable extends Jsonable[Int] {
    def serialize(t: Int) = Json.Num(t.toDouble)
  }
}

def convertToJson[T](x: T)(implicit converter: Jsonable[T]): Json = {
  converter.serialize(x)
}

////////////////////////////////////////////////////////////////////////////////
import ammonite.ops._

@main
def main(args: String*) = {
  println(convertToJson("hello"))
  println(convertToJson(123))
  println(convertToJson(123.56))

  // will cause a compile-time error
  //println(convertToJson(new java.io.File(".")))

} // main