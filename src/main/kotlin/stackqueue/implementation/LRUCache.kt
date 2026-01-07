/**
 * ============================================================================
 * PROBLEM: LRU Cache (Least Recently Used Cache)
 * DIFFICULTY: Medium
 * CATEGORY: Stack & Queue Implementation
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Design a data structure that follows Least Recently Used (LRU) cache constraints.
 * Implement the LRUCache class: 
 * - LRUCache(capacity): Initialize with positive size capacity
 * - get(key): Return value if key exists, otherwise return -1
 * - put(key, value): Update value if key exists, else add key-value pair. 
 *   If cache exceeds capacity, evict the least recently used key.
 * 
 * INPUT FORMAT:
 * Operations: ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get"]
 * Parameters:  [[2], [1,1], [2,2], [1], [3,3], [2], [4,4], [1], [3]]
 * 
 * OUTPUT FORMAT:
 * Results: [null, null, null, 1, null, -1, null, -1, 3]
 * 
 * CONSTRAINTS:
 * - 1 <= capacity <= 3000
 * - 0 <= key <= 10^4
 * - 0 <= value <= 10^5
 * - At most 2 * 10^5 calls to get and put
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * LRU evicts the least recently accessed item.  Think of it like:
 * - A browser's back button (recent pages first)
 * - Your phone's recent apps (last used items on top)
 * - A stack of papers (always pick from top)
 * 
 * KEY INSIGHT:
 * Need O(1) for both operations:
 * 1. Fast lookup:  HashMap
 * 2. Fast removal/insertion:  Doubly Linked List
 * 3. Track order of access
 * 
 * DATA STRUCTURES: 
 * 1. HashMap<Key, Node>: O(1) lookup
 * 2. Doubly Linked List: O(1) removal and insertion
 *    - Most recent at head
 *    - Least recent at tail
 * 
 * ALGORITHM STEPS: 
 * 
 * **GET Operation:**
 * 1. If key doesn't exist, return -1
 * 2. Move node to head (mark as recently used)
 * 3. Return value
 * 
 * **PUT Operation:**
 * 1. If key exists: 
 *    - Update value
 *    - Move to head
 * 2. If key doesn't exist:
 *    - Create new node
 *    - Add to head
 *    - If capacity exceeded, remove tail node
 * 
 * VISUAL EXAMPLE: 
 * ```
 * Capacity = 2
 * 
 * put(1,1): [1] (head)
 * 
 * put(2,2): [2] -> [1]
 * 
 * get(1):   [1] -> [2]  // Move 1 to front
 *          Return: 1
 * 
 * put(3,3): [3] -> [1]  // Evict 2 (at tail)
 * 
 * get(2):   Not found
 *          Return: -1
 * 
 * put(4,4): [4] -> [3]  // Evict 1
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - get(): O(1) - HashMap lookup + list operations
 * - put(): O(1) - HashMap operations + list operations
 * 
 * SPACE COMPLEXITY: O(capacity)
 * - HashMap: O(capacity)
 * - Doubly linked list: O(capacity)
 * - Each key-value pair stored once
 * 
 * WHY O(1):
 * - HashMap get/put: O(1)
 * - Remove node from list: O(1) with prev/next pointers
 * - Add node to head: O(1)
 * - Remove tail: O(1) with tail pointer
 * 
 * ============================================================================
 * IMPLEMENTATION
 * ============================================================================
 */

class LRUCache(private val capacity: Int) {
    
    /**
     * Node for doubly linked list
     */
    private class Node(
        val key: Int,
        var value: Int,
        var prev: Node?  = null,
        var next:  Node? = null
    )
    
    // HashMap for O(1) lookup
    private val cache = mutableMapOf<Int, Node>()
    
    // Dummy head and tail for easier list operations
    private val head = Node(0, 0)
    private val tail = Node(0, 0)
    
    init {
        head.next = tail
        tail. prev = head
    }
    
    /**
     * Get value for key, return -1 if not exists
     * Move accessed node to head (most recent)
     */
    fun get(key: Int): Int {
        val node = cache[key] ?: return -1
        moveToHead(node)
        return node.value
    }
    
    /**
     * Insert or update key-value pair
     * Evict LRU item if capacity exceeded
     */
    fun put(key: Int, value: Int) {
        if (cache.containsKey(key)) {
            // Update existing key
            val node = cache[key]!!
            node.value = value
            moveToHead(node)
        } else {
            // Insert new key
            val newNode = Node(key, value)
            cache[key] = newNode
            addToHead(newNode)
            
            // Check capacity
            if (cache.size > capacity) {
                val lru = removeTail()
                cache.remove(lru.key)
            }
        }
    }
    
    /**
     * Move existing node to head (most recent position)
     */
    private fun moveToHead(node: Node) {
        removeNode(node)
        addToHead(node)
    }
    
    /**
     * Add node right after dummy head
     */
    private fun addToHead(node: Node) {
        node.prev = head
        node.next = head.next
        head.next?. prev = node
        head.next = node
    }
    
    /**
     * Remove node from its current position
     */
    private fun removeNode(node: Node) {
        node.prev?.next = node.next
        node. next?.prev = node.prev
    }
    
    /**
     * Remove and return tail node (least recently used)
     */
    private fun removeTail(): Node {
        val lru = tail.prev!! 
        removeNode(lru)
        return lru
    }
}

/**
 * ============================================================================
 * USAGE EXAMPLES
 * ============================================================================
 */

fun main() {
    // Example 1: Basic LRU operations
    println("Example 1: Basic LRU Cache Operations")
    val cache1 = LRUCache(2)
    
    cache1.put(1, 1)
    cache1.put(2, 2)
    println("get(1): ${cache1.get(1)}")  // 1
    cache1.put(3, 3)                      // evicts key 2
    println("get(2): ${cache1.get(2)}")  // -1 (not found)
    cache1.put(4, 4)                      // evicts key 1
    println("get(1): ${cache1.get(1)}")  // -1 (evicted)
    println("get(3): ${cache1.get(3)}")  // 3
    println("get(4): ${cache1.get(4)}")  // 4
    
    // Example 2: Update existing key
    println("\nExample 2: Update Existing Key")
    val cache2 = LRUCache(2)
    
    cache2.put(1, 100)
    cache2.put(2, 200)
    println("get(1): ${cache2.get(1)}")  // 100
    cache2.put(2, 300)                    // Update value
    println("get(2): ${cache2.get(2)}")  // 300
    cache2.put(3, 400)                    // evicts key 1
    println("get(1): ${cache2.get(1)}")  // -1
    
    // Example 3: Single capacity cache
    println("\nExample 3: Single Capacity Cache")
    val cache3 = LRUCache(1)
    
    cache3.put(1, 10)
    println("get(1): ${cache3.get(1)}")  // 10
    cache3.put(2, 20)                     // evicts key 1
    println("get(1): ${cache3.get(1)}")  // -1
    println("get(2): ${cache3.get(2)}")  // 20
}

/**
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 2: LinkedHashMap
 * - Kotlin's LinkedHashMap maintains insertion order
 * - Can override removeEldestEntry()
 * - Pros:  Simpler implementation
 * - Cons: Less control, not available in all languages
 * 
 * ```kotlin
 * class LRUCache(private val capacity: Int) {
 *     private val cache = object : LinkedHashMap<Int, Int>(capacity, 0.75f, true) {
 *         override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Int, Int>?): Boolean {
 *             return size > capacity
 *         }
 *     }
 *     
 *     fun get(key: Int): Int = cache. getOrDefault(key, -1)
 *     fun put(key: Int, value: Int) { cache[key] = value }
 * }
 * ```
 * 
 * APPROACH 3: Array + HashMap (Circular Buffer)
 * - Use array as circular buffer
 * - HashMap maps key to array index
 * - Pros:  Cache-friendly (better locality)
 * - Cons: More complex eviction logic
 * 
 * WHY CURRENT APPROACH:
 * ✅ Demonstrates understanding of data structures
 * ✅ True O(1) operations
 * ✅ Works in any language
 * ✅ Interview-friendly (shows problem-solving)
 * 
 * ============================================================================
 * EDGE CASES & GOTCHAS
 * ============================================================================
 * 
 * 1. Capacity = 1: Every put evicts previous
 * 2. Updating existing key: Should NOT evict, just move to front
 * 3. get() also updates recency:  Not just a read operation
 * 4. Dummy nodes: Simplify edge cases (no null checks for head/tail)
 * 5. Remove before add: When moving to head, must remove first
 * 
 * ============================================================================
 */
