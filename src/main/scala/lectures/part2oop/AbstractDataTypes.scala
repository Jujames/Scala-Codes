package lectures.part2oop

object AbstractDataTypes extends App {

  // abstract
  abstract class Animal {
    val creatureType : String = "wild"
    def eat: Unit
  }

  class Dog extends Animal {
    override val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch")
  }

  // traits
  trait Carnivore {
    def eat(animal: Animal): Unit
    val preferredMeal: String = "freash meat"
  }
  trait ColdeBlooded
  class Crocodile extends Animal with Carnivore with ColdeBlooded {
    override val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    override def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // traits vs abstract Classes
  // 1 - traits do not have constructor parameters
  // 2 - mutiple traits may be iherited by the same class
  // 3 - traits = behavior, abstract class = "thing"
}
