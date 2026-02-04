/**
 * ============================================================================
 * PROBLEM: Remove Invalid Parentheses
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking/BFS
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string containing parentheses and other characters, remove the
 * minimum number of invalid parentheses to make the string valid. Return all
 * possible valid strings with minimum removals.
 * 
 * A string is valid if:
 * - Every opening parenthesis '(' has a matching closing parenthesis ')'
 * - Every closing parenthesis ')' has a matching opening parenthesis before it
 * 
 * INPUT FORMAT:
 * - s: String containing letters, '(', and ')'
 * 
 * OUTPUT FORMAT:
 * - List of all valid strings with minimum removals
 * - Empty list if input is empty
 * - Results should be unique (no duplicates)
 * 
 * CONSTRAINTS:
 * - 0 <= s.length <= 25
 * - s consists of lowercase English letters and parentheses
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Two main approaches:
 * 1. BFS: Try removing each parenthesis and check if result is valid
 * 2. DFS/Backtracking: Calculate minimum removals needed, then generate all
 *    valid strings by removing exactly that many parentheses
 * 
 * APPROACH 1: BFS (Level-order exploration)
 * - Start with original string
 * - Generate all possible strings by removing one parenthesis
 * - Check if any result is valid -> if yes, stop (minimum removals found)
 * - Otherwise, continue to next level
 * - Use set to avoid duplicates
 * 
 * APPROACH 2: DFS/Backtracking (Implemented)
 * - First pass: Count minimum left and right parentheses to remove
 * - Second pass: Generate all valid strings with exactly those removals
 * - Track open count to ensure validity during construction
 * 
 * VISUAL EXAMPLE:
 * 
 * Input: "()())()"
 * 
 * Invalid: ()())() - one extra ')'
 * 
 * Remove ')' at index 4:
 *   Result: "()()()" ✓ Valid!
 * 
 * Output: ["()()()"]
 * 
 * Another example: "(()"
 * Remove '(' at index 0: "()" ✓
 * Remove '(' at index 1: "()" ✓ (duplicate)
 * 
 * Output: ["()"]
 * 
 * ALGORITHM STEPS (DFS):
 * 1. Count minimum removals needed:
 *    - leftRem: excess opening parentheses
 *    - rightRem: excess closing parentheses
 * 2. Backtrack to generate valid strings:
 *    - For each character:
 *      a. If letter, always include
 *      b. If '(' and leftRem > 0, try removing it
 *      c. If ')' and rightRem > 0, try removing it
 *      d. Try keeping parenthesis if it maintains validity
 * 3. Base case: leftRem = rightRem = 0 and string is valid
 * 4. Use set to store results (handles duplicates)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^N)
 * - In worst case, try all subsets of parentheses
 * - Pruning significantly reduces actual complexity
 * - Checking validity: O(N)
 * - Overall: O(2^N * N)
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack: O(N)
 * - Result set: O(2^N) in worst case
 * - Current string builder: O(N)
 * 
 * ============================================================================
 */

package recursion.hard

class RemoveInvalidParentheses {
    
    /**
     * Remove minimum invalid parentheses
     * TIME: O(2^N), SPACE: O(N)
     * 
     * @param s Input string
     * @return List of all valid strings with minimum removals
     */
    fun removeInvalidParentheses(s: String): List<String> {
        val result = mutableSetOf<String>()
        
        if (s.isEmpty()) return listOf("")
        
        // Calculate minimum removals needed
        val (leftRem, rightRem) = calculateRemovalsNeeded(s)
        
        // Generate all valid strings
        backtrack(s, 0, leftRem, rightRem, 0, StringBuilder(), result)
        
        return result.toList()
    }
    
    /**
     * Calculate minimum left and right parentheses to remove
     */
    private fun calculateRemovalsNeeded(s: String): Pair<Int, Int> {
        var leftRem = 0
        var rightRem = 0
        
        for (ch in s) {
            when (ch) {
                '(' -> leftRem++
                ')' -> {
                    if (leftRem > 0) {
                        leftRem--  // Match with previous '('
                    } else {
                        rightRem++  // Excess ')'
                    }
                }
            }
        }
        
        return Pair(leftRem, rightRem)
    }
    
    /**
     * Backtracking to generate valid strings
     * 
     * @param s Original string
     * @param index Current position
     * @param leftRem Number of '(' left to remove
     * @param rightRem Number of ')' left to remove
     * @param openCount Current count of unmatched '('
     * @param current Current string being built
     * @param result Set to store valid results
     */
    private fun backtrack(
        s: String,
        index: Int,
        leftRem: Int,
        rightRem: Int,
        openCount: Int,
        current: StringBuilder,
        result: MutableSet<String>
    ) {
        // Base case: processed all characters
        if (index == s.length) {
            if (leftRem == 0 && rightRem == 0 && openCount == 0) {
                result.add(current.toString())
            }
            return
        }
        
        val ch = s[index]
        
        when (ch) {
            '(' -> {
                // Option 1: Remove this '('
                if (leftRem > 0) {
                    backtrack(s, index + 1, leftRem - 1, rightRem, openCount, current, result)
                }
                
                // Option 2: Keep this '('
                current.append(ch)
                backtrack(s, index + 1, leftRem, rightRem, openCount + 1, current, result)
                current.deleteCharAt(current.length - 1)  // Backtrack
            }
            ')' -> {
                // Option 1: Remove this ')'
                if (rightRem > 0) {
                    backtrack(s, index + 1, leftRem, rightRem - 1, openCount, current, result)
                }
                
                // Option 2: Keep this ')' if we have unmatched '('
                if (openCount > 0) {
                    current.append(ch)
                    backtrack(s, index + 1, leftRem, rightRem, openCount - 1, current, result)
                    current.deleteCharAt(current.length - 1)  // Backtrack
                }
            }
            else -> {
                // Regular character: always include
                current.append(ch)
                backtrack(s, index + 1, leftRem, rightRem, openCount, current, result)
                current.deleteCharAt(current.length - 1)  // Backtrack
            }
        }
    }
    
    /**
     * Check if a string has valid parentheses
     */
    fun isValid(s: String): Boolean {
        var count = 0
        for (ch in s) {
            when (ch) {
                '(' -> count++
                ')' -> {
                    count--
                    if (count < 0) return false
                }
            }
        }
        return count == 0
    }
    
    /**
     * Get minimum number of removals needed
     */
    fun getMinimumRemovals(s: String): Int {
        val (leftRem, rightRem) = calculateRemovalsNeeded(s)
        return leftRem + rightRem
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: "()())()"
 * 
 * Step 1: Calculate removals needed
 * - Process each character:
 *   '(': leftRem = 1
 *   ')': leftRem = 0 (matched)
 *   '(': leftRem = 1
 *   ')': leftRem = 0 (matched)
 *   ')': rightRem = 1 (excess!)
 *   '(': leftRem = 1
 *   ')': leftRem = 0 (matched)
 * Result: leftRem = 0, rightRem = 1
 * 
 * Step 2: Generate valid strings (remove 1 ')')
 * Try removing ')' at each position...
 * Valid: "()()()" (removed ')' at index 4)
 * 
 * OUTPUT: ["()()()"]
 * 
 * ============================================================================
 * EXAMPLE 2
 * ============================================================================
 * 
 * INPUT: "(a)())()"
 * 
 * Calculate removals: leftRem = 0, rightRem = 1
 * 
 * Valid results:
 * - "(a)()()" - removed ')' at index 4
 * - "(a())()" - removed ')' at index 5... wait, this doesn't work
 * 
 * OUTPUT: ["(a)()()"]
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty string: return [""]
 * 2. All invalid: "(((" -> [""]
 * 3. Already valid: "(())" -> ["(())"]
 * 4. No parentheses: "abc" -> ["abc"]
 * 5. Duplicate results: Use set to handle
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = RemoveInvalidParentheses()
    
    println("=== Remove Invalid Parentheses ===\n")
    
    // Test 1: Basic example
    println("Test 1: s = \"()())()\"")
    val result1 = solution.removeInvalidParentheses("()())()")
    println("Min removals: ${solution.getMinimumRemovals("()())()")}")
    println("Results (${result1.size}):")
    result1.forEach { println("  \"$it\" - Valid: ${solution.isValid(it)}") }
    println()
    
    // Test 2: Multiple solutions
    println("Test 2: s = \"(a)())()\"")
    val result2 = solution.removeInvalidParentheses("(a)())()")
    println("Min removals: ${solution.getMinimumRemovals("(a)())()")}")
    println("Results (${result2.size}):")
    result2.forEach { println("  \"$it\" - Valid: ${solution.isValid(it)}") }
    println()
    
    // Test 3: All need to be removed
    println("Test 3: s = \")(\"")
    val result3 = solution.removeInvalidParentheses(")(")
    println("Min removals: ${solution.getMinimumRemovals(")(")}")
    println("Results (${result3.size}):")
    result3.forEach { println("  \"$it\"") }
    println()
    
    // Test 4: Already valid
    println("Test 4: s = \"(())\"")
    val result4 = solution.removeInvalidParentheses("(())")
    println("Min removals: ${solution.getMinimumRemovals("(())")}")
    println("Results (${result4.size}):")
    result4.forEach { println("  \"$it\"") }
    println()
    
    // Test 5: No parentheses
    println("Test 5: s = \"abc\"")
    val result5 = solution.removeInvalidParentheses("abc")
    println("Min removals: ${solution.getMinimumRemovals("abc")}")
    println("Results (${result5.size}):")
    result5.forEach { println("  \"$it\"") }
    println()
    
    // Test 6: Complex case
    println("Test 6: s = \"(((((\"")
    val result6 = solution.removeInvalidParentheses("(((((")
    println("Min removals: ${solution.getMinimumRemovals("(((((")}")
    println("Results (${result6.size}):")
    result6.forEach { println("  \"$it\"") }
    println()
    
    // Test 7: Mixed
    println("Test 7: s = \"())()(((\"")
    val result7 = solution.removeInvalidParentheses("())()((")
    println("Min removals: ${solution.getMinimumRemovals("())()((")}")
    println("Results (${result7.size}):")
    result7.forEach { println("  \"$it\"") }
}
