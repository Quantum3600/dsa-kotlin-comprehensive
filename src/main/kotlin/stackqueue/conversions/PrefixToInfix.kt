/**
 * ============================================================================
 * PROBLEM: Prefix to Infix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Convert a prefix expression (Polish Notation) to infix notation.
 * 
 * NOTATIONS: 
 * 
 * Prefix:  Operator before operands
 * - Example: +AB, *+ABC
 * - Evaluated right to left
 * - No parentheses needed
 * 
 * Infix:  Operator between operands
 * - Example: A+B, (A+B)*C
 * - Natural for humans
 * - Needs parentheses
 * 
 * EXAMPLES:
 * - "+AB" → "(A+B)"
 * - "*+ABC" → "((A+B)*C)"
 * - "+A*BC" → "(A+(B*C))"
 * - "/*AB+CD" → "((A*B)/(C+D))"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY DIFFERENCE FROM POSTFIX:
 * - Postfix: Scan LEFT to RIGHT
 * - Prefix: Scan RIGHT to LEFT (reverse!)
 * 
 * WHY RIGHT TO LEFT?
 * - In prefix, operator comes BEFORE operands
 * - When scanning right to left, we encounter operands first
 * - Then operator tells us how to combine them
 * - Just like postfix, but reversed! 
 * 
 * ALGORITHM:
 * 1. Create empty stack for operands/expressions
 * 2. Scan prefix RIGHT to LEFT: 
 *    a. If operand:  Push to stack
 *    b.  If operator: 
 *       - Pop two operands (op1, then op2)
 *       - Create infix:  "(op1 operator op2)"
 *       - Push result back to stack
 * 3. Final stack top is the infix expression
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
 * - Create "(A+B)"
 * - Push "(A+B)"
 * Stack: ["C", "(A+B)"]
 * 
 * Step 5: '*' (operator)
 * - Pop "(A+B)", pop "C"
 * - Create "((A+B)*C)"
 * - Push "((A+B)*C)"
 * Stack: ["((A+B)*C)"]
 * 
 * Result: "((A+B)*C)"
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
 * Prefix to Infix converter
 */
class PrefixToInfix {
    
    /**
     * Check if character is an operand
     */
    private fun isOperand(char:  Char): Boolean {
        return char.isLetterOrDigit()
    }
    
    /**
     * Check if character is an operator
     */
    private fun isOperator(char: Char): Boolean {
        return char in "+-*/^"
    }
    
    /**
     * Convert prefix expression to infix
     * TIME:  O(n), SPACE: O(n)
     * 
     * @param prefix The prefix expression
     * @return Infix expression with parentheses
     */
    fun convert(prefix: String): String {
        // Remove spaces
        val expression = prefix.replace(" ", "")
        
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
                
                // If operator, pop two operands and create infix
                isOperator(char) -> {
                    // Check if stack has at least 2 operands
                    if (stack. size < 2) {
                        throw IllegalArgumentException("Invalid prefix expression")
                    }
                    
                    // Pop two operands
                    // Note: First pop is the first operand (left operand)
                    // Second pop is the second operand (right operand)
                    val operand1 = stack.pop()
                    val operand2 = stack.pop()
                    
                    // Create infix expression with parentheses
                    val infixExpr = "($operand1$char$operand2)"
                    
                    // Push back to stack
                    stack.push(infixExpr)
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
        println("Converting prefix to infix: \"$prefix\"")
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
                    stack.push(char. toString())
                    println("Operand, push '$char'")
                }
                
                isOperator(char) -> {
                    val operand1 = stack.pop()
                    val operand2 = stack.pop()
                    val infixExpr = "($operand1$char$operand2)"
                    stack.push(infixExpr)
                    println("Operator, pop '$operand1' and '$operand2', push '$infixExpr'")
                }
            }
            
            println("   Stack: $stack")
        }
        
        val result = stack.pop()
        println("=".repeat(60))
        println("Result: \"$result\"")
        return result
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
 * '+'  | 1        | Operator | Pop A, B, push "(A+B)"      | ["C", "(A+B)"]
 * '*'  | 0        | Operator | Pop (A+B), C, push result   | ["((A+B)*C)"]
 * 
 * Result: "((A+B)*C)"
 * 
 * Example 2: "+A*BC" (scanning right to left: C, B, *, A, +)
 * 
 * Char | Position | Type     | Action                      | Stack
 * -----|----------|----------|-----------------------------|-----------------------
 * 'C'  | 4        | Operand  | Push "C"                    | ["C"]
 * 'B'  | 3        | Operand  | Push "B"                    | ["C", "B"]
 * '*'  | 2        | Operator | Pop B, C, push "(B*C)"      | ["(B*C)"]
 * 'A'  | 1        | Operand  | Push "A"                    | ["(B*C)", "A"]
 * '+'  | 0        | Operator | Pop A, (B*C), push result   | ["(A+(B*C))"]
 * 
 * Result: "(A+(B*C))"
 * 
 * Example 3: "/*AB+CD" (scanning right to left)
 * 
 * Char | Position | Type     | Action                         | Stack
 * -----|----------|----------|--------------------------------|------------------
 * 'D'  | 6        | Operand  | Push "D"                       | ["D"]
 * 'C'  | 5        | Operand  | Push "C"                       | ["D", "C"]
 * '+'  | 4        | Operator | Pop C, D, push "(C+D)"         | ["(C+D)"]
 * 'B'  | 3        | Operand  | Push "B"                       | ["(C+D)", "B"]
 * 'A'  | 2        | Operand  | Push "A"                       | ["(C+D)", "B", "A"]
 * '*'  | 1        | Operator | Pop A, B, push "(A*B)"         | ["(C+D)", "(A*B)"]
 * '/'  | 0        | Operator | Pop (A*B), (C+D), push result  | ["((A*B)/(C+D))"]
 * 
 * Result: "((A*B)/(C+D))"
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Scanning Left to Right (wrong direction):
 *    Wrong: for (char in expression)
 *    Right: for (i in expression.length - 1 downTo 0)
 * 
 * 2. Wrong Operand Order:
 *    - In prefix to infix: First pop = first operand
 *    - In postfix to infix: First pop = second operand
 * 
 * 3. Forgetting Parentheses:
 *    - Always add parentheses to preserve order
 * 
 * 4. Not Validating Stack Size:
 *    - Check before popping
 *    - Check final size = 1
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Prefix to Infix Conversion ===\n")
    
    val converter = PrefixToInfix()
    
    // Test cases
    val testCases = listOf(
        "+AB" to "(A+B)",
        "*+ABC" to "((A+B)*C)",
        "+A*BC" to "(A+(B*C))",
        "*A+BC" to "(A*(B+C))",
        "/*AB+CD" to "((A*B)/(C+D))",
        "^A^BC" to "(A^(B^C))",
        "*+AB-CD" to "((A+B)*(C-D))",
        "-+A*BC/DE" to "((A+(B*C))-(D/E))",
        "+*ABCD" to "(((A*B)*C)+D)",
        "/+*ABCDE" to "((((A*B)+C)*D)/E)"
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
    
    // Test 3: Complex expressions
    println("Test 3: Complex Expressions")
    val complexExpressions = listOf(
        "/+*AB-CD+EF",
        "-+A*BC/+DE*FG",
        "*+A*BC+DE"
    )
    
    complexExpressions.forEach { prefix ->
        val infix = converter.convert(prefix)
        println("\"$prefix\" → \"$infix\"")
    }
    println()
    
    // Test 4: Error handling
    println("Test 4: Error Handling")
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
    
    // Test 5:  Comparison with postfix conversion
    println("Test 5: Prefix vs Postfix Conversion Comparison")
    
    println("\nSame infix, different notation:")
    println("Prefix  \"*+ABC\" → \"${converter.convert("*+ABC")}\"")
    
    val postfixConverter = PostfixToInfix()
    println("Postfix \"AB+C*\" → \"${postfixConverter. convert("AB+C*")}\"")
    println("Both represent:  (A+B)*C")
    println()
    
    // Test 6: Single operand
    println("Test 6: Edge Cases")
    println("\"A\" → \"${converter.convert("A")}\"")
}
