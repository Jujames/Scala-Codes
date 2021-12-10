package WorkExample

object ScalaPracticeProblems extends App{
  /* Topics
        • Mapping over iterables
        • Case classes
        • List Operations
        • Grouping By
     */

  /* Question 1:
     1. suppose you have the case class as follows:
        case class Food(name:String,
        weightInOunces: Int,
        pairedWine: Option[String])
        and the instances of this case class:

        val steak = Food("steak", 8, Some("merlot"))
        val fish = Food("fish", 6, Some("pinot"))
        val bacon = Food("bacon", 4, None)
  */

  case class Food(name:String, weightInOunces: Int, pairedWine: Option[String])
  val steak = Food("steak", 8, Some("merlot"))
  val fish = Food ("fish", 6, Some("pinot"))
  val bacon = Food("bacon", 4, None)

  // A. Make your own food object. List None for the paired wine.
  val shrimp = Food("shrimp", 10, None)

  // B. Create a List with these four instances.
  val meats = Seq(steak, fish, bacon, shrimp)

  // C. Using the list from (b), return a new list that contains just the weights. - expected result: Seq(8,6,4)
  println(meats.map(meat => meat.weightInOunces))

  /* D. Using the list from (b), return a new list that contains just the paired wines that exist
       - expected result: Seq("merlot", "pinot")
  */
  val filterForFavWines = meats.flatMap(_.pairedWine)
  println(filterForFavWines)

  // E. Determine the sum of the weights in the list of food.
  val foodWeight = (meats.map(meat => meat.weightInOunces)).sum

  // F. Assume you like "cabernet" wine. Make a copy of the steak variable and change the pairedWine to "cabernet".
  val wineChange = steak.copy(pairedWine = Some("cabernet"))
  println(wineChange)

  // G. Using the list from (b), return a new list such that the pairedWine value for each Food instance is "cabernet".
  val wineList = meats.map(meat => meat.copy(pairedWine = Some("cabernet")))
  println(wineList)

  // H. You are hungry. Using the list from (b), return a new list with the weightInOunces increased by 10 for each food.
  println(meats.map(meat => meat.weightInOunces + 10))

  /* I. Using the list from (b), filter the list and return only those foods that weigh more than 6 ounces. Output
      only the "name" value of these foods.
      - hint: use filter and then map
      - expected result: Seq("steak", ...)
  */
  println(meats.filter(meat => meat.weightInOunces > 6).map(meat => meat.name))

  // J. Using the list from (b), filter only on the foods that contain a pairedWine. Output only the name of these foods.
  // println(meats.filter(meat => meat.steak).map(paireWine(Some) => pairedWine.name)))


  /*Question 2: Basic List Operations:
    1. Suppose you have the lists:
       val list1 = Seq(1,2,3,4)
       val list2 = Seq(4,5,6,7)
    a. add these lists together to get: Seq(1,2,3,4,4,5,6,7)
    b. Add the value 8 to the list from (a). expected result: Seq(1,2,3,4,4,5,6,7,8)
    c. The lists have a value in common Write an expression that outputs the common value. (one liner)
    d. The lists Seq(1,2,3,4) and Seq(5,6,7,8) do not have a value in common. Write an expression that shows
       they have no value in common. Expected result: False
       Hint: the list of common values is empty.
   */
  // A. add these lists together to get: Seq(1,2,3,4,4,5,6,7)
  val list1 = Seq(1,2,3,4)
  val list2 = Seq(5,6,7)
  val conbinedlist = Seq(list1,list2).flatten
  println(conbinedlist)

  // Add the value 8 to the list from (a). expected result: Seq(1,2,3,4,4,5,6,7,8)
  val newList = conbinedlist.appended(8)

  // C. The lists have a value in common Write an expression that outputs the common value. (one liner)
  println(newList.diff(newList.distinct).distinct)

  /* D.
     The lists Seq(1,2,3,4) and Seq(5,6,7,8) do not have a value in common. Write an expression that shows
     they have no value in common. Expected result: False
     Hint: the list of common values is empty.
     Prerequisite
     The variable “list1” will be for Seq(1,2,3,4).
     The variable “list3” will be for Seq(5,6,7,8). This variable will be created for use in this test.
  */
  val list3 = Seq(5,6,7,8)
  val numsList = Seq(list1, list3).flatten;
  println(numsList.diff(numsList.distinct).distinct.isEmpty)

  case class Name(first: String, last: String)
  case class Book(title: String, numOfPages: Int, genre: String, author: Name, coAuthors: Seq[Name], illistrator: Seq[illustrator])

  val cith = Book("Cat in the hat", 20, "Children", Name("Dr.", "Suess"),Seq.empty[Name], Seq.empty[illustrator])
  val tgt = Book("The Giving tree", 30, "Children",Name("Shel", "Silverstein"),Seq(Name("Bob", "Vance"), Name("Chris", "Evert")), Seq.empty[illustrator])
  val tdc = Book("The DaVinci Code", 350, "Mystery", Name("Dan", "Brown"), Seq.empty[Name], Seq.empty[illustrator])

  //  A. Create a new instance of Book in the "Mystery" genre.
  val stl = Book("Street Life", 250, "Mystery", Name("Donald", "Goings"), Seq.empty[Name], Seq.empty[illustrator])

  // B. Create a list containing all of these books.
  val myBooks = Seq(cith,tgt,tdc,stl)

  // C. Using the list from (b), we will group these books by genre. Write the following and note the resulting object type:
  println(myBooks.groupBy(_.genre))

  // D. Create your own case class, and add it as a field in the Book case class
  case class illustrator(first: String, last: String)

  /* F.
    Using the list from (b), output a list of author and coAuthor names naive implementation may involve
    - mapping over the list twice, but see if there is a better way!
    - expected output: Seq("Dr. Seuss", "Shel Silverstein", "Bob Vance", "Chris Evert", ...)
  */
  val AuthorsAndCoauthors = Seq(myBooks.map(Book => Book.author), myBooks.map(Book => Book.coAuthors)).flatten
  println(AuthorsAndCoauthors)




}



