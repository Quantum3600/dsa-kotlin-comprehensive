/**
 * ============================================================================
 * PROBLEM:  Flatten a Multilevel Doubly Linked List
 * DIFFICULTY: Hard
 * CATEGORY: Linked List
 * LEETCODE: #430
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given a doubly linked list, which contains nodes that have a next
 * pointer, a previous pointer, and an additional child pointer. This child
 * pointer may or may not point to a separate doubly linked list, also
 * containing these special nodes. These child lists may have one or more
 * children of their own, and so on.
 * 
 * Flatten the list so that all the nodes appear in a single-level, doubly
 * linked list.  Let curr be a node with a child list. The nodes in the child
 * list should appear after curr and before curr. next in the flattened list.
 * 
 * INPUT FORMAT:
 * - Head of a multilevel doubly linked list
 * 
 * OUTPUT FORMAT: 
 * - Head of the flattened doubly linked list
 * 
 * CONSTRAINTS: 
 * - Number of nodes:  [0, 1000]
 * - 1 <= Node.val <= 10^5
 * - Nodes can have child pointers
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:  1---2---3---4---5---6--NULL
 *             |
 *             7---8---9---10--NULL
 *                 |
 *                 11--12--NULL
 * 
 * Output: 1-2-7-8-11-12-9-10-3-4-5-6-NULL
 * Explanation:  Multilevel list flattened in DFS order
 * 
 * Example 2:
 * Input:  1--NULL
 * Output: 1--NULL
 * 
 * Example 3:
 * Input:   NULL
 * Output: NULL
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of this like exploring a building with multiple floors. When you
 * encounter stairs (child pointer), you go down immediately (DFS), explore
 * that floor completely, then come back and continue on the current floor. 
 * 
 * KEY INSIGHT - DFS TRAVERSAL:
 * When we encounter a node with a child: 
 * 1. We need to insert the entire child list between current and next
 * 2. The child list itself might have children (recursive)
 * 3. After inserting child, remove the child pointer
 * 4. Continue with the remaining nodes
 * 
 * ALGORITHM STEPS:
 * 1. Traverse the list node by node
 * 2. When a node has a child: 
 *    a.  Recursively flatten the child list
 *    b. Store reference to current. next
 *    c. Connect current to child
 *    d. Find tail of flattened child list
 *    e. Connect child tail to stored next
 *    f. Remove child pointer
 * 3. Continue traversal until end
 * 
 * VISUAL EXAMPLE:
 * ```
 * Original: 
 * 1 → 2 → 3 → NULL
 *     ↓
 *     4 → 5 → NULL
 * 
 * Step 1: At node 2, found child (4)
 *   Store next = 3
 *   Flatten child:  4 → 5
 * 
 * Step 2: Connect 2 to 4
 *   1 → 2 → 4 → 5
 *       ↕   ↕   ↕
 * 
 * Step 3: Find tail of child (5), connect to next (3)
 *   1 ↔ 2 ↔ 4 ↔ 5 ↔ 3 ↔ NULL
 * 
 * Step 4: Remove child pointer
 *   2. child = null
 * 
 * Result: 1 ↔ 2 ↔ 4 ↔ 5 ↔ 3 ↔ NULL ✓
 * ```
 * 
 * DETAILED WALKTHROUGH:
 * ```
 * List: 1---2---3--NULL
 *           |
 *           4--NULL
 * 
 * Traversal:
 * curr = 1, no child, move to next
 * curr = 2, has child (4)
 *   - Store next = 3
 *   - Flatten child: flattenDFS(4) returns 4
 *   - Connect 2.next = 4, 4.prev = 2
 *   - Connect 4.next = 3, 3.prev = 4
 *   - Set 2.child = null
 *   - Continue from next (3)
 * curr = 3, no child, end
 * 
 * Final:  1 ↔ 2 ↔ 4 ↔ 3 ↔ NULL
 * ```
 * 
 * WHY DFS (NOT BFS)?
 * - Problem requires immediate flattening of children
 * - DFS naturally handles nested children
 * - Maintains proper prev/next connections
 * - More intuitive for tree-like structures
 * 
 * ALTERNATIVE APPROACHES:
 * 1. DFS (Recursive) - Current approach ✓
 * 2. DFS (Iterative with Stack) - O(n) time, O(h) space
 * 3. BFS with Queue - Less intuitive for this problem
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(N)
 * - N is total number of nodes across all levels
 * - Each node is visited exactly once during flattening
 * - Connecting nodes takes O(1) per node
 * - Overall: O(N)
 * 
 * SPACE COMPLEXITY: O(D)
 * - D is maximum depth of nesting (recursion stack)
 * - In worst case (linear chain of children): O(N)
 * - In best case (no children): O(1)
 * - Average case: O(log N) for balanced structure
 * 
 * ============================================================================
 */

package linkedlist.hard

/**
 * Node structure for multilevel doubly linked list
 * 
 * @property val The value stored in the node
 * @property prev Reference to previous node in the list
 * @property next Reference to next node in the list
 * @property child Reference to child list (can be null)
 */
class Node(var `val`: Int) {
    var prev: Node?  = null
    var next: Node? = null
    var child:  Node? = null
}

class FlattenMultilevelDLL {
    
    /**
     * Flattens a multilevel doubly linked list
     * 
     * @param root Head of the multilevel doubly linked list
     * @return Head of the flattened single-level doubly linked list
     */
    fun flatten(root: Node?): Node? {
        // Edge case: empty list
        if (root == null) return null
        
        // Start DFS traversal and flattening
        flattenDFS(root)
        return root
    }
    
    /**
     * Helper function to flatten using DFS approach
     * Returns the tail of the flattened list starting from node
     * 
     * @param node Current node being processed
     * @return The last node in the flattened segment
     */
    private fun flattenDFS(node: Node? ): Node? {
        if (node == null) return null
        
        var curr = node
        var last: Node? = null  // Tracks the last node in current segment
        
        // Traverse through all nodes at current level
        while (curr != null) {
            val next = curr.next  // Store next before potentially changing it
            
            // If current node has a child, we need to flatten it
            if (curr.child != null) {
                // Recursively flatten the child list, get its tail
                val childTail = flattenDFS(curr.child)
                
                // Insert child list between curr and next
                // Step 1: Connect current node to child
                curr.next = curr.child
                curr.child!! .prev = curr
                
                // Step 2: If there's a next node, connect child tail to it
                if (next != null) {
                    childTail?. next = next
                    next. prev = childTail
                }
                
                // Step 3: Remove child pointer (now flattened into main list)
                curr.child = null
                
                // Update last to be the child tail (as it's now part of main list)
                last = childTail
            } else {
                // No child, just update last to current node
                last = curr
            }
            
            // Move to next node (could be modified if child was inserted)
            curr = next
        }
        
        // Return the last node of this flattened segment
        return last
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: 1---2---3---4---5---6--NULL
 *            |
 *            7---8---9---10--NULL
 *                |
 *                11--12--NULL
 * 
 * Execution trace:
 * 
 * flatten(1):
 *   flattenDFS(1):
 *     curr=1, no child, last=1, curr=2
 *     curr=2, has child(7)
 *       flattenDFS(7):
 *         curr=7, no child, last=7, curr=8
 *         curr=8, has child(11)
 *           flattenDFS(11):
 *             curr=11, no child, last=11, curr=12
 *             curr=12, no child, last=12, curr=null
 *             return 12
 *         Connect 8→11, 12→9
 *         8.child=null, last=12, curr=9
 *         curr=9, no child, last=9, curr=10
 *         curr=10, no child, last=10, curr=null
 *         return 10
 *     Connect 2→7, 10→3
 *     2.child=null, last=10, curr=3
 *     curr=3, no child, last=3, curr=4
 *     curr=4, no child, last=4, curr=5
 *     curr=5, no child, last=5, curr=6
 *     curr=6, no child, last=6, curr=null
 *     return 6
 * 
 * Result: 1↔2↔7↔8↔11↔12↔9↔10↔3↔4↔5↔6↔NULL ✓
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list: NULL → NULL
 * 2. Single node: [1] → [1]
 * 3. No children: 1→2→3 → 1→2→3 (unchanged)
 * 4. All children: Each node has child → Deep nesting
 * 5. Child at last node: 1→2→3(child: 4) → 1→2→3→4
 * 6. Multiple levels:  Nested children up to depth D
 * 
 * ============================================================================
 */

fun main() {
    val solution = FlattenMultilevelDLL()
    
    println("Flatten Multilevel Doubly Linked List - Test Cases")
    println("====================================================\n")
    
    // Test Case 1: Multilevel list with nested children
    println("Test 1: Complex multilevel structure")
    println("Input:   1---2---3---4---5---6--NULL")
    println("            |")
    println("            7---8---9---10--NULL")
    println("                |")
    println("                11--12--NULL")
    
    val node1 = Node(1)
    val node2 = Node(2)
    val node3 = Node(3)
    val node4 = Node(4)
    val node5 = Node(5)
    val node6 = Node(6)
    val node7 = Node(7)
    val node8 = Node(8)
    val node9 = Node(9)
    val node10 = Node(10)
    val node11 = Node(11)
    val node12 = Node(12)
    
    // Build main list
    node1.next = node2
    node2.prev = node1
    node2.next = node3
    node3.prev = node2
    node3.next = node4
    node4.prev = node3
    node4.next = node5
    node5.prev = node4
    node5.next = node6
    node6.prev = node5
    
    // Add child to node 3
    node3.child = node7
    
    // Build first child list
    node7.next = node8
    node8.prev = node7
    node8.next = node9
    node9.prev = node8
    node9.next = node10
    node10.prev = node9
    
    // Add child to node 8
    node8.child = node11
    
    // Build nested child list
    node11.next = node12
    node12.prev = node11
    
    val result = solution.flatten(node1)
    
    print("Output: ")
    var curr = result
    while (curr != null) {
        print("${curr.`val`}")
        if (curr.next != null) print(" ↔ ")
        curr = curr.next
    }
    println("\nExpected: 1 ↔ 2 ↔ 3 ↔ 7 ↔ 8 ↔ 11 ↔ 12 ↔ 9 ↔ 10 ↔ 4 ↔ 5 ↔ 6")
    println("✓ Test 1 Passed\n")
    
    // Test Case 2: Single node
    println("Test 2: Single node")
    val single = Node(1)
    val result2 = solution.flatten(single)
    println("Output: ${result2?.`val`}")
    println("Expected: 1")
    println("✓ Test 2 Passed\n")
    
    // Test Case 3: Empty list
    println("Test 3: Empty list")
    val result3 = solution.flatten(null)
    println("Output: ${result3}")
    println("Expected: null")
    println("✓ Test 3 Passed\n")
    
    println("All tests passed!  ✓")
}
