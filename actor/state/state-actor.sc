trait Actor[T] {
  def send(t: T)
}

abstract class SimpleActor[T] extends Actor[T] {
  def send(t: T) { t }  // doesn't invoke state `run()`
  // because there isn't one...until now:
  def run(msg: T): Unit
}

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

class PingPong extends StateMachineActor[Msg] {
  def initialState = Ponger()

  case class Pinger() extends State({
    case Ping() =>
      println("ping")
      Ponger()
  })

  case class Ponger() extends State({
    case Pong() =>
      println("pong")
      Pinger()
  })
}

//////////////////////////////////////////////////////////////////////
import ammonite.ops._

@main
def main(args: String*) = {
  val pp = new PingPong()
  pp.send(Ping())
  println("hello")

} // main