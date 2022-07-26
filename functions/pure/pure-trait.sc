sealed trait PureSingleArgExplicitRt {
  val f: (Int) => Int
}

sealed trait PureDualArgExplicitRt {
  val f: (Int, Int) => Int
}

case object PureSingleImplicitRt extends PureSingleArgExplicitRt {
  val f = (x: Int) => x + 1                   // using implicit return type (IRT) syntax
}

case object PureDualExplicitRt extends PureDualArgExplicitRt {
  val f: (Int, Int) => Int = (x, y) => x + y  // using explicit return type (ERT) syntax
}

////////////////////////////////////////////////////////////////////////////////////////////////////

// (ಠ_ಠ)
@main
def main(args: String*) = {
  val x1 = 1
  val x2 = 3
  val y2 = 4

  println("PureSingleImplicitRt.f = " + PureSingleImplicitRt.f(x1))
  println("PureDualExplicitRt.f = " + PureDualExplicitRt.f(x2, y2))

} // main