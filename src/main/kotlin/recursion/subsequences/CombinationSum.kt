package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Combination Sum I (Infinite Supply)
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of distinct integers and a target integer, return all unique
 * combinations where the chosen numbers sum to target. The same number may be
 * chosen from the array an unlimited number of times.
 * 
 * Two combinations are unique if the frequency of at least one number is different.
 * 
 * INPUT FORMAT:
 * - An array of distinct positive integers: [2, 3, 6, 7]
 * - A target sum: 7
 * 
 * OUTPUT FORMAT:
 * - List of all unique combinations that sum to target
 * Example: [[2,2,3], [7]]
 * 
 * CONSTRAINTS:
 * - 1 <= candidates.length <= 30
 * - 2 <= candidates[i] <= 40
 * - All elements in candidates are distinct
 * - 1 <= target <= 40
 * 
 * EXAMPLES:
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3], [7]]
 * 
 * Input: candidates = [2,3,5], target = 8
 * Output: [[2,2,2,2], [2,3,3], [3,5]]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Since we can use the same element multiple times, at each step we have:
 * 1. Pick the current element (and stay at same index - can pick again)
 * 2. Skip the current element (move to next index - never pick again)
 * 
 * KEY DIFFERENCE FROM SUBSETS:
 * When we pick an element, we DON'T move to the next index immediately.
 * We stay at the same index to allow picking it again.
 * 
 * ALGORITHM STEPS:
 * 1. Start from index 0 with target sum
 * 2. At each index:
 *    - If current element <= remaining target:
 *      → Pick it and recurse at SAME index (infinite supply)
 *    - Move to next index (skip current element forever)
 * 3. Base case: target == 0 → found valid combination
 * 4. Pruning: if target < 0 or index >= array.length → return
 * 
 * VISUAL EXAMPLE:
 * Array: [2, 3], Target: 5
 * 
 *                    (idx=0, target=5)
 *                    /              \
 *            Pick 2 (same idx)    Skip 2 (next idx)
 *          (idx=0, target=3)      (idx=1, target=5)
 *           /           \            /           \
 *      Pick 2        Skip 2      Pick 3       Skip 3
 *   (idx=0,t=1)   (idx=1,t=3)  (idx=1,t=2)  (idx=2,t=5)
 *       |            /    \         |            X
 *       X      Pick 3  Skip 3    Pick 3
 *          (idx=1,t=0) (idx=2..)  (idx=1,t=-1)
 *              ✓           X           X
 * 
 * Valid: [2, 3]
 * 
 * OPTIMIZATION:
 * Sort the array first. If current element > target, all remaining elements
 * (which are larger) will also exceed target, so we can break early.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Dynamic Programming: Build up combinations for smaller targets
 * 2. BFS: Explore level by level with queue
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^target)
 * - In worst case (with element 1), we can pick it 'target' times
 * - At each position, we have 2 choices (pick or skip)
 * - Height of recursion tree: O(target) when minimum element is 1
 * - More accurately: O(n^(target/min_element))
 * - For practical purposes: O(2^target)
 * 
 * SPACE COMPLEXITY: O(target/min_element)
 * - Recursion depth: O(target/min_element) - worst case picking smallest element
 * - Temporary combination storage: O(target/min_element)
 * - Output storage not counted
 */

class CombinationSum {
    
    /**
     * Find all combinations that sum to target (with repetition allowed)
     * @param candidates Array of distinct positive integers
     * @param target Target sum
     * @return List of all valid combinations
     */
    fun combinationSum(candidates: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        // Sort for optimization (early termination)
        candidates.sort()
        
        backtrack(candidates, 0, target, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper to find combinations
     * @param candidates Sorted array of candidates
     * @param start Current index in candidates
     * @param remaining Remaining sum needed
     * @param current Current combination being built
     * @param result Collection of all valid combinations
     */
    private fun backtrack(
        candidates: IntArray,
        start: Int,
        remaining: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASE 1: Found valid combination
        if (remaining == 0) {
            result.add(ArrayList(current))
            return
        }
        
        // BASE CASE 2: Invalid state
        if (remaining < 0) {
            return
        }
        
        // Try each candidate starting from 'start'
        for (i in start until candidates.size) {
            val num = candidates[i]
            
            // OPTIMIZATION: If current number exceeds remaining, 
            // all subsequent numbers (being larger) will too
            if (num > remaining) {
                break
            }
            
            // Pick current number (can pick again, so recurse at index i)
            current.add(num)
            backtrack(candidates, i, remaining - num, current, result)
            
            // Backtrack
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * Alternative: Without sorting (less optimal)
     */
    fun combinationSumWithoutSort(candidates: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        backtrackSimple(candidates, 0, target, current, result)
        
        return result
    }
    
    private fun backtrackSimple(
        candidates: IntArray,
        start: Int,
        remaining: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASES
        if (remaining == 0) {
            result.add(ArrayList(current))
            return
        }
        
        if (remaining < 0 || start >= candidates.size) {
            return
        }
        
        // CHOICE 1: Pick current element (stay at same index)
        current.add(candidates[start])
        backtrackSimple(candidates, start, remaining - candidates[start], current, result)
        current.removeAt(current.size - 1)
        
        // CHOICE 2: Skip current element (move to next index)
        backtrackSimple(candidates, start + 1, remaining, current, result)
    }
    
    /**
     * With memoization (though less effective for this problem)
     */
    fun combinationSumMemo(candidates: IntArray, target: Int): List<List<Int>> {
        candidates.sort()
        val memo = mutableMapOf<Pair<Int, Int>, List<List<Int>>>()
        return backtrackMemo(candidates, 0, target, memo)
    }
    
    private fun backtrackMemo(
        candidates: IntArray,
        start: Int,
        remaining: Int,
        memo: MutableMap<Pair<Int, Int>, List<List<Int>>>
    ): List<List<Int>> {
        // Check memo
        val key = Pair(start, remaining)
        if (memo.containsKey(key)) {
            return memo[key]!!
        }
        
        val result = mutableListOf<List<Int>>()
        
        if (remaining == 0) {
            result.add(emptyList())
            return result
        }
        
        if (remaining < 0 || start >= candidates.size) {
            return result
        }
        
        // Try each candidate
        for (i in start until candidates.size) {
            if (candidates[i] > remaining) break
            
            val subResults = backtrackMemo(candidates, i, remaining - candidates[i], memo)
            for (subResult in subResults) {
                val newCombination = mutableListOf(candidates[i])
                newCombination.addAll(subResult)
                result.add(newCombination)
            }
        }
        
        memo[key] = result
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: candidates = [2, 3, 6, 7], target = 7
 * After sorting: [2, 3, 6, 7]
 * 
 * backtrack(candidates, 0, 7, [], result)
 * │
 * ├─ i=0: num=2, remaining=7
 * │  │ current = [2]
 * │  │ backtrack(candidates, 0, 5, [2], result)
 * │  │ │
 * │  │ ├─ i=0: num=2, remaining=5
 * │  │ │  │ current = [2, 2]
 * │  │ │  │ backtrack(candidates, 0, 3, [2,2], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ i=0: num=2, remaining=3
 * │  │ │  │ │  │ current = [2, 2, 2]
 * │  │ │  │ │  │ backtrack(candidates, 0, 1, [2,2,2], result)
 * │  │ │  │ │  │ │ No valid combinations (1 < 2)
 * │  │ │  │ │  │ backtrack, remove 2 → current = [2, 2]
 * │  │ │  │ │
 * │  │ │  │ ├─ i=1: num=3, remaining=3
 * │  │ │  │ │  │ current = [2, 2, 3]
 * │  │ │  │ │  │ backtrack(candidates, 1, 0, [2,2,3], result)
 * │  │ │  │ │  │ │ remaining=0 ✓ Add [2,2,3] to result
 * │  │ │  │ │  │ backtrack, remove 3 → current = [2, 2]
 * │  │ │  │ │
 * │  │ │  │ ├─ i=2: num=6, remaining=3
 * │  │ │  │ │  │ 6 > 3, break
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 2 → current = [2]
 * │  │ │  
 * │  │ ├─ i=1: num=3, remaining=5
 * │  │ │  │ current = [2, 3]
 * │  │ │  │ backtrack(candidates, 1, 2, [2,3], result)
 * │  │ │  │ │ No valid combinations (2 < 3)
 * │  │ │  │ backtrack, remove 3 → current = [2]
 * │  │ │
 * │  │ ├─ i=2: num=6, remaining=5
 * │  │ │  │ 6 > 5, break
 * │  │ │
 * │  │ backtrack, remove 2 → current = []
 * │
 * ├─ i=1: num=3, remaining=7
 * │  │ current = [3]
 * │  │ backtrack(candidates, 1, 4, [3], result)
 * │  │ │
 * │  │ ├─ i=1: num=3, remaining=4
 * │  │ │  │ current = [3, 3]
 * │  │ │  │ backtrack(candidates, 1, 1, [3,3], result)
 * │  │ │  │ │ No valid combinations
 * │  │ │  │ backtrack, remove 3 → current = [3]
 * │  │ │
 * │  │ ├─ i=2: num=6, remaining=4
 * │  │ │  │ 6 > 4, break
 * │  │ │
 * │  │ backtrack, remove 3 → current = []
 * │
 * ├─ i=2: num=6, remaining=7
 * │  │ current = [6]
 * │  │ backtrack(candidates, 2, 1, [6], result)
 * │  │ │ No valid combinations
 * │  │ backtrack, remove 6 → current = []
 * │
 * ├─ i=3: num=7, remaining=7
 * │  │ current = [7]
 * │  │ backtrack(candidates, 3, 0, [7], result)
 * │  │ │ remaining=0 ✓ Add [7] to result
 * │  │ backtrack, remove 7 → current = []
 * 
 * Final Result: [[2,2,3], [7]]
 */

fun main() {
    val solver = CombinationSum()
    
    // Test Case 1: Basic case
    println("Test Case 1: candidates = [2,3,6,7], target = 7")
    println("Result: ${solver.combinationSum(intArrayOf(2, 3, 6, 7), 7)}")
    println("Expected: [[2,2,3], [7]]")
    println()
    
    // Test Case 2: Multiple ways
    println("Test Case 2: candidates = [2,3,5], target = 8")
    println("Result: ${solver.combinationSum(intArrayOf(2, 3, 5), 8)}")
    println("Expected: [[2,2,2,2], [2,3,3], [3,5]]")
    println()
    
    // Test Case 3: No solution
    println("Test Case 3: candidates = [2], target = 1")
    println("Result: ${solver.combinationSum(intArrayOf(2), 1)}")
    println("Expected: []")
    println()
    
    // Test Case 4: Single element multiple times
    println("Test Case 4: candidates = [1], target = 5")
    println("Result: ${solver.combinationSum(intArrayOf(1), 5)}")
    println("Expected: [[1,1,1,1,1]]")
    println()
    
    // Test Case 5: Target equals single element
    println("Test Case 5: candidates = [5,10,15], target = 15")
    println("Result: ${solver.combinationSum(intArrayOf(5, 10, 15), 15)}")
    println("Expected: [[5,5,5], [5,10], [15]]")
    println()
    
    // Test Case 6: Large numbers
    println("Test Case 6: candidates = [7,3,2], target = 18")
    println("Result: ${solver.combinationSum(intArrayOf(7, 3, 2), 18)}")
    println("Expected: Multiple combinations")
    println()
    
    // Test Case 7: Comparison test
    println("Test Case 7: Compare different implementations")
    val cand = intArrayOf(2, 3, 5)
    val tgt = 8
    val result1 = solver.combinationSum(cand, tgt)
    val result2 = solver.combinationSumWithoutSort(cand, tgt)
    println("With Sort: $result1")
    println("Without Sort: $result2")
    println()
}
