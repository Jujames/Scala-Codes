package lectures.part2oop

object Enums {

  enum Permission {
    case READ, WRITE, EXECUTE, NONE

    // add fields/method
    def openDoucument(): Unit =
      if (this == READ) println("opening document....")
      else println("readung not allowed")

  }

  val somePermissions: Permission = Permission.READ

   // consructors args
   enum PermissionWithBits(bits: Int) {
     case READ extends PermissionWithBits(4) // 100
     case WRITE extends PermissionWithBits(2) // 010
     case EXECUTE extends PermissionWithBits(1) // 001
     case NONE extends PermissionWithBits(0) // 000
   }

   object PermissionWithBits {
     def fromBits(bits: Int): PermissionWithBits = // Whatever
        PermissionWithBits.NONE
   }

   // standard API
   val somePermissionOrdinal = somePermissions.ordinal
   val allPermissions = PermissionWithBits.values // array of all possible value of enum
   val readPermission: Permission = Permission.valueOf("READ") // Permission.READ

   def main(args: Array[String]): Unit = {
    somePermissions.openDoucument()
     println(somePermissionOrdinal)
   }

}
