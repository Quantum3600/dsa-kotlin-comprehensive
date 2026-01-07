/**
 * ============================================================================
 * PROBLEM: Delete Node in Singly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Singly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement various deletion operations in a singly linked list:
 * 1. Delete from the beginning (head)
 * 2. Delete from the end (tail)
 * 3. Delete from a specific position
 * 4. Delete a node with a specific value
 * 
 * Each operation should handle edge cases like empty lists, single node lists,
 * and invalid positions/values gracefully.
 * 
 * INPUT FORMAT:
 * - For deleteFromHead: no parameters
 * - For deleteFromTail: no parameters
 * - For deleteAtPosition: position (0-indexed)
 * - For deleteByValue: value to delete
 * 
 * OUTPUT FORMAT:
 * - Deleted value (or null if list is empty/invalid operation)
 * - Updated linked list with the node removed
 * 
 * CONSTRAINTS:
 * - List can be empty
 * - Position must be valid (0 <= position < size)
 * - Value may or may not exist in the list
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Deleting from a linked list is about rerouting pointers to skip over the
 * node we want to remove. Think of it like removing a link from a chain:
 * - From head: Move head pointer to second node
 * - From tail: Find second-last node, make it point to null
 * - From middle: Find previous node, make it skip over the target node
 * 
 * DELETE FROM HEAD - TWO STEPS:
 * 1. Store value of head node (to return)
 * 2. Move head pointer to next node
 * 
 * VISUAL EXAMPLE (Delete from head):
 * Before:  HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Step 1: Store value 10
 * Step 2: HEAD → [20|→] → [30|null]
 * Return: 10
 * 
 * DELETE FROM TAIL - TWO CASES:
 * Case 1: Single node → Set head to null
 * Case 2: Multiple nodes → Find second-last node, set its next to null
 * 
 * VISUAL EXAMPLE (Delete from tail):
 * Before:  HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Traverse: Find [20|→] (second-last node)
 * Store: value 30
 * Update: [20|null]
 * After:   HEAD → [10|→] → [20|null]
 * Return: 30
 * 
 * DELETE AT POSITION - THREE STEPS:
 * 1. Handle special case (position 0 = delete from head)
 * 2. Traverse to node before the target (position-1)
 * 3. Skip over target node: prev.next = target.next
 * 
 * VISUAL EXAMPLE (Delete at position 1):
 * Before:  HEAD → [10|→] → [20|→] → [30|null]
 *                  pos 0    pos 1    pos 2
 * 
 * Want to delete position 1:
 * Step 1: Traverse to position 0 ([10|→])
 * Step 2: Store value of position 1 (20)
 * Step 3: [10|→] points to [30|null] (skipping [20|→])
 * After:  HEAD → [10|→] → [30|null]
 *                  pos 0    pos 1
 * Return: 20
 * 
 * DELETE BY VALUE - TWO CASES:
 * Case 1: Value is at head → Use deleteFromHead
 * Case 2: Value is elsewhere → Find node before it, skip over it
 * 
 * VISUAL EXAMPLE (Delete value 20):
 * Before:  HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Search: Find [10|→] (node before 20)
 * Update: [10|→] points to [30|null]
 * After:  HEAD → [10|→] → [30|null]
 * Return: true
 * 
 * EDGE CASES:
 * 1. Empty list: Return null, don't crash
 * 2. Single node: Handle correctly for all operations
 * 3. Delete from head with one node: Head becomes null
 * 4. Delete from tail with one node: Head becomes null
 * 5. Invalid position: Return null, don't modify list
 * 6. Value not found: Return false, list unchanged
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * DELETE FROM HEAD:
 * TIME COMPLEXITY: O(1)
 * - Just update head pointer
 * - No traversal needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses constant extra space
 * 
 * DELETE FROM TAIL:
 * TIME COMPLEXITY: O(n)
 * - Must traverse to second-last node
 * - n is the number of nodes
 * - Cannot directly access second-last node
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses one pointer for traversal
 * 
 * DELETE AT POSITION:
 * TIME COMPLEXITY: O(n)
 * - Worst case: delete from end, traverse n-1 nodes
 * - Best case: delete from head, O(1)
 * - Average case: O(n/2) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses constant extra space
 * 
 * DELETE BY VALUE:
 * TIME COMPLEXITY: O(n)
 * - May need to search entire list
 * - Worst case: value at end or not present
 * - Best case: value at head, O(1)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses one pointer for traversal
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
 * DeleteNode class demonstrating various deletion operations in a singly linked list
 */
class DeleteNode {
    // Head pointer to the first node in the list
    private var head: Node? = null
    
    // Size counter for O(1) size queries
    private var size: Int = 0
    
    /**
     * Delete the first node from the list
     * 
     * TIME: O(1) - Just update head pointer
     * SPACE: O(1) - No extra space needed
     * 
     * ALGORITHM:
     * 1. Check if list is empty
     * 2. Store value of head node
     * 3. Move head to next node
     * 4. Decrement size
     * 5. Return deleted value
     * 
     * @return Deleted value, or null if list is empty
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
        
        // Decrement size
        size--
        
        return deletedValue
    }
    
    /**
     * Delete the last node from the list
     * 
     * TIME: O(n) - Must traverse to second-last node
     * SPACE: O(1) - Only one pointer used
     * 
     * ALGORITHM:
     * 1. Check if list is empty
     * 2. Handle single node case
     * 3. Traverse to second-last node
     * 4. Store value of last node
     * 5. Set second-last node's next to null
     * 6. Decrement size
     * 7. Return deleted value
     * 
     * @return Deleted value, or null if list is empty
     */
    fun deleteFromTail(): Int? {
        // Check if list is empty
        if (head == null) {
            return null
        }
        
        // Handle single node case
        if (head?.next == null) {
            val deletedValue = head?.data
            head = null
            size--
            return deletedValue
        }
        
        // Traverse to second-last node
        // Stop when current.next.next is null
        var current = head
        while (current?.next?.next != null) {
            current = current.next
        }
        
        // Store value of last node
        val deletedValue = current?.next?.data
        
        // Remove last node by setting second-last's next to null
        current?.next = null
        
        // Decrement size
        size--
        
        return deletedValue
    }
    
    /**
     * Delete a node at a specific position (0-indexed)
     * 
     * TIME: O(n) - May need to traverse to position
     * SPACE: O(1) - Only constant extra space
     * 
     * ALGORITHM:
     * 1. Validate position
     * 2. Handle special case (position 0 = delete from head)
     * 3. Traverse to node before target (position-1)
     * 4. Store value of target node
     * 5. Skip over target node
     * 6. Decrement size
     * 7. Return deleted value
     * 
     * @param position The 0-indexed position to delete from
     * @return Deleted value, or null if invalid position
     */
    fun deleteAtPosition(position: Int): Int? {
        // Validate position
        if (position < 0 || position >= size) {
            return null
        }
        
        // Handle special case - delete from head
        if (position == 0) {
            return deleteFromHead()
        }
        
        // Traverse to node before target
        var current = head
        for (i in 0 until position - 1) {
            current = current?.next
        }
        
        // Store value of node to delete
        val deletedValue = current?.next?.data
        
        // Skip over target node
        // current.next becomes current.next.next
        current?.next = current?.next?.next
        
        // Decrement size
        size--
        
        return deletedValue
    }
    
    /**
     * Delete the first occurrence of a node with the specified value
     * 
     * TIME: O(n) - May need to search entire list
     * SPACE: O(1) - Only constant extra space
     * 
     * ALGORITHM:
     * 1. Check if list is empty
     * 2. Handle special case (value at head)
     * 3. Search for value while tracking previous node
     * 4. If found, skip over target node
     * 5. Decrement size
     * 6. Return success status
     * 
     * @param value The value to delete
     * @return true if value found and deleted, false otherwise
     */
    fun deleteByValue(value: Int): Boolean {
        // Check if list is empty
        if (head == null) {
            return false
        }
        
        // Handle special case - value at head
        if (head?.data == value) {
            head = head?.next
            size--
            return true
        }
        
        // Search for value, keeping track of previous node
        var current = head
        while (current?.next != null) {
            if (current.next?.data == value) {
                // Found the value, skip over this node
                current.next = current.next?.next
                size--
                return true
            }
            current = current.next
        }
        
        // Value not found
        return false
    }
    
    /**
     * Insert a node at the head (helper for testing)
     */
    fun insertAtHead(data: Int) {
        val newNode = Node(data)
        newNode.next = head
        head = newNode
        size++
    }
    
    /**
     * Insert a node at the tail (helper for testing)
     */
    fun insertAtTail(data: Int) {
        val newNode = Node(data)
        
        if (head == null) {
            head = newNode
            size++
            return
        }
        
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        
        current?.next = newNode
        size++
    }
    
    /**
     * Display the linked list
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
     * Get the current size of the list
     */
    fun getSize(): Int = size
    
    /**
     * Check if list is empty
     */
    fun isEmpty(): Boolean = head == null
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's perform various deletion operations:
 * 
 * Initial list: HEAD → [10|→] → [20|→] → [30|→] → [40|null]
 * size = 4
 * 
 * Operation: deleteFromHead()
 * Before: HEAD → [10|→] → [20|→] → [30|→] → [40|null]
 * After:  HEAD → [20|→] → [30|→] → [40|null]
 * Returned: 10
 * size = 3
 * 
 * Operation: deleteFromTail()
 * Before: HEAD → [20|→] → [30|→] → [40|null]
 * Traverse to [30|→] (second-last)
 * After:  HEAD → [20|→] → [30|null]
 * Returned: 40
 * size = 2
 * 
 * Operation: insertAtTail(50) and insertAtTail(60) for more data
 * After:  HEAD → [20|→] → [30|→] → [50|→] → [60|null]
 * size = 4
 * 
 * Operation: deleteAtPosition(1)
 * Before: HEAD → [20|→] → [30|→] → [50|→] → [60|null]
 *                 pos 0    pos 1    pos 2    pos 3
 * Traverse to pos 0, skip over pos 1
 * After:  HEAD → [20|→] → [50|→] → [60|null]
 *                 pos 0    pos 1    pos 2
 * Returned: 30
 * size = 3
 * 
 * Operation: deleteByValue(50)
 * Before: HEAD → [20|→] → [50|→] → [60|null]
 * Find 50 at position 1, skip over it
 * After:  HEAD → [20|→] → [60|null]
 * Returned: true
 * size = 2
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Delete Node in Singly Linked List ===\n")
    
    // Test 1: Delete from empty list
    println("Test 1: Delete from empty list")
    val list = DeleteNode()
    println("Initial state: ${list.display()}")  // List is empty
    println("Delete from head: ${list.deleteFromHead()}")  // null
    println("Delete from tail: ${list.deleteFromTail()}")  // null
    println("Delete at position 0: ${list.deleteAtPosition(0)}")  // null
    println()
    
    // Test 2: Build list and delete from head
    println("Test 2: Delete from head")
    list.insertAtTail(10)
    list.insertAtTail(20)
    list.insertAtTail(30)
    list.insertAtTail(40)
    println("Initial list: ${list.display()}")  // 10 → 20 → 30 → 40
    println("Size: ${list.getSize()}")  // 4
    val deleted1 = list.deleteFromHead()
    println("Deleted from head: $deleted1")  // 10
    println("After deletion: ${list.display()}")  // 20 → 30 → 40
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 3: Delete from tail
    println("Test 3: Delete from tail")
    val deleted2 = list.deleteFromTail()
    println("Deleted from tail: $deleted2")  // 40
    println("After deletion: ${list.display()}")  // 20 → 30
    println("Size: ${list.getSize()}")  // 2
    println()
    
    // Test 4: Add more elements and delete at position
    println("Test 4: Delete at specific position")
    list.insertAtTail(40)
    list.insertAtTail(50)
    list.insertAtTail(60)
    println("List before deletion: ${list.display()}")  // 20 → 30 → 40 → 50 → 60
    println("Size: ${list.getSize()}")  // 5
    val deleted3 = list.deleteAtPosition(2)  // Delete 40
    println("Deleted at position 2: $deleted3")  // 40
    println("After deletion: ${list.display()}")  // 20 → 30 → 50 → 60
    println("Size: ${list.getSize()}")  // 4
    println()
    
    // Test 5: Delete at position 0 (same as deleteFromHead)
    println("Test 5: Delete at position 0")
    val deleted4 = list.deleteAtPosition(0)
    println("Deleted at position 0: $deleted4")  // 20
    println("After deletion: ${list.display()}")  // 30 → 50 → 60
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 6: Delete by value
    println("Test 6: Delete by value")
    println("List before deletion: ${list.display()}")  // 30 → 50 → 60
    val found1 = list.deleteByValue(50)
    println("Delete value 50: success=$found1")  // true
    println("After deletion: ${list.display()}")  // 30 → 60
    println("Size: ${list.getSize()}")  // 2
    println()
    
    // Test 7: Delete non-existent value
    println("Test 7: Delete non-existent value")
    val found2 = list.deleteByValue(100)
    println("Delete value 100: success=$found2")  // false
    println("List unchanged: ${list.display()}")  // 30 → 60
    println("Size: ${list.getSize()}")  // 2
    println()
    
    // Test 8: Delete until list is empty
    println("Test 8: Delete until empty")
    list.deleteFromHead()
    println("After one deletion: ${list.display()}")  // 60
    println("Size: ${list.getSize()}")  // 1
    list.deleteFromHead()
    println("After second deletion: ${list.display()}")  // List is empty
    println("Size: ${list.getSize()}")  // 0
    println("Is empty: ${list.isEmpty()}")  // true
    println()
    
    // Test 9: Single node deletion scenarios
    println("Test 9: Single node deletion scenarios")
    val list2 = DeleteNode()
    list2.insertAtHead(99)
    println("Single node list: ${list2.display()}")  // 99
    val deleted5 = list2.deleteFromTail()
    println("Delete from tail: $deleted5")  // 99
    println("After deletion: ${list2.display()}")  // List is empty
    println()
    
    // Test 10: Delete by value at head
    println("Test 10: Delete by value at head")
    val list3 = DeleteNode()
    list3.insertAtTail(5)
    list3.insertAtTail(10)
    list3.insertAtTail(15)
    println("List: ${list3.display()}")  // 5 → 10 → 15
    val found3 = list3.deleteByValue(5)  // Delete head value
    println("Delete value 5 (at head): success=$found3")  // true
    println("After deletion: ${list3.display()}")  // 10 → 15
    println("Size: ${list3.getSize()}")  // 2
}
