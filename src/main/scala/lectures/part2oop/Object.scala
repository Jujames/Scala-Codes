package lectures.part2oop

object Object extends App {

  // SCALA DOES HAVE CLASS-LEVEL FUNCTIONALITY ("static")
  object Person {    // TYPE + ITS ONLY INSTANCE
    // "static"/"class" - level funtionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method
    def apply(mother: Person, father: Person): Person = new Person("Bobbie")
  }
  class Person(val name: String) {
    // instance-level functionality
  }
  // COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  // Scala object = SIGLETON INSTANCE
  val mary = new Person("Mary")
  val john = new Person("John")
  println(mary == john)

  val person1 = Person
  val person2 = Person
  println(person1 == person2)

  val bobbie = Person(mary, john)

  // Scala Application = Scala object with
  // def main(args: Array[String]) : Unit =

}
