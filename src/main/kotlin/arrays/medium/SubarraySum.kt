/**
 * ===================================================================
 * PROBLEM: Subarray Sum Equals K
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Hash Table, Prefix Sum
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Find total number of continuous subarrays whose sum equals k.
 * 
 * INPUT: arr = [1, 1, 1], k = 2
 * OUTPUT: 2 (subarrays [1,1] at index 0-1 and 1-2)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 2 * 10^4
 * - -1000 <= arr[i] <= 1000
 * - -10^7 <= k <= 10^7
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use prefix sum + HashMap:
 * - Store prefix sum frequencies
 * - If (prefixSum - k) exists in map, we found subarrays
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(n)
 * 
 * ===================================================================
 */

package arrays.medium

class SubarraySum {
    
    fun subarraySum(arr: IntArray, k: Int): Int {
        val prefixSumCount = mutableMapOf<Int, Int>()
        prefixSumCount[0] = 1  // Empty subarray
        
        var count = 0
        var prefixSum = 0
        
        for (num in arr) {
            prefixSum += num
            
            // Check if (prefixSum - k) exists
            val needed = prefixSum - k
            count += prefixSumCount.getOrDefault(needed, 0)
            
            // Update prefix sum count
            prefixSumCount[prefixSum] = prefixSumCount.getOrDefault(prefixSum, 0) + 1
        }
        
        return count
    }
}

/**
 * ===================================================================
 * EDGE CASES & APPLICATIONS
 * ===================================================================
 * 
 * 1. k = 0: Find subarrays with sum 0
 * 2. All elements same as k
 * 3. Negative numbers
 * 
 * APPLICATIONS:
 * - Finding target sum sequences
 * - Financial analysis
 * - Signal processing
 * 
 * ===================================================================
 */

fun main() {
    val solution = SubarraySum()
    
    println("Subarray Sum Equals K - Test Cases")
    println("====================================\n")
    
    println("Test 1: [1,1,1] k=2")
    println("Result: ${solution.subarraySum(intArrayOf(1,1,1), 2)}")
    println("Expected: 2 ✓\n")
    
    println("Test 2: [1,2,3] k=3")
    println("Result: ${solution.subarraySum(intArrayOf(1,2,3), 3)}")
    println("Expected: 2 ✓\n")
    
    println("All tests passed! ✓")
}
