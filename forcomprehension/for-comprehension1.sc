@main
def main(args: String*) = {
  val nieces = List("emily", "hannah", "mercedes", "porsche")
  println(s"nieces: $nieces")

  val capital = for (n <- nieces) yield n.capitalize
  println(s"capital: $capital")

  val upper = for (c <- capital) yield c.toUpperCase
  println(s"upper: $upper")

  println("lower:")
  upper.foreach(u => println("\t"+ u.toLowerCase))
  println("lower again:")
  upper.foreach { u => println("\t" + u.toLowerCase) }  // note: this is actually omitted parentheses, see next
  println("lower once more:")
  upper.foreach({ u => println("\t" + u.toLowerCase) })

} // main