/**
 * ============================================================================
 * PROBLEM: Print Numbers N Times using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, print numbers from 1 to N, where each number
 * is printed on a new line, using recursion.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 5
 * 
 * OUTPUT FORMAT:
 * - Print numbers from 1 to N, one per line
 * - Example:
 *   1
 *   2
 *   3
 *   4
 *   5
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 1000
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To print numbers 1 to N using recursion:
 * - We can either print 1, then print 2 to N recursively
 * - Or recursively print 1 to N-1, then print N
 * 
 * TWO APPROACHES:
 * 
 * Approach 1 (Forward - using counter):
 * - Start with counter = 1
 * - Print counter, then recurse with counter + 1
 * - Stop when counter > N
 * 
 * Approach 2 (Backward):
 * - First recurse to N-1
 * - Then print N on return
 * - Stop when N = 0
 * 
 * VISUAL EXAMPLE (N = 5, Forward):
 * 
 * print(1, 5)
 *   ├─ Print 1
 *   └─ print(2, 5)
 *        ├─ Print 2
 *        └─ print(3, 5)
 *             ├─ Print 3
 *             └─ print(4, 5)
 *                  ├─ Print 4
 *                  └─ print(5, 5)
 *                       ├─ Print 5
 *                       └─ print(6, 5) [BASE CASE]
 * 
 * ALGORITHM STEPS (Forward):
 * 1. Check base case: if current > N, return
 * 2. Print current number
 * 3. Recursively call with current + 1
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N)
 * - Function is called N times
 * - Each call does O(1) work
 * - Total: N * O(1) = O(N)
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion depth is N
 * - Each call takes constant space on stack
 * - Maximum N calls on stack simultaneously
 * 
 * ============================================================================
 */

package basics.recursion

class PrintNTimes {
    
    /**
     * Print numbers from 1 to N using recursion (forward)
     * TIME: O(N), SPACE: O(N) stack space
     * 
     * @param n The upper limit
     */
    fun printNumbers(n: Int) {
        printNumbersHelper(1, n)
    }
    
    /**
     * Helper function with current counter
     */
    private fun printNumbersHelper(current: Int, n: Int) {
        // Base case: when current exceeds n
        if (current > n) {
            return
        }
        
        // Print current number
        println(current)
        
        // Recursive call with incremented counter
        printNumbersHelper(current + 1, n)
    }
    
    /**
     * Print numbers from 1 to N using recursion (backward approach)
     * TIME: O(N), SPACE: O(N)
     * 
     * Recurse first, then print on return
     */
    fun printNumbersBackward(n: Int) {
        // Base case
        if (n == 0) {
            return
        }
        
        // Recurse first to reach base case
        printNumbersBackward(n - 1)
        
        // Print after recursive call returns
        println(n)
    }
    
    /**
     * Print numbers with tail recursion optimization
     * TIME: O(N), SPACE: O(1) with optimization
     */
    tailrec fun printNumbersTailRec(current: Int, n: Int) {
        // Base case
        if (current > n) {
            return
        }
        
        // Print current
        println(current)
        
        // Tail recursive call
        printNumbersTailRec(current + 1, n)
    }
    
    /**
     * Print numbers in range [start, end]
     * TIME: O(end - start + 1), SPACE: O(end - start + 1)
     */
    fun printRange(start: Int, end: Int) {
        // Base case
        if (start > end) {
            return
        }
        
        // Print current
        println(start)
        
        // Recurse for next number
        printRange(start + 1, end)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 5 (using forward approach)
 * 
 * CALL SEQUENCE:
 * 
 * printNumbersHelper(1, 5):
 * - 1 <= 5, continue
 * - Print 1
 * - Call printNumbersHelper(2, 5)
 * 
 * printNumbersHelper(2, 5):
 * - 2 <= 5, continue
 * - Print 2
 * - Call printNumbersHelper(3, 5)
 * 
 * printNumbersHelper(3, 5):
 * - 3 <= 5, continue
 * - Print 3
 * - Call printNumbersHelper(4, 5)
 * 
 * printNumbersHelper(4, 5):
 * - 4 <= 5, continue
 * - Print 4
 * - Call printNumbersHelper(5, 5)
 * 
 * printNumbersHelper(5, 5):
 * - 5 <= 5, continue
 * - Print 5
 * - Call printNumbersHelper(6, 5)
 * 
 * printNumbersHelper(6, 5):
 * - 6 > 5, BASE CASE
 * - Return
 * 
 * OUTPUT:
 * 1
 * 2
 * 3
 * 4
 * 5
 * 
 * ============================================================================
 * BACKWARD APPROACH WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 3
 * 
 * Call printNumbersBackward(3):
 * - 3 != 0, recurse
 * - Call printNumbersBackward(2)
 *   - 2 != 0, recurse
 *   - Call printNumbersBackward(1)
 *     - 1 != 0, recurse
 *     - Call printNumbersBackward(0)
 *       - BASE CASE: return
 *     - Print 1 (after return)
 *   - Print 2 (after return)
 * - Print 3 (after return)
 * 
 * OUTPUT: 1, 2, 3 (printed during unwinding)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = PrintNTimes()
    
    println("=== Print Numbers 1 to N using Recursion ===\n")
    
    // Test 1: Normal case
    println("Test 1: Print 1 to 5 (forward)")
    solution.printNumbers(5)
    println()
    
    // Test 2: Single number
    println("Test 2: Print 1 to 1")
    solution.printNumbers(1)
    println()
    
    // Test 3: Larger range
    println("Test 3: Print 1 to 10")
    solution.printNumbers(10)
    println()
    
    // Test 4: Backward approach
    println("Test 4: Print 1 to 5 (backward approach)")
    solution.printNumbersBackward(5)
    println()
    
    // Test 5: Tail recursion
    println("Test 5: Print 1 to 7 (tail recursive)")
    solution.printNumbersTailRec(1, 7)
    println()
    
    // Test 6: Custom range
    println("Test 6: Print range [5, 10]")
    solution.printRange(5, 10)
    println()
    
    // Test 7: Another custom range
    println("Test 7: Print range [15, 20]")
    solution.printRange(15, 20)
    println()
}
