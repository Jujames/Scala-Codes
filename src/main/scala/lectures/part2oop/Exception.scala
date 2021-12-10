package lectures.part2oop

object Exception extends App {

  val x: String = null
  // println(x.length)
  // this ^^ will crash with a NPE

  // 1. throwing and catchung exceptions

  // val aWeirdValue: String = throw new NullPointerException

  // throwable classes extends the Trowable class.
  // Exception and Errors are the major trowable subtype

  // 2. how to catch exception
  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  val potentialFail = try {
    // code that might throw
    getInt(false)
  } catch {
    case e: RuntimeException => println(43)
  } finally {
    // code that will get executed NO MATTER WHAT.
    // finally is optional
    // finally does not influence the return type of this expression
    // use finally only for side effects
    println("finally")
  }

  println(potentialFail)

  // 3. how to define your own exceptions
  class MyException extends Exception
  val exception = new MyException

  // throw exception

  /*
    1.  Crash your program qith an OutOfMemoryError
    2.  Crash with SOERROR
    3.  PocketCalculator
        - add (x,y)
        - subtract(x,y)
        - multiply(x,y)
        - divide(x,y)

        Throw
         - OverFlowException if add(x,y0 exceeds Int.MAX_VALUE
         - UnderFlowException if subtract(x,y) exceeds Int.MIN_VALUE
         - MAthCalculationException for division by 0
  */
  // OOM
  // val array = Array.ofDim(Int.MaxValue)

  // STACK OVERFLOW
 // def infinite: Int = 1 + infinite
  //val noLimit = infinite
  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MathCalculationException extends RuntimeException("Division by 0")

  object PocketCalculator {
    def add(x: Int, y: Int) = {
      val result = x + y

      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def subtract(x: Int, y: Int) = {
      val result = x - y

      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }

  println(PocketCalculator.add(Int.MaxValue, 10))
  println(PocketCalculator.divide(2, 0))
 }
