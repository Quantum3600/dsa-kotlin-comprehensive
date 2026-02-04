package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Word Search
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences (Backtracking on 2D Grid)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an m x n grid of characters board and a string word, return true if
 * word exists in the grid.
 * 
 * The word can be constructed from letters of sequentially adjacent cells,
 * where adjacent cells are horizontally or vertically neighboring. The same
 * letter cell may not be used more than once.
 * 
 * INPUT FORMAT:
 * - board: 2D character array
 * - word: String to search
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if word exists, false otherwise
 * 
 * CONSTRAINTS:
 * - m == board.length
 * - n == board[i].length
 * - 1 <= m, n <= 6
 * - 1 <= word.length <= 15
 * - board and word consist of only lowercase and uppercase English letters
 * 
 * EXAMPLES:
 * Input: 
 * board = [['A','B','C','E'],
 *          ['S','F','C','S'],
 *          ['A','D','E','E']]
 * word = "ABCCED"
 * Output: true
 * 
 * Input: 
 * board = [['A','B','C','E'],
 *          ['S','F','C','S'],
 *          ['A','D','E','E']]
 * word = "SEE"
 * Output: true
 * 
 * Input:
 * board = [['A','B','C','E'],
 *          ['S','F','C','S'],
 *          ['A','D','E','E']]
 * word = "ABCB"
 * Output: false
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a 2D backtracking problem. We need to:
 * 1. Find the starting character of the word in the grid
 * 2. From that position, explore all 4 directions (up, down, left, right)
 * 3. Match each character of the word sequentially
 * 4. Mark visited cells to avoid reusing them
 * 5. Backtrack if path doesn't lead to solution
 * 
 * ALGORITHM STEPS:
 * 1. Iterate through each cell in the board
 * 2. If cell matches word[0], start DFS from that cell
 * 3. DFS process:
 *    a. Check if current character matches word[index]
 *    b. Mark cell as visited (temporarily modify it)
 *    c. Recursively explore all 4 neighbors
 *    d. If any neighbor leads to solution, return true
 *    e. Backtrack: restore cell value
 * 4. Base cases:
 *    - If index == word.length: found complete word
 *    - If out of bounds or character mismatch: return false
 * 
 * VISUAL EXAMPLE:
 * board = [['A','B','C','E'],
 *          ['S','F','C','S'],
 *          ['A','D','E','E']]
 * word = "ABCCED"
 * 
 * Start at (0,0) 'A':
 * A → B → C → C → E → D
 * ↓   ↓   ↓   ↓   ↓   ↓
 * (0,0)→(0,1)→(0,2)→(1,2)→(2,2)→(2,1) ✓
 * 
 * Path found!
 * 
 * KEY OPTIMIZATIONS:
 * 1. Early termination: Check if word is longer than board cells
 * 2. Character frequency: If word has more of a char than board, return false
 * 3. Start from rarer character: Count frequencies, start search from end if
 *    word's last char is rarer than first
 * 4. Mark visited: Use '#' or similar to mark visited cells (in-place)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Trie-based: Build trie of word, traverse board (good for multiple words)
 * 2. BFS: Use queue instead of recursion
 * 3. Bit manipulation: Use bitmask to track visited cells
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(m * n * 4^L)
 * - m * n: We might start search from every cell
 * - 4^L: At each step of L (word length), we explore up to 4 directions
 * - In practice, much faster due to early termination and pruning
 * - Worst case: O(m * n * 4^L)
 * 
 * SPACE COMPLEXITY: O(L)
 * - Recursion stack depth: O(L) where L is word length
 * - No additional data structures needed (in-place modification)
 */

class WordSearch {
    
    /**
     * Search for word in 2D board
     * @param board 2D character array
     * @param word Word to search
     * @return True if word exists in board
     */
    fun exist(board: Array<CharArray>, word: String): Boolean {
        val rows = board.size
        val cols = board[0].size
        
        // Early termination: word longer than available cells
        if (word.length > rows * cols) return false
        
        // Try starting from each cell
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                // If first character matches, start DFS
                if (board[row][col] == word[0]) {
                    if (dfs(board, word, 0, row, col)) {
                        return true
                    }
                }
            }
        }
        
        return false
    }
    
    /**
     * DFS to search for word starting from (row, col)
     * @param board The game board
     * @param word Target word
     * @param index Current index in word
     * @param row Current row position
     * @param col Current column position
     * @return True if word found from this position
     */
    private fun dfs(
        board: Array<CharArray>,
        word: String,
        index: Int,
        row: Int,
        col: Int
    ): Boolean {
        // BASE CASE: Found complete word
        if (index == word.length) {
            return true
        }
        
        // BOUNDARY CHECKS
        if (row < 0 || row >= board.size || 
            col < 0 || col >= board[0].size) {
            return false
        }
        
        // CHARACTER MISMATCH
        if (board[row][col] != word[index]) {
            return false
        }
        
        // MARK AS VISITED
        // Save original character
        val temp = board[row][col]
        board[row][col] = '#'  // Use special character to mark visited
        
        // EXPLORE ALL 4 DIRECTIONS: UP, DOWN, LEFT, RIGHT
        val found = dfs(board, word, index + 1, row - 1, col) ||  // UP
                    dfs(board, word, index + 1, row + 1, col) ||  // DOWN
                    dfs(board, word, index + 1, row, col - 1) ||  // LEFT
                    dfs(board, word, index + 1, row, col + 1)     // RIGHT
        
        // BACKTRACK: Restore original character
        board[row][col] = temp
        
        return found
    }
    
    /**
     * Optimized version: Start from rarer character
     */
    fun existOptimized(board: Array<CharArray>, word: String): Boolean {
        // Count character frequencies in board
        val boardFreq = mutableMapOf<Char, Int>()
        for (row in board) {
            for (char in row) {
                boardFreq[char] = boardFreq.getOrDefault(char, 0) + 1
            }
        }
        
        // Count character frequencies in word
        val wordFreq = mutableMapOf<Char, Int>()
        for (char in word) {
            wordFreq[char] = wordFreq.getOrDefault(char, 0) + 1
        }
        
        // Check if word can exist in board
        for ((char, count) in wordFreq) {
            if (boardFreq.getOrDefault(char, 0) < count) {
                return false
            }
        }
        
        // OPTIMIZATION: Start from rarer character
        // If word's last char is rarer than first, search in reverse
        val firstCharFreq = boardFreq.getOrDefault(word[0], 0)
        val lastCharFreq = boardFreq.getOrDefault(word[word.length - 1], 0)
        
        val searchWord = if (lastCharFreq < firstCharFreq) {
            word.reversed()
        } else {
            word
        }
        
        // Try starting from each cell
        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col] == searchWord[0]) {
                    if (dfs(board, searchWord, 0, row, col)) {
                        return true
                    }
                }
            }
        }
        
        return false
    }
    
    /**
     * Alternative: Using visited array instead of modifying board
     */
    fun existWithVisitedArray(board: Array<CharArray>, word: String): Boolean {
        val rows = board.size
        val cols = board[0].size
        val visited = Array(rows) { BooleanArray(cols) }
        
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                if (board[row][col] == word[0]) {
                    if (dfsWithVisited(board, word, 0, row, col, visited)) {
                        return true
                    }
                }
            }
        }
        
        return false
    }
    
    private fun dfsWithVisited(
        board: Array<CharArray>,
        word: String,
        index: Int,
        row: Int,
        col: Int,
        visited: Array<BooleanArray>
    ): Boolean {
        // BASE CASE
        if (index == word.length) return true
        
        // BOUNDARY and VISITED checks
        if (row < 0 || row >= board.size || 
            col < 0 || col >= board[0].size || 
            visited[row][col]) {
            return false
        }
        
        // CHARACTER MISMATCH
        if (board[row][col] != word[index]) return false
        
        // MARK VISITED
        visited[row][col] = true
        
        // EXPLORE
        val found = dfsWithVisited(board, word, index + 1, row - 1, col, visited) ||
                    dfsWithVisited(board, word, index + 1, row + 1, col, visited) ||
                    dfsWithVisited(board, word, index + 1, row, col - 1, visited) ||
                    dfsWithVisited(board, word, index + 1, row, col + 1, visited)
        
        // BACKTRACK
        visited[row][col] = false
        
        return found
    }
    
    /**
     * Find all occurrences (returns list of starting positions)
     */
    fun findAllOccurrences(board: Array<CharArray>, word: String): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        
        for (row in board.indices) {
            for (col in board[0].indices) {
                if (board[row][col] == word[0]) {
                    if (dfs(board, word, 0, row, col)) {
                        result.add(Pair(row, col))
                    }
                }
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input:
 * board = [['A','B','C','E'],
 *          ['S','F','C','S'],
 *          ['A','D','E','E']]
 * word = "ABCCED"
 * 
 * Search Process:
 * 
 * Start at (0,0) 'A':
 * 
 * Step 1: (0,0) 'A' matches word[0] ✓
 *   Mark visited: board[0][0] = '#'
 *   [['#','B','C','E'],
 *    ['S','F','C','S'],
 *    ['A','D','E','E']]
 * 
 * Step 2: Try neighbors of (0,0)
 *   UP: (-1,0) - out of bounds
 *   DOWN: (1,0) 'S' ≠ word[1]'B'
 *   LEFT: (0,-1) - out of bounds
 *   RIGHT: (0,1) 'B' = word[1]'B' ✓
 * 
 * Step 3: (0,1) 'B' matches word[1] ✓
 *   Mark visited: board[0][1] = '#'
 *   [['#','#','C','E'],
 *    ['S','F','C','S'],
 *    ['A','D','E','E']]
 * 
 * Step 4: Try neighbors of (0,1)
 *   UP: (-1,1) - out of bounds
 *   DOWN: (1,1) 'F' ≠ word[2]'C'
 *   LEFT: (0,0) '#' - visited
 *   RIGHT: (0,2) 'C' = word[2]'C' ✓
 * 
 * Step 5: (0,2) 'C' matches word[2] ✓
 *   Mark visited: board[0][2] = '#'
 *   [['#','#','#','E'],
 *    ['S','F','C','S'],
 *    ['A','D','E','E']]
 * 
 * Step 6: Try neighbors of (0,2)
 *   UP: (-1,2) - out of bounds
 *   DOWN: (1,2) 'C' = word[3]'C' ✓
 *   LEFT: (0,1) '#' - visited
 *   RIGHT: (0,3) 'E' ≠ word[3]'C'
 * 
 * Step 7: (1,2) 'C' matches word[3] ✓
 *   Mark visited: board[1][2] = '#'
 *   [['#','#','#','E'],
 *    ['S','F','#','S'],
 *    ['A','D','E','E']]
 * 
 * Step 8: Try neighbors of (1,2)
 *   UP: (0,2) '#' - visited
 *   DOWN: (2,2) 'E' = word[4]'E' ✓
 *   LEFT: (1,1) 'F' ≠ word[4]'E'
 *   RIGHT: (1,3) 'S' ≠ word[4]'E'
 * 
 * Step 9: (2,2) 'E' matches word[4] ✓
 *   Mark visited: board[2][2] = '#'
 *   [['#','#','#','E'],
 *    ['S','F','#','S'],
 *    ['A','D','#','E']]
 * 
 * Step 10: Try neighbors of (2,2)
 *   UP: (1,2) '#' - visited
 *   DOWN: (3,2) - out of bounds
 *   LEFT: (2,1) 'D' = word[5]'D' ✓
 *   RIGHT: (2,3) 'E' ≠ word[5]'D'
 * 
 * Step 11: (2,1) 'D' matches word[5] ✓
 *   index = 6 = word.length
 *   FOUND! Return TRUE
 * 
 * Path: (0,0) → (0,1) → (0,2) → (1,2) → (2,2) → (2,1)
 * Word: A → B → C → C → E → D ✓
 */

fun main() {
    val solver = WordSearch()
    
    // Test Case 1: Word exists
    println("Test Case 1: Word 'ABCCED' in board")
    val board1 = arrayOf(
        charArrayOf('A', 'B', 'C', 'E'),
        charArrayOf('S', 'F', 'C', 'S'),
        charArrayOf('A', 'D', 'E', 'E')
    )
    println("Result: ${solver.exist(board1, "ABCCED")}")
    println("Expected: true")
    println()
    
    // Test Case 2: Word exists (different path)
    println("Test Case 2: Word 'SEE' in board")
    val board2 = arrayOf(
        charArrayOf('A', 'B', 'C', 'E'),
        charArrayOf('S', 'F', 'C', 'S'),
        charArrayOf('A', 'D', 'E', 'E')
    )
    println("Result: ${solver.exist(board2, "SEE")}")
    println("Expected: true")
    println()
    
    // Test Case 3: Word doesn't exist (would reuse cell)
    println("Test Case 3: Word 'ABCB' in board")
    val board3 = arrayOf(
        charArrayOf('A', 'B', 'C', 'E'),
        charArrayOf('S', 'F', 'C', 'S'),
        charArrayOf('A', 'D', 'E', 'E')
    )
    println("Result: ${solver.exist(board3, "ABCB")}")
    println("Expected: false (can't reuse 'B')")
    println()
    
    // Test Case 4: Single cell board
    println("Test Case 4: Single cell")
    val board4 = arrayOf(charArrayOf('A'))
    println("Result for 'A': ${solver.exist(board4, "A")}")
    println("Result for 'B': ${solver.exist(board4, "B")}")
    println("Expected: true, false")
    println()
    
    // Test Case 5: Word longer than board
    println("Test Case 5: Word longer than board")
    val board5 = arrayOf(
        charArrayOf('A', 'B'),
        charArrayOf('C', 'D')
    )
    println("Result: ${solver.exist(board5, "ABCDEFGH")}")
    println("Expected: false")
    println()
    
    // Test Case 6: All same characters
    println("Test Case 6: All same characters")
    val board6 = arrayOf(
        charArrayOf('A', 'A', 'A'),
        charArrayOf('A', 'A', 'A'),
        charArrayOf('A', 'A', 'A')
    )
    println("Result for 'AAA': ${solver.exist(board6, "AAA")}")
    println("Result for 'AAAAAAAAAA': ${solver.exist(board6, "AAAAAAAAAA")}")
    println("Expected: true, false")
    println()
    
    // Test Case 7: Zigzag pattern
    println("Test Case 7: Zigzag pattern")
    val board7 = arrayOf(
        charArrayOf('A', 'B', 'C'),
        charArrayOf('D', 'E', 'F'),
        charArrayOf('G', 'H', 'I')
    )
    println("Result for 'ABEFIHG': ${solver.exist(board7, "ABEFIHG")}")
    println("Expected: true")
    println()
    
    // Test Case 8: Compare implementations
    println("Test Case 8: Compare implementations")
    val board8 = arrayOf(
        charArrayOf('A', 'B', 'C', 'E'),
        charArrayOf('S', 'F', 'C', 'S'),
        charArrayOf('A', 'D', 'E', 'E')
    )
    val word = "ABCCED"
    val result1 = solver.exist(board8, word)
    val result2 = solver.existOptimized(board8, word)
    val result3 = solver.existWithVisitedArray(board8, word)
    println("Standard: $result1")
    println("Optimized: $result2")
    println("With Visited Array: $result3")
    println("All match: ${result1 == result2 && result2 == result3}")
}
