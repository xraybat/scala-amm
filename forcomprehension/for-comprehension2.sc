
//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  // preferred style '{}'
  for {
    l1 <- List(1, 2, 3, 4, 5)     // a generator
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
    if l1 != l2                   // a filter
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

  for {
    l1 <- List((1, 1), (2, 2), (3, 3), (4, 4), (5, 5))
    l2 <- List((4, 4), (5, 5), (6, 6), (7, 7), (8, 8))
    if l1 != l2
  } println(s"l1: $l1, l2: $l2")
  println

  // same as above but using definitions to drill into the tuples
  for {
    l1 <- List((1, 1), (2, 2), (3, 3), (4, 4), (5, 5))  // a generator
    l2 <- List((4, 4), (5, 5), (6, 6), (7, 7), (8, 8))
    t1_1 = l1._1                                        // a definition
    t1_2 = l1._2
    t2_1 = l2._1
    t2_2 = l2._2
    if l1 != l2                                         // a filter
  } println(s"(t1._1, t1._2): ($t1_1, $t1_2), (t2._1, t2._2): ($t2_1, $t2_2)")
  println

} // main