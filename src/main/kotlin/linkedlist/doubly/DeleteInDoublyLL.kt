/**
 * ============================================================================
 * PROBLEM: Delete in Doubly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Doubly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement various deletion operations in a doubly linked list:
 * 1. Delete from the beginning (head)
 * 2. Delete from the end (tail)
 * 3. Delete from a specific position
 * 4. Delete a node with a specific value
 * 5. Delete a given node (with direct reference)
 * 
 * In doubly linked lists, deletion is more efficient than singly linked lists
 * because we have access to the previous node via the prev pointer.
 * 
 * INPUT FORMAT:
 * - For deleteFromHead/Tail: no parameters
 * - For deleteAtPosition: position (0-indexed)
 * - For deleteByValue: value to delete
 * - For deleteNode: direct node reference
 * 
 * OUTPUT FORMAT:
 * - Deleted value (or null if list is empty/invalid operation)
 * - Boolean for operations that may fail
 * - Updated list with node removed
 * 
 * CONSTRAINTS:
 * - List can be empty
 * - Position must be valid (0 <= position < size)
 * - Value/node may or may not exist in list
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Deleting from a doubly linked list is like removing a train car. Since
 * each car knows both its front and back neighbors, we can easily disconnect
 * it by updating the neighbors to point to each other, bypassing the removed car.
 * 
 * KEY ADVANTAGE OVER SINGLY LINKED LIST:
 * - Delete from tail: O(1) instead of O(n)
 * - Delete node with reference: O(1) instead of O(n)
 * - Have access to previous node without traversal
 * 
 * DELETE FROM HEAD - THREE STEPS:
 * 1. Store value to return
 * 2. Move head to next node
 * 3. Set new head's prev to null (if exists)
 * 
 * VISUAL EXAMPLE (Delete from head):
 * Before:  HEAD → [null←|10|↔|20|↔|30|→null] ← TAIL
 * 
 * Step 1: Store 10
 * Step 2: HEAD → [null←|20|↔|30|→null] ← TAIL
 * Step 3: Set [20].prev = null
 * Return: 10
 * 
 * DELETE FROM TAIL - THREE STEPS (with tail pointer):
 * 1. Store value to return
 * 2. Move tail to prev node
 * 3. Set new tail's next to null (if exists)
 * 
 * VISUAL EXAMPLE (Delete from tail):
 * Before:  HEAD → [null←|10|↔|20|↔|30|→null] ← TAIL
 * 
 * Step 1: Store 30
 * Step 2: TAIL ← [null←|10|↔|20|→null]
 * Step 3: Set [20].next = null
 * Return: 30
 * 
 * This is O(1) vs O(n) in singly linked list!
 * 
 * DELETE AT POSITION - STEPS:
 * 1. Validate position
 * 2. Handle special cases (0, size-1)
 * 3. Traverse to position
 * 4. Store value to return
 * 5. Update pointers to skip this node:
 *    - prev.next = current.next
 *    - next.prev = current.prev
 * 
 * VISUAL EXAMPLE (Delete at position 1):
 * Before:  HEAD → [null←|10|↔|20|↔|30|→null] ← TAIL
 *                         0     1     2
 * 
 * Delete position 1 (node 20):
 * Step 1: Traverse to [20]
 * Step 2: Store 20
 * Step 3: [10].next = [30]
 * Step 4: [30].prev = [10]
 * After:  HEAD → [null←|10|↔|30|→null] ← TAIL
 *                        0     1
 * Return: 20
 * 
 * DELETE BY VALUE:
 * 1. Search for node with value
 * 2. If found, use node deletion logic
 * 3. Handle head/tail special cases
 * 
 * DELETE NODE (with reference) - O(1):
 * This is the KEY advantage of doubly linked lists!
 * Given a node reference, we can delete it in O(1) time.
 * 
 * Steps:
 * 1. Update prev node's next pointer
 * 2. Update next node's prev pointer
 * 3. Handle head/tail cases
 * 
 * EDGE CASES:
 * 1. Empty list: Return null
 * 2. Single node: Both head and tail become null
 * 3. Delete head: Update head and new head's prev
 * 4. Delete tail: Update tail and new tail's next
 * 5. Value not found: Return false
 * 6. Invalid position: Return null
 * 
 * POINTER NULLIFICATION:
 * After deletion, set removed node's pointers to null to avoid
 * memory leaks (garbage collector handles this in Kotlin).
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * DELETE FROM HEAD:
 * TIME COMPLEXITY: O(1)
 * - Just update head pointer and one prev pointer
 * - No traversal needed
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * DELETE FROM TAIL (with tail pointer):
 * TIME COMPLEXITY: O(1)
 * - Direct access to tail
 * - Update tail pointer and one next pointer
 * - Much better than O(n) in singly linked list!
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * DELETE AT POSITION:
 * TIME COMPLEXITY: O(n)
 * - Must traverse to position
 * - Worst case: delete from end
 * - Best case: delete from head, O(1)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * DELETE BY VALUE:
 * TIME COMPLEXITY: O(n)
 * - Must search for value
 * - May traverse entire list
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * DELETE NODE (with reference):
 * TIME COMPLEXITY: O(1)
 * - Have direct reference to node
 * - Just update adjacent pointers
 * - KEY ADVANTAGE over singly linked list!
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * COMPARISON WITH SINGLY LINKED LIST:
 * Operation            | Singly LL | Doubly LL
 * ---------------------|-----------|----------
 * Delete from head     | O(1)      | O(1)
 * Delete from tail*    | O(n)      | O(1) ← Better!
 * Delete at position   | O(n)      | O(n)
 * Delete by value      | O(n)      | O(n)
 * Delete node (ref)    | O(n)**    | O(1) ← Much better!
 * 
 * * With tail pointer
 * ** Need to find previous node in singly LL
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
 * DeleteInDoublyLL class demonstrating deletion operations
 */
class DeleteInDoublyLL {
    private var head: DoublyNode? = null
    private var tail: DoublyNode? = null
    private var size: Int = 0
    
    /**
     * Delete the first node
     * 
     * TIME: O(1)
     * SPACE: O(1)
     * 
     * @return Deleted value, or null if empty
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
        
        // Set new head's prev to null
        head?.prev = null
        
        size--
        return deletedValue
    }
    
    /**
     * Delete the last node
     * 
     * TIME: O(1) - With tail pointer, this is efficient!
     * SPACE: O(1)
     * 
     * This is the KEY advantage over singly linked list.
     * In singly LL, this would be O(n).
     * 
     * @return Deleted value, or null if empty
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
        
        // Set new tail's next to null
        tail?.next = null
        
        size--
        return deletedValue
    }
    
    /**
     * Delete at specific position
     * 
     * TIME: O(n) - Must traverse to position
     * SPACE: O(1)
     * 
     * @param position Index to delete from
     * @return Deleted value, or null if invalid
     */
    fun deleteAtPosition(position: Int): Int? {
        // Validate position
        if (position < 0 || position >= size) {
            return null
        }
        
        // Special case: delete head
        if (position == 0) {
            return deleteFromHead()
        }
        
        // Special case: delete tail
        if (position == size - 1) {
            return deleteFromTail()
        }
        
        // Traverse to position
        var current = head
        for (i in 0 until position) {
            current = current?.next
        }
        
        val deletedValue = current?.data
        
        // Update pointers to skip this node
        current?.prev?.next = current?.next
        current?.next?.prev = current?.prev
        
        size--
        return deletedValue
    }
    
    /**
     * Delete first occurrence of a value
     * 
     * TIME: O(n) - Must search for value
     * SPACE: O(1)
     * 
     * @param value Value to delete
     * @return true if found and deleted, false otherwise
     */
    fun deleteByValue(value: Int): Boolean {
        if (head == null) {
            return false
        }
        
        // Special case: value at head
        if (head?.data == value) {
            deleteFromHead()
            return true
        }
        
        // Special case: value at tail
        if (tail?.data == value) {
            deleteFromTail()
            return true
        }
        
        // Search for value
        var current = head
        while (current != null) {
            if (current.data == value) {
                // Found it! Delete this node
                current.prev?.next = current.next
                current.next?.prev = current.prev
                size--
                return true
            }
            current = current.next
        }
        
        return false  // Not found
    }
    
    /**
     * Delete a specific node (given reference)
     * 
     * TIME: O(1) - This is a KEY advantage!
     * SPACE: O(1)
     * 
     * In singly linked list, we'd need O(n) to find previous node.
     * Here, we have prev pointer, so it's O(1)!
     * 
     * @param node Node to delete
     * @return true if deleted, false if node is null
     */
    fun deleteNode(node: DoublyNode?): Boolean {
        if (node == null) {
            return false
        }
        
        // Special case: deleting head
        if (node == head) {
            deleteFromHead()
            return true
        }
        
        // Special case: deleting tail
        if (node == tail) {
            deleteFromTail()
            return true
        }
        
        // General case: node is in the middle
        // We have access to both prev and next!
        node.prev?.next = node.next
        node.next?.prev = node.prev
        
        // Clean up (optional, helps GC)
        node.prev = null
        node.next = null
        
        size--
        return true
    }
    
    // Helper methods for building and displaying
    
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
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * List: HEAD → [null←|10|↔|20|↔|30|↔|40|→null] ← TAIL
 * 
 * deleteFromHead():
 * Before: [null←|10|↔|20|↔|30|↔|40|→null]
 * After:  [null←|20|↔|30|↔|40|→null]
 * Returned: 10
 * 
 * deleteFromTail():
 * Before: [null←|20|↔|30|↔|40|→null]
 * After:  [null←|20|↔|30|→null]
 * Returned: 40
 * 
 * deleteAtPosition(1):
 * Before: [null←|20|↔|30|→null]
 *                 0     1
 * [20].next = null
 * After:  [null←|20|→null]
 * Returned: 30
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Delete in Doubly Linked List ===\n")
    
    // Test 1: Delete from empty list
    println("Test 1: Delete from empty list")
    val list = DeleteInDoublyLL()
    println("Delete from head: ${list.deleteFromHead()}")  // null
    println("Delete from tail: ${list.deleteFromTail()}")  // null
    println()
    
    // Test 2: Build list and delete from head
    println("Test 2: Delete from head")
    list.insertAtTail(10)
    list.insertAtTail(20)
    list.insertAtTail(30)
    list.insertAtTail(40)
    println("Initial: ${list.displayForward()}")  // 10 ↔ 20 ↔ 30 ↔ 40
    println("Size: ${list.getSize()}")  // 4
    
    val deleted1 = list.deleteFromHead()
    println("Deleted from head: $deleted1")  // 10
    println("After: ${list.displayForward()}")  // 20 ↔ 30 ↔ 40
    println("Backward: ${list.displayBackward()}")  // 40 ↔ 30 ↔ 20
    println("Size: ${list.getSize()}")  // 3
    println()
    
    // Test 3: Delete from tail
    println("Test 3: Delete from tail (O(1) operation!)")
    val deleted2 = list.deleteFromTail()
    println("Deleted from tail: $deleted2")  // 40
    println("After: ${list.displayForward()}")  // 20 ↔ 30
    println("Size: ${list.getSize()}")  // 2
    println()
    
    // Test 4: Add more and delete at position
    println("Test 4: Delete at position")
    list.insertAtTail(40)
    list.insertAtTail(50)
    list.insertAtTail(60)
    println("Before: ${list.displayForward()}")  // 20 ↔ 30 ↔ 40 ↔ 50 ↔ 60
    
    val deleted3 = list.deleteAtPosition(2)
    println("Deleted at position 2: $deleted3")  // 40
    println("After: ${list.displayForward()}")  // 20 ↔ 30 ↔ 50 ↔ 60
    println("Size: ${list.getSize()}")  // 4
    println()
    
    // Test 5: Delete by value
    println("Test 5: Delete by value")
    val found1 = list.deleteByValue(50)
    println("Delete value 50: success=$found1")  // true
    println("After: ${list.displayForward()}")  // 20 ↔ 30 ↔ 60
    
    val found2 = list.deleteByValue(100)
    println("Delete value 100: success=$found2")  // false
    println("List unchanged: ${list.displayForward()}")  // 20 ↔ 30 ↔ 60
    println()
    
    // Test 6: Delete node with reference
    println("Test 6: Delete node with reference (O(1)!)")
    val node = list.findNode(30)
    println("Found node with value 30: ${node?.data}")
    val success = list.deleteNode(node)
    println("Delete node: success=$success")  // true
    println("After: ${list.displayForward()}")  // 20 ↔ 60
    println("Size: ${list.getSize()}")  // 2
    println()
    
    // Test 7: Delete until empty
    println("Test 7: Delete until empty")
    list.deleteFromHead()
    println("After one deletion: ${list.displayForward()}")  // 60
    list.deleteFromHead()
    println("After second deletion: ${list.displayForward()}")  // List is empty
    println("Is empty: ${list.isEmpty()}")  // true
    println()
    
    // Test 8: Single node deletion
    println("Test 8: Single node deletion")
    val list2 = DeleteInDoublyLL()
    list2.insertAtHead(99)
    println("Single node: ${list2.displayForward()}")  // 99
    list2.deleteFromTail()
    println("After delete from tail: ${list2.displayForward()}")  // List is empty
    println()
    
    // Test 9: Delete head by value
    println("Test 9: Delete head by value")
    val list3 = DeleteInDoublyLL()
    list3.insertAtTail(5)
    list3.insertAtTail(10)
    list3.insertAtTail(15)
    println("Before: ${list3.displayForward()}")  // 5 ↔ 10 ↔ 15
    list3.deleteByValue(5)
    println("After delete 5: ${list3.displayForward()}")  // 10 ↔ 15
    println()
    
    // Test 10: Delete tail by value
    println("Test 10: Delete tail by value")
    list3.deleteByValue(15)
    println("After delete 15: ${list3.displayForward()}")  // 10
    println("Size: ${list3.getSize()}")  // 1
    println("Forward: ${list3.displayForward()}")  // 10
    println("Backward: ${list3.displayBackward()}")  // 10
}

