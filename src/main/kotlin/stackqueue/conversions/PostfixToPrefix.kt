/**
 * ============================================================================
 * PROBLEM:  Postfix to Prefix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Convert a postfix expression (Reverse Polish Notation) to prefix notation
 * (Polish Notation).
 * 
 * NOTATIONS:
 * 
 * Postfix:  Operator after operands
 * - Example: AB+, ABC*+
 * - Evaluated left to right
 * 
 * Prefix: Operator before operands
 * - Example: +AB, +A*BC
 * - Evaluated right to left
 * 
 * EXAMPLES:
 * - "AB+" → "+AB"
 * - "ABC*+" → "+A*BC"
 * - "AB+C*" → "*+ABC"
 * - "AB*CD+/" → "/*AB+CD"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY INSIGHT:
 * Similar to postfix to infix, but build prefix expressions instead! 
 * 
 * ALGORITHM: 
 * 1. Create empty stack for operands/expressions
 * 2. Scan postfix left to right: 
 *    a. If operand: Push to stack
 *    b.  If operator: 
 *       - Pop two operands (op2, then op1)
 *       - Create prefix:  "operator op1 op2"
 *       - Push result back to stack
 * 3. Final stack top is the prefix expression
 * 
 * DIFFERENCE FROM POSTFIX TO INFIX:
 * - Infix: "(op1 operator op2)" - operator in middle, needs parentheses
 * - Prefix: "operator op1 op2" - operator first, no parentheses needed
 * 
 * VISUAL EXAMPLE:  "AB+C*"
 * 
 * Input: A B + C *
 * 
 * Step 1: 'A' (operand)
 * Stack: ["A"]
 * 
 * Step 2: 'B' (operand)
 * Stack: ["A", "B"]
 * 
 * Step 3: '+' (operator)
 * - Pop "B", pop "A"
 * - Create "+AB" (operator first!)
 * - Push "+AB"
 * Stack: ["+AB"]
 * 
 * Step 4: 'C' (operand)
 * Stack: ["+AB", "C"]
 * 
 * Step 5: '*' (operator)
 * - Pop "C", pop "+AB"
 * - Create "*+ABC" (operator first!)
 * - Push "*+ABC"
 * Stack: ["*+ABC"]
 * 
 * Result: "*+ABC"
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - n = length of postfix expression
 * - Each character processed once
 * - Stack operations: O(1)
 * - String concatenation: O(1) per operation
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack stores intermediate expressions
 * - Worst case: All operands → O(n)
 * - String building: O(n)
 * - Total: O(n)
 * 
 * ============================================================================
 */

package stackqueue.conversions

import java.util.Stack

/**
 * Postfix to Prefix converter
 */
class PostfixToPrefix {
    
    /**
     * Check if character is an operand
     */
    private fun isOperand(char: Char): Boolean {
        return char.isLetterOrDigit()
    }
    
    /**
     * Check if character is an operator
     */
    private fun isOperator(char: Char): Boolean {
        return char in "+-*/^"
    }
    
    /**
     * Convert postfix expression to prefix
     * TIME:  O(n), SPACE: O(n)
     * 
     * @param postfix The postfix expression
     * @return Prefix expression
     */
    fun convert(postfix: String): String {
        // Remove spaces
        val expression = postfix.replace(" ", "")
        
        // Stack to hold operands and sub-expressions
        val stack = Stack<String>()
        
        // Process each character from left to right
        for (char in expression) {
            when {
                // If operand, push to stack as string
                isOperand(char) -> {
                    stack.push(char.toString())
                }
                
                // If operator, pop two operands and create prefix
                isOperator(char) -> {
                    // Check if stack has at least 2 operands
                    if (stack. size < 2) {
                        throw IllegalArgumentException("Invalid postfix expression")
                    }
                    
                    // Pop two operands
                    // Note: Second pop is the first operand! 
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    
                    // Create prefix expression:  operator comes first
                    // Format: "operator operand1 operand2"
                    val prefixExpr = "$char$operand1$operand2"
                    
                    // Push back to stack
                    stack.push(prefixExpr)
                }
            }
        }
        
        // Stack should have exactly one element (the final expression)
        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid postfix expression")
        }
        
        return stack.pop()
    }
    
    /**
     * Verbose version with step-by-step trace
     */
    fun convertVerbose(postfix: String): String {
        println("Converting postfix to prefix:  \"$postfix\"")
        println("=". repeat(60))
        
        val expression = postfix.replace(" ", "")
        val stack = Stack<String>()
        
        for ((index, char) in expression.withIndex()) {
            print("Step ${index + 1}: '$char' → ")
            
            when {
                isOperand(char) -> {
                    stack.push(char. toString())
                    println("Operand, push '$char'")
                }
                
                isOperator(char) -> {
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    val prefixExpr = "$char$operand1$operand2"
                    stack.push(prefixExpr)
                    println("Operator, pop '$operand2' and '$operand1', push '$prefixExpr'")
                }
            }
            
            println("   Stack: $stack")
        }
        
        val result = stack.pop()
        println("=".repeat(60))
        println("Result: \"$result\"")
        return result
    }
    
    /**
     * Convert with spaces for readability
     */
    fun convertWithSpaces(postfix:  String): String {
        val expression = postfix.replace(" ", "")
        val stack = Stack<String>()
        
        for (char in expression) {
            when {
                isOperand(char) -> {
                    stack. push(char.toString())
                }
                
                isOperator(char) -> {
                    if (stack.size < 2) {
                        throw IllegalArgumentException("Invalid postfix expression")
                    }
                    
                    val operand2 = stack. pop()
                    val operand1 = stack.pop()
                    
                    // Add spaces between operator and operands
                    val prefixExpr = "$char $operand1 $operand2"
                    
                    stack.push(prefixExpr)
                }
            }
        }
        
        return stack.pop()
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGHS
 * ============================================================================
 * 
 * Example 1: "AB+C*"
 * 
 * Char | Type     | Action                      | Stack
 * -----|----------|-----------------------------|-----------------
 * 'A'  | Operand  | Push "A"                    | ["A"]
 * 'B'  | Operand  | Push "B"                    | ["A", "B"]
 * '+'  | Operator | Pop B, A, push "+AB"        | ["+AB"]
 * 'C'  | Operand  | Push "C"                    | ["+AB", "C"]
 * '*'  | Operator | Pop C, +AB, push "*+ABC"    | ["*+ABC"]
 * 
 * Result: "*+ABC"
 * 
 * Verification:  * + A B C means (A + B) * C ✓
 * 
 * Example 2: "ABC*+"
 * 
 * Char | Type     | Action                      | Stack
 * -----|----------|-----------------------------|-----------------------
 * 'A'  | Operand  | Push "A"                    | ["A"]
 * 'B'  | Operand  | Push "B"                    | ["A", "B"]
 * 'C'  | Operand  | Push "C"                    | ["A", "B", "C"]
 * '*'  | Operator | Pop C, B, push "*BC"        | ["A", "*BC"]
 * '+'  | Operator | Pop *BC, A, push "+A*BC"    | ["+A*BC"]
 * 
 * Result: "+A*BC"
 * 
 * Verification: + A * B C means A + (B * C) ✓
 * 
 * Example 3: "AB*CD+/"
 * 
 * Char | Type     | Action                      | Stack
 * -----|----------|-----------------------------|-----------------------
 * 'A'  | Operand  | Push "A"                    | ["A"]
 * 'B'  | Operand  | Push "B"                    | ["A", "B"]
 * '*'  | Operator | Pop B, A, push "*AB"        | ["*AB"]
 * 'C'  | Operand  | Push "C"                    | ["*AB", "C"]
 * 'D'  | Operand  | Push "D"                    | ["*AB", "C", "D"]
 * '+'  | Operator | Pop D, C, push "+CD"        | ["*AB", "+CD"]
 * '/'  | Operator | Pop +CD, *AB, push "/*AB+CD"| ["/*AB+CD"]
 * 
 * Result: "/*AB+CD"
 * 
 * Verification: / * A B + C D means (A * B) / (C + D) ✓
 * 
 * ============================================================================
 * KEY DIFFERENCES:  POSTFIX → PREFIX vs POSTFIX → INFIX
 * ============================================================================
 * 
 * Operation:  Combining operands with operator
 * 
 * POSTFIX TO INFIX:
 * - Format: "(operand1 operator operand2)"
 * - Needs parentheses
 * - Operator in middle
 * - Example: "(A+B)"
 * 
 * POSTFIX TO PREFIX:
 * - Format: "operator operand1 operand2"
 * - No parentheses needed
 * - Operator first
 * - Example: "+AB"
 * 
 * WHY NO PARENTHESES IN PREFIX?
 * - Operator position determines precedence
 * - Reading right to left gives clear order
 * - Unambiguous without parentheses
 * 
 * ============================================================================
 * COMPARISON OF ALL THREE NOTATIONS
 * ============================================================================
 * 
 * Expression     | Infix       | Prefix     | Postfix
 * ---------------|-------------|------------|------------
 * Simple         | A + B       | + A B      | A B +
 * Precedence     | A + B * C   | + A * B C  | A B C * +
 * Grouping       | (A + B) * C | * + A B C  | A B + C *
 * Complex        | A * (B + C) | * A + B C  | A B C + *
 * Division       | A/B + C/D   | + / A B / C D | A B / C D / +
 * Power          | A ^ B ^ C   | ^ A ^ B C  | A B C ^ ^
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Wrong Operand Order:
 *    Wrong: val prefixExpr = "$char$operand2$operand1"
 *    Right: val prefixExpr = "$char$operand1$operand2"
 * 
 * 2. Adding Parentheses (unnecessary):
 *    Wrong: val prefixExpr = "($char$operand1$operand2)"
 *    Right: val prefixExpr = "$char$operand1$operand2"
 * 
 * 3. Wrong Pop Order:
 *    - First pop = second operand
 *    - Second pop = first operand
 *    - Critical for non-commutative operators
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Postfix to Prefix Conversion ===\n")
    
    val converter = PostfixToPrefix()
    
    // Test cases
    val testCases = listOf(
        "AB+" to "+AB",
        "ABC*+" to "+A*BC",
        "AB+C*" to "*+ABC",
        "ABC+*" to "*A+BC",
        "AB*CD+/" to "/*AB+CD",
        "ABC^^" to "^A^BC",
        "AB+CD-*" to "*+AB-CD",
        "ABC*+DE/-" to "-+A*BC/DE",
        "AB*C+D/" to "/+*ABCD",
        "ABCD+*+E*" to "*+A*B+CDE"
    )
    
    println("Test 1: Basic Conversions")
    testCases.forEach { (postfix, expected) ->
        val result = converter.convert(postfix)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$postfix\" → \"$result\"")
        if (result != expected) {
            println("   Expected: \"$expected\"")
        }
    }
    println()
    
    // Test 2: Verbose mode
    println("Test 2: Verbose Conversion")
    converter.convertVerbose("AB+C*")
    println()
    
    converter.convertVerbose("ABC*+")
    println()
    
    // Test 3: With spaces for readability
    println("Test 3: Conversions with Spaces")
    val expressions = listOf("AB+", "ABC*+", "AB+C*")
    expressions.forEach { postfix ->
        val prefix = converter.convertWithSpaces(postfix)
        println("\"$postfix\" → \"$prefix\"")
    }
    println()
    
    // Test 4: Complex expressions
    println("Test 4: Complex Expressions")
    val complexExpressions = listOf(
        "AB+CD-*EF+/",
        "ABC*+D-EF+/",
        "ABCD+*+E*"
    )
    
    complexExpressions.forEach { postfix ->
        val prefix = converter.convert(postfix)
        val prefixSpaced = converter.convertWithSpaces(postfix)
        println("Postfix: \"$postfix\"")
        println("Prefix:   \"$prefix\"")
        println("Spaced:  \"$prefixSpaced\"")
        println()
    }
    
    // Test 5: Error handling
    println("Test 5: Error Handling")
    val invalidExpressions = listOf(
        "AB++",
        "ABC",
        "+AB"
    )
    
    invalidExpressions.forEach { postfix ->
        try {
            val result = converter.convert(postfix)
            println("✗ \"$postfix\" → \"$result\" (should have failed)")
        } catch (e: Exception) {
            println("✓ \"$postfix\" → Error: ${e.message}")
        }
    }
    println()
    
    // Test 6: Comparison table
    println("Test 6: Three-Way Comparison")
    val infixExamples = listOf("A+B", "(A+B)*C", "A+B*C")
    val postfixVersions = listOf("AB+", "AB+C*", "ABC*+")
    
    println("Infix          | Prefix      | Postfix")
    println("---------------|-------------|------------")
    
    infixExamples.zip(postfixVersions).forEach { (infix, postfix) ->
        val prefix = converter.convert(postfix)
        println("%-14s | %-11s | %s". format(infix, prefix, postfix))
    }
}
