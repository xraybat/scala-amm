////////////////////////////////////////////////////////////////////////////////////////////////////
// į̵̘͍̻̟͔̥̠̦̕ h͓o̢̼̪̞͢p̳̲͠e̯̦̘̝͈̼̱͝ y̷͕͕ơ̴̵̠ú̹̳̻̳̜’̩̰r̸̢̲͉̼̯̬̥ͅͅe̟̦͕̼̞͇͠ w̶̛̖͖̞͇e̷̯̰͎͖̮l̶̻̩̀͝ļ̰̦͈͢͠
object MapAMapWithAXformMap {
  type StartMap = Map[String, String]                               // start with a map...
  type EndMap = StartMap                                            // ...and return a result map
  type XformMap = Map[String, (Xformer, Operands, Option[String])]  // ...xformed via a xformation map
    
  // map a map using (functions in) a xformation map
  def map(mapStart: StartMap, mapXform: XformMap): EndMap = {
    mapStart.map {
      case (k, v) => {
        // make sure to return a tuple from all paths
        if (mapXform.contains(k)) {
          val function = mapXform(k)._1
          val dispatcher = function.op _
          val operander = Dispatcher.dispatch(function)
          val operands = mapXform(k)._2

          // use Arity0..n stored in `Dispatcher.dispatch(function)`??
          function match {
            case Reverse => Arity0.op(operands, dispatcher, k, v) // no extra args apart from k and v
            case Suffix | Prefix | Replace => Arity1.op(operands, dispatcher, k, v, mapXform(k)._3) // one extra arg (already an `Option`-al) plus k and v
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

////////////////////////////////////////////////////////////////////////////////////////////////////
// dispatcher for xform functions
object Dispatcher {
  // value, not a function, indexed by xform function
  // don't store Xformer.op in value tuple, just use it from *key* Xformer??
  val dispatch: Map[Xformer, Arity] = Map(
    Suffix  -> Arity1,
    Prefix  -> Arity1,
    Reverse -> Arity0,
    Replace -> Arity1
  )
} // Dispatcher

////////////////////////////////////////////////////////////////////////////////////////////////////
// xform functions; signatures should match type `Signature`; `v` is the value to be xformed,
// `arg(0...)` are the extra (`Option`-al) args to be applied to the xformation
case object Suffix extends Xformer {
  override def op(v: String, args: Option[String]*): String = v + args(0).getOrElse("")
}

case object Prefix extends Xformer {
  override def op(v: String, args: Option[String]*): String = args(0).getOrElse("") + v
}

case object Reverse extends Xformer {
  override def op(v: String, args: Option[String]*): String = v.reverse
}

case object Replace extends Xformer {
  override def op(v: String, args: Option[String]*): String = args(0).getOrElse(v)    // if no replacement string just return original
}

// xform function; remove this object when/if enum goes??
object Xformer {
  // xform functions use optional varargs
  type Signature = (String, Option[String]*) => String

} // Xformer

// companion (abstract) class for xform function case object extends; insists on `op()` method
abstract class Xformer {
  def op(v: String, args: Option[String]*): String
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// Arity0..n provide arity checking
object Arity0 extends Arity {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String): (String, String) =
    Arity.op(operands, dispatcher, k, v)
}

object Arity1 extends Arity {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, arg: Option[String]): (String, String) =
    Arity.op(operands, dispatcher, k, v, arg)
}

object Arity2 extends Arity {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, arg1: Option[String], arg2: Option[String]): (String, String) =
    Arity.op(operands, dispatcher, k, v, arg1, arg2)
}

// use this arity object to handle dispatching of vararg lists: 0, 1, etc
//   `Arity.op()` becomes `super.op()` when object -> abstract class??
object Arity {
  // call with dispatcher function as mapXform(k)._1, operands as mapXform(k)._2, args as mapXform(k)._3 and so on
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, args: Option[String]*): (String, String) = {
    // pass varargs, if any, via type ascription (`: _*`)
    operands match {
      case Both => ( dispatcher(k, args: _*), dispatcher(v, args: _*) )
      case Key  => ( dispatcher(k, args: _*), v )  // return v unscathed
      case Val  => ( k, dispatcher(v, args: _*) )  // return k unscathed
    }
  } // op
} // Arity

// companion class for Arity0..n extends
abstract class Arity

////////////////////////////////////////////////////////////////////////////////////////////////////
// pass varargs, if any, via type ascription (`: _*`)
case object Both extends Operands {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, args: Option[String]*): (String, String) =
    ( dispatcher(k, args: _*), dispatcher(v, args: _*) )
}

case object Key extends Operands {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, args: Option[String]*): (String, String) =
    ( dispatcher(k, args: _*), v )  // return v unscathed
}

case object Val extends Operands {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, args: Option[String]*): (String, String) =
    ( k, dispatcher(v, args: _*) )  // return k unscathed
}

// use this operands object to handle both/key/val operand variations
abstract class Operands {
  def op(operands: Operands, dispatcher: Xformer.Signature, k: String, v: String, args: Option[String]*): (String, String)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
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
    "key1" ->  (Suffix, Both, Some("-ix")), 
    "key2" ->  (Suffix, Key, Some("-ix")),
    "key3" ->  (Suffix, Val, Some("-ix")),
    "key4" ->  (Prefix, Both, Some("ix-")),
    "key5" ->  (Prefix, Key, Some("ix-")),
    "key6" ->  (Prefix, Val, Some("ix-")),
    // no arg supplied
    "key7" ->  (Reverse, Both, None),
    "key8" ->  (Reverse, Key, None),
    "key9" ->  (Reverse, Val, None),
    "key10" -> (Replace, Both, Some("new-kv-10")),
    "key11" -> (Replace, Key, Some("new-k-11")),
    "key12" -> (Replace, Val, Some("new-v-12")),
    // after xform, same (k, v)-tuples collapse into a single tuple based on key...
    "key13" -> (Replace, Both, Some("new-kv-13-14")),
    "key14" -> (Replace, Both, Some("new-kv-13-14")),
    // ...while, after xform, same keys collapse into a single tuple with only one of the values surviving (the first)??...
    "key15" -> (Replace, Key, Some("new-k-15-16")),
    "key16" -> (Replace, Key, Some("new-k-15-16")),
    // ...finally, after xform, same values are spread across different key tuples
    "key17" -> (Replace, Val, Some("new-v-17-18")),
    "key18" -> (Replace, Val, Some("new-v-17-18"))
  )

  println("mapFrom = " + mapFrom); println
  println("mapWith = " + mapWith); println
  
  // use object directly with arg list
  println("mapTo = " + MapAMapWithAXformMap.map(mapFrom, mapWith)); println

  // use companion class with ctor and members, no arg list
  println("mapTo = " + new MapAMapWithAXformMap(mapFrom, mapWith).map)

} // main