object Chef {

  sealed trait Pizza
  object Pizza {
    sealed trait EmptyPizza extends Pizza
    sealed trait Cheese extends Pizza
    sealed trait Topping extends Pizza
    sealed trait Dough extends Pizza

    type FullPizza = EmptyPizza with Cheese with Topping with Dough
  }
}

case class Food(ingredients: Seq[String])

class Chef[Pizza <: Chef.Pizza](ingredients: Seq[String] = Seq()) {
  import Chef.Pizza._

  def addCheese(cheeseType: String): Chef[Pizza with Cheese] = new Chef(ingredients :+ cheeseType)
  def addTopping(toppingType: String): Chef[Pizza with Topping] = new Chef(ingredients :+ toppingType)
  def addDough: Chef[Pizza with Dough] = new Chef(ingredients :+ "dough")

  def build(implicit ev: Pizza =:= FullPizza): Food = Food(ingredients)
}

// will cause *compile* time error
//new Chef[Chef.Pizza.EmptyPizza]().addDough.build

new Chef[Chef.Pizza.EmptyPizza]()
        .addCheese("mozzarella")
        .addDough
        .addTopping("olives")
        .build