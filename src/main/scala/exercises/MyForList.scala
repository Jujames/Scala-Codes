package exercises

abstract class MyForList[+A] {
  /*
             head = first element of the list
             tail = remainder of the list
             isEmpty = is this list empty
             add(int) => new list with this element added
             toString => a string representation of the list
           */


  def head: A
  def tail:MyForList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): MyForList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B):MyForList[B]
  def flatMap[B](transformer: A => MyForList[B]): MyForList[B]
  def filter(predicate: A => Boolean): MyForList[A]

  // concatenation
  def ++[B >: A](list: MyForList[B]): MyForList[B]

  // hofs
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyForList[A]
  def zipWith[B, C](list: MyForList[B], zip:(A, B) => C): MyForList[C]
  def fold[B](start: B) (operator: (B, A) => B): B
}



case object MyForEmpty extends MyForList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyForList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyForList[B] = new MyForCons(element, MyForEmpty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyForList[B] = MyForEmpty
  def flatMap[B](transformer: Nothing => MyForList[B]): MyForList[B] = MyForEmpty
  def filter(predicate: Nothing => Boolean): MyForList[Nothing] = MyForEmpty

  def ++[B >: Nothing](list:MyForList[B]): MyForList[B] = list

  // hofs
  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int) = MyForEmpty
  def zipWith[B, C](list: MyForList[B], zip: (Nothing, B) => C): MyForList[C] =
    if (!list.IsEmpty) throw new RuntimeException("List do not have the same length")
    else MyForEmpty
  def fold[B](start: B)(operator: (B, Nothing) => B): B = start


}

case class MyForCons[+A](h: A, t: MyForList[A]) extends MyForList[A] {
  def head: A = h
  def tail: MyForList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): MyForList[B] = new MyForCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

  /*


   */

  def filter(predicate: A => Boolean): MyForList[A] =
    if (predicate(h)) new MyForCons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
    [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](transformer: A => B): MyForList[B] =
    new MyForCons(transformer(h),t.map(transformer))
  /*
     [1,2] ++ [3,4,5]
    = new ColletionCons(1, [2] ++ [3,4,5])
    = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
    = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
  */
  def ++[B >: A](list: MyForList[B]): MyForList[B] = new MyForCons(h, t ++ list)
  /*
    [1,2].flatMap(n => [n, n + 1])
    = [1,2] ++ [2].flaMap(n => [n, n + 1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
  */
  def flatMap[B](transformer: A => MyForList[B]): MyForList[B] =
    transformer(h) ++ t.flatMap(transformer)

  // hofs
  def foreach(f: A => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (A, A) => Int): MyForList[A] = {
    def insert(x: A, sortedList: MyForList[A]): MyForList[A] =
      if (sortedList.IsEmpty) new MyForCons(x, MyForEmpty)
      else if (compare(x, sortedList.head) <= 0) new MyForCons(x, sortedList)
      else new MyForCons(sortedList.head,insert(x, sortedList.tail))

    val sortedTail = t.sort(compare)
    insert(h, sortedTail)
  }

  def zipWith[B, C](list: MyForList[B], zip: (A, B) => C): MyForList[C] =
    if (list.IsEmpty) throw new RuntimeException("List do not have the same length")
    else new MyForCons(zip(h, list.head), t.zipWith(list.tail, zip))
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
object MyForListTest extends App {
  val listOfIntegers: MyForList[Int] = new MyForCons(1, new MyForCons(2, new MyForCons(3, MyForEmpty)))
  val cloneListOfintegers: MyForList[Int] = new MyForCons(1, new MyForCons(2, new MyForCons(3, MyForEmpty)))
  val anotherlistOfIntegers: MyForList[Int] = new MyForCons(4, new MyForCons(5, MyForEmpty))
  val listOfStrings: MyForList[String] = new MyForCons("Hello", new MyForCons("Scala", MyForEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(elem => elem * 2).toString)

  println(listOfIntegers.filter(elem => elem % 2 == 0).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(elem => new MyForCons(elem,new MyForCons(elem + 1, MyForEmpty))).toString)

  println(cloneListOfintegers == listOfIntegers)

  listOfIntegers.foreach(println)
  println(listOfIntegers.sort((x, y) => y - x))
  println(anotherlistOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _))
  println(listOfIntegers.fold(0)(_ + _))

  // for comprehension
  val combinations =  for {
    n <- listOfIntegers
    string <- listOfStrings
  } yield n + "-" + string
  println(combinations)
}
