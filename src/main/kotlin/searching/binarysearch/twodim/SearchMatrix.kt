/**
 * ============================================================================
 * PROBLEM: Search in a 2D Matrix
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 2D
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Write an efficient algorithm that searches for a target value in an m x n 
 * integer matrix.  This matrix has the following properties:
 * - Integers in each row are sorted from left to right
 * - The first integer of each row is greater than the last integer of the 
 *   previous row
 * 
 * INPUT FORMAT:
 * - matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]]
 * - target = 3
 * 
 * OUTPUT FORMAT:
 * - true (target 3 is found in the matrix)
 * 
 * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 100
 * - -10^4 <= matrix[i][j], target <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine you have a library with multiple shelves (rows). Books on each shelf
 * are sorted by ID. The first book on shelf 2 has a higher ID than the last
 * book on shelf 1. You can treat ALL books as one continuous sorted list! 
 * 
 * KEY INSIGHT:
 * Since each row is sorted AND the first element of each row is greater than
 * the last element of the previous row, we can treat the entire 2D matrix as
 * a single sorted 1D array and apply standard binary search.
 * 
 * ALGORITHM STEPS: 
 * 1. Calculate total elements:  total = m * n
 * 2. Apply binary search on virtual 1D array from 0 to total-1
 * 3. For each mid index in 1D array: 
 *    a. Convert to 2D:  row = mid / n, col = mid % n
 *    b.  Get element:  matrix[row][col]
 *    c. Compare with target and adjust search space
 * 4. Return true if found, false otherwise
 * 
 * VISUAL EXAMPLE:
 * matrix = [[1,  3,  5,  7],
 *           [10, 11, 16, 20],
 *           [23, 30, 34, 60]]
 * target = 11
 * 
 * Treat as 1D:  [1, 3, 5, 7, 10, 11, 16, 20, 23, 30, 34, 60]
 * Indices:      0  1  2  3   4   5   6   7   8   9  10  11
 * 
 * Step 1: left=0, right=11, mid=5
 *         1D index 5 → 2D: row=5/4=1, col=5%4=1 → matrix[1][1]=11
 *         11 == 11, FOUND!
 * 
 * INDEX CONVERSION FORMULA:
 * - 1D to 2D: row = index / columns, col = index % columns
 * - Example: index=5, cols=4 → row=1, col=1
 * 
 * WHY THIS WORKS:
 * The matrix properties guarantee that if we flatten it, we get a sorted array. 
 * Binary search works perfectly on sorted arrays! 
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search (Virtual 1D): O(log(m*n)) time, O(1) space - OPTIMAL
 * 2. Binary Search Row + Binary Search Col: O(log m + log n) time, O(1) space
 * 3. Linear Search: O(m*n) time, O(1) space - Doesn't use sorted property
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================================
 * TIME: O(log(m*n)) where m is rows and n is columns
 *       - Binary search on m*n elements
 *       - Each operation takes O(1)
 * 
 * SPACE: O(1)
 *        - Only using a constant amount of extra space for pointers
 * ============================================================================
 */

package searching.binarysearch.twodim

/**
 * Searches for a target value in a 2D matrix with sorted rows and sorted columns.
 * 
 * @param matrix A 2D matrix where each row is sorted and first element of each
 *               row is greater than last element of previous row
 * @param target The value to search for
 * @return true if target exists in matrix, false otherwise
 */
fun searchMatrix(matrix: Array<IntArray>, target: Int): Boolean {
    // Edge case: empty matrix
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return false
    }
    
    val m = matrix.size        // Number of rows
    val n = matrix[0].size     // Number of columns
    
    // Binary search on virtual 1D array
    var left = 0
    var right = m * n - 1
    
    while (left <= right) {
        val mid = left + (right - left) / 2
        
        // Convert 1D index to 2D coordinates
        val row = mid / n
        val col = mid % n
        val midValue = matrix[row][col]
        
        when {
            midValue == target -> return true      // Found the target
            midValue < target -> left = mid + 1    // Search right half
            else -> right = mid - 1                // Search left half
        }
    }
    
    return false  // Target not found
}

/**
 * Alternative approach:  Two-step binary search
 * First find the row, then search within that row
 * 
 * Time: O(log m + log n)
 * Space: O(1)
 */
fun searchMatrixTwoStep(matrix: Array<IntArray>, target: Int): Boolean {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return false
    }
    
    val m = matrix.size
    val n = matrix[0]. size
    
    // Step 1: Binary search to find the correct row
    var top = 0
    var bottom = m - 1
    var targetRow = -1
    
    while (top <= bottom) {
        val mid = top + (bottom - top) / 2
        
        when {
            matrix[mid][0] <= target && target <= matrix[mid][n - 1] -> {
                targetRow = mid
                break
            }
            matrix[mid][0] > target -> bottom = mid - 1
            else -> top = mid + 1
        }
    }
    
    // If no valid row found
    if (targetRow == -1) return false
    
    // Step 2: Binary search within the found row
    var left = 0
    var right = n - 1
    
    while (left <= right) {
        val mid = left + (right - left) / 2
        
        when {
            matrix[targetRow][mid] == target -> return true
            matrix[targetRow][mid] < target -> left = mid + 1
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
    // Test Case 1: Target found in middle
    val matrix1 = arrayOf(
        intArrayOf(1, 3, 5, 7),
        intArrayOf(10, 11, 16, 20),
        intArrayOf(23, 30, 34, 60)
    )
    println("Test 1 - Search for 3:  ${searchMatrix(matrix1, 3)}")  // Expected: true
    println("Test 1 (Two-step) - Search for 3: ${searchMatrixTwoStep(matrix1, 3)}")  // Expected: true
    
    // Test Case 2: Target not found
    println("\nTest 2 - Search for 13: ${searchMatrix(matrix1, 13)}")  // Expected: false
    
    // Test Case 3: Target at boundaries
    println("\nTest 3 - Search for 1 (first): ${searchMatrix(matrix1, 1)}")    // Expected: true
    println("Test 3 - Search for 60 (last): ${searchMatrix(matrix1, 60)}")     // Expected: true
    
    // Test Case 4: Single element matrix
    val matrix2 = arrayOf(intArrayOf(5))
    println("\nTest 4 - Single element [5], search 5: ${searchMatrix(matrix2, 5)}")   // Expected: true
    println("Test 4 - Single element [5], search 3: ${searchMatrix(matrix2, 3)}")     // Expected: false
    
    // Test Case 5: Single row
    val matrix3 = arrayOf(intArrayOf(1, 3, 5, 7, 9))
    println("\nTest 5 - Single row, search 5: ${searchMatrix(matrix3, 5)}")   // Expected: true
    
    // Test Case 6: Single column
    val matrix4 = arrayOf(
        intArrayOf(1),
        intArrayOf(3),
        intArrayOf(5),
        intArrayOf(7)
    )
    println("\nTest 6 - Single column, search 3: ${searchMatrix(matrix4, 3)}")  // Expected: true
    
    // Test Case 7: Larger matrix
    val matrix5 = arrayOf(
        intArrayOf(1, 4, 7, 11, 15),
        intArrayOf(2, 5, 8, 12, 19),
        intArrayOf(3, 6, 9, 16, 22),
        intArrayOf(10, 13, 14, 17, 24),
        intArrayOf(18, 21, 23, 26, 30)
    )
    println("\nTest 7 - Search for 5: ${searchMatrix(matrix5, 5)}")   // Expected: true
    println("Test 7 - Search for 20: ${searchMatrix(matrix5, 20)}")   // Expected: false
    
    // Visual demonstration
    println("\n" + "=".repeat(60))
    println("VISUAL DEMONSTRATION")
    println("=".repeat(60))
    
    val demoMatrix = arrayOf(
        intArrayOf(1, 3, 5, 7),
        intArrayOf(10, 11, 16, 20),
        intArrayOf(23, 30, 34, 60)
    )
    
    println("\nMatrix:")
    demoMatrix.forEach { row ->
        println(row.joinToString(", ", "[", "]") { it.toString().padStart(2) })
    }
    
    val target = 16
    println("\nSearching for:  $target")
    println("Result: ${searchMatrix(demoMatrix, target)}")
    
    // Show the virtual 1D representation
    println("\nVirtual 1D array representation:")
    val flattened = demoMatrix.flatMap { it. toList() }
    println(flattened.joinToString(", "))
}
