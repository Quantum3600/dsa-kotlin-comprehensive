/**
 * ============================================================================
 * PROBLEM: Find Maximum Number in K Swaps
 * DIFFICULTY: Hard
 * CATEGORY: Recursion/Backtracking/Optimization
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a positive integer as a string and an integer K, find the maximum
 * number that can be formed by performing at most K swaps of digits.
 * 
 * INPUT FORMAT:
 * - num: String representation of a positive integer
 * - k: Maximum number of swaps allowed
 * 
 * OUTPUT FORMAT:
 * - String representation of the maximum number achievable
 * 
 * CONSTRAINTS:
 * - 1 <= num.length <= 10
 * - 0 <= K <= 10
 * - num consists of digits only
 * - num does not contain leading zeros
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use backtracking to explore all possible swap combinations:
 * 1. For each position, try swapping with all positions to its right
 * 2. Choose swaps that place larger digits earlier
 * 3. Keep track of maximum number found so far
 * 4. Prune branches that won't lead to better solutions
 * 
 * VISUAL EXAMPLE:
 * 
 * Input: num = "1234", K = 2
 * 
 * Start: 1234
 * Swap 1↔4: 4231
 *   Swap 2↔3: 4321 ✓ (Maximum!)
 * 
 * Output: "4321"
 * 
 * Another example: num = "129814", K = 2
 * 
 * Start: 129814
 * Swap 1↔9: 921814
 *   Swap 2↔8: 981214 ✓ (Maximum with 2 swaps)
 * 
 * ALGORITHM STEPS:
 * 1. Convert string to character array for easy swapping
 * 2. Use a variable to track maximum number found
 * 3. For each position i from left to right:
 *    a. Find the maximum digit in remaining positions
 *    b. If a larger digit exists, swap and recurse with K-1
 *    c. Try all swaps that give maximum digit at position i
 * 4. Update global maximum after each complete exploration
 * 5. Backtrack and try other swap combinations
 * 
 * OPTIMIZATION:
 * - Only swap with digits greater than current
 * - Track already explored states to avoid duplicates
 * - If K=0 or position = length, stop
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(N! / (N-K)!)
 * - For N digits and K swaps
 * - Worst case: try all permutations
 * - With pruning: significantly better in practice
 * - Approximately O(N^K) with optimizations
 * 
 * SPACE COMPLEXITY: O(N)
 * - Character array: O(N)
 * - Recursion stack: O(K)
 * - Maximum number storage: O(N)
 * 
 * ============================================================================
 */

package recursion.hard

class LargestNumberInKSwaps {
    
    private var maxNumber = ""
    
    /**
     * Find maximum number possible with K swaps
     * TIME: O(N^K), SPACE: O(N)
     * 
     * @param num String representation of number
     * @param k Maximum swaps allowed
     * @return Maximum number as string
     */
    fun findMaximum(num: String, k: Int): String {
        if (k == 0 || num.isEmpty()) return num
        
        maxNumber = num
        val digits = num.toCharArray()
        findMaximumHelper(digits, k, 0)
        return maxNumber
    }
    
    /**
     * Recursive backtracking helper
     */
    private fun findMaximumHelper(digits: CharArray, k: Int, position: Int) {
        // Update maximum if current number is larger
        val currentNum = String(digits)
        if (currentNum > maxNumber) {
            maxNumber = currentNum
        }
        
        // Base cases
        if (k == 0 || position == digits.size - 1) {
            return
        }
        
        // Find maximum digit in remaining positions
        var maxDigit = digits[position]
        for (i in position + 1 until digits.size) {
            if (digits[i] > maxDigit) {
                maxDigit = digits[i]
            }
        }
        
        // If current position already has max digit, move to next
        if (digits[position] == maxDigit) {
            findMaximumHelper(digits, k, position + 1)
            return
        }
        
        // Try swapping with all occurrences of max digit
        for (i in position + 1 until digits.size) {
            if (digits[i] == maxDigit) {
                // Swap
                swap(digits, position, i)
                
                // Recurse with one less swap
                findMaximumHelper(digits, k - 1, position + 1)
                
                // Backtrack
                swap(digits, position, i)
            }
        }
    }
    
    /**
     * Swap two characters in array
     */
    private fun swap(arr: CharArray, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }
    
    /**
     * Alternative approach: try all possible swaps at each level
     */
    fun findMaximumAllSwaps(num: String, k: Int): String {
        if (k == 0 || num.isEmpty()) return num
        
        maxNumber = num
        val digits = num.toCharArray()
        findMaxAllSwapsHelper(digits, k)
        return maxNumber
    }
    
    /**
     * Try all possible swaps (slower but thorough)
     */
    private fun findMaxAllSwapsHelper(digits: CharArray, k: Int) {
        val currentNum = String(digits)
        if (currentNum > maxNumber) {
            maxNumber = currentNum
        }
        
        if (k == 0) return
        
        // Try all possible swaps
        for (i in 0 until digits.size - 1) {
            for (j in i + 1 until digits.size) {
                // Only swap if it makes sense
                if (digits[j] > digits[i]) {
                    swap(digits, i, j)
                    findMaxAllSwapsHelper(digits, k - 1)
                    swap(digits, i, j)  // Backtrack
                }
            }
        }
    }
    
    /**
     * Get step-by-step swaps to reach maximum
     */
    fun getSwapSequence(num: String, k: Int): List<Pair<Int, Int>> {
        // This is a simplified version - full implementation would track swaps
        val swaps = mutableListOf<Pair<Int, Int>>()
        var current = num.toCharArray()
        val target = findMaximum(num, k).toCharArray()
        
        // Greedy approach to find one sequence of swaps
        var swapsLeft = k
        for (i in current.indices) {
            if (swapsLeft == 0) break
            if (current[i] != target[i]) {
                // Find target[i] in current array
                for (j in i + 1 until current.size) {
                    if (current[j] == target[i]) {
                        swap(current, i, j)
                        swaps.add(Pair(i, j))
                        swapsLeft--
                        break
                    }
                }
            }
        }
        
        return swaps
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: num = "1234", K = 2
 * 
 * Position 0, K=2:
 *   Current: 1234, Max digit in [2,3,4] = 4
 *   Swap 1↔4: 4231
 *   Position 1, K=1:
 *     Current: 4231, Max digit in [3,1] = 3
 *     Swap 2↔3: 4321
 *     Position 2, K=0: Stop
 *     Result: 4321 ✓
 * 
 * OUTPUT: "4321"
 * 
 * ============================================================================
 * EXAMPLE 2
 * ============================================================================
 * 
 * INPUT: num = "7899", K = 1
 * 
 * Position 0, K=1:
 *   Current: 7899, Max digit in [8,9,9] = 9
 *   Swap 7↔9 (first 9): 9897
 *   OR
 *   Swap 7↔9 (second 9): 9897 (same result)
 * 
 * OUTPUT: "9897"
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. K = 0: Return original number
 * 2. Already maximum: No swaps needed
 * 3. K >= N: Can achieve maximum permutation
 * 4. All digits same: Return original
 * 5. Descending order: Already maximum
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solution = LargestNumberInKSwaps()
    
    println("=== Largest Number in K Swaps ===\n")
    
    // Test 1: Basic example
    println("Test 1: num = \"1234\", K = 2")
    val result1 = solution.findMaximum("1234", 2)
    println("Result: $result1")
    println("Expected: 4321\n")
    
    // Test 2: No swaps needed
    println("Test 2: num = \"9876\", K = 2")
    val result2 = solution.findMaximum("9876", 2)
    println("Result: $result2")
    println("Expected: 9876 (already maximum)\n")
    
    // Test 3: K = 0
    println("Test 3: num = \"1234\", K = 0")
    val result3 = solution.findMaximum("1234", 0)
    println("Result: $result3")
    println("Expected: 1234 (no swaps)\n")
    
    // Test 4: Complex case
    println("Test 4: num = \"129814\", K = 2")
    val result4 = solution.findMaximum("129814", 2)
    println("Result: $result4")
    println("Expected: 981214\n")
    
    // Test 5: Duplicate digits
    println("Test 5: num = \"7899\", K = 1")
    val result5 = solution.findMaximum("7899", 1)
    println("Result: $result5")
    println("Expected: 9897\n")
    
    // Test 6: Large K
    println("Test 6: num = \"132\", K = 5")
    val result6 = solution.findMaximum("132", 5)
    println("Result: $result6")
    println("Expected: 321\n")
    
    // Test 7: Single digit
    println("Test 7: num = \"5\", K = 1")
    val result7 = solution.findMaximum("5", 1)
    println("Result: $result7")
    println("Expected: 5\n")
    
    // Test 8: All same digits
    println("Test 8: num = \"7777\", K = 2")
    val result8 = solution.findMaximum("7777", 2)
    println("Result: $result8")
    println("Expected: 7777\n")
    
    // Test 9: Longer number
    println("Test 9: num = \"254736\", K = 3")
    val result9 = solution.findMaximum("254736", 3)
    println("Result: $result9\n")
}
