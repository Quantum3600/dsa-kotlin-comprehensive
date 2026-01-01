/**
 * ============================================================================
 * TOPIC: If-Else Conditional Statements in Kotlin
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * If-else statements control the flow of execution based on conditions.
 * In Kotlin, if is an expression (returns a value), not just a statement.
 * 
 * KEY CONCEPTS:
 * 1. Basic if-else statements
 * 2. If as an expression
 * 3. If-else-if chains
 * 4. Nested if statements
 * 5. Comparison and logical operators
 * 
 * ============================================================================
 * SYNTAX
 * ============================================================================
 * 
 * BASIC IF:
 * if (condition) {
 *     // code
 * }
 * 
 * IF-ELSE:
 * if (condition) {
 *     // code
 * } else {
 *     // code
 * }
 * 
 * IF-ELSE-IF:
 * if (condition1) {
 *     // code
 * } else if (condition2) {
 *     // code
 * } else {
 *     // code
 * }
 * 
 * IF AS EXPRESSION:
 * val result = if (condition) value1 else value2
 * 
 * ============================================================================
 */

package basics.syntax

class IfElse {
    
    /**
     * BASIC IF STATEMENT
     */
    fun basicIfExample() {
        println("=== Basic If Statement ===")
        
        val age = 18
        
        if (age >= 18) {
            println("You are an adult")
        }
        
        val temperature = 25
        if (temperature > 30) {
            println("It's hot!")
        }
        
        println()
    }
    
    /**
     * IF-ELSE STATEMENT
     */
    fun ifElseExample() {
        println("=== If-Else Statement ===")
        
        val number = 10
        
        if (number > 0) {
            println("$number is positive")
        } else {
            println("$number is non-positive")
        }
        
        val isWeekend = false
        if (isWeekend) {
            println("Time to relax!")
        } else {
            println("Time to work!")
        }
        
        println()
    }
    
    /**
     * IF-ELSE-IF CHAINS
     */
    fun ifElseIfExample() {
        println("=== If-Else-If Chains ===")
        
        val score = 85
        
        if (score >= 90) {
            println("Grade: A")
        } else if (score >= 80) {
            println("Grade: B")
        } else if (score >= 70) {
            println("Grade: C")
        } else if (score >= 60) {
            println("Grade: D")
        } else {
            println("Grade: F")
        }
        
        // Temperature classification
        val temp = 22
        if (temp < 0) {
            println("Freezing")
        } else if (temp < 10) {
            println("Cold")
        } else if (temp < 20) {
            println("Cool")
        } else if (temp < 30) {
            println("Warm")
        } else {
            println("Hot")
        }
        
        println()
    }
    
    /**
     * IF AS EXPRESSION (returns value)
     */
    fun ifExpressionExample() {
        println("=== If as Expression ===")
        
        val a = 10
        val b = 20
        
        // Traditional
        val max = if (a > b) a else b
        println("Max of $a and $b: $max")
        
        // With blocks
        val result = if (a > b) {
            println("a is greater")
            a  // last expression is returned
        } else {
            println("b is greater or equal")
            b
        }
        println("Result: $result")
        
        // Inline
        val status = if (a == b) "equal" else "not equal"
        println("Status: $status")
        
        println()
    }
    
    /**
     * NESTED IF STATEMENTS
     */
    fun nestedIfExample() {
        println("=== Nested If Statements ===")
        
        val age = 25
        val hasLicense = true
        
        if (age >= 18) {
            if (hasLicense) {
                println("You can drive")
            } else {
                println("You need a license to drive")
            }
        } else {
            println("You are too young to drive")
        }
        
        // Login validation
        val username = "admin"
        val password = "pass123"
        
        if (username.isNotEmpty()) {
            if (password.isNotEmpty()) {
                if (username == "admin" && password == "pass123") {
                    println("Login successful")
                } else {
                    println("Invalid credentials")
                }
            } else {
                println("Password is required")
            }
        } else {
            println("Username is required")
        }
        
        println()
    }
    
    /**
     * LOGICAL OPERATORS
     */
    fun logicalOperatorsExample() {
        println("=== Logical Operators ===")
        
        val age = 25
        val hasTicket = true
        val isVIP = false
        
        // AND operator (&&)
        if (age >= 18 && hasTicket) {
            println("Entry allowed")
        }
        
        // OR operator (||)
        if (hasTicket || isVIP) {
            println("Can enter the venue")
        }
        
        // NOT operator (!)
        if (!isVIP) {
            println("Regular entry only")
        }
        
        // Complex conditions
        if ((age >= 18 && hasTicket) || isVIP) {
            println("Welcome!")
        }
        
        println()
    }
    
    /**
     * COMPARISON OPERATORS
     */
    fun comparisonOperatorsExample() {
        println("=== Comparison Operators ===")
        
        val x = 10
        val y = 20
        
        // Equal to
        if (x == y) println("x equals y") else println("x does not equal y")
        
        // Not equal to
        if (x != y) println("x not equal to y")
        
        // Greater than
        if (x > y) println("x > y") else println("x <= y")
        
        // Less than
        if (x < y) println("x < y")
        
        // Greater than or equal
        if (x >= 10) println("x >= 10")
        
        // Less than or equal
        if (y <= 20) println("y <= 20")
        
        println()
    }
    
    /**
     * RANGE CHECKS
     */
    fun rangeChecksExample() {
        println("=== Range Checks ===")
        
        val age = 25
        
        // Check if in range
        if (age in 18..65) {
            println("Working age")
        }
        
        // Check if not in range
        if (age !in 0..17) {
            println("Not a minor")
        }
        
        val score = 85
        if (score in 80..89) {
            println("Grade: B")
        }
        
        println()
    }
    
    /**
     * NULL CHECKS
     */
    fun nullChecksExample() {
        println("=== Null Checks ===")
        
        val name: String? = "Alice"
        
        // Null check
        if (name != null) {
            println("Name length: ${name.length}")
        }
        
        // Smart cast after null check
        if (name != null && name.isNotEmpty()) {
            println("Hello, $name!")
        }
        
        // Safe call with if
        val length = if (name != null) name.length else 0
        println("Length: $length")
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: Leap year check
        val year = 2024
        val isLeapYear = if (year % 400 == 0) {
            true
        } else if (year % 100 == 0) {
            false
        } else {
            year % 4 == 0
        }
        println("$year is ${if (isLeapYear) "a" else "not a"} leap year")
        
        // Example 2: Absolute value
        val num = -42
        val absolute = if (num < 0) -num else num
        println("Absolute value of $num: $absolute")
        
        // Example 3: Sign function
        val value = 15
        val sign = if (value > 0) {
            1
        } else if (value < 0) {
            -1
        } else {
            0
        }
        println("Sign of $value: $sign")
        
        // Example 4: Password strength
        val password = "MyP@ssw0rd"
        val strength = if (password.length < 6) {
            "Weak"
        } else if (password.length < 10) {
            "Medium"
        } else if (password.any { it.isUpperCase() } && 
                   password.any { it.isLowerCase() } &&
                   password.any { it.isDigit() }) {
            "Strong"
        } else {
            "Medium"
        }
        println("Password strength: $strength")
        
        println()
    }
}

/**
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * 1. Use if as expression when possible:
 *    ✓ val max = if (a > b) a else b
 *    ✗ var max = 0; if (a > b) max = a else max = b
 * 
 * 2. Keep conditions simple and readable:
 *    ✓ if (age >= 18 && hasLicense)
 *    ✗ if (!(age < 18 || !hasLicense))
 * 
 * 3. Use when for multiple conditions:
 *    If you have many else-if, consider using when instead
 * 
 * 4. Avoid deep nesting:
 *    Use early returns or combine conditions
 * 
 * 5. Use meaningful variable names in conditions:
 *    ✓ if (isLoggedIn && hasPermission)
 *    ✗ if (x && y)
 * 
 * ============================================================================
 * COMMON PATTERNS
 * ============================================================================
 * 
 * PATTERN 1: Max/Min
 * val max = if (a > b) a else b
 * val min = if (a < b) a else b
 * 
 * PATTERN 2: Default values
 * val result = if (value != null) value else defaultValue
 * 
 * PATTERN 3: Type checking
 * if (obj is String) {
 *     println(obj.length)  // Smart cast
 * }
 * 
 * PATTERN 4: Range validation
 * if (value in min..max) {
 *     // valid
 * }
 * 
 * ============================================================================
 */

fun main() {
    val demo = IfElse()
    
    println("=== Kotlin If-Else Statements ===\n")
    
    demo.basicIfExample()
    demo.ifElseExample()
    demo.ifElseIfExample()
    demo.ifExpressionExample()
    demo.nestedIfExample()
    demo.logicalOperatorsExample()
    demo.comparisonOperatorsExample()
    demo.rangeChecksExample()
    demo.nullChecksExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ if is an expression in Kotlin (returns value)")
    println("✓ No ternary operator needed (use if-else)")
    println("✓ Smart casts after type/null checks")
    println("✓ Use 'in' for range checks")
    println("✓ Combine with when for complex branching")
}
