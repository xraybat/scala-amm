import upickle.default._
import pprint._

val jsonStr = os.read(os.pwd / "ammonite-releases.json")
pprintln(jsonStr)

val data = ujson.read(jsonStr)
pprintln(data(0))

val arr = ujson.Arr(ujson.Obj("response" -> ujson.Str("available")))
pprintln(ujson.write(arr))

val map = ujson.Obj("response" -> ujson.Str("available"))
pprintln(ujson.write(map))