////////////////////////////////////////////////////////////////////////////////////////////////////
// į̵̘͍̻̟͔̥̠̦̕ h͓o̢̼̪̞͢p̳̲͠e̯̦̘̝͈̼̱͝ y̷͕͕ơ̴̵̠ú̹̳̻̳̜’̩̰r̸̢̲͉̼̯̬̥ͅͅe̟̦͕̼̞͇͠ w̶̛̖͖̞͇e̷̯̰͎͖̮l̶̻̩̀͝ļ̰̦͈͢͠
object MapAMapWithAXformMap {
  type StartMap = Map[String, String]                     // start with a map...
  type EndMap = StartMap                                  // ...and return a result map...
  type XformMap = Map[String, XformTuple]                 // ...xformed via a xformation map...
  type XformTuple = (Xformer, Operands, Option[String])   // ...made up of these xformer tuples
    
  // map a map using (functions in) a xformation map
  def map(mapStart: StartMap, mapXform: XformMap): EndMap = {
    mapStart.map {
      case (k, v) => {
        // make sure to return a tuple from all paths
        if (mapXform.contains(k)) {
          val (xformer, operands, arg) = ((xt: XformTuple) => (xt._1, xt._2, xt._3))(mapXform(k))

          // match even necessary??
          xformer match {
            case Reverse
               | Suffix
               | Prefix
               | Replace => operands.op(xformer, k, v, arg)
            case _ => (k, v)  // no xformation, just pass thru
          }
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
// xform functions; signatures should match type `Sig`; `v` is the value to be xformed,
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

object Xformer {
  // xform functions use optional varargs; was `Option[Seq]*` but error
  // 'repeated parameters are only allowed in method signatures; use Seq
  // instead' led to change to `Option[Seq[String]]`
  type Sig = (String, Option[Seq[String]]) => String
}

// companion (abstract) class for xform function case object extends; insists on `op()` method
abstract class Xformer {
  def op(v: String, args: Option[String]*): String
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// pass varargs, if any, via type ascription (`: _*`)
case object Both extends Operands {
  override def op(xformer: Xformer, k: String, v: String, args: Option[String]*): (String, String) =
    (xformer.op(k, args: _*), xformer.op(v, args: _*))
}
case object Key extends Operands {
  override def op(xformer: Xformer, k: String, v: String, args: Option[String]*): (String, String) =
    (xformer.op(k, args: _*), v)  // return v unscathed
}
case object Val extends Operands {
  override def op(xformer: Xformer, k: String, v: String, args: Option[String]*): (String, String) =
    (k, xformer.op(v, args: _*))  // return k unscathed
}

// handles both/key/val operand variations
abstract class Operands {
  def op(xformer: Xformer, k: String, v: String, args: Option[String]*): (String, String)
}

//////////////////////////////////////////////////////////////////////

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

  // has taken over the role of dispatcher
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