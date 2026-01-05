/**
 * ============================================================================
 * PROBLEM: Min Stack (Design a Stack with O(1) minimum operation)
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Design a stack that supports push, pop, top, and retrieving the minimum
 * element in constant time O(1).
 * 
 * OPERATIONS TO IMPLEMENT:
 * 1. push(x): Push element x onto stack
 * 2. pop(): Remove element on top of stack
 * 3. top(): Get the top element
 * 4. getMin(): Retrieve the minimum element in the stack
 * 
 * INPUT FORMAT:
 * - Operations:  push(x), pop(), top(), getMin()
 * - Values: Any integer
 * 
 * OUTPUT FORMAT:
 * - For top(): Return top element
 * - For getMin(): Return minimum element
 * 
 * CONSTRAINTS:
 * - All operations must be O(1) time complexity
 * - Methods pop, top, and getMin will always be called on non-empty stacks
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The challenge is maintaining minimum in O(1) time. A naive approach would
 * scan the entire stack (O(n)), but we can do better by maintaining a parallel
 * stack that tracks minimums at each level.
 * 
 * KEY INSIGHT:
 * - When we push a new element, the minimum is either the new element or
 *   the previous minimum (whichever is smaller)
 * - When we pop, we also pop from the min stack to maintain sync
 * - This way, the top of minStack always has current minimum
 * 
 * ALGORITHM STEPS:
 * 1. Maintain two stacks: mainStack and minStack
 * 2. Push operation: 
 *    - Push value to mainStack
 *    - Push min(value, current minimum) to minStack
 * 3. Pop operation:
 *    - Pop from both stacks simultaneously
 * 4. GetMin operation:
 *    - Return top of minStack
 * 
 * VISUAL EXAMPLE:
 * ```
 * push(5):
 * mainStack: [5]      minStack: [5]     min = 5
 * 
 * push(3):
 * mainStack: [5,3]    minStack: [5,3]   min = 3
 * 
 * push(7):
 * mainStack: [5,3,7]  minStack: [5,3,3] min = 3 (7 > 3, so duplicate 3)
 * 
 * push(2):
 * mainStack: [5,3,7,2] minStack: [5,3,3,2] min = 2
 * 
 * pop():
 * mainStack: [5,3,7]  minStack: [5,3,3]   min = 3
 * 
 * pop():
 * mainStack: [5,3]    minStack: [5,3]     min = 3
 * ```
 * 
 * ALTERNATIVE APPROACHES:
 * 1. **Single Stack with Pairs**: Store (value, min) pairs
 *    - Pro: Single data structure
 *    - Con: More complex, same space complexity
 * 
 * 2. **Modified Push (Space Optimized)**: Only push to minStack when new minimum
 *    - Pro:  Less space when many duplicates
 *    - Con:  More complex pop logic, needs counter or different approach
 * 
 * 3. **Store Differences**: Store difference from minimum
 *    - Pro: Can be more space efficient
 *    - Con:  Complex implementation, harder to understand
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(1) for all operations
 * - push(): O(1) - Direct stack operations
 * - pop(): O(1) - Direct stack operations
 * - top(): O(1) - Direct access to top
 * - getMin(): O(1) - Direct access to minStack top
 * 
 * SPACE COMPLEXITY: O(n)
 * - mainStack: O(n) where n is number of elements
 * - minStack: O(n) in worst case (all elements are decreasing)
 * - Total: O(2n) = O(n)
 * 
 * ============================================================================
 */

package stackqueue.basics

import java.util.Stack

/**
 * MinStack implementation using two stacks
 * One stack for actual values, another for tracking minimums
 */
class MinStack {
    // Main stack to store all elements
    private val mainStack = Stack<Int>()
    
    // Auxiliary stack to store minimum at each level
    // Top of this stack always contains current minimum
    private val minStack = Stack<Int>()
    
    /**
     * Push element onto stack
     * TIME: O(1)
     * 
     * @param value Element to push
     */
    fun push(value: Int) {
        // Always push to main stack
        mainStack.push(value)
        
        // For min stack: 
        // - If empty, this is the first and minimum element
        // - Otherwise, push the smaller of new value and current minimum
        if (minStack.isEmpty()) {
            minStack.push(value)
        } else {
            // Current minimum is at top of minStack
            val currentMin = minStack.peek()
            // Push the smaller value (keeps minimum at top)
            minStack. push(minOf(value, currentMin))
        }
    }
    
    /**
     * Remove top element from stack
     * TIME:  O(1)
     * 
     * Both stacks must be popped to maintain synchronization
     */
    fun pop() {
        // Check if stack is not empty
        if (mainStack.isEmpty()) {
            throw IllegalStateException("Stack is empty")
        }
        
        // Pop from both stacks to maintain sync
        mainStack.pop()
        minStack.pop()
    }
    
    /**
     * Get top element without removing
     * TIME: O(1)
     * 
     * @return Top element
     */
    fun top(): Int {
        if (mainStack.isEmpty()) {
            throw IllegalStateException("Stack is empty")
        }
        
        // Return top of main stack
        return mainStack.peek()
    }
    
    /**
     * Get minimum element in stack
     * TIME: O(1)
     * 
     * @return Minimum element in entire stack
     */
    fun getMin(): Int {
        if (minStack.isEmpty()) {
            throw IllegalStateException("Stack is empty")
        }
        
        // Top of minStack always contains current minimum
        return minStack.peek()
    }
    
    /**
     * Check if stack is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean = mainStack.isEmpty()
    
    /**
     * Get current size of stack
     * TIME: O(1)
     */
    fun size(): Int = mainStack.size
    
    /**
     * Display current state of both stacks
     */
    fun display() {
        if (isEmpty()) {
            println("Stack is empty")
            return
        }
        
        println("Main Stack | Min Stack")
        println("-----------|----------")
        val mainList = mainStack.toList()
        val minList = minStack.toList()
        
        for (i in mainList. indices. reversed()) {
            println("   ${mainList[i]. toString().padStart(3)}     |   ${minList[i].toString().padStart(3)}")
        }
        println("\nCurrent Min: ${getMin()}")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's trace a sequence of operations:
 * 
 * Initial State:
 * mainStack: []
 * minStack: []
 * 
 * push(5):
 * - mainStack. push(5) → [5]
 * - minStack is empty, push(5) → [5]
 * - getMin() = 5
 * 
 * push(3):
 * - mainStack.push(3) → [5, 3]
 * - min(3, 5) = 3, minStack. push(3) → [5, 3]
 * - getMin() = 3
 * 
 * push(7):
 * - mainStack.push(7) → [5, 3, 7]
 * - min(7, 3) = 3, minStack.push(3) → [5, 3, 3]
 * - getMin() = 3 (still 3, not 7!)
 * 
 * push(2):
 * - mainStack.push(2) → [5, 3, 7, 2]
 * - min(2, 3) = 2, minStack.push(2) → [5, 3, 3, 2]
 * - getMin() = 2
 * 
 * top():
 * - return mainStack.peek() = 2
 * - Stacks unchanged
 * 
 * pop():
 * - mainStack.pop() → [5, 3, 7]
 * - minStack.pop() → [5, 3, 3]
 * - getMin() = 3 (back to previous minimum)
 * 
 * pop():
 * - mainStack.pop() → [5, 3]
 * - minStack.pop() → [5, 3]
 * - getMin() = 3
 * 
 * pop():
 * - mainStack.pop() → [5]
 * - minStack.pop() → [5]
 * - getMin() = 5
 * 
 * ============================================================================
 * WHY TWO STACKS?
 * ============================================================================
 * 
 * PROBLEM with single stack:
 * - If we only track one global minimum, what happens when we pop it?
 * - We'd need to scan entire stack to find new minimum (O(n))
 * 
 * SOLUTION with minStack:
 * - At each level, we know what the minimum was up to that point
 * - When we pop, the new minimum is automatically available
 * - No scanning needed! 
 * 
 * TRADE-OFF:
 * - We use 2x space (O(n) extra)
 * - But we get O(1) time for all operations
 * - This is a classic space-time tradeoff
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== MinStack Implementation ===\n")
    
    val minStack = MinStack()
    
    // Test 1: Basic operations
    println("Test 1: Basic Push Operations")
    minStack.push(5)
    println("Pushed 5, min = ${minStack.getMin()}")  // 5
    
    minStack.push(3)
    println("Pushed 3, min = ${minStack.getMin()}")  // 3
    
    minStack.push(7)
    println("Pushed 7, min = ${minStack.getMin()}")  // 3 (not 7!)
    
    minStack.push(2)
    println("Pushed 2, min = ${minStack.getMin()}")  // 2
    
    minStack.display()
    println()
    
    // Test 2: Top operation
    println("Test 2: Top Operation")
    println("Top element: ${minStack.top()}")  // 2
    println("Min after top: ${minStack.getMin()}")  // 2 (unchanged)
    println()
    
    // Test 3: Pop operations
    println("Test 3: Pop Operations")
    minStack.pop()
    println("After pop, min = ${minStack.getMin()}")  // 3
    
    minStack.pop()
    println("After pop, min = ${minStack.getMin()}")  // 3
    
    minStack.display()
    println()
    
    // Test 4: More pops
    println("Test 4: More Pops")
    minStack.pop()
    println("After pop, min = ${minStack.getMin()}")  // 5
    
    minStack.pop()
    println("Stack empty:  ${minStack.isEmpty()}")  // true
    println()
    
    // Test 5: Negative numbers
    println("Test 5: Negative Numbers")
    minStack.push(-2)
    minStack.push(0)
    minStack.push(-3)
    println("Top: ${minStack.top()}")  // -3
    println("Min: ${minStack.getMin()}")  // -3
    
    minStack.pop()
    println("After pop, min = ${minStack.getMin()}")  // -2
    
    minStack.push(-5)
    println("After push(-5), min = ${minStack.getMin()}")  // -5
    
    minStack. display()
    println()
    
    // Test 6: Duplicates
    println("Test 6: Duplicate Values")
    val stack2 = MinStack()
    stack2.push(1)
    stack2.push(1)
    stack2.push(1)
    stack2.display()
    println("All minimums are 1")
    println()
    
    // Test 7:  Decreasing sequence
    println("Test 7: Decreasing Sequence")
    val stack3 = MinStack()
    stack3.push(5)
    stack3.push(4)
    stack3.push(3)
    stack3.push(2)
    stack3.push(1)
    stack3.display()
    println("Min at each level decreases")
}// TODO: Implement MinStack
