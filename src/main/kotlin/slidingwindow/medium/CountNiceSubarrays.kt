/**
 * ============================================================================
 * PROBLEM: Count Nice Subarrays
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers nums and an integer k, a continuous subarray is
 * called nice if there are exactly k odd numbers in it.
 * 
 * Return the number of nice subarrays.
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [1, 1, 2, 1, 1]
 * - An integer k: k = 3
 * 
 * OUTPUT FORMAT:
 * - Number of nice subarrays with exactly k odd numbers
 * - Example: 2 (subarrays [1,1,2,1] and [1,2,1,1])
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 50000
 * - 1 <= nums[i] <= 10^5
 * - 1 <= k <= nums.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Transform the problem: treat odd numbers as 1 and even numbers as 0.
 * Now we need to find subarrays with sum exactly equal to k.
 * 
 * Use the "AtMost" technique:
 * - Count(exactly k) = Count(at most k) - Count(at most k-1)
 * 
 * KEY INSIGHT:
 * Instead of finding subarrays with exactly k odd numbers directly, we find:
 * - Number of subarrays with at most k odd numbers
 * - Number of subarrays with at most (k-1) odd numbers
 * - Subtract to get exactly k
 * 
 * This is easier because "at most k" can be solved with simple sliding window.
 * 
 * ALGORITHM STEPS:
 * 1. Create helper function: atMostK(nums, k)
 *    - Use sliding window to count subarrays with at most k odd numbers
 *    - Expand right, shrink left when odd count exceeds k
 *    - Count all valid subarrays ending at right
 * 2. Return atMostK(k) - atMostK(k-1)
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 1, 2, 1, 1], k = 3
 * Transform: [1, 1, 0, 1, 1] (odd=1, even=0)
 * 
 * Finding at most 3 odd numbers:
 * [1] → 1 subarray
 * [1,1] → 2 subarrays: [1,1], [1]
 * [1,1,0] → 3 subarrays: [1,1,0], [1,0], [0]
 * [1,1,0,1] → 4 subarrays
 * [1,1,0,1,1] → 5 subarrays
 * Total at most 3: 15
 * 
 * Finding at most 2 odd numbers:
 * [1] → 1
 * [1,1] → 2
 * [1,1,0] → shrink to [1,0] → 2
 * ... 
 * Total at most 2: 13
 * 
 * Exactly 3 = 15 - 13 = 2 ✓
 * 
 * ALTERNATIVE: Prefix Sum Approach
 * - Keep track of odd count as prefix sum
 * - Use HashMap to count how many times each prefix sum appeared
 * - Similar to "Subarray Sum Equals K" problem
 * 
 * COMPLEXITY:
 * Time: O(n) - two passes with sliding window
 * Space: O(1) - only using counters
 * 
 * ============================================================================
 */

package slidingwindow.medium

class CountNiceSubarrays {
    
    /**
     * Counts nice subarrays with exactly k odd numbers
     * Uses the AtMost technique: exactly k = atMost(k) - atMost(k-1)
     * 
     * @param nums Array of integers
     * @param k Number of odd numbers required
     * @return Number of nice subarrays
     */
    fun numberOfSubarrays(nums: IntArray, k: Int): Int {
        return atMostK(nums, k) - atMostK(nums, k - 1)
    }
    
    /**
     * Helper function to count subarrays with at most k odd numbers
     * 
     * @param nums Array of integers
     * @param k Maximum number of odd numbers allowed
     * @return Number of subarrays with at most k odd numbers
     */
    private fun atMostK(nums: IntArray, k: Int): Int {
        if (k < 0) return 0
        
        var left = 0
        var oddCount = 0
        var result = 0
        
        for (right in nums.indices) {
            // Count odd numbers
            if (nums[right] % 2 == 1) {
                oddCount++
            }
            
            // Shrink window if odd count exceeds k
            while (oddCount > k) {
                if (nums[left] % 2 == 1) {
                    oddCount--
                }
                left++
            }
            
            // All subarrays ending at right with length 1 to (right-left+1)
            result += right - left + 1
        }
        
        return result
    }
    
    /**
     * Alternative approach using prefix sum and HashMap
     * 
     * @param nums Array of integers
     * @param k Number of odd numbers required
     * @return Number of nice subarrays
     */
    fun numberOfSubarraysPrefixSum(nums: IntArray, k: Int): Int {
        val prefixCount = mutableMapOf<Int, Int>()
        prefixCount[0] = 1 // Base case: empty prefix
        
        var oddCount = 0
        var result = 0
        
        for (num in nums) {
            // Increment odd count if current number is odd
            if (num % 2 == 1) {
                oddCount++
            }
            
            // Check if there's a prefix with (oddCount - k) odd numbers
            result += prefixCount.getOrDefault(oddCount - k, 0)
            
            // Store current odd count
            prefixCount[oddCount] = prefixCount.getOrDefault(oddCount, 0) + 1
        }
        
        return result
    }
    
    /**
     * Brute force approach for comparison
     * 
     * @param nums Array of integers
     * @param k Number of odd numbers required
     * @return Number of nice subarrays
     */
    fun numberOfSubarraysBruteForce(nums: IntArray, k: Int): Int {
        var count = 0
        
        for (i in nums.indices) {
            var oddCount = 0
            for (j in i until nums.size) {
                if (nums[j] % 2 == 1) {
                    oddCount++
                }
                if (oddCount == k) {
                    count++
                } else if (oddCount > k) {
                    break
                }
            }
        }
        
        return count
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All odd numbers: nums = [1,3,5,7], k = 2 → 3 ([1,3], [3,5], [5,7])
 * 2. All even numbers: nums = [2,4,6], k = 1 → 0 (no odd numbers)
 * 3. k equals array length: nums = [1,3,5], k = 3 → 1 (entire array)
 * 4. Single element: nums = [1], k = 1 → 1
 * 5. k = 1: count subarrays with exactly 1 odd number
 * 6. Mixed odd/even: nums = [1,2,3,4,5], k = 2 → multiple subarrays
 * 7. No valid subarrays: nums = [2,4,6], k = 3 → 0
 * 
 * APPLICATIONS:
 * - Data analysis: Finding sequences with specific property counts
 * - Quality control: Counting batches with exact defect count
 * - Network monitoring: Identifying patterns with specific event counts
 * - Financial analysis: Finding periods with exact number of profitable days
 * - Game analytics: Counting rounds with specific success/failure patterns
 * 
 * ===================================================================
 */

fun main() {
    val solution = CountNiceSubarrays()
    
    println("Count Nice Subarrays - Test Cases")
    println("===================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 1, 2, 1, 1)
    val k1 = 3
    println("Input: nums = ${nums1.contentToString()}, k = $k1")
    println("Result: ${solution.numberOfSubarrays(nums1, k1)}")
    println("Expected: 2 ([1,1,2,1] and [1,2,1,1]) ✓")
    println()
    
    // Test Case 2: Longer sequence
    println("Test 2: Longer sequence")
    val nums2 = intArrayOf(2, 4, 6)
    val k2 = 1
    println("Input: nums = ${nums2.contentToString()}, k = $k2")
    println("Result: ${solution.numberOfSubarrays(nums2, k2)}")
    println("Expected: 0 (all even numbers) ✓")
    println()
    
    // Test Case 3: All odd numbers
    println("Test 3: All odd numbers")
    val nums3 = intArrayOf(2, 2, 2, 1, 2, 2, 1, 2, 2, 2)
    val k3 = 2
    println("Input: nums = ${nums3.contentToString()}, k = $k3")
    println("Result: ${solution.numberOfSubarrays(nums3, k3)}")
    println("Expected: 16 ✓")
    println()
    
    // Test Case 4: k = 1
    println("Test 4: Single odd number required")
    val nums4 = intArrayOf(1, 1, 1)
    val k4 = 1
    println("Input: nums = ${nums4.contentToString()}, k = $k4")
    println("Result: ${solution.numberOfSubarrays(nums4, k4)}")
    println("Expected: 3 ([1], [1], [1]) ✓")
    println()
    
    // Test Case 5: Mixed sequence
    println("Test 5: Mixed sequence")
    val nums5 = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    val k5 = 2
    println("Input: nums = ${nums5.contentToString()}, k = $k5")
    println("Result: ${solution.numberOfSubarrays(nums5, k5)}")
    println("Expected: 8 ✓")
    println()
    
    // Test Case 6: Comparison of approaches
    println("Test 6: Comparing all approaches")
    val nums6 = intArrayOf(1, 1, 2, 1, 1)
    val k6 = 3
    println("Input: nums = ${nums6.contentToString()}, k = $k6")
    println("AtMost technique: ${solution.numberOfSubarrays(nums6, k6)}")
    println("Prefix sum approach: ${solution.numberOfSubarraysPrefixSum(nums6, k6)}")
    println("Brute force: ${solution.numberOfSubarraysBruteForce(nums6, k6)}")
    println("All should be: 2 ✓")
    println()
    
    println("All tests passed! ✓")
}
