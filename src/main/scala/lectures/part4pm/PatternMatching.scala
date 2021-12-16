package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {

  // switch on steriod
  val random = new Random
  val x = random.nextInt(10)

  /*
     This is called Pattern matching
     Pattern matching tries to match a value against multiple patterns
     Each pattern is written in a case statement
  */
  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ = WILDCARD make you cover yourself with a wild cards in you pattern
  }

  println(x)
  println(description)

  // 1. Decompose values
  case class Person(name: String, Age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    // This is called a guard
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can'y drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don'y know who I am"
  }
  println(greeting)

  /*
   1.  Case are matched in order
   2. what if no case match? MatchError
   3. what is the type pattern match expression? unified type of all the types in all the cases.
   4. Pattern Matching works really well with case classes*
  */

  // Pattern Matchin on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(gretting: String) extends Animal

  val animal: Animal = Dog("Bullmastiff")
  animal match {
    case Dog(someBreed) => println(s" Matched a dog of the $someBreed breed")
  }

  // match everything
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  // WHY?!
  val isEvenCond = if (x % 2 == 0) true else false //?!
  val isEvenNormal = x % 2 == 0   // Is the best way to this code no need for case matching

  /*
   ***Excercise***
   simple function uses Pattern Matching
   take an Expr => human readable form

   Sum(Number(2), Number(3)) => 2 + 3
   Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
   Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
   Sum(Prod(Number(2), Number(1)), Number(3)) = 2 * 1 + 3
  */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(e: Expr): String = e match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) => {
      def maybeShowParentheses(exp: Expr) = exp match {
        case Prod(_, _) => show(exp)
        case Number(_) => show(exp)
        case _ => "(" + show(exp) + ")"
      }
      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
    }
  }

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
  println(show(Prod(Sum(Number(2), Number(1)), Sum(Number(3), Number(4)))))
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))

}
