/**
 * ============================================================================
 * PROBLEM: N-Queens
 * DIFFICULTY: Hard
 * CATEGORY: Recursion - Stronghold
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard
 * such that no two queens attack each other. 
 * 
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 * Each solution contains a distinct board configuration where 'Q' represents
 * a queen and '.' represents an empty space.
 * 
 * CHESS QUEEN RULES:
 * A queen can attack: 
 * - Horizontally (same row)
 * - Vertically (same column)
 * - Diagonally (both diagonals)
 * 
 * INPUT FORMAT:
 * - An integer n representing the board size (n×n)
 * 
 * OUTPUT FORMAT:
 * - A list of board configurations (each configuration is a list of strings)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 9
 * 
 * EXAMPLES:
 * Input: n = 4
 * Output:  [
 *   [".Q..",  // Solution 1
 *    "...Q",
 *    "Q...",
 *    "..Q."],
 *   ["..Q.",  // Solution 2
 *    "Q...",
 *    "...Q",
 *    ".Q.."]
 * ]
 * 
 * Input: n = 1
 * Output: [["Q"]]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Key observation: Since no two queens can be in the same row, we can place
 * exactly ONE queen per row. This reduces the problem to: 
 * - For each row, try placing the queen in each column
 * - Check if placement is safe (doesn't attack previous queens)
 * - Recursively place queens in remaining rows
 * 
 * BACKTRACKING APPROACH:
 * 1. Start with row 0
 * 2. Try placing queen in each column (0 to n-1)
 * 3. If safe, place queen and move to next row
 * 4. If reach row n, found a solution! 
 * 5. Backtrack:  remove queen and try next column
 * 
 * SAFETY CHECK - A position (row, col) is safe if no queen attacks it:
 * - Column check: No queen in same column
 * - Diagonal check: No queen in same diagonal
 * 
 * DIAGONAL PATTERNS:
 * - Main diagonal (↘): row - col is constant
 *   Example: (0,0), (1,1), (2,2) all have row-col = 0
 * - Anti-diagonal (↙): row + col is constant
 *   Example: (0,3), (1,2), (2,1) all have row+col = 3
 * 
 * VISUAL EXAMPLE (n = 4):
 * ```
 * Try placing in row 0:
 * 
 * Q . .  .  ← Place queen at (0,0)
 * . .  .  . 
 * . . . .
 * . . . .
 * 
 * Now column 0, main diagonal (row-col=0), 
 * and anti-diagonal (row+col=0) are blocked.
 * 
 * Try row 1:
 * Q . . . 
 * . . X .   ← (1,2) is safe! 
 * . . . . 
 * . . . .
 * 
 * Continue backtracking until solution found... 
 * ```
 * 
 * OPTIMIZATION TECHNIQUES:
 * 1. Use sets to track attacked columns/diagonals (O(1) lookup)
 * 2. Only check previous rows (haven't placed queens in future rows)
 * 3. One queen per row (implicit constraint)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n!)
 * - Row 0: n choices
 * - Row 1: at most n-1 safe choices
 * - Row 2: at most n-2 safe choices
 * - ... 
 * - Total: n * (n-1) * (n-2) * ... ≈ n! 
 * - Actual complexity is lower due to pruning, but upper bound is O(n!)
 * 
 * WHY NOT O(n^n):
 * - Theoretically, we have n choices per row for n rows = n^n
 * - But safety checks prune most branches
 * - Empirically, complexity is closer to O(n!)
 * 
 * SPACE COMPLEXITY: O(n²)
 * - Board representation: O(n²)
 * - Recursion stack: O(n) depth
 * - Sets for tracking:  O(n) each
 * - Total: O(n²)
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: Backtracking with Sets (Implemented)
 * Time: O(n! ), Space: O(n²)
 * ✅ Most efficient
 * ✅ O(1) safety check using sets
 * ✅ Clean and readable
 * 
 * APPROACH 2: Backtracking with Array Check
 * Time: O(n!  * n), Space: O(n²)
 * - Check all previous queens for attacks (O(n) per check)
 * ❌ Slower due to O(n) safety checks
 * ✅ Easier to understand for beginners
 * 
 * APPROACH 3: Bit Manipulation
 * Time: O(n! ), Space: O(n)
 * - Use bits to represent attacked positions
 * ✅ More space-efficient
 * ❌ More complex, harder to understand
 * 
 * APPROACH 4: Iterative with Stack
 * Time: O(n!), Space: O(n²)
 * - Simulate recursion with explicit stack
 * ❌ More complex code
 * ✅ Avoids recursion stack overflow
 * 
 * ============================================================================
 * EDGE CASES & COMMON MISTAKES
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. n = 1: Only one solution ["Q"]
 * 2. n = 2, 3: No solutions exist (mathematically impossible)
 * 3. n = 4: First case with multiple solutions (2 solutions)
 * 
 * COMMON MISTAKES: 
 * 1. Forgetting diagonal checks → Invalid solutions
 * 2. Wrong diagonal formula → row+col vs row-col confusion
 * 3. Not backtracking properly → Previous state not restored
 * 4. Checking future rows → Unnecessary, queens not placed yet
 * 5. Using row instead of column in solution → Wrong output format
 * 
 * WHY DIAGONALS USE row±col:
 * - Main diagonal (↘): Moving down-right increases both row and col equally
 *   So row-col stays constant
 * - Anti-diagonal (↙): Moving down-left increases row but decreases col
 *   So row+col stays constant
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. N-Queens II (Hard) - Return count of solutions only
 * 2. Sudoku Solver (Hard) - Similar backtracking pattern
 * 3. Valid Sudoku (Medium) - Validation logic
 * 4. Combination Sum (Medium) - Backtracking with different constraints
 * 5. Word Search (Medium) - Backtracking on 2D grid
 * 
 * ============================================================================
 * LEARNING RESOURCES
 * ============================================================================
 * 
 * CONCEPTS DEMONSTRATED:
 * - Classic backtracking algorithm
 * - Constraint satisfaction problem (CSP)
 * - Diagonal pattern recognition
 * - Optimization with set-based lookup
 * 
 * PRACTICE PROGRESSION:
 * 1. Start with n = 4 by hand on paper
 * 2. Implement with array-based safety check first
 * 3. Optimize to set-based approach
 * 4. Try n = 8 (classic 8-queens problem)
 */

package recursion.stronghold

class NQueens {
    
    /**
     * Solves the N-Queens problem using backtracking with set optimization.
     * 
     * @param n Size of the chessboard (n×n)
     * @return List of all distinct solutions
     * 
     * Time Complexity:  O(n!)
     * Space Complexity: O(n²)
     */
    fun solveNQueens(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val board = Array(n) { CharArray(n) { '.' } }
        
        // Sets to track attacked positions (O(1) lookup)
        val cols = mutableSetOf<Int>()           // Columns with queens
        val mainDiag = mutableSetOf<Int>()       // Main diagonals (row - col)
        val antiDiag = mutableSetOf<Int>()       // Anti-diagonals (row + col)
        
        backtrack(0, board, cols, mainDiag, antiDiag, result)
        return result
    }
    
    /**
     * Helper function to perform backtracking. 
     * 
     * @param row Current row to place queen
     * @param board Current board state
     * @param cols Set of columns with queens
     * @param mainDiag Set of main diagonals with queens
     * @param antiDiag Set of anti-diagonals with queens
     * @param result List to store all solutions
     */
    private fun backtrack(
        row: Int,
        board: Array<CharArray>,
        cols: MutableSet<Int>,
        mainDiag: MutableSet<Int>,
        antiDiag: MutableSet<Int>,
        result: MutableList<List<String>>
    ) {
        val n = board.size
        
        // Base case: All queens placed successfully
        if (row == n) {
            result.add(board.map { String(it) })
            return
        }
        
        // Try placing queen in each column of current row
        for (col in 0 until n) {
            val mainDiagId = row - col
            val antiDiagId = row + col
            
            // Check if position is safe
            if (col in cols || mainDiagId in mainDiag || antiDiagId in antiDiag) {
                continue // Position is attacked, skip
            }
            
            // Place queen
            board[row][col] = 'Q'
            cols. add(col)
            mainDiag.add(mainDiagId)
            antiDiag.add(antiDiagId)
            
            // Recurse to next row
            backtrack(row + 1, board, cols, mainDiag, antiDiag, result)
            
            // Backtrack: Remove queen
            board[row][col] = '.'
            cols. remove(col)
            mainDiag.remove(mainDiagId)
            antiDiag.remove(antiDiagId)
        }
    }
    
    /**
     * Alternative approach:  Simpler version with explicit safety check.
     * Easier to understand but slower (O(n) per safety check).
     * 
     * Time Complexity: O(n!  * n)
     * Space Complexity: O(n²)
     */
    fun solveNQueensSimple(n: Int): List<List<String>> {
        val result = mutableListOf<List<String>>()
        val board = Array(n) { CharArray(n) { '.' } }
        
        fun isSafe(row: Int, col: Int): Boolean {
            // Check column
            for (i in 0 until row) {
                if (board[i][col] == 'Q') return false
            }
            
            // Check main diagonal (↖)
            var i = row - 1
            var j = col - 1
            while (i >= 0 && j >= 0) {
                if (board[i][j] == 'Q') return false
                i--
                j--
            }
            
            // Check anti-diagonal (↗)
            i = row - 1
            j = col + 1
            while (i >= 0 && j < n) {
                if (board[i][j] == 'Q') return false
                i--
                j++
            }
            
            return true
        }
        
        fun solve(row: Int) {
            if (row == n) {
                result.add(board.map { String(it) })
                return
            }
            
            for (col in 0 until n) {
                if (isSafe(row, col)) {
                    board[row][col] = 'Q'
                    solve(row + 1)
                    board[row][col] = '.'
                }
            }
        }
        
        solve(0)
        return result
    }
    
    /**
     * Returns only the count of solutions (more memory efficient).
     * Useful for N-Queens II problem variant.
     * 
     * @param n Size of the chessboard
     * @return Number of distinct solutions
     * 
     * Time Complexity:  O(n!)
     * Space Complexity: O(n)
     */
    fun totalNQueens(n: Int): Int {
        var count = 0
        val cols = mutableSetOf<Int>()
        val mainDiag = mutableSetOf<Int>()
        val antiDiag = mutableSetOf<Int>()
        
        fun backtrack(row: Int) {
            if (row == n) {
                count++
                return
            }
            
            for (col in 0 until n) {
                val mainDiagId = row - col
                val antiDiagId = row + col
                
                if (col in cols || mainDiagId in mainDiag || antiDiagId in antiDiag) {
                    continue
                }
                
                cols.add(col)
                mainDiag.add(mainDiagId)
                antiDiag.add(antiDiagId)
                
                backtrack(row + 1)
                
                cols.remove(col)
                mainDiag. remove(mainDiagId)
                antiDiag.remove(antiDiagId)
            }
        }
        
        backtrack(0)
        return count
    }
    
    /**
     * Helper function to print a board solution nicely.
     */
    fun printBoard(board:  List<String>) {
        println("Solution:")
        board.forEach { println(it) }
        println()
    }
}

fun main() {
    val solver = NQueens()
    
    // Test Case 1: n = 1
    println("Test Case 1: n = 1")
    val result1 = solver.solveNQueens(1)
    println("Expected: 1 solution")
    println("Actual:  ${result1.size} solution(s)")
    result1.forEach { solver.printBoard(it) }
    
    // Test Case 2: n = 2
    println("Test Case 2: n = 2 (No solution exists)")
    val result2 = solver.solveNQueens(2)
    println("Expected:  0 solutions")
    println("Actual: ${result2.size} solution(s)")
    println()
    
    // Test Case 3: n = 3
    println("Test Case 3: n = 3 (No solution exists)")
    val result3 = solver.solveNQueens(3)
    println("Expected: 0 solutions")
    println("Actual:  ${result3.size} solution(s)")
    println()
    
    // Test Case 4: n = 4
    println("Test Case 4: n = 4")
    val result4 = solver.solveNQueens(4)
    println("Expected: 2 solutions")
    println("Actual: ${result4.size} solution(s)")
    result4.forEach { solver.printBoard(it) }
    
    // Test Case 5: n = 5
    println("Test Case 5: n = 5")
    val result5 = solver.solveNQueens(5)
    println("Expected: 10 solutions")
    println("Actual: ${result5.size} solution(s)")
    println("First solution:")
    if (result5.isNotEmpty()) solver.printBoard(result5[0])
    
    // Test Case 6: Classic 8-queens problem
    println("Test Case 6: n = 8 (Classic 8-Queens)")
    val result8 = solver.solveNQueens(8)
    println("Expected: 92 solutions")
    println("Actual: ${result8.size} solution(s)")
    println("First solution:")
    if (result8.isNotEmpty()) solver.printBoard(result8[0])
    
    // Test Case 7: Count-only approach
    println("Test Case 7: Count-only approach for n = 8")
    val count8 = solver.totalNQueens(8)
    println("Count: $count8 (should be 92)")
    println()
    
    // Test Case 8: Testing simple approach
    println("Test Case 8: Simple approach for n = 4")
    val resultSimple = solver.solveNQueensSimple(4)
    println("Count: ${resultSimple.size} (should be 2)")
    resultSimple.forEach { solver.printBoard(it) }
    
    // Test Case 9: Solution counts for various n
    println("Test Case 9: Solution counts")
    for (n in 1..10) {
        val count = solver.totalNQueens(n)
        println("n = $n: $count solution(s)")
    }
    // Expected: 1, 0, 0, 2, 10, 4, 40, 92, 352, 724
    
    // Test Case 10: Performance test
    println("\nTest Case 10: Performance test for n = 9")
    val startTime = System.currentTimeMillis()
    val result9 = solver.solveNQueens(9)
    val endTime = System.currentTimeMillis()
    println("Count: ${result9.size} (expected:  352)")
    println("Time taken: ${endTime - startTime}ms")
}
