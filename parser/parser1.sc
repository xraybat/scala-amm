import fastparse._, SingleLineWhitespace._

// add `.map(_.toInt)` after `.!` for x and y


object CoordParser {
  val keywordCoord = "coord"

  def parse(str: String): Unit = {
   def parser[_: P] = 
     P(keywordCoord.!
       ~ CharIn("0-9").rep(1).! ~ "," ~ CharIn("0-9").rep(1).!
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
        ~ CharIn("0-9").rep(1).! ~ "," ~ CharIn("0-9").rep(1).!
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

  val place1 = s"${PlaceParser.keywordPlace} 1,20,NORTH"
  PlaceParser.parse(place1)
  
  val place2 = s"${PlaceParser.keywordPlace} 1,20,EAST"
  PlaceParser.parse(place2)

  val place3 = s"${PlaceParser.keywordPlace} 1,20,SOUTH"
  PlaceParser.parse(place3)

} // main