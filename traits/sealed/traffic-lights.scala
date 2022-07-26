
sealed trait Colour
sealed trait Red extends Colour
sealed trait Amber extends Colour
sealed trait AmberBlinking extends Colour
sealed trait Green extends Colour

case class TrafficLightState[Signal <: Colour]() {
  def stop(implicit ev: Signal =:= Amber) = TrafficLightState[Red]()
  def caution() = TrafficLightState[Amber]()
  def updating(implicit ev: Signal =:= Amber) = TrafficLightState[AmberBlinking]()
  def updated(implicit ev: Signal =:= AmberBlinking) = TrafficLightState[Amber]()
  def go(implicit ev: Signal =:= Amber) = TrafficLightState[Green]()
}

TrafficLightState[Green]().caution
TrafficLightState[Red]().caution
TrafficLightState[Amber]().caution
TrafficLightState[Amber]().updating
//TrafficLightState[AmberBlinking]().stop
TrafficLightState[AmberBlinking]().updated
TrafficLightState[AmberBlinking]().caution
TrafficLightState[Amber]().updating
//TrafficLightState[AmberBlinking]().go
TrafficLightState[AmberBlinking]().updated
TrafficLightState[Amber]().go

// causes *compile* time type errors
//TrafficLightState[Green]().stop
//TrafficLightState[Red]().go

object TrafficLight {
  def initial = TrafficLightState[AmberBlinking]

  def stop = TrafficLightState[Red]
  def caution = TrafficLightState[Amber]
  def go = TrafficLightState[Green]

  private var state = initial

  def toStop: Unit = { state = stop }
  //def toCaution: Unit = { state = caution } //stop } // works
  //def toGo: Unit = { state = go }
}
