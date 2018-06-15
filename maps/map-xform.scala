object MapAMapWithAXformMap {
  type StartMap = Map[String, String]                                         // start with a map...
  type EndMap = StartMap                                                      // ...and return a result map
  type XformMap = Map[String, (Dispatcher.Functions.Type, Option[String])]    // ...xformed via a xformation map
  
  // map a map using (functions in) a xformation map
  def map(mapStart: StartMap, mapXform: XformMap): EndMap = {
    mapStart.map {
      case (k, v) => {
        // make sure to return a tuple from all paths
        if (mapXform.contains(k)) {
          // leave keys alone??
          mapXform(k)._1 match {
            // zero args apart from k and v      // tuple     // func  // idx          // arg
            case Dispatcher.Functions.Reverse => ( Dispatcher.dispatch(mapXform(k)._1)(k),
                                                   Dispatcher.dispatch(mapXform(k)._1)(v) )

            // one extra arg (already an `Option`-al) plus k and v
            case Dispatcher.Functions.Suffix
               | Dispatcher.Functions.Prefix     // tuple     // func  // idx          // arg list
               | Dispatcher.Functions.Replace => ( Dispatcher.dispatch(mapXform(k)._1)(k, mapXform(k)._2),
                                                   Dispatcher.dispatch(mapXform(k)._1)(v, mapXform(k)._2) )

            case _ => (k, v)    // no xformation,  just pass thru

          } // match
        } // if
        else
            (k, v)  // no xformation,  just pass thru

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
} // Dispatcher

//======================================
import ammonite.ops._

@main
def main(args: String*) = {
  // (ಠ_ಠ)
  // map a map using (functions in) another map
  val mapFrom: MapAMapWithAXformMap.StartMap = Map("key1" -> "val1", "key2" -> "val2", "key3" -> "val3", "key4" -> "val4", "key5" -> "val5")
  val mapWith: MapAMapWithAXformMap.XformMap = Map("key1" -> (Dispatcher.Functions.Suffix, Some("-ix")),
                                                   "key2" -> (Dispatcher.Functions.Prefix, Some("ix-")),
                                                   "key3" -> (Dispatcher.Functions.Reverse, None),    // no arg used
                                                   "key4" -> (Dispatcher.Functions.Replace, Some("newval")))

  println("mapFrom = " + mapFrom)
  println("mapWith = " + mapWith)
  
  // use object directly with arg list
  println("mapTo = " + MapAMapWithAXformMap.map(mapFrom, mapWith))

  // use companion class with ctor and members, no arg list
  println("mapTo = " + new MapAMapWithAXformMap(mapFrom, mapWith).map)

} // main