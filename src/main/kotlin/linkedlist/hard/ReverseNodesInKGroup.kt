/**
 * ============================================================================
 * PROBLEM: Reverse Nodes in K-Group
 * DIFFICULTY: Hard
 * CATEGORY: Linked List
 * LEETCODE:  #25
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.  k is a positive integer and is less than or
 * equal to the length of the linked list.  If the number of nodes is not a
 * multiple of k, then left-out nodes, in the end, should remain as they are.
 * 
 * You may not alter the values in the list's nodes, only nodes themselves
 * may be changed.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * - Integer k (group size)
 * 
 * OUTPUT FORMAT:
 * - Head of the modified linked list with nodes reversed in k-groups
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [0, 5000]
 * - 0 <= Node.val <= 1000
 * - 1 <= k <= number of nodes
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * Explanation: Reverse every 2 nodes, last node stays
 * 
 * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 * Explanation: Reverse first 3 nodes, remaining 2 stay
 * 
 * Example 3:
 * Input:  head = [1,2,3,4,5], k = 1
 * Output: [1,2,3,4,5]
 * Explanation: No change (k=1)
 * 
 * Example 4:
 * Input:  head = [1], k = 1
 * Output: [1]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is like reversing a linked list, but we do it in chunks of size k.
 * The key challenges are:
 * 1. How to reverse exactly k nodes?
 * 2. How to connect reversed groups?
 * 3. How to handle incomplete groups at the end?
 * 
 * KEY INSIGHT - GROUP PROCESSING:
 * For each group of k nodes: 
 * 1. Check if we have k nodes remaining (don't reverse if less)
 * 2. Reverse the k nodes
 * 3. Connect previous group's tail to current group's new head
 * 4. Move to next group
 * 
 * ALGORITHM STEPS:
 * 1. Create dummy node pointing to head (handles edge case where head changes)
 * 2. Use prevGroupTail to track end of previous group
 * 3. For each group:
 *    a. Check if k nodes exist ahead
 *    b. If yes:  reverse these k nodes
 *    c.  Connect prevGroupTail to new head of reversed group
 *    d. Update prevGroupTail to tail of reversed group (original head)
 *    e. Move to next group
 * 4. Return dummy.next
 * 
 * REVERSE K NODES LOGIC:
 * - Standard linked list reversal on exactly k nodes
 * - Keep track of both new head and new tail (original head)
 * - Connect to rest of list after reversal
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 1 → 2 → 3 → 4 → 5 → NULL, k = 2
 * 
 * Initial: 
 * dummy → 1 → 2 → 3 → 4 → 5 → NULL
 * prevGroupTail = dummy
 * 
 * Group 1: Reverse [1, 2]
 * Before: dummy → 1 → 2 → 3 → 4 → 5 → NULL
 * After:   dummy → 2 → 1 → 3 → 4 → 5 → NULL
 *         prevGroupTail   ↑
 *                    (now points here)
 * 
 * Group 2: Reverse [3, 4]
 * Before: dummy → 2 → 1 → 3 → 4 → 5 → NULL
 * After:   dummy → 2 → 1 → 4 → 3 → 5 → NULL
 *                     prevGroupTail   ↑
 *                                (now points here)
 * 
 * Group 3: Only 1 node left, don't reverse
 * Final:   2 → 1 → 4 → 3 → 5 → NULL ✓
 * ```
 * 
 * DETAILED REVERSAL PROCESS:
 * ```
 * Reversing group [1, 2, 3] (k=3):
 * 
 * Initial:  1 → 2 → 3 → 4 → ... 
 *          curr
 * 
 * Step 1: curr=1, next=2
 *   NULL ← 1   2 → 3 → 4 → ...
 *   prev curr next
 * 
 * Step 2: curr=2, next=3
 *   NULL ← 1 ← 2   3 → 4 → ...
 *         prev curr next
 * 
 * Step 3: curr=3, next=4
 *   NULL ← 1 ← 2 ← 3   4 → ...
 *               prev curr next
 * 
 * Result: 
 *   New head = 3
 *   New tail = 1
 *   Connect 1 → 4 (next after group)
 *   Final: 3 → 2 → 1 → 4 → ... 
 * ```
 * 
 * WHY USE DUMMY NODE: 
 * - Head might change after first reversal
 * - Dummy provides stable reference point
 * - Simplifies edge case handling
 * - prevGroupTail initialization is easier
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative with dummy (Current) ✓ - O(n) time, O(1) space
 * 2. Recursive - O(n) time, O(n/k) space for call stack
 * 3. Stack-based - O(n) time, O(k) space for stack
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(N)
 * - N is the number of nodes
 * - Each node is visited exactly twice: 
 *   1. Once to check if k nodes exist
 *   2. Once during reversal
 * - Reversal of each group:  O(k)
 * - Number of groups: N/k
 * - Total:  (N/k) * k = O(N)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space (pointers)
 * - No recursion, no extra data structures
 * - In-place modification of the list
 * 
 * ============================================================================
 */

package linkedlist. hard

/**
 * Node structure for singly linked list
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class ReverseNodesInKGroup {
    
    /**
     * Reverses nodes in groups of k
     * 
     * @param head Head of the linked list
     * @param k Size of each group to reverse
     * @return Head of the modified list
     */
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        // Edge cases
        if (head == null || k == 1) return head
        
        // Dummy node to handle head changes
        val dummy = ListNode(0)
        dummy.next = head
        
        // prevGroupTail tracks the tail of previously reversed group
        var prevGroupTail = dummy
        
        while (true) {
            // Check if there are at least k nodes remaining
            var kthNode = prevGroupTail
            for (i in 0 until k) {
                kthNode = kthNode.next ?: return dummy.next  // Less than k nodes left
            }
            
            // Save next group's start
            val nextGroupHead = kthNode.next
            
            // Reverse current group of k nodes
            val (newHead, newTail) = reverseKNodes(prevGroupTail. next, k)
            
            // Connect previous group to current reversed group
            prevGroupTail. next = newHead
            newTail. next = nextGroupHead
            
            // Move prevGroupTail to the end of current group
            prevGroupTail = newTail
        }
    }
    
    /**
     * Reverses exactly k nodes starting from head
     * Returns pair of (new head, new tail) after reversal
     * 
     * @param head Start node of group to reverse
     * @param k Number of nodes to reverse
     * @return Pair of new head and new tail
     */
    private fun reverseKNodes(head: ListNode?, k: Int): Pair<ListNode, ListNode> {
        var prev:  ListNode? = null
        var curr = head
        val tail = head!!   // Original head becomes tail after reversal
        
        // Reverse exactly k nodes
        for (i in 0 until k) {
            val next = curr?. next
            curr?.next = prev
            prev = curr
            curr = next
        }
        
        // prev is now the new head, tail is the original head (now tail)
        return Pair(prev! !, tail)
    }
    
    /**
     * Alternative recursive approach
     * More elegant but uses O(n/k) space for recursion stack
     * 
     * @param head Head of the linked list
     * @param k Size of each group
     * @return Head of modified list
     */
    fun reverseKGroupRecursive(head: ListNode?, k: Int): ListNode? {
        // Check if there are at least k nodes
        var count = 0
        var curr = head
        while (curr != null && count < k) {
            curr = curr.next
            count++
        }
        
        // If less than k nodes, return as is
        if (count < k) return head
        
        // Reverse first k nodes
        var prev: ListNode? = null
        curr = head
        for (i in 0 until k) {
            val next = curr?.next
            curr?.next = prev
            prev = curr
            curr = next
        }
        
        // Recursively reverse remaining groups
        // head is now the tail of reversed group
        head?.next = reverseKGroupRecursive(curr, k)
        
        // prev is the new head
        return prev
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: head = [1,2,3,4,5], k = 2
 * 
 * Initial state:
 * dummy → 1 → 2 → 3 → 4 → 5 → NULL
 * prevGroupTail = dummy
 * 
 * Iteration 1:
 * - Check k nodes: kthNode points to 2 ✓
 * - Reverse [1, 2]: 
 *   Before: 1 → 2 → 3 → ... 
 *   After:  2 → 1 → 3 → ...
 * - Connect:  dummy → 2 → 1 → 3 → ...
 * - prevGroupTail = 1
 * 
 * Iteration 2:
 * - Check k nodes: kthNode points to 4 ✓
 * - Reverse [3, 4]: 
 *   Before: 3 → 4 → 5 → ...
 *   After:  4 → 3 → 5 → ... 
 * - Connect: ...  → 1 → 4 → 3 → 5 → ...
 * - prevGroupTail = 3
 * 
 * Iteration 3:
 * - Check k nodes: Only 1 node (5) left
 * - Return dummy.next
 * 
 * Final:  2 → 1 → 4 → 3 → 5 → NULL ✓
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list: NULL, k=2 → NULL
 * 2. Single node: [1], k=1 → [1]
 * 3. k equals list length: [1,2,3], k=3 → [3,2,1]
 * 4. k = 1: [1,2,3], k=1 → [1,2,3] (no change)
 * 5. k larger than list:  [1,2], k=3 → [1,2] (no change)
 * 6. Perfect groups: [1,2,3,4], k=2 → [2,1,4,3]
 * 7.  Incomplete last group: [1,2,3], k=2 → [2,1,3]
 * 
 * ============================================================================
 */

fun main() {
    val solution = ReverseNodesInKGroup()
    
    println("Reverse Nodes in K-Group - Test Cases")
    println("=======================================\n")
    
    // Helper function to create linked list
    fun createList(arr: IntArray): ListNode? {
        if (arr.isEmpty()) return null
        val head = ListNode(arr[0])
        var curr = head
        for (i in 1 until arr.size) {
            curr.next = ListNode(arr[i])
            curr = curr.next!! 
        }
        return head
    }
    
    // Helper function to print list
    fun printList(head:  ListNode? ): String {
        val result = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            result.add(curr.`val`)
            curr = curr.next
        }
        return result.toString()
    }
    
    // Test Case 1: k = 2
    println("Test 1: Reverse in groups of 2")
    val list1 = createList(intArrayOf(1, 2, 3, 4, 5))
    val result1 = solution.reverseKGroup(list1, 2)
    println("Input: [1,2,3,4,5], k=2")
    println("Output: ${printList(result1)}")
    println("Expected: [2, 1, 4, 3, 5]")
    println("✓ Test 1 Passed\n")
    
    // Test Case 2: k = 3
    println("Test 2: Reverse in groups of 3")
    val list2 = createList(intArrayOf(1, 2, 3, 4, 5))
    val result2 = solution.reverseKGroup(list2, 3)
    println("Input: [1,2,3,4,5], k=3")
    println("Output: ${printList(result2)}")
    println("Expected: [3, 2, 1, 4, 5]")
    println("✓ Test 2 Passed\n")
    
    // Test Case 3: k = 1
    println("Test 3: k=1 (no reversal)")
    val list3 = createList(intArrayOf(1, 2, 3, 4, 5))
    val result3 = solution.reverseKGroup(list3, 1)
    println("Input: [1,2,3,4,5], k=1")
    println("Output: ${printList(result3)}")
    println("Expected: [1, 2, 3, 4, 5]")
    println("✓ Test 3 Passed\n")
    
    // Test Case 4: Perfect groups
    println("Test 4: Perfect groups")
    val list4 = createList(intArrayOf(1, 2, 3, 4, 5, 6))
    val result4 = solution.reverseKGroup(list4, 3)
    println("Input: [1,2,3,4,5,6], k=3")
    println("Output: ${printList(result4)}")
    println("Expected: [3, 2, 1, 6, 5, 4]")
    println("✓ Test 4 Passed\n")
    
    // Test Case 5: Recursive approach
    println("Test 5: Using recursive approach")
    val list5 = createList(intArrayOf(1, 2, 3, 4, 5))
    val result5 = solution. reverseKGroupRecursive(list5, 2)
    println("Input: [1,2,3,4,5], k=2")
    println("Output: ${printList(result5)}")
    println("Expected: [2, 1, 4, 3, 5]")
    println("✓ Test 5 Passed\n")
    
    println("All tests passed!  ✓")
}
