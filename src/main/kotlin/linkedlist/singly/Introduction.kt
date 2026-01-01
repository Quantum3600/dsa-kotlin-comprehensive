/**
 * ============================================================================
 * LINKED LIST: Introduction and Implementation
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Singly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement a singly linked list data structure with basic operations:
 * - Insert at head, tail, and specific position
 * - Delete from head, tail, and specific position
 * - Search for a value
 * - Display the list
 * - Get size
 * 
 * LINKED LIST STRUCTURE:
 * A linked list is a linear data structure where elements are stored in nodes.
 * Each node contains:
 * 1. Data (the value)
 * 2. Next pointer (reference to next node)
 * 
 * Unlike arrays, linked lists don't store elements in contiguous memory.
 * 
 * ============================================================================
 * CONCEPT & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of a treasure hunt where each clue leads you to the next location.
 * Each location (node) has:
 * - A treasure (data)
 * - A clue to the next location (next pointer)
 * 
 * The first location is the "head", and the last location's clue says "END" (null).
 * 
 * VISUAL REPRESENTATION:
 * 
 * HEAD → [1|→] → [2|→] → [3|→] → [4|null]
 *         Node1   Node2   Node3   Node4
 * 
 * Each box contains:
 * - Left part: data value
 * - Right part: pointer to next node
 * 
 * ADVANTAGES OVER ARRAYS:
 * ✅ Dynamic size (grows/shrinks as needed)
 * ✅ Efficient insertion/deletion at beginning: O(1)
 * ✅ No memory wastage (allocates exactly what's needed)
 * ✅ Easy to insert/delete in middle (just update pointers)
 * 
 * DISADVANTAGES:
 * ❌ No random access (must traverse from head)
 * ❌ Extra memory for storing pointers
 * ❌ Not cache-friendly (nodes scattered in memory)
 * ❌ Accessing element is O(n) vs O(1) for arrays
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * OPERATION          | TIME COMPLEXITY | SPACE COMPLEXITY
 * -------------------|-----------------|------------------
 * Insert at head     | O(1)           | O(1)
 * Insert at tail     | O(n)*          | O(1)
 * Insert at position | O(n)           | O(1)
 * Delete from head   | O(1)           | O(1)
 * Delete from tail   | O(n)           | O(1)
 * Delete at position | O(n)           | O(1)
 * Search             | O(n)           | O(1)
 * Access by index    | O(n)           | O(1)
 * Display            | O(n)           | O(1)
 * 
 * * With tail pointer: O(1), Without: O(n)
 * 
 * WHY O(n) FOR MIDDLE OPERATIONS:
 * - Must traverse from head to reach position
 * - No direct access like arrays
 * - Each step follows the 'next' pointer
 * 
 * ============================================================================
 */

package linkedlist.singly

/**
 * Node class representing a single node in the linked list
 * 
 * @param data The value stored in the node
 * @param next Reference to the next node (null if this is the last node)
 */
data class Node(
    var data: Int,
    var next: Node? = null
)

/**
 * Singly Linked List implementation with comprehensive operations
 */
class LinkedList {
    // Head points to the first node, null if list is empty
    private var head: Node? = null
    
    // Size tracks the number of nodes for O(1) size query
    private var size: Int = 0
    
    /**
     * Insert a new node at the beginning of the list
     * TIME: O(1) - Just update head pointer
     * 
     * @param data Value to insert
     */
    fun insertAtHead(data: Int) {
        // Create new node
        val newNode = Node(data)
        
        // New node's next points to current head
        // If list is empty, this sets next to null (correct)
        newNode.next = head
        
        // Update head to point to new node
        head = newNode
        
        // Increment size
        size++
    }
    
    /**
     * Insert a new node at the end of the list
     * TIME: O(n) - Must traverse to find last node
     * 
     * @param data Value to insert
     */
    fun insertAtTail(data: Int) {
        // Create new node
        val newNode = Node(data)
        
        // Special case: Empty list
        if (head == null) {
            head = newNode
            size++
            return
        }
        
        // Traverse to find last node
        // 'current' will eventually point to the last node
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        
        // Link last node to new node
        current?.next = newNode
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
        
        // Special case: Insert at head
        if (position == 0) {
            insertAtHead(data)
            return true
        }
        
        // Create new node
        val newNode = Node(data)
        
        // Traverse to position-1 (node before insertion point)
        var current = head
        for (i in 0 until position - 1) {
            current = current?.next
        }
        
        // Insert new node
        // 1. New node points to current's next
        newNode.next = current?.next
        // 2. Current node points to new node
        current?.next = newNode
        
        size++
        return true
    }
    
    /**
     * Delete the first node
     * TIME: O(1) - Just update head
     * 
     * @return Deleted value, or null if list empty
     */
    fun deleteFromHead(): Int? {
        // Check if list is empty
        if (head == null) {
            return null
        }
        
        // Store value to return
        val deletedValue = head?.data
        
        // Move head to next node
        // If there's only one node, this sets head to null (correct)
        head = head?.next
        
        size--
        return deletedValue
    }
    
    /**
     * Delete the last node
     * TIME: O(n) - Must traverse to second-last node
     * 
     * @return Deleted value, or null if list empty
     */
    fun deleteFromTail(): Int? {
        // Empty list
        if (head == null) {
            return null
        }
        
        // Single node
        if (head?.next == null) {
            val deletedValue = head?.data
            head = null
            size--
            return deletedValue
        }
        
        // Find second-last node
        var current = head
        while (current?.next?.next != null) {
            current = current.next
        }
        
        // Store value of last node
        val deletedValue = current?.next?.data
        
        // Remove last node
        current?.next = null
        
        size--
        return deletedValue
    }
    
    /**
     * Search for a value in the list
     * TIME: O(n) - May need to check all nodes
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
        
        return -1  // Not found
    }
    
    /**
     * Get value at specific index
     * TIME: O(n) - Must traverse to index
     * 
     * @param index Position to get value from
     * @return Value at index, or null if invalid index
     */
    fun get(index: Int): Int? {
        if (index < 0 || index >= size) {
            return null
        }
        
        var current = head
        for (i in 0 until index) {
            current = current?.next
        }
        
        return current?.data
    }
    
    /**
     * Display the linked list
     * TIME: O(n) - Visit each node once
     */
    fun display(): String {
        if (head == null) {
            return "List is empty"
        }
        
        val result = StringBuilder()
        var current = head
        
        while (current != null) {
            result.append(current.data)
            if (current.next != null) {
                result.append(" → ")
            }
            current = current.next
        }
        
        return result.toString()
    }
    
    /**
     * Get the size of the list
     * TIME: O(1) - We maintain size variable
     */
    fun getSize(): Int = size
    
    /**
     * Check if list is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean = head == null
    
    /**
     * Clear the entire list
     * TIME: O(1) - Just set head to null, garbage collector handles rest
     */
    fun clear() {
        head = null
        size = 0
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's build a list step by step:
 * 
 * Initial: HEAD → null (empty list)
 * 
 * After insertAtHead(10):
 * HEAD → [10|null]
 * 
 * After insertAtHead(20):
 * HEAD → [20|→] → [10|null]
 * 
 * After insertAtTail(30):
 * HEAD → [20|→] → [10|→] → [30|null]
 * 
 * After insertAtPosition(15, 2):
 * HEAD → [20|→] → [10|→] → [15|→] → [30|null]
 *                          (inserted here)
 * 
 * After deleteFromHead():
 * HEAD → [10|→] → [15|→] → [30|null]
 * (20 removed)
 * 
 * After deleteFromTail():
 * HEAD → [10|→] → [15|null]
 * (30 removed)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Singly Linked List Implementation ===\n")
    
    val list = LinkedList()
    
    // Test 1: Empty list operations
    println("Test 1: Operations on empty list")
    println("Is empty: ${list.isEmpty()}")  // true
    println("Size: ${list.getSize()}")  // 0
    println("Display: ${list.display()}")  // List is empty
    println("Delete from head: ${list.deleteFromHead()}")  // null
    println()
    
    // Test 2: Insert at head
    println("Test 2: Insert at head")
    list.insertAtHead(10)
    list.insertAtHead(20)
    list.insertAtHead(30)
    println("After inserting 10, 20, 30 at head:")
    println("Display: ${list.display()}")  // 30 → 20 → 10
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 3: Insert at tail
    println("Test 3: Insert at tail")
    list.insertAtTail(40)
    list.insertAtTail(50)
    println("After inserting 40, 50 at tail:")
    println("Display: ${list.display()}")  // 30 → 20 → 10 → 40 → 50
    println("Size: ${list.getSize()}")  // 5
    println()
    
    // Test 4: Insert at position
    println("Test 4: Insert at position")
    list.insertAtPosition(25, 2)  // Insert 25 at index 2
    println("After inserting 25 at position 2:")
    println("Display: ${list.display()}")  // 30 → 20 → 25 → 10 → 40 → 50
    println("Size: ${list.getSize()}")  // 6
    println()
    
    // Test 5: Search
    println("Test 5: Search for elements")
    println("Search for 25: index ${list.search(25)}")  // 2
    println("Search for 50: index ${list.search(50)}")  // 5
    println("Search for 100: index ${list.search(100)}")  // -1 (not found)
    println()
    
    // Test 6: Get by index
    println("Test 6: Get element by index")
    println("Element at index 0: ${list.get(0)}")  // 30
    println("Element at index 3: ${list.get(3)}")  // 10
    println("Element at index 10: ${list.get(10)}")  // null (invalid)
    println()
    
    // Test 7: Delete from head
    println("Test 7: Delete from head")
    val deleted1 = list.deleteFromHead()
    println("Deleted: $deleted1")  // 30
    println("Display: ${list.display()}")  // 20 → 25 → 10 → 40 → 50
    println("Size: ${list.getSize()}")  // 5
    println()
    
    // Test 8: Delete from tail
    println("Test 8: Delete from tail")
    val deleted2 = list.deleteFromTail()
    println("Deleted: $deleted2")  // 50
    println("Display: ${list.display()}")  // 20 → 25 → 10 → 40
    println("Size: ${list.getSize()}")  // 4
    println()
    
    // Test 9: Clear list
    println("Test 9: Clear the list")
    list.clear()
    println("After clear:")
    println("Display: ${list.display()}")  // List is empty
    println("Size: ${list.getSize()}")  // 0
    println("Is empty: ${list.isEmpty()}")  // true
    println()
    
    // Test 10: Build and display
    println("Test 10: Build a new list")
    list.insertAtTail(1)
    list.insertAtTail(2)
    list.insertAtTail(3)
    list.insertAtTail(4)
    list.insertAtTail(5)
    println("List of 1 to 5:")
    println("Display: ${list.display()}")  // 1 → 2 → 3 → 4 → 5
    println("Size: ${list.getSize()}")  // 5
}
