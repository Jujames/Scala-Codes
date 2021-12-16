package exercises

abstract class MyFunctionList[+A] {
  /*
        head = first element of the list
        tail = remainder of the list
        isEmpty = is this list empty
        add(int) => new list with this element added
        toString => a string representation of the list
      */


  def head: A
  def tail: MyFunctionList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): MyFunctionList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B): MyFunctionList[B]
  def flatMap[B](transformer: A => MyFunctionList[B]): MyFunctionList[B]
  def filter(predicate: A => Boolean): MyFunctionList[A]
  // concatenation
  def ++[B >: A](list: MyFunctionList[B]): MyFunctionList[B]
}



case object FunctionEmpty extends MyFunctionList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyFunctionList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): MyFunctionList[B] = new FunctionCons(element, FunctionEmpty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): MyFunctionList[B] = FunctionEmpty
  def flatMap[B](transformer: Nothing => MyFunctionList[B]): MyFunctionList[B] = FunctionEmpty
  def filter(predicate: Nothing => Boolean): MyFunctionList[Nothing] = FunctionEmpty

  def ++[B >: Nothing](list: MyFunctionList[B]): MyFunctionList[B] = list
}

case class FunctionCons[+A](h: A, t: MyFunctionList[A]) extends MyFunctionList[A] {
  def head: A = h
  def tail: MyFunctionList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): MyFunctionList[B] = new FunctionCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

  /*


   */

  def filter(predicate: A => Boolean): MyFunctionList[A] =
    if (predicate(h)) new FunctionCons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
   [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](transformer: A => B): MyFunctionList[B] =
    new FunctionCons(transformer(h),t.map(transformer))

  /*

      [1,2] ++ [3,4,5]
    = new ColletionCons(1, [2] ++ [3,4,5])
    = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
    = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
  */
  def ++[B >: A](list: MyFunctionList[B]): MyFunctionList[B] = new FunctionCons(h, t ++ list)
  /*
    [1,2].flatMap(n => [n, n + 1])
    = [1,2] ++ [2].flaMap(n => [n, n + 1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
  */
  def flatMap[B](transformer: A =>MyFunctionList[B]): MyFunctionList[B] =
    transformer(h) ++ t.flatMap(transformer)
}
/*trait FunctionPredicate[-T] { // T => Boolean
  def functionTest(elem: T): Boolean
}
trait FunctionTransformer[-A, B] {  // A => B
  def functionTransform(elem: A): B
}
*/
object MyFunctionListTest extends App {
  val listOfIntegers: MyFunctionList[Int] = new FunctionCons(1, new FunctionCons(2, new FunctionCons(3, FunctionEmpty)))
  val anotherlistOfIntegers: MyFunctionList[Int] = new FunctionCons(4, new FunctionCons(5, FunctionEmpty))
  val listOfStrings: MyFunctionList[String] = new FunctionCons("Hello", new FunctionCons("Scala", FunctionEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)
  println(listOfIntegers.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }).toString)

  println(listOfIntegers.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(new Function1[Int, MyFunctionList[Int]] {
    override def apply(elem: Int): MyFunctionList[Int] = new FunctionCons(elem,new FunctionCons(elem + 1, FunctionEmpty))
  }).toString)
}




