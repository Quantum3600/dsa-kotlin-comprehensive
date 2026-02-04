package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Combination Sum II (Each Element Used Once)
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a collection of candidate numbers (may have duplicates) and a target,
 * find all unique combinations where the candidate numbers sum to target.
 * Each number in candidates may only be used ONCE in the combination.
 * 
 * INPUT FORMAT:
 * - An array of integers (may contain duplicates): [10,1,2,7,6,1,5]
 * - A target sum: 8
 * 
 * OUTPUT FORMAT:
 * - List of all unique combinations that sum to target
 * Example: [[1,1,6], [1,2,5], [1,7], [2,6]]
 * 
 * CONSTRAINTS:
 * - 1 <= candidates.length <= 100
 * - 1 <= candidates[i] <= 50
 * - 1 <= target <= 30
 * 
 * EXAMPLES:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output: [[1,1,6], [1,2,5], [1,7], [2,6]]
 * 
 * Input: candidates = [2,5,2,1,2], target = 5
 * Output: [[1,2,2], [5]]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to Combination Sum I, but with two key differences:
 * 1. Each element can be used only ONCE (move to next index after picking)
 * 2. Array may have duplicates (need to handle like Subset II)
 * 
 * KEY INSIGHT:
 * Sort the array first to group duplicates together.
 * When exploring at the same recursion level, skip duplicate elements
 * to avoid generating duplicate combinations.
 * 
 * ALGORITHM STEPS:
 * 1. Sort the array to group duplicates
 * 2. Use backtracking with index-based exploration
 * 3. At each level, iterate through remaining candidates
 * 4. Skip duplicates at the same recursion level
 * 5. When picking an element, move to NEXT index (not same)
 * 
 * DUPLICATE HANDLING PATTERN:
 * ```
 * for (i in start until candidates.size) {
 *     if (i > start && candidates[i] == candidates[i-1]) continue
 *     // This skips duplicates at the SAME level
 *     // But allows duplicates in different branches
 * }
 * ```
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 1, 2, 5], Target: 6
 * After sorting: [1, 1, 2, 5]
 * 
 *                     []
 *                   /  |  \
 *                [1]  [1]  [2]  [5]
 *                     skip!
 * 
 * We explore [1] at index 0, but skip [1] at index 1 at this level
 * because they're duplicates at the same recursion depth.
 * 
 * But in deeper levels, we can use the second [1]:
 * 
 *                     []
 *                    /
 *                  [1] (idx 0)
 *                  / \
 *              [1,1] [1,2] [1,5]
 *             (idx 1)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Use Set to store unique combinations (less efficient)
 * 2. Generate all combinations, then filter duplicates
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n * n)
 * - Sorting: O(n log n)
 * - In worst case, we explore 2^n combinations
 * - Each combination copy takes O(n)
 * - Overall: O(n log n + 2^n * n) = O(2^n * n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth: O(n) in worst case
 * - Temporary combination storage: O(n)
 * - Output storage not counted in auxiliary space
 */

class CombinationSumII {
    
    /**
     * Find all unique combinations that sum to target (each element used once)
     * @param candidates Array of integers (may have duplicates)
     * @param target Target sum
     * @return List of all unique combinations
     */
    fun combinationSum2(candidates: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        // Sort to handle duplicates
        candidates.sort()
        
        backtrack(candidates, 0, target, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper with duplicate handling
     * @param candidates Sorted array of candidates
     * @param start Current starting index
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
        // BASE CASE: Found valid combination
        if (remaining == 0) {
            result.add(ArrayList(current))
            return
        }
        
        // BASE CASE: Invalid state
        if (remaining < 0) {
            return
        }
        
        // Try each candidate starting from 'start'
        for (i in start until candidates.size) {
            val num = candidates[i]
            
            // OPTIMIZATION: If current number exceeds remaining, stop
            // (all subsequent numbers are >= current due to sorting)
            if (num > remaining) {
                break
            }
            
            // SKIP DUPLICATES at the same recursion level
            // If current element equals previous at same level, skip it
            if (i > start && candidates[i] == candidates[i - 1]) {
                continue
            }
            
            // Pick current element
            current.add(num)
            
            // Recurse with NEXT index (each element used once)
            backtrack(candidates, i + 1, remaining - num, current, result)
            
            // Backtrack
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * Alternative: Pick/Not-Pick approach with duplicate handling
     */
    fun combinationSum2Alternative(candidates: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        candidates.sort()
        
        backtrackPickNotPick(candidates, 0, target, current, result)
        
        return result
    }
    
    private fun backtrackPickNotPick(
        candidates: IntArray,
        index: Int,
        remaining: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASES
        if (remaining == 0) {
            result.add(ArrayList(current))
            return
        }
        
        if (index >= candidates.size || remaining < 0) {
            return
        }
        
        // PICK current element
        current.add(candidates[index])
        backtrackPickNotPick(candidates, index + 1, remaining - candidates[index], current, result)
        current.removeAt(current.size - 1)
        
        // NOT PICK: Skip all duplicates of current element
        var nextIndex = index + 1
        while (nextIndex < candidates.size && candidates[nextIndex] == candidates[index]) {
            nextIndex++
        }
        
        backtrackPickNotPick(candidates, nextIndex, remaining, current, result)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * After sorting: [1, 1, 2, 5, 6, 7, 10]
 * 
 * backtrack(candidates, 0, 8, [], result)
 * │
 * ├─ i=0: num=1, remaining=8
 * │  │ current = [1]
 * │  │ backtrack(candidates, 1, 7, [1], result)
 * │  │ │
 * │  │ ├─ i=1: num=1, remaining=7
 * │  │ │  │ current = [1, 1]
 * │  │ │  │ backtrack(candidates, 2, 6, [1,1], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ i=2: num=2, remaining=6
 * │  │ │  │ │  │ current = [1, 1, 2]
 * │  │ │  │ │  │ backtrack(candidates, 3, 4, [1,1,2], result)
 * │  │ │  │ │  │ │ Continue exploration... no valid combination
 * │  │ │  │ │  
 * │  │ │  │ ├─ i=3: num=5, remaining=6
 * │  │ │  │ │  │ current = [1, 1, 5]
 * │  │ │  │ │  │ backtrack(candidates, 4, 1, [1,1,5], result)
 * │  │ │  │ │  │ │ No valid combination (all remaining > 1)
 * │  │ │  │ │
 * │  │ │  │ ├─ i=4: num=6, remaining=6
 * │  │ │  │ │  │ current = [1, 1, 6]
 * │  │ │  │ │  │ backtrack(candidates, 5, 0, [1,1,6], result)
 * │  │ │  │ │  │ │ remaining=0 ✓ Add [1,1,6] to result
 * │  │ │  │ │  │ backtrack, remove 6 → current = [1, 1]
 * │  │ │  │ │
 * │  │ │  │ ├─ i=5: num=7, remaining=6
 * │  │ │  │ │  │ 7 > 6, break
 * │  │ │  │
 * │  │ │  │ backtrack, remove 1 → current = [1]
 * │  │ │
 * │  │ ├─ i=2: num=2, remaining=7
 * │  │ │  │ current = [1, 2]
 * │  │ │  │ backtrack(candidates, 3, 5, [1,2], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ i=3: num=5, remaining=5
 * │  │ │  │ │  │ current = [1, 2, 5]
 * │  │ │  │ │  │ backtrack(candidates, 4, 0, [1,2,5], result)
 * │  │ │  │ │  │ │ remaining=0 ✓ Add [1,2,5] to result
 * │  │ │  │ │  │ backtrack, remove 5 → current = [1, 2]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 2 → current = [1]
 * │  │ │
 * │  │ ├─ i=5: num=7, remaining=7
 * │  │ │  │ current = [1, 7]
 * │  │ │  │ backtrack(candidates, 6, 0, [1,7], result)
 * │  │ │  │ │ remaining=0 ✓ Add [1,7] to result
 * │  │ │  │ backtrack, remove 7 → current = [1]
 * │  │ │
 * │  │ backtrack, remove 1 → current = []
 * │
 * ├─ i=1: num=1, remaining=8
 * │  │ SKIP! (i > start && candidates[1] == candidates[0])
 * │
 * ├─ i=2: num=2, remaining=8
 * │  │ current = [2]
 * │  │ backtrack(candidates, 3, 6, [2], result)
 * │  │ │
 * │  │ ├─ i=4: num=6, remaining=6
 * │  │ │  │ current = [2, 6]
 * │  │ │  │ backtrack(candidates, 5, 0, [2,6], result)
 * │  │ │  │ │ remaining=0 ✓ Add [2,6] to result
 * │  │ │  │ backtrack, remove 6 → current = [2]
 * │  │ │
 * │  │ backtrack, remove 2 → current = []
 * │
 * ... continue for remaining elements ...
 * 
 * Final Result: [[1,1,6], [1,2,5], [1,7], [2,6]]
 */

fun main() {
    val solver = CombinationSumII()
    
    // Test Case 1: With duplicates
    println("Test Case 1: candidates = [10,1,2,7,6,1,5], target = 8")
    println("Result: ${solver.combinationSum2(intArrayOf(10, 1, 2, 7, 6, 1, 5), 8)}")
    println("Expected: [[1,1,6], [1,2,5], [1,7], [2,6]]")
    println()
    
    // Test Case 2: Multiple duplicates
    println("Test Case 2: candidates = [2,5,2,1,2], target = 5")
    println("Result: ${solver.combinationSum2(intArrayOf(2, 5, 2, 1, 2), 5)}")
    println("Expected: [[1,2,2], [5]]")
    println()
    
    // Test Case 3: No solution
    println("Test Case 3: candidates = [1], target = 2")
    println("Result: ${solver.combinationSum2(intArrayOf(1), 2)}")
    println("Expected: []")
    println()
    
    // Test Case 4: Exact match
    println("Test Case 4: candidates = [1,2,3], target = 3")
    println("Result: ${solver.combinationSum2(intArrayOf(1, 2, 3), 3)}")
    println("Expected: [[1,2], [3]]")
    println()
    
    // Test Case 5: All duplicates
    println("Test Case 5: candidates = [1,1,1,1,1], target = 3")
    println("Result: ${solver.combinationSum2(intArrayOf(1, 1, 1, 1, 1), 3)}")
    println("Expected: [[1,1,1]]")
    println()
    
    // Test Case 6: Large numbers
    println("Test Case 6: candidates = [14,6,25,9,30,20,33,34,28,30,16,12,31,9,9,12,34,16,25,32,8,7,30,12,33,20,21,29,24,17,27,34,11,17,30,6,32,21,27,17,16,8,24,12,12,28,11,33,10,32,22,13,34,18,12], target = 27")
    println("Result size: ${solver.combinationSum2(intArrayOf(14,6,25,9,30,20,33,34,28,30,16,12,31,9,9,12,34,16,25,32,8,7,30,12,33,20,21,29,24,17,27,34,11,17,30,6,32,21,27,17,16,8,24,12,12,28,11,33,10,32,22,13,34,18,12), 27).size}")
    println("Expected: Multiple combinations")
    println()
    
    // Test Case 7: Compare both approaches
    println("Test Case 7: Compare implementations")
    val cand = intArrayOf(10, 1, 2, 7, 6, 1, 5)
    val tgt = 8
    val result1 = solver.combinationSum2(cand, tgt)
    val result2 = solver.combinationSum2Alternative(cand, tgt)
    println("Approach 1: $result1")
    println("Approach 2: $result2")
    println("Same results: ${result1.map { it.sorted() }.toSet() == result2.map { it.sorted() }.toSet()}")
}
