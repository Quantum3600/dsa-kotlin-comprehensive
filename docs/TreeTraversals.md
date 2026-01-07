# Tree Traversals Guide - Comprehensive Theory

## Table of Contents
1. [Introduction to Trees](#introduction)
2. [Tree Terminology](#terminology)
3. [Depth-First Search (DFS)](#dfs)
4. [Breadth-First Search (BFS)](#bfs)
5. [Traversal Patterns](#patterns)
6. [Recursive vs Iterative](#recursive-vs-iterative)
7. [Time and Space Complexity](#complexity)
8. [Common Problems](#problems)
9. [Advanced Techniques](#advanced)
10. [Best Practices](#best-practices)

---

## Introduction to Trees {#introduction}

### What is a Tree?

A **tree** is a hierarchical data structure consisting of nodes connected by edges. It's an acyclic connected graph. 

```
         1           ← Root
       /   \
      2     3        ← Level 1
     / \     \
    4   5     6      ← Level 2 (Leaves)
```

### Why Tree Traversal? 

Tree traversal is the process of visiting each node in a tree exactly once in a systematic way. 

**Use Cases**:
- Search for a value
- Calculate tree properties (height, size)
- Print all elements
- Copy or serialize a tree
- Evaluate expressions
- File system navigation

---

## Tree Terminology {#terminology}

### Basic Terms

```
         A           ← Root (no parent)
       /   \
      B     C        
     / \     \
    D   E     F      ← Leaves (no children)
    
Tree Components:
- Node: Element in tree (A, B, C, etc.)
- Edge: Connection between nodes
- Root: Top node (A)
- Leaf: Node with no children (D, E, F)
- Parent: Node with children (B is parent of D and E)
- Child: Node with a parent (B is child of A)
- Sibling:  Nodes with same parent (B and C)
- Subtree: Tree formed by node and descendants
```

### Tree Properties

| Property | Definition | Example |
|----------|------------|---------|
| **Height** | Longest path from root to leaf | 2 |
| **Depth** | Distance from root to node | Depth of D = 2 |
| **Level** | Depth + 1 | Level of D = 3 |
| **Size** | Total number of nodes | 6 |
| **Degree** | Number of children | Degree of B = 2 |

### Binary Tree Node

```kotlin
class TreeNode(
    var value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)
```

---

## Depth-First Search (DFS) {#dfs}

**DFS** explores as far as possible along each branch before backtracking.

### Three DFS Orders

```
Tree: 
      1
     / \
    2   3
   / \
  4   5

Inorder (Left-Root-Right):   4, 2, 5, 1, 3
Preorder (Root-Left-Right):  1, 2, 4, 5, 3
Postorder (Left-Right-Root): 4, 5, 2, 3, 1
```

---

### 1. Inorder Traversal (Left-Root-Right)

**Order**: Visit left subtree → Visit root → Visit right subtree

**Use Cases**:
- Get sorted elements from BST
- Expression tree evaluation (infix notation)

#### Recursive Implementation

```kotlin
fun inorderTraversal(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    
    fun inorder(node: TreeNode?) {
        if (node == null) return
        
        inorder(node.left)        // Left
        result.add(node.value)    // Root
        inorder(node.right)       // Right
    }
    
    inorder(root)
    return result
}
```

**Example**:
```
      4
     / \
    2   6
   / \ / \
  1  3 5  7

Inorder: 1, 2, 3, 4, 5, 6, 7 (sorted!)
```

#### Iterative Implementation

```kotlin
fun inorderIterative(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    val stack = ArrayDeque<TreeNode>()
    var current = root
    
    while (current != null || stack.isNotEmpty()) {
        // Go to leftmost node
        while (current != null) {
            stack.addFirst(current)
            current = current.left
        }
        
        // Process node
        current = stack.removeFirst()
        result.add(current.value)
        
        // Move to right subtree
        current = current.right
    }
    
    return result
}
```

**Trace**:
```
Tree:  1 → 2 → 3

Stack states:
[1]           → Push 1, go left
[1,2]         → Push 2, go left (null)
[1]   Add 2   → Pop 2, add to result
[]    Add 1   → Pop 1, add to result
[3]           → Push 3 (right of 1)
[]    Add 3   → Pop 3, add to result

Result: [2, 1, 3]
```

---

### 2. Preorder Traversal (Root-Left-Right)

**Order**: Visit root → Visit left subtree → Visit right subtree

**Use Cases**:
- Create copy of tree
- Expression tree evaluation (prefix notation)
- Serialization

#### Recursive Implementation

```kotlin
fun preorderTraversal(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    
    fun preorder(node: TreeNode?) {
        if (node == null) return
        
        result.add(node. value)    // Root
        preorder(node.left)       // Left
        preorder(node.right)      // Right
    }
    
    preorder(root)
    return result
}
```

**Example**:
```
      1
     / \
    2   3
   / \
  4   5

Preorder: 1, 2, 4, 5, 3
(Root first, then children)
```

#### Iterative Implementation

```kotlin
fun preorderIterative(root: TreeNode? ): List<Int> {
    if (root == null) return emptyList()
    
    val result = mutableListOf<Int>()
    val stack = ArrayDeque<TreeNode>()
    stack.addFirst(root)
    
    while (stack.isNotEmpty()) {
        val node = stack.removeFirst()
        result.add(node.value)
        
        // Push right first so left is processed first
        node.right?.let { stack.addFirst(it) }
        node.left?.let { stack.addFirst(it) }
    }
    
    return result
}
```

---

### 3. Postorder Traversal (Left-Right-Root)

**Order**: Visit left subtree → Visit right subtree → Visit root

**Use Cases**: 
- Delete tree (delete children before parent)
- Expression tree evaluation (postfix notation)
- Calculate directory sizes

#### Recursive Implementation

```kotlin
fun postorderTraversal(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    
    fun postorder(node: TreeNode?) {
        if (node == null) return
        
        postorder(node. left)      // Left
        postorder(node.right)     // Right
        result.add(node.value)    // Root
    }
    
    postorder(root)
    return result
}
```

**Example**:
```
      1
     / \
    2   3
   / \
  4   5

Postorder:  4, 5, 2, 3, 1
(Children before parent)
```

#### Iterative Implementation

```kotlin
fun postorderIterative(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    
    val result = mutableListOf<Int>()
    val stack = ArrayDeque<TreeNode>()
    var lastVisited:  TreeNode? = null
    var current:  TreeNode? = root
    
    while (stack.isNotEmpty() || current != null) {
        // Go to leftmost node
        while (current != null) {
            stack.addFirst(current)
            current = current.left
        }
        
        // Peek at top of stack
        current = stack. first()
        
        // If no right child or right already processed
        if (current. right == null || current.right == lastVisited) {
            result.add(current.value)
            stack.removeFirst()
            lastVisited = current
            current = null  // Don't traverse left again
        } else {
            current = current.right
        }
    }
    
    return result
}
```

**Alternative:  Two-Stack Method**
```kotlin
fun postorderTwoStacks(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    
    val result = ArrayDeque<Int>()
    val stack = ArrayDeque<TreeNode>()
    stack.addFirst(root)
    
    while (stack.isNotEmpty()) {
        val node = stack.removeFirst()
        result.addFirst(node.value)  // Add to front
        
        // Push left first so right is processed first
        node.left?.let { stack. addFirst(it) }
        node.right?.let { stack. addFirst(it) }
    }
    
    return result. toList()
}
```

---

### DFS Traversal Comparison

| Traversal | Order | Use Case | BST Result |
|-----------|-------|----------|------------|
| **Inorder** | Left-Root-Right | Sorted values | Ascending |
| **Preorder** | Root-Left-Right | Copy tree | Root first |
| **Postorder** | Left-Right-Root | Delete tree | Leaves first |

---

## Breadth-First Search (BFS) {#bfs}

**BFS** explores all nodes at current level before moving to next level.

### Level Order Traversal

```
Tree: 
      1          ← Level 0
     / \
    2   3        ← Level 1
   / \   \
  4   5   6      ← Level 2

BFS:  1, 2, 3, 4, 5, 6
```

### Implementation

```kotlin
fun levelOrder(root: TreeNode?): List<List<Int>> {
    if (root == null) return emptyList()
    
    val result = mutableListOf<List<Int>>()
    val queue = ArrayDeque<TreeNode>()
    queue.addLast(root)
    
    while (queue.isNotEmpty()) {
        val levelSize = queue.size
        val currentLevel = mutableListOf<Int>()
        
        repeat(levelSize) {
            val node = queue.removeFirst()
            currentLevel.add(node. value)
            
            node. left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
        
        result.add(currentLevel)
    }
    
    return result
}
```

**Trace**:
```
Tree:      1
         / \
        2   3

Initial: queue = [1]

Iteration 1 (Level 0):
  levelSize = 1
  Process 1 → Add to result: [1]
  Add children:  queue = [2, 3]

Iteration 2 (Level 1):
  levelSize = 2
  Process 2 → Add to result: [2]
  Process 3 → Add to result:  [3]
  queue = []

Result: [[1], [2, 3]]
```

### BFS Variations

#### 1. Zigzag Level Order

```kotlin
fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
    if (root == null) return emptyList()
    
    val result = mutableListOf<List<Int>>()
    val queue = ArrayDeque<TreeNode>()
    queue.addLast(root)
    var leftToRight = true
    
    while (queue.isNotEmpty()) {
        val levelSize = queue.size
        val currentLevel = mutableListOf<Int>()
        
        repeat(levelSize) {
            val node = queue.removeFirst()
            currentLevel.add(node.value)
            
            node.left?.let { queue.addLast(it) }
            node.right?. let { queue.addLast(it) }
        }
        
        if (! leftToRight) {
            currentLevel.reverse()
        }
        
        result.add(currentLevel)
        leftToRight = !leftToRight
    }
    
    return result
}

// Example: 
//       1
//      / \
//     2   3
//    / \   \
//   4   5   6
//
// Result: [[1], [3, 2], [4, 5, 6]]
```

#### 2. Right Side View

```kotlin
fun rightSideView(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    
    val result = mutableListOf<Int>()
    val queue = ArrayDeque<TreeNode>()
    queue.addLast(root)
    
    while (queue.isNotEmpty()) {
        val levelSize = queue.size
        
        repeat(levelSize) { i ->
            val node = queue. removeFirst()
            
            // Add rightmost element of each level
            if (i == levelSize - 1) {
                result.add(node.value)
            }
            
            node. left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
    }
    
    return result
}
```

#### 3. Vertical Order Traversal

```kotlin
fun verticalOrder(root: TreeNode? ): List<List<Int>> {
    if (root == null) return emptyList()
    
    val map = mutableMapOf<Int, MutableList<Int>>()
    val queue = ArrayDeque<Pair<TreeNode, Int>>()
    queue.addLast(Pair(root, 0))
    
    var minCol = 0
    var maxCol = 0
    
    while (queue.isNotEmpty()) {
        val (node, col) = queue.removeFirst()
        
        map.getOrPut(col) { mutableListOf() }.add(node.value)
        minCol = minOf(minCol, col)
        maxCol = maxOf(maxCol, col)
        
        node.left?.let { queue.addLast(Pair(it, col - 1)) }
        node.right?. let { queue.addLast(Pair(it, col + 1)) }
    }
    
    return (minCol..maxCol).map { map[it] ?: emptyList() }
}
```

---

## Traversal Patterns {#patterns}

### 1. Path Problems

Finding or validating paths in tree. 

```kotlin
// Has Path Sum
fun hasPathSum(root:  TreeNode?, targetSum: Int): Boolean {
    if (root == null) return false
    
    if (root.left == null && root.right == null) {
        return root.value == targetSum
    }
    
    val remaining = targetSum - root.value
    return hasPathSum(root.left, remaining) || 
           hasPathSum(root. right, remaining)
}

// All Paths from Root to Leaves
fun binaryTreePaths(root: TreeNode?): List<String> {
    val result = mutableListOf<String>()
    
    fun dfs(node: TreeNode?, path: MutableList<Int>) {
        if (node == null) return
        
        path.add(node.value)
        
        if (node.left == null && node.right == null) {
            result.add(path.joinToString("->"))
        } else {
            dfs(node.left, path)
            dfs(node.right, path)
        }
        
        path.removeAt(path.size - 1)  // Backtrack
    }
    
    dfs(root, mutableListOf())
    return result
}
```

---

### 2. Tree Properties

Calculate height, depth, diameter, etc.

```kotlin
// Tree Height
fun maxDepth(root: TreeNode? ): Int {
    if (root == null) return 0
    return 1 + maxOf(maxDepth(root.left), maxDepth(root.right))
}

// Tree Diameter (longest path between any two nodes)
fun diameterOfBinaryTree(root: TreeNode?): Int {
    var diameter = 0
    
    fun height(node: TreeNode? ): Int {
        if (node == null) return 0
        
        val leftHeight = height(node.left)
        val rightHeight = height(node.right)
        
        // Update diameter:  path through current node
        diameter = maxOf(diameter, leftHeight + rightHeight)
        
        return 1 + maxOf(leftHeight, rightHeight)
    }
    
    height(root)
    return diameter
}

// Check if Balanced
fun isBalanced(root: TreeNode?): Boolean {
    fun height(node: TreeNode?): Int {
        if (node == null) return 0
        
        val leftHeight = height(node.left)
        if (leftHeight == -1) return -1
        
        val rightHeight = height(node.right)
        if (rightHeight == -1) return -1
        
        if (Math.abs(leftHeight - rightHeight) > 1) return -1
        
        return 1 + maxOf(leftHeight, rightHeight)
    }
    
    return height(root) != -1
}
```

---

### 3. Tree Construction

Build tree from traversals.

```kotlin
// Construct from Preorder and Inorder
fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
    if (preorder.isEmpty()) return null
    
    val rootValue = preorder[0]
    val root = TreeNode(rootValue)
    
    val rootIndex = inorder.indexOf(rootValue)
    
    val leftInorder = inorder.sliceArray(0 until rootIndex)
    val rightInorder = inorder.sliceArray(rootIndex + 1 until inorder.size)
    
    val leftPreorder = preorder.sliceArray(1.. leftInorder.size)
    val rightPreorder = preorder.sliceArray(leftInorder.size + 1 until preorder. size)
    
    root.left = buildTree(leftPreorder, leftInorder)
    root.right = buildTree(rightPreorder, rightInorder)
    
    return root
}
```

---

### 4. Lowest Common Ancestor (LCA)

```kotlin
fun lowestCommonAncestor(
    root: TreeNode?, 
    p: TreeNode?, 
    q: TreeNode?
): TreeNode? {
    if (root == null || root == p || root == q) return root
    
    val left = lowestCommonAncestor(root.left, p, q)
    val right = lowestCommonAncestor(root.right, p, q)
    
    return when {
        left != null && right != null -> root  // Found both sides
        left != null -> left                   // Both on left
        else -> right                          // Both on right
    }
}
```

---

## Recursive vs Iterative {#recursive-vs-iterative}

### Recursive Approach

**Pros**:
- ✓ Clean and intuitive code
- ✓ Naturally fits tree structure
- ✓ Easy to implement

**Cons**:
- ✗ Stack overflow risk with deep trees
- ✗ Recursion overhead
- ✗ Harder to control execution

```kotlin
// Recursive:  Clean and simple
fun sumRecursive(root: TreeNode? ): Int {
    if (root == null) return 0
    return root.value + sumRecursive(root.left) + sumRecursive(root. right)
}
```

---

### Iterative Approach

**Pros**:
- ✓ No stack overflow
- ✓ More control over execution
- ✓ Better for very deep trees

**Cons**: 
- ✗ More complex code
- ✗ Requires explicit stack/queue
- ✗ Less intuitive

```kotlin
// Iterative: More control
fun sumIterative(root: TreeNode?): Int {
    if (root == null) return 0
    
    var sum = 0
    val stack = ArrayDeque<TreeNode>()
    stack.addFirst(root)
    
    while (stack.isNotEmpty()) {
        val node = stack.removeFirst()
        sum += node. value
        
        node.right?.let { stack.addFirst(it) }
        node.left?.let { stack.addFirst(it) }
    }
    
    return sum
}
```

---

## Time and Space Complexity {#complexity}

### Time Complexity

All traversals visit each node exactly once. 

| Traversal | Time Complexity |
|-----------|----------------|
| Inorder | O(n) |
| Preorder | O(n) |
| Postorder | O(n) |
| Level Order | O(n) |

**Reasoning**: n nodes, each visited once → O(n)

---

### Space Complexity

| Approach | Best Case | Worst Case | Average (Balanced) |
|----------|-----------|------------|--------------------|
| **Recursive** | O(log n) | O(n) | O(log n) |
| **Iterative** | O(log n) | O(n) | O(log n) |

**Best Case** (Balanced tree): Stack/queue height = O(log n)
**Worst Case** (Skewed tree): Stack/queue height = O(n)

```
Balanced Tree:         Skewed Tree:
      1                   1
     / \                   \
    2   3                   2
   / \                       \
  4   5                       3
                               \
Height: O(log n)                4

                         Height: O(n)
```

---

## Common Problems {#problems}

### Beginner
1. Maximum Depth of Binary Tree
2. Invert Binary Tree
3. Same Tree
4. Symmetric Tree
5. Binary Tree Paths

### Intermediate
1. Validate Binary Search Tree
2.  Lowest Common Ancestor
3. Binary Tree Level Order Traversal
4. Binary Tree Right Side View
5. Path Sum II

### Advanced
1. Serialize and Deserialize Binary Tree
2. Binary Tree Maximum Path Sum
3. Vertical Order Traversal
4. Recover Binary Search Tree
5. Count Complete Tree Nodes

---

## Advanced Techniques {#advanced}

### Morris Traversal (O(1) Space)

Inorder traversal without recursion or stack.

```kotlin
fun morrisInorder(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    var current = root
    
    while (current != null) {
        if (current.left == null) {
            // No left subtree:  visit and go right
            result.add(current.value)
            current = current.right
        } else {
            // Find inorder predecessor
            var predecessor = current.left
            while (predecessor?. right != null && predecessor.right != current) {
                predecessor = predecessor.right
            }
            
            if (predecessor?.right == null) {
                // Create thread
                predecessor?. right = current
                current = current.left
            } else {
                // Remove thread and visit
                predecessor.right = null
                result.add(current. value)
                current = current. right
            }
        }
    }
    
    return result
}
```

**Complexity**: Time O(n), Space O(1) 🎉

---

### DFS with State Tracking

```kotlin
fun sumOfLeftLeaves(root: TreeNode?): Int {
    fun dfs(node: TreeNode?, isLeft: Boolean): Int {
        if (node == null) return 0
        
        // Leaf node and it's a left child
        if (node.left == null && node.right == null && isLeft) {
            return node.value
        }
        
        return dfs(node.left, true) + dfs(node.right, false)
    }
    
    return dfs(root, false)
}
```

---

## Best Practices {#best-practices}

### 1. Choose Right Traversal

```kotlin
// ✓ Use inorder for BST sorted output
fun inorderBST(root: TreeNode?) {
    // Gives sorted values
}

// ✓ Use preorder for tree serialization
fun serialize(root: TreeNode?): String {
    // Root first helps reconstruction
}

// ✓ Use postorder for tree deletion
fun deleteTree(root: TreeNode?) {
    // Delete children before parent
}

// ✓ Use BFS for level-wise operations
fun levelOrder(root: TreeNode?) {
    // Process by levels
}
```

---

### 2. Handle Edge Cases

```kotlin
fun traverse(root: TreeNode?): List<Int> {
    // ✓ Check null root
    if (root == null) return emptyList()
    
    // ✓ Check single node
    if (root.left == null && root.right == null) {
        return listOf(root.value)
    }
    
    // Process tree
    // ... 
}
```

---

### 3. Use Helper Functions

```kotlin
class Solution {
    // ✓ Separate public and private functions
    fun traverse(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        dfs(root, result)
        return result
    }
    
    private fun dfs(node:  TreeNode?, result: MutableList<Int>) {
        if (node == null) return
        result.add(node.value)
        dfs(node.left, result)
        dfs(node.right, result)
    }
}
```

---

### 4. Optimize Space

```kotlin
// ✗ Creating new lists (O(n) space)
fun traverse(root: TreeNode?): List<Int> {
    if (root == null) return emptyList()
    return traverse(root.left) + listOf(root.value) + traverse(root.right)
}

// ✓ Reusing single list (O(h) space)
fun traverse(root: TreeNode?): List<Int> {
    val result = mutableListOf<Int>()
    
    fun dfs(node: TreeNode?) {
        if (node == null) return
        dfs(node.left)
        result.add(node.value)
        dfs(node.right)
    }
    
    dfs(root)
    return result
}
```

---

## Summary

Tree traversal is fundamental to working with tree data structures. 

**Key Takeaways**:
- ✓ **DFS** (Inorder, Preorder, Postorder): Use stack, explore depth
- ✓ **BFS** (Level Order): Use queue, explore breadth
- ✓ **Recursive**: Clean but uses call stack
- ✓ **Iterative**:  Explicit stack/queue, more control
- ✓ **Time**: O(n) for all traversals
- ✓ **Space**: O(h) where h is height

**Decision Tree**:
```
Need sorted BST values?
  → Use Inorder

Need to copy tree?
  → Use Preorder

Need to delete tree? 
  → Use Postorder

Need level-wise processing?
  → Use Level Order (BFS)

Need to find shortest path?
  → Use BFS

Need to explore all paths?
  → Use DFS
```

**Practice Makes Perfect**!  Start with recursive solutions, then learn iterative versions.  Master these patterns and you'll solve most tree problems!  🌲

Happy Traversing! 🚀
