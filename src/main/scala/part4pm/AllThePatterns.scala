package part4pm

import exercises.{MyForList, MyForEmpty, MyForCons}


object AllThePatterns extends App {
/*
  // 1 - constants
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchVariable = x match {
    case something => s"I've found $something"
  }

  // tuples
  val aTuple = (1,2)
  val matchTuple = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I've found $something"
  }

  // Pattern Matching can be nested
  val nestedTuple = (1,(2,3))
  val matchNestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }

  // 4 - case classes - constructor pattern
  // PM can be nested with CaseCs as well
  val aList: MyForList[Int] = MyForCons(1,MyForCons(2, MyForEmpty))
  val matchAList = aList match {
    case MyForEmpty =>
    case MyForCons(head, MyForCons(subhead, subtail)) =>
  }

  // 5 - list pattern
  val astandardList = List (1,2,3,42)
  val standardListMatching = astandardList match {
    case List(1, _, _, _) => // extractor -advanced
    case List(1, _*) => // list of abrtrary length - advanced
    case 1 :: List(_) => // infix pattern
    case List(1,2,3) :+ 42 => // infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case nonEmptyList @ MyForCons(_, _) => // name binding => use the name later(here)
    case MyForCons(1, rest @ MyForCons(2, _)) => // name binding inside nested patterns
  }

  // 8 - multi-patterns
  val mutipattern = aList match {
    case MyForEmpty | MyForCons(0, _) => // compound pattern (multi-pattern)
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case MyForCons(_, MyForCons(specialElement, _)) if specialElement % 2 == 0 =>
  }

  // All

  /*
    Question.
  */
  */
  val numbers = List(1,2,3)
  val numbersMatch = numbers match {
    case listOfString: List[String] => "a list of string"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }

  println(numbersMatch)
  // JVM Trick question



}
