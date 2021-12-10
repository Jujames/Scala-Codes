  package lectures.part1basics


  object ValueVariableTypes extends App {

  val x: Int  = 42
  println(x)

  // VALS ARE IMMUTABLE

  // COMPILER can infer types


  val aString: String = "Hello"
  val anotherString = "goodbye"

  val aBoolean: Boolean = false
  val aChar: Char = 'a'
  val anInt: Int = x
  val aShort: Short = 5124
  val aLong: Long = 5214741256325665523L
  val aFloat: Float = 2.0f
  val aDouble: Double = 3.14

  // variables
  var aVariable: Int = 4

  aVariable = 5  // side effects
}
