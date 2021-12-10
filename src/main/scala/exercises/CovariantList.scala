package exercises

abstract class CovariantList[+A] {

  /*
     head = first element of the list
     tail = remainder of the list
     isEmpty = is the lidt empty
     add(int) => new list with this element added
     toString => a string representing of the list
   */

  def head: A
  def tail: CovariantList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): CovariantList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"
}

object CovariantEmpty extends CovariantList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: CovariantList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): CovariantList[B] = new CovariantCons(element, CovariantEmpty)
  def printElements: String = ""
}

class CovariantCons[+A](h: A, t: CovariantList[A]) extends CovariantList[A] {
  def head: A = h
  def tail: CovariantList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): CovariantList[B] = new CovariantCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

}

object CovariantListListTest extends App {
  val listOfIntegers: CovariantList[Int] = new CovariantCons(1, new CovariantCons(2, new CovariantCons(3, CovariantEmpty)))
  val listOfStrings: CovariantList[String] = new CovariantCons("Hello", new CovariantCons("Scala", CovariantEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)
}

