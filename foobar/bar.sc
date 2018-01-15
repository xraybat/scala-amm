val bar = 11
val lst = 1 :: 2 :: 3 :: Nil

object Single {
  println("in Object")
  override def toString(): String = "Singleton"
}

class Class(s: String) {
  println("in Class")
  override def toString(): String = "Class: " + s
}