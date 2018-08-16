object Stub {
  def doIt: Unit = ???  // will compile, but won't execute
}

//======================================
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println("hello, world!")
//Stub.doIt // uncommenting results in `scala.NotImplementedError: an implementation is missing`
}