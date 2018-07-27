////////////////////////////////////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  def add(x: Int, y: Int) = x + y 
  var x = add(1, 2)   // 3
  var y = add(7, 3)   // 10
  println(s"x = $x, y = $y")

  // currying: 'what this is saying is that the currying process transforms a function of two parameters into a
  // function of one parameter which returns a function of one parameter which itself returns the result.''
  def cadd(x: Int) = (y: Int) => x + y
  x = cadd(1)(2)   // 3
  y = cadd(7)(3)   // 10
  println(s"x = $x, y = $y")

  def suffix(v: String, arg: String): String = v + arg
  def prefix(v: String, arg: String): String = arg + v
  def reverse(v: String): String = v.reverse
  def replace(v: String, arg: String): String = arg
  def concat(v: String, args: String*): String = v + args.toString
  println(suffix("val", "-ix"))
  println(prefix("val", "ix-"))
  println(reverse("val"))
  println(replace("val", "new"))
  println(concat("val", "-add-1", "-add-2"))
  println(concat("val", Vector("-add-1", "-add-2"): _*))  // type ascription for Seqs

  // now the (single arg) curried versions??
  def csuffix(v: String) = (arg: String) => v + arg
  def cprefix(v: String) = (arg: String) => arg + v
  def creplace(v: String) = (arg: String) => arg
  def cconcat(v: String) = (arg: String) => v + arg
  println(csuffix("val")("-ix"))
  println(cprefix("val")("ix-"))
  println(reverse("val")) // concept of curried reverse doesn't make sense
  println(creplace("val")("new"))

  println(cconcat("val")("-add-1"))
  //println(cconcat("val")("-add-1")("-add-2")) // how to make this "recursive"??

  // different curried versions?? or partial functions??
  def psuffix(v: String)(arg: String) = v + arg
  def pprefix(v: String)(arg: String) = arg + v
  def preplace(v: String)(arg: String) = arg
  def pconcat(v: String)(arg: String) = v + arg
  println(psuffix("val")("-ix"))
  println(pprefix("val")("ix-"))
  println(preplace("val")("new"))

  println(pconcat("val")("-add-1"))
  //println(pconcat("val")("-add-1")("-add-2")) // how to make this "recursive"??

} // main