/**
 * ============================================================================
 * PROBLEM: Insert in Doubly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Doubly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement various insertion operations in a doubly linked list:
 * 1. Insert at the beginning (head)
 * 2. Insert at the end (tail)
 * 3. Insert at a specific position
 * 4. Insert before a given node
 * 5. Insert after a given node
 * 
 * In a doubly linked list, each node has two pointers (prev and next),
 * so insertion requires updating four pointer connections instead of two.
 * 
 * INPUT FORMAT:
 * - For insertAtHead/Tail: data value to insert
 * - For insertAtPosition: data value and position (0-indexed)
 * - For insertBefore/After: data value and target node reference
 * 
 * OUTPUT FORMAT:
 * - Updated doubly linked list with the new node inserted
 * - Boolean indicating success/failure for position-based operations
 * 
 * CONSTRAINTS:
 * - List can be empty initially
 * - Position must be valid (0 <= position <= size)
 * - Node references must be valid for before/after operations
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Inserting into a doubly linked list is like adding a new train car that
 * needs to be connected from BOTH sides. Each new car must know which car
 * is behind it (prev) and which is ahead (next), and the adjacent cars
 * must update their links to point to the new car.
 * 
 * KEY DIFFERENCE FROM SINGLY LINKED LIST:
 * - Singly: Update 2 pointers (new.next and prev.next)
 * - Doubly: Update 4 pointers (new.prev, new.next, prev.next, next.prev)
 * 
 * INSERT AT HEAD - FOUR STEPS:
 * 1. Create new node
 * 2. Set new node's next to current head
 * 3. Set current head's prev to new node (if head exists)
 * 4. Update head to new node
 * 
 * VISUAL EXAMPLE (Insert 5 at head):
 * Before:  HEAD → [null←|10|↔|20|→null] ← TAIL
 * 
 * Step 1: Create [5]
 * Step 2: [5|→] points to [10]
 * Step 3: [10|←] points to [5]
 * Step 4: HEAD → [null←|5|↔|10|↔|20|→null] ← TAIL
 * 
 * INSERT AT TAIL - FOUR STEPS (with tail pointer):
 * 1. Create new node
 * 2. Set new node's prev to current tail
 * 3. Set current tail's next to new node (if tail exists)
 * 4. Update tail to new node
 * 
 * VISUAL EXAMPLE (Insert 30 at tail):
 * Before:  HEAD → [null←|10|↔|20|→null] ← TAIL
 * 
 * Step 1: Create [30]
 * Step 2: [30|←] points to [20]
 * Step 3: [20|→] points to [30]
 * Step 4: HEAD → [null←|10|↔|20|↔|30|→null] ← TAIL
 * 
 * INSERT AT POSITION - DETAILED STEPS:
 * 1. Validate position
 * 2. Handle special cases (position 0 or size)
 * 3. Traverse to position
 * 4. Create new node
 * 5. Update four pointers:
 *    - new.prev = current.prev
 *    - new.next = current
 *    - current.prev.next = new
 *    - current.prev = new
 * 
 * VISUAL EXAMPLE (Insert 15 at position 1):
 * Before:  HEAD → [null←|10|↔|20|↔|30|→null] ← TAIL
 *                         0     1     2
 * 
 * Want to insert at position 1 (before node 20):
 * 
 * Step 1: Traverse to position 1 (node [20])
 * Step 2: Create [15]
 * Step 3: Update pointers:
 *   [15].prev = [10]
 *   [15].next = [20]
 *   [10].next = [15]
 *   [20].prev = [15]
 * 
 * After:  HEAD → [null←|10|↔|15|↔|20|↔|30|→null] ← TAIL
 *                        0     1     2     3
 * 
 * INSERT BEFORE NODE:
 * Given a reference to a node, insert a new node before it.
 * This is efficient because we have prev pointer!
 * 
 * INSERT AFTER NODE:
 * Given a reference to a node, insert a new node after it.
 * Similar to singly linked list but update prev pointer too.
 * 
 * EDGE CASES:
 * 1. Empty list: Both head and tail should point to new node
 * 2. Single node: Update both head and tail carefully
 * 3. Insert at position 0: Same as insertAtHead
 * 4. Insert at position size: Same as insertAtTail
 * 5. Invalid position: Return false, don't modify list
 * 
 * POINTER UPDATE ORDER MATTERS:
 * Always set new node's pointers BEFORE updating adjacent nodes!
 * Wrong order can lose references and cause bugs.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * INSERT AT HEAD:
 * TIME COMPLEXITY: O(1)
 * - Create node: O(1)
 * - Update 4 pointers: O(1)
 * - No traversal needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - One new node
 * 
 * INSERT AT TAIL (with tail pointer):
 * TIME COMPLEXITY: O(1)
 * - Direct access to tail
 * - Update 4 pointers: O(1)
 * - No traversal needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - One new node
 * 
 * INSERT AT POSITION:
 * TIME COMPLEXITY: O(n)
 * - Must traverse to position
 * - Worst case: O(n) when position is at end
 * - Average case: O(n/2) = O(n)
 * - Best case: O(1) when position is 0
 * 
 * SPACE COMPLEXITY: O(1)
 * - One new node
 * - Constant traversal variables
 * 
 * INSERT BEFORE/AFTER NODE:
 * TIME COMPLEXITY: O(1)
 * - Direct node reference provided
 * - Just update 4 pointers
 * 
 * SPACE COMPLEXITY: O(1)
 * - One new node
 * 
 * COMPARISON WITH SINGLY LINKED LIST:
 * Operation          | Singly LL | Doubly LL
 * -------------------|-----------|----------
 * Insert at head     | O(1)      | O(1)
 * Insert at tail*    | O(1)      | O(1) ← Same
 * Insert at position | O(n)      | O(n)
 * Insert before node | O(n)**    | O(1) ← Better!
 * Insert after node  | O(1)      | O(1)
 * 
 * * With tail pointer
 * ** Need to find previous node in singly LL
 * 
 * ============================================================================
 */

package linkedlist.doubly

/**
 * DoublyNode class representing a node in doubly linked list
 * 
 * @param data The value stored in this node
 * @param prev Reference to the previous node
 * @param next Reference to the next node
 */
data class DoublyNode(
    var data: Int,
    var prev: DoublyNode? = null,
    var next: DoublyNode? = null
)

/**
 * InsertInDoublyLL class demonstrating insertion operations
 */
class InsertInDoublyLL {
    private var head: DoublyNode? = null
    private var tail: DoublyNode? = null
    private var size: Int = 0
    
    /**
     * Insert at the beginning of the list
     * 
     * TIME: O(1) - Constant time with head pointer
     * SPACE: O(1)
     * 
     * ALGORITHM:
     * 1. Create new node
     * 2. Handle empty list case
     * 3. Link new node to current head
     * 4. Update head's prev pointer
     * 5. Update head to new node
     * 
     * @param data Value to insert
     */
    fun insertAtHead(data: Int) {
        val newNode = DoublyNode(data)
        
        // Empty list case
        if (head == null) {
            head = newNode
            tail = newNode
            size++
            return
        }
        
        // Link new node forward
        newNode.next = head
        
        // Link current head backward to new node
        head?.prev = newNode
        
        // Update head
        head = newNode
        size++
    }
    
    /**
     * Insert at the end of the list
     * 
     * TIME: O(1) - With tail pointer
     * SPACE: O(1)
     * 
     * ALGORITHM:
     * 1. Create new node
     * 2. Handle empty list case
     * 3. Link new node to current tail
     * 4. Update tail's next pointer
     * 5. Update tail to new node
     * 
     * @param data Value to insert
     */
    fun insertAtTail(data: Int) {
        val newNode = DoublyNode(data)
        
        // Empty list case
        if (tail == null) {
            head = newNode
            tail = newNode
            size++
            return
        }
        
        // Link new node backward
        newNode.prev = tail
        
        // Link current tail forward to new node
        tail?.next = newNode
        
        // Update tail
        tail = newNode
        size++
    }
    
    /**
     * Insert at specific position (0-indexed)
     * 
     * TIME: O(n) - Must traverse to position
     * SPACE: O(1)
     * 
     * ALGORITHM:
     * 1. Validate position
     * 2. Handle special cases (0, size)
     * 3. Traverse to position
     * 4. Create new node
     * 5. Update four pointers carefully
     * 
     * @param data Value to insert
     * @param position Index where to insert
     * @return true if successful, false if invalid position
     */
    fun insertAtPosition(data: Int, position: Int): Boolean {
        // Validate position
        if (position < 0 || position > size) {
            return false
        }
        
        // Special case: insert at head
        if (position == 0) {
            insertAtHead(data)
            return true
        }
        
        // Special case: insert at tail
        if (position == size) {
            insertAtTail(data)
            return true
        }
        
        // Create new node
        val newNode = DoublyNode(data)
        
        // Traverse to position
        // We want to insert BEFORE the node at 'position'
        var current = head
        for (i in 0 until position) {
            current = current?.next
        }
        
        // Update four pointers (order matters!)
        // 1. New node's prev points to current's prev
        newNode.prev = current?.prev
        
        // 2. New node's next points to current
        newNode.next = current
        
        // 3. Previous node's next points to new node
        current?.prev?.next = newNode
        
        // 4. Current node's prev points to new node
        current?.prev = newNode
        
        size++
        return true
    }
    
    /**
     * Insert before a given node
     * 
     * TIME: O(1) - Have direct reference to node
     * SPACE: O(1)
     * 
     * This is more efficient than singly linked list because
     * we have access to the previous node via prev pointer!
     * 
     * @param node Reference to node to insert before
     * @param data Value to insert
     * @return true if successful, false if node is null
     */
    fun insertBefore(node: DoublyNode?, data: Int): Boolean {
        if (node == null) {
            return false
        }
        
        // Special case: inserting before head
        if (node == head) {
            insertAtHead(data)
            return true
        }
        
        val newNode = DoublyNode(data)
        
        // Update four pointers
        newNode.prev = node.prev
        newNode.next = node
        node.prev?.next = newNode
        node.prev = newNode
        
        size++
        return true
    }
    
    /**
     * Insert after a given node
     * 
     * TIME: O(1) - Have direct reference to node
     * SPACE: O(1)
     * 
     * @param node Reference to node to insert after
     * @param data Value to insert
     * @return true if successful, false if node is null
     */
    fun insertAfter(node: DoublyNode?, data: Int): Boolean {
        if (node == null) {
            return false
        }
        
        // Special case: inserting after tail
        if (node == tail) {
            insertAtTail(data)
            return true
        }
        
        val newNode = DoublyNode(data)
        
        // Update four pointers
        newNode.prev = node
        newNode.next = node.next
        node.next?.prev = newNode
        node.next = newNode
        
        size++
        return true
    }
    
    /**
     * Find node by value (helper for testing)
     */
    fun findNode(data: Int): DoublyNode? {
        var current = head
        while (current != null) {
            if (current.data == data) {
                return current
            }
            current = current.next
        }
        return null
    }
    
    /**
     * Display list forward
     */
    fun displayForward(): String {
        if (head == null) {
            return "List is empty"
        }
        
        val result = StringBuilder()
        var current = head
        
        while (current != null) {
            result.append(current.data)
            if (current.next != null) {
                result.append(" ↔ ")
            }
            current = current.next
        }
        
        return result.toString()
    }
    
    /**
     * Display list backward
     */
    fun displayBackward(): String {
        if (tail == null) {
            return "List is empty"
        }
        
        val result = StringBuilder()
        var current = tail
        
        while (current != null) {
            result.append(current.data)
            if (current.prev != null) {
                result.append(" ↔ ")
            }
            current = current.prev
        }
        
        return result.toString()
    }
    
    fun getSize(): Int = size
    fun isEmpty(): Boolean = head == null
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example: Building a doubly linked list with various insertions
 * 
 * Initial: HEAD → null, TAIL → null, size=0
 * 
 * insertAtHead(10):
 * HEAD/TAIL → [null←|10|→null]
 * size=1
 * 
 * insertAtTail(20):
 * HEAD → [null←|10|↔|20|→null] ← TAIL
 * size=2
 * 
 * insertAtPosition(15, 1):
 * Traverse to position 1 (node 20)
 * Insert [15] before [20]
 * HEAD → [null←|10|↔|15|↔|20|→null] ← TAIL
 * size=3
 * 
 * Find node with value 15, insertAfter(15, 17):
 * HEAD → [null←|10|↔|15|↔|17|↔|20|→null] ← TAIL
 * size=4
 * 
 * Find node with value 10, insertBefore(10, 5):
 * HEAD → [null←|5|↔|10|↔|15|↔|17|↔|20|→null] ← TAIL
 * size=5
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Insert in Doubly Linked List ===\n")
    
    val list = InsertInDoublyLL()
    
    // Test 1: Insert into empty list
    println("Test 1: Insert at head on empty list")
    list.insertAtHead(10)
    println("Forward: ${list.displayForward()}")  // 10
    println("Backward: ${list.displayBackward()}")  // 10
    println("Size: ${list.getSize()}")  // 1
    println()
    
    // Test 2: Multiple inserts at head
    println("Test 2: Multiple inserts at head")
    list.insertAtHead(20)
    list.insertAtHead(30)
    println("Forward: ${list.displayForward()}")  // 30 ↔ 20 ↔ 10
    println("Backward: ${list.displayBackward()}")  // 10 ↔ 20 ↔ 30
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 3: Insert at tail
    println("Test 3: Insert at tail")
    list.insertAtTail(5)
    list.insertAtTail(1)
    println("Forward: ${list.displayForward()}")  // 30 ↔ 20 ↔ 10 ↔ 5 ↔ 1
    println("Backward: ${list.displayBackward()}")  // 1 ↔ 5 ↔ 10 ↔ 20 ↔ 30
    println("Size: ${list.getSize()}")  // 5
    println()
    
    // Test 4: Insert at specific positions
    println("Test 4: Insert at positions")
    val success1 = list.insertAtPosition(25, 1)  // After 30
    println("Insert 25 at position 1: success=$success1")
    println("Forward: ${list.displayForward()}")  // 30 ↔ 25 ↔ 20 ↔ 10 ↔ 5 ↔ 1
    
    val success2 = list.insertAtPosition(15, 3)  // After 20
    println("Insert 15 at position 3: success=$success2")
    println("Forward: ${list.displayForward()}")  // 30 ↔ 25 ↔ 20 ↔ 15 ↔ 10 ↔ 5 ↔ 1
    println("Size: ${list.getSize()}")  // 7
    println()
    
    // Test 5: Insert before a node
    println("Test 5: Insert before a node")
    val node20 = list.findNode(20)
    val success3 = list.insertBefore(node20, 22)
    println("Insert 22 before node with value 20: success=$success3")
    println("Forward: ${list.displayForward()}")  // 30 ↔ 25 ↔ 22 ↔ 20 ↔ 15 ↔ 10 ↔ 5 ↔ 1
    println("Size: ${list.getSize()}")  // 8
    println()
    
    // Test 6: Insert after a node
    println("Test 6: Insert after a node")
    val node10 = list.findNode(10)
    val success4 = list.insertAfter(node10, 7)
    println("Insert 7 after node with value 10: success=$success4")
    println("Forward: ${list.displayForward()}")  // 30 ↔ 25 ↔ 22 ↔ 20 ↔ 15 ↔ 10 ↔ 7 ↔ 5 ↔ 1
    println("Backward: ${list.displayBackward()}")  // 1 ↔ 5 ↔ 7 ↔ 10 ↔ 15 ↔ 20 ↔ 22 ↔ 25 ↔ 30
    println("Size: ${list.getSize()}")  // 9
    println()
    
    // Test 7: Invalid positions
    println("Test 7: Invalid position tests")
    val fail1 = list.insertAtPosition(100, -1)
    println("Insert at position -1: success=$fail1")  // false
    val fail2 = list.insertAtPosition(100, list.getSize() + 1)
    println("Insert at position ${list.getSize() + 1}: success=$fail2")  // false
    println("List unchanged: ${list.displayForward()}")
    println()
    
    // Test 8: Insert at boundaries using position
    println("Test 8: Insert at boundaries")
    list.insertAtPosition(40, 0)  // At head
    println("Insert 40 at position 0: ${list.displayForward()}")
    list.insertAtPosition(0, list.getSize())  // At tail
    println("Insert 0 at end: ${list.displayForward()}")
    println("Size: ${list.getSize()}")  // 11
    println()
    
    // Test 9: Build a new list
    println("Test 9: Build sorted list")
    val list2 = InsertInDoublyLL()
    list2.insertAtTail(10)
    list2.insertAtTail(20)
    list2.insertAtTail(30)
    println("Initial: ${list2.displayForward()}")  // 10 ↔ 20 ↔ 30
    
    val node = list2.findNode(20)
    list2.insertBefore(node, 15)
    list2.insertAfter(node, 25)
    println("After insertions: ${list2.displayForward()}")  // 10 ↔ 15 ↔ 20 ↔ 25 ↔ 30
    println("Backward: ${list2.displayBackward()}")  // 30 ↔ 25 ↔ 20 ↔ 15 ↔ 10
    println()
    
    // Test 10: Single element operations
    println("Test 10: Single element operations")
    val list3 = InsertInDoublyLL()
    list3.insertAtTail(50)
    println("Single element: ${list3.displayForward()}")  // 50
    val singleNode = list3.findNode(50)
    list3.insertBefore(singleNode, 40)
    list3.insertAfter(singleNode, 60)
    println("After insertions: ${list3.displayForward()}")  // 40 ↔ 50 ↔ 60
    println("Backward: ${list3.displayBackward()}")  // 60 ↔ 50 ↔ 40
}

