/**
 * ============================================================================
 * PROBLEM:     Sort Linked List with 0s, 1s, and 2s
 * DIFFICULTY: Medium
 * CATEGORY:  Linked List
 * RELATED:   Dutch National Flag, GFG
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a linked list where nodes contain only 0s, 1s, and 2s, sort the list
 * in ascending order. 
 * 
 * You should do this in a single pass and in O(1) extra space.
 * 
 * INPUT FORMAT:
 * - Head of a linked list with node values in {0, 1, 2}
 * 
 * OUTPUT FORMAT:
 * - Head of the sorted linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:     [1, 10^4]
 * - Node. val ∈ {0, 1, 2}
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:     [1,2,0,1,2,0,1]
 * Output:  [0,0,1,1,1,2,2]
 * 
 * Example 2:
 * Input:     [2,1,0]
 * Output:  [0,1,2]
 * 
 * Example 3:
 * Input:   [1,1,1]
 * Output:  [1,1,1]
 * 
 * Example 4:
 * Input:     [2,2,0,0,1]
 * Output:  [0,0,1,2,2]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Since we only have three distinct values (0, 1, 2), we can use techniques
 * more efficient than general sorting! 
 * 
 * KEY INSIGHT - MULTIPLE APPROACHES:
 * 
 * APPROACH A: COUNTING (Two Pass)
 * - Count 0s, 1s, 2s
 * - Rebuild list with counts
 * - Simple but requires modifying values
 * 
 * APPROACH B: THREE POINTERS (One Pass)
 * - Maintain three separate lists:  zeros, ones, twos
 * - Traverse once, append to appropriate list
 * - Connect:  zeros → ones → twos
 * - Optimal!  Single pass, O(1) space
 * 
 * APPROACH C: DUTCH NATIONAL FLAG (Array-like)
 * - Use three pointers: low, mid, high
 * - Partition in-place
 * - Complex for linked lists
 * 
 * VISUAL EXAMPLE (Three Pointers):
 * ```
 * Input:  1 -> 2 -> 0 -> 1 -> 2 -> 0 -> null
 * 
 * Initialize three dummy nodes:
 *   zeros:  dummy0
 *   ones:   dummy1
 *   twos:  dummy2
 * 
 * Process each node:
 *   1 → append to ones
 *   2 → append to twos
 *   0 → append to zeros
 *   1 → append to ones
 *   2 → append to twos
 *   0 → append to zeros
 * 
 * Three lists: 
 *   zeros: 0 -> 0 -> null
 *   ones:  1 -> 1 -> null
 *   twos:  2 -> 2 -> null
 * 
 * Connect: 
 *   zeros. tail. next = ones.head
 *   ones.tail.next = twos.head
 *   twos.tail.next = null
 * 
 * Result: 0 -> 0 -> 1 -> 1 -> 2 -> 2 -> null ✓
 * ```
 * 
 * DETAILED WALKTHROUGH:
 * ```
 * Input: 2 -> 1 -> 0 -> null
 * 
 * dummy0 (zeros), zero0 = dummy0
 * dummy1 (ones),  one1  = dummy1
 * dummy2 (twos),  two2  = dummy2
 * 
 * Process 2:
 *   two2.next = node(2)
 *   two2 = two2.next
 *   twos:  2
 * 
 * Process 1:
 *   one1.next = node(1)
 *   one1 = one1.next
 *   ones: 1
 * 
 * Process 0:
 *   zero0.next = node(0)
 *   zero0 = zero0.next
 *   zeros: 0
 * 
 * Connect: 
 *   zero0.next = dummy1.next (1)
 *   one1.next = dummy2.next (2)
 *   two2.next = null
 * 
 * Result: 0 -> 1 -> 2 -> null ✓
 * ```
 * 
 * ALGORITHM STEPS (Three Pointers):
 * 1. Create three dummy nodes: dummy0, dummy1, dummy2
 * 2. Initialize tail pointers for each list
 * 3. Traverse original list: 
 *    - If value is 0, append to zeros list
 *    - If value is 1, append to ones list
 *    - If value is 2, append to twos list
 * 4. Connect the three lists: 
 *    - zeros tail → ones head
 *    - ones tail → twos head
 *    - twos tail → null
 * 5. Return zeros head (or ones/twos if zeros empty)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH A (COUNTING):
 * TIME: O(n) - two passes
 * SPACE: O(1) - three counters
 * Note:  Modifies node values
 * 
 * APPROACH B (THREE POINTERS):
 * TIME: O(n) - single pass
 * SPACE: O(1) - three dummy nodes
 * Optimal!  No value modification needed
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: GENERAL SORTING
 * - Use merge sort or other O(n log n) algorithm
 * - Overkill for just three values! 
 * - Time: O(n log n), Space: O(log n)
 * 
 * APPROACH 2: CONVERT TO ARRAY
 * - Copy to array, use Dutch National Flag
 * - Rebuild list
 * - Time: O(n), Space: O(n)
 * 
 * Three pointers is optimal for linked lists!
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. All zeros [0,0,0] → [0,0,0]
 * 2. All ones [1,1,1] → [1,1,1]
 * 3. All twos [2,2,2] → [2,2,2]
 * 4. No zeros [1,2,1,2] → [1,1,2,2]
 * 5. No ones [0,2,0,2] → [0,0,2,2]
 * 6. No twos [0,1,0,1] → [0,0,1,1]
 * 7. Single node [1] → [1]
 * 8. Already sorted [0,0,1,1,2,2] → no change
 * 9. Reverse sorted [2,2,1,1,0,0] → [0,0,1,1,2,2]
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not handling empty sublists
 *    Result: NullPointerException when connecting
 *    Fix: Check if each list is empty before connecting
 * 
 * 2. ❌ Not setting tail. next = null
 *    Result:  Cycles or wrong connections
 *    Fix: Always null-terminate the last list
 * 
 * 3. ❌ Losing track of tail pointers
 *    Result:  Can't connect lists properly
 *    Fix: Maintain tail pointer for each list
 * 
 * 4. ❌ Modifying original node values unnecessarily
 *    Result: Not truly sorting, just changing values
 *    Fix: Use three-pointer approach, maintain structure
 * 
 * 5. ❌ Wrong connection order
 *    Result: Lists connected incorrectly
 *    Fix:  zeros → ones → twos
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Traffic Lights**: Organizing by color (red, yellow, green)
 * 2. **Priority Queues**: Three priority levels (high, medium, low)
 * 3. **Data Classification**: Three categories (positive, neutral, negative)
 * 4. **Resource Allocation**: Three resource types
 * 5. **Quality Control**: Three quality grades (A, B, C)
 * 6. **Game Development**: Three team colors
 * 7. **Network Packets**: Three priority levels
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Ask about values**:  Confirm only 0, 1, 2
 * 2. **Discuss approaches**: Count vs three pointers
 * 3. **Explain efficiency**: O(n) single pass
 * 4. **Draw the three lists**: Visual representation
 * 5. **Handle empty lists**: Show edge case handling
 * 6. **Connection order**: Emphasize correct linking
 * 7. **Space constraint**: Highlight O(1) space
 * 8. **Compare to Dutch flag**: Mention array algorithm
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Sort with k distinct values (generalize)
 * 2. Sort with negative values included
 * 3. Count occurrences of each value
 * 4. Check if already sorted
 * 5. Sort in descending order
 * 6. Partition around middle value (1)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 75: Sort Colors (array version - Dutch National Flag)
 * - LeetCode 148: Sort List (general sorting)
 * - LeetCode 86: Partition List
 * - LeetCode 328: Odd Even Linked List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class Sort012LL {
    
    /**
     * APPROACH 1: THREE POINTERS (OPTIMAL)
     * 
     * TIME COMPLEXITY: O(n) - single pass
     * SPACE COMPLEXITY: O(1) - three dummy nodes
     */
    fun sort012(head: ListNode?): ListNode? {
        if (head?. next == null) return head
        
        // Create three dummy nodes
        val dummy0 = ListNode(0)
        val dummy1 = ListNode(0)
        val dummy2 = ListNode(0)
        
        // Tail pointers for each list
        var zero = dummy0
        var one = dummy1
        var two = dummy2
        
        // Traverse and segregate
        var current = head
        while (current != null) {
            when (current.`val`) {
                0 -> {
                    zero. next = current
                    zero = zero.next!! 
                }
                1 -> {
                    one.next = current
                    one = one. next!!
                }
                2 -> {
                    two. next = current
                    two = two.next!!
                }
            }
            current = current.next
        }
        
        // Connect the three lists
        // zeros → ones → twos
        zero. next = dummy1.next ?: dummy2.next
        one.next = dummy2.next
        two.next = null  // Important: terminate list
        
        // Return head (could be 0s, 1s, or 2s)
        return dummy0.next ?: dummy1.next ?: dummy2.next
    }
    
    /**
     * APPROACH 2: COUNTING (Two Pass)
     * 
     * TIME COMPLEXITY: O(n) - two passes
     * SPACE COMPLEXITY: O(1)
     */
    fun sort012Counting(head: ListNode?): ListNode? {
        if (head == null) return null
        
        // Count occurrences
        var count0 = 0
        var count1 = 0
        var count2 = 0
        
        var current = head
        while (current != null) {
            when (current.`val`) {
                0 -> count0++
                1 -> count1++
                2 -> count2++
            }
            current = current.next
        }
        
        // Rebuild list with counts
        current = head
        
        // Fill with 0s
        while (count0 > 0) {
            current!! .`val` = 0
            current = current.next
            count0--
        }
        
        // Fill with 1s
        while (count1 > 0) {
            current!!.`val` = 1
            current = current.next
            count1--
        }
        
        // Fill with 2s
        while (count2 > 0) {
            current!!.`val` = 2
            current = current.next
            count2--
        }
        
        return head
    }
    
    /**
     * VARIATION: Sort in descending order (2, 1, 0)
     */
    fun sort012Descending(head: ListNode?): ListNode? {
        if (head?. next == null) return head
        
        val dummy0 = ListNode(0)
        val dummy1 = ListNode(0)
        val dummy2 = ListNode(0)
        
        var zero = dummy0
        var one = dummy1
        var two = dummy2
        
        var current = head
        while (current != null) {
            when (current.`val`) {
                0 -> {
                    zero.next = current
                    zero = zero.next!!
                }
                1 -> {
                    one.next = current
                    one = one.next!!
                }
                2 -> {
                    two.next = current
                    two = two.next!!
                }
            }
            current = current.next
        }
        
        // Connect in reverse order:  twos → ones → zeros
        two.next = dummy1.next ?: dummy0.next
        one.next = dummy0.next
        zero. next = null
        
        return dummy2.next ?: dummy1.next ?: dummy0.next
    }
    
    /**
     * BONUS: Get counts of each value
     */
    fun getCounts(head: ListNode?): Triple<Int, Int, Int> {
        var count0 = 0
        var count1 = 0
        var count2 = 0
        
        var current = head
        while (current != null) {
            when (current.`val`) {
                0 -> count0++
                1 -> count1++
                2 -> count2++
            }
            current = current.next
        }
        
        return Triple(count0, count1, count2)
    }
    
    /**
     * BONUS: Check if already sorted
     */
    fun isAlreadySorted(head: ListNode?): Boolean {
        var current = head
        while (current?. next != null) {
            if (current.`val` > current.next!! .`val`) {
                return false
            }
            current = current.next
        }
        return true
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
    val solver = Sort012LL()
    
    println("=". repeat(70))
    println("SORT LINKED LIST WITH 0s, 1s, 2s - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Mixed [1,2,0,1,2,0,1]
    println("\nTest Case 1: [1,2,0,1,2,0,1]")
    var head = solver.createList(intArrayOf(1, 2, 0, 1, 2, 0, 1))
    print("Original: ")
    solver.printList(head)
    val (c0, c1, c2) = solver.getCounts(head)
    println("Counts: 0s=$c0, 1s=$c1, 2s=$c2")
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 1 -> 1 -> 1 -> 2 -> 2")
    println("Is sorted: ${solver.isAlreadySorted(head)}")
    
    // Test Case 2: Simple [2,1,0]
    println("\nTest Case 2: [2,1,0]")
    head = solver.createList(intArrayOf(2, 1, 0))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 0 -> 1 -> 2")
    
    // Test Case 3: All same [1,1,1]
    println("\nTest Case 3: [1,1,1] - all same")
    head = solver.createList(intArrayOf(1, 1, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 1 -> 1")
    
    // Test Case 4: All zeros [0,0,0]
    println("\nTest Case 4: [0,0,0] - all zeros")
    head = solver.createList(intArrayOf(0, 0, 0))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 0")
    
    // Test Case 5: No zeros [1,2,1,2,1]
    println("\nTest Case 5: [1,2,1,2,1] - no zeros")
    head = solver.createList(intArrayOf(1, 2, 1, 2, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 1 -> 1 -> 2 -> 2")
    
    // Test Case 6: No ones [0,2,0,2]
    println("\nTest Case 6: [0,2,0,2] - no ones")
    head = solver.createList(intArrayOf(0, 2, 0, 2))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 2 -> 2")
    
    // Test Case 7: Already sorted
    println("\nTest Case 7: [0,0,1,1,2,2] - already sorted")
    head = solver.createList(intArrayOf(0, 0, 1, 1, 2, 2))
    print("Original: ")
    solver.printList(head)
    println("Already sorted: ${solver.isAlreadySorted(head)}")
    head = solver.sort012(head)
    print("After sort: ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 1 -> 1 -> 2 -> 2")
    
    // Test Case 8: Reverse sorted
    println("\nTest Case 8: [2,2,1,1,0,0] - reverse sorted")
    head = solver.createList(intArrayOf(2, 2, 1, 1, 0, 0))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 1 -> 1 -> 2 -> 2")
    
    // Test Case 9: Counting approach
    println("\nTest Case 9: Counting approach [2,0,1,2,0,1]")
    head = solver.createList(intArrayOf(2, 0, 1, 2, 0, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012Counting(head)
    print("Sorted (counting): ")
    solver.printList(head)
    println("Expected: 0 -> 0 -> 1 -> 1 -> 2 -> 2")
    
    // Test Case 10: Descending order
    println("\nTest Case 10: Descending [1,2,0,1,2,0]")
    head = solver.createList(intArrayOf(1, 2, 0, 1, 2, 0))
    print("Original: ")
    solver.printList(head)
    head = solver.sort012Descending(head)
    print("Sorted (descending): ")
    solver.printList(head)
    println("Expected: 2 -> 2 -> 1 -> 1 -> 0 -> 0")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
