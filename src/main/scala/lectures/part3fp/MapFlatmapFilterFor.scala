package lectures.part3fp

object MapFlatmapFilterFor extends App {

  val list = List(1,2,3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combination between two list
  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  // List("a1", "a2"... "d4")

  // "iterating"
  val combinations = numbers.filter(_ % 2 == 0)flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)

  // foreach
  list.foreach(println)

  // for-comprehension
  val forCombination = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombination)

  // foreach-comprehension
  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }
  /*
   1. MyList supports for comprehension?
      map(f: A => B) = MyForList[B]
      filter(p: A => MyForList[B]) = MyForList[B]
   2. A small collection of at most ONE element - Maybe[+T]
      - map, flatMap, filter
  */


}
