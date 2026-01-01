# Data Structures Overview

## Table of Contents
1. [Introduction](#introduction)
2. [Arrays and Lists](#arrays-lists)
3. [Stacks and Queues](#stacks-queues)
4. [Hash Tables](#hash-tables)
5. [Trees](#trees)
6. [Graphs](#graphs)
7. [Heaps](#heaps)
8. [Comparison and Selection](#comparison)

---

## Introduction {#introduction}

Data structures are ways to organize and store data for efficient access and modification. Choosing the right data structure is crucial for performance.

### Why Data Structures Matter
- ✓ Affect algorithm efficiency
- ✓ Enable specific operations
- ✓ Determine space-time tradeoffs
- ✓ Foundation of software design

---

## Arrays and Lists {#arrays-lists}

### Array
Fixed-size, contiguous memory allocation.

**Operations**:
- Access: O(1)
- Search: O(n)
- Insert/Delete: O(n)

**Kotlin**:
```kotlin
val array = intArrayOf(1, 2, 3, 4, 5)
val element = array[2]  // O(1) access
```

**Use When**:
- Size known in advance
- Need fast random access
- Memory efficiency important

### ArrayList/Dynamic Array
Resizable array with amortized O(1) append.

**Operations**:
- Access: O(1)
- Append: O(1) amortized
- Insert/Delete: O(n)

**Kotlin**:
```kotlin
val list = mutableListOf(1, 2, 3)
list.add(4)  // O(1) amortized
list[0] = 10  // O(1) access
```

**Use When**:
- Size unknown or changes
- Need fast access by index
- Frequent additions at end

### LinkedList
Nodes with data and references to next/previous.

**Operations**:
- Access: O(n)
- Insert at head: O(1)
- Insert/Delete at known position: O(1)
- Search: O(n)

**Use When**:
- Frequent insertions/deletions
- Don't need random access
- Building queues/stacks

---

## Stacks and Queues {#stacks-queues}

### Stack
Last-In-First-Out (LIFO) structure.

**Operations**: All O(1)
- push(): Add to top
- pop(): Remove from top
- peek(): View top

**Kotlin**:
```kotlin
val stack = ArrayDeque<Int>()
stack.addFirst(1)  // push
stack.removeFirst()  // pop
stack.first()  // peek
```

**Use Cases**:
- Function call stack
- Undo operations
- Expression evaluation
- Depth-first search

### Queue
First-In-First-Out (FIFO) structure.

**Operations**: All O(1)
- enqueue(): Add to back
- dequeue(): Remove from front
- peek(): View front

**Kotlin**:
```kotlin
val queue = ArrayDeque<Int>()
queue.addLast(1)  // enqueue
queue.removeFirst()  // dequeue
queue.first()  // peek
```

**Use Cases**:
- Task scheduling
- Breadth-first search
- Buffer management
- Print queue

---

## Hash Tables {#hash-tables}

### HashMap
Key-value pairs with hashing for fast lookup.

**Operations**: Average O(1), Worst O(n)
- Get: O(1)
- Put: O(1)
- Delete: O(1)

**Kotlin**:
```kotlin
val map = hashMapOf<String, Int>()
map["key"] = 42  // O(1)
val value = map["key"]  // O(1)
```

**Use When**:
- Need fast lookups
- Key-value associations
- Caching
- Counting occurrences

### HashSet
Unique elements with fast membership testing.

**Operations**: Average O(1)
- Add: O(1)
- Contains: O(1)
- Remove: O(1)

**Kotlin**:
```kotlin
val set = hashSetOf(1, 2, 3)
set.add(4)  // O(1)
println(3 in set)  // O(1)
```

**Use When**:
- Need unique elements
- Fast membership testing
- Remove duplicates

---

## Trees {#trees}

### Binary Tree
Each node has at most two children.

**Types**:
- Full: All nodes have 0 or 2 children
- Complete: All levels filled except possibly last
- Perfect: All levels completely filled

### Binary Search Tree (BST)
Ordered binary tree: left < parent < right.

**Operations**: Average O(log n), Worst O(n)
- Search: O(log n)
- Insert: O(log n)
- Delete: O(log n)

**Use When**:
- Need sorted data
- Range queries
- Fast search in ordered data

### Balanced Trees
AVL, Red-Black trees maintain O(log n) operations.

**Guaranteed**: O(log n) for all operations

**Use When**:
- Need guaranteed performance
- Many insertions/deletions
- Self-balancing required

---

## Graphs {#graphs}

### Graph Representation

#### Adjacency List
List of neighbors for each vertex.

**Space**: O(V + E)

**Kotlin**:
```kotlin
val graph = mutableMapOf<Int, MutableList<Int>>()
graph[1] = mutableListOf(2, 3)
graph[2] = mutableListOf(1, 4)
```

**Use When**:
- Sparse graphs
- Need to iterate neighbors
- Memory efficiency important

#### Adjacency Matrix
2D array indicating edges.

**Space**: O(V²)

**Kotlin**:
```kotlin
val matrix = Array(n) { BooleanArray(n) }
matrix[1][2] = true  // Edge from 1 to 2
```

**Use When**:
- Dense graphs
- Need fast edge existence check
- Many edge queries

### Traversal Algorithms

#### Depth-First Search (DFS)
Explore as far as possible before backtracking.

**Time**: O(V + E)  
**Space**: O(V)

```kotlin
fun dfs(node: Int, visited: MutableSet<Int>, graph: Map<Int, List<Int>>) {
    visited.add(node)
    for (neighbor in graph[node] ?: emptyList()) {
        if (neighbor !in visited) {
            dfs(neighbor, visited, graph)
        }
    }
}
```

#### Breadth-First Search (BFS)
Explore all neighbors before going deeper.

**Time**: O(V + E)  
**Space**: O(V)

```kotlin
fun bfs(start: Int, graph: Map<Int, List<Int>>) {
    val queue = ArrayDeque<Int>()
    val visited = mutableSetOf<Int>()
    
    queue.add(start)
    visited.add(start)
    
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        for (neighbor in graph[node] ?: emptyList()) {
            if (neighbor !in visited) {
                queue.add(neighbor)
                visited.add(neighbor)
            }
        }
    }
}
```

---

## Heaps {#heaps}

### Binary Heap
Complete binary tree with heap property.

**Types**:
- Max Heap: Parent ≥ children
- Min Heap: Parent ≤ children

**Operations**:
- Insert: O(log n)
- Get min/max: O(1)
- Remove min/max: O(log n)

**Kotlin**:
```kotlin
val minHeap = PriorityQueue<Int>()
minHeap.add(5)  // O(log n)
val min = minHeap.peek()  // O(1)
minHeap.poll()  // O(log n)
```

**Use Cases**:
- Priority queues
- Heap sort
- Finding k largest/smallest
- Scheduling algorithms

---

## Comparison and Selection {#comparison}

### Quick Reference Table

| Data Structure | Access | Search | Insert | Delete | Space |
|----------------|--------|--------|--------|--------|-------|
| Array | O(1) | O(n) | O(n) | O(n) | O(n) |
| ArrayList | O(1) | O(n) | O(n) | O(n) | O(n) |
| LinkedList | O(n) | O(n) | O(1)* | O(1)* | O(n) |
| Stack | O(n) | O(n) | O(1) | O(1) | O(n) |
| Queue | O(n) | O(n) | O(1) | O(1) | O(n) |
| HashMap | - | O(1)† | O(1)† | O(1)† | O(n) |
| HashSet | - | O(1)† | O(1)† | O(1)† | O(n) |
| BST | O(log n)† | O(log n)† | O(log n)† | O(log n)† | O(n) |
| Heap | - | O(n) | O(log n) | O(log n) | O(n) |

\* At known position  
† Average case

### Selection Guide

**Need fast access by index?**
→ Array, ArrayList

**Frequent insertions/deletions?**
→ LinkedList, Balanced Tree

**Need fast lookups?**
→ HashMap, HashSet

**Need sorted order?**
→ BST, Sorted Array

**Need min/max quickly?**
→ Heap, Priority Queue

**LIFO operations?**
→ Stack

**FIFO operations?**
→ Queue

**Graph representation?**
→ Adjacency List (sparse), Matrix (dense)

---

## Key Takeaways

1. ✓ Choose data structure based on operations needed
2. ✓ Arrays for fast index access
3. ✓ Lists for flexible size
4. ✓ Hash tables for fast lookups
5. ✓ Trees for sorted data
6. ✓ Heaps for priority access
7. ✓ Graphs for relationships
8. ✓ Consider time-space tradeoffs
