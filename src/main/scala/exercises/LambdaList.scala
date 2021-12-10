package exercises

abstract class LambdaList[+A] {
  /*
         head = first element of the list
         tail = remainder of the list
         isEmpty = is this list empty
         add(int) => new list with this element added
         toString => a string representation of the list
       */


  def head: A
  def tail: LambdaList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): LambdaList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  // higher-order functions
  def map[B](transformer: A => B):LambdaList[B]
  def flatMap[B](transformer: A => LambdaList[B]): LambdaList[B]
  def filter(predicate: A => Boolean): LambdaList[A]
  // concatenation
  def ++[B >: A](list: LambdaList[B]): LambdaList[B]
}



object LambdaEmpty extends LambdaList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: LambdaList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): LambdaList[B] = new LambdaCons(element, LambdaEmpty)
  def printElements: String = ""

  def map[B](transformer: Nothing => B): LambdaList[B] = LambdaEmpty
  def flatMap[B](transformer: Nothing => LambdaList[B]): LambdaList[B] = LambdaEmpty
  def filter(predicate: Nothing => Boolean): LambdaList[Nothing] = LambdaEmpty

  def ++[B >: Nothing](list: LambdaList[B]): LambdaList[B] = list
}

class LambdaCons[+A](h: A, t: LambdaList[A]) extends LambdaList[A] {
  def head: A = h
  def tail: LambdaList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): LambdaList[B] = new LambdaCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

  /*


   */

  def filter(predicate: A => Boolean): LambdaList[A] =
    if (predicate(h)) new LambdaCons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
   [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](transformer: A => B): LambdaList[B] =
    new LambdaCons(transformer(h),t.map(transformer))

  /*

      [1,2] ++ [3,4,5]
    = new ColletionCons(1, [2] ++ [3,4,5])
    = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
    = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
  */
  def ++[B >: A](list: LambdaList[B]): LambdaList[B] = new LambdaCons(h, t ++ list)
  /*
    [1,2].flatMap(n => [n, n + 1])
    = [1,2] ++ [2].flaMap(n => [n, n + 1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
  */
  def flatMap[B](transformer: A => LambdaList[B]): LambdaList[B] =
    transformer(h) ++ t.flatMap(transformer)
}
/*trait FunctionPredicate[-T] { // T => Boolean
  def functionTest(elem: T): Boolean
}
trait FunctionTransformer[-A, B] {  // A => B
  def functionTransform(elem: A): B
}
*/
object LambdaListTest extends App {
  val listOfIntegers: LambdaList[Int] = new LambdaCons(1, new LambdaCons(2, new LambdaCons(3, LambdaEmpty)))
  val cloneListOfintegers: LambdaList[Int] = new LambdaCons(1, new LambdaCons(2, new LambdaCons(3, LambdaEmpty))) 
  val anotherlistOfIntegers: LambdaList[Int] = new LambdaCons(4, new LambdaCons(5, LambdaEmpty))
  val listOfStrings: LambdaList[String] = new LambdaCons("Hello", new LambdaCons("Scala", LambdaEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)

  println(listOfIntegers.map(elem => elem * 2).toString)

  println(listOfIntegers.filter(elem => elem % 2 == 0).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(elem => new LambdaCons(elem,new LambdaCons(elem + 1, LambdaEmpty))).toString)
  
  println(cloneListOfintegers == listOfIntegers)
}


