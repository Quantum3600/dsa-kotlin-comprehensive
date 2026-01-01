# Time Complexity Guide

## 📖 Table of Contents
- [What is Time Complexity?](#what-is-time-complexity)
- [Why Do We Need It?](#why-do-we-need-it)
- [Big O Notation](#big-o-notation)
- [Common Time Complexities](#common-time-complexities)
- [How to Calculate Time Complexity](#how-to-calculate-time-complexity)
- [Examples and Patterns](#examples-and-patterns)
- [Best, Average, and Worst Case](#best-average-and-worst-case)
- [Practice Problems](#practice-problems)

## What is Time Complexity?

**Time complexity** is a way to describe how the runtime of an algorithm grows as the input size increases. It helps us:
- Compare different algorithms
- Predict performance with large inputs
- Make informed decisions about which algorithm to use

### 🎯 Key Insight
Time complexity doesn't measure actual time in seconds. Instead, it measures the **number of operations** relative to input size.

## Why Do We Need It?

Imagine you have two solutions to the same problem:
- **Solution A**: Takes 10 seconds for 100 items
- **Solution B**: Takes 5 seconds for 100 items

Which is better? Solution B seems faster, but what happens with 1 million items?

Time complexity helps us understand how algorithms **scale** with input size!

## Big O Notation

**Big O** describes the **upper bound** (worst case) of an algorithm's growth rate.

### Notation Format
- **O(1)** - Constant time
- **O(log n)** - Logarithmic time
- **O(n)** - Linear time
- **O(n log n)** - Linearithmic time
- **O(n²)** - Quadratic time
- **O(n³)** - Cubic time
- **O(2ⁿ)** - Exponential time
- **O(n!)** - Factorial time

### Visual Representation
```
Operations
│
│     O(n!)
│     │
│     │ O(2ⁿ)
│     │ │
│     │ │  O(n²)
│     │ │  │
│     │ │  │ O(n log n)
│     │ │  │ │
│     │ │  │ │ O(n)
│     │ │  │ │ │
│     │ │  │ │ │ O(log n)
│     │ │  │ │ │ │
│     │ │  │ │ │ │ O(1)
│     │ │  │ │ │ │ │
└─────┴─┴──┴─┴─┴─┴─┴──────> Input Size (n)

Slower ↑
Faster ↓
```

## Common Time Complexities

### O(1) - Constant Time
**Operations count stays the same regardless of input size**

```kotlin
fun getFirstElement(arr: IntArray): Int {
    return arr[0]  // Single operation, always
}
// n = 10 → 1 operation
// n = 1000 → 1 operation
// n = 1000000 → 1 operation
```

**Examples:**
- Array access by index
- Hash map get/put
- Push/pop from stack
- Math calculations

### O(log n) - Logarithmic Time
**Operations count grows slowly as input doubles**

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
// n = 16 → ~4 operations (2⁴ = 16)
// n = 1024 → ~10 operations (2¹⁰ = 1024)
// n = 1048576 → ~20 operations (2²⁰ = 1048576)
```

**Examples:**
- Binary search
- Balanced tree operations
- Finding power of 2

**Why "log"?** Each step eliminates half the remaining elements!

### O(n) - Linear Time
**Operations count grows proportionally with input size**

```kotlin
fun findMax(arr: IntArray): Int {
    var max = arr[0]
    for (i in 1 until arr.size) {  // n-1 iterations
        if (arr[i] > max) {
            max = arr[i]
        }
    }
    return max
}
// n = 10 → ~10 operations
// n = 100 → ~100 operations
// n = 1000 → ~1000 operations
```

**Examples:**
- Single loop through array
- Linear search
- Finding sum of elements

### O(n log n) - Linearithmic Time
**Combination of linear and logarithmic growth**

```kotlin
fun mergeSort(arr: IntArray): IntArray {
    // Divide: log n levels
    // Conquer: n work at each level
    // Total: n * log n
}
// n = 16 → ~64 operations (16 * 4)
// n = 1024 → ~10240 operations (1024 * 10)
```

**Examples:**
- Merge sort
- Quick sort (average case)
- Heap sort
- Most efficient comparison-based sorts

### O(n²) - Quadratic Time
**Operations grow with square of input size**

```kotlin
fun bubbleSort(arr: IntArray) {
    for (i in arr.indices) {           // n iterations
        for (j in 0 until arr.size - i - 1) {  // n iterations
            if (arr[j] > arr[j + 1]) {
                // Swap
            }
        }
    }
}
// n = 10 → ~100 operations
// n = 100 → ~10,000 operations
// n = 1000 → ~1,000,000 operations
```

**Examples:**
- Bubble sort, selection sort, insertion sort
- Nested loops iterating over same data
- Checking all pairs in array

### O(2ⁿ) - Exponential Time
**Operations double with each additional input element**

```kotlin
fun fibonacci(n: Int): Int {
    if (n <= 1) return n
    return fibonacci(n - 1) + fibonacci(n - 2)  // Branches exponentially
}
// n = 10 → ~1024 operations
// n = 20 → ~1,048,576 operations
// n = 30 → ~1,073,741,824 operations
```

**Examples:**
- Naive recursive Fibonacci
- Generating all subsets
- Solving problems by trying all combinations

### O(n!) - Factorial Time
**Operations grow factorially with input**

```kotlin
fun generatePermutations(arr: IntArray) {
    // Generate all possible orderings
    // n! permutations
}
// n = 5 → 120 operations
// n = 10 → 3,628,800 operations
// n = 15 → 1,307,674,368,000 operations
```

**Examples:**
- Generating all permutations
- Traveling salesman (brute force)
- N-Queens with all solutions

## How to Calculate Time Complexity

### Step 1: Identify Basic Operations
Count the most frequently executed operation in your code.

### Step 2: Express as Function of Input Size
Write how many times the operation executes in terms of n.

### Step 3: Simplify to Big O
Remove constants and lower-order terms, keep the dominant term.

### Rules for Simplification

1. **Drop Constants**
   - `O(2n)` → `O(n)`
   - `O(n/2)` → `O(n)`
   - `O(100)` → `O(1)`

2. **Drop Lower-Order Terms**
   - `O(n² + n)` → `O(n²)`
   - `O(n log n + n)` → `O(n log n)`
   - `O(n³ + n² + n)` → `O(n³)`

3. **Different Variables for Different Inputs**
   - Two arrays of size m and n: `O(m + n)`, not `O(n)`
   - Nested loops with different ranges: `O(m * n)`

## Examples and Patterns

### Example 1: Simple Loop
```kotlin
fun example1(n: Int) {
    for (i in 0 until n) {
        println(i)  // O(1) operation
    }
}
// Time Complexity: O(n)
// Loop runs n times, each iteration is O(1)
```

### Example 2: Nested Loops (Same Variable)
```kotlin
fun example2(n: Int) {
    for (i in 0 until n) {           // n times
        for (j in 0 until n) {       // n times
            println("$i, $j")        // O(1)
        }
    }
}
// Time Complexity: O(n²)
// Outer loop: n, Inner loop: n, Total: n * n
```

### Example 3: Nested Loops (Different Variables)
```kotlin
fun example3(m: Int, n: Int) {
    for (i in 0 until m) {           // m times
        for (j in 0 until n) {       // n times
            println("$i, $j")
        }
    }
}
// Time Complexity: O(m * n)
// Can't simplify to O(n²) because m and n are different
```

### Example 4: Sequential Loops
```kotlin
fun example4(n: Int) {
    for (i in 0 until n) {
        println(i)
    }
    for (j in 0 until n) {
        println(j)
    }
}
// Time Complexity: O(n) + O(n) = O(2n) = O(n)
// Sequential, not nested, so we add
```

### Example 5: Loop with Division
```kotlin
fun example5(n: Int) {
    var i = n
    while (i > 0) {
        println(i)
        i /= 2  // Dividing by 2 each time
    }
}
// Time Complexity: O(log n)
// Each iteration halves i
// n → n/2 → n/4 → n/8 → ... → 1
```

### Example 6: Loop with Multiplication
```kotlin
fun example6(n: Int) {
    var i = 1
    while (i < n) {
        println(i)
        i *= 2  // Multiplying by 2 each time
    }
}
// Time Complexity: O(log n)
// 1 → 2 → 4 → 8 → ... → n
// Number of steps = log₂(n)
```

### Example 7: Dependent Nested Loops
```kotlin
fun example7(n: Int) {
    for (i in 0 until n) {
        for (j in 0 until i) {  // j depends on i
            println("$i, $j")
        }
    }
}
// Iterations: 0 + 1 + 2 + ... + (n-1) = n(n-1)/2
// Time Complexity: O(n²)
```

### Example 8: Recursive Function
```kotlin
fun factorial(n: Int): Int {
    if (n <= 1) return 1
    return n * factorial(n - 1)
}
// Recurrence: T(n) = T(n-1) + O(1)
// Time Complexity: O(n)
// Makes n recursive calls
```

### Example 9: Binary Recursion
```kotlin
fun fibonacci(n: Int): Int {
    if (n <= 1) return n
    return fibonacci(n - 1) + fibonacci(n - 2)
}
// Recurrence: T(n) = T(n-1) + T(n-2) + O(1)
// Time Complexity: O(2ⁿ)
// Each call branches into two calls
```

## Best, Average, and Worst Case

### Definitions
- **Best Case (Ω)**: Minimum time required (most favorable input)
- **Average Case (Θ)**: Expected time for random input
- **Worst Case (O)**: Maximum time required (least favorable input)

### Example: Linear Search
```kotlin
fun linearSearch(arr: IntArray, target: Int): Int {
    for (i in arr.indices) {
        if (arr[i] == target) return i
    }
    return -1
}
```

- **Best Case**: O(1) - Target is first element
- **Average Case**: O(n/2) = O(n) - Target in middle
- **Worst Case**: O(n) - Target is last or not present

### Why Focus on Worst Case?
- Guarantees algorithm won't perform worse
- Most practical for real-world systems
- Big O typically refers to worst case

## Common Mistakes to Avoid

### Mistake 1: Counting Lines of Code
❌ Wrong: "This has 10 lines so O(10) = O(1)"
✅ Correct: Analyze how many times each line executes

### Mistake 2: Ignoring Loop Conditions
```kotlin
for (i in 0 until n step 2) { }  // Still O(n), not O(n/2)
```
Constants don't matter in Big O!

### Mistake 3: Confusing Space with Time
They are different! An O(1) time algorithm can use O(n) space.

### Mistake 4: Not Considering All Operations
Look at all loops, recursive calls, and function calls!

## Quick Reference Table

| Complexity | Name | Example | n=10 | n=100 | n=1000 |
|------------|------|---------|------|-------|--------|
| O(1) | Constant | Array access | 1 | 1 | 1 |
| O(log n) | Logarithmic | Binary search | 3 | 7 | 10 |
| O(n) | Linear | Loop through array | 10 | 100 | 1000 |
| O(n log n) | Linearithmic | Merge sort | 30 | 700 | 10000 |
| O(n²) | Quadratic | Nested loops | 100 | 10K | 1M |
| O(2ⁿ) | Exponential | Fibonacci recursion | 1K | ? | ? |

## Practice Problems

### Problem 1
```kotlin
fun mystery(n: Int) {
    var sum = 0
    for (i in 0 until n) {
        for (j in 0 until 100) {
            sum += i + j
        }
    }
    return sum
}
```
**Answer**: O(n) - Inner loop is constant (100 times)

### Problem 2
```kotlin
fun mystery2(n: Int) {
    for (i in 0 until n) {
        for (j in i until n) {
            println("$i, $j")
        }
    }
}
```
**Answer**: O(n²) - Total iterations = n + (n-1) + (n-2) + ... + 1 = n(n+1)/2

### Problem 3
```kotlin
fun mystery3(n: Int) {
    var i = 1
    while (i < n) {
        println(i)
        i *= 3
    }
}
```
**Answer**: O(log₃ n) = O(log n) - Multiplying by 3 each time

## Tips for Success

1. **Practice identifying patterns**: The more code you analyze, the faster you'll recognize complexities
2. **Start simple**: Break complex algorithms into smaller parts
3. **Draw it out**: Visualize how loops iterate
4. **Use pen and paper**: Write out iteration counts for small n
5. **Compare algorithms**: Understanding trade-offs helps solidify concepts

## Summary

- Time complexity describes how runtime grows with input size
- Big O notation expresses worst-case complexity
- Focus on dominant terms, drop constants
- Common complexities: O(1), O(log n), O(n), O(n log n), O(n²)
- Always consider worst case for guarantees
- Practice analyzing code to build intuition

---

**Next Steps**: Now that you understand time complexity, explore [Space Complexity](SpaceComplexity.md) to understand memory usage!
