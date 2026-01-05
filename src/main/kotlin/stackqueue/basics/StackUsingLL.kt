/**
 * ============================================================================
 * DATA STRUCTURE:  Stack Implementation Using Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement a stack using a singly linked list with all basic stack operations. 
 * 
 * WHY LINKED LIST OVER ARRAY?
 * 
 * ARRAY LIMITATIONS:
 * ❌ Fixed size (must declare capacity)
 * ❌ Stack overflow when full
 * ❌ Wasted memory if capacity too large
 * ❌ Need to resize (costly operation)
 * 
 * LINKED LIST ADVANTAGES:
 * ✅ Dynamic size (grows/shrinks as needed)
 * ✅ No stack overflow (until system memory full)
 * ✅ No wasted space
 * ✅ Efficient push/pop at one end
 * 
 * LINKED LIST DISADVANTAGES:
 * ❌ Extra memory for pointers (next reference)
 * ❌ No random access
 * ❌ Slightly more complex implementation
 * ❌ Cache locality issues
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY DECISION:  Where to add/remove? 
 * 
 * Option 1: Add/Remove at END
 * - Need to traverse to end:  O(n)
 * - Inefficient ❌
 * 
 * Option 2: Add/Remove at BEGINNING
 * - Direct access via head pointer: O(1)
 * - Efficient ✓ (This is what we do!)
 * 
 * STRUCTURE:
 * - Each node:  data + next pointer
 * - Maintain top pointer (head of list)
 * - Push:  Add new node at top
 * - Pop: Remove node from top
 * 
 * VISUAL: 
 * ```
 * Empty Stack:
 * top = null
 * 
 * After push(10):
 * top → [10|null]
 * 
 * After push(20):
 * top → [20|•] → [10|null]
 * 
 * After push(30):
 * top → [30|•] → [20|•] → [10|null]
 * 
 * After pop():
 * top → [20|•] → [10|null]
 *       (30 removed)
 * ```
 * 
 * ALGORITHM:
 * 
 * push(value):
 * 1. Create new node with value
 * 2. Set newNode.next = top
 * 3. Update top = newNode
 * 
 * pop():
 * 1. If top is null: throw error
 * 2. Save top.data
 * 3. Move top = top.next
 * 4. Return saved data
 * 
 * peek():
 * 1. If top is null: throw error
 * 2. Return top.data (don't modify top)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - push(): O(1) - Add at head
 * - pop(): O(1) - Remove from head
 * - peek(): O(1) - Access head
 * - isEmpty(): O(1) - Check if top is null
 * - size(): O(1) - Maintain counter
 * - search(): O(n) - Must traverse list
 * 
 * SPACE COMPLEXITY:  O(n)
 * - n nodes, each with data + pointer
 * - Extra O(1) for top pointer and size counter
 * 
 * ============================================================================
 */

package stackqueue.basics

/**
 * Node class for linked list
 * 
 * @param data Value stored in node
 * @param next Reference to next node
 */
data class StackNode(
    val data: Int,
    var next: StackNode? = null
)

/**
 * Stack implementation using singly linked list
 * All operations are performed at the head (top) of the list
 */
class StackUsingLL {
    // Top of stack (head of linked list)
    private var top: StackNode? = null
    
    // Track size for O(1) size() operation
    private var currentSize = 0
    
    /**
     * Push element onto stack
     * TIME: O(1) - Always add at head
     * 
     * @param value Element to push
     */
    fun push(value: Int) {
        // Create new node
        val newNode = StackNode(value)
        
        // Link new node to current top
        newNode.next = top
        
        // Update top to new node
        top = newNode
        
        currentSize++
        println("Pushed:  $value")
    }
    
    /**
     * Pop element from stack
     * TIME: O(1) - Remove from head
     * 
     * @return Popped element
     * @throws IllegalStateException if stack is empty
     */
    fun pop(): Int {
        // Check if stack is empty
        if (isEmpty()) {
            throw IllegalStateException("Stack Underflow!  Cannot pop from empty stack")
        }
        
        // Get data from top node
        val value = top!! .data
        
        // Move top to next node
        top = top?. next
        
        currentSize--
        println("Popped: $value")
        return value
    }
    
    /**
     * Peek at top element without removing
     * TIME: O(1)
     * 
     * @return Top element
     * @throws IllegalStateException if stack is empty
     */
    fun peek(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Stack is empty! Cannot peek")
        }
        
        return top!!. data
    }
    
    /**
     * Check if stack is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean {
        return top == null
    }
    
    /**
     * Get current size of stack
     * TIME: O(1) - We maintain size counter
     */
    fun size(): Int {
        return currentSize
    }
    
    /**
     * Search for element in stack
     * TIME: O(n) - Must traverse list
     * 
     * @param value Element to search
     * @return Position from top (0-indexed), or -1 if not found
     */
    fun search(value:  Int): Int {
        var current = top
        var position = 0
        
        // Traverse from top to bottom
        while (current != null) {
            if (current.data == value) {
                return position
            }
            current = current.next
            position++
        }
        
        return -1  // Not found
    }
    
    /**
     * Display all elements in stack
     * TIME:  O(n)
     */
    fun display() {
        if (isEmpty()) {
            println("Stack is empty")
            return
        }
        
        println("Stack (top to bottom):")
        var current = top
        
        while (current != null) {
            print("  │ ${current.data} │")
            if (current.next != null) print(" →")
            println()
            current = current.next
        }
        println("  └───┘")
        println("Size: $currentSize")
    }
    
    /**
     * Clear the stack
     * TIME: O(1) - Just set top to null
     * Garbage collector will handle node cleanup
     */
    fun clear() {
        top = null
        currentSize = 0
        println("Stack cleared")
    }
    
    /**
     * Convert stack to list (for testing)
     * TIME: O(n)
     */
    fun toList(): List<Int> {
        val list = mutableListOf<Int>()
        var current = top
        
        while (current != null) {
            list.add(current.data)
            current = current.next
        }
        
        return list
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Initial State:
 * top = null, size = 0
 * 
 * push(10):
 * - newNode = Node(10, null)
 * - newNode.next = null (top was null)
 * - top = newNode
 * - top → [10|null]
 * - size = 1
 * 
 * push(20):
 * - newNode = Node(20, null)
 * - newNode.next = top (pointing to 10)
 * - top = newNode
 * - top → [20|•] → [10|null]
 * - size = 2
 * 
 * push(30):
 * - newNode = Node(30, null)
 * - newNode.next = top (pointing to 20)
 * - top = newNode
 * - top → [30|•] → [20|•] → [10|null]
 * - size = 3
 * 
 * peek():
 * - Return top.data = 30
 * - No change to structure
 * - Still:  top → [30|•] → [20|•] → [10|null]
 * 
 * pop():
 * - value = top.data = 30
 * - top = top.next
 * - top → [20|•] → [10|null]
 * - size = 2
 * - Return 30
 * 
 * pop():
 * - value = top.data = 20
 * - top = top.next
 * - top → [10|null]
 * - size = 1
 * - Return 20
 * 
 * pop():
 * - value = top.data = 10
 * - top = top.next = null
 * - top = null (empty stack)
 * - size = 0
 * - Return 10
 * 
 * ============================================================================
 * COMPARISON:  Array vs Linked List
 * ============================================================================
 * 
 * ASPECT          | ARRAY          | LINKED LIST
 * ----------------|----------------|------------------
 * Size            | Fixed          | Dynamic
 * Overflow        | Yes            | Rare (memory limit)
 * Memory/element  | Just data      | Data + pointer
 * Cache           | Better         | Worse
 * Implementation  | Simpler        | Slightly complex
 * Resize          | Costly O(n)    | Not needed
 * Memory waste    | Unused slots   | Pointer overhead
 * Random access   | O(1)           | O(n)
 * 
 * WHEN TO USE LINKED LIST: 
 * ✓ Unknown maximum size
 * ✓ Frequent push/pop, rare search
 * ✓ Size varies significantly
 * ✓ Memory fragmentation acceptable
 * 
 * WHEN TO USE ARRAY:
 * ✓ Known size limit
 * ✓ Need cache efficiency
 * ✓ Memory constrained
 * ✓ Frequent searches
 * 
 * ============================================================================
 * MEMORY ANALYSIS
 * ============================================================================
 * 
 * Array-based Stack (capacity 100):
 * - 100 × 4 bytes (int) = 400 bytes
 * - Fixed, even if only 10 elements used
 * 
 * Linked List Stack (10 elements):
 * - 10 × (4 bytes data + 8 bytes pointer) = 120 bytes
 * - Grows/shrinks with actual size
 * 
 * Break-even: When wasted array space > pointer overhead
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty Stack Pop:
 *    - top = null
 *    - Throw exception
 * 
 * 2. Single Element: 
 *    - top → [10|null]
 *    - After pop: top = null
 * 
 * 3. Large Stack:
 *    - Limited only by system memory
 *    - No overflow unless out of memory
 * 
 * 4. Repeated Push/Pop:
 *    - Each creates/removes node
 *    - GC handles cleanup
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Stack Implementation Using Linked List ===\n")
    
    val stack = StackUsingLL()
    
    // Test 1: Empty stack
    println("Test 1: Initial State")
    println("Is empty: ${stack.isEmpty()}")
    println("Size: ${stack.size()}")
    println()
    
    // Test 2: Push operations
    println("Test 2: Push Operations")
    stack.push(10)
    stack.push(20)
    stack.push(30)
    stack.push(40)
    stack.push(50)
    stack.display()
    println()
    
    // Test 3: Peek operation
    println("Test 3: Peek Operation")
    println("Top element: ${stack.peek()}")
    println("Size after peek: ${stack.size()}")  // Unchanged
    println()
    
    // Test 4: Pop operations
    println("Test 4: Pop Operations")
    stack.pop()
    stack.pop()
    stack.display()
    println()
    
    // Test 5: Search operation
    println("Test 5: Search Operation")
    println("Search for 30: position ${stack.search(30)}")  // 0 (at top)
    println("Search for 10: position ${stack.search(10)}")  // 2 (from top)
    println("Search for 100: position ${stack.search(100)}")  // -1 (not found)
    println()
    
    // Test 6: Mix operations
    println("Test 6: Mixed Operations")
    stack.push(60)
    stack.push(70)
    println("Peek: ${stack.peek()}")
    stack.pop()
    stack.display()
    println()
    
    // Test 7: Pop until empty
    println("Test 7: Pop Until Empty")
    while (!stack.isEmpty()) {
        stack.pop()
    }
    println("Stack empty: ${stack.isEmpty()}")
    println()
    
    // Test 8: Push after empty
    println("Test 8: Push After Empty")
    stack.push(100)
    stack.push(200)
    stack.display()
    println()
    
    // Test 9: Exception handling
    println("Test 9: Exception Handling")
    stack.clear()
    try {
        stack.pop()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    try {
        stack.peek()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    println()
    
    // Test 10: Large stack (dynamic size advantage)
    println("Test 10: Large Stack (Dynamic Size)")
    repeat(100) { stack.push(it) }
    println("Pushed 100 elements, size: ${stack.size()}")
    println("Top 5 elements: ${stack.toList().take(5)}")
    println("No overflow!  (Dynamic sizing works)")
}
