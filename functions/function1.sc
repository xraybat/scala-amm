object MyFunctionObj {
 def apply(k: String, v: String): String = k + ": " + v
}

object MyFunctionObj2 extends Function2[String, String, String] {
 def apply(k: String, v: String): String = k + " = " + v
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// (ಠ_ಠ)
@main
def main(args: String*) = {
/*
  // assign an object representing the function to a variable
  val f1 = (x: Int) => x + 1
  println(f1(2))
  // define another function1 by calling compose method on f1 and chaining two different functions together
  val f2 = (x: Int) => x * 2
  // now (as a mathematician would say) 'apply a function to its arguments' by calling `apply()`
  println(f2.apply(2))
  // now compose thetwo functions into one
  val f3 = f1 compose f2
  println(f3(3))  // (3*2)+1
*/
/*
  val f1 = (v: String) => v + ": " + v
  println(f1("val1"))

  val f2 = (v: String) => v + " = " + v
  println(f2.apply("val2"))

  val f3 = f1 compose f2
  println(f3("val3"))
*/
  // apply the arguments to the function by `apply()`; these two are synonomous got convenience9
  println(MyFunctionObj.apply("key1", "val2"))
  println(MyFunctionObj("key1", "val2"))
  println(MyFunctionObj2("key1", "val2"))
  println(MyFunctionObj("key1", "val2") != MyFunctionObj2("key1", "val2"))

  val func2Anon = new Function2[String, String, String] {
    def apply(k: String, v: String): String = k + ": " + v
  }
  println(func2Anon.apply("key1", "val2"))
  println(func2Anon("key1", "val2"))

  println(MyFunctionObj("key1", "val2") == func2Anon("key1", "val2"))

} // main