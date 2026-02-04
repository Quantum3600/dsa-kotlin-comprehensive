/**
 * ============================================================================
 * PROBLEM: Valid Parenthesis String
 * DIFFICULTY: Easy
 * CATEGORY: Greedy, Strings
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string containing '(', ')', and '*', determine if string is valid.
 * '*' can be treated as '(' or ')' or empty string.
 * Valid means all parentheses are properly closed and balanced.
 * 
 * INPUT FORMAT:
 * - s: "(*))" (string with parentheses and asterisks)
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if valid, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 100
 * - s[i] is '(', ')', or '*'
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Track range of possible open parentheses count [minOpen, maxOpen].
 * '*' gives us flexibility - it can act as '(', ')', or nothing.
 * 
 * KEY INSIGHT:
 * - minOpen: assuming '*' as ')' or empty (pessimistic)
 * - maxOpen: assuming '*' as '(' (optimistic)
 * - If maxOpen < 0: too many ')', invalid
 * - If minOpen < 0: reset to 0 (can use '*' as empty)
 * - At end: minOpen must be 0 (all opens closed)
 * 
 * ALGORITHM STEPS:
 * 1. Initialize minOpen = 0, maxOpen = 0
 * 2. For each character:
 *    - '(': increment both
 *    - ')': decrement both
 *    - '*': decrement minOpen (treat as ')'), increment maxOpen (treat as '(')
 * 3. If maxOpen < 0: return false (too many closes)
 * 4. If minOpen < 0: reset to 0 (use '*' as empty)
 * 5. At end: return minOpen == 0
 * 
 * VISUAL EXAMPLE 1:
 * s = "(*))"
 * 
 * i=0 '(': minOpen=1, maxOpen=1
 * i=1 '*': minOpen=0, maxOpen=2
 * i=2 ')': minOpen=-1→0, maxOpen=1
 * i=3 ')': minOpen=-1→0, maxOpen=0
 * Result: minOpen=0 → true ✓
 * 
 * VISUAL EXAMPLE 2:
 * s = "(()"
 * 
 * i=0 '(': minOpen=1, maxOpen=1
 * i=1 '(': minOpen=2, maxOpen=2
 * i=2 ')': minOpen=1, maxOpen=1
 * Result: minOpen=1 → false ✗ (unclosed '(')
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pointer greedy (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Dynamic Programming: O(n^3) time, O(n^2) space - Overkill
 * 3. Stack-based: O(n) time, O(n) space - More space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through string: O(n)
 * - Each character processed in O(1) time
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using two integer variables
 * - No additional data structures
 * 
 * ============================================================================
 */

package greedy.easy

class ValidParenthesisString {
    
    /**
     * Checks if parenthesis string is valid
     */
    fun checkValidString(s: String): Boolean {
        var minOpen = 0  // Minimum possible open parentheses
        var maxOpen = 0  // Maximum possible open parentheses
        
        for (char in s) {
            when (char) {
                '(' -> {
                    minOpen++
                    maxOpen++
                }
                ')' -> {
                    minOpen--
                    maxOpen--
                }
                '*' -> {
                    minOpen--  // Treat '*' as ')'
                    maxOpen++  // Treat '*' as '('
                }
            }
            
            // Too many ')' - impossible to balance
            if (maxOpen < 0) return false
            
            // Can't have negative opens - use '*' as empty
            if (minOpen < 0) minOpen = 0
        }
        
        // All opens must be closed
        return minOpen == 0
    }
    
    /**
     * Alternative: Two-pass approach
     * Left-to-right: check if too many ')'
     * Right-to-left: check if too many '('
     */
    fun checkValidStringTwoPass(s: String): Boolean {
        var balance = 0
        
        // Left to right: treat '*' as '('
        for (char in s) {
            if (char == '(' || char == '*') balance++
            else balance--
            
            if (balance < 0) return false
        }
        
        balance = 0
        
        // Right to left: treat '*' as ')'
        for (i in s.length - 1 downTo 0) {
            if (s[i] == ')' || s[i] == '*') balance++
            else balance--
            
            if (balance < 0) return false
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty string: "" → true
 * 2. Single '(': "(" → false
 * 3. Single ')': ")" → false
 * 4. Single '*': "*" → true
 * 5. Only stars: "***" → true
 * 6. Balanced: "()" → true
 * 7. With stars: "(*)" → true
 * 8. Multiple stars: "(*))" → true
 * 9. Invalid: "(()" → false
 * 10. Complex: "(*)(*" → true
 * 
 * APPLICATIONS:
 * - Expression validation
 * - Parser implementation
 * - Compiler syntax checking
 * - Code editor validation
 * 
 * ============================================================================
 */

fun main() {
    val solution = ValidParenthesisString()
    
    println("Valid Parenthesis String - Test Cases")
    println("=======================================\n")
    
    println("Test 1: \"()\"")
    println("Result: ${solution.checkValidString("()")}")
    println("Expected: true ✓\n")
    
    println("Test 2: \"(*)\"")
    println("Result: ${solution.checkValidString("(*)")}")
    println("Expected: true ✓\n")
    
    println("Test 3: \"(*))\"")
    println("Result: ${solution.checkValidString("(*))")}")
    println("Expected: true ✓\n")
    
    println("Test 4: \"(()\"")
    println("Result: ${solution.checkValidString("(()")}")
    println("Expected: false ✓\n")
    
    println("Test 5: \"*\"")
    println("Result: ${solution.checkValidString("*")}")
    println("Expected: true ✓\n")
}
