/**
 * ============================================================================
 * PROBLEM:  Reverse Linked List (Iterative)
 * DIFFICULTY: Medium
 * CATEGORY:  Linked List
 * LEETCODE: #206
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, reverse the list iteratively and 
 * return the reversed list. 
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - Head of the reversed linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [0, 5000]
 * - -5000 <= Node.val <= 5000
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:   1 -> 2 -> 3 -> 4 -> 5 -> null
 * Output: 5 -> 4 -> 3 -> 2 -> 1 -> null
 * 
 * Example 2:
 * Input:  1 -> 2 -> null
 * Output: 2 -> 1 -> null
 * 
 * Example 3:
 * Input:  null
 * Output: null
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of reversing a chain of paper clips. You need to: 
 * 1. Detach the first clip from the second
 * 2. Attach it to what was previously behind it
 * 3. Move to the next clip and repeat
 * 
 * In a linked list, each node points to the next node.  To reverse:
 * - Make each node point to its previous node instead
 * - This requires tracking three pointers: previous, current, next
 * 
 * KEY INSIGHT:
 * We need to change the direction of arrows (next pointers), but we must
 * save the next node BEFORE changing the current node's pointer, otherwise
 * we lose access to the rest of the list! 
 * 
 * VISUAL EXAMPLE:
 * ```
 * Original: 1 -> 2 -> 3 -> 4 -> null
 * 
 * Step 0: Setup
 *   prev = null
 *   curr = 1
 *   next = ? 
 * 
 * Step 1: At node 1
 *   prev    curr   next
 *   null     1  ->  2  ->  3  ->  4  -> null
 *   
 *   next = curr. next  (save 2)
 *   curr.next = prev  (1 now points to null)
 *   Result: null <- 1    2 -> 3 -> 4 -> null
 *   
 *   prev = curr  (move prev to 1)
 *   curr = next  (move curr to 2)
 * 
 * Step 2: At node 2
 *           prev   curr   next
 *   null <- 1       2  ->  3  ->  4  -> null
 *   
 *   next = curr. next  (save 3)
 *   curr.next = prev  (2 now points to 1)
 *   Result: null <- 1 <- 2    3 -> 4 -> null
 *   
 *   prev = curr  (move prev to 2)
 *   curr = next  (move curr to 3)
 * 
 * Step 3: At node 3
 *                  prev   curr   next
 *   null <- 1 <- 2        3  ->  4  -> null
 *   
 *   next = curr.next  (save 4)
 *   curr.next = prev  (3 now points to 2)
 *   Result: null <- 1 <- 2 <- 3    4 -> null
 *   
 *   prev = curr  (move prev to 3)
 *   curr = next  (move curr to 4)
 * 
 * Step 4: At node 4
 *                         prev   curr   next
 *   null <- 1 <- 2 <- 3          4  -> null
 *   
 *   next = curr.next  (save null)
 *   curr.next = prev  (4 now points to 3)
 *   Result: null <- 1 <- 2 <- 3 <- 4
 *   
 *   prev = curr  (move prev to 4)
 *   curr = next  (curr becomes null, loop ends)
 * 
 * Final:  prev points to new head (4)
 *   4 -> 3 -> 2 -> 1 -> null
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Initialize prev = null (nothing before first node)
 * 2. Initialize curr = head
 * 3. While curr is not null:
 *    a. Save next node: next = curr.next
 *    b. Reverse current link: curr.next = prev
 *    c. Move prev forward: prev = curr
 *    d. Move curr forward: curr = next
 * 4. Return prev (new head)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n)
 * - We visit each node exactly once
 * - n = number of nodes in the list
 * - Each operation (pointer reassignment) is O(1)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only three pointers used:  prev, curr, next
 * - No additional data structures
 * - No recursion, so no stack space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 2:  RECURSIVE (Space:  O(n))
 * - Recursively reverse from the end
 * - Space complexity is O(n) due to recursion stack
 * - Less intuitive but elegant
 * 
 * APPROACH 3: USING STACK (Space: O(n))
 * - Push all nodes onto a stack
 * - Pop and rebuild links
 * - Simple but uses extra space
 * 
 * APPROACH 4: CONVERT TO ARRAY (Space: O(n))
 * - Store values in array
 * - Reverse array
 * - Create new list
 * - Inefficient for large lists
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list (head = null) → return null
 * 2. Single node → return the same node
 * 3. Two nodes → reverse normally
 * 4. Already reversed → becomes original
 * 5. All same values �� structure changes, values same
 * 6. Large list (5000 nodes) → should handle efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not saving next node before changing curr. next
 *    Result:  Lose access to rest of list! 
 * 
 * 2. ❌ Returning curr instead of prev at the end
 *    Result: curr is null, prev is the new head
 * 
 * 3. ❌ Not handling null input
 *    Result: NullPointerException
 * 
 * 4. ❌ Forgetting to move prev and curr forward
 *    Result:  Infinite loop
 * 
 * 5. ❌ Modifying original list when you shouldn't
 *    Sometimes you need to preserve original
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Undo Functionality**: Browser back button, undo in text editors
 * 2. **Music Playlist**: Reverse playback order
 * 3. **Transaction History**: Display most recent first
 * 4. **Call Stack**: Stack unwinding in debuggers
 * 5. **File Systems**: Reverse directory path
 * 6. **DNA Sequences**: Reverse complementary strand
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Draw it out**: Always visualize with 3-4 nodes
 * 2. **Explain the three pointers**: prev, curr, next
 * 3. **Walk through one iteration**: Show the pointer changes
 * 4. **Mention space complexity**: O(1) is a big advantage
 * 5. **Ask about constraints**: Can we modify original?  Need to preserve?
 * 6. **Discuss alternatives**: Mention recursive approach
 * 7. **Handle edge cases**: Always check for null
 * 8. **Test with examples**: Walk through 0, 1, 2, many nodes
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Reverse a portion of list (left to right index)
 * 2. Reverse in groups of k
 * 3. Reverse alternate k nodes
 * 4. Reverse doubly linked list
 * 5. Check if list is palindrome (reverse and compare)
 * 6. Implement using recursion
 * 7. Reverse without modifying original (create new list)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 92: Reverse Linked List II (reverse portion)
 * - LeetCode 25: Reverse Nodes in k-Group
 * - LeetCode 234: Palindrome Linked List (uses reverse)
 * - LeetCode 143: Reorder List (uses reverse)
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class ReverseIterative {
    
    /**
     * APPROACH 1: ITERATIVE WITH THREE POINTERS (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n) - single pass through list
     * SPACE COMPLEXITY: O(1) - only three pointers
     * 
     * This is the standard optimal solution for reversing a linked list. 
     */
    fun reverseList(head: ListNode?): ListNode? {
        // Handle empty list
        if (head == null) return null
        
        // Initialize three pointers
        var prev: ListNode? = null  // Nothing before first node
        var curr:  ListNode? = head  // Start at head
        var next: ListNode? = null  // Will store next node
        
        // Traverse and reverse
        while (curr != null) {
            // Step 1: Save next node before we change curr.next
            next = curr. next
            
            // Step 2: Reverse the current node's pointer
            curr.next = prev
            
            // Step 3: Move prev and curr one step forward
            prev = curr
            curr = next
        }
        
        // prev is now pointing to the new head
        return prev
    }
    
    /**
     * APPROACH 2: RECURSIVE
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY:  O(n) - recursion stack
     */
    fun reverseListRecursive(head: ListNode?): ListNode? {
        // Base cases
        if (head == null || head.next == null) return head
        
        // Recursive case: reverse rest of list
        val newHead = reverseListRecursive(head.next)
        
        // Make next node point back to current
        head.next?.next = head
        head.next = null
        
        return newHead
    }
    
    /**
     * APPROACH 3: USING STACK
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n) - stack storage
     */
    fun reverseListStack(head: ListNode? ): ListNode? {
        if (head == null) return null
        
        val stack = ArrayDeque<ListNode>()
        var curr = head
        
        // Push all nodes onto stack
        while (curr != null) {
            stack.addLast(curr)
            curr = curr.next
        }
        
        // Pop and rebuild
        val newHead = stack.removeLast()
        curr = newHead
        
        while (stack.isNotEmpty()) {
            curr?.next = stack.removeLast()
            curr = curr?. next
        }
        curr?.next = null
        
        return newHead
    }
    
    // Helper function to create linked list from array
    fun createList(arr: IntArray): ListNode? {
        if (arr. isEmpty()) return null
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
        println(arr.joinToString(" -> ") + " -> null")
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = ReverseIterative()
    
    println("=". repeat(70))
    println("REVERSE LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Regular list
    println("\nTest Case 1: Regular list [1,2,3,4,5]")
    var head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original:  ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed:  ")
    solver.printList(head)
    println("Expected: 5 -> 4 -> 3 -> 2 -> 1 -> null")
    
    // Test Case 2: Two nodes
    println("\nTest Case 2: Two nodes [1,2]")
    head = solver.createList(intArrayOf(1, 2))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: 2 -> 1 -> null")
    
    // Test Case 3: Single node
    println("\nTest Case 3: Single node [1]")
    head = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: 1 -> null")
    
    // Test Case 4: Empty list
    println("\nTest Case 4: Empty list []")
    head = solver.createList(intArrayOf())
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: null")
    
    // Test Case 5: Longer list
    println("\nTest Case 5: Longer list [1,2,3,4,5,6,7,8,9,10]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: 10 -> 9 -> 8 -> 7 -> 6 -> 5 -> 4 -> 3 -> 2 -> 1 -> null")
    
    // Test Case 6: Duplicate values
    println("\nTest Case 6: Duplicate values [1,2,2,3,3,3]")
    head = solver.createList(intArrayOf(1, 2, 2, 3, 3, 3))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: 3 -> 3 -> 3 -> 2 -> 2 -> 1 -> null")
    
    // Test Case 7: Negative numbers
    println("\nTest Case 7: Negative numbers [-5,-3,0,2,4]")
    head = solver.createList(intArrayOf(-5, -3, 0, 2, 4))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseList(head)
    print("Reversed: ")
    solver.printList(head)
    println("Expected: 4 -> 2 -> 0 -> -3 -> -5 -> null")
    
    // Test Case 8: Recursive approach
    println("\nTest Case 8: Recursive approach [1,2,3,4,5]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseListRecursive(head)
    print("Reversed (recursive): ")
    solver.printList(head)
    println("Expected: 5 -> 4 -> 3 -> 2 -> 1 -> null")
    
    // Test Case 9: Stack approach
    println("\nTest Case 9: Stack approach [1,2,3,4,5]")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.reverseListStack(head)
    print("Reversed (stack): ")
    solver.printList(head)
    println("Expected: 5 -> 4 -> 3 -> 2 -> 1 -> null")
    
    // Test Case 10: Large list
    println("\nTest Case 10: Large list (first and last 5 elements)")
    val largeArray = IntArray(100) { it + 1 }
    head = solver.createList(largeArray)
    println("Original: 1 -> 2 -> 3 -> 4 -> 5 -> ...  -> 96 -> 97 -> 98 -> 99 -> 100")
    head = solver.reverseList(head)
    println("Reversed:  100 -> 99 -> 98 -> 97 -> 96 -> ...  -> 5 -> 4 -> 3 -> 2 -> 1")
    val reversed = solver.toArray(head)
    println("First 5: ${reversed.take(5)}")
    println("Last 5: ${reversed.takeLast(5)}")
    println("Expected first 5: [100, 99, 98, 97, 96]")
    println("Expected last 5: [5, 4, 3, 2, 1]")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
