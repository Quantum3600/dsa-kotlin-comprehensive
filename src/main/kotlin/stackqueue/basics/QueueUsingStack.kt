/**
 * ============================================================================
 * PROBLEM:  Implement Queue Using Stacks
 * DIFFICULTY:  Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * Implement a queue data structure using only two stacks. The queue should
 * support all standard queue operations (enqueue, dequeue, front, isEmpty).
 * 
 * CONSTRAINT:
 * - Can only use stack operations:  push, pop, peek, isEmpty
 * - Must maintain FIFO (First-In-First-Out) order
 * 
 * WHY THIS PROBLEM? 
 * - Understanding relationship between data structures
 * - Stack (LIFO) vs Queue (FIFO) conversion
 * - Important interview question
 * - Tests understanding of both structures
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * KEY CHALLENGE:
 * - Stack is LIFO:  Last In, First Out
 * - Queue is FIFO: First In, First Out
 * - How to reverse order?  Use second stack! 
 * 
 * INTUITION:
 * Think of two stacks as an hourglass:
 * - Pour sand (elements) into first stack
 * - Flip hourglass:  elements reverse order
 * - Pour from second stack:  original order restored! 
 * 
 * TWO APPROACHES:
 * 
 * **Approach 1: Make Enqueue Costly (O(n))**
 * - Enqueue: Transfer all to stack2, push new, transfer back
 * - Dequeue: Just pop from stack1
 * 
 * **Approach 2: Make Dequeue Costly (O(n) amortized O(1))**
 * - Enqueue: Just push to stack1
 * - Dequeue:  If stack2 empty, transfer all from stack1, then pop
 * - This is MORE EFFICIENT (implemented here)
 * 
 * ============================================================================
 * VISUAL EXPLANATION
 * ============================================================================
 * 
 * Using Approach 2 (Dequeue costly):
 * 
 * enqueue(1):
 * stack1: [1]      stack2: []
 * 
 * enqueue(2):
 * stack1: [1,2]    stack2: []
 *         ↑ top
 * 
 * enqueue(3):
 * stack1: [1,2,3]  stack2: []
 *            ↑ top
 * 
 * dequeue() - First time:
 * Step 1: Transfer stack1 to stack2
 *   Pop 3 from stack1, push to stack2
 *   Pop 2 from stack1, push to stack2
 *   Pop 1 from stack1, push to stack2
 * 
 * stack1: []       stack2: [3,2,1]
 *                            ↑ top
 * 
 * Step 2: Pop from stack2
 * Result: 1 (correct FIFO!)
 * stack1: []       stack2: [3,2]
 * 
 * dequeue() - Second time:
 * No transfer needed!  Just pop from stack2
 * Result: 2
 * stack1: []       stack2: [3]
 * 
 * enqueue(4):
 * stack1: [4]      stack2: [3]
 * 
 * dequeue():
 * stack2 not empty, so pop from stack2
 * Result: 3
 * stack1: [4]      stack2: []
 * 
 * dequeue():
 * stack2 empty, transfer stack1 to stack2
 * stack1: []       stack2: [4]
 * Pop from stack2, Result: 4
 * 
 * ============================================================================
 * ALGORITHM STEPS
 * ============================================================================
 * 
 * enqueue(x):
 * 1. Push x to stack1
 * 
 * dequeue():
 * 1. If both stacks empty:  throw error
 * 2. If stack2 is empty: 
 *    a. While stack1 not empty: 
 *       - Pop from stack1
 *       - Push to stack2
 * 3. Pop and return from stack2
 * 
 * front():
 * 1. Same as dequeue, but use peek instead of pop
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - enqueue(): O(1) - Just push to stack1
 * 
 * - dequeue(): 
 *   * Worst case: O(n) - Transfer all elements
 *   * Best case: O(1) - stack2 already has elements
 *   * Amortized: O(1) - Each element moved at most twice
 * 
 * - front(): Same as dequeue()
 * 
 * AMORTIZED ANALYSIS:
 * - n enqueue operations:  n × O(1) = O(n)
 * - n dequeue operations: Each element pushed once, popped once = 2n = O(n)
 * - Total for n operations: O(n)
 * - Average per operation: O(1)
 * 
 * SPACE COMPLEXITY:  O(n)
 * - Two stacks, but only n total elements
 * - Elements are in one stack or the other, never duplicated
 * 
 * ============================================================================
 * WHY APPROACH 2 IS BETTER
 * ============================================================================
 * 
 * Approach 1 (Enqueue costly):
 * - enqueue:  O(n) always
 * - dequeue: O(1) always
 * - Total for n operations: O(n²)
 * 
 * Approach 2 (Dequeue costly):
 * - enqueue: O(1) always
 * - dequeue: O(1) amortized
 * - Total for n operations:  O(n)
 * 
 * Real-world:  Usually more enqueues than dequeues
 * So making enqueue faster is better! 
 * 
 * ============================================================================
 */

package stackqueue.basics

import java.util.Stack

/**
 * Queue implementation using two stacks
 * Approach:  Make dequeue costly (more efficient)
 */
class QueueUsingStack {
    // Stack for enqueue operations
    // Elements are pushed here
    private val stack1 = Stack<Int>()
    
    // Stack for dequeue operations
    // Elements are popped from here
    private val stack2 = Stack<Int>()
    
    /**
     * Add element to queue
     * TIME: O(1)
     * 
     * @param value Element to add
     */
    fun enqueue(value: Int) {
        // Simply push to stack1
        // This is O(1) operation
        stack1.push(value)
        println("Enqueued: $value")
    }
    
    /**
     * Remove and return front element
     * TIME: O(1) amortized, O(n) worst case
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun dequeue(): Int {
        // Check if both stacks are empty
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!  Cannot dequeue")
        }
        
        // If stack2 is empty, transfer all elements from stack1
        // This reverses the order, making oldest element on top
        if (stack2.isEmpty()) {
            transferStack1ToStack2()
        }
        
        // Pop from stack2 (this is the oldest element)
        val value = stack2.pop()
        println("Dequeued: $value")
        return value
    }
    
    /**
     * Get front element without removing
     * TIME: O(1) amortized, O(n) worst case
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun front(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!")
        }
        
        // If stack2 is empty, transfer elements
        if (stack2.isEmpty()) {
            transferStack1ToStack2()
        }
        
        // Peek at stack2 top (oldest element)
        return stack2.peek()
    }
    
    /**
     * Transfer all elements from stack1 to stack2
     * This reverses the order of elements
     * TIME: O(n) where n is size of stack1
     */
    private fun transferStack1ToStack2() {
        // Move each element from stack1 to stack2
        // This reverses the order
        while (!stack1.isEmpty()) {
            stack2.push(stack1.pop())
        }
    }
    
    /**
     * Check if queue is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean {
        // Queue is empty only if both stacks are empty
        return stack1.isEmpty() && stack2.isEmpty()
    }
    
    /**
     * Get current size of queue
     * TIME:  O(1)
     */
    fun size(): Int {
        // Total elements = elements in both stacks
        return stack1.size + stack2.size
    }
    
    /**
     * Display current state of queue
     * Shows both stacks and logical queue order
     */
    fun display() {
        if (isEmpty()) {
            println("Queue is empty")
            return
        }
        
        println("=== Internal State ===")
        println("Stack1 (newest): ${stack1.reversed()}")
        println("Stack2 (oldest): ${stack2.reversed()}")
        
        // Show logical queue order (front to rear)
        print("Logical Queue (front to rear): ")
        
        // Elements in stack2 come first (bottom to top)
        if (!stack2.isEmpty()) {
            val stack2List = stack2.toList().reversed()
            stack2List.forEach { print("[$it] ") }
        }
        
        // Then elements in stack1 (bottom to top)
        if (!stack1.isEmpty()) {
            val stack1List = stack1.toList()
            stack1List.forEach { print("[$it] ") }
        }
        println("\nSize: ${size()}")
    }
    
    /**
     * Clear the queue
     * TIME: O(1)
     */
    fun clear() {
        stack1.clear()
        stack2.clear()
        println("Queue cleared")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Operation Sequence:  enqueue(1,2,3), dequeue(), dequeue(), enqueue(4), dequeue()
 * 
 * Initial: 
 * stack1: []
 * stack2: []
 * 
 * enqueue(1):
 * stack1: [1]
 * stack2: []
 * Queue: [1]
 * 
 * enqueue(2):
 * stack1: [1,2] ← top
 * stack2: []
 * Queue: [1,2]
 * 
 * enqueue(3):
 * stack1: [1,2,3] ← top
 * stack2: []
 * Queue: [1,2,3]
 * 
 * dequeue():
 * - stack2 is empty, so transfer
 * - Pop 3,2,1 from stack1 → Push to stack2
 * stack1: []
 * stack2: [3,2,1] ← top
 * - Pop from stack2 → 1
 * stack1: []
 * stack2: [3,2] ← top
 * Queue:  [2,3]
 * Result: 1 ✓
 * 
 * dequeue():
 * - stack2 not empty, just pop
 * - Pop from stack2 → 2
 * stack1: []
 * stack2: [3] ← top
 * Queue: [3]
 * Result: 2 ✓
 * 
 * enqueue(4):
 * stack1: [4]
 * stack2: [3]
 * Queue: [3,4]
 * 
 * dequeue():
 * - stack2 not empty, pop from it
 * - Pop from stack2 → 3
 * stack1: [4]
 * stack2: []
 * Queue: [4]
 * Result: 3 ✓
 * 
 * ============================================================================
 * KEY INSIGHTS
 * ============================================================================
 * 
 * 1. **Lazy Transfer**: Don't transfer until needed (dequeue/front)
 * 2. **Amortized Analysis**: Each element moved at most once
 * 3. **Order Reversal**: Two reversals = original order
 *    - Stack1 reverses:  [1,2,3] → [3,2,1]
 *    - Transfer reverses again: [3,2,1] → [1,2,3]
 * 4. **Efficiency**:  Enqueue always O(1), dequeue amortized O(1)
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * - Understanding data structure conversions
 * - System design:  Converting between interfaces
 * - Undo/Redo with queuing
 * - Task scheduling with stack-based execution
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Queue Implementation Using Two Stacks ===\n")
    
    val queue = QueueUsingStack()
    
    // Test 1: Basic operations
    println("Test 1: Basic Enqueue and Dequeue")
    queue.enqueue(1)
    queue.enqueue(2)
    queue.enqueue(3)
    queue.display()
    println()
    
    // Test 2: Dequeue operations (triggers transfer)
    println("Test 2: Dequeue (Transfer happens)")
    queue.dequeue()
    queue.display()
    println()
    
    // Test 3: More dequeues (no transfer needed)
    println("Test 3: More Dequeues (No transfer)")
    queue.dequeue()
    queue.display()
    println()
    
    // Test 4: Mixed operations
    println("Test 4: Mixed Operations")
    queue.enqueue(4)
    queue.enqueue(5)
    queue.display()
    println("Front: ${queue.front()}")
    queue.dequeue()
    queue.display()
    println()
    
    // Test 5: Empty queue
    println("Test 5: Empty Queue")
    while (!queue.isEmpty()) {
        queue.dequeue()
    }
    println("Is empty: ${queue.isEmpty()}")
    println()
    
    // Test 6: Exception handling
    println("Test 6: Exception Handling")
    try {
        queue.dequeue()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    println()
    
    // Test 7: Enqueue after empty
    println("Test 7: Enqueue After Empty")
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)
    queue.display()
    println()
    
    // Test 8: Performance test
    println("Test 8: Performance Test (100 operations)")
    val perfQueue = QueueUsingStack()
    
    // Enqueue 50 elements
    repeat(50) { perfQueue.enqueue(it) }
    println("Enqueued 50 elements")
    
    // Dequeue 25 elements
    repeat(25) { perfQueue.dequeue() }
    println("Dequeued 25 elements")
    
    // Enqueue 25 more
    repeat(25) { perfQueue.enqueue(it + 50) }
    println("Enqueued 25 more elements")
    
    println("Final size: ${perfQueue.size()}")
    println("Front element: ${perfQueue.front()}")
}// TODO: Implement QueueUsingStack
