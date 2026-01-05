/**
 * ============================================================================
 * PROBLEM:  Valid Parentheses
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid. 
 * 
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets
 * 2. Open brackets must be closed in the correct order
 * 3. Every close bracket has a corresponding open bracket of the same type
 * 
 * INPUT FORMAT:
 * - String containing only brackets:  (), {}, []
 * - Length:  0 to 10^4
 * 
 * OUTPUT FORMAT:
 * - true if valid
 * - false otherwise
 * 
 * EXAMPLES:
 * - "()" → true
 * - "()[]{}" → true
 * - "(]" → false (wrong type)
 * - "([)]" → false (wrong order)
 * - "{[]}" → true (nested correctly)
 * 
 * CONSTRAINTS:
 * - 0 <= s.length <= 10^4
 * - s consists of parentheses only '()[]{}'
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * WHY USE A STACK?
 * - Need to match MOST RECENT opening bracket
 * - Stack provides LIFO:  Last In, First Out
 * - Perfect for tracking nested structures! 
 * 
 * INTUITION:
 * Think of it like Russian dolls (matryoshka):
 * - Must close inner dolls before closing outer dolls
 * - Can't close a doll that wasn't opened
 * - Can't close wrong type of doll
 * 
 * Example: "({[]})"
 * - Open ( → must be closed last
 * - Open { → must be closed before (
 * - Open [ → must be closed before {
 * - Close ] → matches [, remove both
 * - Close } → matches {, remove both
 * - Close ) → matches (, remove both
 * - All matched!  Valid! 
 * 
 * ALGORITHM STEPS:
 * 1. Create empty stack
 * 2. For each character in string:
 *    a. If opening bracket:  push to stack
 *    b. If closing bracket:
 *       - If stack empty: return false (no matching opening)
 *       - Pop from stack
 *       - If popped bracket doesn't match: return false
 * 3. After processing all characters:
 *    - If stack empty: return true (all matched)
 *    - If stack not empty: return false (unmatched openings)
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: "({[]})"
 * 
 * Process '(':
 * Stack: ['(']
 * 
 * Process '{':
 * Stack: ['(', '{']
 * 
 * Process '[':
 * Stack: ['(', '{', '[']
 * 
 * Process ']':
 * - Pop '[', matches!  ✓
 * Stack: ['(', '{']
 * 
 * Process '}':
 * - Pop '{', matches! ✓
 * Stack: ['(']
 * 
 * Process ')':
 * - Pop '(', matches! ✓
 * Stack: []
 * 
 * Stack empty → Valid!
 * ```
 * 
 * EDGE CASES:
 * 1. Empty string "" → true (nothing to match)
 * 2. Single bracket "(" → false (unmatched)
 * 3. Wrong type "(]" → false (mismatch)
 * 4. Wrong order "([)]" → false
 * 5. Only opening "(((" → false
 * 6. Only closing ")))" → false
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - n = length of string
 * - Process each character exactly once
 * - Stack operations (push/pop) are O(1)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Worst case: All opening brackets → stack size = n
 * - Example: "((((((" → stack grows to n
 * - Best case:  Immediately matched → O(1)
 * - Average case: O(n/2) = O(n)
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * 1. **Counter-based (Only for single type)**:
 *    - Works only for one bracket type like ()
 *    - Counter++  for '(', counter-- for ')'
 *    - Fails for multiple types:  can't distinguish
 *    - Not suitable for this problem
 * 
 * 2. **Recursion**:
 *    - Remove matched pairs repeatedly
 *    - Inefficient:  O(n²) time
 *    - Not recommended
 * 
 * 3. **Stack with HashMap**:
 *    - Store matching pairs in map
 *    - Cleaner code, same complexity
 *    - Good for many bracket types
 * 
 * ============================================================================
 */

package stackqueue.basics

import java.util.Stack

/**
 * Solution for Valid Parentheses problem
 */
class ValidParentheses {
    
    /**
     * Check if string has valid parentheses
     * TIME: O(n), SPACE: O(n)
     * 
     * @param s Input string containing brackets
     * @return true if valid, false otherwise
     */
    fun isValid(s: String): Boolean {
        // Edge case: empty string is valid
        if (s.isEmpty()) return true
        
        // Edge case: odd length can't be balanced
        if (s.length % 2 != 0) return false
        
        // Stack to store opening brackets
        val stack = Stack<Char>()
        
        // Process each character
        for (char in s) {
            when (char) {
                // Opening brackets:  push to stack
                '(', '{', '[' -> {
                    stack.push(char)
                }
                
                // Closing brackets:  check matching
                ')' -> {
                    // No matching opening bracket
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false
                    }
                }
                '}' -> {
                    if (stack.isEmpty() || stack.pop() != '{') {
                        return false
                    }
                }
                ']' -> {
                    if (stack. isEmpty() || stack.pop() != '[') {
                        return false
                    }
                }
            }
        }
        
        // Valid only if all brackets are matched (stack is empty)
        return stack.isEmpty()
    }
    
    /**
     * Alternative implementation using HashMap for cleaner code
     * TIME: O(n), SPACE: O(n)
     */
    fun isValidWithMap(s: String): Boolean {
        if (s.isEmpty()) return true
        if (s.length % 2 != 0) return false
        
        // Map closing brackets to their opening counterparts
        val matchingBrackets = mapOf(
            ')' to '(',
            '}' to '{',
            ']' to '['
        )
        
        val stack = Stack<Char>()
        
        for (char in s) {
            when {
                // If it's an opening bracket
                char in matchingBrackets. values -> {
                    stack.push(char)
                }
                // If it's a closing bracket
                char in matchingBrackets. keys -> {
                    if (stack.isEmpty() || stack.pop() != matchingBrackets[char]) {
                        return false
                    }
                }
            }
        }
        
        return stack.isEmpty()
    }
    
    /**
     * Verbose version with detailed step tracing (for learning)
     */
    fun isValidVerbose(s: String): Boolean {
        println("Checking: \"$s\"")
        
        if (s.isEmpty()) {
            println("Empty string → Valid")
            return true
        }
        
        if (s.length % 2 != 0) {
            println("Odd length → Invalid")
            return false
        }
        
        val stack = Stack<Char>()
        
        for ((index, char) in s.withIndex()) {
            print("Step ${index + 1}: '$char' → ")
            
            when (char) {
                '(', '{', '[' -> {
                    stack.push(char)
                    println("Push '$char', Stack: $stack")
                }
                ')' -> {
                    if (stack.isEmpty()) {
                        println("Empty stack, no match → Invalid")
                        return false
                    }
                    val popped = stack.pop()
                    if (popped != '(') {
                        println("Popped '$popped', doesn't match → Invalid")
                        return false
                    }
                    println("Matched with '$popped', Stack: $stack")
                }
                '}' -> {
                    if (stack.isEmpty()) {
                        println("Empty stack, no match → Invalid")
                        return false
                    }
                    val popped = stack. pop()
                    if (popped != '{') {
                        println("Popped '$popped', doesn't match → Invalid")
                        return false
                    }
                    println("Matched with '$popped', Stack: $stack")
                }
                ']' -> {
                    if (stack.isEmpty()) {
                        println("Empty stack, no match → Invalid")
                        return false
                    }
                    val popped = stack.pop()
                    if (popped != '[') {
                        println("Popped '$popped', doesn't match → Invalid")
                        return false
                    }
                    println("Matched with '$popped', Stack: $stack")
                }
            }
        }
        
        val isValid = stack.isEmpty()
        println("Final stack: $stack → ${if (isValid) "Valid" else "Invalid (unmatched openings)"}")
        return isValid
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: "({[]})"
 * 
 * Index 0: '(' → Opening, push → Stack: ['(']
 * Index 1: '{' → Opening, push → Stack: ['(', '{']
 * Index 2: '[' → Opening, push → Stack: ['(', '{', '[']
 * Index 3: ']' → Closing, pop '[', matches ✓ → Stack: ['(', '{']
 * Index 4: '}' → Closing, pop '{', matches ✓ → Stack: ['(']
 * Index 5: ')' → Closing, pop '(', matches ✓ → Stack: []
 * Stack empty → VALID
 * 
 * Example 2: "([)]"
 * 
 * Index 0: '(' → Opening, push → Stack: ['(']
 * Index 1: '[' → Opening, push → Stack: ['(', '[']
 * Index 2: ')' → Closing, pop '[', doesn't match '(' ✗ → INVALID
 * 
 * Example 3: "((("
 * 
 * Index 0: '(' → Opening, push → Stack: ['(']
 * Index 1: '(' → Opening, push → Stack: ['(', '(']
 * Index 2: '(' → Opening, push → Stack:  ['(', '(', '(']
 * Stack not empty → INVALID (unmatched openings)
 * 
 * Example 4: ")))"
 * 
 * Index 0: ')' → Closing, stack empty ✗ → INVALID
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Forgetting to check if stack is empty before popping
 *    → Results in EmptyStackException
 * 
 * 2. Not checking stack emptiness at the end
 *    → "(((" would incorrectly return true
 * 
 * 3. Using wrong matching pairs
 *    → Must match '(' with ')', not with ']'
 * 
 * 4. Not handling empty string
 *    → Empty string is valid by definition
 * 
 * 5. Not optimizing odd length check
 *    → Odd length can never be balanced
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Valid Parentheses Problem ===\n")
    
    val solution = ValidParentheses()
    
    // Test cases with expected results
    val testCases = listOf(
        "()" to true,
        "()[]{}" to true,
        "(]" to false,
        "([)]" to false,
        "{[]}" to true,
        "" to true,
        "(" to false,
        ")" to false,
        "(((" to false,
        ")))" to false,
        "{[()]}" to true,
        "{[(])}" to true,
        "{{[[(())]]}}" to true,
        "{{[[(()])]}" to false,
        "({})" to true,
        "([{}])" to true
    )
    
    println("Test 1: Basic Test Cases")
    testCases.forEach { (input, expected) ->
        val result = solution.isValid(input)
        val status = if (result == expected) "✓" else "✗"
        println("$status \"$input\" → $result (expected: $expected)")
    }
    println()
    
    // Test 2: Using map-based implementation
    println("Test 2: Map-Based Implementation")
    val testInputs = listOf("()", "([)]", "{[]}")
    testInputs.forEach { input ->
        val result = solution.isValidWithMap(input)
        println("\"$input\" → $result")
    }
    println()
    
    // Test 3: Verbose mode
