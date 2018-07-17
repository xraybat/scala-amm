object MapAMapWithAXformMap {
  type StartMap = Map[String, String]                                                      // start with a map...
  type EndMap = StartMap                                                                   // ...and return a result map
  type XformMap = Map[String, (Dispatcher.Functions, Operander.Operands, Option[String])]  // ...xformed via a xformation map
    
  // map a map using (functions in) a xformation map
  def map(mapStart: StartMap, mapXform: XformMap): EndMap = {
    mapStart.map {
      case (k, v) => {
        // make sure to return a tuple from all paths
        if (mapXform.contains(k)) {
          val function = mapXform(k)._1
          val dispatcher = Dispatcher.dispatch(function)
          val operands = mapXform(k)._2

          function match {
            // no extra args apart from k and v
            case Dispatcher.Reverse => Operander0.operate(operands, dispatcher, k, v)

            // one extra arg (already an `Option`-al) plus k and v
            case Dispatcher.Suffix
               | Dispatcher.Prefix
               | Dispatcher.Replace => Operander1.operate(operands, dispatcher, k, v, mapXform(k)._3)

            case _ => (k, v)  // no xformation, just pass thru

          } // match
        } // if
        else
          (k, v)  // no xformation, just pass thru

      } // case
    } // map
  } // map
} // MapAMapWithAXformMap

// companion class (note: companion classes can access private members of companion object, if needed)
class MapAMapWithAXformMap(mapStart: MapAMapWithAXformMap.StartMap, mapXform: MapAMapWithAXformMap.XformMap) {
  def map: MapAMapWithAXformMap.EndMap = MapAMapWithAXformMap.map(mapStart, mapXform)
}

// į̵̘͍̻̟͔̥̠̦̕ h͓o̢̼̪̞͢p̳̲͠e̯̦̘̝͈̼̱͝ y̷͕͕ơ̴̵̠ú̹̳̻̳̜’̩̰r̸̢̲͉̼̯̬̥ͅͅe̟̦͕̼̞͇͠ w̶̛̖͖̞͇e̷̯̰͎͖̮l̶̻̩̀͝ļ̰̦͈͢͠
object Dispatcher {
  // either...                            // ...or (and use `Functions.Type` everywhere)
  sealed trait Functions                  //   object Functions extends Enumeration {
  case object Suffix extends Functions    //     type Type = Value
  case object Prefix extends Functions    //     val Suffix, Prefix, Reverse, Replace = Value
  case object Reverse extends Functions   //   }
  case object Replace extends Functions 

  // signatures should match type `FunctionSignature`; `v` is the value to be xformed,
  // `arg(0...)` are the extra (`Option`-al) args to be applied to the xformation
  private def suffix(v: String, args: Option[String]*): String = v + args(0).getOrElse("")
  private def prefix(v: String, args: Option[String]*): String = args(0).getOrElse("") + v
  private def reverse(v: String, args: Option[String]*): String = v.reverse
  private def replace(v: String, args: Option[String]*): String = args(0).getOrElse(v)    // if no replacement string just return original
  
  // dispatcher functions use optional varargs
  type FunctionSignature = (String, Option[String]*) => String

  // value, not a function
  val dispatch: Map[Functions, FunctionSignature] = Map(
    Suffix -> suffix,
    Prefix -> prefix,
    Reverse -> reverse,
    Replace -> replace
  )
} // Dispatcher

// Operander0..n provide arity checking
object Operander0 extends Operander {
  def operate(operands: Operander.Operands, dispatcher: Dispatcher.FunctionSignature, k: String, v: String) =
    Operander.operate(operands, dispatcher, k, v)
}

object Operander1 extends Operander {
  def operate(operands: Operander.Operands, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, arg: Option[String]) =
    Operander.operate(operands, dispatcher, k, v, arg)
}

object Operander2 extends Operander {
  def operate(operands: Operander.Operands, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, arg1: Option[String], arg2: Option[String]) =
    Operander.operate(operands, dispatcher, k, v, arg1, arg2)
}

// use this "operander" object to handle dispatching of both/key/val operand variations with diffrerent arg lists: 0, 1, etc
object Operander {
  // either...                        // ...or (and use `Operands.Type` everywhere)
  sealed trait Operands               //   object Operands extends Enumeration {
  case object Both extends Operands   //     type Type = Value
  case object Key extends Operands    //     val Both, Key, Val = Value
  case object Val extends Operands    //   }

  // call with dispatcher function as mapXform(k)._1, operands as mapXform(k)._2, args as mapXform(k)._3 and so on
  def operate(operands: Operands, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, args: Option[String]*) = {
    operands match {        // tuple        // extra args, if any
      // pass varargs via type ascription (`: _*`)
      case Both => ( dispatcher(k, args: _*), dispatcher(v, args: _*) )
      case Key  => ( dispatcher(k, args: _*), v )  // return v unscathed
      case Val  => ( k, dispatcher(v, args: _*) )  // return k unscathed
    }
  } // operate
} // Operander

// companion class for Operander0..n extends
class Operander

//======================================
import ammonite.ops._

// (ಠ_ಠ)
@main
def main(args: String*) = {
  // map a map using (functions in) another map
  val mapFrom: MapAMapWithAXformMap.StartMap = Map(
    "key1" -> "val1", "key2" -> "val2", "key3" -> "val3",
    "key4" -> "val4", "key5" -> "val5", "key6" -> "val6",
    "key7" -> "val7", "key8" -> "val8", "key9" -> "val9",
    "key10" -> "val10", "key11" -> "val11", "key12" -> "val12",
    "key13" -> "val13", "key14" -> "val14", "key15" -> "val15", "key16" -> "val16", "key17" -> "val17", "key18" -> "val18",  
    "key19" -> "val19"
  )

  val mapWith: MapAMapWithAXformMap.XformMap = Map(
    // arg supplied
    "key1" ->  (Dispatcher.Suffix, Operander.Both, Some("-ix")), 
    "key2" ->  (Dispatcher.Suffix, Operander.Key, Some("-ix")),
    "key3" ->  (Dispatcher.Suffix, Operander.Val, Some("-ix")),
    "key4" ->  (Dispatcher.Prefix, Operander.Both, Some("ix-")),
    "key5" ->  (Dispatcher.Prefix, Operander.Key, Some("ix-")),
    "key6" ->  (Dispatcher.Prefix, Operander.Val, Some("ix-")),
    // no arg supplied
    "key7" ->  (Dispatcher.Reverse, Operander.Both, None),
    "key8" ->  (Dispatcher.Reverse, Operander.Key, None),
    "key9" ->  (Dispatcher.Reverse, Operander.Val, None),
    "key10" -> (Dispatcher.Replace, Operander.Both, Some("new-kv-10")),
    "key11" -> (Dispatcher.Replace, Operander.Key, Some("new-k-11")),
    "key12" -> (Dispatcher.Replace, Operander.Val, Some("new-v-12")),
    // after xform, same (k, v)-tuples collapse into a single tuple based on key...
    "key13" -> (Dispatcher.Replace, Operander.Both, Some("new-kv-13-14")),
    "key14" -> (Dispatcher.Replace, Operander.Both, Some("new-kv-13-14")),
    // ...while, after xform, same keys collapse into a single tuple with only one of the values surviving (the first)??...
    "key15" -> (Dispatcher.Replace, Operander.Key, Some("new-k-15-16")),
    "key16" -> (Dispatcher.Replace, Operander.Key, Some("new-k-15-16")),
    // ...finally, after xform, same values are spread across different key tuples
    "key17" -> (Dispatcher.Replace, Operander.Val, Some("new-v-17-18")),
    "key18" -> (Dispatcher.Replace, Operander.Val, Some("new-v-17-18"))
  )

  println("mapFrom = " + mapFrom)
  println("mapWith = " + mapWith)
  println
  
  // use object directly with arg list
  println("mapTo = " + MapAMapWithAXformMap.map(mapFrom, mapWith))
  println

  // use companion class with ctor and members, no arg list
  println("mapTo = " + new MapAMapWithAXformMap(mapFrom, mapWith).map)

} // main