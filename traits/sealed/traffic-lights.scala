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

object TrafficLight {
  private var tls[Red]: TrafficLightState[Red]

  def initial: TrafficLightState[Red] = TrafficLightState[Red]

  def stop: TrafficLightState[Red] = TrafficLightState[Red]
  def caution: TrafficLightState[Amber] = TrafficLightState[Amber]
  def go: TrafficLightState[Green] = TrafficLightState[Green]
}
