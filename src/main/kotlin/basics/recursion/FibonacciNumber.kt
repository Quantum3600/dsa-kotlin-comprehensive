/**
 * ============================================================================
 * PROBLEM: Fibonacci Number using Recursion
 * DIFFICULTY: Easy
 * CATEGORY: Basics - Recursion
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * The Fibonacci sequence is a series where each number is the sum of the two
 * preceding numbers. Starting with 0 and 1, the sequence looks like:
 * 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, ...
 * 
 * Given n, find the nth Fibonacci number using recursion.
 * 
 * INPUT FORMAT:
 * - A non-negative integer n (0-indexed)
 * - n = 6
 * 
 * OUTPUT FORMAT:
 * - The nth Fibonacci number
 * - For n=6: output = 8
 * 
 * CONSTRAINTS:
 * - 0 <= n <= 30 (for basic recursion)
 * - 0 <= n <= 50 (for optimized version)
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The Fibonacci sequence has a natural recursive definition:
 * - F(0) = 0 (base case)
 * - F(1) = 1 (base case)
 * - F(n) = F(n-1) + F(n-2) for n > 1
 * 
 * This translates directly into a recursive function!
 * 
 * RECURSION TREE EXAMPLE for F(5):
 *                         F(5)
 *                      /        \
 *                 F(4)            F(3)
 *               /      \        /      \
 *            F(3)      F(2)   F(2)    F(1)
 *           /   \      /  \    /  \      |
 *        F(2) F(1)  F(1) F(0) F(1) F(0)  1
 *        / \    |     |    |    |    |
 *     F(1) F(0) 1     1    0    1    0
 *       |   |
 *       1   0
 * 
 * Notice: F(3), F(2), F(1), F(0) are calculated multiple times!
 * This is the problem with naive recursion - overlapping subproblems.
 * 
 * ALGORITHM STEPS (Basic Recursion):
 * 1. Base case: If n is 0, return 0
 * 2. Base case: If n is 1, return 1
 * 3. Recursive case: Return fib(n-1) + fib(n-2)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Naive Recursion: O(2^n) time, O(n) space - VERY SLOW
 * 2. Recursion with Memoization: O(n) time, O(n) space - FAST
 * 3. Iterative (Loop): O(n) time, O(1) space - OPTIMAL
 * 4. Matrix Exponentiation: O(log n) time - ADVANCED
 * 5. Golden Ratio Formula: O(1) time - MATHEMATICAL
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * NAIVE RECURSION:
 * TIME COMPLEXITY: O(2^n)
 * - Each call branches into 2 recursive calls
 * - Creates a binary tree of calls
 * - For n=10: ~1024 calls
 * - For n=20: ~1,048,576 calls  
 * - For n=30: ~1,073,741,824 calls (over 1 billion!)
 * - Grows EXPONENTIALLY - very slow!
 * 
 * WHY O(2^n):
 * - Each level of recursion approximately doubles the work
 * - Height of recursion tree is n
 * - Total nodes ≈ 2^n
 * 
 * SPACE COMPLEXITY: O(n)
 * - Maximum depth of recursion tree is n
 * - Call stack can hold up to n function calls
 * - NOT O(2^n)! Only one path is active at a time in the stack
 * 
 * MEMOIZED VERSION:
 * TIME COMPLEXITY: O(n)
 * - Each number from 0 to n is calculated exactly once
 * - Subsequent calls use cached result: O(1)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Memoization array stores n values: O(n)
 * - Call stack depth: O(n)
 * - Total: O(n)
 * 
 * ITERATIVE VERSION:
 * TIME COMPLEXITY: O(n)
 * - Single loop from 2 to n
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only stores last two values
 * - No recursion stack
 * - OPTIMAL for space!
 * 
 * ============================================================================
 */

package basics.recursion

class FibonacciNumber {
    
    /**
     * Naive recursive solution - Educational but SLOW
     * TIME: O(2^n), SPACE: O(n)
     * 
     * @param n The position in Fibonacci sequence (0-indexed)
     * @return nth Fibonacci number
     */
    fun fibonacciNaive(n: Int): Long {
        // Base case 1: F(0) = 0
        // If n is 0, by definition the 0th Fibonacci number is 0
        if (n == 0) return 0L
        
        // Base case 2: F(1) = 1
        // If n is 1, by definition the 1st Fibonacci number is 1
        if (n == 1) return 1L
        
        // Recursive case: F(n) = F(n-1) + F(n-2)
        // To find F(n), we need:
        // 1. F(n-1): The previous Fibonacci number
        // 2. F(n-2): The one before that
        // Add them together to get F(n)
        return fibonacciNaive(n - 1) + fibonacciNaive(n - 2)
    }
    
    /**
     * Memoized recursive solution - FAST
     * Uses a cache (memo) to store already computed values
     * TIME: O(n), SPACE: O(n)
     * 
     * @param n Position in sequence
     * @param memo Cache of previously computed values
     * @return nth Fibonacci number
     */
    fun fibonacciMemo(n: Int, memo: MutableMap<Int, Long> = mutableMapOf()): Long {
        // Base cases
        if (n == 0) return 0L
        if (n == 1) return 1L
        
        // Check if already computed
        // If this value exists in memo, return it immediately
        // This avoids recalculating the same value multiple times
        if (memo.containsKey(n)) {
            return memo[n]!!
        }
        
        // Compute and store in memo
        // Calculate F(n) recursively
        val result = fibonacciMemo(n - 1, memo) + fibonacciMemo(n - 2, memo)
        
        // Store the result in memo for future use
        // Next time we need F(n), we can return it immediately
        memo[n] = result
        
        return result
    }
    
    /**
     * Iterative solution - OPTIMAL for large n
     * TIME: O(n), SPACE: O(1)
     * 
     * @param n Position in sequence
     * @return nth Fibonacci number
     */
    fun fibonacciIterative(n: Int): Long {
        // Handle base cases
        if (n == 0) return 0L
        if (n == 1) return 1L
        
        // Initialize first two Fibonacci numbers
        // prev2 represents F(i-2), prev1 represents F(i-1)
        var prev2 = 0L  // F(0)
        var prev1 = 1L  // F(1)
        
        // Calculate from F(2) to F(n)
        // In each iteration, we compute the next Fibonacci number
        for (i in 2..n) {
            // Current Fibonacci number is sum of previous two
            val current = prev1 + prev2
            
            // Slide the window forward
            // What was prev1 becomes prev2
            // What is current becomes prev1
            prev2 = prev1
            prev1 = current
        }
        
        // prev1 now holds F(n)
        return prev1
    }
    
    /**
     * Tail-recursive version - Optimized by Kotlin compiler
     * TIME: O(n), SPACE: O(1) with tail call optimization
     */
    tailrec fun fibonacciTailRec(n: Int, a: Long = 0L, b: Long = 1L): Long {
        return when (n) {
            0 -> a
            1 -> b
            else -> fibonacciTailRec(n - 1, b, a + b)
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Naive Recursion
 * ============================================================================
 * 
 * INPUT: n = 5
 * 
 * CALL TREE:
 * 
 * fib(5)
 *   ↓ calls fib(4) and fib(3)
 * 
 * fib(4)
 *   ↓ calls fib(3) and fib(2)
 * 
 * fib(3)  [computed twice!]
 *   ↓ calls fib(2) and fib(1)
 * 
 * fib(2)  [computed three times!]
 *   ↓ calls fib(1) and fib(0)
 * 
 * fib(1)  [computed five times!]
 *   ↓ returns 1
 * 
 * fib(0)  [computed three times!]
 *   ↓ returns 0
 * 
 * EXECUTION:
 * fib(5) = fib(4) + fib(3)
 *        = (fib(3) + fib(2)) + (fib(2) + fib(1))
 *        = ((fib(2) + fib(1)) + (fib(1) + fib(0))) + ((fib(1) + fib(0)) + 1)
 *        = (((fib(1) + fib(0)) + 1) + (1 + 0)) + ((1 + 0) + 1)
 *        = (((1 + 0) + 1) + 1) + (1 + 1)
 *        = ((1 + 1) + 1) + 2
 *        = (2 + 1) + 2
 *        = 3 + 2
 *        = 5 ✓
 * 
 * Total function calls: 15 for just n=5!
 * 
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Iterative
 * ============================================================================
 * 
 * INPUT: n = 5
 * 
 * Initial: prev2 = 0 (F(0)), prev1 = 1 (F(1))
 * 
 * i = 2: current = 0 + 1 = 1
 *        prev2 = 1, prev1 = 1  (F(2) = 1)
 * 
 * i = 3: current = 1 + 1 = 2
 *        prev2 = 1, prev1 = 2  (F(3) = 2)
 * 
 * i = 4: current = 1 + 2 = 3
 *        prev2 = 2, prev1 = 3  (F(4) = 3)
 * 
 * i = 5: current = 2 + 3 = 5
 *        prev2 = 3, prev1 = 5  (F(5) = 5)
 * 
 * Return prev1 = 5 ✓
 * 
 * Total iterations: 4 (much better than 15 calls!)
 * 
 * ============================================================================
 * PERFORMANCE COMPARISON
 * ============================================================================
 */

fun main() {
    val fib = FibonacciNumber()
    
    println("=== Fibonacci Sequence ===\n")
    
    // Display first 10 Fibonacci numbers
    println("First 10 Fibonacci numbers:")
    for (i in 0..10) {
        print("${fib.fibonacciIterative(i)} ")
    }
    println("\n")
    
    // Test different approaches
    val n = 10
    println("Computing F($n) using different methods:\n")
    
    // Naive recursion
    val naiveStart = System.nanoTime()
    val naiveResult = fib.fibonacciNaive(n)
    val naiveTime = (System.nanoTime() - naiveStart) / 1000
    println("Naive Recursion: $naiveResult (took $naiveTime μs)")
    
    // Memoized recursion
    val memoStart = System.nanoTime()
    val memoResult = fib.fibonacciMemo(n)
    val memoTime = (System.nanoTime() - memoStart) / 1000
    println("Memoized: $memoResult (took $memoTime μs)")
    
    // Iterative
    val iterStart = System.nanoTime()
    val iterResult = fib.fibonacciIterative(n)
    val iterTime = (System.nanoTime() - iterStart) / 1000
    println("Iterative: $iterResult (took $iterTime μs)")
    
    // Tail recursive
    val tailStart = System.nanoTime()
    val tailResult = fib.fibonacciTailRec(n)
    val tailTime = (System.nanoTime() - tailStart) / 1000
    println("Tail Recursive: $tailResult (took $tailTime μs)")
    
    println("\n=== Performance Test (n=30) ===\n")
    
    // Only test fast versions for n=30 (naive would take too long!)
    val largeN = 30
    
    println("Memoized for n=$largeN:")
    val memoLargeStart = System.nanoTime()
    println("Result: ${fib.fibonacciMemo(largeN)}")
    println("Time: ${(System.nanoTime() - memoLargeStart) / 1000} μs\n")
    
    println("Iterative for n=$largeN:")
    val iterLargeStart = System.nanoTime()
    println("Result: ${fib.fibonacciIterative(largeN)}")
    println("Time: ${(System.nanoTime() - iterLargeStart) / 1000} μs\n")
    
    println("Note: Naive recursion for n=30 would take several seconds!")
    println("This demonstrates why algorithm choice matters!")
}
