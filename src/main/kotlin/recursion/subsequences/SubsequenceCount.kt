package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Count Subsequences with Sum K
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers and a target sum K, count the number of
 * subsequences that have a sum equal to K.
 * 
 * A subsequence maintains the relative order of elements but doesn't need
 * to be contiguous.
 * 
 * INPUT FORMAT:
 * - An array of integers: [1, 2, 1]
 * - A target sum K: 2
 * 
 * OUTPUT FORMAT:
 * - Count of subsequences with sum K
 * Example: 2
 * 
 * CONSTRAINTS:
 * - 1 <= array.size <= 20
 * - -100 <= array[i] <= 100
 * - -1000 <= K <= 1000
 * 
 * EXAMPLES:
 * Input: arr = [1, 2, 1], K = 2
 * Output: 2
 * Explanation: [1, 1] and [2]
 * 
 * Input: arr = [1, 1, 1], K = 2
 * Output: 3
 * Explanation: [1, 1] (indices 0,1), [1, 1] (indices 0,2), [1, 1] (indices 1,2)
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to printing subsequences, but instead of storing them, we just
 * count them. At each element, we have pick/not-pick choices.
 * 
 * The key insight: Count from leaf nodes bubbles up through recursion tree
 * 
 * ALGORITHM STEPS:
 * 1. At each index, explore both pick and not-pick branches
 * 2. Base case: When index reaches array length
 *    - If targetSum == 0, return 1 (found one valid subsequence)
 *    - Otherwise, return 0
 * 3. Return sum of counts from both branches
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 2, 1], K = 2
 * 
 *                    f(0,2)
 *                   /      \
 *              f(1,1)       f(1,2)
 *              /    \        /    \
 *          f(2,-1) f(2,1)  f(2,0) f(2,2)
 *           /  \    /  \    /  \   /  \
 *         0    0   1   0   1   0  0   0
 * 
 * Count bubbles up:
 * - f(2,1): Pick→0, Skip→0 = 0
 * - f(2,0): Base case returns 1
 * - f(1,1): 0 + 1 = 1
 * - f(1,2): 1 + 0 = 1
 * - f(0,2): 1 + 1 = 2 ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Dynamic Programming: O(n * sum) time, O(n * sum) space
 * 2. Memoization: Cache results of (index, targetSum) pairs
 * 3. Space-optimized DP: Use 1D array
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n)
 * - Each element has 2 choices (pick/not pick)
 * - Creates a binary tree of recursion calls
 * - Total nodes = 2^n (approximate)
 * - Each call does O(1) work
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion stack depth: O(n)
 * - No additional data structures
 * - Only storing count (integer) at each level
 */

class SubsequenceCount {
    
    /**
     * Count all subsequences with sum equal to K
     * @param arr Input array
     * @param k Target sum
     * @return Count of valid subsequences
     */
    fun countSubsequences(arr: IntArray, k: Int): Int {
        return countHelper(arr, 0, k)
    }
    
    /**
     * Recursive helper to count subsequences
     * @param arr Input array
     * @param index Current position in array
     * @param targetSum Remaining sum needed
     * @return Count of valid subsequences from this position
     */
    private fun countHelper(arr: IntArray, index: Int, targetSum: Int): Int {
        // BASE CASE: Reached end of array
        if (index == arr.size) {
            // If sum is 0, we found one valid subsequence
            return if (targetSum == 0) 1 else 0
        }
        
        // RECURSIVE CASE 1: Pick current element
        // Count subsequences that include arr[index]
        val pickCount = countHelper(arr, index + 1, targetSum - arr[index])
        
        // RECURSIVE CASE 2: Don't pick current element
        // Count subsequences that exclude arr[index]
        val notPickCount = countHelper(arr, index + 1, targetSum)
        
        // Total count = sum of both branches
        return pickCount + notPickCount
    }
    
    /**
     * Memoized version for better performance
     * Uses map to cache results of (index, targetSum) pairs
     */
    fun countSubsequencesMemoized(arr: IntArray, k: Int): Int {
        val memo = mutableMapOf<Pair<Int, Int>, Int>()
        return countMemoHelper(arr, 0, k, memo)
    }
    
    /**
     * Helper with memoization
     * @param memo Cache for (index, targetSum) → count mapping
     */
    private fun countMemoHelper(
        arr: IntArray,
        index: Int,
        targetSum: Int,
        memo: MutableMap<Pair<Int, Int>, Int>
    ): Int {
        // BASE CASE
        if (index == arr.size) {
            return if (targetSum == 0) 1 else 0
        }
        
        // Check if result already computed
        val key = Pair(index, targetSum)
        if (memo.containsKey(key)) {
            return memo[key]!!
        }
        
        // Compute result
        val pickCount = countMemoHelper(arr, index + 1, targetSum - arr[index], memo)
        val notPickCount = countMemoHelper(arr, index + 1, targetSum, memo)
        val totalCount = pickCount + notPickCount
        
        // Store in cache
        memo[key] = totalCount
        
        return totalCount
    }
    
    /**
     * Iterative Dynamic Programming approach
     * dp[i][sum] = count of subsequences using first i elements with given sum
     */
    fun countSubsequencesDP(arr: IntArray, k: Int): Int {
        val n = arr.size
        
        // Handle negative sums by shifting
        // If K can be negative, we need offset
        val offset = 1000  // Based on constraint: -1000 <= K <= 1000
        val maxSum = 2001  // Total range: -1000 to 1000
        
        // dp[i][sum+offset] = count of subsequences using first i elements
        val dp = Array(n + 1) { IntArray(maxSum) { 0 } }
        
        // Base case: empty subsequence has sum 0
        dp[0][offset] = 1
        
        // Fill the DP table
        for (i in 1..n) {
            val element = arr[i - 1]
            for (sum in 0 until maxSum) {
                // Don't pick current element
                dp[i][sum] = dp[i - 1][sum]
                
                // Pick current element (if valid)
                val prevSum = sum - element
                if (prevSum >= 0 && prevSum < maxSum) {
                    dp[i][sum] += dp[i - 1][prevSum]
                }
            }
        }
        
        return dp[n][k + offset]
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: arr = [1, 2, 1], K = 2
 * 
 * Recursion Tree:
 * 
 * countHelper(arr, 0, 2)
 * ├─ Pick arr[0]=1 → countHelper(arr, 1, 1)
 * │  ├─ Pick arr[1]=2 → countHelper(arr, 2, -1)
 * │  │  ├─ Pick arr[2]=1 → countHelper(arr, 3, -2) → 0
 * │  │  └─ Skip arr[2] → countHelper(arr, 3, -1) → 0
 * │  │  Result: 0
 * │  └─ Skip arr[1]=2 → countHelper(arr, 2, 1)
 * │     ├─ Pick arr[2]=1 → countHelper(arr, 3, 0) → 1 ✓
 * │     └─ Skip arr[2] → countHelper(arr, 3, 1) → 0
 * │     Result: 1
 * │  Result: 0 + 1 = 1
 * │
 * └─ Skip arr[0]=1 → countHelper(arr, 1, 2)
 *    ├─ Pick arr[1]=2 → countHelper(arr, 2, 0)
 *    │  ├─ Pick arr[2]=1 → countHelper(arr, 3, -1) → 0
 *    │  └─ Skip arr[2] → countHelper(arr, 3, 0) → 1 ✓
 *    │  Result: 1
 *    └─ Skip arr[1]=2 → countHelper(arr, 2, 2)
 *       ├─ Pick arr[2]=1 → countHelper(arr, 3, 1) → 0
 *       └─ Skip arr[2] → countHelper(arr, 3, 2) → 0
 *       Result: 0
 *    Result: 1 + 0 = 1
 * 
 * Final Result: 1 + 1 = 2
 * 
 * The two valid subsequences are: [1,1] and [2]
 */

fun main() {
    val solver = SubsequenceCount()
    
    // Test Case 1: Multiple valid subsequences
    println("Test Case 1: arr = [1, 2, 1], K = 2")
    println("Count (Recursive): ${solver.countSubsequences(intArrayOf(1, 2, 1), 2)}")
    println("Count (Memoized): ${solver.countSubsequencesMemoized(intArrayOf(1, 2, 1), 2)}")
    println("Expected: 2")
    println()
    
    // Test Case 2: All same elements
    println("Test Case 2: arr = [1, 1, 1, 1], K = 2")
    println("Count: ${solver.countSubsequences(intArrayOf(1, 1, 1, 1), 2)}")
    println("Expected: 6 (C(4,2) ways to choose 2 ones)")
    println()
    
    // Test Case 3: No valid subsequence
    println("Test Case 3: arr = [1, 2, 3], K = 10")
    println("Count: ${solver.countSubsequences(intArrayOf(1, 2, 3), 10)}")
    println("Expected: 0")
    println()
    
    // Test Case 4: Target is 0
    println("Test Case 4: arr = [1, -1, 2, -2], K = 0")
    println("Count: ${solver.countSubsequences(intArrayOf(1, -1, 2, -2), 0)}")
    println("Expected: Multiple combinations that sum to 0")
    println()
    
    // Test Case 5: Single element
    println("Test Case 5: arr = [5], K = 5")
    println("Count: ${solver.countSubsequences(intArrayOf(5), 5)}")
    println("Expected: 1")
    println()
    
    // Test Case 6: Empty subsequence case
    println("Test Case 6: arr = [1, 2, 3], K = 0")
    println("Count: ${solver.countSubsequences(intArrayOf(1, 2, 3), 0)}")
    println("Expected: 0 (empty subsequence not counted)")
    println()
    
    // Test Case 7: Performance test with memoization
    println("Test Case 7: Larger array performance comparison")
    val largeArr = IntArray(15) { it + 1 }  // [1, 2, 3, ..., 15]
    val k = 30
    
    val startRecursive = System.currentTimeMillis()
    val countRecursive = solver.countSubsequences(largeArr, k)
    val timeRecursive = System.currentTimeMillis() - startRecursive
    
    val startMemo = System.currentTimeMillis()
    val countMemo = solver.countSubsequencesMemoized(largeArr, k)
    val timeMemo = System.currentTimeMillis() - startMemo
    
    println("Array: [1..15], K = 30")
    println("Recursive: $countRecursive (Time: ${timeRecursive}ms)")
    println("Memoized: $countMemo (Time: ${timeMemo}ms)")
}
