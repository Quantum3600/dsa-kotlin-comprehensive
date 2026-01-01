/**
 * ============================================================================
 * TOPIC: When Expression in Kotlin (Switch Alternative)
 * DIFFICULTY: Beginner
 * CATEGORY: Kotlin Syntax Basics
 * ============================================================================
 * 
 * OVERVIEW:
 * The 'when' expression in Kotlin replaces the switch statement from Java.
 * It's more powerful and flexible, supporting various types of conditions.
 * Like if, when is also an expression that returns a value.
 * 
 * KEY CONCEPTS:
 * 1. Basic when statement
 * 2. When as expression
 * 3. Multiple conditions in one branch
 * 4. Range checks in when
 * 5. Type checks with when
 * 6. When without argument
 * 
 * ============================================================================
 * SYNTAX
 * ============================================================================
 * 
 * BASIC WHEN:
 * when (x) {
 *     1 -> println("One")
 *     2 -> println("Two")
 *     else -> println("Other")
 * }
 * 
 * WHEN AS EXPRESSION:
 * val result = when (x) {
 *     1 -> "One"
 *     2 -> "Two"
 *     else -> "Other"
 * }
 * 
 * MULTIPLE CONDITIONS:
 * when (x) {
 *     1, 2, 3 -> println("Small")
 *     else -> println("Large")
 * }
 * 
 * ============================================================================
 */

package basics.syntax

class Switch {
    
    /**
     * BASIC WHEN STATEMENT
     */
    fun basicWhenExample() {
        println("=== Basic When Statement ===")
        
        val day = 3
        
        when (day) {
            1 -> println("Monday")
            2 -> println("Tuesday")
            3 -> println("Wednesday")
            4 -> println("Thursday")
            5 -> println("Friday")
            6 -> println("Saturday")
            7 -> println("Sunday")
            else -> println("Invalid day")
        }
        
        println()
    }
    
    /**
     * WHEN AS EXPRESSION
     */
    fun whenExpressionExample() {
        println("=== When as Expression ===")
        
        val month = 12
        val season = when (month) {
            12, 1, 2 -> "Winter"
            3, 4, 5 -> "Spring"
            6, 7, 8 -> "Summer"
            9, 10, 11 -> "Fall"
            else -> "Unknown"
        }
        println("Month $month is in $season")
        
        // With blocks
        val grade = 85
        val feedback = when (grade) {
            in 90..100 -> {
                println("Excellent performance!")
                "A"
            }
            in 80..89 -> {
                println("Good job!")
                "B"
            }
            in 70..79 -> "C"
            in 60..69 -> "D"
            else -> "F"
        }
        println("Grade: $feedback")
        
        println()
    }
    
    /**
     * MULTIPLE CONDITIONS IN ONE BRANCH
     */
    fun multipleConditionsExample() {
        println("=== Multiple Conditions ===")
        
        val char = 'a'
        
        when (char) {
            'a', 'e', 'i', 'o', 'u' -> println("$char is a vowel")
            else -> println("$char is a consonant")
        }
        
        val number = 6
        when (number) {
            1, 3, 5, 7, 9 -> println("$number is odd")
            2, 4, 6, 8, 10 -> println("$number is even")
            else -> println("Number out of range")
        }
        
        println()
    }
    
    /**
     * RANGE CHECKS IN WHEN
     */
    fun rangeChecksExample() {
        println("=== Range Checks ===")
        
        val age = 25
        
        when (age) {
            in 0..12 -> println("Child")
            in 13..19 -> println("Teenager")
            in 20..59 -> println("Adult")
            in 60..120 -> println("Senior")
            else -> println("Invalid age")
        }
        
        val score = 85
        val grade = when (score) {
            in 90..100 -> "A"
            in 80..89 -> "B"
            in 70..79 -> "C"
            in 60..69 -> "D"
            in 0..59 -> "F"
            else -> "Invalid score"
        }
        println("Score $score = Grade $grade")
        
        println()
    }
    
    /**
     * TYPE CHECKS WITH WHEN
     */
    fun typeChecksExample() {
        println("=== Type Checks ===")
        
        val obj: Any = "Hello"
        
        when (obj) {
            is String -> println("String of length ${obj.length}")
            is Int -> println("Integer: $obj")
            is Double -> println("Double: $obj")
            is Boolean -> println("Boolean: $obj")
            else -> println("Unknown type")
        }
        
        // Multiple types
        val value: Any = 42
        val description = when (value) {
            is String, is Char -> "Text"
            is Int, is Long, is Short, is Byte -> "Integer number"
            is Float, is Double -> "Decimal number"
            is Boolean -> "Boolean value"
            else -> "Other type"
        }
        println("Type category: $description")
        
        println()
    }
    
    /**
     * WHEN WITHOUT ARGUMENT
     */
    fun whenWithoutArgumentExample() {
        println("=== When Without Argument ===")
        
        val x = 10
        val y = 20
        
        // Like if-else-if chain
        when {
            x > y -> println("x is greater")
            x < y -> println("y is greater")
            else -> println("x equals y")
        }
        
        // Complex conditions
        val age = 25
        val hasLicense = true
        
        when {
            age < 18 -> println("Too young to drive")
            age >= 18 && !hasLicense -> println("Need a license")
            age >= 18 && hasLicense -> println("Can drive")
            else -> println("Unknown status")
        }
        
        println()
    }
    
    /**
     * CHECKING MULTIPLE PROPERTIES
     */
    fun multiplePropertiesExample() {
        println("=== Multiple Properties ===")
        
        val temperature = 25
        val isRaining = false
        
        val weather = when {
            temperature < 0 && isRaining -> "Freezing rain"
            temperature < 0 && !isRaining -> "Freezing"
            temperature > 30 && isRaining -> "Hot rain"
            temperature > 30 && !isRaining -> "Hot"
            isRaining -> "Rainy"
            else -> "Nice weather"
        }
        println("Weather: $weather")
        
        println()
    }
    
    /**
     * WHEN WITH COLLECTIONS
     */
    fun whenWithCollectionsExample() {
        println("=== When with Collections ===")
        
        val numbers = listOf(1, 2, 3, 4, 5)
        val x = 3
        
        when (x) {
            in numbers -> println("$x is in the list")
            !in numbers -> println("$x is not in the list")
        }
        
        val colors = setOf("red", "green", "blue")
        val color = "yellow"
        
        val message = when (color) {
            in colors -> "Primary color"
            else -> "Not a primary color"
        }
        println("$color: $message")
        
        println()
    }
    
    /**
     * WHEN WITH ENUMS
     */
    enum class Direction { NORTH, SOUTH, EAST, WEST }
    
    fun whenWithEnumsExample() {
        println("=== When with Enums ===")
        
        val direction = Direction.NORTH
        
        val instruction = when (direction) {
            Direction.NORTH -> "Go forward"
            Direction.SOUTH -> "Go backward"
            Direction.EAST -> "Turn right"
            Direction.WEST -> "Turn left"
        }
        println("Direction $direction: $instruction")
        
        println()
    }
    
    /**
     * PRACTICAL EXAMPLES
     */
    fun practicalExamples() {
        println("=== Practical Examples ===")
        
        // Example 1: Day type classifier
        val day = "Monday"
        val dayType = when (day) {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Weekday"
            "Saturday", "Sunday" -> "Weekend"
            else -> "Invalid day"
        }
        println("$day is a $dayType")
        
        // Example 2: HTTP status code
        val statusCode = 404
        val statusMessage = when (statusCode) {
            200 -> "OK"
            201 -> "Created"
            400 -> "Bad Request"
            401 -> "Unauthorized"
            403 -> "Forbidden"
            404 -> "Not Found"
            500 -> "Internal Server Error"
            in 200..299 -> "Success"
            in 400..499 -> "Client Error"
            in 500..599 -> "Server Error"
            else -> "Unknown Status"
        }
        println("HTTP $statusCode: $statusMessage")
        
        // Example 3: Calculator
        val num1 = 10.0
        val num2 = 5.0
        val operator = '+'
        
        val result = when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> if (num2 != 0.0) num1 / num2 else Double.NaN
            '%' -> num1 % num2
            else -> Double.NaN
        }
        println("$num1 $operator $num2 = $result")
        
        // Example 4: File extension handler
        val fileName = "document.pdf"
        val fileType = when {
            fileName.endsWith(".jpg", true) || fileName.endsWith(".png", true) -> "Image"
            fileName.endsWith(".pdf", true) -> "PDF Document"
            fileName.endsWith(".txt", true) -> "Text File"
            fileName.endsWith(".mp3", true) || fileName.endsWith(".wav", true) -> "Audio"
            fileName.endsWith(".mp4", true) || fileName.endsWith(".avi", true) -> "Video"
            else -> "Unknown"
        }
        println("$fileName is a $fileType")
        
        println()
    }
}

/**
 * ============================================================================
 * WHEN vs SWITCH (Java)
 * ============================================================================
 * 
 * ADVANTAGES OF WHEN OVER SWITCH:
 * 
 * 1. Returns a value (expression)
 *    when: val result = when(x) { ... }
 *    switch: Cannot assign directly
 * 
 * 2. No break statements needed
 *    when: Automatic break after each branch
 *    switch: Must use break to avoid fall-through
 * 
 * 3. Multiple conditions per branch
 *    when: 1, 2, 3 -> "Small"
 *    switch: Need multiple case labels
 * 
 * 4. Range checks
 *    when: in 1..10 -> "Range"
 *    switch: Not supported
 * 
 * 5. Type checks
 *    when: is String -> ...
 *    switch: Not supported
 * 
 * 6. Can be used without argument
 *    when: when { x > 0 -> ... }
 *    switch: Always needs expression
 * 
 * 7. Any expression in branches
 *    when: x.length > 5 -> ...
 *    switch: Only constants
 * 
 * ============================================================================
 * BEST PRACTICES
 * ============================================================================
 * 
 * 1. Use when instead of long if-else-if chains
 * 2. Always include else branch for completeness
 * 3. For expressions, else is mandatory
 * 4. Use ranges when checking numeric ranges
 * 5. Group related conditions together
 * 6. Keep branches simple and readable
 * 7. Use when without argument for complex conditions
 * 
 * ============================================================================
 */

fun main() {
    val demo = Switch()
    
    println("=== Kotlin When Expression ===\n")
    
    demo.basicWhenExample()
    demo.whenExpressionExample()
    demo.multipleConditionsExample()
    demo.rangeChecksExample()
    demo.typeChecksExample()
    demo.whenWithoutArgumentExample()
    demo.multiplePropertiesExample()
    demo.whenWithCollectionsExample()
    demo.whenWithEnumsExample()
    demo.practicalExamples()
    
    println("=== Key Takeaways ===")
    println("✓ 'when' replaces 'switch' in Kotlin")
    println("✓ More powerful: ranges, types, expressions")
    println("✓ Can be used as expression (returns value)")
    println("✓ No break statements needed")
    println("✓ Can be used without argument (like if-else)")
    println("✓ Supports smart casts after type checks")
}
