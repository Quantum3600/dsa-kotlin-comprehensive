# Graph Algorithms Guide

## Table of Contents
1. [Introduction to Graphs](#introduction)
2. [Graph Representations](#representations)
3. [Graph Traversal Algorithms](#traversal)
4. [Shortest Path Algorithms](#shortest-path)
5. [Minimum Spanning Tree](#mst)
6. [Topological Sorting](#topological-sort)
7. [Strongly Connected Components](#scc)
8. [Advanced Graph Algorithms](#advanced)
9. [Common Graph Problems](#common-problems)
10. [Implementation Tips](#implementation-tips)

---

## Introduction to Graphs {#introduction}

### What is a Graph?

A **graph** is a data structure consisting of:
- **Vertices (Nodes)**: The entities in the graph
- **Edges**: Connections between vertices

**Notation**: G = (V, E) where V is set of vertices and E is set of edges

### Types of Graphs

#### 1. Directed vs Undirected

**Directed Graph (Digraph)**:
- Edges have direction (one-way)
- Edge (u, v) means u → v
- Example: Twitter following (A follows B ≠ B follows A)

```
  A → B
  ↓   ↓
  C ← D
```

**Undirected Graph**:
- Edges are bidirectional
- Edge (u, v) means u ↔ v
- Example: Facebook friends (symmetric relationship)

```
  A — B
  |   |
  C — D
```

#### 2. Weighted vs Unweighted

**Weighted Graph**:
- Edges have weights/costs
- Example: Road network (distances)

```
    5
  A — B
  |2  |3
  C — D
    4
```

**Unweighted Graph**:
- All edges have same weight (usually 1)
- Example: Social network connections

#### 3. Cyclic vs Acyclic

**Cyclic**: Contains at least one cycle (path from node to itself)
**Acyclic**: No cycles

**DAG (Directed Acyclic Graph)**: Directed graph with no cycles
- Important for: Task scheduling, dependency resolution

#### 4. Connected vs Disconnected

**Connected**: Path exists between any two vertices
**Disconnected**: Some vertices are unreachable from others

### Graph Terminology

- **Degree**: Number of edges connected to a vertex
  - **In-degree**: Incoming edges (directed graphs)
  - **Out-degree**: Outgoing edges (directed graphs)
- **Path**: Sequence of vertices connected by edges
- **Cycle**: Path that starts and ends at the same vertex
- **Connected Component**: Maximal set of connected vertices
- **Tree**: Connected acyclic undirected graph
- **Forest**: Disconnected graph where each component is a tree

---

## Graph Representations {#representations}

### 1. Adjacency Matrix

A 2D array where `matrix[i][j]` indicates edge from vertex i to j.

**Advantages**:
- ✅ O(1) edge lookup
- ✅ Simple to implement
- ✅ Good for dense graphs

**Disadvantages**:
- ❌ O(V²) space (wasteful for sparse graphs)
- ❌ O(V) to iterate over neighbors

**Implementation**:
```kotlin
class GraphMatrix(private val vertices: Int) {
    private val adjMatrix = Array(vertices) { IntArray(vertices) }
    
    // Add edge from u to v with weight w
    fun addEdge(u: Int, v: Int, weight: Int = 1) {
        adjMatrix[u][v] = weight
        // For undirected graph: adjMatrix[v][u] = weight
    }
    
    // Check if edge exists
    fun hasEdge(u: Int, v: Int): Boolean {
        return adjMatrix[u][v] != 0
    }
    
    // Get neighbors of vertex u
    fun getNeighbors(u: Int): List<Int> {
        val neighbors = mutableListOf<Int>()
        for (v in 0 until vertices) {
            if (adjMatrix[u][v] != 0) {
                neighbors.add(v)
            }
        }
        return neighbors
    }
}
```

**Space Complexity**: O(V²)

---

### 2. Adjacency List

Array of lists where `adjList[i]` contains neighbors of vertex i.

**Advantages**:
- ✅ O(V + E) space (efficient for sparse graphs)
- ✅ O(degree) to iterate over neighbors
- ✅ Memory efficient

**Disadvantages**:
- ❌ O(degree) edge lookup
- ❌ Slightly more complex

**Implementation**:
```kotlin
class GraphList(private val vertices: Int) {
    private val adjList = Array(vertices) { mutableListOf<Int>() }
    
    // Add edge from u to v
    fun addEdge(u: Int, v: Int) {
        adjList[u].add(v)
        // For undirected graph: adjList[v].add(u)
    }
    
    // Get neighbors of vertex u
    fun getNeighbors(u: Int): List<Int> {
        return adjList[u]
    }
}

// For weighted graphs
class WeightedGraphList(private val vertices: Int) {
    data class Edge(val to: Int, val weight: Int)
    
    private val adjList = Array(vertices) { mutableListOf<Edge>() }
    
    fun addEdge(u: Int, v: Int, weight: Int) {
        adjList[u].add(Edge(v, weight))
        // For undirected: adjList[v].add(Edge(u, weight))
    }
    
    fun getNeighbors(u: Int): List<Edge> {
        return adjList[u]
    }
}
```

**Space Complexity**: O(V + E)

---

### 3. Edge List

Simple list of all edges in the graph.

**Implementation**:
```kotlin
data class Edge(val from: Int, val to: Int, val weight: Int = 1)

class GraphEdgeList {
    private val edges = mutableListOf<Edge>()
    
    fun addEdge(from: Int, to: Int, weight: Int = 1) {
        edges.add(Edge(from, to, weight))
    }
    
    fun getAllEdges(): List<Edge> = edges
}
```

**Use Cases**:
- Kruskal's MST algorithm
- When you need to process all edges

---

## Graph Traversal Algorithms {#traversal}

### 1. Breadth-First Search (BFS)

Explores graph level by level using a queue.

**Properties**:
- Finds shortest path in unweighted graphs
- Time: O(V + E)
- Space: O(V) for queue

**Applications**:
- Shortest path in unweighted graphs
- Level-order traversal
- Finding connected components
- Bipartite graph checking

**Implementation**:
```kotlin
fun bfs(graph: Array<MutableList<Int>>, start: Int): List<Int> {
    val n = graph.size
    val visited = BooleanArray(n)
    val result = mutableListOf<Int>()
    val queue = ArrayDeque<Int>()
    
    // Start BFS from start node
    queue.add(start)
    visited[start] = true
    
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        result.add(node)
        
        // Visit all neighbors
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true
                queue.add(neighbor)
            }
        }
    }
    
    return result
}

// BFS for shortest path in unweighted graph
fun shortestPathBFS(graph: Array<MutableList<Int>>, start: Int, end: Int): Int {
    val n = graph.size
    val visited = BooleanArray(n)
    val queue = ArrayDeque<Pair<Int, Int>>()  // (node, distance)
    
    queue.add(Pair(start, 0))
    visited[start] = true
    
    while (queue.isNotEmpty()) {
        val (node, dist) = queue.removeFirst()
        
        if (node == end) return dist
        
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                visited[neighbor] = true
                queue.add(Pair(neighbor, dist + 1))
            }
        }
    }
    
    return -1  // No path found
}
```

**Visual Example**:
```
Graph:      BFS from 0:
  0          Level 0: 0
 / \         Level 1: 1, 2
1   2        Level 2: 3, 4
|   |
3   4        Order: 0 → 1 → 2 → 3 → 4
```

---

### 2. Depth-First Search (DFS)

Explores as far as possible along each branch using recursion or stack.

**Properties**:
- Time: O(V + E)
- Space: O(V) for recursion stack

**Applications**:
- Cycle detection
- Topological sorting
- Finding connected components
- Path finding
- Maze solving

**Recursive Implementation**:
```kotlin
fun dfs(graph: Array<MutableList<Int>>, start: Int): List<Int> {
    val visited = BooleanArray(graph.size)
    val result = mutableListOf<Int>()
    
    fun dfsHelper(node: Int) {
        visited[node] = true
        result.add(node)
        
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                dfsHelper(neighbor)
            }
        }
    }
    
    dfsHelper(start)
    return result
}
```

**Iterative Implementation**:
```kotlin
fun dfsIterative(graph: Array<MutableList<Int>>, start: Int): List<Int> {
    val visited = BooleanArray(graph.size)
    val result = mutableListOf<Int>()
    val stack = ArrayDeque<Int>()
    
    stack.add(start)
    
    while (stack.isNotEmpty()) {
        val node = stack.removeLast()
        
        if (!visited[node]) {
            visited[node] = true
            result.add(node)
            
            // Add neighbors in reverse order to maintain left-to-right traversal
            for (i in graph[node].size - 1 downTo 0) {
                val neighbor = graph[node][i]
                if (!visited[neighbor]) {
                    stack.add(neighbor)
                }
            }
        }
    }
    
    return result
}
```

**Visual Example**:
```
Graph:      DFS from 0:
  0          Go deep: 0 → 1 → 3 (dead end)
 / \         Backtrack to 1 (no more)
1   2        Backtrack to 0
|   |        Continue: 0 → 2 → 4 (dead end)
3   4        
             Order: 0 → 1 → 3 → 2 → 4
```

---

## Shortest Path Algorithms {#shortest-path}

### 1. Dijkstra's Algorithm

Finds shortest path from source to all vertices in **weighted graph with non-negative weights**.

**Properties**:
- Time: O((V + E) log V) with priority queue
- Space: O(V)
- Does NOT work with negative weights

**Algorithm Steps**:
1. Initialize distances: source = 0, others = ∞
2. Use min-heap to always process nearest unvisited vertex
3. For each neighbor, relax edge if shorter path found
4. Repeat until all vertices processed

**Implementation**:
```kotlin
import java.util.PriorityQueue

data class Edge(val to: Int, val weight: Int)

fun dijkstra(graph: Array<MutableList<Edge>>, start: Int): IntArray {
    val n = graph.size
    val dist = IntArray(n) { Int.MAX_VALUE }
    val visited = BooleanArray(n)
    
    // Priority queue: (distance, node)
    val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
    
    dist[start] = 0
    pq.offer(Pair(0, start))
    
    while (pq.isNotEmpty()) {
        val (d, u) = pq.poll()
        
        if (visited[u]) continue
        visited[u] = true
        
        // Relax edges
        for (edge in graph[u]) {
            val v = edge.to
            val weight = edge.weight
            
            if (dist[u] + weight < dist[v]) {
                dist[v] = dist[u] + weight
                pq.offer(Pair(dist[v], v))
            }
        }
    }
    
    return dist
}
```

**Example**:
```
Graph:        Shortest paths from A:
    2         A to B: 2
  A → B       A to C: 3
  |3  |1      A to D: 3
  ↓   ↓
  C → D
    1
```

---

### 2. Bellman-Ford Algorithm

Finds shortest path with **negative weight edges**. Can detect negative cycles.

**Properties**:
- Time: O(V * E)
- Space: O(V)
- Works with negative weights
- Detects negative cycles

**Implementation**:
```kotlin
data class Edge(val from: Int, val to: Int, val weight: Int)

fun bellmanFord(vertices: Int, edges: List<Edge>, start: Int): IntArray? {
    val dist = IntArray(vertices) { Int.MAX_VALUE }
    dist[start] = 0
    
    // Relax all edges V-1 times
    repeat(vertices - 1) {
        for (edge in edges) {
            if (dist[edge.from] != Int.MAX_VALUE) {
                if (dist[edge.from] + edge.weight < dist[edge.to]) {
                    dist[edge.to] = dist[edge.from] + edge.weight
                }
            }
        }
    }
    
    // Check for negative cycles
    for (edge in edges) {
        if (dist[edge.from] != Int.MAX_VALUE) {
            if (dist[edge.from] + edge.weight < dist[edge.to]) {
                return null  // Negative cycle detected
            }
        }
    }
    
    return dist
}
```

---

### 3. Floyd-Warshall Algorithm

Finds shortest paths between **all pairs** of vertices.

**Properties**:
- Time: O(V³)
- Space: O(V²)
- Works with negative weights (but not negative cycles)
- Simple to implement

**Implementation**:
```kotlin
fun floydWarshall(graph: Array<IntArray>): Array<IntArray> {
    val n = graph.size
    val dist = Array(n) { i -> graph[i].clone() }
    
    // Try using each vertex as intermediate
    for (k in 0 until n) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (dist[i][k] != Int.MAX_VALUE && dist[k][j] != Int.MAX_VALUE) {
                    dist[i][j] = minOf(dist[i][j], dist[i][k] + dist[k][j])
                }
            }
        }
    }
    
    return dist
}
```

---

## Minimum Spanning Tree {#mst}

A **spanning tree** of a graph is a subgraph that:
- Connects all vertices
- Has no cycles
- Has V-1 edges (for V vertices)

A **Minimum Spanning Tree (MST)** is a spanning tree with minimum total edge weight.

### 1. Kruskal's Algorithm

**Approach**: Greedy - add edges in increasing weight order, skip if creates cycle

**Properties**:
- Time: O(E log E) for sorting
- Uses Union-Find data structure

**Implementation**:
```kotlin
class UnionFind(n: Int) {
    private val parent = IntArray(n) { it }
    private val rank = IntArray(n) { 0 }
    
    fun find(x: Int): Int {
        if (parent[x] != x) {
            parent[x] = find(parent[x])  // Path compression
        }
        return parent[x]
    }
    
    fun union(x: Int, y: Int): Boolean {
        val rootX = find(x)
        val rootY = find(y)
        
        if (rootX == rootY) return false  // Already in same set
        
        // Union by rank
        when {
            rank[rootX] < rank[rootY] -> parent[rootX] = rootY
            rank[rootX] > rank[rootY] -> parent[rootY] = rootX
            else -> {
                parent[rootY] = rootX
                rank[rootX]++
            }
        }
        return true
    }
}

data class Edge(val from: Int, val to: Int, val weight: Int)

fun kruskalMST(vertices: Int, edges: List<Edge>): List<Edge> {
    val mst = mutableListOf<Edge>()
    val uf = UnionFind(vertices)
    
    // Sort edges by weight
    val sortedEdges = edges.sortedBy { it.weight }
    
    for (edge in sortedEdges) {
        // Add edge if it doesn't create cycle
        if (uf.union(edge.from, edge.to)) {
            mst.add(edge)
            if (mst.size == vertices - 1) break  // MST complete
        }
    }
    
    return mst
}
```

---

### 2. Prim's Algorithm

**Approach**: Greedy - grow MST by adding minimum weight edge connecting tree to non-tree vertex

**Implementation**:
```kotlin
data class Edge(val to: Int, val weight: Int)

fun primMST(graph: Array<MutableList<Edge>>, start: Int = 0): List<Pair<Int, Int>> {
    val n = graph.size
    val inMST = BooleanArray(n)
    val mst = mutableListOf<Pair<Int, Int>>()  // (from, to)
    val pq = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })  // (weight, from, to)
    
    // Start with vertex 0
    inMST[start] = true
    for (edge in graph[start]) {
        pq.offer(Triple(edge.weight, start, edge.to))
    }
    
    while (pq.isNotEmpty() && mst.size < n - 1) {
        val (weight, from, to) = pq.poll()
        
        if (inMST[to]) continue  // Skip if already in MST
        
        // Add edge to MST
        inMST[to] = true
        mst.add(Pair(from, to))
        
        // Add all edges from new vertex
        for (edge in graph[to]) {
            if (!inMST[edge.to]) {
                pq.offer(Triple(edge.weight, to, edge.to))
            }
        }
    }
    
    return mst
}
```

---

## Topological Sorting {#topological-sort}

**Topological Sort**: Linear ordering of vertices in a DAG such that for every edge u → v, u comes before v.

**Applications**:
- Task scheduling with dependencies
- Course prerequisites
- Build systems
- Package managers

### Method 1: DFS-based

**Implementation**:
```kotlin
fun topologicalSortDFS(graph: Array<MutableList<Int>>): List<Int> {
    val n = graph.size
    val visited = BooleanArray(n)
    val stack = ArrayDeque<Int>()
    
    fun dfs(node: Int) {
        visited[node] = true
        
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                dfs(neighbor)
            }
        }
        
        stack.addLast(node)  // Add to stack after visiting all descendants
    }
    
    // Visit all vertices
    for (i in 0 until n) {
        if (!visited[i]) {
            dfs(i)
        }
    }
    
    // Return reverse order
    return stack.reversed()
}
```

### Method 2: Kahn's Algorithm (BFS-based)

**Implementation**:
```kotlin
fun topologicalSortKahn(graph: Array<MutableList<Int>>): List<Int> {
    val n = graph.size
    val inDegree = IntArray(n)
    val result = mutableListOf<Int>()
    val queue = ArrayDeque<Int>()
    
    // Calculate in-degrees
    for (u in 0 until n) {
        for (v in graph[u]) {
            inDegree[v]++
        }
    }
    
    // Add all vertices with in-degree 0
    for (i in 0 until n) {
        if (inDegree[i] == 0) {
            queue.add(i)
        }
    }
    
    // Process vertices
    while (queue.isNotEmpty()) {
        val u = queue.removeFirst()
        result.add(u)
        
        // Reduce in-degree of neighbors
        for (v in graph[u]) {
            inDegree[v]--
            if (inDegree[v] == 0) {
                queue.add(v)
            }
        }
    }
    
    // Check for cycle (if result.size < n, graph has cycle)
    return if (result.size == n) result else emptyList()
}
```

---

## Strongly Connected Components {#scc}

A **Strongly Connected Component (SCC)** is a maximal set of vertices where every vertex is reachable from every other vertex.

### Kosaraju's Algorithm

**Steps**:
1. Do DFS on original graph, store finish times
2. Reverse the graph
3. Do DFS on reversed graph in order of decreasing finish times
4. Each DFS tree is an SCC

**Implementation**:
```kotlin
fun kosarajuSCC(graph: Array<MutableList<Int>>): List<List<Int>> {
    val n = graph.size
    val visited = BooleanArray(n)
    val stack = ArrayDeque<Int>()
    
    // Step 1: Fill stack with finish times
    fun dfs1(node: Int) {
        visited[node] = true
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                dfs1(neighbor)
            }
        }
        stack.addLast(node)
    }
    
    for (i in 0 until n) {
        if (!visited[i]) {
            dfs1(i)
        }
    }
    
    // Step 2: Reverse graph
    val reversedGraph = Array(n) { mutableListOf<Int>() }
    for (u in 0 until n) {
        for (v in graph[u]) {
            reversedGraph[v].add(u)
        }
    }
    
    // Step 3: DFS on reversed graph
    visited.fill(false)
    val sccs = mutableListOf<List<Int>>()
    
    fun dfs2(node: Int, component: MutableList<Int>) {
        visited[node] = true
        component.add(node)
        for (neighbor in reversedGraph[node]) {
            if (!visited[neighbor]) {
                dfs2(neighbor, component)
            }
        }
    }
    
    while (stack.isNotEmpty()) {
        val node = stack.removeLast()
        if (!visited[node]) {
            val component = mutableListOf<Int>()
            dfs2(node, component)
            sccs.add(component)
        }
    }
    
    return sccs
}
```

---

## Advanced Graph Algorithms {#advanced}

### 1. Cycle Detection

**Undirected Graph (DFS)**:
```kotlin
fun hasCycleUndirected(graph: Array<MutableList<Int>>): Boolean {
    val visited = BooleanArray(graph.size)
    
    fun dfs(node: Int, parent: Int): Boolean {
        visited[node] = true
        
        for (neighbor in graph[node]) {
            if (!visited[neighbor]) {
                if (dfs(neighbor, node)) return true
            } else if (neighbor != parent) {
                return true  // Back edge found (cycle)
            }
        }
        return false
    }
    
    for (i in graph.indices) {
        if (!visited[i] && dfs(i, -1)) {
            return true
        }
    }
    return false
}
```

**Directed Graph (DFS with colors)**:
```kotlin
fun hasCycleDirected(graph: Array<MutableList<Int>>): Boolean {
    val WHITE = 0  // Not visited
    val GRAY = 1   // Being processed
    val BLACK = 2  // Fully processed
    
    val color = IntArray(graph.size)
    
    fun dfs(node: Int): Boolean {
        color[node] = GRAY
        
        for (neighbor in graph[node]) {
            if (color[neighbor] == GRAY) return true  // Back edge (cycle)
            if (color[neighbor] == WHITE && dfs(neighbor)) return true
        }
        
        color[node] = BLACK
        return false
    }
    
    for (i in graph.indices) {
        if (color[i] == WHITE && dfs(i)) {
            return true
        }
    }
    return false
}
```

### 2. Bipartite Graph Check

A graph is bipartite if vertices can be colored with two colors such that no adjacent vertices have the same color.

```kotlin
fun isBipartite(graph: Array<MutableList<Int>>): Boolean {
    val n = graph.size
    val color = IntArray(n) { -1 }  // -1: uncolored, 0: color A, 1: color B
    
    fun bfs(start: Int): Boolean {
        val queue = ArrayDeque<Int>()
        queue.add(start)
        color[start] = 0
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            val nextColor = 1 - color[node]
            
            for (neighbor in graph[node]) {
                if (color[neighbor] == -1) {
                    color[neighbor] = nextColor
                    queue.add(neighbor)
                } else if (color[neighbor] != nextColor) {
                    return false  // Conflict: adjacent nodes have same color
                }
            }
        }
        return true
    }
    
    // Check all components
    for (i in 0 until n) {
        if (color[i] == -1 && !bfs(i)) {
            return false
        }
    }
    return true
}
```

---

## Common Graph Problems {#common-problems}

### Easy Problems
- Number of Islands (DFS/BFS)
- Clone Graph
- Find if Path Exists in Graph
- All Paths from Source to Target

### Medium Problems
- Course Schedule (Topological Sort + Cycle Detection)
- Network Delay Time (Dijkstra)
- Cheapest Flights Within K Stops (Modified Dijkstra/Bellman-Ford)
- Graph Valid Tree
- Number of Connected Components
- Minimum Height Trees

### Hard Problems
- Word Ladder
- Alien Dictionary
- Critical Connections (Bridges)
- Redundant Connection
- Shortest Path Visiting All Nodes

---

## Implementation Tips {#implementation-tips}

### 1. Choosing the Right Representation

| Graph Type | Best Representation |
|------------|---------------------|
| Dense graph (many edges) | Adjacency Matrix |
| Sparse graph (few edges) | Adjacency List |
| Need all edges | Edge List |
| Weighted graph | Adjacency List with weights |

### 2. Common Patterns

**Pattern 1: Connectivity**
```kotlin
// Use DFS/BFS to find connected components
```

**Pattern 2: Shortest Path**
```kotlin
// Unweighted: BFS
// Weighted (non-negative): Dijkstra
// Weighted (with negative): Bellman-Ford
// All pairs: Floyd-Warshall
```

**Pattern 3: Cycle Detection**
```kotlin
// Undirected: DFS with parent tracking
// Directed: DFS with color marking
```

### 3. Debugging Tips

1. **Draw the graph**: Visualize small examples
2. **Check edge cases**: Empty graph, single node, disconnected components
3. **Verify visited array**: Ensure it's properly initialized
4. **Test on simple graphs**: Start with linear, tree, complete graphs

### 4. Common Mistakes

❌ Forgetting to mark nodes as visited
❌ Not handling disconnected graphs
❌ Confusing directed vs undirected
❌ Integer overflow in distance calculations
❌ Not checking for negative cycles in Bellman-Ford

---

## Conclusion

Graph algorithms are fundamental to computer science and have numerous real-world applications:

- **Social Networks**: Friend recommendations, influence propagation
- **Maps/Navigation**: Shortest routes, traffic optimization
- **Web**: PageRank, web crawling
- **Networks**: Routing protocols, network flow
- **Dependencies**: Build systems, task scheduling
- **Games**: Pathfinding, AI decision trees

**Key Takeaways**:
1. Choose appropriate representation (matrix vs list)
2. Master BFS and DFS - they're building blocks
3. Understand when to use each shortest path algorithm
4. Practice with different graph types
5. Always consider edge cases and disconnected components

Happy graph traversing! 🚀
