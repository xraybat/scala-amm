import sys.process._
import java.io.File

object GetEnviron extends App {
  def myMain(args: Array[String]) {
    println(Process(Seq("bash", "-c", "echo $M2_HOME")).!!.trim)
    Process(Seq("bash", "-c", "echo $M2_HOME")).!
    println(Process(Seq("bash", "-c", "echo $M2_HOME")).!)

    val environ = Process(Seq("bash", "-c", "echo $M2_HOME")).!!.trim
    println(s"environ = $environ (${environ.length()})")
    val notenviron = Process(Seq("bash", "-c", "echo $MXXX_HOME")).!!.trim
    println(s"notenviron = $notenviron (${notenviron.length()})")

  } // main
} // GetEnviron

//======================================

@main
def main(args: String*) = {
  GetEnviron.myMain(Array(""))
}