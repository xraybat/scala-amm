// sealed traits make adding methods easy (but new subclasses more difficult
// since all methods need to be extended with the new subclass). open traits are
// the opposite, adding new subclasses is easy, just implement exusting trait interface
// methods, but adding methods is more difficult as all subclasses need to be
// extended with the new method.

sealed trait Expr
case class BinOp(left: Expr, op: String, right: Expr) extends Expr
case class Literal(value: Int) extends Expr
case class Variable(name: String) extends Expr

def stringify(expr: Expr): String = expr match {
  case BinOp(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
  case Literal(value) => value.toString
  case Variable(name) => name
}

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  val smallExpr = BinOp(Variable("x"), "+", Literal(1))

  val largeExpr = BinOp(
    BinOp(Variable("x"), "+", Literal(1)),
    "*",
    BinOp(Variable("y"), "-", Literal(1))
  )

  println(stringify(smallExpr))
  println(stringify(largeExpr))

} // main