import scala.concurrent.{ Future }
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }
import scala.util.Random

object Cloud {
  def runAlgorithm(i: Int): Future[Int] = Future {  // is `Future.apply`, yes?
    // some long process
    Thread.sleep(Random.nextInt(50))
    val result = i + 10
    println(s"Cloud.runAlgorithm, returning: $result")
    result
  }
} // Cloud

//======================================

@main
def main(args: String*) = {
  println("starting futures")
  val result1 = Cloud.runAlgorithm(10)
  val result2 = Cloud.runAlgorithm(20)
  val result3 = Cloud.runAlgorithm(30)

  println("before for-comprehension")
  val result = for {
    r1 <- result1
    r2 <- result2
    r3 <- result3
  } yield (r1 + r2 + r3)

  println("before onSuccess")
  result onSuccess {
    case result => println(s"total: $result")
  } 

  // keep jvm from shutting down
  Thread.sleep(2000)

} // main