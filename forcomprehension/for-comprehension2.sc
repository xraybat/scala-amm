
//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  // preferred style '{}'
  for {
    l1 <- List(1, 2, 3, 4, 5)
    l2 <- List(4, 5, 6, 7, 8)
  } println(s"l1: $l1, l2: $l2")
  println

  // can use '(;)'
  for (
    l1 <- List(1, 2, 3, 4, 5);
    l2 <- List(4, 5, 6, 7, 8)
  ) println(s"l1: $l1, l2: $l2")
  println

  for {
    l1 <- List(1, 2, 3, 4, 5)
    l2 <- List(4, 5, 6, 7, 8)
    if l1 != l2
  } println(s"l1: $l1, l2: $l2")
  println

  val arr = Array.ofDim[Int](2, 2)
  arr(0)(0) = 0
  arr(0)(1) = 1
  arr(1)(0) = 2
  arr(1)(1) = 3

  for {
    i <- 0 to 1
    j <- 0 to 1
  } println(s"arr($i)($j): ${arr(i)(j)}")

} // main