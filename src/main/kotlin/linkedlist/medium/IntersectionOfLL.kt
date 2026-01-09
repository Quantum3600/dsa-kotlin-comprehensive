/**
 * ============================================================================
 * PROBLEM:    Intersection of Two Linked Lists
 * DIFFICULTY: Medium (actually Easy)
 * CATEGORY:  Linked List
 * LEETCODE:   #160
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given the heads of two singly linked lists headA and headB, return the node
 * at which the two lists intersect. If the two lists have no intersection,
 * return null.
 * 
 * The intersection is based on REFERENCE, not value.  Two lists intersect if
 * they share the same node object (not just same value).
 * 
 * INPUT FORMAT:
 * - headA: Head of first linked list
 * - headB: Head of second linked list
 * - Note: Lists may have different lengths before intersection
 * 
 * OUTPUT FORMAT:
 * - The intersection node, or null if no intersection
 * 
 * CONSTRAINTS:
 * - Number of nodes in listA: [0, 3 * 10^4]
 * - Number of nodes in listB: [0, 3 * 10^4]
 * - 1 <= Node.val <= 10^5
 * - No cycles in either list
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:   
 *   listA: 4 -> 1 ↘
 *                  8 -> 4 -> 5
 *   listB: 5 -> 6 ↗
 * Output: Node with value 8
 * 
 * Example 2:
 * Input: 
 *   listA: 1 -> 9 -> 1 ↘
 *                       2 -> 4
 *   listB:           3 ↗
 * Output: Node with value 2
 * 
 * Example 3:
 * Input:
 *   listA: 2 -> 6 -> 4
 *   listB: 1 -> 5
 * Output: null (no intersection)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION - THE LENGTH DIFFERENCE PROBLEM:
 * 
 * If two lists intersect, they share a common "tail" from the intersection
 * point onward.   The challenge is that lists may have different lengths
 * BEFORE the intersection. 
 * 
 * KEY INSIGHT - MULTIPLE APPROACHES:
 * 
 * APPROACH A: ALIGN BY LENGTH
 * - Calculate lengths of both lists
 * - Advance the longer list by (length difference)
 * - Now both are aligned!  Move together until they meet
 * 
 * APPROACH B: TWO POINTERS (ELEGANT!)
 * - Let both pointers traverse:  A → B, and B → A
 * - They cover the same total distance! 
 * - They'll meet at intersection (or null)
 * 
 * APPROACH C: HASH SET
 * - Store all nodes from listA in a set
 * - Traverse listB, first node in set is intersection
 * 
 * VISUAL EXAMPLE (Approach A - Align):
 * ```
 * listA: 4 -> 1 -> 8 -> 4 -> 5  (length 5)
 * listB: 5 -> 6 -> 8 -> 4 -> 5  (length 5)
 * 
 * Same length, so no alignment needed
 * 
 * Step by step:
 *   p1 = 4, p2 = 5  (different)
 *   p1 = 1, p2 = 6  (different)
 *   p1 = 8, p2 = 8  (same!) ← intersection
 * 
 * Return node 8
 * ```
 * 
 * VISUAL EXAMPLE (Different Lengths):
 * ```
 * listA: 4 -> 1 -> 8 -> 4 -> 5  (length 5)
 * listB:       6 -> 8 -> 4 -> 5  (length 4)
 * 
 * Difference = 5 - 4 = 1
 * Advance p1 by 1 step: 
 * 
 * p1 starts at 1 (after skipping 4)
 * p2 starts at 6
 * 
 * Now aligned: 
 *   p1 = 1, p2 = 6  (different)
 *   p1 = 8, p2 = 8  (same!) ← intersection
 * ```
 * 
 * VISUAL EXAMPLE (Approach B - Two Pointers):
 * ```
 * listA: 4 -> 1 -> 8 -> 4 -> 5  (lenA = 5)
 * listB:      6 -> 8 -> 4 -> 5  (lenB = 4)
 * 
 * Let p1 traverse A then B:  4,1,8,4,5, then 6,8,4,5
 * Let p2 traverse B then A: 6,8,4,5, then 4,1,8,4,5
 * 
 * Total distance both travel: 5 + 4 = 9
 * 
 * Step by step:
 *   p1=4, p2=6
 *   p1=1, p2=8
 *   p1=8, p2=4
 *   p1=4, p2=5
 *   p1=5, p2=4  (p2 switches to listA)
 *   p1=6, p2=1  (p1 switches to listB)
 *   p1=8, p2=8  ← Meet at intersection! 
 * ```
 * 
 * WHY TWO POINTERS WORK:
 * - Distance p1 travels: lenA + (lenB - common)
 * - Distance p2 travels: lenB + (lenA - common)
 * - Both equal!  They meet at intersection (or null)
 * 
 * ALGORITHM STEPS (Two Pointers - Optimal):
 * 1. Initialize p1 = headA, p2 = headB
 * 2. While p1 != p2:
 *    a. If p1 is null, p1 = headB (switch to other list)
 *    b. Else p1 = p1.next
 *    c. If p2 is null, p2 = headA (switch to other list)
 *    d. Else p2 = p2.next
 * 3. Return p1 (intersection or null)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH A (ALIGN BY LENGTH):
 * TIME: O(m + n) - calculate lengths, then traverse
 * SPACE: O(1) - only pointers
 * 
 * APPROACH B (TWO POINTERS):
 * TIME: O(m + n) - at most traverse both lists once
 * SPACE: O(1) - only two pointers
 * 
 * APPROACH C (HASH SET):
 * TIME: O(m + n)
 * SPACE: O(m) or O(n) - store one list
 * 
 * Two pointers is most elegant!
 * 
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: BRUTE FORCE
 * - For each node in A, check all nodes in B
 * - Time: O(m * n), Space: O(1)
 * - Too slow
 * 
 * APPROACH 2: MARK NODES (Destructive)
 * - Modify visited nodes with special marker
 * - First marked node in B is intersection
 * - Modifies list (not acceptable)
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. No intersection → return null
 * 2. Same head (intersect at first node) → return head
 * 3. Intersect at last node → return last
 * 4. One list empty → return null
 * 5. Both lists empty → return null
 * 6. Different lengths before intersection → handle correctly
 * 7. Same length, no intersection → return null
 * 8. Single node each, same node → return that node
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. ❌ Comparing values instead of references
 *    Result: Wrong intersection point
 *    Fix: Compare node objects, not node. val
 * 
 * 2. ❌ Not handling different lengths
 *    Result: Miss the intersection
 *    Fix:  Align lists or use two-pointer trick
 * 
 * 3. ❌ Infinite loop in two-pointer approach
 *    Result: Never terminates
 *    Fix: Switch to other list when null
 * 
 * 4. ❌ Not handling null inputs
 *    Result: NullPointerException
 *    Fix: Check for null heads
 * 
 * 5. ❌ Wrong length calculation
 *    Result:  Misalignment
 *    Fix: Count carefully
 * 
 * ============================================================================
 * REAL-WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Version Control**:  Find common ancestor of branches
 * 2. **File Systems**: Find shared directory path
 * 3. **Network Topology**: Find common network node
 * 4. **Family Trees**: Find common ancestor
 * 5. **Dependency Graphs**: Find shared dependency
 * 6. **Supply Chains**: Find common supplier
 * 7. **Social Networks**: Find mutual connections
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify intersection**: Reference, not value! 
 * 2. **Draw the diagram**: Show Y-shape intersection
 * 3. **Explain two approaches**: Align vs two-pointer
 * 4. **Walk through example**: Show pointer movements
 * 5. **Handle no intersection**: Both reach null
 * 6. **Space complexity**:  Highlight O(1)
 * 7. **Edge cases**: Empty lists, same head
 * 8. **Mathematical proof**: Why two-pointer works
 * 
 * ============================================================================
 * VARIATIONS & FOLLOW-UPS
 * ============================================================================
 * 
 * 1. Find the length of the common part
 * 2. Count nodes before intersection in each list
 * 3. Split at intersection (remove common part)
 * 4. Merge two lists at specific point
 * 5. Find intersection of three lists
 * 6. Handle lists with cycles
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - LeetCode 21: Merge Two Sorted Lists
 * - LeetCode 141: Linked List Cycle
 * - LeetCode 142: Linked List Cycle II
 * - LeetCode 234: Palindrome Linked List
 * 
 * ============================================================================
 * CODE IMPLEMENTATION
 * ============================================================================
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class IntersectionOfLL {
    
    /**
     * APPROACH 1: TWO POINTERS (MOST ELEGANT)
     * 
     * TIME COMPLEXITY: O(m + n)
     * SPACE COMPLEXITY:  O(1)
     */
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) return null
        
        var p1 = headA
        var p2 = headB
        
        // Traverse both lists
        // When reaching end, switch to other list
        while (p1 != p2) {
            p1 = if (p1 == null) headB else p1.next
            p2 = if (p2 == null) headA else p2.next
        }
        
        // Either intersection node or null
        return p1
    }
    
    /**
     * APPROACH 2: ALIGN BY LENGTH
     * 
     * TIME COMPLEXITY: O(m + n)
     * SPACE COMPLEXITY: O(1)
     */
    fun getIntersectionNodeAlign(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) return null
        
        // Calculate lengths
        val lenA = getLength(headA)
        val lenB = getLength(headB)
        
        // Align the longer list
        var p1 = headA
        var p2 = headB
        
        if (lenA > lenB) {
            repeat(lenA - lenB) {
                p1 = p1?. next
            }
        } else {
            repeat(lenB - lenA) {
                p2 = p2?.next
            }
        }
        
        // Now both aligned, find intersection
        while (p1 != p2) {
            p1 = p1?.next
            p2 = p2?.next
        }
        
        return p1
    }
    
    private fun getLength(head: ListNode? ): Int {
        var length = 0
        var curr = head
        while (curr != null) {
            length++
            curr = curr.next
        }
        return length
    }
    
    /**
     * APPROACH 3: USING HASH SET
     * 
     * TIME COMPLEXITY: O(m + n)
     * SPACE COMPLEXITY: O(m) or O(n)
     */
    fun getIntersectionNodeHashSet(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) return null
        
        // Store all nodes from listA
        val nodesA = mutableSetOf<ListNode>()
        var curr = headA
        while (curr != null) {
            nodesA.add(curr)
            curr = curr.next
        }
        
        // Find first node from listB that's in set
        curr = headB
        while (curr != null) {
            if (curr in nodesA) {
                return curr
            }
            curr = curr.next
        }
        
        return null
    }
    
    /**
     * BONUS: Get length of common part
     */
    fun getCommonLength(headA: ListNode?, headB: ListNode?): Int {
        val intersection = getIntersectionNode(headA, headB) ?: return 0
        
        var length = 0
        var curr = intersection
        while (curr != null) {
            length++
            curr = curr.next
        }
        
        return length
    }
    
    /**
     * BONUS: Count nodes before intersection
     */
    fun getNodesBeforeIntersection(head: ListNode?, intersection: ListNode?): Int {
        if (intersection == null) return 0
        
        var count = 0
        var curr = head
        while (curr != null && curr != intersection) {
            count++
            curr = curr.next
        }
        
        return count
    }
    
    // Helper function to create intersecting lists
    fun createIntersectingLists(
        valsA: IntArray,
        valsB: IntArray,
        valsCommon: IntArray
    ): Pair<ListNode?, ListNode? > {
        // Create common part
        val common = if (valsCommon.isNotEmpty()) {
            createList(valsCommon)
        } else null
        
        // Create listA
        val headA = if (valsA.isNotEmpty()) {
            val head = createList(valsA)
            var curr = head
            while (curr?. next != null) {
                curr = curr.next
            }
            curr?. next = common
            head
        } else common
        
        // Create listB
        val headB = if (valsB.isNotEmpty()) {
            val head = createList(valsB)
            var curr = head
            while (curr?.next != null) {
                curr = curr.next
            }
            curr?.next = common
            head
        } else common
        
        return Pair(headA, headB)
    }
    
    private fun createList(vals: IntArray): ListNode? {
        if (vals.isEmpty()) return null
        val head = ListNode(vals[0])
        var curr = head
        for (i in 1 until vals.size) {
            curr.next = ListNode(vals[i])
            curr = curr.next!! 
        }
        return head
    }
    
    // Helper function to print list (stop at intersection or end)
    fun printList(head: ListNode?, intersection: ListNode?) {
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr.`val`)
            if (curr == intersection) {
                println(values.joinToString(" -> ") + " -> [INTERSECTION]")
                return
            }
            curr = curr.next
        }
        println(values.joinToString(" -> ") + " -> null")
    }
}

/**
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */
fun main() {
    val solver = IntersectionOfLL()
    
    println("=". repeat(70))
    println("INTERSECTION OF TWO LINKED LISTS - TEST CASES")
    println("=".repeat(70))
    
    // Test Case 1: Standard intersection [4,1] and [5,6] meeting at [8,4,5]
    println("\nTest Case 1: Intersection at [8,4,5]")
    var (headA, headB) = solver.createIntersectingLists(
        intArrayOf(4, 1),
        intArrayOf(5, 6),
        intArrayOf(8, 4, 5)
    )
    print("List A: ")
    solver.printList(headA, null)
    print("List B: ")
    solver.printList(headB, null)
    val intersection1 = solver.getIntersectionNode(headA, headB)
    println("Intersection value: ${intersection1?.`val`}")
    println("Expected: 8")
    
    // Test Case 2: Different lengths [1,9,1] and [3] meeting at [2,4]
    println("\nTest Case 2: Different lengths")
    val (headA2, headB2) = solver.createIntersectingLists(
        intArrayOf(1, 9, 1),
        intArrayOf(3),
        intArrayOf(2, 4)
    )
    val intersection2 = solver.getIntersectionNode(headA2, headB2)
    println("Intersection value: ${intersection2?. `val`}")
    println("Expected: 2")
    
    // Test Case 3: No intersection
    println("\nTest Case 3: No intersection")
    val (headA3, headB3) = solver.createIntersectingLists(
        intArrayOf(2, 6, 4),
        intArrayOf(1, 5),
        intArrayOf()
    )
    val intersection3 = solver.getIntersectionNode(headA3, headB3)
    println("Intersection:  ${intersection3?.`val` ?: "null"}")
    println("Expected: null")
    
    // Test Case 4: Intersection at first node
    println("\nTest Case 4: Intersection at first node")
    val (headA4, headB4) = solver.createIntersectingLists(
        intArrayOf(),
        intArrayOf(),
        intArrayOf(1, 2, 3)
    )
    val intersection4 = solver.getIntersectionNode(headA4, headB4)
    println("Intersection value: ${intersection4?.`val`}")
    println("Expected: 1 (same head)")
    
    // Test Case 5: Single nodes, same
    println("\nTest Case 5: Single node, intersection")
    val (headA5, headB5) = solver.createIntersectingLists(
        intArrayOf(),
        intArrayOf(),
        intArrayOf(1)
    )
    val intersection5 = solver.getIntersectionNode(headA5, headB5)
    println("Intersection value: ${intersection5?.`val`}")
    println("Expected: 1")
    
    // Test Case 6: One list empty
    println("\nTest Case 6: One list empty")
    val intersection6 = solver.getIntersectionNode(null, headB2)
    println("Intersection:  ${intersection6?.`val` ?: "null"}")
    println("Expected: null")
    
    // Test Case 7: Using align method
    println("\nTest Case 7: Align method")
    val (headA7, headB7) = solver.createIntersectingLists(
        intArrayOf(10, 20),
        intArrayOf(30),
        intArrayOf(40, 50, 60)
    )
    val intersection7 = solver.getIntersectionNodeAlign(headA7, headB7)
    println("Intersection value (align): ${intersection7?.`val`}")
    println("Expected: 40")
    
    // Test Case 8: Using hash set method
    println("\nTest Case 8: Hash set method")
    val (headA8, headB8) = solver.createIntersectingLists(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5),
        intArrayOf(6, 7, 8)
    )
    val intersection8 = solver.getIntersectionNodeHashSet(headA8, headB8)
    println("Intersection value (hash set): ${intersection8?.`val`}")
    println("Expected: 6")
    
    // Test Case 9: Get common length
    println("\nTest Case 9: Common length")
    val (headA9, headB9) = solver.createIntersectingLists(
        intArrayOf(1),
        intArrayOf(2, 3),
        intArrayOf(4, 5, 6, 7)
    )
    val commonLen = solver.getCommonLength(headA9, headB9)
    println("Common part length: $commonLen")
    println("Expected: 4")
    
    // Test Case 10: Nodes before intersection
    println("\nTest Case 10: Count nodes before intersection")
    val (headA10, headB10) = solver.createIntersectingLists(
        intArrayOf(10, 20, 30),
        intArrayOf(40),
        intArrayOf(50, 60)
    )
    val intersection10 = solver.getIntersectionNode(headA10, headB10)
    val countA = solver.getNodesBeforeIntersection(headA10, intersection10)
    val countB = solver.getNodesBeforeIntersection(headB10, intersection10)
    println("Nodes in A before intersection: $countA")
    println("Nodes in B before intersection: $countB")
    println("Expected: A=3, B=1")
    
    println("\n" + "=".repeat(70))
    println("ALL TEST CASES COMPLETED")
    println("=".repeat(70))
}
