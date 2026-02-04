package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Combination Sum III
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Find all valid combinations of k numbers that sum up to n such that:
 * - Only numbers 1 through 9 are used
 * - Each number is used at most once
 * 
 * Return a list of all possible valid combinations.
 * 
 * INPUT FORMAT:
 * - k: Number of numbers in the combination
 * - n: Target sum
 * 
 * OUTPUT FORMAT:
 * - List of all valid combinations
 * Example: [[1,2,4]]
 * 
 * CONSTRAINTS:
 * - 2 <= k <= 9
 * - 1 <= n <= 60
 * 
 * EXAMPLES:
 * Input: k = 3, n = 7
 * Output: [[1,2,4]]
 * 
 * Input: k = 3, n = 9
 * Output: [[1,2,6], [1,3,5], [2,3,4]]
 * 
 * Input: k = 4, n = 1
 * Output: []
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is a constrained combination problem where:
 * - We can only use digits 1-9
 * - Each digit used at most once
 * - Must use exactly k numbers
 * - Sum must equal n
 * 
 * We need to track THREE conditions:
 * 1. Current sum
 * 2. Numbers remaining to pick (k)
 * 3. Next number to consider (1-9)
 * 
 * ALGORITHM STEPS:
 * 1. Start from number 1, need k numbers, target sum n
 * 2. Try each number from current to 9
 * 3. Pick a number if:
 *    - It doesn't exceed remaining sum
 *    - We still need more numbers
 * 4. Recurse with: next number, k-1, reduced sum
 * 5. Base case: k == 0
 *    - If sum == 0: valid combination
 *    - Otherwise: invalid
 * 
 * PRUNING OPTIMIZATIONS:
 * 1. If current number > remaining sum: break (all subsequent larger)
 * 2. If remaining numbers can't be formed from available digits: return
 * 3. Minimum possible sum with k numbers starting from num:
 *    num + (num+1) + ... + (num+k-1)
 * 4. Maximum possible sum with k numbers ending at 9:
 *    9 + 8 + 7 + ... + (9-k+1)
 * 
 * VISUAL EXAMPLE:
 * k = 3, n = 9
 * 
 *                      (start=1, k=3, sum=9)
 *                    /     |      |      \
 *                 [1]     [2]    [3]    [4]...
 *              (2,2,8)  (3,2,7) (4,2,6)
 *               /  \       |
 *            [1,2][1,3]  [2,3]
 *            (3,1,6)(4,1,5)(4,1,4)
 *             /       |       |
 *          [1,2,6] [1,3,5] [2,3,4]
 *          (7,0,0) (6,0,0) (5,0,0)
 *             ✓       ✓       ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative with stack/queue
 * 2. Dynamic Programming (build up combinations)
 * 3. Bit manipulation (generate all 2^9 subsets, filter valid ones)
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(C(9, k) * k)
 * - We choose k numbers from 9 available: C(9, k) combinations
 * - For each combination, copying takes O(k)
 * - C(9, k) is maximized when k = 4 or 5: C(9, 4) = 126
 * - Overall: O(126 * k) ≈ O(1) since k <= 9
 * 
 * SPACE COMPLEXITY: O(k)
 * - Recursion depth: O(k)
 * - Temporary combination storage: O(k)
 * - Output storage not counted
 */

class CombinationSumIII {
    
    /**
     * Find all combinations of k numbers that sum to n
     * @param k Number of numbers in combination
     * @param n Target sum
     * @return List of all valid combinations
     */
    fun combinationSum3(k: Int, n: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        // Early termination: Check if n is achievable
        // Minimum sum with k numbers: 1+2+...+k = k*(k+1)/2
        // Maximum sum with k numbers: 9+8+...+(9-k+1) = k*(9+10-k)/2
        val minSum = k * (k + 1) / 2
        val maxSum = k * (9 + 10 - k) / 2
        
        if (n < minSum || n > maxSum) {
            return result  // Impossible to achieve
        }
        
        backtrack(1, k, n, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper
     * @param start Starting number to consider (1-9)
     * @param k Numbers remaining to pick
     * @param remaining Remaining sum needed
     * @param current Current combination being built
     * @param result Collection of all valid combinations
     */
    private fun backtrack(
        start: Int,
        k: Int,
        remaining: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASE 1: All k numbers picked
        if (k == 0) {
            // Check if sum equals target
            if (remaining == 0) {
                result.add(ArrayList(current))
            }
            return
        }
        
        // BASE CASE 2: Invalid state
        if (remaining < 0) {
            return
        }
        
        // PRUNING: If we need k more numbers but don't have enough digits left
        val digitsLeft = 10 - start  // Numbers from start to 9
        if (digitsLeft < k) {
            return
        }
        
        // Try each number from start to 9
        for (num in start..9) {
            // PRUNING: If current number exceeds remaining, stop
            if (num > remaining) {
                break
            }
            
            // PRUNING: Check if remaining k numbers can sum to (remaining - num)
            // Minimum possible sum with (k-1) numbers starting from (num+1)
            val minPossibleSum = (k - 1) * (num + 1 + num + k - 1) / 2
            if (remaining - num < minPossibleSum) {
                break
            }
            
            // PRUNING: Maximum possible sum with (k-1) numbers ending at 9
            val maxPossibleSum = (k - 1) * (9 + 9 - k + 2) / 2
            if (remaining - num > maxPossibleSum) {
                continue
            }
            
            // Pick current number
            current.add(num)
            
            // Recurse: next number, one less to pick, reduced sum
            backtrack(num + 1, k - 1, remaining - num, current, result)
            
            // Backtrack
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * Simpler version without advanced pruning
     */
    fun combinationSum3Simple(k: Int, n: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        backtrackSimple(1, k, n, current, result)
        
        return result
    }
    
    private fun backtrackSimple(
        start: Int,
        k: Int,
        remaining: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASE
        if (k == 0 && remaining == 0) {
            result.add(ArrayList(current))
            return
        }
        
        // PRUNING
        if (k == 0 || remaining <= 0 || start > 9) {
            return
        }
        
        // Try each number
        for (num in start..9) {
            current.add(num)
            backtrackSimple(num + 1, k - 1, remaining - num, current, result)
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * Bit manipulation approach
     * Generate all subsets of {1,2,...,9}, filter valid ones
     */
    fun combinationSum3BitMask(k: Int, n: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        
        // Iterate through all possible subsets of {1,2,...,9}
        // Total subsets: 2^9 = 512
        for (mask in 0 until (1 shl 9)) {
            val combination = mutableListOf<Int>()
            var sum = 0
            
            // Check each bit
            for (i in 0 until 9) {
                if ((mask and (1 shl i)) != 0) {
                    combination.add(i + 1)
                    sum += i + 1
                }
            }
            
            // Check if valid
            if (combination.size == k && sum == n) {
                result.add(combination)
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: k = 3, n = 9
 * 
 * backtrack(1, 3, 9, [], result)
 * │
 * ├─ num=1: remaining=9, k=3
 * │  │ current = [1]
 * │  │ backtrack(2, 2, 8, [1], result)
 * │  │ │
 * │  │ ├─ num=2: remaining=8, k=2
 * │  │ │  │ current = [1, 2]
 * │  │ │  │ backtrack(3, 1, 6, [1,2], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ num=3: remaining=6, k=1
 * │  │ │  │ │  │ 3 <= 6, continue
 * │  │ │  │ │  │ current = [1, 2, 3]
 * │  │ │  │ │  │ backtrack(4, 0, 3, [1,2,3], result)
 * │  │ │  │ │  │ │ k=0 but remaining=3≠0, invalid
 * │  │ │  │ │  │ backtrack, remove 3 → current = [1, 2]
 * │  │ │  │ │
 * │  │ │  │ ├─ num=4: remaining=6, k=1
 * │  │ │  │ │  │ current = [1, 2, 4]
 * │  │ │  │ │  │ backtrack(5, 0, 2, [1,2,4], result)
 * │  │ │  │ │  │ │ k=0 but remaining=2≠0, invalid
 * │  │ │  │ │  │ backtrack, remove 4 → current = [1, 2]
 * │  │ │  │ │
 * │  │ │  │ ├─ num=6: remaining=6, k=1
 * │  │ │  │ │  │ current = [1, 2, 6]
 * │  │ │  │ │  │ backtrack(7, 0, 0, [1,2,6], result)
 * │  │ │  │ │  │ │ k=0 AND remaining=0 ✓ Add [1,2,6] to result
 * │  │ │  │ │  │ backtrack, remove 6 → current = [1, 2]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 2 → current = [1]
 * │  │ │
 * │  │ ├─ num=3: remaining=8, k=2
 * │  │ │  │ current = [1, 3]
 * │  │ │  │ backtrack(4, 1, 5, [1,3], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ num=5: remaining=5, k=1
 * │  │ │  │ │  │ current = [1, 3, 5]
 * │  │ │  │ │  │ backtrack(6, 0, 0, [1,3,5], result)
 * │  │ │  │ │  │ │ k=0 AND remaining=0 ✓ Add [1,3,5] to result
 * │  │ │  │ │  │ backtrack, remove 5 → current = [1, 3]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 3 → current = [1]
 * │  │ │
 * │  │ backtrack, remove 1 → current = []
 * │
 * ├─ num=2: remaining=9, k=3
 * │  │ current = [2]
 * │  │ backtrack(3, 2, 7, [2], result)
 * │  │ │
 * │  │ ├─ num=3: remaining=7, k=2
 * │  │ │  │ current = [2, 3]
 * │  │ │  │ backtrack(4, 1, 4, [2,3], result)
 * │  │ │  │ │
 * │  │ │  │ ├─ num=4: remaining=4, k=1
 * │  │ │  │ │  │ current = [2, 3, 4]
 * │  │ │  │ │  │ backtrack(5, 0, 0, [2,3,4], result)
 * │  │ │  │ │  │ │ k=0 AND remaining=0 ✓ Add [2,3,4] to result
 * │  │ │  │ │  │ backtrack, remove 4 → current = [2, 3]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 3 → current = [2]
 * │  │ │
 * │  │ backtrack, remove 2 → current = []
 * │
 * ... continue for remaining numbers ...
 * 
 * Final Result: [[1,2,6], [1,3,5], [2,3,4]]
 */

fun main() {
    val solver = CombinationSumIII()
    
    // Test Case 1: Single combination
    println("Test Case 1: k = 3, n = 7")
    println("Result: ${solver.combinationSum3(3, 7)}")
    println("Expected: [[1,2,4]]")
    println()
    
    // Test Case 2: Multiple combinations
    println("Test Case 2: k = 3, n = 9")
    println("Result: ${solver.combinationSum3(3, 9)}")
    println("Expected: [[1,2,6], [1,3,5], [2,3,4]]")
    println()
    
    // Test Case 3: Impossible case
    println("Test Case 3: k = 4, n = 1")
    println("Result: ${solver.combinationSum3(4, 1)}")
    println("Expected: []")
    println()
    
    // Test Case 4: Maximum k
    println("Test Case 4: k = 9, n = 45")
    println("Result: ${solver.combinationSum3(9, 45)}")
    println("Expected: [[1,2,3,4,5,6,7,8,9]] (sum of all digits)")
    println()
    
    // Test Case 5: Minimum k
    println("Test Case 5: k = 2, n = 17")
    println("Result: ${solver.combinationSum3(2, 17)}")
    println("Expected: [[8,9]]")
    println()
    
    // Test Case 6: Sum too large
    println("Test Case 6: k = 2, n = 18")
    println("Result: ${solver.combinationSum3(2, 18)}")
    println("Expected: [[9,9]] but each digit once, so []")
    println()
    
    // Test Case 7: Edge case
    println("Test Case 7: k = 3, n = 6")
    println("Result: ${solver.combinationSum3(3, 6)}")
    println("Expected: [[1,2,3]]")
    println()
    
    // Test Case 8: Compare implementations
    println("Test Case 8: Compare all three implementations")
    val k = 3
    val n = 9
    val result1 = solver.combinationSum3(k, n)
    val result2 = solver.combinationSum3Simple(k, n)
    val result3 = solver.combinationSum3BitMask(k, n)
    println("Optimized: $result1")
    println("Simple: $result2")
    println("Bit Mask: $result3")
    println("All equal: ${result1.toSet() == result2.toSet() && result2.toSet() == result3.toSet()}")
}
