/**
 * ============================================================================
 * PROBLEM:  Remove Nth Node From End of List
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * LEETCODE:  #19
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a linked list, remove the nth node from the end of the
 * list and return its head. 
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * - Integer n:  position from end (1-indexed)
 * 
 * OUTPUT FORMAT:
 * - Head of the modified linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [1, 30]
 * - 0 <= Node.val <= 100
 * - 1 <= n <= number of nodes
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:   head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * Explanation: Remove 4 (2nd from end)
 * 
 * Example 2:
 * Input:   head = [1], n = 1
 * Output: []
 * Explanation: Remove only node, list becomes empty
 * 
 * Example 3:
 * Input:  head = [1,2], n = 1
 * Output: [1]
 * Explanation: Remove 2 (1st from end = last node)
 * 
 * Example 4:
 * Input:   head = [1,2], n = 2
 * Output: [2]
 * Explanation: Remove 1 (2nd from end = first node)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION - THE TWO-POINTER GAP TECHNIQUE: 
 * 
 * Think about two people walking in a line:
 * - They start with a gap of n people between them
 * - They walk forward together, maintaining the gap
 * - When the front person reaches the end, the back person is exactly
 *   n positions from the end! 
 * 
 * This is the KEY INSIGHT for solving in one pass! 
 * 
 * VISUAL EXAMPLE:
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> 5 -> null
 * Remove 2nd from end (n = 2)
 * 
 * Step 1: Create gap of n between fast and slow
 *   fast moves n=2 steps ahead: 
 *   
 *   slow fast
 *    ↓    ↓
 *    1 -> 2 -> 3 -> 4 -> 5 -> null
 *   
 *   Actually, we need fast to be n+1 ahead to get prev! 
 *   
 *   slow     fast
 *    ↓        ↓
 *    1 -> 2 -> 3 -> 4 -> 5 -> null
 *              (gap of 2)
 * 
 * Step 2: Move both until fast reaches end
 *   
 *   slow          fast
 *    ↓             ↓
 *    1 -> 2 -> 3 -> 4 -> 5 -> null
 *   
 *         slow          fast
 *          ↓             ↓
 *    1 -> 2 -> 3 -> 4 -> 5 -> null
 *   
 *              slow          fast
 *               ↓             ↓
 *    1 -> 2 -> 3 -> 4 -> 5 -> null
 *   
 *   Fast reached null!  Slow is at node 3 (before target)
 * 
 * Step 3: Delete next node
 *   slow. next = slow.next.next
 *   3. next = 5 (skip 4)
 * 
 * Result: 1 -> 2 -> 3 -> 5 -> null ✓
 * ```
 * 
 * WHY USE DUMMY NODE?
 * Edge case: removing first node (n = length)
 * - Without dummy:  need special handling
 * - With dummy:  dummy.next points to head, unified logic! 
 * 
 * ```
 * Remove 1st node from [1,2,3], n = 3
 * 
 * With dummy:
 *   dummy -> 1 -> 2 -> 3 -> null
 *     ↑
 *   After removal: dummy -> 2 -> 3 -> null
 *   Return dummy.next ✓
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Create dummy node pointing to head
 * 2. Initialize slow = dummy, fast = dummy
 * 3. Move fast n+1 steps forward (create gap)
 * 4. Move both slow and fast until fast is null
 * 5. Delete: slow.next = slow.next. next
 * 6. Return dummy.next
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(L) where L = length of list
 * - Fast pointer moves (n+1) + (L-n-1) = L steps total
 * - Single pass through the list
 * - No backtracking needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only two pointers and one dummy node
 * - No additional data structures
 * - Constant extra space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: TWO-PASS (COUNT THEN DELETE)
 * - First pass: count total nodes (L)
 * - Second pass: go to (L - n)th node
 * - Delete next node
 * - Time: O(L), Space: O(1)
 * - Disadvantage: Two passes instead of one
 * 
 * APPROACH 2: USING RECURSION
 * - Recursively traverse to end
 * - Count backwards on return
 * - Delete when count equals n
 * - Time: O(L), Space: O(L) for recursion stack
 * - Disadvantage: Extra space
 * 
 * APPROACH 3: USING ARRAY/LIST
 * - Store all nodes in array
 * - Access (length - n)th node directly
 * - Delete it
 * - Time: O(L), Space: O(L)
 * - Disadvantage: Extra space
 * 
 * Two-pointer with dummy is optimal: 
 * ✅ Single pass
 * ✅ O(1) space
 * ✅ Handles all edge cases elegantly
 * ✅ Clean code
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single node, n=1 → return null (empty list)
 * 2. Remove first node (n = length) → need dummy node
 * 3. Remove last node (n = 1) → standard case
 * 4. Two nodes, remove first (n=2) → return [second]
 * 5. Two nodes, remove second (n=1) → return [first]
 * 6. n equals length → remove head
 * 7. All nodes have same value → structure matters
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not using dummy node
 *    Result: Complex special case for removing head
 *    Fix: Always use dummy node
 * 
 * 2. ❌ Moving fast n steps instead of n+1
 *    Result: Slow ends at target, not before target
 *    Fix: Move fast n+1 steps to get node before target
 * 
 * 3. ❌ Not handling remove head case
 *    Result:  Returning wrong head
 *    Fix: Use dummy. next as return value
 * 
 * 4. ❌ Off-by-one in gap calculation
 *    Result: Delete wrong node
 *    Fix:  Carefully count the gap
 * 
 * 5. ❌ Not checking fast. next in loop
 *    Result: NullPointerException
 *    Fix: Loop while fast.next != null or fast != null
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Undo Operations**: Remove last n operations
 * 2. **Log Trimming**: Remove oldest n entries
 * 3. **Queue Management**: Remove nth person from end of queue
 * 4. **Playlist**:  Remove nth-to-last song
 * 5. **Version Control**: Delete nth commit from end
 * 6. **Browser History**: Remove nth-last visited page
 * 7. **Network Packets**: Drop nth-last packet in buffer
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Ask about n**:  Confirm 1-indexed, confirm n is valid
 * 2. **Dummy node first**: Always start with dummy node
 * 3. **Draw the gap**: Visualize the n-step gap
 * 4. **Explain one-pass**: Emphasize efficiency
 * 5. **Handle edge cases**: Show single node, remove head
 * 6. **Two-pointer technique**: Explain the gap maintenance
 * 7. **Walk through example**: Show pointer movements
 * 8. **Alternative approaches**:  Mention two-pass, explain why one-pass is better
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Remove multiple nodes from end (n to n+k)
 * 2. Remove all nodes within n distance from end
 * 3. Return the removed node's value
 * 4. Remove nth node from start instead
 * 5. Remove nodes at positions n1, n2, ...  from end
 * 6. Find nth node from end without removing
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 876: Middle of Linked List (two-pointer)
 * - LeetCode 141: Linked List Cycle (two-pointer)
 * - LeetCode 142: Linked List Cycle II
 * - LeetCode 2095: Delete Middle Node
 * - LeetCode 237: Delete Node in Linked List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class RemoveNthFromEnd {
    
    /**
     * APPROACH 1: TWO-POINTER WITH DUMMY NODE (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(L) - single pass, L = length
     * SPACE COMPLEXITY: O(1) - two pointers + dummy
     */
    fun removeNthFromEnd(head: ListNode?, n:  Int): ListNode? {
        // Create dummy node
        val dummy = ListNode(0)
        dummy.next = head
        
        // Initialize two pointers
        var slow:  ListNode? = dummy
        var fast: ListNode? = dummy
        
        // Move fast n+1 steps ahead to create gap
        repeat(n + 1) {
            fast = fast?.next
        }
        
        // Move both until fast reaches end
        while (fast != null) {
            slow = slow?.next
            fast = fast?. next
        }
        
        // Delete nth node from end
        slow?.next = slow?.next?.next
        
        // Return new head
        return dummy.next
    }
    
    /**
     * APPROACH 2: TWO-PASS (COUNT THEN DELETE)
     * 
     * TIME COMPLEXITY: O(L) - two passes
     * SPACE COMPLEXITY: O(1)
     */
    fun removeNthFromEndTwoPass(head: ListNode?, n: Int): ListNode? {
        // First pass: count length
        var length = 0
        var curr = head
        while (curr != null) {
            length++
            curr = curr.next
        }
        
        // Edge case: remove first node
        if (n == length) {
            return head?.next
        }
        
        // Second pass: go to (length - n - 1)th node
        val target = length - n
        curr = head
        repeat(target - 1) {
            curr = curr?.next
        }
        
        // Delete next node
        curr?.next = curr?.next?.next
        
        return head
    }
    
    /**
     * APPROACH 3: USING RECURSION
     * 
     * TIME COMPLEXITY: O(L)
     * SPACE COMPLEXITY:  O(L) - recursion stack
     */
    fun removeNthFromEndRecursive(head: ListNode?, n: Int): ListNode? {
        val dummy = ListNode(0)
        dummy.next = head
        removeNthHelper(dummy, n)
        return dummy.next
    }
    
    private fun removeNthHelper(node: ListNode?, n: Int): Int {
        if (node == null) return 0
        
        val count = removeNthHelper(node.next, n) + 1
        
        if (count == n + 1) {
            node. next = node.next?.next
        }
        
        return count
    }
    
    /**
     * VARIATION: Remove and return the value of removed node
     */
    fun removeAndGetValue(head: ListNode?, n: Int): Pair<ListNode?, Int? > {
        val dummy = ListNode(0)
        dummy.next = head
        
        var slow: ListNode? = dummy
        var fast: ListNode? = dummy
        
        repeat(n + 1) {
            fast = fast?.next
        }
        
        while (fast != null) {
            slow = slow?.next
            fast = fast?.next
        }
        
        val removedValue = slow?.next?. `val`
        slow?.next = slow?.next?.next
        
        return Pair(dummy.next, removedValue)
    }
    
    /**
     * VARIATION: Find nth node from end (without removing)
     */
    fun findNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var slow = head
        var fast = head
        
        // Create gap of n
        repeat(n) {
            fast = fast?.next
        }
        
        // Move both until fast reaches end
        while (fast != null) {
            slow = slow?.next
            fast = fast?.next
        }
        
        return slow
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
    val solver = RemoveNthFromEnd()
    
    println("=". repeat(70))
    println("REMOVE NTH NODE FROM END - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Example [1,2,3,4,5], n=2
    println("\nTest Case 1: [1,2,3,4,5], remove 2nd from end")
    var head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 2)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 5")
    
    // Test Case 2: Single node [1], n=1
    println("\nTest Case 2: [1], remove 1st from end")
    head = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 1)
    print("After removal:  ")
    solver.printList(head)
    println("Expected:  []")
    
    // Test Case 3: Two nodes [1,2], n=1 (remove last)
    println("\nTest Case 3: [1,2], remove 1st from end (last node)")
    head = solver.createList(intArrayOf(1, 2))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 1)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 1")
    
    // Test Case 4: Two nodes [1,2], n=2 (remove first)
    println("\nTest Case 4: [1,2], remove 2nd from end (first node)")
    head = solver.createList(intArrayOf(1, 2))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 2)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 2")
    
    // Test Case 5: Remove first node [1,2,3,4,5], n=5
    println("\nTest Case 5: [1,2,3,4,5], remove 5th from end (first node)")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 5)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 2 -> 3 -> 4 -> 5")
    
    // Test Case 6: Remove last node [1,2,3,4,5], n=1
    println("\nTest Case 6: [1,2,3,4,5], remove 1st from end (last node)")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original:  ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 1)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 4")
    
    // Test Case 7: Middle node [1,2,3,4,5], n=3
    println("\nTest Case 7: [1,2,3,4,5], remove 3rd from end (middle)")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEnd(head, 3)
    print("After removal: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 4 -> 5")
    
    // Test Case 8: Two-pass approach
    println("\nTest Case 8: Two-pass approach on [10,20,30,40,50], n=2")
    head = solver.createList(intArrayOf(10, 20, 30, 40, 50))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEndTwoPass(head, 2)
    print("After removal (two-pass): ")
    solver.printList(head)
    println("Expected: 10 -> 20 -> 30 -> 50")
    
    // Test Case 9: Recursive approach
    println("\nTest Case 9: Recursive approach on [5,15,25,35,45], n=4")
    head = solver.createList(intArrayOf(5, 15, 25, 35, 45))
    print("Original: ")
    solver.printList(head)
    head = solver.removeNthFromEndRecursive(head, 4)
    print("After removal (recursive): ")
    solver.printList(head)
    println("Expected: 5 -> 25 -> 35 -> 45")
    
    // Test Case 10: Remove and get value
    println("\nTest Case 10: Remove and get value [100,200,300,400,500], n=3")
    head = solver.createList(intArrayOf(100, 200, 300, 400, 500))
    print("Original: ")
    solver.printList(head)
    val (newHead, removedVal) = solver.removeAndGetValue(head, 3)
    print("After removal: ")
    solver.printList(newHead)
    println("Removed value: $removedVal")
    println("Expected: 100 -> 200 -> 400 -> 500, removed:  300")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
