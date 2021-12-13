package exercises

abstract class HOFList[+A] {
  /*
           head = first element of the list
           tail = remainder of the list
           isEmpty = is this list empty
           add(int) => new list with this element added
           toString => a string representation of the list
         */


  def head: A
  def tail: HOFList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): HOFList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B):HOFList[B]
  def flatMap[B](transformer: A => HOFList[B]): HOFList[B]
  def filter(predicate: A => Boolean): HOFList[A]

  // concatenation
  def ++[B >: A](list: HOFList[B]): HOFList[B]

  // hofs
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): HOFList[A]
  def zipWith[B, C](list: HOFList[B], zip:(A, B) => C): HOFList[C]
  def fold[B](start: B) (operator: (B, A) => B): B
}



case object HOFEmpty extends HOFList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: HOFList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): HOFList[B] = new HOFCons(element, HOFEmpty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): HOFList[B] = HOFEmpty
  def flatMap[B](transformer: Nothing => HOFList[B]): HOFList[B] = HOFEmpty
  def filter(predicate: Nothing => Boolean): HOFList[Nothing] = HOFEmpty

  def ++[B >: Nothing](list:HOFList[B]): HOFList[B] = list

  // hofs
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int) = HOFEmpty
  def zipWith[B, C](list: HOFList[B], zip: (Nothing, B) => C): HOFList[C] =
    if (!list.IsEmpty) throw new RuntimeException("List do not have the same length")
    else HOFEmpty
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start


}

class HOFCons[+A](h: A, t: HOFList[A]) extends HOFList[A] {
  def head: A = h
  def tail: HOFList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): HOFList[B] = new HOFCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

  /*


   */

  def filter(predicate: A => Boolean): HOFList[A] =
    if (predicate(h)) new HOFCons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
    [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](transformer: A => B): HOFList[B] =
    new HOFCons(transformer(h),t.map(transformer))
  /*
     [1,2] ++ [3,4,5]
    = new ColletionCons(1, [2] ++ [3,4,5])
    = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
    = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
  */
  def ++[B >: A](list: HOFList[B]): HOFList[B] = new HOFCons(h, t ++ list)
  /*
    [1,2].flatMap(n => [n, n + 1])
    = [1,2] ++ [2].flaMap(n => [n, n + 1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
  */
  def flatMap[B](transformer: A => HOFList[B]): HOFList[B] =
    transformer(h) ++ t.flatMap(transformer)

 // hofs
  def foreach(f: A => Unit): Unit = {
   f(h)
   t.foreach(f)
  }

  def sort(compare: (A, A) => Int): HOFList[A] = {
    def insert(x: A, sortedList: HOFList[A]): HOFList[A] =
      if (sortedList.IsEmpty) new HOFCons(x, HOFEmpty)
      else if (compare(x, sortedList.head) <= 0) new HOFCons(x, sortedList)
      else new HOFCons(sortedList.head,insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: HOFList[B], zip: (A, B) => C): HOFList[C] =
    if (list.IsEmpty) throw new RuntimeException("List do not have the same length")
    else new HOFCons(zip(h, list.head), t.zipWith(list.tail, zip))
  /*
    [1,2,3].fold(0)(+) =
    = [2,3].fold(1)(+) =
    = [3].fold(3)(+) =
    = [].fold(6)(+) =
    = 6
  */
  def fold[B](start: B)(operator: (B, A) => B): B = {
    t.fold(operator(start, h))(operator)
  }

}
 /* trait FunctionPredicate[-T] { // T => Boolean
   def functionTest(elem: T): Boolean
   }
   trait FunctionTransformer[-A, B] {  // A => B
   def functionTransform(elem: A): B
   }
 */
object HOFListTest extends App {
  val listOfIntegers: HOFList[Int] = new HOFCons(1, new HOFCons(2, new HOFCons(3,HOFEmpty)))
  val cloneListOfintegers: HOFList[Int] = new HOFCons(1, new HOFCons(2, new HOFCons(3, HOFEmpty)))
  val anotherlistOfIntegers: HOFList[Int] = new HOFCons(4, new HOFCons(5, HOFEmpty))
  val listOfStrings: HOFList[String] = new HOFCons("Hello", new HOFCons("Scala", HOFEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(elem => elem * 2).toString)

  println(listOfIntegers.filter(elem => elem % 2 == 0).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(elem => new HOFCons(elem,new HOFCons(elem + 1, HOFEmpty))).toString)

  println(cloneListOfintegers == listOfIntegers)

  listOfIntegers.foreach(println)
  println(listOfIntegers.sort((x, y) => y - x))
  println(anotherlistOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(listOfIntegers.fold(0)(_ + _))
}
