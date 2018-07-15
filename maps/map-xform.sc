object MapAMapWithAXformMap {
  type StartMap = Map[String, String]                                                                 // start with a map...
  type EndMap = StartMap                                                                              // ...and return a result map
  type XformMap = Map[String, (Dispatcher.Functions.Type, Dispatcher.Operands.Type, Option[String])]  // ...xformed via a xformation map
    
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
            case Dispatcher.Functions.Reverse => Dispatcher.Operander.operate0(operands, dispatcher, k, v)

            // one extra arg (already an `Option`-al) plus k and v
            case Dispatcher.Functions.Suffix
               | Dispatcher.Functions.Prefix
               | Dispatcher.Functions.Replace => Dispatcher.Operander.operate1(operands, dispatcher, k, v, mapXform(k)._3)

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
  object Functions extends Enumeration {
    type Type = Value
    val Suffix, Prefix, Reverse, Replace = Value
  }

  object Operands extends Enumeration {
    type Type = Value
    val Both, Key, Val = Value
  }

  // signatures should match type `FunctionSignature`; `v` is the value to be xformed,
  // `arg(0...)` are the extra (`Option`-al) args to be applied to the xformation
  private def suffix(v: String, args: Option[String]*): String = v + args(0).getOrElse("")
  private def prefix(v: String, args: Option[String]*): String = args(0).getOrElse("") + v
  private def reverse(v: String, args: Option[String]*): String = v.reverse
  private def replace(v: String, args: Option[String]*): String = args(0).getOrElse(v)    // if no replacement string just return original
  
  // dispatcher functions use optional varargs
  type FunctionSignature = (String, Option[String]*) => String

  // value, not a function
  val dispatch: Map[Functions.Type, FunctionSignature] = Map(
    Functions.Suffix -> suffix,
    Functions.Prefix -> prefix,
    Functions.Reverse -> reverse,
    Functions.Replace -> replace
  )

  // use this "operander" object to handle dispatching of both/key/val operand variations with diffrerent arg lists: 0, 1, etc
  object Operander {
    // call with dispatcher function as mapXform(k)._1, operands as mapXform(k)._2, args as mapXform(k)._3 and so on
    // remove these and pass on varargs directly to operate() from map(), above?? or not possible with tuple syntax there??
    def operate0(operands: Dispatcher.Operands.Type, dispatcher: Dispatcher.FunctionSignature, k: String, v: String) = operate(operands, dispatcher, k, v)
    def operate1(operands: Dispatcher.Operands.Type, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, arg: Option[String]) = operate(operands, dispatcher, k, v, arg)
    def operate2(operands: Dispatcher.Operands.Type, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, arg1: Option[String], arg2: Option[String]) = operate(operands, dispatcher, k, v, arg1, arg2)

    // handle varargs all in one; type ascription (`: _*`) ahoy!
    private def operate(operands: Dispatcher.Operands.Type, dispatcher: Dispatcher.FunctionSignature, k: String, v: String, args: Option[String]*) = {
      operands match {                   // tuple        // extra args, if any
        case Dispatcher.Operands.Both => ( dispatcher(k, args: _*), dispatcher(v, args: _*) )
        case Dispatcher.Operands.Key  => ( dispatcher(k, args: _*), v )  // return v unscathed
        case Dispatcher.Operands.Val  => ( k, dispatcher(v, args: _*) )  // return k unscathed
      }
    } // operate
  } // Operander
} // Dispatcher

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
    "key1" ->  (Dispatcher.Functions.Suffix, Dispatcher.Operands.Both, Some("-ix")), 
    "key2" ->  (Dispatcher.Functions.Suffix, Dispatcher.Operands.Key, Some("-ix")),
    "key3" ->  (Dispatcher.Functions.Suffix, Dispatcher.Operands.Val, Some("-ix")),
    "key4" ->  (Dispatcher.Functions.Prefix, Dispatcher.Operands.Both, Some("ix-")),
    "key5" ->  (Dispatcher.Functions.Prefix, Dispatcher.Operands.Key, Some("ix-")),
    "key6" ->  (Dispatcher.Functions.Prefix, Dispatcher.Operands.Val, Some("ix-")),
    // no arg supplied
    "key7" ->  (Dispatcher.Functions.Reverse, Dispatcher.Operands.Both, None),
    "key8" ->  (Dispatcher.Functions.Reverse, Dispatcher.Operands.Key, None),
    "key9" ->  (Dispatcher.Functions.Reverse, Dispatcher.Operands.Val, None),
    "key10" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Both, Some("new-kv-10")),
    "key11" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Key, Some("new-k-11")),
    "key12" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Val, Some("new-v-12")),
    // after xform, same (k, v)-tuples collapse into a single tuple based on key...
    "key13" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Both, Some("new-kv-13-14")),
    "key14" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Both, Some("new-kv-13-14")),
    // ...while, after xform, same keys collapse into a single tuple with only one of the values surviving (the first)??...
    "key15" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Key, Some("new-k-15-16")),
    "key16" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Key, Some("new-k-15-16")),
    // ...finally, after xform, same values are spread across different key tuples
    "key17" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Val, Some("new-v-17-18")),
    "key18" -> (Dispatcher.Functions.Replace, Dispatcher.Operands.Val, Some("new-v-17-18"))
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