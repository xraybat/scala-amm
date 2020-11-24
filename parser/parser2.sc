import fastparse._, SingleLineWhitespace._
import scala.util.{ Try, Success, Failure }

// try up
//     down
//     left
//     right 
object Commands {
  val commandUp = "up"
  val commandDown = "down"
  val commandLeft = "left"
  val commandRight = "right"

  def parse(str: String): Unit = {
    def parserCommands[_: P] = 
      P(commandUp.!
        | commandDown.!
        | commandLeft.!
        | commandRight.!
        ~ End)

    print("Commands.parse: ")
    fastparse.parse(str, parserCommands(_)) match {
      case Parsed.Success(value, index) => {
        println(s"found value = ${value}, getClass = ${value.getClass}, index = ${index}")
        value match {
          case c: String => println(s"c = ${c}")
        }
      }
      case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
    }
  } // parse
} // Commands

object CommandsParsed {
  def parse[T](str: String): Parsed[T] = {
    def parserCommands[_: P] = 
      P(Commands.commandUp.!
        | Commands.commandDown.!
        | Commands.commandLeft.!
        | Commands.commandRight.!
        ~ End)

    print("CommandsParsed.parse: ")
    val result: Parsed[T] = fastparse.parse(str, parserCommands(_)) match {
      case Parsed.Success(value, index) => {
        println(s"found value = ${value}, getClass = ${value.getClass}, index = ${index}")
        value match {
          case c: String => println(s"c = ${c}")
          // parser2.sc:49: type mismatch;
          //   found   : Unit
          //   required: fastparse.Parsed[T]
          // Error occurred in an application involving default arguments.
          //   case c: String => println(s"c = ${c}")

        }
      }
      case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
    }

    result

  } // parse
} // CommandsParsed

case class ResultException(private val cause: Throwable = None.orNull) extends Exception("result is empty!", cause) 

object CommandsOptionParsed {
  def parse[T](str: String): Option[Parsed[T]] = {
    def parserCommands[_: P] = 
      P(Commands.commandUp.!
        | Commands.commandDown.!
        | Commands.commandLeft.!
        | Commands.commandRight.!
        ~ End)

    print("CommandsOptionParsed.parse: ")
    val result: Option[Parsed[T]] = Some(fastparse.parse(str, parserCommands(_)))
    // parser2.sc:81: type mismatch;
    //   found   : fastparse.package.P[String]
    //   (which expands to)  fastparse.ParsingRun[String]
    //   required: fastparse.P[T]
    //   (which expands to)  fastparse.ParsingRun[T]
    // Error occurred in an application involving default arguments.
    //   val result: Option[Parsed[T]] = Some(fastparse.parse(str, parserCommands(_)))

    result.getOrElse(Failure(ResultException())) match {
      case Parsed.Success(value, index) => {
        println(s"found value = ${value}, getClass = ${value.getClass}, index = ${index}")
        value match {
          case c: String => println(s"c = ${c}")
        }
      }
      case Parsed.Failure(expected, index, extra) => println(extra.trace().longMsg)
    }

    result

  } // parse
} // CommandsOptionParsed

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {

  Commands.parse(Commands.commandUp)
  Commands.parse(Commands.commandDown)
  Commands.parse(Commands.commandLeft)
  Commands.parse(Commands.commandRight)

  CommandsParsed.parse(Commands.commandUp)
  CommandsParsed.parse(Commands.commandDown)
  CommandsParsed.parse(Commands.commandLeft)
  CommandsParsed.parse(Commands.commandRight)

  CommandsOptionParsed.parse[String](Commands.commandUp)
  CommandsOptionParsed.parse(Commands.commandDown)
  CommandsOptionParsed.parse(Commands.commandLeft)
  CommandsOptionParsed.parse(Commands.commandRight)

} // main