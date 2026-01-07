/**
 * ============================================================================
 * PROBLEM: Sudoku Solver
 * DIFFICULTY:  Hard
 * CATEGORY: Recursion - Stronghold
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 * 
 * A sudoku solution must satisfy all of the following rules:
 * 1. Each row must contain the digits 1-9 without repetition.
 * 2. Each column must contain the digits 1-9 without repetition. 
 * 3. Each of the nine 3×3 sub-boxes must contain the digits 1-9 without repetition.
 * 
 * The '.' character indicates empty cells.
 * 
 * INPUT FORMAT:
 * - A 9×9 2D array where each cell is either: 
 *   - A digit '1'-'9' (given cell)
 *   - A '.' (empty cell to fill)
 * 
 * OUTPUT FORMAT:
 * - The same 9×9 array with all empty cells filled
 * - Guaranteed to have exactly one solution
 * 
 * CONSTRAINTS:
 * - board.length == 9
 * - board[i].length == 9
 * - board[i][j] is a digit '1'-'9' or '.'
 * - It is guaranteed that the input board has only one solution
 * 
 * EXAMPLE:
 * Input: 
 * [["5","3",".",".","7",".",".",".","."],
 *  ["6",".",". ","1","9","5",". ",".","."],
 *  [". ","9","8",".",".",".",".","6","."],
 *  ["8",".",".",".","6",".",".",". ","3"],
 *  ["4",".",".","8",". ","3",".",".","1"],
 *  ["7",". ",".",".","2",".",".",".","6"],
 *  [".","6",".",". ",".",".","2","8","."],
 *  [". ",".",".","4","1","9",".",".","5"],
 *  [".",".",".",".","8",".",".","7","9"]]
 * 
 * Output:
 * [["5","3","4","6","7","8","9","1","2"],
 *  ["6","7","2","1","9","5","3","4","8"],
 *  ["1","9","8","3","4","2","5","6","7"],
 *  ["8","5","9","7","6","1","4","2","3"],
 *  ["4","2","6","8","5","3","7","9","1"],
 *  ["7","1","3","9","2","4","8","5","6"],
 *  ["9","6","1","5","3","7","2","8","4"],
 *  ["2","8","7","4","1","9","6","3","5"],
 *  ["3","4","5","2","8","6","1","7","9"]]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * Sudoku is a classic Constraint Satisfaction Problem (CSP). We need to:
 * 1. Find an empty cell
 * 2. Try placing digits 1-9
 * 3. Check if placement is valid (satisfies all constraints)
 * 4. If valid, recursively solve remaining cells
 * 5. If stuck, backtrack and try different digit
 * 
 * KEY INSIGHT:
 * This is similar to N-Queens but with more complex constraints: 
 * - Row constraint (like N-Queens row)
 * - Column constraint (like N-Queens column)
 * - 3×3 box constraint (unique to Sudoku)
 * 
 * ALGORITHM STEPS:
 * 1. Find next empty cell (marked with '.')
 * 2. If no empty cell, puzzle solved!  Return true
 * 3. For each digit 1-9:
 *    a. Check if digit is valid at current position
 *    b.  If valid, place digit
 *    c. Recursively solve rest of board
 *    d. If successful, return true
 *    e. If stuck, remove digit (backtrack) and try next
 * 4. If no digit works, return false (trigger backtracking)
 * 
 * VALIDITY CHECK - A digit is valid at (row, col) if:
 * - Not already in the same row
 * - Not already in the same column
 * - Not already in the same 3×3 box
 * 
 * 3×3 BOX CALCULATION:
 * - Box row index: row / 3
 * - Box column index: col / 3
 * - Starting position of box: (boxRow * 3, boxCol * 3)
 * 
 * VISUAL EXAMPLE (3×3 Box Indices):
 * ```
 * Board:              Box Indices:
 * 0 1 2 | 3 4 5 | 6 7 8     0 0 0 | 0 0 0 | 0 0 0
 * 0 1 2 | 3 4 5 | 6 7 8     0 0 0 | 0 0 0 | 0 0 0
 * 0 1 2 | 3 4 5 | 6 7 8     0 0 0 | 0 0 0 | 0 0 0
 * ------+-------+------     ------+-------+------
 * 0 1 2 | 3 4 5 | 6 7 8     1 1 1 | 1 1 1 | 1 1 1
 * 0 1 2 | 3 4 5 | 6 7 8     1 1 1 | 1 1 1 | 1 1 1
 * 0 1 2 | 3 4 5 | 6 7 8     1 1 1 | 1 1 1 | 1 1 1
 * ------+-------+------     ------+-------+------
 * 0 1 2 | 3 4 5 | 6 7 8     2 2 2 | 2 2 2 | 2 2 2
 * 0 1 2 | 3 4 5 | 6 7 8     2 2 2 | 2 2 2 | 2 2 2
 * 0 1 2 | 3 4 5 | 6 7 8     2 2 2 | 2 2 2 | 2 2 2
 * 
 * For cell (4,5):
 * - Box row: 4/3 = 1
 * - Box col: 5/3 = 1
 * - Box start: (3,3)
 * ```
 * 
 * BACKTRACKING DECISION TREE:
 * ```
 *                    [Empty board]
 *                         |
 *                  [Try 1-9 in first empty cell]
 *                    /    |    \
 *              [digit 1] [2]  [3]... 
 *                   |
 *          [If valid, move to next empty]
 *                   |
 *          [Try 1-9 in next empty]
 *                  / \
 *             [valid] [invalid]
 *                |        |
 *           [continue] [backtrack]
 * ```
 * 
 * OPTIMIZATION TECHNIQUES:
 * 1. **Early Pruning**: Check validity before recursion
 * 2. **Smart Cell Selection**: Choose cell with fewest possibilities
 * 3. **Constraint Propagation**: Use sets to track used digits
 * 4. **Box Indexing**: Fast box lookup with formula
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(9^m) where m = number of empty cells
 * - Worst case: Try 9 digits for each empty cell
 * - With pruning and constraints, much better in practice
 * - For typical puzzles: O(1) due to constant board size
 * 
 * WHY 9^m (theoretical worst):
 * - Each empty cell:  up to 9 choices
 * - m empty cells: 9 × 9 × 9 × ... (m times)
 * - Constraints significantly prune search space
 * 
 * ACTUAL COMPLEXITY (practical):
 * - Well-formed puzzles: O(1) - constant 9×9 board
 * - Most branches pruned by validity checks
 * - Typical solve time: milliseconds
 * 
 * SPACE COMPLEXITY: O(1)
 * - Board is 9×9: O(81) = O(1) constant
 * - Recursion depth: at most 81 levels (one per cell)
 * - No additional data structures that scale with input
 * - In-place modification of board
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: Backtracking (Implemented)
 * Time: O(9^m), Space: O(1)
 * ✅ Simple and intuitive
 * ✅ Guaranteed to find solution
 * ✅ In-place modification
 * ❌ Can be slow for difficult puzzles
 * 
 * APPROACH 2: Backtracking with Sets
 * Time: O(9^m), Space: O(1)
 * ✅ Faster validity checks (O(1) instead of O(9))
 * ✅ Tracks used digits per row/col/box
 * ❌ More memory overhead
 * 
 * APPROACH 3: Constraint Propagation (Advanced)
 * Time: O(1) for most puzzles, Space: O(1)
 * ✅ Much faster - eliminates possibilities
 * ✅ Used by human solvers
 * ❌ Complex implementation
 * 
 * APPROACH 4: Dancing Links (DLX Algorithm)
 * Time: O(1) average, Space: O(1)
 * ✅ Extremely fast
 * ✅ Optimal for hard puzzles
 * ❌ Very complex
 * ❌ Overkill for standard Sudoku
 * 
 * ============================================================================
 * EDGE CASES & COMMON MISTAKES
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Board with only one empty cell
 * 2. Board that's already complete
 * 3. Board with many empty cells (harder)
 * 4. Board with invalid initial state (not tested, problem guarantees valid)
 * 
 * COMMON MISTAKES:
 * 1. Wrong box calculation → (row/3)*3 + col/3 is WRONG
 * 2. Not backtracking properly → Must reset cell to '.'
 * 3. Checking entire board → Only need to check row/col/box
 * 4. Using integer instead of char → Board uses '1'-'9', not 1-9
 * 5. Not converting char to int → '5' - '1' = 4 (wrong), need '5' - '0' = 5
 * 6. Forgetting to return false → Must signal failure to trigger backtrack
 * 7. Modifying loop variable → Changes digit during iteration
 * 
 * BOX INDEX FORMULA (CRITICAL):
 * ❌ WRONG: (row / 3) * 3 + (col / 3) 
 *    - This gives box number, not starting position
 * ✅ CORRECT: 
 *    - Box start row: (row / 3) * 3
 *    - Box start col: (col / 3) * 3
 *    - Check cells: [startRow.. startRow+2, startCol.. startCol+2]
 * 
 * CHAR vs INT (CRITICAL):
 * - Board uses Char:  '1', '2', .. ., '9', '.'
 * - To check digit: char == '1' not char == 1
 * - To convert:  char. digitToInt() or char - '0'
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Valid Sudoku (Medium) - Check if board is valid
 * 2. N-Queens (Hard) - Similar backtracking pattern
 * 3. Word Search (Medium) - Backtracking on 2D grid
 * 4. Combination Sum (Medium) - Backtracking with numbers
 * 5. Palindrome Partitioning (Medium) - Backtracking on strings
 * 6. Regular Expression Matching (Hard) - Complex backtracking
 * 
 * ============================================================================
 * LEARNING RESOURCES
 * ============================================================================
 * 
 * CONCEPTS DEMONSTRATED:
 * - Advanced backtracking algorithm
 * - Constraint Satisfaction Problem (CSP)
 * - Multi-dimensional constraint checking
 * - In-place board modification
 * - Box/grid indexing mathematics
 * 
 * PRACTICE PROGRESSION:
 * 1. Solve "Valid Sudoku" first (checking logic)
 * 2. Implement basic backtracking on smaller grid (4×4)
 * 3. Extend to full 9×9 Sudoku
 * 4. Optimize with sets for O(1) checks
 * 5. Advanced:  Implement constraint propagation
 * 
 * SUDOKU SOLVING TECHNIQUES (Human Strategies):
 * 1. Naked Singles:  Cell has only one possibility
 * 2. Hidden Singles:  Digit has only one place in row/col/box
 * 3. Naked Pairs/Triples: Eliminate possibilities
 * 4. X-Wing, Swordfish:  Advanced pattern recognition
 * 5. Backtracking: Last resort when logic fails
 * 
 * MATHEMATICAL INSIGHT:
 * - Valid Sudoku grids:  ~6. 67 × 10^21 (6. 67 sextillion)
 * - Essentially different grids:  5,472,730,538 (after symmetries)
 * - Minimum clues for unique solution: 17
 * - Most puzzles:  25-30 clues
 */

package recursion.stronghold

class SudokuSolver {
    
    companion object {
        const val BOARD_SIZE = 9
        const val BOX_SIZE = 3
        const val EMPTY = '.'
    }
    
    /**
     * Solves a Sudoku puzzle using backtracking. 
     * Modifies the board in-place. 
     * 
     * @param board 9×9 Sudoku board
     * 
     * Time Complexity: O(9^m) where m = number of empty cells
     * Space Complexity: O(1)
     */
    fun solveSudoku(board: Array<CharArray>) {
        solve(board)
    }
    
    /**
     * Recursive helper function to solve the board.
     * 
     * @param board Current board state
     * @return True if solution found, false if need to backtrack
     */
    private fun solve(board: Array<CharArray>): Boolean {
        // Find next empty cell
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                if (board[row][col] == EMPTY) {
                    // Try digits 1-9
                    for (digit in '1'. .'9') {
                        if (isValid(board, row, col, digit)) {
                            // Place digit
                            board[row][col] = digit
                            
                            // Recursively solve rest of board
                            if (solve(board)) {
                                return true // Solution found! 
                            }
                            
                            // Backtrack:  remove digit
                            board[row][col] = EMPTY
                        }
                    }
                    
                    // No valid digit found, trigger backtracking
                    return false
                }
            }
        }
        
        // No empty cells left - puzzle solved! 
        return true
    }
    
    /**
     * Checks if placing a digit at (row, col) is valid.
     * 
     * @param board Current board state
     * @param row Target row
     * @param col Target column
     * @param digit Digit to place ('1'-'9')
     * @return True if placement is valid
     * 
     * Time Complexity:  O(1) - constant checks (9+9+9)
     */
    private fun isValid(
        board: Array<CharArray>,
        row: Int,
        col: Int,
        digit: Char
    ): Boolean {
        // Check row
        for (c in 0 until BOARD_SIZE) {
            if (board[row][c] == digit) {
                return false
            }
        }
        
        // Check column
        for (r in 0 until BOARD_SIZE) {
            if (board[r][col] == digit) {
                return false
            }
        }
        
        // Check 3×3 box
        val boxStartRow = (row / BOX_SIZE) * BOX_SIZE
        val boxStartCol = (col / BOX_SIZE) * BOX_SIZE
        
        for (r in boxStartRow until boxStartRow + BOX_SIZE) {
            for (c in boxStartCol until boxStartCol + BOX_SIZE) {
                if (board[r][c] == digit) {
                    return false
                }
            }
        }
        
        return true
    }
    
    /**
     * Alternative approach: Backtracking with sets for O(1) validity checks.
     * Tracks used digits in each row, column, and box.
     * 
     * Time Complexity: O(9^m)
     * Space Complexity: O(1) - constant size sets
     */
    fun solveSudokuOptimized(board: Array<CharArray>) {
        // Initialize sets to track used digits
        val rows = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        val cols = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        val boxes = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        
        // Populate sets with existing digits
        for (r in 0 until BOARD_SIZE) {
            for (c in 0 until BOARD_SIZE) {
                if (board[r][c] != EMPTY) {
                    val digit = board[r][c]
                    val boxIndex = (r / BOX_SIZE) * BOX_SIZE + (c / BOX_SIZE)
                    rows[r].add(digit)
                    cols[c].add(digit)
                    boxes[boxIndex].add(digit)
                }
            }
        }
        
        fun solveOptimized(): Boolean {
            for (r in 0 until BOARD_SIZE) {
                for (c in 0 until BOARD_SIZE) {
                    if (board[r][c] == EMPTY) {
                        val boxIndex = (r / BOX_SIZE) * BOX_SIZE + (c / BOX_SIZE)
                        
                        for (digit in '1'..'9') {
                            // O(1) validity check using sets
                            if (digit !in rows[r] && 
                                digit !in cols[c] && 
                                digit !in boxes[boxIndex]) {
                                
                                // Place digit
                                board[r][c] = digit
                                rows[r].add(digit)
                                cols[c].add(digit)
                                boxes[boxIndex].add(digit)
                                
                                if (solveOptimized()) {
                                    return true
                                }
                                
                                // Backtrack
                                board[r][c] = EMPTY
                                rows[r].remove(digit)
                                cols[c].remove(digit)
                                boxes[boxIndex].remove(digit)
                            }
                        }
                        
                        return false
                    }
                }
            }
            return true
        }
        
        solveOptimized()
    }
    
    /**
     * Validates if a board is a valid Sudoku (doesn't solve it).
     * Useful for testing and understanding constraints.
     * 
     * @param board 9×9 Sudoku board
     * @return True if board is valid
     * 
     * Time Complexity: O(1) - constant 81 cells
     * Space Complexity: O(1)
     */
    fun isValidSudoku(board: Array<CharArray>): Boolean {
        val rows = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        val cols = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        val boxes = Array(BOARD_SIZE) { mutableSetOf<Char>() }
        
        for (r in 0 until BOARD_SIZE) {
            for (c in 0 until BOARD_SIZE) {
                val digit = board[r][c]
                if (digit == EMPTY) continue
                
                val boxIndex = (r / BOX_SIZE) * BOX_SIZE + (c / BOX_SIZE)
                
                if (digit in rows[r] || 
                    digit in cols[c] || 
                    digit in boxes[boxIndex]) {
                    return false
                }
                
                rows[r].add(digit)
                cols[c]. add(digit)
                boxes[boxIndex].add(digit)
            }
        }
        
        return true
    }
    
    /**
     * Prints the board in a readable format.
     */
    fun printBoard(board:  Array<CharArray>) {
        println("╔═══════╦═══════╦═══════╗")
        for (r in 0 until BOARD_SIZE) {
            if (r > 0 && r % BOX_SIZE == 0) {
                println("╠═══════╬═══════╬═══════╣")
            }
            print("║ ")
            for (c in 0 until BOARD_SIZE) {
                print(board[r][c])
                if ((c + 1) % BOX_SIZE == 0) {
                    print(" ║ ")
                } else {
                    print(" ")
                }
            }
            println()
        }
        println("╚═══════╩═══════╩═══════╝")
    }
    
    /**
     * Counts the number of empty cells in the board.
     * Useful for complexity analysis.
     */
    fun countEmptyCells(board: Array<CharArray>): Int {
        return board.sumOf { row -> row.count { it == EMPTY } }
    }
}

fun main() {
    val solver = SudokuSolver()
    
    // Test Case 1: Classic Sudoku puzzle
    println("Test Case 1: Classic Sudoku")
    val board1 = arrayOf(
        charArrayOf('5','3','.','.','7','.','.','.','.'),
        charArrayOf('6','.','.','1','9','5','.','.','.'),
        charArrayOf('. ','9','8','.','.','.','.','6','.'),
        charArrayOf('8','.','.','.','6','.','.','.','3'),
        charArrayOf('4','.','.','8','.','3','.','.','1'),
        charArrayOf('7','.','.','.','2','.','.','. ','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('. ','.','.','4','1','9','.','.','5'),
        charArrayOf('.','.','.','.','8','.','.','7','9')
    )
    
    println("Before solving:")
    solver.printBoard(board1)
    println("Empty cells: ${solver.countEmptyCells(board1)}")
    println("Is valid: ${solver.isValidSudoku(board1)}")
    println()
    
    val startTime1 = System.currentTimeMillis()
    solver.solveSudoku(board1)
    val endTime1 = System. currentTimeMillis()
    
    println("After solving:")
    solver.printBoard(board1)
    println("Is valid: ${solver.isValidSudoku(board1)}")
    println("Time taken: ${endTime1 - startTime1}ms")
    println()
    
    // Test Case 2: Easy puzzle (more clues)
    println("Test Case 2: Easy puzzle")
    val board2 = arrayOf(
        charArrayOf('5','3','.','.','7','.','.','.','.'),
        charArrayOf('6','.','. ','1','9','5','. ','.','.'),
        charArrayOf('.','9','8','.','.','.','.','6','. '),
        charArrayOf('8','.','.','.','6','.','.','.','3'),
        charArrayOf('4','.','.','8','.','3','.','.','1'),
        charArrayOf('7','.','.','.','2','.','.','. ','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('.','.','.','4','1','9','.','.','5'),
        charArrayOf('.','.','.','. ','8','.','.','7','9')
    )
    
    val startTime2 = System.currentTimeMillis()
    solver.solveSudokuOptimized(board2)
    val endTime2 = System.currentTimeMillis()
    
    println("Solved with optimized approach:")
    solver.printBoard(board2)
    println("Time taken: ${endTime2 - startTime2}ms")
    println()
    
    // Test Case 3: Hard puzzle (fewer clues)
    println("Test Case 3: Hard puzzle")
    val board3 = arrayOf(
        charArrayOf('. ','.','.','.','.','.','.','.','.'),
        charArrayOf('.','.','.','.','.','3','.','8','5'),
        charArrayOf('.','.','.','.','.','1','.','2','.'),
        charArrayOf('. ','.','.','.','.','.','.','.','.'),
        charArrayOf('.','.','.','.','.','.','.','.','.'),
        charArrayOf('.','8','.','.','.','5','.','7','9'),
        charArrayOf('.','.','.','.','.','.','.','.','.'),
        charArrayOf('.','.','.','.','.','9','.','.','.'),
        charArrayOf('2','.','.','.','.','. ','.','.','.')
    )
    
    println("Before solving:")
    solver.printBoard(board3)
    println("Empty cells: ${solver. countEmptyCells(board3)}")
    
    val startTime3 = System.currentTimeMillis()
    solver.solveSudoku(board3)
    val endTime3 = System.currentTimeMillis()
    
    println("After solving:")
    solver.printBoard(board3)
    println("Time taken: ${endTime3 - startTime3}ms")
    println()
    
    // Test Case 4: Nearly complete puzzle
    println("Test Case 4: Nearly complete (1 empty cell)")
    val board4 = arrayOf(
        charArrayOf('5','3','4','6','7','8','9','1','2'),
        charArrayOf('6','7','2','1','9','5','3','4','8'),
        charArrayOf('1','9','8','3','4','2','5','6','7'),
        charArrayOf('8','5','9','7','6','1','4','2','3'),
        charArrayOf('4','2','6','8','5','3','7','9','1'),
        charArrayOf('7','1','3','9','2','4','8','5','6'),
        charArrayOf('9','6','1','5','3','7','2','8','4'),
        charArrayOf('2','8','7','4','1','9','6','3','5'),
        charArrayOf('3','4','5','2','8','6','.','7','9')
    )
    
    println("Before solving:")
    solver.printBoard(board4)
    
    solver.solveSudoku(board4)
    
    println("After solving:")
    solver.printBoard(board4)
    println()
    
    // Test Case 5: Minimal clues puzzle (17 clues - theoretical minimum)
    println("Test Case 5: Minimal clues")
    val board5 = arrayOf(
        charArrayOf('.','.','.','.','.','.','.','.','1'),
        charArrayOf('.','.','2','.','.','.','.','.','3'),
        charArrayOf('. ','4','.','.','.','5','.','.','.'),
        charArrayOf('.','.','.','1','.','.','.','6','.'),
        charArrayOf('.','.','.','.','.','. ','2','.','.'),
        charArrayOf('. ','.','.','.','.','3','.','.','.'),
        charArrayOf('5','.','.','.','.','.','. ','.','.'),
        charArrayOf('.','.','.','.','. ','.','.','7','.'),
        charArrayOf('. ','.','.','.','.','.','.','.','.') 
    )
    
    println("Empty cells: ${solver.countEmptyCells(board5)}")
    solver.printBoard(board5)
    
    val startTime5 = System.currentTimeMillis()
    solver.solveSudoku(board5)
    val endTime5 = System.currentTimeMillis()
    
    println("After solving:")
    solver.printBoard(board5)
    println("Time taken: ${endTime5 - startTime5}ms")
    println()
    
    // Test Case 6: Validation test
    println("Test Case 6: Validation tests")
    val validBoard = arrayOf(
        charArrayOf('5','3','.','. ','7','.','.','.','. '),
        charArrayOf('6','.','.','1','9','5','.','.','.'),
        charArrayOf('.','9','8','.','.','.','.','6','.'),
        charArrayOf('8','.','.','. ','6','.','.','.','3'),
        charArrayOf('4','.','.','8','. ','3','.','.','1'),
        charArrayOf('7','.','.','.','2','. ','.','.','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('.','.','.','4','1','9','.','.','5'),
        charArrayOf('.','. ','.','.','8','.','. ','7','9')
    )
    println("Valid board: ${solver.isValidSudoku(validBoard)}")
    
    val invalidBoard = arrayOf(
        charArrayOf('5','5','.','.','7','.','. ','.','.'), // Two 5s in first row
        charArrayOf('6','.','.','1','9','5','.','.','.'),
        charArrayOf('.','9','8','.','.','.','. ','6','.'),
        charArrayOf('8','.','.','.','6','.','.','. ','3'),
        charArrayOf('4','.','.','8','.','3','.','.','1'),
        charArrayOf('7','.','.','.','2','.','.','.','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('.','.','.','4','1','9','.','.','5'),
        charArrayOf('.','.','.','.','8','.','.','7','9')
    )
    println("Invalid board:  ${solver.isValidSudoku(invalidBoard)}")
    println()
    
    // Test Case 7: Performance comparison
    println("Test Case 7: Performance comparison")
    val testBoard1 = arrayOf(
        charArrayOf('5','3','.','.','7','.','.','.','.'),
        charArrayOf('6','.','.','1','9','5','.','.','.'),
        charArrayOf('.','9','8','.','.','.','. ','6','.'),
        charArrayOf('8','.','.','.','6','.','.','. ','3'),
        charArrayOf('4','.','.','8','.','3','.','.','1'),
        charArrayOf('7','.','.','.','2','.','.','.','6'),
        charArrayOf('.','6','.','.','.','.','2','8','.'),
        charArrayOf('.','.','.','4','1','9','.','.','5'),
        charArrayOf('.','.','.','.','8','.','.','7','9')
    )
    
    val testBoard2 = testBoard1.map { it.clone() }.toTypedArray()
    
    val time1 = System.currentTimeMillis()
    solver.solveSudoku(testBoard1)
    val time2 = System.currentTimeMillis()
    
    val time3 = System.currentTimeMillis()
    solver.solveSudokuOptimized(testBoard2)
    val time4 = System.currentTimeMillis()
    
    println("Basic backtracking:  ${time2 - time1}ms")
    println("Optimized with sets: ${time4 - time3}ms")
    println()
    
    // Test Case 8: Box index verification
    println("Test Case 8: Box index verification")
    println("Testing 3×3 box calculations:")
    for (r in listOf(0, 4, 8)) {
        for (c in listOf(0, 4, 8)) {
            val boxRow = (r / 3) * 3
            val boxCol = (c / 3) * 3
            val boxIndex = (r / 3) * 3 + (c / 3)
            println("Cell ($r,$c) → Box starts at ($boxRow,$boxCol), Box index: $boxIndex")
        }
    }
}
