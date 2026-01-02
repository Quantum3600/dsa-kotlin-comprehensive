/**
 * ============================================================================
 * PROBLEM: Print Numbers from N to 1 using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, print all numbers from N to 1 in descending order
 * using recursion. Each number should be printed on a new line.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 5
 * 
 * OUTPUT FORMAT:
 * - Numbers from N to 1, one per line
 * - Example: 5 4 3 2 1
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 1000
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To print N to 1:
 * - Print N first
 * - Then recursively print N-1 to 1
 * - Stop when N = 0
 * 
 * VISUAL EXAMPLE (N = 5):
 * 
 * printNTo1(5)
 *   ├─ Print 5
 *   └─ printNTo1(4)
 *        ├─ Print 4
 *        └─ printNTo1(3)
 *             ├─ Print 3
 *             └─ printNTo1(2)
 *                  ├─ Print 2
 *                  └─ printNTo1(1)
 *                       ├─ Print 1
 *                       └─ printNTo1(0) [BASE CASE]
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if N = 0, return
 * 2. Print N
 * 3. Recursively call with N-1
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
 * 
 * ============================================================================
 */

package basics.recursion

class PrintNTo1 {
    
    /**
     * Print numbers from N to 1 using recursion
     * TIME: O(N), SPACE: O(N)
     */
    fun printNTo1(n: Int) {
        // Base case
        if (n == 0) {
            return
        }
        
        // Print N first
        println(n)
        
        // Then recurse for N-1
        printNTo1(n - 1)
    }
    
    /**
     * Tail recursive version
     */
    tailrec fun printNTo1TailRec(n: Int) {
        if (n == 0) return
        println(n)
        printNTo1TailRec(n - 1)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 3
 * 
 * printNTo1(3):
 *   - Print 3
 *   - Call printNTo1(2)
 *     - Print 2
 *     - Call printNTo1(1)
 *       - Print 1
 *       - Call printNTo1(0) → return
 * 
 * OUTPUT: 3 2 1
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = PrintNTo1()
    
    println("=== Print N to 1 ===\n")
    
    println("Test 1: N = 5")
    solution.printNTo1(5)
    println()
    
    println("Test 2: N = 1")
    solution.printNTo1(1)
    println()
    
    println("Test 3: N = 10")
    solution.printNTo1(10)
    println()
    
    println("Test 4: Tail recursive, N = 7")
    solution.printNTo1TailRec(7)
    println()
}
