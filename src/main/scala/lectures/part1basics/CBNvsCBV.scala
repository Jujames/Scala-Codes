package lectures.part1basics

    object CBNvsCBV extends App {

      def calledByValue(x: Long): Unit = {
        println("by value: " + x)          //  92451469981200 ----->  calls the value
        println("by value: " + x)          //  92451469981200
      }

      def calledByName(x: => Long): Unit = {
        println("by name: " + x)           // System.nanoTime()) ----->  calls this everytime and re
        println("by name: " + x)           // System.nanoTime())
      }

      calledByValue(System.nanoTime())
      calledByName(System.nanoTime())

      def infinite(): Int = 1 + infinite()
      def printFirst(x: Int, y: => Int) = println(x)

      // printFirst(infinite(), 34)
      printFirst(34, infinite())
    }


