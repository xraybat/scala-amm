sealed trait DoorState
sealed trait Open extends DoorState
sealed trait Closed extends DoorState

// *type* `State` must be subtype, `<:`, of `DoorState`
case class Door[State <: DoorState](){
  // `State` *types* must be equivalent, `=:=`, of (sub-)`DoorState`s
  def open(implicit ev: State =:= Closed) = Door[Open]()
  def close(implicit ev: State =:= Open) = Door[Closed]()
}

def open(implicit ev: State =:= Closed) = Door[Open]()

Door[Open]().close
Door[Closed]().open

// causes *compile* time type errors (even w/out an implicit `ev`)
Door[Open]().open
Door[Closed]().close
