/**
 * ============================================================================
 * PROBLEM: Reverse Doubly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Doubly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement methods to reverse a doubly linked list:
 * 1. Iterative approach - swap prev and next pointers for each node
 * 2. Recursive approach - reverse using recursion
 * 
 * Reversing a doubly linked list means making the tail become the head
 * and vice versa, with all links reversed.
 * 
 * INPUT FORMAT:
 * - A doubly linked list (can be empty or have multiple nodes)
 * 
 * OUTPUT FORMAT:
 * - The same list but reversed
 * - All forward links become backward links and vice versa
 * 
 * CONSTRAINTS:
 * - List can be empty (no-op)
 * - List can have 1 to n nodes
 * - All integer data values are valid
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Reversing a doubly linked list is like reversing a train where each car
 * points to both its front and back neighbors. To reverse:
 * 1. For each car, swap which end is "front" and which is "back"
 * 2. The last car becomes the first car
 * 3. The first car becomes the last car
 * 
 * KEY INSIGHT FOR DOUBLY LINKED LIST:
 * Unlike singly linked list, we just need to SWAP prev and next pointers
 * for each node. No need to keep track of previous node!
 * 
 * ITERATIVE APPROACH:
 * 
 * For each node:
 * 1. Swap its prev and next pointers
 * 2. Move to the next node (which is actually current.prev after swap!)
 * 3. After all swaps, swap head and tail
 * 
 * VISUAL EXAMPLE:
 * 
 * Original:
 * HEAD → [null←|10|→] ↔ [←|20|→] ↔ [←|30|→null] ← TAIL
 * 
 * Step 1: At node 10, swap prev↔next
 * [null→|10|←] (now 10's "next" points left, "prev" points right)
 * 
 * Step 2: At node 20, swap prev↔next
 * [→|20|←]
 * 
 * Step 3: At node 30, swap prev↔next
 * [→|30|←null]
 * 
 * Step 4: Swap head and tail
 * HEAD → [null←|30|→] ↔ [←|20|→] ↔ [←|10|→null] ← TAIL
 * 
 * Reversed!
 * 
 * DETAILED ITERATION STEPS:
 * 
 * Original: 10 ↔ 20 ↔ 30
 * 
 * current = 10
 * temp = null
 * Swap: 10.prev ↔ 10.next
 * Result: 10's next = null, 10's prev = 20
 * Move: current = 10.prev (which is 20 now)
 * 
 * current = 20
 * Swap: 20.prev ↔ 20.next
 * Result: 20's next = 10, 20's prev = 30
 * Move: current = 20.prev (which is 30 now)
 * 
 * current = 30
 * Swap: 30.prev ↔ 30.next
 * Result: 30's next = 20, 30's prev = null
 * Move: current = 30.prev (which is null)
 * 
 * Stop. Swap head and tail.
 * 
 * RECURSIVE APPROACH:
 * 
 * Base case: If node is null or single node, return
 * Recursive case:
 * 1. Swap current node's prev and next
 * 2. Recursively reverse the rest (move to prev, which is now next)
 * 3. Update head/tail at the end
 * 
 * EDGE CASES:
 * 1. Empty list: No operation needed
 * 2. Single node: No operation needed (already "reversed")
 * 3. Two nodes: Swap head and tail, update pointers
 * 4. Multiple nodes: Follow algorithm
 * 
 * COMPARISON WITH SINGLY LINKED LIST:
 * - Singly: Need to track previous node, more complex
 * - Doubly: Just swap prev/next for each node, simpler!
 * - Both: O(n) time complexity
 * 
 * WHY SWAP HEAD AND TAIL:
 * After swapping all node pointers, the original head is now at the end
 * and original tail is now at the start. We need to update our head/tail
 * references to reflect this.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * ITERATIVE APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Visit each node exactly once
 * - For each node, perform constant time operations (swap pointers)
 * - n is the number of nodes
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses a few pointer variables (current, temp)
 * - No additional data structures
 * - No recursion stack
 * - Truly constant space
 * 
 * RECURSIVE APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Visit each node exactly once
 * - Each recursive call processes one node
 * - n recursive calls total
 * 
 * SPACE COMPLEXITY: O(n)
 * - Uses call stack for recursion
 * - Maximum recursion depth = n
 * - Each call stores return address and local variables
 * - Stack space: O(n)
 * 
 * COMPARISON:
 * Approach    | Time | Space | Notes
 * ------------|------|-------|------
 * Iterative   | O(n) | O(1)  | Preferred for large lists
 * Recursive   | O(n) | O(n)  | More elegant, stack overhead
 * 
 * RECOMMENDATION:
 * Use iterative approach in production for better space efficiency.
 * Use recursive approach for learning or when elegance is preferred.
 * 
 * ============================================================================
 */

package linkedlist.doubly

/**
 * DoublyNode class for doubly linked list
 */
data class DoublyNode(
    var data: Int,
    var prev: DoublyNode? = null,
    var next: DoublyNode? = null
)

/**
 * ReverseDoublyLL class demonstrating reverse operations
 */
class ReverseDoublyLL {
    private var head: DoublyNode? = null
    private var tail: DoublyNode? = null
    private var size: Int = 0
    
    /**
     * Reverse the doubly linked list iteratively
     * 
     * TIME: O(n) - Visit each node once
     * SPACE: O(1) - Only a few pointer variables
     * 
     * ALGORITHM:
     * 1. Traverse the list
     * 2. For each node, swap its prev and next pointers
     * 3. Move to next node (which is current.prev after swap!)
     * 4. After traversal, swap head and tail
     * 
     * This is more intuitive than singly linked list reversal
     * because we just swap pointers for each node.
     */
    fun reverseIterative() {
        // Empty or single node - no need to reverse
        if (head == null || head == tail) {
            return
        }
        
        // Start from head
        var current = head
        var temp: DoublyNode? = null
        
        // Traverse and swap prev/next for each node
        while (current != null) {
            // Store next before we lose it
            temp = current.prev
            
            // Swap prev and next
            current.prev = current.next
            current.next = temp
            
            // Move to next node (which is now in prev!)
            current = current.prev
        }
        
        // After loop, temp.prev is the new head
        // Swap head and tail
        val oldHead = head
        head = tail
        tail = oldHead
    }
    
    /**
     * Reverse the doubly linked list recursively
     * 
     * TIME: O(n) - Visit each node once
     * SPACE: O(n) - Recursion call stack
     * 
     * ALGORITHM:
     * 1. Base case: if node is null, return null
     * 2. Swap current node's prev and next
     * 3. If this was the last node (next is null before swap), it's new head
     * 4. Recursively process rest of list
     * 5. Return new head
     */
    fun reverseRecursive() {
        if (head == null || head == tail) {
            return
        }
        
        // Perform recursive reversal
        val newHead = reverseRecursiveHelper(head)
        
        // Update head and tail
        tail = head
        head = newHead
    }
    
    /**
     * Helper function for recursive reversal
     * 
     * @param node Current node to process
     * @return The new head of reversed portion
     */
    private fun reverseRecursiveHelper(node: DoublyNode?): DoublyNode? {
        // Base case: null node
        if (node == null) {
            return null
        }
        
        // Swap prev and next for this node
        val temp = node.prev
        node.prev = node.next
        node.next = temp
        
        // If this was the last node (prev is now null), return it as new head
        if (node.prev == null) {
            return node
        }
        
        // Recursively process rest of list
        // Note: after swap, the "rest" is in prev direction
        return reverseRecursiveHelper(node.prev)
    }
    
    /**
     * Alternative recursive implementation (more intuitive)
     */
    fun reverseRecursiveAlt() {
        if (head == null || head == tail) {
            return
        }
        
        reverseRecursiveAltHelper(head)
        
        // Swap head and tail
        val oldHead = head
        head = tail
        tail = oldHead
    }
    
    private fun reverseRecursiveAltHelper(node: DoublyNode?) {
        if (node == null) {
            return
        }
        
        // Swap prev and next
        val temp = node.prev
        node.prev = node.next
        node.next = temp
        
        // Recursively process next node (which is now in prev!)
        if (node.prev != null) {
            reverseRecursiveAltHelper(node.prev)
        }
    }
    
    // Helper methods for building and displaying
    
    fun insertAtTail(data: Int) {
        val newNode = DoublyNode(data)
        if (tail == null) {
            head = newNode
            tail = newNode
        } else {
            newNode.prev = tail
            tail?.next = newNode
            tail = newNode
        }
        size++
    }
    
    fun insertAtHead(data: Int) {
        val newNode = DoublyNode(data)
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            newNode.next = head
            head?.prev = newNode
            head = newNode
        }
        size++
    }
    
    fun displayForward(): String {
        if (head == null) return "List is empty"
        
        val result = StringBuilder()
        var current = head
        while (current != null) {
            result.append(current.data)
            if (current.next != null) result.append(" ↔ ")
            current = current.next
        }
        return result.toString()
    }
    
    fun displayBackward(): String {
        if (tail == null) return "List is empty"
        
        val result = StringBuilder()
        var current = tail
        while (current != null) {
            result.append(current.data)
            if (current.prev != null) result.append(" ↔ ")
            current = current.prev
        }
        return result.toString()
    }
    
    fun getSize(): Int = size
    fun isEmpty(): Boolean = head == null
    
    fun clear() {
        head = null
        tail = null
        size = 0
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Original list: 10 ↔ 20 ↔ 30 ↔ 40
 * HEAD = 10, TAIL = 40
 * 
 * ITERATIVE REVERSAL:
 * 
 * Step 1: current = [10]
 *   Before: 10.prev = null, 10.next = 20
 *   Swap:   10.prev = 20, 10.next = null
 *   Move:   current = 10.prev = 20
 * 
 * Step 2: current = [20]
 *   Before: 20.prev = 10, 20.next = 30
 *   Swap:   20.prev = 30, 20.next = 10
 *   Move:   current = 20.prev = 30
 * 
 * Step 3: current = [30]
 *   Before: 30.prev = 20, 30.next = 40
 *   Swap:   30.prev = 40, 30.next = 20
 *   Move:   current = 30.prev = 40
 * 
 * Step 4: current = [40]
 *   Before: 40.prev = 30, 40.next = null
 *   Swap:   40.prev = null, 40.next = 30
 *   Move:   current = 40.prev = null (stop)
 * 
 * Step 5: Swap head and tail
 *   HEAD = 40, TAIL = 10
 * 
 * Result: 40 ↔ 30 ↔ 20 ↔ 10
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Reverse Doubly Linked List ===\n")
    
    // Test 1: Reverse empty list
    println("Test 1: Reverse empty list")
    val list = ReverseDoublyLL()
    list.reverseIterative()
    println("After reverse: ${list.displayForward()}")  // List is empty
    println()
    
    // Test 2: Reverse single element
    println("Test 2: Reverse single element")
    list.insertAtHead(10)
    println("Before: ${list.displayForward()}")  // 10
    list.reverseIterative()
    println("After: ${list.displayForward()}")  // 10
    println()
    
    // Test 3: Reverse two elements
    println("Test 3: Reverse two elements")
    list.clear()
    list.insertAtTail(10)
    list.insertAtTail(20)
    println("Before: ${list.displayForward()}")  // 10 ↔ 20
    println("Backward: ${list.displayBackward()}")  // 20 ↔ 10
    list.reverseIterative()
    println("After: ${list.displayForward()}")  // 20 ↔ 10
    println("Backward: ${list.displayBackward()}")  // 10 ↔ 20
    println()
    
    // Test 4: Reverse multiple elements (iterative)
    println("Test 4: Reverse multiple elements (iterative)")
    list.clear()
    list.insertAtTail(1)
    list.insertAtTail(2)
    list.insertAtTail(3)
    list.insertAtTail(4)
    list.insertAtTail(5)
    println("Before: ${list.displayForward()}")  // 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5
    println("Backward before: ${list.displayBackward()}")  // 5 ↔ 4 ↔ 3 ↔ 2 ↔ 1
    
    list.reverseIterative()
    println("After: ${list.displayForward()}")  // 5 ↔ 4 ↔ 3 ↔ 2 ↔ 1
    println("Backward after: ${list.displayBackward()}")  // 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5
    println("Size: ${list.getSize()}")  // 5
    println()
    
    // Test 5: Reverse back to original
    println("Test 5: Reverse twice returns to original")
    list.reverseIterative()
    println("After second reverse: ${list.displayForward()}")  // 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5
    println()
    
    // Test 6: Reverse using recursive approach
    println("Test 6: Reverse using recursive approach")
    val list2 = ReverseDoublyLL()
    list2.insertAtTail(10)
    list2.insertAtTail(20)
    list2.insertAtTail(30)
    list2.insertAtTail(40)
    println("Before: ${list2.displayForward()}")  // 10 ↔ 20 ↔ 30 ↔ 40
    
    list2.reverseRecursive()
    println("After recursive: ${list2.displayForward()}")  // 40 ↔ 30 ↔ 20 ↔ 10
    println("Backward: ${list2.displayBackward()}")  // 10 ↔ 20 ↔ 30 ↔ 40
    println()
    
    // Test 7: Alternate recursive method
    println("Test 7: Alternate recursive method")
    list2.reverseRecursiveAlt()
    println("After alt recursive: ${list2.displayForward()}")  // 10 ↔ 20 ↔ 30 ↔ 40
    println()
    
    // Test 8: Large list
    println("Test 8: Reverse larger list")
    val list3 = ReverseDoublyLL()
    for (i in 1..10) {
        list3.insertAtTail(i)
    }
    println("Before: ${list3.displayForward()}")  // 1 to 10
    list3.reverseIterative()
    println("After: ${list3.displayForward()}")  // 10 to 1
    println("Size: ${list3.getSize()}")  // 10
    println()
    
    // Test 9: Reverse with negative numbers
    println("Test 9: Reverse with negative numbers")
    val list4 = ReverseDoublyLL()
    list4.insertAtTail(-5)
    list4.insertAtTail(0)
    list4.insertAtTail(5)
    list4.insertAtTail(10)
    println("Before: ${list4.displayForward()}")  // -5 ↔ 0 ↔ 5 ↔ 10
    list4.reverseIterative()
    println("After: ${list4.displayForward()}")  // 10 ↔ 5 ↔ 0 ↔ -5
    println()
    
    // Test 10: Verify integrity after reversal
    println("Test 10: Verify list integrity")
    val list5 = ReverseDoublyLL()
    list5.insertAtTail(100)
    list5.insertAtTail(200)
    list5.insertAtTail(300)
    println("Original: ${list5.displayForward()}")  // 100 ↔ 200 ↔ 300
    
    list5.reverseIterative()
    println("Reversed: ${list5.displayForward()}")  // 300 ↔ 200 ↔ 100
    println("Backward traversal works: ${list5.displayBackward()}")  // 100 ↔ 200 ↔ 300
    
    // Add element after reversal
    list5.insertAtTail(50)
    println("After adding 50: ${list5.displayForward()}")  // 300 ↔ 200 ↔ 100 ↔ 50
    
    list5.insertAtHead(400)
    println("After adding 400 at head: ${list5.displayForward()}")  // 400 ↔ 300 ↔ 200 ↔ 100 ↔ 50
    println("List is fully functional after reversal!")
}

