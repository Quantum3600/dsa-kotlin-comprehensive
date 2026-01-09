/**
 * ============================================================================
 * PROBLEM:  Nth Root of a Number using Binary Search
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two numbers n and m, find the nth root of m.  If the nth root is not
 * an integer, return -1.
 * 
 * The nth root of m is a number x such that x^n = m. 
 * 
 * INPUT FORMAT:
 * - n: The root degree (e.g., 2 for square root, 3 for cube root)
 * - m: The number whose root we need to find
 * Example: n = 3, m = 27
 * 
 * OUTPUT FORMAT:
 * - The nth root if it's an integer, otherwise -1
 * Example: 3 (because 3^3 = 27)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 30
 * - 1 <= m <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to finding square root, but we check if mid^n == m. 
 * The answer space is [1, m], and we use binary search to find the
 * exact value.  If no exact value exists, return -1.
 * 
 * KEY INSIGHT:
 * - If mid^n == m:  Found the answer
 * - If mid^n < m:  Answer might be larger, search right
 * - If mid^n > m: Answer must be smaller, search left
 * 
 * ALGORITHM STEPS:
 * 1. Set search space: low = 1, high = m
 * 2. While low <= high:
 *    - Calculate mid
 *    - Calculate power = mid^n (with overflow check)
 *    - If power == m: return mid
 *    - If power < m:  search right
 *    - If power > m: search left
 * 3. Return -1 (no exact nth root exists)
 * 
 * VISUAL EXAMPLE:
 * n = 3, m = 27 (finding cube root)
 * 
 * [1, 2, 3, 4, 5, .. ., 27]
 *  L                    H
 *              M=14
 * 14^3 = 2744 > 27, search left
 * 
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]
 *  L                                       H
 *                    M=7
 * 7^3 = 343 > 27, search left
 * 
 * [1, 2, 3, 4, 5, 6]
 *  L              H
 *        M=3
 * 3^3 = 27 == 27, return 3 ✓
 * 
 * Example 2: n = 2, m = 10 (no exact square root)
 * Will check values but never find exact match, return -1
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log m * log n)
 * - Binary search:  O(log m) iterations
 * - Power calculation: O(log n) with fast exponentiation
 * - Total: O(log m * log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

import kotlin.math.pow

class NthRoot {
    
    /**
     * Finds the nth root of m if it exists as an integer
     * @param n The degree of the root
     * @param m The number
     * @return The nth root if exact, otherwise -1
     */
    fun nthRoot(n: Int, m: Int): Int {
        // Edge case: any number to the power 1 is itself
        if (n == 1) return m
        
        var low = 1
        var high = m
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val power = calculatePower(mid, n, m)
            
            when (power) {
                0 -> return mid  // mid^n == m (exact match)
                1 -> low = mid + 1  // mid^n < m (too small)
                else -> high = mid - 1  // mid^n > m (too large)
            }
        }
        
        return -1  // No exact nth root exists
    }
    
    /**
     * Calculate mid^n with overflow handling
     * Returns:  0 if mid^n == m, 1 if mid^n < m, 2 if mid^n > m
     */
    private fun calculatePower(mid: Int, n: Int, m: Int): Int {
        var result = 1L
        
        for (i in 1.. n) {
            result *= mid
            
            // Overflow check or exceeds m
            if (result > m) return 2
        }
        
        return when {
            result == m. toLong() -> 0
            result < m -> 1
            else -> 2
        }
    }
    
    /**
     * Alternative using Kotlin's built-in power function
     * Less efficient but simpler
     */
    fun nthRootSimple(n: Int, m:  Int): Int {
        if (n == 1) return m
        
        val root = m.toDouble().pow(1.0 / n).toInt()
        
        // Check both floor and ceiling due to floating point precision
        if (calculatePower(root, n, m) == 0) return root
        if (calculatePower(root + 1, n, m) == 0) return root + 1
        
        return -1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: n = 3, m = 27
 * Finding cube root of 27
 * 
 * Initial: low=1, high=27
 * 
 * Iteration 1: mid=14
 * 14^3 = 2744 > 27, return 2
 * high=13
 * 
 * Iteration 2: mid=7
 * 7^3 = 343 > 27, return 2
 * high=6
 * 
 * Iteration 3: mid=3
 * 3^3 = 27 == 27, return 0
 * Answer: 3 ✓
 * 
 * Example 2: n = 4, m = 69
 * Finding 4th root of 69
 * 
 * Check values around 2.9...  (no exact integer root)
 * 2^4 = 16 < 69
 * 3^4 = 81 > 69
 * Answer: -1 (no exact root)
 * 
 * Example 3: n = 2, m = 9
 * Finding square root of 9
 * 
 * mid=5:  5^2=25 > 9, search left
 * mid=2: 2^2=4 < 9, search right
 * mid=3: 3^2=9 == 9 ✓
 * Answer: 3
 * 
 * ============================================================================
 */

fun main() {
    val solution = NthRoot()
    
    // Test Case 1: Perfect cube root
    println("Test 1: 3rd root of 27 = ${solution.nthRoot(3, 27)}")  // Expected: 3
    
    // Test Case 2: Perfect square root
    println("Test 2: 2nd root of 9 = ${solution.nthRoot(2, 9)}")    // Expected: 3
    
    // Test Case 3: Perfect 4th root
    println("Test 3: 4th root of 81 = ${solution.nthRoot(4, 81)}")  // Expected: 3
    
    // Test Case 4: No exact root
    println("Test 4: 3rd root of 26 = ${solution.nthRoot(3, 26)}")  // Expected: -1
    println("Test 5: 2nd root of 10 = ${solution.nthRoot(2, 10)}")  // Expected: -1
    
    // Test Case 5: Edge cases
    println("Test 6: 1st root of 100 = ${solution.nthRoot(1, 100)}") // Expected: 100
    println("Test 7: 5th root of 32 = ${solution.nthRoot(5, 32)}")   // Expected: 2
    
    // Test Case 6: Large numbers
    println("Test 8: 3rd root of 1000000 = ${solution.nthRoot(3, 1000000)}") // Expected: 100
}
