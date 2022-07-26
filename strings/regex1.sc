object Regex1 extends App {
  def myMain(args: Array[String]) {
    val regex1 = raw"[0-9]+".r  // `.r` method creates regex object
    val addr = "123 main street, suite 101" 
    val foundOne = regex1.findFirstIn(addr).getOrElse("nothing") 
    println(foundOne) 
    val foundAll = regex1.findAllIn(addr) 
    foundAll.foreach(println)

    val regex2 = raw"([0-9]+) ([a-zA-z]+)".r
    val regex2(count, fruit) = "100 bananas"
    println(count + ": " + fruit)  

  } // main
} // Regex1

//====================================================================

@main
def main(args: String*) = {
  Regex1.myMain(Array(""))
}