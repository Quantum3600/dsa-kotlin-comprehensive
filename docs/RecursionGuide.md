# Recursion Guide

## Table of Contents
1. [Introduction to Recursion](#introduction)
2. [How Recursion Works](#how-it-works)
3. [Base Case and Recursive Case](#base-and-recursive)
4. [Common Patterns](#common-patterns)
5. [Recursion vs Iteration](#vs-iteration)
6. [Practical Examples](#examples)
7. [Best Practices](#best-practices)

---

## Introduction to Recursion {#introduction}

**Recursion** is a programming technique where a function calls itself to solve a problem by breaking it down into smaller, similar subproblems.

### Key Characteristics
- Function calls itself
- Problem divided into smaller instances
- Eventually reaches a base case
- Solution built from subproblem solutions

### When to Use Recursion
✓ Problem can be divided into similar subproblems  
✓ Tree/graph traversal  
✓ Backtracking problems  
✓ Divide and conquer algorithms  
✓ Mathematical sequences (factorial, fibonacci)

---

## How Recursion Works {#how-it-works}

### Call Stack
Every recursive call is pushed onto the call stack. When a call returns, it's popped from the stack.

```
factorial(4)
  → factorial(3)
    → factorial(2)
      → factorial(1)
        → factorial(0)  // Base case
        ← returns 1
      ← returns 1 * 1 = 1
    ← returns 2 * 1 = 2
  ← returns 3 * 2 = 6
← returns 4 * 6 = 24
```

### Stack Depth
- Each call uses stack memory
- Deep recursion can cause `StackOverflowError`
- Typical stack depth limit: ~1000-10000 calls

---

## Base Case and Recursive Case {#base-and-recursive}

### Base Case
The condition that stops recursion. **Every recursive function MUST have a base case!**

```kotlin
fun factorial(n: Int): Long {
    if (n <= 1) return 1  // BASE CASE
    return n * factorial(n - 1)  // RECURSIVE CASE
}
```

### Recursive Case
The part where the function calls itself with a "smaller" problem.

**Requirements:**
1. Must move towards base case
2. Must change parameters (usually smaller)
3. Must eventually reach base case

---

## Common Recursion Patterns {#common-patterns}

### 1. Linear Recursion
Single recursive call per function execution.

```kotlin
// Sum of first n numbers
fun sum(n: Int): Int {
    if (n == 0) return 0
    return n + sum(n - 1)
}
```

### 2. Binary Recursion
Two recursive calls per execution.

```kotlin
// Fibonacci
fun fibonacci(n: Int): Long {
    if (n <= 1) return n.toLong()
    return fibonacci(n - 1) + fibonacci(n - 2)
}
```

### 3. Tail Recursion
Recursive call is the last operation.

```kotlin
tailrec fun factorial(n: Int, acc: Long = 1): Long {
    if (n <= 1) return acc
    return factorial(n - 1, n * acc)
}
```

### 4. Multiple Recursion
More than two recursive calls.

```kotlin
// Tree traversal
fun traverse(node: TreeNode?) {
    if (node == null) return
    traverse(node.left)
    traverse(node.right)
    traverse(node.center)
}
```

---

## Recursion vs Iteration {#vs-iteration}

### Comparison

| Aspect | Recursion | Iteration |
|--------|-----------|-----------|
| Memory | O(n) stack space | O(1) space |
| Speed | Slower (function calls) | Faster |
| Code | Often simpler | Can be complex |
| Readability | More intuitive | Less intuitive |
| Risk | Stack overflow | None |

### When to Choose

**Use Recursion:**
- Tree/graph problems
- Backtracking
- Divide and conquer
- When code is much simpler

**Use Iteration:**
- Simple loops
- Performance critical
- Large input sizes
- Stack depth concerns

---

## Practical Examples {#examples}

### Example 1: Factorial

```kotlin
fun factorial(n: Int): Long {
    // Base case
    if (n <= 1) return 1
    
    // Recursive case
    return n * factorial(n - 1)
}

// Example: factorial(5) = 5 * 4 * 3 * 2 * 1 = 120
```

### Example 2: Fibonacci

```kotlin
// Naive recursion - exponential time
fun fibonacci(n: Int): Long {
    if (n <= 1) return n.toLong()
    return fibonacci(n - 1) + fibonacci(n - 2)
}

// Optimized with memoization
fun fibonacciMemo(n: Int, memo: MutableMap<Int, Long> = mutableMapOf()): Long {
    if (n <= 1) return n.toLong()
    if (n in memo) return memo[n]!!
    
    val result = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo)
    memo[n] = result
    return result
}
```

### Example 3: Binary Search

```kotlin
fun binarySearch(arr: IntArray, target: Int, left: Int, right: Int): Int {
    // Base case: not found
    if (left > right) return -1
    
    val mid = left + (right - left) / 2
    
    // Base case: found
    if (arr[mid] == target) return mid
    
    // Recursive cases
    return if (arr[mid] > target) {
        binarySearch(arr, target, left, mid - 1)
    } else {
        binarySearch(arr, target, mid + 1, right)
    }
}
```

### Example 4: Power Function

```kotlin
fun power(base: Int, exp: Int): Long {
    // Base cases
    if (exp == 0) return 1
    if (exp == 1) return base.toLong()
    
    // Recursive case with optimization
    val half = power(base, exp / 2)
    return if (exp % 2 == 0) {
        half * half
    } else {
        half * half * base
    }
}
```

### Example 5: Sum of Digits

```kotlin
fun sumOfDigits(n: Int): Int {
    // Base case
    if (n == 0) return 0
    
    // Recursive case
    return (n % 10) + sumOfDigits(n / 10)
}
```

### Example 6: Reverse String

```kotlin
fun reverseString(str: String): String {
    // Base case
    if (str.isEmpty()) return ""
    
    // Recursive case
    return reverseString(str.substring(1)) + str[0]
}
```

### Example 7: Tower of Hanoi

```kotlin
fun towerOfHanoi(n: Int, from: String, to: String, aux: String) {
    // Base case
    if (n == 1) {
        println("Move disk 1 from $from to $to")
        return
    }
    
    // Move n-1 disks from source to auxiliary
    towerOfHanoi(n - 1, from, aux, to)
    
    // Move nth disk from source to destination
    println("Move disk $n from $from to $to")
    
    // Move n-1 disks from auxiliary to destination
    towerOfHanoi(n - 1, aux, to, from)
}
```

---

## Best Practices {#best-practices}

### 1. Always Have a Base Case
```kotlin
// ✗ BAD - No base case, infinite recursion
fun bad(n: Int): Int {
    return 1 + bad(n - 1)
}

// ✓ GOOD - Has base case
fun good(n: Int): Int {
    if (n <= 0) return 0
    return 1 + good(n - 1)
}
```

### 2. Ensure Progress Toward Base Case
```kotlin
// ✗ BAD - Doesn't progress
fun bad(n: Int): Int {
    if (n == 0) return 0
    return bad(n)  // Same n!
}

// ✓ GOOD - Progresses toward base case
fun good(n: Int): Int {
    if (n == 0) return 0
    return good(n - 1)  // Smaller n
}
```

### 3. Use Tail Recursion When Possible
```kotlin
// Regular recursion
fun factorial(n: Int): Long {
    if (n <= 1) return 1
    return n * factorial(n - 1)  // Not tail recursive
}

// Tail recursive (can be optimized)
tailrec fun factorialTail(n: Int, acc: Long = 1): Long {
    if (n <= 1) return acc
    return factorialTail(n - 1, n * acc)  // Tail recursive
}
```

### 4. Consider Memoization
```kotlin
// Without memoization - O(2^n)
fun fib(n: Int): Long {
    if (n <= 1) return n.toLong()
    return fib(n - 1) + fib(n - 2)
}

// With memoization - O(n)
fun fibMemo(n: Int, memo: MutableMap<Int, Long> = mutableMapOf()): Long {
    if (n <= 1) return n.toLong()
    if (n in memo) return memo[n]!!
    memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo)
    return memo[n]!!
}
```

### 5. Watch Stack Depth
```kotlin
// May cause StackOverflowError for large n
fun sum(n: Int): Int {
    if (n == 0) return 0
    return n + sum(n - 1)  // n stack frames
}

// Better: Use iteration for large n
fun sumIterative(n: Int): Int {
    var sum = 0
    for (i in 1..n) sum += i
    return sum
}
```

---

## Common Mistakes

### Mistake 1: Missing Base Case
```kotlin
// ✗ Will cause StackOverflowError
fun countdown(n: Int) {
    println(n)
    countdown(n - 1)  // Never stops!
}
```

### Mistake 2: Wrong Base Case
```kotlin
// ✗ Doesn't handle n=0
fun factorial(n: Int): Int {
    if (n == 1) return 1  // What if n=0?
    return n * factorial(n - 1)
}
```

### Mistake 3: Not Moving Toward Base Case
```kotlin
// ✗ Infinite recursion if n is odd
fun bad(n: Int): Int {
    if (n == 0) return 0
    return bad(n - 2)  // Skips odd numbers!
}
```

---

## Key Takeaways

1. ✓ Every recursive function needs a base case
2. ✓ Recursive case must progress toward base case
3. ✓ Consider stack depth for large inputs
4. ✓ Use memoization to optimize repeated calculations
5. ✓ Tail recursion can be optimized by compiler
6. ✓ When in doubt, use iteration for simple problems
7. ✓ Recursion excels at tree/graph problems
8. ✓ Always test with small inputs first

---

## Further Reading

- Divide and Conquer algorithms
- Dynamic Programming (recursion + memoization)
- Backtracking algorithms
- Tree and Graph traversals
- Recursion in functional programming
