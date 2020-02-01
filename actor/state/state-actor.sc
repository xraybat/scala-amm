trait Actor[T] {}

abstract class BaseActor[T]() extends Actor[T] {}
abstract class SimpleActor[T]() extends BaseActor[T] {}

abstract class StateMachineActor[T]() extends SimpleActor[T]() {
  class State(run0: T => State = null) {
    def run = run0
  }
  protected[this] def initialState: State
  private[this] var state0: State = null
  protected[this] def state = {
    if (state0 == null) state0 = initialState
    state0
  }
  def run(msg: T): Unit = {
    state0 = state.run(msg)
  }
} 

sealed trait Msg
case class Ping() extends Msg
case class Pong() extends Msg
case class Out() extends Msg

class PingPong extends StateMachineActor[Msg] {
  def initialState = Ponger()

  case class Ponger() extends State({
    case Pong() =>
      println("pong")
      Ponger()
  })
}

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

@main
def main(args: String*) = {
  val pp = new PingPong()
  println("hello")

} // main