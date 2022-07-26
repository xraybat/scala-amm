object MyMain extends App {
  def myMain(args: Array[String]) {
    println(s"MyMain.main, args: $args")
    println("args")
    args.foreach { println }
  } /// main
} // Future1

//======================================

@main
def main(args: String*) = {
  // invokable from cli
  println(s"main, args: $args")
  println("args:")
  args.foreach { println }
  val myargs = Array(args(0).toString, args(1).toString, args(2).toString)
  println("myargs:")
  myargs.foreach { println }
  MyMain.myMain(myargs)

} // main