/**
 * ============================================================================
 * PROBLEM: Median of Two Sorted Arrays
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two sorted arrays nums1 and nums2 of size m and n respectively,
 * return the median of the two sorted arrays. 
 * 
 * The overall run time complexity should be O(log (m+n)).
 * 
 * INPUT FORMAT:
 * - nums1: First sorted array
 * - nums2: Second sorted array
 * Example: nums1 = [1,3], nums2 = [2]
 * 
 * OUTPUT FORMAT:
 * - Median of combined sorted arrays
 * Example: 2.0
 * Explanation:  Merged = [1,2,3], median = 2
 * 
 * CONSTRAINTS:
 * - nums1.length == m
 * - nums2.length == n
 * - 0 <= m <= 1000
 * - 0 <= n <= 1000
 * - 1 <= m + n <= 2000
 * - -10^6 <= nums1[i], nums2[i] <= 10^6
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of merging arrays, use binary search to find the correct partition.
 * The median is at position (m+n)/2. We need to partition both arrays such
 * that elements on the left are <= elements on the right.
 * 
 * KEY INSIGHT:
 * Partition arrays into left and right halves: 
 * - Left half: nums1[0.. i-1] + nums2[0..j-1]
 * - Right half:  nums1[i..m-1] + nums2[j..n-1]
 * - Total elements in left = Total elements in right (or left has 1 more)
 * 
 * Valid partition when: 
 * - nums1[i-1] <= nums2[j] AND nums2[j-1] <= nums1[i]
 * 
 * Median: 
 * - If (m+n) is odd: max(left side)
 * - If (m+n) is even: average of max(left) and min(right)
 * 
 * ALGORITHM STEPS:
 * 1. Ensure nums1 is the smaller array (for efficiency)
 * 2. Binary search on nums1: low = 0, high = m
 * 3. For partition i in nums1:
 *    - Calculate j in nums2: j = (m+n+1)/2 - i
 *    - Check if valid partition (cross comparison)
 *    - If nums1[i-1] > nums2[j]: move left (high = i-1)
 *    - If nums2[j-1] > nums1[i]: move right (low = i+1)
 *    - Else: found valid partition, calculate median
 * 
 * VISUAL EXAMPLE:
 * nums1 = [1,3], nums2 = [2]
 * m=2, n=1, total=3 (odd)
 * 
 * Left half should have (3+1)/2 = 2 elements
 * 
 * Try i=1 (partition nums1 after index 0):
 *   nums1: [1] | [3]
 *   j = 2 - 1 = 1
 *   nums2: [2] | []
 * 
 * Left:  [1, 2], Right: [3]
 * Check: 1 <= 2 (nums1 left <= nums2 right) ✗ (no nums2 right)
 *        2 <= 3 (nums2 left <= nums1 right) ✓
 * 
 * Valid partition! 
 * Median = max(1, 2) = 2.0 ✓
 * 
 * Example 2: nums1 = [1,2], nums2 = [3,4]
 * m=2, n=2, total=4 (even)
 * 
 * Left half should have (4+1)/2 = 2 elements
 * 
 * Try i=2: 
 *   nums1: [1,2] | []
 *   j = 2 - 2 = 0
 *   nums2: [] | [3,4]
 * 
 * Left: [1,2], Right: [3,4]
 * Check:  2 <= 3 ✓, no left in nums2
 * 
 * Valid partition!
 * Median = (max(1,2) + min(3,4)) / 2 = (2+3)/2 = 2.5 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log(min(m, n)))
 * - Binary search on smaller array
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch. answers

class MedianOfTwoSorted {
    
    /**
     * Finds median of two sorted arrays in O(log(min(m,n))) time
     * @param nums1 First sorted array
     * @param nums2 Second sorted array
     * @return Median of combined arrays
     */
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        // Ensure nums1 is the smaller array
        if (nums1.size > nums2.size) {
            return findMedianSortedArrays(nums2, nums1)
        }
        
        val m = nums1.size
        val n = nums2.size
        var low = 0
        var high = m
        
        while (low <= high) {
            // Partition nums1 at index i
            val i = (low + high) / 2
            // Partition nums2 at index j to balance the halves
            val j = (m + n + 1) / 2 - i
            
            // Get boundary elements
            val maxLeft1 = if (i == 0) Int.MIN_VALUE else nums1[i - 1]
            val minRight1 = if (i == m) Int.MAX_VALUE else nums1[i]
            
            val maxLeft2 = if (j == 0) Int.MIN_VALUE else nums2[j - 1]
            val minRight2 = if (j == n) Int.MAX_VALUE else nums2[j]
            
            // Check if valid partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Found the correct partition
                if ((m + n) % 2 == 0) {
                    // Even total:  average of two middle elements
                    return (maxOf(maxLeft1, maxLeft2) + minOf(minRight1, minRight2)) / 2.0
                } else {
                    // Odd total: max of left side
                    return maxOf(maxLeft1, maxLeft2).toDouble()
                }
            } else if (maxLeft1 > minRight2) {
                // i is too large, move left
                high = i - 1
            } else {
                // i is too small, move right
                low = i + 1
            }
        }
        
        throw IllegalArgumentException("Input arrays are not sorted")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: nums1 = [1,3], nums2 = [2]
 * 
 * m=2, n=1, total=3 (odd)
 * low=0, high=2
 * 
 * Iteration 1: i=1, j=(2+1+1)/2-1=1
 * nums1: [1] | [3]
 * nums2: [2] | []
 * 
 * maxLeft1 = nums1[0] = 1
 * minRight1 = nums1[1] = 3
 * maxLeft2 = nums2[0] = 2
 * minRight2 = MAX_VALUE
 * 
 * Check:  1 <= MAX_VALUE ✓, 2 <= 3 ✓
 * Valid partition!
 * Odd total:  max(1, 2) = 2.0 ✓
 * 
 * Example 2: nums1 = [1,2], nums2 = [3,4]
 * 
 * m=2, n=2, total=4 (even)
 * low=0, high=2
 * 
 * Iteration 1: i=1, j=(2+2+1)/2-1=1
 * nums1: [1] | [2]
 * nums2: [3] | [4]
 * 
 * maxLeft1 = 1, minRight1 = 2
 * maxLeft2 = 3, minRight2 = 4
 * 
 * Check: 1 <= 4 ✓, 3 <= 2 ✗
 * maxLeft2 > minRight1, need larger i
 * low = 2
 * 
 * Iteration 2: i=2, j=(2+2+1)/2-2=0
 * nums1: [1,2] | []
 * nums2: [] | [3,4]
 * 
 * maxLeft1 = 2, minRight1 = MAX_VALUE
 * maxLeft2 = MIN_VALUE, minRight2 = 3
 * 
 * Check: 2 <= 3 ✓, MIN_VALUE <= MAX_VALUE ✓
 * Valid partition!
 * Even total: (max(2,MIN_VALUE) + min(MAX_VALUE,3))/2 = (2+3)/2 = 2.5 ✓
 * 
 * Example 3: nums1 = [], nums2 = [1]
 * 
 * m=0, n=1, total=1 (odd)
 * i=0, j=(0+1+1)/2-0=1
 * 
 * nums1: [] | []
 * nums2: [1] | []
 * 
 * maxLeft1 = MIN_VALUE, minRight1 = MAX_VALUE
 * maxLeft2 = 1, minRight2 = MAX_VALUE
 * 
 * Valid partition!
 * Odd total: max(MIN_VALUE, 1) = 1.0 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = MedianOfTwoSorted()
    
    // Test Case 1: Odd total
    println("Test 1: nums1=[1,3], nums2=[2]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(1,3), intArrayOf(2))}")
    // Expected: 2.0
    
    // Test Case 2: Even total
    println("\nTest 2: nums1=[1,2], nums2=[3,4]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(1,2), intArrayOf(3,4))}")
    // Expected: 2.5
    
    // Test Case 3: Empty array
    println("\nTest 3: nums1=[], nums2=[1]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(), intArrayOf(1))}")
    // Expected: 1.0
    
    // Test Case 4: Different sizes
    println("\nTest 4: nums1=[1], nums2=[2,3,4,5,6]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(1), intArrayOf(2,3,4,5,6))}")
    // Expected: 3.5
    
    // Test Case 5: Negative numbers
    println("\nTest 5: nums1=[-5,-3], nums2=[-2,-1]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(-5,-3), intArrayOf(-2,-1))}")
    // Expected: -2.5
    
    // Test Case 6: Large difference
    println("\nTest 6: nums1=[1,2], nums2=[1000,1001]")
    println("Result: ${solution.findMedianSortedArrays(intArrayOf(1,2), intArrayOf(1000,1001))}")
    // Expected: 501.0
    
    println("\n--- Key Concept ---")
    println("Binary search on partition position, not on values!")
    println("Time:  O(log(min(m,n))) vs naive merge O(m+n)")
}
