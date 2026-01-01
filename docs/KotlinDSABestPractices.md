# Kotlin DSA Best Practices

## Table of Contents
1. [Code Style](#code-style)
2. [Performance Optimization](#performance)
3. [Common Patterns](#patterns)
4. [Testing Strategies](#testing)
5. [Error Handling](#error-handling)
6. [Memory Management](#memory)

---

## Code Style {#code-style}

### Naming Conventions

**Variables and Functions**: camelCase
```kotlin
val studentCount = 10
fun calculateAverage() { }
```

**Classes and Interfaces**: PascalCase
```kotlin
class BinarySearchTree { }
interface Comparable { }
```

**Constants**: UPPER_SNAKE_CASE
```kotlin
const val MAX_SIZE = 100
const val DEFAULT_CAPACITY = 10
```

### Function Design

**Keep Functions Small**
```kotlin
// ✗ Too complex
fun processData(data: List<Int>): Result {
    // 50 lines of mixed logic
}

// ✓ Well-organized
fun processData(data: List<Int>): Result {
    val filtered = filterData(data)
    val transformed = transformData(filtered)
    return aggregateResults(transformed)
}
```

**Single Responsibility**
```kotlin
// ✗ Doing too much
fun validateAndSaveUser(user: User) {
    validate(user)
    hash(user.password)
    save(user)
    sendEmail(user)
}

// ✓ Separate concerns
fun validateUser(user: User): Boolean { }
fun saveUser(user: User) { }
```

---

## Performance Optimization {#performance}

### Choose Right Data Structure

**Lookup-Heavy Operations**
```kotlin
// ✗ Slow: O(n) lookup
val list = mutableListOf<Int>()
if (5 in list) { }  // O(n)

// ✓ Fast: O(1) lookup
val set = mutableSetOf<Int>()
if (5 in set) { }  // O(1)
```

**Key-Value Storage**
```kotlin
// ✗ Using List of Pairs
val pairs = listOf(Pair("key1", 1), Pair("key2", 2))
val value = pairs.find { it.first == "key1" }?.second  // O(n)

// ✓ Using Map
val map = mapOf("key1" to 1, "key2" to 2)
val value = map["key1"]  // O(1)
```

### Avoid Unnecessary Operations

**Minimize Object Creation**
```kotlin
// ✗ Creates new list each time
fun processData(items: List<Int>): List<Int> {
    return items.filter { it > 0 }
               .map { it * 2 }
               .filter { it < 100 }  // Multiple intermediate lists
}

// ✓ Single pass with sequence
fun processData(items: List<Int>): List<Int> {
    return items.asSequence()
               .filter { it > 0 }
               .map { it * 2 }
               .filter { it < 100 }
               .toList()  // Lazy evaluation
}
```

**Reuse Computed Values**
```kotlin
// ✗ Recomputes every time
fun isValid(arr: IntArray): Boolean {
    if (arr.sum() > 100) return false
    if (arr.sum() < 10) return false
    return arr.sum() % 2 == 0
}

// ✓ Compute once
fun isValid(arr: IntArray): Boolean {
    val sum = arr.sum()
    return sum in 10..100 && sum % 2 == 0
}
```

### Use Primitive Arrays

```kotlin
// ✗ Boxed integers - overhead
val numbers = arrayOf(1, 2, 3, 4, 5)

// ✓ Primitive int array - efficient
val numbers = intArrayOf(1, 2, 3, 4, 5)
```

---

## Common Patterns {#patterns}

### Early Return

```kotlin
// ✗ Nested conditions
fun process(value: Int?): String {
    if (value != null) {
        if (value > 0) {
            if (value < 100) {
                return "valid"
            }
        }
    }
    return "invalid"
}

// ✓ Guard clauses
fun process(value: Int?): String {
    if (value == null) return "invalid"
    if (value <= 0) return "invalid"
    if (value >= 100) return "invalid"
    return "valid"
}
```

### Null Safety

```kotlin
// ✗ Risky
fun getLength(str: String?): Int {
    return str!!.length  // Might throw NullPointerException
}

// ✓ Safe
fun getLength(str: String?): Int {
    return str?.length ?: 0
}
```

### Extension Functions

```kotlin
// ✗ Utility function
fun isEven(number: Int): Boolean {
    return number % 2 == 0
}
val result = isEven(5)

// ✓ Extension function
fun Int.isEven(): Boolean {
    return this % 2 == 0
}
val result = 5.isEven()
```

---

## Testing Strategies {#testing}

### Unit Tests

**Test Edge Cases**
```kotlin
@Test
fun testBinarySearch() {
    val arr = intArrayOf(1, 3, 5, 7, 9)
    
    // Normal case
    assertEquals(2, binarySearch(arr, 5))
    
    // Edge cases
    assertEquals(0, binarySearch(arr, 1))  // First element
    assertEquals(4, binarySearch(arr, 9))  // Last element
    assertEquals(-1, binarySearch(arr, 4))  // Not found
    
    // Empty array
    assertEquals(-1, binarySearch(intArrayOf(), 5))
    
    // Single element
    assertEquals(0, binarySearch(intArrayOf(5), 5))
}
```

**Test Boundary Conditions**
```kotlin
@Test
fun testArraySum() {
    // Empty
    assertEquals(0, sum(intArrayOf()))
    
    // Single element
    assertEquals(5, sum(intArrayOf(5)))
    
    // All positive
    assertEquals(15, sum(intArrayOf(1, 2, 3, 4, 5)))
    
    // All negative
    assertEquals(-15, sum(intArrayOf(-1, -2, -3, -4, -5)))
    
    // Mixed
    assertEquals(0, sum(intArrayOf(-5, 5)))
    
    // Max int
    assertEquals(Int.MAX_VALUE, sum(intArrayOf(Int.MAX_VALUE)))
}
```

### Performance Tests

```kotlin
@Test
fun testPerformance() {
    val largeArray = IntArray(1_000_000) { it }
    
    val start = System.nanoTime()
    binarySearch(largeArray, 999_999)
    val duration = System.nanoTime() - start
    
    // Should complete in microseconds, not milliseconds
    assertTrue(duration < 1_000_000)  // 1ms
}
```

---

## Error Handling {#error-handling}

### Validate Input

```kotlin
fun binarySearch(arr: IntArray, target: Int): Int {
    require(arr.isNotEmpty()) { "Array cannot be empty" }
    require(arr.isSorted()) { "Array must be sorted" }
    
    // Implementation
}

private fun IntArray.isSorted(): Boolean {
    for (i in 0 until size - 1) {
        if (this[i] > this[i + 1]) return false
    }
    return true
}
```

### Meaningful Exceptions

```kotlin
// ✗ Generic exception
fun divide(a: Int, b: Int): Int {
    if (b == 0) throw Exception("Error")
    return a / b
}

// ✓ Specific exception with message
fun divide(a: Int, b: Int): Int {
    if (b == 0) {
        throw IllegalArgumentException("Cannot divide by zero: $a / $b")
    }
    return a / b
}
```

### Result Type Pattern

```kotlin
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

fun divide(a: Int, b: Int): Result<Int> {
    return if (b == 0) {
        Result.Error("Cannot divide by zero")
    } else {
        Result.Success(a / b)
    }
}

// Usage
when (val result = divide(10, 2)) {
    is Result.Success -> println(result.data)
    is Result.Error -> println(result.message)
}
```

---

## Memory Management {#memory}

### Avoid Memory Leaks

**Clear Collections**
```kotlin
class Cache {
    private val data = mutableMapOf<String, ByteArray>()
    
    fun add(key: String, value: ByteArray) {
        if (data.size > MAX_SIZE) {
            data.clear()  // Prevent unbounded growth
        }
        data[key] = value
    }
}
```

**Use Weak References for Caches**
```kotlin
import java.lang.ref.WeakReference

class ImageCache {
    private val cache = mutableMapOf<String, WeakReference<Bitmap>>()
    
    fun get(key: String): Bitmap? {
        return cache[key]?.get()  // May be GC'd
    }
}
```

### Optimize Space Usage

**Use Primitive Types**
```kotlin
// ✗ Boxed - extra memory
val numbers = ArrayList<Int>()

// ✓ Primitive array - efficient
val numbers = IntArray(100)
```

**Reuse Objects**
```kotlin
// ✗ Creates many objects
fun process(items: List<Int>) {
    for (item in items) {
        val result = Result(item)  // New object each time
        use(result)
    }
}

// ✓ Reuse object
fun process(items: List<Int>) {
    val result = Result()
    for (item in items) {
        result.value = item
        use(result)
    }
}
```

---

## Code Review Checklist

### Correctness
- [ ] Handles all edge cases
- [ ] No off-by-one errors
- [ ] No integer overflow
- [ ] No null pointer issues
- [ ] Correct algorithm logic

### Performance
- [ ] Optimal time complexity
- [ ] Optimal space complexity
- [ ] No unnecessary operations
- [ ] Appropriate data structures

### Code Quality
- [ ] Meaningful names
- [ ] Proper comments
- [ ] Clean formatting
- [ ] Single responsibility
- [ ] DRY (Don't Repeat Yourself)

### Testing
- [ ] Unit tests written
- [ ] Edge cases tested
- [ ] Performance tested
- [ ] Error cases tested

---

## Key Takeaways

1. ✓ Write clean, readable code
2. ✓ Choose appropriate data structures
3. ✓ Optimize only when needed
4. ✓ Test thoroughly
5. ✓ Handle errors gracefully
6. ✓ Manage memory wisely
7. ✓ Follow Kotlin conventions
8. ✓ Use language features effectively
9. ✓ Document complex logic
10. ✓ Review and refactor regularly

---

## Quick Reference

### Time Complexity Goals
- Small input (< 100): Any algorithm works
- Medium (< 10,000): O(n²) acceptable
- Large (< 1,000,000): Need O(n log n)
- Very large (> 1,000,000): Need O(n) or better

### Space Complexity
- Prefer O(1) when possible
- O(log n) for recursion is acceptable
- O(n) is common and okay
- O(n²) should be avoided if possible

### Common Optimizations
1. Hash maps for lookups
2. Two pointers for sorted arrays
3. Sliding window for subarrays
4. Binary search for sorted data
5. Memoization for recursion
6. Early termination
7. Lazy evaluation with sequences
