/**
 * ============================================================================
 * PROBLEM: Print Numbers from 1 to N using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, print all numbers from 1 to N in ascending order
 * using recursion. Each number should be printed on a new line.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 5
 * 
 * OUTPUT FORMAT:
 * - Numbers from 1 to N, one per line
 * - Example: 1 2 3 4 5
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 1000
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To print 1 to N, we can think recursively:
 * - First print 1 to N-1 (recursively)
 * - Then print N
 * 
 * Or alternatively:
 * - Use a counter starting from 1
 * - Print counter, recurse with counter+1
 * - Stop when counter > N
 * 
 * VISUAL EXAMPLE (N = 5, bottom-up):
 * 
 * print1ToN(5)
 *   └─ print1ToN(4) first
 *        └─ print1ToN(3) first
 *             └─ print1ToN(2) first
 *                  └─ print1ToN(1) first
 *                       └─ print1ToN(0) [BASE CASE]
 *                       Print 1
 *                  Print 2
 *             Print 3
 *        Print 4
 *   Print 5
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if N = 0, return
 * 2. Recursively call with N-1
 * 3. Print N after recursive call returns
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N)
 * - N recursive calls
 * - Each call does O(1) work
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack depth is N
 * - Each frame uses constant space
 * 
 * ============================================================================
 */

package basics.recursion

class Print1ToN {
    
    /**
     * Print numbers from 1 to N using recursion
     * TIME: O(N), SPACE: O(N)
     */
    fun print1ToN(n: Int) {
        // Base case
        if (n == 0) {
            return
        }
        
        // Recurse for N-1 first
        print1ToN(n - 1)
        
        // Print N after recursion returns
        println(n)
    }
    
    /**
     * Alternative: using counter parameter
     */
    fun print1ToNCounter(n: Int) {
        print1ToNHelper(1, n)
    }
    
    private fun print1ToNHelper(current: Int, n: Int) {
        if (current > n) return
        println(current)
        print1ToNHelper(current + 1, n)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 3
 * 
 * print1ToN(3):
 *   - Call print1ToN(2)
 *     - Call print1ToN(1)
 *       - Call print1ToN(0) → return
 *       - Print 1
 *     - Print 2
 *   - Print 3
 * 
 * OUTPUT: 1 2 3
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = Print1ToN()
    
    println("=== Print 1 to N ===\n")
    
    println("Test 1: N = 5")
    solution.print1ToN(5)
    println()
    
    println("Test 2: N = 1")
    solution.print1ToN(1)
    println()
    
    println("Test 3: N = 10")
    solution.print1ToN(10)
    println()
    
    println("Test 4: Using counter approach, N = 7")
    solution.print1ToNCounter(7)
    println()
}
