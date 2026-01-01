/**
 * ===================================================================
 * PROBLEM: Find Second Smallest Element
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, find the second smallest element.
 * Return -1 if it doesn't exist.
 * 
 * INPUT: arr = [12, 35, 1, 10, 34, 1]
 * OUTPUT: 10
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Track two variables:
 * - smallest: smallest element seen
 * - secondSmallest: second smallest seen
 * 
 * For each element:
 * - If smaller than smallest: update both
 * - Else if smaller than secondSmallest: update secondSmallest
 * 
 * VISUAL EXAMPLE:
 * arr = [12, 35, 1, 10, 34, 1]
 * 
 * Initialize: smallest=∞, secondSmallest=∞
 * i=0: arr[0]=12 → smallest=12, secondSmallest=∞
 * i=1: arr[1]=35 → smallest=12, secondSmallest=35
 * i=2: arr[2]=1  → smallest=1, secondSmallest=12
 * i=3: arr[3]=10 → smallest=1, secondSmallest=10
 * i=4: arr[4]=34 → no change
 * i=5: arr[5]=1  → no change (duplicate)
 * 
 * Result: secondSmallest = 10 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - two variables
 * 
 * ===================================================================
 */

package arrays.easy

class SecondSmallest {
    
    fun findSecondSmallest(arr: IntArray): Int {
        if (arr.size < 2) return -1
        
        var smallest = Int.MAX_VALUE
        var secondSmallest = Int.MAX_VALUE
        
        for (num in arr) {
            if (num < smallest) {
                secondSmallest = smallest
                smallest = num
            } else if (num < secondSmallest && num != smallest) {
                secondSmallest = num
            }
        }
        
        return if (secondSmallest == Int.MAX_VALUE) -1 else secondSmallest
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Empty array: [] → -1
 * 2. Single element: [5] → -1
 * 3. All same: [1, 1, 1] → -1
 * 4. Two different: [1, 2] → 2
 * 5. With duplicates: [1, 1, 2, 2] → 2
 * 6. Negative numbers: [-5, -1, 0] → -1
 * 
 * ===================================================================
 */

fun main() {
    val solution = SecondSmallest()
    
    println("Second Smallest - Test Cases")
    println("==============================\n")
    
    println("Test 1: [12, 35, 1, 10, 34, 1]")
    println("Result: ${solution.findSecondSmallest(intArrayOf(12, 35, 1, 10, 34, 1))}")
    println("Expected: 10 ✓\n")
    
    println("Test 2: [1, 1, 1]")
    println("Result: ${solution.findSecondSmallest(intArrayOf(1, 1, 1))}")
    println("Expected: -1 ✓\n")
    
    println("All tests passed! ✓")
}
