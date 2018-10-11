////////////////////////////////////////////////////////////////////////////////
import ammonite.ops._

def abs(n: Int): Int = if (n <= 0) -n else n

def factorial(n: Int): Int = {
  @annotation.tailrec   // will warn if *not* tail recursive
  def loop(n: Int, acc: Int): Int = if (n <= 0) acc else loop(n-1, n*acc)
  loop(n, 1)
}

def fibonacci(n: Int): Int = {
  @annotation.tailrec
  def loop(n: Int, prev: Int, curr: Int): Int = if (n <= 0) prev else loop(n-1, curr, prev+curr)
  loop(n, 0, 1) // seed values 0 and 1
}

/*def formatAbs(n: Int) = {
  val msg = "the absolute value of %d is %d"
  msg.format(n, abs(n))
}

def formatFactorial(n: Int) = {
  val msg = "the factorial of %d is %d"
  msg.format(n, factorial(n))
}

def formatFibonacci(n: Int) = {
  val msg = "term %d of the fibonacci sequence is %d"
  msg.format(n, fibonacci(n))
}
*/
def formatResult(name: String, n: Int, f: Int => Int) = {
  val msg = "the %s of %d is %d"
  msg.format(name, n, f(n))
}

// (ಠ_ಠ)
@main
def main(args: String*) = {
  /*println(formatAbs(-42))
  println(formatFactorial(7))
  println(formatFibonacci(20))*/
  println(formatResult("absolute value", -42, abs))
  println(formatResult("factorial", 7, factorial))
  println(formatResult("fibonacci sequence", 20, fibonacci))

} // main