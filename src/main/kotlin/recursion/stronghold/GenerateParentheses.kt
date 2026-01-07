/**
 * ============================================================================
 * PROBLEM: Generate Parentheses
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Stronghold
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given n pairs of parentheses, write a function to generate all combinations
 * of well-formed (valid) parentheses.
 * 
 * A valid parentheses combination means:
 * - Every opening bracket has a corresponding closing bracket
 * - Opening brackets must come before their corresponding closing brackets
 * - Brackets are properly nested
 * 
 * INPUT FORMAT:
 * - An integer n representing the number of pairs of parentheses
 * 
 * OUTPUT FORMAT:
 * - A list of strings containing all valid combinations
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 8
 * 
 * EXAMPLES:
 * Input: n = 1
 * Output:  ["()"]
 * 
 * Input: n = 2
 * Output: ["(())", "()()"]
 * 
 * Input: n = 3
 * Output: ["((()))", "(()())", "(())()", "()(())", "()()()"]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of building a valid parentheses string character by character. 
 * At each step, we can add either '(' or ')' - but with constraints:
 * 
 * 1. We can add '(' if we haven't used all opening brackets yet
 * 2. We can add ')' ONLY if it won't exceed the number of '(' used so far
 * 
 * This ensures we never have more ')' than '(' at any point, which would
 * make the string invalid.
 * 
 * KEY INSIGHT: 
 * - Use BACKTRACKING to explore all possibilities
 * - Track:  open count (how many '(' used), close count (how many ')' used)
 * - Only add ')' when close < open (ensures validity)
 * - Only add '(' when open < n (ensures we don't exceed limit)
 * 
 * ALGORITHM STEPS:
 * 1. Start with empty string, open=0, close=0
 * 2. If current string length = 2*n, we have a valid combination → add to result
 * 3. If open < n, we can add '(' → recurse with open+1
 * 4. If close < open, we can add ')' → recurse with close+1
 * 5.  Backtrack and explore other branches
 * 
 * VISUAL EXAMPLE (n = 2):
 * ```
 *                          ""
 *                     (open=0, close=0)
 *                          |
 *                         "("
 *                     (open=1, close=0)
 *                     /           \
 *                   "(("          "()"
 *              (open=2,close=0)  (open=1,close=1)
 *                   |               |
 *                  "(()"           "()("
 *              (open=2,close=1)  (open=2,close=1)
 *                   |               |
 *                  "(())"          "()()"
 *              (open=2,close=2)  (open=2,close=2)
 *                   ✓               ✓
 * ```
 * 
 * WHY THIS WORKS:
 * - At any point, close <= open ensures no invalid ')' placement
 * - open <= n ensures we don't exceed available opening brackets
 * - When length = 2*n with these constraints, string is guaranteed valid
 * 
 * DECISION TREE PRUNING:
 * - We never explore branches where close > open (invalid)
 * - We never explore branches where open > n (exceeds limit)
 * - This significantly reduces the search space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(4^n / √n) - Catalan number bound
 * - More precisely: O(C_n) where C_n = (2n)! / ((n+1)! * n!)
 * - The number of valid parentheses combinations is the nth Catalan number
 * - For n=3: C_3 = 5 combinations
 * - For n=4: C_4 = 14 combinations
 * 
 * WHY 4^n / √n: 
 * - At each position, we have at most 2 choices (add '(' or ')')
 * - Total positions: 2n
 * - Upper bound: 2^(2n) = 4^n possibilities
 * - But with pruning (close <= open), actual combinations ≈ 4^n / √n
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion stack depth: O(n) - at most 2n levels
 * - String builder: O(n) - stores current combination
 * - Result list: O(4^n / √n) but not counted in space complexity (output)
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: Backtracking (Implemented)
 * Time:  O(4^n / √n), Space: O(n)
 * ✅ Most efficient
 * ✅ Generates only valid combinations
 * ✅ Easy to understand
 * 
 * APPROACH 2: Brute Force + Validation
 * Time: O(2^(2n) * n), Space: O(n)
 * - Generate all 2^(2n) possible strings
 * - Validate each one (O(n) per string)
 * ❌ Extremely inefficient
 * ❌ Generates many invalid combinations
 * 
 * APPROACH 3: Dynamic Programming
 * Time: O(4^n / √n), Space: O(4^n / √n)
 * - Build combinations from smaller subproblems
 * - For n, use combinations from 0 to n-1
 * ❌ More complex
 * ❌ Higher space complexity
 * 
 * ============================================================================
 * EDGE CASES & COMMON MISTAKES
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 1: Only "()"
 * 2. n = 0: Should return [""] (empty string is valid)
 * 
 * COMMON MISTAKES: 
 * 1. Adding ')' when close >= open → Invalid combinations
 * 2. Not tracking open and close separately → Can't determine validity
 * 3. Using indices instead of counts → Confusing and error-prone
 * 4. Forgetting to backtrack → StringBuilder not reset properly
 * 
 * WHY CLOSE < OPEN IS CRUCIAL:
 * - If close >= open, adding ')' would create ")(" pattern → invalid
 * - Example: "())" has close > open at position 3 → invalid
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Valid Parentheses (Easy) - Check if a string is valid
 * 2. Longest Valid Parentheses (Hard) - Find longest valid substring
 * 3. Remove Invalid Parentheses (Hard) - Remove minimum to make valid
 * 4. Different Ways to Add Parentheses (Medium) - Expression evaluation
 * 5. Minimum Add to Make Parentheses Valid (Medium) - Count additions needed
 * 
 * ============================================================================
 * LEARNING RESOURCES
 * ============================================================================
 * 
 * CONCEPTS DEMONSTRATED:
 * - Backtracking algorithm pattern
 * - Decision trees and pruning
 * - Catalan numbers in combinatorics
 * - Constraint-based recursion
 * 
 * PRACTICE PROGRESSION:
 * 1. Master this problem with n <= 3
 * 2. Extend to different bracket types:  [], {}
 * 3. Try "Valid Parentheses" checker first (easier)
 * 4. Move to "Remove Invalid Parentheses" (harder)
 */

package recursion.stronghold

class GenerateParentheses {
    
    /**
     * Generates all valid combinations of n pairs of parentheses using backtracking. 
     * 
     * @param n Number of pairs of parentheses
     * @return List of all valid combinations
     * 
     * Time Complexity: O(4^n / √n)
     * Space Complexity: O(n)
     */
    fun generateParenthesis(n: Int): List<String> {
        val result = mutableListOf<String>()
        val current = StringBuilder()
        backtrack(result, current, 0, 0, n)
        return result
    }
    
    /**
     * Helper function to perform backtracking. 
     * 
     * @param result List to store all valid combinations
     * @param current Current combination being built
     * @param open Number of opening brackets used so far
     * @param close Number of closing brackets used so far
     * @param max Maximum number of pairs (n)
     */
    private fun backtrack(
        result: MutableList<String>,
        current: StringBuilder,
        open: Int,
        close: Int,
        max: Int
    ) {
        // Base case: If we've used all brackets, add to result
        if (current.length == max * 2) {
            result.add(current.toString())
            return
        }
        
        // Choice 1: Add opening bracket (if we haven't used all)
        if (open < max) {
            current.append('(')
            backtrack(result, current, open + 1, close, max)
            current.deleteCharAt(current.length - 1) // Backtrack
        }
        
        // Choice 2: Add closing bracket (only if it won't make string invalid)
        if (close < open) {
            current.append(')')
            backtrack(result, current, open, close + 1, max)
            current. deleteCharAt(current.length - 1) // Backtrack
        }
    }
    
    /**
     * Alternative approach:  Using string concatenation instead of StringBuilder. 
     * Less efficient but easier to understand (no explicit backtracking needed).
     * 
     * Time Complexity: O(4^n / √n)
     * Space Complexity: O(n)
     */
    fun generateParenthesisAlternative(n: Int): List<String> {
        val result = mutableListOf<String>()
        
        fun generate(current: String, open: Int, close: Int) {
            // Base case
            if (current.length == n * 2) {
                result.add(current)
                return
            }
            
            // Add '(' if possible
            if (open < n) {
                generate(current + "(", open + 1, close)
            }
            
            // Add ')' if valid
            if (close < open) {
                generate(current + ")", open, close + 1)
            }
        }
        
        generate("", 0, 0)
        return result
    }
    
    /**
     * Validates if a given string has valid parentheses.
     * Useful for testing and understanding the problem.
     * 
     * @param s String to validate
     * @return True if valid, false otherwise
     * 
     * Time Complexity:  O(n)
     * Space Complexity: O(1)
     */
    fun isValid(s: String): Boolean {
        var balance = 0
        for (char in s) {
            if (char == '(') {
                balance++
            } else if (char == ')') {
                balance--
                if (balance < 0) return false // More ')' than '(' so far
            }
        }
        return balance == 0 // Equal number of '(' and ')'
    }
}

fun main() {
    val generator = GenerateParentheses()
    
    // Test Case 1: n = 1
    println("Test Case 1: n = 1")
    println("Expected: [\"()\"]")
    val result1 = generator.generateParenthesis(1)
    println("Actual:  $result1")
    println("Valid: ${result1.all { generator.isValid(it) }}")
    println()
    
    // Test Case 2: n = 2
    println("Test Case 2: n = 2")
    println("Expected: [\"(())\", \"()()\"]")
    val result2 = generator. generateParenthesis(2)
    println("Actual: $result2")
    println("Count: ${result2.size}")
    println("Valid: ${result2.all { generator.isValid(it) }}")
    println()
    
    // Test Case 3: n = 3
    println("Test Case 3: n = 3")
    println("Expected: [\"((()))\", \"(()())\", \"(())()\", \"()(())\", \"()()()\"]")
    val result3 = generator.generateParenthesis(3)
    println("Actual: $result3")
    println("Count: ${result3.size} (should be 5)")
    println("Valid: ${result3.all { generator.isValid(it) }}")
    println()
    
    // Test Case 4: n = 4 (Catalan number C_4 = 14)
    println("Test Case 4: n = 4")
    val result4 = generator.generateParenthesis(4)
    println("Count: ${result4.size} (should be 14)")
    println("Valid: ${result4.all { generator.isValid(it) }}")
    println("Sample: ${result4.take(5)}")
    println()
    
    // Test Case 5: Edge case n = 0
    println("Test Case 5: n = 0 (Edge case)")
    val result5 = generator.generateParenthesis(0)
    println("Expected: [\"\"]")
    println("Actual: $result5")
    println()
    
    // Test Case 6: Testing alternative approach
    println("Test Case 6: Alternative approach with n = 3")
    val result6 = generator.generateParenthesisAlternative(3)
    println("Actual: $result6")
    println("Count: ${result6.size}")
    println("Valid: ${result6.all { generator.isValid(it) }}")
    println()
    
    // Test Case 7: Catalan numbers verification
    println("Test Case 7: Catalan numbers verification")
    println("n=1: ${generator.generateParenthesis(1).size} (expected:  1)")
    println("n=2: ${generator.generateParenthesis(2).size} (expected: 2)")
    println("n=3: ${generator.generateParenthesis(3).size} (expected: 5)")
    println("n=4: ${generator.generateParenthesis(4).size} (expected: 14)")
    println("n=5: ${generator.generateParenthesis(5).size} (expected: 42)")
    println()
    
    // Test Case 8: Performance test for larger n
    println("Test Case 8: Performance test n = 6")
    val startTime = System.currentTimeMillis()
    val result8 = generator.generateParenthesis(6)
    val endTime = System.currentTimeMillis()
    println("Count: ${result8.size} (expected: 132)")
    println("Time taken: ${endTime - startTime}ms")
    println("Valid:  ${result8.all { generator. isValid(it) }}")
}
