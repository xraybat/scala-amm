object GeneralMapOps {
  // parade of general map operations
  def doThem: Unit = {
    // ( ͡° ͜ʖ ͡°)

    // map of tuples
    val m1 = Map("key1" -> "val1", "key2" -> "val2", "key3" -> "val3")
    //val m1 = Map(("key1", "val1"), ("key2", "val2"), ("key3", "val3"))    // also tuples
    println("m1 = " + m1)
    println("m1(\"key1\") = " + m1("key1"))
    println("m1(\"key4\") = " + m1.getOrElse("key4", ""))
    println("m1.contains(\"key1\") = " + m1.contains("key1"))   // true
    println("m1.contains(\"val2\") = " + m1.contains("val2"))   // false
    println("m1.valuesIterator.exists(_.contains(\"val2\")) = " + m1.valuesIterator.exists(_.contains("val2")))   // true
    println("m1.valuesIterator.contains(\"val2\") = " + m1.valuesIterator.contains("val2"))                       // true

    for ((k, v) <- m1)
        println(s"${k} -> ${v}")
    val y = for ((k,v) <- m1) yield {
      s"${k} -> ${v}"
    }
    println("y = " + y)

    m1.foreach(t => println(t))
    m1.foreach(t => println(t._1 + " -> " + t._2))
    m1.keys.foreach((k) => println(k))
    m1.values.foreach(v => println(v))  // brackets around index optional always?

    val m2 = m1.map(t => (t._1 + "-ix", t._2 + "-ix"))
    println("m2 = " + m2)
    
    val m3 = m2.map {
      case (k, v) => (k + "-ix", v + "-ix")
      //case (k, v) => (k + "-ix") -> (v + "-ix")     // works too
    }
    println("m3 = " + m3)
    
    val m4 = m3.map {
      case (k, v) => (k.toUpperCase, v.toUpperCase)
      //case (k, v) => k.toUpperCase -> v.toUpperCase // works too
    }
    println("m4 = " + m4)

  } // doThem
} // GeneralMapOps

//======================================
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  // parade of general map operations
  GeneralMapOps.doThem
}