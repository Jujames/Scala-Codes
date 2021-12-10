package exercises

abstract class CaseClassList[+A] {
  
  /*
      head = first element of the list
      tail = remainder of the list
      isEmpty = is this list empty
      add(int) => new list with this element added
      toString => a string representation of the list
    */


  def head: A
  def tail: CaseClassList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): CaseClassList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  def map[B](caseTransformer: MyCaseTransformer[A, B]): CaseClassList[B]
  def flatMap[B](transformer: MyCaseTransformer[A, CaseClassList[B]]): CaseClassList[B]
  def filter(predicate: MyCasePredicate[A]): CaseClassList[A]
  // concatenation
  def ++[B >: A](list: CaseClassList[B]): CaseClassList[B]
}



object CaseEmpty extends CaseClassList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: CaseClassList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): CaseClassList[B] = new CaseCons(element, CaseEmpty)
  def printElements: String = ""

  def map[B](transformer: MyCaseTransformer[Nothing, B]): CaseClassList[B] = CaseEmpty
  def flatMap[B](transformer: MyCaseTransformer[Nothing, CaseClassList[B]]): CaseClassList[B] = CaseEmpty
  def filter(predicate: MyCasePredicate[Nothing]): CaseClassList[Nothing] = CaseEmpty

  def ++[B >: Nothing](list: CaseClassList[B]): CaseClassList[B] = list
}

class CaseCons[+A](h: A, t: CaseClassList[A]) extends CaseClassList[A] {
  def head: A = h
  def tail: CaseClassList[A] = t
  def IsEmpty: Boolean = false
  def add[B >: A](element: B): CaseClassList[B] = new CaseCons(element, this)
  def printElements: String =
    if(t.IsEmpty) "" + h
    else s"${h.toString} ${t.printElements}"

  /*


   */

  def filter(predicate: MyCasePredicate[A]): CaseClassList[A] =
    if (predicate.caseTest(h)) new CaseCons(h, t.filter(predicate))
    else t.filter(predicate)
  /*
   [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](transformer: MyCaseTransformer[A, B]): CaseClassList[B] =
    new CaseCons(transformer.caseTransform(h),t.map(transformer))

  /*

      [1,2] ++ [3,4,5]
    = new ColletionCons(1, [2] ++ [3,4,5])
    = new ColletionCons(1, new ColletionCons(2, Empty ++ [3,4,5]))
    = new ColletionCons(1, new ColletionCons(2, new ColletionCons(3, new ColletionCons(4, new ColletionCons(5)))))
  */
  def ++[B >: A](list: CaseClassList[B]): CaseClassList[B] = new CaseCons(h, t ++ list)
  /*
    [1,2].flatMap(n => [n, n + 1])
    = [1,2] ++ [2].flaMap(n => [n, n + 1])
    = [1,2] ++ [2,3] ++ Empty.flatMap(n => [n, n+1})
    = [1,2] ++ [2,3] ++ Empty
    = [1,2,2,3]
  */
  def flatMap[B](transformer: MyCaseTransformer[A, CaseClassList[B]]): CaseClassList[B] =
    transformer.caseTransform(h) ++ t.flatMap(transformer)


}
trait MyCasePredicate[-T] {
  def caseTest(elem: T): Boolean
}
trait MyCaseTransformer[-A, B] {
  def caseTransform(elem: A): B
}

object CaseClassListTest extends App {
  val listOfIntegers: CaseClassList[Int] = new CaseCons(1, new CaseCons(2, new CaseCons(3, CaseEmpty)))
  val anotherlistOfIntegers: CaseClassList[Int] = new CaseCons(4, new CaseCons(5, CaseEmpty))
  val listOfStrings: CaseClassList[String] = new CaseCons("Hello", new CaseCons("Scala", CaseEmpty))

  println(listOfIntegers.toString)
  println(listOfStrings.toString)
  println(listOfIntegers.map(new MyCaseTransformer[Int, Int] {
    override def caseTransform(elem: Int): Int = elem * 2
  }).toString)

  println(listOfIntegers.filter(new MyCasePredicate[Int] {
    override def caseTest(elem: Int): Boolean = elem % 2 == 0
  }).toString)

  println((listOfIntegers ++ anotherlistOfIntegers).toString)
  println(listOfIntegers.flatMap(new MyCaseTransformer[Int, CaseClassList[Int]] {
    override def caseTransform(elem: Int): CaseClassList[Int] = new CaseCons(elem,new CaseCons(elem + 1, CaseEmpty))
  }).toString)
}


