/**
 * ============================================================================
 * PROBLEM: Infix to Prefix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Convert an infix expression to prefix notation (also called Polish Notation).
 * 
 * NOTATIONS EXPLAINED:
 * 
 * **Infix**:  Operator between operands
 * - Example: A + B, (A + B) * C
 * 
 * **Prefix (Polish Notation)**: Operator before operands
 * - Example:  + A B, * + A B C
 * - No parentheses needed
 * - Read right to left for evaluation
 * 
 * EXAMPLES:
 * - "A+B" → "+AB"
 * - "A+B*C" → "+A*BC"
 * - "(A+B)*C" → "*+ABC"
 * - "A*(B+C)" → "*A+BC"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY INSIGHT:
 * Prefix is like postfix but for reversed expression! 
 * 
 * CLEVER ALGORITHM:
 * 1. Reverse the infix expression
 * 2. Swap '(' with ')' and vice versa
 * 3. Convert to postfix
 * 4. Reverse the result
 * 
 * WHY THIS WORKS? 
 * - Reversing changes order:  "A+B" → "B+A"
 * - Swapping parentheses maintains grouping
 * - Postfix of reversed = reversed prefix
 * - Reverse again = prefix! 
 * 
 * VISUAL EXAMPLE:  "(A+B)*C"
 * 
 * Step 1: Reverse
 * Original: (A+B)*C
 * Reversed: C*)B+A(
 * 
 * Step 2: Swap parentheses
 * C*)B+A( → C*(B+A)
 * 
 * Step 3: Convert to postfix
 * C*(B+A) → CBA+*
 * 
 * Step 4: Reverse result
 * CBA+* → *+ABC
 * 
 * Result: "*+ABC" ✓
 * 
 * ALTERNATIVE APPROACH:
 * Direct method (more complex):
 * - Scan right to left
 * - Use stack with modified rules
 * - More error-prone
 * 
 * ============================================================================
 * ALGORITHM STEPS
 * ============================================================================
 * 
 * 1. Reverse the infix expression
 * 2. Swap '(' with ')' and ')' with '('
 * 3. Convert modified expression to postfix
 * 4. Reverse the postfix to get prefix
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - Reverse: O(n)
 * - Swap parentheses: O(n)
 * - Convert to postfix:  O(n)
 * - Reverse again: O(n)
 * - Total: 4 × O(n) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack for conversion: O(n)
 * - Temporary strings: O(n)
 * - Total: O(n)
 * 
 * ============================================================================
 */

package stackqueue.conversions

import java.util.Stack

/**
 * Infix to Prefix converter
 */
class InfixToPrefix {
    
    /**
     * Get precedence level of operator
     */
    private fun precedence(operator: Char): Int {
        return when (operator) {
            '^' -> 3
            '*', '/' -> 2
            '+', '-' -> 1
            else -> 0
        }
    }
    
    /**
     * Check if operator is right associative
     */
    private fun isRightAssociative(operator: Char): Boolean {
        return operator == '^'
    }
    
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
     * Reverse string and swap parentheses
     * 
     * @param expression The expression to reverse
     * @return Reversed expression with swapped parentheses
     */
    private fun reverseAndSwapParentheses(expression: String): String {
        val result = StringBuilder()
        
        // Reverse and swap parentheses in one pass
        for (i in expression.length - 1 downTo 0) {
            val char = expression[i]
            when (char) {
                '(' -> result.append(')')
                ')' -> result.append('(')
                else -> result.append(char)
            }
        }
        
        return result.toString()
    }
    
    /**
     * Convert modified infix to postfix
     * Similar to regular infix to postfix conversion
     */
    private fun infixToPostfix(infix: String): String {
        val stack = Stack<Char>()
        val postfix = StringBuilder()
        
        for (char in infix) {
            when {
                isOperand(char) -> {
                    postfix.append(char)
                }
                
                char == '(' -> {
                    stack.push(char)
                }
                
                char == ')' -> {
                    while (stack.isNotEmpty() && stack.peek() != '(') {
                        postfix. append(stack.pop())
                    }
                    if (stack.isNotEmpty()) {
                        stack.pop()  // Remove '('
                    }
                }
                
                isOperator(char) -> {
                    // For prefix conversion, we need slightly different logic
                    // Pop operators with higher precedence (not equal)
                    while (stack.isNotEmpty() && 
                           stack.peek() != '(' &&
                           precedence(stack.peek()) > precedence(char)) {
                        postfix.append(stack. pop())
                    }
                    stack.push(char)
                }
            }
        }
        
        while (stack.isNotEmpty()) {
            postfix.append(stack.pop())
        }
        
        return postfix.toString()
    }
    
    /**
     * Convert infix expression to prefix
     * TIME:  O(n), SPACE: O(n)
     * 
     * @param infix The infix expression
     * @return Prefix expression
     */
    fun convert(infix: String): String {
        // Remove spaces
        val expression = infix.replace(" ", "")
        
        // Step 1: Reverse and swap parentheses
        val reversed = reverseAndSwapParentheses(expression)
        
        // Step 2: Convert to postfix
        val postfix = infixToPostfix(reversed)
        
        // Step 3: Reverse to get prefix
        val prefix = postfix.reversed()
        
        return prefix
    }
    
    /**
     * Verbose version with step-by-step trace
     */
    fun convertVerbose(infix: String): String {
        println("Converting infix to prefix:  \"$infix\"")
        println("=". repeat(60))
        
        val expression = infix.replace(" ", "")
        
        // Step 1
        println("Step 1: Reverse and swap parentheses")
        println("  Original: \"$expression\"")
        val reversed = reverseAndSwapParentheses(expression)
        println("  Reversed:  \"$reversed\"")
        println()
        
        // Step 2
        println("Step 2: Convert to postfix")
        println("  Processing: \"$reversed\"")
        val postfix = infixToPostfix(reversed)
        println("  Postfix: \"$postfix\"")
        println()
        
        // Step 3
        println("Step 3: Reverse to get prefix")
        println("  Postfix: \"$postfix\"")
        val prefix = postfix.reversed()
        println("  Prefix: \"$prefix\"")
        println("=".repeat(60))
        
        return prefix
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGHS
 * ============================================================================
 * 
 * Example 1: "A+B*C"
 * 
 * Original infix: A+B*C
 * 
 * Step 1: Reverse and swap
 * - Reverse: C*B+A
 * - No parentheses to swap
 * - Result: C*B+A
 * 
 * Step 2: Convert to postfix
 * - Process C:  Output "C"
 * - Process *: Stack [*]
 * - Process B:  Output "CB"
 * - Process +:  Pop *, push +.  Output "CB*", Stack [+]
 * - Process A: Output "CB*A"
 * - End: Pop +.  Output "CB*A+"
 * 
 * Step 3: Reverse
 * - Reverse "CB*A+": "+A*BC"
 * 
 * Result: "+A*BC"
 * 
 * Verification: +A*BC means A + (B * C) ✓
 * 
 * Example 2: "(A+B)*C"
 * 
 * Original infix: (A+B)*C
 * 
 * Step 1: Reverse and swap
 * - Reverse: C*)B+A(
 * - Swap: C*(B+A)
 * 
 * Step 2: Convert to postfix
 * - C*(B+A) → CBA+*
 * 
 * Step 3: Reverse
 * - Reverse "CBA+*": "*+ABC"
 * 
 * Result: "*+ABC"
 * 
 * Verification: *+ABC means (A + B) * C ✓
 * 
 * Example 3: "A^B^C"
 * 
 * Original infix: A^B^C
 * 
 * Step 1: Reverse and swap
 * - Reverse: C^B^A
 * 
 * Step 2: Convert to postfix
 * - C^B^A → CB^A^
 * 
 * Step 3: Reverse
 * - Reverse "CB^A^":  "^A^BC"
 * 
 * Result: "^A^BC"
 * 
 * Verification: ^A^BC means A^(B^C) ✓ (right associative)
 * 
 * ============================================================================
 * WHY THE ALGORITHM WORKS
 * ============================================================================
 * 
 * MATHEMATICAL PROOF:
 * 
 * 1. Infix: A op B
 *    - Postfix: A B op
 *    - Prefix: op A B
 * 
 * 2. Reverse infix: B op A
 *    - Postfix of reversed: B A op
 *    - Reverse postfix: op A B = Prefix!  ✓
 * 
 * 3. Parentheses:  (A op B)
 *    - Reversed: (B op A)  ← Wrong!
 *    - Need:  )B op A( ← Need to swap
 *    - Convert:  (B op A) → postfix → reverse → prefix ✓
 * 
 * ============================================================================
 * COMPARISON:  PREFIX VS POSTFIX
 * ============================================================================
 * 
 * Expression | Infix    | Prefix    | Postfix
 * -----------|----------|-----------|----------
 * Simple     | A + B    | + A B     | A B +
 * Precedence | A + B*C  | + A * B C | A B C * +
 * Grouping   | (A+B)*C  | * + A B C | A B + C *
 * Complex    | A*(B+C)  | * A + B C | A B C + *
 * Power      | A^B^C    | ^ A ^ B C | A B C ^ ^
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Infix to Prefix Conversion ===\n")
    
    val converter = InfixToPrefix()
    
    // Test cases
    val testCases = listOf(
        "A+B" to "+AB",
        "A+B*C" to "+A*BC",
        "(A+B)*C" to "*+ABC",
        "A*(B+C)" to "*A+BC",
        "A+B-C" to "-+ABC",
        "A*B+C" to "+*ABC",
        "A^B^C" to "^A^BC",
        "A*(B+C)/D" to "/*A+BCD",
        "(A+B)*(C-D)" to "*+AB-CD",
        "A+B*C-D/E" to "-+A*BC/DE",
        "A^B*C+D" to "+*^ABCD",
        "((A+B)*C-D)/E" to "/-*+ABCDE"
    )
    
    println("Test 1: Basic Conversions")
    testCases.forEach { (infix, expected) ->
        val result = converter.convert(infix)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$infix\" → \"$result\" (expected: \"$expected\")")
    }
    println()
    
    // Test 2: Verbose mode
    println("Test 2: Verbose Conversion")
    converter.convertVerbose("(A+B)*C")
    println()
    
    converter.convertVerbose("A+B*C")
    println()
    
    // Test 3: Complex expressions
    println("Test 3: Complex Expressions")
    val complexExpressions = listOf(
        "A+B*(C^D-E)^(F+G*H)-I",
        "((A+B)*C-(D-E))^(F+G)",
        "A*B+C*D+E*F"
    )
    
    complexExpressions.forEach { infix ->
        val prefix = converter.convert(infix)
        println("\"$infix\"")
        println("→ \"$prefix\"")
        println()
    }
    
    // Test 4: Comparison with postfix
    println("Test 4: Prefix vs Postfix Comparison")
    val postfixConverter = InfixToPostfix()
    val expressions = listOf("A+B", "(A+B)*C", "A+B*C")
    
    println("Infix          | Prefix      | Postfix")
    println("---------------|-------------|-------------")
    expressions.forEach { infix ->
        val prefix = converter.convert(infix)
        val postfix = postfixConverter.convert(infix)
        println("%-14s | %-11s | %s". format(infix, prefix, postfix))
    }
}
