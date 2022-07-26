trait Actor[T] {
  def send(t: T)
}

abstract class SimpleActor[T] extends Actor[T] {
  def send(t: T) { println("SimpleActor.send, t is " + t + " (send doing nothing)") }
  def run(msg: T): Unit
}

abstract class StateMachineActor[T]() extends SimpleActor[T]() {
  
  class State(run0: T => State = null) {
    def run = run0
  }
  protected[this] def initialState: State
  private[this]   var state0: State = null
  protected[this] def state = {
    if (state0 == null) state0 = initialState
    state0
  }

  def run(msg: T): Unit = {
    println("StateMachineActor.run, msg is " + msg + ", state is " + state)
    state0 = state.run(msg)
  }
} // StateMachineActor

sealed trait Msg
case class Ping() extends Msg
case class Pong() extends Msg

class PingPongFsm extends StateMachineActor[Msg] {
  def initialState = Pinger()

  case class Pinger() extends State({
    case Ping() => println("Pinger.run, Ping()"); Pinger()
    case Pong() => println("Pinger.run, Pong()"); Ponger()
  })

  case class Ponger() extends State({
    case Pong() => println("Ponger.run, Pong()"); Ponger()
    case Ping() => println("Ponger.run, Ping()"); Pinger()
  })
} // PingPongFsm

//////////////////////////////////////////////////////////////////////

@main
def main(args: String*) = {
  val pp = new PingPongFsm()
  pp.send(Ping())
  pp.run(Ping())

} // main