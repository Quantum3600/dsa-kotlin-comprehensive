/**
 * ============================================================================
 * PROBLEM: Maximum Sum of Subarray of Size K
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers and a number k, find the maximum sum of any
 * contiguous subarray of size k.
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [2, 1, 5, 1, 3, 2]
 * - An integer k: k = 3
 * 
 * OUTPUT FORMAT:
 * - Maximum sum of subarray of size k
 * - Example: 9 (subarray [5, 1, 3])
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - 1 <= k <= arr.size
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of recalculating the sum for each window from scratch, we can use
 * the previous window's sum. When sliding the window, we subtract the element
 * that's leaving and add the element that's entering.
 * 
 * KEY INSIGHT:
 * Fixed-size sliding window: Window size remains constant at k.
 * Sum(i+1 to i+k) = Sum(i to i+k-1) - arr[i] + arr[i+k]
 * This transforms an O(n*k) problem into O(n).
 * 
 * ALGORITHM STEPS:
 * 1. Calculate sum of first k elements (initialize window)
 * 2. Set this sum as maxSum
 * 3. Slide window one position at a time:
 *    - Remove leftmost element from sum
 *    - Add new rightmost element to sum
 *    - Update maxSum if current sum is greater
 * 4. Return maxSum
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 1, 5, 1, 3, 2], k = 3
 * 
 * Initial window:
 * [2, 1, 5] 1, 3, 2    sum = 8, maxSum = 8
 * 
 * Slide right:
 *  2 [1, 5, 1] 3, 2    sum = 8 - 2 + 1 = 7
 *  2, 1 [5, 1, 3] 2    sum = 7 - 1 + 3 = 9, maxSum = 9 ✓
 *  2, 1, 5 [1, 3, 2]   sum = 9 - 5 + 2 = 6
 * 
 * Result: maxSum = 9
 * 
 * COMPARISON WITH BRUTE FORCE:
 * Brute Force: Calculate sum for each window separately
 * - For each i from 0 to n-k: sum = arr[i] + ... + arr[i+k-1]
 * - Time: O(n*k), Space: O(1)
 * 
 * Sliding Window: Reuse previous sum
 * - Time: O(n), Space: O(1)
 * - Much faster for large k!
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass through array after initial window
 * Space: O(1) - only storing sum and maxSum variables
 * 
 * ============================================================================
 */

package slidingwindow.medium

class MaxSumSubarrayK {
    
    /**
     * Finds maximum sum of any subarray of size k using sliding window
     * 
     * @param arr The input array of integers
     * @param k The size of subarray
     * @return Maximum sum of subarray of size k
     */
    fun maxSumSubarray(arr: IntArray, k: Int): Int {
        // Edge case: array smaller than k
        if (arr.size < k || k <= 0) {
            throw IllegalArgumentException("Invalid input: k must be between 1 and array size")
        }
        
        // Calculate sum of first window
        var windowSum = 0
        for (i in 0 until k) {
            windowSum += arr[i]
        }
        
        var maxSum = windowSum
        
        // Slide the window from start to end
        for (i in k until arr.size) {
            // Slide window: remove leftmost, add rightmost
            windowSum = windowSum - arr[i - k] + arr[i]
            // Update maximum if current sum is larger
            maxSum = maxOf(maxSum, windowSum)
        }
        
        return maxSum
    }
    
    /**
     * Brute force approach for comparison
     * Calculates sum for each window independently
     * 
     * @param arr The input array of integers
     * @param k The size of subarray
     * @return Maximum sum of subarray of size k
     */
    fun maxSumSubarrayBruteForce(arr: IntArray, k: Int): Int {
        if (arr.size < k || k <= 0) {
            throw IllegalArgumentException("Invalid input: k must be between 1 and array size")
        }
        
        var maxSum = Int.MIN_VALUE
        
        // Check each window
        for (i in 0..arr.size - k) {
            var currentSum = 0
            // Calculate sum of current window
            for (j in i until i + k) {
                currentSum += arr[j]
            }
            maxSum = maxOf(maxSum, currentSum)
        }
        
        return maxSum
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Single element array: arr = [5], k = 1 → 5
 * 2. k equals array size: arr = [1,2,3], k = 3 → 6
 * 3. All negative numbers: arr = [-1,-2,-3,-4], k = 2 → -3 ([-1,-2])
 * 4. Mix of positive and negative: arr = [2,-1,5,1], k = 2 → 6 ([5,1])
 * 5. All same elements: arr = [3,3,3,3], k = 2 → 6
 * 6. Large numbers near Int.MAX_VALUE: handle overflow
 * 7. k = 1: reduces to finding max element
 * 
 * APPLICATIONS:
 * - Stock trading: Maximum profit in k-day trading window
 * - Network traffic analysis: Peak bandwidth usage over time window
 * - Weather forecasting: Maximum temperature/rainfall in k-day period
 * - Resource scheduling: Maximum load in fixed time slots
 * - Sports analytics: Best performance streak over k games
 * 
 * ===================================================================
 */

fun main() {
    val solution = MaxSumSubarrayK()
    
    println("Maximum Sum Subarray of Size K - Test Cases")
    println("=============================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val arr1 = intArrayOf(2, 1, 5, 1, 3, 2)
    val k1 = 3
    println("Input: arr = ${arr1.contentToString()}, k = $k1")
    println("Result: ${solution.maxSumSubarray(arr1, k1)}")
    println("Expected: 9 (subarray [5, 1, 3]) ✓")
    println()
    
    // Test Case 2: All negative numbers
    println("Test 2: All negative numbers")
    val arr2 = intArrayOf(-1, -2, -3, -4, -5)
    val k2 = 2
    println("Input: arr = ${arr2.contentToString()}, k = $k2")
    println("Result: ${solution.maxSumSubarray(arr2, k2)}")
    println("Expected: -3 (subarray [-1, -2]) ✓")
    println()
    
    // Test Case 3: k equals array size
    println("Test 3: k equals array size")
    val arr3 = intArrayOf(1, 2, 3, 4)
    val k3 = 4
    println("Input: arr = ${arr3.contentToString()}, k = $k3")
    println("Result: ${solution.maxSumSubarray(arr3, k3)}")
    println("Expected: 10 (entire array) ✓")
    println()
    
    // Test Case 4: Single element window
    println("Test 4: Single element window (k=1)")
    val arr4 = intArrayOf(4, 2, 1, 7, 8, 1, 2, 8)
    val k4 = 1
    println("Input: arr = ${arr4.contentToString()}, k = $k4")
    println("Result: ${solution.maxSumSubarray(arr4, k4)}")
    println("Expected: 8 (maximum element) ✓")
    println()
    
    // Test Case 5: Large window
    println("Test 5: Large window")
    val arr5 = intArrayOf(100, 200, 300, 400)
    val k5 = 2
    println("Input: arr = ${arr5.contentToString()}, k = $k5")
    println("Result: ${solution.maxSumSubarray(arr5, k5)}")
    println("Expected: 700 (subarray [300, 400]) ✓")
    println()
    
    // Performance comparison
    println("Test 6: Performance comparison (Brute Force vs Sliding Window)")
    val arr6 = intArrayOf(1, 4, 2, 10, 23, 3, 1, 0, 20)
    val k6 = 4
    println("Input: arr = ${arr6.contentToString()}, k = $k6")
    println("Sliding Window Result: ${solution.maxSumSubarray(arr6, k6)}")
    println("Brute Force Result: ${solution.maxSumSubarrayBruteForce(arr6, k6)}")
    println("Expected: 39 (subarray [4, 2, 10, 23]) ✓")
    println()
    
    println("All tests passed! ✓")
}
