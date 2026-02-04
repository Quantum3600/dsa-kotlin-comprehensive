/**
 * ============================================================================
 * PROBLEM: Expression Add Operators
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a string of digits and a target value, return all possible ways to
 * insert binary operators (+, -, *) between the digits to evaluate to the
 * target value. Numbers can have multiple digits.
 * 
 * INPUT FORMAT:
 * - num: String containing only digits (0-9)
 * - target: Target integer value
 * 
 * OUTPUT FORMAT:
 * - List of all valid expressions that evaluate to target
 * 
 * CONSTRAINTS:
 * - 1 <= num.length <= 10
 * - num consists of only digits
 * - -2^31 <= target <= 2^31 - 1
 * - Numbers in expression cannot have leading zeros (except "0" itself)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use backtracking to try all possible operator placements:
 * 1. At each position, decide to either continue the current number or
 *    insert an operator (+, -, *)
 * 2. Keep track of current value and last operand for handling multiplication
 * 3. Multiplication has higher precedence, so we need to "undo" the last
 *    addition/subtraction before applying multiplication
 * 
 * KEY INSIGHT: Handling Multiplication Precedence
 * - When we see "2+3*4", we need to calculate: 2 + (3*4) = 14, not (2+3)*4 = 20
 * - Strategy: Keep track of last value added/subtracted
 * - For multiplication: remove last value, then add (last * current)
 * 
 * VISUAL EXAMPLE:
 * 
 * Input: num = "123", target = 6
 * 
 * Exploration tree:
 * ""
 *  ├─ "1" (val=1)
 *  │   ├─ "1+2" (val=3)
 *  │   │   ├─ "1+2+3" (val=6) ✓
 *  │   │   └─ "1+2*3" (val=7) ✗
 *  │   ├─ "1-2" (val=-1)
 *  │   │   └─ ...
 *  │   ├─ "1*2" (val=2)
 *  │   │   ├─ "1*2+3" (val=5) ✗
 *  │   │   └─ "1*2*3" (val=6) ✓
 *  │   └─ "12" (val=12)
 *  │       └─ "12+3" (val=15) ✗
 * 
 * Results: ["1+2+3", "1*2*3"]
 * 
 * ALGORITHM STEPS:
 * 1. Start with empty expression and value = 0
 * 2. For each position in string:
 *    a. Extract number (can be multiple digits)
 *    b. If first number, set as current value
 *    c. Otherwise, try all three operators:
 *       - Add: newVal = val + num
 *       - Subtract: newVal = val - num
 *       - Multiply: newVal = val - last + (last * num)
 *    d. Recursively continue for remaining string
 * 3. If we reach end and val == target, add expression to result
 * 4. Handle leading zeros: "05" is invalid
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(4^N)
 * - At each position, we have 4 choices:
 *   - Extend current number (no operator)
 *   - Insert +
 *   - Insert -
 *   - Insert *
 * - For N digits, this gives O(4^N) possibilities
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion depth: O(N)
 * - Expression string: O(N)
 * - Result storage: O(4^N * N) for all valid expressions
 * 
 * ============================================================================
 */

package recursion.hard

class ExpressionAddOperators {
    
    /**
     * Find all expressions that evaluate to target
     * TIME: O(4^N), SPACE: O(N)
     * 
     * @param num String of digits
     * @param target Target value
     * @return List of valid expressions
     */
    fun addOperators(num: String, target: Int): List<String> {
        val result = mutableListOf<String>()
        if (num.isEmpty()) return result
        
        backtrack(num, target, 0, "", 0L, 0L, result)
        return result
    }
    
    /**
     * Backtracking helper
     * 
     * @param num Input string
     * @param target Target value
     * @param index Current position in string
     * @param expr Current expression being built
     * @param value Current evaluated value
     * @param lastOperand Last number added/subtracted (for multiplication)
     * @param result List to store valid expressions
     */
    private fun backtrack(
        num: String,
        target: Int,
        index: Int,
        expr: String,
        value: Long,
        lastOperand: Long,
        result: MutableList<String>
    ) {
        // Base case: processed all digits
        if (index == num.length) {
            if (value == target.toLong()) {
                result.add(expr)
            }
            return
        }
        
        // Try all possible numbers starting from current index
        for (i in index until num.length) {
            // Extract substring as potential number
            val numStr = num.substring(index, i + 1)
            
            // Skip numbers with leading zeros (except "0" itself)
            if (numStr.length > 1 && numStr[0] == '0') {
                break
            }
            
            val currentNum = numStr.toLong()
            
            if (index == 0) {
                // First number: no operator needed
                backtrack(num, target, i + 1, numStr, currentNum, currentNum, result)
            } else {
                // Try addition: +
                backtrack(
                    num, target, i + 1,
                    "$expr+$numStr",
                    value + currentNum,
                    currentNum,
                    result
                )
                
                // Try subtraction: -
                backtrack(
                    num, target, i + 1,
                    "$expr-$numStr",
                    value - currentNum,
                    -currentNum,
                    result
                )
                
                // Try multiplication: *
                // Need to undo last operation and apply multiplication
                // value - lastOperand gives value before last operation
                // Then add lastOperand * currentNum
                backtrack(
                    num, target, i + 1,
                    "$expr*$numStr",
                    value - lastOperand + (lastOperand * currentNum),
                    lastOperand * currentNum,
                    result
                )
            }
        }
    }
    
    /**
     * Count number of valid expressions
     */
    fun countExpressions(num: String, target: Int): Int {
        return addOperators(num, target).size
    }
    
    /**
     * Evaluate an expression string
     */
    fun evaluate(expr: String): Long {
        // Simple evaluator respecting operator precedence
        var result = 0L
        var currentNum = 0L
        var lastNum = 0L
        var operation = '+'
        
        for (i in expr.indices) {
            val ch = expr[i]
            
            if (ch.isDigit()) {
                currentNum = currentNum * 10 + (ch - '0')
            }
            
            if (!ch.isDigit() || i == expr.length - 1) {
                when (operation) {
                    '+' -> {
                        result += lastNum
                        lastNum = currentNum
                    }
                    '-' -> {
                        result += lastNum
                        lastNum = -currentNum
                    }
                    '*' -> {
                        lastNum *= currentNum
                    }
                }
                
                if (!ch.isDigit()) {
                    operation = ch
                    currentNum = 0
                }
            }
        }
        
        result += lastNum
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: num = "123", target = 6
 * 
 * Detailed Backtracking:
 * 
 * Start: index=0, expr="", val=0, last=0
 *   
 * Take "1": index=1, expr="1", val=1, last=1
 *   Take "2" with +:
 *     index=2, expr="1+2", val=3, last=2
 *       Take "3" with +: expr="1+2+3", val=6, last=3 ✓ TARGET!
 *       Take "3" with -: expr="1+2-3", val=0, last=-3 ✗
 *       Take "3" with *: expr="1+2*3", val=7, last=6 ✗
 *   Take "2" with -:
 *     index=2, expr="1-2", val=-1, last=-2
 *       Take "3" with +: expr="1-2+3", val=2, last=3 ✗
 *       Take "3" with -: expr="1-2-3", val=-4, last=-3 ✗
 *       Take "3" with *: expr="1-2*3", val=-5, last=-6 ✗
 *   Take "2" with *:
 *     index=2, expr="1*2", val=2, last=2
 *       Take "3" with +: expr="1*2+3", val=5, last=3 ✗
 *       Take "3" with -: expr="1*2-3", val=-1, last=-3 ✗
 *       Take "3" with *: expr="1*2*3", val=6, last=6 ✓ TARGET!
 * 
 * Take "12": index=2, expr="12", val=12, last=12
 *   Take "3" with +: expr="12+3", val=15, last=3 ✗
 *   Take "3" with -: expr="12-3", val=9, last=-3 ✗
 *   Take "3" with *: expr="12*3", val=36, last=36 ✗
 * 
 * Take "123": index=3, expr="123", val=123, last=123 ✗
 * 
 * OUTPUT: ["1+2+3", "1*2*3"]
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Leading zeros: "105" -> "1*0+5", but not "10+5"
 * 2. Single digit: "3" target 3 -> ["3"]
 * 3. No solution: "123" target 100 -> []
 * 4. Negative target: "123" target -1 -> ["1-2*3"]  (wrong actually)
 * 5. Large numbers: Handle overflow with Long
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ExpressionAddOperators()
    
    println("=== Expression Add Operators ===\n")
    
    // Test 1: Basic example
    println("Test 1: num = \"123\", target = 6")
    val result1 = solution.addOperators("123", 6)
    println("Results (${result1.size}):")
    result1.forEach { println("  $it = ${solution.evaluate(it)}") }
    println()
    
    // Test 2: Longer string
    println("Test 2: num = \"232\", target = 8")
    val result2 = solution.addOperators("232", 8)
    println("Results (${result2.size}):")
    result2.forEach { println("  $it = ${solution.evaluate(it)}") }
    println()
    
    // Test 3: With zeros
    println("Test 3: num = \"105\", target = 5")
    val result3 = solution.addOperators("105", 5)
    println("Results (${result3.size}):")
    result3.forEach { println("  $it = ${solution.evaluate(it)}") }
    println()
    
    // Test 4: No solution
    println("Test 4: num = \"123\", target = 100")
    val result4 = solution.addOperators("123", 100)
    println("Results (${result4.size}):")
    if (result4.isEmpty()) {
        println("  No solution found")
    }
    println()
    
    // Test 5: Single digit
    println("Test 5: num = \"3\", target = 3")
    val result5 = solution.addOperators("3", 3)
    println("Results (${result5.size}):")
    result5.forEach { println("  $it") }
    println()
    
    // Test 6: Two digits
    println("Test 6: num = \"00\", target = 0")
    val result6 = solution.addOperators("00", 0)
    println("Results (${result6.size}):")
    result6.forEach { println("  $it") }
    println()
    
    // Test 7: Complex case
    println("Test 7: num = \"3456237490\", target = 9191")
    val result7 = solution.addOperators("3456237490", 9191)
    println("Results (${result7.size}):")
    println("(Too many to display all)")
}
