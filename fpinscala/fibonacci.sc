def fibonacci(n: Int): Int = {
  @annotation.tailrec
  def go(n: Int, prev: Int, curr: Int): Int = {
    if (n <= 0) prev
    else go(n-1, curr, prev+curr)
  }

  go(n, 0, 1) // seed values 0 and 1
}

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println("fibonacci(-1) = " + fibonacci(-1)) // won't work in reverse

  for (i <- 0 to 20)
    println(s"fibonacci($i) = " + fibonacci(i))

} // main