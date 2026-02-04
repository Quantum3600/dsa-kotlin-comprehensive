/**
 * ============================================================================
 * PROBLEM: M-Coloring Problem (Graph Coloring)
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an undirected graph and M colors, determine if the graph can be
 * colored with at most M colors such that no two adjacent vertices have
 * the same color. Find one such coloring if it exists.
 * 
 * INPUT FORMAT:
 * - N: Number of vertices (0 to N-1)
 * - graph: 2D array where graph[i][j] = 1 if edge exists between i and j
 * - M: Number of colors available
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if coloring is possible, false otherwise
 * - Color assignment array if solution exists
 * 
 * CONSTRAINTS:
 * - 1 <= N <= 20
 * - 1 <= M <= 10
 * - Graph is undirected: graph[i][j] = graph[j][i]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a classic backtracking problem:
 * 1. Try assigning each color to a vertex
 * 2. Check if the assignment is safe (no adjacent vertex has same color)
 * 3. If safe, recursively color remaining vertices
 * 4. If stuck, backtrack and try a different color
 * 5. If all vertices colored successfully, we found a solution
 * 
 * VISUAL EXAMPLE:
 * 
 * Graph with 4 vertices:
 *     0 --- 1
 *     |     |
 *     3 --- 2
 * 
 * M = 3 colors (Red=1, Green=2, Blue=3)
 * 
 * Solution:
 *     R --- G
 *     |     |
 *     B --- R
 * 
 * Vertex 0: Color 1 (Red)
 * Vertex 1: Color 2 (Green)
 * Vertex 2: Color 1 (Red)
 * Vertex 3: Color 3 (Blue)
 * 
 * ALGORITHM STEPS:
 * 1. Start with vertex 0
 * 2. For each vertex:
 *    a. Try each color from 1 to M
 *    b. Check if color is safe (not used by adjacent vertices)
 *    c. If safe, assign color and recurse to next vertex
 *    d. If recursion succeeds, return true
 *    e. If recursion fails, backtrack (remove color) and try next color
 * 3. If all vertices colored, return true
 * 4. If no color works, return false
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Backtracking (implemented) - O(M^N) worst case
 * 2. Greedy (Welsh-Powell) - Fast but not always optimal
 * 3. Dynamic Programming - For specific graph structures
 * 4. Constraint Satisfaction Problem solvers
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(M^N)
 * - In worst case, try all M colors for each of N vertices
 * - With pruning, actual complexity is much better in practice
 * - For sparse graphs, many branches are pruned early
 * 
 * SPACE COMPLEXITY: O(N)
 * - Color array: O(N)
 * - Recursion stack: O(N)
 * - Graph storage: O(N^2) for adjacency matrix
 * 
 * ============================================================================
 */

package recursion.hard

class MColoringProblem {
    
    /**
     * Check if graph can be colored with M colors
     * TIME: O(M^N), SPACE: O(N)
     * 
     * @param graph Adjacency matrix
     * @param m Number of colors available
     * @return true if coloring is possible
     */
    fun canColor(graph: Array<IntArray>, m: Int): Boolean {
        val n = graph.size
        if (n == 0) return true
        
        val colors = IntArray(n) { 0 }  // 0 means uncolored
        return canColorUtil(graph, m, colors, 0)
    }
    
    /**
     * Find a valid coloring and return the color assignment
     * 
     * @return Pair of (isPossible, colorArray)
     */
    fun findColoring(graph: Array<IntArray>, m: Int): Pair<Boolean, IntArray> {
        val n = graph.size
        if (n == 0) return Pair(true, intArrayOf())
        
        val colors = IntArray(n) { 0 }
        val possible = canColorUtil(graph, m, colors, 0)
        return Pair(possible, colors)
    }
    
    /**
     * Recursive backtracking utility
     */
    private fun canColorUtil(
        graph: Array<IntArray>,
        m: Int,
        colors: IntArray,
        vertex: Int
    ): Boolean {
        val n = graph.size
        
        // Base case: all vertices colored
        if (vertex == n) {
            return true
        }
        
        // Try each color for current vertex
        for (color in 1..m) {
            // Check if this color is safe
            if (isSafe(graph, colors, vertex, color)) {
                // Assign color
                colors[vertex] = color
                
                // Recurse for next vertex
                if (canColorUtil(graph, m, colors, vertex + 1)) {
                    return true
                }
                
                // Backtrack if recursion fails
                colors[vertex] = 0
            }
        }
        
        // No color worked, return false
        return false
    }
    
    /**
     * Check if assigning color to vertex is safe
     * (no adjacent vertex has the same color)
     */
    private fun isSafe(
        graph: Array<IntArray>,
        colors: IntArray,
        vertex: Int,
        color: Int
    ): Boolean {
        // Check all adjacent vertices
        for (i in colors.indices) {
            // If there's an edge and adjacent vertex has same color
            if (graph[vertex][i] == 1 && colors[i] == color) {
                return false
            }
        }
        return true
    }
    
    /**
     * Find chromatic number (minimum colors needed)
     */
    fun findChromaticNumber(graph: Array<IntArray>): Int {
        val n = graph.size
        if (n == 0) return 0
        
        // Try with increasing number of colors
        for (m in 1..n) {
            if (canColor(graph, m)) {
                return m
            }
        }
        return n  // Worst case: need N colors
    }
    
    /**
     * Print the coloring solution
     */
    fun printSolution(colors: IntArray) {
        println("Vertex coloring:")
        colors.forEachIndexed { vertex, color ->
            println("Vertex $vertex -> Color $color")
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: 4 vertices, 3 colors
 * Graph:
 *   0-1, 0-3, 1-2, 2-3
 * 
 * Adjacency Matrix:
 *     0  1  2  3
 *  0 [0, 1, 0, 1]
 *  1 [1, 0, 1, 0]
 *  2 [0, 1, 0, 1]
 *  3 [1, 0, 1, 0]
 * 
 * Backtracking Process:
 * 
 * Vertex 0: Try color 1 ✓ (no adjacent colored yet)
 * Vertex 1: Try color 1 ✗ (0 has color 1)
 *           Try color 2 ✓
 * Vertex 2: Try color 1 ✗ (adjacent to 1)
 *           Try color 2 ✗ (1 has color 2)
 *           Try color 3 ✓
 * Vertex 3: Try color 1 ✗ (0 has color 1)
 *           Try color 2 ✓ (0 and 2 don't conflict)
 * 
 * OUTPUT: [1, 2, 3, 2]
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = MColoringProblem()
    
    println("=== M-Coloring Problem ===\n")
    
    // Test 1: Simple 4-vertex graph (cycle)
    println("Test 1: 4-vertex cycle graph, 3 colors")
    val graph1 = arrayOf(
        intArrayOf(0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0),
        intArrayOf(0, 1, 0, 1),
        intArrayOf(1, 0, 1, 0)
    )
    val (possible1, colors1) = solution.findColoring(graph1, 3)
    println("Can color: $possible1")
    if (possible1) {
        solution.printSolution(colors1)
    }
    println()
    
    // Test 2: Complete graph K4 (needs 4 colors)
    println("Test 2: Complete graph K4, 3 colors (should fail)")
    val graph2 = arrayOf(
        intArrayOf(0, 1, 1, 1),
        intArrayOf(1, 0, 1, 1),
        intArrayOf(1, 1, 0, 1),
        intArrayOf(1, 1, 1, 0)
    )
    val possible2 = solution.canColor(graph2, 3)
    println("Can color with 3 colors: $possible2")
    
    println("\nTest 2b: Same graph with 4 colors")
    val (possible2b, colors2b) = solution.findColoring(graph2, 4)
    println("Can color with 4 colors: $possible2b")
    if (possible2b) {
        solution.printSolution(colors2b)
    }
    println()
    
    // Test 3: Linear graph (needs 2 colors)
    println("Test 3: Linear graph (0-1-2-3), 2 colors")
    val graph3 = arrayOf(
        intArrayOf(0, 1, 0, 0),
        intArrayOf(1, 0, 1, 0),
        intArrayOf(0, 1, 0, 1),
        intArrayOf(0, 0, 1, 0)
    )
    val chromatic3 = solution.findChromaticNumber(graph3)
    println("Chromatic number: $chromatic3")
    val (_, colors3) = solution.findColoring(graph3, chromatic3)
    solution.printSolution(colors3)
    println()
    
    // Test 4: Star graph
    println("Test 4: Star graph (center 0, leaves 1,2,3), 2 colors")
    val graph4 = arrayOf(
        intArrayOf(0, 1, 1, 1),
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 0, 0, 0),
        intArrayOf(1, 0, 0, 0)
    )
    val chromatic4 = solution.findChromaticNumber(graph4)
    println("Chromatic number: $chromatic4")
    val (_, colors4) = solution.findColoring(graph4, chromatic4)
    solution.printSolution(colors4)
    println()
    
    // Test 5: Empty graph (no edges)
    println("Test 5: Empty graph (4 vertices, no edges)")
    val graph5 = Array(4) { IntArray(4) { 0 } }
    val chromatic5 = solution.findChromaticNumber(graph5)
    println("Chromatic number: $chromatic5")
    println("(All vertices can have the same color)")
}
