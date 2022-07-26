object ArgsMap {
  type ArgsMapType = Map[String, (String, Option[List[String]])]
}

//////////////////////////////////////////////////////////////////////

// (ಠ_ಠ)
@main
def main(args: String*) = {
  val mapArgs: ArgsMap.ArgsMapType = Map(
    "key1" -> ("fixed", Some(List("-ix"))),                     // single arg supplied 
    "key2" -> ("fixed", None),                                  // no arg supplied
    "key3" -> ("fixed", Some(List("add-v-19-1", "add-v-19-2"))) // multiple args supplied
  )

  println("mapArgs = " + mapArgs)
  
} // main