/**
 * ============================================================================
 * PROBLEM: Length of Singly Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Linked Lists - Singly Linked List
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement methods to calculate the length (number of nodes) of a singly
 * linked list using different approaches:
 * 1. Iterative approach - traverse and count nodes
 * 2. Recursive approach - count using recursion
 * 3. Maintain size counter - O(1) retrieval
 * 
 * The length is the total number of nodes in the list, not the number of
 * data values. An empty list has length 0.
 * 
 * INPUT FORMAT:
 * - A linked list (can be empty or have multiple nodes)
 * 
 * OUTPUT FORMAT:
 * - An integer representing the number of nodes in the list
 * 
 * CONSTRAINTS:
 * - List can be empty (length = 0)
 * - List can have 1 to n nodes
 * - All integer data values are valid
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Counting nodes in a linked list is like counting train cars. Since we
 * can't see all cars at once, we must start from the front and count each
 * car as we pass it, until we reach the end.
 * 
 * THREE APPROACHES:
 * 
 * APPROACH 1: ITERATIVE (Most Common)
 * - Start from head with counter = 0
 * - Move through each node, incrementing counter
 * - Stop when we reach null
 * - Return counter
 * 
 * APPROACH 2: RECURSIVE (Elegant)
 * - Base case: if node is null, return 0
 * - Recursive case: return 1 + length(next node)
 * - Call stack naturally counts nodes
 * 
 * APPROACH 3: MAINTAIN SIZE VARIABLE (Optimal for Frequent Queries)
 * - Keep a size variable in the class
 * - Increment on insert, decrement on delete
 * - Return size in O(1) time
 * 
 * VISUAL EXAMPLE (Iterative Counting):
 * HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Step 1: Start at head, count=0
 * Step 2: Visit [10], count=1, move next
 * Step 3: Visit [20], count=2, move next
 * Step 4: Visit [30], count=3, move next
 * Step 5: Reached null, stop
 * Return: 3
 * 
 * VISUAL EXAMPLE (Recursive Counting):
 * HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Call 1: length([10|→]) = 1 + length([20|→])
 * Call 2:                   = 1 + length([30|null])
 * Call 3:                   = 1 + length(null)
 * Call 4:                   = 1 + 0
 * Unwind:                   = 1 + 1 = 2
 * Unwind:                   = 1 + 2 = 3
 * Return: 3
 * 
 * RECURSIVE CALL STACK:
 * length([10]) → 1 + length([20])
 *                    ↓
 *                    1 + length([30])
 *                        ↓
 *                        1 + length(null)
 *                            ↓
 *                            0
 *                        1 + 0 = 1
 *                    1 + 1 = 2
 *                1 + 2 = 3
 * 
 * EDGE CASES:
 * 1. Empty list (head = null): length = 0
 * 2. Single node: length = 1
 * 3. Two nodes: length = 2
 * 4. Very long list: ensure no integer overflow (practical limit)
 * 
 * TRADE-OFFS:
 * 
 * Iterative:
 * ✅ No stack overflow risk
 * ✅ More efficient (no function call overhead)
 * ✅ Easy to understand
 * ❌ Requires loop construct
 * 
 * Recursive:
 * ✅ More elegant and concise
 * ✅ Demonstrates recursion concept
 * ❌ Stack overflow risk for very long lists
 * ❌ Function call overhead
 * 
 * Size Variable:
 * ✅ O(1) retrieval - fastest
 * ✅ No traversal needed
 * ❌ Must maintain correctly on every insert/delete
 * ❌ Extra memory for size variable
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * ITERATIVE APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Must visit each of the n nodes exactly once
 * - Cannot skip any node
 * - Each visit is O(1) operation
 * - Total: n * O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses two variables: current pointer and counter
 * - No additional data structures
 * - Space usage doesn't grow with input size
 * 
 * RECURSIVE APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Must visit each of the n nodes exactly once
 * - Each recursive call processes one node
 * - Total: n recursive calls
 * 
 * SPACE COMPLEXITY: O(n)
 * - Uses call stack for recursion
 * - Maximum depth = n (number of nodes)
 * - Each call stores return address and parameters
 * - Stack frames: n * (constant space) = O(n)
 * 
 * SIZE VARIABLE APPROACH:
 * TIME COMPLEXITY: O(1)
 * - Direct variable access
 * - No traversal needed
 * - Best possible time complexity
 * 
 * SPACE COMPLEXITY: O(1)
 * - One integer variable
 * - Constant extra space
 * 
 * COMPARISON:
 * Approach    | Time | Space | Best For
 * ------------|------|-------|----------
 * Iterative   | O(n) | O(1)  | One-time length calculation
 * Recursive   | O(n) | O(n)  | Learning recursion
 * Size Var    | O(1) | O(1)  | Frequent length queries
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
 * LengthOfLL class demonstrating different methods to calculate linked list length
 */
class LengthOfLL {
    // Head pointer to the first node
    private var head: Node? = null
    
    // Size variable maintained for O(1) length queries
    // Updated on every insert/delete operation
    private var size: Int = 0
    
    /**
     * Calculate length iteratively by traversing the list
     * 
     * TIME: O(n) - Must visit each node
     * SPACE: O(1) - Only uses two variables
     * 
     * ALGORITHM:
     * 1. Initialize counter to 0
     * 2. Start from head
     * 3. While current node is not null:
     *    a. Increment counter
     *    b. Move to next node
     * 4. Return counter
     * 
     * This is the most common and efficient approach for calculating
     * length without maintaining a size variable.
     * 
     * @return Number of nodes in the list
     */
    fun getLengthIterative(): Int {
        // Counter for number of nodes
        var count = 0
        
        // Start from head
        var current = head
        
        // Traverse the list
        while (current != null) {
            // Increment counter for each node
            count++
            // Move to next node
            current = current.next
        }
        
        // Return total count
        return count
    }
    
    /**
     * Calculate length recursively
     * 
     * TIME: O(n) - Visit each node once
     * SPACE: O(n) - Recursive call stack depth = n
     * 
     * ALGORITHM:
     * 1. Base case: if node is null, return 0
     * 2. Recursive case: return 1 + length of rest of list
     * 
     * This demonstrates the recursive approach but is less efficient
     * than iterative due to call stack overhead and space usage.
     * 
     * @return Number of nodes in the list
     */
    fun getLengthRecursive(): Int {
        // Call helper function starting from head
        return getLengthRecursiveHelper(head)
    }
    
    /**
     * Helper function for recursive length calculation
     * 
     * @param node Current node being processed
     * @return Length of list starting from this node
     */
    private fun getLengthRecursiveHelper(node: Node?): Int {
        // Base case: reached end of list
        if (node == null) {
            return 0
        }
        
        // Recursive case: current node (1) + length of remaining list
        return 1 + getLengthRecursiveHelper(node.next)
    }
    
    /**
     * Get length using maintained size variable
     * 
     * TIME: O(1) - Direct variable access
     * SPACE: O(1) - No extra space
     * 
     * This is the most efficient approach when length is queried frequently.
     * The size variable is maintained during insert/delete operations.
     * 
     * @return Number of nodes in the list
     */
    fun getLength(): Int {
        return size
    }
    
    /**
     * Alternative name for getLength (for clarity)
     */
    fun getSize(): Int = size
    
    /**
     * Check if list is empty
     * 
     * TIME: O(1)
     * 
     * @return true if list has no nodes, false otherwise
     */
    fun isEmpty(): Boolean {
        return head == null
        // Alternatively: return size == 0
    }
    
    // Helper methods for building and manipulating list
    
    /**
     * Insert at head
     * Updates size variable
     */
    fun insertAtHead(data: Int) {
        val newNode = Node(data)
        newNode.next = head
        head = newNode
        size++  // Maintain size counter
    }
    
    /**
     * Insert at tail
     * Updates size variable
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
        size++  // Maintain size counter
    }
    
    /**
     * Delete from head
     * Updates size variable
     */
    fun deleteFromHead(): Int? {
        if (head == null) {
            return null
        }
        
        val deletedValue = head?.data
        head = head?.next
        size--  // Maintain size counter
        return deletedValue
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
     * Clear the list
     * Resets size to 0
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
 * Example 1: Iterative length calculation
 * List: HEAD → [10|→] → [20|→] → [30|null]
 * 
 * Step 1: count=0, current=[10]
 * Step 2: count=1, current=[20]
 * Step 3: count=2, current=[30]
 * Step 4: count=3, current=null (stop)
 * Return: 3
 * 
 * Example 2: Recursive length calculation
 * List: HEAD → [10|→] → [20|null]
 * 
 * Call stack:
 * getLengthRecursive([10]) 
 *   → 1 + getLengthRecursive([20])
 *       → 1 + getLengthRecursive(null)
 *           → 0
 *       → 1 + 0 = 1
 *   → 1 + 1 = 2
 * Return: 2
 * 
 * Example 3: Size variable approach
 * Operations:
 * 1. insertAtHead(5) → size=1
 * 2. insertAtTail(10) → size=2
 * 3. insertAtTail(15) → size=3
 * 4. deleteFromHead() → size=2
 * 5. getLength() → return 2 (O(1) operation)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Length of Singly Linked List ===\n")
    
    // Test 1: Empty list
    println("Test 1: Empty list")
    val list = LengthOfLL()
    println("List: ${list.display()}")  // List is empty
    println("Length (iterative): ${list.getLengthIterative()}")  // 0
    println("Length (recursive): ${list.getLengthRecursive()}")  // 0
    println("Length (size var): ${list.getLength()}")  // 0
    println("Is empty: ${list.isEmpty()}")  // true
    println()
    
    // Test 2: Single element
    println("Test 2: Single element")
    list.insertAtHead(10)
    println("List: ${list.display()}")  // 10
    println("Length (iterative): ${list.getLengthIterative()}")  // 1
    println("Length (recursive): ${list.getLengthRecursive()}")  // 1
    println("Length (size var): ${list.getLength()}")  // 1
    println()
    
    // Test 3: Multiple elements
    println("Test 3: Multiple elements")
    list.insertAtTail(20)
    list.insertAtTail(30)
    list.insertAtTail(40)
    list.insertAtTail(50)
    println("List: ${list.display()}")  // 10 → 20 → 30 → 40 → 50
    println("Length (iterative): ${list.getLengthIterative()}")  // 5
    println("Length (recursive): ${list.getLengthRecursive()}")  // 5
    println("Length (size var): ${list.getLength()}")  // 5
    println()
    
    // Test 4: After deletions
    println("Test 4: After deletions")
    list.deleteFromHead()
    list.deleteFromHead()
    println("List: ${list.display()}")  // 30 → 40 → 50
    println("Length (iterative): ${list.getLengthIterative()}")  // 3
    println("Length (recursive): ${list.getLengthRecursive()}")  // 3
    println("Length (size var): ${list.getLength()}")  // 3
    println()
    
    // Test 5: Build and measure
    println("Test 5: Build list incrementally")
    val list2 = LengthOfLL()
    for (i in 1..10) {
        list2.insertAtTail(i * 10)
        println("After inserting ${i * 10}: length = ${list2.getLength()}")
    }
    println("Final list: ${list2.display()}")
    println("Final length (iterative): ${list2.getLengthIterative()}")
    println()
    
    // Test 6: Verify all methods return same result
    println("Test 6: Verify all methods agree")
    val iterative = list2.getLengthIterative()
    val recursive = list2.getLengthRecursive()
    val sizeVar = list2.getLength()
    println("Iterative: $iterative")
    println("Recursive: $recursive")
    println("Size variable: $sizeVar")
    println("All methods agree: ${iterative == recursive && recursive == sizeVar}")
    println()
    
    // Test 7: Clear and check
    println("Test 7: Clear list")
    list2.clear()
    println("List: ${list2.display()}")  // List is empty
    println("Length after clear: ${list2.getLength()}")  // 0
    println("Is empty: ${list2.isEmpty()}")  // true
    println()
    
    // Test 8: Two elements
    println("Test 8: Two elements")
    val list3 = LengthOfLL()
    list3.insertAtHead(100)
    list3.insertAtHead(200)
    println("List: ${list3.display()}")  // 200 → 100
    println("Length: ${list3.getLength()}")  // 2
    println()
    
    // Test 9: Performance comparison (conceptual)
    println("Test 9: Performance characteristics")
    println("For a list with 1000 nodes:")
    println("  - Iterative: O(n) time, O(1) space")
    println("  - Recursive: O(n) time, O(n) space")
    println("  - Size variable: O(1) time, O(1) space ← Best!")
    println()
    
    // Test 10: Demonstrate size maintenance
    println("Test 10: Size maintained correctly")
    val list4 = LengthOfLL()
    println("Initial size: ${list4.getSize()}")  // 0
    
    list4.insertAtHead(5)
    println("After insertAtHead(5): ${list4.getSize()}")  // 1
    
    list4.insertAtTail(10)
    println("After insertAtTail(10): ${list4.getSize()}")  // 2
    
    list4.insertAtHead(1)
    println("After insertAtHead(1): ${list4.getSize()}")  // 3
    
    list4.deleteFromHead()
    println("After deleteFromHead(): ${list4.getSize()}")  // 2
    
    println("Final list: ${list4.display()}")  // 5 → 10
    println("Verify with iterative count: ${list4.getLengthIterative()}")  // 2
}
