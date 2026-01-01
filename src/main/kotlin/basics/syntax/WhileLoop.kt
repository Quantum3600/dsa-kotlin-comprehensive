/**
 * ============================================================================
 * TOPIC: While Loops in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * While loops execute code repeatedly as long as a condition is true.
 * Kotlin has two types: while (checks condition first) and do-while (checks
 * condition after first execution).
 * 
 * KEY CONCEPTS:
 * 1. While loop basics
 * 2. Do-while loop
 * 3. Infinite loops
 * 4. Break and continue
 * 5. Loop control patterns
 * 
 * ============================================================================
 * SYNTAX
 * ============================================================================
 * 
 * WHILE:
 * while (condition) {
 *     // code
 * }
 * 
 * DO-WHILE:
 * do {
 *     // code
 * } while (condition)
 * 
 * DIFFERENCE:
 * - while: May execute 0 times (checks condition first)
 * - do-while: Executes at least once (checks condition after)
 * 
 * ============================================================================
 */

package basics.syntax

class WhileLoop {
    
    /**
     * BASIC WHILE LOOP
     */
    fun basicWhileExample() {
        println("=== Basic While Loop ===")
        
        // Count to 5
        var count = 1
        print("Count to 5: ")
        while (count <= 5) {
            print("$count ")
            count++
        }
        println()
        
        // Sum of numbers
        var sum = 0
        var i = 1
        while (i <= 10) {
            sum += i
            i++
        }
        println("Sum 1 to 10: $sum")
        
        println()
    }
    
    /**
     * DO-WHILE LOOP
     */
    fun doWhileExample() {
        println("=== Do-While Loop ===")
        
        // Executes at least once
        var x = 10
        do {
            println("x = $x (executes at least once)")
            x--
        } while (x > 10)  // Condition false, but body executed once
        
        // Countdown
        var count = 5
        print("\nCountdown: ")
        do {
            print("$count ")
            count--
        } while (count > 0)
        println("Liftoff!")
        
        println()
    }
    
    /**
     * WHILE VS DO-WHILE
     */
    fun whileVsDoWhileExample() {
        println("=== While vs Do-While ===")
        
        // While: May not execute
        var i = 10
        println("While loop (condition false):")
        while (i < 5) {
            println("This won't print")
            i++
        }
        println("While body didn't execute")
        
        // Do-while: Always executes once
        var j = 10
        println("\nDo-while loop (condition false):")
        do {
            println("This prints once! j=$j")
            j++
        } while (j < 5)
        
        println()
    }
    
    /**
     * BREAK IN WHILE LOOP
     */
    fun breakInWhileExample() {
        println("=== Break in While ===")
        
        // Break out of loop
        var num = 1
        print("Break at 5: ")
        while (num <= 10) {
            if (num == 5) break
            print("$num ")
            num++
        }
        println("\nStopped at: $num")
        
        // Find first multiple
        var n = 1
        while (true) {  // Infinite loop
            if (n % 7 == 0 && n % 13 == 0) {
                break
            }
            n++
        }
        println("First number divisible by both 7 and 13: $n")
        
        println()
    }
    
    /**
     * CONTINUE IN WHILE LOOP
     */
    fun continueInWhileExample() {
        println("=== Continue in While ===")
        
        // Skip even numbers
        var i = 0
        print("Odd numbers: ")
        while (i < 10) {
            i++
            if (i % 2 == 0) continue
            print("$i ")
        }
        println()
        
        // Skip multiples of 3
        var j = 0
        print("Not multiples of 3: ")
        while (j < 15) {
            j++
            if (j % 3 == 0) continue
            print("$j ")
        }
        println("\n")
    }
    
    /**
     * NESTED WHILE LOOPS
     */
    fun nestedWhileExample() {
        println("=== Nested While Loops ===")
        
        // Multiplication table
        println("3x3 Multiplication:")
        var i = 1
        while (i <= 3) {
            var j = 1
            while (j <= 3) {
                print("${i * j} ")
                j++
            }
            println()
            i++
        }
        
        println()
    }
    
    /**
     * INFINITE LOOPS
     */
    fun infiniteLoopsExample() {
        println("=== Infinite Loops (demonstration) ===")
        
        // Infinite loop pattern 1
        println("Pattern: while (true) { ... break ... }")
        var count = 0
        while (true) {
            count++
            if (count > 5) break
        }
        println("Broke out after $count iterations")
        
        // Infinite loop pattern 2
        println("\nPattern: do { ... break ... } while (true)")
        var n = 0
        do {
            n++
            if (n > 3) break
        } while (true)
        println("Broke out after $n iterations")
        
        println()
    }
    
    /**
     * READING INPUT WITH WHILE
     */
    fun inputLoopExample() {
        println("=== Input Loop Pattern ===")
        
        // Demonstrate pattern (not actual input)
        println("Pattern for reading until sentinel:")
        println("""
            while (true) {
                val input = readLine() ?: break
                if (input == "quit") break
                // process input
            }
        """.trimIndent())
        
        // Sum until zero pattern
        println("\nSum numbers until zero (demonstration):")
        val numbers = listOf(5, 10, 15, 0, 20)  // Simulated input
        var sum = 0
        var index = 0
        while (index < numbers.size) {
            val num = numbers[index]
            if (num == 0) break
            sum += num
            index++
        }
        println("Sum: $sum")
        
        println()
    }
    
    /**
     * CONDITION-BASED LOOPS
     */
    fun conditionBasedExample() {
        println("=== Condition-Based Loops ===")
        
        // Power of 2 less than 1000
        var power = 1
        print("Powers of 2 less than 1000: ")
        while (power < 1000) {
            print("$power ")
            power *= 2
        }
        println()
        
        // Divide until 1
        var num = 100
        print("\nDivide by 2 until 1: ")
        while (num > 1) {
            print("$num ")
            num /= 2
        }
        println("$num")
        
        // Collatz sequence (3n+1 problem)
        num = 13
        print("\nCollatz sequence from 13: ")
        while (num != 1) {
            print("$num ")
            num = if (num % 2 == 0) num / 2 else 3 * num + 1
        }
        println("$num")
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: Reverse a number
        fun reverseNumber(n: Int): Int {
            var num = n
            var reversed = 0
            while (num != 0) {
                val digit = num % 10
                reversed = reversed * 10 + digit
                num /= 10
            }
            return reversed
        }
        val original = 12345
        println("Reverse of $original: ${reverseNumber(original)}")
        
        // Example 2: Count digits
        fun countDigits(n: Int): Int {
            var num = Math.abs(n)
            var count = 0
            do {
                count++
                num /= 10
            } while (num != 0)
            return count
        }
        println("Digits in 54321: ${countDigits(54321)}")
        
        // Example 3: GCD using Euclidean algorithm
        fun gcd(a: Int, b: Int): Int {
            var x = a
            var y = b
            while (y != 0) {
                val temp = y
                y = x % y
                x = temp
            }
            return x
        }
        println("GCD(48, 18): ${gcd(48, 18)}")
        
        // Example 4: Binary to decimal
        fun binaryToDecimal(binary: String): Int {
            var decimal = 0
            var power = 0
            var i = binary.length - 1
            while (i >= 0) {
                if (binary[i] == '1') {
                    decimal += (1 shl power)  // 2^power
                }
                power++
                i--
            }
            return decimal
        }
        println("Binary 1101 to decimal: ${binaryToDecimal("1101")}")
        
        // Example 5: Find sum of digits
        fun sumOfDigits(n: Int): Int {
            var num = Math.abs(n)
            var sum = 0
            while (num > 0) {
                sum += num % 10
                num /= 10
            }
            return sum
        }
        println("Sum of digits of 12345: ${sumOfDigits(12345)}")
        
        println()
    }
}

/**
 * ============================================================================
 * WHEN TO USE WHICH LOOP
 * ============================================================================
 * 
 * FOR LOOP:
 * ✓ Iterating over a range (1..10)
 * ✓ Iterating over collections
 * ✓ Known number of iterations
 * 
 * WHILE LOOP:
 * ✓ Unknown number of iterations
 * ✓ Condition-based termination
 * ✓ May not execute at all
 * 
 * DO-WHILE LOOP:
 * ✓ Must execute at least once
 * ✓ Input validation loops
 * ✓ Menu systems
 * 
 * ============================================================================
 * COMMON PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Count up
 * var i = 0
 * while (i < n) {
 *     // process
 *     i++
 * }
 * 
 * PATTERN 2: Process until condition
 * while (condition) {
 *     // process
 *     // update condition
 * }
 * 
 * PATTERN 3: Sentinel-controlled
 * while (true) {
 *     val input = readLine() ?: break
 *     if (input == "quit") break
 *     // process
 * }
 * 
 * PATTERN 4: At least once execution
 * do {
 *     // code
 * } while (condition)
 * 
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * 1. Initialize loop variables before loop
 * 2. Ensure loop condition will eventually become false
 * 3. Update loop variables inside loop
 * 4. Use break for infinite loop patterns
 * 5. Prefer for loop when iterations are known
 * 6. Use do-while when code must execute once
 * 7. Be careful with infinite loops
 * 8. Test edge cases (0 iterations, 1 iteration)
 * 
 * COMMON MISTAKES:
 * ✗ Forgetting to update loop variable (infinite loop)
 * ✗ Off-by-one errors in conditions
 * ✗ Using = instead of == in condition
 * ✗ Modifying loop variable incorrectly
 * 
 * ============================================================================
 */

fun main() {
    val demo = WhileLoop()
    
    println("=== Kotlin While Loops ===\n")
    
    demo.basicWhileExample()
    demo.doWhileExample()
    demo.whileVsDoWhileExample()
    demo.breakInWhileExample()
    demo.continueInWhileExample()
    demo.nestedWhileExample()
    demo.infiniteLoopsExample()
    demo.inputLoopExample()
    demo.conditionBasedExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ while: Condition checked before execution")
    println("✓ do-while: Executes at least once")
    println("✓ Use break to exit, continue to skip")
    println("✓ Ensure termination condition is reachable")
    println("✓ Prefer for loop when iterations are known")
}
