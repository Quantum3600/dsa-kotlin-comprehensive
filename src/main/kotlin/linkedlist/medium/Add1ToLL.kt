/**
 * ============================================================================
 * PROBLEM:   Add One to Number Represented as Linked List
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * RELATED:   LeetCode 369, GFG
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A number is represented by a linked list where each node contains a single
 * digit. The digits are stored such that the most significant digit is at
 * the head.  Add 1 to the number and return the resulting linked list.
 * 
 * INPUT FORMAT:
 * - Head of a linked list representing a number (most significant first)
 * 
 * OUTPUT FORMAT:
 * - Head of the resulting linked list after adding 1
 * 
 * CONSTRAINTS:
 * - Number of nodes:  [1, 10^4]
 * - 0 <= Node.val <= 9
 * - The number does not contain leading zeros (except 0 itself)
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:    [1,2,3]
 * Output:  [1,2,4]
 * Explanation: 123 + 1 = 124
 * 
 * Example 2:
 * Input:    [9,9,9]
 * Output: [1,0,0,0]
 * Explanation: 999 + 1 = 1000
 * 
 * Example 3:
 * Input:   [0]
 * Output: [1]
 * Explanation: 0 + 1 = 1
 * 
 * Example 4:
 * Input:    [1,9,9]
 * Output: [2,0,0]
 * Explanation: 199 + 1 = 200
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Adding 1 to a number is like manual addition starting from the rightmost
 * digit (least significant). However, the list is stored with most significant
 * digit first! 
 * 
 * CHALLENGES:
 * 1. List goes left-to-right (MSD to LSD)
 * 2. Addition works right-to-left (LSD to MSD)
 * 3. Need to handle carry propagation backward
 * 
 * KEY INSIGHT - MULTIPLE APPROACHES:
 * 
 * Approach A:  Reverse → Add → Reverse
 * - Reverse the list
 * - Add 1 from head (now LSD)
 * - Reverse back
 * 
 * Approach B: Recursion
 * - Recurse to end
 * - Add 1 on return path
 * - Propagate carry backward
 * 
 * Approach C: Find Rightmost Non-9
 * - Find the rightmost digit that's not 9
 * - Increment it
 * - Set all digits after it to 0
 * - If all 9s, prepend 1
 * 
 * VISUAL EXAMPLE (Approach A - Reverse):
 * ```
 * Input: 1 -> 9 -> 9
 * 
 * Step 1: Reverse
 *   9 -> 9 -> 1
 * 
 * Step 2: Add 1
 *   9 + 1 = 10 → digit:  0, carry: 1
 *   9 + carry(1) = 10 → digit: 0, carry: 1
 *   1 + carry(1) = 2 → digit: 2, carry: 0
 *   Result: 0 -> 0 -> 2
 * 
 * Step 3: Reverse back
 *   2 -> 0 -> 0 ✓
 * ```
 * 
 * VISUAL EXAMPLE (Approach C - Rightmost Non-9):
 * ```
 * Input: 1 -> 9 -> 9
 * 
 * Find rightmost non-9: node with value 1
 *   1 -> 9 -> 9
 *   ^
 *   rightmost non-9
 * 
 * Increment it: 1 becomes 2
 *   2 -> 9 -> 9
 * 
 * Set all after it to 0:
 *   2 -> 0 -> 0 ✓
 * ```
 * 
 * VISUAL EXAMPLE (All 9s):
 * ```
 * Input: 9 -> 9 -> 9
 * 
 * No non-9 digit found
 * Need to prepend 1: 1 -> 0 -> 0 -> 0
 * 
 * Set all to 0:
 *   0 -> 0 -> 0
 * 
 * Prepend 1: 
 *   1 -> 0 -> 0 -> 0 ✓
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH A (REVERSE):
 * TIME: O(n) - three passes (reverse, add, reverse)
 * SPACE: O(1) - in-place reversal
 * 
 * APPROACH B (RECURSION):
 * TIME: O(n) - single pass
 * SPACE: O(n) - recursion stack
 * 
 * APPROACH C (RIGHTMOST NON-9):
 * TIME: O(n) - single or two passes
 * SPACE: O(1) - only pointers
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: USING STACK
 * - Push all values onto stack
 * - Pop and add with carry
 * - Build result list
 * - Time: O(n), Space: O(n)
 * 
 * APPROACH 2: CONVERT TO INTEGER (Not scalable)
 * - Convert to integer, add 1, convert back
 * - Overflow issues for large numbers
 * - Not recommended
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single digit 0: [0] → [1]
 * 2. Single digit 9: [9] → [1,0]
 * 3. All 9s: [9,9,9] → [1,0,0,0]
 * 4. No carry: [1,2,3] → [1,2,4]
 * 5. Carry in middle: [1,9,9] → [2,0,0]
 * 6. Leading zeros after add: shouldn't happen with valid input
 * 7. Large number:  should handle any length
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not handling all 9s case
 *    Result: Missing most significant 1
 *    Fix: Check if carry remains after processing all digits
 * 
 * 2. ❌ Modifying without reversing back
 *    Result: Result in wrong order
 *    Fix:  Reverse back after addition
 * 
 * 3. ❌ Recursion without proper carry handling
 *    Result:  Incorrect result
 *    Fix: Return carry from recursion
 * 
 * 4. ❌ Not creating new head for all 9s
 *    Result: Wrong result
 *    Fix:  Prepend 1 when all digits are 9
 * 
 * 5. ❌ Integer overflow
 *    Result: Wrong answer for large numbers
 *    Fix:  Never convert to integer
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Auto-increment IDs**: Database primary keys
 * 2. **Version Numbers**:  Incrementing version strings
 * 3. **Sequence Generators**: Ticket numbers, order IDs
 * 4. **Counters**: Visitor counters, download counts
 * 5. **Financial Systems**: Account numbers, transaction IDs
 * 6. **Gaming**: Score increments, level progression
 * 7. **Calendar Systems**: Day/year increments
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify order**: Confirm most significant first
 * 2. **Ask about modification**: Can we modify original? 
 * 3. **Discuss approaches**: Mention all three methods
 * 4. **Handle all 9s**: Always walk through this case
 * 5. **Space constraints**: Ask if recursion allowed
 * 6. **Draw diagrams**: Show carry propagation
 * 7. **Edge cases**: 0, single 9, all 9s
 * 8. **Optimal choice**:  Rightmost non-9 for O(1) space
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Add k to the number (not just 1)
 * 2. Add 1 when least significant is first (easier!)
 * 3. Subtract 1 from the number
 * 4. Double the number
 * 5. Multiply by 10 (append 0)
 * 6. Check if number is power of 10 after adding 1
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 369: Plus One Linked List
 * - LeetCode 66: Plus One (array version)
 * - LeetCode 2: Add Two Numbers
 * - LeetCode 445: Add Two Numbers II
 * - LeetCode 67: Add Binary
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class Add1ToLL {
    
    /**
     * APPROACH 1: REVERSE → ADD → REVERSE (Clean)
     * 
     * TIME COMPLEXITY: O(n) - three passes
     * SPACE COMPLEXITY: O(1) - in-place
     */
    fun addOne(head: ListNode?): ListNode? {
        if (head == null) return null
        
        // Step 1: Reverse the list
        var reversed = reverseList(head)
        
        // Step 2: Add 1 from head (now LSD)
        var current = reversed
        var carry = 1
        var prev: ListNode? = null
        
        while (current != null && carry > 0) {
            val sum = current.`val` + carry
            current.`val` = sum % 10
            carry = sum / 10
            prev = current
            current = current. next
        }
        
        // If carry remains, add new node
        if (carry > 0) {
            prev?.next = ListNode(carry)
        }
        
        // Step 3: Reverse back
        return reverseList(reversed)
    }
    
    private fun reverseList(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        
        return prev
    }
    
    /**
     * APPROACH 2: RECURSION
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY:  O(n) - recursion stack
     */
    fun addOneRecursive(head: ListNode?): ListNode? {
        val carry = addOneHelper(head)
        
        // If carry remains, prepend new node
        if (carry > 0) {
            val newHead = ListNode(carry)
            newHead.next = head
            return newHead
        }
        
        return head
    }
    
    private fun addOneHelper(node: ListNode?): Int {
        if (node == null) return 1  // Base case: start adding 1
        
        // Recurse to end
        val carry = addOneHelper(node.next)
        
        // Add carry on return path
        val sum = node.`val` + carry
        node.`val` = sum % 10
        
        return sum / 10  // Return carry
    }
    
    /**
     * APPROACH 3: FIND RIGHTMOST NON-9 (Optimal O(1) space)
     * 
     * TIME COMPLEXITY:  O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun addOneOptimal(head: ListNode? ): ListNode? {
        if (head == null) return null
        
        // Find rightmost non-9 digit
        var rightmostNon9: ListNode? = null
        var current = head
        
        while (current != null) {
            if (current.`val` != 9) {
                rightmostNon9 = current
            }
            current = current.next
        }
        
        // Case 1: All 9s - need to prepend 1
        if (rightmostNon9 == null) {
            val newHead = ListNode(1)
            newHead.next = head
            current = head
            while (current != null) {
                current.`val` = 0
                current = current.next
            }
            return newHead
        }
        
        // Case 2: Has non-9 digit
        // Increment the rightmost non-9
        rightmostNon9.`val`++
        
        // Set all digits after it to 0
        current = rightmostNon9.next
        while (current != null) {
            current.`val` = 0
            current = current.next
        }
        
        return head
    }
    
    /**
     * APPROACH 4: USING STACK
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun addOneStack(head: ListNode?): ListNode? {
        if (head == null) return null
        
        // Push all nodes onto stack
        val stack = ArrayDeque<ListNode>()
        var current = head
        while (current != null) {
            stack.addLast(current)
            current = current.next
        }
        
        // Add 1 from end
        var carry = 1
        while (stack.isNotEmpty() && carry > 0) {
            val node = stack.removeLast()
            val sum = node.`val` + carry
            node.`val` = sum % 10
            carry = sum / 10
        }
        
        // If carry remains, prepend
        if (carry > 0) {
            val newHead = ListNode(carry)
            newHead.next = head
            return newHead
        }
        
        return head
    }
    
    /**
     * VARIATION: Add k to the number
     */
    fun addK(head: ListNode?, k:  Int): ListNode? {
        if (head == null) return null
        
        var reversed = reverseList(head)
        var current = reversed
        var carry = k
        var prev: ListNode? = null
        
        while (current != null || carry > 0) {
            val digit = current?.`val` ?: 0
            val sum = digit + carry
            
            if (current != null) {
                current. `val` = sum % 10
                prev = current
                current = current. next
            } else {
                prev?.next = ListNode(sum % 10)
                prev = prev?.next
            }
            
            carry = sum / 10
        }
        
        return reverseList(reversed)
    }
    
    // Helper function to create linked list from array
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
    
    // Helper function to print as number
    fun printAsNumber(head: ListNode?) {
        val arr = toArray(head)
        if (arr.isEmpty()) {
            println("0")
        } else {
            println(arr. joinToString(""))
        }
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
    val solver = Add1ToLL()
    
    println("=". repeat(70))
    println("ADD ONE TO LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Simple [1,2,3]
    println("\nTest Case 1: [1,2,3] → 123 + 1 = 124")
    var head = solver.createList(intArrayOf(1, 2, 3))
    print("Original: ")
    solver.printList(head)
    print("As number: ")
    solver.printAsNumber(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    print("As number: ")
    solver.printAsNumber(head)
    println("Expected: [1,2,4]")
    
    // Test Case 2: All 9s [9,9,9]
    println("\nTest Case 2: [9,9,9] → 999 + 1 = 1000")
    head = solver.createList(intArrayOf(9, 9, 9))
    print("Original: ")
    solver.printList(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    print("As number: ")
    solver.printAsNumber(head)
    println("Expected: [1,0,0,0]")
    
    // Test Case 3: Single digit [0]
    println("\nTest Case 3: [0] → 0 + 1 = 1")
    head = solver.createList(intArrayOf(0))
    print("Original: ")
    solver.printList(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    println("Expected: [1]")
    
    // Test Case 4: Single 9
    println("\nTest Case 4: [9] → 9 + 1 = 10")
    head = solver.createList(intArrayOf(9))
    print("Original: ")
    solver.printList(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    println("Expected: [1,0]")
    
    // Test Case 5: Carry in middle [1,9,9]
    println("\nTest Case 5: [1,9,9] → 199 + 1 = 200")
    head = solver.createList(intArrayOf(1, 9, 9))
    print("Original: ")
    solver.printList(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    println("Expected: [2,0,0]")
    
    // Test Case 6: No carry [1,2,8]
    println("\nTest Case 6: [1,2,8] → 128 + 1 = 129")
    head = solver.createList(intArrayOf(1, 2, 8))
    print("Original: ")
    solver.printList(head)
    head = solver.addOne(head)
    print("After +1: ")
    solver.printList(head)
    println("Expected: [1,2,9]")
    
    // Test Case 7: Recursive approach
    println("\nTest Case 7: Recursive on [4,9,9]")
    head = solver.createList(intArrayOf(4, 9, 9))
    print("Original:  ")
    solver.printList(head)
    head = solver.addOneRecursive(head)
    print("After +1 (recursive): ")
    solver.printList(head)
    println("Expected: [5,0,0]")
    
    // Test Case 8: Optimal approach
    println("\nTest Case 8: Optimal on [8,9,9]")
    head = solver.createList(intArrayOf(8, 9, 9))
    print("Original: ")
    solver.printList(head)
    head = solver.addOneOptimal(head)
    print("After +1 (optimal): ")
    solver.printList(head)
    println("Expected: [9,0,0]")
    
    // Test Case 9: Stack approach
    println("\nTest Case 9: Stack on [1,9,9,9]")
    head = solver.createList(intArrayOf(1, 9, 9, 9))
    print("Original: ")
    solver.printList(head)
    head = solver.addOneStack(head)
    print("After +1 (stack): ")
    solver.printList(head)
    println("Expected: [2,0,0,0]")
    
    // Test Case 10: Add k variation
    println("\nTest Case 10: Add k=25 to [1,7,5]")
    head = solver.createList(intArrayOf(1, 7, 5))
    print("Original (175): ")
    solver.printList(head)
    head = solver.addK(head, 25)
    print("After +25 (200): ")
    solver.printList(head)
    println("Expected: [2,0,0]")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
