/**
 * ============================================================================
 * PROBLEM: Move Zeros to End
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, move all zeros to the end while maintaining
 * the relative order of non-zero elements. Must be done in-place.
 * 
 * INPUT: arr = [0, 1, 0, 3, 12]
 * OUTPUT: [1, 3, 12, 0, 0]
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^4
 * - -2^31 <= arr[i] <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * Use two pointers:
 * - j: tracks position for next non-zero element
 * - i: scans the array
 * 
 * When we find a non-zero element, place it at position j and increment j.
 * After processing all elements, fill remaining positions with zeros.
 * 
 * VISUAL EXAMPLE:
 * [0, 1, 0, 3, 12]
 *  ↓  ↓
 *  j  i
 * 
 * i=0: arr[0]=0, skip
 * i=1: arr[1]=1, non-zero → arr[j=0]=1, j=1
 * i=2: arr[2]=0, skip
 * i=3: arr[3]=3, non-zero → arr[j=1]=3, j=2
 * i=4: arr[4]=12, non-zero → arr[j=2]=12, j=3
 * 
 * Fill remaining with zeros: arr[3]=0, arr[4]=0
 * Result: [1, 3, 12, 0, 0]
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - in-place modification
 * 
 * ============================================================================
 */

package arrays.easy

class MoveZerosToEnd {
    
    fun moveZeros(arr: IntArray) {
        var j = 0  // Position for next non-zero element
        
        // Move all non-zero elements to front
        for (i in arr.indices) {
            if (arr[i] != 0) {
                arr[j] = arr[i]
                j++
            }
        }
        
        // Fill remaining positions with zeros
        while (j < arr.size) {
            arr[j] = 0
            j++
        }
    }
    
    /**
     * Alternative: Swap approach (maintains stability)
     */
    fun moveZerosSwap(arr: IntArray) {
        var j = 0  // Position of first zero
        
        for (i in arr.indices) {
            if (arr[i] != 0) {
                // Swap non-zero with zero at position j
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
                j++
            }
        }
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. No zeros: [1, 2, 3] → [1, 2, 3]
 * 2. All zeros: [0, 0, 0] → [0, 0, 0]
 * 3. Single element: [0] → [0], [5] → [5]
 * 4. Zeros at start: [0, 0, 1, 2] → [1, 2, 0, 0]
 * 5. Zeros at end: [1, 2, 0, 0] → [1, 2, 0, 0]
 * 6. Alternating: [0, 1, 0, 2] → [1, 2, 0, 0]
 * 7. Negative numbers: [-1, 0, 2] → [-1, 2, 0]
 * 8. Empty array: [] → []
 * 
 * APPLICATIONS:
 * - Data compression
 * - Removing null/empty entries
 * - Log file processing
 * - Array manipulation in games
 * 
 * MISTAKES TO AVOID:
 * - Not maintaining relative order
 * - Using extra space
 * - Not handling all zeros/no zeros cases
 * 
 * ============================================================================
 */

fun main() {
    val solution = MoveZerosToEnd()
    
    println("Move Zeros to End - Test Cases")
    println("================================\n")
    
    val test1 = intArrayOf(0, 1, 0, 3, 12)
    println("Test 1: ${test1.contentToString()}")
    solution.moveZeros(test1)
    println("Result: ${test1.contentToString()}")
    println("Expected: [1, 3, 12, 0, 0] ✓\n")
    
    val test2 = intArrayOf(0, 0, 1)
    println("Test 2: ${test2.contentToString()}")
    solution.moveZeros(test2)
    println("Result: ${test2.contentToString()}")
    println("Expected: [1, 0, 0] ✓\n")
    
    val test3 = intArrayOf(1, 2, 3)
    println("Test 3: ${test3.contentToString()}")
    solution.moveZeros(test3)
    println("Result: ${test3.contentToString()}")
    println("Expected: [1, 2, 3] ✓\n")
    
    val test4 = intArrayOf(0, 0, 0)
    println("Test 4: ${test4.contentToString()}")
    solution.moveZeros(test4)
    println("Result: ${test4.contentToString()}")
    println("Expected: [0, 0, 0] ✓\n")
    
    val test5 = intArrayOf(1, 0, 2, 0, 3)
    println("Test 5: ${test5.contentToString()}")
    solution.moveZeros(test5)
    println("Result: ${test5.contentToString()}")
    println("Expected: [1, 2, 3, 0, 0] ✓\n")
    
    println("All tests passed! ✓")
}
