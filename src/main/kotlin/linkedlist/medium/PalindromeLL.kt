/**
 * ============================================================================
 * PROBLEM:   Palindrome Linked List
 * DIFFICULTY: Medium
 * CATEGORY:  Linked List
 * LEETCODE:   #234
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the head of a singly linked list, return true if it is a palindrome,
 * or false otherwise.  
 * 
 * A palindrome reads the same forward and backward.
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - true if palindrome, false otherwise
 * 
 * CONSTRAINTS:
 * - Number of nodes:   [1, 10^5]
 * - 0 <= Node.val <= 9
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:    [1,2,2,1]
 * Output:  true
 * Explanation:  Reads same forward and backward
 * 
 * Example 2:
 * Input:   [1,2]
 * Output: false
 * Explanation: 1→2 forward, 2→1 backward (different)
 * 
 * Example 3:
 * Input:   [1,2,3,2,1]
 * Output: true
 * Explanation: Symmetric around center (3)
 * 
 * Example 4:
 * Input:   [1]
 * Output: true
 * Explanation: Single element is palindrome
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To check if a linked list is a palindrome, we need to compare first half
 * with second half. But linked list only goes forward!  
 * 
 * SOLUTION:  
 * 1. Find the middle of the list (slow/fast pointers)
 * 2. Reverse the second half
 * 3. Compare first half with reversed second half
 * 4.  Restore the list (optional, good practice)
 * 
 * KEY INSIGHT: 
 * If we reverse the second half, we can compare both halves by moving
 * forward from both ends toward the middle!  
 * 
 * VISUAL EXAMPLE (EVEN LENGTH):
 * ```
 * Input: 1 -> 2 -> 2 -> 1 -> null
 * 
 * Step 1: Find middle using slow/fast
 *   slow      fast
 *    ↓         ↓
 *    1 -> 2 -> 2 -> 1 -> null
 *         ↓              ↓
 *       slow           fast
 *   
 *   slow stops at 2nd half start (2)
 * 
 * Step 2: Reverse second half
 *   First half: 1 -> 2 -> null
 *   Second half: 2 -> 1 -> null
 *   After reverse: 1 <- 2
 *   
 *   Now we have: 
 *   1 -> 2    1 <- 2
 *   p1        p2
 * 
 * Step 3: Compare
 *   Compare p1(1) with p2(1) ✓
 *   Compare p1(2) with p2(2) ✓
 *   All match → palindrome!  
 * ```
 * 
 * VISUAL EXAMPLE (ODD LENGTH):
 * ```
 * Input: 1 -> 2 -> 3 -> 2 -> 1 -> null
 * 
 * Step 1: Find middle
 *   slow stops at 3 (middle element)
 * 
 * Step 2: Reverse second half (after middle)
 *   First: 1 -> 2 -> 3
 *   Second (reversed): 1 <- 2
 *   
 *   Middle element (3) doesn't need comparison
 * 
 * Step 3: Compare
 *   Compare 1 with 1 ✓
 *   Compare 2 with 2 ✓
 *   Palindrome! 
 * ```
 * 
 * WHY THIS WORKS:
 * - Reversing second half allows forward traversal from both ends
 * - We only need to compare up to middle
 * - Middle element (odd length) doesn't affect palindrome property
 * 
 * ALGORITHM STEPS:
 * 1. Find middle using slow/fast pointers
 * 2. Reverse second half starting from slow
 * 3. Compare first half with reversed second half
 * 4. (Optional) Restore list by reversing second half again
 * 5. Return true if all elements match
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:   O(n)
 * - Finding middle: O(n/2) ≈ O(n)
 * - Reversing second half: O(n/2) ≈ O(n)
 * - Comparing:  O(n/2) ≈ O(n)
 * - Total: O(n) + O(n) + O(n) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only a few pointers used
 * - Reverse is done in-place
 * - No additional data structures
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: USING ARRAY (Space:  O(n))
 * - Copy all values to array
 * - Use two pointers from both ends
 * - Time: O(n), Space: O(n)
 * - Simple but uses extra space
 * 
 * APPROACH 2: USING STACK (Space: O(n))
 * - Push first half onto stack
 * - Pop and compare with second half
 * - Time: O(n), Space: O(n)
 * 
 * APPROACH 3: RECURSION (Space: O(n))
 * - Use recursion to reach end
 * - Compare on return path
 * - Time: O(n), Space: O(n) for call stack
 * 
 * Reverse second half is optimal: 
 * ✅ O(n) time
 * ✅ O(1) space
 * ✅ Can restore original list
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single node [1] → true (trivially palindrome)
 * 2. Two same [1,1] → true
 * 3. Two different [1,2] → false
 * 4. All same values [5,5,5,5,5] → true
 * 5. Even length palindrome [1,2,2,1] → true
 * 6. Odd length palindrome [1,2,3,2,1] → true
 * 7. Almost palindrome [1,2,3,3,1] → false
 * 8. Empty list → true (by convention)
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not finding correct middle for odd/even length
 *    Result: Wrong comparison range
 *    Fix: Use slow/fast correctly
 * 
 * 2. ❌ Not handling odd length middle element
 *    Result: Including middle in comparison
 *    Fix: Middle doesn't matter for palindrome
 * 
 * 3. ❌ Comparing wrong number of elements
 *    Result:  False positive/negative
 *    Fix: Compare until one pointer is null
 * 
 * 4. ❌ Not restoring original list
 *    Result: Modifying input (may not be acceptable)
 *    Fix: Reverse second half again before returning
 * 
 * 5. ❌ Using == instead of value comparison
 *    Result:  Comparing references instead of values
 *    Fix: Compare node. val, not node itself
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **DNA Sequences**: Check for palindromic sequences
 * 2. **Data Validation**: Verify symmetric data structures
 * 3. **Cryptography**:  Palindrome-based checksums
 * 4. **Text Processing**: Find symmetric patterns
 * 5. **Network Packets**:  Validate packet symmetry
 * 6. **Music**:  Detect symmetric melodies
 * 7. **Games**:  Verify symmetric game states
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Ask about modification**: Can we modify the list? 
 * 2. **Space constraint**:  Clarify if O(n) space is acceptable
 * 3. **Draw it out**: Visualize the reversal and comparison
 * 4. **Explain three steps**: Find middle, reverse, compare
 * 5. **Handle odd/even**:  Show both cases
 * 6. **Restoration**: Mention you can restore original
 * 7. **Alternative approaches**:  Discuss stack/array methods
 * 8. **Edge cases**: Single node, two nodes
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Check palindrome without modifying list (use stack)
 * 2. Find longest palindromic sublist
 * 3. Make list palindrome with minimum deletions
 * 4. Count palindromic sublists
 * 5. Check k-palindrome (up to k mismatches)
 * 6.  Reverse list to make it palindrome
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 125: Valid Palindrome (string)
 * - LeetCode 9: Palindrome Number
 * - LeetCode 206: Reverse Linked List
 * - LeetCode 876: Middle of Linked List
 * - LeetCode 143: Reorder List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class PalindromeLL {
    
    /**
     * APPROACH 1: REVERSE SECOND HALF (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(1)
     */
    fun isPalindrome(head: ListNode?): Boolean {
        if (head?. next == null) return true
        
        // Step 1: Find middle
        val middle = findMiddle(head)
        
        // Step 2: Reverse second half
        var secondHalf = reverseList(middle)
        val secondHalfHead = secondHalf  // Save for restoration
        
        // Step 3: Compare both halves
        var firstHalf = head
        var isPalin = true
        
        while (secondHalf != null) {
            if (firstHalf?.`val` != secondHalf.`val`) {
                isPalin = false
                break
            }
            firstHalf = firstHalf. next
            secondHalf = secondHalf.next
        }
        
        // Step 4: Restore list (optional but good practice)
        reverseList(secondHalfHead)
        
        return isPalin
    }
    
    /**
     * Helper:  Find middle of linked list
     */
    private fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (fast?. next != null) {
            slow = slow?.next
            fast = fast. next?.next
        }
        
        return slow
    }
    
    /**
     * Helper: Reverse linked list
     */
    private fun reverseList(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        
        while (curr != null) {
            val next = curr.next
            curr. next = prev
            prev = curr
            curr = next
        }
        
        return prev
    }
    
    /**
     * APPROACH 2: USING ARRAY
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun isPalindromeArray(head: ListNode?): Boolean {
        // Convert to array
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr. `val`)
            curr = curr.next
        }
        
        // Two pointers
        var left = 0
        var right = values.size - 1
        
        while (left < right) {
            if (values[left] != values[right]) {
                return false
            }
            left++
            right--
        }
        
        return true
    }
    
    /**
     * APPROACH 3: USING STACK
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n)
     */
    fun isPalindromeStack(head: ListNode?): Boolean {
        if (head?. next == null) return true
        
        // Find middle and push first half to stack
        val stack = ArrayDeque<Int>()
        var slow = head
        var fast = head
        
        while (fast?. next != null) {
            stack.addLast(slow!! .`val`)
            slow = slow.next
            fast = fast.next?.next
        }
        
        // If odd length, skip middle element
        if (fast != null) {
            slow = slow?. next
        }
        
        // Compare second half with stack
        while (slow != null) {
            if (slow.`val` != stack.removeLast()) {
                return false
            }
            slow = slow.next
        }
        
        return true
    }
    
    /**
     * APPROACH 4: RECURSIVE
     * 
     * TIME COMPLEXITY: O(n)
     * SPACE COMPLEXITY: O(n) - recursion stack
     */
    fun isPalindromeRecursive(head: ListNode?): Boolean {
        frontPointer = head
        return recursiveCheck(head)
    }
    
    private var frontPointer: ListNode?  = null
    
    private fun recursiveCheck(currentNode: ListNode?): Boolean {
        if (currentNode != null) {
            if (! recursiveCheck(currentNode.next)) return false
            if (currentNode. `val` != frontPointer?.`val`) return false
            frontPointer = frontPointer?.next
        }
        return true
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
    
    // Helper function to print linked list
    fun printList(head: ListNode?) {
        var curr = head
        val values = mutableListOf<Int>()
        while (curr != null) {
            values.add(curr.`val`)
            curr = curr.next
        }
        println(values.joinToString(" -> "))
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = PalindromeLL()
    
    println("=". repeat(70))
    println("PALINDROME LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Even length palindrome [1,2,2,1]
    println("\nTest Case 1: [1,2,2,1] - even length palindrome")
    var head = solver.createList(intArrayOf(1, 2, 2, 1))
    print("List: ")
    solver.printList(head)
    val result1 = solver.isPalindrome(head)
    println("Is palindrome: $result1")
    println("Expected: true")
    
    // Test Case 2: Not palindrome [1,2]
    println("\nTest Case 2: [1,2] - not palindrome")
    head = solver.createList(intArrayOf(1, 2))
    print("List: ")
    solver.printList(head)
    val result2 = solver.isPalindrome(head)
    println("Is palindrome: $result2")
    println("Expected:  false")
    
    // Test Case 3: Odd length palindrome [1,2,3,2,1]
    println("\nTest Case 3: [1,2,3,2,1] - odd length palindrome")
    head = solver.createList(intArrayOf(1, 2, 3, 2, 1))
    print("List: ")
    solver.printList(head)
    val result3 = solver.isPalindrome(head)
    println("Is palindrome: $result3")
    println("Expected: true")
    
    // Test Case 4: Single node [1]
    println("\nTest Case 4: [1] - single node")
    head = solver.createList(intArrayOf(1))
    print("List: ")
    solver.printList(head)
    val result4 = solver.isPalindrome(head)
    println("Is palindrome: $result4")
    println("Expected: true")
    
    // Test Case 5: Two same [5,5]
    println("\nTest Case 5: [5,5] - two same values")
    head = solver.createList(intArrayOf(5, 5))
    print("List: ")
    solver.printList(head)
    val result5 = solver.isPalindrome(head)
    println("Is palindrome: $result5")
    println("Expected: true")
    
    // Test Case 6: All same [7,7,7,7]
    println("\nTest Case 6: [7,7,7,7] - all same values")
    head = solver.createList(intArrayOf(7, 7, 7, 7))
    print("List: ")
    solver.printList(head)
    val result6 = solver.isPalindrome(head)
    println("Is palindrome: $result6")
    println("Expected: true")
    
    // Test Case 7: Almost palindrome [1,2,3,3,1]
    println("\nTest Case 7: [1,2,3,3,1] - almost palindrome")
    head = solver.createList(intArrayOf(1, 2, 3, 3, 1))
    print("List: ")
    solver.printList(head)
    val result7 = solver.isPalindrome(head)
    println("Is palindrome:  $result7")
    println("Expected: false")
    
    // Test Case 8: Longer palindrome [1,2,3,4,3,2,1]
    println("\nTest Case 8: [1,2,3,4,3,2,1] - longer palindrome")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 3, 2, 1))
    print("List: ")
    solver.printList(head)
    val result8 = solver.isPalindrome(head)
    println("Is palindrome: $result8")
    println("Expected: true")
    
    // Test Case 9: Array approach
    println("\nTest Case 9: Array approach on [1,0,1]")
    head = solver.createList(intArrayOf(1, 0, 1))
    print("List: ")
    solver.printList(head)
    val result9 = solver.isPalindromeArray(head)
    println("Is palindrome (array): $result9")
    println("Expected: true")
    
    // Test Case 10: Stack approach
    println("\nTest Case 10: Stack approach on [9,1,9]")
    head = solver.createList(intArrayOf(9, 1, 9))
    print("List: ")
    solver.printList(head)
    val result10 = solver.isPalindromeStack(head)
    println("Is palindrome (stack): $result10")
    println("Expected: true")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
