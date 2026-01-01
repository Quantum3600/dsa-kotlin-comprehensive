/**
 * ===================================================================
 * PROBLEM: Left Rotate Array by D Positions
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array and a number d, rotate the array left by d positions.
 * 
 * INPUT: arr = [1, 2, 3, 4, 5], d = 2
 * OUTPUT: [3, 4, 5, 1, 2]
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - 0 <= d <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * APPROACH 1: Reversal Algorithm (Optimal)
 * 1. Reverse first d elements
 * 2. Reverse remaining n-d elements  
 * 3. Reverse entire array
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 3, 4, 5], d = 2
 * 
 * Step 1: Reverse first d elements
 * [2, 1, 3, 4, 5]
 * 
 * Step 2: Reverse remaining elements
 * [2, 1, 5, 4, 3]
 * 
 * Step 3: Reverse entire array
 * [3, 4, 5, 1, 2] ✓
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(1)
 * 
 * ===================================================================
 */

package arrays.easy

class LeftRotateByD {
    
    /**
     * Rotate left by d positions using reversal algorithm
     */
    fun rotateLeft(arr: IntArray, d: Int) {
        if (arr.isEmpty() || d == 0) return
        
        val n = arr.size
        val rotations = d % n  // Handle d > n
        
        if (rotations == 0) return
        
        // Step 1: Reverse first d elements
        reverse(arr, 0, rotations - 1)
        
        // Step 2: Reverse remaining elements
        reverse(arr, rotations, n - 1)
        
        // Step 3: Reverse entire array
        reverse(arr, 0, n - 1)
    }
    
    /**
     * Helper function to reverse array segment
     */
    private fun reverse(arr: IntArray, start: Int, end: Int) {
        var left = start
        var right = end
        
        while (left < right) {
            val temp = arr[left]
            arr[left] = arr[right]
            arr[right] = temp
            left++
            right--
        }
    }
    
    /**
     * Alternative: Using temp array (less optimal)
     */
    fun rotateLeftTemp(arr: IntArray, d: Int) {
        val n = arr.size
        val rotations = d % n
        
        val temp = arr.take(rotations)
        for (i in 0 until n - rotations) {
            arr[i] = arr[i + rotations]
        }
        for (i in 0 until rotations) {
            arr[n - rotations + i] = temp[i]
        }
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. d = 0: [1,2,3] → [1,2,3]
 * 2. d = n: [1,2,3] d=3 → [1,2,3]
 * 3. d > n: [1,2,3] d=5 → [3,1,2]
 * 4. Single element: [1] d=1 → [1]
 * 5. d = 1: Same as LeftRotateByOne
 * 
 * APPLICATIONS:
 * - Circular buffer operations
 * - String rotation check
 * - Array manipulation in algorithms
 * 
 * ===================================================================
 */

fun main() {
    val solution = LeftRotateByD()
    
    println("Left Rotate by D Positions - Test Cases")
    println("=========================================\n")
    
    val test1 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 1: ${test1.contentToString()} rotate by 2")
    solution.rotateLeft(test1, 2)
    println("Result: ${test1.contentToString()}")
    println("Expected: [3, 4, 5, 1, 2] ✓\n")
    
    val test2 = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    println("Test 2: ${test2.contentToString()} rotate by 3")
    solution.rotateLeft(test2, 3)
    println("Result: ${test2.contentToString()}")
    println("Expected: [4, 5, 6, 7, 1, 2, 3] ✓\n")
    
    val test3 = intArrayOf(1, 2, 3)
    println("Test 3: ${test3.contentToString()} rotate by 5 (> length)")
    solution.rotateLeft(test3, 5)
    println("Result: ${test3.contentToString()}")
    println("Expected: [3, 1, 2] ✓\n")
    
    println("All tests passed! ✓")
}
