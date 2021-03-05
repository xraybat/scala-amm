// creates new list
def removeFours(lst: List[Int], acc: List[Int] = List.empty): List[Int] = lst match {
  case Nil    => acc.reverse
  case 4 :: t => removeFours(t, acc)
  case h :: t => removeFours(t, h :: acc)
}

// was `traverse()` but creates a new list...
def copy(lst: List[Int], acc: List[Int] = List.empty): List[Int] = lst match {
  case Nil    => acc.reverse
  case h :: t => copy(t, h :: acc)
}

def reverse(lst: List[Int], acc: List[Int] = List.empty): List[Int] = lst match {
  case Nil    => acc
  case h :: t => reverse(t, h :: acc)
}

def sum(lst: List[Int]): Int = lst match {
  case Nil => 0
  case h :: t => h + sum(t)
}

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  val lst = 1 :: 2 :: 3 :: 4 :: 5 :: Nil  // 'Nil' reqd
  println(lst)

  println("removeFours: " + removeFours(lst))
  println("traverse: " + copy(lst))
  println("reverse: " + reverse(lst))     // same as `lst.reverse`
  println("sum: " + sum(lst))
}