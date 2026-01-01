# Problem Solving Strategies

## Table of Contents
1. [Understanding the Problem](#understanding)
2. [Problem-Solving Framework](#framework)
3. [Common Techniques](#techniques)
4. [Interview Tips](#interview-tips)
5. [Debugging Strategies](#debugging)
6. [Practice Approach](#practice)

---

## Understanding the Problem {#understanding}

### Step 1: Read Carefully
- Read problem statement 2-3 times
- Identify input format and constraints
- Understand what output is expected
- Note edge cases mentioned

### Step 2: Ask Clarifying Questions
- What's the input size range?
- Are there duplicates?
- Is the input sorted?
- What should I return for edge cases?
- Any memory constraints?

### Step 3: Work Through Examples
- Trace through given examples
- Create your own simple examples
- Think of edge cases:
  - Empty input
  - Single element
  - All same elements
  - Maximum constraints

---

## Problem-Solving Framework {#framework}

### 1. Understand
□ Can you explain the problem in your own words?  
□ What are the inputs and outputs?  
□ Do you understand all constraints?

### 2. Plan
□ What pattern does this fit?  
□ What data structures are needed?  
□ What's the brute force approach?  
□ Can you optimize it?

### 3. Execute
□ Write pseudocode first  
□ Code step by step  
□ Test as you write

### 4. Review
□ Does it handle edge cases?  
□ Can you optimize further?  
□ Is the code clean and readable?

---

## Common Techniques {#techniques}

### 1. Brute Force First
Always consider brute force solution first:
- Often simpler to implement
- Helps understand problem
- Baseline for optimization
- May be good enough for small inputs

**Example**: Find pair with sum
```kotlin
// Brute force: O(n²)
fun findPair(arr: IntArray, target: Int): Pair<Int, Int>? {
    for (i in arr.indices) {
        for (j in i + 1 until arr.size) {
            if (arr[i] + arr[j] == target) {
                return Pair(i, j)
            }
        }
    }
    return null
}
```

### 2. Optimize with Data Structures
Use appropriate data structures:
- HashMap for O(1) lookups
- Set for membership tests
- Stack/Queue for LIFO/FIFO
- Heap for min/max

**Optimized**: Same problem O(n)
```kotlin
fun findPair(arr: IntArray, target: Int): Pair<Int, Int>? {
    val seen = mutableMapOf<Int, Int>()
    for (i in arr.indices) {
        val complement = target - arr[i]
        if (complement in seen) {
            return Pair(seen[complement]!!, i)
        }
        seen[arr[i]] = i
    }
    return null
}
```

### 3. Look for Patterns
Recognize common patterns:
- Two pointers
- Sliding window
- Divide and conquer
- Dynamic programming
- Backtracking

### 4. Draw Pictures
Visual representation helps:
- Arrays: Draw boxes
- Trees: Draw nodes and edges
- Process: Draw step-by-step
- Flow: Use arrows

### 5. Work Backwards
Sometimes easier to work from goal:
- Start with desired output
- Determine what's needed
- Work back to input

### 6. Simplify the Problem
- Reduce constraints
- Solve simpler version first
- Build up to full solution

---

## Interview Tips {#interview-tips}

### Before Coding

1. **Communicate Throughout**
   - Explain your thought process
   - Ask questions
   - Discuss trade-offs

2. **Clarify Requirements**
   ```
   "Can I assume the array is sorted?"
   "Should I handle negative numbers?"
   "What should I return if no solution exists?"
   ```

3. **State Your Approach**
   ```
   "I'll use a hash map to track seen elements,
    which gives us O(n) time and O(n) space."
   ```

### While Coding

4. **Write Clean Code**
   - Meaningful variable names
   - Proper indentation
   - Comments for complex logic

5. **Test As You Go**
   ```kotlin
   // Test with simple case
   val test1 = findPair(intArrayOf(2, 7, 11), 9)
   println("Expected: (0,1), Got: $test1")
   ```

6. **Handle Edge Cases**
   - Empty input
   - Single element
   - All same values
   - Min/max constraints

### After Coding

7. **Walk Through Solution**
   - Trace with example
   - Verify correctness
   - Check edge cases

8. **Analyze Complexity**
   ```
   "Time: O(n) - single pass through array
    Space: O(n) - hash map stores n elements"
   ```

9. **Discuss Optimizations**
   ```
   "If memory is constrained, we could use
    two pointers on a sorted array for O(1) space."
   ```

---

## Debugging Strategies {#debugging}

### 1. Print Debugging
Add strategic print statements:
```kotlin
fun debug(arr: IntArray) {
    println("Input: ${arr.contentToString()}")
    for (i in arr.indices) {
        println("Step $i: Processing ${arr[i]}")
        // ... logic
    }
}
```

### 2. Trace Execution
Walk through code line by line:
- Use debugger
- Print intermediate values
- Check assumptions

### 3. Test Edge Cases
```kotlin
// Edge case tests
test(intArrayOf())           // Empty
test(intArrayOf(1))         // Single element
test(intArrayOf(1, 1, 1))   // All same
test(intArrayOf(1, 2))      // Minimum size
```

### 4. Binary Search Debugging
If bug occurs sometimes:
- Identify working and failing cases
- Find simplest failing case
- Compare with working case

### 5. Rubber Duck Debugging
Explain code to someone (or something):
- Forces you to think clearly
- Often reveals bugs
- Clarifies logic

---

## Practice Approach {#practice}

### 1. Start with Easy Problems
- Build confidence
- Learn basic patterns
- Understand fundamentals

### 2. Focus on Patterns
Group problems by pattern:
- Two pointers problems
- Sliding window problems
- Binary search problems
- etc.

### 3. Time Yourself
Simulate interview conditions:
- 20-30 minutes per problem
- Explain as you code
- Practice under pressure

### 4. Review Solutions
After solving (or struggling):
- Read optimal solutions
- Understand different approaches
- Learn from others

### 5. Redo Problems
Revisit problems after time:
- Ensure you remember approach
- Try to optimize further
- Explain to someone

### 6. Track Progress
Keep a log:
```
Problem: Two Sum
Date: 2024-01-01
Pattern: Hash Map
Time: 25 min
Difficulty: Easy
Notes: Forgot to check for duplicates initially
```

---

## Common Pitfalls

### 1. Off-by-One Errors
```kotlin
// ✗ Wrong
for (i in 0..arr.size) { }  // Goes one too far

// ✓ Correct
for (i in 0 until arr.size) { }
```

### 2. Integer Overflow
```kotlin
// ✗ Risk of overflow
val sum = a + b

// ✓ Safe
val sum = a.toLong() + b.toLong()
```

### 3. Null Pointer
```kotlin
// ✗ Might be null
val first = list[0]

// ✓ Safe
val first = list.firstOrNull()
```

### 4. Modifying While Iterating
```kotlin
// ✗ ConcurrentModificationException
for (item in list) {
    list.remove(item)
}

// ✓ Use iterator or create new list
list.removeIf { condition }
```

---

## Problem-Solving Checklist

Before submitting:
- [ ] Handles all edge cases
- [ ] No off-by-one errors
- [ ] No integer overflow
- [ ] No null pointer issues
- [ ] Correct time complexity
- [ ] Correct space complexity
- [ ] Clean, readable code
- [ ] Tested with examples
- [ ] Handles negative numbers (if applicable)
- [ ] Handles duplicates (if applicable)

---

## Key Takeaways

1. ✓ Understand before coding
2. ✓ Start with brute force
3. ✓ Communicate constantly
4. ✓ Test with examples
5. ✓ Handle edge cases
6. ✓ Analyze complexity
7. ✓ Practice regularly
8. ✓ Learn from mistakes
9. ✓ Review solutions
10. ✓ Build pattern recognition

---

## Resources for Practice

**Websites**:
- LeetCode (leetcode.com)
- HackerRank (hackerrank.com)
- CodeSignal (codesignal.com)
- Codeforces (codeforces.com)

**Books**:
- Cracking the Coding Interview
- Elements of Programming Interviews
- Algorithm Design Manual

**Tips**:
- Start with easy problems
- Focus on understanding, not speed
- Quality over quantity
- Review and learn from solutions
