/**
 * ============================================================================
 * DOUBLY LINKED LIST: Introduction and Implementation
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Doubly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement a doubly linked list data structure with basic operations:
 * - Insert at head, tail, and specific position
 * - Delete from head, tail, and specific position
 * - Search for a value
 * - Display forward and backward
 * - Get size
 * 
 * DOUBLY LINKED LIST STRUCTURE:
 * A doubly linked list is a linear data structure where each node contains:
 * 1. Data (the value)
 * 2. Next pointer (reference to next node)
 * 3. Previous pointer (reference to previous node)
 * 
 * The bidirectional links allow traversal in both directions.
 * 
 * ============================================================================
 * CONCEPT & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of a doubly linked list like a train where each car (node) knows:
 * - Which car comes AFTER it (next pointer)
 * - Which car comes BEFORE it (prev pointer)
 * 
 * This bidirectional knowledge makes operations more flexible than singly
 * linked lists, though it requires more memory (extra pointer per node).
 * 
 * VISUAL REPRESENTATION:
 * 
 *        ┌────────────────────────────────────────┐
 *        │                                        │
 *  null←[←|1|→]↔[←|2|→]↔[←|3|→]↔[←|4|→]→null   │
 *   HEAD→  ↑                         ↑           │
 *          │                         └───────TAIL┘
 * 
 * Each node has three parts:
 * - prev: pointer to previous node
 * - data: the value stored
 * - next: pointer to next node
 * 
 * COMPARISON WITH SINGLY LINKED LIST:
 * 
 * Singly:  HEAD → [1|→] → [2|→] → [3|null]
 *                 (one direction only)
 * 
 * Doubly:  HEAD ↔ [←|1|→] ↔ [←|2|→] ↔ [←|3|→] ↔ TAIL
 *                 (both directions)
 * 
 * ADVANTAGES OVER SINGLY LINKED LIST:
 * ✅ Can traverse in both directions
 * ✅ Delete node in O(1) if we have reference to it
 * ✅ Insert before a given node easily
 * ✅ Access previous element without storing it
 * ✅ Better for implementing deque, LRU cache
 * 
 * DISADVANTAGES:
 * ❌ Extra memory for prev pointer (double pointer overhead)
 * ❌ More complex insertion/deletion (update 4 pointers vs 2)
 * ❌ Slightly slower operations due to extra pointer updates
 * 
 * ADVANTAGES OVER ARRAYS:
 * ✅ Dynamic size (no reallocation needed)
 * ✅ Efficient insertion/deletion at both ends: O(1)
 * ✅ Efficient insertion/deletion in middle: O(1) with node reference
 * ✅ No wasted memory from over-allocation
 * 
 * DISADVANTAGES VS ARRAYS:
 * ❌ No random access (must traverse)
 * ❌ Extra memory for two pointers per node
 * ❌ Not cache-friendly (nodes scattered in memory)
 * ❌ More complex to implement
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * OPERATION              | TIME         | SPACE
 * -----------------------|--------------|--------
 * Insert at head         | O(1)         | O(1)
 * Insert at tail         | O(1)*        | O(1)
 * Insert at position     | O(n)         | O(1)
 * Delete from head       | O(1)         | O(1)
 * Delete from tail       | O(1)*        | O(1)
 * Delete at position     | O(n)         | O(1)
 * Search                 | O(n)         | O(1)
 * Access by index        | O(n)         | O(1)
 * Display forward        | O(n)         | O(1)
 * Display backward       | O(n)         | O(1)
 * 
 * * With tail pointer: O(1), Without: O(n)
 * 
 * KEY IMPROVEMENTS OVER SINGLY LINKED LIST:
 * 1. Delete from tail: O(1) vs O(n) - don't need to find previous node
 * 2. Can traverse backward efficiently
 * 3. Delete node with reference: O(1) - have access to previous
 * 
 * ============================================================================
 */

package linkedlist.doubly

/**
 * DoublyNode class representing a node in the doubly linked list
 * 
 * @param data The value stored in this node
 * @param prev Reference to the previous node (null if this is the first node)
 * @param next Reference to the next node (null if this is the last node)
 */
data class DoublyNode(
    var data: Int,
    var prev: DoublyNode? = null,
    var next: DoublyNode? = null
)

/**
 * Doubly Linked List implementation with comprehensive operations
 */
class DoublyLinkedList {
    // Head points to the first node, null if list is empty
    private var head: DoublyNode? = null
    
    // Tail points to the last node, null if list is empty
    // Maintained for O(1) operations at the end
    private var tail: DoublyNode? = null
    
    // Size tracks the number of nodes
    private var size: Int = 0
    
    /**
     * Insert a new node at the beginning of the list
     * TIME: O(1) - Just update head and links
     * 
     * @param data Value to insert
     */
    fun insertAtHead(data: Int) {
        // Create new node
        val newNode = DoublyNode(data)
        
        // Handle empty list case
        if (head == null) {
            head = newNode
            tail = newNode
            size++
            return
        }
        
        // Link new node to current head
        newNode.next = head
        head?.prev = newNode
        
        // Update head
        head = newNode
        size++
    }
    
    /**
     * Insert a new node at the end of the list
     * TIME: O(1) - With tail pointer
     * 
     * @param data Value to insert
     */
    fun insertAtTail(data: Int) {
        // Create new node
        val newNode = DoublyNode(data)
        
        // Handle empty list case
        if (tail == null) {
            head = newNode
            tail = newNode
            size++
            return
        }
        
        // Link new node to current tail
        newNode.prev = tail
        tail?.next = newNode
        
        // Update tail
        tail = newNode
        size++
    }
    
    /**
     * Insert at specific position (0-indexed)
     * TIME: O(n) - Must traverse to position
     * 
     * @param data Value to insert
     * @param position Index where to insert (0 = head)
     * @return true if inserted, false if invalid position
     */
    fun insertAtPosition(data: Int, position: Int): Boolean {
        // Validation
        if (position < 0 || position > size) {
            return false
        }
        
        // Special cases
        if (position == 0) {
            insertAtHead(data)
            return true
        }
        
        if (position == size) {
            insertAtTail(data)
            return true
        }
        
        // Create new node
        val newNode = DoublyNode(data)
        
        // Traverse to position
        var current = head
        for (i in 0 until position) {
            current = current?.next
        }
        
        // Insert node before current
        newNode.prev = current?.prev
        newNode.next = current
        current?.prev?.next = newNode
        current?.prev = newNode
        
        size++
        return true
    }
    
    /**
     * Delete the first node
     * TIME: O(1)
     * 
     * @return Deleted value, or null if list empty
     */
    fun deleteFromHead(): Int? {
        if (head == null) {
            return null
        }
        
        val deletedValue = head?.data
        
        // Single node case
        if (head == tail) {
            head = null
            tail = null
            size--
            return deletedValue
        }
        
        // Move head forward
        head = head?.next
        head?.prev = null
        
        size--
        return deletedValue
    }
    
    /**
     * Delete the last node
     * TIME: O(1) - With tail pointer
     * 
     * @return Deleted value, or null if list empty
     */
    fun deleteFromTail(): Int? {
        if (tail == null) {
            return null
        }
        
        val deletedValue = tail?.data
        
        // Single node case
        if (head == tail) {
            head = null
            tail = null
            size--
            return deletedValue
        }
        
        // Move tail backward
        tail = tail?.prev
        tail?.next = null
        
        size--
        return deletedValue
    }
    
    /**
     * Delete at specific position
     * TIME: O(n)
     * 
     * @param position Index to delete from
     * @return Deleted value, or null if invalid
     */
    fun deleteAtPosition(position: Int): Int? {
        if (position < 0 || position >= size) {
            return null
        }
        
        if (position == 0) {
            return deleteFromHead()
        }
        
        if (position == size - 1) {
            return deleteFromTail()
        }
        
        // Traverse to position
        var current = head
        for (i in 0 until position) {
            current = current?.next
        }
        
        val deletedValue = current?.data
        
        // Remove node from chain
        current?.prev?.next = current?.next
        current?.next?.prev = current?.prev
        
        size--
        return deletedValue
    }
    
    /**
     * Search for a value
     * TIME: O(n)
     * 
     * @param data Value to search
     * @return Index if found, -1 if not found
     */
    fun search(data: Int): Int {
        var current = head
        var index = 0
        
        while (current != null) {
            if (current.data == data) {
                return index
            }
            current = current.next
            index++
        }
        
        return -1
    }
    
    /**
     * Display the list forward (head to tail)
     * TIME: O(n)
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
     * Display the list backward (tail to head)
     * TIME: O(n)
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
    
    /**
     * Get the size of the list
     * TIME: O(1)
     */
    fun getSize(): Int = size
    
    /**
     * Check if list is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean = head == null
    
    /**
     * Clear the entire list
     * TIME: O(1)
     */
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
 * Building a doubly linked list:
 * 
 * Initial: HEAD → null, TAIL → null
 * 
 * After insertAtHead(10):
 * HEAD/TAIL → [null←|10|→null]
 * 
 * After insertAtHead(20):
 * HEAD → [null←|20|↔|10|→null] ← TAIL
 * 
 * After insertAtTail(5):
 * HEAD → [null←|20|↔|10|↔|5|→null] ← TAIL
 * 
 * After deleteFromHead():
 * HEAD → [null←|10|↔|5|→null] ← TAIL
 * 
 * After deleteFromTail():
 * HEAD/TAIL → [null←|10|→null]
 * 
 * ============================================================================
 */

fun main() {
    println("=== Doubly Linked List Implementation ===\n")
    
    val list = DoublyLinkedList()
    
    // Test 1: Empty list
    println("Test 1: Empty list operations")
    println("Display forward: ${list.displayForward()}")
    println("Display backward: ${list.displayBackward()}")
    println("Size: ${list.getSize()}")
    println()
    
    // Test 2: Insert at head
    println("Test 2: Insert at head")
    list.insertAtHead(10)
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
    
    // Test 4: Insert at position
    println("Test 4: Insert at position")
    list.insertAtPosition(15, 2)
    println("Forward: ${list.displayForward()}")  // 30 ↔ 20 ↔ 15 ↔ 10 ↔ 5 ↔ 1
    println("Size: ${list.getSize()}")  // 6
    println()
    
    // Test 5: Delete from head
    println("Test 5: Delete from head")
    val deleted1 = list.deleteFromHead()
    println("Deleted: $deleted1")  // 30
    println("Forward: ${list.displayForward()}")  // 20 ↔ 15 ↔ 10 ↔ 5 ↔ 1
    println()
    
    // Test 6: Delete from tail
    println("Test 6: Delete from tail")
    val deleted2 = list.deleteFromTail()
    println("Deleted: $deleted2")  // 1
    println("Forward: ${list.displayForward()}")  // 20 ↔ 15 ↔ 10 ↔ 5
    println()
    
    // Test 7: Delete at position
    println("Test 7: Delete at position")
    val deleted3 = list.deleteAtPosition(1)
    println("Deleted: $deleted3")  // 15
    println("Forward: ${list.displayForward()}")  // 20 ↔ 10 ↔ 5
    println()
    
    // Test 8: Search
    println("Test 8: Search")
    println("Search 10: ${list.search(10)}")  // 1
    println("Search 20: ${list.search(20)}")  // 0
    println("Search 100: ${list.search(100)}")  // -1
    println()
    
    // Test 9: Clear
    println("Test 9: Clear list")
    list.clear()
    println("Forward: ${list.displayForward()}")  // List is empty
    println("Size: ${list.getSize()}")  // 0
    println()
    
    // Test 10: Build new list
    println("Test 10: Build and display")
    list.insertAtTail(1)
    list.insertAtTail(2)
    list.insertAtTail(3)
    list.insertAtTail(4)
    list.insertAtTail(5)
    println("Forward: ${list.displayForward()}")  // 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5
    println("Backward: ${list.displayBackward()}")  // 5 ↔ 4 ↔ 3 ↔ 2 ↔ 1
    println("Size: ${list.getSize()}")  // 5
}

