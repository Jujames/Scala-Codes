package exercises


abstract class CollectionList[+A] {
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


  def head: A
  def tail: CollectionList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): CollectionList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  def map[B](transformer: MyTransformer[A, B]): CollectionList[B]
  def flatMap[B](transformer: MyTransformer[A, CollectionList[B]]): CollectionList[B]
  def filter(predicate: MyPredicate[A]): CollectionList[A]
  // concatenation
  def ++[B >: A](list: CollectionList[B]): CollectionList[B]
}



object CollectionEmpty extends CollectionList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: CollectionList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): CollectionList[B] = new CollectionCons(element, CollectionEmpty)
  def printElements: String = ""

  def map[B](transformer: MyTransformer[Nothing, B]): CollectionList[B] = CollectionEmpty
  def flatMap[B](transformer: MyTransformer[Nothing, CollectionList[B]]): CollectionList[B] = CollectionEmpty
  def filter(predicate: MyPredicate[Nothing]): CollectionList[Nothing] = CollectionEmpty

  def ++[B >: Nothing](list: CollectionList[B]): CollectionList[B] = list
}

class CollectionCons[+A](h: A, t: CollectionList[A]) extends CollectionList[A] {
  def head: A = h
  def tail: CollectionList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): CollectionList[B] = new CollectionCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

    /*


     */

  def filter(predicate: MyPredicate[A]): CollectionList[A] =
    if (predicate.test(h)) new CollectionCons(h, t.filter(predicate))
    else t.filter(predicate)
    /*
     [1,2,3].map(n * 2)
      = new CollectionCons(2, [2,3].map(n * 2))
      = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
      = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
      = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
    */
  def map[B](transformer: MyTransformer[A, B]): CollectionList[B] =
    new CollectionCons(transformer.transform(h),t.map(transformer))

    /*

        [1,2] ++ [3,4,5]
      = new ColletionCons(1, [2] ++ [3,4,5])
      = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
      = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
    */
  def ++[B >: A](list: CollectionList[B]): CollectionList[B] = new CollectionCons(h, t ++ list)
    /*
      [1,2].flatMap(n => [n, n + 1])
      = [1,2] ++ [2].flaMap(n => [n, n + 1])
      = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
      = [1,2] ++ [2,3] ++ Empty
      = [1,2,2,3]
    */
  def flatMap[B](transformer: MyTransformer[A, CollectionList[B]]): CollectionList[B] =
    transformer.transform(h) ++ t.flatMap(transformer)


}
trait MyPredicate[-T] {
  def test(elem: T): Boolean
}
trait MyTransformer[-A, B] {
  def transform(elem: A): B
}

object CollectionListTest extends App {
  val listOfIntegers: CollectionList[Int] = new CollectionCons(1, new CollectionCons(2, new CollectionCons(3, CollectionEmpty)))
  val anotherlistOfIntegers: CollectionList[Int] = new CollectionCons(4, new CollectionCons(5, CollectionEmpty))
  val listOfStrings: CollectionList[String] = new CollectionCons("Hello", new CollectionCons("Scala", CollectionEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)
  println(listOfIntegers.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
   }).toString)

  println(listOfIntegers.filter(new MyPredicate[Int] {
    override def test(elem: Int): Boolean = elem % 2 == 0
   }).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(new MyTransformer[Int, CollectionList[Int]] {
    override def transform(elem: Int): CollectionList[Int] = new CollectionCons(elem,new CollectionCons(elem + 1, CollectionEmpty))
  }).toString)
}

