/**
 * ============================================================================
 * TOPIC: User Input/Output in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * User I/O is fundamental to any programming language. In Kotlin, we primarily
 * use readLine() for input and println()/print() for output.
 * 
 * KEY CONCEPTS:
 * 1. Reading input from console
 * 2. Different ways to read various data types
 * 3. Output formatting
 * 4. Error handling for invalid input
 * 5. Reading multiple inputs
 * 
 * ============================================================================
 * INPUT METHODS
 * ============================================================================
 * 
 * 1. readLine(): Reads a line of text as String?
 * 2. readln(): Reads a line of text as String (throws on EOF)
 * 3. Scanner: More flexible input reading
 * 4. BufferedReader: For efficient reading
 * 
 * ============================================================================
 * OUTPUT METHODS
 * ============================================================================
 * 
 * 1. println(): Print with newline
 * 2. print(): Print without newline
 * 3. printf(): Formatted output
 * 4. String templates: "${variable}"
 * 
 * ============================================================================
 */

package basics.syntax

import java.util.Scanner

class UserIO {
    
    /**
     * READ STRING INPUT
     */
    fun readStringExample() {
        println("=== Reading String ===")
        
        // Method 1: readLine() - returns String?
        print("Enter your name: ")
        val name = readLine() ?: "Unknown"
        println("Hello, $name!")
        
        // Method 2: readln() - returns String (throws on EOF)
        print("Enter your city: ")
        val city = readln()
        println("You live in $city")
        
        println()
    }
    
    /**
     * READ INTEGER INPUT
     */
    fun readIntegerExample() {
        println("=== Reading Integers ===")
        
        // Safe way with error handling
        print("Enter your age: ")
        val ageStr = readLine()
        val age = ageStr?.toIntOrNull()
        
        if (age != null) {
            println("You are $age years old")
        } else {
            println("Invalid age entered")
        }
        
        // Using elvis operator for default value
        print("Enter a number: ")
        val number = readLine()?.toIntOrNull() ?: 0
        println("Number: $number")
        
        println()
    }
    
    /**
     * READ DIFFERENT DATA TYPES
     */
    fun readDifferentTypesExample() {
        println("=== Reading Different Types ===")
        
        // Reading Double
        print("Enter price: ")
        val price = readLine()?.toDoubleOrNull() ?: 0.0
        println("Price: $$price")
        
        // Reading Boolean
        print("Are you a student? (true/false): ")
        val isStudent = readLine()?.toBooleanStrictOrNull() ?: false
        println("Student: $isStudent")
        
        // Reading Long
        print("Enter population: ")
        val population = readLine()?.toLongOrNull() ?: 0L
        println("Population: $population")
        
        println()
    }
    
    /**
     * READ MULTIPLE VALUES ON ONE LINE
     */
    fun readMultipleValuesExample() {
        println("=== Reading Multiple Values ===")
        
        // Method 1: Split by space
        print("Enter two numbers (space-separated): ")
        val input = readLine() ?: "0 0"
        val parts = input.split(" ")
        
        if (parts.size >= 2) {
            val num1 = parts[0].toIntOrNull() ?: 0
            val num2 = parts[1].toIntOrNull() ?: 0
            println("Sum: ${num1 + num2}")
        }
        
        // Method 2: Destructuring
        print("Enter three numbers (space-separated): ")
        val (a, b, c) = readLine()
            ?.split(" ")
            ?.mapNotNull { it.toIntOrNull() }
            ?.takeIf { it.size >= 3 }
            ?: listOf(0, 0, 0)
        println("Product: ${a * b * c}")
        
        println()
    }
    
    /**
     * READ ARRAY OF NUMBERS
     */
    fun readArrayExample() {
        println("=== Reading Arrays ===")
        
        print("Enter array size: ")
        val n = readLine()?.toIntOrNull() ?: 0
        
        if (n > 0) {
            val arr = IntArray(n)
            println("Enter $n numbers (space-separated): ")
            val numbers = readLine()
                ?.split(" ")
                ?.mapNotNull { it.toIntOrNull() }
                ?: emptyList()
            
            for (i in 0 until minOf(n, numbers.size)) {
                arr[i] = numbers[i]
            }
            
            println("Array: ${arr.contentToString()}")
            println("Sum: ${arr.sum()}")
        }
        
        println()
    }
    
    /**
     * OUTPUT FORMATTING
     */
    fun outputFormattingExample() {
        println("=== Output Formatting ===")
        
        // Basic output
        println("This prints with newline")
        print("This prints without newline. ")
        println("Next text on same line")
        
        // String templates
        val name = "Alice"
        val age = 25
        println("Name: $name, Age: $age")
        
        // Expressions in templates
        println("Next year: ${age + 1}")
        
        // Formatted decimals
        val pi = 3.14159265359
        println("Pi: ${"%.2f".format(pi)}")
        println("Pi (4 decimals): ${"%.4f".format(pi)}")
        
        // Width and alignment
        println("%-10s: %5d".format("Item", 100))
        println("%-10s: %5d".format("Total", 5000))
        
        println()
    }
    
    /**
     * USING SCANNER (Java style)
     */
    fun scannerExample() {
        println("=== Using Scanner ===")
        
        val scanner = Scanner(System.`in`)
        
        println("Enter name, age, and GPA:")
        print("Name: ")
        val name = scanner.next()
        
        print("Age: ")
        val age = scanner.nextInt()
        
        print("GPA: ")
        val gpa = scanner.nextDouble()
        
        println("\nStudent Info:")
        println("Name: $name")
        println("Age: $age")
        println("GPA: $gpa")
        
        scanner.close()
        println()
    }
    
    /**
     * ERROR HANDLING FOR INPUT
     */
    fun errorHandlingExample() {
        println("=== Error Handling ===")
        
        // Using try-catch
        print("Enter a number: ")
        try {
            val number = readLine()!!.toInt()
            println("You entered: $number")
        } catch (e: NumberFormatException) {
            println("Error: Invalid number format")
        } catch (e: NullPointerException) {
            println("Error: No input provided")
        }
        
        // Safe approach without exceptions
        print("Enter an integer: ")
        val input = readLine()
        when (val num = input?.toIntOrNull()) {
            null -> println("Invalid input")
            else -> println("Valid number: $num")
        }
        
        println()
    }
}

/**
 * ============================================================================
 * COMMON INPUT PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Read single integer
 * val n = readLine()!!.toInt()
 * 
 * PATTERN 2: Read two integers
 * val (a, b) = readLine()!!.split(" ").map { it.toInt() }
 * 
 * PATTERN 3: Read array of integers
 * val arr = readLine()!!.split(" ").map { it.toInt() }.toIntArray()
 * 
 * PATTERN 4: Read multiple lines
 * repeat(n) {
 *     val line = readLine()!!
 *     // process line
 * }
 * 
 * PATTERN 5: Read until empty line
 * while (true) {
 *     val line = readLine() ?: break
 *     if (line.isEmpty()) break
 *     // process line
 * }
 * 
 * ============================================================================
 * COMPETITIVE PROGRAMMING TEMPLATE
 * ============================================================================
 */

fun main() {
    // Fast I/O for competitive programming
    fun readInt() = readLine()!!.toInt()
    fun readLong() = readLine()!!.toLong()
    fun readDouble() = readLine()!!.toDouble()
    fun readStrings() = readLine()!!.split(" ")
    fun readInts() = readStrings().map { it.toInt() }
    fun readLongs() = readStrings().map { it.toLong() }
    
    println("=== Kotlin User I/O Examples ===\n")
    
    val userIO = UserIO()
    
    // Example 1: Basic String I/O
    println("Example 1: String Input")
    println("(Type 'skip' to skip interactive examples)")
    val skip = readLine()?.lowercase() == "skip"
    
    if (!skip) {
        userIO.readStringExample()
        userIO.readIntegerExample()
        userIO.readDifferentTypesExample()
        userIO.readMultipleValuesExample()
        userIO.readArrayExample()
    }
    
    // Example 2: Output Formatting (always show)
    userIO.outputFormattingExample()
    
    // Example 3: Common patterns (demo)
    println("=== Common Patterns (Demo) ===")
    
    // Pattern demonstrations
    println("\nPattern 1: Reading single values")
    println("Code: val n = readLine()!!.toInt()")
    println("Use: Reading count, single number")
    
    println("\nPattern 2: Reading pairs")
    println("Code: val (a, b) = readLine()!!.split(\" \").map { it.toInt() }")
    println("Use: Reading two numbers on one line")
    
    println("\nPattern 3: Reading arrays")
    println("Code: val arr = readLine()!!.split(\" \").map { it.toInt() }")
    println("Use: Reading list of numbers")
    
    println("\nPattern 4: Reading matrix")
    println("Code:")
    println("repeat(rows) {")
    println("    val row = readLine()!!.split(\" \").map { it.toInt() }")
    println("}")
    
    // Safe input helper
    println("\n=== Safe Input Helper Functions ===")
    
    fun safeReadInt(prompt: String = "", default: Int = 0): Int {
        print(prompt)
        return readLine()?.toIntOrNull() ?: default
    }
    
    fun safeReadDouble(prompt: String = "", default: Double = 0.0): Double {
        print(prompt)
        return readLine()?.toDoubleOrNull() ?: default
    }
    
    println("Use helper functions for safer input:")
    println("val age = safeReadInt(\"Age: \", 0)")
    println("val price = safeReadDouble(\"Price: \", 0.0)")
    
    println("\n=== Key Takeaways ===")
    println("✓ Use readLine() for basic input")
    println("✓ Always handle null and conversion errors")
    println("✓ Use toIntOrNull() for safe conversion")
    println("✓ String templates ($var) for easy output")
    println("✓ split() and map() for parsing multiple values")
    println("✓ Scanner for more complex input patterns")
}
