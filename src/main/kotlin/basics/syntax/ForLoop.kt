/**
 * ============================================================================
 * TOPIC: For Loops in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * For loops are used to iterate over ranges, collections, arrays, and any
 * object that provides an iterator. Kotlin's for loop is more powerful and
 * concise than traditional C-style for loops.
 * 
 * KEY CONCEPTS:
 * 1. For loop with ranges
 * 2. For loop with collections
 * 3. For loop with indices
 * 4. Step and downTo
 * 5. Break and continue
 * 6. Nested loops
 * 
 * ============================================================================
 * SYNTAX
 * ============================================================================
 * 
 * BASIC FOR:
 * for (item in collection) {
 *     // code
 * }
 * 
 * FOR WITH RANGE:
 * for (i in 1..10) {
 *     // code
 * }
 * 
 * FOR WITH STEP:
 * for (i in 1..10 step 2) {
 *     // code
 * }
 * 
 * FOR DOWNTO:
 * for (i in 10 downTo 1) {
 *     // code
 * }
 * 
 * ============================================================================
 */

package basics.syntax

class ForLoop {
    
    /**
     * BASIC FOR LOOP WITH RANGE
     */
    fun basicForLoopExample() {
        println("=== Basic For Loop ===")
        
        // Inclusive range (1 to 5)
        print("1 to 5: ")
        for (i in 1..5) {
            print("$i ")
        }
        println()
        
        // Exclusive range (1 until 5 means 1 to 4)
        print("1 until 5: ")
        for (i in 1 until 5) {
            print("$i ")
        }
        println()
        
        // Print numbers 1 to 10
        print("1 to 10: ")
        for (i in 1..10) {
            print("$i ")
        }
        println("\n")
    }
    
    /**
     * FOR LOOP WITH STEP
     */
    fun forLoopWithStepExample() {
        println("=== For Loop with Step ===")
        
        // Step by 2
        print("Odd numbers (1 to 10): ")
        for (i in 1..10 step 2) {
            print("$i ")
        }
        println()
        
        // Even numbers
        print("Even numbers (2 to 10): ")
        for (i in 2..10 step 2) {
            print("$i ")
        }
        println()
        
        // Step by 5
        print("Multiples of 5: ")
        for (i in 0..50 step 5) {
            print("$i ")
        }
        println("\n")
    }
    
    /**
     * REVERSE ITERATION (DOWNTO)
     */
    fun reverseIterationExample() {
        println("=== Reverse Iteration ===")
        
        // Countdown
        print("Countdown: ")
        for (i in 10 downTo 1) {
            print("$i ")
        }
        println("Liftoff!")
        
        // Reverse with step
        print("Reverse by 2: ")
        for (i in 10 downTo 1 step 2) {
            print("$i ")
        }
        println("\n")
    }
    
    /**
     * FOR LOOP WITH ARRAYS
     */
    fun forLoopWithArraysExample() {
        println("=== For Loop with Arrays ===")
        
        val fruits = arrayOf("Apple", "Banana", "Orange", "Mango")
        
        // Iterate elements
        print("Fruits: ")
        for (fruit in fruits) {
            print("$fruit ")
        }
        println()
        
        // Iterate with index
        println("\nWith indices:")
        for (i in fruits.indices) {
            println("$i: ${fruits[i]}")
        }
        
        // Using withIndex()
        println("\nUsing withIndex:")
        for ((index, fruit) in fruits.withIndex()) {
            println("$index: $fruit")
        }
        
        println()
    }
    
    /**
     * FOR LOOP WITH COLLECTIONS
     */
    fun forLoopWithCollectionsExample() {
        println("=== For Loop with Collections ===")
        
        // List
        val numbers = listOf(1, 2, 3, 4, 5)
        print("List: ")
        for (num in numbers) {
            print("$num ")
        }
        println()
        
        // Set
        val colors = setOf("Red", "Green", "Blue")
        print("Set: ")
        for (color in colors) {
            print("$color ")
        }
        println()
        
        // Map
        val ages = mapOf("Alice" to 25, "Bob" to 30, "Charlie" to 35)
        println("\nMap:")
        for ((name, age) in ages) {
            println("$name is $age years old")
        }
        
        println()
    }
    
    /**
     * NESTED FOR LOOPS
     */
    fun nestedForLoopsExample() {
        println("=== Nested For Loops ===")
        
        // Multiplication table
        println("Multiplication Table (1-5):")
        for (i in 1..5) {
            for (j in 1..5) {
                print("${(i * j).toString().padStart(3)} ")
            }
            println()
        }
        
        // Pattern printing
        println("\nPattern:")
        for (i in 1..5) {
            for (j in 1..i) {
                print("* ")
            }
            println()
        }
        
        println()
    }
    
    /**
     * BREAK AND CONTINUE
     */
    fun breakContinueExample() {
        println("=== Break and Continue ===")
        
        // Break - exit loop
        print("Break at 5: ")
        for (i in 1..10) {
            if (i == 5) break
            print("$i ")
        }
        println()
        
        // Continue - skip iteration
        print("Skip even numbers: ")
        for (i in 1..10) {
            if (i % 2 == 0) continue
            print("$i ")
        }
        println()
        
        // Find first element
        val numbers = listOf(1, 3, 5, 7, 8, 10)
        var firstEven = 0
        for (num in numbers) {
            if (num % 2 == 0) {
                firstEven = num
                break
            }
        }
        println("First even: $firstEven")
        
        println()
    }
    
    /**
     * LABELED BREAKS
     */
    fun labeledBreaksExample() {
        println("=== Labeled Breaks ===")
        
        // Break from outer loop
        outer@ for (i in 1..3) {
            for (j in 1..3) {
                if (i == 2 && j == 2) {
                    println("Breaking from outer loop at i=$i, j=$j")
                    break@outer
                }
                print("($i,$j) ")
            }
            println()
        }
        
        println()
    }
    
    /**
     * FOR EACH METHODS
     */
    fun forEachMethodsExample() {
        println("=== forEach Methods ===")
        
        val numbers = listOf(1, 2, 3, 4, 5)
        
        // forEach
        print("forEach: ")
        numbers.forEach { print("$it ") }
        println()
        
        // forEachIndexed
        println("forEachIndexed:")
        numbers.forEachIndexed { index, value ->
            println("  Index $index: $value")
        }
        
        // Note: Can't use break/continue with forEach
        
        println()
    }
    
    /**
     * CHAR RANGES
     */
    fun charRangesExample() {
        println("=== Character Ranges ===")
        
        // Letters
        print("Lowercase: ")
        for (c in 'a'..'e') {
            print("$c ")
        }
        println()
        
        print("Uppercase: ")
        for (c in 'A'..'E') {
            print("$c ")
        }
        println("\n")
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: Sum of numbers
        var sum = 0
        for (i in 1..100) {
            sum += i
        }
        println("Sum 1 to 100: $sum")
        
        // Example 2: Factorial
        fun factorial(n: Int): Long {
            var result = 1L
            for (i in 1..n) {
                result *= i
            }
            return result
        }
        println("Factorial of 5: ${factorial(5)}")
        
        // Example 3: Find primes
        fun isPrime(n: Int): Boolean {
            if (n < 2) return false
            for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
                if (n % i == 0) return false
            }
            return true
        }
        print("\nPrimes up to 20: ")
        for (i in 2..20) {
            if (isPrime(i)) print("$i ")
        }
        println()
        
        // Example 4: Fibonacci sequence
        print("\nFirst 10 Fibonacci: ")
        var a = 0
        var b = 1
        for (i in 1..10) {
            print("$a ")
            val next = a + b
            a = b
            b = next
        }
        println()
        
        // Example 5: Array search
        val array = intArrayOf(5, 2, 8, 1, 9, 3)
        val target = 8
        var foundIndex = -1
        for (i in array.indices) {
            if (array[i] == target) {
                foundIndex = i
                break
            }
        }
        println("\nFound $target at index: $foundIndex")
        
        println()
    }
}

/**
 * ============================================================================
 * COMMON PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Sum of range
 * var sum = 0
 * for (i in 1..n) sum += i
 * 
 * PATTERN 2: Iterate with index
 * for (i in array.indices) { array[i] }
 * 
 * PATTERN 3: Iterate pairs
 * for ((key, value) in map) { ... }
 * 
 * PATTERN 4: Find element
 * for (item in list) {
 *     if (condition) break
 * }
 * 
 * PATTERN 5: Skip elements
 * for (item in list) {
 *     if (condition) continue
 *     // process
 * }
 * 
 * ============================================================================
 * FOR LOOP vs WHILE LOOP
 * ============================================================================
 * 
 * USE FOR WHEN:
 * ✓ Number of iterations is known
 * ✓ Iterating over collection/range
 * ✓ Need index-based iteration
 * 
 * USE WHILE WHEN:
 * ✓ Number of iterations unknown
 * ✓ Condition-based termination
 * ✓ Reading input until sentinel
 * 
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * 1. Use ranges (1..10) instead of C-style loops
 * 2. Use until instead of .. when you want exclusive end
 * 3. Use indices for array iteration
 * 4. Use withIndex() when you need both index and value
 * 5. Use forEach for simple iterations (when break not needed)
 * 6. Use descriptive variable names (not just i, j, k)
 * 7. Avoid modifying collection while iterating
 * 8. Use labels for nested loop breaks
 * 
 * ============================================================================
 */

fun main() {
    val demo = ForLoop()
    
    println("=== Kotlin For Loops ===\n")
    
    demo.basicForLoopExample()
    demo.forLoopWithStepExample()
    demo.reverseIterationExample()
    demo.forLoopWithArraysExample()
    demo.forLoopWithCollectionsExample()
    demo.nestedForLoopsExample()
    demo.breakContinueExample()
    demo.labeledBreaksExample()
    demo.forEachMethodsExample()
    demo.charRangesExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ For loops iterate over ranges and collections")
    println("✓ Use .. for inclusive, until for exclusive")
    println("✓ step modifies increment, downTo for reverse")
    println("✓ break exits loop, continue skips iteration")
    println("✓ Use labels for nested loop control")
    println("✓ forEach methods for functional style")
}
