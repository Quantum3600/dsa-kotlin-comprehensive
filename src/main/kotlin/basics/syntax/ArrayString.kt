/**
 * ============================================================================
 * TOPIC: Arrays and Strings in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Arrays and Strings are fundamental data structures. Kotlin provides rich
 * support for both with numerous built-in functions and operators.
 * 
 * KEY CONCEPTS:
 * 1. Array creation and initialization
 * 2. Array operations and manipulations
 * 3. String creation and operations
 * 4. String templates and formatting
 * 5. Common array and string algorithms
 * 
 * ============================================================================
 * ARRAYS IN KOTLIN
 * ============================================================================
 * 
 * TYPES:
 * - Array<T>: Generic array
 * - IntArray, DoubleArray, etc: Primitive type arrays (more efficient)
 * 
 * CREATION:
 * - arrayOf(): Create array with elements
 * - Array(size) { init }: Create with size and initializer
 * - emptyArray(): Create empty array
 * 
 * ============================================================================
 */

package basics.syntax

class ArrayString {
    
    /**
     * ARRAY CREATION
     */
    fun arrayCreationExample() {
        println("=== Array Creation ===")
        
        // Using arrayOf
        val numbers = arrayOf(1, 2, 3, 4, 5)
        println("arrayOf: ${numbers.contentToString()}")
        
        // Using IntArray (more efficient for primitives)
        val intArray = intArrayOf(1, 2, 3, 4, 5)
        println("intArrayOf: ${intArray.contentToString()}")
        
        // Using constructor with size
        val zeros = IntArray(5)  // [0, 0, 0, 0, 0]
        println("IntArray(5): ${zeros.contentToString()}")
        
        // Using constructor with initializer
        val squares = IntArray(5) { it * it }  // [0, 1, 4, 9, 16]
        println("Squares: ${squares.contentToString()}")
        
        // Array of nulls
        val nullableArray = arrayOfNulls<String>(3)
        println("Nulls: ${nullableArray.contentToString()}")
        
        println()
    }
    
    /**
     * ARRAY ACCESS AND MODIFICATION
     */
    fun arrayAccessExample() {
        println("=== Array Access ===")
        
        val fruits = arrayOf("Apple", "Banana", "Orange", "Mango")
        
        // Access elements
        println("First: ${fruits[0]}")
        println("Last: ${fruits[fruits.size - 1]}")
        println("Last (alternative): ${fruits.last()}")
        
        // Modify elements
        fruits[1] = "Blueberry"
        println("Modified: ${fruits.contentToString()}")
        
        // Get with default
        println("Get or else: ${fruits.getOrElse(10) { "Not found" }}")
        
        println()
    }
    
    /**
     * ARRAY ITERATION
     */
    fun arrayIterationExample() {
        println("=== Array Iteration ===")
        
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        
        // For loop
        print("For loop: ")
        for (num in numbers) {
            print("$num ")
        }
        println()
        
        // For with index
        print("With index: ")
        for (i in numbers.indices) {
            print("${numbers[i]} ")
        }
        println()
        
        // forEach
        print("forEach: ")
        numbers.forEach { print("$it ") }
        println()
        
        // forEachIndexed
        print("forEachIndexed: ")
        numbers.forEachIndexed { index, value ->
            print("[$index]=$value ")
        }
        println("\n")
    }
    
    /**
     * ARRAY OPERATIONS
     */
    fun arrayOperationsExample() {
        println("=== Array Operations ===")
        
        val numbers = intArrayOf(1, 2, 3, 4, 5)
        
        // Sum, average, min, max
        println("Sum: ${numbers.sum()}")
        println("Average: ${numbers.average()}")
        println("Max: ${numbers.maxOrNull()}")
        println("Min: ${numbers.minOrNull()}")
        
        // Filter
        val evens = numbers.filter { it % 2 == 0 }
        println("Evens: $evens")
        
        // Map
        val doubled = numbers.map { it * 2 }
        println("Doubled: $doubled")
        
        // Sort
        val unsorted = intArrayOf(5, 2, 8, 1, 9)
        unsorted.sort()
        println("Sorted: ${unsorted.contentToString()}")
        
        // Reverse
        val reversed = numbers.reversedArray()
        println("Reversed: ${reversed.contentToString()}")
        
        println()
    }
    
    /**
     * MULTI-DIMENSIONAL ARRAYS
     */
    fun multiDimensionalArrayExample() {
        println("=== Multi-Dimensional Arrays ===")
        
        // 2D array
        val matrix = arrayOf(
            intArrayOf(1, 2, 3),
            intArrayOf(4, 5, 6),
            intArrayOf(7, 8, 9)
        )
        
        println("Matrix:")
        for (row in matrix) {
            println(row.contentToString())
        }
        
        // Access element
        println("Element [1][1]: ${matrix[1][1]}")
        
        // Create 2D array with initializer
        val grid = Array(3) { IntArray(3) { 0 } }
        println("\n3x3 Grid: ")
        grid.forEach { println(it.contentToString()) }
        
        println()
    }
    
    /**
     * STRING BASICS
     */
    fun stringBasicsExample() {
        println("=== String Basics ===")
        
        // String creation
        val str1 = "Hello, Kotlin!"
        val str2 = String(charArrayOf('H', 'i'))
        
        println("String 1: $str1")
        println("String 2: $str2")
        
        // String properties
        println("Length: ${str1.length}")
        println("Empty: ${str1.isEmpty()}")
        println("Blank: ${str1.isBlank()}")
        
        // String immutability
        val original = "Hello"
        val modified = original.uppercase()  // Creates new string
        println("Original: $original")
        println("Modified: $modified")
        
        println()
    }
    
    /**
     * STRING OPERATIONS
     */
    fun stringOperationsExample() {
        println("=== String Operations ===")
        
        val text = "Kotlin Programming"
        
        // Case conversion
        println("Uppercase: ${text.uppercase()}")
        println("Lowercase: ${text.lowercase()}")
        
        // Substring
        println("Substring(0, 6): ${text.substring(0, 6)}")
        
        // Replace
        println("Replace: ${text.replace("Kotlin", "Java")}")
        
        // Split
        val parts = text.split(" ")
        println("Split: $parts")
        
        // Trim
        val padded = "  Hello  "
        println("Trimmed: '${padded.trim()}'")
        
        // Contains
        println("Contains 'Program': ${text.contains("Program")}")
        
        // StartsWith, EndsWith
        println("Starts with 'Kotlin': ${text.startsWith("Kotlin")}")
        println("Ends with 'ing': ${text.endsWith("ing")}")
        
        println()
    }
    
    /**
     * STRING TEMPLATES
     */
    fun stringTemplatesExample() {
        println("=== String Templates ===")
        
        val name = "Alice"
        val age = 25
        
        // Simple template
        println("Name: $name, Age: $age")
        
        // Expression template
        println("Next year: ${age + 1}")
        
        // Function call in template
        println("Name length: ${name.length}")
        
        // Complex expression
        val scores = intArrayOf(85, 90, 78)
        println("Average: ${scores.average()}")
        
        // Raw string with templates
        val json = """
            {
                "name": "$name",
                "age": $age
            }
        """.trimIndent()
        println("JSON:\n$json")
        
        println()
    }
    
    /**
     * STRING MANIPULATION
     */
    fun stringManipulationExample() {
        println("=== String Manipulation ===")
        
        // Concatenation
        val hello = "Hello"
        val world = "World"
        println("Concat: ${hello + " " + world}")
        println("Join: $hello $world")
        
        // StringBuilder (for efficient concatenation)
        val builder = StringBuilder()
        builder.append("Kotlin")
        builder.append(" is")
        builder.append(" awesome")
        println("StringBuilder: $builder")
        
        // Repeat
        println("Repeat: ${"*".repeat(10)}")
        
        // Pad
        println("Pad left: ${"42".padStart(5, '0')}")
        println("Pad right: ${"42".padEnd(5, '0')}")
        
        println()
    }
    
    /**
     * CHARACTER OPERATIONS
     */
    fun characterOperationsExample() {
        println("=== Character Operations ===")
        
        val text = "Hello123"
        
        // Iterate characters
        print("Characters: ")
        for (char in text) {
            print("$char ")
        }
        println()
        
        // Character checks
        val ch = 'A'
        println("\nCharacter '$ch':")
        println("Is letter: ${ch.isLetter()}")
        println("Is digit: ${ch.isDigit()}")
        println("Is uppercase: ${ch.isUpperCase()}")
        println("To lowercase: ${ch.lowercase()}")
        
        // String to char array
        val chars = text.toCharArray()
        println("\nChar array: ${chars.contentToString()}")
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: Reverse array
        fun reverseArray(arr: IntArray): IntArray {
            val result = IntArray(arr.size)
            for (i in arr.indices) {
                result[i] = arr[arr.size - 1 - i]
            }
            return result
        }
        val original = intArrayOf(1, 2, 3, 4, 5)
        println("Original: ${original.contentToString()}")
        println("Reversed: ${reverseArray(original).contentToString()}")
        
        // Example 2: Palindrome check
        fun isPalindrome(str: String): Boolean {
            val cleaned = str.lowercase().replace(" ", "")
            return cleaned == cleaned.reversed()
        }
        val word = "racecar"
        println("\n'$word' is palindrome: ${isPalindrome(word)}")
        
        // Example 3: Word count
        fun wordCount(text: String): Int {
            return text.trim().split("\\s+".toRegex()).size
        }
        val sentence = "The quick brown fox jumps"
        println("\nWord count in '$sentence': ${wordCount(sentence)}")
        
        // Example 4: Anagram check
        fun areAnagrams(str1: String, str2: String): Boolean {
            val s1 = str1.lowercase().replace(" ", "").toCharArray().sorted()
            val s2 = str2.lowercase().replace(" ", "").toCharArray().sorted()
            return s1 == s2
        }
        println("\n'listen' and 'silent' are anagrams: ${areAnagrams("listen", "silent")}")
        
        println()
    }
}

/**
 * ============================================================================
 * COMMON PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Find element in array
 * val index = array.indexOf(element)
 * val contains = element in array
 * 
 * PATTERN 2: Remove duplicates
 * val unique = array.distinct()
 * 
 * PATTERN 3: Count occurrences
 * val count = array.count { it == value }
 * 
 * PATTERN 4: Merge arrays
 * val merged = array1 + array2
 * 
 * PATTERN 5: String reversal
 * val reversed = str.reversed()
 * 
 * PATTERN 6: Remove spaces
 * val noSpaces = str.replace(" ", "")
 * 
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * ARRAYS:
 * 1. Use IntArray, DoubleArray for primitives (more efficient)
 * 2. Prefer List over Array for most cases (more functional)
 * 3. Use contentToString() for printing arrays
 * 4. Use contentEquals() for comparing arrays
 * 5. Consider Collections (List, Set, Map) instead of arrays
 * 
 * STRINGS:
 * 1. Strings are immutable - operations create new strings
 * 2. Use StringBuilder for multiple concatenations
 * 3. Use string templates ($var) instead of concatenation
 * 4. Use raw strings (""") for multi-line text
 * 5. Remember: == compares content, === compares reference
 * 
 * ============================================================================
 */

fun main() {
    val demo = ArrayString()
    
    println("=== Kotlin Arrays and Strings ===\n")
    
    demo.arrayCreationExample()
    demo.arrayAccessExample()
    demo.arrayIterationExample()
    demo.arrayOperationsExample()
    demo.multiDimensionalArrayExample()
    demo.stringBasicsExample()
    demo.stringOperationsExample()
    demo.stringTemplatesExample()
    demo.stringManipulationExample()
    demo.characterOperationsExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ Arrays: Fixed size, efficient, mutable")
    println("✓ Strings: Immutable, rich operations")
    println("✓ Use IntArray/DoubleArray for primitives")
    println("✓ String templates ($) for interpolation")
    println("✓ Many built-in operations available")
}
