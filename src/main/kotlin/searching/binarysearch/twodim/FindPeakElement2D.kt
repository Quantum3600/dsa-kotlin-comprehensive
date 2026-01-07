/**
 * ============================================================================
 * PROBLEM:  Find a Peak Element II
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 2D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A peak element in a 2D grid is an element that is strictly greater than all
 * of its adjacent neighbors (up, down, left, right). Given a 0-indexed m x n
 * matrix, find ANY peak element and return its position [row, col].
 * 
 * You may assume: 
 * - The matrix is surrounded by -infinity (elements at borders can be peaks)
 * - There is always at least one peak in the matrix
 * 
 * INPUT FORMAT:
 * - matrix = [[1,  4,  3,  2],
 *             [5,  10, 9,  8],
 *             [6,  7,  11, 12]]
 * 
 * OUTPUT FORMAT:
 * - [1, 1] (value 10 is a peak:  10 > 4, 10 > 5, 10 > 9, 10 > 7)
 * - OR [2, 3] (value 12 is also a peak: 12 > 8, 12 > 11)
 * - Any valid peak is acceptable
 * 
 * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 500
 * - -10^9 <= matrix[i][j] <= 10^9
 * - Adjacent elements are distinct
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of the matrix as a hiking trail map with elevations.  A peak is the
 * highest point around you. Instead of checking every spot (exhausting!), 
 * find the highest point in a vertical slice of the map.  If your neighbor
 * to the right is higher, the peak must be to the right. Apply binary search
 * on columns to narrow down where the peak must be! 
 * 
 * KEY INSIGHT:
 * In the middle column, find the maximum element. Look at its neighbors: 
 * - If left neighbor is greater, a peak MUST exist in left half
 * - If right neighbor is greater, a peak MUST exist in right half
 * - Otherwise, this maximum IS a peak!
 * This allows us to eliminate half the columns in each step!
 * 
 * ALGORITHM STEPS (Binary Search on Columns):
 * 1. Set left = 0, right = n-1 (column indices)
 * 2. While left <= right:
 *    a. midCol = (left + right) / 2
 *    b. Find maximum element in midCol → (maxRow, midCol)
 *    c. Check neighbors:
 *       - If left neighbor > current:  right = midCol - 1 (peak in left half)
 *       - Else if right neighbor > current: left = midCol + 1 (peak in right half)
 *       - Else: current is a peak! Return [maxRow, midCol]
 * 3. Return the peak position
 * 
 * WHY THIS WORKS:
 * - If we're at the max of a column and a neighbor is higher, following that
 *   direction guarantees we'll eventually reach a peak (matrix is finite)
 * - We eliminate half the columns each iteration
 * 
 * VISUAL EXAMPLE:
 * matrix = [[1,  4,  3,  2],
 *           [5,  10, 9,  8],
 *           [6,  7,  11, 12]]
 * 
 * Step 1: Check middle column (col = 1 or 2, let's say 1)
 *         Column 1: [4, 10, 7]
 *         Max is 10 at row 1
 *         
 *         [1,  4→, 3,  2]
 *         [5,  10, 9,  8]    10 is max in this column
 *         [6,  7←, 11, 12]
 *         
 *         Check neighbors of 10:
 *         - Left: 5 < 10 ✓
 *         - Right: 9 < 10 ✓
 *         - Up: 4 < 10 ✓
 *         - Down: 7 < 10 ✓
 *         
 *         10 is a PEAK!  Return [1, 1]
 * 
 * ALTERNATIVE EXAMPLE (when peak not in middle):
 * matrix = [[10, 8,  7],
 *           [9,  1,  2],
 *           [5,  3,  4]]
 * 
 * Step 1: midCol = 1, max in col 1 is 8 at row 0
 *         Check neighbors:  left (10) > 8
 *         Peak must be in LEFT half!  right = 0
 * 
 * Step 2: midCol = 0, max in col 0 is 10 at row 0
 *         Check neighbors: right (8) < 10, up (-inf) < 10, down (9) < 10
 *         10 is a PEAK! Return [0, 0]
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search on Columns:  O(m * log n) time, O(1) space - OPTIMAL
 * 2. Binary Search on Rows: O(n * log m) time, O(1) space - Similar
 * 3. Greedy Climb: O(m * n) worst case, O(1) space - Simpler but slower
 * 4. Brute Force: O(m * n) time, O(1) space - Check every element
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================================
 * TIME:  O(m * log n) where m is rows and n is columns
 *       - Binary search on columns: O(log n) iterations
 *       - Each iteration finds max in a column:  O(m)
 *       - Total: m * log n
 * 
 * SPACE: O(1)
 *        - Only using constant extra space for pointers
 * ============================================================================
 */

package searching.binarysearch. twodim

/**
 * Finds a peak element in a 2D matrix using binary search on columns. 
 * 
 * @param matrix A 2D matrix of integers
 * @return IntArray of size 2 containing [row, col] of a peak element
 */
fun findPeakElement2D(matrix: Array<IntArray>): IntArray {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return intArrayOf(-1, -1)
    }
    
    val m = matrix.size
    val n = matrix[0].size
    
    var left = 0
    var right = n - 1
    
    while (left <= right) {
        val midCol = left + (right - left) / 2
        
        // Find the maximum element in the middle column
        val maxRow = findMaxInColumn(matrix, midCol)
        val currentValue = matrix[maxRow][midCol]
        
        // Get neighbor values (treat out of bounds as negative infinity)
        val leftValue = if (midCol > 0) matrix[maxRow][midCol - 1] else Int.MIN_VALUE
        val rightValue = if (midCol < n - 1) matrix[maxRow][midCol + 1] else Int.MIN_VALUE
        
        // Check if current element is a peak
        when {
            currentValue > leftValue && currentValue > rightValue -> {
                // Found a peak! 
                return intArrayOf(maxRow, midCol)
            }
            leftValue > currentValue -> {
                // Peak must be in left half
                right = midCol - 1
            }
            else -> {
                // Peak must be in right half (rightValue > currentValue)
                left = midCol + 1
            }
        }
    }
    
    // Should never reach here if input is valid
    return intArrayOf(-1, -1)
}

/**
 * Helper function to find the row index of maximum element in a given column. 
 * 
 * @param matrix The 2D matrix
 * @param col The column index to search
 * @return Row index of maximum element in the column
 */
private fun findMaxInColumn(matrix: Array<IntArray>, col: Int): Int {
    var maxRow = 0
    var maxValue = matrix[0][col]
    
    for (row in 1 until matrix.size) {
        if (matrix[row][col] > maxValue) {
            maxValue = matrix[row][col]
            maxRow = row
        }
    }
    
    return maxRow
}

/**
 * Alternative approach:  Greedy climb from any starting point
 * Always move to the highest neighbor until no neighbor is higher
 * 
 * Time: O(m * n) worst case, but often much faster in practice
 * Space: O(1)
 */
fun findPeakElement2DGreedy(matrix: Array<IntArray>): IntArray {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return intArrayOf(-1, -1)
    }
    
    val m = matrix. size
    val n = matrix[0].size
    
    // Start from middle of matrix
    var row = m / 2
    var col = n / 2
    
    while (true) {
        val current = matrix[row][col]
        
        // Check all four neighbors
        val neighbors = mutableListOf<Triple<Int, Int, Int>>()
        
        if (row > 0) neighbors.add(Triple(row - 1, col, matrix[row - 1][col]))        // Up
        if (row < m - 1) neighbors.add(Triple(row + 1, col, matrix[row + 1][col]))    // Down
        if (col > 0) neighbors.add(Triple(row, col - 1, matrix[row][col - 1]))        // Left
        if (col < n - 1) neighbors.add(Triple(row, col + 1, matrix[row][col + 1]))    // Right
        
        // Find the maximum neighbor
        val maxNeighbor = neighbors. maxByOrNull { it.third }
        
        // If no neighbor is greater, current is a peak
        if (maxNeighbor == null || maxNeighbor.third <= current) {
            return intArrayOf(row, col)
        }
        
        // Move to the maximum neighbor
        row = maxNeighbor.first
        col = maxNeighbor.second
    }
}

/**
 * Brute force approach: Check every element
 * 
 * Time: O(m * n)
 * Space: O(1)
 */
fun findPeakElement2DBruteForce(matrix: Array<IntArray>): IntArray {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return intArrayOf(-1, -1)
    }
    
    val m = matrix. size
    val n = matrix[0].size
    
    for (i in 0 until m) {
        for (j in 0 until n) {
            val current = matrix[i][j]
            
            // Check all neighbors
            val up = if (i > 0) matrix[i - 1][j] else Int.MIN_VALUE
            val down = if (i < m - 1) matrix[i + 1][j] else Int.MIN_VALUE
            val left = if (j > 0) matrix[i][j - 1] else Int.MIN_VALUE
            val right = if (j < n - 1) matrix[i][j + 1] else Int.MIN_VALUE
            
            // Check if current is a peak
            if (current > up && current > down && current > left && current > right) {
                return intArrayOf(i, j)
            }
        }
    }
    
    return intArrayOf(-1, -1)
}

/**
 * Helper function to verify if a position is a peak
 */
fun isPeak(matrix: Array<IntArray>, row: Int, col: Int): Boolean {
    val m = matrix.size
    val n = matrix[0].size
    val current = matrix[row][col]
    
    val up = if (row > 0) matrix[row - 1][col] else Int.MIN_VALUE
    val down = if (row < m - 1) matrix[row + 1][col] else Int.MIN_VALUE
    val left = if (col > 0) matrix[row][col - 1] else Int.MIN_VALUE
    val right = if (col < n - 1) matrix[row][col + 1] else Int.MIN_VALUE
    
    return current > up && current > down && current > left && current > right
}

/**
 * ============================================================================
 * TEST CASES & USAGE
 * ============================================================================
 */
fun main() {
    // Test Case 1: Standard case with clear peak
    val matrix1 = arrayOf(
        intArrayOf(1, 4, 3, 2),
        intArrayOf(5, 10, 9, 8),
        intArrayOf(6, 7, 11, 12)
    )
    val peak1 = findPeakElement2D(matrix1)
    println("Test 1 - Binary Search:  [${peak1[0]}, ${peak1[1]}] = ${matrix1[peak1[0]][peak1[1]]}")
    println("Is peak? ${isPeak(matrix1, peak1[0], peak1[1])}")
    
    // Test Case 2: Peak at corner
    val matrix2 = arrayOf(
        intArrayOf(10, 8, 7),
        intArrayOf(9, 1, 2),
        intArrayOf(5, 3, 4)
    )
    val peak2 = findPeakElement2D(matrix2)
    println("\nTest 2 - Peak at corner: [${peak2[0]}, ${peak2[1]}] = ${matrix2[peak2[0]][peak2[1]]}")
    println("Is peak? ${isPeak(matrix2, peak2[0], peak2[1])}")
    
    // Test Case 3: Single element
    val matrix3 = arrayOf(intArrayOf(5))
    val peak3 = findPeakElement2D(matrix3)
    println("\nTest 3 - Single element:  [${peak3[0]}, ${peak3[1]}] = ${matrix3[peak3[0]][peak3[1]]}")
    
    // Test Case 4: Single row
    val matrix4 = arrayOf(intArrayOf(1, 5, 3, 2, 8, 4))
    val peak4 = findPeakElement2D(matrix4)
    println("\nTest 4 - Single row:  [${peak4[0]}, ${peak4[1]}] = ${matrix4[peak4[0]][peak4[1]]}")
    println("Is peak? ${isPeak(matrix4, peak4[0], peak4[1])}")
    
    // Test Case 5: Single column
    val matrix5 = arrayOf(
        intArrayOf(1),
        intArrayOf(5),
        intArrayOf(3),
        intArrayOf(8),
        intArrayOf(4)
    )
    val peak5 = findPeakElement2D(matrix5)
    println("\nTest 5 - Single column: [${peak5[0]}, ${peak5[1]}] = ${matrix5[peak5[0]][peak5[1]]}")
    println("Is peak? ${isPeak(matrix5, peak5[0], peak5[1])}")
    
    // Test Case 6: Peak at boundary
    val matrix6 = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 15),
        intArrayOf(7, 8, 9)
    )
    val peak6 = findPeakElement2D(matrix6)
    println("\nTest 6 - Peak at boundary: [${peak6[0]}, ${peak6[1]}] = ${matrix6[peak6[0]][peak6[1]]}")
    println("Is peak?  ${isPeak(matrix6, peak6[0], peak6[1])}")
    
    // Compare all approaches
    println("\n" + "=". repeat(60))
    println("COMPARING ALL APPROACHES")
    println("=".repeat(60))
    
    val peak1a = findPeakElement2D(matrix1)
    val peak1b = findPeakElement2DGreedy(matrix1)
    val peak1c = findPeakElement2DBruteForce(matrix1)
    
    println("\nBinary Search:  [${peak1a[0]}, ${peak1a[1]}] = ${matrix1[peak1a[0]][peak1a[1]]}")
    println("Greedy Climb: [${peak1b[0]}, ${peak1b[1]}] = ${matrix1[peak1b[0]][peak1b[1]]}")
    println("Brute Force: [${peak1c[0]}, ${peak1c[1]}] = ${matrix1[peak1c[0]][peak1c[1]]}")
    
    // Visual demonstration
    println("\n" + "=".repeat(60))
    println("VISUAL DEMONSTRATION")
    println("=".repeat(60))
    
    println("\nMatrix:")
    matrix1.forEachIndexed { i, row ->
        println(row.joinToString("  ", "[", "]") { it.toString().padStart(2) })
    }
    
    println("\nPeak found at [${peak1a[0]}, ${peak1a[1]}] with value ${matrix1[peak1a[0]][peak1a[1]]}")
    
    // Show all peaks in the matrix
    println("\nAll peaks in matrix:")
    for (i in matrix1.indices) {
        for (j in matrix1[0].indices) {
            if (isPeak(matrix1, i, j)) {
                println("  [${i}, ${j}] = ${matrix1[i][j]}")
            }
        }
    }
}
