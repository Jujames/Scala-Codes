package lectures.part3fp

import scala.annotation.tailrec

object TuplesAndMaps extends App {

  // tuples = finite order "list
  val aTuple = (2, "Hello, Scala ") // OR val aTuple = new Tuple2 <-------> Tuple2[Int, String] = (Int, String)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = " Goodbye Java"))
  println(aTuple.swap) // ("hello, Scala", 2)

  // Maps - keys -> value
  val aMap: Map[String, Int] = Map()

  val phonebook = Map(("Jim", 555), "Daniel" -> 789, ("JIM", 9000)).withDefaultValue(-1)
  // a -> b is sugar for (a, b)
  println(phonebook)

  // map ops
  println(phonebook.contains("Jim"))
  println(phonebook("Mary"))

  // add a pairing
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // funtional on maps
  // map, flatMap, filter
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.view.filterKeys(x => x.startsWith("J")).toMap)

  // mapValues
  println(phonebook.view.mapValues(number => "0245-" + number).toMap)

  // conversion to other collection
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  // group By
  val names = List("Bob", "James", "Angela", "Mary", "Daneil", "Jim")
  println(names.groupBy(name => name.charAt(0)))
  /*
  1. What would happen if I had two original entries "Jim" -> 555 and "Jim" -> 900

     !!! carful with mapping keys.

  2. Overly simplified social network based on maps
     Person = String
     - add a person to network
     - remove
     - friends (mutual)
     - unfriend

     - number of friends of a person
     - person with most friends
     - how many people have NO friends
     - if there is a social connection between two people (direct or not)
  */
  def add (network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network + (person -> Set())

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
 }
  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def remove(network: Map[String, Set[String]], person: String): Map[String, Set[String]] = {
    def removeAux (friends: Set[String], networkAcc: Map[String, Set[String]]): Map[String, Set[String]] =
      if (friends.isEmpty) networkAcc
      else removeAux(friends.tail, unfriend(networkAcc, person, friends.head))

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }

  val empty: Map[String, Set[String]] = Map()
  val network = add(add(empty, "Bob"), "Mary")
  println(network)
  println(friend(network, "Bob", "Mary"))
  println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))
  println(remove(friend(network, "Bob", "Mary"), "Bob"))

  // Jim,Bob,Mary
  val people = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val jimBob = friend(people, "Bob", "Jim")
  val testNet = friend(jimBob, "Bob", "Mary")

  println(testNet)

  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) 0
    else network(person).size

  println(nFriends(testNet, "Bob"))

  def mostFriends(network: Map[String, Set[String]]): String =
    network.maxBy(pair => pair._2.size)._1

  println(mostFriends(testNet))

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
   network.view.filterKeys(k => network(k).isEmpty).toMap.size
   //  shorthand ---> network.count(pair => pair._2.isEmpty) <----> OR <---->
   //  even more shorthand  --->  network.count(_._2.isEmpty)

  println(nPeopleWithNoFriends(testNet))

  def socialConection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    @tailrec
    def bfs(target: String, consideredPeople: Set[String], discoverdedPeople: Set[String]): Boolean = {
      if (discoverdedPeople.isEmpty) false
      else {
        val person = discoverdedPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoverdedPeople.tail)
        else bfs(target, consideredPeople + person, discoverdedPeople.tail ++ network(person))
      }
    }

    bfs(b, Set(), network(a) + a)
  }

  println(socialConection(testNet, "Mary", "Jim"))
  println(socialConection(network, "Mary", "Bob"))
}