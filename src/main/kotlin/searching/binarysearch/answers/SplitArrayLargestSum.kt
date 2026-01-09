/**
 * ============================================================================
 * PROBLEM: Split Array Largest Sum
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array nums which consists of non-negative integers and an integer m,
 * you can split the array into m non-empty continuous subarrays. Write an
 * algorithm to minimize the largest sum among these m subarrays.
 * 
 * INPUT FORMAT: 
 * - nums: Array of non-negative integers
 * - m: Number of subarrays to split into
 * Example: nums = [7,2,5,10,8], m = 2
 * 
 * OUTPUT FORMAT:
 * - Minimized largest sum
 * Example:  18
 * Explanation: Split into [7,2,5] and [10,8]
 *              Sums:  14 and 18
 *              Largest sum: 18 (minimized)
 * 
 * CONSTRAINTS:
 * - 1 <= nums. length <= 1000
 * - 0 <= nums[i] <= 10^6
 * - 1 <= m <= min(50, nums.length)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is EXACTLY the same as Book Allocation and Painter's Partition!
 * We're minimizing the maximum sum among m subarrays.  Use binary search
 * on the answer (maximum subarray sum).
 * 
 * KEY INSIGHT:
 * - Minimum possible sum: max(nums) (one subarray must contain max element)
 * - Maximum possible sum: sum(nums) (all elements in one subarray)
 * - For a given max sum, greedily create subarrays
 * - Find the smallest max sum that allows splitting into m subarrays
 * 
 * ALGORITHM STEPS:
 * 1. Binary search:  low = max(nums), high = sum(nums)
 * 2. For each mid:
 *    - Check if we can split into <= m subarrays with each sum <= mid
 *    - Use greedy: add elements to current subarray until exceeds mid
 *    - If subarrays needed <= m: valid, try smaller (high = mid - 1)
 *    - Else: need larger max sum (low = mid + 1)
 * 3. Return low (minimum valid max sum)
 * 
 * VISUAL EXAMPLE:
 * nums = [7,2,5,10,8], m = 2
 * 
 * Split with maxSum = 18:
 * Subarray 1: 7 (7), add 2 (9), add 5 (14) ✓ [can't add 10, would be 24]
 * Subarray 2: 10 (10), add 8 (18) ✓
 * Subarrays:  2 <= 2 ✓
 * 
 * Split with maxSum = 15:
 * Subarray 1: 7 (7), add 2 (9), add 5 (14) ✓ [can't add 10]
 * Subarray 2: 10 (10) ✓ [can't add 8, would be 18]
 * Subarray 3: 8 (8) ✓
 * Subarrays: 3 > 2 ✗
 * 
 * Binary Search Process:
 * low=10 (max), high=32 (sum)
 * 
 * mid=21:  subarrays=2 <= 2, high=20
 * mid=15: subarrays=3 > 2, low=16
 * mid=18: subarrays=2 <= 2, high=17
 * mid=16: subarrays=3 > 2, low=17
 * mid=17: subarrays=3 > 2, low=18
 * low==high=18, return 18 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n * log(sum - max))
 * - Binary search: O(log(sum - max))
 * - Each iteration checks split: O(n)
 * - Total: O(n * log(sum - max))
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class SplitArrayLargestSum {
    
    /**
     * Finds the minimized largest sum when splitting into m subarrays
     * @param nums Array of non-negative integers
     * @param m Number of subarrays
     * @return Minimized largest sum
     */
    fun splitArray(nums: IntArray, m:  Int): Int {
        var low = nums.maxOrNull() ?: 0  // At least max element
        var high = nums. sum()  // At most entire sum
        var result = high
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val subarraysNeeded = countSubarrays(nums, mid)
            
            if (subarraysNeeded <= m) {
                // Can split with maxSum = mid
                result = mid
                high = mid - 1  // Try to minimize further
            } else {
                // Need larger max sum
                low = mid + 1
            }
        }
        
        return result
    }
    
    /**
     * Counts minimum subarrays needed if each can have sum at most maxSum
     * @param nums Array of integers
     * @param maxSum Maximum sum per subarray
     * @return Number of subarrays needed
     */
    private fun countSubarrays(nums: IntArray, maxSum: Int): Int {
        var subarrays = 1
        var currentSum = 0
        
        for (num in nums) {
            if (currentSum + num <= maxSum) {
                // Add this element to current subarray
                currentSum += num
            } else {
                // Need a new subarray for this element
                subarrays++
                currentSum = num
            }
        }
        
        return subarrays
    }
    
    /**
     * Returns the actual split subarrays
     */
    fun splitArrayWithPartition(nums: IntArray, m: Int): Pair<Int, List<List<Int>>> {
        val maxSum = splitArray(nums, m)
        
        // Reconstruct the partition
        val partition = mutableListOf<MutableList<Int>>()
        partition.add(mutableListOf())
        var currentSum = 0
        
        for (num in nums) {
            if (currentSum + num <= maxSum) {
                currentSum += num
                partition.last().add(num)
            } else {
                partition.add(mutableListOf(num))
                currentSum = num
            }
        }
        
        return Pair(maxSum, partition)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: nums = [7,2,5,10,8], m = 2
 * 
 * Initial: low=10, high=32
 * 
 * Iteration 1: mid=21
 * Subarray split:
 * - Subarray 1: 7+2+5+10 = 24 > 21
 * - Subarray 1: 7+2+5 = 14 <= 21 ✓
 * - Subarray 2: 10+8 = 18 <= 21 ✓
 * Subarrays: 2 <= 2, result=21, high=20
 * 
 * Iteration 2: mid=15
 * Subarray split:
 * - Subarray 1: 7+2+5 = 14 <= 15 ✓
 * - Subarray 2: 10 <= 15 ✓
 * - Subarray 3: 8 <= 15 ✓
 * Subarrays: 3 > 2, low=16
 * 
 * Iteration 3: mid=18
 * Subarray split: 
 * - Subarray 1: 7+2+5 = 14 <= 18 ✓
 * - Subarray 2: 10+8 = 18 <= 18 ✓
 * Subarrays: 2 <= 2, result=18, high=17
 * 
 * Iteration 4: mid=16
 * Subarray split:
 * - Subarray 1: 7+2+5 = 14 <= 16 ✓
 * - Subarray 2: 10 <= 16 ✓
 * - Subarray 3: 8 <= 16 ✓
 * Subarrays: 3 > 2, low=17
 * 
 * Iteration 5: mid=17
 * Subarray split:  (same as mid=16)
 * Subarrays: 3 > 2, low=18
 * 
 * low == high = 18, return 18 ✓
 * 
 * Example 2: nums = [1,2,3,4,5], m = 2
 * 
 * low=5, high=15
 * 
 * mid=10:
 * - Subarray 1: 1+2+3+4 = 10 ✓
 * - Subarray 2: 5 ✓
 * Subarrays: 2, result=10, high=9
 * 
 * mid=7:
 * - Subarray 1: 1+2+3 = 6 ✓
 * - Subarray 2: 4 ✓
 * - Subarray 3: 5 ✓
 * Subarrays: 3 > 2, low=8
 * 
 * mid=8:
 * - Subarray 1: 1+2+3 = 6, add 4 = 10 > 8
 * - Subarray 1: 1+2+3 = 6 ✓
 * - Subarray 2: 4 ✓
 * - Subarray 3: 5 ✓
 * Subarrays: 3 > 2, low=9
 * 
 * mid=9:
 * - Subarray 1: 1+2+3 = 6, add 4 = 10 > 9
 * - Subarray 1: 1+2+3 = 6 ✓
 * - Subarray 2: 4+5 = 9 ✓
 * Subarrays: 2, result=9, high=8
 * 
 * low > high, return result=9 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = SplitArrayLargestSum()
    
    // Test Case 1: Standard case
    println("Test 1: nums=[7,2,5,10,8], m=2")
    val (sum1, partition1) = solution.splitArrayWithPartition(intArrayOf(7,2,5,10,8), 2)
    println("Result: $sum1")
    partition1.forEachIndexed { i, subarray ->
        println("  Subarray ${i+1}: ${subarray.joinToString("+")} = ${subarray.sum()}")
    }
    // Expected: 18
    
    // Test Case 2: Balanced split
    println("\nTest 2: nums=[1,2,3,4,5], m=2")
    val (sum2, partition2) = solution.splitArrayWithPartition(intArrayOf(1,2,3,4,5), 2)
    println("Result: $sum2")
    partition2.forEachIndexed { i, subarray ->
        println("  Subarray ${i+1}: ${subarray. joinToString("+")} = ${subarray.sum()}")
    }
    // Expected: 9
    
    // Test Case 3: Single element per subarray
    println("\nTest 3: nums=[1,4,4], m=3")
    println("Result: ${solution.splitArray(intArrayOf(1,4,4), 3)}")
    // Expected: 4
    
    // Test Case 4: All in one subarray
    println("\nTest 4: nums=[10,20,30], m=1")
    println("Result: ${solution.splitArray(intArrayOf(10,20,30), 1)}")
    // Expected: 60
    
    // Test Case 5: Large numbers
    println("\nTest 5: nums=[1,1000000,1,1], m=2")
    println("Result: ${solution.splitArray(intArrayOf(1,1000000,1,1), 2)}")
    // Expected: 1000000
    
    // Show relationship to other problems
    println("\n--- This problem is identical to:  ---")
    println("1. Book Allocation Problem")
    println("2. Painter's Partition Problem")
    println("3. Split Array Largest Sum")
    println("\nAll three use the same 'minimize the maximum' binary search pattern!")
}
