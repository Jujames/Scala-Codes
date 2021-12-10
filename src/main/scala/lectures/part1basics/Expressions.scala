package lectures.part1basics

object Expressions extends App {

  val x = 1 + 2 // EXPRESSION
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x)
  // == != > >= < <=

  println(!(1 == x))
  // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ....... side effects
  println(aVariable)

  // Instruction (DO) vs Expression (VALUE)

  // If expression
  val aCondition = false
  val aConditionValue = if(aCondition) 5 else 3 // IF EXPRESSION
  println(aConditionValue)
  println(if(aCondition) 5 else 3 )

  // NEVER WRITE THIS AGAIN.
  var i = 0
  val aWhile = while (i < 10) {
    println(i)
    i += 1
  }

  // EVERTHING in scala is an Expression

  val aWeirdValue = (aVariable = 3)  // Unit === void
  println(aWeirdValue)

  // side effects: println(), whiles, reassigning

  // Code Blocks

  val aCodeBlock = {
    val y = 2
    val z = y + 1

    if (z > 2) "hello" else "goodbye"
      }
  // 1. difference between "hello world" vs println("hello world")
  // 2.
  val someValue = {
    2 < 3
  }
  println(someValue)

  val someOtherValue = {
    if (someValue) 239 else 986  // this doesn't matter 42 is the value
      42
    }
  println(someOtherValue)
}
