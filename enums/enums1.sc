
object Functions {
  // either...                            // ...or (and use `Functions.Type` everywhere)
  sealed trait Functions                  //   object Functions extends Enumeration {
  case object Suffix extends Functions    //     type Type = Value
  case object Prefix extends Functions    //     val Suffix, Prefix, Reverse, Replace = Value
  case object Reverse extends Functions   //   }
  case object Replace extends Functions 

} // Functions

object Operands {
  // either (use extra `Operands.`) ...   // ...or 
  object Operands extends Enumeration {   //   sealed trait Operands
    type Type = Value                     //   case object Both extends Operands
    val Both, Key, Val = Value            //   case object Key extends Operands     
  }                                       //   case object Val extends Operands

} // Operands

////////////////////////////////////////////////////////////////////////////////////////////////////
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  println(Functions.Suffix)
  println(Operands.Operands.Key)

} // main