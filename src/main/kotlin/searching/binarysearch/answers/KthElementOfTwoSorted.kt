/**
 * ============================================================================
 * PROBLEM: Kth Element of Two Sorted Arrays
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two sorted arrays arr1 and arr2, find the kth element (1-indexed) in
 * the merged sorted array without actually merging the arrays.
 * 
 * INPUT FORMAT:
 * - arr1: First sorted array
 * - arr2: Second sorted array
 * - k: Position of element to find (1-indexed)
 * Example: arr1 = [2, 3, 6, 7, 9], arr2 = [1, 4, 8, 10], k = 5
 * 
 * OUTPUT FORMAT:
 * - Integer: The kth element
 * Example: 6 (merged: [1, 2, 3, 4, 6, 7, 8, 9, 10], 5th element is 6)
 * 
 * CONSTRAINTS:
 * - 1 <= arr1.length, arr2.length <= 1000
 * - 1 <= k <= arr1.length + arr2.length
 * - -10^9 <= arr1[i], arr2[i] <= 10^9
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of merging arrays, we can use binary search. We partition both arrays
 * such that we have exactly k elements on the left side. The kth element is the
 * maximum of the left partition.
 * 
 * KEY INSIGHT:
 * If we take 'x' elements from arr1, we need 'k - x' elements from arr2.
 * We binary search on 'x' (number of elements to take from arr1).
 * 
 * For a valid partition:
 * - arr1[x-1] <= arr2[k-x] (left of arr1 <= right of arr2)
 * - arr2[k-x-1] <= arr1[x] (left of arr2 <= right of arr1)
 * 
 * ALGORITHM STEPS:
 * 1. Ensure arr1 is the smaller array (for optimization)
 * 2. Binary search on number of elements to take from arr1
 * 3. For each partition, check if it's valid
 * 4. The kth element is max(arr1[x-1], arr2[k-x-1])
 * 
 * VISUAL EXAMPLE:
 * arr1 = [2, 3, 6, 7, 9], arr2 = [1, 4, 8, 10], k = 5
 * 
 * Merged: [1, 2, 3, 4, 6, 7, 8, 9, 10]
 * 5th element = 6
 * 
 * We need 5 elements on left side.
 * Try taking 3 from arr1, 2 from arr2:
 * arr1: [2, 3, 6] | [7, 9]
 * arr2: [1, 4] | [8, 10]
 * 
 * Check: 6 <= 8 ✓ and 4 <= 7 ✓
 * Valid! kth element = max(6, 4) = 6
 * 
 * WHY BINARY SEARCH:
 * - Search space: Number of elements to take from arr1 [0 to min(k, arr1.size)]
 * - For each partition, we can check validity
 * - Find the correct partition efficiently
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

class KthElementOfTwoSorted {
    
    /**
     * Find kth element in merged sorted array
     * 
     * @param arr1 First sorted array
     * @param arr2 Second sorted array
     * @param k Position (1-indexed)
     * @return The kth element
     */
    fun solve(arr1: IntArray, arr2: IntArray, k: Int): Int {
        val n = arr1.size
        val m = arr2.size
        
        // Ensure arr1 is the smaller array for optimization
        if (n > m) {
            return solve(arr2, arr1, k)
        }
        
        // Edge cases
        if (n == 0) return arr2[k - 1]
        if (k == 1) return minOf(arr1[0], arr2[0])
        if (k == n + m) return maxOf(arr1[n - 1], arr2[m - 1])
        
        // Binary search on number of elements to take from arr1
        var low = maxOf(0, k - m)  // Can't take fewer than k-m from arr1
        var high = minOf(k, n)      // Can't take more than n from arr1
        
        while (low <= high) {
            val cut1 = (low + high) / 2  // Elements to take from arr1
            val cut2 = k - cut1           // Elements to take from arr2
            
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
                // kth element is the maximum of left side
                return maxOf(left1, left2)
            } else if (left1 > right2) {
                // Too many elements from arr1, reduce
                high = cut1 - 1
            } else {
                // Too few elements from arr1, increase
                low = cut1 + 1
            }
        }
        
        // Should never reach here with valid input
        return -1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr1 = [2, 3, 6, 7, 9], arr2 = [1, 4, 8, 10], k = 5
 * 
 * n = 5, m = 4, k = 5
 * Merged would be: [1, 2, 3, 4, 6, 7, 8, 9, 10]
 * 5th element = 6
 * 
 * Binary Search on cut1 (elements from arr1):
 * low = max(0, 5-4) = 1, high = min(5, 5) = 5
 * 
 * Iteration 1: low=1, high=5
 * - cut1 = 3, cut2 = 2
 * - left1 = arr1[2] = 6, right1 = arr1[3] = 7
 * - left2 = arr2[1] = 4, right2 = arr2[2] = 8
 * - Check: 6 <= 8 ✓ and 4 <= 7 ✓
 * - Valid! Return max(6, 4) = 6
 * 
 * OUTPUT: 6 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solver = KthElementOfTwoSorted()
    
    println("=== Testing Kth Element of Two Sorted Arrays ===\n")
    
    // Test Case 1: Normal case
    val arr1_1 = intArrayOf(2, 3, 6, 7, 9)
    val arr2_1 = intArrayOf(1, 4, 8, 10)
    val k1 = 5
    println("Test 1: arr1 = ${arr1_1.contentToString()}, arr2 = ${arr2_1.contentToString()}, k = $k1")
    println("Result: ${solver.solve(arr1_1, arr2_1, k1)}")
    println("Expected: 6\n")
    
    // Test Case 2: First element
    val arr1_2 = intArrayOf(2, 3, 6, 7, 9)
    val arr2_2 = intArrayOf(1, 4, 8, 10)
    val k2 = 1
    println("Test 2: arr1 = ${arr1_2.contentToString()}, arr2 = ${arr2_2.contentToString()}, k = $k2")
    println("Result: ${solver.solve(arr1_2, arr2_2, k2)}")
    println("Expected: 1\n")
    
    // Test Case 3: Last element
    val arr1_3 = intArrayOf(2, 3, 6, 7, 9)
    val arr2_3 = intArrayOf(1, 4, 8, 10)
    val k3 = 9
    println("Test 3: arr1 = ${arr1_3.contentToString()}, arr2 = ${arr2_3.contentToString()}, k = $k3")
    println("Result: ${solver.solve(arr1_3, arr2_3, k3)}")
    println("Expected: 10\n")
    
    // Test Case 4: One array much smaller
    val arr1_4 = intArrayOf(1, 2)
    val arr2_4 = intArrayOf(3, 4, 5, 6, 7, 8)
    val k4 = 4
    println("Test 4: arr1 = ${arr1_4.contentToString()}, arr2 = ${arr2_4.contentToString()}, k = $k4")
    println("Result: ${solver.solve(arr1_4, arr2_4, k4)}")
    println("Expected: 4\n")
    
    // Test Case 5: Equal sized arrays
    val arr1_5 = intArrayOf(1, 3, 5, 7)
    val arr2_5 = intArrayOf(2, 4, 6, 8)
    val k5 = 5
    println("Test 5: arr1 = ${arr1_5.contentToString()}, arr2 = ${arr2_5.contentToString()}, k = $k5")
    println("Result: ${solver.solve(arr1_5, arr2_5, k5)}")
    println("Expected: 5\n")
    
    // Test Case 6: Duplicate values
    val arr1_6 = intArrayOf(1, 2, 3, 3, 4)
    val arr2_6 = intArrayOf(2, 3, 5)
    val k6 = 4
    println("Test 6: arr1 = ${arr1_6.contentToString()}, arr2 = ${arr2_6.contentToString()}, k = $k6")
    println("Result: ${solver.solve(arr1_6, arr2_6, k6)}")
    println("Expected: 3\n")
}
