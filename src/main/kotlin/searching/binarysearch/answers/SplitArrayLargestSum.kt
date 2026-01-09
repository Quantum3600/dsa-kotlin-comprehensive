/**
 * ============================================================================
 * PROBLEM: Split Array Largest Sum
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array nums and an integer k, split the array into k non-empty
 * continuous subarrays. Minimize the largest sum among these k subarrays.
 * 
 * INPUT FORMAT:
 * - nums: Array of non-negative integers
 * - k: Number of subarrays to split into
 * Example: nums = [7, 2, 5, 10, 8], k = 2
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimized largest sum among the k subarrays
 * Example: 18 (Split into [7,2,5] sum=14 and [10,8] sum=18, max=18)
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 1000
 * - 0 <= nums[i] <= 10^6
 * - 1 <= k <= min(50, nums.length)
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is identical to Book Allocation and Painter's Partition! We want to
 * split array into k subarrays minimizing the maximum sum. If max sum S works,
 * any sum > S also works (monotonic). Binary search on the answer.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = max(nums) (at least one subarray contains max element)
 *    high = sum(nums) (one subarray contains all elements)
 * 2. Binary search on maximum sum:
 *    - For each mid, check if split possible with max sum = mid
 *    - If yes, try smaller sum (search left)
 *    - If no, need larger sum (search right)
 * 3. Return minimum valid max sum
 * 
 * VISUAL EXAMPLE:
 * nums = [7, 2, 5, 10, 8], k = 2
 * 
 * Try max sum = 18:
 * Subarray 1: [7, 2, 5] = 14 ✓
 * Subarray 2: [10, 8] = 18 ✓
 * Need 2 subarrays ✓
 * 
 * Try max sum = 15:
 * Subarray 1: [7, 2, 5] = 14 ✓
 * Subarray 2: [10] = 10 ✓
 * Subarray 3: [8] = 8 ✓
 * Need 3 subarrays ✗ (only have k=2)
 * 
 * Answer: 18
 * 
 * WHY BINARY SEARCH:
 * - Search space: [max(nums) to sum(nums)]
 * - Monotonic: If max sum S works, S+1 also works
 * - Want minimum S that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search range: [max, sum]
 * - Iterations: O(log(sum - max))
 * - Each iteration checks split: O(n)
 * 
 * For n = 1000, max = 10^6, sum = 10^9:
 * - Binary search: ~30 iterations
 * - Each check: 1000 operations
 * - Total: ~30,000 operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class SplitArrayLargestSum {
    
    /**
     * Split array into k subarrays minimizing largest sum
     * 
     * @param nums Array of non-negative integers
     * @param k Number of subarrays
     * @return Minimized largest sum
     */
    fun solve(nums: IntArray, k: Int): Int {
        // Edge case: k >= array length, each element in own subarray
        if (k >= nums.size) {
            return nums.max()
        }
        
        // Search space: [max element, sum of all elements]
        var low = nums.max()
        var high = nums.sum()
        
        var answer = high
        
        // Binary search on maximum subarray sum
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Check if split possible with this max sum
            if (canSplit(nums, k, mid)) {
                // Possible, try smaller max sum
                answer = mid
                high = mid - 1
            } else {
                // Not possible, need larger max sum
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Check if array can be split into k subarrays with max sum constraint
     * 
     * @param nums Array of numbers
     * @param k Number of subarrays allowed
     * @param maxSum Maximum sum any subarray should have
     * @return true if split possible, false otherwise
     */
    private fun canSplit(nums: IntArray, k: Int, maxSum: Int): Boolean {
        var subarraysNeeded = 1
        var currentSum = 0
        
        for (num in nums) {
            // If adding this number exceeds max sum for current subarray
            if (currentSum + num > maxSum) {
                // Start new subarray
                subarraysNeeded++
                currentSum = num
                
                // If we need more subarrays than allowed
                if (subarraysNeeded > k) {
                    return false
                }
            } else {
                // Add to current subarray
                currentSum += num
            }
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: nums = [7, 2, 5, 10, 8], k = 2
 * 
 * Step 1: Initialize
 * low = 10, high = 32
 * 
 * Binary Search:
 * 
 * Iteration 1: low=10, high=32
 * - mid = 21
 * - Can split with max sum 21?
 *   Subarray 1: 7+2+5 = 14 ✓
 *   Subarray 2: 10+8 = 18 ✓
 *   Yes! 2 subarrays needed
 * - answer = 21, high = 20
 * 
 * Iteration 2: low=10, high=20
 * - mid = 15
 * - Can split with max sum 15?
 *   Subarray 1: 7+2+5 = 14 ✓
 *   Subarray 2: 10 = 10 ✓
 *   Subarray 3: 8 = 8 ✗ Need 3 subarrays!
 *   No!
 * - low = 16
 * 
 * Continue searching...
 * Final answer = 18
 * 
 * ============================================================================
 */

fun main() {
    val solver = SplitArrayLargestSum()
    
    println("=== Testing Split Array Largest Sum ===\n")
    
    // Test Case 1: Normal case
    val nums1 = intArrayOf(7, 2, 5, 10, 8)
    val k1 = 2
    println("Test 1: nums = ${nums1.contentToString()}, k = $k1")
    println("Result: ${solver.solve(nums1, k1)}")
    println("Expected: 18\n")
    
    // Test Case 2: Each element in own subarray
    val nums2 = intArrayOf(1, 2, 3, 4, 5)
    val k2 = 5
    println("Test 2: nums = ${nums2.contentToString()}, k = $k2")
    println("Result: ${solver.solve(nums2, k2)}")
    println("Expected: 5\n")
    
    // Test Case 3: Single subarray
    val nums3 = intArrayOf(1, 4, 4)
    val k3 = 1
    println("Test 3: nums = ${nums3.contentToString()}, k = $k3")
    println("Result: ${solver.solve(nums3, k3)}")
    println("Expected: 9\n")
    
    // Test Case 4: k = 3
    val nums4 = intArrayOf(1, 4, 4)
    val k4 = 3
    println("Test 4: nums = ${nums4.contentToString()}, k = $k4")
    println("Result: ${solver.solve(nums4, k4)}")
    println("Expected: 4\n")
    
    // Test Case 5: Larger array
    val nums5 = intArrayOf(10, 20, 30, 40, 50, 60, 70)
    val k5 = 3
    println("Test 5: nums = ${nums5.contentToString()}, k = $k5")
    println("Result: ${solver.solve(nums5, k5)}")
    println("Expected: 110\n")
    
    // Test Case 6: All same values
    val nums6 = intArrayOf(5, 5, 5, 5, 5)
    val k6 = 2
    println("Test 6: nums = ${nums6.contentToString()}, k = $k6")
    println("Result: ${solver.solve(nums6, k6)}")
    println("Expected: 15\n")
}
