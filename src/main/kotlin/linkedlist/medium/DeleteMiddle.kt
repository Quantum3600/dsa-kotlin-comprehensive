/**
 * ============================================================================
 * PROBLEM:  Delete the Middle Node of a Linked List
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * LEETCODE:  #2095
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Given the head of a linked list, delete the middle node and return the head
 * of the modified linked list. 
 * 
 * The middle node is defined as the ⌊n / 2⌋th node from the start using 
 * 0-based indexing, where n is the number of nodes in the list.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - Head of the modified linked list with middle node deleted
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [1, 10^5]
 * - 1 <= Node.val <= 10^5
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1: 
 * Input:   [1,3,4,7,1,2,6]
 * Output: [1,3,4,1,2,6]
 * Explanation: n = 7, middle = ⌊7/2⌋ = 3 (0-indexed), delete node 7
 * 
 * Example 2:
 * Input:   [1,2,3,4]
 * Output: [1,2,4]
 * Explanation: n = 4, middle = ⌊4/2⌋ = 2 (0-indexed), delete node 3
 * 
 * Example 3:
 * Input:   [2,1]
 * Output: [2]
 * Explanation: n = 2, middle = ⌊2/2⌋ = 1 (0-indexed), delete node 1
 * 
 * Example 4:
 * Input:   [1]
 * Output: []
 * Explanation: Only one node, delete it, list becomes empty
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This problem combines two concepts:
 * 1. Finding the middle node (using slow/fast pointers)
 * 2. Deleting a node (requires tracking previous node)
 * 
 * KEY INSIGHT:
 * To delete a node, we need: 
 * - The node BEFORE the middle node (prev)
 * - Set prev.next = middle.next (skip the middle)
 * 
 * We can use slow/fast pointer technique, but also track the previous node! 
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 1 -> 3 -> 4 -> 7 -> 1 -> 2 -> 6 -> null
 *        0    1    2    3    4    5    6  (indices)
 * 
 * n = 7, middle index = ⌊7/2⌋ = 3
 * 
 * Step 1: Find middle using slow/fast
 *   Initial: 
 *   prev = null, slow = 1, fast = 1
 * 
 *   After iteration 1:
 *   prev = 1, slow = 3, fast = 4
 * 
 *   After iteration 2:
 *   prev = 3, slow = 4, fast = 1
 * 
 *   After iteration 3:
 *   prev = 4, slow = 7, fast = 6
 * 
 *   After iteration 4:
 *   fast. next = null, stop
 *   prev = 4, slow = 7 (middle!)
 * 
 * Step 2: Delete middle
 *   prev.next = slow.next
 *   4.next = 1 (skip 7)
 * 
 * Result: 1 -> 3 -> 4 -> 1 -> 2 -> 6 -> null ✓
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Handle edge case:  single node → return null
 * 2. Initialize prev = null, slow = head, fast = head
 * 3. While fast and fast.next are not null:
 *    a. prev = slow
 *    b. slow = slow.next (1 step)
 *    c. fast = fast.next.next (2 steps)
 * 4. Delete middle:  prev.next = slow.next
 * 5. Return head
 * 
 * WHY TRACK PREV? 
 * - To delete slow (middle), we need the node before it
 * - prev.next = slow.next skips the middle node
 * - Without prev, we can't delete the middle! 
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - Single pass through list using fast pointer
 * - Fast pointer moves 2 steps, reaches end in n/2 iterations
 * - Deletion is O(1)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only three pointers: prev, slow, fast
 * - No additional data structures
 * - Constant extra space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: COUNT THEN DELETE (Two Pass)
 * - First pass: count total nodes
 * - Second pass:  traverse to (n/2 - 1)th node
 * - Delete next node
 * - Time: O(n), Space: O(1)
 * - Disadvantage: Two passes
 * 
 * APPROACH 2: USING ARRAY
 * - Store all nodes in array
 * - Find middle index
 * - Reconnect nodes
 * - Time:  O(n), Space: O(n)
 * - Disadvantage: Extra space
 * 
 * Slow/Fast with prev is optimal: 
 * ✅ Single pass
 * ✅ O(1) space
 * ✅ Efficient deletion
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single node [1] → return null (list becomes empty)
 * 2. Two nodes [1,2] → delete 2nd, return [1]
 * 3. Three nodes [1,2,3] → delete 2nd, return [1,3]
 * 4. Even length [1,2,3,4] → delete index 2 (value 3)
 * 5. Odd length [1,2,3,4,5] → delete index 2 (value 3)
 * 6. Large list → should handle efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not handling single node case
 *    Result:  Trying to delete head without returning null
 *    Fix: Check if head. next == null, return null
 * 
 * 2. ❌ Not tracking previous node
 *    Result: Can't delete middle node
 *    Fix:  Maintain prev pointer
 * 
 * 3. ❌ Off-by-one error in middle calculation
 *    Result: Delete wrong node
 *    Fix: Use slow/fast technique correctly
 * 
 * 4. ❌ Not checking fast. next before fast. next.next
 *    Result: NullPointerException
 *    Fix: Check both fast != null && fast.next != null
 * 
 * 5. ❌ Forgetting to update prev
 *    Result: prev points to wrong node
 *    Fix: Update prev = slow before moving slow
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Playlist Management**: Remove middle song from queue
 * 2. **Process Scheduling**: Remove middle priority task
 * 3. **Cache Eviction**: Remove middle accessed item
 * 4. **Data Cleanup**: Remove median outlier
 * 5. **Tournament Bracket**:  Eliminate middle ranked player
 * 6. **Queue Management**: Remove middle customer
 * 7. **Buffer Management**: Delete middle element for balance
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify single node**:  Ask what to return (null or [])
 * 2. **Draw the diagram**: Show prev, slow, fast pointers
 * 3. **Explain deletion**: Need prev to skip middle
 * 4. **Walk through example**: Show pointer movements
 * 5. **Edge cases first**: Handle single node early
 * 6. **Space complexity**:  Highlight O(1) advantage
 * 7. **One pass**: Mention efficiency vs two-pass
 * 8. **Verify indices**:  Confirm middle calculation with examples
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Delete all middle nodes (iteratively)
 * 2. Delete kth node from middle
 * 3. Delete node closest to middle with specific value
 * 4. Delete multiple middle nodes in k-length windows
 * 5. Return deleted node's value
 * 6. Delete first middle for even length
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 876: Middle of Linked List (finding middle)
 * - LeetCode 19: Remove Nth Node From End
 * - LeetCode 203: Remove Linked List Elements
 * - LeetCode 237: Delete Node in Linked List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class DeleteMiddle {
    
    /**
     * APPROACH 1: SLOW/FAST POINTERS WITH PREV (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n) - single pass
     * SPACE COMPLEXITY: O(1) - three pointers
     */
    fun deleteMiddle(head: ListNode?): ListNode? {
        // Edge case: single node or empty
        if (head?. next == null) return null
        
        // Initialize pointers
        var prev: ListNode? = null
        var slow = head
        var fast = head
        
        // Find middle, tracking previous
        while (fast?. next != null) {
            prev = slow
            slow = slow?. next      // 1 step
            fast = fast. next?.next  // 2 steps
        }
        
        // Delete middle:  skip slow
        prev?.next = slow?. next
        
        return head
    }
    
    /**
     * APPROACH 2: TWO-PASS (COUNT THEN DELETE)
     * 
     * TIME COMPLEXITY: O(n) - two passes
     * SPACE COMPLEXITY: O(1)
     */
    fun deleteMiddleTwoPass(head: ListNode?): ListNode? {
        if (head?. next == null) return null
        
        // First pass: count nodes
        var count = 0
        var curr = head
        while (curr != null) {
            count++
            curr = curr.next
        }
        
        // Find position before middle
        val middleIndex = count / 2
        curr = head
        repeat(middleIndex - 1) {
            curr = curr?.next
        }
        
        // Delete middle
        curr?.next = curr?.next?.next
        
        return head
    }
    
    /**
     * APPROACH 3: USING ARRAY
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY:  O(n)
     */
    fun deleteMiddleArray(head: ListNode?): ListNode? {
        if (head?.next == null) return null
        
        // Store all nodes
        val nodes = mutableListOf<ListNode>()
        var curr = head
        while (curr != null) {
            nodes.add(curr)
            curr = curr.next
        }
        
        // Find middle and delete
        val middleIndex = nodes.size / 2
        if (middleIndex > 0) {
            nodes[middleIndex - 1].next = nodes. getOrNull(middleIndex + 1)
        }
        
        return head
    }
    
    /**
     * VARIATION: Delete and return the value of deleted node
     */
    fun deleteMiddleReturnValue(head: ListNode? ): Pair<ListNode?, Int? > {
        if (head?. next == null) return Pair(null, head?.`val`)
        
        var prev: ListNode? = null
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            prev = slow
            slow = slow?.next
            fast = fast.next?.next
        }
        
        val deletedValue = slow?.`val`
        prev?.next = slow?.next
        
        return Pair(head, deletedValue)
    }
    
    /**
     * VARIATION: Delete kth node from middle
     * k=0 is middle, k=1 is next to middle, k=-1 is before middle
     */
    fun deleteKthFromMiddle(head: ListNode?, k: Int): ListNode? {
        if (head == null) return null
        
        // Find middle first
        var slow = head
        var fast = head
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast. next?.next
        }
        
        // Move k steps from middle
        var target = slow
        repeat(k. coerceAtLeast(0)) {
            target = target?.next
        }
        
        // Find and delete target
        if (target == head && k >= 0) {
            return head. next
        }
        
        var curr = head
        while (curr?. next != target) {
            curr = curr?.next
        }
        curr?.next = target?.next
        
        return head
    }
    
    // Helper function to create linked list from array
    fun createList(arr: IntArray): ListNode? {
        if (arr.isEmpty()) return null
        val head = ListNode(arr[0])
        var curr = head
        for (i in 1 until arr.size) {
            curr. next = ListNode(arr[i])
            curr = curr.next!! 
        }
        return head
    }
    
    // Helper function to convert linked list to array
    fun toArray(head: ListNode?): IntArray {
        val result = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            result.add(curr.`val`)
            curr = curr.next
        }
        return result. toIntArray()
    }
    
    // Helper function to print linked list
    fun printList(head: ListNode?) {
        val arr = toArray(head)
        if (arr.isEmpty()) {
            println("[]")
        } else {
            println(arr.joinToString(" -> "))
        }
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = DeleteMiddle()
    
    println("=". repeat(70))
    println("DELETE MIDDLE NODE OF LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Example from problem [1,3,4,7,1,2,6]
    println("\nTest Case 1: [1,3,4,7,1,2,6] - 7 nodes")
    var head = solver.createList(intArrayOf(1, 3, 4, 7, 1, 2, 6))
    print("Original:   ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete:  ")
    solver.printList(head)
    println("Expected: 1 -> 3 -> 4 -> 1 -> 2 -> 6")
    println("Middle index: ⌊7/2⌋ = 3, deleted value: 7")
    
    // Test Case 2: Even length [1,2,3,4]
    println("\nTest Case 2: [1,2,3,4] - 4 nodes")
    head = solver.createList(intArrayOf(1, 2, 3, 4))
    print("Original:  ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 4")
    println("Middle index: ⌊4/2⌋ = 2, deleted value: 3")
    
    // Test Case 3: Two nodes [2,1]
    println("\nTest Case 3: [2,1] - 2 nodes")
    head = solver.createList(intArrayOf(2, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete: ")
    solver.printList(head)
    println("Expected: 2")
    println("Middle index: ⌊2/2⌋ = 1, deleted value: 1")
    
    // Test Case 4: Single node [1]
    println("\nTest Case 4: [1] - 1 node")
    head = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete:  ")
    solver.printList(head)
    println("Expected:  []")
    println("Middle index:  ⌊1/2⌋ = 0, deleted value: 1, list becomes empty")
    
    // Test Case 5: Three nodes [1,2,3]
    println("\nTest Case 5: [1,2,3] - 3 nodes")
    head = solver.createList(intArrayOf(1, 2, 3))
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete: ")
    solver.printList(head)
    println("Expected: 1 -> 3")
    println("Middle index:  ⌊3/2⌋ = 1, deleted value: 2")
    
    // Test Case 6: Five nodes [1,2,3,4,5]
    println("\nTest Case 6: [1,2,3,4,5] - 5 nodes")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original:  ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 4 -> 5")
    println("Middle index: ⌊5/2⌋ = 2, deleted value: 3")
    
    // Test Case 7: Two-pass approach
    println("\nTest Case 7: Two-pass approach on [1,2,3,4,5,6,7]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6, 7))
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddleTwoPass(head)
    print("After delete (two-pass): ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 5 -> 6 -> 7")
    
    // Test Case 8: Array approach
    println("\nTest Case 8: Array approach on [5,10,15,20,25]")
    head = solver.createList(intArrayOf(5, 10, 15, 20, 25))
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddleArray(head)
    print("After delete (array): ")
    solver.printList(head)
    println("Expected: 5 -> 10 -> 20 -> 25")
    
    // Test Case 9: Delete and return value
    println("\nTest Case 9: Delete and return value [10,20,30,40,50]")
    head = solver.createList(intArrayOf(10, 20, 30, 40, 50))
    print("Original: ")
    solver.printList(head)
    val (newHead, deletedVal) = solver.deleteMiddleReturnValue(head)
    print("After delete: ")
    solver.printList(newHead)
    println("Deleted value: $deletedVal")
    println("Expected deleted value: 30")
    
    // Test Case 10: Large list
    println("\nTest Case 10: Large list [1.. 20]")
    head = solver.createList(IntArray(20) { it + 1 })
    print("Original: ")
    solver.printList(head)
    head = solver.deleteMiddle(head)
    print("After delete: ")
    solver.printList(head)
    println("Expected: 1.. 10, then 12..20 (deleted 11)")
    println("Middle index: ⌊20/2⌋ = 10")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
