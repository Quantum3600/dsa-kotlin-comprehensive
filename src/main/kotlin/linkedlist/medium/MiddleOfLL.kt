/**
 * ============================================================================
 * PROBLEM:  Middle of the Linked List
 * DIFFICULTY: Medium (but actually Easy)
 * CATEGORY: Linked List
 * LEETCODE: #876
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, return the middle node.
 * If there are two middle nodes, return the second middle node.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - The middle node of the linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [1, 100]
 * - 1 <= Node.val <= 100
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:   [1,2,3,4,5]
 * Output:  [3,4,5] (node with value 3)
 * Explanation: The middle node is 3.
 * 
 * Example 2:
 * Input:  [1,2,3,4,5,6]
 * Output:  [4,5,6] (node with value 4)
 * Explanation: Two middle nodes (3 and 4), return the second one.
 * 
 * Example 3:
 * Input:   [1]
 * Output: [1]
 * Explanation: Single node is the middle. 
 * 
 * Example 4:
 * Input:   [1,2]
 * Output: [2]
 * Explanation: Second middle node. 
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION - THE SLOW AND FAST POINTER TECHNIQUE:
 * Imagine two people walking along a path: 
 * - Person A walks normally (1 step at a time)
 * - Person B walks twice as fast (2 steps at a time)
 * 
 * When Person B reaches the end: 
 * - Person A will be exactly in the middle! 
 * 
 * This is because: 
 * - If B moved 2n steps, A moved n steps
 * - n is exactly half the distance
 * 
 * KEY INSIGHT:
 * Use two pointers moving at different speeds: 
 * - Slow pointer:  moves 1 step per iteration
 * - Fast pointer: moves 2 steps per iteration
 * - When fast reaches end, slow is at middle! 
 * 
 * VISUAL EXAMPLE (ODD LENGTH):
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> 5 -> null
 * 
 * Initial: 
 *   slow = 1, fast = 1
 * 
 * Step 1:
 *   slow moves to 2
 *   fast moves to 3
 *   1 -> 2 -> 3 -> 4 -> 5 -> null
 *        s    f
 * 
 * Step 2:
 *   slow moves to 3
 *   fast moves to 5
 *   1 -> 2 -> 3 -> 4 -> 5 -> null
 *             s         f
 * 
 * Step 3:
 *   fast. next is null, stop
 *   slow is at 3 (middle!) ✓
 * ```
 * 
 * VISUAL EXAMPLE (EVEN LENGTH):
 * ```
 * List: 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
 * 
 * Initial:
 *   slow = 1, fast = 1
 * 
 * Step 1:
 *   slow = 2, fast = 3
 *   1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
 *        s    f
 * 
 * Step 2:
 *   slow = 3, fast = 5
 *   1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
 *             s         f
 * 
 * Step 3:
 *   slow = 4, fast = null
 *   1 -> 2 -> 3 -> 4 -> 5 -> 6 -> null
 *                  s    f
 * 
 * fast is null, stop
 * slow is at 4 (second middle!) ✓
 * ```
 * 
 * WHY SECOND MIDDLE FOR EVEN LENGTH?
 * - When fast reaches null, slow is past the first middle
 * - This is the desired behavior for this problem
 * - If you wanted first middle, use different stop condition
 * 
 * ALGORITHM STEPS:
 * 1. Initialize slow and fast pointers at head
 * 2. While fast and fast.next are not null:
 *    a. Move slow by 1 step
 *    b. Move fast by 2 steps
 * 3. Return slow (it's at the middle)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - We traverse the list once
 * - Fast pointer moves 2 steps, reaches end in n/2 iterations
 * - Each iteration is O(1)
 * - Total: O(n/2) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only two pointers used
 * - No additional data structures
 * - Constant extra space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: COUNT THEN TRAVERSE (Two Pass)
 * - First pass: Count total nodes (n)
 * - Second pass: Traverse to n/2 position
 * - Time: O(n), Space: O(1)
 * - Disadvantage: Requires two passes
 * 
 * APPROACH 2: STORE IN ARRAY
 * - Store all nodes in array
 * - Return array[n/2]
 * - Time: O(n), Space: O(n)
 * - Disadvantage: Uses extra space
 * 
 * APPROACH 3: RECURSIVE (Stack Space)
 * - Use recursion to find length and middle
 * - Time: O(n), Space: O(n) due to recursion
 * - Disadvantage: Uses stack space
 * 
 * Slow/Fast pointer approach is optimal: 
 * ✅ Single pass
 * ✅ O(1) space
 * ✅ Simple and elegant
 * ✅ No counting needed
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single node → return that node
 * 2. Two nodes → return second node
 * 3. Three nodes → return second node
 * 4. Odd number of nodes → return exact middle
 * 5. Even number of nodes → return second of two middles
 * 6. Large list (100 nodes) → should work efficiently
 * 7. All same values → structure matters, not values
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not checking fast. next before moving fast
 *    Result: NullPointerException
 *    Fix: Check fast != null && fast.next != null
 * 
 * 2. ❌ Moving pointers in wrong order
 *    Result:  Off-by-one error
 *    Fix: Move slow first, then fast (or vice versa, consistently)
 * 
 * 3. ❌ Wrong loop condition
 *    Result: Slow pointer in wrong position
 *    Fix: Loop while fast and fast.next are not null
 * 
 * 4. ❌ Not handling single node
 *    Result: May return null or error
 *    Fix: Single node is already the middle
 * 
 * 5. ❌ Trying to return first middle for even length
 *    Result:  Need different logic
 *    Fix: Use fast.next. next != null condition instead
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Pagination**: Find middle page of search results
 * 2. **Load Balancing**: Split tasks into two equal halves
 * 3. **Binary Search on List**: Find middle for divide-and-conquer
 * 4. **Media Player**: Find middle timestamp of playlist
 * 5. **Database Sharding**: Split data evenly
 * 6. **Merge Sort on Lists**: Find middle to split list
 * 7. **Network Routing**: Find midpoint server in chain
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Draw the diagram**: Show slow/fast pointers moving
 * 2. **Explain the ratio**: Fast moves 2x, so slow is at middle
 * 3. **Walk through example**: Show both odd and even cases
 * 4. **Mention edge cases**: Single node, two nodes
 * 5. **Discuss alternatives**: Two-pass vs one-pass
 * 6. **Space complexity**:  Highlight O(1) advantage
 * 7. **Follow-up ready**: Be prepared for variations
 * 8. **Clarify requirements**: First vs second middle for even length
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Return first middle node for even length
 * 2. Delete the middle node
 * 3. Split list into two halves at middle
 * 4. Find middle of middle (quarter point)
 * 5. Reverse second half starting from middle
 * 6. Check if list is palindrome (use middle)
 * 7. Find kth node from middle
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 234: Palindrome Linked List (uses middle finding)
 * - LeetCode 143: Reorder List (uses middle)
 * - LeetCode 148: Sort List (merge sort uses middle)
 * - LeetCode 19: Remove Nth from End (similar two-pointer technique)
 * - LeetCode 141: Linked List Cycle (same slow/fast technique)
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class MiddleOfLL {
    
    /**
     * APPROACH 1: SLOW AND FAST POINTERS (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n) - single pass
     * SPACE COMPLEXITY: O(1) - only two pointers
     * 
     * Returns the second middle node for even-length lists. 
     */
    fun middleNode(head: ListNode? ): ListNode? {
        // Handle null (though constraints say n >= 1)
        if (head == null) return null
        
        // Initialize both pointers at head
        var slow = head
        var fast = head
        
        // Move slow by 1, fast by 2
        while (fast?. next != null) {
            slow = slow?.next      // 1 step
            fast = fast. next?.next  // 2 steps
        }
        
        // Slow is now at middle
        return slow
    }
    
    /**
     * APPROACH 2: TWO-PASS (COUNT THEN TRAVERSE)
     * 
     * TIME COMPLEXITY:  O(n) - two passes
     * SPACE COMPLEXITY: O(1)
     */
    fun middleNodeTwoPass(head: ListNode?): ListNode? {
        if (head == null) return null
        
        // First pass: count nodes
        var count = 0
        var curr = head
        while (curr != null) {
            count++
            curr = curr.next
        }
        
        // Second pass: go to middle
        val middleIndex = count / 2
        curr = head
        repeat(middleIndex) {
            curr = curr?. next
        }
        
        return curr
    }
    
    /**
     * APPROACH 3: USING ARRAY
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY:  O(n)
     */
    fun middleNodeArray(head: ListNode?): ListNode? {
        if (head == null) return null
        
        val nodes = mutableListOf<ListNode>()
        var curr = head
        
        // Store all nodes
        while (curr != null) {
            nodes.add(curr)
            curr = curr.next
        }
        
        // Return middle
        return nodes[nodes.size / 2]
    }
    
    /**
     * VARIATION: Get FIRST middle node for even-length lists
     * For [1,2,3,4], return 2 instead of 3
     */
    fun firstMiddleNode(head: ListNode?): ListNode? {
        if (head == null) return null
        
        var slow = head
        var fast = head
        
        // Different condition:  stop before fast reaches end
        while (fast?.next?. next != null) {
            slow = slow?.next
            fast = fast. next?.next
        }
        
        return slow
    }
    
    /**
     * VARIATION: Split list into two halves at middle
     * Returns pair of (first half head, second half head)
     */
    fun splitAtMiddle(head: ListNode?): Pair<ListNode?, ListNode?> {
        if (head == null) return Pair(null, null)
        
        var slow = head
        var fast = head
        var prev: ListNode? = null
        
        while (fast?. next != null) {
            prev = slow
            slow = slow?. next
            fast = fast.next?.next
        }
        
        // Break the link
        prev?.next = null
        
        return Pair(head, slow)
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
        return result.toIntArray()
    }
    
    // Helper function to print linked list
    fun printList(head: ListNode?) {
        val arr = toArray(head)
        if (arr.isEmpty()) {
            println("null")
        } else {
            println(arr. joinToString(" -> "))
        }
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = MiddleOfLL()
    
    println("=". repeat(70))
    println("MIDDLE OF LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Odd length - 5 nodes
    println("\nTest Case 1: [1,2,3,4,5] (odd length)")
    val head1 = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head1)
    val middle1 = solver.middleNode(head1)
    print("From middle: ")
    solver.printList(middle1)
    println("Expected: 3 -> 4 -> 5")
    println("Middle value: ${middle1?.`val`}")
    println("Expected middle value: 3")
    
    // Test Case 2: Even length - 6 nodes
    println("\nTest Case 2: [1,2,3,4,5,6] (even length)")
    val head2 = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6))
    print("Original:  ")
    solver.printList(head2)
    val middle2 = solver.middleNode(head2)
    print("From middle: ")
    solver.printList(middle2)
    println("Expected: 4 -> 5 -> 6 (second middle)")
    println("Middle value: ${middle2?.`val`}")
    println("Expected middle value: 4")
    
    // Test Case 3: Single node
    println("\nTest Case 3: [1] (single node)")
    val head3 = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head3)
    val middle3 = solver.middleNode(head3)
    print("From middle: ")
    solver.printList(middle3)
    println("Expected:  1")
    
    // Test Case 4: Two nodes
    println("\nTest Case 4: [1,2] (two nodes)")
    val head4 = solver.createList(intArrayOf(1, 2))
    print("Original: ")
    solver.printList(head4)
    val middle4 = solver.middleNode(head4)
    print("From middle: ")
    solver.printList(middle4)
    println("Expected: 2 (second middle)")
    
    // Test Case 5: Three nodes
    println("\nTest Case 5: [1,2,3] (three nodes)")
    val head5 = solver.createList(intArrayOf(1, 2, 3))
    print("Original:  ")
    solver.printList(head5)
    val middle5 = solver.middleNode(head5)
    print("From middle: ")
    solver.printList(middle5)
    println("Expected: 2 -> 3")
    
    // Test Case 6: Four nodes
    println("\nTest Case 6: [1,2,3,4] (four nodes)")
    val head6 = solver.createList(intArrayOf(1, 2, 3, 4))
    print("Original: ")
    solver.printList(head6)
    val middle6 = solver. middleNode(head6)
    print("From middle: ")
    solver.printList(middle6)
    println("Expected: 3 -> 4 (second middle)")
    
    // Test Case 7: Two-pass approach
    println("\nTest Case 7: Two-pass approach on [1,2,3,4,5]")
    val head7 = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    val middle7 = solver.middleNodeTwoPass(head7)
    print("From middle (two-pass): ")
    solver.printList(middle7)
    println("Expected: 3 -> 4 -> 5")
    
    // Test Case 8: Array approach
    println("\nTest Case 8: Array approach on [1,2,3,4,5,6]")
    val head8 = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6))
    val middle8 = solver.middleNodeArray(head8)
    print("From middle (array): ")
    solver.printList(middle8)
    println("Expected: 4 -> 5 -> 6")
    
    // Test Case 9: First middle for even length
    println("\nTest Case 9: First middle of [1,2,3,4,5,6]")
    val head9 = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6))
    val firstMiddle = solver.firstMiddleNode(head9)
    print("From first middle: ")
    solver.printList(firstMiddle)
    println("Expected: 3 -> 4 -> 5 -> 6 (first middle)")
    
    // Test Case 10: Split at middle
    println("\nTest Case 10: Split [1,2,3,4,5] at middle")
    val head10 = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    val (first, second) = solver.splitAtMiddle(head10)
    print("First half: ")
    solver.printList(first)
    print("Second half: ")
    solver.printList(second)
    println("Expected first:  1 -> 2")
    println("Expected second: 3 -> 4 -> 5")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
