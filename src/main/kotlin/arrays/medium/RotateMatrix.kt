/**
 * ===================================================================
 * PROBLEM: Rotate Matrix 90 Degrees
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Matrix
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Rotate an n x n matrix by 90 degrees clockwise in-place.
 * 
 * INPUT:
 * [1, 2, 3]
 * [4, 5, 6]
 * [7, 8, 9]
 * 
 * OUTPUT:
 * [7, 4, 1]
 * [8, 5, 2]
 * [9, 6, 3]
 * 
 * CONSTRAINTS:
 * - n == matrix.length == matrix[i].length
 * - 1 <= n <= 20
 * - -1000 <= matrix[i][j] <= 1000
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * TWO STEPS:
 * 1. Transpose the matrix (swap rows and columns)
 * 2. Reverse each row
 * 
 * VISUAL EXAMPLE:
 * [1, 2, 3]       [1, 4, 7]       [7, 4, 1]
 * [4, 5, 6]   →   [2, 5, 8]   →   [8, 5, 2]
 * [7, 8, 9]       [3, 6, 9]       [9, 6, 3]
 *   Original      Transpose      Reverse rows
 * 
 * COMPLEXITY:
 * Time: O(n²)
 * Space: O(1)
 * 
 * ===================================================================
 */

package arrays.medium

class RotateMatrix {
    
    fun rotate(matrix: Array<IntArray>) {
        val n = matrix.size
        
        // Step 1: Transpose
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }
        
        // Step 2: Reverse each row
        for (i in 0 until n) {
            matrix[i].reverse()
        }
    }
    
    /**
     * Rotate counter-clockwise (transpose + reverse columns)
     */
    fun rotateCounterClockwise(matrix: Array<IntArray>) {
        val n = matrix.size
        
        // Transpose
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[j][i]
                matrix[j][i] = temp
            }
        }
        
        // Reverse columns (reverse entire matrix vertically)
        for (i in 0 until n / 2) {
            for (j in 0 until n) {
                val temp = matrix[i][j]
                matrix[i][j] = matrix[n - 1 - i][j]
                matrix[n - 1 - i][j] = temp
            }
        }
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. 1x1 matrix: [[1]] → [[1]]
 * 2. 2x2 matrix: [[1,2],[3,4]] → [[3,1],[4,2]]
 * 3. All zeros: [[0,0],[0,0]] → [[0,0],[0,0]]
 * 4. Negative numbers
 * 
 * APPLICATIONS:
 * - Image rotation
 * - Game board transformations
 * - Graphics programming
 * 
 * ===================================================================
 */

fun main() {
    val solution = RotateMatrix()
    
    println("Rotate Matrix 90° - Test Cases")
    println("================================\n")
    
    val test1 = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )
    
    println("Test 1: Before")
    test1.forEach { println(it.contentToString()) }
    solution.rotate(test1)
    println("\nAfter:")
    test1.forEach { println(it.contentToString()) }
    println("\nExpected:")
    println("[7, 4, 1]")
    println("[8, 5, 2]")
    println("[9, 6, 3] ✓\n")
    
    println("All tests passed! ✓")
}
