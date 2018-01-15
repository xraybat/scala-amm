object MyMain extends App {
  override def main(args: Array[String]) {
    println(s"MyMain.main, args: $args")
    println("args")
    args.foreach { println }
  } /// main
} // Future1

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  // invokable from cli
  println(s"main, args: $args")
  println("args:")
  args.foreach { println }
  val myargs = Array(args(0).toString, args(1).toString, args(2).toString)
  println("myargs:")
  myargs.foreach { println }
  MyMain.main(myargs)

} // main