# Space Complexity Guide

## 📖 Table of Contents
- [What is Space Complexity?](#what-is-space-complexity)
- [Components of Space Complexity](#components-of-space-complexity)
- [Common Space Complexities](#common-space-complexities)
- [How to Calculate Space Complexity](#how-to-calculate-space-complexity)
- [Space vs Time Trade-offs](#space-vs-time-trade-offs)
- [Examples and Patterns](#examples-and-patterns)
- [Auxiliary Space vs Total Space](#auxiliary-space-vs-total-space)
- [Practice Problems](#practice-problems)

## What is Space Complexity?

**Space complexity** measures the amount of memory an algorithm uses as the input size grows. It helps us understand:
- How much memory an algorithm needs
- Whether an algorithm is practical for large inputs
- Trade-offs between time and space

### 🎯 Key Insight
Space complexity includes:
1. **Input space**: Memory for input data
2. **Auxiliary space**: Extra memory used by the algorithm
3. **Stack space**: Memory used by recursive calls

Usually, we focus on **auxiliary space** - the extra space beyond the input.

## Components of Space Complexity

### 1. Fixed Part (Constants)
Space that doesn't depend on input size:
- Variables (int, float, boolean)
- Fixed-size data structures
- Program code itself

### 2. Variable Part
Space that depends on input size:
- Dynamic arrays
- Recursion call stack
- Data structures built during execution

### Formula
```
Total Space = Fixed Part + Variable Part
Space Complexity focuses on Variable Part
```

## Common Space Complexities

### O(1) - Constant Space
**Memory usage stays the same regardless of input size**

```kotlin
fun sum(arr: IntArray): Int {
    var total = 0  // One integer variable
    for (num in arr) {
        total += num
    }
    return total
}
// Space: O(1) - only 'total' variable
// Input array doesn't count toward auxiliary space
```

**Examples:**
- Using few variables
- In-place algorithms
- Iterative solutions without extra data structures

### O(log n) - Logarithmic Space
**Memory usage grows logarithmically**

```kotlin
fun binarySearch(arr: IntArray, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    
    while (left <= right) {
        val mid = left + (right - left) / 2
        when {
            arr[mid] == target -> return mid
            arr[mid] < target -> left = mid + 1
            else -> right = mid - 1
        }
    }
    return -1
}
// Space: O(1) iterative
// But recursive binary search: O(log n) for call stack
```

**Examples:**
- Recursive binary search (call stack)
- Balanced tree operations (recursion depth)

### O(n) - Linear Space
**Memory usage grows linearly with input**

```kotlin
fun reverseArray(arr: IntArray): IntArray {
    val reversed = IntArray(arr.size)  // New array of size n
    for (i in arr.indices) {
        reversed[i] = arr[arr.size - 1 - i]
    }
    return reversed
}
// Space: O(n) - creating new array
```

**Examples:**
- Creating a copy of input
- Hash map storing n elements
- Recursion with n depth

### O(n²) - Quadratic Space
**Memory usage grows with square of input**

```kotlin
fun create2DMatrix(n: Int): Array<IntArray> {
    return Array(n) { IntArray(n) }  // n x n matrix
}
// Space: O(n²) - 2D array
```

**Examples:**
- 2D arrays/matrices
- Graph adjacency matrices
- Dynamic programming tables

## How to Calculate Space Complexity

### Step 1: Identify All Memory Usage
- Variables
- Arrays/Lists
- Hash maps/Sets
- Recursion stack

### Step 2: Express as Function of Input Size
Count memory in terms of n (input size).

### Step 3: Apply Big O Rules
Remove constants, keep dominant term.

## Space vs Time Trade-offs

Often, we can trade space for time or vice versa:

### Example 1: Fibonacci

**High Space, Low Time**
```kotlin
fun fibonacciDP(n: Int): Int {
    if (n <= 1) return n
    val dp = IntArray(n + 1)  // O(n) space
    dp[0] = 0
    dp[1] = 1
    for (i in 2..n) {
        dp[i] = dp[i-1] + dp[i-2]
    }
    return dp[n]
}
// Time: O(n), Space: O(n)
```

**Low Space, Same Time**
```kotlin
fun fibonacciOptimized(n: Int): Int {
    if (n <= 1) return n
    var prev2 = 0  // Only 2 variables
    var prev1 = 1
    for (i in 2..n) {
        val current = prev1 + prev2
        prev2 = prev1
        prev1 = current
    }
    return prev1
}
// Time: O(n), Space: O(1)
```

### Example 2: String Reverse

**Creating New String**
```kotlin
fun reverse(s: String): String {
    return s.reversed()  // Creates new string
}
// Space: O(n) - new string
```

**In-place (if possible with char array)**
```kotlin
fun reverseInPlace(chars: CharArray) {
    var left = 0
    var right = chars.size - 1
    while (left < right) {
        val temp = chars[left]
        chars[left] = chars[right]
        chars[right] = temp
        left++
        right--
    }
}
// Space: O(1) - only temp variable
```

## Examples and Patterns

### Example 1: Simple Variables
```kotlin
fun example1(n: Int) {
    val a = 5        // O(1)
    val b = 10       // O(1)
    val c = a + b    // O(1)
    println(c)
}
// Space: O(1) - fixed number of variables
```

### Example 2: Single Array
```kotlin
fun example2(n: Int) {
    val arr = IntArray(n)  // O(n)
    for (i in 0 until n) {
        arr[i] = i
    }
}
// Space: O(n) - array of size n
```

### Example 3: Multiple Arrays
```kotlin
fun example3(n: Int) {
    val arr1 = IntArray(n)      // O(n)
    val arr2 = IntArray(n)      // O(n)
    val arr3 = IntArray(n)      // O(n)
}
// Space: O(n) + O(n) + O(n) = O(3n) = O(n)
// Constants don't matter
```

### Example 4: 2D Array
```kotlin
fun example4(n: Int) {
    val matrix = Array(n) { IntArray(n) }  // O(n²)
}
// Space: O(n²) - n rows, each with n elements
```

### Example 5: Hash Map
```kotlin
fun example5(arr: IntArray) {
    val map = mutableMapOf<Int, Int>()  // O(n) worst case
    for (num in arr) {
        map[num] = map.getOrDefault(num, 0) + 1
    }
}
// Space: O(n) - hash map can store up to n unique elements
```

### Example 6: Simple Recursion
```kotlin
fun factorial(n: Int): Int {
    if (n <= 1) return 1
    return n * factorial(n - 1)
}
// Space: O(n) - call stack depth is n
// Each call adds a frame to the stack
```

### Example 7: Binary Recursion
```kotlin
fun fibonacci(n: Int): Int {
    if (n <= 1) return n
    return fibonacci(n - 1) + fibonacci(n - 2)
}
// Space: O(n) - maximum depth of recursion tree
// Not O(2ⁿ)! Stack depth is still just n
```

### Example 8: Tail Recursion
```kotlin
tailrec fun sumTailRec(n: Int, accumulator: Int = 0): Int {
    if (n == 0) return accumulator
    return sumTailRec(n - 1, accumulator + n)
}
// Space: O(1) in Kotlin (tail call optimization)
// Without optimization: O(n)
```

## Auxiliary Space vs Total Space

### Auxiliary Space
Extra space used beyond the input.

```kotlin
fun findMax(arr: IntArray): Int {
    var max = arr[0]  // O(1) auxiliary
    for (num in arr) {
        if (num > max) max = num
    }
    return max
}
// Auxiliary Space: O(1)
// Total Space: O(n) (includes input array)
```

### When to Use Each
- **Interview**: Usually discuss auxiliary space
- **System Design**: Consider total space
- **Big O**: Typically refers to auxiliary space

## Recursion Stack Space

### Understanding the Call Stack

```kotlin
fun printN(n: Int) {
    if (n == 0) return
    print("$n ")
    printN(n - 1)
}
// Call Stack:
// printN(5) -> printN(4) -> printN(3) -> printN(2) -> printN(1) -> printN(0)
// Maximum depth: 5
// Space: O(n)
```

### Calculating Recursion Space

**Rule**: Space = Maximum depth of recursion tree

**Linear Recursion**
```kotlin
fun linearRec(n: Int) {
    if (n <= 0) return
    linearRec(n - 1)
}
// Depth: n, Space: O(n)
```

**Binary Recursion**
```kotlin
fun binaryRec(n: Int) {
    if (n <= 0) return
    binaryRec(n - 1)
    binaryRec(n - 1)
}
// Depth: n (not 2ⁿ!), Space: O(n)
// Only one branch is active at a time on the stack
```

## In-Place Algorithms

**In-place** means O(1) auxiliary space (modifying input directly).

### Example: In-Place Array Reversal
```kotlin
fun reverseInPlace(arr: IntArray) {
    var left = 0
    var right = arr.size - 1
    while (left < right) {
        // Swap without extra array
        val temp = arr[left]
        arr[left] = arr[right]
        arr[right] = temp
        left++
        right--
    }
}
// Space: O(1) - only 'temp' variable
```

### Example: In-Place Sorting
```kotlin
fun bubbleSort(arr: IntArray) {
    for (i in arr.indices) {
        for (j in 0 until arr.size - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
            }
        }
    }
}
// Space: O(1) - modifies array in place
```

## Common Patterns

### Pattern 1: Fixed Variables
```kotlin
val x = 10       // O(1)
val y = 20       // O(1)
val sum = x + y  // O(1)
// Total: O(1)
```

### Pattern 2: Single Loop, No Extra Space
```kotlin
for (i in 0 until n) {
    // O(1) operations
}
// Space: O(1)
```

### Pattern 3: Building Result Array
```kotlin
val result = IntArray(n)  // O(n)
for (i in 0 until n) {
    result[i] = i * 2
}
// Space: O(n)
```

### Pattern 4: Hash Map for Counting
```kotlin
val map = mutableMapOf<Int, Int>()  // O(k) where k ≤ n
for (num in arr) {
    map[num] = map.getOrDefault(num, 0) + 1
}
// Space: O(k) where k is unique elements
// Worst case: O(n) if all unique
```

### Pattern 5: Recursion with Return
```kotlin
fun recursive(n: Int): Int {
    if (n <= 0) return 0
    return n + recursive(n - 1)
}
// Space: O(n) for call stack
```

## Practice Problems

### Problem 1
```kotlin
fun mystery(arr: IntArray): Int {
    var sum = 0
    for (num in arr) {
        sum += num
    }
    return sum
}
```
**Answer**: O(1) - only 'sum' variable

### Problem 2
```kotlin
fun mystery2(n: Int): IntArray {
    val result = IntArray(n)
    var temp = IntArray(n)
    return result
}
```
**Answer**: O(n) - two arrays of size n, but O(2n) = O(n)

### Problem 3
```kotlin
fun mystery3(n: Int) {
    if (n <= 0) return
    mystery3(n - 1)
    mystery3(n - 1)
}
```
**Answer**: O(n) - max recursion depth is n, not O(2ⁿ)!

### Problem 4
```kotlin
fun mystery4(n: Int) {
    val matrix = Array(n) { IntArray(n) }
    val temp = IntArray(n)
}
```
**Answer**: O(n²) - matrix dominates over array

## Tips for Reducing Space

1. **Reuse Variables**: Don't create unnecessary variables
2. **In-Place Modifications**: Modify input when possible
3. **Iteration Over Recursion**: Use loops to avoid stack space
4. **Sliding Window**: Instead of storing all, keep track of window
5. **Two Pointers**: Process from both ends without extra space

## Common Mistakes

### Mistake 1: Counting Input Space
❌ Wrong: Array input means O(n) space
✅ Correct: Only count auxiliary space

### Mistake 2: Not Counting Recursion
❌ Wrong: Recursion has no space cost
✅ Correct: Each recursive call uses stack space

### Mistake 3: Confusing Time and Space
They are independent! O(n) time doesn't mean O(n) space.

### Mistake 4: Forgetting Hidden Space
Hash maps, sets, and other data structures use space!

## Quick Reference

| Algorithm | Time | Space | Notes |
|-----------|------|-------|-------|
| Bubble Sort | O(n²) | O(1) | In-place |
| Merge Sort | O(n log n) | O(n) | Extra array |
| Quick Sort | O(n log n) | O(log n) | Recursion stack |
| Binary Search (iterative) | O(log n) | O(1) | Few variables |
| Binary Search (recursive) | O(log n) | O(log n) | Call stack |
| DFS (recursion) | O(V+E) | O(V) | Call stack |
| BFS | O(V+E) | O(V) | Queue |
| Dynamic Programming | O(n) | O(n) | DP table |

## Summary

- Space complexity measures memory usage as input grows
- Focus on auxiliary space (extra memory beyond input)
- Common complexities: O(1), O(log n), O(n), O(n²)
- Recursion uses stack space = O(depth)
- In-place algorithms use O(1) space
- Often can trade space for time and vice versa

---

**Next**: Explore [Recursion Guide](RecursionGuide.md) to master recursive solutions!
