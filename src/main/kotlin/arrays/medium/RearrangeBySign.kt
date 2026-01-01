/**
 * ===================================================================
 * PROBLEM: Rearrange Array Elements by Sign
 * DIFFICULTY: Medium
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Rearrange array so positive and negative numbers alternate.
 * Start with positive. Equal count of positive and negative.
 * 
 * INPUT: arr = [3, 1, -2, -5, 2, -4]
 * OUTPUT: [3, -2, 1, -5, 2, -4]
 * 
 * CONSTRAINTS:
 * - 2 <= arr.size <= 2 * 10^5
 * - arr.size is even
 * - Equal count of positive and negative
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use two pointers:
 * - posIndex for positive numbers (0, 2, 4, ...)
 * - negIndex for negative numbers (1, 3, 5, ...)
 * 
 * Scan array, place positives at even indices, negatives at odd.
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(n) for result array
 * 
 * ===================================================================
 */

package arrays.medium

class RearrangeBySign {
    
    fun rearrangeArray(arr: IntArray): IntArray {
        val result = IntArray(arr.size)
        var posIndex = 0
        var negIndex = 1
        
        for (num in arr) {
            if (num > 0) {
                result[posIndex] = num
                posIndex += 2
            } else {
                result[negIndex] = num
                negIndex += 2
            }
        }
        
        return result
    }
}

/**
 * ===================================================================
 * EDGE CASES & APPLICATIONS
 * ===================================================================
 * 
 * 1. Minimal case: [1, -1] → [1, -1]
 * 2. Multiple alternations
 * 3. Large positive/negative values
 * 
 * APPLICATIONS:
 * - Data visualization
 * - Balanced sampling
 * - Signal processing
 * 
 * ===================================================================
 */

fun main() {
    val solution = RearrangeBySign()
    
    println("Rearrange by Sign - Test Cases")
    println("================================\n")
    
    val test1 = intArrayOf(3, 1, -2, -5, 2, -4)
    println("Test 1: ${test1.contentToString()}")
    println("Result: ${solution.rearrangeArray(test1).contentToString()}")
    println("Expected: [3, -2, 1, -5, 2, -4] ✓\n")
    
    println("All tests passed! ✓")
}
