# Dynamic Programming Patterns Guide

## Table of Contents
1. [Introduction to Dynamic Programming](#introduction)
2. [Core DP Patterns](#core-patterns)
3. [Pattern Recognition](#pattern-recognition)
4. [Implementation Strategies](#implementation-strategies)
5. [Common DP Problems by Pattern](#common-problems)
6. [Optimization Techniques](#optimization-techniques)
7. [Practice Guidelines](#practice-guidelines)

---

## Introduction to Dynamic Programming {#introduction}

### What is Dynamic Programming?

**Dynamic Programming (DP)** is a method for solving complex problems by breaking them down into simpler subproblems. It is particularly useful when:
- The problem has **overlapping subproblems**
- The problem exhibits **optimal substructure**
- We can build the solution from previously solved subproblems

### Key Characteristics

#### 1. Overlapping Subproblems
The same subproblems are solved multiple times. DP stores these solutions to avoid redundant computation.

**Example**: Fibonacci Numbers
```
fib(5) requires fib(4) and fib(3)
fib(4) requires fib(3) and fib(2)
Notice: fib(3) is computed twice!
```

#### 2. Optimal Substructure
An optimal solution can be constructed from optimal solutions of its subproblems.

**Example**: Shortest path in a graph
```
If the shortest path from A to C goes through B,
then the path from A to B must also be shortest.
```

### DP vs Greedy vs Divide and Conquer

| Aspect | DP | Greedy | Divide & Conquer |
|--------|----|---------|--------------------|
| Approach | Bottom-up or Top-down | Local optimum | Split & merge |
| Subproblems | Overlapping | Independent | Independent |
| Optimal solution | Yes (if exists) | Maybe | Yes (if exists) |
| Time complexity | Polynomial | Usually linear | Usually O(n log n) |

---

## Core DP Patterns {#core-patterns}

### 1. **1D DP Pattern**

Problems where state depends on a single dimension (usually array index).

**Formula**: `dp[i]` represents the answer for the first `i` elements

**Common Problems**:
- Climbing Stairs
- House Robber
- Decode Ways
- Jump Game

**Template**:
```kotlin
fun solve1D(arr: IntArray): Int {
    val n = arr.size
    val dp = IntArray(n)
    
    // Base case
    dp[0] = /* base value */
    
    // Fill DP table
    for (i in 1 until n) {
        dp[i] = /* recurrence relation using dp[i-1], dp[i-2], etc. */
    }
    
    return dp[n-1]
}
```

**Example: Climbing Stairs**
```kotlin
// At each step, you can climb 1 or 2 stairs
fun climbStairs(n: Int): Int {
    if (n <= 2) return n
    
    val dp = IntArray(n + 1)
    dp[1] = 1  // One way to reach step 1
    dp[2] = 2  // Two ways to reach step 2
    
    for (i in 3..n) {
        dp[i] = dp[i-1] + dp[i-2]  // Sum of previous two steps
    }
    
    return dp[n]
}
```

---

### 2. **2D DP Pattern**

Problems where state depends on two dimensions (usually two indices or strings).

**Formula**: `dp[i][j]` represents the answer for subproblem involving first `i` and `j` elements

**Common Problems**:
- Longest Common Subsequence
- Edit Distance
- Unique Paths
- Minimum Path Sum

**Template**:
```kotlin
fun solve2D(arr1: IntArray, arr2: IntArray): Int {
    val m = arr1.size
    val n = arr2.size
    val dp = Array(m + 1) { IntArray(n + 1) }
    
    // Base cases
    for (i in 0..m) dp[i][0] = /* base value */
    for (j in 0..n) dp[0][j] = /* base value */
    
    // Fill DP table
    for (i in 1..m) {
        for (j in 1..n) {
            dp[i][j] = /* recurrence using dp[i-1][j], dp[i][j-1], dp[i-1][j-1] */
        }
    }
    
    return dp[m][n]
}
```

**Example: Longest Common Subsequence (LCS)**
```kotlin
fun longestCommonSubsequence(text1: String, text2: String): Int {
    val m = text1.length
    val n = text2.length
    val dp = Array(m + 1) { IntArray(n + 1) }
    
    for (i in 1..m) {
        for (j in 1..n) {
            if (text1[i-1] == text2[j-1]) {
                // Characters match: extend LCS by 1
                dp[i][j] = dp[i-1][j-1] + 1
            } else {
                // Characters don't match: take max of excluding one character
                dp[i][j] = maxOf(dp[i-1][j], dp[i][j-1])
            }
        }
    }
    
    return dp[m][n]
}
```

---

### 3. **Knapsack Pattern**

Problems involving selecting items with constraints (usually capacity).

**Types**:
- **0/1 Knapsack**: Each item can be taken at most once
- **Unbounded Knapsack**: Each item can be taken unlimited times
- **Bounded Knapsack**: Each item has a limited quantity

**Formula**: `dp[i][w]` = maximum value using first `i` items with weight limit `w`

**0/1 Knapsack Template**:
```kotlin
fun knapsack(weights: IntArray, values: IntArray, capacity: Int): Int {
    val n = weights.size
    val dp = Array(n + 1) { IntArray(capacity + 1) }
    
    for (i in 1..n) {
        for (w in 1..capacity) {
            if (weights[i-1] <= w) {
                // Can take item i: choose max of taking or not taking
                dp[i][w] = maxOf(
                    dp[i-1][w],  // Don't take item i
                    dp[i-1][w - weights[i-1]] + values[i-1]  // Take item i
                )
            } else {
                // Can't take item i: weight exceeds capacity
                dp[i][w] = dp[i-1][w]
            }
        }
    }
    
    return dp[n][capacity]
}
```

**Space Optimized (1D array)**:
```kotlin
fun knapsackOptimized(weights: IntArray, values: IntArray, capacity: Int): Int {
    val dp = IntArray(capacity + 1)
    
    for (i in weights.indices) {
        // Traverse backwards to avoid using updated values
        for (w in capacity downTo weights[i]) {
            dp[w] = maxOf(dp[w], dp[w - weights[i]] + values[i])
        }
    }
    
    return dp[capacity]
}
```

**Applications**:
- Subset Sum
- Partition Equal Subset Sum
- Target Sum
- Coin Change

---

### 4. **Subsequence Pattern**

Problems finding optimal subsequences with specific properties.

**Common Problems**:
- Longest Increasing Subsequence (LIS)
- Longest Decreasing Subsequence
- Maximum Sum Increasing Subsequence
- Number of LIS

**Template (LIS)**:
```kotlin
fun lengthOfLIS(nums: IntArray): Int {
    val n = nums.size
    if (n == 0) return 0
    
    // dp[i] = length of LIS ending at index i
    val dp = IntArray(n) { 1 }
    var maxLength = 1
    
    for (i in 1 until n) {
        for (j in 0 until i) {
            if (nums[j] < nums[i]) {
                // Can extend LIS ending at j
                dp[i] = maxOf(dp[i], dp[j] + 1)
            }
        }
        maxLength = maxOf(maxLength, dp[i])
    }
    
    return maxLength
}
```

**Optimized O(n log n) using Binary Search**:
```kotlin
fun lengthOfLISOptimized(nums: IntArray): Int {
    val tails = mutableListOf<Int>()
    
    for (num in nums) {
        val pos = tails.binarySearch(num)
        val insertPos = if (pos < 0) -(pos + 1) else pos
        
        if (insertPos == tails.size) {
            tails.add(num)
        } else {
            tails[insertPos] = num
        }
    }
    
    return tails.size
}
```

---

### 5. **String DP Pattern**

Problems involving string operations like matching, editing, or palindromes.

**Common Problems**:
- Edit Distance
- Longest Palindromic Substring
- Palindrome Partitioning
- Regular Expression Matching

**Example: Edit Distance**
```kotlin
fun minDistance(word1: String, word2: String): Int {
    val m = word1.length
    val n = word2.length
    val dp = Array(m + 1) { IntArray(n + 1) }
    
    // Base cases: empty string conversions
    for (i in 0..m) dp[i][0] = i  // Delete all characters
    for (j in 0..n) dp[0][j] = j  // Insert all characters
    
    for (i in 1..m) {
        for (j in 1..n) {
            if (word1[i-1] == word2[j-1]) {
                // Characters match: no operation needed
                dp[i][j] = dp[i-1][j-1]
            } else {
                // Characters don't match: try all operations
                dp[i][j] = 1 + minOf(
                    dp[i-1][j],      // Delete from word1
                    dp[i][j-1],      // Insert into word1
                    dp[i-1][j-1]     // Replace in word1
                )
            }
        }
    }
    
    return dp[m][n]
}
```

---

### 6. **Interval DP Pattern**

Problems involving intervals or subarrays where solution depends on smaller intervals.

**Formula**: `dp[i][j]` represents answer for interval `[i, j]`

**Common Problems**:
- Matrix Chain Multiplication
- Burst Balloons
- Minimum Cost to Merge Stones
- Palindrome Partitioning II

**Template**:
```kotlin
fun intervalDP(arr: IntArray): Int {
    val n = arr.size
    val dp = Array(n) { IntArray(n) }
    
    // Base case: intervals of length 1
    for (i in 0 until n) {
        dp[i][i] = /* base value */
    }
    
    // Iterate by increasing interval length
    for (len in 2..n) {
        for (i in 0..n - len) {
            val j = i + len - 1
            dp[i][j] = Int.MAX_VALUE
            
            // Try all possible split points
            for (k in i until j) {
                val cost = dp[i][k] + dp[k+1][j] + /* merge cost */
                dp[i][j] = minOf(dp[i][j], cost)
            }
        }
    }
    
    return dp[0][n-1]
}
```

---

### 7. **State Machine DP Pattern**

Problems with multiple states at each position.

**Common Problems**:
- Best Time to Buy and Sell Stock (all variations)
- Paint House
- Paint Fence

**Example: Stock with Cooldown**
```kotlin
fun maxProfit(prices: IntArray): Int {
    val n = prices.size
    if (n <= 1) return 0
    
    // Three states: hold stock, sold stock, cooldown
    var hold = -prices[0]    // Bought stock
    var sold = 0             // Just sold
    var cooldown = 0         // Cooldown or never bought
    
    for (i in 1 until n) {
        val prevHold = hold
        val prevSold = sold
        val prevCooldown = cooldown
        
        hold = maxOf(prevHold, prevCooldown - prices[i])
        sold = prevHold + prices[i]
        cooldown = maxOf(prevCooldown, prevSold)
    }
    
    return maxOf(sold, cooldown)
}
```

---

### 8. **Game Theory DP Pattern**

Problems involving two players playing optimally.

**Common Problems**:
- Stone Game
- Predict the Winner
- Can I Win

**Template**:
```kotlin
fun gameTheoryDP(nums: IntArray): Boolean {
    val n = nums.size
    // dp[i][j] = max score difference player 1 can achieve over player 2
    // for subarray [i, j]
    val dp = Array(n) { IntArray(n) }
    
    // Base case: single element
    for (i in 0 until n) {
        dp[i][i] = nums[i]
    }
    
    // Fill diagonally
    for (len in 2..n) {
        for (i in 0..n - len) {
            val j = i + len - 1
            // Player can take from either end
            dp[i][j] = maxOf(
                nums[i] - dp[i+1][j],   // Take left
                nums[j] - dp[i][j-1]    // Take right
            )
        }
    }
    
    return dp[0][n-1] >= 0  // Player 1 wins if difference >= 0
}
```

---

## Pattern Recognition {#pattern-recognition}

### How to Identify DP Problems

1. **Look for these keywords**:
   - "Optimal" (maximum/minimum)
   - "Count number of ways"
   - "Longest/shortest"
   - "All possible"

2. **Check for overlapping subproblems**:
   - Drawing recursion tree shows repeated states
   - Can you cache results?

3. **Check for optimal substructure**:
   - Can solution be built from subproblem solutions?
   - Does making optimal choice at each step lead to optimal solution?

### Problem Classification Flowchart

```
Does problem ask for optimal value or count?
├─ Yes: Likely DP
│  ├─ One array/string involved?
│  │  └─ Use 1D DP
│  ├─ Two arrays/strings involved?
│  │  └─ Use 2D DP
│  ├─ Items with capacity constraint?
│  │  └─ Use Knapsack pattern
│  ├─ Intervals/subarrays?
│  │  └─ Use Interval DP
│  └─ Multiple states at each position?
│     └─ Use State Machine DP
└─ No: Consider other approaches
```

---

## Implementation Strategies {#implementation-strategies}

### Top-Down (Memoization)

**Advantages**:
- Natural to think about (follows recursion)
- Only computes needed subproblems
- Easy to write from recursive solution

**Template**:
```kotlin
class Solution {
    private lateinit var memo: Array<IntArray>
    
    fun solve(params): Int {
        memo = Array(size1) { IntArray(size2) { -1 } }
        return dp(initialState)
    }
    
    private fun dp(state1: Int, state2: Int): Int {
        // Base case
        if (/* base condition */) return /* base value */
        
        // Check memo
        if (memo[state1][state2] != -1) return memo[state1][state2]
        
        // Compute and store
        val result = /* recurrence relation */
        memo[state1][state2] = result
        return result
    }
}
```

### Bottom-Up (Tabulation)

**Advantages**:
- Usually faster (no recursion overhead)
- Easier to optimize space
- More intuitive for iteration order

**Template**:
```kotlin
fun solve(params): Int {
    val dp = Array(size1) { IntArray(size2) }
    
    // Initialize base cases
    /* fill base cases */
    
    // Fill table in correct order
    for (i in /* range */) {
        for (j in /* range */) {
            dp[i][j] = /* recurrence relation */
        }
    }
    
    return dp[target1][target2]
}
```

### Space Optimization

Many DP problems can be optimized from O(n²) or O(n) space to O(1) or O(n).

**Techniques**:
1. **Rolling array**: Only keep last few rows/columns
2. **In-place updates**: Modify input array
3. **Two variables**: For Fibonacci-like problems

**Example: Fibonacci Space Optimization**
```kotlin
// O(n) space
fun fibArray(n: Int): Int {
    val dp = IntArray(n + 1)
    dp[0] = 0
    dp[1] = 1
    for (i in 2..n) dp[i] = dp[i-1] + dp[i-2]
    return dp[n]
}

// O(1) space
fun fibOptimized(n: Int): Int {
    if (n <= 1) return n
    var prev2 = 0
    var prev1 = 1
    
    for (i in 2..n) {
        val current = prev1 + prev2
        prev2 = prev1
        prev1 = current
    }
    return prev1
}
```

---

## Common DP Problems by Pattern {#common-problems}

### 1D DP
- Climbing Stairs
- House Robber
- Decode Ways
- Min Cost Climbing Stairs
- Maximum Product Subarray
- Jump Game

### 2D DP
- Unique Paths
- Minimum Path Sum
- Longest Common Subsequence
- Edit Distance
- Maximal Square
- Interleaving String

### Knapsack
- 0/1 Knapsack
- Subset Sum
- Partition Equal Subset Sum
- Target Sum
- Coin Change
- Coin Change 2

### Subsequence
- Longest Increasing Subsequence
- Longest Common Subsequence
- Maximum Sum Increasing Subsequence
- Russian Doll Envelopes

### String DP
- Longest Palindromic Substring
- Palindrome Partitioning
- Regular Expression Matching
- Wildcard Matching
- Distinct Subsequences

### Interval DP
- Matrix Chain Multiplication
- Burst Balloons
- Minimum Cost to Merge Stones
- Strange Printer

---

## Optimization Techniques {#optimization-techniques}

1. **State Reduction**: Minimize state dimensions
2. **Space Optimization**: Use rolling arrays or variables
3. **Early Termination**: Stop when answer is found
4. **Binary Search**: For specific patterns like LIS
5. **Monotonic Stack/Queue**: For range queries
6. **Bitmasking**: For subset problems with small n

---

## Practice Guidelines {#practice-guidelines}

### Beginner Level
1. Start with 1D DP (Fibonacci, Climbing Stairs)
2. Move to simple 2D (Unique Paths, Minimum Path Sum)
3. Learn basic Knapsack problems

### Intermediate Level
1. Master LCS and Edit Distance variations
2. Practice different Knapsack types
3. Learn State Machine DP

### Advanced Level
1. Interval DP (Matrix Chain Multiplication)
2. Game Theory DP
3. DP with bitmasking
4. DP on trees and graphs

### Problem-Solving Strategy

1. **Identify if it's DP**: Look for optimal substructure and overlapping subproblems
2. **Define state**: What information do we need to track?
3. **Write recurrence**: How does state relate to previous states?
4. **Handle base cases**: What are the simplest cases?
5. **Implement**: Start with top-down, optimize to bottom-up
6. **Optimize**: Can we reduce space or time?

---

## Conclusion

Dynamic Programming is a powerful technique that requires practice to master. Start with simple problems, understand the patterns, and gradually move to more complex variations. Remember:

- **Break down** problems into subproblems
- **Identify** the pattern
- **Define** state and recurrence clearly
- **Start simple**, optimize later
- **Practice** regularly with different patterns

Good luck with your DP journey! 🚀
