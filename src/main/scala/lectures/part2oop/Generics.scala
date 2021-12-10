package lectures.part2oop

object Generics extends App{

  class MyList[+A] {
    // use the  type A
    def add[B >: A](element: B) : MyList[B] = ???
    /*
      A = Cat
      B = Animal
    */
  }

  class MyMap[Key, value]
  val listOfIntegers = new MyList[Int]
  val listOfString = new MyList[String]

  // generic method
  object MyList {
    def empty[A]: MyList[A] = ???
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // 1. yes, list of [Cat] extends List{Animal] = COVARIANCE
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog)??? Hard Question => We return a list of Animals

  // 2. No = INVARIANCE
  class InvariantList[A]
  val invariantAnimalList: InvariantList[Animal] = new InvariantList[Animal]

  // 3. Hell, no! CONTRAVAIANCE
  class Trainer[-A]
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // bounded types
  class Cage[A <: Animal](animal: A)
  val cage = new Cage(new Dog)

  //class Car
  // generic type needs proper bounded type
  //val newCage = new Cage(new Car)


  // expand MyList to be generic

  /*
     1. Generic traits MyPreducate[-T] with a little method test (T) => Boolean
     2. Generic trait MyTransformer[-A, B] with a method transform(A) => B
     3. MyList:
        - map(transformer) => MyList
        - filter(predicate) => MyList
        - flatMap(transformer from A to MyList [B]) => MyList

        class venPredicate extends MyPredicate[Int]
        class StringToIntTransformer extends MyTransformer[String, Int]

        [1,2,3].map(n * 2) = [2,4,6]
        [1,2,3,4].filter(n % 2) = [2,4]
        [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]

    */
}
