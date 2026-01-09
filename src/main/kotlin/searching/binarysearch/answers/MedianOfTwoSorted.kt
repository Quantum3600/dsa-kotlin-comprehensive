/**
 * ============================================================================
 * PROBLEM: Median of Two Sorted Arrays
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two sorted arrays arr1 and arr2, find the median of the two sorted
 * arrays without actually merging them. The overall runtime complexity should
 * be O(log(min(m,n))).
 * 
 * INPUT FORMAT:
 * - arr1: First sorted array
 * - arr2: Second sorted array
 * Example: arr1 = [1, 3, 8, 9, 15], arr2 = [7, 11, 18, 19, 21, 25]
 * 
 * OUTPUT FORMAT:
 * - Double: The median of merged sorted array
 * Example: 11.0 (merged: [1,3,7,8,9,11,15,18,19,21,25], median = 11)
 * 
 * CONSTRAINTS:
 * - 0 <= arr1.length <= 1000
 * - 0 <= arr2.length <= 1000
 * - 1 <= arr1.length + arr2.length
 * - -10^6 <= arr1[i], arr2[i] <= 10^6
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The median divides the merged array into two equal halves. We partition both
 * arrays such that:
 * 1. Left half has (n+m+1)/2 elements
 * 2. All elements in left half <= all elements in right half
 * 
 * This is similar to finding kth element, where k = (n+m+1)/2
 * 
 * KEY INSIGHT:
 * Binary search on the partition point of the smaller array. For each partition:
 * - Calculate corresponding partition in second array
 * - Check if partition is valid (left elements <= right elements)
 * - If valid, calculate median based on odd/even total length
 * 
 * ALGORITHM STEPS:
 * 1. Ensure arr1 is the smaller array
 * 2. Binary search on partition position in arr1
 * 3. For each partition:
 *    - Calculate partition in arr2
 *    - Check validity: left1 <= right2 && left2 <= right1
 *    - If valid, calculate median
 * 4. If total length is odd: median = max(left1, left2)
 *    If total length is even: median = (max(left1, left2) + min(right1, right2)) / 2
 * 
 * VISUAL EXAMPLE:
 * arr1 = [1, 3, 8], arr2 = [7, 11, 18, 19]
 * Total = 7 (odd), median is 4th element
 * 
 * Partition to get 4 elements on left:
 * arr1: [1, 3] | [8]        (2 from arr1)
 * arr2: [7, 11] | [18, 19]  (2 from arr2)
 * 
 * Check: 3 <= 18 ✓ and 11 <= 8 ✗
 * Not valid! Try again...
 * 
 * arr1: [1] | [3, 8]        (1 from arr1)
 * arr2: [7, 11, 18] | [19]  (3 from arr2)
 * 
 * Check: 1 <= 19 ✓ and 18 <= 3 ✗
 * Not valid! Try again...
 * 
 * Eventually find: median = 11
 * 
 * WHY BINARY SEARCH:
 * - Search on partition point of smaller array
 * - For each partition, we can check validity in O(1)
 * - Find correct partition in O(log(min(n,m)))
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log(min(n, m)))
 * - Binary search on smaller array
 * - Each iteration is O(1)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class MedianOfTwoSorted {
    
    /**
     * Find median of two sorted arrays
     * 
     * @param arr1 First sorted array
     * @param arr2 Second sorted array
     * @return The median
     */
    fun solve(arr1: IntArray, arr2: IntArray): Double {
        val n = arr1.size
        val m = arr2.size
        
        // Ensure arr1 is the smaller array
        if (n > m) {
            return solve(arr2, arr1)
        }
        
        // Handle edge cases
        if (n == 0) {
            return if (m % 2 == 0) {
                (arr2[m / 2 - 1] + arr2[m / 2]) / 2.0
            } else {
                arr2[m / 2].toDouble()
            }
        }
        
        val total = n + m
        val half = (total + 1) / 2  // Elements on left side
        
        // Binary search on partition in arr1
        var low = 0
        var high = n
        
        while (low <= high) {
            val cut1 = (low + high) / 2  // Partition in arr1
            val cut2 = half - cut1        // Partition in arr2
            
            // Validate cut2 is within bounds
            if (cut2 < 0 || cut2 > m) {
                if (cut2 < 0) low = cut1 + 1
                else high = cut1 - 1
                continue
            }
            
            // Get boundary elements
            val left1 = if (cut1 == 0) Int.MIN_VALUE else arr1[cut1 - 1]
            val left2 = if (cut2 == 0) Int.MIN_VALUE else arr2[cut2 - 1]
            val right1 = if (cut1 == n) Int.MAX_VALUE else arr1[cut1]
            val right2 = if (cut2 == m) Int.MAX_VALUE else arr2[cut2]
            
            // Check if partition is valid
            if (left1 <= right2 && left2 <= right1) {
                // Valid partition found
                return if (total % 2 == 0) {
                    // Even total length: median is average of two middle elements
                    (maxOf(left1, left2) + minOf(right1, right2)) / 2.0
                } else {
                    // Odd total length: median is the larger left element
                    maxOf(left1, left2).toDouble()
                }
            } else if (left1 > right2) {
                // Too many elements from arr1
                high = cut1 - 1
            } else {
                // Too few elements from arr1
                low = cut1 + 1
            }
        }
        
        // Should never reach here with valid input
        return 0.0
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr1 = [1, 3, 8, 9, 15], arr2 = [7, 11, 18, 19, 21, 25]
 * 
 * n = 5, m = 6, total = 11 (odd)
 * half = (11 + 1) / 2 = 6 elements on left
 * 
 * Merged: [1, 3, 7, 8, 9, 11, 15, 18, 19, 21, 25]
 * Median = 11 (6th element, 0-indexed: arr[5])
 * 
 * Binary Search on cut1:
 * low = 0, high = 5
 * 
 * Iteration 1: low=0, high=5
 * - cut1 = 2, cut2 = 4
 * - left1 = arr1[1] = 3, right1 = arr1[2] = 8
 * - left2 = arr2[3] = 19, right2 = arr2[4] = 21
 * - Check: 3 <= 21 ✓ but 19 <= 8 ✗
 * - left2 > right1, need more from arr1
 * - low = 3
 * 
 * Iteration 2: low=3, high=5
 * - cut1 = 4, cut2 = 2
 * - left1 = arr1[3] = 9, right1 = arr1[4] = 15
 * - left2 = arr2[1] = 11, right2 = arr2[2] = 18
 * - Check: 9 <= 18 ✓ but 11 <= 15 ✓
 * - Valid! Median = max(9, 11) = 11
 * 
 * OUTPUT: 11.0 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solver = MedianOfTwoSorted()
    
    println("=== Testing Median of Two Sorted Arrays ===\n")
    
    // Test Case 1: Odd total length
    val arr1_1 = intArrayOf(1, 3, 8, 9, 15)
    val arr2_1 = intArrayOf(7, 11, 18, 19, 21, 25)
    println("Test 1: arr1 = ${arr1_1.contentToString()}, arr2 = ${arr2_1.contentToString()}")
    println("Result: ${solver.solve(arr1_1, arr2_1)}")
    println("Expected: 11.0\n")
    
    // Test Case 2: Even total length
    val arr1_2 = intArrayOf(1, 3)
    val arr2_2 = intArrayOf(2, 4)
    println("Test 2: arr1 = ${arr1_2.contentToString()}, arr2 = ${arr2_2.contentToString()}")
    println("Result: ${solver.solve(arr1_2, arr2_2)}")
    println("Expected: 2.5\n")
    
    // Test Case 3: One array empty
    val arr1_3 = intArrayOf()
    val arr2_3 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 3: arr1 = ${arr1_3.contentToString()}, arr2 = ${arr2_3.contentToString()}")
    println("Result: ${solver.solve(arr1_3, arr2_3)}")
    println("Expected: 3.0\n")
    
    // Test Case 4: Different sized arrays
    val arr1_4 = intArrayOf(1, 2)
    val arr2_4 = intArrayOf(3, 4, 5, 6, 7, 8)
    println("Test 4: arr1 = ${arr1_4.contentToString()}, arr2 = ${arr2_4.contentToString()}")
    println("Result: ${solver.solve(arr1_4, arr2_4)}")
    println("Expected: 4.5\n")
    
    // Test Case 5: All elements in arr1 < all in arr2
    val arr1_5 = intArrayOf(1, 2, 3)
    val arr2_5 = intArrayOf(4, 5, 6)
    println("Test 5: arr1 = ${arr1_5.contentToString()}, arr2 = ${arr2_5.contentToString()}")
    println("Result: ${solver.solve(arr1_5, arr2_5)}")
    println("Expected: 3.5\n")
    
    // Test Case 6: Interleaved arrays
    val arr1_6 = intArrayOf(1, 3, 5, 7, 9)
    val arr2_6 = intArrayOf(2, 4, 6, 8, 10)
    println("Test 6: arr1 = ${arr1_6.contentToString()}, arr2 = ${arr2_6.contentToString()}")
    println("Result: ${solver.solve(arr1_6, arr2_6)}")
    println("Expected: 5.5\n")
}
