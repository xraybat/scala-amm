object Chef {
  sealed trait Pizza
  object Pizza {
    sealed trait EmptyPizza extends Pizza
    sealed trait Cheese extends Pizza
    sealed trait Topping extends Pizza
    sealed trait Dough extends Pizza

    type FullPizza = EmptyPizza with Cheese with Topping with Dough

  } // Pizza
} // Chef

case class Food(ingredients: Seq[String])

// *type* `Pizza` must be subtype, `<:`, of `Chef.Pizza`
class Chef[Pizza <: Chef.Pizza](ingredients: Seq[String] = Seq()) {
  import Chef.Pizza._

  def addCheese(cheeseType: String): Chef[Pizza with Cheese] = new Chef(ingredients :+ cheeseType)
  def addTopping(toppingType: String): Chef[Pizza with Topping] = new Chef(ingredients :+ toppingType)
  def addDough: Chef[Pizza with Dough] = new Chef(ingredients :+ "dough")

  // *type* `Pizza` must be equivalent, `=:=`, of `FullPizza`
  def build(implicit ev: Pizza =:= FullPizza): Food = Food(ingredients)

} // Chef

// will cause *compile* time type error
//new Chef[Chef.Pizza.EmptyPizza]().addDough.build

// all good at *compile* time
new Chef[Chef.Pizza.EmptyPizza]()
        .addCheese("mozzarella")
        .addDough
        .addTopping("olives")
        .build

println("all good!!")