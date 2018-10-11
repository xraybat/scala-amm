def add(x: Int, y: Int): Int = x+y 

def partial1[A, B, C](a: A, f: (A,B) => C): B => C =
//(b: B) => f(a, b)
  (b) => f(a, b)  // types inferred

def curry[A, B, C](f: (A, B) => C): A => (B => C) =
//(a: A) => (b: B) => f(a, b)
  a => b => f(a, b)

def uncurry[A, B, C](f: A => B => C): (A, B) => C =
//(a: A, b: B) => f(a)(b)
  (a, b) => f(a)(b)

def myCompose[A, B, C](f: B => C, g: A => B): A => C =
//(a: A) => f(g(a))
  a => f(g(a))

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println("add(0, 25) = " + add(10, 25))

  val p1 = partial1[Int, Int, Int](20, add)
  println("p1(25) = " + p1(25))

  val c = curry[Int, Int, Int](add)
  println("c(30)(25) = " + c(30)(25))

  def uc = uncurry[Int, Int, Int](curry[Int, Int, Int](add))
  println("uc(40, 25) = " + uc(40, 25))


  val f = (x: Double) => math.Pi/2-x
  val cos = f compose math.sin    // works on val funcs
  println("cos(10), compose f = " + cos(10))

  //val mycos = f myCompose math.sin(_)
  //println("mycos(10), myCompose f = " + mycos(10))

  val atcos = f andThen math.sin  // is `g compose f`, *not* `f compose g`
  println("atcos(10), andThen f = " + atcos(10))

} // main