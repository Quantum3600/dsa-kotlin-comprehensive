# Algorithm Design Patterns

## Table of Contents
1. [Introduction](#introduction)
2. [Two Pointers](#two-pointers)
3. [Sliding Window](#sliding-window)
4. [Divide and Conquer](#divide-conquer)
5. [Dynamic Programming](#dynamic-programming)
6. [Greedy Algorithms](#greedy)
7. [Backtracking](#backtracking)

---

## Introduction {#introduction}

Algorithm design patterns are proven approaches to solving common computational problems. Understanding these patterns helps you:
- Recognize problem types quickly
- Apply known solutions
- Optimize code efficiently
- Pass technical interviews

---

## Two Pointers Pattern {#two-pointers}

### Concept
Use two pointers to traverse data structure, often from different positions or directions.

### When to Use
- Array/string problems with pairs
- Sorted array manipulation
- Palindrome checking
- Finding pairs with specific sum

### Common Variations
1. **Opposite ends**: Start from both ends, move towards center
2. **Same direction**: Both start at beginning, move at different speeds
3. **Slow-fast**: One pointer moves faster than the other

### Examples

#### 1. Two Sum (Sorted Array)
```kotlin
fun twoSum(arr: IntArray, target: Int): Pair<Int, Int>? {
    var left = 0
    var right = arr.size - 1
    
    while (left < right) {
        val sum = arr[left] + arr[right]
        when {
            sum == target -> return Pair(left, right)
            sum < target -> left++
            else -> right--
        }
    }
    return null
}
```

#### 2. Remove Duplicates
```kotlin
fun removeDuplicates(arr: IntArray): Int {
    if (arr.isEmpty()) return 0
    
    var i = 0
    for (j in 1 until arr.size) {
        if (arr[j] != arr[i]) {
            i++
            arr[i] = arr[j]
        }
    }
    return i + 1
}
```

#### 3. Palindrome Check
```kotlin
fun isPalindrome(s: String): Boolean {
    var left = 0
    var right = s.length - 1
    
    while (left < right) {
        if (s[left] != s[right]) return false
        left++
        right--
    }
    return true
}
```

---

## Sliding Window Pattern {#sliding-window}

### Concept
Maintain a window that slides through the data, updating result as it moves.

### When to Use
- Subarray/substring problems
- Finding max/min in subarrays
- Problems involving contiguous sequences
- Problems asking for "longest/shortest/maximum" subsequence

### Types
1. **Fixed Size**: Window size is constant
2. **Variable Size**: Window expands and contracts

### Examples

#### 1. Maximum Sum Subarray (Fixed Window)
```kotlin
fun maxSum(arr: IntArray, k: Int): Int {
    var maxSum = 0
    var windowSum = 0
    
    // First window
    for (i in 0 until k) {
        windowSum += arr[i]
    }
    maxSum = windowSum
    
    // Slide window
    for (i in k until arr.size) {
        windowSum = windowSum - arr[i - k] + arr[i]
        maxSum = maxOf(maxSum, windowSum)
    }
    
    return maxSum
}
```

#### 2. Longest Substring Without Repeating Characters
```kotlin
fun lengthOfLongestSubstring(s: String): Int {
    val seen = mutableSetOf<Char>()
    var left = 0
    var maxLen = 0
    
    for (right in s.indices) {
        while (s[right] in seen) {
            seen.remove(s[left])
            left++
        }
        seen.add(s[right])
        maxLen = maxOf(maxLen, right - left + 1)
    }
    
    return maxLen
}
```

---

## Divide and Conquer {#divide-conquer}

### Concept
Break problem into smaller subproblems, solve recursively, combine results.

### Pattern
1. **Divide**: Break into subproblems
2. **Conquer**: Solve subproblems recursively
3. **Combine**: Merge subproblem solutions

### When to Use
- Problem can be divided into similar subproblems
- Optimal substructure exists
- Sorting, searching problems
- Tree-based problems

### Examples

#### 1. Merge Sort
```kotlin
fun mergeSort(arr: IntArray, left: Int, right: Int) {
    if (left < right) {
        val mid = left + (right - left) / 2
        mergeSort(arr, left, mid)      // Divide
        mergeSort(arr, mid + 1, right) // Divide
        merge(arr, left, mid, right)    // Combine
    }
}
```

#### 2. Binary Search
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
```

---

## Dynamic Programming {#dynamic-programming}

### Concept
Store solutions to subproblems to avoid recomputation.

### Key Characteristics
- Optimal substructure
- Overlapping subproblems
- Memoization or tabulation

### Approaches
1. **Top-Down (Memoization)**: Recursion + caching
2. **Bottom-Up (Tabulation)**: Iterative + table

### Examples

#### 1. Fibonacci (Top-Down)
```kotlin
fun fibonacci(n: Int, memo: MutableMap<Int, Long> = mutableMapOf()): Long {
    if (n <= 1) return n.toLong()
    if (n in memo) return memo[n]!!
    
    memo[n] = fibonacci(n - 1, memo) + fibonacci(n - 2, memo)
    return memo[n]!!
}
```

#### 2. Fibonacci (Bottom-Up)
```kotlin
fun fibonacci(n: Int): Long {
    if (n <= 1) return n.toLong()
    
    val dp = LongArray(n + 1)
    dp[0] = 0
    dp[1] = 1
    
    for (i in 2..n) {
        dp[i] = dp[i - 1] + dp[i - 2]
    }
    
    return dp[n]
}
```

---

## Greedy Algorithms {#greedy}

### Concept
Make locally optimal choice at each step, hoping to find global optimum.

### When to Use
- Problem has greedy choice property
- Optimal substructure exists
- Local optimum leads to global optimum

### Warning
⚠️ Not all problems have greedy solutions! Verify correctness.

### Examples

#### 1. Coin Change (Greedy - works for some coin systems)
```kotlin
fun minCoins(coins: IntArray, amount: Int): Int {
    coins.sortDescending()
    var remaining = amount
    var count = 0
    
    for (coin in coins) {
        count += remaining / coin
        remaining %= coin
    }
    
    return if (remaining == 0) count else -1
}
```

#### 2. Activity Selection
```kotlin
data class Activity(val start: Int, val end: Int)

fun maxActivities(activities: List<Activity>): Int {
    val sorted = activities.sortedBy { it.end }
    var count = 1
    var lastEnd = sorted[0].end
    
    for (i in 1 until sorted.size) {
        if (sorted[i].start >= lastEnd) {
            count++
            lastEnd = sorted[i].end
        }
    }
    
    return count
}
```

---

## Backtracking {#backtracking}

### Concept
Build solution incrementally, abandoning paths that don't lead to valid solution.

### Pattern
1. Choose an option
2. Explore with that choice
3. If it fails, undo (backtrack) and try another

### When to Use
- Finding all solutions
- Combinatorial problems
- Constraint satisfaction
- Puzzles (Sudoku, N-Queens)

### Examples

#### 1. Generate All Subsets
```kotlin
fun subsets(nums: IntArray): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    
    fun backtrack(start: Int, current: MutableList<Int>) {
        result.add(current.toList())
        
        for (i in start until nums.size) {
            current.add(nums[i])
            backtrack(i + 1, current)
            current.removeAt(current.size - 1)
        }
    }
    
    backtrack(0, mutableListOf())
    return result
}
```

#### 2. Generate Permutations
```kotlin
fun permute(nums: IntArray): List<List<Int>> {
    val result = mutableListOf<List<Int>>()
    
    fun backtrack(current: MutableList<Int>, remaining: MutableList<Int>) {
        if (remaining.isEmpty()) {
            result.add(current.toList())
            return
        }
        
        for (i in remaining.indices) {
            val num = remaining[i]
            current.add(num)
            remaining.removeAt(i)
            backtrack(current, remaining)
            remaining.add(i, num)
            current.removeAt(current.size - 1)
        }
    }
    
    backtrack(mutableListOf(), nums.toMutableList())
    return result
}
```

---

## Pattern Recognition Guide

### Problem → Pattern Mapping

| Problem Type | Pattern |
|-------------|---------|
| Find pair with sum | Two Pointers |
| Max/min in subarray | Sliding Window |
| Divide into halves | Divide and Conquer |
| Optimize with previous results | Dynamic Programming |
| Local optimum → global | Greedy |
| Find all solutions | Backtracking |

### Keywords to Look For

**Two Pointers**:
- Sorted array
- Find pair
- Remove duplicates
- Palindrome

**Sliding Window**:
- Subarray/substring
- Contiguous sequence
- Longest/shortest with condition

**Divide and Conquer**:
- Sorted data
- Binary
- Merge
- Split in half

**Dynamic Programming**:
- Optimization
- Count ways
- Min/max value
- "Can you do X"

**Greedy**:
- Maximize/minimize
- Selection
- Scheduling
- Local choice

**Backtracking**:
- All combinations
- All permutations
- Generate all
- Constraint satisfaction

---

## Key Takeaways

1. ✓ Learn to recognize problem patterns
2. ✓ Two Pointers for sorted array problems
3. ✓ Sliding Window for subarray problems
4. ✓ Divide and Conquer for recursive decomposition
5. ✓ Dynamic Programming for optimization
6. ✓ Greedy for local optimal choices
7. ✓ Backtracking for generating solutions
8. ✓ Practice mapping problems to patterns
