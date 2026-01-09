/**
 * ============================================================================
 * PROBLEM: Square Root using Binary Search
 * DIFFICULTY: Easy
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a non-negative integer x, compute and return the square root of x. 
 * Since the return type is an integer, the decimal digits are truncated,
 * and only the integer part of the result is returned.
 * 
 * INPUT FORMAT:
 * - A non-negative integer:  x = 8
 * 
 * OUTPUT FORMAT:
 * - Integer square root (floor value): 2
 *   Because sqrt(8) = 2.828...  and we return the integer part
 * 
 * CONSTRAINTS:
 * - 0 <= x <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of checking every number from 1 to x, we can use binary search
 * on the answer space [0, x].  We're looking for the largest number whose
 * square is <= x.
 * 
 * KEY INSIGHT:
 * If mid * mid <= x, the answer could be mid or something larger.
 * If mid * mid > x, the answer must be smaller than mid.
 * This monotonic property allows us to use binary search. 
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:  low = 0, high = x
 * 2. While low <= high:
 *    - Calculate mid = (low + high) / 2
 *    - If mid * mid == x:  return mid (perfect square)
 *    - If mid * mid < x: answer could be mid, search right (low = mid + 1)
 *    - If mid * mid > x: search left (high = mid - 1)
 * 3. Return high (largest number whose square <= x)
 * 
 * VISUAL EXAMPLE:
 * x = 8, finding floor(sqrt(8))
 * 
 * [0, 1, 2, 3, 4, 5, 6, 7, 8]
 *  L                       H
 *           M=4
 * 4*4=16 > 8, search left
 * 
 * [0, 1, 2, 3]
 *  L        H
 *      M=1
 * 1*1=1 < 8, search right
 * 
 * [2, 3]
 *  L  H
 *  M=2
 * 2*2=4 < 8, search right
 * 
 * [3]
 *  L
 *  H
 * M=3
 * 3*3=9 > 8, search left
 * H becomes 2, loop ends
 * Answer:  2 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log x)
 * - Binary search on range [0, x]
 * - Each iteration eliminates half the search space
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using a few variables
 * - No recursion or extra data structures
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class SquareRoot {
    
    /**
     * Finds integer square root using binary search
     * @param x Non-negative integer
     * @return Floor of square root of x
     */
    fun mySqrt(x: Int): Int {
        // Edge case:  0 and 1 are their own square roots
        if (x < 2) return x
        
        var low = 0L  // Using Long to prevent overflow
        var high = x. toLong()
        var result = 0L
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val square = mid * mid
            
            when {
                square == x. toLong() -> return mid.toInt()  // Perfect square
                square < x -> {
                    result = mid  // mid could be the answer
                    low = mid + 1  // But check if there's a larger answer
                }
                else -> {
                    high = mid - 1  // mid is too large
                }
            }
        }
        
        return result.toInt()
    }
    
    /**
     * Alternative approach using high pointer as answer
     */
    fun mySqrtAlternative(x: Int): Int {
        if (x < 2) return x
        
        var low = 0L
        var high = x. toLong()
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val square = mid * mid
            
            when {
                square == x.toLong() -> return mid.toInt()
                square < x -> low = mid + 1
                else -> high = mid - 1
            }
        }
        
        return high.toInt()  // high will be the floor value
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: x = 8
 * Initial: low=0, high=8, result=0
 * 
 * Iteration 1: mid=4, square=16
 * 16 > 8, high=3
 * 
 * Iteration 2: mid=1, square=1
 * 1 < 8, result=1, low=2
 * 
 * Iteration 3: mid=2, square=4
 * 4 < 8, result=2, low=3
 * 
 * Iteration 4: mid=3, square=9
 * 9 > 8, high=2
 * 
 * low > high, return result=2 ✓
 * 
 * Example 2: x = 16
 * Initial: low=0, high=16
 * 
 * Iteration 1: mid=8, square=64
 * 64 > 16, high=7
 * 
 * Iteration 2: mid=3, square=9
 * 9 < 16, result=3, low=4
 * 
 * Iteration 3: mid=5, square=25
 * 25 > 16, high=4
 * 
 * Iteration 4: mid=4, square=16
 * 16 == 16, return 4 ✓ (perfect square)
 * 
 * ============================================================================
 */

fun main() {
    val solution = SquareRoot()
    
    // Test Case 1: Perfect square
    println("Test 1: sqrt(16) = ${solution.mySqrt(16)}")  // Expected: 4
    
    // Test Case 2: Non-perfect square
    println("Test 2: sqrt(8) = ${solution.mySqrt(8)}")    // Expected: 2
    
    // Test Case 3: Small numbers
    println("Test 3: sqrt(0) = ${solution.mySqrt(0)}")    // Expected: 0
    println("Test 4: sqrt(1) = ${solution.mySqrt(1)}")    // Expected: 1
    println("Test 5: sqrt(4) = ${solution.mySqrt(4)}")    // Expected: 2
    
    // Test Case 4: Large number
    println("Test 6: sqrt(2147395599) = ${solution.mySqrt(2147395599)}")  // Expected: 46339
    
    // Test Case 5: Edge cases
    println("Test 7: sqrt(3) = ${solution.mySqrt(3)}")    // Expected: 1
    println("Test 8: sqrt(15) = ${solution.mySqrt(15)}")  // Expected: 3
    println("Test 9: sqrt(100) = ${solution.mySqrt(100)}") // Expected: 10
}
