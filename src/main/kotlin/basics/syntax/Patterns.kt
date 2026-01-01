/**
 * ============================================================================
 * TOPIC: Pattern Printing in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * Pattern printing is a classic programming exercise that helps understand
 * loops, nested loops, and logic building. It's commonly asked in interviews
 * and helps develop problem-solving skills.
 * 
 * KEY CONCEPTS:
 * 1. Nested loops for rows and columns
 * 2. Understanding row-column relationships
 * 3. Star patterns
 * 4. Number patterns
 * 5. Character patterns
 * 6. Pyramid and triangle patterns
 * 
 * ============================================================================
 * PATTERN APPROACH
 * ============================================================================
 * 
 * STEPS:
 * 1. Identify number of rows
 * 2. Identify items per row (often depends on row number)
 * 3. Identify spaces needed (for triangles/pyramids)
 * 4. Use nested loops: outer for rows, inner for columns
 * 5. Print character/number/space based on position
 * 
 * ============================================================================
 */

package basics.syntax

class Patterns {
    
    /**
     * SQUARE PATTERNS
     */
    fun squarePatterns() {
        println("=== Square Patterns ===")
        
        // Pattern 1: Solid square
        println("Pattern 1: Solid Square")
        val n1 = 5
        for (i in 1..n1) {
            for (j in 1..n1) {
                print("* ")
            }
            println()
        }
        
        // Pattern 2: Hollow square
        println("\nPattern 2: Hollow Square")
        val n2 = 5
        for (i in 1..n2) {
            for (j in 1..n2) {
                if (i == 1 || i == n2 || j == 1 || j == n2) {
                    print("* ")
                } else {
                    print("  ")
                }
            }
            println()
        }
        
        println()
    }
    
    /**
     * TRIANGLE PATTERNS
     */
    fun trianglePatterns() {
        println("=== Triangle Patterns ===")
        
        // Pattern 1: Right-aligned triangle
        println("Pattern 1: Right-Aligned Triangle")
        val n1 = 5
        for (i in 1..n1) {
            for (j in 1..i) {
                print("* ")
            }
            println()
        }
        
        // Pattern 2: Inverted right-aligned triangle
        println("\nPattern 2: Inverted Triangle")
        val n2 = 5
        for (i in n2 downTo 1) {
            for (j in 1..i) {
                print("* ")
            }
            println()
        }
        
        // Pattern 3: Left-aligned triangle
        println("\nPattern 3: Left-Aligned Triangle")
        val n3 = 5
        for (i in 1..n3) {
            // Print spaces
            for (j in 1..(n3 - i)) {
                print("  ")
            }
            // Print stars
            for (j in 1..i) {
                print("* ")
            }
            println()
        }
        
        println()
    }
    
    /**
     * PYRAMID PATTERNS
     */
    fun pyramidPatterns() {
        println("=== Pyramid Patterns ===")
        
        // Pattern 1: Full pyramid
        println("Pattern 1: Full Pyramid")
        val n1 = 5
        for (i in 1..n1) {
            // Print spaces
            for (j in 1..(n1 - i)) {
                print(" ")
            }
            // Print stars
            for (j in 1..(2 * i - 1)) {
                print("*")
            }
            println()
        }
        
        // Pattern 2: Inverted pyramid
        println("\nPattern 2: Inverted Pyramid")
        val n2 = 5
        for (i in n2 downTo 1) {
            // Print spaces
            for (j in 1..(n2 - i)) {
                print(" ")
            }
            // Print stars
            for (j in 1..(2 * i - 1)) {
                print("*")
            }
            println()
        }
        
        // Pattern 3: Diamond
        println("\nPattern 3: Diamond")
        val n3 = 5
        // Upper half
        for (i in 1..n3) {
            for (j in 1..(n3 - i)) print(" ")
            for (j in 1..(2 * i - 1)) print("*")
            println()
        }
        // Lower half
        for (i in (n3 - 1) downTo 1) {
            for (j in 1..(n3 - i)) print(" ")
            for (j in 1..(2 * i - 1)) print("*")
            println()
        }
        
        println()
    }
    
    /**
     * NUMBER PATTERNS
     */
    fun numberPatterns() {
        println("=== Number Patterns ===")
        
        // Pattern 1: Sequential numbers
        println("Pattern 1: Sequential Numbers")
        val n1 = 5
        for (i in 1..n1) {
            for (j in 1..i) {
                print("$j ")
            }
            println()
        }
        
        // Pattern 2: Same number in row
        println("\nPattern 2: Same Number Per Row")
        val n2 = 5
        for (i in 1..n2) {
            for (j in 1..i) {
                print("$i ")
            }
            println()
        }
        
        // Pattern 3: Continuous numbers
        println("\nPattern 3: Continuous Numbers")
        val n3 = 5
        var num = 1
        for (i in 1..n3) {
            for (j in 1..i) {
                print("$num ")
                num++
            }
            println()
        }
        
        // Pattern 4: Number pyramid
        println("\nPattern 4: Number Pyramid")
        val n4 = 5
        for (i in 1..n4) {
            for (j in 1..(n4 - i)) print(" ")
            for (j in 1..i) print("$j")
            for (j in (i - 1) downTo 1) print("$j")
            println()
        }
        
        println()
    }
    
    /**
     * CHARACTER PATTERNS
     */
    fun characterPatterns() {
        println("=== Character Patterns ===")
        
        // Pattern 1: Sequential alphabets
        println("Pattern 1: Sequential Alphabets")
        val n1 = 5
        for (i in 1..n1) {
            var ch = 'A'
            for (j in 1..i) {
                print("$ch ")
                ch++
            }
            println()
        }
        
        // Pattern 2: Same char per row
        println("\nPattern 2: Same Char Per Row")
        val n2 = 5
        for (i in 1..n2) {
            val ch = ('A' + i - 1)
            for (j in 1..i) {
                print("$ch ")
            }
            println()
        }
        
        // Pattern 3: Character pyramid
        println("\nPattern 3: Character Pyramid")
        val n3 = 5
        for (i in 1..n3) {
            for (j in 1..(n3 - i)) print(" ")
            var ch = 'A'
            for (j in 1..i) {
                print("$ch")
                ch++
            }
            ch -= 2
            for (j in 1 until i) {
                print("$ch")
                ch--
            }
            println()
        }
        
        println()
    }
    
    /**
     * SPECIAL PATTERNS
     */
    fun specialPatterns() {
        println("=== Special Patterns ===")
        
        // Pattern 1: Pascal's Triangle
        println("Pattern 1: Pascal's Triangle")
        val n1 = 5
        for (i in 0 until n1) {
            // Print spaces
            for (j in 0 until (n1 - i)) print(" ")
            
            // Print numbers
            var value = 1
            for (j in 0..i) {
                print("$value ")
                value = value * (i - j) / (j + 1)
            }
            println()
        }
        
        // Pattern 2: Floyd's Triangle
        println("\nPattern 2: Floyd's Triangle")
        val n2 = 5
        var num = 1
        for (i in 1..n2) {
            for (j in 1..i) {
                print("$num ")
                num++
            }
            println()
        }
        
        // Pattern 3: Binary pattern
        println("\nPattern 3: Binary Pattern")
        val n3 = 5
        for (i in 1..n3) {
            for (j in 1..i) {
                print("${(i + j) % 2} ")
            }
            println()
        }
        
        // Pattern 4: Butterfly
        println("\nPattern 4: Butterfly")
        val n4 = 5
        // Upper half
        for (i in 1..n4) {
            // Left stars
            for (j in 1..i) print("*")
            // Spaces
            for (j in 1..(2 * (n4 - i))) print(" ")
            // Right stars
            for (j in 1..i) print("*")
            println()
        }
        // Lower half
        for (i in n4 downTo 1) {
            for (j in 1..i) print("*")
            for (j in 1..(2 * (n4 - i))) print(" ")
            for (j in 1..i) print("*")
            println()
        }
        
        println()
    }
    
    /**
     * ADVANCED PATTERNS
     */
    fun advancedPatterns() {
        println("=== Advanced Patterns ===")
        
        // Pattern 1: Hollow diamond
        println("Pattern 1: Hollow Diamond")
        val n1 = 5
        // Upper half
        for (i in 1..n1) {
            for (j in 1..(n1 - i)) print(" ")
            for (j in 1..(2 * i - 1)) {
                if (j == 1 || j == (2 * i - 1)) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
        // Lower half
        for (i in (n1 - 1) downTo 1) {
            for (j in 1..(n1 - i)) print(" ")
            for (j in 1..(2 * i - 1)) {
                if (j == 1 || j == (2 * i - 1)) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
        
        // Pattern 2: Zigzag
        println("\nPattern 2: Zigzag")
        val rows = 3
        val cols = 20
        for (i in 1..rows) {
            for (j in 1..cols) {
                if ((i + j) % 4 == 0 || (i == 2 && j % 4 == 0)) {
                    print("*")
                } else {
                    print(" ")
                }
            }
            println()
        }
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Generate any triangle pattern
        fun printTriangle(n: Int, char: Char = '*') {
            for (i in 1..n) {
                for (j in 1..i) {
                    print("$char ")
                }
                println()
            }
        }
        
        // Generate pyramid pattern
        fun printPyramid(n: Int, char: Char = '*') {
            for (i in 1..n) {
                for (j in 1..(n - i)) print(" ")
                for (j in 1..(2 * i - 1)) print(char)
                println()
            }
        }
        
        println("Triangle with #:")
        printTriangle(4, '#')
        
        println("\nPyramid with @:")
        printPyramid(4, '@')
        
        println()
    }
}

/**
 * ============================================================================
 * PATTERN SOLVING TIPS
 * ============================================================================
 * 
 * 1. IDENTIFY STRUCTURE:
 *    - How many rows?
 *    - How many items per row?
 *    - Relationship between row and items?
 * 
 * 2. SPACES NEEDED:
 *    - Left padding: (n - i) spaces
 *    - Right padding: depends on pattern
 * 
 * 3. COMMON FORMULAS:
 *    - Items in row i: i (triangle)
 *    - Items in row i: (2*i - 1) (pyramid)
 *    - Spaces before row i: (n - i)
 * 
 * 4. LOOP STRUCTURE:
 *    ```
 *    for row in 1..n:
 *        for spaces
 *        for content
 *        newline
 *    ```
 * 
 * 5. TESTING:
 *    - Start with small n (3-5)
 *    - Draw pattern on paper first
 *    - Identify pattern in each row
 * 
 * ============================================================================
 * COMMON PATTERNS AT A GLANCE
 * ============================================================================
 * 
 * RIGHT TRIANGLE:
 * *
 * * *
 * * * *
 * 
 * PYRAMID:
 *   *
 *  ***
 * *****
 * 
 * DIAMOND:
 *   *
 *  ***
 * *****
 *  ***
 *   *
 * 
 * SQUARE:
 * * * * *
 * * * * *
 * * * * *
 * * * * *
 * 
 * HOLLOW SQUARE:
 * * * * *
 * *     *
 * *     *
 * * * * *
 * 
 * ============================================================================
 */

fun main() {
    val demo = Patterns()
    
    println("=== Kotlin Pattern Printing ===\n")
    
    demo.squarePatterns()
    demo.trianglePatterns()
    demo.pyramidPatterns()
    demo.numberPatterns()
    demo.characterPatterns()
    demo.specialPatterns()
    demo.advancedPatterns()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ Use nested loops: outer for rows, inner for columns")
    println("✓ Identify spaces needed (n - i) for alignment")
    println("✓ Row-column relationship determines pattern")
    println("✓ Practice with small n first")
    println("✓ Draw pattern before coding")
}
