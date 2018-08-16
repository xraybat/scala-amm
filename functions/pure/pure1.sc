// implicit return type (IRT)
val ifi = (x: Int) => x + 1
val jfi = (x: Int, y: Int) => x + y

// explicit return type (ERT)
val ife: Int => Int = (x) => x + 1
val jfe: (Int, Int) => Int = (x, y) => x + y

// trad methods
def im(x: Int): Int = x + 1
def jm(x: Int, y: Int): Int = x + y

// passing functions / methods as args
def iCalc(x: Int, i: (Int) => Int): Int = i(x)
def jCalc(x: Int, y: Int, j: (Int, Int) => Int): Int = j(x, y)

////////////////////////////////////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  val ix = 1
  val jx = 3
  val jy = 4

  println("ifi = " + ifi(ix))
  println("jfi = " + jfi(jx, jy))
  println("ife = " + ife(ix))
  println("jfe = " + jfe(jx, jy))

  println("im = " + im(ix))
  println("jm = " + jm(jx, jy))

  println("iCalc, fi = " + iCalc(ix, ifi))
  println("jCalc, fi = " + jCalc(jx, jy, jfi))
  println("iCalc, fe = " + iCalc(ix, ife))
  println("jCalc, fe = " + jCalc(jx, jy, jfe))
  println("iCalc, m = " + iCalc(ix, im))
  println("jCalc, m = " + jCalc(jx, jy, jm))

} // main