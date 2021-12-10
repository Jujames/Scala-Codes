package lectures.part2oop
import scala.language.postfixOps

object MethodNotations extends App {
  class Person(val name: String, favoriteMovie: String, val age: Int = 20) {
    def likes(movie: String): Boolean = movie == favoriteMovie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
    def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie) // Answer to 1
    def unary_! : String = s"$name, what the heck?!"
    def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I lkie $favoriteMovie"
    def apply(n: Int): String = s"$name watched $favoriteMovie $n times "

    def learns(thing: String) = s"$name is learning $thing"
    def learnScala = this learns "Scala"
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")   // equivalent
  // infix notation = operator notation (syntactic sugar)

  // "Operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2)
  println(1.+(2))

  // ALL OPERATORS ARE METHODS
  // Akka actors have ! ? operators

  // prefix notation
  val x = -1       // equivalent with 1.unary_-
  val y = 1.unary_-

  // unary_ prefix only works with - + ~ !

  println(!mary)
  println(mary.unary_!)

  // postfix notations
  println(mary.isAlive)
  println(mary isAlive) // only used on method with no parameters.

  // apply
  println(mary.apply())
  println(mary())  // same as (mary.apply())

  /*
  * 1. Overload the + operator
       mary + "the rockstar" => new person "Mary (the rockstar)"

    2. Add an age to the person Class
       Add a unary + operator => new person with the age + 1
       +mary => mary with the age increment

    3. Add a "learns" method in the person class => "Mary learns Scala"
       Add learnsScala method, calls learns method with "scala".
       Use it postfix notation.

    4. Overload the apply method
       mary.apply(2) => "Mary watchesd Inception 2 times"
    */
  println((mary + "the Rockstar").apply())
  println((+mary).age)
  println(mary learnScala)
  println(mary(10))











}
