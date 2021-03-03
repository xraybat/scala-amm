
sealed abstract class Op {
  def msg: String
  def op: Unit
  override def toString : String = msg
}

sealed trait Dir extends Op {
  def op: Unit = println(msg)
}

case class Up() extends Op with Dir {
  def msg: String = "up"
}
case class Down() extends Op with Dir {
  def msg: String = "down"
}
case class Left() extends Op with Dir {
  def msg: String = "left"
}
case class Right() extends Op with Dir {
  def msg: String = "right"
}

// actually creates a new list...
def traverse(lst: List[Op], acc: List[Op] = List.empty): List[Op] = lst match {
  case Nil    => acc.reverse
  case h :: t => traverse(t, h :: acc)
}

def operate(lst: List[Op]): Unit = lst match {
  case Nil => 
  case h :: t => h.op; operate(t)
}

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  val lst = Up() :: Down() :: Left() :: Right() :: Nil   // `()` and `Nil` reqd
  println(lst)
  println("traverse: " + traverse(lst))
  operate(lst)
}