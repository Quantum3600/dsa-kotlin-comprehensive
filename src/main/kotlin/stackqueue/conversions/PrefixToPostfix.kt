/**
 * ============================================================================
 * PROBLEM:  Prefix to Postfix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Convert a prefix expression (Polish Notation) to postfix notation
 * (Reverse Polish Notation).
 * 
 * NOTATIONS:
 * 
 * Prefix:  Operator before operands
 * - Example: +AB, *+ABC
 * - Evaluated right to left
 * 
 * Postfix: Operator after operands
 * - Example:  AB+, AB+C*
 * - Evaluated left to right
 * 
 * EXAMPLES:
 * - "+AB" → "AB+"
 * - "*+ABC" → "AB+C*"
 * - "+A*BC" → "ABC*+"
 * - "/*AB+CD" → "AB*CD+/"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY INSIGHT: 
 * Similar to prefix to infix, but build postfix expressions instead! 
 * 
 * ALGORITHM:
 * 1. Create empty stack for operands/expressions
 * 2. Scan prefix RIGHT to LEFT:
 *    a. If operand: Push to stack
 *    b. If operator:
 *       - Pop two operands (op1, then op2)
 *       - Create postfix: "op1 op2 operator"
 *       - Push result back to stack
 * 3. Final stack top is the postfix expression
 * 
 * DIFFERENCE FROM PREFIX TO INFIX:
 * - Infix:  "(op1 operator op2)" - operator in middle, needs parentheses
 * - Postfix: "op1 op2 operator" - operator last, no parentheses needed
 * 
 * VISUAL EXAMPLE:  "*+ABC"
 * 
 * Input: * + A B C (scanning right to left:  C, B, A, +, *)
 * 
 * Step 1: 'C' (operand)
 * Stack: ["C"]
 * 
 * Step 2: 'B' (operand)
 * Stack: ["C", "B"]
 * 
 * Step 3: 'A' (operand)
 * Stack: ["C", "B", "A"]
 * 
 * Step 4: '+' (operator)
 * - Pop "A", pop "B"
 * - Create "AB+" (operands first, operator last!)
 * - Push "AB+"
 * Stack: ["C", "AB+"]
 * 
 * Step 5: '*' (operator)
 * - Pop "AB+", pop "C"
 * - Create "AB+C*" (operands first, operator last!)
 * - Push "AB+C*"
 * Stack: ["AB+C*"]
 * 
 * Result: "AB+C*"
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - n = length of prefix expression
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
 * Prefix to Postfix converter
 */
class PrefixToPostfix {
    
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
     * Convert prefix expression to postfix
     * TIME:  O(n), SPACE: O(n)
     * 
     * @param prefix The prefix expression
     * @return Postfix expression
     */
    fun convert(prefix: String): String {
        // Remove spaces
        val expression = prefix. replace(" ", "")
        
        // Stack to hold operands and sub-expressions
        val stack = Stack<String>()
        
        // Process each character from RIGHT to LEFT
        for (i in expression.length - 1 downTo 0) {
            val char = expression[i]
            
            when {
                // If operand, push to stack as string
                isOperand(char) -> {
                    stack.push(char.toString())
                }
                
                // If operator, pop two operands and create postfix
                isOperator(char) -> {
                    // Check if stack has at least 2 operands
                    if (stack. size < 2) {
                        throw IllegalArgumentException("Invalid prefix expression")
                    }
                    
                    // Pop two operands
                    // First pop is the first operand (left operand)
                    // Second pop is the second operand (right operand)
                    val operand1 = stack.pop()
                    val operand2 = stack.pop()
                    
                    // Create postfix expression:  operands first, operator last
                    // Format: "operand1 operand2 operator"
                    val postfixExpr = "$operand1$operand2$char"
                    
                    // Push back to stack
                    stack.push(postfixExpr)
                }
            }
        }
        
        // Stack should have exactly one element (the final expression)
        if (stack.size != 1) {
            throw IllegalArgumentException("Invalid prefix expression")
        }
        
        return stack.pop()
    }
    
    /**
     * Verbose version with step-by-step trace
     */
    fun convertVerbose(prefix: String): String {
        println("Converting prefix to postfix:  \"$prefix\"")
        println("Scanning RIGHT to LEFT")
        println("=". repeat(60))
        
        val expression = prefix.replace(" ", "")
        val stack = Stack<String>()
        
        for (i in expression.length - 1 downTo 0) {
            val char = expression[i]
            val step = expression.length - i
            
            print("Step $step: '$char' (pos $i) → ")
            
            when {
                isOperand(char) -> {
                    stack.push(char.toString())
                    println("Operand, push '$char'")
                }
                
                isOperator(char) -> {
                    val operand1 = stack.pop()
                    val operand2 = stack.pop()
                    val postfixExpr = "$operand1$operand2$char"
                    stack. push(postfixExpr)
                    println("Operator, pop '$operand1' and '$operand2', push '$postfixExpr'")
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
    fun convertWithSpaces(prefix: String): String {
        val expression = prefix.replace(" ", "")
        val stack = Stack<String>()
        
        for (i in expression.length - 1 downTo 0) {
            val char = expression[i]
            
            when {
                isOperand(char) -> {
                    stack.push(char.toString())
                }
                
                isOperator(char) -> {
                    if (stack.size < 2) {
                        throw IllegalArgumentException("Invalid prefix expression")
                    }
                    
                    val operand1 = stack.pop()
                    val operand2 = stack.pop()
                    
                    // Add spaces between operands and operator
                    val postfixExpr = "$operand1 $operand2 $char"
                    
                    stack.push(postfixExpr)
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
 * Example 1: "*+ABC" (scanning right to left:  C, B, A, +, *)
 * 
 * Char | Position | Type     | Action                      | Stack
 * -----|----------|----------|-----------------------------|-----------------
 * 'C'  | 4        | Operand  | Push "C"                    | ["C"]
 * 'B'  | 3        | Operand  | Push "B"                    | ["C", "B"]
 * 'A'  | 2        | Operand  | Push "A"                    | ["C", "B", "A"]
 * '+'  | 1        | Operator | Pop A, B, push "AB+"        | ["C", "AB+"]
 * '*'  | 0        | Operator | Pop AB+, C, push "AB+C*"    | ["AB+C*"]
 * 
 * Result:  "AB+C*"
 * 
 * Verification:  AB+C* means (A+B)*C ✓
 * 
 * Example 2: "+A*BC" (scanning right to left:  C, B, *, A, +)
 * 
 * Char | Position | Type     | Action                      | Stack
 * -----|----------|----------|-----------------------------|-----------------------
 * 'C'  | 4        | Operand  | Push "C"                    | ["C"]
 * 'B'  | 3        | Operand  | Push "B"                    | ["C", "B"]
 * '*'  | 2        | Operator | Pop B, C, push "BC*"        | ["BC*"]
 * 'A'  | 1        | Operand  | Push "A"                    | ["BC*", "A"]
 * '+'  | 0        | Operator | Pop A, BC*, push "ABC*+"    | ["ABC*+"]
 * 
 * Result: "ABC*+"
 * 
 * Verification: ABC*+ means A+(B*C) ✓
 * 
 * Example 3: "/*AB+CD" (scanning right to left)
 * 
 * Char | Position | Type     | Action                         | Stack
 * -----|----------|----------|--------------------------------|------------------
 * 'D'  | 6        | Operand  | Push "D"                       | ["D"]
 * 'C'  | 5        | Operand  | Push "C"                       | ["D", "C"]
 * '+'  | 4        | Operator | Pop C, D, push "CD+"           | ["CD+"]
 * 'B'  | 3        | Operand  | Push "B"                       | ["CD+", "B"]
 * 'A'  | 2        | Operand  | Push "A"                       | ["CD+", "B", "A"]
 * '*'  | 1        | Operator | Pop A, B, push "AB*"           | ["CD+", "AB*"]
 * '/'  | 0        | Operator | Pop AB*, CD+, push "AB*CD+/"   | ["AB*CD+/"]
 * 
 * Result: "AB*CD+/"
 * 
 * Verification: AB*CD+/ means (A*B)/(C+D) ✓
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Scanning Left to Right (wrong for prefix):
 *    Wrong: for (char in expression)
 *    Right: for (i in expression.length - 1 downTo 0)
 * 
 * 2. Wrong Operand Order in Postfix Build:
 *    Wrong: val postfixExpr = "$operand2$operand1$char"
 *    Right: val postfixExpr = "$operand1$operand2$char"
 * 
 * 3. Confusing with Postfix Conversion:
 *    - Prefix:  Scan RIGHT to LEFT
 *    - Postfix:  Scan LEFT to RIGHT
 * 
 * 4. Not Handling Stack Underflow:
 *    - Always check stack size before popping
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Prefix to Postfix Conversion ===\n")
    
    val converter = PrefixToPostfix()
    
    // Test cases
    val testCases = listOf(
        "+AB" to "AB+",
        "*+ABC" to "AB+C*",
        "+A*BC" to "ABC*+",
        "*A+BC" to "ABC+*",
        "/*AB+CD" to "AB*CD+/",
        "^A^BC" to "ABC^^",
        "*+AB-CD" to "AB+CD-*",
        "-+A*BC/DE" to "ABC*+DE/-",
        "+*ABCD" to "AB*C*D+",
        "/+*ABCDE" to "AB*C+D*E/"
    )
    
    println("Test 1: Basic Conversions")
    testCases.forEach { (prefix, expected) ->
        val result = converter.convert(prefix)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$prefix\" → \"$result\"")
        if (result != expected) {
            println("   Expected: \"$expected\"")
        }
    }
    println()
    
    // Test 2: Verbose mode
    println("Test 2: Verbose Conversion")
    converter.convertVerbose("*+ABC")
    println()
    
    converter.convertVerbose("+A*BC")
    println()
    
    // Test 3: With spaces for readability
    println("Test 3: Conversions with Spaces")
    val expressions = listOf("+AB", "*+ABC", "+A*BC")
    expressions.forEach { prefix ->
        val postfix = converter.convertWithSpaces(prefix)
        println("\"$prefix\" → \"$postfix\"")
    }
    println()
    
    // Test 4: Complex expressions
    println("Test 4: Complex Expressions")
    val complexExpressions = listOf(
        "/+*AB-CD+EF",
        "-+A*BC/+DE*FG",
        "*+A*BC+DE"
    )
    
    complexExpressions.forEach { prefix ->
        val postfix = converter.convert(prefix)
        val postfixSpaced = converter.convertWithSpaces(prefix)
        println("Prefix:   \"$prefix\"")
        println("Postfix: \"$postfix\"")
        println("Spaced:  \"$postfixSpaced\"")
        println()
    }
    
    // Test 5: Error handling
    println("Test 5: Error Handling")
    val invalidExpressions = listOf(
        "++AB",
        "ABC",
        "AB+"
    )
    
    invalidExpressions.forEach { prefix ->
        try {
            val result = converter.convert(prefix)
            println("✗ \"$prefix\" → \"$result\" (should have failed)")
        } catch (e: Exception) {
            println("✓ \"$prefix\" → Error: ${e.message}")
        }
    }
    println()
    
    // Test 6: Round-trip conversion
    println("Test 6: Round-Trip Conversion")
    println("Demonstrating:  Prefix → Postfix → Prefix")
    
    val originalPrefix = "*+ABC"
    val postfix = converter.convert(originalPrefix)
    
    val postfixToPrefix = PostfixToPrefix()
    val backToPrefix = postfixToPrefix.convert(postfix)
    
    println("Original Prefix: \"$originalPrefix\"")
    println("To Postfix:       \"$postfix\"")
    println("Back to Prefix:  \"$backToPrefix\"")
    println("Match:  ${originalPrefix == backToPrefix}")
    println()
    
    // Test 7: Three-way comparison
    println("Test 7: Three-Way Notation Comparison")
    
    val prefixExamples = listOf("+AB", "*+ABC", "+A*BC")
    
    println("Prefix     | Postfix    | Meaning")
    println("-----------|------------|------------------")
    
    prefixExamples.forEach { prefix ->
        val postfix = converter.convert(prefix)
        val meaning = when(prefix) {
            "+AB" -> "A + B"
            "*+ABC" -> "(A + B) * C"
            "+A*BC" -> "A + (B * C)"
            else -> "Complex"
        }
        println("%-10s | %-10s | %s". format(prefix, postfix, meaning))
    }
}
