/**
 * ============================================================================
 * PROBLEM:  Smallest Divisor Given a Threshold
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers nums and an integer threshold, choose a positive
 * integer divisor, divide all the array elements by it, and sum the division's
 * result. Find the smallest divisor such that the result is less than or equal
 * to the threshold.
 * 
 * Each result of the division is rounded to the nearest integer greater than
 * or equal to that element (ceiling division).
 * 
 * INPUT FORMAT:
 * - nums: Array of integers
 * - threshold: Maximum allowed sum
 * Example: nums = [1,2,5,9], threshold = 6
 * 
 * OUTPUT FORMAT:
 * - The smallest divisor
 * Example: 5
 * Explanation: ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5) = 1+1+1+2 = 5 <= 6
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - 1 <= nums[i] <= 10^6
 * - nums. length <= threshold <= 10^6
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to find the smallest divisor that makes the sum <= threshold.
 * As the divisor increases, the sum decreases (inverse relationship).
 * This monotonic property allows binary search on the divisor value.
 * 
 * KEY INSIGHT:
 * - Divisor range: [1, max(nums)]
 * - If divisor is max(nums), sum will be minimum (all 1s)
 * - If divisor is 1, sum will be maximum (original sum)
 * - Find the smallest divisor where sum <= threshold
 * 
 * ALGORITHM STEPS: 
 * 1. Set search space: low = 1, high = max(nums)
 * 2. While low < high:
 *    - mid = (low + high) / 2
 *    - Calculate sum with mid as divisor
 *    - If sum <= threshold:  answer could be mid or smaller, high = mid
 *    - If sum > threshold: divisor too small, low = mid + 1
 * 3. Return low (smallest valid divisor)
 * 
 * VISUAL EXAMPLE: 
 * nums = [1, 2, 5, 9], threshold = 6
 * 
 * Divisor = 1: 1+2+5+9 = 17 > 6 (too small divisor)
 * Divisor = 2: 1+1+3+5 = 10 > 6
 * Divisor = 3: 1+1+2+3 = 7 > 6
 * Divisor = 4: 1+1+2+3 = 7 > 6
 * Divisor = 5: 1+1+1+2 = 5 <= 6 ✓ (valid!)
 * Divisor = 6: 1+1+1+2 = 5 <= 6
 * 
 * Smallest valid divisor = 5
 * 
 * Binary Search Process:
 * [1, 2, 3, 4, 5, 6, 7, 8, 9]
 *  L                       H
 *              M=5
 * sum=5 <= 6, try smaller:  high=5
 * 
 * [1, 2, 3, 4, 5]
 *  L           H
 *        M=3
 * sum=7 > 6, need larger:  low=4
 * 
 * [4, 5]
 *  L  H
 *  M=4
 * sum=7 > 6, need larger:  low=5
 * 
 * [5]
 * L=H=5, return 5 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n * log m)
 * - Binary search:  O(log m) where m = max(nums)
 * - Each iteration calculates sum:  O(n)
 * - Total: O(n * log m)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch. answers

import kotlin.math.ceil

class SmallestDivisor {
    
    /**
     * Finds the smallest divisor such that sum <= threshold
     * @param nums Array of integers
     * @param threshold Maximum allowed sum
     * @return The smallest valid divisor
     */
    fun smallestDivisor(nums:  IntArray, threshold: Int): Int {
        var low = 1
        var high = nums.maxOrNull() ?: 1
        
        while (low < high) {
            val mid = low + (high - low) / 2
            val sum = calculateSum(nums, mid)
            
            if (sum <= threshold) {
                // mid works, but check if smaller divisor works
                high = mid
            } else {
                // mid is too small, need larger divisor
                low = mid + 1
            }
        }
        
        return low
    }
    
    /**
     * Calculates the sum of ceiling divisions
     * @param nums Array to divide
     * @param divisor The divisor
     * @return Sum of ceil(nums[i] / divisor)
     */
    private fun calculateSum(nums: IntArray, divisor: Int): Int {
        var sum = 0
        for (num in nums) {
            // Ceiling division: (num + divisor - 1) / divisor
            sum += (num + divisor - 1) / divisor
        }
        return sum
    }
    
    /**
     * Alternative ceiling division using Kotlin's ceil
     */
    private fun calculateSumAlternative(nums: IntArray, divisor: Int): Int {
        return nums.sumOf { 
            ceil(it. toDouble() / divisor).toInt()
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: nums = [1,2,5,9], threshold = 6
 * 
 * Initial: low=1, high=9
 * 
 * Iteration 1: mid=5
 * sum = ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5)
 *     = 1 + 1 + 1 + 2 = 5 <= 6
 * high = 5
 * 
 * Iteration 2: mid=3
 * sum = ceil(1/3) + ceil(2/3) + ceil(5/3) + ceil(9/3)
 *     = 1 + 1 + 2 + 3 = 7 > 6
 * low = 4
 * 
 * Iteration 3: mid=4
 * sum = ceil(1/4) + ceil(2/4) + ceil(5/4) + ceil(9/4)
 *     = 1 + 1 + 2 + 3 = 7 > 6
 * low = 5
 * 
 * low == high, return 5 ✓
 * 
 * Example 2: nums = [44,22,33,11,1], threshold = 5
 * Max = 44
 * 
 * Need divisor where sum <= 5
 * Since array has 5 elements, minimum sum is 5 (all 1s)
 * Divisor = 44:  all become 1, sum = 5 ✓
 * But can we find smaller? 
 * 
 * Divisor = 22: ceil(44/22)+ceil(22/22)+ceil(33/22)+ceil(11/22)+ceil(1/22)
 *             = 2+1+2+1+1 = 7 > 5
 * Divisor = 33: 2+1+1+1+1 = 6 > 5
 * Divisor = 44: 1+1+1+1+1 = 5 <= 5 ✓
 * 
 * Answer:  44
 * 
 * ============================================================================
 */

fun main() {
    val solution = SmallestDivisor()
    
    // Test Case 1: Standard case
    println("Test 1: nums=[1,2,5,9], threshold=6")
    println("Result: ${solution.smallestDivisor(intArrayOf(1,2,5,9), 6)}")  
    // Expected: 5
    
    // Test Case 2: Threshold equals array length
    println("\nTest 2: nums=[44,22,33,11,1], threshold=5")
    println("Result: ${solution.smallestDivisor(intArrayOf(44,22,33,11,1), 5)}")  
    // Expected: 44
    
    // Test Case 3: Large threshold
    println("\nTest 3: nums=[2,3,5,7,11], threshold=11")
    println("Result: ${solution.smallestDivisor(intArrayOf(2,3,5,7,11), 11)}")  
    // Expected: 3
    
    // Test Case 4: Single element
    println("\nTest 4: nums=[10], threshold=1")
    println("Result: ${solution.smallestDivisor(intArrayOf(10), 1)}")  
    // Expected: 10
    
    // Test Case 5: All same numbers
    println("\nTest 5: nums=[5,5,5,5], threshold=8")
    println("Result: ${solution.smallestDivisor(intArrayOf(5,5,5,5), 8)}")  
    // Expected: 3
    
    // Detailed walkthrough for Test 1
    println("\n--- Detailed Walkthrough for [1,2,5,9], threshold=6 ---")
    val nums = intArrayOf(1,2,5,9)
    for (div in 1..9) {
        val sum = nums.sumOf { (it + div - 1) / div }
        val status = if (sum <= 6) "✓" else "✗"
        println("Divisor=$div:  sum=$sum $status")
    }
}
