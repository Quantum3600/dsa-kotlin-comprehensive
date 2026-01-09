/**
 * ============================================================================
 * PROBLEM: Find the Smallest Divisor Given a Threshold
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers nums and an integer threshold, we need to find
 * the smallest divisor such that the sum of the division results (rounded up)
 * is less than or equal to threshold.
 * 
 * For each nums[i], we divide it by the divisor and round up. The sum of all
 * these values should be <= threshold.
 * 
 * INPUT FORMAT:
 * - nums: Array of positive integers
 * - threshold: Maximum allowed sum
 * Example: nums = [1, 2, 5, 9], threshold = 6
 * 
 * OUTPUT FORMAT:
 * - Integer: Smallest divisor such that sum <= threshold
 * Example: 5 (ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5) = 1+1+1+2 = 5 <= 6)
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - 1 <= nums[i] <= 10^6
 * - nums.length <= threshold <= 10^6
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We want smallest divisor that keeps sum <= threshold. If divisor D works,
 * any divisor > D also works (larger divisor = smaller quotients = smaller sum).
 * This monotonic property allows binary search on the divisor.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = 1 (minimum possible divisor)
 *    high = max(nums) (dividing by max gives sum = length)
 * 2. Binary search on divisor:
 *    - For each divisor, calculate sum of ceiling divisions
 *    - If sum <= threshold, try smaller divisor (search left)
 *    - If sum > threshold, need larger divisor (search right)
 * 3. Return smallest divisor that works
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 2, 5, 9], threshold = 6
 * 
 * Try divisor = 5:
 * ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5)
 * = 1 + 1 + 1 + 2 = 5 <= 6 ✓
 * 
 * Try divisor = 4:
 * ceil(1/4) + ceil(2/4) + ceil(5/4) + ceil(9/4)
 * = 1 + 1 + 2 + 3 = 7 > 6 ✗
 * 
 * Answer: 5
 * 
 * WHY BINARY SEARCH:
 * - Search space: [1 to max(nums)]
 * - Monotonic: Larger divisor → smaller sum
 * - Want smallest divisor where sum <= threshold
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(max))
 * - Binary search range: [1, max(nums)]
 * - Iterations: O(log(max))
 * - Each iteration calculates sum: O(n)
 * 
 * For n = 50,000, max = 10^6:
 * - Binary search: ~20 iterations
 * - Each check: 50,000 operations
 * - Total: ~1M operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class SmallestDivisor {
    
    /**
     * Find smallest divisor such that division sum <= threshold
     * 
     * @param nums Array of positive integers
     * @param threshold Maximum allowed sum
     * @return Smallest valid divisor
     */
    fun solve(nums: IntArray, threshold: Int): Int {
        // Search space: [1 to max(nums)]
        var low = 1
        var high = nums.max()
        
        var answer = high
        
        // Binary search on divisor
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Calculate sum with this divisor
            val sum = calculateSum(nums, mid)
            
            if (sum <= threshold) {
                // Divisor works, try smaller
                answer = mid
                high = mid - 1
            } else {
                // Sum too large, need larger divisor
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Calculate sum of ceiling divisions
     * 
     * @param nums Array of numbers
     * @param divisor Divisor to use
     * @return Sum of ceil(nums[i] / divisor)
     */
    private fun calculateSum(nums: IntArray, divisor: Int): Long {
        var sum = 0L
        
        for (num in nums) {
            // Ceiling division: ceil(a/b) = (a + b - 1) / b
            sum += (num + divisor - 1) / divisor
        }
        
        return sum
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: nums = [1, 2, 5, 9], threshold = 6
 * 
 * Step 1: Initialize
 * low = 1, high = 9
 * 
 * Binary Search:
 * 
 * Iteration 1: low=1, high=9
 * - mid = 5
 * - Sum with divisor 5:
 *   ceil(1/5) + ceil(2/5) + ceil(5/5) + ceil(9/5)
 *   = 1 + 1 + 1 + 2 = 5 <= 6 ✓
 * - answer = 5, high = 4
 * 
 * Iteration 2: low=1, high=4
 * - mid = 2
 * - Sum with divisor 2:
 *   ceil(1/2) + ceil(2/2) + ceil(5/2) + ceil(9/2)
 *   = 1 + 1 + 3 + 5 = 10 > 6 ✗
 * - low = 3
 * 
 * Iteration 3: low=3, high=4
 * - mid = 3
 * - Sum with divisor 3:
 *   ceil(1/3) + ceil(2/3) + ceil(5/3) + ceil(9/3)
 *   = 1 + 1 + 2 + 3 = 7 > 6 ✗
 * - low = 4
 * 
 * Iteration 4: low=4, high=4
 * - mid = 4
 * - Sum with divisor 4:
 *   ceil(1/4) + ceil(2/4) + ceil(5/4) + ceil(9/4)
 *   = 1 + 1 + 2 + 3 = 7 > 6 ✗
 * - low = 5
 * 
 * Iteration 5: low=5, high=4
 * - low > high, exit
 * 
 * OUTPUT: 5
 * 
 * ============================================================================
 */

fun main() {
    val solver = SmallestDivisor()
    
    println("=== Testing Smallest Divisor ===\n")
    
    // Test Case 1: Normal case
    val nums1 = intArrayOf(1, 2, 5, 9)
    val threshold1 = 6
    println("Test 1: nums = ${nums1.contentToString()}, threshold = $threshold1")
    println("Result: ${solver.solve(nums1, threshold1)}")
    println("Expected: 5\n")
    
    // Test Case 2: Threshold equals length
    val nums2 = intArrayOf(44, 22, 33, 11, 1)
    val threshold2 = 5
    println("Test 2: nums = ${nums2.contentToString()}, threshold = $threshold2")
    println("Result: ${solver.solve(nums2, threshold2)}")
    println("Expected: 44 (max element)\n")
    
    // Test Case 3: Large threshold
    val nums3 = intArrayOf(2, 3, 5, 7, 11)
    val threshold3 = 11
    println("Test 3: nums = ${nums3.contentToString()}, threshold = $threshold3")
    println("Result: ${solver.solve(nums3, threshold3)}")
    println("Expected: 3\n")
    
    // Test Case 4: Single element
    val nums4 = intArrayOf(10)
    val threshold4 = 5
    println("Test 4: nums = ${nums4.contentToString()}, threshold = $threshold4")
    println("Result: ${solver.solve(nums4, threshold4)}")
    println("Expected: 2\n")
    
    // Test Case 5: All same values
    val nums5 = intArrayOf(10, 10, 10, 10)
    val threshold5 = 8
    println("Test 5: nums = ${nums5.contentToString()}, threshold = $threshold5")
    println("Result: ${solver.solve(nums5, threshold5)}")
    println("Expected: 5\n")
    
    // Test Case 6: Minimum divisor is 1
    val nums6 = intArrayOf(1, 2, 3)
    val threshold6 = 10
    println("Test 6: nums = ${nums6.contentToString()}, threshold = $threshold6")
    println("Result: ${solver.solve(nums6, threshold6)}")
    println("Expected: 1\n")
}
