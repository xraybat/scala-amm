// from https://medium.com/@maximilianofelice/builder-pattern-in-scala-with-phantom-types-3e29a167e863

// traits (not case classes) since (type-)"matching" at compile time
sealed trait DoorState
sealed trait Open extends DoorState
sealed trait Closed extends DoorState

// *type* `State` must be subtype, `<:`, of `DoorState`
case class Door[State <: DoorState]() {
  // `State` *types* must be equivalent, `=:=`, of (sub-)`DoorState`s
  // the (never instantiated) implicit `ev` forces (meta-)type information
  def open(implicit ev: State =:= Closed) = Door[Open]()
  def close(implicit ev: State =:= Open) = Door[Closed]()
}

Door[Open]().close
Door[Closed]().open

// causes *compile* time type errors (even w/out an implicit `ev`)
//Door[Open]().open
//Door[Closed]().close

// or...
trait AbstractState

abstract class AbstractDoor {
  type State <: AbstractState
  def state: State
  //val _state: State   // note: using a '_' prefix (only) causes a compile error...
}

trait OpenDoor extends AbstractState
class Opens(val state: OpenDoor) extends AbstractDoor {
  type State = OpenDoor
}

trait CloseDoor extends AbstractState // commenting out 'extends' causes *compile* time type error
class Closes(val state: CloseDoor) extends AbstractDoor {
  type State = CloseDoor
}
