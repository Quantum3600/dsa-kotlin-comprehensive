/**
 * ============================================================================
 * PROBLEM: Sum of First N Natural Numbers using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Basic
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer N, calculate the sum of first N natural numbers
 * (1 + 2 + 3 + ... + N) using recursion.
 * 
 * INPUT FORMAT:
 * - A positive integer N
 * - Example: N = 5
 * 
 * OUTPUT FORMAT:
 * - Sum of numbers from 1 to N
 * - Example: 15 (because 1+2+3+4+5 = 15)
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 10000
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Sum of 1 to N can be thought of recursively:
 * - sum(N) = N + sum(N-1)
 * - sum(1) = 1 (base case)
 * 
 * Example: sum(5) = 5 + sum(4)
 *                 = 5 + 4 + sum(3)
 *                 = 5 + 4 + 3 + sum(2)
 *                 = 5 + 4 + 3 + 2 + sum(1)
 *                 = 5 + 4 + 3 + 2 + 1
 *                 = 15
 * 
 * RECURSIVE RELATION:
 * sum(N) = N + sum(N-1)
 * sum(0) = 0 (base case)
 * 
 * VISUAL EXAMPLE (N = 5):
 * 
 * sum(5)
 *   └─ return 5 + sum(4)
 *                 └─ return 4 + sum(3)
 *                              └─ return 3 + sum(2)
 *                                         └─ return 2 + sum(1)
 *                                                    └─ return 1 + sum(0)
 *                                                               └─ return 0
 * Result: 5 + 4 + 3 + 2 + 1 + 0 = 15
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if N = 0, return 0
 * 2. Recursive case: return N + sum(N-1)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Recursion: O(N) time, O(N) space - Straightforward
 * 2. Formula: O(1) time, O(1) space - sum = N*(N+1)/2
 * 3. Iteration: O(N) time, O(1) space - Using loop
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * RECURSIVE APPROACH:
 * TIME COMPLEXITY: O(N)
 * - N recursive calls
 * - Each call does O(1) work (addition)
 * 
 * SPACE COMPLEXITY: O(N)
 * - Recursion stack depth is N
 * - Each frame stores return address and N
 * 
 * FORMULA APPROACH:
 * TIME: O(1), SPACE: O(1)
 * Mathematical formula: sum = N * (N + 1) / 2
 * 
 * ============================================================================
 */

package basics.recursion

class SumOfFirstN {
    
    /**
     * Calculate sum using recursion
     * TIME: O(N), SPACE: O(N)
     */
    fun sumRecursive(n: Int): Int {
        // Base case: sum of 0 numbers is 0
        if (n == 0) {
            return 0
        }
        
        // Recursive case: N + sum of (N-1) numbers
        return n + sumRecursive(n - 1)
    }
    
    /**
     * Calculate sum using formula (non-recursive)
     * TIME: O(1), SPACE: O(1)
     * 
     * Formula: sum = N * (N + 1) / 2
     */
    fun sumFormula(n: Int): Long {
        // Use Long to avoid overflow for large N
        return n.toLong() * (n + 1) / 2
    }
    
    /**
     * Tail recursive version
     * TIME: O(N), SPACE: O(1) with optimization
     */
    tailrec fun sumTailRec(n: Int, accumulator: Int = 0): Int {
        // Base case
        if (n == 0) {
            return accumulator
        }
        
        // Tail recursive call with updated accumulator
        return sumTailRec(n - 1, accumulator + n)
    }
    
    /**
     * Sum using parameterized recursion
     */
    fun sumParameterized(n: Int): Int {
        return sumHelper(n, 0)
    }
    
    private fun sumHelper(n: Int, acc: Int): Int {
        if (n == 0) return acc
        return sumHelper(n - 1, acc + n)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 5
 * 
 * RECURSIVE APPROACH:
 * 
 * sumRecursive(5):
 *   return 5 + sumRecursive(4)
 *     return 4 + sumRecursive(3)
 *       return 3 + sumRecursive(2)
 *         return 2 + sumRecursive(1)
 *           return 1 + sumRecursive(0)
 *             return 0 [BASE CASE]
 *           return 1 + 0 = 1
 *         return 2 + 1 = 3
 *       return 3 + 3 = 6
 *     return 4 + 6 = 10
 *   return 5 + 10 = 15 ✓
 * 
 * CALL STACK:
 * sumRecursive(5) = 5 + 10 = 15
 * sumRecursive(4) = 4 + 6  = 10
 * sumRecursive(3) = 3 + 3  = 6
 * sumRecursive(2) = 2 + 1  = 3
 * sumRecursive(1) = 1 + 0  = 1
 * sumRecursive(0) = 0
 * 
 * TAIL RECURSIVE APPROACH (N = 5):
 * 
 * sumTailRec(5, 0):
 *   sumTailRec(4, 0+5=5):
 *     sumTailRec(3, 5+4=9):
 *       sumTailRec(2, 9+3=12):
 *         sumTailRec(1, 12+2=14):
 *           sumTailRec(0, 14+1=15):
 *             return 15 [BASE CASE]
 * 
 * Result: 15 ✓
 * 
 * FORMULA APPROACH:
 * sum = 5 * (5 + 1) / 2
 *     = 5 * 6 / 2
 *     = 30 / 2
 *     = 15 ✓
 * 
 * ============================================================================
 * MATHEMATICAL PROOF OF FORMULA
 * ============================================================================
 * 
 * The formula sum = N*(N+1)/2 comes from:
 * 
 * Method 1 (Pairing):
 * Sum = 1 + 2 + 3 + ... + N
 * Also: Sum = N + (N-1) + ... + 1
 * Add both: 2*Sum = (N+1) + (N+1) + ... + (N+1)  [N times]
 *           2*Sum = N * (N+1)
 *           Sum = N * (N+1) / 2
 * 
 * Example with N=5:
 *   1 + 2 + 3 + 4 + 5
 * + 5 + 4 + 3 + 2 + 1
 * -------------------
 *   6 + 6 + 6 + 6 + 6 = 5 * 6 = 30
 * Sum = 30/2 = 15 ✓
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = SumOfFirstN()
    
    println("=== Sum of First N Natural Numbers ===\n")
    
    // Test 1: Normal case
    println("Test 1: N = 5")
    println("Recursive: ${solution.sumRecursive(5)}")
    println("Formula: ${solution.sumFormula(5)}")
    println("Tail Recursive: ${solution.sumTailRec(5)}")
    println("Expected: 15\n")
    
    // Test 2: Single number
    println("Test 2: N = 1")
    println("Recursive: ${solution.sumRecursive(1)}")
    println("Formula: ${solution.sumFormula(1)}")
    println("Expected: 1\n")
    
    // Test 3: Larger number
    println("Test 3: N = 10")
    println("Recursive: ${solution.sumRecursive(10)}")
    println("Formula: ${solution.sumFormula(10)}")
    println("Expected: 55\n")
    
    // Test 4: Even larger
    println("Test 4: N = 100")
    println("Recursive: ${solution.sumRecursive(100)}")
    println("Formula: ${solution.sumFormula(100)}")
    println("Tail Recursive: ${solution.sumTailRec(100)}")
    println("Expected: 5050\n")
    
    // Test 5: Very large (use formula to avoid stack overflow)
    println("Test 5: N = 10000")
    println("Formula: ${solution.sumFormula(10000)}")
    println("Expected: 50005000\n")
    
    // Verification examples
    println("=== Verification ===")
    for (n in listOf(3, 7, 15)) {
        val recursive = solution.sumRecursive(n)
        val formula = solution.sumFormula(n)
        val match = recursive.toLong() == formula
        println("N=$n: Recursive=$recursive, Formula=$formula, Match=$match")
    }
}
