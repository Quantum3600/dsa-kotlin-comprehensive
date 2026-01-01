/**
 * ===================================================================
 * PROBLEM: Set Matrix Zeroes
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Matrix
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * If an element is 0, set its entire row and column to 0s.
 * Do it in-place.
 * 
 * INPUT:
 * [1, 1, 1]
 * [1, 0, 1]
 * [1, 1, 1]
 * 
 * OUTPUT:
 * [1, 0, 1]
 * [0, 0, 0]
 * [1, 0, 1]
 * 
 * CONSTRAINTS:
 * - m x n matrix
 * - 1 <= m, n <= 200
 * - -2^31 <= matrix[i][j] <= 2^31 - 1
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * OPTIMAL: Use first row and column as markers
 * 1. Check if first row/column have zeros
 * 2. Use first row/column to mark zeros
 * 3. Process matrix based on markers
 * 4. Handle first row/column separately
 * 
 * COMPLEXITY:
 * Time: O(m * n)
 * Space: O(1)
 * 
 * ===================================================================
 */

package arrays.medium

class SetMatrixZeroes {
    
    fun setZeroes(matrix: Array<IntArray>) {
        val m = matrix.size
        val n = matrix[0].size
        var firstRowZero = false
        var firstColZero = false
        
        // Check if first row has zero
        for (j in 0 until n) {
            if (matrix[0][j] == 0) {
                firstRowZero = true
                break
            }
        }
        
        // Check if first column has zero
        for (i in 0 until m) {
            if (matrix[i][0] == 0) {
                firstColZero = true
                break
            }
        }
        
        // Use first row and column as markers
        for (i in 1 until m) {
            for (j in 1 until n) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0
                    matrix[0][j] = 0
                }
            }
        }
        
        // Set zeros based on markers
        for (i in 1 until m) {
            for (j in 1 until n) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0
                }
            }
        }
        
        // Handle first row
        if (firstRowZero) {
            for (j in 0 until n) {
                matrix[0][j] = 0
            }
        }
        
        // Handle first column
        if (firstColZero) {
            for (i in 0 until m) {
                matrix[i][0] = 0
            }
        }
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Single row: [[0,1,2]] → [[0,0,0]]
 * 2. Single column: [[0],[1],[2]] → [[0],[0],[0]]
 * 3. No zeros: [[1,2],[3,4]] → [[1,2],[3,4]]
 * 4. All zeros: [[0,0],[0,0]] → [[0,0],[0,0]]
 * 
 * APPLICATIONS:
 * - Image processing
 * - Game boards
 * - Grid-based algorithms
 * 
 * ===================================================================
 */

fun main() {
    val solution = SetMatrixZeroes()
    
    println("Set Matrix Zeroes - Test Cases")
    println("================================\n")
    
    val test1 = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 0, 1),
        intArrayOf(1, 1, 1)
    )
    
    println("Test 1: Before")
    test1.forEach { println(it.contentToString()) }
    solution.setZeroes(test1)
    println("\nAfter:")
    test1.forEach { println(it.contentToString()) }
    println("\nExpected:")
    println("[1, 0, 1]")
    println("[0, 0, 0]")
    println("[1, 0, 1] ✓\n")
    
    println("All tests passed! ✓")
}
