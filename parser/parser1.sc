import fastparse._, SingleLineWhitespace._

object CoordParser {
  val keywordCoord = "coord"

  def parse(str: String): Unit = {
    def parser[_: P] = 
      P(keywordCoord.!
        ~ CharIn("0-9").rep(1).!.map(_.toInt)
        ~ "," ~ CharIn("0-9").rep(1).!.map(_.toInt)
        ~ End)

    print("CoordParser.parse: ")
    fastparse.parse(str, parser(_)) match {
      case Parsed.Success(value, index) => {
        println(s"found ${keywordCoord}, value = ${value}, index = ${index}")
        println(s"value._1 = ${value._1}")
        println(s"value._2 = ${value._2}")
        println(s"value._3 = ${value._3}")
      }
      case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
      case _ => println("problem parsing ${keywordCoord}") 

   } // match
 } // parse
} // CoordParser

// try coord x,y
//     up
//     down
//     left
//     right 
object CoordAndMove {
  val keywordCoord = "coord"
  val commandUp = "up"
  val commandDown = "down"
  val commandLeft = "left"
  val commandRight = "right"

  def parse(str: String): Unit = {
    def parserKeyword[_: P] = 
      P(keywordCoord.!
        ~ CharIn("0-9").rep(1).!.map(_.toInt)
        ~ "," ~ CharIn("0-9").rep(1).!.map(_.toInt)
        ~ End)
    def parserCommands[_: P] = 
      P(parserKeyword
        | commandUp.!
        | commandDown.!
        | commandLeft.!
        | commandRight.!
        ~ End)

    print("CoordAndMove.parse: ")
    fastparse.parse(str, parserCommands(_)) match {
      case Parsed.Success(value, index) => {
        println(s"found ${keywordCoord}, value = ${value}, getClass = ${value.getClass}, index = ${index}")
        value match {
          case (m: String, x: Int, y: Int) => {
            println(s"x = ${x}")
            println(s"y = ${y}")
            println(s"m = ${m}")
          }
          case c: String =>
            println(s"c = ${c}")
          case _ => println("problem matching value ${value}") 
        }
      }
      case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
      case _ => println("problem parsing ${keywordCoord}") 

   } // match
 } // parse
} // CoordAndMove

object PlaceParser {
  val keywordPlace = "PLACE"

  object Orientation extends Enumeration {
    type Orientation = Value
  
    val North = Value("NORTH")
    val East = Value("EAST")
    val South = Value("SOUTH")
    val West = Value("WEST")
  }

  def parse(str: String): Unit = {
    def parser[_: P] = 
      P(keywordPlace.!
        ~ CharIn("0-9").rep(1).!.map(_.toInt)
          ~ ","
          ~ CharIn("0-9").rep(1).!.map(_.toInt)
        ~ ","
        ~ (Orientation.North.toString.!
          | Orientation.East.toString.!
          | Orientation.South.toString.!
          | Orientation.West.toString.!)
        ~ End)

     print("PlaceParser.parse: ")
     fastparse.parse(str, parser(_)) match {
       case Parsed.Success(value, index) => {
         println(s"found ${keywordPlace}, value = ${value}, index = ${index}")
         println(s"value._1 = ${value._1}")
         println(s"value._2 = ${value._2}")
         println(s"value._3 = ${value._3}")
         println(s"value._4 = ${value._4}")
       }
       case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
       case _ => println("problem parsing ${keywordPlace}")

   } // match
 } // parse
} // PlaceParser

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {

  val coord = s"${CoordParser.keywordCoord} 1,20"
  CoordParser.parse(coord)

  CoordAndMove.parse(coord)
  CoordAndMove.parse(CoordAndMove.commandUp)
  CoordAndMove.parse(CoordAndMove.commandDown)
  CoordAndMove.parse(CoordAndMove.commandLeft)
  CoordAndMove.parse(CoordAndMove.commandRight)

  val place1 = s"${PlaceParser.keywordPlace} 1,20,NORTH"
  PlaceParser.parse(place1)
  
  val place2 = s"${PlaceParser.keywordPlace} 1,20,EAST"
  PlaceParser.parse(place2)

  val place3 = s"${PlaceParser.keywordPlace} 1,20,SOUTH"
  PlaceParser.parse(place3)

} // main