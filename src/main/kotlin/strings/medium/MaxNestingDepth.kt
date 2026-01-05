/**
 * ============================================================================
 * PROBLEM: Maximum Nesting Depth of Parentheses
 * DIFFICULTY: Medium
 * CATEGORY: Strings - Medium
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a valid parentheses string s, return the maximum nesting depth of 
 * the parentheses.
 * 
 * A string is a valid parentheses string (VPS) if it meets one of the following:
 * - It is an empty string "", or a single pair "()"
 * - It can be written as AB (A concatenated with B), where A and B are VPS
 * - It can be written as (A), where A is a VPS
 * 
 * The nesting depth is the maximum number of nested parentheses.
 * 
 * INPUT FORMAT:
 * - A valid parentheses string s: s = "(1+(2*3)+((8)/4))+1"
 * 
 * OUTPUT FORMAT:
 * - Integer representing maximum nesting depth: 3
 * 
 * CONSTRAINTS:
 * - 1 <= s.length <= 100
 * - s consists of digits and characters '+', '-', '*', '/', '(', and ')'
 * - It is guaranteed that s is a valid parentheses string
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The nesting depth is like tracking how deep we go into nested structures.
 * Think of it like going into nested rooms:
 * - Each '(' is entering a room (depth increases)
 * - Each ')' is exiting a room (depth decreases)
 * - Maximum depth is the deepest room we visit
 * 
 * Example: "(1+(2*3)+((8)/4))+1"
 * 
 * (          → depth = 1
 * 1+(        → depth = 2
 * 2*3)       → depth = 1
 * +((        → depth = 3  ← Maximum!
 * 8)/4))     → depth = 0
 * +1
 * 
 * KEY INSIGHTS:
 * 1. Only parentheses affect depth, ignore other characters
 * 2. '(' increases current depth
 * 3. ')' decreases current depth
 * 4. Track maximum depth reached
 * 5. Valid string guarantees depth never goes negative
 * 
 * ALGORITHM STEPS:
 * 1. Initialize current depth = 0, max depth = 0
 * 2. For each character in string:
 *    a. If '(': increment current depth
 *    b. Update max depth if current > max
 *    c. If ')': decrement current depth
 * 3. Return max depth
 * 
 * VISUAL EXAMPLE:
 * s = "(1+(2*3)+((8)/4))+1"
 * 
 * Step by step:
 * Index  Char  Action         Current  Max
 * 0      (     depth++        1        1
 * 1      1     skip           1        1
 * 2      +     skip           1        1
 * 3      (     depth++        2        2
 * 4      2     skip           2        2
 * 5      *     skip           2        2
 * 6      3     skip           2        2
 * 7      )     depth--        1        2
 * 8      +     skip           1        2
 * 9      (     depth++        2        2
 * 10     (     depth++        3        3 ← Max!
 * 11     8     skip           3        3
 * 12     )     depth--        2        3
 * 13     /     skip           2        3
 * 14     4     skip           2        3
 * 15     )     depth--        1        3
 * 16     )     depth--        0        3
 * 17     +     skip           0        3
 * 18     1     skip           0        3
 * 
 * OUTPUT: 3
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Single Pass with Counter: O(n) time, O(1) space - OPTIMAL
 * 2. Stack-based: O(n) time, O(n) space - Overkill for this problem
 * 3. Recursive: O(n) time, O(n) space - Unnecessary complexity
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through the string
 * - Each character is processed exactly once
 * - Constant time operations per character
 * - n is the length of the string
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only use two integer variables (current, max)
 * - No additional data structures needed
 * - Space usage is independent of input size
 * 
 * WHY THIS IS OPTIMAL:
 * - Must examine every character at least once: O(n) minimum
 * - Simple counter is sufficient, no need for stack
 * - Cannot improve time complexity beyond O(n)
 * - Cannot use less than O(1) space
 * 
 * ============================================================================
 */

package strings.medium

class MaxNestingDepth {
    
    /**
     * Calculates maximum nesting depth of parentheses
     * 
     * @param s Valid parentheses string
     * @return Maximum nesting depth
     */
    fun maxDepth(s: String): Int {
        // Track current depth and maximum depth
        var currentDepth = 0
        var maxDepth = 0
        
        // Process each character
        for (char in s) {
            when (char) {
                '(' -> {
                    // Opening parenthesis increases depth
                    currentDepth++
                    // Update maximum if current is greater
                    maxDepth = maxOf(maxDepth, currentDepth)
                }
                ')' -> {
                    // Closing parenthesis decreases depth
                    currentDepth--
                }
                // Ignore all other characters (digits, operators)
            }
        }
        
        return maxDepth
    }
    
    /**
     * Alternative: Using fold for functional programming style
     */
    fun maxDepthFunctional(s: String): Int {
        // Use fold to accumulate (currentDepth, maxDepth)
        val (_, maxDepth) = s.fold(0 to 0) { (current, max), char ->
            when (char) {
                '(' -> {
                    val newCurrent = current + 1
                    newCurrent to maxOf(max, newCurrent)
                }
                ')' -> (current - 1) to max
                else -> current to max
            }
        }
        return maxDepth
    }
    
    /**
     * Stack-based approach (educational purposes)
     * Shows how a stack could be used, though overkill for this problem
     */
    fun maxDepthStack(s: String): Int {
        val stack = mutableListOf<Char>()
        var maxDepth = 0
        
        for (char in s) {
            when (char) {
                '(' -> {
                    // Push opening parenthesis
                    stack.add(char)
                    // Update max depth
                    maxDepth = maxOf(maxDepth, stack.size)
                }
                ')' -> {
                    // Pop matching opening parenthesis
                    if (stack.isNotEmpty()) {
                        stack.removeAt(stack.lastIndex)
                    }
                }
            }
        }
        
        return maxDepth
    }
    
    /**
     * Helper function to visualize depth changes
     */
    fun visualizeDepth(s: String): List<Pair<Char, Int>> {
        val depths = mutableListOf<Pair<Char, Int>>()
        var currentDepth = 0
        
        for (char in s) {
            when (char) {
                '(' -> {
                    currentDepth++
                    depths.add(char to currentDepth)
                }
                ')' -> {
                    depths.add(char to currentDepth)
                    currentDepth--
                }
                else -> {
                    depths.add(char to currentDepth)
                }
            }
        }
        
        return depths
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: s = "(1+(2*3)+((8)/4))+1"
 * 
 * DETAILED EXECUTION:
 * 
 * Initial state:
 * currentDepth = 0, maxDepth = 0
 * 
 * Processing:
 * 
 * '(' : currentDepth = 1, maxDepth = 1
 * '1' : no change
 * '+' : no change
 * '(' : currentDepth = 2, maxDepth = 2
 * '2' : no change
 * '*' : no change
 * '3' : no change
 * ')' : currentDepth = 1
 * '+' : no change
 * '(' : currentDepth = 2
 * '(' : currentDepth = 3, maxDepth = 3  ← Maximum reached!
 * '8' : no change
 * ')' : currentDepth = 2
 * '/' : no change
 * '4' : no change
 * ')' : currentDepth = 1
 * ')' : currentDepth = 0
 * '+' : no change
 * '1' : no change
 * 
 * Final: maxDepth = 3
 * OUTPUT: 3
 * 
 * ---
 * 
 * Example 2: s = "(1)+((2))+(((3)))"
 * 
 * Depth trace:
 * ( → 1
 * 1 → 1
 * ) → 0
 * + → 0
 * ( → 1
 * ( → 2
 * 2 → 2
 * ) → 1
 * ) → 0
 * + → 0
 * ( → 1
 * ( → 2
 * ( → 3  ← Maximum!
 * 3 → 3
 * ) → 2
 * ) → 1
 * ) → 0
 * 
 * OUTPUT: 3
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty parentheses "()" → depth 1
 * 2. Single level "(1+2)" → depth 1
 * 3. No parentheses "123+456" → depth 0
 * 4. Only parentheses "(())" → depth 2
 * 5. Maximum nesting "((((1))))" → depth 4
 * 6. Sequential pairs "()()()" → depth 1 (not nested)
 * 7. Mixed nesting "(1+(2+(3+4)))" → depth 3
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Updating max before incrementing
 * ❌ maxDepth = max(maxDepth, currentDepth); currentDepth++
 * ✅ currentDepth++; maxDepth = max(maxDepth, currentDepth)
 * 
 * MISTAKE 2: Counting all characters
 * ❌ Incrementing depth for non-parentheses
 * ✅ Only process '(' and ')'
 * 
 * MISTAKE 3: Not handling empty string
 * ❌ Assuming string always has parentheses
 * ✅ Works correctly even if no parentheses (returns 0)
 * 
 * MISTAKE 4: Decrementing before checking max
 * ❌ For ')': check max then decrement
 * ✅ For '(': increment then check max
 * 
 * MISTAKE 5: Using unnecessary data structures
 * ❌ Using stack or list when simple counter suffices
 * ✅ Two variables are enough
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Compiler Design**: Parsing nested expressions
 * 2. **Code Analysis**: Measuring code complexity (cyclomatic complexity)
 * 3. **XML/HTML Parsing**: Tracking nested tags
 * 4. **Math Evaluators**: Understanding expression structure
 * 5. **Configuration Files**: Validating nested structures (JSON, YAML)
 * 6. **Database Queries**: Analyzing nested subqueries
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * - Valid Parentheses (check validity)
 * - Minimum Add to Make Parentheses Valid
 * - Score of Parentheses
 * - Remove Invalid Parentheses
 * - Generate Parentheses
 * - Different Ways to Add Parentheses
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = MaxNestingDepth()
    
    println("=== Testing Maximum Nesting Depth ===\n")
    
    // Test 1: Complex expression
    println("Test 1: Complex expression")
    println("Input: s = \"(1+(2*3)+((8)/4))+1\"")
    println("Output: ${solution.maxDepth("(1+(2*3)+((8)/4))+1")}")
    println("Expected: 3\n")
    
    // Test 2: Multiple nesting levels
    println("Test 2: Multiple nesting levels")
    println("Input: s = \"(1)+((2))+(((3)))\"")
    println("Output: ${solution.maxDepth("(1)+((2))+(((3)))")}")
    println("Expected: 3\n")
    
    // Test 3: Single level
    println("Test 3: Single level")
    println("Input: s = \"(1+2)\"")
    println("Output: ${solution.maxDepth("(1+2)")}")
    println("Expected: 1\n")
    
    // Test 4: Empty parentheses
    println("Test 4: Empty parentheses")
    println("Input: s = \"()\"")
    println("Output: ${solution.maxDepth("()")}")
    println("Expected: 1\n")
    
    // Test 5: No parentheses
    println("Test 5: No parentheses")
    println("Input: s = \"1+2+3\"")
    println("Output: ${solution.maxDepth("1+2+3")}")
    println("Expected: 0\n")
    
    // Test 6: Sequential pairs (not nested)
    println("Test 6: Sequential pairs")
    println("Input: s = \"()()()\"")
    println("Output: ${solution.maxDepth("()()()")}")
    println("Expected: 1\n")
    
    // Test 7: Deep nesting
    println("Test 7: Deep nesting")
    println("Input: s = \"((((1))))\"")
    println("Output: ${solution.maxDepth("((((1))))")}")
    println("Expected: 4\n")
    
    // Test 8: Functional approach
    println("Test 8: Using functional approach")
    println("Input: s = \"(1+(2*3)+((8)/4))+1\"")
    println("Output: ${solution.maxDepthFunctional("(1+(2*3)+((8)/4))+1")}")
    println("Expected: 3\n")
    
    // Test 9: Visualize depth changes
    println("Test 9: Visualize depth changes")
    println("Input: s = \"(1+(2))\"")
    val depths = solution.visualizeDepth("(1+(2))")
    println("Depth trace:")
    depths.forEach { (char, depth) ->
        println("  '$char' → depth $depth")
    }
    println()
}
