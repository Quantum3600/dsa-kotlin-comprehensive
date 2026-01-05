/**
 * ============================================================================
 * PROBLEM:  Implement Stack Using Queues
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement a stack data structure using only queues.  The stack should
 * support all standard stack operations (push, pop, top, isEmpty).
 * 
 * CONSTRAINT:
 * - Can only use queue operations:  enqueue, dequeue, front, isEmpty
 * - Must maintain LIFO (Last-In-First-Out) order
 * 
 * WHY THIS PROBLEM? 
 * - Understanding data structure relationships
 * - Queue (FIFO) vs Stack (LIFO) conversion
 * - Important interview question (reverse of queue using stacks)
 * - Tests understanding of both structures
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY CHALLENGE:
 * - Queue is FIFO:  First In, First Out
 * - Stack is LIFO: Last In, First Out
 * - How to reverse order using FIFO structure?
 * 
 * TWO MAIN APPROACHES:
 * 
 * **Approach 1: Make Push Costly (Using Two Queues)**
 * - Push:  O(n) - Move all elements to reverse order
 * - Pop: O(1) - Just dequeue
 * 
 * **Approach 2: Make Pop Costly (Using One Queue)**
 * - Push: O(1) - Just enqueue
 * - Pop:  O(n) - Rotate queue n-1 times
 * - This is MORE EFFICIENT (uses one queue, implemented here)
 * 
 * ============================================================================
 * VISUAL EXPLANATION - APPROACH 2 (ONE QUEUE)
 * ============================================================================
 * 
 * KEY INSIGHT:  Rotate queue to bring last element to front! 
 * 
 * push(1):
 * queue: [1]
 * Stack view: [1] ← top
 * 
 * push(2):
 * Step 1: Enqueue 2
 * queue: [1, 2]
 * 
 * Step 2: Rotate (size-1) = 1 time
 * - Dequeue 1, enqueue 1
 * queue: [2, 1]
 * Stack view: [2, 1] ← top (2 is at front, so it's on "top")
 * 
 * push(3):
 * Step 1: Enqueue 3
 * queue: [2, 1, 3]
 * 
 * Step 2: Rotate (size-1) = 2 times
 * - Dequeue 2, enqueue 2: [1, 3, 2]
 * - Dequeue 1, enqueue 1: [3, 2, 1]
 * Stack view: [3, 2, 1] ← top (3 is at front)
 * 
 * pop():
 * - Just dequeue front: 3
 * queue: [2, 1]
 * Result: 3 (correct LIFO!)
 * 
 * push(4):
 * Step 1: Enqueue 4
 * queue: [2, 1, 4]
 * 
 * Step 2: Rotate 2 times
 * - Dequeue 2, enqueue 2: [1, 4, 2]
 * - Dequeue 1, enqueue 1: [4, 2, 1]
 * Stack view:  [4, 2, 1] ← top
 * 
 * top():
 * - Return front: 4
 * queue:  [4, 2, 1] (unchanged)
 * 
 * ============================================================================
 * ALGORITHM STEPS
 * ============================================================================
 * 
 * push(x):
 * 1. Enqueue x to queue
 * 2. Rotate queue (size-1) times:
 *    - For i from 0 to size-2:
 *      * Dequeue front element
 *      * Enqueue it back
 *    - This brings newest element to front
 * 
 * pop():
 * 1. If queue empty:  throw error
 * 2. Dequeue and return (front is the "top")
 * 
 * top():
 * 1. If queue empty: throw error
 * 2. Return front element (don't remove)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * Using Approach 2 (One Queue, Push Costly):
 * 
 * TIME COMPLEXITY:
 * - push(): O(n) - Must rotate n-1 elements
 * - pop(): O(1) - Just dequeue
 * - top(): O(1) - Just peek at front
 * - isEmpty(): O(1)
 * 
 * SPACE COMPLEXITY:  O(n)
 * - Only one queue with n elements
 * - More space-efficient than two-queue approach
 * 
 * ============================================================================
 * WHY THIS APPROACH? 
 * ============================================================================
 * 
 * Alternative:  Two Queues (Push Costly)
 * - Space:  O(n) for two queues
 * - Push: O(n) - Transfer between queues
 * - Pop:  O(1)
 * - More complex logic
 * 
 * Our Approach:  One Queue (Push Costly)
 * - Space: O(n) for one queue ✓ More efficient
 * - Push: O(n) - Rotate elements
 * - Pop: O(1)
 * - Simpler logic ✓
 * 
 * Trade-off: 
 * - If more pushes:  This approach is slower
 * - If more pops: This approach is faster
 * - Real-world:  Usually balanced, so either works
 * - Our approach wins on simplicity and space
 * 
 * ============================================================================
 * COMPARISON WITH OTHER APPROACH
 * ============================================================================
 * 
 * Two Queues Approach:
 * ```
 * push(x):
 *   1. Enqueue x to queue2
 *   2. Move all from queue1 to queue2
 *   3. Swap queue1 and queue2
 * 
 * pop():
 *   1. Dequeue from queue1
 * ```
 * 
 * One Queue Approach (Ours):
 * ```
 * push(x):
 *   1. Enqueue x
 *   2. Rotate n-1 times
 * 
 * pop():
 *   1. Dequeue
 * ```
 * 
 * Both have same time complexity, but ours is simpler! 
 * 
 * ============================================================================
 */

package stackqueue.basics

import java.util.LinkedList
import java.util.Queue

/**
 * Stack implementation using one queue
 * Approach:  Make push costly (rotate queue after each push)
 */
class StackUsingQueue {
    // Single queue to implement stack
    // Front of queue represents top of stack
    private val queue: Queue<Int> = LinkedList()
    
    /**
     * Push element onto stack
     * TIME: O(n) - Must rotate n-1 elements
     * 
     * @param value Element to push
     */
    fun push(value: Int) {
        // Get current size before adding
        val currentSize = queue. size
        
        // Step 1: Add new element to queue
        queue.offer(value)
        
        // Step 2: Rotate queue to bring new element to front
        // Move all previous elements (currentSize) to back
        // This makes the new element the "top" of stack
        repeat(currentSize) {
            queue.offer(queue.poll())
        }
        
        println("Pushed:  $value")
    }
    
    /**
     * Pop element from stack
     * TIME: O(1) - Just dequeue
     * 
     * @return Popped element
     * @throws IllegalStateException if stack is empty
     */
    fun pop(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Stack Underflow!  Cannot pop from empty stack")
        }
        
        // Front of queue is top of stack
        val value = queue.poll()
        println("Popped: $value")
        return value
    }
    
    /**
     * Get top element without removing
     * TIME: O(1)
     * 
     * @return Top element
     * @throws IllegalStateException if stack is empty
     */
    fun top(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Stack is empty!  Cannot peek")
        }
        
        // Peek at front (this is stack top)
        return queue.peek()
    }
    
    /**
     * Check if stack is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
    
    /**
     * Get current size of stack
     * TIME: O(1)
     */
    fun size(): Int {
        return queue.size
    }
    
    /**
     * Display stack contents
     * Shows internal queue and logical stack representation
     */
    fun display() {
        if (isEmpty()) {
            println("Stack is empty")
            return
        }
        
        println("Internal Queue (front to rear):")
        println("  ${queue.toList()}")
        
        println("\nLogical Stack (top to bottom):")
        queue.toList().forEach { value ->
            println("  │ $value │")
        }
        println("  └───┘")
        println("Size: ${size()}")
    }
    
    /**
     * Clear the stack
     * TIME: O(1)
     */
    fun clear() {
        queue.clear()
        println("Stack cleared")
    }
    
    /**
     * Search for element in stack
     * TIME: O(n)
     * 
     * @param value Element to search
     * @return Position from top (0-indexed), or -1 if not found
     */
    fun search(value:  Int): Int {
        return queue.toList().indexOf(value).let {
            if (it == -1) -1 else it
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - DETAILED
 * ============================================================================
 * 
 * Let's trace push operations step by step: 
 * 
 * Initial: 
 * queue:  []
 * 
 * push(1):
 * - currentSize = 0
 * - Enqueue 1: [1]
 * - Rotate 0 times (no rotation needed)
 * - Final:  [1]
 * - Stack: [1] ← top
 * 
 * push(2):
 * - currentSize = 1
 * - Enqueue 2: [1, 2]
 * - Rotate 1 time: 
 *   * Dequeue 1, Enqueue 1: [2, 1]
 * - Final: [2, 1]
 * - Stack: [2, 1] ← top
 * 
 * push(3):
 * - currentSize = 2
 * - Enqueue 3: [2, 1, 3]
 * - Rotate 2 times:
 *   * Iteration 1: Dequeue 2, Enqueue 2: [1, 3, 2]
 *   * Iteration 2: Dequeue 1, Enqueue 1: [3, 2, 1]
 * - Final: [3, 2, 1]
 * - Stack: [3, 2, 1] ← top
 * 
 * top():
 * - Return front = 3
 * - queue unchanged:  [3, 2, 1]
 * 
 * pop():
 * - Dequeue:  3
 * - queue: [2, 1]
 * - Stack: [2, 1] ← top
 * 
 * push(4):
 * - currentSize = 2
 * - Enqueue 4: [2, 1, 4]
 * - Rotate 2 times:
 *   * Iteration 1: [1, 4, 2]
 *   * Iteration 2: [4, 2, 1]
 * - Final: [4, 2, 1]
 * - Stack:  [4, 2, 1] ← top
 * 
 * ============================================================================
 * KEY INSIGHTS
 * ============================================================================
 * 
 * 1. **Rotation Magic**: Moving elements to back brings new element to front
 * 2. **LIFO from FIFO**: Reordering after each push maintains LIFO
 * 3. **Front = Top**: Queue front always represents stack top
 * 4. **Lazy Approach**: Could also make pop costly instead of push
 * 5. **Trade-off**: Push O(n) but pop O(1) vs push O(1) but pop O(n)
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * - Understanding data structure relationships
 * - Interview problems on structure conversion
 * - System design:  Adapting interfaces
 * - Message queues with stack-like processing
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Stack Implementation Using Queue ===\n")
    
    val stack = StackUsingQueue()
    
    // Test 1: Basic push operations
    println("Test 1: Basic Push Operations")
    stack.push(1)
    stack.push(2)
    stack.push(3)
    stack.display()
    println()
    
    // Test 2: Top operation
    println("Test 2: Top Operation")
    println("Top element: ${stack.top()}")
    println("Size: ${stack.size()}")
    stack.display()
    println()
    
    // Test 3: Pop operations
    println("Test 3: Pop Operations")
    stack.pop()
    stack.display()
    stack.pop()
    stack.display()
    println()
    
    // Test 4: Mixed operations
    println("Test 4: Mixed Operations")
    stack.push(4)
    stack.push(5)
    println("Top:  ${stack.top()}")
    stack.display()
    stack.pop()
    stack.push(6)
    stack.display()
    println()
    
    // Test 5: Empty stack
    println("Test 5: Empty Stack")
    while (!stack.isEmpty()) {
        stack.pop()
    }
    println("Is empty: ${stack.isEmpty()}")
    println()
    
    // Test 6: Exception handling
    println("Test 6: Exception Handling")
    try {
        stack.pop()
    } catch (e: IllegalStateException) {
        println("Caught:  ${e.message}")
    }
    try {
        stack.top()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    println()
    
    // Test 7: Push after empty
    println("Test 7: Push After Empty")
    stack.push(10)
    stack.push(20)
    stack.push(30)
    stack.display()
    println()
    
    // Test 8: Search operation
    println("Test 8: Search Operation")
    println("Search 30: position ${stack.search(30)}")  // 0 (top)
    println("Search 10: position ${stack.search(10)}")  // 2
    println("Search 100: position ${stack.search(100)}")  // -1
    println()
    
    // Test 9: Performance test
    println("Test 9: Performance Test")
    val perfStack = StackUsingQueue()
    
    // Push 10 elements
    repeat(10) { perfStack.push(it) }
    println("Pushed 10 elements")
    println("Top element: ${perfStack.top()}")
    
    // Pop 5 elements
    repeat(5) { perfStack.pop() }
    println("After 5 pops, top:  ${perfStack.top()}")
    println("Size: ${perfStack.size()}")
}
