package exercises

abstract class CaseClassList[+A] {
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
  def tail: CaseClassList[A]
  def IsEmpty: Boolean
  def add[B >: A](element: B): CaseClassList[B]
  def printElements: String
  // polymorphic call
  override def toString: String = "[" + printElements + "]"

  def map[B](caseTransformer: MyCaseTransformer[A, B]): CaseClassList[B]
  def flatMap[B](caseTransformer: MyCaseTransformer[A, CaseClassList[B]]): CaseClassList[B]
  def filter(casePredicate: MyCasePredicate[A]): CaseClassList[A]
  // concatenation
  def ++[B >: A](list: CaseClassList[B]): CaseClassList[B]
}



object CaseEmpty extends CaseClassList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: CaseClassList[Nothing] = throw new NoSuchElementException
  def IsEmpty: Boolean = true
  def add[B >: Nothing](element: B): CaseClassList[B] = new CaseCons(element, CaseEmpty)
  def printElements: String = ""

  def map[B](caseTransformer: MyCaseTransformer[Nothing, B]): CaseClassList[B] = CaseEmpty
  def flatMap[B](caseTransformer: MyCaseTransformer[Nothing, CaseClassList[B]]): CaseClassList[B] = CaseEmpty
  def filter(casePredicate: MyCasePredicate[Nothing]): CaseClassList[Nothing] = CaseEmpty

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

  def filter(casePredicate: MyCasePredicate[A]): CaseClassList[A] =
    if (casePredicate.caseTest(h)) new CaseCons(h, t.filter(casePredicate))
    else t.filter(casePredicate)
  /*
   [1,2,3].map(n * 2)
    = new CollectionCons(2, [2,3].map(n * 2))
    = new CollectionCons(2, new CollectionCons(4, [3].map(n * 2)))
    = new CollectionCons(2, new CollectionCons(4, new CollectionCons(6, Empty.map(n * 2))))
    = new CollectionCons(2, new CollectionCons(4, new CollectionsCons(6, Empty)))
  */
  def map[B](caseTransformer: MyCaseTransformer[A, B]): CaseClassList[B] =
    new CaseCons(caseTransformer.caseTransform(h),t.map(caseTransformer))

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
  def flatMap[B](caseTransformer: MyCaseTransformer[A, CaseClassList[B]]): CaseClassList[B] =
    caseTransformer.caseTransform(h) ++ t.flatMap(caseTransformer)


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


