// (ಠ_ಠ)
@main
def main(args: String*) = {
  val oneTwo = List(1, 2)
  val threeFour = List(3, 4)
  val oneTwoThreeFour = oneTwo ::: threeFour  // concatenate
  println(oneTwo + " and " + threeFour + " were not mutated")
  println("thus, " + oneTwoThreeFour + " is a new list")

  //oneTwo(0) = 3 // error 'value .update() not a member of List[Int]', unlike Array

  val twoThree = List(2, 3)
  val oneTwoThree = 1 :: twoThree     // 'cons' - prepend
  println(oneTwoThree)

  val oneTwoThree2 = 1 :: 2 :: 3 :: Nil   // 'Nil' reqd
  println(oneTwoThree2)
}