/**
 * ============================================================================
 * PROBLEM: Rotten Oranges (Minimum Time for All Oranges to Rot)
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue Implementation (BFS)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a grid where: 
 * - 0 represents empty cell
 * - 1 represents fresh orange
 * - 2 represents rotten orange
 * 
 * Every minute, any fresh orange adjacent (4-directionally) to a rotten orange
 * becomes rotten.  Return minimum minutes until no fresh orange remains.
 * Return -1 if impossible.
 * 
 * INPUT FORMAT:
 * grid = [
 *   [2, 1, 1],
 *   [1, 1, 0],
 *   [0, 1, 1]
 * ]
 * 
 * OUTPUT FORMAT:
 * 4 (minutes until all oranges rot)
 * 
 * CONSTRAINTS:
 * - 1 <= m, n <= 10
 * - grid[i][j] is 0, 1, or 2
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * This is a multi-source BFS problem. Think of it like:
 * - A virus spreading from multiple starting points
 * - Wildfire spreading from multiple ignition points
 * - Ripples in water from multiple stones
 * 
 * KEY INSIGHT:
 * - All rotten oranges rot their neighbors simultaneously
 * - Need to process all oranges at current time before moving to next minute
 * - Use BFS (not DFS) because we need shortest time (level-order traversal)
 * 
 * WHY BFS?
 * - BFS processes nodes level by level
 * - Each level = one minute of time
 * - Guarantees minimum time to reach all nodes
 * 
 * ALGORITHM STEPS:
 * 1. **Initialize:**
 *    - Count fresh oranges
 *    - Add all rotten oranges to queue
 * 
 * 2. **BFS:**
 *    - For each minute (queue level):
 *      - Process all rotten oranges at this time
 *      - Rot adjacent fresh oranges
 *      - Add newly rotten oranges to queue
 *      - Decrement fresh count
 *    - Increment time
 * 
 * 3. **Return:**
 *    - If fresh count = 0, return time
 *    - Else return -1 (impossible)
 * 
 * VISUAL EXAMPLE: 
 * ```
 * Initial (t=0):    After 1 min:     After 2 min:     After 3 min:     After 4 min:
 * [2, 1, 1]         [2, 2, 1]        [2, 2, 2]        [2, 2, 2]        [2, 2, 2]
 * [1, 1, 0]   →     [2, 1, 0]   →    [2, 2, 0]   →    [2, 2, 0]   →    [2, 2, 0]
 * [0, 1, 1]         [0, 1, 1]        [0, 2, 1]        [0, 2, 2]        [0, 2, 2]
 * 
 * Fresh:  6          Fresh: 4         Fresh: 2         Fresh: 1         Fresh: 0 ✓
 * 
 * Queue:             Queue:           Queue:           Queue:           Queue:
 * [(0,0)]          [(0,1),(1,0)]    [(0,2),(1,1),    [(2,1)]          []
 *                                    (2,1)]
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(m * n)
 * - Visit each cell at most once
 * - Each cell processed in O(1) time
 * - Queue operations: O(1) per cell
 * - Total: O(m * n) where m = rows, n = cols
 * 
 * SPACE COMPLEXITY: O(m * n)
 * - Queue can hold at most all cells:  O(m * n)
 * - No extra grid needed (modify in-place possible)
 * - Worst case: All oranges rotten initially
 * 
 * WHY NOT DFS?
 * - DFS explores one path completely before others
 * - Doesn't give minimum time (shortest path)
 * - Could mark oranges rotten at wrong time
 * 
 * ============================================================================
 * IMPLEMENTATION
 * ============================================================================
 */

fun orangesRotting(grid: Array<IntArray>): Int {
    val rows = grid.size
    val cols = grid[0].size
    
    // Queue for BFS:  stores (row, col) of rotten oranges
    val queue = ArrayDeque<Pair<Int, Int>>()
    var freshCount = 0
    var minutes = 0
    
    // Step 1: Initialize - count fresh and enqueue rotten
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            when (grid[r][c]) {
                1 -> freshCount++
                2 -> queue.addLast(Pair(r, c))
            }
        }
    }
    
    // Edge case: No fresh oranges
    if (freshCount == 0) return 0
    
    // Directions: up, down, left, right
    val directions = arrayOf(
        Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1)
    )
    
    // Step 2: BFS - process level by level
    while (queue.isNotEmpty() && freshCount > 0) {
        val size = queue.size
        
        // Process all oranges that rot in this minute
        repeat(size) {
            val (row, col) = queue.removeFirst()
            
            // Check all 4 adjacent cells
            for ((dr, dc) in directions) {
                val newRow = row + dr
                val newCol = col + dc
                
                // Check bounds and if cell has fresh orange
                if (newRow in 0 until rows && 
                    newCol in 0 until cols && 
                    grid[newRow][newCol] == 1) {
                    
                    // Rot the fresh orange
                    grid[newRow][newCol] = 2
                    freshCount--
                    queue.addLast(Pair(newRow, newCol))
                }
            }
        }
        
        // One minute passed
        minutes++
    }
    
    // Step 3: Check if all oranges rotted
    return if (freshCount == 0) minutes else -1
}

/**
 * ============================================================================
 * USAGE EXAMPLES
 * ============================================================================
 */

fun main() {
    // Example 1: All oranges eventually rot
    println("Example 1: All Oranges Rot")
    val grid1 = arrayOf(
        intArrayOf(2, 1, 1),
        intArrayOf(1, 1, 0),
        intArrayOf(0, 1, 1)
    )
    println("Minutes:  ${orangesRotting(grid1)}")  // 4
    
    // Example 2: Impossible to rot all
    println("\nExample 2: Impossible Case")
    val grid2 = arrayOf(
        intArrayOf(2, 1, 1),
        intArrayOf(0, 1, 1),
        intArrayOf(1, 0, 1)
    )
    println("Minutes: ${orangesRotting(grid2)}")  // -1 (orange at (2,2) unreachable)
    
    // Example 3: No fresh oranges
    println("\nExample 3: No Fresh Oranges")
    val grid3 = arrayOf(
        intArrayOf(0, 2)
    )
    println("Minutes: ${orangesRotting(grid3)}")  // 0
    
    // Example 4: All fresh, no rotten
    println("\nExample 4: No Rotten Oranges")
    val grid4 = arrayOf(
        intArrayOf(1, 1),
        intArrayOf(1, 1)
    )
    println("Minutes: ${orangesRotting(grid4)}")  // -1
    
    // Example 5: Single rotten orange
    println("\nExample 5: Single Rotten")
    val grid5 = arrayOf(
        intArrayOf(2, 1),
        intArrayOf(1, 1)
    )
    println("Minutes: ${orangesRotting(grid5)}")  // 2
}

/**
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 2: DFS with Time Tracking
 * - Use DFS but pass time parameter
 * - Track minimum time to reach each cell
 * - Pros: Can work with modifications
 * - Cons: More complex, not natural fit
 * 
 * APPROACH 3: Dynamic Programming
 * - Build time matrix using DP
 * - Cell value = min of neighbor times + 1
 * - Pros: Different perspective
 * - Cons: Multiple passes needed
 * 
 * WHY BFS IS BEST:
 * ✅ Natural level-order traversal
 * ✅ Guarantees minimum time
 * ✅ Single pass solution
 * ✅ Easy to understand and implement
 * ✅ Standard for multi-source shortest path
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * 1. **01 Matrix** - Distance to nearest 0
 * 2. **Walls and Gates** - Distance to gate in dungeon
 * 3. **Word Ladder** - Shortest transformation sequence
 * 4. **Shortest Path in Binary Matrix** - Diagonal movement allowed
 * 5. **Surrounded Regions** - Board capture problem
 * 
 * PATTERN:  Multi-source BFS
 * - Multiple starting points
 * - Find shortest distance/time to all targets
 * - Use queue to process level by level
 * 
 * ============================================================================
 * EDGE CASES & GOTCHAS
 * ============================================================================
 * 
 * 1. Empty grid: Should return 0
 * 2. No rotten oranges: Return -1 if fresh exist, 0 otherwise
 * 3. No fresh oranges: Return 0 immediately
 * 4. Isolated fresh orange: Will cause -1 return
 * 5. All cells empty: Return 0
 * 6. Single cell grid: Check if rotten or fresh
 * 7. Level-order processing: Must process entire level before incrementing time
 * 
 * COMMON MISTAKES:
 * ❌ Incrementing time for each orange (should be per level)
 * ❌ Not checking freshCount == 0 initially
 * ❌ Forgetting to decrement freshCount
 * ❌ Using DFS instead of BFS
 * ❌ Not checking bounds properly
 * 
 * ============================================================================
 */
