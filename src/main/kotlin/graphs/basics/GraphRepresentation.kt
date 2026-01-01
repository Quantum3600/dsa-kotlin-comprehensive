/**
 * ============================================================================
 * CONCEPT: Graph Representation
 * DIFFICULTY: Easy
 * CATEGORY: Graphs - Basics
 * ============================================================================
 * 
 * WHAT IS A GRAPH?
 * A graph is a non-linear data structure consisting of vertices (nodes) and
 * edges (connections between nodes).
 * 
 * COMPONENTS:
 * - **Vertex/Node**: A point in the graph
 * - **Edge**: A connection between two vertices
 * - **Degree**: Number of edges connected to a vertex
 * 
 * TYPES OF GRAPHS:
 * 
 * 1. **Directed Graph (Digraph)**:
 *    Edges have direction (A → B is different from B → A)
 *    Example: Twitter followers, web pages
 *    ```
 *    A → B
 *    ↓   ↓
 *    C ← D
 *    ```
 * 
 * 2. **Undirected Graph**:
 *    Edges have no direction (A-B is same as B-A)
 *    Example: Facebook friends, roads between cities
 *    ```
 *    A — B
 *    |   |
 *    C — D
 *    ```
 * 
 * 3. **Weighted Graph**:
 *    Edges have weights/costs
 *    Example: Roads with distances, network latency
 *    ```
 *    A -5- B
 *    |     |
 *    3     2
 *    |     |
 *    C -4- D
 *    ```
 * 
 * 4. **Unweighted Graph**:
 *    All edges have same weight (or no weight)
 * 
 * ============================================================================
 * GRAPH REPRESENTATIONS
 * ============================================================================
 * 
 * 1. **ADJACENCY MATRIX**: 2D array where matrix[i][j] = 1 if edge exists
 * 
 * Graph:     A --- B
 *            |     |
 *            C --- D
 * 
 * Matrix:    A  B  C  D
 *         A [0, 1, 1, 0]
 *         B [1, 0, 0, 1]
 *         C [1, 0, 0, 1]
 *         D [0, 1, 1, 0]
 * 
 * Pros: ✅ O(1) edge lookup, ✅ Simple
 * Cons: ❌ O(V²) space, ❌ Slow for sparse graphs
 * 
 * 2. **ADJACENCY LIST**: Array/Map of lists showing neighbors
 * 
 * List:
 * A → [B, C]
 * B → [A, D]
 * C → [A, D]
 * D → [B, C]
 * 
 * Pros: ✅ O(V+E) space, ✅ Fast for sparse graphs
 * Cons: ❌ O(V) to check if edge exists
 * 
 * 3. **EDGE LIST**: List of all edges
 * 
 * Edges: [(A,B), (A,C), (B,D), (C,D)]
 * 
 * Pros: ✅ Simple, ✅ Good for edge operations
 * Cons: ❌ Slow to find neighbors
 * 
 * ============================================================================
 * TERMINOLOGY
 * ============================================================================
 * 
 * - **Path**: Sequence of vertices connected by edges
 * - **Cycle**: Path that starts and ends at same vertex
 * - **Connected Graph**: Path exists between every pair of vertices
 * - **Acyclic Graph**: Graph with no cycles
 * - **DAG**: Directed Acyclic Graph
 * - **Degree**: Number of edges connected to a vertex
 *   - In-degree: Incoming edges (directed)
 *   - Out-degree: Outgoing edges (directed)
 * 
 * ============================================================================
 * APPLICATIONS
 * ============================================================================
 * 
 * - Social Networks (friends, followers)
 * - Maps and Navigation (roads, routes)
 * - Computer Networks (routers, connections)
 * - Web Pages (links between pages)
 * - Dependencies (task scheduling)
 * - Recommendation Systems
 * - Circuit Design
 * - Game States
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * Operation            | Adjacency Matrix | Adjacency List
 * ---------------------|------------------|----------------
 * Add Vertex           | O(V²)            | O(1)
 * Add Edge             | O(1)             | O(1)
 * Remove Vertex        | O(V²)            | O(V+E)
 * Remove Edge          | O(1)             | O(E)
 * Check if edge exists | O(1)             | O(V)
 * Find all neighbors   | O(V)             | O(degree)
 * Space                | O(V²)            | O(V+E)
 * 
 * V = vertices, E = edges
 * 
 * ============================================================================
 */

package graphs.basics

/**
 * Graph implementation using Adjacency List
 * Better for sparse graphs (fewer edges)
 */
class GraphAdjList(private val vertices: Int, private val isDirected: Boolean = false) {
    // Map from vertex to list of its neighbors
    // Using MutableMap for flexibility
    private val adjList = mutableMapOf<Int, MutableList<Int>>()
    
    init {
        // Initialize empty lists for all vertices
        for (i in 0 until vertices) {
            adjList[i] = mutableListOf()
        }
    }
    
    /**
     * Add an edge between vertices u and v
     * TIME: O(1) - Just append to list
     */
    fun addEdge(u: Int, v: Int) {
        // Validate vertices
        if (u !in 0 until vertices || v !in 0 until vertices) {
            throw IllegalArgumentException("Invalid vertex")
        }
        
        // Add edge from u to v
        adjList[u]?.add(v)
        
        // If undirected, add edge from v to u as well
        if (!isDirected) {
            adjList[v]?.add(u)
        }
    }
    
    /**
     * Get all neighbors of a vertex
     * TIME: O(1) to access list
     */
    fun getNeighbors(vertex: Int): List<Int> {
        return adjList[vertex] ?: emptyList()
    }
    
    /**
     * Print the graph
     * TIME: O(V+E)
     */
    fun printGraph() {
        println("Graph (Adjacency List):")
        for (i in 0 until vertices) {
            print("$i → ")
            println(adjList[i]?.joinToString(", ") ?: "[]")
        }
    }
    
    /**
     * Get the degree of a vertex
     */
    fun getDegree(vertex: Int): Int {
        return adjList[vertex]?.size ?: 0
    }
}

/**
 * Graph implementation using Adjacency Matrix
 * Better for dense graphs (many edges)
 */
class GraphAdjMatrix(private val vertices: Int, private val isDirected: Boolean = false) {
    // 2D array: matrix[i][j] = 1 if edge exists from i to j
    private val matrix = Array(vertices) { IntArray(vertices) }
    
    /**
     * Add an edge between vertices u and v
     * TIME: O(1) - Direct array access
     */
    fun addEdge(u: Int, v: Int, weight: Int = 1) {
        // Validate vertices
        if (u !in 0 until vertices || v !in 0 until vertices) {
            throw IllegalArgumentException("Invalid vertex")
        }
        
        // Set edge from u to v
        matrix[u][v] = weight
        
        // If undirected, set edge from v to u as well
        if (!isDirected) {
            matrix[v][u] = weight
        }
    }
    
    /**
     * Check if edge exists
     * TIME: O(1)
     */
    fun hasEdge(u: Int, v: Int): Boolean {
        return matrix[u][v] != 0
    }
    
    /**
     * Get weight of edge
     * TIME: O(1)
     */
    fun getWeight(u: Int, v: Int): Int {
        return matrix[u][v]
    }
    
    /**
     * Get all neighbors of a vertex
     * TIME: O(V) - Must scan entire row
     */
    fun getNeighbors(vertex: Int): List<Int> {
        val neighbors = mutableListOf<Int>()
        for (i in 0 until vertices) {
            if (matrix[vertex][i] != 0) {
                neighbors.add(i)
            }
        }
        return neighbors
    }
    
    /**
     * Print the adjacency matrix
     * TIME: O(V²)
     */
    fun printMatrix() {
        println("Adjacency Matrix:")
        print("   ")
        for (i in 0 until vertices) print("$i ")
        println()
        
        for (i in 0 until vertices) {
            print("$i [")
            for (j in 0 until vertices) {
                print("${matrix[i][j]}")
                if (j < vertices - 1) print(" ")
            }
            println("]")
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Creating an undirected graph:
 * 
 *     0 --- 1
 *     |     |
 *     2 --- 3
 * 
 * Adjacency List Representation:
 * 0 → [1, 2]
 * 1 → [0, 3]
 * 2 → [0, 3]
 * 3 → [1, 2]
 * 
 * Adjacency Matrix Representation:
 *   0 1 2 3
 * 0[0 1 1 0]
 * 1[1 0 0 1]
 * 2[1 0 0 1]
 * 3[0 1 1 0]
 * 
 * Operations:
 * - addEdge(0, 1): Creates edge 0-1
 * - getNeighbors(0): Returns [1, 2]
 * - hasEdge(0, 3): Returns false
 * - hasEdge(1, 3): Returns true
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Graph Representations ===\n")
    
    // Test 1: Undirected graph with adjacency list
    println("Test 1: Undirected Graph (Adjacency List)")
    println("Creating graph:")
    println("    0 --- 1")
    println("    |     |")
    println("    2 --- 3")
    println()
    
    val graphList = GraphAdjList(vertices = 4, isDirected = false)
    graphList.addEdge(0, 1)
    graphList.addEdge(0, 2)
    graphList.addEdge(1, 3)
    graphList.addEdge(2, 3)
    
    graphList.printGraph()
    println("\nNeighbors of vertex 0: ${graphList.getNeighbors(0)}")
    println("Neighbors of vertex 3: ${graphList.getNeighbors(3)}")
    println("Degree of vertex 0: ${graphList.getDegree(0)}")
    println()
    
    // Test 2: Directed graph with adjacency list
    println("Test 2: Directed Graph (Adjacency List)")
    println("Creating graph:")
    println("    0 → 1")
    println("    ↓   ↓")
    println("    2 ← 3")
    println()
    
    val directedList = GraphAdjList(vertices = 4, isDirected = true)
    directedList.addEdge(0, 1)
    directedList.addEdge(0, 2)
    directedList.addEdge(1, 3)
    directedList.addEdge(3, 2)
    
    directedList.printGraph()
    println("\nOut-degree of vertex 0: ${directedList.getDegree(0)}")
    println("Out-degree of vertex 2: ${directedList.getDegree(2)}")
    println()
    
    // Test 3: Undirected graph with adjacency matrix
    println("Test 3: Undirected Graph (Adjacency Matrix)")
    val graphMatrix = GraphAdjMatrix(vertices = 4, isDirected = false)
    graphMatrix.addEdge(0, 1)
    graphMatrix.addEdge(0, 2)
    graphMatrix.addEdge(1, 3)
    graphMatrix.addEdge(2, 3)
    
    graphMatrix.printMatrix()
    println("\nHas edge 0-1: ${graphMatrix.hasEdge(0, 1)}")
    println("Has edge 0-3: ${graphMatrix.hasEdge(0, 3)}")
    println("Neighbors of vertex 1: ${graphMatrix.getNeighbors(1)}")
    println()
    
    // Test 4: Weighted directed graph
    println("Test 4: Weighted Directed Graph (Adjacency Matrix)")
    println("Creating weighted graph:")
    println("    0 -5→ 1")
    println("    ↓3    ↓2")
    println("    2 -4→ 3")
    println()
    
    val weightedGraph = GraphAdjMatrix(vertices = 4, isDirected = true)
    weightedGraph.addEdge(0, 1, 5)
    weightedGraph.addEdge(0, 2, 3)
    weightedGraph.addEdge(1, 3, 2)
    weightedGraph.addEdge(2, 3, 4)
    
    weightedGraph.printMatrix()
    println("\nWeight of edge 0→1: ${weightedGraph.getWeight(0, 1)}")
    println("Weight of edge 1→3: ${weightedGraph.getWeight(1, 3)}")
    println()
    
    // Test 5: Comparison of representations
    println("Test 5: Space Complexity Comparison")
    val v = 1000
    val sparseEdges = 2000  // Sparse graph
    val denseEdges = v * (v - 1) / 2  // Dense graph (complete)
    
    println("For $v vertices:")
    println("\nSparse Graph ($sparseEdges edges):")
    println("  Adjacency List: ~${(v + sparseEdges * 2) * 4} bytes")
    println("  Adjacency Matrix: ~${v * v * 4} bytes")
    println("  → Adjacency List is better!")
    
    println("\nDense Graph ($denseEdges edges):")
    println("  Adjacency List: ~${(v + denseEdges * 2) * 4} bytes")
    println("  Adjacency Matrix: ~${v * v * 4} bytes")
    println("  → Adjacency Matrix is competitive!")
    println()
    
    println("=== When to Use Each Representation ===")
    println("\nAdjacency List:")
    println("  ✅ Sparse graphs (few edges)")
    println("  ✅ Need to iterate through neighbors")
    println("  ✅ Memory is limited")
    println("  ✅ BFS, DFS traversals")
    
    println("\nAdjacency Matrix:")
    println("  ✅ Dense graphs (many edges)")
    println("  ✅ Need fast edge lookup")
    println("  ✅ Weighted graphs")
    println("  ✅ Graph algorithms (Floyd-Warshall, etc.)")
}
