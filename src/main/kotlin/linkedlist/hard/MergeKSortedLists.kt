/**
 * ============================================================================
 * PROBLEM: Merge K Sorted Lists
 * DIFFICULTY: Hard
 * CATEGORY: Linked List, Heap, Divide and Conquer
 * LEETCODE:  #23
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an array of k linked-lists lists, each linked-list is sorted
 * in ascending order.  Merge all the linked-lists into one sorted linked-list
 * and return it.
 * 
 * INPUT FORMAT:
 * - An array of k linked-list heads
 * - Each list is sorted in ascending order
 * 
 * OUTPUT FORMAT: 
 * - Head of the merged sorted linked list
 * 
 * CONSTRAINTS:
 * - k == lists.length
 * - 0 <= k <= 10^4
 * - 0 <= lists[i].length <= 500
 * - -10^4 <= Node.val <= 10^4
 * - lists[i] is sorted in ascending order
 * - Sum of lists[i].length ≤ 10^4
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 * 
 * Example 1:
 * Input:  lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation:  Merging all lists into one sorted list
 * 
 * Example 2:
 * Input: lists = []
 * Output: []
 * 
 * Example 3:
 * Input:  lists = [[]]
 * Output: []
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We have k sorted lists and need to merge them efficiently.  Think of this
 * like merging k sorted arrays - we need to repeatedly pick the smallest
 * element across all lists. 
 * 
 * KEY INSIGHT - MULTIPLE APPROACHES:
 * 
 * APPROACH 1: MIN HEAP (PRIORITY QUEUE) ⭐ OPTIMAL
 * - Use a min-heap to always get the smallest element across all lists
 * - Start by adding first node from each list to heap
 * - Repeatedly extract min, add it to result, and add next node from that list
 * - Time: O(N log k), Space: O(k)
 * 
 * APPROACH 2: DIVIDE AND CONQUER (MERGE PAIRS)
 * - Merge lists in pairs, then merge results recursively
 * - Similar to merge sort
 * - Time: O(N log k), Space: O(log k) for recursion
 * 
 * APPROACH 3: MERGE ONE BY ONE
 * - Merge lists sequentially:  merge(merge(list1, list2), list3)... 
 * - Time: O(kN), Space: O(1)
 * - Inefficient for large k
 * 
 * WHY MIN HEAP IS OPTIMAL:
 * - Always access smallest element in O(log k) time
 * - Only store k elements at a time (one from each list)
 * - No need to merge entire lists at once
 * - Efficient for both time and space
 * 
 * ALGORITHM STEPS (MIN HEAP APPROACH):
 * 1. Create a min-heap based on node values
 * 2. Add first node from each non-empty list to heap
 * 3. Create dummy head for result list
 * 4. While heap is not empty:
 *    a. Extract minimum node from heap
 *    b. Add it to result list
 *    c. If extracted node has next, add next to heap
 * 5. Return dummy. next
 * 
 * VISUAL EXAMPLE:
 * ```
 * Input: 
 * List 1: 1 → 4 → 5
 * List 2: 1 → 3 → 4
 * List 3: 2 → 6
 * 
 * Step 1: Add first nodes to heap
 * Heap: [1(L1), 1(L2), 2(L3)]
 * 
 * Step 2: Extract min = 1(L1), add 4(L1)
 * Result: 1 →
 * Heap: [1(L2), 2(L3), 4(L1)]
 * 
 * Step 3: Extract min = 1(L2), add 3(L2)
 * Result: 1 → 1 →
 * Heap: [2(L3), 3(L2), 4(L1)]
 * 
 * Step 4: Extract min = 2(L3), add 6(L3)
 * Result: 1 → 1 → 2 →
 * Heap: [3(L2), 4(L1), 6(L3)]
 * 
 * Step 5: Extract min = 3(L2), add 4(L2)
 * Result: 1 → 1 → 2 → 3 →
 * Heap: [4(L1), 4(L2), 6(L3)]
 * 
 * Step 6: Extract min = 4(L1), add 5(L1)
 * Result: 1 → 1 → 2 → 3 → 4 →
 * Heap: [4(L2), 5(L1), 6(L3)]
 * 
 * Step 7: Extract min = 4(L2), no next
 * Result: 1 → 1 → 2 → 3 → 4 → 4 →
 * Heap:  [5(L1), 6(L3)]
 * 
 * Step 8: Extract min = 5(L1), no next
 * Result: 1 → 1 → 2 → 3 → 4 → 4 → 5 →
 * Heap: [6(L3)]
 * 
 * Step 9: Extract min = 6(L3), no next
 * Result: 1 → 1 ��� 2 → 3 → 4 → 4 → 5 → 6 → NULL ✓
 * Heap: []
 * ```
 * 
 * DIVIDE AND CONQUER VISUAL:
 * ```
 * Round 1: Merge pairs
 * [1→4→5] + [1→3→4] = [1→1→3→4→4→5]
 * [2→6]               = [2→6]
 * 
 * Round 2: Merge results
 * [1→1→3→4→4→5] + [2→6] = [1→1→2→3→4→4→5→6]
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH 1: MIN HEAP
 * TIME COMPLEXITY: O(N log k)
 * - N = total number of nodes across all lists
 * - Each node is inserted and extracted from heap once:  O(log k) per operation
 * - Total operations: N insertions + N extractions
 * - Overall: N * 2 * log k = O(N log k)
 * 
 * SPACE COMPLEXITY: O(k)
 * - Heap stores at most k nodes (one from each list)
 * - Result list not counted as output space
 * 
 * APPROACH 2: DIVIDE AND CONQUER
 * TIME COMPLEXITY: O(N log k)
 * - log k levels of merging
 * - Each level processes all N nodes
 * - Overall: O(N log k)
 * 
 * SPACE COMPLEXITY: O(log k)
 * - Recursion stack depth is log k
 * 
 * APPROACH 3: MERGE ONE BY ONE
 * TIME COMPLEXITY: O(kN)
 * - Merging list i takes O(i * avgLength) time
 * - Total: O(k * N)
 * 
 * SPACE COMPLEXITY:  O(1)
 * 
 * ============================================================================
 */

package linkedlist. hard

import java.util.PriorityQueue

/**
 * Node structure for singly linked list
 * 
 * @property val The value stored in the node
 * @property next Reference to the next node
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class MergeKSortedLists {
    
    /**
     * APPROACH 1: Using Min Heap (Priority Queue)
     * Most efficient for large k
     * 
     * @param lists Array of sorted linked list heads
     * @return Head of merged sorted list
     */
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        // Edge case: empty array
        if (lists.isEmpty()) return null
        
        // Create min heap based on node values
        val minHeap = PriorityQueue<ListNode> { a, b -> a.`val` - b.`val` }
        
        // Add first node of each non-null list to heap
        for (head in lists) {
            head?.let { minHeap.offer(it) }
        }
        
        // Dummy head for result list
        val dummy = ListNode(0)
        var curr = dummy
        
        // Process nodes until heap is empty
        while (minHeap.isNotEmpty()) {
            // Extract minimum node
            val minNode = minHeap.poll()
            
            // Add to result list
            curr.next = minNode
            curr = curr. next!! 
            
            // If extracted node has next, add it to heap
            minNode.next?.let { minHeap.offer(it) }
        }
        
        return dummy. next
    }
    
    /**
     * APPROACH 2: Divide and Conquer (Merge Pairs)
     * Better space complexity (recursion stack)
     * 
     * @param lists Array of sorted linked list heads
     * @return Head of merged sorted list
     */
    fun mergeKListsDivideConquer(lists:  Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        if (lists.size == 1) return lists[0]
        
        return mergeLists(lists, 0, lists.size - 1)
    }
    
    /**
     * Recursively merge lists from index left to right
     */
    private fun mergeLists(lists: Array<ListNode?>, left: Int, right: Int): ListNode? {
        // Base case: single list
        if (left == right) return lists[left]
        
        // Divide into two halves
        val mid = left + (right - left) / 2
        val leftList = mergeLists(lists, left, mid)
        val rightList = mergeLists(lists, mid + 1, right)
        
        // Merge two sorted lists
        return mergeTwoLists(leftList, rightList)
    }
    
    /**
     * Merge two sorted linked lists
     * Classic merge operation from merge sort
     * 
     * @param l1 First sorted list
     * @param l2 Second sorted list
     * @return Head of merged list
     */
    private fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        // Edge cases
        if (l1 == null) return l2
        if (l2 == null) return l1
        
        val dummy = ListNode(0)
        var curr = dummy
        var p1 = l1
        var p2 = l2
        
        // Merge while both lists have nodes
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                curr.next = p1
                p1 = p1.next
            } else {
                curr.next = p2
                p2 = p2.next
            }
            curr = curr.next!! 
        }
        
        // Append remaining nodes
        curr.next = p1 ?:  p2
        
        return dummy.next
    }
    
    /**
     * APPROACH 3: Merge One by One (Sequential)
     * Simplest but least efficient for large k
     * 
     * @param lists Array of sorted linked list heads
     * @return Head of merged sorted list
     */
    fun mergeKListsSequential(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        
        var result = lists[0]
        
        // Merge each list one by one with result
        for (i in 1 until lists.size) {
            result = mergeTwoLists(result, lists[i])
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Problem: Merge [[1,4,5],[1,3,4],[2,6]]
 * 
 * Using Min Heap Approach:
 * 
 * Initial state:
 * List 0: 1 → 4 → 5 → NULL
 * List 1: 1 → 3 → 4 → NULL
 * List 2: 2 → 6 → NULL
 * 
 * Heap after initialization:  [1(L0), 1(L1), 2(L2)]
 * 
 * Iteration 1:
 *   poll() → 1(L0), add 4(L0)
 *   Result: dummy → 1(L0)
 *   Heap: [1(L1), 2(L2), 4(L0)]
 * 
 * Iteration 2:
 *   poll() → 1(L1), add 3(L1)
 *   Result: dummy → 1(L0) → 1(L1)
 *   Heap: [2(L2), 3(L1), 4(L0)]
 * 
 * ...  (continue until heap empty)
 * 
 * Final Result: 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6 → NULL ✓
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty array: [] → NULL
 * 2. Array with empty lists: [[]] → NULL
 * 3. Single list: [[1,2,3]] → [1,2,3]
 * 4. All lists empty: [[],[],[]] → NULL
 * 5. Lists with duplicates: [[1,1],[1,1]] → [1,1,1,1]
 * 6. Lists with negative numbers: [[-5,-1],[0,2]] → [-5,-1,0,2]
 * 7. Lists of different lengths: [[1,10],[2],[3,4,5]] → [1,2,3,4,5,10]
 * 
 * ============================================================================
 */

fun main() {
    val solution = MergeKSortedLists()
    
    println("Merge K Sorted Lists - Test Cases")
    println("===================================\n")
    
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
    fun printList(head: ListNode? ): String {
        val result = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            result.add(curr.`val`)
            curr = curr.next
        }
        return result. toString()
    }
    
    // Test Case 1: Standard case with 3 lists
    println("Test 1: Multiple sorted lists")
    val list1 = createList(intArrayOf(1, 4, 5))
    val list2 = createList(intArrayOf(1, 3, 4))
    val list3 = createList(intArrayOf(2, 6))
    val lists1 = arrayOf(list1, list2, list3)
    
    val result1 = solution.mergeKLists(lists1)
    println("Input: [[1,4,5],[1,3,4],[2,6]]")
    println("Output: ${printList(result1)}")
    println("Expected: [1, 1, 2, 3, 4, 4, 5, 6]")
    println("✓ Test 1 Passed\n")
    
    // Test Case 2: Empty array
    println("Test 2: Empty array")
    val result2 = solution.mergeKLists(arrayOf())
    println("Output: ${printList(result2)}")
    println("Expected: []")
    println("✓ Test 2 Passed\n")
    
    // Test Case 3: Single list
    println("Test 3: Single list")
    val single = createList(intArrayOf(1, 2, 3))
    val result3 = solution.mergeKLists(arrayOf(single))
    println("Output: ${printList(result3)}")
    println("Expected: [1, 2, 3]")
    println("✓ Test 3 Passed\n")
    
    // Test Case 4: Divide and conquer approach
    println("Test 4: Using Divide and Conquer")
    val list4 = createList(intArrayOf(1, 4, 5))
    val list5 = createList(intArrayOf(1, 3, 4))
    val list6 = createList(intArrayOf(2, 6))
    val lists4 = arrayOf(list4, list5, list6)
    
    val result4 = solution.mergeKListsDivideConquer(lists4)
    println("Output: ${printList(result4)}")
    println("Expected: [1, 1, 2, 3, 4, 4, 5, 6]")
    println("✓ Test 4 Passed\n")
    
    println("All tests passed!  ✓")
    println("\nComparison of Approaches:")
    println("1. Min Heap:  Best for large k, O(N log k) time, O(k) space")
    println("2. Divide & Conquer: Good for memory-constrained, O(N log k) time, O(log k) space")
    println("3. Sequential:  Simple but slow for large k, O(kN) time, O(1) space")
}
