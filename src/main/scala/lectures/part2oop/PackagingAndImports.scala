package lectures.part2oop

 import playground.Cinderella


object PackagingAndImports extends App {

  // Package members are accesible by their simple name
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // import the package
  val princess = new Cinderella // playground.Cinderella = fully qualified name to use with out import

  // packages are in hierarchy
  // matching folder structure
  
  // package object

}
