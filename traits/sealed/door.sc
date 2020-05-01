// from https://medium.com/@maximilianofelice/builder-pattern-in-scala-with-phantom-types-3e29a167e863

// traits (not case classes) since (type-)"matching" at compile time
sealed trait DoorState
sealed trait Open extends DoorState
sealed trait Closed extends DoorState

// *type* `State` must be subtype, `<:`, of `DoorState`
case class Door[State <: DoorState](){
  // `State` *types* must be equivalent, `=:=`, of (sub-)`DoorState`s
  // the (never instantiated) implicit `ev` forces (meta-)type information
  def open(implicit ev: State =:= Closed) = Door[Open]()
  def close(implicit ev: State =:= Open) = Door[Closed]()
}

def open(implicit ev: State =:= Closed) = Door[Open]()

Door[Open]().close
Door[Closed]().open

// causes *compile* time type errors (even w/out an implicit `ev`)
Door[Open]().open
Door[Closed]().close
