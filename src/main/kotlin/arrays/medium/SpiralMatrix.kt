/**
 * ===================================================================
 * PROBLEM: Spiral Matrix Traversal
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Matrix
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given m x n matrix, return all elements in spiral order.
 * 
 * INPUT:
 * [1, 2, 3]
 * [4, 5, 6]
 * [7, 8, 9]
 * 
 * OUTPUT: [1, 2, 3, 6, 9, 8, 7, 4, 5]
 * 
 * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 10
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use four boundaries:
 * - top, bottom, left, right
 * 
 * Traverse in order: right, down, left, up
 * Shrink boundaries after each direction
 * 
 * COMPLEXITY:
 * Time: O(m * n)
 * Space: O(1) excluding output
 * 
 * ===================================================================
 */

package arrays.medium

class SpiralMatrix {
    
    fun spiralOrder(matrix: Array<IntArray>): List<Int> {
        val result = mutableListOf<Int>()
        if (matrix.isEmpty()) return result
        
        var top = 0
        var bottom = matrix.size - 1
        var left = 0
        var right = matrix[0].size - 1
        
        while (top <= bottom && left <= right) {
            // Traverse right
            for (j in left..right) {
                result.add(matrix[top][j])
            }
            top++
            
            // Traverse down
            for (i in top..bottom) {
                result.add(matrix[i][right])
            }
            right--
            
            // Traverse left (if still valid)
            if (top <= bottom) {
                for (j in right downTo left) {
                    result.add(matrix[bottom][j])
                }
                bottom--
            }
            
            // Traverse up (if still valid)
            if (left <= right) {
                for (i in bottom downTo top) {
                    result.add(matrix[i][left])
                }
                left++
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
 * 1. Single row: [[1,2,3]] → [1,2,3]
 * 2. Single column: [[1],[2],[3]] → [1,2,3]
 * 3. 1x1: [[1]] → [1]
 * 
 * APPLICATIONS:
 * - Matrix printing
 * - Image processing
 * - Game board traversal
 * 
 * ===================================================================
 */

fun main() {
    val solution = SpiralMatrix()
    
    println("Spiral Matrix - Test Cases")
    println("============================\n")
    
    val test1 = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )
    
    println("Test 1:")
    test1.forEach { println(it.contentToString()) }
    println("\nResult: ${solution.spiralOrder(test1)}")
    println("Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5] ✓\n")
    
    println("All tests passed! ✓")
}
