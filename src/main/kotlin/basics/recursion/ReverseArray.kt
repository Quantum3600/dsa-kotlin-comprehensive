/**
 * ============================================================================
 * PROBLEM: Reverse an Array using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Recursion/Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, reverse the array in-place using recursion.
 * 
 * INPUT FORMAT:
 * - An array of integers
 * - Example: [1, 2, 3, 4, 5]
 * 
 * OUTPUT FORMAT:
 * - The same array reversed
 * - Example: [5, 4, 3, 2, 1]
 * 
 * CONSTRAINTS:
 * - 0 <= array.size <= 10^5
 * - -10^9 <= array[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To reverse an array recursively:
 * - Swap first and last elements
 * - Recursively reverse the middle portion
 * - Base case: when start >= end, stop
 * 
 * TWO-POINTER APPROACH:
 * - Use two indices: start (beginning) and end (end of array)
 * - Swap elements at start and end
 * - Move start forward, end backward
 * - Recurse until start >= end
 * 
 * VISUAL EXAMPLE:
 * 
 * Array: [1, 2, 3, 4, 5]
 *         ↓           ↓
 *      start=0     end=4
 * 
 * Step 1: Swap arr[0] and arr[4]
 * Result: [5, 2, 3, 4, 1]
 *            ↓        ↓
 *         start=1  end=3
 * 
 * Step 2: Swap arr[1] and arr[3]
 * Result: [5, 4, 3, 2, 1]
 *               ↓  ↓
 *            start=2, end=2
 * 
 * Step 3: start >= end, STOP ✓
 * 
 * ALGORITHM STEPS:
 * 1. Base case: if start >= end, return (array is reversed)
 * 2. Swap elements at start and end indices
 * 3. Recursively call with start+1 and end-1
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pointer recursion: O(n) time, O(n) space - IMPLEMENTED
 * 2. Single index recursion: O(n) time, O(n) space - Alternative
 * 3. Iterative two-pointer: O(n) time, O(1) space - Non-recursive
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n/2) = O(n)
 * - We make n/2 recursive calls (swap pairs)
 * - Each call does O(1) work (swap)
 * - Total: O(n/2) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth is n/2
 * - Each frame uses constant space
 * - Total stack space: O(n/2) = O(n)
 * 
 * WHY n/2 CALLS:
 * - We swap elements from both ends moving inward
 * - Stop when pointers meet in middle
 * - For n elements, we make n/2 swaps
 * 
 * ============================================================================
 */

package basics.recursion

class ReverseArray {
    
    /**
     * Reverse array using two-pointer recursion
     * TIME: O(n), SPACE: O(n) stack space
     */
    fun reverseArray(arr: IntArray) {
        reverseHelper(arr, 0, arr.size - 1)
    }
    
    /**
     * Helper function with start and end pointers
     */
    private fun reverseHelper(arr: IntArray, start: Int, end: Int) {
        // Base case: when pointers meet or cross
        if (start >= end) {
            return
        }
        
        // Swap elements at start and end
        val temp = arr[start]
        arr[start] = arr[end]
        arr[end] = temp
        
        // Recursive call with moved pointers
        reverseHelper(arr, start + 1, end - 1)
    }
    
    /**
     * Reverse using single index (alternative approach)
     * TIME: O(n), SPACE: O(n)
     */
    fun reverseArraySingleIndex(arr: IntArray) {
        reverseSingleHelper(arr, 0)
    }
    
    private fun reverseSingleHelper(arr: IntArray, index: Int) {
        // Base case: reached middle
        if (index >= arr.size / 2) {
            return
        }
        
        // Swap with mirror position
        val mirrorIndex = arr.size - 1 - index
        val temp = arr[index]
        arr[index] = arr[mirrorIndex]
        arr[mirrorIndex] = temp
        
        // Recurse for next index
        reverseSingleHelper(arr, index + 1)
    }
    
    /**
     * Create reversed copy (functional approach)
     * TIME: O(n), SPACE: O(n)
     * 
     * Returns new array, doesn't modify original
     */
    fun reverseArrayCopy(arr: IntArray): IntArray {
        if (arr.isEmpty()) return intArrayOf()
        return reverseArrayCopyHelper(arr, arr.size - 1)
    }
    
    private fun reverseArrayCopyHelper(arr: IntArray, index: Int): IntArray {
        // Base case: last element
        if (index == 0) {
            return intArrayOf(arr[0])
        }
        
        // Build reversed array recursively
        return intArrayOf(arr[index]) + reverseArrayCopyHelper(arr, index - 1)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [1, 2, 3, 4, 5]
 * 
 * TWO-POINTER APPROACH:
 * 
 * reverseHelper([1,2,3,4,5], 0, 4):
 * - start=0, end=4, not >= , continue
 * - Swap arr[0] and arr[4]: [5,2,3,4,1]
 * - Call reverseHelper([5,2,3,4,1], 1, 3)
 * 
 * reverseHelper([5,2,3,4,1], 1, 3):
 * - start=1, end=3, not >=, continue
 * - Swap arr[1] and arr[3]: [5,4,3,2,1]
 * - Call reverseHelper([5,4,3,2,1], 2, 2)
 * 
 * reverseHelper([5,4,3,2,1], 2, 2):
 * - start=2, end=2, start >= end
 * - BASE CASE: return
 * 
 * Result: [5, 4, 3, 2, 1] ✓
 * 
 * CALL STACK:
 * reverseHelper(arr, 0, 4) → swap 1↔5
 *   └─ reverseHelper(arr, 1, 3) → swap 2↔4
 *        └─ reverseHelper(arr, 2, 2) → base case
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty array: [] → []
 * 2. Single element: [5] → [5]
 * 3. Two elements: [1, 2] → [2, 1]
 * 4. Odd length: [1, 2, 3] → [3, 2, 1]
 * 5. Even length: [1, 2, 3, 4] → [4, 3, 2, 1]
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ReverseArray()
    
    println("=== Reverse Array using Recursion ===\n")
    
    // Test 1: Normal case - odd length
    println("Test 1: Odd length array")
    val arr1 = intArrayOf(1, 2, 3, 4, 5)
    println("Original: ${arr1.contentToString()}")
    solution.reverseArray(arr1)
    println("Reversed: ${arr1.contentToString()}")
    println("Expected: [5, 4, 3, 2, 1]\n")
    
    // Test 2: Even length
    println("Test 2: Even length array")
    val arr2 = intArrayOf(10, 20, 30, 40)
    println("Original: ${arr2.contentToString()}")
    solution.reverseArray(arr2)
    println("Reversed: ${arr2.contentToString()}")
    println("Expected: [40, 30, 20, 10]\n")
    
    // Test 3: Single element
    println("Test 3: Single element")
    val arr3 = intArrayOf(7)
    println("Original: ${arr3.contentToString()}")
    solution.reverseArray(arr3)
    println("Reversed: ${arr3.contentToString()}")
    println("Expected: [7]\n")
    
    // Test 4: Two elements
    println("Test 4: Two elements")
    val arr4 = intArrayOf(5, 10)
    println("Original: ${arr4.contentToString()}")
    solution.reverseArray(arr4)
    println("Reversed: ${arr4.contentToString()}")
    println("Expected: [10, 5]\n")
    
    // Test 5: Empty array
    println("Test 5: Empty array")
    val arr5 = intArrayOf()
    println("Original: ${arr5.contentToString()}")
    solution.reverseArray(arr5)
    println("Reversed: ${arr5.contentToString()}")
    println("Expected: []\n")
    
    // Test 6: Single index approach
    println("Test 6: Single index approach")
    val arr6 = intArrayOf(1, 2, 3, 4, 5, 6)
    println("Original: ${arr6.contentToString()}")
    solution.reverseArraySingleIndex(arr6)
    println("Reversed: ${arr6.contentToString()}")
    println("Expected: [6, 5, 4, 3, 2, 1]\n")
    
    // Test 7: Copy approach (functional)
    println("Test 7: Functional copy approach")
    val arr7 = intArrayOf(11, 22, 33, 44)
    println("Original: ${arr7.contentToString()}")
    val reversed7 = solution.reverseArrayCopy(arr7)
    println("Original unchanged: ${arr7.contentToString()}")
    println("Reversed copy: ${reversed7.contentToString()}")
    println("Expected original: [11, 22, 33, 44]")
    println("Expected reversed: [44, 33, 22, 11]\n")
    
    // Test 8: With negative numbers
    println("Test 8: With negative numbers")
    val arr8 = intArrayOf(-1, -2, 0, 1, 2)
    println("Original: ${arr8.contentToString()}")
    solution.reverseArray(arr8)
    println("Reversed: ${arr8.contentToString()}")
    println("Expected: [2, 1, 0, -2, -1]")
}
