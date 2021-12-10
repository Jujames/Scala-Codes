package lectures.part2oop

import lectures.part2oop.Object.Person

object CaseClass extends App {

  /*
   equals, hashCode, toString
  */

  case class Person(name: String, age: Int)

  // 1. class parameters are fields
  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim.age)

  // 2. sensible toString
  // println(instance) = println(instance.toString) // syntatic sugar
  println(jim)

  // 3. equals and hascode implemeted OOTB
  val jim2 = new Person("Jim", 34)
  println(jim == jim2)

  // 4. Case Class have handy copy method
  val jim3 = jim.copy(age = 45)
  println(jim3)

  // 5. Case class have companion objects
  val thePerson = Person
  val mary = Person("Mary", 23)

  // 6. CC's are serializable
  // AKKa

  // 7. CC's have extractor patterns == CC's can be used in PATTERN MATCHING

  case object UnitedKingdom {
    def name: String = "The UK of GB an NI"
  }

  /*
    Expand MyList - yse case classes and case objects
  */

}
