package part3fp

object WhatsAFunction extends App {

  // use function as first class elements
  // problem: oop
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function[A,B]
  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types Function2[A, B, R] === (A,B) => R

  // ALL SCALA FUNCTION ARE OBJECTS

  /*
     1.  A FUNCTION WHICH TAKES 2 STRINGS AND CONCATENATES THEM
     2.  TRANSFORM THE MyPredicate and MyTransformer into function types
     3.  defines a function which takes an int and returns another function which takes an int and returns an int
         - what's the type of this function
         - how to do it
  */
  def concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String =  a + b
  }
  println(concatenator("Hello ", "Julius"))

  // Function 1[Int, Function1[Int, Int]]
  val superAdder: Function1[Int, Function1[Int, Int]] = new Function[Int, Function1[Int, Int]] {
    override def apply(x: Int): Function1[Int, Int] = new Function[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }
  val adder3 = superAdder(3)
  println(adder3(4))
  println(superAdder(3)(4)) // curried function
}

trait MyFunction[A, B] {
  def apply(element: A): B
}
