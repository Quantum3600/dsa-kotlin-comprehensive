/**
 * ============================================================================
 * PROBLEM: Row with Maximum 1s
 * DIFFICULTY: Medium
 * CATEGORY:  Searching - Binary Search 2D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a boolean 2D matrix where each row is sorted (0s followed by 1s),
 * find the row with the maximum number of 1s.  If multiple rows have the same
 * maximum count, return the smallest row index.
 * 
 * INPUT FORMAT:
 * - matrix = [[0, 1, 1, 1],
 *             [0, 0, 1, 1],
 *             [1, 1, 1, 1],  ← This row has maximum 1s (4)
 *             [0, 0, 0, 0]]
 * 
 * OUTPUT FORMAT:
 * - 2 (index of row with maximum 1s)
 * 
 * CONSTRAINTS:
 * - 1 <= matrix.length, matrix[0].length <= 1000
 * - matrix[i][j] is either 0 or 1
 * - Each row is sorted (all 0s come before all 1s)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine rows as queues of people where some have tickets (1) and some don't (0).
 * People without tickets are at the front, ticket holders at the back.  Since 
 * it's sorted, you only need to find WHERE the tickets start in each row, not
 * count every person! 
 * 
 * KEY INSIGHT:
 * Since each row is sorted (0s followed by 1s), we don't need to count all 1s.
 * We just need to find the FIRST occurrence of 1 in each row using binary search.
 * Then:  count of 1s = n - (index of first 1)
 * 
 * ALGORITHM STEPS (Binary Search Approach):
 * 1. Initialize maxCount = 0, maxRowIndex = -1
 * 2. For each row (0 to m-1):
 *    a. Use binary search to find index of first 1
 *    b. Calculate count of 1s:  n - firstIndex
 *    c. If count > maxCount: 
 *       - Update maxCount = count
 *       - Update maxRowIndex = current row
 * 3. Return maxRowIndex (or -1 if no 1s found)
 * 
 * BINARY SEARCH FOR FIRST 1:
 * - We're looking for the LEFTMOST 1 (lower bound)
 * - If mid is 1, search left to find earlier 1s
 * - If mid is 0, search right for the first 1
 * 
 * VISUAL EXAMPLE: 
 * matrix = [[0, 0, 1, 1],    ← 2 ones (n=4, first 1 at index 2, count=4-2=2)
 *           [0, 0, 0, 1],    ← 1 one  (n=4, first 1 at index 3, count=4-3=1)
 *           [1, 1, 1, 1],    ← 4 ones (n=4, first 1 at index 0, count=4-0=4) ★
 *           [0, 0, 0, 0]]    ← 0 ones (n=4, no 1 found, count=0)
 * 
 * Row 0: Binary search finds first 1 at index 2 → count = 4-2 = 2
 * Row 1: Binary search finds first 1 at index 3 → count = 4-3 = 1
 * Row 2: Binary search finds first 1 at index 0 → count = 4-0 = 4 (MAX!)
 * Row 3: Binary search finds no 1 → count = 0
 * 
 * Answer: Row 2 with 4 ones
 * 
 * OPTIMIZED APPROACH (Staircase):
 * Start from top-right corner and move left when we see 1:
 * - If we see 1, move LEFT and update result (more 1s in this row)
 * - If we see 0, move DOWN (no more 1s in this row)
 * This eliminates rows/columns in O(m + n) time!
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search per Row: O(m * log n) time, O(1) space - Good for large n
 * 2. Staircase Optimization: O(m + n) time, O(1) space - OPTIMAL
 * 3. Linear Count per Row: O(m * n) time, O(1) space - Doesn't use sorted property
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================================
 * TIME: O(m * log n) where m is rows and n is columns
 *       - For each of m rows, we do binary search:  O(log n)
 *       - Total: m * log n
 * 
 * SPACE: O(1)
 *        - Only using constant extra space for tracking max
 * ============================================================================
 */

package searching.binarysearch.twodim

/**
 * Finds the row with maximum number of 1s using binary search.
 * 
 * @param matrix A 2D boolean matrix where each row is sorted (0s followed by 1s)
 * @return Index of row with maximum 1s, or -1 if matrix is empty or no 1s exist
 */
fun rowWithMax1s(matrix: Array<IntArray>): Int {
    // Edge case: empty matrix
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    val m = matrix.size
    val n = matrix[0].size
    
    var maxCount = 0
    var maxRowIndex = -1
    
    // Check each row
    for (i in 0 until m) {
        // Find index of first 1 using binary search
        val firstOneIndex = findFirstOne(matrix[i])
        
        // Calculate count of 1s in this row
        val countOnes = if (firstOneIndex == -1) 0 else n - firstOneIndex
        
        // Update max if current row has more 1s
        if (countOnes > maxCount) {
            maxCount = countOnes
            maxRowIndex = i
        }
    }
    
    return maxRowIndex
}

/**
 * Binary search to find the index of the first occurrence of 1 in a sorted row.
 * Returns -1 if no 1 is found.
 * 
 * @param row A sorted array containing 0s and 1s
 * @return Index of first 1, or -1 if no 1 exists
 */
private fun findFirstOne(row: IntArray): Int {
    var left = 0
    var right = row.size - 1
    var result = -1
    
    while (left <= right) {
        val mid = left + (right - left) / 2
        
        if (row[mid] == 1) {
            // Found a 1, but check if there's an earlier one
            result = mid
            right = mid - 1  // Search in left half for earlier 1
        } else {
            // row[mid] == 0, search in right half
            left = mid + 1
        }
    }
    
    return result
}

/**
 * OPTIMIZED APPROACH:  Staircase method
 * Start from top-right and move strategically
 * 
 * Time:  O(m + n) - More efficient! 
 * Space: O(1)
 */
fun rowWithMax1sOptimized(matrix:  Array<IntArray>): Int {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    val m = matrix.size
    val n = matrix[0]. size
    
    var row = 0
    var col = n - 1
    var maxRowIndex = -1
    
    // Start from top-right corner
    while (row < m && col >= 0) {
        if (matrix[row][col] == 1) {
            // Found a 1, move left to find more 1s
            maxRowIndex = row  // Update result
            col--              // Move left
        } else {
            // Found a 0, move down
            row++
        }
    }
    
    return maxRowIndex
}

/**
 * Brute force approach: Count 1s in each row
 * 
 * Time: O(m * n)
 * Space: O(1)
 */
fun rowWithMax1sBruteForce(matrix:  Array<IntArray>): Int {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    var maxCount = 0
    var maxRowIndex = -1
    
    for (i in matrix.indices) {
        val count = matrix[i].count { it == 1 }
        if (count > maxCount) {
            maxCount = count
            maxRowIndex = i
        }
    }
    
    return maxRowIndex
}

/**
 * ============================================================================
 * TEST CASES & USAGE
 * ============================================================================
 */
fun main() {
    // Test Case 1: Standard case
    val matrix1 = arrayOf(
        intArrayOf(0, 1, 1, 1),
        intArrayOf(0, 0, 1, 1),
        intArrayOf(1, 1, 1, 1),  // Row 2 has max (4 ones)
        intArrayOf(0, 0, 0, 0)
    )
    println("Test 1 - Binary Search: ${rowWithMax1s(matrix1)}")  // Expected: 2
    println("Test 1 - Optimized: ${rowWithMax1sOptimized(matrix1)}")  // Expected: 2
    println("Test 1 - Brute Force: ${rowWithMax1sBruteForce(matrix1)}")  // Expected: 2
    
    // Test Case 2: All zeros
    val matrix2 = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )
    println("\nTest 2 - All zeros: ${rowWithMax1s(matrix2)}")  // Expected: -1
    
    // Test Case 3: All ones
    val matrix3 = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1)
    )
    println("\nTest 3 - All ones: ${rowWithMax1s(matrix3)}")  // Expected: 0 (first row)
    
    // Test Case 4: First row has max
    val matrix4 = arrayOf(
        intArrayOf(1, 1, 1, 1, 1),  // Row 0 has max
        intArrayOf(0, 0, 1, 1, 1),
        intArrayOf(0, 0, 0, 1, 1)
    )
    println("\nTest 4 - First row max: ${rowWithMax1s(matrix4)}")  // Expected: 0
    
    // Test Case 5: Last row has max
    val matrix5 = arrayOf(
        intArrayOf(0, 0, 0, 0, 1),
        intArrayOf(0, 0, 0, 1, 1),
        intArrayOf(0, 1, 1, 1, 1)   // Row 2 has max
    )
    println("\nTest 5 - Last row max: ${rowWithMax1s(matrix5)}")  // Expected: 2
    
    // Test Case 6: Single row
    val matrix6 = arrayOf(intArrayOf(0, 0, 1, 1, 1))
    println("\nTest 6 - Single row:  ${rowWithMax1s(matrix6)}")  // Expected: 0
    
    // Test Case 7: Single column
    val matrix7 = arrayOf(
        intArrayOf(0),
        intArrayOf(0),
        intArrayOf(1),  // Row 2 has the only 1
        intArrayOf(1)
    )
    println("\nTest 7 - Single column: ${rowWithMax1s(matrix7)}")  // Expected: 2
    
    // Test Case 8: Tie - should return first occurrence
    val matrix8 = arrayOf(
        intArrayOf(0, 1, 1, 1),  // Row 0 has 3 ones
        intArrayOf(0, 1, 1, 1),  // Row 1 also has 3 ones
        intArrayOf(0, 0, 1, 1)
    )
    println("\nTest 8 - Tie (should return first): ${rowWithMax1s(matrix8)}")  // Expected: 0
    
    // Performance comparison
    println("\n" + "=".repeat(60))
    println("PERFORMANCE COMPARISON")
    println("=".repeat(60))
    
    val largeMatrix = Array(100) { row ->
        IntArray(100) { col ->
            if (col >= row) 1 else 0  // Increasing number of 1s per row
        }
    }
    
    val start1 = System.nanoTime()
    val result1 = rowWithMax1s(largeMatrix)
    val time1 = (System.nanoTime() - start1) / 1000
    
    val start2 = System.nanoTime()
    val result2 = rowWithMax1sOptimized(largeMatrix)
    val time2 = (System.nanoTime() - start2) / 1000
    
    val start3 = System.nanoTime()
    val result3 = rowWithMax1sBruteForce(largeMatrix)
    val time3 = (System.nanoTime() - start3) / 1000
    
    println("\n100x100 matrix:")
    println("Binary Search: Row $result1, Time: $time1 μs")
    println("Optimized (Staircase): Row $result2, Time: $time2 μs")
    println("Brute Force: Row $result3, Time: $time3 μs")
    
    // Visual demonstration
    println("\n" + "=".repeat(60))
    println("VISUAL DEMONSTRATION")
    println("=".repeat(60))
    
    println("\nMatrix:")
    matrix1.forEachIndexed { index, row ->
        val countOnes = row.count { it == 1 }
        println("Row $index: ${row.joinToString(" ")} → $countOnes ones")
    }
    
    println("\nResult:  Row ${rowWithMax1s(matrix1)} has maximum 1s")
}
