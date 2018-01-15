import scala.concurrent.{ Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import scala.util.Random

object OnSuccessAndFailure extends App {
  override def main(args: Array[String]) {
    println("starting calculation...")

    val f = Future {  // equivalent of `Future.apply`, yes? 
      Thread.sleep(Random.nextInt(500))
      if (Random.nextInt(500) > 250)
        throw new Exception("yikes!")
      else
        42
    }

    println("before onSuccess")
    f onSuccess {
      case result => println(s"success, result: $result")
    }
 
    println("before onFailure")
    f onFailure {
      case e => println(s"exception: ${e.getMessage}")
    }
 
    // do the rest of the work
    println("A..."); Thread.sleep(100)
    println("B..."); Thread.sleep(100)
    println("C..."); Thread.sleep(100)
    println("D..."); Thread.sleep(100)
    println("E..."); Thread.sleep(100)
    println("F..."); Thread.sleep(100)
    
     // keep jvm from shutting down
   Thread.sleep(2000)

  } /// main
} // Future1

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  OnSuccessAndFailure.main(Array(""))
  OnSuccessAndFailure.main(Array(""))
  OnSuccessAndFailure.main(Array(""))
  OnSuccessAndFailure.main(Array(""))
}