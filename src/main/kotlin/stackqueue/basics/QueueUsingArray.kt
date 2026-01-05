/**
 * ============================================================================
 * DATA STRUCTURE: Queue Implementation Using Array
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * WHAT IS A QUEUE?
 * A queue is a linear data structure that follows the First-In-First-Out (FIFO)
 * principle. The first element added is the first one to be removed. 
 * 
 * ANALOGY:
 * Think of a queue at a ticket counter: 
 * - People join at the rear (enqueue)
 * - People are served from the front (dequeue)
 * - First person in line is served first
 * - Can't skip the queue (FIFO order)
 * 
 * VISUAL REPRESENTATION:
 * ```
 * Front → [1] [2] [3] [4] [5] ← Rear
 *         ↑               ↑
 *      Remove here    Add here
 * ```
 * 
 * CORE OPERATIONS:
 * 1. **enqueue(x)**: Add element x to rear
 * 2. **dequeue()**: Remove and return front element
 * 3. **front()**: Return front element without removing
 * 4. **isEmpty()**: Check if queue is empty
 * 5. **isFull()**: Check if queue is full
 * 6. **size()**: Return number of elements
 * 
 * ============================================================================
 * WHY USE A QUEUE?
 * ============================================================================
 * 
 * APPLICATIONS:
 * - Task scheduling (CPU, print jobs)
 * - Breadth-First Search (BFS)
 * - Request handling (web servers)
 * - Message queues
 * - Call center systems
 * - Buffer for data streams
 * - Playlist management
 * 
 * ADVANTAGES:
 * ✅ Fair ordering (FIFO)
 * ✅ O(1) enqueue and dequeue (with circular array)
 * ✅ Predictable behavior
 * ✅ Efficient for sequential processing
 * 
 * DISADVANTAGES: 
 * ❌ No random access
 * ❌ Fixed size (if array-based)
 * ❌ Wasted space (if not circular)
 * 
 * ============================================================================
 * IMPLEMENTATION APPROACHES
 * ============================================================================
 * 
 * 1. **Linear Array (Naive)**:
 *    - Enqueue at rear, dequeue from front
 *    - Problem: Front space wasted after dequeue
 *    - Solution: Shift elements (inefficient O(n))
 * 
 * 2. **Circular Array** (Implemented here):
 *    - Use modulo to wrap around
 *    - Reuse front space
 *    - Efficient O(1) operations
 * 
 * 3. **Linked List**:
 *    - Dynamic size
 *    - No overflow (until memory full)
 * 
 * ============================================================================
 * CIRCULAR ARRAY CONCEPT
 * ============================================================================
 * 
 * KEY IDEA:
 * - When rear reaches end, wrap around to beginning
 * - Use modulo arithmetic:  (index + 1) % capacity
 * - Track front and rear pointers
 * - Track size to distinguish full vs empty
 * 
 * VISUAL: 
 * ```
 * Initial: capacity = 5
 * [_, _, _, _, _]
 *  ↑
 *  front, rear (size = 0)
 * 
 * After enqueue(1,2,3):
 * [1, 2, 3, _, _]
 *  ↑        ↑
 *  front    rear (size = 3)
 * 
 * After dequeue():
 * [_, 2, 3, _, _]
 *     ↑     ↑
 *     front rear (size = 2)
 * 
 * After enqueue(4,5,6):
 * [6, 2, 3, 4, 5]
 *     ↑  ↑
 *     front rear (size = 5) - Wrapped around! 
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * OPERATION     | TIME COMPLEXITY | SPACE COMPLEXITY
 * --------------|-----------------|------------------
 * enqueue()     | O(1)           | O(1)
 * dequeue()     | O(1)           | O(1)
 * front()       | O(1)           | O(1)
 * rear()        | O(1)           | O(1)
 * isEmpty()     | O(1)           | O(1)
 * isFull()      | O(1)           | O(1)
 * 
 * Overall Space: O(n) where n is capacity
 * 
 * ============================================================================
 */

package stackqueue.basics

/**
 * Queue implementation using circular array
 * 
 * @param capacity Maximum number of elements queue can hold
 */
class QueueUsingArray(private val capacity: Int) {
    // Array to store queue elements
    private val array = IntArray(capacity)
    
    // Index of front element
    private var front = 0
    
    // Index where next element will be inserted
    private var rear = 0
    
    // Current number of elements in queue
    // Needed to distinguish between empty and full queue
    private var currentSize = 0
    
    /**
     * Add element to rear of queue
     * TIME: O(1)
     * 
     * @param value Element to add
     * @throws IllegalStateException if queue is full
     */
    fun enqueue(value: Int) {
        // Check if queue is full
        if (isFull()) {
            throw IllegalStateException("Queue Overflow!  Cannot enqueue $value")
        }
        
        // Place element at rear position
        array[rear] = value
        
        // Move rear to next position (circular)
        // Modulo ensures wrapping:  (4+1) % 5 = 0
        rear = (rear + 1) % capacity
        
        // Increment size
        currentSize++
        
        println("Enqueued:  $value")
    }
    
    /**
     * Remove and return front element
     * TIME:  O(1)
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun dequeue(): Int {
        // Check if queue is empty
        if (isEmpty()) {
            throw IllegalStateException("Queue Underflow! Cannot dequeue from empty queue")
        }
        
        // Get element at front
        val value = array[front]
        
        // Move front to next position (circular)
        front = (front + 1) % capacity
        
        // Decrement size
        currentSize--
        
        println("Dequeued: $value")
        return value
    }
    
    /**
     * Get front element without removing
     * TIME: O(1)
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun front(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!")
        }
        
        return array[front]
    }
    
    /**
     * Get rear element without removing
     * TIME: O(1)
     * 
     * @return Rear element
     */
    fun rear(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!")
        }
        
        // Rear points to next empty slot, so we need previous element
        // (rear - 1 + capacity) handles wraparound
        val rearIndex = (rear - 1 + capacity) % capacity
        return array[rearIndex]
    }
    
    /**
     * Check if queue is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean {
        return currentSize == 0
    }
    
    /**
     * Check if queue is full
     * TIME: O(1)
     */
    fun isFull(): Boolean {
        return currentSize == capacity
    }
    
    /**
     * Get current size
     * TIME: O(1)
     */
    fun size(): Int {
        return currentSize
    }
    
    /**
     * Display queue contents
     * TIME: O(n)
     */
    fun display() {
        if (isEmpty()) {
            println("Queue is empty")
            return
        }
        
        print("Queue (front to rear): ")
        var i = front
        var count = 0
        
        // Traverse from front to rear (circular)
        while (count < currentSize) {
            print("[${array[i]}] ")
            i = (i + 1) % capacity
            count++
        }
        println()
        println("Front index: $front, Rear index: $rear, Size: $currentSize")
    }
    
    /**
     * Clear the queue
     * TIME: O(1)
     */
    fun clear() {
        front = 0
        rear = 0
        currentSize = 0
        println("Queue cleared")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Let's trace operations on a queue of capacity 5:
 * 
 * Initial: 
 * array = [_, _, _, _, _]
 * front = 0, rear = 0, size = 0
 * 
 * enqueue(10):
 * array[0] = 10
 * rear = (0+1) % 5 = 1
 * size = 1
 * array = [10, _, _, _, _]
 *          ↑   ↑
 *        front rear
 * 
 * enqueue(20):
 * array[1] = 20
 * rear = 2, size = 2
 * array = [10, 20, _, _, _]
 *          ↑       ↑
 *        front    rear
 * 
 * enqueue(30):
 * array = [10, 20, 30, _, _]
 *          ↑          ↑
 *        front       rear
 * 
 * dequeue():
 * value = array[0] = 10
 * front = 1, size = 2
 * array = [_, 20, 30, _, _]
 *              ↑       ↑
 *            front    rear
 * 
 * enqueue(40, 50, 60):
 * array = [60, 20, 30, 40, 50]
 *               ↑   ↑
 *             front rear (wrapped around!)
 * size = 5 (full)
 * 
 * dequeue() twice:
 * Remove 20, then 30
 * front = 3, size = 3
 * array = [60, _, _, 40, 50]
 *           ↑        ↑
 *         rear     front
 * 
 * ============================================================================
 * COMMON PITFALLS
 * ============================================================================
 * 
 * 1. **Forgetting Modulo**: Without %, indices go out of bounds
 * 2. **Empty vs Full**: Both can have front == rear! 
 *    Solution: Track size separately
 * 3. **Rear Calculation**: rear() needs (rear-1+capacity) % capacity
 * 4. **Initial State**: Set front = rear = 0, not -1
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Queue Implementation Using Circular Array ===\n")
    
    val queue = QueueUsingArray(5)
    
    // Test 1: Basic enqueue operations
    println("Test 1: Basic Enqueue")
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)
    queue.display()
    println("Front: ${queue.front()}, Rear: ${queue.rear()}")
    println()
    
    // Test 2: Dequeue operations
    println("Test 2: Dequeue")
    queue.dequeue()
    queue.dequeue()
    queue.display()
    println()
    
    // Test 3:  Circular behavior
    println("Test 3: Circular Wraparound")
    queue.enqueue(40)
    queue.enqueue(50)
    queue.enqueue(60)  // Wraps to index 0
    queue.display()
    println()
    
    // Test 4: Full queue
    println("Test 4: Full Queue")
    println("Is full:  ${queue.isFull()}")
    try {
        queue.enqueue(70)  // Should fail
    } catch (e: IllegalStateException) {
        println("Caught:  ${e.message}")
    }
    println()
    
    // Test 5: Empty queue
    println("Test 5: Empty Queue")
    repeat(5) { queue.dequeue() }
    println("Is empty: ${queue. isEmpty()}")
    try {
        queue.dequeue()  // Should fail
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    println()
    
    // Test 6: Refill after empty
    println("Test 6: Refill After Empty")
    queue.enqueue(100)
    queue.enqueue(200)
    queue.display()
    println()
    
    // Test 7: Real-world example - Task scheduler
    println("Test 7: Task Scheduler Simulation")
    taskScheduler()
}

/**
 * Example application: Simple task scheduler
 */
fun taskScheduler() {
    val taskQueue = QueueUsingArray(5)
    val tasks = listOf("Task1", "Task2", "Task3", "Task4", "Task5")
    
    println("Adding tasks to queue:")
    tasks.forEachIndexed { index, task ->
        println("  Scheduling:  $task")
        taskQueue.enqueue(index + 1)
    }
    
    println("\nProcessing tasks:")
    while (!taskQueue.isEmpty()) {
        val taskId = taskQueue.dequeue()
        println("  Executing: Task$taskId")
    }
}
