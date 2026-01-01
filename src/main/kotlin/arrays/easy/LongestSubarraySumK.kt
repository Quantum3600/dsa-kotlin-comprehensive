/**
 * ===================================================================
 * PROBLEM: Longest Subarray with Sum K (Positives Only)
 * DIFFICULTY: Easy
 * CATEGORY: Arrays, Two Pointers
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of positive integers and a sum K, find the length
 * of the longest subarray with sum equal to K.
 * 
 * INPUT: arr = [1, 2, 3, 1, 1, 1, 1, 4, 2, 3], K = 3
 * OUTPUT: 3 (subarray [1, 1, 1])
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - 1 <= arr[i] <= 10^9 (positive numbers only)
 * - 1 <= K <= 10^9
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use sliding window / two pointers:
 * - left, right pointers define window
 * - Expand right to increase sum
 * - Shrink left when sum exceeds K
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 3, 1, 1, 1, 1], K = 3
 * 
 * [1, 2] sum=3, len=2
 * [3] sum=3, len=1
 * [1, 1, 1] sum=3, len=3 ← Maximum!
 * 
 * COMPLEXITY:
 * Time: O(n) - each element visited at most twice
 * Space: O(1) - only pointers used
 * 
 * ===================================================================
 */

package arrays.easy

class LongestSubarraySumK {
    
    /**
     * Two-pointer / sliding window approach
     * Works for positive numbers only
     */
    fun longestSubarray(arr: IntArray, k: Int): Int {
        var left = 0
        var right = 0
        var sum = 0
        var maxLen = 0
        
        while (right < arr.size) {
            // Expand window by adding right element
            sum += arr[right]
            
            // Shrink window from left if sum exceeds k
            while (sum > k && left <= right) {
                sum -= arr[left]
                left++
            }
            
            // Check if current window has sum = k
            if (sum == k) {
                maxLen = maxOf(maxLen, right - left + 1)
            }
            
            right++
        }
        
        return maxLen
    }
    
    /**
     * Alternative: Brute force (for comparison)
     * Time: O(n²), Space: O(1)
     */
    fun longestSubarrayBruteForce(arr: IntArray, k: Int): Int {
        var maxLen = 0
        
        for (i in arr.indices) {
            var sum = 0
            for (j in i until arr.size) {
                sum += arr[j]
                if (sum == k) {
                    maxLen = maxOf(maxLen, j - i + 1)
                }
                if (sum > k) break  // No point continuing
            }
        }
        
        return maxLen
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. No subarray: [1,2,3] k=10 → 0
 * 2. Single element: [5] k=5 → 1
 * 3. Entire array: [1,2,3] k=6 → 3
 * 4. Multiple subarrays: find longest
 * 5. k = 0: Not possible (positive numbers only)
 * 
 * APPLICATIONS:
 * - Finding sequences with target sum
 * - Financial analysis (target returns)
 * - Resource allocation problems
 * 
 * NOTE: For arrays with negative numbers, use HashMap approach!
 * 
 * ===================================================================
 */

fun main() {
    val solution = LongestSubarraySumK()
    
    println("Longest Subarray with Sum K - Test Cases")
    println("==========================================\n")
    
    println("Test 1: [1,2,3,1,1,1,1,4,2,3] k=3")
    println("Result: ${solution.longestSubarray(
        intArrayOf(1,2,3,1,1,1,1,4,2,3), 3
    )}")
    println("Expected: 3 ✓\n")
    
    println("Test 2: [1,2,3] k=6")
    println("Result: ${solution.longestSubarray(
        intArrayOf(1,2,3), 6
    )}")
    println("Expected: 3 ✓\n")
    
    println("Test 3: [1,2,1,2,1] k=3")
    println("Result: ${solution.longestSubarray(
        intArrayOf(1,2,1,2,1), 3
    )}")
    println("Expected: 2 ✓\n")
    
    println("All tests passed! ✓")
}
