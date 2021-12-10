package lectures.part3fp

object AnonymousFunctions extends App {

  // anonymous function (LAMBDA)
  val doubler = (x: Int) =>  x * 2

  // multiple params in lambda
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params
  val justDoIt: () => Int = () => 3
  println(justDoIt) // function itself
  println(justDoIt())  // call

  // curlt braces with lambda
  val StringToInt = {(str: String) =>
    str.toInt
  }

  // More syntactic sugar
  val niceIncrementer: Int => Int = _ + 1  // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _  // eqivalent to (a,b) => a + b

  /*
    1. MyList: replace all FunctionX call with Lambdas
    2. Rewrite the "special" adders as an anynomous function
  */

  val superAdd = (x: Int) => (y: Int) => x + y
  println(superAdd(3)(4))


}
