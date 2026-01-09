/**
 * ============================================================================
 * PROBLEM: Nth Root of a Number
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two integers n and m, find the nth root of m. If the nth root is not
 * an integer, return -1.
 * 
 * INPUT FORMAT:
 * - n: The root to find (e.g., n=3 means cube root)
 * - m: The number
 * Example: n = 3, m = 27
 * 
 * OUTPUT FORMAT:
 * - Integer: nth root if it exists and is an integer, -1 otherwise
 * Example: 3 (since 3³ = 27)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 30
 * - 1 <= m <= 10^9
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to square root, we can binary search for the answer. We're looking
 * for a value k such that k^n = m. The monotonic property holds: if k^n < m,
 * then all values less than k are also too small. If k^n > m, then all values
 * greater than k are too large.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space: low = 1, high = m (or optimize to m^(1/n) upper bound)
 * 2. Binary search:
 *    - Calculate mid
 *    - Compute mid^n carefully to avoid overflow
 *    - If mid^n == m, return mid
 *    - If mid^n < m, search right
 *    - If mid^n > m, search left
 * 3. If no exact match found, return -1
 * 
 * VISUAL EXAMPLE:
 * n = 3, m = 27
 * 
 * Find k where k³ = 27:
 * low=1, high=27
 * 
 * mid=14: 14³=2744 > 27, search left
 * mid=7: 7³=343 > 27, search left
 * mid=4: 4³=64 > 27, search left
 * mid=2: 2³=8 < 27, search right
 * mid=3: 3³=27 == 27, found!
 * 
 * Answer: 3
 * 
 * WHY BINARY SEARCH:
 * - Search space: [1 to m]
 * - Monotonic: k^n is strictly increasing with k
 * - We need exact match, not just closest value
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log m * log n)
 * - Binary search: O(log m) iterations
 * - Computing k^n: O(log n) using exponentiation by squaring
 * - Or O(log m * n) if using simple multiplication
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class NthRoot {
    
    /**
     * Find nth root of m using binary search
     * 
     * @param n The root (e.g., 2 for square root, 3 for cube root)
     * @param m The number
     * @return nth root if exists as integer, -1 otherwise
     */
    fun solve(n: Int, m: Int): Int {
        // Edge case: nth root of 1 is always 1
        if (m == 1) return 1
        
        // Search space: [1, m]
        // Can optimize: nth root of m is at most m
        var low = 1
        var high = m
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Calculate mid^n and check against m
            val result = power(mid, n, m)
            
            when (result) {
                // Exact match found
                0 -> return mid
                
                // mid^n < m, try larger values
                -1 -> low = mid + 1
                
                // mid^n > m, try smaller values
                1 -> high = mid - 1
            }
        }
        
        // No exact nth root exists
        return -1
    }
    
    /**
     * Calculate base^n and compare with target
     * Returns:
     *   0 if base^n == target
     *  -1 if base^n < target
     *   1 if base^n > target
     * 
     * This avoids overflow by checking during computation
     */
    private fun power(base: Int, n: Int, target: Int): Int {
        var result = 1L
        
        for (i in 1..n) {
            result *= base
            
            // If result exceeds target during computation, no need to continue
            if (result > target) {
                return 1
            }
        }
        
        return when {
            result == target.toLong() -> 0
            result < target -> -1
            else -> 1
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 3, m = 27
 * 
 * Goal: Find k where k³ = 27
 * 
 * Iteration 1: low=1, high=27
 * - mid = 14
 * - 14³ = 2744 > 27
 * - high = 13
 * 
 * Iteration 2: low=1, high=13
 * - mid = 7
 * - 7³ = 343 > 27
 * - high = 6
 * 
 * Iteration 3: low=1, high=6
 * - mid = 3
 * - 3³ = 27 == 27
 * - Found! Return 3
 * 
 * OUTPUT: 3
 * 
 * ---
 * 
 * INPUT: n = 4, m = 69
 * 
 * Goal: Find k where k⁴ = 69
 * 
 * After binary search, we find:
 * - 2⁴ = 16 < 69
 * - 3⁴ = 81 > 69
 * 
 * No integer k exists where k⁴ = 69
 * 
 * OUTPUT: -1
 * 
 * ============================================================================
 */

fun main() {
    val solver = NthRoot()
    
    println("=== Testing Nth Root ===\n")
    
    // Test Case 1: Perfect cube root
    println("Test 1: n = 3, m = 27")
    println("Result: ${solver.solve(3, 27)}")
    println("Expected: 3\n")
    
    // Test Case 2: Perfect square root
    println("Test 2: n = 2, m = 16")
    println("Result: ${solver.solve(2, 16)}")
    println("Expected: 4\n")
    
    // Test Case 3: No integer root exists
    println("Test 3: n = 4, m = 69")
    println("Result: ${solver.solve(4, 69)}")
    println("Expected: -1\n")
    
    // Test Case 4: Root of 1
    println("Test 4: n = 5, m = 1")
    println("Result: ${solver.solve(5, 1)}")
    println("Expected: 1\n")
    
    // Test Case 5: Large perfect power
    println("Test 5: n = 3, m = 1000")
    println("Result: ${solver.solve(3, 1000)}")
    println("Expected: 10 (10³ = 1000)\n")
    
    // Test Case 6: Large n
    println("Test 6: n = 10, m = 1024")
    println("Result: ${solver.solve(10, 1024)}")
    println("Expected: 2 (2^10 = 1024)\n")
    
    // Test Case 7: No solution
    println("Test 7: n = 2, m = 10")
    println("Result: ${solver.solve(2, 10)}")
    println("Expected: -1\n")
}
