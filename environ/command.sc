import sys.process._
import java.io.File

object Command extends App {
  override def main(args: Array[String]) {
    println(Process("ls -al").!!)
    //println(Seq("ls -al").!!) // doesn't work!
    println(Seq("ls", "-al").!!)
    println(Seq("ls", "-a", "-l").!!)
    println("ls -al".!!)
    println("ls -al" !!)

    var rc = Process("ls -al").!
    println("rc = " + rc)
    rc = "ls -al".!
    println("rc = " + rc)
    rc = ("ls -al" !)
    println("rc = " + rc)

    println("pwd".!!.trim)
    println(Process("ls -al", new File(".")).!!)
    println(Process("ls -al", new File("./")).!!)
    println(Process("ls -al", new File("/Users/psc/Documents/My Documents/Development/Scala/amm/environ")).!!)
    println(Process("ls -al", new File("/Users")).!!)

  } // main
} // SetEnviron

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  Command.main(Array(""))
}