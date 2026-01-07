/**
 * ============================================================================
 * PROBLEM: Insert Node in Singly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Singly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement various insertion operations in a singly linked list:
 * 1. Insert at the beginning (head)
 * 2. Insert at the end (tail)
 * 3. Insert at a specific position
 * 
 * A singly linked list is a linear data structure where each node contains:
 * - Data: the value stored in the node
 * - Next: reference to the next node (or null if it's the last node)
 * 
 * INPUT FORMAT:
 * - For insertAtHead: data value to insert
 * - For insertAtTail: data value to insert
 * - For insertAtPosition: data value and position (0-indexed)
 * 
 * OUTPUT FORMAT:
 * - Updated linked list with the new node inserted
 * - For insertAtPosition: boolean indicating success/failure
 * 
 * CONSTRAINTS:
 * - Data can be any integer value
 * - Position must be valid (0 <= position <= size)
 * - List can be empty initially
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of a linked list like a chain of train cars. Each car (node) knows
 * which car comes after it. When we insert a new car:
 * - At head: Add new car at the front, connect it to the first car
 * - At tail: Find the last car, connect it to the new car
 * - At position: Find the car before insertion point, reroute connections
 * 
 * INSERT AT HEAD - THREE STEPS:
 * 1. Create new node
 * 2. Point new node's next to current head
 * 3. Update head to point to new node
 * 
 * VISUAL EXAMPLE (Insert 5 at head):
 * Before:  HEAD → [10|→] → [20|null]
 * 
 * Step 1: Create [5|?]
 * Step 2: [5|→] points to [10|→]
 * Step 3: HEAD → [5|→] → [10|→] → [20|null]
 * 
 * INSERT AT TAIL - TWO CASES:
 * Case 1: Empty list → Same as insert at head
 * Case 2: Non-empty → Traverse to last node, link it to new node
 * 
 * VISUAL EXAMPLE (Insert 30 at tail):
 * Before:  HEAD → [10|→] → [20|null]
 * 
 * Traverse: current moves to [20|null] (last node)
 * Link:     [20|→] → [30|null]
 * After:    HEAD → [10|→] → [20|→] → [30|null]
 * 
 * INSERT AT POSITION - FOUR STEPS:
 * 1. Validate position (0 <= pos <= size)
 * 2. Handle special case (position 0 = insert at head)
 * 3. Traverse to node before insertion point
 * 4. Reroute pointers: new node points to current's next, current points to new node
 * 
 * VISUAL EXAMPLE (Insert 15 at position 1):
 * Before:  HEAD → [10|→] → [20|→] → [30|null]
 *                  pos 0    pos 1    pos 2
 * 
 * Want to insert at position 1:
 * Step 1: Traverse to position 0 (node [10|→])
 * Step 2: Create [15|?]
 * Step 3: [15|→] points to [20|→] (current's next)
 * Step 4: [10|→] points to [15|→]
 * After:  HEAD → [10|→] → [15|→] → [20|→] → [30|null]
 *                  pos 0    pos 1    pos 2    pos 3
 * 
 * EDGE CASES:
 * 1. Empty list: All operations should handle this
 * 2. Single node: Ensure pointers are updated correctly
 * 3. Insert at position 0: Same as insert at head
 * 4. Insert at position = size: Same as insert at tail
 * 5. Invalid position: Return false, don't crash
 * 
 * ALTERNATIVE APPROACHES:
 * 1. With tail pointer: O(1) insertAtTail - maintain tail reference
 * 2. Without tail pointer: O(n) insertAtTail - traverse to find last node
 * 3. Recursive insertion: More elegant but uses call stack
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * INSERT AT HEAD:
 * TIME COMPLEXITY: O(1)
 * - Create new node: O(1)
 * - Update two pointers: O(1)
 * - No traversal needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only one new node created
 * - No additional data structures
 * 
 * INSERT AT TAIL (without tail pointer):
 * TIME COMPLEXITY: O(n)
 * - Must traverse entire list to find last node
 * - n is the number of nodes in the list
 * - Create and link node: O(1)
 * - Total: O(n) + O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only one new node created
 * - Traversal uses one variable
 * 
 * INSERT AT POSITION:
 * TIME COMPLEXITY: O(n)
 * - Worst case: insert at end, traverse n nodes
 * - Average case: traverse n/2 nodes
 * - Best case: insert at head, O(1)
 * - Overall: O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only one new node created
 * - Traversal uses constant extra space
 * 
 * ============================================================================
 */

package linkedlist.singly

/**
 * Node class representing a single node in the singly linked list
 * 
 * @param data The integer value stored in this node
 * @param next Reference to the next node in the list (null if this is the last node)
 */
data class Node(
    var data: Int,
    var next: Node? = null
)

/**
 * InsertNode class demonstrating various insertion operations in a singly linked list
 */
class InsertNode {
    // Head pointer to the first node in the list
    // null indicates an empty list
    private var head: Node? = null
    
    // Size counter to track the number of nodes
    // Maintained for O(1) size queries and position validation
    private var size: Int = 0
    
    /**
     * Insert a new node at the beginning of the list
     * 
     * TIME: O(1) - Constant time operation, no traversal needed
     * SPACE: O(1) - Only creates one new node
     * 
     * ALGORITHM:
     * 1. Create new node with given data
     * 2. Point new node's next to current head (even if null)
     * 3. Update head to point to new node
     * 4. Increment size counter
     * 
     * @param data The value to insert at the head
     */
    fun insertAtHead(data: Int) {
        // Step 1: Create new node with the given data
        // By default, next is null
        val newNode = Node(data)
        
        // Step 2: Link new node to the current head
        // If list is empty (head = null), this correctly sets next to null
        // If list has nodes, this links new node to the first existing node
        newNode.next = head
        
        // Step 3: Update head to point to the new node
        // This makes the new node the first node in the list
        head = newNode
        
        // Step 4: Increment size as we added a node
        size++
    }
    
    /**
     * Insert a new node at the end of the list
     * 
     * TIME: O(n) - Must traverse to the last node
     * SPACE: O(1) - Only creates one new node
     * 
     * ALGORITHM:
     * 1. Create new node with given data
     * 2. If list is empty, set head to new node
     * 3. Otherwise, traverse to the last node
     * 4. Link last node's next to the new node
     * 5. Increment size counter
     * 
     * @param data The value to insert at the tail
     */
    fun insertAtTail(data: Int) {
        // Step 1: Create new node
        val newNode = Node(data)
        
        // Step 2: Handle empty list case
        // If list is empty, new node becomes the head
        if (head == null) {
            head = newNode
            size++
            return
        }
        
        // Step 3: Traverse to find the last node
        // Start from head and keep moving until we find a node whose next is null
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        
        // Step 4: Link the last node to the new node
        // current now points to the last node
        // Make its next point to our new node
        current?.next = newNode
        
        // Step 5: Increment size
        size++
    }
    
    /**
     * Insert a new node at a specific position (0-indexed)
     * 
     * TIME: O(n) - May need to traverse up to n nodes
     * SPACE: O(1) - Only creates one new node
     * 
     * ALGORITHM:
     * 1. Validate position (must be 0 <= position <= size)
     * 2. Handle special case: position 0 (insert at head)
     * 3. Traverse to node at position-1 (node before insertion point)
     * 4. Create new node
     * 5. Link new node to current's next
     * 6. Link current to new node
     * 7. Increment size counter
     * 
     * @param data The value to insert
     * @param position The 0-indexed position where to insert
     * @return true if insertion successful, false if invalid position
     */
    fun insertAtPosition(data: Int, position: Int): Boolean {
        // Step 1: Validate position
        // Must be non-negative and not exceed current size
        // Position can equal size (insert at end)
        if (position < 0 || position > size) {
            return false
        }
        
        // Step 2: Handle special case - insert at head
        if (position == 0) {
            insertAtHead(data)
            return true
        }
        
        // Step 3: Traverse to the node before insertion point
        // We need to reach position-1 to insert at position
        var current = head
        for (i in 0 until position - 1) {
            current = current?.next
        }
        
        // Step 4: Create new node
        val newNode = Node(data)
        
        // Step 5 & 6: Reroute pointers
        // First, new node points to what current points to (order matters!)
        newNode.next = current?.next
        // Then, current points to new node
        current?.next = newNode
        
        // Step 7: Increment size
        size++
        return true
    }
    
    /**
     * Display the linked list in a readable format
     * 
     * TIME: O(n) - Must visit each node
     * SPACE: O(n) - String builder accumulates all node values
     * 
     * @return String representation of the list
     */
    fun display(): String {
        // Handle empty list
        if (head == null) {
            return "List is empty"
        }
        
        // Build string representation
        val result = StringBuilder()
        var current = head
        
        while (current != null) {
            result.append(current.data)
            // Add arrow if there's a next node
            if (current.next != null) {
                result.append(" → ")
            }
            current = current.next
        }
        
        return result.toString()
    }
    
    /**
     * Get the current size of the list
     * 
     * TIME: O(1) - We maintain a size counter
     * 
     * @return Number of nodes in the list
     */
    fun getSize(): Int = size
    
    /**
     * Check if the list is empty
     * 
     * TIME: O(1)
     * 
     * @return true if list is empty, false otherwise
     */
    fun isEmpty(): Boolean = head == null
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's build a list using different insertion operations:
 * 
 * Initial state:
 * HEAD → null (empty list)
 * size = 0
 * 
 * Operation: insertAtHead(10)
 * HEAD → [10|null]
 * size = 1
 * 
 * Operation: insertAtHead(5)
 * HEAD → [5|→] → [10|null]
 * size = 2
 * 
 * Operation: insertAtTail(20)
 * Traverse to [10|null] (last node)
 * HEAD → [5|→] → [10|→] → [20|null]
 * size = 3
 * 
 * Operation: insertAtPosition(15, 2)
 * Traverse to position 1 (node with value 10)
 * Create [15|?]
 * Link: [15|→] points to [20|null]
 * Link: [10|→] points to [15|→]
 * HEAD → [5|→] → [10|→] → [15|→] → [20|null]
 *         pos 0    pos 1    pos 2    pos 3
 * size = 4
 * 
 * Operation: insertAtPosition(3, 0)
 * Special case: position 0 → insertAtHead(3)
 * HEAD → [3|→] → [5|→] → [10|→] → [15|→] → [20|null]
 * size = 5
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Insert Node in Singly Linked List ===\n")
    
    val list = InsertNode()
    
    // Test 1: Insert into empty list
    println("Test 1: Insert into empty list")
    println("Initial state: ${list.display()}")  // List is empty
    println("Is empty: ${list.isEmpty()}")  // true
    println("Size: ${list.getSize()}")  // 0
    println()
    
    // Test 2: Insert at head on empty list
    println("Test 2: Insert at head on empty list")
    list.insertAtHead(10)
    println("After insertAtHead(10): ${list.display()}")  // 10
    println("Size: ${list.getSize()}")  // 1
    println()
    
    // Test 3: Multiple inserts at head
    println("Test 3: Multiple inserts at head")
    list.insertAtHead(20)
    list.insertAtHead(30)
    println("After insertAtHead(20, 30): ${list.display()}")  // 30 → 20 → 10
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 4: Insert at tail
    println("Test 4: Insert at tail")
    list.insertAtTail(5)
    list.insertAtTail(1)
    println("After insertAtTail(5, 1): ${list.display()}")  // 30 → 20 → 10 → 5 → 1
    println("Size: ${list.getSize()}")  // 5
    println()
    
    // Test 5: Insert at specific positions
    println("Test 5: Insert at specific positions")
    val success1 = list.insertAtPosition(25, 1)  // Insert 25 at position 1
    println("Insert 25 at position 1: success=$success1")
    println("After insertion: ${list.display()}")  // 30 → 25 → 20 → 10 → 5 → 1
    println("Size: ${list.getSize()}")  // 6
    println()
    
    // Test 6: Insert at position 0 (should behave like insertAtHead)
    println("Test 6: Insert at position 0")
    val success2 = list.insertAtPosition(40, 0)
    println("Insert 40 at position 0: success=$success2")
    println("After insertion: ${list.display()}")  // 40 → 30 → 25 → 20 → 10 → 5 → 1
    println("Size: ${list.getSize()}")  // 7
    println()
    
    // Test 7: Insert at end using position (position = size)
    println("Test 7: Insert at end using position")
    val success3 = list.insertAtPosition(0, list.getSize())
    println("Insert 0 at position ${list.getSize() - 1}: success=$success3")
    println("After insertion: ${list.display()}")  // 40 → 30 → 25 → 20 → 10 → 5 → 1 → 0
    println("Size: ${list.getSize()}")  // 8
    println()
    
    // Test 8: Invalid position tests
    println("Test 8: Invalid position tests")
    val fail1 = list.insertAtPosition(100, -1)  // Negative position
    println("Insert at position -1: success=$fail1")  // false
    val fail2 = list.insertAtPosition(100, list.getSize() + 1)  // Position > size
    println("Insert at position ${list.getSize() + 1}: success=$fail2")  // false
    println("List unchanged: ${list.display()}")
    println("Size: ${list.getSize()}")  // 8
    println()
    
    // Test 9: Build a new list with mixed operations
    println("Test 9: Build a new list with mixed operations")
    val list2 = InsertNode()
    list2.insertAtHead(50)
    list2.insertAtTail(100)
    list2.insertAtHead(25)
    list2.insertAtTail(150)
    list2.insertAtPosition(75, 2)  // Middle insertion
    println("Built list: ${list2.display()}")  // 25 → 50 → 75 → 100 → 150
    println("Size: ${list2.getSize()}")  // 5
    println()
    
    // Test 10: Single element operations
    println("Test 10: Single element operations")
    val list3 = InsertNode()
    list3.insertAtTail(99)
    println("Single element via tail: ${list3.display()}")  // 99
    list3.insertAtPosition(88, 0)
    println("Insert at position 0: ${list3.display()}")  // 88 → 99
    list3.insertAtPosition(77, 2)
    println("Insert at position 2: ${list3.display()}")  // 88 → 99 → 77
    println("Final size: ${list3.getSize()}")  // 3
}
