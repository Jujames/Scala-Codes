package WorkExample

object MethodsAndCaseMatching extends App {
  /*
  Section 1: Methods
     1. Create a case class for Pizza that includes the following fields:
     a. toppings - List of Strings
     b. sizeInches - integer
     c. cost - integer
     d. discountApplied - Optional Boolean
     e. name - String */

  case class Pizza(topping: List[String], sizeInches: Int, cost: Int, discountApplied: Boolean, name: String) {

    // 2. Create a method called tellMeAboutThePie that takes a pizza object as input, and returns a String
    def tellMeAboutThePie(pizza: Pizza): String = {
      val discount = if (discountApplied == true) "includes"
      else "not include"
      s"This pizza is called $name, and it has the following toppings: It costs $cost dollars, " +
        s"which $discount It is $sizeInches inches in diameter."


    }
  }
    /* 3. The method should return the following statement: "This pizza is called [name], and it has the
        following toppings: [toppings separated by comma]. It costs [cost] dollars, which ["includes" or "does
        not include"] a discount. It is [sizeInches] inches in diameter."
        - note: I do not expect this method to be done in one line
     4. Instantiate 2 or 3 different kinds of pizzas, and call tellMeAboutThePie method for each one.
    */

      val meatLover = new Pizza(topping = List("peperoni","sausage","hambuger","bacon"), sizeInches = 14, cost = 20, discountApplied = true, name = "Meat lovers")
      val chickenBacon = new Pizza(topping = List("chicken", "bacon", "onions"), sizeInches = 16, cost = 17, discountApplied = false, name = "Clucker")
      val supreme = new Pizza(topping = List("peperoni","onions", "mushroom", "bacon", "sausage", "hamburger"),sizeInches = 20, cost = 22, discountApplied = false, name = "Supreme")
      val Pep = new Pizza(topping= List("peperoni"),sizeInches = 14, cost = 18, discountApplied = true, name = "Peperoni")
      val Sausage = new Pizza(topping= List("sausage"),sizeInches = 14, cost = 18, discountApplied = false, name = "Sausage")

       /* val myObject: Int = 123
       myObject match {
        case s: String => "its a String"
        case i: Int => "its an Integer"
        case _ => "it's something else"
        */



      }

