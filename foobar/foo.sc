import $file.bar //, bar._
import $ivy.`com.lihaoyi::scalatags:0.6.5`
import scalatags.Text.all._
import $ivy.`joda-time:joda-time:2.9.9`, org.joda.time._

println(new DateTime())

val foo = 10
println(h1("hello, world no. " + (foo + bar.bar).toString() + "!"))
println(p(i("the value is "), b(bar.lst.toString())))
println(bar.Single)
println(new bar.Class("class"))

//======================================
val lst = 4 :: 5 :: 6 :: Nil
val concat = bar.lst ::: lst
val noconcat = bar.lst :: lst
println(s"""concat (:::)
        |\t$concat
        |\thead: ${concat.head}
        |\ttail: ${concat.tail}""".stripMargin)
println(s"""noconcat (::)
        |\t$noconcat
        |\thead: ${noconcat.head}
        |\ttail: ${noconcat.tail}""".stripMargin)

//======================================
import scalaz._
import std.option._     // functions and type class instances for Option
import std.list._       // functions and type class instances for List
import std.list._       // type class instances for List
import syntax.bind._    // type class instances for List

println(Apply[Option].apply2(some(1), some(2))((a, b) => a + b))
println(List(List(1)).join)

//======================================
object HelloWorld1 {
  var a = 1
  a += 1

  def main(args: Array[String]) {
    println(s"hello from HelloWorld1.main, args: ${args.size}, a: $a") // guess what's the value of `a`?
  }

  def whatsANow = a
}

object HelloWorld2 extends App {
  var a = 1
  a += 1

  override def main(args: Array[String]) {
    println(s"hello from HelloWorld2.App.main, args: ${args.size}, a: $a") // guess what's the value of `a`?
  }

  def whatsANow = a
}

//======================================

@main
def main(i: Int, s: String, path: Path = pwd) = {
  // invokable from cli
  println(s"hello! ${s * i} ${path.last}")

  val args = Array(s, i.toString)
  print("args = ")
  args.foreach { println }

  HelloWorld1.main(args)
  println(s"whatsANow: ${HelloWorld1.whatsANow}")
  HelloWorld2.main(args)
  println(s"whatsANow: ${HelloWorld2.whatsANow}")

} // main