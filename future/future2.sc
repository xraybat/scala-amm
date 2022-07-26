import scala.concurrent.{ Await, Future, future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

//======================================

@main
def main(args: String*) = {
  implicit val baseTime = System.currentTimeMillis

  def longRunningComputation(i: Int): Future[Int] = future {
    Thread.sleep(100)
    i + 1
  }

  for (i <- 1 to 5) {
    // this does not block, first println occcurs together
    println("before calculation onComplete")
    longRunningComputation(i).onComplete {
      case Success(result) => println(s"result: $result")
      case Failure(e) => e.printStackTrace
    }
  }
  
  // keep jvm from shutting down
  Thread.sleep(1000)

} // main