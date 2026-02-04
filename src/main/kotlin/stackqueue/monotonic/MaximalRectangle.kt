/**
 * ============================================================================
 * PROBLEM: Maximal Rectangle
 * DIFFICULTY: Hard
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest 
 * rectangle containing only 1's and return its area.
 * 
 * INPUT FORMAT:
 * - matrix: 2D array of characters ('0' or '1')
 * Example:
 * matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"],
 *   ["1","1","1","1","1"],
 *   ["1","0","0","1","0"]
 * ]
 * 
 * OUTPUT FORMAT:
 * - Integer representing maximum rectangle area
 * Example: 6
 * 
 * CONSTRAINTS:
 * - rows == matrix.length
 * - cols == matrix[0].length
 * - 1 <= rows, cols <= 200
 * - matrix[i][j] is '0' or '1'
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This problem is an extension of "Largest Rectangle in Histogram"!
 * 
 * KEY INSIGHT:
 * Treat each row as the base of a histogram:
 * - For each cell, if it's '1', add to height
 * - If it's '0', reset height to 0
 * - For each row, find largest rectangle in that histogram
 * 
 * VISUAL EXAMPLE:
 * ```
 * matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"],
 *   ["1","1","1","1","1"],
 *   ["1","0","0","1","0"]
 * ]
 * 
 * Build histograms row by row:
 * 
 * After row 0: heights = [1, 0, 1, 0, 0]
 *   1   1
 *   █ 0 █ 0 0
 *   Max area = 1
 * 
 * After row 1: heights = [2, 0, 2, 1, 1]
 *   2   2
 *   █   █ 1 1
 *   █ 0 █ █ █
 *   Max area = 3 (last three bars of height 1)
 * 
 * After row 2: heights = [3, 1, 3, 2, 2]
 *   3   3
 *   █ 1 █ 2 2
 *   █ █ █ █ █
 *   █ 0 █ █ █
 *   Max area = 6 (bars 2,3,4 with heights [3,2,2])
 * 
 * After row 3: heights = [4, 0, 0, 3, 0]
 *   4
 *   █     3
 *   █   0 █
 *   █ 0 0 █ 0
 *   █ 0 0 █ 0
 *   Max area = 4 (first bar)
 * 
 * Overall max = 6
 * 
 * The rectangle with area 6:
 * Row 1-2, Columns 2-4:
 *   ["1","1","1"],
 *   ["1","1","1"]
 * ```
 * 
 * DETAILED EXPLANATION:
 * For each row, we treat accumulated heights as a histogram:
 * - If matrix[i][j] == '1': height[j]++ (bar grows)
 * - If matrix[i][j] == '0': height[j] = 0 (bar resets)
 * 
 * Then we use "Largest Rectangle in Histogram" algorithm.
 * 
 * ALGORITHM:
 * 1. Initialize heights array with size cols
 * 2. For each row in matrix:
 *    a. Update heights:
 *       - If matrix[row][col] == '1': heights[col]++
 *       - If matrix[row][col] == '0': heights[col] = 0
 *    b. Calculate largest rectangle in current histogram
 *    c. Update max area
 * 3. Return max area
 * 
 * LARGEST RECTANGLE IN HISTOGRAM (Review):
 * Use monotonic stack:
 * - For each bar, find previous smaller and next smaller
 * - Width = NSE - PSE - 1
 * - Area = height * width
 * 
 * DETAILED TRACE:
 * ```
 * matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"]
 * ]
 * 
 * Initial: heights = [0, 0, 0, 0, 0]
 * 
 * Row 0:
 *   Update heights: [1, 0, 1, 0, 0]
 *   Histogram:
 *   1   1
 *   █ 0 █ 0 0
 *   Max rectangle = 1
 * 
 * Row 1:
 *   Update heights: [2, 0, 2, 1, 1]
 *   matrix[1][0]='1': heights[0] = 1+1 = 2
 *   matrix[1][1]='0': heights[1] = 0
 *   matrix[1][2]='1': heights[2] = 1+1 = 2
 *   matrix[1][3]='1': heights[3] = 0+1 = 1
 *   matrix[1][4]='1': heights[4] = 0+1 = 1
 *   
 *   Histogram:
 *   2   2
 *   █   █ 1 1
 *   █ 0 █ █ █
 *   
 *   Rectangles:
 *   - Bar 0: height=2, width=1, area=2
 *   - Bar 2: height=2, width=1, area=2
 *   - Bars 3-4: height=1, width=3, area=3 ← max
 *   
 *   Max = 3
 * 
 * Overall max = 3
 * ```
 * 
 * WHY THIS WORKS:
 * Each row represents a different "ground level" for rectangles.
 * By treating consecutive 1's as increasing histogram heights,
 * we can find all possible rectangles ending at current row.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(rows² * cols²) - Try all rectangles
 * 2. Dynamic Programming: O(rows * cols²) - For each cell, expand
 * 3. Histogram Stack: O(rows * cols) - Current approach
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(rows * cols)
 * - For each row: O(rows)
 *   - Update heights: O(cols)
 *   - Find max rectangle in histogram: O(cols)
 * - Total: O(rows * cols)
 * 
 * SPACE COMPLEXITY: O(cols)
 * - Heights array: O(cols)
 * - Stack for histogram: O(cols)
 * - Total: O(cols)
 * 
 * COMPARISON:
 * For 200x200 matrix:
 * Brute Force: ~1.6 billion operations
 * This approach: ~80,000 operations
 * Speedup: ~20,000x!
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*
import kotlin.math.max

class MaximalRectangle {
    
    /**
     * Main solution using histogram approach
     */
    fun maximalRectangle(matrix: Array<CharArray>): Int {
        if (matrix.isEmpty() || matrix[0].isEmpty()) return 0
        
        val rows = matrix.size
        val cols = matrix[0].size
        val heights = IntArray(cols)
        var maxArea = 0
        
        // Process each row
        for (row in 0 until rows) {
            // Update heights for current row
            for (col in 0 until cols) {
                if (matrix[row][col] == '1') {
                    heights[col]++
                } else {
                    heights[col] = 0
                }
            }
            
            // Find largest rectangle in current histogram
            val area = largestRectangleInHistogram(heights)
            maxArea = max(maxArea, area)
        }
        
        return maxArea
    }
    
    /**
     * Find largest rectangle in histogram using monotonic stack
     * Same algorithm as "Largest Rectangle in Histogram" problem
     */
    private fun largestRectangleInHistogram(heights: IntArray): Int {
        val stack = Stack<Int>()
        var maxArea = 0
        
        for (i in heights.indices) {
            while (stack.isNotEmpty() && heights[stack.peek()] > heights[i]) {
                val height = heights[stack.pop()]
                val width = if (stack.isEmpty()) i else i - stack.peek() - 1
                maxArea = max(maxArea, height * width)
            }
            stack.push(i)
        }
        
        // Process remaining bars
        while (stack.isNotEmpty()) {
            val height = heights[stack.pop()]
            val width = if (stack.isEmpty()) heights.size else heights.size - stack.peek() - 1
            maxArea = max(maxArea, height * width)
        }
        
        return maxArea
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Empty matrix: [] → 0
 * 2. All zeros: [['0','0'],['0','0']] → 0
 * 3. All ones: [['1','1'],['1','1']] → 4
 * 4. Single cell: [['1']] → 1
 * 5. Single row: [['1','0','1','1']] → 2
 * 6. Single column: [['1'],['1'],['0'],['1']] → 2
 * 7. No rectangle: [['1','0'],['0','1']] → 1
 * 
 * DETAILED VERIFICATION:
 * matrix = [
 *   ["1","0","1","0","0"],
 *   ["1","0","1","1","1"],
 *   ["1","1","1","1","1"],
 *   ["1","0","0","1","0"]
 * ]
 * 
 * Row 0: heights=[1,0,1,0,0], max=1
 * Row 1: heights=[2,0,2,1,1], max=3
 * Row 2: heights=[3,1,3,2,2], max=6
 *   Best rectangle: columns 2-4, heights [3,2,2]
 *   Height = min(3,2,2) = 2
 *   Width = 3
 *   Area = 2*3 = 6
 * Row 3: heights=[4,0,0,3,0], max=4
 * 
 * Overall max = 6 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = MaximalRectangle()
    
    println("Maximal Rectangle - Test Cases")
    println("================================\n")
    
    // Test 1: Standard example
    println("Test 1: Standard Example")
    val matrix1 = arrayOf(
        charArrayOf('1','0','1','0','0'),
        charArrayOf('1','0','1','1','1'),
        charArrayOf('1','1','1','1','1'),
        charArrayOf('1','0','0','1','0')
    )
    println("Matrix:")
    matrix1.forEach { println("  ${it.contentToString()}") }
    println("\nHistogram progression:")
    println("Row 0: [1,0,1,0,0]")
    println("Row 1: [2,0,2,1,1]")
    println("Row 2: [3,1,3,2,2] ← max area 6 here")
    println("Row 3: [4,0,0,3,0]")
    println("\nOutput: ${solution.maximalRectangle(matrix1)}")
    println("Expected: 6\n")
    
    // Test 2: All ones
    println("Test 2: All Ones")
    val matrix2 = arrayOf(
        charArrayOf('1','1'),
        charArrayOf('1','1')
    )
    println("Matrix:")
    matrix2.forEach { println("  ${it.contentToString()}") }
    println("Output: ${solution.maximalRectangle(matrix2)}")
    println("Expected: 4\n")
    
    // Test 3: Single row
    println("Test 3: Single Row")
    val matrix3 = arrayOf(
        charArrayOf('1','0','1','1','1')
    )
    println("Matrix:")
    matrix3.forEach { println("  ${it.contentToString()}") }
    println("Output: ${solution.maximalRectangle(matrix3)}")
    println("Expected: 3\n")
    
    // Test 4: Single column
    println("Test 4: Single Column")
    val matrix4 = arrayOf(
        charArrayOf('1'),
        charArrayOf('1'),
        charArrayOf('0'),
        charArrayOf('1'),
        charArrayOf('1')
    )
    println("Matrix:")
    matrix4.forEach { println("  ${it.contentToString()}") }
    println("Output: ${solution.maximalRectangle(matrix4)}")
    println("Expected: 2\n")
    
    // Test 5: All zeros
    println("Test 5: All Zeros")
    val matrix5 = arrayOf(
        charArrayOf('0','0'),
        charArrayOf('0','0')
    )
    println("Matrix:")
    matrix5.forEach { println("  ${it.contentToString()}") }
    println("Output: ${solution.maximalRectangle(matrix5)}")
    println("Expected: 0\n")
    
    // Test 6: Single cell
    println("Test 6: Single Cell")
    val matrix6 = arrayOf(charArrayOf('1'))
    println("Matrix: ${matrix6[0].contentToString()}")
    println("Output: ${solution.maximalRectangle(matrix6)}")
    println("Expected: 1\n")
    
    // Test 7: Complex pattern
    println("Test 7: Complex Pattern")
    val matrix7 = arrayOf(
        charArrayOf('1','1','1','1'),
        charArrayOf('1','1','1','1'),
        charArrayOf('1','1','1','0')
    )
    println("Matrix:")
    matrix7.forEach { println("  ${it.contentToString()}") }
    println("Output: ${solution.maximalRectangle(matrix7)}")
    println("Expected: 8\n")
    
    println("=== Key Insight ===")
    println("This problem combines two concepts:")
    println("1. Build histogram from each row")
    println("2. Find largest rectangle in histogram")
    println()
    println("For each row, treat accumulated 1's as histogram heights.")
    println("When we see '0', reset height to 0 (can't extend rectangle).")
    println("When we see '1', increment height (rectangle can extend up).")
    println()
    
    println("=== Histogram Transformation ===")
    println("matrix = [[1,0,1,0,0],")
    println("          [1,0,1,1,1],")
    println("          [1,1,1,1,1]]")
    println()
    println("After row 0: heights = [1,0,1,0,0]")
    println("After row 1: heights = [2,0,2,1,1]")
    println("After row 2: heights = [3,1,3,2,2]")
    println("                              ^^^^")
    println("                              Rectangle of area 6 found here!")
    println()
    
    println("=== Why It Works ===")
    println("Each row represents a potential 'base' for rectangles.")
    println("Heights tell us how many consecutive 1's above (including current).")
    println("Finding max rectangle in histogram gives max rectangle ending at this row.")
    println("By processing all rows, we find the global maximum!")
    println()
    
    println("=== Performance ===")
    println("Time: O(rows × cols)")
    println("Space: O(cols)")
    println("\nFor 200×200 matrix:")
    println("Operations: ~80,000")
    println("Brute force would need: ~1.6 billion")
    println("Speedup: ~20,000x!")
    
    println("\n=== Related Problems ===")
    println("1. Largest Rectangle in Histogram (LeetCode 84)")
    println("2. Maximal Rectangle (LeetCode 85) ← This problem")
    println("3. Maximal Square (LeetCode 221)")
}
