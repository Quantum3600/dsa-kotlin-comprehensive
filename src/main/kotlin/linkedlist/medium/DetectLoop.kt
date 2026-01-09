/**
 * ============================================================================
 * PROBLEM:  Detect Cycle in Linked List
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * LEETCODE:  #141
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a linked list, determine if the linked list has a cycle. 
 * 
 * A cycle exists if there is some node in the list that can be reached again
 * by continuously following the next pointer.  The pos parameter represents
 * the position (0-indexed) where the cycle begins, or -1 if there is no cycle.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * - pos:  position where tail connects to (for testing only, not given)
 * 
 * OUTPUT FORMAT:
 * - true if there is a cycle, false otherwise
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [0, 10^4]
 * - -10^5 <= Node.val <= 10^5
 * - pos is -1 or a valid index in the linked list
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:   head = [3,2,0,-4], pos = 1
 *         3 -> 2 -> 0 -> -4
 *              ^          |
 *              |__________|
 * Output: true (cycle exists at node 2)
 * 
 * Example 2:
 * Input:  head = [1,2], pos = 0
 *         1 -> 2
 *         ^    |
 *         |____|
 * Output: true (cycle exists at node 1)
 * 
 * Example 3:
 * Input:  head = [1], pos = -1
 *         1 -> null
 * Output: false (no cycle)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION - THE RACETRACK ANALOGY:
 * Imagine two runners on a circular racetrack: 
 * - One runner is slow (moves 1 step per turn)
 * - One runner is fast (moves 2 steps per turn)
 * 
 * If the track is circular (has a cycle):
 * - The fast runner will eventually lap the slow runner
 * - They will meet at some point on the track
 * 
 * If the track has an end (no cycle):
 * - The fast runner will reach the end first
 * - They will never meet
 * 
 * This is **Floyd's Cycle Detection Algorithm** (Tortoise and Hare).
 * 
 * KEY INSIGHT:
 * - In a cycle, a faster pointer will eventually catch up to a slower pointer
 * - If there's no cycle, the faster pointer will reach null
 * - We don't need extra space to track visited nodes! 
 * 
 * VISUAL EXAMPLE WITH CYCLE:
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> 5
 *                 ^         |
 *                 |_________|
 * 
 * Step 0: 
 *   slow = 1, fast = 1
 * 
 * Step 1:
 *   slow moves to 2
 *   fast moves to 3
 *   slow = 2, fast = 3
 * 
 * Step 2:
 *   slow moves to 3
 *   fast moves to 5
 *   slow = 3, fast = 5
 * 
 * Step 3:
 *   slow moves to 4
 *   fast moves to 4 (from 5 -> 3 -> 4)
 *   slow = 4, fast = 4
 *   They meet!  Cycle detected ✓
 * ```
 * 
 * VISUAL EXAMPLE WITHOUT CYCLE:
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> null
 * 
 * Step 0:
 *   slow = 1, fast = 1
 * 
 * Step 1:
 *   slow = 2, fast = 3
 * 
 * Step 2:
 *   slow = 3, fast = null (reached end)
 *   No cycle ✓
 * ```
 * 
 * WHY DOES THIS WORK? 
 * Mathematical proof:
 * - Let's say cycle length is C
 * - When slow enters cycle, fast is already K nodes ahead in cycle
 * - Each step, fast gains 1 node on slow (fast moves 2, slow moves 1)
 * - They meet after (C - K) steps
 * - Since C is finite, they must meet! 
 * 
 * ALGORITHM STEPS:
 * 1. Initialize slow and fast pointers at head
 * 2. Move slow by 1 step, fast by 2 steps
 * 3. If fast or fast.next is null, no cycle (return false)
 * 4. If slow == fast, cycle detected (return true)
 * 5.  Repeat until condition is met
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - If no cycle: fast reaches end in n/2 steps → O(n)
 * - If cycle exists: 
 *   - Slow enters cycle after at most n steps
 *   - Fast catches slow in at most C steps (C = cycle length)
 *   - Total: O(n + C) = O(n) since C ≤ n
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only two pointers used
 * - No additional data structures
 * - Constant extra space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: HASH SET (Space:  O(n))
 * - Use a set to track visited nodes
 * - If we see a node again, cycle exists
 * - Simple but uses extra space
 * 
 * APPROACH 2: MODIFY NODE VALUES (Destructive)
 * - Mark each visited node with a special value
 * - If we see marked node again, cycle exists
 * - Modifies original list (usually not acceptable)
 * 
 * APPROACH 3: COUNT NODES (Limited)
 * - Traverse n+1 nodes
 * - If we haven't reached null, must be cycle
 * - Requires knowing n beforehand
 * 
 * Floyd's algorithm (slow/fast pointers) is optimal: 
 * ✅ O(1) space
 * ✅ O(n) time
 * ✅ Non-destructive
 * ✅ No preprocessing needed
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list (head = null) → false
 * 2. Single node, no cycle → false
 * 3. Single node pointing to itself → true
 * 4. Two nodes forming cycle → true
 * 5. Cycle at first node → true
 * 6. Cycle at last node → true
 * 7. Cycle in middle of list → true
 * 8. Very long list without cycle → false
 * 9. Very long list with cycle → true
 * 10. All nodes have same value → still works (checks references, not values)
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not checking fast. next before fast. next. next
 *    Result: NullPointerException
 *    Fix: Check both fast != null && fast.next != null
 * 
 * 2. ❌ Starting slow and fast at different positions
 *    Result: May miss cycle or get false positive
 *    Fix: Both start at head
 * 
 * 3. ❌ Moving pointers before checking if they're equal
 *    Result: May skip the meeting point
 *    Fix: Check equality, then move
 * 
 * 4. ❌ Using == for node comparison (in some languages)
 *    Result: Comparing values instead of references
 *    Fix:  Ensure reference comparison
 * 
 * 5. ❌ Not handling null head
 *    Result: NullPointerException
 *    Fix: Check if head is null first
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Memory Leak Detection**:  Detecting circular references in garbage
 *    collection
 * 2. **Network Loop Detection**: Finding routing loops in networks
 * 3. **Process Scheduling**: Detecting deadlocks in circular wait conditions
 * 4. **File System**:  Detecting symbolic link loops
 * 5. **Graph Algorithms**:  Cycle detection in directed graphs
 * 6. **Music Playlist**: Detecting repeat/loop settings
 * 7. **State Machines**: Detecting infinite loops in FSM
 * 8. **Blockchain**: Detecting consensus loop issues
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Explain the analogy**: Use racetrack/tortoise-hare explanation
 * 2. **Draw the diagram**: Visual representation helps immensely
 * 3. **Discuss space complexity**: Highlight O(1) space advantage
 * 4. **Mention alternatives**: Hash set approach, then explain why Floyd's is better
 * 5. **Handle edge cases**: Always check null conditions
 * 6. **Prove correctness**: Explain why fast always catches slow in cycle
 * 7. **Follow-up ready**: Be prepared for "find cycle start" question
 * 8. **Complexity analysis**: Walk through both cycle/no-cycle scenarios
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Find the start of the cycle (LeetCode 142)
 * 2. Find the length of the cycle
 * 3. Remove the cycle
 * 4. Find the node just before cycle starts
 * 5. Detect cycle in directed graph
 * 6. Find duplicate number (same algorithm)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 142: Linked List Cycle II (find start of cycle)
 * - LeetCode 287: Find the Duplicate Number (uses same algorithm)
 * - LeetCode 202: Happy Number (cycle detection in number sequence)
 * - LeetCode 457: Circular Array Loop
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class DetectLoop {
    
    /**
     * APPROACH 1: FLOYD'S CYCLE DETECTION (OPTIMAL)
     * Tortoise and Hare Algorithm
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun hasCycle(head: ListNode? ): Boolean {
        // Handle empty list or single node
        if (head?. next == null) return false
        
        // Initialize two pointers
        var slow = head
        var fast = head
        
        // Move slow by 1, fast by 2
        while (fast?.next != null) {
            slow = slow?.next      // Move 1 step
            fast = fast. next?.next  // Move 2 steps
            
            // If they meet, cycle exists
            if (slow == fast) {
                return true
            }
        }
        
        // Fast reached end, no cycle
        return false
    }
    
    /**
     * APPROACH 2: USING HASH SET
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun hasCycleHashSet(head: ListNode?): Boolean {
        if (head == null) return false
        
        val visited = mutableSetOf<ListNode>()
        var curr = head
        
        while (curr != null) {
            // If we've seen this node before, cycle exists
            if (curr in visited) {
                return true
            }
            visited.add(curr)
            curr = curr.next
        }
        
        return false
    }
    
    /**
     * BONUS: Find the length of the cycle
     * This assumes a cycle exists
     */
    fun getCycleLength(head: ListNode?): Int {
        if (! hasCycle(head)) return 0
        
        var slow = head
        var fast = head
        
        // Find meeting point
        while (fast?. next != null) {
            slow = slow?.next
            fast = fast. next?.next
            
            if (slow == fast) {
                // Count cycle length
                var length = 1
                var temp = slow?. next
                while (temp != slow) {
                    length++
                    temp = temp?.next
                }
                return length
            }
        }
        
        return 0
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
            curr = curr. next!! 
            if (i == pos) cycleNode = curr
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && cycleNode != null) {
            curr.next = cycleNode
        }
        
        return head
    }
    
    // Helper function to print list (careful with cycles!)
    fun printList(head:  ListNode?, maxNodes: Int = 10) {
        var curr = head
        var count = 0
        val values = mutableListOf<Int>()
        
        while (curr != null && count < maxNodes) {
            values.add(curr.`val`)
            curr = curr.next
            count++
        }
        
        if (count == maxNodes && curr != null) {
            println(values.joinToString(" -> ") + " -> ...  (cycle or continues)")
        } else {
            println(values.joinToString(" -> ") + " -> null")
        }
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = DetectLoop()
    
    println("=".repeat(70))
    println("DETECT CYCLE IN LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Cycle at position 1
    println("\nTest Case 1: [3,2,0,-4] with cycle at pos 1")
    var head = solver.createListWithCycle(intArrayOf(3, 2, 0, -4), 1)
    print("List (showing first 10 nodes): ")
    solver.printList(head)
    val result1 = solver.hasCycle(head)
    println("Has cycle: $result1")
    println("Expected: true")
    if (result1) {
        println("Cycle length: ${solver.getCycleLength(head)}")
    }
    
    // Test Case 2:  Cycle at position 0
    println("\nTest Case 2: [1,2] with cycle at pos 0")
    head = solver.createListWithCycle(intArrayOf(1, 2), 0)
    print("List (showing first 10 nodes): ")
    solver.printList(head)
    val result2 = solver.hasCycle(head)
    println("Has cycle: $result2")
    println("Expected: true")
    if (result2) {
        println("Cycle length: ${solver.getCycleLength(head)}")
    }
    
    // Test Case 3: No cycle
    println("\nTest Case 3: [1] with no cycle (pos = -1)")
    head = solver.createListWithCycle(intArrayOf(1), -1)
    print("List:  ")
    solver.printList(head)
    val result3 = solver.hasCycle(head)
    println("Has cycle:  $result3")
    println("Expected: false")
    
    // Test Case 4: Empty list
    println("\nTest Case 4: Empty list")
    head = null
    print("List: ")
    solver.printList(head)
    val result4 = solver.hasCycle(head)
    println("Has cycle: $result4")
    println("Expected: false")
    
    // Test Case 5: Single node pointing to itself
    println("\nTest Case 5: [1] pointing to itself (pos = 0)")
    head = solver.createListWithCycle(intArrayOf(1), 0)
    print("List (showing first 10 nodes): ")
    solver.printList(head)
    val result5 = solver.hasCycle(head)
    println("Has cycle: $result5")
    println("Expected: true")
    if (result5) {
        println("Cycle length: ${solver.getCycleLength(head)}")
    }
    
    // Test Case 6: Two nodes, no cycle
    println("\nTest Case 6: [1,2] with no cycle")
    head = solver.createListWithCycle(intArrayOf(1, 2), -1)
    print("List: ")
    solver.printList(head)
    val result6 = solver.hasCycle(head)
    println("Has cycle:  $result6")
    println("Expected: false")
    
    // Test Case 7: Long list with cycle
    println("\nTest Case 7: [1,2,3,4,5,6,7,8,9,10] with cycle at pos 3")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3)
    print("List (showing first 15 nodes): ")
    solver.printList(head, 15)
    val result7 = solver.hasCycle(head)
    println("Has cycle: $result7")
    println("Expected: true")
    if (result7) {
        println("Cycle length: ${solver. getCycleLength(head)}")
        println("Expected cycle length: 7 (nodes 4-10)")
    }
    
    // Test Case 8: Long list without cycle
    println("\nTest Case 8: [1,2,3,4,5,6,7,8,9,10] with no cycle")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), -1)
    print("List: ")
    solver.printList(head)
    val result8 = solver.hasCycle(head)
    println("Has cycle:  $result8")
    println("Expected: false")
    
    // Test Case 9: Cycle at last node
    println("\nTest Case 9: [1,2,3,4,5] with cycle at last node (pos 4)")
    head = solver.createListWithCycle(intArrayOf(1, 2, 3, 4, 5), 4)
    print("List (showing first 10 nodes): ")
    solver.printList(head)
    val result9 = solver.hasCycle(head)
    println("Has cycle:  $result9")
    println("Expected: true")
    if (result9) {
        println("Cycle length: ${solver.getCycleLength(head)}")
        println("Expected cycle length: 1")
    }
    
    // Test Case 10: Using hash set approach
    println("\nTest Case 10: Hash set approach on [3,2,0,-4] with cycle at pos 1")
    head = solver.createListWithCycle(intArrayOf(3, 2, 0, -4), 1)
    val result10 = solver.hasCycleHashSet(head)
    println("Has cycle (hash set): $result10")
    println("Expected: true")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
