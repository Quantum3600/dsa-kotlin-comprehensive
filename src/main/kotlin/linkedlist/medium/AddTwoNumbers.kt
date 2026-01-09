/**
 * ============================================================================
 * PROBLEM:   Add Two Numbers
 * DIFFICULTY: Medium
 * CATEGORY: Linked List
 * LEETCODE:   #2
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * You are given two non-empty linked lists representing two non-negative
 * integers. The digits are stored in reverse order, and each node contains
 * a single digit.  Add the two numbers and return the sum as a linked list. 
 * 
 * You may assume the two numbers do not contain any leading zero, except
 * the number 0 itself. 
 * 
 * INPUT FORMAT:
 * - l1: Head of first linked list (reverse order digits)
 * - l2: Head of second linked list (reverse order digits)
 * 
 * OUTPUT FORMAT:
 * - Head of result linked list (reverse order digits)
 * 
 * CONSTRAINTS:
 * - Number of nodes in each list:  [1, 100]
 * - 0 <= Node.val <= 9
 * - The numbers do not contain leading zeros
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:    l1 = [2,4,3], l2 = [5,6,4]
 * Output:  [7,0,8]
 * Explanation: 342 + 465 = 807
 * 
 * Example 2:
 * Input:    l1 = [0], l2 = [0]
 * Output: [0]
 * 
 * Example 3:
 * Input:   l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * Explanation: 9999999 + 9999 = 10009998
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is exactly like adding two numbers by hand! 
 * 
 * When adding digits: 
 * 1. Start from rightmost digit (which is head in reversed list)
 * 2. Add corresponding digits plus any carry
 * 3. If sum >= 10, carry 1 to next position
 * 4. Continue until both lists exhausted and no carry
 * 
 * KEY INSIGHT:
 * Since digits are already in reverse order (least significant first),
 * we can traverse both lists simultaneously from head to tail! 
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 
 *   l1: 2 -> 4 -> 3  (represents 342)
 *   l2: 5 -> 6 -> 4  (represents 465)
 * 
 * Step by step addition:
 * 
 * Position 0 (ones):
 *   digit1 = 2, digit2 = 5, carry = 0
 *   sum = 2 + 5 + 0 = 7
 *   result: 7, carry = 0
 * 
 * Position 1 (tens):
 *   digit1 = 4, digit2 = 6, carry = 0
 *   sum = 4 + 6 + 0 = 10
 *   result: 0, carry = 1
 * 
 * Position 2 (hundreds):
 *   digit1 = 3, digit2 = 4, carry = 1
 *   sum = 3 + 4 + 1 = 8
 *   result: 8, carry = 0
 * 
 * Final:  7 -> 0 -> 8 (represents 807) ✓
 * ```
 * 
 * VISUAL EXAMPLE WITH DIFFERENT LENGTHS:
 * ```
 * Input:
 *   l1: 9 -> 9 -> 9  (represents 999)
 *   l2: 1            (represents 1)
 * 
 * Position 0:
 *   9 + 1 + 0 = 10 → digit:  0, carry:  1
 * 
 * Position 1:
 *   9 + 0 + 1 = 10 → digit: 0, carry: 1
 * 
 * Position 2:
 *   9 + 0 + 1 = 10 → digit: 0, carry: 1
 * 
 * Position 3:
 *   0 + 0 + 1 = 1 → digit: 1, carry: 0
 * 
 * Result: 0 -> 0 -> 0 -> 1 (represents 1000) ✓
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Create dummy head for result list
 * 2. Initialize carry = 0, current = dummy
 * 3. While l1 or l2 or carry exists:
 *    a. Get digit from l1 (or 0 if null)
 *    b. Get digit from l2 (or 0 if null)
 *    c. Calculate sum = digit1 + digit2 + carry
 *    d. Create new node with sum % 10
 *    e. Update carry = sum / 10
 *    f. Move l1, l2, current forward
 * 4. Return dummy.next
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(max(m, n))
 * - m = length of l1, n = length of l2
 * - We traverse both lists once
 * - May need one extra iteration for final carry
 * - Total: O(max(m, n))
 * 
 * SPACE COMPLEXITY: O(max(m, n))
 * - Result list has max(m, n) or max(m, n) + 1 nodes
 * - Not counting output space:  O(1) extra space
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: CONVERT TO INTEGERS (Not scalable)
 * - Convert lists to integers
 * - Add integers
 * - Convert back to list
 * - Problem: Overflow for large numbers! 
 * - Time: O(m + n), Space: O(1)
 * - Not recommended
 * 
 * APPROACH 2: RECURSION
 * - Recursively add digits with carry
 * - Time: O(max(m, n))
 * - Space: O(max(m, n)) for recursion stack
 * - Elegant but uses extra space
 * 
 * Iterative with carry is optimal: 
 * ✅ O(max(m, n)) time
 * ✅ O(1) extra space (output doesn't count)
 * ✅ Handles any length
 * ✅ No overflow issues
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Different lengths: [1,2,3] + [4,5] = [5,7,3]
 * 2. Final carry: [9,9,9] + [1] = [0,0,0,1]
 * 3. Multiple carries: [9,9,9] + [9,9,9] = [8,9,9,1]
 * 4. One list empty: [5] + [0] = [5]
 * 5. Both single digit: [5] + [5] = [0,1]
 * 6. No carry: [1,2,3] + [4,5,6] = [5,7,9]
 * 7. All zeros: [0] + [0] = [0]
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Forgetting final carry
 *    Result: Missing most significant digit
 *    Fix: Check carry after loop, add if non-zero
 * 
 * 2. ❌ Not handling different lengths
 *    Result: NullPointerException
 *    Fix: Use 0 when list is null
 * 
 * 3. ❌ Wrong carry calculation
 *    Result:  Incorrect sum
 *    Fix:  carry = sum / 10, digit = sum % 10
 * 
 * 4. ❌ Trying to convert to integer
 *    Result: Overflow for large numbers
 *    Fix:  Process digit by digit
 * 
 * 5. ❌ Not using dummy head
 *    Result: Complex head handling
 *    Fix: Always use dummy node
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Big Integer Arithmetic**: Adding numbers larger than native types
 * 2. **Financial Systems**: Precise decimal arithmetic
 * 3. **Cryptography**: Operations on very large numbers
 * 4. **Scientific Computing**: Arbitrary precision calculations
 * 5. **Calculator Apps**: Implementing addition for large numbers
 * 6. **Blockchain**: Computing large hash values
 * 7. **Gaming**: Damage/score calculations exceeding int limits
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify input format**: Confirm reverse order
 * 2. **Ask about overflow**: Can we use int? What if huge numbers?
 * 3. **Draw the addition**: Show manual addition process
 * 4. **Explain carry**:  Emphasize carry propagation
 * 5. **Handle lengths**: Show different length example
 * 6. **Edge cases**: Final carry, all 9s
 * 7. **Dummy node**:  Explain why it simplifies code
 * 8. **Walk through**:  Step-by-step with carries
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Add two numbers in normal order (most significant first)
 * 2. Subtract two numbers represented as lists
 * 3. Multiply two numbers represented as lists
 * 4. Add three numbers
 * 5. Add numbers in different bases (binary, hexadecimal)
 * 6. Handle negative numbers
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 445: Add Two Numbers II (normal order)
 * - LeetCode 67: Add Binary (string addition)
 * - LeetCode 415: Add Strings
 * - LeetCode 43: Multiply Strings
 * - LeetCode 369: Plus One Linked List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class AddTwoNumbers {
    
    /**
     * APPROACH 1: ITERATIVE WITH CARRY (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(max(m, n))
     * SPACE COMPLEXITY: O(max(m, n)) for result, O(1) extra
     */
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var carry = 0
        
        var p1 = l1
        var p2 = l2
        
        // Process both lists and carry
        while (p1 != null || p2 != null || carry != 0) {
            // Get digits (0 if list exhausted)
            val digit1 = p1?. `val` ?: 0
            val digit2 = p2?.`val` ?: 0
            
            // Calculate sum with carry
            val sum = digit1 + digit2 + carry
            val digit = sum % 10
            carry = sum / 10
            
            // Add to result
            current.next = ListNode(digit)
            current = current.next!!
            
            // Move to next nodes
            p1 = p1?.next
            p2 = p2?.next
        }
        
        return dummy.next
    }
    
    /**
     * APPROACH 2: RECURSIVE
     * 
     * TIME COMPLEXITY: O(max(m, n))
     * SPACE COMPLEXITY: O(max(m, n)) - recursion stack
     */
    fun addTwoNumbersRecursive(l1: ListNode?, l2: ListNode?, carry: Int = 0): ListNode? {
        // Base case: both null and no carry
        if (l1 == null && l2 == null && carry == 0) return null
        
        // Get digits
        val digit1 = l1?.`val` ?: 0
        val digit2 = l2?.`val` ?: 0
        
        // Calculate sum
        val sum = digit1 + digit2 + carry
        val digit = sum % 10
        val newCarry = sum / 10
        
        // Create node and recurse
        val node = ListNode(digit)
        node.next = addTwoNumbersRecursive(l1?. next, l2?.next, newCarry)
        
        return node
    }
    
    /**
     * VARIATION: Add two numbers in normal order (LeetCode 445)
     * Numbers are stored most significant digit first
     */
    fun addTwoNumbersII(l1: ListNode?, l2: ListNode?): ListNode? {
        // Reverse both lists
        val rev1 = reverseList(l1)
        val rev2 = reverseList(l2)
        
        // Add them
        val result = addTwoNumbers(rev1, rev2)
        
        // Reverse result back
        return reverseList(result)
    }
    
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
     * VARIATION:  Subtract two numbers (l1 - l2)
     * Assumes l1 >= l2
     */
    fun subtractTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var borrow = 0
        
        var p1 = l1
        var p2 = l2
        
        while (p1 != null) {
            val digit1 = p1.`val`
            val digit2 = (p2?. `val` ?: 0)
            
            var diff = digit1 - digit2 - borrow
            
            if (diff < 0) {
                diff += 10
                borrow = 1
            } else {
                borrow = 0
            }
            
            current.next = ListNode(diff)
            current = current. next!!
            
            p1 = p1.next
            p2 = p2?. next
        }
        
        return dummy.next
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
        return result. toIntArray()
    }
    
    // Helper function to print as number
    fun printAsNumber(head: ListNode?) {
        val arr = toArray(head).reversedArray()
        if (arr.isEmpty()) {
            println("0")
        } else {
            println(arr.joinToString(""))
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
    val solver = AddTwoNumbers()
    
    println("=". repeat(70))
    println("ADD TWO NUMBERS - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Example [2,4,3] + [5,6,4]
    println("\nTest Case 1: [2,4,3] + [5,6,4]")
    var l1 = solver.createList(intArrayOf(2, 4, 3))
    var l2 = solver.createList(intArrayOf(5, 6, 4))
    print("l1: ")
    solver.printList(l1)
    print("As number: ")
    solver.printAsNumber(l1)
    print("l2: ")
    solver.printList(l2)
    print("As number: ")
    solver.printAsNumber(l2)
    var result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number: ")
    solver.printAsNumber(result)
    println("Expected: [7,0,8] representing 807")
    
    // Test Case 2: [0] + [0]
    println("\nTest Case 2: [0] + [0]")
    l1 = solver.createList(intArrayOf(0))
    l2 = solver.createList(intArrayOf(0))
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    println("Expected: [0]")
    
    // Test Case 3: [9,9,9,9,9,9,9] + [9,9,9,9]
    println("\nTest Case 3: [9,9,9,9,9,9,9] + [9,9,9,9]")
    l1 = solver.createList(intArrayOf(9, 9, 9, 9, 9, 9, 9))
    l2 = solver. createList(intArrayOf(9, 9, 9, 9))
    print("l1 represents: ")
    solver.printAsNumber(l1)
    print("l2 represents: ")
    solver.printAsNumber(l2)
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number: ")
    solver.printAsNumber(result)
    println("Expected: [8,9,9,9,0,0,0,1] representing 10009998")
    
    // Test Case 4: Different lengths [1,2,3] + [4,5]
    println("\nTest Case 4: [1,2,3] + [4,5]")
    l1 = solver.createList(intArrayOf(1, 2, 3))
    l2 = solver.createList(intArrayOf(4, 5))
    print("l1 (321): ")
    solver.printList(l1)
    print("l2 (54): ")
    solver.printList(l2)
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number (375): ")
    solver.printAsNumber(result)
    println("Expected: [5,7,3]")
    
    // Test Case 5: Single digits with carry [5] + [5]
    println("\nTest Case 5: [5] + [5]")
    l1 = solver.createList(intArrayOf(5))
    l2 = solver.createList(intArrayOf(5))
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    println("Expected: [0,1] representing 10")
    
    // Test Case 6: Multiple carries [9,9,9] + [1]
    println("\nTest Case 6: [9,9,9] + [1]")
    l1 = solver.createList(intArrayOf(9, 9, 9))
    l2 = solver.createList(intArrayOf(1))
    print("l1 (999): ")
    solver.printList(l1)
    print("l2 (1): ")
    solver.printList(l2)
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number (1000): ")
    solver.printAsNumber(result)
    println("Expected: [0,0,0,1]")
    
    // Test Case 7: No carry [1,2,3] + [4,5,6]
    println("\nTest Case 7: [1,2,3] + [4,5,6]")
    l1 = solver.createList(intArrayOf(1, 2, 3))
    l2 = solver.createList(intArrayOf(4, 5, 6))
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number:  ")
    solver.printAsNumber(result)
    println("Expected: [5,7,9] representing 975")
    
    // Test Case 8: Recursive approach
    println("\nTest Case 8: Recursive approach [2,4,3] + [5,6,4]")
    l1 = solver.createList(intArrayOf(2, 4, 3))
    l2 = solver.createList(intArrayOf(5, 6, 4))
    result = solver.addTwoNumbersRecursive(l1, l2)
    print("Result (recursive): ")
    solver.printList(result)
    println("Expected: [7,0,8]")
    
    // Test Case 9: Very long numbers
    println("\nTest Case 9: Long numbers")
    l1 = solver. createList(intArrayOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1))
    l2 = solver.createList(intArrayOf(5, 6, 4))
    print("l1: ")
    solver.printAsNumber(l1)
    print("l2: ")
    solver.printAsNumber(l2)
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printAsNumber(result)
    
    // Test Case 10: Edge case with final carry
    println("\nTest Case 10: [9] + [9,9,9]")
    l1 = solver.createList(intArrayOf(9))
    l2 = solver.createList(intArrayOf(9, 9, 9))
    print("l1 (9): ")
    solver.printList(l1)
    print("l2 (999): ")
    solver.printList(l2)
    result = solver.addTwoNumbers(l1, l2)
    print("Result: ")
    solver.printList(result)
    print("As number (1008): ")
    solver.printAsNumber(result)
    println("Expected: [8,0,0,1]")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
