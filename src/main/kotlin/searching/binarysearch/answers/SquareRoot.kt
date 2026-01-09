/**
 * ============================================================================
 * PROBLEM: Square Root using Binary Search
 * DIFFICULTY: Easy
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a non-negative integer n, find the integer square root of n.
 * The integer square root is the largest integer k such that k² <= n.
 * 
 * INPUT FORMAT:
 * - n: Non-negative integer
 * Example: n = 25
 * 
 * OUTPUT FORMAT:
 * - Integer: Floor of square root of n
 * Example: 5 (since 5² = 25)
 * 
 * CONSTRAINTS:
 * - 0 <= n <= 2^31 - 1
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of checking every number from 1 to n, we can use binary search
 * to find the square root. If k² <= n, then any number less than k is also
 * a valid candidate (but not the answer). If k² > n, then k and all larger
 * numbers are too big. This monotonic property enables binary search.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space: low = 0, high = n
 * 2. Binary search:
 *    - Calculate mid
 *    - If mid² == n, return mid (perfect square)
 *    - If mid² < n, answer could be mid or larger, search right
 *    - If mid² > n, answer must be smaller, search left
 * 3. Return the largest value where mid² <= n
 * 
 * VISUAL EXAMPLE:
 * n = 28
 * 
 * Search for largest k where k² <= 28:
 * low=0, high=28
 * 
 * mid=14: 14²=196 > 28, search left
 * mid=7: 7²=49 > 28, search left
 * mid=3: 3²=9 < 28, could be answer, search right
 * mid=5: 5²=25 < 28, could be answer, search right
 * mid=6: 6²=36 > 28, search left
 * 
 * Answer: 5 (5²=25 <= 28, but 6²=36 > 28)
 * 
 * WHY BINARY SEARCH:
 * - Search space: [0 to n]
 * - Monotonic property: If k² > n, then (k+1)² > n as well
 * - If k² <= n, we want largest such k
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log n)
 * - Binary search over range [0, n]
 * - Each iteration eliminates half the search space
 * - For n = 10^9, max iterations ≈ 30
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 */

package searching.binarysearch.answers

class SquareRoot {
    
    /**
     * Find integer square root using binary search
     * 
     * @param n Non-negative integer
     * @return Floor of square root
     */
    fun solve(n: Int): Int {
        // Edge cases
        if (n == 0 || n == 1) return n
        
        // Search space: [0, n]
        // We can optimize to [0, n/2] since sqrt(n) <= n/2 for n >= 4
        var low = 0
        var high = n
        var answer = 0
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Calculate mid² using long to avoid overflow
            val square = mid.toLong() * mid
            
            when {
                // Perfect square found
                square == n.toLong() -> return mid
                
                // mid² < n: mid could be answer, try larger values
                square < n -> {
                    answer = mid
                    low = mid + 1
                }
                
                // mid² > n: mid is too large, try smaller values
                else -> {
                    high = mid - 1
                }
            }
        }
        
        return answer
    }
    
    /**
     * Alternative: Using built-in sqrt for comparison
     * Not allowed in interviews but useful for testing
     */
    fun solveUsingBuiltIn(n: Int): Int {
        return kotlin.math.sqrt(n.toDouble()).toInt()
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: n = 28
 * 
 * Iteration 1: low=0, high=28
 * - mid = 14, 14² = 196 > 28
 * - high = 13
 * 
 * Iteration 2: low=0, high=13
 * - mid = 6, 6² = 36 > 28
 * - high = 5
 * 
 * Iteration 3: low=0, high=5
 * - mid = 2, 2² = 4 < 28
 * - answer = 2, low = 3
 * 
 * Iteration 4: low=3, high=5
 * - mid = 4, 4² = 16 < 28
 * - answer = 4, low = 5
 * 
 * Iteration 5: low=5, high=5
 * - mid = 5, 5² = 25 < 28
 * - answer = 5, low = 6
 * 
 * Iteration 6: low=6, high=5
 * - low > high, exit
 * 
 * OUTPUT: 5
 * 
 * ============================================================================
 */

fun main() {
    val solver = SquareRoot()
    
    println("=== Testing Square Root ===\n")
    
    // Test Case 1: Perfect square
    println("Test 1: n = 25")
    println("Result: ${solver.solve(25)}")
    println("Expected: 5\n")
    
    // Test Case 2: Non-perfect square
    println("Test 2: n = 28")
    println("Result: ${solver.solve(28)}")
    println("Expected: 5\n")
    
    // Test Case 3: Zero
    println("Test 3: n = 0")
    println("Result: ${solver.solve(0)}")
    println("Expected: 0\n")
    
    // Test Case 4: One
    println("Test 4: n = 1")
    println("Result: ${solver.solve(1)}")
    println("Expected: 1\n")
    
    // Test Case 5: Large perfect square
    println("Test 5: n = 144")
    println("Result: ${solver.solve(144)}")
    println("Expected: 12\n")
    
    // Test Case 6: Large non-perfect square
    println("Test 6: n = 150")
    println("Result: ${solver.solve(150)}")
    println("Expected: 12\n")
    
    // Test Case 7: Very large number
    println("Test 7: n = 2147395600")
    println("Result: ${solver.solve(2147395600)}")
    println("Expected: 46340\n")
}
