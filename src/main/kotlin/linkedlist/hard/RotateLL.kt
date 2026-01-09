/**
 * ============================================================================
 * PROBLEM:  Rotate Linked List
 * DIFFICULTY: Hard
 * CATEGORY: Linked List
 * LEETCODE:  #61
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a linked list, rotate the list to the right by k places. 
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * - Integer k (number of positions to rotate)
 * 
 * OUTPUT FORMAT: 
 * - Head of the rotated linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [0, 500]
 * - -100 <= Node.val <= 100
 * - 0 <= k <= 2 * 10^9
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [4,5,1,2,3]
 * Explanation: 
 *   Rotate 1: [5,1,2,3,4]
 *   Rotate 2: [4,5,1,2,3]
 * 
 * Example 2:
 * Input: head = [0,1,2], k = 4
 * Output: [2,0,1]
 * Explanation: k=4 is same as k=1 (4 % 3 = 1)
 *   After rotating 4 times, it's equivalent to rotating 1 time
 * 
 * Example 3:
 * Input:  head = [1], k = 0
 * Output: [1]
 * 
 * Example 4:
 * Input:  head = [1,2], k = 1
 * Output:  [2,1]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Rotating a linked list to the right by k means:
 * - The last k nodes move to the front
 * - The remaining nodes shift to the right
 * 
 * KEY INSIGHT - MAKE IT CIRCULAR:
 * Instead of repeatedly moving nodes, we can:
 * 1. Make the list circular (connect tail to head)
 * 2. Find the new tail (at position length - k - 1)
 * 3. Break the circle at new tail
 * 4. The node after new tail becomes new head
 * 
 * CRITICAL OPTIMIZATION:
 * Since k can be very large (up to 2 * 10^9), we need to use modulo: 
 * - k = k % length (rotating by length gives same list)
 * - If k = 0, no rotation needed
 * 
 * ALGORITHM STEPS:
 * 1. Find length of list and get tail node
 * 2. Handle edge cases (empty, single node, k=0)
 * 3. Calculate effective rotations:  k = k % length
 * 4. If k = 0, return original head (no rotation)
 * 5. Make list circular:  tail. next = head
 * 6. Find new tail at position (length - k - 1)
 * 7. New head = newTail.next
 * 8. Break circle: newTail.next = null
 * 9. Return new head
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: [1, 2, 3, 4, 5], k = 2
 * 
 * Step 1: Find length and tail
 * 1 → 2 → 3 → 4 → 5 → NULL
 *                 ↑
 *               tail
 * length = 5
 * 
 * Step 2: Calculate effective rotations
 * k = 2 % 5 = 2
 * 
 * Step 3: Make circular
 * 1 → 2 → 3 → 4 → 5
 * ↑_______________|
 * 
 * Step 4: Find new tail at position (5 - 2 - 1) = 2
 * Count from head: 0→1→2→3 (node 3)
 * 1 → 2 → 3 → 4 → 5
 *         ↑
 *      new tail
 * 
 * Step 5: Break circle
 * New head = 4
 * 3. next = null
 * 
 * Result: 4 → 5 → 1 → 2 → 3 → NULL ✓
 * ```
 * 
 * DETAILED WALKTHROUGH WITH k > length:
 * ```
 * Input: [0, 1, 2], k = 4
 * 
 * Step 1: length = 3, tail = 2
 * Step 2: k = 4 % 3 = 1 (rotating 4 times = rotating 1 time)
 * 
 * Step 3: Make circular
 * 0 → 1 → 2
 * ↑_______|
 * 
 * Step 4: New tail at position (3 - 1 - 1) = 1
 * 0 → 1 → 2
 *     ↑
 *  new tail
 * 
 * Step 5: Break circle
 * New head = 2
 * 1.next = null
 * 
 * Result: 2 → 0 → 1 → NULL ✓
 * ```
 * 
 * WHY THIS APPROACH IS OPTIMAL:
 * - Single pass to find length:  O(n)
 * - Single pass to find new tail: O(n)
 * - No repeated rotations
 * - Handles large k efficiently with modulo
 * - In-place:  O(1) space
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Circular list method (Current) ✓ - O(n) time, O(1) space
 * 2. Repeated single rotations - O(k*n) time, O(1) space (too slow)
 * 3. Move last k nodes - O(n) time, O(1) space (more complex)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(N)
 * - First pass to find length and tail: O(n)
 * - Calculate k % length: O(1)
 * - Second pass to find new tail: O(n)
 * - Overall: O(n) + O(n) = O(n)
 * - Note: Even though we traverse twice, it's still linear
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space (pointers)
 * - No additional data structures
 * - In-place modification
 * 
 * ============================================================================
 */

package linkedlist. hard

/**
 * Node structure for singly linked list
 * 
 * @property val The value stored in the node
 * @property next Reference to next node
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class RotateLL {
    
    /**
     * Rotates linked list to the right by k positions
     * 
     * @param head Head of the linked list
     * @param k Number of positions to rotate
     * @return Head of the rotated list
     */
    fun rotateRight(head: ListNode?, k: Int): ListNode? {
        // Edge case 1: empty list or single node
        if (head == null || head.next == null) return head
        
        // Edge case 2: k = 0 (no rotation)
        if (k == 0) return head
        
        // Step 1: Find length and tail of the list
        var length = 1
        var tail = head
        while (tail?. next != null) {
            tail = tail.next
            length++
        }
        
        // Step 2: Calculate effective rotations (handle k > length)
        val effectiveK = k % length
        
        // If effective rotation is 0, return original list
        if (effectiveK == 0) return head
        
        // Step 3: Make the list circular
        tail?. next = head
        
        // Step 4: Find the new tail (at position length - k - 1)
        // New tail is the node before the new head
        var stepsToNewTail = length - effectiveK - 1
        var newTail = head
        for (i in 0 until stepsToNewTail) {
            newTail = newTail?.next
        }
        
        // Step 5: New head is the node after new tail
        val newHead = newTail?.next
        
        // Step 6: Break the circle
        newTail?.next = null
        
        return newHead
    }
    
    /**
     * Alternative approach:  Move last k nodes to front
     * More intuitive but slightly more complex
     * 
     * @param head Head of the linked list
     * @param k Number of positions to rotate
     * @return Head of the rotated list
     */
    fun rotateRightAlternative(head: ListNode?, k:  Int): ListNode? {
        if (head == null || head.next == null || k == 0) return head
        
        // Find length
        var length = 0
        var curr = head
        while (curr != null) {
            length++
            curr = curr.next
        }
        
        val effectiveK = k % length
        if (effectiveK == 0) return head
        
        // Find the node at position (length - k)
        // This will be the new head
        var slow = head
        var fast = head
        
        // Move fast pointer k steps ahead
        for (i in 0 until effectiveK) {
            fast = fast?. next
        }
        
        // Move both pointers until fast reaches last node
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast. next
        }
        
        // Now slow is at new tail position
        val newHead = slow?.next
        slow?.next = null  // Break the list
        fast?.next = head  // Connect old tail to old head
        
        return newHead
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Problem: Rotate [1,2,3,4,5] by k=2
 * 
 * Step-by-step execution:
 * 
 * Initial: 1 → 2 → 3 → 4 → 5 → NULL
 * 
 * Phase 1: Find length and tail
 *   Start: head = 1, length = 1, tail = 1
 *   Loop: tail = 2, length = 2
 *   Loop: tail = 3, length = 3
 *   Loop: tail = 4, length = 4
 *   Loop: tail = 5, length = 5
 *   Exit:  length = 5, tail
