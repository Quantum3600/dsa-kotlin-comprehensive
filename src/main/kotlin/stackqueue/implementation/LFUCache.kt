/**
 * ============================================================================
 * PROBLEM: LFU Cache (Least Frequently Used Cache)
 * DIFFICULTY: Hard
 * CATEGORY: Stack & Queue Implementation
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Design and implement a data structure for Least Frequently Used (LFU) cache.
 * Implement the LFUCache class with the following operations:
 * - LFUCache(capacity): Initialize the cache with positive capacity
 * - get(key): Get the value of the key if exists, otherwise return -1
 * - put(key, value): Update or insert if not exists. When cache reaches capacity,
 *   invalidate the least frequently used key before inserting new item.
 *   If there's a tie, remove the least recently used key.
 * 
 * INPUT FORMAT:
 * Operations:  ["LFUCache", "put", "put", "get", "put", "get", "get"]
 * Parameters: [[2], [1,1], [2,2], [1], [3,3], [2], [3]]
 * 
 * OUTPUT FORMAT:
 * Results: [null, null, null, 1, null, -1, 3]
 * 
 * CONSTRAINTS:
 * - 1 <= capacity <= 10^4
 * - 0 <= key, value <= 10^5
 * - At most 2 * 10^5 calls to get and put
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * LFU evicts items used least frequently. Think of it like: 
 * - A library removing books borrowed least often
 * - If multiple books have same borrow count, remove the oldest
 * 
 * KEY CHALLENGE:
 * Need to track: 
 * 1. Frequency of each key
 * 2. Keys with same frequency (in LRU order)
 * 3. Minimum frequency for quick eviction
 * 
 * DATA STRUCTURES NEEDED:
 * 1. HashMap<Key, Node>: Fast key lookup
 * 2. HashMap<Frequency, LinkedHashSet<Key>>: Groups keys by frequency
 * 3. minFreq: Track minimum frequency for O(1) eviction
 * 
 * ALGORITHM STEPS:
 * 
 * **GET Operation:**
 * 1. If key doesn't exist, return -1
 * 2. Get value from cache
 * 3. Increment frequency of key
 * 4. Update minFreq if necessary
 * 5. Return value
 * 
 * **PUT Operation:**
 * 1. If key exists, update value and increment frequency
 * 2. If cache is full: 
 *    - Find least frequently used key (at minFreq)
 *    - If tie, pick least recently used (first in set)
 *    - Evict that key
 * 3. Insert new key with frequency = 1
 * 4. Set minFreq = 1
 * 
 * VISUAL EXAMPLE:
 * ```
 * Capacity = 2
 * 
 * put(1,1): Cache = {1:1(freq=1)}
 *          minFreq = 1
 * 
 * put(2,2): Cache = {1:1(freq=1), 2:2(freq=1)}
 *          minFreq = 1
 * 
 * get(1):   Cache = {1:1(freq=2), 2:2(freq=1)}
 *          minFreq = 1
 *          Return:  1
 * 
 * put(3,3): Cache FULL! Evict key=2 (freq=1, oldest)
 *          Cache = {1:1(freq=2), 3:3(freq=1)}
 *          minFreq = 1
 * 
 * get(2):   Not found
 *          Return: -1
 * 
 * get(3):   Cache = {1:1(freq=2), 3:3(freq=2)}
 *          minFreq = 2
 *          Return: 3
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - get(): O(1) - HashMap lookup + LinkedHashSet operations
 * - put(): O(1) - HashMap operations + LinkedHashSet operations
 * 
 * SPACE COMPLEXITY:  O(capacity)
 * - cache HashMap: O(capacity)
 * - freqMap HashMap: O(capacity)
 * - Each key stored once across all structures
 * 
 * WHY O(1) for all operations:
 * - HashMap get/put: O(1)
 * - LinkedHashSet add/remove: O(1)
 * - minFreq tracking: O(1)
 * 
 * ============================================================================
 * IMPLEMENTATION
 * ============================================================================
 */

class LFUCache(private val capacity: Int) {
    
    // Node to store key-value-frequency
    private data class Node(val key: Int, var value: Int, var freq: Int)
    
    // Main cache:  key -> Node
    private val cache = mutableMapOf<Int, Node>()
    
    // Frequency map: freq -> LinkedHashSet of keys (maintains insertion order)
    private val freqMap = mutableMapOf<Int, LinkedHashSet<Int>>()
    
    // Track minimum frequency for O(1) eviction
    private var minFreq = 0
    
    /**
     * Get value for key, return -1 if not exists
     * Also increments frequency of the key
     */
    fun get(key: Int): Int {
        val node = cache[key] ?: return -1
        updateFrequency(node)
        return node.value
    }
    
    /**
     * Insert or update key-value pair
     * Evicts LFU item if capacity is reached
     */
    fun put(key: Int, value: Int) {
        if (capacity == 0) return
        
        // Case 1: Key exists, update value and frequency
        if (cache. containsKey(key)) {
            val node = cache[key]!! 
            node.value = value
            updateFrequency(node)
            return
        }
        
        // Case 2: Cache is full, evict LFU item
        if (cache.size >= capacity) {
            evictLFU()
        }
        
        // Case 3: Insert new key
        val newNode = Node(key, value, 1)
        cache[key] = newNode
        freqMap. getOrPut(1) { LinkedHashSet() }.add(key)
        minFreq = 1
    }
    
    /**
     * Update frequency of a node
     * Move from current frequency group to next
     */
    private fun updateFrequency(node: Node) {
        val oldFreq = node.freq
        val key = node.key
        
        // Remove from old frequency group
        freqMap[oldFreq]?.remove(key)
        
        // If this was the only key at minFreq and we removed it,
        // increment minFreq
        if (oldFreq == minFreq && freqMap[oldFreq]?.isEmpty() == true) {
            minFreq++
        }
        
        // Increment frequency and add to new group
        node.freq++
        freqMap. getOrPut(node.freq) { LinkedHashSet() }.add(key)
    }
    
    /**
     * Evict least frequently used item
     * If tie, evict least recently used (first in LinkedHashSet)
     */
    private fun evictLFU() {
        val keysAtMinFreq = freqMap[minFreq] ?: return
        
        // Get first key (least recently used at this frequency)
        val keyToEvict = keysAtMinFreq.first()
        
        // Remove from frequency map and cache
        keysAtMinFreq.remove(keyToEvict)
        cache.remove(keyToEvict)
    }
}

/**
 * ============================================================================
 * USAGE EXAMPLES
 * ============================================================================
 */

fun main() {
    // Example 1: Basic operations
    println("Example 1: Basic LFU Cache Operations")
    val cache1 = LFUCache(2)
    
    cache1.put(1, 1)
    cache1.put(2, 2)
    println("get(1): ${cache1.get(1)}")  // 1 (freq:  1->2)
    cache1.put(3, 3)                      // evicts key 2
    println("get(2): ${cache1.get(2)}")  // -1 (not found)
    println("get(3): ${cache1.get(3)}")  // 3
    cache1.put(4, 4)                      // evicts key 1 (freq=2 but older than 3)
    println("get(1): ${cache1.get(1)}")  // -1 (evicted)
    println("get(3): ${cache1.get(3)}")  // 3
    println("get(4): ${cache1.get(4)}")  // 4
    
    // Example 2: Frequency tie-breaking
    println("\nExample 2: Frequency Tie-Breaking")
    val cache2 = LFUCache(3)
    
    cache2.put(1, 100)
    cache2.put(2, 200)
    cache2.put(3, 300)
    println("get(1): ${cache2.get(1)}")  // 100 (freq=2)
    println("get(2): ${cache2.get(2)}")  // 200 (freq=2)
    cache2.put(4, 400)                    // evicts 3 (freq=1)
    println("get(3): ${cache2.get(3)}")  // -1
    println("get(4): ${cache2.get(4)}")  // 400
}

/**
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 2: Using Priority Queue
 * - Store (key, freq, timestamp) in min heap
 * - Pros: Simple conceptual model
 * - Cons: O(log n) operations, harder to update frequency
 * 
 * APPROACH 3: Two-Level Doubly Linked List
 * - Outer list: frequency levels
 * - Inner list: keys at that frequency (LRU order)
 * - Pros: True O(1) with better constants
 * - Cons: Complex implementation
 * 
 * WHY CURRENT APPROACH:
 * ✅ True O(1) for both operations
 * ✅ Cleaner code with Kotlin collections
 * ✅ LinkedHashSet maintains insertion order
 * ✅ HashMap provides fast lookups
 * 
 * ============================================================================
 * EDGE CASES & GOTCHAS
 * ============================================================================
 * 
 * 1. Capacity = 0: Should handle gracefully
 * 2. Single element cache:  Eviction happens on every put
 * 3. All items same frequency: Falls back to LRU
 * 4. Updating existing key: Should increment frequency
 * 5. minFreq tracking: Must update when removing last item at that freq
 * 
 * ============================================================================
 */
