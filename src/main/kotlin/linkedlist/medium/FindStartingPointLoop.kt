/**
 * ============================================================================
 * PROBLEM:    Linked List Cycle II - Find Starting Point of Loop
 * DIFFICULTY: Medium
 * CATEGORY:  Linked List
 * LEETCODE:    #142
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Given the head of a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 * 
 * A cycle exists if there is some node that can be reached again by 
 * continuously following the next pointer.  Do not modify the linked list.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * - pos:  position where tail connects (for testing, not given as input)
 * 
 * OUTPUT FORMAT:
 * - The node where cycle begins, or null if no cycle
 * 
 * CONSTRAINTS:
 * - Number of nodes:    [0, 10^4]
 * - -10^5 <= Node.val <= 10^5
 * - pos is -1 or a valid index
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:     head = [3,2,0,-4], pos = 1
 *          3 -> 2 -> 0 -> -4
 *               ^          |
 *               |__________|
 * Output:  Node with value 2 (cycle starts at index 1)
 * 
 * Example 2:
 * Input:   head = [1,2], pos = 0
 *          1 -> 2
 *          ^    |
 *          |____|
 * Output:  Node with value 1 (cycle starts at index 0)
 * 
 * Example 3:
 * Input:   head = [1], pos = -1
 * Output:  null (no cycle)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION - FLOYD'S ALGORITHM EXTENDED:
 * 
 * This builds on cycle detection (LeetCode 141).  Now we need to FIND where
 * the cycle starts, not just detect it!   
 * 
 * AMAZING MATHEMATICAL PROPERTY:
 * After slow and fast meet inside the cycle: 
 * - Distance from head to cycle start = Distance from meeting point to cycle start
 * 
 * PROOF (Simplified):
 * Let: 
 * - L = distance from head to cycle start
 * - C = cycle length
 * - k = distance from cycle start to meeting point
 * 
 * When they meet:
 * - Slow traveled: L + k
 * - Fast traveled:  L + k + nC (n complete cycles)
 * - Fast = 2 × Slow:  L + k + nC = 2(L + k)
 * - Simplify: L = nC - k
 * 
 * This means: 
 * - Starting from head and moving L steps reaches cycle start
 * - Starting from meeting point and moving L steps also reaches cycle start! 
 * 
 * VISUAL EXAMPLE:
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> 5 -> 6
 *                 ^              |
 *                 |______________|
 * 
 * L = 2 (nodes 1, 2)
 * Cycle:  3 -> 4 -> 5 -> 6 -> 3... 
 * C = 4
 * 
 * Phase 1: Detect cycle using slow/fast
 *   They meet at some point, say node 5
 * 
 * Phase 2: Find cycle start
 *   p1 = head (node 1)
 *   p2 = meeting point (node 5)
 *   
 *   Move both one step at a time:
 *   
 *   Step 1:
 *     p1 = 2
 *     p2 = 6
 *   
 *   Step 2:
 *     p1 = 3 ← cycle start!
 *     p2 = 3 ← they meet at cycle start! 
 * 
 * Return node 3 ✓
 * ```
 * 
 * DETAILED WALKTHROUGH:
 * ```
 * [3,2,0,-4] with pos=1
 * 
 * List structure:
 *   3 -> 2 -> 0 -> -4
 *        ^          |
 *        |__________|
 * 
 * Phase 1: Find meeting point
 *   slow = 3, fast = 3
 *   
 *   Step 1: slow = 2, fast = 0
 *   Step 2: slow = 0, fast = -4
 *   Step 3: slow = -4, fast = 0
 *   Step 4: slow = 2, fast = -4
 *   Step 5: slow = 0, fast = 0
 *   
 *   Meet at node 0! 
 * 
 * Phase 2: Find cycle start
 *   p1 = head (3)
 *   p2 = meeting point (0)
 *   
 *   Step 1:
 *     p1 = 2
 *     p2 = -4
 *   
 *   Step 2:
 *     p1 = 0
 *     p2 = 2
 *   
 *   Wait, let me recalculate...
 *   Actually when they meet, reset p1 to head
 *   and both move one step at a time
 *   
 *   p1 starts at 3
 *   p2 starts at meeting point
 *   
 *   They will meet at cycle start (node with value 2)
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Phase 1: Detect cycle using slow/fast pointers
 *    - If no cycle, return null
 *    - If cycle exists, they meet at some point
 * 2. Phase 2: Find cycle start
 *    - Set p1 = head
 *    - Set p2 = meeting point
 *    - Move both one step at a time
 *    - When p1 == p2, that's the cycle start! 
 * 3. Return the cycle start node
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:    O(n)
 * - Phase 1 (detect): O(n) to find meeting point
 * - Phase 2 (find start): O(n) to find cycle start
 * - Total: O(n) + O(n) = O(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only a few pointers used
 * - No additional data structures
 * - Constant space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: HASH SET
 * - Store all visited nodes in a set
 * - First node seen twice is cycle start
 * - Time: O(n), Space: O(n)
 * - Simple but uses extra space
 * 
 * APPROACH 2: MODIFY NODE VALUES (Destructive)
 * - Mark visited nodes with special value
 * - First marked node is cycle start
 * - Modifies original list (not acceptable)
 * 
 * Floyd's algorithm is optimal: 
 * ✅ O(n) time
 * ✅ O(1) space
 * ✅ Non-destructive
 * ✅ Elegant mathematical solution
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. No cycle → return null
 * 2. Cycle at head (pos = 0) → return head
 * 3. Cycle at last node → return last node
 * 4. Single node pointing to itself → return that node
 * 5. Two nodes forming cycle → return first or second based on pos
 * 6. Very long list with small cycle → should handle efficiently
 * 7. All nodes in cycle (pos = 0, all nodes connected) → return head
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Returning meeting point instead of cycle start
 *    Result: Wrong node returned
 *    Fix: Need phase 2 to find actual start
 * 
 * 2. ❌ Not checking if cycle exists first
 *    Result: NullPointerException
 *    Fix:  Detect cycle before finding start
 * 
 * 3. ❌ Moving pointers at different speeds in phase 2
 *    Result: Won't find correct start
 *    Fix: Both move ONE step at a time in phase 2
 * 
 * 4. ❌ Wrong initial position for phase 2
 *    Result:  Incorrect start found
 *    Fix: p1 = head, p2 = meeting point
 * 
 * 5. ❌ Not understanding the math
 *    Result: Trying to count or use different logic
 *    Fix: Trust the mathematical property
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Memory Leak Detection**:  Find source of circular references
 * 2. **Deadlock Detection**: Identify where circular wait begins
 * 3. **Network Loops**: Find where routing loop starts
 * 4. **File System**:  Detect and locate symbolic link loops
 * 5. **Process Dependencies**: Find circular dependency origin
 * 6. **Graph Algorithms**:  Detect cycle entry point
 * 7. **Duplicate Detection**: Find where duplication pattern starts
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Explain two phases**:  Detect, then find start
 * 2. **Draw the diagram**: Visual proof of the math
 * 3. **Mention the math**: Distance equality property
 * 4. **Walk through example**: Show both phases clearly
 * 5. **Compare to hash set**: Discuss space tradeoff
 * 6. **Handle no cycle**: Check and return null
 * 7. **Edge cases**: Cycle at head, single node cycle
 * 8. **Prove correctness**: At least sketch the mathematical proof
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Find the length of the cycle
 * 2. Find the node just before cycle start
 * 3. Remove the cycle (break the link)
 * 4. Count nodes in the cycle
 * 5. Find all nodes in the cycle
 * 6. Detect multiple cycles (if allowed)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 141: Linked List Cycle (detect only)
 * - LeetCode 287: Find Duplicate Number (uses same algorithm)
 * - LeetCode 202: Happy Number (cycle detection)
 * - LeetCode 457:  Circular Array Loop
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class FindStartingPointLoop {
    
    /**
     * APPROACH 1: FLOYD'S ALGORITHM (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun detectCycle(head: ListNode? ): ListNode? {
        if (head?. next == null) return null
        
        // Phase 1: Detect cycle and find meeting point
        var slow = head
        var fast = head
        var hasCycle = false
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast. next?.next
            
            if (slow == fast) {
                hasCycle = true
                break
            }
        }
        
        // No cycle found
        if (!hasCycle) return null
        
        // Phase 2: Find cycle start
        var p1 = head
        var p2 = slow  // meeting point
        
        // Move both one step at a time until they meet
        while (p1 != p2) {
            p1 = p1?.next
            p2 = p2?.next
        }
        
        // They meet at cycle start
        return p1
    }
    
    /**
     * APPROACH 2: USING HASH SET
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun detectCycleHashSet(head: ListNode?): ListNode? {
        val visited = mutableSetOf<ListNode>()
        var current = head
        
        while (current != null) {
            if (current in visited) {
                return current  // First repeated node is cycle start
            }
            visited.add(current)
            current = current. next
        }
        
        return null
    }
    
    /**
     * BONUS:  Get cycle length
     */
    fun getCycleLength(head: ListNode?): Int {
        val cycleStart = detectCycle(head) ?: return 0
        
        var length = 1
        var current = cycleStart. next
        
        while (current != cycleStart) {
            length++
            current = current?. next
        }
        
        return length
    }
    
    /**
     * BONUS: Get all nodes in cycle
     */
    fun getNodesInCycle(head: ListNode?): List<Int> {
        val cycleStart = detectCycle(head) ?: return emptyList()
        
        val nodes = mutableListOf<Int>()
        nodes.add(cycleStart.`val`)
        
        var current = cycleStart.next
        while (current != cycleStart) {
            nodes.add(current!! .`val`)
            current = current.next
        }
        
        return nodes
    }
    
    /**
     * BONUS: Remove the cycle
     */
    fun removeCycle(head: ListNode? ): ListNode? {
        val cycleStart = detectCycle(head) ?: return head
        
        // Find the node before cycle start
        var current = cycleStart
        while (current?.next != cycleStart) {
            current = current?.next
        }
        
        // Break the cycle
        current?.next = null
        
        return head
    }
    
    // Helper function to create a linked list with cycle
    fun createListWithCycle(arr: IntArray, pos: Int): ListNode? {
        if (arr.isEmpty()) return null
        
        val head = ListNode(arr[0])
        var curr = head
        var cycleNode:  ListNode? = null
        
        if (pos == 0) cycleNode = head
        
        for (i in 1 until arr.size) {
            curr.next = ListNode(arr[i])
            curr = curr.next!!
            if (i == pos) cycleNode = curr
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && cycleNode != null) {
            curr.next = cycleNode
        }
        
        return head
    }
    
    // Helper function to print list (careful with cycles!)
    fun printList(head:  ListNode?, maxNodes: Int = 20) {
        var curr = head
        var count = 0
        val values = mutableListOf<Int>()
        val seen = mutableSetOf<ListNode>()
        
        while (curr != null && count < maxNodes) {
            if (curr in seen) {
                values.add(curr.`val`)
                println(values.joinToString(" -> ") + " -> [CYCLE BACK TO ${curr.`val`}]")
                return
            }
            seen.add(curr)
            values.add(curr.`val`)
            curr = curr.next
            count++
        }
        
        if (count == maxNodes && curr != null) {
            println(values.joinToString(" -> ") + " -> ...")
        } else {
            println(values. joinToString(" -> ") + " -> null")
        }
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = FindStartingPointLoop()
    
    println("=". repeat(70))
    println("FIND STARTING POINT OF LOOP - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Cycle at position 1 [3,2,0,-4]
    println("\nTest Case 1: [3,2,0,-4] with cycle at pos 1")
    var head = solver.createListWithCycle(intArrayOf(3, 2, 0, -4), 1)
    print("List (first 10 nodes): ")
    solver.printList(head, 10)
    val cycleStart1 = solver.detectCycle(head)
    println("Cycle starts at node with value:  ${cycleStart1?.`val`}")
    println("Expected: 2 (index 1)")
    if (cycleStart1 != null) {
        println("Cycle length: ${solver.getCycleLength(head)}")
        println("Nodes in cycle: ${solver. getNodesInCycle(head)}")
    }
    
    // Test Case 2: Cycle at position 0 [1,2]
    println("\nTest Case 2: [1,2] with cycle at pos 0")
    head = solver.createListWithCycle(intArrayOf(1, 2), 0)
    print("List (first 10 nodes): ")
    solver.printList(head, 10)
    val cycleStart2 = solver.detectCycle(head)
    println("Cycle starts at node with value: ${cycleStart2?.`val`}")
    println("Expected: 1 (index 0)")
    if (cycleStart2 != null) {
        println("Cycle length: ${solver.getCycleLength(head)}")
    }
    
    // Test Case 3: No cycle [1]
    println("\nTest Case 3: [1] with no cycle")
    head = solver.createListWithCycle(intArrayOf(1), -1)
    print("List: ")
    solver.printList(head)
    val cycleStart3 = solver.detectCycle(head)
    println("Cycle starts at:  ${cycleStart3?.`val` ?: "null"}")
    println("Expected: null")
    
    // Test Case 4: No cycle [1,2,3,4,5]
    println("\nTest Case 4: [1,2,3,4,5] with no cycle")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5), -1)
    print("List: ")
    solver.printList(head)
    val cycleStart4 = solver.detectCycle(head)
    println("Cycle starts at: ${cycleStart4?.`val` ?: "null"}")
    println("Expected: null")
    
    // Test Case 5: Single node pointing to itself
    println("\nTest Case 5: [1] pointing to itself (pos 0)")
    head = solver.createListWithCycle(intArrayOf(1), 0)
    print("List (first 5 nodes): ")
    solver.printList(head, 5)
    val cycleStart5 = solver.detectCycle(head)
    println("Cycle starts at node with value: ${cycleStart5?.`val`}")
    println("Expected: 1")
    
    // Test Case 6: Cycle at last node
    println("\nTest Case 6: [1,2,3,4,5] with cycle at pos 4 (last node)")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5), 4)
    print("List (first 10 nodes): ")
    solver.printList(head, 10)
    val cycleStart6 = solver.detectCycle(head)
    println("Cycle starts at node with value: ${cycleStart6?.`val`}")
    println("Expected: 5 (last node)")
    
    // Test Case 7: Cycle in middle
    println("\nTest Case 7: [1,2,3,4,5,6,7] with cycle at pos 3")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5, 6, 7), 3)
    print("List (first 15 nodes): ")
    solver.printList(head, 15)
    val cycleStart7 = solver.detectCycle(head)
    println("Cycle starts at node with value: ${cycleStart7?.`val`}")
    println("Expected: 4 (index 3)")
    if (cycleStart7 != null) {
        println("Cycle length: ${solver.getCycleLength(head)}")
        println("Nodes in cycle: ${solver.getNodesInCycle(head)}")
    }
    
    // Test Case 8: Using hash set approach
    println("\nTest Case 8: Hash set approach on [5,10,15,20] with cycle at pos 1")
    head = solver.createListWithCycle(intArrayOf(5, 10, 15, 20), 1)
    val cycleStart8 = solver. detectCycleHashSet(head)
    println("Cycle starts at node with value: ${cycleStart8?. `val`}")
    println("Expected: 10")
    
    // Test Case 9: Remove cycle
    println("\nTest Case 9: Remove cycle from [1,2,3,4,5] with cycle at pos 2")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5), 2)
    print("Before removal (first 10 nodes): ")
    solver.printList(head, 10)
    head = solver.removeCycle(head)
    print("After removal:  ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 4 -> 5 -> null")
    
    // Test Case 10: Large cycle
    println("\nTest Case 10: Large list with cycle")
    val largeArray = IntArray(20) { it + 1 }
    head = solver.createListWithCycle(largeArray, 10)
    print("List (first 30 nodes): ")
    solver.printList(head, 30)
    val cycleStart10 = solver.detectCycle(head)
    println("Cycle starts at node with value: ${cycleStart10?.`val`}")
    println("Expected: 11 (index 10)")
    if (cycleStart10 != null) {
        println("Cycle length: ${solver.getCycleLength(head)}")
    }
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
