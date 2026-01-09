/**
 * ============================================================================
 * PROBLEM:   Odd Even Linked List
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * LEETCODE:   #328
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, group all nodes with odd indices
 * together followed by nodes with even indices, and return the reordered list.
 * 
 * The first node is considered odd, second node even, and so on.  
 * Note:  The relative order inside both odd and even groups should remain the same. 
 * You must solve it in O(1) space and O(n) time.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - Head of reordered list (odd indices, then even indices)
 * 
 * CONSTRAINTS:
 * - Number of nodes:   [0, 10^4]
 * - -10^6 <= Node.val <= 10^6
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:    [1,2,3,4,5]
 * Output:  [1,3,5,2,4]
 * Explanation:  Odd indices (1,3,5) then even indices (2,4)
 * 
 * Example 2:
 * Input:   [2,1,3,5,6,4,7]
 * Output: [2,3,6,7,1,5,4]
 * Explanation:  Odd:  2,3,6,7 (positions 1,3,5,7), Even: 1,5,4 (positions 2,4,6)
 * 
 * Example 3:
 * Input:    [1,2]
 * Output: [1,2]
 * Explanation:  Already in correct order
 * 
 * Example 4:
 * Input:   [1]
 * Output: [1]
 * Explanation: Single node
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to separate nodes into two groups:
 * - Odd indexed nodes: 1st, 3rd, 5th, ... 
 * - Even indexed nodes:  2nd, 4th, 6th, ...
 * 
 * Then connect:  odd group → even group
 * 
 * KEY INSIGHT:
 * We can build two separate lists simultaneously:
 * - One pointer (odd) builds odd-indexed list
 * - Another pointer (even) builds even-indexed list
 * - Finally, connect odd's tail to even's head!  
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 1 -> 2 -> 3 -> 4 -> 5 -> null
 * 
 * Initial: 
 *   odd = 1, even = 2, evenHead = 2
 * 
 * Step 1: Connect odd to next odd
 *   odd. next = even. next
 *   1 -> 3
 *   odd = odd.next (now at 3)
 * 
 * Step 2: Connect even to next even
 *   even.next = odd.next
 *   2 -> 4
 *   even = even. next (now at 4)
 * 
 * After iteration:
 *   Odd list: 1 -> 3 -> 5
 *   Even list: 2 -> 4
 * 
 * Step 3: Connect odd tail to even head
 *   5. next = 2
 * 
 * Result: 1 -> 3 -> 5 -> 2 -> 4 -> null ✓
 * ```
 * 
 * DETAILED WALKTHROUGH:
 * ```
 * [1,2,3,4,5]
 * 
 * Initial:
 *   odd = 1, even = 2, evenHead = 2
 *   1 -> 2 -> 3 -> 4 -> 5
 *   o    e
 * 
 * Iteration 1:
 *   odd. next = even.next (1->3)
 *   odd = odd.next (odd=3)
 *   even.next = odd.next (2->4)
 *   even = even.next (even=4)
 *   
 *   Odd:  1 -> 3, Even: 2 -> 4
 * 
 * Iteration 2:
 *   odd.next = even.next (3->5)
 *   odd = odd.next (odd=5)
 *   even.next = odd.next (4->null)
 *   even = even.next (even=null)
 *   
 *   Odd:  1 -> 3 -> 5, Even: 2 -> 4 -> null
 * 
 * Connect: 
 *   odd.next = evenHead (5->2)
 *   
 * Final:  1 -> 3 -> 5 -> 2 -> 4 -> null
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Handle edge cases (null, single node)
 * 2. Initialize odd = head, even = head.next, evenHead = even
 * 3. While even and even.next are not null:
 *    a. odd.next = even.next (skip even node)
 *    b. odd = odd.next (move to next odd)
 *    c. even.next = odd. next (skip odd node)
 *    d. even = even.next (move to next even)
 * 4. Connect odd's tail to evenHead
 * 5. Return head
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - Single pass through the list
 * - Each node visited once
 * - Constant work per node
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only three pointers:  odd, even, evenHead
 * - No additional data structures
 * - In-place reordering
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: USING TWO ARRAYS
 * - Separate nodes into two arrays
 * - Concatenate arrays
 * - Rebuild list
 * - Time: O(n), Space: O(n)
 * - Not acceptable due to space constraint
 * 
 * APPROACH 2: CREATE NEW LISTS
 * - Create two new linked lists
 * - Add odd nodes to first, even to second
 * - Connect them
 * - Time: O(n), Space: O(n)
 * - Uses extra space
 * 
 * Two-pointer in-place is optimal: 
 * ✅ O(n) time
 * ✅ O(1) space
 * ✅ Single pass
 * ✅ No extra nodes created
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list → return null
 * 2. Single node → return that node
 * 3. Two nodes → already in correct order
 * 4.  Odd length list → odd group has one more
 * 5. Even length list → both groups equal
 * 6. All same values → reorder by position only
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not saving evenHead at the start
 *    Result:  Lose reference to even list
 *    Fix: Save even head before modifying pointers
 * 
 * 2. ❌ Wrong loop condition
 *    Result: NullPointerException or wrong result
 *    Fix: Check both even != null && even.next != null
 * 
 * 3. ❌ Forgetting to connect odd tail to even head
 *    Result:  Lose even list
 *    Fix: odd.next = evenHead after loop
 * 
 * 4. ❌ Moving pointers in wrong order
 *    Result: Lose nodes or wrong connections
 *    Fix: Update next pointers before moving
 * 
 * 5. ❌ Not handling single/two node cases
 *    Result: May access null. next
 *    Fix: Early return for small lists
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Data Segregation**: Separate data by attributes
 * 2. **Task Scheduling**: Group high/low priority tasks
 * 3. **Network Routing**: Separate packets by type
 * 4. **File Organization**: Group files by category
 * 5. **Queue Management**: Separate urgent/normal requests
 * 6. **Graphics**: Separate vertices by layer
 * 7. **Game Development**: Group entities by type
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify indices**:  Confirm 1-indexed (first is odd)
 * 2. **Draw the diagram**: Show two separate chains being built
 * 3. **Explain the skip pattern**: odd skips even, even skips odd
 * 4. **Save evenHead**:  Emphasize importance of this
 * 5. **Walk through example**: Show pointer movements
 * 6. **Space complexity**: Highlight O(1) achievement
 * 7. **Edge cases**: Single node, two nodes
 * 8. **Maintain order**: Emphasize relative order preserved
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Group in triplets (1st,4th,7th), (2nd,5th,8th), (3rd,6th,9th)
 * 2. Group nodes by value (odd values, even values)
 * 3.  Reverse the order within each group
 * 4. K-group separation
 * 5. Group positive values first, then negative
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 86: Partition List (similar grouping)
 * - LeetCode 725: Split Linked List in Parts
 * - LeetCode 143: Reorder List
 * - LeetCode 24: Swap Nodes in Pairs
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class OddEvenLL {
    
    /**
     * APPROACH 1: TWO-POINTER IN-PLACE (OPTIMAL)
     * 
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun oddEvenList(head: ListNode?): ListNode? {
        // Edge cases
        if (head?. next == null) return head
        
        // Initialize pointers
        var odd = head
        var even = head. next
        val evenHead = even  // Save even list head
        
        // Build two separate lists
        while (even?. next != null) {
            // Connect odd to next odd
            odd?. next = even. next
            odd = odd?.next
            
            // Connect even to next even
            even. next = odd?.next
            even = even.next
        }
        
        // Connect odd list tail to even list head
        odd?.next = evenHead
        
        return head
    }
    
    /**
     * APPROACH 2: USING TWO SEPARATE LISTS (Not optimal - uses extra space)
     * Shown for educational purposes
     * 
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun oddEvenListTwoLists(head: ListNode?): ListNode? {
        if (head?. next == null) return head
        
        // Create two dummy nodes
        val oddDummy = ListNode(0)
        val evenDummy = ListNode(0)
        var oddTail = oddDummy
        var evenTail = evenDummy
        
        var curr = head
        var index = 1
        
        // Separate into two lists
        while (curr != null) {
            val newNode = ListNode(curr.`val`)
            if (index % 2 == 1) {
                oddTail.next = newNode
                oddTail = oddTail.next!! 
            } else {
                evenTail.next = newNode
                evenTail = evenTail.next!!
            }
            curr = curr.next
            index++
        }
        
        // Connect odd to even
        oddTail.next = evenDummy.next
        
        return oddDummy.next
    }
    
    /**
     * VARIATION: Group by value (odd values first, then even values)
     */
    fun oddEvenListByValue(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val oddDummy = ListNode(0)
        val evenDummy = ListNode(0)
        var oddTail = oddDummy
        var evenTail = evenDummy
        
        var curr = head
        
        while (curr != null) {
            if (curr.`val` % 2 == 1) {
                oddTail. next = curr
                oddTail = oddTail.next!! 
            } else {
                evenTail.next = curr
                evenTail = evenTail.next!!
            }
            curr = curr.next
        }
        
        evenTail.next = null
        oddTail.next = evenDummy.next
        
        return oddDummy.next
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
    val solver = OddEvenLL()
    
    println("=". repeat(70))
    println("ODD EVEN LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Example [1,2,3,4,5]
    println("\nTest Case 1: [1,2,3,4,5]")
    var head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 1 -> 3 -> 5 -> 2 -> 4")
    
    // Test Case 2: Example [2,1,3,5,6,4,7]
    println("\nTest Case 2: [2,1,3,5,6,4,7]")
    head = solver.createList(intArrayOf(2, 1, 3, 5, 6, 4, 7))
    print("Original:  ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered:  ")
    solver.printList(head)
    println("Expected:  2 -> 3 -> 6 -> 7 -> 1 -> 5 -> 4")
    
    // Test Case 3: Two nodes [1,2]
    println("\nTest Case 3: [1,2]")
    head = solver.createList(intArrayOf(1, 2))
    print("Original:  ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered:  ")
    solver.printList(head)
    println("Expected:  1 -> 2")
    
    // Test Case 4: Single node [1]
    println("\nTest Case 4: [1]")
    head = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 1")
    
    // Test Case 5: Three nodes [1,2,3]
    println("\nTest Case 5: [1,2,3]")
    head = solver.createList(intArrayOf(1, 2, 3))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 1 -> 3 -> 2")
    
    // Test Case 6: Four nodes [1,2,3,4]
    println("\nTest Case 6: [1,2,3,4]")
    head = solver.createList(intArrayOf(1, 2, 3, 4))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 1 -> 3 -> 2 -> 4")
    
    // Test Case 7: All same values [5,5,5,5,5]
    println("\nTest Case 7: [5,5,5,5,5] - all same values")
    head = solver.createList(intArrayOf(5, 5, 5, 5, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 5 -> 5 -> 5 -> 5 -> 5 (structure changes, not values)")
    
    // Test Case 8: Longer list [1,2,3,4,5,6,7,8,9,10]
    println("\nTest Case 8: [1,2,3,4,5,6,7,8,9,10]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenList(head)
    print("Reordered: ")
    solver.printList(head)
    println("Expected: 1 -> 3 -> 5 -> 7 -> 9 -> 2 -> 4 -> 6 -> 8 -> 10")
    
    // Test Case 9: Using two lists approach
    println("\nTest Case 9: Two lists approach on [10,20,30,40,50]")
    head = solver.createList(intArrayOf(10, 20, 30, 40, 50))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenListTwoLists(head)
    print("Reordered (two lists): ")
    solver.printList(head)
    println("Expected: 10 -> 30 -> 50 -> 20 -> 40")
    
    // Test Case 10: Group by value (odd values, even values)
    println("\nTest Case 10: Group by value [1,2,3,4,5,6,7,8]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8))
    print("Original: ")
    solver.printList(head)
    head = solver.oddEvenListByValue(head)
    print("By value (odd first): ")
    solver.printList(head)
    println("Expected:  1 -> 3 -> 5 -> 7 -> 2 -> 4 -> 6 -> 8")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
