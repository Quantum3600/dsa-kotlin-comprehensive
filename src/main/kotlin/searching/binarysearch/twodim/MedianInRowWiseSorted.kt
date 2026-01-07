/**
 * ============================================================================
 * PROBLEM:  Median in a Row-Wise Sorted Matrix
 * DIFFICULTY: Hard
 * CATEGORY:  Searching - Binary Search 2D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an m x n matrix where each row is sorted in non-decreasing order,
 * find the median of the matrix.  The median is the middle element when all
 * elements are arranged in sorted order. 
 * 
 * Assume m * n is always odd (guaranteed single median).
 * 
 * INPUT FORMAT:
 * - matrix = [[1,  3,  5],
 *             [2,  6,  9],
 *             [3,  6,  9]]
 * 
 * OUTPUT FORMAT:
 * - 5 (sorted array would be [1,2,3,3,5,6,6,9,9], median at index 4 is 5)
 * 
 * CONSTRAINTS:
 * - m == matrix.length
 * - n == matrix[i].length
 * - 1 <= m, n <= 500
 * - m * n is odd
 * - 1 <= matrix[i][j] <= 10^6
 * - Each row is sorted in non-decreasing order
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of actually sorting all elements (expensive! ), we play a guessing
 * game:  "Is X the median?" The median has a special property:  exactly HALF
 * the elements are smaller than or equal to it. So we binary search on the 
 * VALUE range (not indices), counting how many elements are ≤ our guess.  
 * When the count is exactly half + 1, we found it! 
 * 
 * Think of it like asking: "How many students scored less than 75?" If the
 * answer is "exactly half the class", then 75 is the median score! 
 * 
 * KEY INSIGHT:
 * The median has the property that at least (m*n)/2 + 1 elements are ≤ it.
 * Instead of sorting (O(mn log mn)), we:
 * 1. Binary search on the VALUE space (min to max in matrix)
 * 2. For each candidate value, count elements ≤ it across all rows
 * 3. Adjust search space based on count
 * 
 * ALGORITHM STEPS (Binary Search on Answer):
 * 1. Find min = smallest element in matrix (matrix[0][0] or min of first column)
 * 2. Find max = largest element in matrix (matrix[m-1][n-1] or max of last column)
 * 3. Calculate desired = (m * n) / 2 (number of elements that should be smaller)
 * 4. Binary search on value range [min, max]: 
 *    a. mid = (min + max) / 2
 *    b. count = number of elements ≤ mid in entire matrix
 *    c. If count ≤ desired:  median is larger, min = mid + 1
 *    d. Else: median could be mid or smaller, max = mid - 1
 * 5. Return min (the median value)
 * 
 * COUNTING ELEMENTS ≤ MID:
 * For each row (which is sorted), use upper bound to count elements ≤ mid,
 * then sum across all rows.  This is O(m * log n).
 * 
 * VISUAL EXAMPLE:
 * matrix = [[1, 3, 5],
 *           [2, 6, 9],
 *           [3, 6, 9]]
 * 
 * Flattened sorted:  [1, 2, 3, 3, 5, 6, 6, 9, 9]
 *                    0  1  2  3  4  5  6  7  8
 * Total elements:  9, Median position: index 4 (0-indexed) = 5th element
 * Desired count: 9/2 = 4 (want 4 elements smaller than median)
 * 
 * Binary Search Process:
 * min = 1, max = 9, desired = 4
 * 
 * Iteration 1: mid = (1 + 9) / 2 = 5
 *   Count elements ≤ 5:
 *   Row 0: [1, 3, 5] → 3 elements ≤ 5 (1, 3, 5)
 *   Row 1: [2, 6, 9] → 1 element ≤ 5 (2)
 *   Row 2: [3, 6, 9] → 1 element ≤ 5 (3)
 *   Total count = 5 > desired (4)
 *   max = 5 - 1 = 4
 * 
 * Iteration 2: mid = (1 + 4) / 2 = 2
 *   Count elements ≤ 2:
 *   Row 0: [1, 3, 5] → 1 element ≤ 2 (1)
 *   Row 1: [2, 6, 9] → 1 element ≤ 2 (2)
 *   Row 2: [3, 6, 9] → 0 elements ≤ 2
 *   Total count = 2 ≤ desired (4)
 *   min = 2 + 1 = 3
 * 
 * Iteration 3: mid = (3 + 4) / 2 = 3
 *   Count elements ≤ 3:
 *   Row 0: [1, 3, 5] → 2 elements ≤ 3 (1, 3)
 *   Row 1: [2, 6, 9] → 1 element ≤ 3 (2)
 *   Row 2: [3, 6, 9] → 1 element ≤ 3 (3)
 *   Total count = 4 ≤ desired (4)
 *   min = 3 + 1 = 4
 * 
 * Iteration 4: mid = (4 + 4) / 2 = 4
 *   Count elements ≤ 4:
 *   Row 0: [1, 3, 5] → 2 elements ≤ 4 (1, 3)
 *   Row 1: [2, 6, 9] → 1 element ≤ 4 (2)
 *   Row 2: [3, 6, 9] → 1 element ≤ 4 (3)
 *   Total count = 4 ≤ desired (4)
 *   min = 4 + 1 = 5
 * 
 * Iteration 5: min = 5, max = 4, loop ends
 * Answer: 5 ✓
 * 
 * WHY THIS WORKS:
 * We're searching for the smallest number where at least (n*m)/2 + 1 elements
 * are ≤ it. This is by definition the median! 
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search on Answer: O(log(max-min) * m * log n) time, O(1) space - OPTIMAL
 * 2. Merge All Rows: O(m*n log(m*n)) time, O(m*n) space - Simple but inefficient
 * 3. Min Heap (K-way merge): O(m*n log m) time, O(m) space - Better but still slow
 * 4. Counting Sort: O(m*n + range) time, O(range) space - Only if range is small
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY
 * ============================================================================
 * TIME:  O(log(max - min) * m * log n)
 *       - Binary search on value range:  O(log(max - min))
 *       - For each iteration, count elements in all rows: O(m * log n)
 *         - m rows, each binary search takes O(log n)
 *       - If values are in range [1, 10^6], log(max-min) ≈ 20
 * 
 * SPACE: O(1)
 *        - Only using constant extra space for variables
 * ============================================================================
 */

package searching. binarysearch. twodim

/**
 * Finds the median of a row-wise sorted matrix using binary search on values.
 * 
 * @param matrix A 2D matrix where each row is sorted in non-decreasing order
 * @return The median value
 */
fun findMedianSortedMatrix(matrix: Array<IntArray>): Int {
    // Edge case: empty matrix
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    val m = matrix.size
    val n = matrix[0].size
    
    // Find the minimum and maximum values in the matrix
    var min = Int.MAX_VALUE
    var max = Int.MIN_VALUE
    
    for (i in 0 until m) {
        min = minOf(min, matrix[i][0])           // First element of each row
        max = maxOf(max, matrix[i][n - 1])       // Last element of each row
    }
    
    // The median position:  for odd total, it's at index (m*n)/2
    // We want 'desired' elements to be smaller than median
    val desired = (m * n) / 2
    
    // Binary search on the value range
    while (min < max) {
        val mid = min + (max - min) / 2
        
        // Count how many elements are ≤ mid
        val count = countLessOrEqual(matrix, mid)
        
        if (count <= desired) {
            // Not enough elements ≤ mid, median must be larger
            min = mid + 1
        } else {
            // Too many elements ≤ mid, median is mid or smaller
            max = mid
        }
    }
    
    return min
}

/**
 * Counts how many elements in the matrix are less than or equal to the target.
 * Uses binary search on each row since rows are sorted.
 * 
 * @param matrix The row-wise sorted matrix
 * @param target The value to compare against
 * @return Total count of elements ≤ target
 */
private fun countLessOrEqual(matrix: Array<IntArray>, target: Int): Int {
    var count = 0
    
    // For each row, find how many elements are ≤ target
    for (row in matrix) {
        count += upperBound(row, target)
    }
    
    return count
}

/**
 * Finds the number of elements less than or equal to target in a sorted array.
 * This is essentially finding the upper bound (rightmost position where we can insert).
 * 
 * @param arr Sorted array
 * @param target Value to find upper bound for
 * @return Number of elements ≤ target
 */
private fun upperBound(arr: IntArray, target: Int): Int {
    var left = 0
    var right = arr.size
    
    while (left < right) {
        val mid = left + (right - left) / 2
        
        if (arr[mid] <= target) {
            left = mid + 1
        } else {
            right = mid
        }
    }
    
    return left
}

/**
 * Alternative approach: Flatten and sort
 * Simple but memory intensive
 * 
 * Time: O(m*n log(m*n))
 * Space: O(m*n)
 */
fun findMedianSortedMatrixNaive(matrix: Array<IntArray>): Int {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    // Flatten the matrix into a single list
    val flatList = matrix.flatMap { it. toList() }
    
    // Sort the list
    val sorted = flatList.sorted()
    
    // Return the middle element
    return sorted[sorted. size / 2]
}

/**
 * Alternative approach: Using min heap (k-way merge)
 * More efficient than naive but still not optimal
 * 
 * Time: O(m*n log m)
 * Space: O(m)
 */
fun findMedianSortedMatrixHeap(matrix: Array<IntArray>): Int {
    if (matrix.isEmpty() || matrix[0].isEmpty()) {
        return -1
    }
    
    val m = matrix.size
    val n = matrix[0]. size
    val totalElements = m * n
    val medianPos = totalElements / 2
    
    // Min heap:  stores (value, rowIndex, colIndex)
    val heap = java.util.PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })
    
    // Initialize heap with first element of each row
    for (i in 0 until m) {
        heap.offer(Triple(matrix[i][0], i, 0))
    }
    
    // Extract elements one by one until we reach median position
    var count = 0
    var median = -1
    
    while (heap.isNotEmpty() && count <= medianPos) {
        val (value, row, col) = heap.poll()
        median = value
        count++
        
        // Add next element from the same row if available
        if (col + 1 < n) {
            heap.offer(Triple(matrix[row][col + 1], row, col + 1))
        }
    }
    
    return median
}

/**
 * ============================================================================
 * TEST CASES & USAGE
 * ============================================================================
 */
fun main() {
    // Test Case 1: Standard 3x3 matrix
    val matrix1 = arrayOf(
        intArrayOf(1, 3, 5),
        intArrayOf(2, 6, 9),
        intArrayOf(3, 6, 9)
    )
    println("Test 1 - Standard matrix:")
    println("Binary Search:  ${findMedianSortedMatrix(matrix1)}")  // Expected: 5
    println("Naive Approach: ${findMedianSortedMatrixNaive(matrix1)}")  // Expected: 5
    println("Heap Approach: ${findMedianSortedMatrixHeap(matrix1)}")  // Expected: 5
    
    // Test Case 2: Matrix with duplicates
    val matrix2 = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(2, 2, 2),
        intArrayOf(3, 3, 3)
    )
    println("\nTest 2 - Duplicates:")
    println("Result: ${findMedianSortedMatrix(matrix2)}")  // Expected: 2
    
    // Test Case 3: Single row
    val matrix3 = arrayOf(
        intArrayOf(1, 2, 3, 4, 5)
    )
    println("\nTest 3 - Single row:")
    println("Result: ${findMedianSortedMatrix(matrix3)}")  // Expected: 3
    
    // Test Case 4: Single column
    val matrix4 = arrayOf(
        intArrayOf(1),
        intArrayOf(2),
        intArrayOf(3),
        intArrayOf(4),
        intArrayOf(5)
    )
    println("\nTest 4 - Single column:")
    println("Result: ${findMedianSortedMatrix(matrix4)}")  // Expected: 3
    
    // Test Case 5: Large value range
    val matrix5 = arrayOf(
        intArrayOf(1, 10, 20),
        intArrayOf(15, 25, 35),
        intArrayOf(5, 30, 40)
    )
    println("\nTest 5 - Large value range:")
    println("Result: ${findMedianSortedMatrix(matrix5)}")  // Expected: 20
    
    // Test Case 6: All same values
    val matrix6 = arrayOf(
        intArrayOf(5, 5, 5),
        intArrayOf(5, 5, 5),
        intArrayOf(5, 5, 5)
    )
    println("\nTest 6 - All same values:")
    println("Result: ${findMedianSortedMatrix(matrix6)}")  // Expected: 5
    
    // Test Case 7: Sequential values
    val matrix7 = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )
    println("\nTest 7 - Sequential 1-9:")
    println("Result: ${findMedianSortedMatrix(matrix7)}")  // Expected: 5
    
    // Visual demonstration
    println("\n" + "=".repeat(60))
    println("VISUAL DEMONSTRATION")
    println("=".repeat(60))
    
    val demoMatrix = arrayOf(
        intArrayOf(1, 3, 5),
        intArrayOf(2, 6, 9),
        intArrayOf(3, 6, 9)
    )
    
    println("\nMatrix:")
    demoMatrix.forEach { row ->
        println(row.joinToString(", ", "[", "]"))
    }
    
    println("\nFlattened and sorted:")
    val flattened = demoMatrix. flatMap { it.toList() }. sorted()
    println(flattened.joinToString(", ", "[", "]"))
    println("                    ↑")
    println("                 median (index ${flattened.size / 2})")
    
    val median = findMedianSortedMatrix(demoMatrix)
    println("\nMedian value: $median")
    
    // Performance comparison
    println("\n" + "=".repeat(60))
    println("PERFORMANCE COMPARISON")
    println("=".repeat(60))
    
    val largeMatrix = Array(100) { row ->
        IntArray(100) { col ->
            row * 100 + col + 1
        }
    }
    
    val start1 = System.nanoTime()
    val result1 = findMedianSortedMatrix(largeMatrix)
    val time1 = (System.nanoTime() - start1) / 1_000_000
    
    val start2 = System.nanoTime()
    val result2 = findMedianSortedMatrixNaive(largeMatrix)
    val time2 = (System.nanoTime() - start2) / 1_000_000
    
    val start3 = System.nanoTime()
    val result3 = findMedianSortedMatrixHeap(largeMatrix)
    val time3 = (System.nanoTime() - start3) / 1_000_000
    
    println("\n100x100 matrix (10,000 elements):")
    println("Binary Search on Answer: $result1, Time: $time1 ms")
    println("Naive (Flatten & Sort): $result2, Time: $time2 ms")
    println("Min Heap (K-way merge): $result3, Time: $time3 ms")
    
    // Step-by-step demonstration
    println("\n" + "=".repeat(60))
    println("STEP-BY-STEP BINARY SEARCH")
    println("=".repeat(60))
    
    val stepMatrix = arrayOf(
        intArrayOf(1, 3, 5),
        intArrayOf(2, 6, 9),
        intArrayOf(3, 6, 9)
    )
    
    var min = Int.MAX_VALUE
    var max = Int.MIN_VALUE
    
    for (i in stepMatrix.indices) {
        min = minOf(min, stepMatrix[i][0])
        max = maxOf(max, stepMatrix[i][stepMatrix[0].size - 1])
    }
    
    val desired = (stepMatrix.size * stepMatrix[0].size) / 2
    
    println("\nInitial:  min = $min, max = $max, desired count = $desired")
    
    var iteration = 1
    while (min < max) {
        val mid = min + (max - min) / 2
        val count = countLessOrEqual(stepMatrix, mid)
        
        println("\nIteration $iteration:")
        println("  mid = $mid")
        println("  count(≤ $mid) = $count")
        
        if (count <= desired) {
            println("  count ≤ desired → search higher, min = ${mid + 1}")
            min = mid + 1
        } else {
            println("  count > desired → search lower, max = $mid")
            max = mid
        }
        
        iteration++
    }
    
    println("\nFinal answer: $min")
}
