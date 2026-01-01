/**
 * ============================================================================
 * TOPIC: Functions in Kotlin
 * DIFFICULTY: Beginner to Intermediate
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Functions are blocks of reusable code. Kotlin has powerful function features
 * including default parameters, named arguments, extension functions, and more.
 * 
 * KEY CONCEPTS:
 * 1. Function declaration and calling
 * 2. Parameters and return types
 * 3. Default and named parameters
 * 4. Single-expression functions
 * 5. Variable arguments (vararg)
 * 6. Extension functions
 * 7. Higher-order functions
 * 8. Lambda expressions
 * 
 * ============================================================================
 * SYNTAX
 * ============================================================================
 * 
 * BASIC FUNCTION:
 * fun functionName(param: Type): ReturnType {
 *     return value
 * }
 * 
 * SINGLE-EXPRESSION:
 * fun functionName(param: Type) = expression
 * 
 * NO RETURN (Unit):
 * fun functionName(param: Type) {
 *     // code
 * }
 * 
 * ============================================================================
 */

package basics.syntax

class Functions {
    
    /**
     * BASIC FUNCTIONS
     */
    fun basicFunctionsExample() {
        println("=== Basic Functions ===")
        
        // Simple function
        fun greet() {
            println("Hello, World!")
        }
        greet()
        
        // Function with parameters
        fun greetPerson(name: String) {
            println("Hello, $name!")
        }
        greetPerson("Alice")
        
        // Function with return value
        fun add(a: Int, b: Int): Int {
            return a + b
        }
        println("5 + 3 = ${add(5, 3)}")
        
        println()
    }
    
    /**
     * SINGLE-EXPRESSION FUNCTIONS
     */
    fun singleExpressionExample() {
        println("=== Single-Expression Functions ===")
        
        // Type inference
        fun square(x: Int) = x * x
        println("Square of 5: ${square(5)}")
        
        // Explicit return type
        fun max(a: Int, b: Int): Int = if (a > b) a else b
        println("Max of 10 and 20: ${max(10, 20)}")
        
        // String operations
        fun capitalize(str: String) = str.uppercase()
        println("Capitalize 'kotlin': ${capitalize("kotlin")}")
        
        println()
    }
    
    /**
     * DEFAULT PARAMETERS
     */
    fun defaultParametersExample() {
        println("=== Default Parameters ===")
        
        fun printInfo(name: String, age: Int = 25, city: String = "Unknown") {
            println("Name: $name, Age: $age, City: $city")
        }
        
        printInfo("Alice")
        printInfo("Bob", 30)
        printInfo("Charlie", 35, "New York")
        
        // Function with multiple defaults
        fun formatNumber(number: Int, prefix: String = "", suffix: String = "") =
            "$prefix$number$suffix"
        
        println("\n${formatNumber(100)}")
        println(formatNumber(100, prefix = "$"))
        println(formatNumber(100, suffix = "%"))
        println(formatNumber(100, "$", "%"))
        
        println()
    }
    
    /**
     * NAMED ARGUMENTS
     */
    fun namedArgumentsExample() {
        println("=== Named Arguments ===")
        
        fun createUser(name: String, age: Int, email: String, city: String) {
            println("User: $name, $age, $email, $city")
        }
        
        // Positional arguments
        createUser("Alice", 25, "alice@example.com", "NYC")
        
        // Named arguments (any order)
        createUser(
            email = "bob@example.com",
            name = "Bob",
            city = "LA",
            age = 30
        )
        
        // Mix positional and named
        createUser("Charlie", 35, email = "charlie@example.com", city = "Chicago")
        
        println()
    }
    
    /**
     * VARIABLE ARGUMENTS (VARARG)
     */
    fun varargExample() {
        println("=== Variable Arguments ===")
        
        // Vararg function
        fun sum(vararg numbers: Int): Int {
            var total = 0
            for (num in numbers) {
                total += num
            }
            return total
        }
        
        println("Sum(): ${sum()}")
        println("Sum(1): ${sum(1)}")
        println("Sum(1,2,3): ${sum(1, 2, 3)}")
        println("Sum(1,2,3,4,5): ${sum(1, 2, 3, 4, 5)}")
        
        // Spreading array
        val numbers = intArrayOf(10, 20, 30)
        println("Sum(array): ${sum(*numbers)}")  // Spread operator
        
        // Vararg with other parameters
        fun printWithPrefix(prefix: String, vararg items: String) {
            for (item in items) {
                println("$prefix $item")
            }
        }
        
        println()
        printWithPrefix("Item:", "Apple", "Banana", "Orange")
        
        println()
    }
    
    /**
     * RETURN TYPES
     */
    fun returnTypesExample() {
        println("=== Return Types ===")
        
        // Explicit return type
        fun divide(a: Int, b: Int): Double {
            return a.toDouble() / b
        }
        println("10 / 3 = ${divide(10, 3)}")
        
        // Unit return (void)
        fun logMessage(msg: String): Unit {
            println("Log: $msg")
        }
        logMessage("Test message")
        
        // Unit can be omitted
        fun printBanner(text: String) {
            println("=== $text ===")
        }
        printBanner("Hello")
        
        // Nothing return type (never returns normally)
        fun fail(message: String): Nothing {
            throw IllegalStateException(message)
        }
        // fail("Error!") // Would throw exception
        
        println()
    }
    
    /**
     * EXTENSION FUNCTIONS
     */
    fun extensionFunctionsExample() {
        println("=== Extension Functions ===")
        
        // Extension on String
        fun String.isPalindrome(): Boolean {
            return this == this.reversed()
        }
        
        println("'racecar'.isPalindrome(): ${"racecar".isPalindrome()}")
        println("'hello'.isPalindrome(): ${"hello".isPalindrome()}")
        
        // Extension on Int
        fun Int.isEven() = this % 2 == 0
        
        println("10.isEven(): ${10.isEven()}")
        println("7.isEven(): ${7.isEven()}")
        
        // Extension on List
        fun List<Int>.sum(): Int {
            var total = 0
            for (item in this) total += item
            return total
        }
        
        val numbers = listOf(1, 2, 3, 4, 5)
        println("List sum: ${numbers.sum()}")
        
        println()
    }
    
    /**
     * HIGHER-ORDER FUNCTIONS
     */
    fun higherOrderFunctionsExample() {
        println("=== Higher-Order Functions ===")
        
        // Function taking function as parameter
        fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
            return operation(a, b)
        }
        
        val add = { x: Int, y: Int -> x + y }
        val multiply = { x: Int, y: Int -> x * y }
        
        println("operate(5, 3, add): ${operate(5, 3, add)}")
        println("operate(5, 3, multiply): ${operate(5, 3, multiply)}")
        
        // Function returning function
        fun makeMultiplier(factor: Int): (Int) -> Int {
            return { x -> x * factor }
        }
        
        val double = makeMultiplier(2)
        val triple = makeMultiplier(3)
        
        println("double(5): ${double(5)}")
        println("triple(5): ${triple(5)}")
        
        println()
    }
    
    /**
     * LAMBDA EXPRESSIONS
     */
    fun lambdaExpressionsExample() {
        println("=== Lambda Expressions ===")
        
        // Basic lambda
        val sum = { a: Int, b: Int -> a + b }
        println("sum(3, 4): ${sum(3, 4)}")
        
        // Lambda with single parameter (it)
        val square: (Int) -> Int = { it * it }
        println("square(5): ${square(5)}")
        
        // Lambda with multiple statements
        val describe = { x: Int ->
            val result = if (x > 0) "positive" else if (x < 0) "negative" else "zero"
            "Number $x is $result"
        }
        println(describe(10))
        
        // Using lambdas with collections
        val numbers = listOf(1, 2, 3, 4, 5)
        println("Filter even: ${numbers.filter { it % 2 == 0 }}")
        println("Map square: ${numbers.map { it * it }}")
        println("Sum: ${numbers.reduce { acc, num -> acc + num }}")
        
        println()
    }
    
    /**
     * INLINE FUNCTIONS
     */
    // Inline function (optimized for lambdas) - defined at class level
    inline fun measureTime(block: () -> Unit): Long {
        val start = System.nanoTime()
        block()
        return System.nanoTime() - start
    }
    
    fun inlineFunctionsExample() {
        println("=== Inline Functions ===")
        
        val time = measureTime {
            var sum = 0
            for (i in 1..1000) sum += i
        }
        println("Execution time: ${time / 1000} μs")
        
        println()
    }
    
    /**
     * TAIL RECURSIVE FUNCTIONS
     */
    fun tailRecursiveExample() {
        println("=== Tail Recursive Functions ===")
        
        // Regular recursion
        fun factorial(n: Int): Long {
            return if (n <= 1) 1 else n * factorial(n - 1)
        }
        
        // Tail recursive (optimized)
        tailrec fun factorialTail(n: Int, accumulator: Long = 1): Long {
            return if (n <= 1) accumulator else factorialTail(n - 1, n * accumulator)
        }
        
        println("factorial(5): ${factorial(5)}")
        println("factorialTail(5): ${factorialTail(5)}")
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: String utilities
        fun String.wordCount() = this.trim().split("\\s+".toRegex()).size
        
        val text = "The quick brown fox jumps"
        println("Word count: ${text.wordCount()}")
        
        // Example 2: Math utilities
        fun isPrime(n: Int): Boolean {
            if (n < 2) return false
            for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
                if (n % i == 0) return false
            }
            return true
        }
        
        println("17 is prime: ${isPrime(17)}")
        println("20 is prime: ${isPrime(20)}")
        
        // Example 3: Array operations
        fun IntArray.swap(i: Int, j: Int) {
            val temp = this[i]
            this[i] = this[j]
            this[j] = temp
        }
        
        val arr = intArrayOf(1, 2, 3, 4, 5)
        arr.swap(0, 4)
        println("After swap: ${arr.contentToString()}")
        
        // Example 4: Validation
        fun isValidEmail(email: String) = email.matches(Regex("^[\\w.-]+@[\\w.-]+\\.\\w+$"))
        
        println("\nvalid@email.com: ${isValidEmail("valid@email.com")}")
        println("invalid-email: ${isValidEmail("invalid-email")}")
        
        println()
    }
}

/**
 * ============================================================================
 * FUNCTION TYPES
 * ============================================================================
 * 
 * FUNCTION TYPE NOTATION:
 * (ParamType1, ParamType2) -> ReturnType
 * 
 * EXAMPLES:
 * () -> Unit                  // No parameters, no return
 * (Int) -> Int                // One Int param, returns Int
 * (Int, Int) -> Boolean       // Two Int params, returns Boolean
 * (String) -> Unit            // One String param, no return
 * 
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * 1. Keep functions small and focused (single responsibility)
 * 2. Use descriptive names (verbs for actions)
 * 3. Use default parameters instead of overloads
 * 4. Use named arguments for clarity with many parameters
 * 5. Prefer single-expression functions when simple
 * 6. Use extension functions to add utility methods
 * 7. Use inline for functions with lambda parameters
 * 8. Document public functions with KDoc
 * 9. Limit function parameters (max 3-4 recommended)
 * 10. Use data classes for multiple return values
 * 
 * ============================================================================
 * COMMON PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Validation
 * fun isValid(value: String): Boolean = value.isNotEmpty()
 * 
 * PATTERN 2: Transformation
 * fun transform(input: Type): OutputType = ...
 * 
 * PATTERN 3: Builder
 * fun build(config: () -> Unit): Object { ... }
 * 
 * PATTERN 4: Factory
 * fun create(): Object = Object()
 * 
 * PATTERN 5: Extension utility
 * fun Type.utility() = ...
 * 
 * ============================================================================
 */

fun main() {
    val demo = Functions()
    
    println("=== Kotlin Functions ===\n")
    
    demo.basicFunctionsExample()
    demo.singleExpressionExample()
    demo.defaultParametersExample()
    demo.namedArgumentsExample()
    demo.varargExample()
    demo.returnTypesExample()
    demo.extensionFunctionsExample()
    demo.higherOrderFunctionsExample()
    demo.lambdaExpressionsExample()
    demo.inlineFunctionsExample()
    demo.tailRecursiveExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ Functions are first-class citizens")
    println("✓ Support default and named parameters")
    println("✓ Extension functions add methods to existing types")
    println("✓ Higher-order functions take/return functions")
    println("✓ Lambdas provide concise function syntax")
    println("✓ Inline functions optimize lambda usage")
}
