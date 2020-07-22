/*
    Traits are used to share interfaces and fields between classes.
    They are similar to Java 8’s interfaces. Classes and objects can extend traits,
    but traits cannot be instantiated and therefore have no parameters.
 */

object Traits_ extends App {

  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println("Traits and tuples !");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

  println("==================");
  println("Defining a trait : ");
  println("==================");

  // A minimal trait is simply the keyword trait and an identifier:

  trait HairColor
  // Traits become especially useful as generic types and with abstract methods.

  trait Iterator[A] {
    def hasNext: Boolean
    def next(): A
  }

  // Extending the trait Iterator[A] requires a type A and implementations of the methods hasNext and next.

  println("==================");
  println("Using traits : ");
  println("==================");

  // Use the extends keyword to extend a trait.
  // Then implement any abstract members of the trait using the override keyword:

  trait Iterator_[A] {
    def hasNext: Boolean
    def next(): A
  }

  class IntIterator(to: Int) extends Iterator[Int] {
    private var current = 0
    override def hasNext: Boolean = current < to
    override def next(): Int = {
      if (hasNext) {
        val t = current
        current += 1
        t
      } else 0
    }
  }

  println("create IntIterator(10)");
  val iterator = new IntIterator(10)
  println(iterator.next()) // returns 0
  println(iterator.next()) // returns 1

  while (iterator.hasNext) {
    println(iterator.next())
  }
  // This IntIterator class takes a parameter to as an upper bound. It extends Iterator[Int] which means that the next method must return an Int.

  println("==================");
  println("Subtyping : ");
  println("==================");
  // Where a given trait is required, a subtype of the trait can be used instead.

  import scala.collection.mutable.ArrayBuffer

  trait Pet {
    val name: String
  }

  class Cat(val name: String) extends Pet
  class Dog(val name: String) extends Pet

  val dog = new Dog("Harry-dog")
  val cat = new Cat("Sally-cat")

  val animals = ArrayBuffer.empty[Pet]
  animals.append(dog)
  animals.append(cat)
  animals.foreach(pet => println(pet.name)) // Prints Harry Sally
  // The trait Pet has an abstract field name that gets implemented by Cat and Dog in their constructors.
  // On the last line, we call pet.name, which must be implemented in any subtype of the trait Pet

  println("==================");
  println("TUPLES : ");
  println("==================");
  // in Scala, a tuple is a value that contains a fixed number of elements,
  // each with a distinct type. Tuples are immutable.
  // Tuples are especially handy for returning multiple values from a method.

  // A tuple with two elements can be created as follows:
  val ingredient = ("Sugar", 25)

  // This creates a tuple containing a String element and an Int element.
  // The inferred type of ingredient is (String, Int), which is shorthand for Tuple2[String, Int].
  val ricetta: Tuple2[String, Int] = ("ciao", 3)
  println(ricetta, ricetta._2)

  println("==================");
  println("Accessing the elements : ");
  println("==================");
  //  One way of accessing tuple elements is by position. The individual elements are named _1, _2, and so forth.

  println(ingredient._1) // Sugar
  println(ingredient._2) // 25

  // To represent tuples, Scala uses a series of classes: Tuple2, Tuple3, etc., through Tuple22.
  // Each class has as many type parameters as it has elements.

  println("==================");
  println("Pattern matching on tuples : ");
  println("==================");

  // A tuple can also be taken apart using pattern matching:

  val (name, quantity) = ingredient
  println(name) // Sugar
  println(quantity) // 25
  // Here name’s inferred type is String and quantity’s inferred type is Int.

  // Here is another example of pattern-matching a tuple:

  val planets =
    List(
      ("Mercury", 57.9),
      ("Venus", 108.2),
      ("Earth", 149.6),
      ("Mars", 227.9),
      ("Jupiter", 778.3)
    )
  planets.foreach {
    case ("Earth", distance) =>
      println(s"Our planet is $distance million kilometers from the sun")
    case _ => 
  }

}
