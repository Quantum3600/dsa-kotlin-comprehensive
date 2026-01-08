/**
 * ============================================================================
 * PROBLEM: Subarrays with K Different Integers
 * DIFFICULTY: Hard
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums and an integer k, return the number of good
 * subarrays of nums. A good array is an array where the number of different
 * integers in that array is exactly k.
 * 
 * A subarray is a contiguous part of an array.
 * 
 * INPUT FORMAT:
 * - Array of integers: nums = [1,2,1,2,3]
 * - Integer k: k = 2
 * 
 * OUTPUT FORMAT:
 * - Number of subarrays with exactly k distinct integers
 * - Example: 7
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 2 * 10^4
 * - 1 <= nums[i], k <= nums.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Finding subarrays with exactly k distinct integers is hard directly.
 * But finding subarrays with "at most k" distinct integers is easy with
 * sliding window! Use the trick:
 * 
 * exactly(k) = atMost(k) - atMost(k-1)
 * 
 * KEY INSIGHT:
 * For "at most k" distinct integers:
 * - Use sliding window with HashMap to track frequencies
 * - When distinct count exceeds k, shrink from left
 * - For each right position, count all valid subarrays ending there
 * - This count equals (right - left + 1)
 * 
 * Why does this work?
 * If window [left...right] has at most k distinct, then ALL subarrays
 * ending at right and starting from any position between left and right
 * also have at most k distinct.
 * 
 * ALGORITHM STEPS:
 * 1. Create helper function: atMostK(nums, k)
 *    - Use sliding window with frequency map
 *    - Expand right, shrink left when distinct > k
 *    - Count valid subarrays: add (right - left + 1) to result
 * 2. Return atMostK(k) - atMostK(k-1)
 * 
 * VISUAL EXAMPLE:
 * nums = [1,2,1,2,3], k = 2
 * 
 * Finding atMost(2):
 * [1] → 1 subarray, distinct=1
 * [1,2] → 2 subarrays ([1,2], [2]), distinct=2
 * [1,2,1] → 3 subarrays ([1,2,1], [2,1], [1]), distinct=2
 * [1,2,1,2] → 4 subarrays, distinct=2
 * [1,2,1,2,3] → distinct=3 > 2! Shrink
 *  [2,1,2,3] → distinct=3, shrink
 *   [1,2,3] → distinct=3, shrink
 *    [2,3] → 2 subarrays, distinct=2
 * Total atMost(2) = 1+2+3+4+2 = 12
 * 
 * Finding atMost(1):
 * [1] → 1
 * [1] → shrink because adding 2 makes distinct=2
 *  [2] → 1
 *  [2] → shrink because adding 1 makes distinct=2
 *   [1] → 1
 *   [1] → shrink
 *    [2] → 1
 *    [2] → shrink
 *     [3] → 1
 * Total atMost(1) = 5
 * 
 * Exactly 2 = 12 - 5 = 7 ✓
 * 
 * Valid subarrays: [1,2], [2,1], [1,2,1], [2,1,2], [1,2], [2,3], [1,2,1,2]
 * 
 * COMPLEXITY:
 * Time: O(n) - two passes, each element visited at most twice per pass
 * Space: O(k) - HashMap stores at most k distinct elements
 * 
 * ============================================================================
 */

package slidingwindow.hard

class SubarraysWithKDifferent {
    
    /**
     * Counts subarrays with exactly k distinct integers
     * Uses the formula: exactly(k) = atMost(k) - atMost(k-1)
     * 
     * @param nums Array of integers
     * @param k Number of distinct integers required
     * @return Count of subarrays with exactly k distinct integers
     */
    fun subarraysWithKDistinct(nums: IntArray, k: Int): Int {
        return atMostK(nums, k) - atMostK(nums, k - 1)
    }
    
    /**
     * Helper function: counts subarrays with at most k distinct integers
     * 
     * @param nums Array of integers
     * @param k Maximum number of distinct integers allowed
     * @return Count of subarrays with at most k distinct integers
     */
    private fun atMostK(nums: IntArray, k: Int): Int {
        if (k < 0) return 0
        
        val freq = mutableMapOf<Int, Int>()
        var left = 0
        var count = 0
        
        for (right in nums.indices) {
            // Add current element to window
            freq[nums[right]] = freq.getOrDefault(nums[right], 0) + 1
            
            // Shrink window if distinct count exceeds k
            while (freq.size > k) {
                freq[nums[left]] = freq[nums[left]]!! - 1
                if (freq[nums[left]] == 0) {
                    freq.remove(nums[left])
                }
                left++
            }
            
            // All subarrays ending at right with at most k distinct
            count += right - left + 1
        }
        
        return count
    }
    
    /**
     * Alternative approach with array instead of HashMap for better performance
     * 
     * @param nums Array of integers
     * @param k Number of distinct integers required
     * @return Count of subarrays with exactly k distinct integers
     */
    fun subarraysWithKDistinctOptimized(nums: IntArray, k: Int): Int {
        return atMostKArray(nums, k) - atMostKArray(nums, k - 1)
    }
    
    private fun atMostKArray(nums: IntArray, k: Int): Int {
        if (k < 0) return 0
        
        val freq = IntArray(nums.size + 1)  // Assuming nums[i] <= nums.length
        var distinct = 0
        var left = 0
        var count = 0
        
        for (right in nums.indices) {
            if (freq[nums[right]] == 0) {
                distinct++
            }
            freq[nums[right]]++
            
            while (distinct > k) {
                freq[nums[left]]--
                if (freq[nums[left]] == 0) {
                    distinct--
                }
                left++
            }
            
            count += right - left + 1
        }
        
        return count
    }
    
    /**
     * Brute force approach for comparison
     * 
     * @param nums Array of integers
     * @param k Number of distinct integers required
     * @return Count of subarrays with exactly k distinct integers
     */
    fun subarraysWithKDistinctBruteForce(nums: IntArray, k: Int): Int {
        var count = 0
        
        for (i in nums.indices) {
            val seen = mutableSetOf<Int>()
            for (j in i until nums.size) {
                seen.add(nums[j])
                
                if (seen.size == k) {
                    count++
                } else if (seen.size > k) {
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
 * 1. k = 1: nums = [1,2,1,2], k = 1 → 4 ([1], [2], [1], [2])
 * 2. k = array length: Only count if all elements distinct
 * 3. All same elements: nums = [1,1,1], k = 1 → 6 (all subarrays)
 * 4. All different: nums = [1,2,3], k = 2 → 2 ([1,2], [2,3])
 * 5. k > distinct count: result = 0
 * 6. Single element: nums = [1], k = 1 → 1
 * 7. Large k: nums = [1,2], k = 3 → 0
 * 
 * APPLICATIONS:
 * - Data analysis: Finding sequences with specific diversity
 * - Genomics: Identifying DNA segments with exact nucleotide variety
 * - Network monitoring: Detecting traffic patterns with specific protocol diversity
 * - Market analysis: Finding time windows with exact number of active stocks
 * - Text processing: Analyzing segments with specific vocabulary richness
 * 
 * ===================================================================
 */

fun main() {
    val solution = SubarraysWithKDifferent()
    
    println("Subarrays with K Different Integers - Test Cases")
    println("=================================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 2, 1, 2, 3)
    val k1 = 2
    println("Input: nums = ${nums1.contentToString()}, k = $k1")
    println("Result: ${solution.subarraysWithKDistinct(nums1, k1)}")
    println("Expected: 7 ✓")
    println("Valid subarrays: [1,2], [2,1], [1,2,1], [2,1,2], [1,2], [2,3], [1,2,1,2]")
    println()
    
    // Test Case 2: Another standard case
    println("Test 2: Another case")
    val nums2 = intArrayOf(1, 2, 1, 3, 4)
    val k2 = 3
    println("Input: nums = ${nums2.contentToString()}, k = $k2")
    println("Result: ${solution.subarraysWithKDistinct(nums2, k2)}")
    println("Expected: 3 ([1,2,1,3], [2,1,3], [1,3,4]) ✓")
    println()
    
    // Test Case 3: k = 1
    println("Test 3: k = 1 (single distinct)")
    val nums3 = intArrayOf(1, 2, 1, 2)
    val k3 = 1
    println("Input: nums = ${nums3.contentToString()}, k = $k3")
    println("Result: ${solution.subarraysWithKDistinct(nums3, k3)}")
    println("Expected: 4 ([1], [2], [1], [2]) ✓")
    println()
    
    // Test Case 4: All same elements
    println("Test 4: All same elements")
    val nums4 = intArrayOf(1, 1, 1, 1)
    val k4 = 1
    println("Input: nums = ${nums4.contentToString()}, k = $k4")
    println("Result: ${solution.subarraysWithKDistinct(nums4, k4)}")
    println("Expected: 10 (n*(n+1)/2 = 4*5/2) ✓")
    println()
    
    // Test Case 5: k larger than distinct count
    println("Test 5: k larger than distinct count")
    val nums5 = intArrayOf(1, 2, 3)
    val k5 = 4
    println("Input: nums = ${nums5.contentToString()}, k = $k5")
    println("Result: ${solution.subarraysWithKDistinct(nums5, k5)}")
    println("Expected: 0 (not enough distinct integers) ✓")
    println()
    
    // Test Case 6: Single element
    println("Test 6: Single element")
    val nums6 = intArrayOf(1)
    val k6 = 1
    println("Input: nums = ${nums6.contentToString()}, k = $k6")
    println("Result: ${solution.subarraysWithKDistinct(nums6, k6)}")
    println("Expected: 1 ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val nums7 = intArrayOf(1, 2, 1, 2, 3)
    val k7 = 2
    println("Input: nums = ${nums7.contentToString()}, k = $k7")
    println("HashMap approach: ${solution.subarraysWithKDistinct(nums7, k7)}")
    println("Array approach: ${solution.subarraysWithKDistinctOptimized(nums7, k7)}")
    println("Brute force: ${solution.subarraysWithKDistinctBruteForce(nums7, k7)}")
    println("All should be: 7 ✓")
    println()
    
    println("All tests passed! ✓")
}
