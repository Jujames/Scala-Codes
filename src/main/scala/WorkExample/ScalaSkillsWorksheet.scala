package WorkExample

import WorkExample.ScalaSkillsWorksheet.testMap

object ScalaSkillsWorksheet extends App {
  /* Question 1.
     Assume I have a map val testMap = Map(1 -> “one”, 2 -> “two”, 3 -> “three”).
   Extract the value “two” from this map.
  */
  val testMap = Map(1 -> "one", 2 -> "two", 3 -> "three")
  println(testMap(2))

  /* Question 2.
     Define a sequence with the values “cat”, “dog”, “pig”, “skunk”, “cardinal”.
     Then filter that sequence to only output “cardinal” and “dog”.
  */
  val myPets = Seq("cat", "dog", "pig", "cardinal")
  val filterForDogAndCardinal = myPets.filter(animal => (animal == "dog" || animal == "cardinal"))
  println(filterForDogAndCardinal)



  /* Question 3.
      Using this sequence of animals, add the word “big” in front of each Hint: use the “.map” method.
   */

  val listWithBig = myPets.map(value => "big" + value)
  println(listWithBig)


  /* Question 4.
     Define a case class for a Person with the following properties:
     Age
     First Name
     Last Name
     Height in Inches
     List of Hobbies
     Likes Chocolate
  */
  case class Person(age: Int, firstName: String, lastName: String, heigth: Double, hobbies: Seq[String], likeChocolate: Boolean)

  /* Question 5.
     Create a list/sequence of several people using the case class above, and
     A. filter on people who only like chocolate (output the result)
     B. filter on people above age 40 (output the result)
  */

  val sportNamesList = Seq(Person(60, "Larry", "Bird", 6.5, Seq("fishing", "basketball", "Golf"), false),
    Person(60, "Majic", "Johnson", 6.8, Seq("Golf", "basketball", "skydive"), true),
    Person(55, "Micheal", "Jordan", 6.6, Seq("gambling", "golf", "basketball"), true),
    Person(40, "Lebron", "James", 6.8, Seq("Movies", "GoKarts", "Fishing"), false))

  val whoLikesChoco = sportNamesList.filter(person => person.likeChocolate == true)
  println(whoLikesChoco)

  val ageOver40 = sportNamesList.filter(person => person.age > 40)
  println(ageOver40)

  /* Question 6.
      Change the last name of each person in this case class to “Smith” (and store in a new list)
   */

  val smithChange = sportNamesList.map(person => person.copy(lastName = "Smith"))
  println(smithChange)

  /* Question 7.
      Output the full list of hobbies for all members.
      Hint: May need to use .flatMap
   */

  val allHobbies = sportNamesList.flatMap(_.hobbies)
  println(allHobbies)

  /* Question 8
      Create the following Optional value: val myTestOpt = Some(“123456”)
      a. Check whether this optional is empty
      b. Check whether the value inside the option is equal to “123456”
      c. Check whether the value inside the option is equal to “7890”
      d. Check whether the String inside the option contains “5”
      Prerequisite: Make the following Optional value
   */

  val myTestOpt = Some("123456")
  println(myTestOpt.isEmpty)
  println(myTestOpt.contains("123456"))
  println(myTestOpt.contains("7890"))
  println(myTestOpt.getOrElse("").contains("5"))

  /* Question 9
      Using the Person list, Create a new list with the following properties:
      a. If the person likes chocolate, add the string “He loves chocolate!” to the list.
      b. If the person does not like chocolate, add the string “He hates chocolate…” to the list.
      Answer: Not a list of people, but a return a list of strings
   */

  val LikesChocolateList = sportNamesList.map(person => if person.likeChocolate then "He love chocolate" else "He hates chocolate")
  println(LikesChocolateList)

}