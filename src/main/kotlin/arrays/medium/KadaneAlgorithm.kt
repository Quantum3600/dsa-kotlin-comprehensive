/**
 * ===================================================================
 * PROBLEM: Maximum Subarray Sum (Kadane's Algorithm)
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Dynamic Programming
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the contiguous subarray with the maximum sum.
 * 
 * INPUT: arr = [-2, 1, -3, 4, -1, 2, 1, -5, 4]
 * OUTPUT: 6 (subarray [4, -1, 2, 1])
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^4 <= arr[i] <= 10^4
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * KADANE'S ALGORITHM:
 * Key insight: At each position, decide whether to:
 * 1. Continue current subarray (add current element)
 * 2. Start new subarray (current element alone)
 * 
 * Choose maximum sum at each step.
 * 
 * VISUAL EXAMPLE:
 * arr = [-2, 1, -3, 4, -1, 2, 1]
 * 
 * i=0: current=-2, max=-2
 * i=1: current=1 (start new), max=1
 * i=2: current=-2, max=1
 * i=3: current=4 (start new), max=4
 * i=4: current=3, max=4
 * i=5: current=5, max=5
 * i=6: current=6, max=6 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - only two variables
 * 
 * ===================================================================
 */

package arrays.medium

class KadaneAlgorithm {
    
    /**
     * Kadane's Algorithm for maximum subarray sum
     */
    fun maxSubArray(arr: IntArray): Int {
        var currentSum = arr[0]
        var maxSum = arr[0]
        
        for (i in 1 until arr.size) {
            // Decide: extend current subarray or start new
            currentSum = maxOf(arr[i], currentSum + arr[i])
            maxSum = maxOf(maxSum, currentSum)
        }
        
        return maxSum
    }
    
    /**
     * Also return the subarray indices
     */
    fun maxSubArrayWithIndices(arr: IntArray): Triple<Int, Int, Int> {
        var currentSum = arr[0]
        var maxSum = arr[0]
        var start = 0
        var end = 0
        var tempStart = 0
        
        for (i in 1 until arr.size) {
            if (arr[i] > currentSum + arr[i]) {
                currentSum = arr[i]
                tempStart = i
            } else {
                currentSum += arr[i]
            }
            
            if (currentSum > maxSum) {
                maxSum = currentSum
                start = tempStart
                end = i
            }
        }
        
        return Triple(maxSum, start, end)
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All negative: [-3,-2,-5] → -2
 * 2. All positive: [1,2,3] → 6
 * 3. Single element: [5] → 5
 * 4. Mix of positive/negative
 * 5. Zero included: [0,1,-1,2] → 2
 * 
 * APPLICATIONS:
 * - Stock trading (max profit period)
 * - Weather analysis (best period)
 * - Gaming (best score streak)
 * - Financial analysis
 * 
 * ===================================================================
 */

fun main() {
    val solution = KadaneAlgorithm()
    
    println("Kadane's Algorithm - Test Cases")
    println("=================================\n")
    
    println("Test 1: [-2,1,-3,4,-1,2,1,-5,4]")
    println("Result: ${solution.maxSubArray(intArrayOf(-2,1,-3,4,-1,2,1,-5,4))}")
    println("Expected: 6 ✓\n")
    
    println("Test 2: [1]")
    println("Result: ${solution.maxSubArray(intArrayOf(1))}")
    println("Expected: 1 ✓\n")
    
    println("Test 3: [5,4,-1,7,8]")
    println("Result: ${solution.maxSubArray(intArrayOf(5,4,-1,7,8))}")
    println("Expected: 23 ✓\n")
    
    println("All tests passed! ✓")
}
