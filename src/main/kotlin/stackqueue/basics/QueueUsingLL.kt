/**
 * ============================================================================
 * DATA STRUCTURE: Queue Implementation Using Linked List
 * DIFFICULTY: Easy
 * CATEGORY: Stack & Queue - Basics
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement a queue using a singly linked list with all basic queue operations. 
 * 
 * WHY LINKED LIST OVER ARRAY?
 * 
 * ARRAY LIMITATIONS:
 * ❌ Fixed size (must be declared upfront)
 * ❌ Overflow when full
 * ❌ Wasted space with circular array
 * 
 * LINKED LIST ADVANTAGES:
 * ✅ Dynamic size (grows/shrinks as needed)
 * ✅ No overflow (until system memory full)
 * ✅ No wasted space
 * ✅ Efficient insertion/deletion
 * 
 * LINKED LIST DISADVANTAGES: 
 * ❌ Extra memory for pointers
 * ❌ No random access
 * ❌ Cache locality issues
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * STRUCTURE:
 * - Each node contains:  data + next pointer
 * - Maintain head (front) and tail (rear) pointers
 * - Enqueue at tail, dequeue from head
 * 
 * VISUAL:
 * ```
 * Empty Queue: 
 * head = null, tail = null
 * 
 * After enqueue(10):
 * head → [10|null] ← tail
 * 
 * After enqueue(20):
 * head → [10|•] → [20|null] ← tail
 * 
 * After enqueue(30):
 * head → [10|•] → [20|•] → [30|null] ← tail
 * 
 * After dequeue():
 * head → [20|•] → [30|null] ← tail
 *        (10 removed)
 * ```
 * 
 * ALGORITHM:
 * 
 * Enqueue(value):
 * 1. Create new node with value
 * 2. If queue empty:  head = tail = new node
 * 3. Else: tail. next = new node, tail = new node
 * 
 * Dequeue():
 * 1. If empty: throw error
 * 2. Save head. data
 * 3. Move head to head.next
 * 4. If head becomes null: tail = null too
 * 5. Return saved data
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - enqueue(): O(1) - Add at tail
 * - dequeue(): O(1) - Remove from head
 * - front(): O(1) - Access head
 * - isEmpty(): O(1) - Check head
 * 
 * SPACE COMPLEXITY:  O(n)
 * - n nodes, each with data + pointer
 * - Extra space per node for pointer
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
data class QueueNode(
    val data: Int,
    var next: QueueNode? = null
)

/**
 * Queue implementation using singly linked list
 */
class QueueUsingLL {
    // Front of queue (for dequeue)
    private var head:  QueueNode? = null
    
    // Rear of queue (for enqueue)
    private var tail: QueueNode? = null
    
    // Track size for O(1) size() operation
    private var currentSize = 0
    
    /**
     * Add element to rear of queue
     * TIME: O(1)
     * 
     * @param value Element to add
     */
    fun enqueue(value:  Int) {
        // Create new node
        val newNode = QueueNode(value)
        
        // If queue is empty
        if (isEmpty()) {
            // Both head and tail point to new node
            head = newNode
            tail = newNode
        } else {
            // Link new node after current tail
            tail?.next = newNode
            // Move tail to new node
            tail = newNode
        }
        
        currentSize++
        println("Enqueued: $value")
    }
    
    /**
     * Remove and return front element
     * TIME: O(1)
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun dequeue(): Int {
        // Check if empty
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!  Cannot dequeue")
        }
        
        // Get value from head
        val value = head!! .data
        
        // Move head to next node
        head = head?.next
        
        // If head becomes null, queue is now empty
        // So tail should also be null
        if (head == null) {
            tail = null
        }
        
        currentSize--
        println("Dequeued: $value")
        return value
    }
    
    /**
     * Get front element without removing
     * TIME:  O(1)
     * 
     * @return Front element
     * @throws IllegalStateException if queue is empty
     */
    fun front(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!")
        }
        
        return head!!.data
    }
    
    /**
     * Get rear element without removing
     * TIME: O(1)
     * 
     * @return Rear element
     * @throws IllegalStateException if queue is empty
     */
    fun rear(): Int {
        if (isEmpty()) {
            throw IllegalStateException("Queue is empty!")
        }
        
        return tail!!.data
    }
    
    /**
     * Check if queue is empty
     * TIME: O(1)
     */
    fun isEmpty(): Boolean {
        return head == null
    }
    
    /**
     * Get current size of queue
     * TIME: O(1)
     */
    fun size(): Int {
        return currentSize
    }
    
    /**
     * Display all elements
     * TIME: O(n)
     */
    fun display() {
        if (isEmpty()) {
            println("Queue is empty")
            return
        }
        
        print("Queue (front to rear): ")
        var current = head
        while (current != null) {
            print("[${current.data}] ")
            if (current.next != null) print("→ ")
            current = current.next
        }
        println()
        println("Front:  ${front()}, Rear: ${rear()}, Size: $currentSize")
    }
    
    /**
     * Clear the queue
     * TIME: O(1)
     */
    fun clear() {
        head = null
        tail = null
        currentSize = 0
        println("Queue cleared")
    }
    
    /**
     * Convert queue to list (for testing)
     * TIME: O(n)
     */
    fun toList(): List<Int> {
        val list = mutableListOf<Int>()
        var current = head
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
 * head = null, tail = null, size = 0
 * 
 * enqueue(10):
 * - newNode = Node(10, null)
 * - Queue empty, so:  head = tail = newNode
 * - head → [10|null] ← tail
 * - size = 1
 * 
 * enqueue(20):
 * - newNode = Node(20, null)
 * - tail.next = newNode → [10|•] → [20|null]
 * - tail = newNode
 * - head → [10|•] → [20|null] ← tail
 * - size = 2
 * 
 * enqueue(30):
 * - newNode = Node(30, null)
 * - tail.next = newNode
 * - tail = newNode
 * - head → [10|•] → [20|•] → [30|null] ← tail
 * - size = 3
 * 
 * front():
 * - return head.data = 10
 * - No change to structure
 * 
 * rear():
 * - return tail.data = 30
 * - No change to structure
 * 
 * dequeue():
 * - value = head.data = 10
 * - head = head.next
 * - head → [20|•] → [30|null] ← tail
 * - size = 2
 * - return 10
 * 
 * dequeue():
 * - value = head.data = 20
 * - head = head.next
 * - head → [30|null] ← tail
 * - size = 1
 * - return 20
 * 
 * dequeue():
 * - value = head.data = 30
 * - head = head.next = null
 * - Since head is null, set tail = null too
 * - head = null, tail = null
 * - size = 0
 * - return 30
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty Queue Dequeue:
 *    - head = null
 *    - Throw exception
 * 
 * 2. Single Element:
 *    - After enqueue: head = tail
 *    - After dequeue:  head = null, tail = null (both!)
 * 
 * 3. Two Elements:
 *    - head → [10|•] → [20|null] ← tail
 *    - After dequeue: head → [20|null] ← tail
 * 
 * ============================================================================
 * COMPARISON:  Array vs Linked List
 * ============================================================================
 * 
 * ASPECT         | ARRAY              | LINKED LIST
 * ---------------|--------------------|-----------------
 * Size           | Fixed              | Dynamic
 * Overflow       | Possible           | Rare (memory limit)
 * Space per elem | Just data          | Data + pointer
 * Cache locality | Better (contiguous)| Worse (scattered)
 * Implementation | More complex       | Simpler
 * Memory waste   | Unused slots       | Pointer overhead
 * 
 * WHEN TO USE ARRAY:
 * - Size known in advance
 * - Need cache efficiency
 * - Memory constrained (no pointers)
 * 
 * WHEN TO USE LINKED LIST:
 * - Unknown/variable size
 * - Frequent growth/shrinkage
 * - No size limit needed
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    println("=== Queue Implementation Using Linked List ===\n")
    
    val queue = QueueUsingLL()
    
    // Test 1: Empty queue
    println("Test 1: Initial State")
    println("Is empty: ${queue.isEmpty()}")
    println("Size: ${queue.size()}")
    println()
    
    // Test 2: Enqueue operations
    println("Test 2: Enqueue Operations")
    queue.enqueue(10)
    queue.enqueue(20)
    queue.enqueue(30)
    queue.enqueue(40)
    queue.display()
    println()
    
    // Test 3: Front and Rear
    println("Test 3: Front and Rear")
    println("Front: ${queue.front()}")
    println("Rear: ${queue.rear()}")
    println()
    
    // Test 4: Dequeue operations
    println("Test 4: Dequeue Operations")
    queue.dequeue()
    queue.dequeue()
    queue.display()
    println()
    
    // Test 5: Mix of operations
    println("Test 5: Mix Operations")
    queue.enqueue(50)
    queue.enqueue(60)
    queue.display()
    queue.dequeue()
    queue.enqueue(70)
    queue.display()
    println()
    
    // Test 6: Dequeue until empty
    println("Test 6: Dequeue Until Empty")
    while (!queue.isEmpty()) {
        queue.dequeue()
    }
    println("Queue empty: ${queue.isEmpty()}")
    println()
    
    // Test 7: Enqueue after empty
    println("Test 7: Enqueue After Empty")
    queue.enqueue(100)
    queue.enqueue(200)
    queue.display()
    println()
    
    // Test 8: Exception handling
    println("Test 8: Exception Handling")
    queue.clear()
    try {
        queue.dequeue()
    } catch (e: IllegalStateException) {
        println("Caught: ${e. message}")
    }
    try {
        queue.front()
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
    println()
    
    // Test 9: Large queue
    println("Test 9: Large Queue (Dynamic Size)")
    repeat(10) { queue.enqueue(it * 10) }
    println("Enqueued 10 elements, size: ${queue.size()}")
    queue.display()
}// TODO: Implement QueueUsingLL
