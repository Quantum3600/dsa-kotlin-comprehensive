/**
 * ============================================================================
 * PROBLEM:  Infix to Postfix Conversion
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Conversions
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Convert an infix expression to postfix notation (also called Reverse Polish
 * Notation - RPN).
 * 
 * NOTATIONS EXPLAINED:
 * 
 * **Infix**: Operator between operands (how we normally write)
 * - Example: A + B, (A + B) * C
 * - Needs parentheses for precedence
 * 
 * **Postfix (RPN)**: Operator after operands
 * - Example:  A B +, A B + C *
 * - No parentheses needed! 
 * - Easy for computers to evaluate
 * 
 * WHY POSTFIX?
 * ✅ No parentheses needed
 * ✅ No ambiguity in evaluation order
 * ✅ Easy to evaluate using a stack
 * ✅ Used in calculators, compilers
 * ✅ Eliminates precedence issues
 * 
 * INPUT FORMAT:
 * - Infix expression string
 * - Operands:  A-Z, a-z, 0-9
 * - Operators: +, -, *, /, ^
 * - Parentheses: (, )
 * 
 * OUTPUT FORMAT:
 * - Postfix expression string
 * 
 * EXAMPLES:
 * - "A+B" → "AB+"
 * - "A+B*C" → "ABC*+"
 * - "(A+B)*C" → "AB+C*"
 * - "A*(B+C)/D" → "ABC+*D/"
 * - "A+B^C*D" → "ABC^D*+"
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY CONCEPTS:
 * 
 * 1. **Operator Precedence**:
 *    - ^ (Power): Highest
 *    - *, / (Multiply, Divide): Medium
 *    - +, - (Add, Subtract): Lowest
 * 
 * 2. **Associativity**:
 *    - Left to Right:  +, -, *, /
 *    - Right to Left: ^
 * 
 * WHY USE A STACK?
 * - Stack holds operators temporarily
 * - Operators are output when lower precedence comes
 * - Parentheses control evaluation order
 * - LIFO property handles nested expressions
 * 
 * ALGORITHM (Shunting Yard by Dijkstra):
 * 
 * 1. Create empty stack for operators
 * 2. Create empty string for output
 * 3. Scan infix expression left to right: 
 *    
 *    a. If operand (A-Z, 0-9): Add to output
 *    
 *    b. If '(': Push to stack
 *    
 *    c. If ')': 
 *       - Pop and output until '(' found
 *       - Discard '('
 *    
 *    d. If operator:
 *       - While stack not empty AND
 *         top has higher/equal precedence:
 *         * Pop and output top
 *       - Push current operator
 * 
 * 4. Pop all remaining operators to output
 * 
 * VISUAL EXAMPLE:  "A+B*C"
 * 
 * Step 1: 'A' (operand)
 * Output: "A"
 * Stack: []
 * 
 * Step 2: '+' (operator)
 * Output: "A"
 * Stack: ['+']
 * 
 * Step 3: 'B' (operand)
 * Output: "AB"
 * Stack: ['+']
 * 
 * Step 4: '*' (operator, higher precedence than +)
 * Output: "AB"
 * Stack: ['+', '*']  (* not popped, higher precedence)
 * 
 * Step 5: 'C' (operand)
 * Output: "ABC"
 * Stack:  ['+', '*']
 * 
 * Step 6: End of expression, pop all
 * Pop '*':  Output:  "ABC*"
 * Pop '+': Output: "ABC*+"
 * Stack: []
 * 
 * Result: "ABC*+"
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - n = length of infix expression
 * - Each character scanned once
 * - Each operator pushed/popped once
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack:  O(n) worst case (all operators)
 * - Output string: O(n)
 * - Total: O(n)
 * 
 * ============================================================================
 */

package stackqueue.conversions

import java.util.Stack

/**
 * Infix to Postfix converter
 */
class InfixToPostfix {
    
    /**
     * Get precedence level of operator
     * Higher number = higher precedence
     * 
     * @param operator The operator character
     * @return Precedence level (0-3)
     */
    private fun precedence(operator: Char): Int {
        return when (operator) {
            '^' -> 3  // Highest precedence
            '*', '/' -> 2  // Medium precedence
            '+', '-' -> 1  // Lowest precedence
            else -> 0  // Not an operator
        }
    }
    
    /**
     * Check if operator is right associative
     * Only ^ is right associative
     * 
     * @param operator The operator character
     * @return true if right associative
     */
    private fun isRightAssociative(operator:  Char): Boolean {
        return operator == '^'
    }
    
    /**
     * Check if character is an operand (letter or digit)
     * 
     * @param char The character to check
     * @return true if operand
     */
    private fun isOperand(char: Char): Boolean {
        return char.isLetterOrDigit()
    }
    
    /**
     * Check if character is an operator
     * 
     * @param char The character to check
     * @return true if operator
     */
    private fun isOperator(char:  Char): Boolean {
        return char in "+-*/^"
    }
    
    /**
     * Convert infix expression to postfix
     * TIME: O(n), SPACE: O(n)
     * 
     * @param infix The infix expression
     * @return Postfix expression
     */
    fun convert(infix: String): String {
        // Remove spaces from input
        val expression = infix.replace(" ", "")
        
        // Stack to hold operators and parentheses
        val stack = Stack<Char>()
        
        // StringBuilder for efficient string building
        val postfix = StringBuilder()
        
        // Process each character
        for (char in expression) {
            when {
                // Case 1: Operand (letter or digit)
                isOperand(char) -> {
                    // Directly add to output
                    postfix.append(char)
                }
                
                // Case 2: Opening parenthesis
                char == '(' -> {
                    // Push to stack
                    stack.push(char)
                }
                
                // Case 3: Closing parenthesis
                char == ')' -> {
                    // Pop until matching '(' found
                    while (stack. isNotEmpty() && stack.peek() != '(') {
                        postfix.append(stack. pop())
                    }
                    // Remove the '(' from stack
                    if (stack.isNotEmpty()) {
                        stack.pop()
                    }
                }
                
                // Case 4: Operator
                isOperator(char) -> {
                    // Pop operators with higher or equal precedence
                    while (stack.isNotEmpty() && 
                           stack.peek() != '(' &&
                           (precedence(stack.peek()) > precedence(char) ||
                            (precedence(stack.peek()) == precedence(char) && 
                             ! isRightAssociative(char)))) {
                        postfix.append(stack.pop())
                    }
                    // Push current operator
                    stack.push(char)
                }
            }
        }
        
        // Pop all remaining operators
        while (stack. isNotEmpty()) {
            postfix.append(stack.pop())
        }
        
        return postfix.toString()
    }
    
    /**
     * Verbose version with step-by-step trace
     */
    fun convertVerbose(infix: String): String {
        println("Converting infix to postfix:  \"$infix\"")
        println("=" . repeat(60))
        
        val expression = infix.replace(" ", "")
        val stack = Stack<Char>()
        val postfix = StringBuilder()
        
        for ((index, char) in expression.withIndex()) {
            print("Step ${index + 1}: '$char' → ")
            
            when {
                isOperand(char) -> {
                    postfix.append(char)
                    println("Operand, add to output")
                }
                
                char == '(' -> {
                    stack.push(char)
                    println("Opening parenthesis, push to stack")
                }
                
                char == ')' -> {
                    print("Closing parenthesis, pop until '(':  ")
                    while (stack. isNotEmpty() && stack.peek() != '(') {
                        val op = stack.pop()
                        postfix.append(op)
                        print("$op ")
                    }
                    if (stack.isNotEmpty()) stack.pop()  // Remove '('
                    println()
                }
                
                isOperator(char) -> {
                    print("Operator (prec=${precedence(char)}), ")
                    if (stack.isNotEmpty() && stack.peek() != '(') {
                        print("pop higher/equal:  ")
                        while (stack.isNotEmpty() && 
                               stack.peek() != '(' &&
                               (precedence(stack.peek()) > precedence(char) ||
                                (precedence(stack.peek()) == precedence(char) && 
                                 !isRightAssociative(char)))) {
                            val op = stack.pop()
                            postfix.append(op)
                            print("$op ")
                        }
                    }
                    stack.push(char)
                    println("push '$char'")
                }
            }
            
            println("   Output: \"$postfix\"  Stack: $stack")
        }
        
        print("End:  Pop remaining:  ")
        while (stack.isNotEmpty()) {
            val op = stack.pop()
            postfix.append(op)
            print("$op ")
        }
        println()
        
        println("=" .repeat(60))
        println("Result: \"$postfix\"")
        return postfix.toString()
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGHS
 * ============================================================================
 * 
 * Example 1: "(A+B)*C"
 * 
 * Char | Action              | Output | Stack
 * -----|---------------------|--------|--------
 * '('  | Push                | ""     | ['(']
 * 'A'  | Operand, add        | "A"    | ['(']
 * '+'  | Push operator       | "A"    | ['(', '+']
 * 'B'  | Operand, add        | "AB"   | ['(', '+']
 * ')'  | Pop until '('       | "AB+"  | []
 * '*'  | Push operator       | "AB+"  | ['*']
 * 'C'  | Operand, add        | "AB+C" | ['*']
 * END  | Pop all             | "AB+C*"| []
 * 
 * Result: "AB+C*"
 * 
 * Example 2: "A+B*C"
 * 
 * Char | Action              | Output | Stack
 * -----|---------------------|--------|--------
 * 'A'  | Operand             | "A"    | []
 * '+'  | Push                | "A"    | ['+']
 * 'B'  | Operand             | "AB"   | ['+']
 * '*'  | Higher prec, push   | "AB"   | ['+', '*']
 * 'C'  | Operand             | "ABC"  | ['+', '*']
 * END  | Pop all             | "ABC*+"| []
 * 
 * Result: "ABC*+"
 * 
 * Example 3: "A^B^C" (Right associative)
 * 
 * Char | Action              | Output | Stack
 * -----|---------------------|--------|--------
 * 'A'  | Operand             | "A"    | []
 * '^'  | Push                | "A"    | ['^']
 * 'B'  | Operand             | "AB"   | ['^']
 * '^'  | Right assoc, push   | "AB"   | ['^', '^']
 * 'C'  | Operand             | "ABC"  | ['^', '^']
 * END  | Pop all             | "ABC^^"| []
 * 
 * Result: "ABC^^" (means A^(B^C), not (A^B)^C)
 * 
 * ============================================================================
 * PRECEDENCE & ASSOCIATIVITY TABLE
 * ============================================================================
 * 
 * Operator | Precedence | Associativity | Example
 * ---------|------------|---------------|------------------
 * ^        | 3 (High)   | Right to Left | A^B^C = A^(B^C)
 * *, /     | 2 (Medium) | Left to Right | A*B*C = (A*B)*C
 * +, -     | 1 (Low)    | Left to Right | A+B+C = (A+B)+C
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. **Ignoring Associativity**:  
 *    - A^B^C should be ABC^^ not AB^C^
 * 
 * 2. **Not Checking for '(' before popping**:
 *    - Can cause '(' to appear in output
 * 
 * 3. **Forgetting to pop remaining operators**:
 *    - Incomplete postfix expression
 * 
 * 4. **Wrong precedence comparison**:
 *    - Using < instead of <=
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Infix to Postfix Conversion ===\n")
    
    val converter = InfixToPostfix()
    
    // Test cases
    val testCases = listOf(
        "A+B" to "AB+",
        "A+B*C" to "ABC*+",
        "(A+B)*C" to "AB+C*",
        "A*(B+C)" to "ABC+*",
        "A+B-C" to "AB+C-",
        "A*B+C" to "AB*C+",
        "A+B+C" to "AB+C+",
        "A^B^C" to "ABC^^",
        "A*(B+C)/D" to "ABC+*D/",
        "((A+B)*C-D)/E" to "AB+C*D-E/",
        "A+B*C-D/E" to "ABC*+DE/-",
        "(A+B)*(C-D)" to "AB+CD-*",
        "A^B*C+D" to "AB^C*D+",
        "A+B^C*D" to "ABC^D*+"
    )
    
    println("Test 1: Basic Conversions")
    testCases.forEach { (infix, expected) ->
        val result = converter.convert(infix)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$infix\" → \"$result\" (expected: \"$expected\")")
    }
    println()
    
    // Test 2: Verbose mode for learning
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
        val postfix = converter.convert(infix)
        println("\"$infix\"")
        println("→ \"$postfix\"")
        println()
    }
    
    // Test 4: Edge cases
    println("Test 4: Edge Cases")
    val edgeCases = listOf(
        "A" to "A",
        "(A)" to "A",
        "A+B+C+D" to "AB+C+D+",
        "A*B*C*D" to "AB*C*D*",
        "((((A))))" to "A"
    )
    
    edgeCases.forEach { (infix, expected) ->
        val result = converter.convert(infix)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$infix\" → \"$result\"")
    }
}
