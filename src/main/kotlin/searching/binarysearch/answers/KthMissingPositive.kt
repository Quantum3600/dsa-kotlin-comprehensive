/**
 * ============================================================================
 * PROBLEM: Kth Missing Positive Number
 * DIFFICULTY: Easy
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a sorted array of positive integers arr and an integer k, find the
 * kth positive integer that is missing from the array.
 * 
 * INPUT FORMAT:
 * - arr: Sorted array of positive integers (may have gaps)
 * - k: Which missing number to find (1-indexed)
 * Example: arr = [2, 3, 4, 7, 11], k = 5
 * 
 * OUTPUT FORMAT:
 * - Integer: The kth missing positive number
 * Example: 9 (missing numbers are: 1, 5, 6, 8, 9... so 5th is 9)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.length <= 1000
 * - 1 <= arr[i] <= 1000
 * - arr is sorted in strictly increasing order
 * - 1 <= k <= 1000
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * At any index i, if all numbers before arr[i] were present, there would be
 * i+1 numbers (indices 0 to i). The actual value is arr[i]. So the number of
 * missing numbers before index i is: arr[i] - (i + 1)
 * 
 * We can binary search to find the index where exactly k numbers are missing.
 * 
 * ALGORITHM STEPS:
 * 1. Binary search to find rightmost index where missing count < k
 * 2. At that index, we know how many are missing
 * 3. Calculate the kth missing number based on the pattern
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 3, 4, 7, 11], k = 5
 * 
 * Index 0: arr[0]=2, should be 1, missing before = 2-1 = 1 (number 1)
 * Index 1: arr[1]=3, should be 2, missing before = 3-2 = 1 (still just 1)
 * Index 2: arr[2]=4, should be 3, missing before = 4-3 = 1 (still just 1)
 * Index 3: arr[3]=7, should be 4, missing before = 7-4 = 3 (1, 5, 6)
 * Index 4: arr[4]=11, should be 5, missing before = 11-5 = 6 (1, 5, 6, 8, 9, 10)
 * 
 * We want 5th missing, which is between index 3 and 4
 * After index 3, we have 3 missing. We need 2 more.
 * Starting from arr[3]=7, add (5-3) = 2, gives us 9
 * 
 * WHY BINARY SEARCH:
 * - Missing count is monotonically increasing
 * - We want to find where missing count reaches k
 * - Binary search finds this position efficiently
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log n)
 * - Binary search over array length
 * - Each iteration is O(1)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class KthMissingPositive {
    
    /**
     * Find kth missing positive number
     * 
     * @param arr Sorted array of positive integers
     * @param k Which missing number to find
     * @return The kth missing positive number
     */
    fun solve(arr: IntArray, k: Int): Int {
        // Binary search to find position
        var low = 0
        var high = arr.size - 1
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Calculate how many numbers are missing before index mid
            // Expected value at index mid is (mid + 1)
            // Actual value is arr[mid]
            // Missing count = arr[mid] - (mid + 1)
            val missingCount = arr[mid] - (mid + 1)
            
            if (missingCount < k) {
                // Need to look further right
                low = mid + 1
            } else {
                // This position or earlier has k missing
                high = mid - 1
            }
        }
        
        // At this point, 'high' is the rightmost index where missing count < k
        // If high = -1, all k numbers are missing before the array starts
        // Answer = k
        // Otherwise, answer = arr[high] + (k - missing_count_at_high)
        
        if (high == -1) {
            // All k missing numbers are before arr[0]
            return k
        }
        
        // Calculate missing count at 'high'
        val missingAtHigh = arr[high] - (high + 1)
        
        // We need (k - missingAtHigh) more numbers after arr[high]
        return arr[high] + (k - missingAtHigh)
    }
    
    /**
     * Alternative: Linear search approach (simpler but O(n))
     * Useful for understanding the problem
     */
    fun solveLinear(arr: IntArray, k: Int): Int {
        var missingCount = 0
        var current = 1
        var idx = 0
        
        while (missingCount < k) {
            // If current number is in array, skip it
            if (idx < arr.size && arr[idx] == current) {
                idx++
            } else {
                // Current number is missing
                missingCount++
                if (missingCount == k) {
                    return current
                }
            }
            current++
        }
        
        return current - 1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [2, 3, 4, 7, 11], k = 5
 * 
 * Missing numbers: 1, 5, 6, 8, 9, 10, 12, ...
 * We want the 5th missing number = 9
 * 
 * Binary Search:
 * 
 * Iteration 1: low=0, high=4
 * - mid = 2
 * - missing at mid: arr[2] - (2+1) = 4 - 3 = 1 < 5
 * - low = 3
 * 
 * Iteration 2: low=3, high=4
 * - mid = 3
 * - missing at mid: arr[3] - (3+1) = 7 - 4 = 3 < 5
 * - low = 4
 * 
 * Iteration 3: low=4, high=4
 * - mid = 4
 * - missing at mid: arr[4] - (4+1) = 11 - 5 = 6 >= 5
 * - high = 3
 * 
 * Iteration 4: low=4, high=3
 * - low > high, exit
 * 
 * At high=3: missing = 3, need 5-3=2 more
 * Answer = arr[3] + 2 = 7 + 2 = 9 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solver = KthMissingPositive()
    
    println("=== Testing Kth Missing Positive ===\n")
    
    // Test Case 1: Normal case
    val arr1 = intArrayOf(2, 3, 4, 7, 11)
    val k1 = 5
    println("Test 1: arr = ${arr1.contentToString()}, k = $k1")
    println("Result: ${solver.solve(arr1, k1)}")
    println("Expected: 9\n")
    
    // Test Case 2: Missing from start
    val arr2 = intArrayOf(2, 3, 4, 7, 11)
    val k2 = 1
    println("Test 2: arr = ${arr2.contentToString()}, k = $k2")
    println("Result: ${solver.solve(arr2, k2)}")
    println("Expected: 1\n")
    
    // Test Case 3: Large k, goes beyond array
    val arr3 = intArrayOf(1, 2, 3, 4)
    val k3 = 2
    println("Test 3: arr = ${arr3.contentToString()}, k = $k3")
    println("Result: ${solver.solve(arr3, k3)}")
    println("Expected: 6\n")
    
    // Test Case 4: Array starts from 1, no initial gaps
    val arr4 = intArrayOf(1, 2, 3, 4, 5)
    val k4 = 3
    println("Test 4: arr = ${arr4.contentToString()}, k = $k4")
    println("Result: ${solver.solve(arr4, k4)}")
    println("Expected: 8\n")
    
    // Test Case 5: Large gaps
    val arr5 = intArrayOf(1, 10, 20)
    val k5 = 5
    println("Test 5: arr = ${arr5.contentToString()}, k = $k5")
    println("Result: ${solver.solve(arr5, k5)}")
    println("Expected: 6\n")
    
    // Test Case 6: Single element
    val arr6 = intArrayOf(5)
    val k6 = 3
    println("Test 6: arr = ${arr6.contentToString()}, k = $k6")
    println("Result: ${solver.solve(arr6, k6)}")
    println("Expected: 3\n")
}
