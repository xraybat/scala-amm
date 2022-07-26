object VarArgs {
  def say(must: String, args: String*) = must + " " + args.mkString(" ")
  def callSay(must: String, args: String*) = say(must, args: _*)  // must use type ascription `: _*` here too to pass on varargs

} // VarArgs

//====================================================================

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println(VarArgs.say("hello,", "it", "is", "me"))
  println(VarArgs.say("hello,", List("it", "is", "me"): _*))  // must use type ascription `: _*` for sequences
  println(VarArgs.say("hello,", Array("it", "is", "me"): _*))

  println(VarArgs.callSay("hello,", "it", "is", "me"))

} // main