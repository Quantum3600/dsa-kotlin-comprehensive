/**
 * ============================================================================
 * PROBLEM:  Postfix to Infix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Convert a postfix expression (Reverse Polish Notation) to infix notation.
 * 
 * NOTATIONS: 
 * 
 * **Postfix**:  Operator after operands
 * - Example: AB+, ABC*+
 * - Easy for computers to evaluate
 * - No parentheses needed
 * 
 * **Infix**:  Operator between operands
 * - Example: A+B, A+(B*C)
 * - Natural for humans
 * - Needs parentheses
 * 
 * EXAMPLES:
 * - "AB+" → "(A+B)"
 * - "ABC*+" → "(A+(B*C))"
 * - "AB+C*" → "((A+B)*C)"
 * - "AB*CD+/" → "((A*B)/(C+D))"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY INSIGHT:
 * Postfix is evaluated left to right using a stack. 
 * We can build infix expressions the same way! 
 * 
 * ALGORITHM:
 * 1. Create empty stack for operands/expressions
 * 2. Scan postfix left to right: 
 *    a. If operand: Push to stack
 *    b.  If operator: 
 *       - Pop two operands (op2, then op1)
 *       - Create infix:  "(op1 operator op2)"
 *       - Push result back to stack
 * 3. Final stack top is the infix expression
 * 
 * WHY PARENTHESES?
 * - Preserve operator precedence
 * - Ensure correct evaluation order
 * - Can be minimized later if needed
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
 * - Create "(A+B)"
 * - Push "(A+B)"
 * Stack: ["(A+B)"]
 * 
 * Step 4: 'C' (operand)
 * Stack: ["(A+B)", "C"]
 * 
 * Step 5: '*' (operator)
 * - Pop "C", pop "(A+B)"
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
 * - n = length of postfix expression
 * - Each character processed once
 * - Stack operations:  O(1)
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
 * Postfix to Infix converter
 */
class PostfixToInfix {
    
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
     * Convert postfix expression to infix
     * TIME: O(n), SPACE: O(n)
     * 
     * @param postfix The postfix expression
     * @return Infix expression with parentheses
     */
    fun convert(postfix: String): String {
        // Remove spaces
        val expression = postfix.replace(" ", "")
        
        // Stack to hold operands and sub-expressions
        val stack = Stack<String>()
        
        // Process each character
        for (char in expression) {
            when {
                // If operand, push to stack as string
                isOperand(char) -> {
                    stack.push(char.toString())
                }
                
                // If operator, pop two operands and create infix
                isOperator(char) -> {
                    // Check if stack has at least 2 operands
                    if (stack. size < 2) {
                        throw IllegalArgumentException("Invalid postfix expression")
                    }
                    
                    // Pop two operands
                    // Note: Second pop is the first operand! 
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    
                    // Create infix expression with parentheses
                    val infixExpr = "($operand1$char$operand2)"
                    
                    // Push back to stack
                    stack.push(infixExpr)
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
     * Convert with minimal parentheses (removes unnecessary ones)
     * More complex but cleaner output
     */
    fun convertMinimal(postfix: String): String {
        val expression = postfix.replace(" ", "")
        val stack = Stack<String>()
        
        for (char in expression) {
            when {
                isOperand(char) -> {
                    stack.push(char.toString())
                }
                
                isOperator(char) -> {
                    if (stack. size < 2) {
                        throw IllegalArgumentException("Invalid postfix expression")
                    }
                    
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    
                    // Add parentheses only when necessary
                    // (This is simplified; full implementation would check precedence)
                    val infixExpr = if (char in "+-") {
                        "($operand1$char$operand2)"
                    } else {
                        "$operand1$char$operand2"
                    }
                    
                    stack.push(infixExpr)
                }
            }
        }
        
        return stack.pop()
    }
    
    /**
     * Verbose version with step-by-step trace
     */
    fun convertVerbose(postfix: String): String {
        println("Converting postfix to infix:  \"$postfix\"")
        println("=". repeat(60))
        
        val expression = postfix.replace(" ", "")
        val stack = Stack<String>()
        
        for ((index, char) in expression.withIndex()) {
            print("Step ${index + 1}: '$char' → ")
            
            when {
                isOperand(char) -> {
                    stack.push(char.toString())
                    println("Operand, push '$char'")
                }
                
                isOperator(char) -> {
                    val operand2 = stack.pop()
                    val operand1 = stack.pop()
                    val infixExpr = "($operand1$char$operand2)"
                    stack.push(infixExpr)
                    println("Operator, pop '$operand2' and '$operand1', push '$infixExpr'")
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
 * Example 1: "AB+C*"
 * 
 * Char | Type     | Action                    | Stack
 * -----|----------|---------------------------|------------------
 * 'A'  | Operand  | Push "A"                  | ["A"]
 * 'B'  | Operand  | Push "B"                  | ["A", "B"]
 * '+'  | Operator | Pop B, A, push "(A+B)"    | ["(A+B)"]
 * 'C'  | Operand  | Push "C"                  | ["(A+B)", "C"]
 * '*'  | Operator | Pop C, (A+B), push result | ["((A+B)*C)"]
 * 
 * Result: "((A+B)*C)"
 * 
 * Example 2: "ABC*+"
 * 
 * Char | Type     | Action                    | Stack
 * -----|----------|---------------------------|----------------------
 * 'A'  | Operand  | Push "A"                  | ["A"]
 * 'B'  | Operand  | Push "B"                  | ["A", "B"]
 * 'C'  | Operand  | Push "C"                  | ["A", "B", "C"]
 * '*'  | Operator | Pop C, B, push "(B*C)"    | ["A", "(B*C)"]
 * '+'  | Operator | Pop (B*C), A, push result | ["(A+(B*C))"]
 * 
 * Result: "(A+(B*C))"
 * 
 * Example 3: "AB*CD+/"
 * 
 * Char | Type     | Action                         | Stack
 * -----|----------|--------------------------------|------------------
 * 'A'  | Operand  | Push "A"                       | ["A"]
 * 'B'  | Operand  | Push "B"                       | ["A", "B"]
 * '*'  | Operator | Pop B, A, push "(A*B)"         | ["(A*B)"]
 * 'C'  | Operand  | Push "C"                       | ["(A*B)", "C"]
 * 'D'  | Operand  | Push "D"                       | ["(A*B)", "C", "D"]
 * '+'  | Operator | Pop D, C, push "(C+D)"         | ["(A*B)", "(C+D)"]
 * '/'  | Operator | Pop (C+D), (A*B), push result  | ["((A*B)/(C+D))"]
 * 
 * Result: "((A*B)/(C+D))"
 * 
 * ============================================================================
 * KEY OBSERVATIONS
 * ============================================================================
 * 
 * 1. **Stack Order Matters**:
 *    - First pop = second operand
 *    - Second pop = first operand
 *    - Important for non-commutative operators (-, /)
 * 
 * 2. **Parentheses**:
 *    - All sub-expressions get parentheses
 *    - Ensures correct precedence
 *    - Can be optimized later
 * 
 * 3. **Error Detection**:
 *    - Stack size < 2 before operator → Invalid
 *    - Stack size != 1 at end → Invalid
 * 
 * 4. **Expression Building**:
 *    - Each operator combines two expressions
 *    - Recursive structure emerges naturally
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. **Wrong Operand Order**:
 *    ❌ val infixExpr = "($operand2$char$operand1)"
 *    ✓ val infixExpr = "($operand1$char$operand2)"
 * 
 * 2. **Forgetting Parentheses**: 
 *    ❌ val infixExpr = "$operand1$char$operand2"
 *    ✓ val infixExpr = "($operand1$char$operand2)"
 * 
 * 3. **Not Checking Stack Size**:
 *    - Can cause EmptyStackException
 *    - Always validate before pop
 * 
 * 4. **Not Validating Final Stack**:
 *    - Should have exactly 1 element
 *    - Multiple elements = invalid expression
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Postfix to Infix Conversion ===\n")
    
    val converter = PostfixToInfix()
    
    // Test cases
    val testCases = listOf(
        "AB+" to "(A+B)",
        "ABC*+" to "(A+(B*C))",
        "AB+C*" to "((A+B)*C)",
        "ABC+*" to "(A*(B+C))",
        "AB*CD+/" to "((A*B)/(C+D))",
        "ABC^^" to "(A^(B^C))",
        "AB+CD-*" to "((A+B)*(C-D))",
        "ABC*+DE/-" to "((A+(B*C))-(D/E))",
        "ABCD^*+E-" to "(((A+(B*(C^D)))-E))",
        "AB*C+D/" to "(((A*B)+C)/D)"
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
    
    // Test 3: Complex expressions
    println("Test 3: Complex Expressions")
    val complexExpressions = listOf(
        "AB+CD-*EF+/",
        "ABC*+D-EF+/",
        "ABCD+*+E*"
    )
    
    complexExpressions.forEach { postfix ->
        val infix = converter.convert(postfix)
        println("\"$postfix\" → \"$infix\"")
    }
    println()
    
    // Test 4: Error handling
    println("Test 4: Error Handling")
    val invalidExpressions = listOf(
        "AB++",    // Too many operators
        "ABC",     // No operators
        "+AB"      // Operator before operands
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
    
    // Test 5: Single operand
    println("Test 5: Edge Cases")
    println("\"A\" → \"${converter.convert("A")}\"")
}
