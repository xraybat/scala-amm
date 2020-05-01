import ammonite.ops._

sealed trait Colour
sealed trait Red extends Colour
sealed trait Amber extends Colour
sealed trait Green extends Colour

case class TrafficLightState[Signal <: Colour]() {
  def stop(implicit ev: Signal =:= Amber) = TrafficLightState[Red]()
  def caution() = TrafficLightState[Amber]()
  def go(implicit ev: Signal =:= Amber) = TrafficLightState[Green]()
}

TrafficLightState[Green]().caution
TrafficLightState[Red]().caution
TrafficLightState[Amber]().caution
TrafficLightState[Amber]().stop
TrafficLightState[Amber]().go

// causes *compile* time type errors
//TrafficLightState[Green]().stop
//TrafficLightState[Red]().go

case class TrafficLight(signal: Colour)
case class Change[Step]()

object TrafficLightTransition {
  def initial(tl: TrafficLight): TrafficLightState[Red] = TrafficLightState[Red]
  def stop(tls: TrafficLightState[Amber]): TrafficLightState[Red] = TrafficLightState[Red]
  def caution(tls: TrafficLightState[Red]): TrafficLightState[Amber] = TrafficLightState[Amber]
  def go(tls: TrafficLightState[Amber]): TrafficLightState[Green] = TrafficLightState[Green]
}
