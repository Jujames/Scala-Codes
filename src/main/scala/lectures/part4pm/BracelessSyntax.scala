package lectures.part4pm

object BracelessSyntax {

  // if - expression
  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // java-style
  val anIfExpression_v2 =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // compact
  val anIfExpression_v3 =
    if (2 > 3) "bigger"
    else "smaller"

  // scala 3 Exclusive
  val anIfExpression_v4 =
    if 2 > 3 then
      "bigger" // higher identation than the if part
    else
       "smaller"

  val anIfExpression_v5 =
    if 2 > 3 then
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  // scala 3 one-liner
  val anIfExpression_v6 = if 2 > 3 then "bigger" else "smaller"

  // for comprehension
  val aForComprehension = for {
    n <- List(1,2,3)
    s <- List("black", "white")
  }yield s"$n$s"

  // scala 3
  val aForComprehension_v2 =
    for
      n <- List(1,2,3)
      s <- List("black", "white")
    yield s"$n$s"

  // pattern matching
  val meaningOfLife = 42
  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  // scala 3
  val aPatternMatch_v2 =
    meaningOfLife match
      case 1 => "the one"
      case 2 => "double or nothing"
      case _ => "something else"

  // methods without braces
  def compuyeMeaningOfLife(arg: Int): Int =
    val partialResult = 40


    partialResult + 2

  // class definition with significant indentation (same for traits, object, enums etc)
  class Animal: // the compiler expects the body of Animal
    def eat(): Unit =
      println("I'm eating")
    end eat

    def grow(): Unit =
      println("I'm growing")

  // 3000 more line of code
  end Animal // for it, match, for, method, classes, trait, enums, objects

  // anonymous classes
  val aSpecialAnimal = new Animal:
    override def eat(): Unit = println("I'm special")

  // indentation = stricly larger indentation
  // Example:
  // 3 spaces + 2tabs > 2 space + 2 tabs
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces


  def main(args: Array[String]): Unit = {
    println(compuyeMeaningOfLife(78))

  }
}
