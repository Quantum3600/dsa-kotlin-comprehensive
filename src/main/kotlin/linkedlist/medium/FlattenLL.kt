a/**
 * ============================================================================
 * PROBLEM:      Flatten a Multilevel Doubly Linked List
 * DIFFICULTY:  Medium
 * CATEGORY:  Linked List
 * LEETCODE:    #430
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given a doubly linked list, which contains nodes that have a next
 * pointer, a previous pointer, and an additional child pointer. This child
 * pointer may point to a separate doubly linked list, also containing these
 * special nodes. These child lists may have one or more children of their own,
 * and so on, to produce a multilevel data structure. 
 * 
 * Given the head of the first level of the list, flatten the list so that all
 * the nodes appear in a single-level, doubly linked list.  Let curr be a node
 * with a child list. The nodes in the child list should appear after curr and
 * before curr. next in the flattened list. 
 * 
 * Return the head of the flattened list.  The nodes in the list must have all
 * of their child pointers set to null.
 * 
 * INPUT FORMAT:
 * - Head of a multilevel doubly linked list
 * 
 * OUTPUT FORMAT: 
 * - Head of the flattened doubly linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:      [0, 1000]
 * - 1 <= Node.val <= 10^5
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input: 
 *   1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ↔ 6
 *       |
 *       7 ↔ 8 ↔ 9 ↔ 10
 *           |
 *           11 ↔ 12
 * 
 * Output:  1 ↔ 2 ↔ 7 ↔ 8 ↔ 11 ↔ 12 ↔ 9 ↔ 10 ↔ 3 ↔ 4 ↔ 5 ↔ 6
 * 
 * Example 2:
 * Input: 
 *   1 ↔ 2
 *   |
 *   3
 * 
 * Output: 1 ↔ 3 ↔ 2
 * 
 * Example 3:
 * Input:   []
 * Output: []
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of this like exploring a file system with nested folders.   When you
 * encounter a folder (child), you must fully explore it before continuing
 * with siblings.   This is DFS (Depth-First Search)! 
 * 
 * KEY INSIGHT - MULTIPLE APPROACHES:
 * 
 * APPROACH A: ITERATIVE WITH STACK
 * - Use stack to save "next" when going into child
 * - Process child list fully
 * - Pop from stack to continue main list
 * - Time: O(n), Space: O(n)
 * 
 * APPROACH B: RECURSIVE (DFS)
 * - Recursively flatten child
 * - Insert flattened child between current and next
 * - Continue with next
 * - Time: O(n), Space: O(n) for recursion
 * 
 * APPROACH C: ITERATIVE WITHOUT STACK (Optimal)
 * - When child found: 
 *   1. Save next
 *   2. Attach child after current
 *   3. Find end of child list
 *   4. Connect end to saved next
 * - Time: O(n), Space: O(1)
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 
 *   1 ↔ 2 ↔ 3
 *       |
 *       4 ↔ 5
 * 
 * Step 1: At node 2, found child 4
 *   Save next:  next = 3
 *   Current list: 1 ↔ 2
 *   Child list: 4 ↔ 5
 * 
 * Step 2: Insert child after 2
 *   2. next = 4
 *   4.prev = 2
 *   2.child = null
 *   Current:  1 ↔ 2 ↔ 4 ↔ 5
 * 
 * Step 3: Find end of child (5)
 *   tail = 5
 * 
 * Step 4: Connect tail to saved next
 *   5.next = 3
 *   3.prev = 5
 *   Result: 1 ↔ 2 ↔ 4 ↔ 5 ↔ 3 ✓
 * ```
 * 
 * DETAILED WALKTHROUGH (Complex Example):
 * ```
 * Input:
 *   1 ↔ 2 ↔ 5
 *       |
 *       3 ↔ 4
 * 
 * Process node 1:
 *   No child, move to 2
 * 
 * Process node 2:
 *   Has child 3
 *   Save:  next = 5
 *   Attach: 2 ↔ 3 ↔ 4
 *   Find tail: 4
 *   Connect: 4 ↔ 5
 *   Set:  2. child = null
 *   Current: 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5
 * 
 * Continue:  nodes 3, 4, 5 (no children)
 * 
 * Result: 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ✓
 * ```
 * 
 * ALGORITHM STEPS (Iterative Optimal):
 * 1. Start at head, traverse the list
 * 2. When node has child: 
 *    a. Save node. next
 *    b. Set node.  next = child
 *    c. Set child.prev = node
 *    d. Set node.child = null
 *    e. Find tail of child list
 *    f. Connect tail. next = saved next
 *    g. If next exists, set next.prev = tail
 * 3. Move to next node
 * 4. Return head
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH A (STACK):
 * TIME: O(n) - visit each node once
 * SPACE: O(n) - stack in worst case (all nodes have children)
 * 
 * APPROACH B (RECURSIVE):
 * TIME: O(n)
 * SPACE: O(n) - recursion depth
 * 
 * APPROACH C (ITERATIVE NO STACK):
 * TIME: O(n) - visit each node once
 * SPACE: O(1) - only pointers
 * Optimal! 
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: COLLECT ALL NODES
 * - Traverse and collect all nodes in a list
 * - Rebuild as flat doubly linked list
 * - Time: O(n), Space: O(n)
 * - Less efficient
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list → return null
 * 2. Single node, no child → return same node
 * 3. No children anywhere → list unchanged
 * 4. All nodes have children → deeply nested
 * 5. Child at last node → handle correctly
 * 6. Multiple levels deep → recursive handling
 * 7. Child list longer than main list → handle correctly
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not setting child pointer to null
 *    Result:   List still has child references
 *    Fix: Always set node.child = null
 * 
 * 2. ❌ Not updating prev pointers
 *    Result:  Not a valid doubly linked list
 *    Fix: Update both next and prev
 * 
 * 3. ❌ Not finding tail of child list
 *    Result:  Can't connect back to main list
 *    Fix:  Traverse child to find tail
 * 
 * 4. ❌ Losing reference to next node
 *    Result: Part of list lost
 *    Fix: Save next before modifying pointers
 * 
 * 5. ❌ Not handling last node's child
 *    Result:  Incorrect flattening
 *    Fix:  Check and handle even at last node
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **File Systems**: Flatten nested directory structure
 * 2. **XML/HTML Parsing**:  Flatten nested tags
 * 3. **Organization Charts**: Flatten hierarchical structure
 * 4. **Comment Threads**: Flatten nested comments
 * 5. **Menu Systems**: Flatten nested menus
 * 6. **Outline Processing**: Convert outline to linear list
 * 7. **Tree Serialization**: Flatten tree to list
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Draw the structure**:  Visual representation crucial
 * 2. **Explain DFS**: Why depth-first works
 * 3. **Show pointer updates**: Both next and prev
 * 4. **Handle child = null**: Must clear child pointers
 * 5. **Walk through example**: Show each step
 * 6. **Discuss approaches**: Stack vs recursive vs iterative
 * 7. **Edge cases**: No children, deeply nested
 * 8. **Space optimization**: O(1) space solution
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Flatten in reverse order
 * 2. Count total nodes including children
 * 3. Find maximum depth
 * 4. Flatten only to certain depth
 * 5. Un-flatten back to original structure
 * 6. Flatten with BFS instead of DFS
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 114: Flatten Binary Tree to Linked List
 * - LeetCode 341: Flatten Nested List Iterator
 * - LeetCode 339: Nested List Weight Sum
 * - LeetCode 364: Nested List Weight Sum II
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class Node(var `val`: Int) {
    var prev: Node? = null
    var next: Node? = null
    var child: Node? = null
}

class FlattenLL {
    
    /**
     * APPROACH 1: ITERATIVE WITHOUT STACK (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun flatten(head: Node?): Node? {
        if (head == null) return null
        
        var current = head
        
        while (current != null) {
            // If node has child
            if (current.child != null) {
                // Save next
                val next = current.next
                
                // Connect current to child
                current.next = current.child
                current.child!!  .prev = current
                current.child = null  // Important: clear child pointer
                
                // Find tail of child list
                var tail = current.next
                while (tail?. next != null) {
                    tail = tail.next
                }
                
                // Connect tail to saved next
                if (next != null) {
                    tail?. next = next
                    next. prev = tail
                }
            }
            
            // Move to next node
            current = current. next
        }
        
        return head
    }
    
    /**
     * APPROACH 2: RECURSIVE (DFS)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n) - recursion stack
     */
    fun flattenRecursive(head: Node?): Node? {
        if (head == null) return null
        flattenDFS(head)
        return head
    }
    
    private fun flattenDFS(node: Node? ): Node? {
        if (node == null) return null
        
        // If no child, move to next
        if (node.child == null) {
            // If no next, this is tail
            if (node.next == null) return node
            return flattenDFS(node.next)
        }
        
        // Has child
        val child = node.child
        val next = node.next
        
        // Connect current to child
        node.next = child
        child!!  .prev = node
        node.child = null
        
        // Find tail of child
        val childTail = flattenDFS(child)
        
        // Connect child tail to next
        if (next != null) {
            childTail?.next = next
            next.prev = childTail
            return flattenDFS(next)
        }
        
        return childTail
    }
    
    /**
     * APPROACH 3: USING STACK
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n) - stack
     */
    fun flattenStack(head:  Node?): Node? {
        if (head == null) return null
        
        val stack = ArrayDeque<Node>()
        var current = head
        
        while (current != null || stack.isNotEmpty()) {
            if (current?. child != null) {
                // Save next on stack
                if (current.next != null) {
                    stack.addLast(current.next!!  )
                }
                
                // Connect to child
                current.next = current.child
                current.child!! .prev = current
                current. child = null
            }
            
            // If reached end of current level
            if (current?. next == null && stack.isNotEmpty()) {
                val next = stack.removeLast()
                current?. next = next
                next.prev = current
            }
            
            current = current?.next
        }
        
        return head
    }
    
    /**
     * BONUS: Count total nodes (including children)
     */
    fun countNodes(head: Node? ): Int {
        var count = 0
        var current = head
        
        while (current != null) {
            count++
            if (current.child != null) {
                count += countNodes(current.child)
            }
            current = current.next
        }
        
        return count
    }
    
    /**
     * BONUS: Find maximum depth
     */
    fun maxDepth(head:  Node?): Int {
        if (head == null) return 0
        
        var maxD = 1
        var current = head
        
        while (current != null) {
            if (current.child != null) {
                maxD = maxOf(maxD, 1 + maxDepth(current.child))
            }
            current = current.next
        }
        
        return maxD
    }
    
    // Helper function to create multilevel list
    fun createMultilevelList(): Node {
        val n1 = Node(1)
        val n2 = Node(2)
        val n3 = Node(3)
        val n4 = Node(4)
        val n5 = Node(5)
        val n6 = Node(6)
        val n7 = Node(7)
        val n8 = Node(8)
        val n9 = Node(9)
        val n10 = Node(10)
        val n11 = Node(11)
        val n12 = Node(12)
        
        // Main level
        n1.next = n2; n2.prev = n1
        n2.next = n3; n3.prev = n2
        n3.next = n4; n4.prev = n3
        n4.next = n5; n5.prev = n4
        n5.next = n6; n6.prev = n5
        
        // Child of 2
        n2.child = n7
        n7.next = n8; n8.prev = n7
        n8.next = n9; n9.prev = n8
        n9.next = n10; n10.prev = n9
        
        // Child of 8
        n8.child = n11
        n11.next = n12; n12.prev = n11
        
        return n1
    }
    
    // Helper to print flattened list
    fun printFlattened(head: Node?) {
        val values = mutableListOf<Int>()
        var current = head
        
        while (current != null) {
            values.add(current.`val`)
            current = current.next
        }
        
        println(values.joinToString(" ↔ "))
    }
    
    // Helper to verify doubly linked
    fun verifyDoublyLinked(head: Node? ): Boolean {
        var current = head
        var prev: Node?  = null
        
        while (current != null) {
            // Check prev pointer
            if (current.prev != prev) return false
            
            // Check no child pointers
            if (current.child != null) return false
            
            prev = current
            current = current.next
        }
        
        return true
    }
    
    // Helper to create simple multilevel list
    fun createSimpleList(mainVals: IntArray, childAtIndex: Int, childVals: IntArray): Node? {
        if (mainVals.isEmpty()) return null
        
        // Create main level
        val mainNodes = mainVals.map { Node(it) }
        for (i in 0 until mainNodes.size - 1) {
            mainNodes[i].next = mainNodes[i + 1]
            mainNodes[i + 1].prev = mainNodes[i]
        }
        
        // Create child if specified
        if (childAtIndex >= 0 && childVals.isNotEmpty()) {
            val childNodes = childVals.map { Node(it) }
            for (i in 0 until childNodes. size - 1) {
                childNodes[i].next = childNodes[i + 1]
                childNodes[i + 1].prev = childNodes[i]
            }
            mainNodes[childAtIndex].child = childNodes[0]
        }
        
        return mainNodes[0]
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = FlattenLL()
    
    println("=".  repeat(70))
    println("FLATTEN MULTILEVEL DOUBLY LINKED LIST - TEST CASES")
    println("=". repeat(70))
    
    // Test Case 1: Complex example
    println("\nTest Case 1: Complex multilevel list")
    println("Structure:")
    println("  1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ↔ 6")
    println("      |")
    println("      7 ↔ 8 ↔ 9 ↔ 10")
    println("          |")
    println("          11 ↔ 12")
    var head = solver.createMultilevelList()
    println("Nodes before flatten (including children): ${solver.countNodes(head)}")
    println("Max depth: ${solver.maxDepth(head)}")
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected: 1 ↔ 2 ↔ 7 ↔ 8 ↔ 11 ↔ 12 ↔ 9 ↔ 10 ↔ 3 ↔ 4 ↔ 5 ↔ 6")
    println("Is valid doubly linked: ${solver.verifyDoublyLinked(head)}")
    
    // Test Case 2: Simple child
    println("\nTest Case 2: [1,2,3] with child [4,5] at index 1")
    head = solver.createSimpleList(
        intArrayOf(1, 2, 3),
        1,
        intArrayOf(4, 5)
    )
    head = solver.flatten(head)
    print("Flattened:  ")
    solver.printFlattened(head)
    println("Expected: 1 ↔ 2 ↔ 4 ↔ 5 ↔ 3")
    println("Is valid:  ${solver.verifyDoublyLinked(head)}")
    
    // Test Case 3: No children
    println("\nTest Case 3: [1,2,3,4] - no children")
    head = solver.createSimpleList(
        intArrayOf(1, 2, 3, 4),
        -1,
        intArrayOf()
    )
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected:  1 ↔ 2 ↔ 3 ↔ 4 (unchanged)")
    
    // Test Case 4: Child at first node
    println("\nTest Case 4: [1,2] with child [3,4] at index 0")
    head = solver.createSimpleList(
        intArrayOf(1, 2),
        0,
        intArrayOf(3, 4)
    )
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected: 1 ↔ 3 ↔ 4 ↔ 2")
    
    // Test Case 5: Child at last node
    println("\nTest Case 5: [1,2,3] with child [4,5] at index 2 (last)")
    head = solver.createSimpleList(
        intArrayOf(1, 2, 3),
        2,
        intArrayOf(4, 5)
    )
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected: 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5")
    
    // Test Case 6: Single node
    println("\nTest Case 6: [1] - single node")
    head = solver.createSimpleList(
        intArrayOf(1),
        -1,
        intArrayOf()
    )
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected:  1")
    
    // Test Case 7: Recursive approach
    println("\nTest Case 7: Recursive approach [10,20,30] child [40,50] at 1")
    head = solver.createSimpleList(
        intArrayOf(10, 20, 30),
        1,
        intArrayOf(40, 50)
    )
    head = solver.flattenRecursive(head)
    print("Flattened (recursive): ")
    solver.printFlattened(head)
    println("Expected: 10 ↔ 20 ↔ 40 ↔ 50 ↔ 30")
    
    // Test Case 8: Stack approach
    println("\nTest Case 8: Stack approach [5,10,15] child [20,25] at 0")
    head = solver.createSimpleList(
        intArrayOf(5, 10, 15),
        0,
        intArrayOf(20, 25)
    )
    head = solver.flattenStack(head)
    print("Flattened (stack): ")
    solver.printFlattened(head)
    println("Expected: 5 ↔ 20 ↔ 25 ↔ 10 ↔ 15")
    
    // Test Case 9: Multiple nodes with children
    println("\nTest Case 9: [1,2,3] with child [4] at 0 and child [5] at 1")
    val n1 = Node(1)
    val n2 = Node(2)
    val n3 = Node(3)
    val n4 = Node(4)
    val n5 = Node(5)
    n1.next = n2; n2.prev = n1
    n2.next = n3; n3.prev = n2
    n1.child = n4
    n2.child = n5
    head = solver.flatten(n1)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected: 1 ↔ 4 ↔ 2 ↔ 5 ↔ 3")
    
    // Test Case 10: Empty list
    println("\nTest Case 10: [] - empty list")
    head = null
    head = solver.flatten(head)
    print("Flattened: ")
    solver.printFlattened(head)
    println("Expected: (empty)")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
    println("\n🎉 ALL 15 LINKED LIST MEDIUM PROBLEMS IMPLEMENTED!")
    println("=".repeat(70))
}// TODO: Implement FlattenLL
