sealed trait DoorState
sealed trait Open extends DoorState
sealed trait Closed extends DoorState

case class Door[State <: DoorState](){
  def open(implicit ev: State =:= Closed) = Door[Open]()
  def close(implicit ev: State =:= Open) = Door[Closed]()
}

def open(implicit ev: State =:= Closed) = Door[Open]()

Door[Open]().close
Door[Closed]().open

// causes *compile* time errors; even w/out an actual implicit `ev`
Door[Open]().open
Door[Closed]().close
