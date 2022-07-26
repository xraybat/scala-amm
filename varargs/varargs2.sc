// do use overrides as they *are* overrides of the *same* method
/*object Arity0 extends Arity {
  override def op(k: String, v: String): (String, String) = super.op(k, v)
}

object Arity1 extends Arity {
  override def op(k: String, v: String, arg: String): (String, String) = super.op(k, v, arg)
}

object Arity2 extends Arity {
  override def op(k: String, v: String, arg1: String, arg2: String): (String, String) = super.op(k, v, arg1, arg2)
}

class Arity {
  def op( k: String, v: String): (String, String) = (k, v)
  def op(k: String, v: String, arg: String): (String, String) = (k, v + arg)
  def op(k: String, v: String, arg1: String, arg2: String): (String, String) = (k, v + arg1 + arg2)
}*/

// don't use overrides as they *aren't* overrides of the *same* method
object Arity0 extends Arity {
  def op(k: String, v: String): (String, String) = super.op(k, v)
}

object Arity1 extends Arity {
  def op(k: String, v: String, arg: String): (String, String) = super.op(k, v, arg)
}

object Arity2 extends Arity {
  def op(k: String, v: String, arg1: String, arg2: String): (String, String) = super.op(k, v, arg1, arg2)
}

class Arity {
  def op( k: String, v: String, args: String*): (String, String) = (k, v + args.toString())
}

//////////////////////////////////////////////////////////////////////

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println("arity0: " + Arity0.op("key1", "val1"))
  println("arity0: " + Arity1.op("key1", "val1", "arg1"))
  println("arity0: " + Arity2.op("key1", "val1", "arg1", "arg2"))

} // main