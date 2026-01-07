/**
 * ============================================================================
 * PROBLEM: Search a 2D Matrix II
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 2D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Write an efficient algorithm that searches for a target value in an m x n
 * integer matrix. This matrix has the following properties:
 * - Integers in each row are sorted in ascending from left to right
 * - Integers in each column are sorted in ascending from top to bottom
 * 
 * NOTE: Unlike SearchMatrix, the first element of a row may NOT be greater
 * than the last element of the previous row.
 * 
 * INPUT FORMAT:
 * - matrix = [[1,4,7,11,15],
 *             [2,5,8,12,19],
 *             [3,6,9,16,22],
 *             [10,13,14,17,24],
 *             [18,21,23,26,30]]
 * - target = 5
 * 
 * OUTPUT FORMAT:
 * - true (target 5 is found at position [1,1])
 * 
 * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i]. length
 * - 1 <= m, n <= 300
 * - -10^9 <= matrix[i][j], target <= 10^9
 * - Each row is sorted in non-decreasing order
 * - Each column is sorted in non-decreasing order
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine you're looking at a staircase from the side. You can either go down
 * (increase row) or go left (decrease column). Start from the top-right corner: 
 * - If current number is too large, move LEFT (smaller numbers)
 * - If current number is too small, move DOWN (larger numbers)
 * This way, you eliminate one row or column with each comparison! 
 * 
 * KEY INSIGHT:
 * Starting from top-right (or bottom-left), we have a unique property:
 * - All elements to the LEFT are smaller
 * - All elements BELOW are larger
 * This gives us a clear decision path at each step!
 * 
 * ALGORITHM STEPS (Top-Right Approach):
 * 1. Start at top-right corner:  row = 0, col = n-1
 * 2. While within bounds:
 *    a.  If matrix[row][col] == target, return true (FOUND!)
 *    b. If matrix[row][col] > target, move LEFT (col--)
 *       - Target can't be in this column (all below are larger)
 *    c. If matrix[row][col] < target, move DOWN (row++)
 *       - Target can't be in this row (all to left are smaller)
 * 3. If we go out of bounds, target not found, return false
 * 
 * VISUAL EXAMPLE:
 * matrix = [[1,  4,  7,  11, 15],      target = 5
 *           [2,  5,  8,  12, 19],
 *           [3,  6,  9,  16, 22],
 *           [10, 13, 14, 17, 24],
 *           [18, 21, 23, 26, 30]]
 * 
 * Step 1: Start at [0,4] = 15
 *         [1, 4, 7, 11, 15*]  ← We are here
 *         15 > 5, move LEFT
 * 
 * Step 2: Now at [0,3] = 11
 *         [1, 4, 7, 11*, 15]
 *         11 > 5, move LEFT
 * 
 * Step 3: Now at [0,2] = 7
 *         [1, 4, 7*, 11, 15]
 *         7 > 5, move LEFT
 * 
 * Step 4: Now at [0,1] = 4
 *         [1, 4*, 7, 11, 15]
 *         4 < 5, move DOWN
 * 
 * Step 5: Now at [1,1] = 5
 *         [2, 5*, 8, 12, 19]
 *         5 == 5, FOUND!
 * 
 * WHY TOP-RIGHT (or BOTTOM-LEFT)?
 * These corners have the special property where one direction always increases
 * and the other always decreases. Top-left and bottom-right don't have this!
 * 
 * ALTERNATIVE APPROACHES: 
 * 1. Staircase Search (Top-Right): O(m + n) time, O(1) space - OPTIMAL
 * 2. Staircase Search (Bottom-Left): O(m + n) time, O(1) space - Same efficiency
 * 3. Binary Search Each Row: O(m * log n) time, O(1) space - Slower
 * 4.  Brute Force: O(m * n) time, O(1) space - Doesn't use sorted property
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================================
 * TIME: O(m + n) where m is rows and n is columns
 *       - In worst case, we traverse from top-right to bottom-left
 *       - Maximum steps: m (down) + n (left)
 * 
 * SPACE: O(1)
 *        - Only using constant extra space for row and col pointers
 * ============================================================================
 */

package searching.binarysearch.twodim

/**
 * Searches for a target value in a 2D matrix where each row and column is sorted.
 * Uses the "staircase" approach starting from top-right corner.
 * 
 * @param matrix A 2D matrix where rows and columns are sorted in ascending order
 * @param target The value to search for
 * @return true if target exists in matrix, false otherwise
 */
fun searchMatrixII(matrix: Array<IntArray>, target: Int): Boolean {
    // Edge case: empty matrix
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return false
    }
    
    var row = 0                      // Start at top row
    var col = matrix[0].size - 1     // Start at rightmost column
    
    // Traverse the matrix using staircase approach
    while (row < matrix.size && col >= 0) {
        val current = matrix[row][col]
        
        when {
            current == target -> return true    // Found the target! 
            current > target -> col--          // Move left (smaller values)
            else -> row++                      // Move down (larger values)
        }
    }
    
    return false  // Target not found after exhausting search
}

/**
 * Alternative approach: Start from bottom-left corner
 * Same complexity, just different starting position
 * 
 * Time: O(m + n)
 * Space: O(1)
 */
fun searchMatrixIIBottomLeft(matrix: Array<IntArray>, target: Int): Boolean {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return false
    }
    
    var row = matrix.size - 1    // Start at bottom row
    var col = 0                  // Start at leftmost column
    
    while (row >= 0 && col < matrix[0].size) {
        val current = matrix[row][col]
        
        when {
            current == target -> return true    // Found! 
            current > target -> row--          // Move up (smaller values)
            else -> col++                      // Move right (larger values)
        }
    }
    
    return false
}

/**
 * Alternative approach: Binary search on each row
 * Less efficient but still valid
 * 
 * Time: O(m * log n)
 * Space: O(1)
 */
fun searchMatrixIIBinarySearch(matrix: Array<IntArray>, target:  Int): Boolean {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return false
    }
    
    // Apply binary search on each row
    for (row in matrix) {
        if (binarySearchRow(row, target)) {
            return true
        }
    }
    
    return false
}

/**
 * Helper function:  Binary search in a single row
 */
private fun binarySearchRow(row:  IntArray, target: Int): Boolean {
    var left = 0
    var right = row.size - 1
    
    while (left <= right) {
        val mid = left + (right - left) / 2
        
        when {
            row[mid] == target -> return true
            row[mid] < target -> left = mid + 1
            else -> right = mid - 1
        }
    }
    
    return false
}

/**
 * ============================================================================
 * TEST CASES & USAGE
 * ============================================================================
 */
fun main() {
    val matrix = arrayOf(
        intArrayOf(1, 4, 7, 11, 15),
        intArrayOf(2, 5, 8, 12, 19),
        intArrayOf(3, 6, 9, 16, 22),
        intArrayOf(10, 13, 14, 17, 24),
        intArrayOf(18, 21, 23, 26, 30)
    )
    
    // Test Case 1: Target exists in matrix
    println("Test 1 - Search for 5: ${searchMatrixII(matrix, 5)}")  // Expected: true
    println("Test 1 (Bottom-Left) - Search for 5: ${searchMatrixIIBottomLeft(matrix, 5)}")  // Expected: true
    
    // Test Case 2: Target doesn't exist
    println("\nTest 2 - Search for 20: ${searchMatrixII(matrix, 20)}")  // Expected: false
    
    // Test Case 3: Target at corners
    println("\nTest 3 - Search for 1 (top-left): ${searchMatrixII(matrix, 1)}")    // Expected: true
    println("Test 3 - Search for 30 (bottom-right): ${searchMatrixII(matrix, 30)}")  // Expected: true
    println("Test 3 - Search for 15 (top-right): ${searchMatrixII(matrix, 15)}")    // Expected: true
    println("Test 3 - Search for 18 (bottom-left): ${searchMatrixII(matrix, 18)}")  // Expected: true
    
    // Test Case 4: Single element matrix
    val matrix2 = arrayOf(intArrayOf(5))
    println("\nTest 4 - Single element [5], search 5: ${searchMatrixII(matrix2, 5)}")  // Expected: true
    println("Test 4 - Single element [5], search 3: ${searchMatrixII(matrix2, 3)}")    // Expected: false
    
    // Test Case 5: Single row
    val matrix3 = arrayOf(intArrayOf(1, 3, 5, 7, 9))
    println("\nTest 5 - Single row, search 5: ${searchMatrixII(matrix3, 5)}")  // Expected: true
    
    // Test Case 6: Single column
    val matrix4 = arrayOf(
        intArrayOf(1),
        intArrayOf(3),
        intArrayOf(5),
        intArrayOf(7)
    )
    println("\nTest 6 - Single column, search 3: ${searchMatrixII(matrix4, 3)}")  // Expected: true
    
    // Test Case 7: Edge values
    println("\nTest 7 - Search for -1 (below min): ${searchMatrixII(matrix, -1)}")   // Expected: false
    println("Test 7 - Search for 100 (above max): ${searchMatrixII(matrix, 100)}")   // Expected: false
    
    // Compare all three approaches
    println("\n" + "=".repeat(60))
    println("COMPARING ALL APPROACHES")
    println("=".repeat(60))
    
    val target = 14
    println("\nSearching for: $target")
    println("Top-Right Approach: ${searchMatrixII(matrix, target)}")
    println("Bottom-Left Approach: ${searchMatrixIIBottomLeft(matrix, target)}")
    println("Binary Search Approach: ${searchMatrixIIBinarySearch(matrix, target)}")
    
    // Visual demonstration
    println("\n" + "=".repeat(60))
    println("VISUAL DEMONSTRATION - Path Taken")
    println("=".repeat(60))
    
    println("\nMatrix:")
    matrix.forEach { row ->
        println(row.joinToString("  ", "[", "]") { it.toString().padStart(2) })
    }
    
    println("\nSearching for 5 from top-right:")
    println("Path: [0,4]=15 → [0,3]=11 → [0,2]=7 → [0,1]=4 → [1,1]=5 ✓")
}
