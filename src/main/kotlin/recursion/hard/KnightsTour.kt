/**
 * ============================================================================
 * PROBLEM: Knight's Tour
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a chessboard of size N x N and a starting position, find a sequence
 * of moves for a knight such that it visits every square exactly once.
 * A knight moves in an L-shape: 2 squares in one direction and 1 square
 * perpendicular (or vice versa).
 * 
 * INPUT FORMAT:
 * - N: Size of the chessboard (N x N)
 * - startX, startY: Starting position of the knight (0-indexed)
 * 
 * OUTPUT FORMAT:
 * - 2D array showing the order in which squares were visited
 * - board[i][j] = k means square (i,j) was the k-th move
 * - Return null if no solution exists
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 8 (larger boards take exponential time)
 * - 0 <= startX, startY < N
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use backtracking to explore all possible knight moves:
 * 1. Start from the given position and mark it as visited (move 0)
 * 2. Try all 8 possible knight moves from current position
 * 3. For each valid unvisited square, recursively continue the tour
 * 4. If we visit all N*N squares, we found a solution
 * 5. If stuck, backtrack and try a different move
 * 
 * KNIGHT MOVES:
 * From position (x, y), knight can move to 8 positions:
 *   (x+2, y+1), (x+2, y-1)
 *   (x-2, y+1), (x-2, y-1)
 *   (x+1, y+2), (x+1, y-2)
 *   (x-1, y+2), (x-1, y-2)
 * 
 * VISUAL EXAMPLE (5x5 board from (0,0)):
 * 
 *  0  59  38  33  30
 * 37  34   1  60  39
 * 58   x  36  29  32
 * 35  48  41  42  61
 * 50  57  52  47  40
 * 
 * ALGORITHM STEPS:
 * 1. Initialize board with -1 (unvisited)
 * 2. Mark starting position with 0
 * 3. For each square, try all 8 knight moves
 * 4. Check if move is valid (in bounds and unvisited)
 * 5. If valid, mark square and recurse
 * 6. If all squares visited, return true
 * 7. If stuck, unmark square (backtrack) and try next move
 * 
 * OPTIMIZATION: Warnsdorff's Heuristic
 * - Choose the square with fewest onward moves first
 * - Dramatically reduces backtracking
 * - Makes the problem solvable for larger boards
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(8^(N²))
 * - Without heuristics: exponential, up to 8^(N²) possibilities
 * - With Warnsdorff's heuristic: nearly linear in practice
 * - For N=8, naive approach is infeasible
 * 
 * SPACE COMPLEXITY: O(N²)
 * - Board storage: O(N²)
 * - Recursion stack: O(N²) in worst case
 * 
 * ============================================================================
 */

package recursion.hard

class KnightsTour {
    
    // All 8 possible knight moves
    private val moveX = intArrayOf(2, 1, -1, -2, -2, -1, 1, 2)
    private val moveY = intArrayOf(1, 2, 2, 1, -1, -2, -2, -1)
    
    /**
     * Find a knight's tour starting from (startX, startY)
     * TIME: O(8^(N²)) worst case, SPACE: O(N²)
     * 
     * @param n Board size
     * @param startX Starting row
     * @param startY Starting column
     * @return Board with move sequence, or null if no solution
     */
    fun findTour(n: Int, startX: Int = 0, startY: Int = 0): Array<IntArray>? {
        if (n < 1 || startX < 0 || startX >= n || startY < 0 || startY >= n) {
            return null
        }
        
        // Initialize board with -1 (unvisited)
        val board = Array(n) { IntArray(n) { -1 } }
        
        // Mark starting position as first move
        board[startX][startY] = 0
        
        // Try to solve using backtracking
        return if (solveKnightTour(board, startX, startY, 1, n)) {
            board
        } else {
            null
        }
    }
    
    /**
     * Recursive backtracking to find tour
     */
    private fun solveKnightTour(
        board: Array<IntArray>,
        x: Int,
        y: Int,
        moveCount: Int,
        n: Int
    ): Boolean {
        // Base case: all squares visited
        if (moveCount == n * n) {
            return true
        }
        
        // Try all 8 possible knight moves
        for (i in 0..7) {
            val nextX = x + moveX[i]
            val nextY = y + moveY[i]
            
            if (isValid(board, nextX, nextY, n)) {
                // Make move
                board[nextX][nextY] = moveCount
                
                // Recurse
                if (solveKnightTour(board, nextX, nextY, moveCount + 1, n)) {
                    return true
                }
                
                // Backtrack
                board[nextX][nextY] = -1
            }
        }
        
        return false
    }
    
    /**
     * Check if position is valid and unvisited
     */
    private fun isValid(board: Array<IntArray>, x: Int, y: Int, n: Int): Boolean {
        return x >= 0 && x < n && y >= 0 && y < n && board[x][y] == -1
    }
    
    /**
     * Find tour using Warnsdorff's heuristic (optimized)
     * Chooses moves that lead to squares with fewer onward options
     */
    fun findTourOptimized(n: Int, startX: Int = 0, startY: Int = 0): Array<IntArray>? {
        if (n < 1 || startX < 0 || startX >= n || startY < 0 || startY >= n) {
            return null
        }
        
        val board = Array(n) { IntArray(n) { -1 } }
        board[startX][startY] = 0
        
        return if (solveKnightTourHeuristic(board, startX, startY, 1, n)) {
            board
        } else {
            null
        }
    }
    
    /**
     * Solve using Warnsdorff's heuristic
     */
    private fun solveKnightTourHeuristic(
        board: Array<IntArray>,
        x: Int,
        y: Int,
        moveCount: Int,
        n: Int
    ): Boolean {
        if (moveCount == n * n) {
            return true
        }
        
        // Get all valid next moves with their accessibility counts
        val nextMoves = mutableListOf<Triple<Int, Int, Int>>()
        
        for (i in 0..7) {
            val nextX = x + moveX[i]
            val nextY = y + moveY[i]
            
            if (isValid(board, nextX, nextY, n)) {
                val accessibility = countAccessibility(board, nextX, nextY, n)
                nextMoves.add(Triple(nextX, nextY, accessibility))
            }
        }
        
        // Sort by accessibility (Warnsdorff's heuristic)
        nextMoves.sortBy { it.third }
        
        // Try moves in order of increasing accessibility
        for ((nextX, nextY, _) in nextMoves) {
            board[nextX][nextY] = moveCount
            
            if (solveKnightTourHeuristic(board, nextX, nextY, moveCount + 1, n)) {
                return true
            }
            
            board[nextX][nextY] = -1
        }
        
        return false
    }
    
    /**
     * Count number of unvisited squares reachable from (x, y)
     */
    private fun countAccessibility(board: Array<IntArray>, x: Int, y: Int, n: Int): Int {
        var count = 0
        for (i in 0..7) {
            val nextX = x + moveX[i]
            val nextY = y + moveY[i]
            if (isValid(board, nextX, nextY, n)) {
                count++
            }
        }
        return count
    }
    
    /**
     * Print the board in a readable format
     */
    fun printBoard(board: Array<IntArray>?) {
        if (board == null) {
            println("No solution exists")
            return
        }
        
        val n = board.size
        val width = (n * n).toString().length
        
        for (i in 0 until n) {
            for (j in 0 until n) {
                print("%${width}d ".format(board[i][j]))
            }
            println()
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: N = 5, start = (0, 0)
 * 
 * Initial board:
 *  0 -1 -1 -1 -1
 * -1 -1 -1 -1 -1
 * -1 -1 -1 -1 -1
 * -1 -1 -1 -1 -1
 * -1 -1 -1 -1 -1
 * 
 * After some moves (example solution):
 *  0 11 16 21  6
 * 17 20  1 10 15
 * 12 23 18  5 22
 * 19  2  7 14  9
 * 24 13  4  3  8
 * 
 * ============================================================================
 * INTERESTING FACTS
 * ============================================================================
 * 
 * - First studied by mathematician Leonhard Euler in 1759
 * - For 8x8 board, there are 26,534,728,821,064 possible tours
 * - A closed tour returns to starting square (requires even board size)
 * - Warnsdorff's heuristic was published in 1823
 * - Problem is NP-complete for general board shapes
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = KnightsTour()
    
    println("=== Knight's Tour ===\n")
    
    // Test 1: Small board 5x5
    println("Test 1: 5x5 board, starting at (0, 0)")
    val board1 = solution.findTour(5, 0, 0)
    solution.printBoard(board1)
    println()
    
    // Test 2: 6x6 board
    println("Test 2: 6x6 board, starting at (0, 0)")
    val board2 = solution.findTour(6, 0, 0)
    solution.printBoard(board2)
    println()
    
    // Test 3: Standard chessboard 8x8 (may take a while without heuristic)
    println("Test 3: 8x8 board with Warnsdorff's heuristic, starting at (0, 0)")
    val startTime = System.currentTimeMillis()
    val board3 = solution.findTourOptimized(8, 0, 0)
    val endTime = System.currentTimeMillis()
    solution.printBoard(board3)
    println("Time taken: ${endTime - startTime}ms")
    println()
    
    // Test 4: Different starting position
    println("Test 4: 5x5 board, starting at (2, 2)")
    val board4 = solution.findTour(5, 2, 2)
    solution.printBoard(board4)
    println()
    
    // Test 5: Impossible case (very small board)
    println("Test 5: 3x3 board (likely no solution)")
    val board5 = solution.findTour(3, 0, 0)
    solution.printBoard(board5)
}
