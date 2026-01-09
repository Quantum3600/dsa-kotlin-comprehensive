/**
 * ============================================================================
 * PROBLEM:     Sort Linked List
 * DIFFICULTY: Medium
 * CATEGORY:  Linked List
 * LEETCODE:    #148
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Given the head of a linked list, return the list after sorting it in
 * ascending order.
 * 
 * You must solve the problem in O(n log n) time complexity and O(1) space
 * complexity (constant space, not counting recursion stack).
 * 
 * INPUT FORMAT:
 * - Head of a singly linked list
 * 
 * OUTPUT FORMAT:
 * - Head of the sorted linked list
 * 
 * CONSTRAINTS:
 * - Number of nodes:     [0, 5 * 10^4]
 * - -10^5 <= Node.val <= 10^5
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:     [4,2,1,3]
 * Output:  [1,2,3,4]
 * 
 * Example 2:
 * Input:    [-1,5,3,4,0]
 * Output:  [-1,0,3,4,5]
 * 
 * Example 3:
 * Input:    []
 * Output:  []
 * 
 * Example 4:
 * Input:     [1]
 * Output:  [1]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For O(n log n) time, we need efficient sorting algorithms: 
 * - Merge Sort - Perfect for linked lists!  
 * - Quick Sort - Can work but less efficient for lists
 * - Heap Sort - Requires extra space
 * 
 * WHY MERGE SORT IS IDEAL:
 * 1. O(n log n) time complexity ✓
 * 2. Works naturally with linked lists (no random access needed)
 * 3. Can be done in-place (O(1) space if iterative)
 * 4. Stable sort (maintains relative order)
 * 
 * KEY INSIGHT - MERGE SORT ON LINKED LIST:
 * 1. Find middle (using slow/fast pointers)
 * 2. Split into two halves
 * 3. Recursively sort each half
 * 4. Merge two sorted halves
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 4 -> 2 -> 1 -> 3 -> null
 * 
 * Step 1: Split
 *   4 -> 2 -> null
 *   1 -> 3 -> null
 * 
 * Step 2: Split again
 *   4 -> null
 *   2 -> null
 *   1 -> null
 *   3 -> null
 * 
 * Step 3: Merge pairs
 *   2 -> 4 -> null
 *   1 -> 3 -> null
 * 
 * Step 4: Merge halves
 *   1 -> 2 -> 3 -> 4 -> null ✓
 * ```
 * 
 * DETAILED MERGE PROCESS:
 * ```
 * Merge [2,4] with [1,3]
 * 
 * Compare 2 vs 1 → take 1
 * Result: 1 -> ... 
 * 
 * Compare 2 vs 3 → take 2
 * Result: 1 -> 2 -> ...
 * 
 * Compare 4 vs 3 → take 3
 * Result: 1 -> 2 -> 3 -> ...
 * 
 * Only 4 left → append
 * Result: 1 -> 2 -> 3 -> 4 ✓
 * ```
 * 
 * ALGORITHM STEPS:
 * 1. Base case: If list has 0 or 1 node, already sorted
 * 2. Find middle using slow/fast pointers
 * 3. Split list into two halves at middle
 * 4. Recursively sort left half
 * 5. Recursively sort right half
 * 6. Merge two sorted halves
 * 7. Return merged list
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * MERGE SORT (RECURSIVE):
 * TIME COMPLEXITY:    O(n log n)
 * - Splitting: O(log n) levels
 * - Merging:  O(n) work per level
 * - Total:  O(n log n)
 * 
 * SPACE COMPLEXITY: O(log n)
 * - Recursion stack depth:  O(log n)
 * - Not O(1) but best we can do with recursion
 * 
 * MERGE SORT (ITERATIVE - Bottom-up):
 * TIME: O(n log n)
 * SPACE: O(1) - true constant space! 
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: QUICK SORT
 * - Choose pivot, partition around it
 * - Time: O(n log n) average, O(n²) worst
 * - Space: O(log n) recursion
 * - Less efficient for linked lists
 * 
 * APPROACH 2: INSERTION SORT
 * - Insert each node into sorted position
 * - Time: O(n²) - too slow! 
 * - Space: O(1)
 * - Only good for nearly sorted lists
 * 
 * APPROACH 3: CONVERT TO ARRAY
 * - Copy to array, sort array, rebuild list
 * - Time: O(n log n)
 * - Space: O(n) - violates constraint
 * 
 * APPROACH 4: HEAP SORT
 * - Build min-heap, extract elements
 * - Time:  O(n log n)
 * - Space: O(n) - heap storage
 * 
 * Merge Sort is optimal for linked lists! 
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty list → return null
 * 2. Single node → already sorted
 * 3. Two nodes → simple comparison
 * 4. Already sorted → still O(n log n) but optimal
 * 5. Reverse sorted → worst case but still O(n log n)
 * 6. All same values → stable sort maintains order
 * 7. Alternating order → benefits from merge sort
 * 8. Large list (50000 nodes) → should handle efficiently
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Not breaking the link at middle
 *    Result:  Infinite recursion or wrong result
 *    Fix: Set prev.next = null to split
 * 
 * 2. ❌ Wrong middle finding
 *    Result: Unbalanced splits
 *    Fix: Use slow/fast pointer correctly
 * 
 * 3. ❌ Incorrect merge logic
 *    Result: Wrong order or lost nodes
 *    Fix: Carefully compare and link nodes
 * 
 * 4. ❌ Not handling null cases
 *    Result: NullPointerException
 *    Fix: Check for null at each step
 * 
 * 5. ❌ Forgetting dummy node in merge
 *    Result: Complex head handling
 *    Fix: Use dummy node to simplify
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Database Systems**: Sorting query results
 * 2. **File Systems**: Sorting directory entries
 * 3. **Music Playlists**: Sorting songs by name/artist
 * 4. **Contact Lists**: Alphabetical sorting
 * 5. **E-commerce**: Sorting products by price/rating
 * 6. **Social Media**: Sorting posts by timestamp
 * 7. **Network Routing**: Sorting routes by priority
 * 8. **Task Managers**: Sorting tasks by priority/deadline
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Explain O(n log n)**: Why merge sort is chosen
 * 2. **Draw the recursion tree**: Show splitting and merging
 * 3. **Walk through merge**:  Detail the merge process
 * 4. **Find middle**: Explain slow/fast technique
 * 5. **Space complexity**: Discuss recursion stack
 * 6. **Stable sort**: Mention if order preservation matters
 * 7. **Compare alternatives**: Quick sort, insertion sort
 * 8. **Edge cases**: Empty, single node, already sorted
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Sort in descending order
 * 2. Sort by absolute values
 * 3. Sort with custom comparator
 * 4. Find kth largest element (QuickSelect)
 * 5.  Merge k sorted linked lists
 * 6. Sort list with 0s, 1s, 2s only (counting sort)
 * 7. Implement iterative merge sort (O(1) space)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 21: Merge Two Sorted Lists (used in merge step)
 * - LeetCode 23:  Merge k Sorted Lists
 * - LeetCode 147: Insertion Sort List
 * - LeetCode 75: Sort Colors (0,1,2)
 * - LeetCode 876: Middle of Linked List (find middle)
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class SortLL {
    
    /**
     * APPROACH 1: MERGE SORT (RECURSIVE) - OPTIMAL
     * 
     * TIME COMPLEXITY: O(n log n)
     * SPACE COMPLEXITY:  O(log n) - recursion stack
     */
    fun sortList(head:  ListNode?): ListNode? {
        // Base case: empty or single node
        if (head?. next == null) return head
        
        // Find middle and split
        val middle = findMiddle(head)
        val rightHead = middle?. next
        middle?.next = null  // Break the link
        
        // Recursively sort both halves
        val left = sortList(head)
        val right = sortList(rightHead)
        
        // Merge sorted halves
        return merge(left, right)
    }
    
    /**
     * Find middle of linked list (for splitting)
     * Returns node before middle for even length
     */
    private fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head?. next
        
        while (fast?. next != null) {
            slow = slow?.next
            fast = fast. next?.next
        }
        
        return slow
    }
    
    /**
     * Merge two sorted linked lists
     */
    private fun merge(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2
        
        // Compare and merge
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current.next!! 
        }
        
        // Append remaining nodes
        current.next = p1 ?: p2
        
        return dummy.next
    }
    
    /**
     * APPROACH 2: INSERTION SORT (Simple but O(n²))
     * 
     * TIME COMPLEXITY: O(n²)
     * SPACE COMPLEXITY: O(1)
     */
    fun sortListInsertion(head: ListNode? ): ListNode? {
        if (head?. next == null) return head
        
        val dummy = ListNode(Int.MIN_VALUE)
        var current = head
        
        while (current != null) {
            val next = current.next
            
            // Find insertion position
            var prev = dummy
            while (prev.next != null && prev.next!! .`val` < current.`val`) {
                prev = prev.next!! 
            }
            
            // Insert current
            current.next = prev.next
            prev.next = current
            
            current = next
        }
        
        return dummy. next
    }
    
    /**
     * APPROACH 3: USING ARRAY (Violates space constraint)
     * 
     * TIME COMPLEXITY: O(n log n)
     * SPACE COMPLEXITY: O(n)
     */
    fun sortListArray(head: ListNode?): ListNode? {
        if (head == null) return null
        
        // Convert to array
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr.`val`)
            curr = curr.next
        }
        
        // Sort array
        values.sort()
        
        // Rebuild list
        val dummy = ListNode(0)
        curr = dummy
        for (value in values) {
            curr.next = ListNode(value)
            curr = curr.next!!
        }
        
        return dummy. next
    }
    
    /**
     * VARIATION: Sort in descending order
     */
    fun sortListDescending(head:  ListNode?): ListNode? {
        if (head?. next == null) return head
        
        val middle = findMiddle(head)
        val rightHead = middle?.next
        middle?.next = null
        
        val left = sortListDescending(head)
        val right = sortListDescending(rightHead)
        
        return mergeDescending(left, right)
    }
    
    private fun mergeDescending(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2
        
        while (p1 != null && p2 != null) {
            if (p1.`val` >= p2.`val`) {  // Changed comparison
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current.next!!
        }
        
        current.next = p1 ?: p2
        return dummy.next
    }
    
    /**
     * VARIATION: Sort by absolute values
     */
    fun sortListByAbsolute(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val middle = findMiddle(head)
        val rightHead = middle?.next
        middle?.next = null
        
        val left = sortListByAbsolute(head)
        val right = sortListByAbsolute(rightHead)
        
        return mergeByAbsolute(left, right)
    }
    
    private fun mergeByAbsolute(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2
        
        while (p1 != null && p2 != null) {
            if (kotlin.math.abs(p1.`val`) <= kotlin.math.abs(p2.`val`)) {
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current. next!!
        }
        
        current.next = p1 ?: p2
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
    
    // Helper function to print linked list
    fun printList(head: ListNode?) {
        val arr = toArray(head)
        if (arr.isEmpty()) {
            println("[]")
        } else {
            println(arr.joinToString(" -> "))
        }
    }
    
    // Helper to check if sorted
    fun isSorted(head: ListNode?): Boolean {
        var curr = head
        while (curr?. next != null) {
            if (curr.`val` > curr.next!! .`val`) return false
            curr = curr.next
        }
        return true
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = SortLL()
    
    println("=". repeat(70))
    println("SORT LINKED LIST - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Example [4,2,1,3]
    println("\nTest Case 1: [4,2,1,3]")
    var head = solver.createList(intArrayOf(4, 2, 1, 3))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 4")
    println("Is sorted: ${solver.isSorted(head)}")
    
    // Test Case 2: Example [-1,5,3,4,0]
    println("\nTest Case 2: [-1,5,3,4,0]")
    head = solver.createList(intArrayOf(-1, 5, 3, 4, 0))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: -1 -> 0 -> 3 -> 4 -> 5")
    println("Is sorted: ${solver.isSorted(head)}")
    
    // Test Case 3: Empty list
    println("\nTest Case 3: []")
    head = solver.createList(intArrayOf())
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: []")
    
    // Test Case 4: Single node
    println("\nTest Case 4: [1]")
    head = solver.createList(intArrayOf(1))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1")
    
    // Test Case 5: Two nodes
    println("\nTest Case 5: [2,1]")
    head = solver.createList(intArrayOf(2, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected:  1 -> 2")
    
    // Test Case 6: Already sorted
    println("\nTest Case 6: [1,2,3,4,5] - already sorted")
    head = solver.createList(intArrayOf(1, 2, 3, 4, 5))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 4 -> 5")
    
    // Test Case 7: Reverse sorted
    println("\nTest Case 7: [5,4,3,2,1] - reverse sorted")
    head = solver.createList(intArrayOf(5, 4, 3, 2, 1))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 3 -> 4 -> 5")
    
    // Test Case 8: Duplicates
    println("\nTest Case 8: [3,1,2,3,1,2]")
    head = solver.createList(intArrayOf(3, 1, 2, 3, 1, 2))
    print("Original: ")
    solver.printList(head)
    head = solver.sortList(head)
    print("Sorted: ")
    solver.printList(head)
    println("Expected: 1 -> 1 -> 2 -> 2 -> 3 -> 3")
    
    // Test Case 9: Insertion sort
    println("\nTest Case 9: Insertion sort [5,2,8,1,9]")
    head = solver.createList(intArrayOf(5, 2, 8, 1, 9))
    print("Original: ")
    solver.printList(head)
    head = solver.sortListInsertion(head)
    print("Sorted (insertion): ")
    solver.printList(head)
    println("Expected: 1 -> 2 -> 5 -> 8 -> 9")
    
    // Test Case 10: Descending order
    println("\nTest Case 10: Sort descending [3,1,4,1,5,9,2]")
    head = solver.createList(intArrayOf(3, 1, 4, 1, 5, 9, 2))
    print("Original:  ")
    solver.printList(head)
    head = solver.sortListDescending(head)
    print("Sorted (descending): ")
    solver.printList(head)
    println("Expected: 9 -> 5 -> 4 -> 3 -> 2 -> 1 -> 1")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
