/**
 * ============================================================================
 * PROBLEM: Rat in a Maze
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A rat starts at position (0, 0) in an N x N maze and needs to reach the
 * destination at (N-1, N-1). The rat can move in four directions: Down (D),
 * Up (U), Left (L), Right (R). The maze contains:
 * - 1: open cell (can move through)
 * - 0: blocked cell (cannot move through)
 * 
 * Find all possible paths from source to destination. The rat cannot visit
 * a cell more than once in a single path.
 * 
 * INPUT FORMAT:
 * - N x N matrix of 0s and 1s
 * - maze[0][0] and maze[N-1][N-1] must be 1
 * 
 * OUTPUT FORMAT:
 * - List of all possible paths as strings
 * - Each path is a sequence of moves: "DDRR", "RRDL", etc.
 * - Empty list if no path exists
 * 
 * CONSTRAINTS:
 * - 2 <= N <= 10
 * - maze[0][0] = maze[N-1][N-1] = 1
 * - Only valid moves within maze boundaries
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use backtracking to explore all possible paths:
 * 1. Start at (0, 0) and mark as visited
 * 2. Try moving in all 4 directions (D, L, R, U)
 * 3. For each valid move, recursively explore from new position
 * 4. If destination reached, save the path
 * 5. Backtrack by unmarking current cell and trying other directions
 * 
 * VISUAL EXAMPLE:
 * 
 * Maze (4x4):
 *   1 0 0 0
 *   1 1 0 1
 *   1 1 0 0
 *   0 1 1 1
 * 
 * Possible paths:
 * Path 1: DDRDRR
 *   Start -> D -> D -> R -> D -> R -> R -> End
 * 
 * Path 2: DRDDRR
 *   Start -> D -> R -> D -> D -> R -> R -> End
 * 
 * ALGORITHM STEPS:
 * 1. Check if current position is destination -> save path
 * 2. Mark current cell as visited
 * 3. Try each direction in order (D, L, R, U):
 *    a. Check if next cell is valid (in bounds, open, unvisited)
 *    b. If valid, move to next cell and recurse
 *    c. Add direction to path string
 * 4. Unmark current cell (backtrack)
 * 5. Return all found paths
 * 
 * MOVE PRIORITY: Down, Left, Right, Up (lexicographic order)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(4^(N²))
 * - In worst case (all cells open), up to 4 choices at each cell
 * - Maximum path length is N²
 * - Actual complexity much better due to pruning and visited tracking
 * 
 * SPACE COMPLEXITY: O(N²)
 * - Visited array: O(N²)
 * - Recursion stack: O(N²) for path length
 * - Path storage: O(K * N²) where K is number of paths
 * 
 * ============================================================================
 */

package recursion.hard

class RatInMaze {
    
    /**
     * Find all paths from (0,0) to (N-1, N-1)
     * TIME: O(4^(N²)), SPACE: O(N²)
     * 
     * @param maze N x N matrix with 1 (open) and 0 (blocked)
     * @return List of all possible paths
     */
    fun findPaths(maze: Array<IntArray>): List<String> {
        val n = maze.size
        
        // Edge cases
        if (n == 0 || maze[0][0] == 0 || maze[n-1][n-1] == 0) {
            return emptyList()
        }
        
        val paths = mutableListOf<String>()
        val visited = Array(n) { BooleanArray(n) { false } }
        
        findPathsUtil(maze, 0, 0, "", visited, paths)
        
        return paths.sorted()  // Return in lexicographic order
    }
    
    /**
     * Recursive backtracking to find all paths
     */
    private fun findPathsUtil(
        maze: Array<IntArray>,
        x: Int,
        y: Int,
        path: String,
        visited: Array<BooleanArray>,
        paths: MutableList<String>
    ) {
        val n = maze.size
        
        // Reached destination
        if (x == n - 1 && y == n - 1) {
            paths.add(path)
            return
        }
        
        // Mark current cell as visited
        visited[x][y] = true
        
        // Try all 4 directions in lexicographic order: D, L, R, U
        
        // Down
        if (isSafe(maze, x + 1, y, visited)) {
            findPathsUtil(maze, x + 1, y, path + "D", visited, paths)
        }
        
        // Left
        if (isSafe(maze, x, y - 1, visited)) {
            findPathsUtil(maze, x, y - 1, path + "L", visited, paths)
        }
        
        // Right
        if (isSafe(maze, x, y + 1, visited)) {
            findPathsUtil(maze, x, y + 1, path + "R", visited, paths)
        }
        
        // Up
        if (isSafe(maze, x - 1, y, visited)) {
            findPathsUtil(maze, x - 1, y, path + "U", visited, paths)
        }
        
        // Backtrack: unmark current cell
        visited[x][y] = false
    }
    
    /**
     * Check if position is safe to move
     */
    private fun isSafe(
        maze: Array<IntArray>,
        x: Int,
        y: Int,
        visited: Array<BooleanArray>
    ): Boolean {
        val n = maze.size
        return x >= 0 && x < n && 
               y >= 0 && y < n && 
               maze[x][y] == 1 && 
               !visited[x][y]
    }
    
    /**
     * Check if any path exists
     */
    fun hasPath(maze: Array<IntArray>): Boolean {
        return findPaths(maze).isNotEmpty()
    }
    
    /**
     * Find shortest path (BFS would be better, but using backtracking)
     */
    fun findShortestPath(maze: Array<IntArray>): String? {
        val paths = findPaths(maze)
        return paths.minByOrNull { it.length }
    }
    
    /**
     * Print maze with a specific path highlighted
     */
    fun printMazeWithPath(maze: Array<IntArray>, path: String) {
        val n = maze.size
        val pathCells = mutableSetOf<Pair<Int, Int>>()
        
        // Track path cells
        var x = 0
        var y = 0
        pathCells.add(Pair(x, y))
        
        for (move in path) {
            when (move) {
                'D' -> x++
                'U' -> x--
                'R' -> y++
                'L' -> y--
            }
            pathCells.add(Pair(x, y))
        }
        
        // Print maze
        println("Maze with path: $path")
        for (i in 0 until n) {
            for (j in 0 until n) {
                when {
                    Pair(i, j) in pathCells -> print("* ")
                    maze[i][j] == 1 -> print(". ")
                    else -> print("X ")
                }
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
 * INPUT:
 * 1 0 0 0
 * 1 1 0 1
 * 0 1 0 0
 * 0 1 1 1
 * 
 * Starting at (0,0), destination (3,3)
 * 
 * Exploration:
 * Start (0,0) - Can only go Down
 *   -> (1,0) - Can go Down or Right
 *      -> (1,1) - Can only go Down
 *         -> (2,1) - Can only go Down
 *            -> (3,1) - Can go Right
 *               -> (3,2) - Can go Right
 *                  -> (3,3) - DESTINATION! Path: "DDDDRR"
 * 
 * OUTPUT: ["DDDDRR"]
 * 
 * ============================================================================
 * VARIATIONS
 * ============================================================================
 * 
 * 1. Single path: Return first path found (stop after finding one)
 * 2. Shortest path: Use BFS instead of DFS
 * 3. With obstacles: Current implementation
 * 4. Multi-destination: Find paths to multiple targets
 * 5. With costs: Find minimum cost path (Dijkstra's)
 * 6. 8-directional: Allow diagonal moves
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = RatInMaze()
    
    println("=== Rat in a Maze ===\n")
    
    // Test 1: Simple 4x4 maze
    println("Test 1: 4x4 maze")
    val maze1 = arrayOf(
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 1, 0, 1),
        intArrayOf(0, 1, 0, 0),
        intArrayOf(0, 1, 1, 1)
    )
    val paths1 = solution.findPaths(maze1)
    println("Number of paths: ${paths1.size}")
    paths1.forEach { println("  $it") }
    if (paths1.isNotEmpty()) {
        solution.printMazeWithPath(maze1, paths1[0])
    }
    println()
    
    // Test 2: Multiple paths
    println("Test 2: 4x4 maze with multiple paths")
    val maze2 = arrayOf(
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 1, 0, 1),
        intArrayOf(1, 1, 0, 0),
        intArrayOf(0, 1, 1, 1)
    )
    val paths2 = solution.findPaths(maze2)
    println("Number of paths: ${paths2.size}")
    paths2.forEach { println("  $it") }
    println()
    
    // Test 3: No path exists
    println("Test 3: Maze with no path")
    val maze3 = arrayOf(
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 0, 0, 1),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 1, 1, 1)
    )
    val paths3 = solution.findPaths(maze3)
    println("Number of paths: ${paths3.size}")
    if (paths3.isEmpty()) {
        println("  No path exists!")
    }
    println()
    
    // Test 4: All open maze (many paths)
    println("Test 4: 3x3 all open maze")
    val maze4 = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1)
    )
    val paths4 = solution.findPaths(maze4)
    println("Number of paths: ${paths4.size}")
    println("Shortest path: ${solution.findShortestPath(maze4)}")
    println("First few paths:")
    paths4.take(5).forEach { println("  $it") }
    println()
    
    // Test 5: 2x2 minimal maze
    println("Test 5: 2x2 minimal maze")
    val maze5 = arrayOf(
        intArrayOf(1, 1),
        intArrayOf(1, 1)
    )
    val paths5 = solution.findPaths(maze5)
    println("Number of paths: ${paths5.size}")
    paths5.forEach { println("  $it") }
}
