import scala.concurrent.{ Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import scala.util.Random

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  println("starting calculation...")

  val f = Future {
    Thread.sleep(Random.nextInt(500))
    42
  }

  println("before onComplete")
  f.onComplete {
    case Success(value) => println(s"callback, value: $value")
    case Failure(e) => e.printStackTrace
  }
  println("after onComplete")

  println("A..."); Thread.sleep(100)
  println("B..."); Thread.sleep(100)
  println("C..."); Thread.sleep(100)
  println("D..."); Thread.sleep(100)
  println("E..."); Thread.sleep(100)
  println("F..."); Thread.sleep(100)
  
  // keep jvm from shutting down
  Thread.sleep(2000)

} // main