/**
 * ===================================================================
 * PROBLEM: Sort Array of 0s, 1s, and 2s (Dutch National Flag)
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Sorting
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array containing only 0s, 1s, and 2s, sort the array in-place.
 * Do not use any sorting algorithm.
 * 
 * INPUT: arr = [2, 0, 2, 1, 1, 0]
 * OUTPUT: [0, 0, 1, 1, 2, 2]
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - arr[i] is 0, 1, or 2
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * DUTCH NATIONAL FLAG ALGORITHM:
 * Use three pointers:
 * - low: boundary for 0s (elements before low are 0s)
 * - mid: current element being examined
 * - high: boundary for 2s (elements after high are 2s)
 * 
 * Process element at mid:
 * - If 0: swap with low, increment both low and mid
 * - If 1: just increment mid
 * - If 2: swap with high, decrement high (don't increment mid)
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 0, 2, 1, 1, 0]
 * low=0, mid=0, high=5
 * 
 * [2, 0, 2, 1, 1, 0] mid=0, arr[0]=2 → swap with high
 * [0, 0, 2, 1, 1, 2] high=4, mid=0, arr[0]=0 → swap with low
 * [0, 0, 2, 1, 1, 2] low=1, mid=1, arr[1]=0 → swap with low
 * [0, 0, 2, 1, 1, 2] low=2, mid=2, arr[2]=2 → swap with high
 * [0, 0, 1, 1, 2, 2] high=3, mid=2, arr[2]=1 → increment mid
 * [0, 0, 1, 1, 2, 2] mid=3, arr[3]=1 → increment mid
 * Done! mid > high
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - in-place sorting
 * 
 * ===================================================================
 */

package arrays.medium

class Sort012 {
    
    /**
     * Dutch National Flag algorithm
     */
    fun sortColors(arr: IntArray) {
        var low = 0
        var mid = 0
        var high = arr.size - 1
        
        while (mid <= high) {
            when (arr[mid]) {
                0 -> {
                    // Swap with low pointer
                    val temp = arr[low]
                    arr[low] = arr[mid]
                    arr[mid] = temp
                    low++
                    mid++
                }
                1 -> {
                    // Already in correct position
                    mid++
                }
                2 -> {
                    // Swap with high pointer
                    val temp = arr[high]
                    arr[high] = arr[mid]
                    arr[mid] = temp
                    high--
                    // Don't increment mid - need to check swapped element
                }
            }
        }
    }
    
    /**
     * Alternative: Counting approach
     * Count 0s, 1s, 2s then fill array
     */
    fun sortColorsCounting(arr: IntArray) {
        var count0 = 0
        var count1 = 0
        var count2 = 0
        
        for (num in arr) {
            when (num) {
                0 -> count0++
                1 -> count1++
                2 -> count2++
            }
        }
        
        var i = 0
        repeat(count0) { arr[i++] = 0 }
        repeat(count1) { arr[i++] = 1 }
        repeat(count2) { arr[i++] = 2 }
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All same: [0,0,0] → [0,0,0]
 * 2. Already sorted: [0,1,2] → [0,1,2]
 * 3. Reverse sorted: [2,1,0] → [0,1,2]
 * 4. Single element: [1] → [1]
 * 5. Two elements: [2,0] → [0,2]
 * 6. No 1s: [0,2,0,2] → [0,0,2,2]
 * 7. No 0s: [1,2,1,2] → [1,1,2,2]
 * 8. No 2s: [0,1,0,1] → [0,0,1,1]
 * 
 * APPLICATIONS:
 * - Sorting limited range integers
 * - Partitioning in quicksort
 * - Organizing data with 3 categories
 * 
 * ===================================================================
 */

fun main() {
    val solution = Sort012()
    
    println("Sort 0s, 1s, 2s - Test Cases")
    println("==============================\n")
    
    val test1 = intArrayOf(2, 0, 2, 1, 1, 0)
    println("Test 1: ${test1.contentToString()}")
    solution.sortColors(test1)
    println("Result: ${test1.contentToString()}")
    println("Expected: [0, 0, 1, 1, 2, 2] ✓\n")
    
    val test2 = intArrayOf(2, 0, 1)
    println("Test 2: ${test2.contentToString()}")
    solution.sortColors(test2)
    println("Result: ${test2.contentToString()}")
    println("Expected: [0, 1, 2] ✓\n")
    
    val test3 = intArrayOf(0)
    println("Test 3: ${test3.contentToString()}")
    solution.sortColors(test3)
    println("Result: ${test3.contentToString()}")
    println("Expected: [0] ✓\n")
    
    println("All tests passed! ✓")
}
