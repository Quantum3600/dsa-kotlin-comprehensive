/**
 * ============================================================================
 * PROBLEM:      Copy List with Random Pointer
 * DIFFICULTY:  Medium
 * CATEGORY:  Linked List
 * LEETCODE:    #138
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A linked list is given such that each node contains an additional random
 * pointer which could point to any node in the list or null.
 * 
 * Return a deep copy of the list.  The deep copy should consist of exactly n
 * brand new nodes, where each new node has its value set to the value of its
 * corresponding original node. Both the next and random pointers of the new
 * nodes should point to new nodes in the copied list such that the pointers
 * in the original list and copied list represent the same list state.
 * 
 * INPUT FORMAT:
 * - Head of a linked list with next and random pointers
 * 
 * OUTPUT FORMAT:
 * - Head of the deep copied linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:      [0, 1000]
 * - -10^4 <= Node.val <= 10^4
 * - random pointer can point to any node or null
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:      [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output:  [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Explanation: Each [val, random_index] pair
 * 
 * Example 2:
 * Input:     [[1,1],[2,1]]
 * Output:  [[1,1],[2,1]]
 * 
 * Example 3:
 * Input:      [[3,null],[3,0],[3,null]]
 * Output:  [[3,null],[3,0],[3,null]]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The challenge is the random pointer!   It can point to ANY node, including: 
 * - Itself
 * - A node before it
 * - A node after it
 * - null
 * 
 * We need to maintain the mapping between original nodes and copied nodes.
 * 
 * KEY INSIGHT - THREE APPROACHES:
 * 
 * APPROACH A:  HASH MAP (Most Intuitive)
 * - First pass: Create all new nodes, map old → new
 * - Second pass:  Set next and random using map
 * - Time: O(n), Space: O(n)
 * 
 * APPROACH B: INTERWEAVING (Optimal Space)
 * - Interweave:  A → A' → B → B' → C → C'
 * - Set random pointers:   A'. random = A. random.next
 * - Separate the lists
 * - Time: O(n), Space: O(1) - no hash map! 
 * 
 * APPROACH C: RECURSIVE WITH MAP
 * - Recursively clone with memoization
 * - Time:  O(n), Space: O(n)
 * 
 * VISUAL EXAMPLE (Approach A - HashMap):
 * ```
 * Original: 
 *   7 → 13 → 11 → 10 → 1
 *   ↓   ↓    ↓    ↓    ↓
 *   X   7    1    11   7
 *   (random pointers)
 * 
 * Pass 1: Create nodes and map
 *   map[7] = 7'
 *   map[13] = 13'
 *   map[11] = 11'
 *   map[10] = 10'
 *   map[1] = 1'
 * 
 * Pass 2: Set pointers
 *   7'.  next = map[13] = 13'
 *   7'. random = null
 *   
 *   13'. next = map[11] = 11'
 *   13'. random = map[7] = 7'
 *   
 *   ...  and so on
 * 
 * Result: Complete deep copy ✓
 * ```
 * 
 * VISUAL EXAMPLE (Approach B - Interweaving):
 * ```
 * Original:  A → B → C
 *            ↓   ↓   ↓
 *            C   A   null
 * 
 * Step 1: Interweave
 *   A → A' → B → B' → C → C'
 * 
 * Step 2: Set random pointers
 *   A. random = C, so A'.random = C. next = C'
 *   B.random = A, so B'.random = A.next = A'
 *   C.random = null, so C'.random = null
 * 
 * Step 3: Separate
 *   Original: A → B → C
 *   Copy:     A' → B' → C'
 * 
 * Result: Deep copy without extra space!  ✓
 * ```
 * 
 * ALGORITHM STEPS (HashMap - Simple):
 * 1. Pass 1: Create all new nodes
 *    - For each node, create copy and map old → new
 * 2. Pass 2: Set pointers
 *    - For each old node: 
 *      - new.next = map[old.  next]
 *      - new.random = map[old. random]
 * 3. Return map[head]
 * 
 * ALGORITHM STEPS (Interweaving - Optimal):
 * 1. Interweave: Insert copies after originals
 *    - A → A' → B → B' → C → C'
 * 2. Set random pointers for copies
 *    - A'.random = A.random.  next (if exists)
 * 3. Separate the two lists
 *    - Restore original:  A → B → C
 *    - Extract copy: A' → B' → C'
 * 4. Return copy head
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH A (HASHMAP):
 * TIME: O(n) - two passes
 * SPACE: O(n) - hash map storage
 * 
 * APPROACH B (INTERWEAVING):
 * TIME: O(n) - three passes
 * SPACE: O(1) - no extra data structure!  
 * Optimal space complexity!
 * 
 * APPROACH C (RECURSIVE):
 * TIME: O(n)
 * SPACE: O(n) - recursion stack + map
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: BRUTE FORCE
 * - For each node, find its position
 * - Find random's position
 * - Recreate in copy
 * - Time: O(n²), Space: O(1)
 * - Too slow! 
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list → return null
 * 2. Single node, random to self → copy points to copy
 * 3. Single node, random to null → copy random is null
 * 4. All randoms are null → simple copy
 * 5. Circular random pointers → should handle
 * 6. Random pointing to first/last → correct mapping
 * 7. Multiple nodes with same value → structure matters
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Shallow copy instead of deep copy
 *    Result: Pointers point to original nodes
 *    Fix: Create brand new nodes
 * 
 * 2. ❌ Not handling null random pointers
 *    Result:  NullPointerException
 *    Fix: Check before accessing
 * 
 * 3. ❌ In interweaving, not restoring original list
 *    Result: Original list modified
 *    Fix: Properly separate the lists
 * 
 * 4. ❌ Wrong random pointer assignment
 *    Result: Incorrect copy structure
 *    Fix: Use map or . next correctly
 * 
 * 5. ❌ Forgetting to handle self-referencing random
 *    Result: Random points to original, not copy
 *    Fix:  Ensure all pointers are to copies
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Undo/Redo Systems**: Copying state with complex references
 * 2. **Object Serialization**: Deep copy for persistence
 * 3. **Game State Cloning**: Copy game state with references
 * 4. **Distributed Systems**: Replicating data structures
 * 5. **Version Control**: Branching with references
 * 6. **Graph Cloning**: Similar problem with arbitrary edges
 * 7. **Memory Management**: Copy-on-write implementations
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify deep copy**:  Ensure understanding of requirement
 * 2. **Draw the structure**: Show next and random pointers
 * 3. **Explain mapping**: Old → new node mapping
 * 4. **Two approaches**: HashMap vs interweaving
 * 5. **Walk through example**: Show pointer assignments
 * 6. **Space optimization**: Discuss O(1) space solution
 * 7. **Handle null**: Show null random pointer handling
 * 8. **Edge cases**: Self-reference, all nulls
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Clone a graph with arbitrary connections
 * 2. Clone a tree with parent pointers
 * 3. Clone a doubly linked list with random pointer
 * 4. Detect if two lists are clones
 * 5. Clone with multiple random pointers
 * 6. Serialize and deserialize the list
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 133: Clone Graph (similar concept)
 * - LeetCode 1485: Clone Binary Tree With Random Pointer
 * - LeetCode 1490: Clone N-ary Tree
 * - LeetCode 297:  Serialize and Deserialize Binary Tree
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class Node(var `val`: Int) {
    var next: Node?  = null
    var random: Node? = null
}

class CloneWithRandom {
    
    /**
     * APPROACH 1: USING HASHMAP (Most Intuitive)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n) - hash map
     */
    fun copyRandomList(head: Node?): Node? {
        if (head == null) return null
        
        // Map old nodes to new nodes
        val map = mutableMapOf<Node, Node>()
        
        // Pass 1: Create all nodes
        var current = head
        while (current != null) {
            map[current] = Node(current. `val`)
            current = current.next
        }
        
        // Pass 2: Set next and random pointers
        current = head
        while (current != null) {
            val newNode = map[current]!!  
            newNode.next = map[current.next]
            newNode.random = map[current. random]
            current = current. next
        }
        
        return map[head]
    }
    
    /**
     * APPROACH 2: INTERWEAVING (Optimal Space)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(1) - no extra data structure! 
     */
    fun copyRandomListOptimal(head: Node?): Node? {
        if (head == null) return null
        
        // Step 1: Interweave - Insert copies after originals
        // A → A' → B → B' → C → C'
        var current = head
        while (current != null) {
            val copy = Node(current.`val`)
            copy.  next = current.next
            current.next = copy
            current = copy.next
        }
        
        // Step 2: Set random pointers for copies
        current = head
        while (current != null) {
            if (current.random != null) {
                current.next?. random = current.random?. next
            }
            current = current.next?.next
        }
        
        // Step 3: Separate the lists
        val dummy = Node(0)
        var copyCurrent = dummy
        current = head
        
        while (current != null) {
            val copy = current.next
            
            // Restore original list
            current.next = copy? . next
            
            // Build copy list
            copyCurrent.next = copy
            copyCurrent = copy!!  
            
            current = current.next
        }
        
        return dummy.next
    }
    
    /**
     * APPROACH 3: RECURSIVE WITH MAP
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY:  O(n) - map + recursion stack
     */
    fun copyRandomListRecursive(head: Node?): Node? {
        val map = mutableMapOf<Node, Node>()
        return cloneHelper(head, map)
    }
    
    private fun cloneHelper(node: Node?, map:  MutableMap<Node, Node>): Node? {
        if (node == null) return null
        
        // If already cloned, return from map
        if (node in map) return map[node]
        
        // Create new node
        val copy = Node(node. `val`)
        map[node] = copy
        
        // Recursively clone next and random
        copy.next = cloneHelper(node.next, map)
        copy.random = cloneHelper(node.random, map)
        
        return copy
    }
    
    /**
     * BONUS:  Verify if two lists are identical clones
     */
    fun areIdenticalClones(original: Node?, clone: Node?): Boolean {
        val map = mutableMapOf<Node, Node>()
        
        // Build mapping
        var o = original
        var c = clone
        while (o != null && c != null) {
            if (o.`val` != c.`val`) return false
            map[o] = c
            o = o.next
            c = c.next
        }
        
        // Both should be null
        if (o != null || c != null) return false
        
        // Verify random pointers
        o = original
        while (o != null) {
            val expectedRandom = if (o.random != null) map[o.random] else null
            val actualRandom = map[o]?. random
            if (expectedRandom != actualRandom) return false
            o = o.next
        }
        
        return true
    }
    
    // Helper function to create list with random pointers
    fun createListWithRandom(values: IntArray, randomIndices: IntArray): Node? {
        if (values.isEmpty()) return null
        
        // Create nodes
        val nodes = values.map { Node(it) }.toTypedArray()
        
        // Set next pointers
        for (i in 0 until nodes.size - 1) {
            nodes[i].next = nodes[i + 1]
        }
        
        // Set random pointers
        for (i in randomIndices.indices) {
            if (randomIndices[i] != -1) {
                nodes[i].random = nodes[randomIndices[i]]
            }
        }
        
        return nodes[0]
    }
    
    // Helper function to print list with random pointers
    fun printListWithRandom(head: Node?) {
        val nodeToIndex = mutableMapOf<Node, Int>()
        var current = head
        var index = 0
        
        // Build index map
        while (current != null) {
            nodeToIndex[current] = index++
            current = current.next
        }
        
        // Print
        current = head
        val result = mutableListOf<String>()
        while (current != null) {
            val randomIdx = if (current.random != null) {
                nodeToIndex[current. random]?. toString() ?: "?"
            } else {
                "null"
            }
            result. add("[${current.`val`},$randomIdx]")
            current = current.next
        }
        
        println(result.joinToString(" -> "))
    }
    
    // Helper to verify structure
    fun verifyStructure(original: Node?, copy: Node?): Boolean {
        if (original == null && copy == null) return true
        if (original == null || copy == null) return false
        
        // Check that copy is truly a different object
        if (original === copy) return false
        
        var o = original
        var c = copy
        
        while (o != null && c != null) {
            // Values should match
            if (o.`val` != c.`val`) return false
            
            // But nodes should be different objects
            if (o === c) return false
            if (o.next != null && o.next === c. next) return false
            if (o.random != null && o. random === c.random) return false
            
            o = o. next
            c = c.next
        }
        
        return o == null && c == null
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = CloneWithRandom()
    
    println("=".  repeat(70))
    println("COPY LIST WITH RANDOM POINTER - TEST CASES")
    println("=". repeat(70))
    
    // Test Case 1: Example [[7,null],[13,0],[11,4],[10,2],[1,0]]
    println("\nTest Case 1: [[7,null],[13,0],[11,4],[10,2],[1,0]]")
    var head = solver.createListWithRandom(
        intArrayOf(7, 13, 11, 10, 1),
        intArrayOf(-1, 0, 4, 2, 0)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    var copy = solver.copyRandomList(head)
    print("Copy (HashMap): ")
    solver.printListWithRandom(copy)
    println("Is valid clone:  ${solver.verifyStructure(head, copy)}")
    println("Are identical: ${solver.areIdenticalClones(head, copy)}")
    
    // Test Case 2: Simple [[1,1],[2,1]]
    println("\nTest Case 2: [[1,1],[2,1]]")
    head = solver.createListWithRandom(
        intArrayOf(1, 2),
        intArrayOf(1, 1)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy:  ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver. verifyStructure(head, copy)}")
    
    // Test Case 3: [[3,null],[3,0],[3,null]]
    println("\nTest Case 3: [[3,null],[3,0],[3,null]]")
    head = solver.createListWithRandom(
        intArrayOf(3, 3, 3),
        intArrayOf(-1, 0, -1)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy: ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver.verifyStructure(head, copy)}")
    
    // Test Case 4: Single node pointing to itself
    println("\nTest Case 4: [[1,0]] - self reference")
    head = solver.createListWithRandom(
        intArrayOf(1),
        intArrayOf(0)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy: ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver. verifyStructure(head, copy)}")
    
    // Test Case 5: All randoms null
    println("\nTest Case 5: [[1,null],[2,null],[3,null]] - all nulls")
    head = solver.createListWithRandom(
        intArrayOf(1, 2, 3),
        intArrayOf(-1, -1, -1)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy: ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver.verifyStructure(head, copy)}")
    
    // Test Case 6: Empty list
    println("\nTest Case 6: [] - empty list")
    head = null
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy: ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver.verifyStructure(head, copy)}")
    
    // Test Case 7:  Optimal (interweaving) approach
    println("\nTest Case 7: Optimal approach [[10,2],[20,0],[30,1]]")
    head = solver.createListWithRandom(
        intArrayOf(10, 20, 30),
        intArrayOf(2, 0, 1)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomListOptimal(head)
    print("Copy (Optimal): ")
    solver.printListWithRandom(copy)
    println("Is valid clone:  ${solver.verifyStructure(head, copy)}")
    print("Original after (should be restored): ")
    solver.printListWithRandom(head)
    
    // Test Case 8: Recursive approach
    println("\nTest Case 8: Recursive approach [[5,1],[10,2],[15,0]]")
    head = solver.createListWithRandom(
        intArrayOf(5, 10, 15),
        intArrayOf(1, 2, 0)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomListRecursive(head)
    print("Copy (Recursive): ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver. verifyStructure(head, copy)}")
    
    // Test Case 9: Complex random pattern
    println("\nTest Case 9: Complex pattern [[1,4],[2,3],[3,1],[4,0],[5,2]]")
    head = solver.createListWithRandom(
        intArrayOf(1, 2, 3, 4, 5),
        intArrayOf(4, 3, 1, 0, 2)
    )
    print("Original: ")
    solver.printListWithRandom(head)
    copy = solver.copyRandomList(head)
    print("Copy: ")
    solver.printListWithRandom(copy)
    println("Is valid clone: ${solver.verifyStructure(head, copy)}")
    
    // Test Case 10: Long list
    println("\nTest Case 10: Longer list (10 nodes)")
    val values = IntArray(10) { it + 1 }
    val randoms = intArrayOf(5, 9, 0, 7, 2, 8, 1, 4, 3, 6)
    head = solver.createListWithRandom(values, randoms)
    print("Original (first 5): ")
    var curr = head
    for (i in 0 until 5) {
        val nodeToIndex = mutableMapOf<Node, Int>()
        var temp = head
        var idx = 0
        while (temp != null) {
            nodeToIndex[temp] = idx++
            temp = temp.next
        }
        val randomIdx = if (curr?. random != null) nodeToIndex[curr?. random] else -1
        print("[${curr?. `val`},$randomIdx] ")
        curr = curr?.next
    }
    println("...")
    copy = solver.copyRandomList(head)
    println("Is valid clone: ${solver.verifyStructure(head, copy)}")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
