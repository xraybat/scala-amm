import sys.process._
import java.io.File

object SetEnviron extends App {
  def myMain(args: Array[String]) {
    println(Process("ls -al").!!)
    println("ls -al".!!)
    println("pwd".!!.trim)

    println(Process("./foo.sh").!!.trim)  // need './' prefix as per cli
    println("./foo.sh".!!.trim)
    println(Process("./foo.sh", new File(".")).!!.trim)
    println(Process("./foo.sh", new File("."), "ENVIRON_VAR" -> "environ_var_val").!!.trim)
    println(Process("./foo.sh", None, "ENVIRON_VAR" -> "environ_var_val").!!.trim)

    // to set multiple environment variables at one time, keep
    // adding them at the end of the Process constructor
    println(Process("./foo.sh",
                    None,
                    "ENVIRON_VAR" -> "environ_var_val",
                    "ANOTHER_ENVIRON_VAR" -> "another_environ_var_val").!!.trim)

  } // main
} // SetEnviron

//======================================

@main
def main(args: String*) = {
  SetEnviron.myMain(Array(""))
}