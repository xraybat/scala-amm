import scala.util.{ Try, Success, Failure }

case class MyException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause) 

object Options {
  def anOption: Option[Int] = { Some(42) }  // must use `Some()`
  def aNone: Option[Int] = { None }
}

object Tries {
  def aSuccess: Try[Int] = { Success(42) }  // `Try(42)` works as well
  def anException: Try[Int] = { /*just*/ throw new MyException("what a failure!") }
  def aFailure: Try[Int] = { Failure(new MyException("what a failure!")) }  // `Try(throw new...` works as well
}

// converting `Option[]` to `Try[]` via `match`
object OptionToTryMatch {
  def anOptionSucceeds: Try[Int] = {
    Options.anOption match {
      case Some(value) => Success(value)
      case None => Failure(new MyException("nothing!"))
    }
  }

  def aNoneFails: Try[Int] = {
    Options.aNone match {
      case Some(value) => Success(value)
      case None => Failure(new MyException("nothing!"))
    }
  }
} // OptionToTryMatch

// idiomatic conversion of `Option[]` to `Try[]`
object OptionToTryIdiomatic {
  def anOptionSucceeds: Try[Int] = {
    // `map()` works on `Option[]` even tho not a collection
    Options.anOption.map(Success(_)).getOrElse(Failure(new MyException("nothing!")))
  }
    
  def aNoneFails: Try[Int] = {
    Try { Options.aNone.getOrElse(throw new MyException("nothing!")) }
  }
} // OptionToTryIdiomatic

//======================================
object OptionToTry extends App {
  override def main(args: Array[String]) {
    Options.anOption match {
      case Some(value) => println(s"value: $value")
      case None => println("nothing!")
    } 

    Options.aNone match { 
      case Some(value) => println(s"value: $value")
      case None => println("nothing!")
    } 

    Tries.aSuccess match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 

    try {
      Tries.anException match {
        case Success(value) => println(s"value: $value")
        case Failure(s) => println("this won't be reached")
      }
    } catch {
      case m: MyException => println(s"failed: $m")
    }

    Tries.aFailure match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 

    /*
     * now convert an `Option` to a `Try[]`
     */ 
    OptionToTryMatch.anOptionSucceeds match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 

    OptionToTryMatch.aNoneFails match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 

    OptionToTryIdiomatic.anOptionSucceeds match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 

    OptionToTryIdiomatic.aNoneFails match {
      case Success(value) => println(s"value: $value")
      case Failure(s) => println(s"failed: $s")
    } 
  } // main
} // OptionToTry

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  OptionToTry.main(Array(""))
}