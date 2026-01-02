/**
 * ============================================================================
 * PROBLEM: Pascal's Triangle
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Mathematics, Dynamic Programming
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement three variants of Pascal's Triangle:
 * 1. Generate the entire triangle with n rows
 * 2. Generate a specific row (0-indexed)
 * 3. Get a specific element at row r, column c
 * 
 * Pascal's Triangle properties:
 * - Each number is the sum of the two numbers above it
 * - First and last elements of each row are 1
 * - Row 0: [1], Row 1: [1,1], Row 2: [1,2,1], etc.
 * 
 * INPUT FORMAT:
 * - Variant 1: numRows = 5
 * - Variant 2: rowIndex = 3
 * - Variant 3: row = 4, col = 2
 * 
 * OUTPUT FORMAT:
 * - Variant 1: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]
 * - Variant 2: [1,3,3,1]
 * - Variant 3: 6 (element at position [4,2])
 * 
 * CONSTRAINTS:
 * - 1 <= numRows <= 30
 * - 0 <= rowIndex <= 33
 * - 0 <= row, col <= 30
 * - col <= row
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Each element can be computed using:
 * - DP: triangle[i][j] = triangle[i-1][j-1] + triangle[i-1][j]
 * - Formula: C(n,r) = n! / (r! * (n-r)!)
 * - Optimized: Use previous value in row to compute next
 * 
 * KEY INSIGHT:
 * For row n: element at position k = C(n,k) = n! / (k! * (n-k)!)
 * Can compute iteratively: C(n,k+1) = C(n,k) * (n-k) / (k+1)
 * 
 * VISUAL EXAMPLE:
 * Pascal's Triangle (5 rows):
 * 
 *         1           row 0
 *       1   1         row 1
 *     1   2   1       row 2
 *   1   3   3   1     row 3
 * 1   4   6   4   1   row 4
 * 
 * Each element = sum of two above:
 * triangle[2][1] = triangle[1][0] + triangle[1][1] = 1 + 1 = 2
 * triangle[3][2] = triangle[2][1] + triangle[2][2] = 2 + 1 = 3
 * 
 * ALGORITHM VARIANTS:
 * 
 * Variant 1 (Full Triangle):
 * 1. Create 2D list
 * 2. First row = [1]
 * 3. For each subsequent row:
 *    - Start with [1]
 *    - Add sums of adjacent elements from previous row
 *    - End with [1]
 * 
 * Variant 2 (Specific Row):
 * 1. Use single array, update in-place
 * 2. Or use combination formula C(n,k)
 * 
 * Variant 3 (Specific Element):
 * Use formula: C(n,k) computed efficiently
 * 
 * COMPLEXITY:
 * Variant 1: Time O(n²), Space O(n²) - full triangle
 * Variant 2: Time O(n), Space O(n) - single row
 * Variant 3: Time O(min(k, n-k)), Space O(1) - single element
 * 
 * ============================================================================
 */

package arrays.hard

class PascalTriangle {
    
    /**
     * Variant 1: Generates entire Pascal's Triangle with numRows
     * 
     * @param numRows Number of rows to generate
     * @return Complete Pascal's Triangle
     */
    fun generate(numRows: Int): List<List<Int>> {
        val triangle = mutableListOf<List<Int>>()
        
        if (numRows <= 0) return triangle
        
        // First row
        triangle.add(listOf(1))
        
        for (i in 1 until numRows) {
            val prevRow = triangle[i - 1]
            val currentRow = mutableListOf<Int>()
            
            // First element is always 1
            currentRow.add(1)
            
            // Middle elements
            for (j in 1 until i) {
                currentRow.add(prevRow[j - 1] + prevRow[j])
            }
            
            // Last element is always 1
            currentRow.add(1)
            
            triangle.add(currentRow)
        }
        
        return triangle
    }
    
    /**
     * Variant 2: Generates a specific row of Pascal's Triangle
     * 
     * @param rowIndex 0-indexed row number
     * @return The specified row
     */
    fun getRow(rowIndex: Int): List<Int> {
        val row = MutableList(rowIndex + 1) { 1 }
        
        for (i in 2..rowIndex) {
            // Update from right to left to avoid overwriting needed values
            for (j in i - 1 downTo 1) {
                row[j] += row[j - 1]
            }
        }
        
        return row
    }
    
    /**
     * Variant 2 Alternative: Using combination formula
     */
    fun getRowFormula(rowIndex: Int): List<Int> {
        val row = mutableListOf<Int>()
        
        for (k in 0..rowIndex) {
            row.add(calculateCombination(rowIndex, k))
        }
        
        return row
    }
    
    /**
     * Variant 3: Gets specific element at row r, column c
     * 
     * @param row Row index (0-indexed)
     * @param col Column index (0-indexed)
     * @return Value at position [row][col]
     */
    fun getElement(row: Int, col: Int): Int {
        if (col > row || col < 0 || row < 0) return 0
        return calculateCombination(row, col)
    }
    
    /**
     * Helper: Calculates C(n, k) = n! / (k! * (n-k)!)
     * Optimized to avoid overflow
     */
    private fun calculateCombination(n: Int, k: Int): Int {
        var k = k
        if (k > n - k) k = n - k  // Optimize: C(n,k) = C(n,n-k)
        
        var result = 1L
        for (i in 0 until k) {
            result = result * (n - i) / (i + 1)
        }
        
        return result.toInt()
    }
    
    /**
     * Pretty print Pascal's Triangle
     */
    fun printTriangle(numRows: Int) {
        val triangle = generate(numRows)
        
        for (row in triangle) {
            val spaces = " ".repeat((numRows - row.size) * 2)
            print(spaces)
            println(row.joinToString("   "))
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * VARIANT 1: Generate full triangle (n=5)
 * 
 * Row 0: [1]
 * Row 1: [1, 1]
 * Row 2: [1, 1+1, 1] = [1, 2, 1]
 * Row 3: [1, 1+2, 2+1, 1] = [1, 3, 3, 1]
 * Row 4: [1, 1+3, 3+3, 3+1, 1] = [1, 4, 6, 4, 1]
 * 
 * VARIANT 2: Get row 4
 * Start: [1, 1, 1, 1, 1]
 * After i=2: [1, 2, 1, 1, 1]
 * After i=3: [1, 3, 3, 1, 1]
 * After i=4: [1, 4, 6, 4, 1]
 * 
 * VARIANT 3: Get element at [4, 2]
 * C(4,2) = 4!/(2!*2!) = 24/(2*2) = 6
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single row: n=1 → [[1]]
 * 2. Two rows: n=2 → [[1], [1,1]]
 * 3. Row 0: [[1]]
 * 4. Get row 0: [1]
 * 5. Element at [0,0]: 1
 * 6. Element at [n,0]: 1 (first element always 1)
 * 7. Element at [n,n]: 1 (last element always 1)
 * 
 * APPLICATIONS:
 * 1. Binomial coefficients in probability
 * 2. Combinatorics problems
 * 3. Polynomial expansion (binomial theorem)
 * 4. Graph theory (paths counting)
 * 5. Dynamic programming base cases
 * 6. Number theory
 * 7. Computer graphics (Bezier curves)
 * 
 * ============================================================================
 * MATHEMATICAL PROPERTIES
 * ============================================================================
 * 
 * 1. Sum of row n = 2^n
 * 2. Each row is symmetric
 * 3. Element at [n,k] = C(n,k) = n!/(k!(n-k)!)
 * 4. Sum of diagonal = Fibonacci numbers
 * 5. Each number is sum of two numbers above it
 * 6. Row n has n+1 elements
 * 7. Used in binomial expansion: (a+b)^n
 * 
 * ============================================================================
 */

fun main() {
    val solution = PascalTriangle()
    
    println("=== Pascal's Triangle Tests ===\n")
    
    // Test 1: Generate full triangle
    println("Test 1: Generate 5 rows")
    val triangle1 = solution.generate(5)
    println("Result:")
    for (row in triangle1) {
        println(row)
    }
    println("Expected:")
    println("[1]")
    println("[1, 1]")
    println("[1, 2, 1]")
    println("[1, 3, 3, 1]")
    println("[1, 4, 6, 4, 1]\n")
    
    // Test 2: Get specific row
    println("Test 2: Get row 4")
    val row2 = solution.getRow(4)
    println("Result: $row2")
    println("Expected: [1, 4, 6, 4, 1]\n")
    
    // Test 3: Get row using formula
    println("Test 3: Get row 3 using formula")
    val row3 = solution.getRowFormula(3)
    println("Result: $row3")
    println("Expected: [1, 3, 3, 1]\n")
    
    // Test 4: Get specific element
    println("Test 4: Get element at [4, 2]")
    val element4 = solution.getElement(4, 2)
    println("Result: $element4")
    println("Expected: 6\n")
    
    // Test 5: Edge cases
    println("Test 5: Edge cases")
    println("Row 0: ${solution.getRow(0)}")
    println("Element [0,0]: ${solution.getElement(0, 0)}")
    println("Element [5,0]: ${solution.getElement(5, 0)}")
    println("Element [5,5]: ${solution.getElement(5, 5)}")
    println()
    
    // Test 6: Pretty print
    println("Test 6: Pretty printed triangle (7 rows)")
    solution.printTriangle(7)
    
    println("\nAll tests completed!")
}
