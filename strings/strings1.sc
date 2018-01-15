object Strings1 extends App {
  override def main(args: Array[String]) {
    val hello = "hello, world!"

    println(hello)
    hello.foreach(print); println

    println(hello.map(c => c.toUpper))
    println(hello.map(_.toUpper)) // the preferred way

    for (c <- hello) print(c.toUpper); println  // if `map()` confusing...
    
    println(for(c <- hello) yield c.toUpper)
    println { for(c <- hello) yield c.toUpper }
    println(for { c <- hello } yield c.toUpper) // haha! see next

    println(for {
      c <- hello
      if c != 'l' // a guard
    } yield c.toUpper)

    println(hello.toUpperCase)  // but, note, we have this _string_ method

    println(hello.filter(_ != 'l').map(_.toUpper))

  } // main
} // Strings1

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  Strings1.main(Array(""))
}