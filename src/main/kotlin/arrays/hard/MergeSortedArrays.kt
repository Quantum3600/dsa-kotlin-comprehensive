/**
 * ============================================================================
 * PROBLEM: Merge Sorted Arrays
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Two Pointers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing
 * order, and two integers m and n, representing the number of elements in
 * nums1 and nums2 respectively. Merge nums2 into nums1 as one sorted array.
 * 
 * The final sorted array should be stored inside nums1. nums1 has a length
 * of m + n where the first m elements are the ones to merge, and the last n
 * elements are 0 and should be ignored. nums2 has length n.
 * 
 * INPUT FORMAT:
 * - nums1 = [1,2,3,0,0,0], m = 3
 * - nums2 = [2,5,6], n = 3
 * 
 * OUTPUT FORMAT:
 * - nums1 modified in-place: [1,2,2,3,5,6]
 * 
 * CONSTRAINTS:
 * - nums1.length == m + n
 * - nums2.length == n
 * - 0 <= m, n <= 200
 * - 1 <= m + n <= 200
 * - -10^9 <= nums1[i], nums2[j] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of merging from left to right (which requires shifting),
 * merge from right to left. Start from the largest elements and place
 * them at the end of nums1.
 * 
 * KEY INSIGHT:
 * - nums1 has extra space at the end
 * - Merging backwards avoids overwriting unprocessed elements
 * - Three pointers: p1 (nums1 end), p2 (nums2 end), p (merge position)
 * 
 * ALGORITHM STEPS:
 * 1. Set p1 = m - 1 (last element in nums1)
 * 2. Set p2 = n - 1 (last element in nums2)
 * 3. Set p = m + n - 1 (last position in nums1)
 * 4. While p2 >= 0:
 *    - If p1 >= 0 and nums1[p1] > nums2[p2]:
 *      Place nums1[p1] at position p, decrement p1
 *    - Else:
 *      Place nums2[p2] at position p, decrement p2
 *    - Decrement p
 * 
 * VISUAL EXAMPLE:
 * nums1 = [1,2,3,0,0,0], m = 3
 * nums2 = [2,5,6], n = 3
 * 
 * p1=2, p2=2, p=5
 * Compare nums1[2]=3 vs nums2[2]=6
 * 6 > 3, place 6 at p=5
 * [1,2,3,0,0,6], p1=2, p2=1, p=4
 * 
 * Compare nums1[2]=3 vs nums2[1]=5
 * 5 > 3, place 5 at p=4
 * [1,2,3,0,5,6], p1=2, p2=0, p=3
 * 
 * Compare nums1[2]=3 vs nums2[0]=2
 * 3 > 2, place 3 at p=3
 * [1,2,3,3,5,6], p1=1, p2=0, p=2
 * 
 * Compare nums1[1]=2 vs nums2[0]=2
 * Equal, place 2 from nums2 at p=2
 * [1,2,2,3,5,6], p1=1, p2=-1, p=1
 * 
 * p2 < 0, done!
 * 
 * COMPLEXITY:
 * Time: O(m + n) - single pass through both arrays
 * Space: O(1) - in-place merge
 * 
 * ============================================================================
 */

package arrays.hard

class MergeSortedArrays {
    
    /**
     * Merges two sorted arrays in-place using three-pointer approach
     * 
     * @param nums1 First sorted array with extra space
     * @param m Number of elements in nums1
     * @param nums2 Second sorted array
     * @param n Number of elements in nums2
     */
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        var p1 = m - 1  // Pointer for nums1
        var p2 = n - 1  // Pointer for nums2
        var p = m + n - 1  // Pointer for merge position
        
        // Merge from right to left
        while (p2 >= 0) {
            if (p1 >= 0 && nums1[p1] > nums2[p2]) {
                nums1[p] = nums1[p1]
                p1--
            } else {
                nums1[p] = nums2[p2]
                p2--
            }
            p--
        }
        
        // No need to copy remaining nums1 elements - they're already in place
    }
    
    /**
     * Alternative with explicit comparison
     */
    fun mergeExplicit(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        var p1 = m - 1
        var p2 = n - 1
        
        // Fill from the end
        for (p in m + n - 1 downTo 0) {
            when {
                p2 < 0 -> break  // All nums2 elements processed
                p1 < 0 -> {
                    // All nums1 elements processed, copy remaining nums2
                    nums1[p] = nums2[p2]
                    p2--
                }
                nums1[p1] > nums2[p2] -> {
                    nums1[p] = nums1[p1]
                    p1--
                }
                else -> {
                    nums1[p] = nums2[p2]
                    p2--
                }
            }
        }
    }
    
    /**
     * Brute force: Copy to temp array, merge, copy back
     * Time: O(m+n), Space: O(m)
     */
    fun mergeBruteForce(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        val temp = nums1.copyOf(m)
        var p1 = 0
        var p2 = 0
        var p = 0
        
        while (p1 < m && p2 < n) {
            if (temp[p1] <= nums2[p2]) {
                nums1[p++] = temp[p1++]
            } else {
                nums1[p++] = nums2[p2++]
            }
        }
        
        while (p1 < m) nums1[p++] = temp[p1++]
        while (p2 < n) nums1[p++] = nums2[p2++]
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. nums2 empty: nums1=[1,2,3], m=3, nums2=[], n=0
 *    - Result: [1,2,3] (no change)
 * 
 * 2. nums1 empty: nums1=[0,0,0], m=0, nums2=[1,2,3], n=3
 *    - Result: [1,2,3]
 * 
 * 3. All nums1 < nums2: nums1=[1,2,3,0,0,0], nums2=[4,5,6]
 *    - Result: [1,2,3,4,5,6]
 * 
 * 4. All nums2 < nums1: nums1=[4,5,6,0,0,0], nums2=[1,2,3]
 *    - Result: [1,2,3,4,5,6]
 * 
 * 5. Interleaved: nums1=[1,3,5,0,0,0], nums2=[2,4,6]
 *    - Result: [1,2,3,4,5,6]
 * 
 * 6. Duplicates: nums1=[1,2,2,0,0], nums2=[2,3]
 *    - Result: [1,2,2,2,3]
 * 
 * 7. Single elements: nums1=[2,0], nums2=[1]
 *    - Result: [1,2]
 * 
 * APPLICATIONS:
 * 1. Merging sorted logs from multiple sources
 * 2. Database merge operations
 * 3. External sorting (merge phase)
 * 4. Combining sorted search results
 * 5. Time series data merging
 * 6. File system merge operations
 * 7. Version control merge
 * 
 * ============================================================================
 */

fun main() {
    val solution = MergeSortedArrays()
    
    println("=== Merge Sorted Arrays Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1_1 = intArrayOf(1, 2, 3, 0, 0, 0)
    val nums2_1 = intArrayOf(2, 5, 6)
    println("Before: nums1=${nums1_1.contentToString()}, nums2=${nums2_1.contentToString()}")
    solution.merge(nums1_1, 3, nums2_1, 3)
    println("After: ${nums1_1.contentToString()}")
    println("Expected: [1, 2, 2, 3, 5, 6]\n")
    
    // Test 2: nums2 empty
    println("Test 2: nums2 empty")
    val nums1_2 = intArrayOf(1, 2, 3)
    val nums2_2 = intArrayOf()
    println("Before: nums1=${nums1_2.contentToString()}, nums2=${nums2_2.contentToString()}")
    solution.merge(nums1_2, 3, nums2_2, 0)
    println("After: ${nums1_2.contentToString()}")
    println("Expected: [1, 2, 3]\n")
    
    // Test 3: nums1 empty
    println("Test 3: nums1 empty (m=0)")
    val nums1_3 = intArrayOf(0, 0, 0)
    val nums2_3 = intArrayOf(1, 2, 3)
    println("Before: nums1=${nums1_3.contentToString()}, nums2=${nums2_3.contentToString()}")
    solution.merge(nums1_3, 0, nums2_3, 3)
    println("After: ${nums1_3.contentToString()}")
    println("Expected: [1, 2, 3]\n")
    
    // Test 4: All nums1 < nums2
    println("Test 4: All nums1 < nums2")
    val nums1_4 = intArrayOf(1, 2, 3, 0, 0, 0)
    val nums2_4 = intArrayOf(4, 5, 6)
    println("Before: nums1=${nums1_4.contentToString()}, nums2=${nums2_4.contentToString()}")
    solution.merge(nums1_4, 3, nums2_4, 3)
    println("After: ${nums1_4.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5, 6]\n")
    
    // Test 5: Single element each
    println("Test 5: Single element each")
    val nums1_5 = intArrayOf(2, 0)
    val nums2_5 = intArrayOf(1)
    println("Before: nums1=${nums1_5.contentToString()}, nums2=${nums2_5.contentToString()}")
    solution.merge(nums1_5, 1, nums2_5, 1)
    println("After: ${nums1_5.contentToString()}")
    println("Expected: [1, 2]\n")
    
    println("All tests completed!")
}
