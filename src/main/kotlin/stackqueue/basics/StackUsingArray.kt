/**
 * ============================================================================
 * DATA STRUCTURE: Stack Implementation Using Array
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * WHAT IS A STACK?
 * A stack is a linear data structure that follows the Last-In-First-Out (LIFO)
 * principle. The last element added is the first one to be removed.
 * 
 * ANALOGY:
 * Think of a stack of plates:
 * - You add new plates on top (push)
 * - You remove plates from the top (pop)
 * - You can only see the top plate (peek)
 * - You can't access plates in the middle without removing those on top
 * 
 * VISUAL REPRESENTATION:
 * ```
 *   TOP → [5] ← Last added, first to remove
 *         [4]
 *         [3]
 *         [2]
 *         [1] ← First added, last to remove
 * ```
 * 
 * CORE OPERATIONS:
 * 1. **push(x)**: Add element x to top
 * 2. **pop()**: Remove and return top element
 * 3. **peek()**: Return top element without removing
 * 4. **isEmpty()**: Check if stack is empty
 * 5. **size()**: Return number of elements
 * 
 * ============================================================================
 * WHY USE A STACK?
 * ============================================================================
 * 
 * APPLICATIONS:
 * - Function call stack (recursion)
 * - Undo mechanism in editors
 * - Browser back button
 * - Expression evaluation (postfix, infix)
 * - Backtracking algorithms
 * - Balanced parentheses checking
 * - Depth-First Search (DFS)
 * - Syntax parsing
 * 
 * ADVANTAGES:
 * ✅ Simple and intuitive
 * ✅ O(1) push and pop operations
 * ✅ O(1) access to top element
 * ✅ Automatic memory management
 * 
 * DISADVANTAGES:
 * ❌ No random access (can't access middle elements)
 * ❌ Fixed size (if array-based)
 * ❌ Can overflow (if array full)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * OPERATION     | TIME COMPLEXITY | SPACE COMPLEXITY
 * --------------|-----------------|------------------
 * push()        | O(1)           | O(1)
 * pop()         | O(1)           | O(1)
 * peek()        | O(1)           | O(1)
 * isEmpty()     | O(1)           | O(1)
 * size()        | O(1)           | O(1)
 * search()      | O(n)           | O(1)
 * 
 * Overall Space: O(n) where n is maximum capacity
 * 
 * ============================================================================
 * IMPLEMENTATION APPROACHES
 * ============================================================================
 * 
 * 1. **Array-Based**: Fixed size, simple, fast (implemented here)
 * 2. **Dynamic Array**: Resizable, more flexible
 * 3. **Linked List**: Dynamic size, no size limit
 * 4. **Using Collections**: Built-in Stack class
 * 
 * ============================================================================
 */

package stackqueue.basics

/**
 * Stack implementation using a fixed-size array
 * 
 * @param capacity Maximum number of elements stack can hold
 */
class StackUsingArray(private val capacity: Int) {
    // Array to store stack elements
    private val array = IntArray(capacity)
    
    // Top pointer: index of top element
    // -1 indicates empty stack
    private var top = -1
    
    /**
     * Push element onto stack
     * TIME: O(1) - Direct array access
     * 
     * @param value Element to push
     * @throws IllegalStateException if stack is full
     */
    fun push(value: Int) {
        // Check for stack overflow
        // If top reaches capacity-1, stack is full
        if (isFull()) {
            throw IllegalStateException("Stack Overflow! Cannot push $value")
        }
        
        // Increment top to next position
        top++
        
        // Place element at top position
        array[top] = value
        
        println("Pushed: $value")
    }
    
    /**
     * Pop element from stack
     * TIME: O(1) - Direct array access
     * 
     * @return Popped element
     * @throws IllegalStateException if stack is empty
     */
    fun pop(): Int {
        // Check for stack underflow
        // If top is -1, stack is empty
        if (isEmpty()) {
            throw IllegalStateException("Stack Underflow! Cannot pop from empty stack")
        }
        
        // Get element at top
        val poppedValue = array[top]
        
        // Decrement top pointer
        // This effectively removes the element
        // (we don't need to actually delete from array)
        top--
        
        println("Popped: $poppedValue")
        return poppedValue
    }
    
    /**
     * Peek at top element without removing
     * TIME: O(1)
     * 
     * @return Top element
     * @throws IllegalStateException if stack is empty
     */
    fun peek(): Int {
        // Check if stack is empty
        if (isEmpty()) {
            throw IllegalStateException("Stack is empty! Cannot peek")
        }
        
        // Return element at top (don't modify top)
        return array[top]
    }
    
    /**
     * Check if stack is empty
     * TIME: O(1)
     * 
     * @return true if empty, false otherwise
     */
    fun isEmpty(): Boolean {
        // Stack is empty when top is -1
        return top == -1
    }
    
    /**
     * Check if stack is full
     * TIME: O(1)
     * 
     * @return true if full, false otherwise
     */
    fun isFull(): Boolean {
        // Stack is full when top reaches capacity-1
        return top == capacity - 1
    }
    
    /**
     * Get current size of stack
     * TIME: O(1)
     * 
     * @return Number of elements in stack
     */
    fun size(): Int {
        // Size = top + 1
        // (top is 0-indexed, so add 1 for count)
        return top + 1
    }
    
    /**
     * Display all elements in stack
     * TIME: O(n)
     */
    fun display() {
        if (isEmpty()) {
            println("Stack is empty")
            return
        }
        
        println("Stack (top to bottom):")
        // Print from top to bottom
        for (i in top downTo 0) {
            println("  │ ${array[i]} │")
        }
        println("  └───┘")
    }
    
    /**
     * Search for an element in stack
     * TIME: O(n) - May need to check all elements
     * 
     * @param value Element to search
     * @return Position from top (0-indexed), or -1 if not found
     */
    fun search(value: Int): Int {
        // Search from top to bottom
        for (i in top downTo 0) {
            if (array[i] == value) {
                // Return distance from top
                return top - i
            }
        }
        return -1  // Not found
    }
    
    /**
     * Clear the stack
     * TIME: O(1) - Just reset top pointer
     */
    fun clear() {
        top = -1
        println("Stack cleared")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's trace operations on a stack of capacity 5:
 * 
 * Initial State:
 * - array = [_, _, _, _, _]
 * - top = -1
 * - Stack is empty
 * 
 * push(10):
 * - top++ → top = 0
 * - array[0] = 10
 * - array = [10, _, _, _, _]
 * - Stack: [10] ← top
 * 
 * push(20):
 * - top++ → top = 1
 * - array[1] = 20
 * - array = [10, 20, _, _, _]
 * - Stack: [20] ← top
 *          [10]
 * 
 * push(30):
 * - top++ → top = 2
 * - array[2] = 30
 * - Stack: [30] ← top
 *          [20]
 *          [10]
 * 
 * peek():
 * - return array[2] = 30
 * - top unchanged
 * - Stack: [30] ← top (still here)
 *          [20]
 *          [10]
 * 
 * pop():
 * - value = array[2] = 30
 * - top-- → top = 1
 * - return 30
 * - Stack: [20] ← top (moved down)
 *          [10]
 * 
 * pop():
 * - value = array[1] = 20
 * - top-- → top = 0
 * - return 20
 * - Stack: [10] ← top
 * 
 * isEmpty():
 * - top = 0, not -1
 * - return false
 * 
 * pop():
 * - value = array[0] = 10
 * - top-- → top = -1
 * - return 10
 * - Stack: empty
 * 
 * isEmpty():
 * - top = -1
 * - return true
 * 
 * ============================================================================
 * REAL-WORLD EXAMPLES
 * ============================================================================
 * 
 * 1. FUNCTION CALL STACK:
 * ```
 * fun main() {      Stack: [main]
 *   foo()           Stack: [foo, main]
 * }                 Stack: [main]
 * 
 * fun foo() {
 *   bar()           Stack: [bar, foo, main]
 * }                 Stack: [foo, main]
 * 
 * fun bar() {
 *   // code
 * }                 Stack: [foo, main]
 * ```
 * 
 * 2. UNDO MECHANISM:
 * - Each action pushed onto stack
 * - Undo pops last action
 * - Can't undo middle actions without undoing recent ones
 * 
 * 3. BALANCED PARENTHESES:
 * - Push opening brackets: (, [, {
 * - Pop when finding closing brackets
 * - If pop matches, continue
 * - If stack empty at end, balanced!
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Stack Implementation Using Array ===\n")
    
    // Create stack with capacity 5
    val stack = StackUsingArray(5)
    
    // Test 1: Check initial state
    println("Test 1: Initial State")
    println("Is empty: ${stack.isEmpty()}")  // true
    println("Is full: ${stack.isFull()}")    // false
    println("Size: ${stack.size()}")         // 0
    println()
    
    // Test 2: Push operations
    println("Test 2: Push Operations")
    stack.push(10)
    stack.push(20)
    stack.push(30)
    stack.push(40)
    stack.push(50)
    println("Size after pushes: ${stack.size()}")  // 5
    println("Is full: ${stack.isFull()}")          // true
    stack.display()
    println()
    
    // Test 3: Peek operation
    println("Test 3: Peek Operation")
    println("Top element: ${stack.peek()}")  // 50
    println("Size after peek: ${stack.size()}")  // Still 5
    println()
    
    // Test 4: Pop operations
    println("Test 4: Pop Operations")
    stack.pop()  // 50
    stack.pop()  // 40
    println("Size after 2 pops: ${stack.size()}")  // 3
    stack.display()
    println()
    
    // Test 5: Search operation
    println("Test 5: Search Operation")
    println("Search for 30: position ${stack.search(30)}")  // 0 (at top)
    println("Search for 10: position ${stack.search(10)}")  // 2 (from top)
    println("Search for 100: position ${stack.search(100)}")  // -1 (not found)
    println()
    
    // Test 6: Push after pops
    println("Test 6: Push After Pops")
    stack.push(60)
    stack.push(70)
    println("Is full: ${stack.isFull()}")  // true
    stack.display()
    println()
    
    // Test 7: Clear stack
    println("Test 7: Clear Stack")
    stack.clear()
    println("Is empty after clear: ${stack.isEmpty()}")  // true
    println("Size after clear: ${stack.size()}")  // 0
    println()
    
    // Test 8: Exception handling
    println("Test 8: Exception Handling")
    
    // Try to pop from empty stack
    try {
        stack.pop()
    } catch (e: IllegalStateException) {
        println("Caught exception: ${e.message}")
    }
    
    // Fill stack and try overflow
    repeat(5) { stack.push(it) }
    try {
        stack.push(99)  // Should cause overflow
    } catch (e: IllegalStateException) {
        println("Caught exception: ${e.message}")
    }
    println()
    
    // Test 9: Practical example - balanced parentheses
    println("Test 9: Practical Example - Balanced Parentheses")
    println("Expression: ((()))")
    println("Is balanced: ${isBalanced("((()))")}")  // true
    println("\nExpression: (()())")
    println("Is balanced: ${isBalanced("(()())")}")  // true
    println("\nExpression: (()")
    println("Is balanced: ${isBalanced("(()")}")     // false
}

/**
 * Example application: Check if parentheses are balanced
 * Using stack to match opening and closing parentheses
 */
fun isBalanced(expression: String): Boolean {
    val stack = StackUsingArray(expression.length)
    
    for (char in expression) {
        when (char) {
            '(' -> stack.push(1)  // Push for opening
            ')' -> {
                if (stack.isEmpty()) return false  // No matching opening
                stack.pop()  // Pop matching opening
            }
        }
    }
    
    // Balanced if stack is empty (all matched)
    return stack.isEmpty()
}
