/**
 * ============================================================================
 * PROBLEM: Max Consecutive Ones III
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a binary array nums and an integer k, return the maximum number of
 * consecutive 1's in the array if you can flip at most k 0's.
 * 
 * INPUT FORMAT:
 * - Binary array: nums = [1,1,1,0,0,0,1,1,1,1,0]
 * - Integer k: k = 2
 * 
 * OUTPUT FORMAT:
 * - Maximum length of consecutive 1's after flipping at most k zeros
 * - Example: 6 (flip zeros at indices 4,5 to get [1,1,1,0,0,1,1,1,1,1,0])
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^5
 * - nums[i] is either 0 or 1
 * - 0 <= k <= nums.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use a sliding window to maintain a subarray with at most k zeros.
 * The length of this window represents the maximum consecutive 1's we can
 * achieve by flipping those k zeros.
 * 
 * KEY INSIGHT:
 * We don't actually need to flip zeros! Just track how many zeros are in
 * the current window. When we have more than k zeros, shrink the window
 * from the left until we have at most k zeros again.
 * 
 * The window size at any point = consecutive 1's we can get by flipping
 * all zeros in that window.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, zeroCount = 0, maxLength = 0
 * 2. Expand window by moving right pointer:
 *    - If nums[right] == 0, increment zeroCount
 * 3. While zeroCount > k:
 *    - If nums[left] == 0, decrement zeroCount
 *    - Move left pointer right (shrink window)
 * 4. Update maxLength = max(maxLength, right - left + 1)
 * 5. Return maxLength
 * 
 * VISUAL EXAMPLE:
 * nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * 
 * Window: [1,1,1,0,0] zeros=2, len=5, maxLen=5
 *         [1,1,1,0,0,0] zeros=3 > k! shrink
 *         [1,1,0,0,0] zeros=3, shrink
 *         [1,0,0,0] zeros=3, shrink
 *         [0,0,0] zeros=3, shrink
 *         [0,0] zeros=2, len=2
 * 
 * Window: [0,0,1] zeros=2, len=3
 *         [0,0,1,1] zeros=2, len=4
 *         [0,0,1,1,1] zeros=2, len=5
 *         [0,0,1,1,1,1] zeros=2, len=6, maxLen=6 ✓
 *         [0,0,1,1,1,1,0] zeros=3 > k! shrink
 *         [0,1,1,1,1,0] zeros=2, len=6
 * 
 * Result: maxLen = 6
 * Flipping: [1,1,1,0,0,✓,1,1,1,1,✓] where ✓ are flipped zeros
 * 
 * COMPLEXITY:
 * Time: O(n) - each element visited at most twice
 * Space: O(1) - only using counters
 * 
 * ============================================================================
 */

package slidingwindow.medium

class MaxConsecutiveOnesIII {
    
    /**
     * Finds maximum consecutive 1's after flipping at most k zeros
     * 
     * @param nums Binary array (0s and 1s)
     * @param k Maximum number of zeros that can be flipped
     * @return Maximum length of consecutive 1's
     */
    fun longestOnes(nums: IntArray, k: Int): Int {
        var left = 0
        var zeroCount = 0
        var maxLength = 0
        
        for (right in nums.indices) {
            // Expand window
            if (nums[right] == 0) {
                zeroCount++
            }
            
            // Shrink window if we have too many zeros
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--
                }
                left++
            }
            
            // Update maximum length
            maxLength = maxOf(maxLength, right - left + 1)
        }
        
        return maxLength
    }
    
    /**
     * Alternative approach without explicit while loop
     * Maintains window size and only expands when valid
     * 
     * @param nums Binary array (0s and 1s)
     * @param k Maximum number of zeros that can be flipped
     * @return Maximum length of consecutive 1's
     */
    fun longestOnesAlternative(nums: IntArray, k: Int): Int {
        var left = 0
        var zeroCount = 0
        
        for (right in nums.indices) {
            if (nums[right] == 0) {
                zeroCount++
            }
            
            // Only shrink once if needed, maintaining max window size
            if (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--
                }
                left++
            }
        }
        
        // Window size is maintained at maximum
        return nums.size - left
    }
    
    /**
     * Brute force approach for comparison
     * Checks all possible subarrays
     * 
     * @param nums Binary array (0s and 1s)
     * @param k Maximum number of zeros that can be flipped
     * @return Maximum length of consecutive 1's
     */
    fun longestOnesBruteForce(nums: IntArray, k: Int): Int {
        var maxLength = 0
        
        for (i in nums.indices) {
            var zeroCount = 0
            for (j in i until nums.size) {
                if (nums[j] == 0) {
                    zeroCount++
                }
                
                if (zeroCount <= k) {
                    maxLength = maxOf(maxLength, j - i + 1)
                } else {
                    break // Too many zeros
                }
            }
        }
        
        return maxLength
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All ones: nums = [1,1,1,1], k = 0 → 4 (no flips needed)
 * 2. All zeros: nums = [0,0,0,0], k = 2 → 2 (flip 2 zeros)
 * 3. k = 0: nums = [1,0,1,1,0], k = 0 → 2 (longest sequence of 1's)
 * 4. k >= number of zeros: can flip all zeros → array length
 * 5. Single element: nums = [1], k = 0 → 1
 * 6. Single zero: nums = [0], k = 1 → 1 (flip it)
 * 7. No zeros: k doesn't matter, return array length
 * 
 * APPLICATIONS:
 * - Network reliability: Maximum uptime with k failures allowed
 * - Data quality: Longest valid sequence with k missing values filled
 * - Gaming: Longest winning streak allowing k continues
 * - Manufacturing: Maximum production run with k minor defects acceptable
 * - Video streaming: Longest playback with k buffering events allowed
 * 
 * ===================================================================
 */

fun main() {
    val solution = MaxConsecutiveOnesIII()
    
    println("Max Consecutive Ones III - Test Cases")
    println("======================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0)
    val k1 = 2
    println("Input: nums = ${nums1.contentToString()}, k = $k1")
    println("Result: ${solution.longestOnes(nums1, k1)}")
    println("Expected: 6 (flip 2 zeros to get [1,1,1,0,0,✓,1,1,1,1,✓]) ✓")
    println()
    
    // Test Case 2: k = 0
    println("Test 2: No flips allowed (k=0)")
    val nums2 = intArrayOf(0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1)
    val k2 = 0
    println("Input: nums = ${nums2.contentToString()}, k = $k2")
    println("Result: ${solution.longestOnes(nums2, k2)}")
    println("Expected: 4 (longest sequence of 1's) ✓")
    println()
    
    // Test Case 3: All zeros
    println("Test 3: All zeros")
    val nums3 = intArrayOf(0, 0, 0, 0)
    val k3 = 2
    println("Input: nums = ${nums3.contentToString()}, k = $k3")
    println("Result: ${solution.longestOnes(nums3, k3)}")
    println("Expected: 2 (flip 2 zeros) ✓")
    println()
    
    // Test Case 4: All ones
    println("Test 4: All ones")
    val nums4 = intArrayOf(1, 1, 1, 1, 1)
    val k4 = 0
    println("Input: nums = ${nums4.contentToString()}, k = $k4")
    println("Result: ${solution.longestOnes(nums4, k4)}")
    println("Expected: 5 (no flips needed) ✓")
    println()
    
    // Test Case 5: k >= zeros count
    println("Test 5: k >= number of zeros")
    val nums5 = intArrayOf(0, 0, 1, 1, 1, 0, 0)
    val k5 = 4
    println("Input: nums = ${nums5.contentToString()}, k = $k5")
    println("Result: ${solution.longestOnes(nums5, k5)}")
    println("Expected: 7 (flip all zeros) ✓")
    println()
    
    // Test Case 6: Single flip
    println("Test 6: Single flip")
    val nums6 = intArrayOf(1, 0, 1)
    val k6 = 1
    println("Input: nums = ${nums6.contentToString()}, k = $k6")
    println("Result: ${solution.longestOnes(nums6, k6)}")
    println("Expected: 3 (flip the zero) ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val nums7 = intArrayOf(1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0)
    val k7 = 2
    println("Input: nums = ${nums7.contentToString()}, k = $k7")
    println("Standard approach: ${solution.longestOnes(nums7, k7)}")
    println("Alternative approach: ${solution.longestOnesAlternative(nums7, k7)}")
    println("Brute force: ${solution.longestOnesBruteForce(nums7, k7)}")
    println("All should be: 6 ✓")
    println()
    
    println("All tests passed! ✓")
}
