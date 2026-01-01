/**
 * ============================================================================
 * CONCEPT: Binary Tree - Introduction and Representation
 * DIFFICULTY: Easy
 * CATEGORY: Trees - Binary Tree Basics
 * ============================================================================
 * 
 * WHAT IS A BINARY TREE?
 * A binary tree is a hierarchical data structure where each node has at most
 * two children, referred to as the left child and the right child.
 * 
 * STRUCTURE:
 * ```
 *         1
 *        / \
 *       2   3
 *      / \
 *     4   5
 * ```
 * 
 * TERMINOLOGY:
 * - **Root**: Top node (1 in example)
 * - **Parent**: Node with children (1 is parent of 2 and 3)
 * - **Child**: Node below parent (2 and 3 are children of 1)
 * - **Leaf**: Node with no children (4, 5, 3 are leaves)
 * - **Internal Node**: Node with at least one child (1, 2)
 * - **Siblings**: Nodes with same parent (2 and 3 are siblings)
 * - **Height**: Longest path from root to leaf (2 in example)
 * - **Depth**: Distance from root to node
 * - **Level**: All nodes at same depth
 * 
 * ============================================================================
 * TYPES OF BINARY TREES
 * ============================================================================
 * 
 * 1. **Full Binary Tree**: Every node has 0 or 2 children
 * ```
 *       1
 *      / \
 *     2   3
 *    / \
 *   4   5
 * ```
 * 
 * 2. **Complete Binary Tree**: All levels filled except possibly last,
 *    which is filled from left to right
 * ```
 *       1
 *      / \
 *     2   3
 *    / \  /
 *   4  5 6
 * ```
 * 
 * 3. **Perfect Binary Tree**: All internal nodes have 2 children,
 *    all leaves at same level
 * ```
 *       1
 *      / \
 *     2   3
 *    / \ / \
 *   4  5 6  7
 * ```
 * 
 * 4. **Balanced Binary Tree**: Height difference between left and right
 *    subtrees is at most 1 for all nodes
 * 
 * 5. **Degenerate Tree**: Every parent has only one child (like linked list)
 * ```
 *   1
 *    \
 *     2
 *      \
 *       3
 * ```
 * 
 * ============================================================================
 * WHY USE BINARY TREES?
 * ============================================================================
 * 
 * ADVANTAGES:
 * ✅ Hierarchical structure (natural for representing relationships)
 * ✅ Efficient searching (O(log n) in balanced trees)
 * ✅ Efficient insertion/deletion (O(log n) in balanced trees)
 * ✅ Dynamic size
 * ✅ Natural for divide-and-conquer algorithms
 * 
 * APPLICATIONS:
 * - File systems (directories and files)
 * - Expression parsing (mathematical expressions)
 * - Database indexing (B-trees)
 * - Huffman coding (data compression)
 * - Decision trees (machine learning)
 * - Game trees (chess, tic-tac-toe)
 * - Priority queues (heap)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * OPERATION          | BALANCED TREE | SKEWED TREE
 * -------------------|---------------|-------------
 * Search             | O(log n)      | O(n)
 * Insert             | O(log n)      | O(n)
 * Delete             | O(log n)      | O(n)
 * Space              | O(n)          | O(n)
 * 
 * HEIGHT OF TREE:
 * - Balanced: O(log n)
 * - Skewed: O(n)
 * 
 * ============================================================================
 */

package trees.binarytree.traversals

/**
 * TreeNode class representing a node in binary tree
 */
data class TreeNode(
    var value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null
)

/**
 * BinaryTree class with basic operations
 */
class BinaryTree {
    var root: TreeNode? = null
    
    /**
     * Calculate height of tree
     * Height = longest path from root to leaf
     * TIME: O(n) - visit each node once
     * SPACE: O(h) - recursion stack, h is height
     */
    fun height(node: TreeNode? = root): Int {
        // Base case: empty tree has height 0
        if (node == null) return 0
        
        // Recursive case: height = 1 + max(left height, right height)
        val leftHeight = height(node.left)
        val rightHeight = height(node.right)
        
        return 1 + maxOf(leftHeight, rightHeight)
    }
    
    /**
     * Count total number of nodes
     * TIME: O(n) - visit each node
     * SPACE: O(h) - recursion stack
     */
    fun countNodes(node: TreeNode? = root): Int {
        // Base case: empty tree has 0 nodes
        if (node == null) return 0
        
        // Count = 1 (current) + nodes in left + nodes in right
        return 1 + countNodes(node.left) + countNodes(node.right)
    }
    
    /**
     * Count leaf nodes (nodes with no children)
     * TIME: O(n)
     * SPACE: O(h)
     */
    fun countLeaves(node: TreeNode? = root): Int {
        // Base case: null node
        if (node == null) return 0
        
        // If both children are null, this is a leaf
        if (node.left == null && node.right == null) return 1
        
        // Otherwise, count leaves in left and right subtrees
        return countLeaves(node.left) + countLeaves(node.right)
    }
    
    /**
     * Search for a value in tree
     * TIME: O(n) - may need to check all nodes
     * SPACE: O(h) - recursion stack
     */
    fun search(node: TreeNode?, value: Int): Boolean {
        // Base case: reached null, value not found
        if (node == null) return false
        
        // Found the value
        if (node.value == value) return true
        
        // Search in left or right subtree
        return search(node.left, value) || search(node.right, value)
    }
    
    /**
     * Find maximum value in tree
     * TIME: O(n) - must check all nodes
     * SPACE: O(h)
     */
    fun findMax(node: TreeNode? = root): Int? {
        // Base case: empty tree
        if (node == null) return null
        
        // If leaf node, return its value
        if (node.left == null && node.right == null) {
            return node.value
        }
        
        // Find max in left and right subtrees
        val leftMax = findMax(node.left) ?: Int.MIN_VALUE
        val rightMax = findMax(node.right) ?: Int.MIN_VALUE
        
        // Return maximum of current, left max, right max
        return maxOf(node.value, leftMax, rightMax)
    }
    
    /**
     * Check if tree is a full binary tree
     * (Every node has 0 or 2 children)
     */
    fun isFull(node: TreeNode? = root): Boolean {
        // Empty tree is full
        if (node == null) return true
        
        // Leaf node (0 children) is valid
        if (node.left == null && node.right == null) return true
        
        // Node has exactly one child - not full
        if (node.left == null || node.right == null) return false
        
        // Both children exist - check recursively
        return isFull(node.left) && isFull(node.right)
    }
    
    /**
     * Print tree structure (sideways)
     */
    fun printTree(node: TreeNode? = root, prefix: String = "", isLeft: Boolean = true) {
        if (node == null) return
        
        // Print right subtree first (will appear at top)
        printTree(node.right, prefix + (if (isLeft) "│   " else "    "), false)
        
        // Print current node
        println(prefix + (if (isLeft) "└── " else "┌── ") + node.value)
        
        // Print left subtree (will appear at bottom)
        printTree(node.left, prefix + (if (isLeft) "    " else "│   "), true)
    }
}

/**
 * ============================================================================
 * TREE CONSTRUCTION HELPERS
 * ============================================================================
 */

/**
 * Build a sample binary tree for testing
 * 
 * Structure:
 *         1
 *        / \
 *       2   3
 *      / \   \
 *     4   5   6
 */
fun buildSampleTree1(): BinaryTree {
    val tree = BinaryTree()
    
    tree.root = TreeNode(1)
    tree.root?.left = TreeNode(2)
    tree.root?.right = TreeNode(3)
    tree.root?.left?.left = TreeNode(4)
    tree.root?.left?.right = TreeNode(5)
    tree.root?.right?.right = TreeNode(6)
    
    return tree
}

/**
 * Build a full binary tree
 * 
 * Structure:
 *         10
 *        /  \
 *       5    15
 *      / \   / \
 *     3   7 12  20
 */
fun buildFullTree(): BinaryTree {
    val tree = BinaryTree()
    
    tree.root = TreeNode(10)
    tree.root?.left = TreeNode(5)
    tree.root?.right = TreeNode(15)
    tree.root?.left?.left = TreeNode(3)
    tree.root?.left?.right = TreeNode(7)
    tree.root?.right?.left = TreeNode(12)
    tree.root?.right?.right = TreeNode(20)
    
    return tree
}

/**
 * Build a skewed tree (degenerate)
 * 
 * Structure:
 *   1
 *    \
 *     2
 *      \
 *       3
 *        \
 *         4
 */
fun buildSkewedTree(): BinaryTree {
    val tree = BinaryTree()
    
    tree.root = TreeNode(1)
    tree.root?.right = TreeNode(2)
    tree.root?.right?.right = TreeNode(3)
    tree.root?.right?.right?.right = TreeNode(4)
    
    return tree
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Height Calculation
 * ============================================================================
 * 
 * Tree:
 *         1
 *        / \
 *       2   3
 *      / \
 *     4   5
 * 
 * height(1):
 *   leftHeight = height(2)
 *     leftHeight = height(4) = 1 (leaf)
 *     rightHeight = height(5) = 1 (leaf)
 *     return 1 + max(1, 1) = 2
 *   rightHeight = height(3) = 1 (leaf)
 *   return 1 + max(2, 1) = 3
 * 
 * Result: Height = 3 ✓
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Binary Tree - Introduction ===\n")
    
    // Test 1: Sample tree operations
    println("Test 1: Sample Tree")
    val tree1 = buildSampleTree1()
    println("Tree structure (sideways view):")
    tree1.printTree()
    println("\nHeight: ${tree1.height()}")
    println("Total nodes: ${tree1.countNodes()}")
    println("Leaf nodes: ${tree1.countLeaves()}")
    println("Max value: ${tree1.findMax()}")
    println("Search for 5: ${tree1.search(tree1.root, 5)}")
    println("Search for 10: ${tree1.search(tree1.root, 10)}")
    println("Is full: ${tree1.isFull()}")
    println()
    
    // Test 2: Full binary tree
    println("Test 2: Full Binary Tree")
    val tree2 = buildFullTree()
    println("Tree structure:")
    tree2.printTree()
    println("\nHeight: ${tree2.height()}")
    println("Total nodes: ${tree2.countNodes()}")
    println("Leaf nodes: ${tree2.countLeaves()}")
    println("Is full: ${tree2.isFull()}")
    println()
    
    // Test 3: Skewed tree
    println("Test 3: Skewed Tree (Degenerate)")
    val tree3 = buildSkewedTree()
    println("Tree structure:")
    tree3.printTree()
    println("\nHeight: ${tree3.height()}")
    println("Total nodes: ${tree3.countNodes()}")
    println("Notice: Height equals number of nodes (worst case)")
    println("Is full: ${tree3.isFull()}")
    println()
    
    // Test 4: Single node
    println("Test 4: Single Node Tree")
    val tree4 = BinaryTree()
    tree4.root = TreeNode(42)
    println("Tree structure:")
    tree4.printTree()
    println("\nHeight: ${tree4.height()}")
    println("Total nodes: ${tree4.countNodes()}")
    println("Leaf nodes: ${tree4.countLeaves()}")
    println()
    
    // Test 5: Empty tree
    println("Test 5: Empty Tree")
    val tree5 = BinaryTree()
    println("Height: ${tree5.height()}")
    println("Total nodes: ${tree5.countNodes()}")
    println("Leaf nodes: ${tree5.countLeaves()}")
    println("Max value: ${tree5.findMax()}")
}
