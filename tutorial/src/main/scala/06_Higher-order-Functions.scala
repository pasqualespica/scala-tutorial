object Higher_order_Functions extends App {

  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
  println("Higher_order_Functions !");
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");;
  println(">>>>>>>>>>>>>>>>>>>>>>>>>>");;

  /*

  Higher order functions take other functions as parameters or return a function as a result.
  This is possible because functions are first-class values in Scala. The terminology can get a bit confusing at this point, and we use the phrase “higher order function” for both methods and functions that take functions as parameters or that return a function.

  In a pure Object Oriented world a good practice is to avoid exposing methods
  parameterized with functions that might leak object’s internal state. Leaking
  internal state might break the invariants of the object itself thus violating encapsulation.

  One of the most common examples is the higher-order function `map` which is available for collections in Scala.

   */

  val salaries = Seq(20000, 70000, 40000)
  val employers: Seq[String] = Seq("Mario", "Luca", "Gigi")
  val doubleSalary = (x: Int) => x * 2
  val newSalaries = salaries.map(doubleSalary) // List(40000, 140000, 80000)
  println(newSalaries, employers);
  newSalaries.foreach(println)
  employers.map(println)

  // doubleSalary is a function which takes a single Int, x, and returns x * 2. In general, the tuple on the left of the arrow => is a parameter list and the value of the expression on the right is what gets returned. On line 3, the function doubleSalary gets applied to each element in the list of salaries.

  //  To shrink the code, we could make the function anonymous and pass it directly as an argument to map:
  val newSalaries_shrink =
    salaries.map(x => x * 2) // List(40000, 140000, 80000)
  println(s"newSalaries_shrink : $newSalaries_shrink")
  // Notice how x is not declared as an Int in the above example.
  // That’s because the compiler can infer the type based on the type of function map expects
  // (see Currying.)

  //  An even more idiomatic way to write the same piece of code would be:

  val newSalaries_idiomatic = salaries.map(_ * 2)
  println(s"newSalaries_idiomatic : $newSalaries_idiomatic")
  // Since the Scala compiler already knows the type of the parameters (a single Int),
  // you just need to provide the right side of the function. The only caveat is that
  // you need to use _ in place of a parameter name (it was x in the previous example).

  println("==================");
  println("Coercing methods into functions : ");
  println("==================");
  // It is also possible to pass methods as arguments to higher-order functions
  // because the Scala compiler will coerce the method into a function.

  case class WeeklyWeatherForecast(temperatures: Seq[Double]) {

    private def convertCtoF(temp: Double) = temp * 1.8 + 32

    def forecastInFahrenheit: Seq[Double] =
      temperatures.map(convertCtoF) // <-- passing the method convertCtoF

  }

  val p = new WeeklyWeatherForecast(Seq(10.0, 20.0, 30.0))
  println(p.forecastInFahrenheit)
  // Here the method convertCtoF is passed to the higher order function map.
  // This is possible because the compiler coerces convertCtoF to the function x => convertCtoF(x)
  // (note: x will be a generated name which is guaranteed to be unique within its scope).

  println("==================");
  println("Functions that accept functions : ");
  println("==================");
  // One reason to use higher-order functions is to reduce redundant code.
  // Let’s say you wanted some methods that could raise someone’s salaries by various factors.
  // Without creating a higher-order function, it might look something like this:

  object SalaryRaiser {

    def smallPromotion(salaries: List[Double]): List[Double] =
      salaries.map(salary => salary * 1.1)

    def greatPromotion(salaries: List[Double]): List[Double] =
      salaries.map(salary => salary * math.log(salary))

    def hugePromotion(salaries: List[Double]): List[Double] =
      salaries.map(salary => salary * salary)
  }
  // Notice how each of the three methods vary only by the multiplication factor.
  // To simplify, you can extract the repeated code into a higher-order function like so:

  object SalaryRaiser_ {

    private def promotion(
        salaries: List[Double],
        promotionFunction: Double => Double
    ): List[Double] =
      salaries.map(promotionFunction)

    def smallPromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * 1.1)

    def greatPromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * math.log(salary))

    def hugePromotion(salaries: List[Double]): List[Double] =
      promotion(salaries, salary => salary * salary)
  }

  val salariesDouble = List(20000.0, 70000.0, 40000.0)
  val nuoviSalari = SalaryRaiser
  println(s"smallPromotion ${nuoviSalari.smallPromotion(salariesDouble)}")
  println(s"greatPromotion ${nuoviSalari.greatPromotion(salariesDouble)}")
  println(s"hugePromotion ${nuoviSalari.hugePromotion(salariesDouble)}")
  // The new method, promotion, takes the salaries plus a function of type Double => Double
  //  (i.e. a function that takes a Double and returns a Double) and returns the product.

  // Methods and functions usually express behaviours or data transformations,
  // therefore having functions that compose based on other functions can help
  // building generic mechanisms.
  // Those generic operations defer to lock down the entire operation behaviour
  // giving clients a way to control or further customize parts of the operation itself.

  println("==================");
  println("Functions that return functions : ");
  println("==================");
  // There are certain cases where you want to generate a function.
  // Here’s an example of a method that returns a function.

  def urlBuilder(
      ssl: Boolean,
      domainName: String
  ): (String, String) => String = {
    val schema = if (ssl) "https://" else "http://"
    (endpoint: String, query: String) => s"$schema$domainName/$endpoint?$query"
  }

  val domainName = "www.example.com"
  def getURL = urlBuilder(ssl = true, domainName)
  val endpoint = "users"
  val query = "id=1"
  val url =
    getURL(endpoint, query) // "https://www.example.com/users?id=1": String
  println(s"url : $url")

  // Notice the return type of urlBuilder (String, String) => String.
  // This means that the returned anonymous function takes two Strings and returns a String.
  // In this case, the returned anonymous function is
  // (endpoint: String, query: String) => s"https://www.example.com/$endpoint?$query".

  println("==================");
  println("NESTED METHODS : ");
  println("==================");

  // In Scala it is possible to nest method definitions.
  // The following object provides a factorial method for computing the factorial of a given number:

  def factorial(x: Int): Int = {
    def fact(x: Int, accumulator: Int): Int = {
      if (x <= 1) accumulator
      else fact(x - 1, x * accumulator)
    }
    fact(x, 1)
  }

  println("Factorial of 2: " + factorial(2))
  println("Factorial of 3: " + factorial(3))

  println("==================");
  println("MULTIPLE PARAMETER LISTS (CURRYING) : ");
  println("==================");

  // Methods may have multiple parameter lists.
  // Here is an example, as defined on the TraversableOnce trait in Scala’s collections API:

  // def foldLeft[B](z: B)(op: (B, A) => B): B

  // foldLeft applies a two-parameter function op to an initial
  // value z and all elements of this collection, going left to right. Shown below is an example of its usage.

  // Starting with an initial value of 0,
  // foldLeft here applies the function (m, n) => m + n to each element in the List and the previous accumulated value.

  val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val res = numbers.foldLeft(0)((m, n) => m + n)
  println(s"foldLeft(0) $res") // 55
  println(numbers.sum)

  println("==================");
  println("Use cases - DRIVE TYPE INFERENCE : ");
  println("==================");
  // Suggested use cases for multiple parameter lists include:
  // It so happens that in Scala, type inference proceeds one parameter list at a time. Say you have the following method:

  def foldLeft1[A, B](as: List[A], b0: B, op: (B, A) => B) = ???

  // Then you’d like to call it in the following way, but will find that it doesn’t compile:

  // def notPossible = foldLeft1(numbers, 0, _ + _)
  // you will have to call it like one of the below ways:

  def firstWay = foldLeft1[Int, Int](numbers, 0, _ + _)
  def secondWay = foldLeft1(numbers, 0, (a: Int, b: Int) => a + b)
  // That’s because Scala won’t be able to infer the type of the function _ + _, 
  // as it’s still inferring A and B. By moving the parameter op to its own parameter list,
  //  A and B are inferred in the first parameter list. 
  // These inferred types will then be available to the second parameter list and _ + _ will match the inferred type (Int, Int) => Int


  def foldLeft2[A, B](as: List[A], b0: B)(op: (B, A) => B) = ???
  def possible = foldLeft2(numbers, 0)(_ + _)
  // This definition doesn’t need any type hints and can infer all of its type parameters.



  println("==================");
  println("IMPLICIT PARAMETERS : ");
  println("==================");
  // To specify only certain parameters as implicit, they must be placed in their own implicit parameter list.

  // An example of this is:
  def execute(arg: Int)(implicit ec: scala.concurrent.ExecutionContext) = ???


  println("==================");
  println("PARTIAL APPLICATION : ");
  println("==================");
  // When a method is called with a fewer number of parameter lists, 
  // then this will yield a function taking the missing parameter lists as its arguments. This is formally known as partial application.

  // For example,

  val numbers = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  val numberFunc = numbers.foldLeft(List[Int]()) _

  val squares = numberFunc((xs, x) => xs :+ x*x)
  println(squares) // List(1, 4, 9, 16, 25, 36, 49, 64, 81, 100)

  val cubes = numberFunc((xs, x) => xs :+ x*x*x)
  println(cubes)  // List(1, 8, 27, 64, 125, 216, 343, 512, 729, 1000

}
