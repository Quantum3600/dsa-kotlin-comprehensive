# Hashing Theory - Comprehensive Guide

## Table of Contents
1. [Introduction to Hashing](#introduction)
2. [Hash Functions](#hash-functions)
3. [Hash Tables](#hash-tables)
4. [Collision Resolution](#collision-resolution)
5. [Load Factor and Rehashing](#load-factor)
6. [Common Hashing Patterns](#patterns)
7. [HashMap vs HashSet](#map-vs-set)
8. [Time and Space Complexity](#complexity)
9. [Practical Applications](#applications)
10. [Common Problems](#problems)
11. [Best Practices](#best-practices)

---

## Introduction to Hashing {#introduction}

### What is Hashing?

**Hashing** is a technique that converts data of arbitrary size into a fixed-size value (hash code). This hash code is then used as an index to store and retrieve data in constant time. 

### Key Characteristics

- **Fast lookup**: O(1) average time complexity
- **Efficient insertion/deletion**: O(1) average time
- **Space-time tradeoff**: Uses extra space for speed
- **Non-ordered**: Elements are not stored in order

### Why Use Hashing?

| Operation | Array (Unsorted) | Array (Sorted) | Hash Table |
|-----------|------------------|----------------|------------|
| Search | O(n) | O(log n) | O(1) average |
| Insert | O(1) | O(n) | O(1) average |
| Delete | O(n) | O(n) | O(1) average |

### Real-World Analogies

**Library Card Catalog**: Instead of searching every book, you use the catalog (hash table) to quickly find the shelf location (hash value) of your book.

**Phone Contacts**: Your phone doesn't search all contacts linearly. It uses hashing to instantly find a contact. 

---

## Hash Functions {#hash-functions}

### What is a Hash Function?

A **hash function** maps data (key) to a fixed-size integer (hash code).

```
hash_function:  Key → Integer (Hash Code)
```

### Properties of Good Hash Functions

#### 1. **Deterministic**
Same input always produces same output. 

```kotlin
val str = "hello"
println(str.hashCode())  // Always same value
println(str.hashCode())  // Same again
```

#### 2. **Uniform Distribution**
Distributes keys evenly across hash table to minimize collisions.

```
Good:   [5] [4] [6] [5] [5]  // Balanced
Bad:   [0] [0] [20] [0] [0]  // Clustered
```

#### 3. **Fast to Compute**
Should calculate hash quickly (O(1) or O(k) where k is key length).

#### 4. **Minimizes Collisions**
Different keys should produce different hash codes as much as possible.

### Common Hash Function Techniques

#### 1. **Division Method**
```kotlin
fun hashDivision(key: Int, tableSize: Int): Int {
    return key % tableSize
}

// Example
hashDivision(123, 10)  // Returns 3
hashDivision(456, 10)  // Returns 6
```

**Pros**: Simple and fast
**Cons**: Poor distribution if tableSize not prime

#### 2. **Multiplication Method**
```kotlin
fun hashMultiplication(key: Int, tableSize: Int): Int {
    val A = 0. 6180339887  // Golden ratio
    return ((key * A) % 1 * tableSize).toInt()
}
```

**Pros**: Works well with any table size
**Cons**: Slower than division

#### 3. **String Hashing (Polynomial Rolling Hash)**
```kotlin
fun hashString(str: String): Int {
    var hash = 0
    val prime = 31  // Common choice
    
    for (char in str) {
        hash = hash * prime + char.code
    }
    
    return hash
}

// Example
hashString("cat")  // 'c'*31^2 + 'a'*31 + 't'
```

**Why 31?** It's prime, fast (31 * x = (x << 5) - x), and gives good distribution.

#### 4. **Object Hashing**
```kotlin
data class Person(val name: String, val age: Int) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }
}
```

### Hash Function Example in Kotlin

```kotlin
class SimpleHash(private val tableSize: Int) {
    fun hash(key:  Int): Int {
        return (key % tableSize + tableSize) % tableSize
    }
    
    fun hash(key: String): Int {
        var hash = 0
        for (char in key) {
            hash = (hash * 31 + char.code) % tableSize
        }
        return (hash + tableSize) % tableSize
    }
}
```

---

## Hash Tables {#hash-tables}

### Structure

A **hash table** (hash map) is an array where each index stores a bucket that can hold key-value pairs.

```
Hash Table Structure: 
┌─────────────────────────────────┐
│ Index │  Bucket                 │
├───────┼─────────────────────────┤
│   0   │ → (key1, val1)          │
│   1   │ → (key2, val2) → ...     │
│   2   │ → empty                 │
│   3   │ → (key3, val3)          │
│  ...  │                         │
└─────────────────────────────────┘
```

### Basic Operations

#### 1. **Insert (Put)**

```kotlin
fun put(key: K, value: V) {
    val index = hash(key)
    // Store at index (handle collision if needed)
    table[index] = Entry(key, value)
}
```

**Time Complexity**: O(1) average

#### 2. **Search (Get)**

```kotlin
fun get(key: K): V? {
    val index = hash(key)
    return table[index]?.value
}
```

**Time Complexity**: O(1) average

#### 3. **Delete (Remove)**

```kotlin
fun remove(key: K): V? {
    val index = hash(key)
    val value = table[index]?.value
    table[index] = null
    return value
}
```

**Time Complexity**: O(1) average

### Implementation:  Simple Hash Map

```kotlin
class SimpleHashMap<K, V>(private val capacity: Int = 16) {
    private data class Entry<K, V>(val key: K, var value: V)
    
    private val table = arrayOfNulls<Entry<K, V>>(capacity)
    private var size = 0
    
    private fun hash(key: K): Int {
        return (key.hashCode() and 0x7FFFFFFF) % capacity
    }
    
    fun put(key: K, value: V) {
        val index = hash(key)
        table[index] = Entry(key, value)
        size++
    }
    
    fun get(key: K): V? {
        val index = hash(key)
        return table[index]?. value
    }
    
    fun remove(key: K): V? {
        val index = hash(key)
        val entry = table[index]
        table[index] = null
        if (entry != null) size--
        return entry?. value
    }
    
    fun contains(key: K): Boolean = get(key) != null
    
    fun size(): Int = size
}
```

---

## Collision Resolution {#collision-resolution}

### What is a Collision? 

A **collision** occurs when two different keys hash to the same index.

```kotlin
hash("cat") = 5
hash("dog") = 5  // Collision!
```

### Methods to Handle Collisions

### 1. **Chaining (Separate Chaining)**

Each table index stores a linked list (or list) of entries.

```
┌─────────────────────────────────┐
│ Index │  Chain                  │
├───────┼─────────────────────────┤
│   0   │ → null                  │
│   1   │ → [A] → [B] → [C]       │
│   2   │ → [D]                   │
│   3   │ → null                  │
└─────────────────────────────────┘
```

**Implementation**:
```kotlin
class HashMapChaining<K, V>(private val capacity: Int = 16) {
    private data class Entry<K, V>(val key: K, var value: V)
    
    private val table:  Array<MutableList<Entry<K, V>>> = 
        Array(capacity) { mutableListOf() }
    
    private fun hash(key: K): Int {
        return (key.hashCode() and 0x7FFFFFFF) % capacity
    }
    
    fun put(key:  K, value: V) {
        val index = hash(key)
        val chain = table[index]
        
        // Update if key exists
        for (entry in chain) {
            if (entry.key == key) {
                entry.value = value
                return
            }
        }
        
        // Add new entry
        chain.add(Entry(key, value))
    }
    
    fun get(key: K): V? {
        val index = hash(key)
        val chain = table[index]
        
        for (entry in chain) {
            if (entry.key == key) {
                return entry.value
            }
        }
        
        return null
    }
    
    fun remove(key: K): V? {
        val index = hash(key)
        val chain = table[index]
        
        val iterator = chain.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            if (entry.key == key) {
                iterator.remove()
                return entry.value
            }
        }
        
        return null
    }
}
```

**Pros**:
- Simple to implement
- Never "fills up"
- Good when collisions are common

**Cons**:
- Extra memory for pointers
- Poor cache performance

**Time Complexity**:
- Average: O(1)
- Worst: O(n) if all keys hash to same index

---

### 2. **Open Addressing**

All entries stored in the table itself.  When collision occurs, probe for next available slot.

#### a. **Linear Probing**

Try next index linearly:  `(hash + i) % capacity`

```
Insert "cat" with hash 5:
┌───┬───┬───┬───┬───┬───┬───┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │ 5 │ 6 │ 7 │
└───┴───┴───┴───┴───┴───┴───┴───┘

Insert "dog" with hash 5 (collision):
Try 5 → occupied, try 6 → success
┌───┬───┬───┬───┬───┬─────┬─────┬───┐
│ 0 │ 1 │ 2 │ 3 │ 4 │ cat │ dog │ 7 │
└───┴───┴───┴───┴───┴─────┴─────┴───┘
```

**Implementation**:
```kotlin
class HashMapLinearProbing<K, V>(private val capacity: Int = 16) {
    private data class Entry<K, V>(val key:  K, var value: V, var deleted: Boolean = false)
    
    private val table = arrayOfNulls<Entry<K, V>>(capacity)
    
    private fun hash(key: K): Int {
        return (key.hashCode() and 0x7FFFFFFF) % capacity
    }
    
    fun put(key: K, value: V) {
        var index = hash(key)
        var i = 0
        
        while (i < capacity) {
            val entry = table[index]
            
            when {
                entry == null || entry.deleted -> {
                    table[index] = Entry(key, value)
                    return
                }
                entry.key == key -> {
                    entry.value = value
                    return
                }
                else -> {
                    index = (index + 1) % capacity
                    i++
                }
            }
        }
        
        throw IllegalStateException("Hash table is full")
    }
    
    fun get(key: K): V? {
        var index = hash(key)
        var i = 0
        
        while (i < capacity) {
            val entry = table[index]
            
            when {
                entry == null -> return null
                ! entry.deleted && entry.key == key -> return entry.value
                else -> {
                    index = (index + 1) % capacity
                    i++
                }
            }
        }
        
        return null
    }
    
    fun remove(key: K): V? {
        var index = hash(key)
        var i = 0
        
        while (i < capacity) {
            val entry = table[index]
            
            when {
                entry == null -> return null
                !entry.deleted && entry.key == key -> {
                    entry.deleted = true
                    return entry.value
                }
                else -> {
                    index = (index + 1) % capacity
                    i++
                }
            }
        }
        
        return null
    }
}
```

**Pros**:
- Better cache performance
- No extra memory for pointers

**Cons**:
- Primary clustering (groups of occupied slots)
- Performance degrades as table fills

---

#### b. **Quadratic Probing**

Try indices: `(hash + i²) % capacity`

```kotlin
fun quadraticProbe(hash: Int, i: Int, capacity: Int): Int {
    return (hash + i * i) % capacity
}

// Sequence:  hash, hash+1, hash+4, hash+9, hash+16, ... 
```

**Pros**:  Reduces primary clustering

**Cons**: Secondary clustering still occurs

---

#### c. **Double Hashing**

Use second hash function for probe interval: 
```kotlin
fun doubleHash(key: K, i: Int, capacity: Int): Int {
    val hash1 = key.hashCode() % capacity
    val hash2 = 1 + (key.hashCode() % (capacity - 1))
    return (hash1 + i * hash2) % capacity
}
```

**Pros**: Best open addressing method, minimizes clustering

**Cons**: More complex, two hash functions needed

---

### Collision Resolution Comparison

| Method | Cache | Memory | Clustering | Complexity |
|--------|-------|---------|------------|------------|
| Chaining | Poor | Extra | None | Simple |
| Linear Probing | Best | Efficient | Primary | Simple |
| Quadratic Probing | Good | Efficient | Secondary | Medium |
| Double Hashing | Good | Efficient | Minimal | Complex |

---

## Load Factor and Rehashing {#load-factor}

### Load Factor

**Load factor (α)** = Number of entries / Table capacity

```kotlin
val loadFactor = size. toDouble() / capacity
```

### Threshold

Most implementations rehash when load factor exceeds **0.75**.

```kotlin
if (size > capacity * 0.75) {
    rehash()
}
```

### Rehashing

When load factor is too high, create larger table and rehash all entries. 

```kotlin
fun rehash() {
    val oldTable = table
    capacity *= 2
    table = arrayOfNulls(capacity)
    size = 0
    
    for (entry in oldTable) {
        entry?.let { put(it.key, it.value) }
    }
}
```

**Time Complexity**: O(n) but amortized O(1) per operation

### Load Factor Effects

| Load Factor | Space Efficiency | Time Efficiency | Collisions |
|-------------|------------------|-----------------|------------|
| Low (< 0.5) | Poor | Excellent | Rare |
| Medium (0.75) | Good | Good | Occasional |
| High (> 0.9) | Excellent | Poor | Frequent |

---

## Common Hashing Patterns {#patterns}

### 1. **Frequency Counting**

Count occurrences of elements. 

```kotlin
fun frequencyCount(arr: IntArray): Map<Int, Int> {
    val freq = mutableMapOf<Int, Int>()
    
    for (num in arr) {
        freq[num] = freq.getOrDefault(num, 0) + 1
    }
    
    return freq
}

// Example
frequencyCount(intArrayOf(1, 2, 2, 3, 3, 3))
// {1=1, 2=2, 3=3}
```

**Time**:  O(n), **Space**: O(k) where k is unique elements

---

### 2. **Two Sum Pattern**

Find pairs with given sum.

```kotlin
fun twoSum(nums: IntArray, target: Int): IntArray {
    val map = mutableMapOf<Int, Int>()
    
    for (i in nums.indices) {
        val complement = target - nums[i]
        
        if (complement in map) {
            return intArrayOf(map[complement]! !, i)
        }
        
        map[nums[i]] = i
    }
    
    return intArrayOf()
}
```

**Time**: O(n), **Space**: O(n)

---

### 3. **Group Anagrams**

Group strings that are anagrams. 

```kotlin
fun groupAnagrams(strs: Array<String>): List<List<String>> {
    val map = mutableMapOf<String, MutableList<String>>()
    
    for (str in strs) {
        val sorted = str.toCharArray().sorted().joinToString("")
        map. getOrPut(sorted) { mutableListOf() }.add(str)
    }
    
    return map.values.toList()
}

// Example
groupAnagrams(arrayOf("eat", "tea", "tan", "ate", "nat", "bat"))
// [["eat","tea","ate"], ["tan","nat"], ["bat"]]
```

---

### 4. **Longest Consecutive Sequence**

Find longest sequence of consecutive numbers.

```kotlin
fun longestConsecutive(nums:  IntArray): Int {
    val set = nums.toSet()
    var maxLength = 0
    
    for (num in set) {
        // Only start sequence if num-1 not in set
        if (num - 1 ! in set) {
            var current = num
            var length = 1
            
            while (current + 1 in set) {
                current++
                length++
            }
            
            maxLength = maxOf(maxLength, length)
        }
    }
    
    return maxLength
}
```

**Time**: O(n), **Space**: O(n)

---

### 5. **Subarray Sum Equals K**

Count subarrays with given sum.

```kotlin
fun subarraySum(nums: IntArray, k: Int): Int {
    val map = mutableMapOf<Int, Int>()
    map[0] = 1  // Base case: empty subarray
    
    var sum = 0
    var count = 0
    
    for (num in nums) {
        sum += num
        
        // If (sum - k) exists, we found subarray(s)
        count += map.getOrDefault(sum - k, 0)
        
        // Update current sum frequency
        map[sum] = map.getOrDefault(sum, 0) + 1
    }
    
    return count
}
```

**Pattern**: Prefix sum + hash map

---

## HashMap vs HashSet {#map-vs-set}

### HashMap

Stores **key-value pairs**. 

```kotlin
val map = hashMapOf<String, Int>()
map["apple"] = 1
map["banana"] = 2

println(map["apple"])  // 1
```

**Use When**:
- Need to associate values with keys
- Lookup by key required
- Key-value relationships

---

### HashSet

Stores **unique elements** only.

```kotlin
val set = hashSetOf<Int>()
set.add(1)
set.add(2)
set.add(1)  // Ignored (duplicate)

println(set.size)  // 2
```

**Use When**:
- Need unique elements
- Membership testing
- Remove duplicates

---

### Comparison

| Feature | HashMap | HashSet |
|---------|---------|---------|
| Stores | Key-Value pairs | Elements only |
| Allows duplicates | Keys:  No, Values: Yes | No |
| Null | One null key allowed | One null allowed |
| Use case | Lookup, mapping | Membership, uniqueness |

---

## Time and Space Complexity {#complexity}

### Time Complexity

| Operation | Average | Worst Case |
|-----------|---------|------------|
| Insert (put) | O(1) | O(n) |
| Search (get) | O(1) | O(n) |
| Delete (remove) | O(1) | O(n) |
| Contains | O(1) | O(n) |

**Note**:  Worst case O(n) occurs when all keys hash to same index (poor hash function or many collisions).

### Space Complexity

- **O(n)** where n is number of entries
- Plus overhead for empty buckets and collision structures

---

## Practical Applications {#applications}

### 1. **Caching**
```kotlin
class LRUCache(private val capacity: Int) {
    private val cache = LinkedHashMap<Int, Int>(capacity, 0.75f, true)
    
    fun get(key: Int): Int {
        return cache. getOrDefault(key, -1)
    }
    
    fun put(key: Int, value: Int) {
        cache[key] = value
        if (cache.size > capacity) {
            val firstKey = cache.keys.first()
            cache.remove(firstKey)
        }
    }
}
```

### 2. **Database Indexing**
Hash tables used for fast lookups in databases.

### 3. **Symbol Tables**
Compilers use hash tables to store variable names and attributes.

### 4. **Spell Checkers**
Dictionary stored in hash table for O(1) lookup.

### 5. **Routers**
IP address to MAC address mapping.

---

## Common Problems {#problems}

### Beginner
1. Two Sum
2. Contains Duplicate
3. Valid Anagram
4. Intersection of Two Arrays
5. First Unique Character

### Intermediate
1. Group Anagrams
2. Top K Frequent Elements
3. Longest Consecutive Sequence
4. Subarray Sum Equals K
5. 4Sum

### Advanced
1. LRU Cache
2. Design HashMap
3. Insert Delete GetRandom O(1)
4. All O' One Data Structure
5. Longest Substring Without Repeating Characters

---

## Best Practices {#best-practices}

### 1. **Choose Right Data Structure**

```kotlin
// ✓ Need key-value mapping
val scores = mutableMapOf<String, Int>()

// ✓ Need unique elements
val uniqueIds = mutableSetOf<Int>()

// ✗ Wrong: Using list for membership testing
val ids = mutableListOf<Int>()
if (5 in ids) { }  // O(n) instead of O(1)
```

### 2. **Override hashCode() and equals()**

```kotlin
data class Person(val name: String, val age: Int) {
    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + age
        return result
    }
    
    override fun equals(other:  Any?): Boolean {
        if (this === other) return true
        if (other ! is Person) return false
        return name == other.name && age == other.age
    }
}
```

**Rule**: Objects used as keys MUST have proper hashCode() and equals().

### 3. **Use getOrDefault() and getOrPut()**

```kotlin
// ✗ Verbose
val freq = mutableMapOf<Int, Int>()
for (num in nums) {
    if (num in freq) {
        freq[num] = freq[num]!! + 1
    } else {
        freq[num] = 1
    }
}

// ✓ Concise
val freq = mutableMapOf<Int, Int>()
for (num in nums) {
    freq[num] = freq.getOrDefault(num, 0) + 1
}
```

### 4. **Consider Load Factor**

```kotlin
// Specify initial capacity if size known
val map = HashMap<String, Int>(1000)  // Avoids rehashing
```

### 5. **Use Immutable Keys**

```kotlin
// ✓ Good:  String is immutable
val map = mutableMapOf<String, Int>()

// ✗ Bad: Array is mutable
val badMap = mutableMapOf<IntArray, String>()
```

---

## Summary

Hashing is a fundamental technique for achieving constant-time operations: 

**Key Takeaways**:
- ✓ Hash functions map keys to indices
- ✓ Collisions handled via chaining or open addressing
- ✓ Average O(1) time for insert/search/delete
- ✓ Trade space for time efficiency
- ✓ Load factor determines when to rehash
- ✓ Use HashMap for key-value, HashSet for uniqueness
- ✓ Essential for frequency counting, lookups, and caching

**When to Use Hashing**:
- Need fast lookup/insert/delete
- Checking membership or uniqueness
- Counting frequencies
- Implementing caches
- Finding pairs or subarrays

Happy Hashing!  🗂️
