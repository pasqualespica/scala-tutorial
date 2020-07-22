object DefaultParameterValues extends App {

  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println("DefaultParameterValues !");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

  def log(message: String, level: String = "INFO") =
    println(s"$level: $message")

  log("System starting") // prints INFO: System starting
  log("User not found", "WARNING") // prints WARNING: User not found

  // The parameter level has a default value so it is optional. On the last line,
  // the argument "WARNING" overrides the default argument "INFO". Where you might do overloaded methods in Java,
  // you can use methods with optional parameters to achieve the same effect.

  // However, if the caller omits an argument, any following arguments must be named.

  class Point(val x: Double = 0, val y: Double = 0)

  val point1 = new Point(y = 1)

  println("==================");
  println("NAMED ARGUMENTS positional VS named : ");
  println("==================");

  // When calling methods, you can label the arguments with their parameter names like so:
  def printName(first: String, last: String): Unit = {
    println(first + " " + last)
  }

  printName("John", "Smith") // Prints "John Smith"
  printName(first = "John", last = "Smith") // Prints "John Smith"
  printName(last = "Smith", first = "John") // Prints "John Smith"
  // Notice how the order of named arguments can be rearranged. However, if some arguments are named and others are not, the unnamed arguments must come first and in the order of their parameters in the method signature.

//   printName(last = "Smith", "john") // error: positional after named argument
  // Note that named arguments do not work with calls to Java method

}
