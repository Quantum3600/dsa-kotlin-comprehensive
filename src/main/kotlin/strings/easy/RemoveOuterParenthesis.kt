/**
 * ============================================================================
 * PROBLEM: Remove Outermost Parentheses
 * DIFFICULTY: Easy
 * CATEGORY: Strings - Easy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a valid parentheses string (a string consisting only of '(' and ')'), 
 * remove the outermost parentheses of every primitive string in the decomposition.
 * 
 * A primitive string is a valid parentheses string that cannot be split into two 
 * non-empty valid parentheses strings.
 * 
 * INPUT FORMAT:
 * - A string s containing only '(' and ')': s = "(()())(())"
 * 
 * OUTPUT FORMAT:
 * - String with outermost parentheses removed: "()()()"
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 10^5
 * - s is a valid parentheses string
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of nested parentheses like Russian nesting dolls. We want to remove only
 * the outermost doll (parentheses) but keep everything inside.
 * 
 * Example: "(()())(())" breaks into primitives: "(()())" and "(())"
 * - From "(()())" remove outer '(' and ')' → "()()"
 * - From "(())" remove outer '(' and ')' → "()"
 * - Result: "()()" + "()" = "()()()"
 * 
 * KEY INSIGHT:
 * Use a counter to track nesting depth:
 * - When we see '(' and depth is 0, it's an outer opening parenthesis (skip it)
 * - When we see ')' and depth becomes 0, it's an outer closing parenthesis (skip it)
 * - Everything else is inner content (keep it)
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result string and depth counter to 0
 * 2. For each character in string:
 *    a. If '(': 
 *       - Add to result only if depth > 0 (not outer)
 *       - Increment depth
 *    b. If ')':
 *       - Decrement depth
 *       - Add to result only if depth > 0 (not outer)
 * 3. Return result
 * 
 * VISUAL EXAMPLE:
 * Input: "(()())(())"
 * 
 * Step-by-step:
 * '(' depth=0→1, skip (outer)        result: ""
 * '(' depth=1→2, add                 result: "("
 * ')' depth=2→1, add                 result: "()"
 * '(' depth=1→2, add                 result: "()("
 * ')' depth=2→1, add                 result: "()()"
 * ')' depth=1→0, skip (outer)        result: "()()"
 * '(' depth=0→1, skip (outer)        result: "()()"
 * '(' depth=1→2, add                 result: "()()("
 * ')' depth=2→1, add                 result: "()()()"
 * ')' depth=1→0, skip (outer)        result: "()()()"
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Counter approach: O(n) time, O(n) space - OPTIMAL
 * 2. Stack approach: O(n) time, O(n) space - More space overhead
 * 3. String manipulation: O(n²) time, O(n) space - Inefficient
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through the string
 * - Each character processed exactly once
 * - All operations (counter increment/decrement, append) are O(1)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result string stores at most n-2 characters (removing outer pair)
 * - StringBuilder for efficient string building
 * - Counter variable uses O(1) space
 * - Overall: O(n) for the output
 * 
 * WHY THIS IS OPTIMAL:
 * Must examine each character to determine if it's outer or inner, so O(n) is optimal.
 * 
 * ============================================================================
 */

package strings.easy

class RemoveOuterParenthesis {
    
    /**
     * Removes the outermost parentheses from every primitive string
     * 
     * @param s Valid parentheses string
     * @return String with outermost parentheses removed
     */
    fun removeOuterParentheses(s: String): String {
        // StringBuilder for efficient string building
        val result = StringBuilder()
        
        // Counter tracks nesting depth
        // depth = 0 means we're at the outer level
        var depth = 0
        
        // Process each character
        for (char in s) {
            when (char) {
                '(' -> {
                    // If depth > 0, this is an inner opening parenthesis
                    // If depth == 0, this is an outer opening parenthesis (skip it)
                    if (depth > 0) {
                        result.append(char)
                    }
                    // Increment depth as we've seen an opening parenthesis
                    depth++
                }
                ')' -> {
                    // Decrement depth first as we're closing a parenthesis
                    depth--
                    // If depth > 0 after decrement, this is an inner closing parenthesis
                    // If depth == 0, this is an outer closing parenthesis (skip it)
                    if (depth > 0) {
                        result.append(char)
                    }
                }
            }
        }
        
        return result.toString()
    }
    
    /**
     * Alternative approach using stack for clarity (but more space)
     * Same complexity but easier to understand for beginners
     */
    fun removeOuterParenthesesStack(s: String): String {
        val result = StringBuilder()
        val stack = mutableListOf<Char>()
        
        for (char in s) {
            if (char == '(') {
                // Add to result if stack is not empty (not outer)
                if (stack.isNotEmpty()) {
                    result.append(char)
                }
                stack.add(char)
            } else {
                stack.removeAt(stack.size - 1)
                // Add to result if stack is not empty after removal (not outer)
                if (stack.isNotEmpty()) {
                    result.append(char)
                }
            }
        }
        
        return result.toString()
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "(()())(())"
 * 
 * DECOMPOSITION INTO PRIMITIVES:
 * - First primitive: "(()())"
 * - Second primitive: "(())"
 * 
 * PROCESSING FIRST PRIMITIVE "(()())":
 * - Remove outer '(' and ')'
 * - Result: "()()"
 * 
 * PROCESSING SECOND PRIMITIVE "(())":
 * - Remove outer '(' and ')'
 * - Result: "()"
 * 
 * FINAL OUTPUT: "()()" + "()" = "()()()"
 * 
 * ---
 * 
 * Example 2: s = "()()"
 * 
 * DECOMPOSITION:
 * - First primitive: "()"
 * - Second primitive: "()"
 * 
 * PROCESSING:
 * - From "()": remove outer → ""
 * - From "()": remove outer → ""
 * 
 * FINAL OUTPUT: "" + "" = ""
 * 
 * ---
 * 
 * Example 3: s = "(()()()()())"
 * 
 * DECOMPOSITION:
 * - Single primitive: "(()()()()())"
 * 
 * PROCESSING:
 * - Remove outer '(' and ')'
 * 
 * FINAL OUTPUT: "()()()()()"
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Simplest case "()" → ""
 * 2. Multiple primitives "()()" → ""
 * 3. Nested "(())" → "()"
 * 4. Deep nesting "((()))" → "(())"
 * 5. Multiple groups "(()())(())" → "()()()"
 * 6. All nested "(((())))" → "((()))"
 * 7. Complex pattern "(()())(())(()(()))" → "()()()()(())"
 * 8. Maximum nesting depth handled correctly
 * 9. Minimum string length (2) handled
 * 10. Valid parentheses assumption allows simple algorithm
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not tracking depth correctly
 * ❌ Adding to result after incrementing depth for '('
 * ✅ Add to result before incrementing (check if depth > 0 first)
 * 
 * MISTAKE 2: Wrong order for closing parenthesis
 * ❌ Checking depth before decrementing
 * ✅ Decrement first, then check if depth > 0
 * 
 * MISTAKE 3: Using string concatenation
 * ❌ result += char  // O(n) per operation due to string immutability
 * ✅ result.append(char)  // O(1) with StringBuilder
 * 
 * MISTAKE 4: Not understanding primitives
 * - Primitives are maximal valid parentheses substrings
 * - Cannot be split further into valid substrings
 * 
 * MISTAKE 5: Assuming fixed structure
 * - Don't assume specific pattern
 * - Algorithm should work for any valid parentheses string
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ String contains only parentheses
 * ✅ Parentheses are guaranteed to be valid
 * ✅ Need to process nested structures
 * ✅ Tracking depth/nesting level is required
 * 
 * SIMILAR PROBLEMS:
 * - Valid Parentheses (basic validation)
 * - Score of Parentheses
 * - Minimum Add to Make Parentheses Valid
 * - Generate Parentheses
 * - Longest Valid Parentheses
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Code Formatting**: Removing redundant brackets in expressions
 * 2. **Expression Simplification**: Simplifying mathematical expressions
 * 3. **Compiler Design**: Parsing and simplifying syntax trees
 * 4. **Text Processing**: Cleaning up nested markup/tags
 * 5. **Data Serialization**: Optimizing nested structure representation
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = RemoveOuterParenthesis()
    
    println("=== Testing Remove Outer Parentheses ===\n")
    
    // Test 1: Basic example with nested parentheses
    println("Test 1: Multiple primitives with nesting")
    val test1 = "(()())(())"
    println("Input: $test1")
    println("Output: ${solution.removeOuterParentheses(test1)}")
    println("Expected: ()()()\n")
    
    // Test 2: Simple case
    println("Test 2: Two simple primitives")
    val test2 = "()()"
    println("Input: $test2")
    println("Output: ${solution.removeOuterParentheses(test2)}")
    println("Expected: (empty string)\n")
    
    // Test 3: Single primitive with nesting
    println("Test 3: Single nested primitive")
    val test3 = "(()()()()())"
    println("Input: $test3")
    println("Output: ${solution.removeOuterParentheses(test3)}")
    println("Expected: ()()()()()\n")
    
    // Test 4: Deep nesting
    println("Test 4: Deep nesting")
    val test4 = "((()))"
    println("Input: $test4")
    println("Output: ${solution.removeOuterParentheses(test4)}")
    println("Expected: (())\n")
    
    // Test 5: Complex pattern
    println("Test 5: Complex mixed pattern")
    val test5 = "(()())(())(()(()))"
    println("Input: $test5")
    println("Output: ${solution.removeOuterParentheses(test5)}")
    println("Expected: ()()()()(())\n")
    
    // Test 6: Simplest primitive
    println("Test 6: Single primitive pair")
    val test6 = "()"
    println("Input: $test6")
    println("Output: ${solution.removeOuterParentheses(test6)}")
    println("Expected: (empty string)\n")
    
    // Test 7: Alternating pattern
    println("Test 7: Alternating pattern")
    val test7 = "()()()()"
    println("Input: $test7")
    println("Output: ${solution.removeOuterParentheses(test7)}")
    println("Expected: (empty string)\n")
    
    // Test 8: Using stack approach
    println("Test 8: Stack approach comparison")
    val test8 = "(()())(())"
    println("Input: $test8")
    println("Counter approach: ${solution.removeOuterParentheses(test8)}")
    println("Stack approach: ${solution.removeOuterParenthesesStack(test8)}")
    println("Both should give: ()()()\n")
    
    // Test 9: Very nested
    println("Test 9: Maximum nesting")
    val test9 = "(((()()())))((()))"
    println("Input: $test9")
    println("Output: ${solution.removeOuterParentheses(test9)}")
    println("Expected: ((()()()))(())\n")
    
    // Test 10: Long string
    println("Test 10: Long string with multiple primitives")
    val test10 = "()()()()()()()()"
    println("Input: $test10")
    println("Output: ${solution.removeOuterParentheses(test10)}")
    println("Expected: (empty string)\n")
}
