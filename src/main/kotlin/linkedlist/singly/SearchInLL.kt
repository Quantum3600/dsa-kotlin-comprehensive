/**
 * ============================================================================
 * PROBLEM: Search in Singly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Singly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement search operations in a singly linked list:
 * 1. Search for a value and return its index (0-based)
 * 2. Check if a value exists in the list
 * 3. Get value at a specific index
 * 4. Find the first node with a specific value
 * 
 * Unlike arrays, linked lists don't support random access, so we must
 * traverse from the head to search for elements.
 * 
 * INPUT FORMAT:
 * - For search: integer value to search for
 * - For get: index position (0-based)
 * 
 * OUTPUT FORMAT:
 * - For search: index of first occurrence, or -1 if not found
 * - For contains: boolean (true if exists, false otherwise)
 * - For get: value at index, or null if invalid index
 * 
 * CONSTRAINTS:
 * - List can be empty
 * - Values can be duplicated (return first occurrence)
 * - Index must be valid (0 <= index < size)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Searching in a linked list is like searching for a specific car in a train
 * where you can only see one car at a time. You start from the front (head)
 * and check each car (node) until you find what you're looking for or reach
 * the end.
 * 
 * Key difference from arrays:
 * - Arrays: Direct access to any element in O(1) - nums[5]
 * - Linked Lists: Must traverse from head in O(n) - follow next pointers
 * 
 * SEARCH BY VALUE - LINEAR SEARCH:
 * 1. Start from head
 * 2. Keep counter for current index
 * 3. Compare each node's data with target
 * 4. If match found, return current index
 * 5. If reached end (null), return -1
 * 
 * VISUAL EXAMPLE (Search for 30):
 * HEAD → [10|→] → [20|→] → [30|→] → [40|null]
 *         i=0      i=1      i=2      i=3
 * 
 * Step 1: Check [10], data=10 ≠ 30, move next
 * Step 2: Check [20], data=20 ≠ 30, move next
 * Step 3: Check [30], data=30 = 30, FOUND at index 2
 * Return: 2
 * 
 * VISUAL EXAMPLE (Search for 50 - not found):
 * HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Step 1: Check [10], data=10 ≠ 50, move next
 * Step 2: Check [20], data=20 ≠ 50, move next
 * Step 3: Check [30], data=30 ≠ 50, move next
 * Step 4: Reached null (end of list)
 * Return: -1 (not found)
 * 
 * GET BY INDEX:
 * 1. Validate index (0 <= index < size)
 * 2. Traverse exactly 'index' times from head
 * 3. Return data at that node
 * 
 * VISUAL EXAMPLE (Get value at index 2):
 * HEAD → [10|→] → [20|→] → [30|→] → [40|null]
 *         i=0      i=1      i=2      i=3
 * 
 * Start at head (i=0)
 * Move next (i=1)
 * Move next (i=2) - Stop here
 * Return: 30
 * 
 * CONTAINS (Check Existence):
 * Simply check if search() returns -1 or not
 * - If search returns >= 0: value exists
 * - If search returns -1: value doesn't exist
 * 
 * EDGE CASES:
 * 1. Empty list: All searches return -1/null/false
 * 2. Single element: Check head only
 * 3. Value at head: Return 0 immediately
 * 4. Value at tail: Must traverse entire list
 * 5. Duplicate values: Return first occurrence
 * 6. Negative index: Return null
 * 7. Index >= size: Return null
 * 
 * WHY O(n) AND NOT BETTER:
 * - No random access in linked lists
 * - Can't use binary search (even if sorted)
 * - Must follow next pointers sequentially
 * - Cannot skip nodes or jump to middle
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * SEARCH BY VALUE:
 * TIME COMPLEXITY: O(n)
 * - Best case: O(1) - value at head
 * - Average case: O(n/2) = O(n) - value in middle
 * - Worst case: O(n) - value at end or not present
 * - n is the number of nodes in the list
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses two variables: current pointer and index counter
 * - No additional data structures
 * 
 * GET BY INDEX:
 * TIME COMPLEXITY: O(n)
 * - Must traverse 'index' nodes
 * - Worst case: O(n) when index = size-1
 * - Best case: O(1) when index = 0
 * - Average case: O(n/2) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses one pointer for traversal
 * 
 * CONTAINS:
 * TIME COMPLEXITY: O(n)
 * - Same as search (internally uses search)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Same as search
 * 
 * COMPARISON WITH ARRAYS:
 * Operation       | Linked List | Array
 * ----------------|-------------|-------
 * Search by value | O(n)        | O(n)
 * Get by index    | O(n)        | O(1) ← Array wins!
 * Contains check  | O(n)        | O(n)
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
 * SearchInLL class demonstrating various search operations in a singly linked list
 */
class SearchInLL {
    // Head pointer to the first node
    private var head: Node? = null
    
    // Size counter for O(1) size queries and validation
    private var size: Int = 0
    
    /**
     * Search for a value in the list and return its first occurrence index
     * 
     * TIME: O(n) - May need to traverse entire list
     * SPACE: O(1) - Only uses two variables
     * 
     * ALGORITHM:
     * 1. Start from head with index = 0
     * 2. While current node is not null:
     *    a. If current node's data matches target, return index
     *    b. Move to next node and increment index
     * 3. If loop ends, value not found, return -1
     * 
     * @param value The value to search for
     * @return Index of first occurrence (0-based), or -1 if not found
     */
    fun search(value: Int): Int {
        // Start from the head
        var current = head
        var index = 0
        
        // Traverse the list
        while (current != null) {
            // Check if current node contains the value
            if (current.data == value) {
                return index  // Found! Return current index
            }
            
            // Move to next node
            current = current.next
            index++
        }
        
        // Reached end of list without finding value
        return -1
    }
    
    /**
     * Check if a value exists in the list
     * 
     * TIME: O(n) - Uses search internally
     * SPACE: O(1)
     * 
     * This is a convenience method that makes the API more intuitive
     * when you just want to check existence without caring about position.
     * 
     * @param value The value to check for
     * @return true if value exists, false otherwise
     */
    fun contains(value: Int): Boolean {
        // Use search method: if found (>=0), return true; if not found (-1), return false
        return search(value) != -1
    }
    
    /**
     * Get the value at a specific index
     * 
     * TIME: O(n) - Must traverse to index
     * SPACE: O(1)
     * 
     * ALGORITHM:
     * 1. Validate index (must be 0 <= index < size)
     * 2. Start from head
     * 3. Move next 'index' times
     * 4. Return data at that node
     * 
     * @param index The 0-based index to retrieve
     * @return Value at index, or null if invalid index
     */
    fun get(index: Int): Int? {
        // Validate index
        if (index < 0 || index >= size) {
            return null
        }
        
        // Traverse to the index
        var current = head
        for (i in 0 until index) {
            current = current?.next
        }
        
        // Return data at this node
        return current?.data
    }
    
    /**
     * Find all indices where a value occurs (for duplicate values)
     * 
     * TIME: O(n) - Must traverse entire list
     * SPACE: O(k) - Where k is number of occurrences
     * 
     * ALGORITHM:
     * 1. Create empty list to store indices
     * 2. Traverse list with index counter
     * 3. When value matches, add index to list
     * 4. Return list of all indices
     * 
     * @param value The value to search for
     * @return List of all indices where value occurs (empty if not found)
     */
    fun findAll(value: Int): List<Int> {
        val indices = mutableListOf<Int>()
        var current = head
        var index = 0
        
        while (current != null) {
            if (current.data == value) {
                indices.add(index)  // Found occurrence, add to list
            }
            current = current.next
            index++
        }
        
        return indices
    }
    
    /**
     * Get the first node containing a specific value
     * 
     * TIME: O(n) - May traverse entire list
     * SPACE: O(1)
     * 
     * This is useful when you need the actual node reference,
     * not just the value or index.
     * 
     * @param value The value to find
     * @return Node containing the value, or null if not found
     */
    fun findNode(value: Int): Node? {
        var current = head
        
        while (current != null) {
            if (current.data == value) {
                return current  // Found the node
            }
            current = current.next
        }
        
        return null  // Not found
    }
    
    // Helper methods for building and displaying list
    
    /**
     * Insert at head (for building test lists)
     */
    fun insertAtHead(data: Int) {
        val newNode = Node(data)
        newNode.next = head
        head = newNode
        size++
    }
    
    /**
     * Insert at tail (for building test lists)
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
     * Display the list
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
     * Get size of list
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
 * List: HEAD → [10|→] → [20|→] → [30|→] → [20|→] → [40|null]
 *               i=0      i=1      i=2      i=3      i=4
 * 
 * Operation: search(20)
 * Iteration 1: i=0, data=10 ≠ 20, continue
 * Iteration 2: i=1, data=20 = 20, FOUND!
 * Return: 1 (first occurrence)
 * 
 * Operation: contains(30)
 * Calls search(30) internally
 * search(30) returns 2 (index of 30)
 * 2 != -1, so return true
 * 
 * Operation: get(3)
 * Start at head (i=0)
 * Move next 3 times: i=0 → i=1 → i=2 → i=3
 * Now at node with data=20
 * Return: 20
 * 
 * Operation: findAll(20)
 * Iteration 1: i=0, data=10 ≠ 20
 * Iteration 2: i=1, data=20 = 20, add 1 to list
 * Iteration 3: i=2, data=30 ≠ 20
 * Iteration 4: i=3, data=20 = 20, add 3 to list
 * Iteration 5: i=4, data=40 ≠ 20
 * Return: [1, 3]
 * 
 * Operation: search(100) (not in list)
 * Traverse entire list, no match found
 * Return: -1
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Search in Singly Linked List ===\n")
    
    // Test 1: Search in empty list
    println("Test 1: Search in empty list")
    val list = SearchInLL()
    println("List: ${list.display()}")  // List is empty
    println("Search for 10: ${list.search(10)}")  // -1
    println("Contains 10: ${list.contains(10)}")  // false
    println("Get at index 0: ${list.get(0)}")  // null
    println()
    
    // Test 2: Build list and search for existing values
    println("Test 2: Search for existing values")
    list.insertAtTail(10)
    list.insertAtTail(20)
    list.insertAtTail(30)
    list.insertAtTail(40)
    list.insertAtTail(50)
    println("List: ${list.display()}")  // 10 → 20 → 30 → 40 → 50
    println("Search for 30: ${list.search(30)}")  // 2
    println("Search for 10: ${list.search(10)}")  // 0 (at head)
    println("Search for 50: ${list.search(50)}")  // 4 (at tail)
    println("Search for 20: ${list.search(20)}")  // 1
    println()
    
    // Test 3: Search for non-existent value
    println("Test 3: Search for non-existent value")
    println("Search for 100: ${list.search(100)}")  // -1
    println("Search for -5: ${list.search(-5)}")  // -1
    println("Contains 100: ${list.contains(100)}")  // false
    println()
    
    // Test 4: Get by index
    println("Test 4: Get value by index")
    println("Get at index 0: ${list.get(0)}")  // 10
    println("Get at index 2: ${list.get(2)}")  // 30
    println("Get at index 4: ${list.get(4)}")  // 50 (last)
    println("Get at index 5: ${list.get(5)}")  // null (out of bounds)
    println("Get at index -1: ${list.get(-1)}")  // null (negative)
    println()
    
    // Test 5: Contains check
    println("Test 5: Check if values exist")
    println("Contains 30: ${list.contains(30)}")  // true
    println("Contains 40: ${list.contains(40)}")  // true
    println("Contains 60: ${list.contains(60)}")  // false
    println()
    
    // Test 6: Duplicate values
    println("Test 6: Handle duplicate values")
    val list2 = SearchInLL()
    list2.insertAtTail(5)
    list2.insertAtTail(10)
    list2.insertAtTail(5)
    list2.insertAtTail(20)
    list2.insertAtTail(5)
    println("List: ${list2.display()}")  // 5 → 10 → 5 → 20 → 5
    println("Search for 5 (first occurrence): ${list2.search(5)}")  // 0
    println("Find all occurrences of 5: ${list2.findAll(5)}")  // [0, 2, 4]
    println("Find all occurrences of 10: ${list2.findAll(10)}")  // [1]
    println("Find all occurrences of 100: ${list2.findAll(100)}")  // []
    println()
    
    // Test 7: Single element list
    println("Test 7: Single element list")
    val list3 = SearchInLL()
    list3.insertAtHead(99)
    println("List: ${list3.display()}")  // 99
    println("Search for 99: ${list3.search(99)}")  // 0
    println("Search for 100: ${list3.search(100)}")  // -1
    println("Get at index 0: ${list3.get(0)}")  // 99
    println("Get at index 1: ${list3.get(1)}")  // null
    println()
    
    // Test 8: Find node reference
    println("Test 8: Find node by value")
    println("List: ${list.display()}")  // 10 → 20 → 30 → 40 → 50
    val node = list.findNode(30)
    println("Find node with value 30: ${node?.data}")  // 30
    println("Next node after found node: ${node?.next?.data}")  // 40
    val notFound = list.findNode(999)
    println("Find node with value 999: $notFound")  // null
    println()
    
    // Test 9: Sequential access pattern
    println("Test 9: Access all elements by index")
    print("Values accessed by index: ")
    for (i in 0 until list.getSize()) {
        print("${list.get(i)}")
        if (i < list.getSize() - 1) print(", ")
    }
    println()  // 10, 20, 30, 40, 50
    println()
    
    // Test 10: Negative numbers
    println("Test 10: Search with negative numbers")
    val list4 = SearchInLL()
    list4.insertAtTail(-5)
    list4.insertAtTail(0)
    list4.insertAtTail(-10)
    list4.insertAtTail(15)
    println("List: ${list4.display()}")  // -5 → 0 → -10 → 15
    println("Search for -5: ${list4.search(-5)}")  // 0
    println("Search for -10: ${list4.search(-10)}")  // 2
    println("Search for 0: ${list4.search(0)}")  // 1
    println("Get at index 2: ${list4.get(2)}")  // -10
}
