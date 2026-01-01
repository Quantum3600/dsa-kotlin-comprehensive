/**
 * ============================================================================
 * PROBLEM: Climbing Stairs (Classic DP Problem)
 * DIFFICULTY: Easy
 * CATEGORY: Dynamic Programming - 1D DP
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways
 * can you climb to the top?
 * 
 * INPUT FORMAT:
 * - An integer n (number of steps)
 * - n = 5
 * 
 * OUTPUT FORMAT:
 * - Number of ways to climb n stairs
 * - For n=5: output = 8
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 45
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To reach step n, you could have come from:
 * 1. Step (n-1) by taking a 1-step, OR
 * 2. Step (n-2) by taking a 2-step
 * 
 * So: ways(n) = ways(n-1) + ways(n-2)
 * 
 * Wait... this is the Fibonacci sequence!
 * 
 * EXAMPLE for n=5:
 * Ways to climb 5 stairs:
 * 1. 1+1+1+1+1 (five 1-steps)
 * 2. 1+1+1+2   (three 1-steps, one 2-step)
 * 3. 1+1+2+1   
 * 4. 1+2+1+1
 * 5. 2+1+1+1
 * 6. 1+2+2     (one 1-step, two 2-steps)
 * 7. 2+1+2
 * 8. 2+2+1
 * Total: 8 ways
 * 
 * PATTERN RECOGNITION:
 * n=1: 1 way  → (1)
 * n=2: 2 ways → (1+1), (2)
 * n=3: 3 ways → (1+1+1), (1+2), (2+1)
 * n=4: 5 ways → ways(3) + ways(2) = 3 + 2 = 5
 * n=5: 8 ways → ways(4) + ways(3) = 5 + 3 = 8
 * 
 * RECURRENCE RELATION:
 * dp[n] = dp[n-1] + dp[n-2]
 * Base cases: dp[1] = 1, dp[2] = 2
 * 
 * DYNAMIC PROGRAMMING APPROACH:
 * Instead of solving same subproblems multiple times (recursion),
 * we solve each subproblem once and store the result (memoization).
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Naive Recursion: O(2^n) time, O(n) space - VERY SLOW
 * 2. Recursion + Memoization: O(n) time, O(n) space - Top-down DP
 * 3. Iterative DP: O(n) time, O(n) space - Bottom-up DP
 * 4. Space-Optimized DP: O(n) time, O(1) space - OPTIMAL
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH 1: Naive Recursion
 * TIME: O(2^n) - Exponential! Very slow for large n
 * SPACE: O(n) - Recursion stack depth
 * 
 * APPROACH 2: Memoization (Top-Down DP)
 * TIME: O(n) - Each subproblem solved once
 * SPACE: O(n) - Memo array + recursion stack
 * 
 * APPROACH 3: Tabulation (Bottom-Up DP)
 * TIME: O(n) - Single loop from 1 to n
 * SPACE: O(n) - DP array
 * 
 * APPROACH 4: Space-Optimized
 * TIME: O(n) - Single loop
 * SPACE: O(1) - Only store last 2 values
 * 
 * ============================================================================
 * DYNAMIC PROGRAMMING EXPLAINED
 * ============================================================================
 * 
 * WHAT IS DYNAMIC PROGRAMMING (DP)?
 * A method for solving complex problems by:
 * 1. Breaking it into simpler subproblems
 * 2. Solving each subproblem once
 * 3. Storing solutions to avoid recomputation
 * 
 * WHEN TO USE DP?
 * ✅ Optimal Substructure: Solution can be built from solutions to subproblems
 * ✅ Overlapping Subproblems: Same subproblems are solved multiple times
 * 
 * TWO APPROACHES:
 * 1. **Memoization (Top-Down)**:
 *    - Start with original problem
 *    - Recursively break down
 *    - Store results in memo
 *    - Check memo before computing
 * 
 * 2. **Tabulation (Bottom-Up)**:
 *    - Start with simplest subproblem
 *    - Build up to original problem
 *    - Fill DP table iteratively
 *    - No recursion needed
 * 
 * ============================================================================
 */

package dynamicprogramming.onedim

class ClimbingStairs {
    
    /**
     * Approach 1: Naive Recursion - SLOW
     * TIME: O(2^n), SPACE: O(n)
     */
    fun climbStairsNaive(n: Int): Long {
        // Base cases
        if (n == 1) return 1L  // Only one way: (1)
        if (n == 2) return 2L  // Two ways: (1+1), (2)
        
        // Recursive case: sum of ways from (n-1) and (n-2)
        return climbStairsNaive(n - 1) + climbStairsNaive(n - 2)
    }
    
    /**
     * Approach 2: Memoization (Top-Down DP) - FAST
     * TIME: O(n), SPACE: O(n)
     * 
     * @param n Number of stairs
     * @param memo Cache of previously computed results
     */
    fun climbStairsMemo(n: Int, memo: MutableMap<Int, Long> = mutableMapOf()): Long {
        // Base cases
        if (n == 1) return 1L
        if (n == 2) return 2L
        
        // Check if already computed
        if (memo.containsKey(n)) {
            return memo[n]!!  // Return cached result
        }
        
        // Compute and store
        val result = climbStairsMemo(n - 1, memo) + climbStairsMemo(n - 2, memo)
        memo[n] = result
        
        return result
    }
    
    /**
     * Approach 3: Tabulation (Bottom-Up DP) - STANDARD DP
     * TIME: O(n), SPACE: O(n)
     * 
     * @param n Number of stairs
     * @return Number of ways to climb
     */
    fun climbStairsDP(n: Int): Long {
        // Edge cases
        if (n == 1) return 1L
        if (n == 2) return 2L
        
        // Create DP table
        // dp[i] represents number of ways to climb i stairs
        val dp = LongArray(n + 1)
        
        // Initialize base cases
        dp[1] = 1L  // 1 way to climb 1 stair
        dp[2] = 2L  // 2 ways to climb 2 stairs
        
        // Fill the DP table bottom-up
        // For each step i, we can reach it from (i-1) or (i-2)
        for (i in 3..n) {
            // Ways to reach step i = ways to reach (i-1) + ways to reach (i-2)
            dp[i] = dp[i - 1] + dp[i - 2]
        }
        
        // The answer for n stairs is in dp[n]
        return dp[n]
    }
    
    /**
     * Approach 4: Space-Optimized DP - OPTIMAL
     * TIME: O(n), SPACE: O(1)
     * 
     * Since we only need the last 2 values, we don't need entire DP array
     */
    fun climbStairsOptimized(n: Int): Long {
        // Edge cases
        if (n == 1) return 1L
        if (n == 2) return 2L
        
        // Only keep track of last two values
        // prev2 = dp[i-2], prev1 = dp[i-1]
        var prev2 = 1L  // Ways to climb 1 stair
        var prev1 = 2L  // Ways to climb 2 stairs
        
        // Build up from 3 to n
        for (i in 3..n) {
            // Current ways = sum of previous two
            val current = prev1 + prev2
            
            // Slide the window forward
            prev2 = prev1
            prev1 = current
        }
        
        return prev1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Tabulation Approach
 * ============================================================================
 * 
 * INPUT: n = 5
 * 
 * STEP 1: Initialize
 * dp = [0, 1, 2, 0, 0, 0]
 *       ↑  ↑  ↑
 *      n=0 n=1 n=2 (base cases)
 * 
 * STEP 2: i = 3
 * dp[3] = dp[2] + dp[1] = 2 + 1 = 3
 * dp = [0, 1, 2, 3, 0, 0]
 * 
 * STEP 3: i = 4
 * dp[4] = dp[3] + dp[2] = 3 + 2 = 5
 * dp = [0, 1, 2, 3, 5, 0]
 * 
 * STEP 4: i = 5
 * dp[5] = dp[4] + dp[3] = 5 + 3 = 8
 * dp = [0, 1, 2, 3, 5, 8]
 * 
 * RESULT: dp[5] = 8 ✓
 * 
 * ============================================================================
 * VISUALIZATION OF SOLUTIONS
 * ============================================================================
 * 
 * For n=5, here are all 8 ways:
 * 
 * 1. [1][1][1][1][1]  → 5 steps of 1
 * 2. [1][1][1][2]     → 3 steps of 1, then 2
 * 3. [1][1][2][1]     → 2 steps of 1, then 2, then 1
 * 4. [1][2][1][1]     → 1 step, then 2, then 2 steps of 1
 * 5. [2][1][1][1]     → 2 first, then 3 steps of 1
 * 6. [1][2][2]        → 1, then two 2-steps
 * 7. [2][1][2]        → 2, then 1, then 2
 * 8. [2][2][1]        → two 2-steps, then 1
 * 
 * Total: 8 distinct ways
 * 
 * ============================================================================
 * DP STATES EXPLAINED
 * ============================================================================
 * 
 * STATE: What information do we need to track?
 * - Current step number (i)
 * 
 * TRANSITION: How do we move between states?
 * - From step i, we can go to (i+1) or (i+2)
 * - To reach step i, we came from (i-1) or (i-2)
 * 
 * BASE CASE: What's the simplest answer we know?
 * - 1 stair: 1 way
 * - 2 stairs: 2 ways
 * 
 * ANSWER: Where is our final answer?
 * - dp[n] contains ways to climb n stairs
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = ClimbingStairs()
    
    println("=== Climbing Stairs - Dynamic Programming ===\n")
    
    // Show pattern for small values
    println("Number of ways to climb n stairs:")
    for (n in 1..10) {
        println("n=$n: ${solution.climbStairsOptimized(n)} ways")
    }
    println()
    
    // Test different approaches
    val testN = 10
    println("Computing for n=$testN using different methods:\n")
    
    // Naive (only for small n!)
    val naiveStart = System.nanoTime()
    val naiveResult = solution.climbStairsNaive(testN)
    val naiveTime = (System.nanoTime() - naiveStart) / 1000
    println("Naive Recursion: $naiveResult (took $naiveTime μs)")
    
    // Memoization
    val memoStart = System.nanoTime()
    val memoResult = solution.climbStairsMemo(testN)
    val memoTime = (System.nanoTime() - memoStart) / 1000
    println("Memoization: $memoResult (took $memoTime μs)")
    
    // Tabulation
    val dpStart = System.nanoTime()
    val dpResult = solution.climbStairsDP(testN)
    val dpTime = (System.nanoTime() - dpStart) / 1000
    println("Tabulation: $dpResult (took $dpTime μs)")
    
    // Optimized
    val optStart = System.nanoTime()
    val optResult = solution.climbStairsOptimized(testN)
    val optTime = (System.nanoTime() - optStart) / 1000
    println("Space-Optimized: $optResult (took $optTime μs)")
    
    println("\n=== Performance Test (n=30) ===\n")
    
    val largeN = 30
    
    // Only test efficient versions
    println("Memoization for n=$largeN:")
    val memoLargeStart = System.nanoTime()
    println("Result: ${solution.climbStairsMemo(largeN)}")
    println("Time: ${(System.nanoTime() - memoLargeStart) / 1000} μs\n")
    
    println("Tabulation for n=$largeN:")
    val dpLargeStart = System.nanoTime()
    println("Result: ${solution.climbStairsDP(largeN)}")
    println("Time: ${(System.nanoTime() - dpLargeStart) / 1000} μs\n")
    
    println("Space-Optimized for n=$largeN:")
    val optLargeStart = System.nanoTime()
    println("Result: ${solution.climbStairsOptimized(largeN)}")
    println("Time: ${(System.nanoTime() - optLargeStart) / 1000} μs\n")
    
    println("Note: Naive recursion for n=30 would take very long!")
    println("This is why Dynamic Programming is essential!")
}
