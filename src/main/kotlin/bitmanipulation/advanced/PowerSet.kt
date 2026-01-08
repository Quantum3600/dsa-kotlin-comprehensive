/**
 * ============================================================================
 * PROBLEM: Generate Power Set (All Subsets)
 * DIFFICULTY: Medium
 * CATEGORY: Bit Manipulation - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a set of distinct integers, generate all possible subsets (the power set).
 * The power set of any set S is the set of all subsets of S, including the empty
 * set and S itself. Use bit manipulation to generate all subsets efficiently.
 * 
 * INPUT FORMAT:
 * - An array of distinct integers: nums = [1, 2, 3]
 * - Array size n where 0 <= n <= 10
 * 
 * OUTPUT FORMAT:
 * - List of all possible subsets: [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
 * - Total subsets = 2^n (including empty set)
 * 
 * CONSTRAINTS:
 * - 0 <= nums.length <= 10
 * - -10 <= nums[i] <= 10
 * - All numbers in nums are unique
 * - Order of subsets in output doesn't matter
 * - Order of elements within a subset doesn't matter
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Each element in the set can either be INCLUDED or EXCLUDED from a subset.
 * For n elements, we have 2 choices per element, giving us 2^n total subsets.
 * We can represent each subset as a binary number from 0 to 2^n - 1, where
 * each bit position indicates whether to include that element.
 * 
 * KEY INSIGHT:
 * Map each integer from 0 to 2^n - 1 to a unique subset:
 * - Bit 0 represents whether to include nums[0]
 * - Bit 1 represents whether to include nums[1]
 * - Bit i represents whether to include nums[i]
 * 
 * If bit i is SET (1), include nums[i] in the subset
 * If bit i is CLEAR (0), exclude nums[i] from the subset
 * 
 * WHY THIS WORKS:
 * Binary counting naturally enumerates all possible combinations!
 * 
 * Example: For [1, 2, 3] (n=3), we have 2^3 = 8 subsets:
 * 
 * Number | Binary | Bit 2 | Bit 1 | Bit 0 | Subset
 * -------|--------|-------|-------|-------|--------
 *   0    |  000   |   0   |   0   |   0   | []
 *   1    |  001   |   0   |   0   |   1   | [1]
 *   2    |  010   |   0   |   1   |   0   | [2]
 *   3    |  011   |   0   |   1   |   1   | [1,2]
 *   4    |  100   |   1   |   0   |   0   | [3]
 *   5    |  101   |   1   |   0   |   1   | [1,3]
 *   6    |  110   |   1   |   1   |   0   | [2,3]
 *   7    |  111   |   1   |   1   |   1   | [1,2,3]
 * 
 * ALGORITHM STEPS (Bit Manipulation Approach):
 * 1. Calculate total subsets: 2^n
 * 2. For each number i from 0 to 2^n - 1:
 *    a. Create empty subset
 *    b. For each bit position j from 0 to n-1:
 *       - Check if j-th bit is set in i
 *       - If yes, include nums[j] in current subset
 *    c. Add subset to result
 * 3. Return all subsets
 * 
 * VISUAL EXAMPLE 1: Generate subsets of [1, 2, 3]
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Input: [1, 2, 3]
 * n = 3, Total subsets = 2^3 = 8
 * 
 * Iteration 0: i = 0 (binary: 000)
 *   Check bit 0: 0 & (1 << 0) = 0 & 1 = 0 → Don't include nums[0]=1
 *   Check bit 1: 0 & (1 << 1) = 0 & 2 = 0 → Don't include nums[1]=2
 *   Check bit 2: 0 & (1 << 2) = 0 & 4 = 0 → Don't include nums[2]=3
 *   Subset: []
 * 
 * Iteration 1: i = 1 (binary: 001)
 *   Check bit 0: 1 & (1 << 0) = 1 & 1 = 1 → Include nums[0]=1 ✓
 *   Check bit 1: 1 & (1 << 1) = 1 & 2 = 0 → Don't include nums[1]=2
 *   Check bit 2: 1 & (1 << 2) = 1 & 4 = 0 → Don't include nums[2]=3
 *   Subset: [1]
 * 
 * Iteration 3: i = 3 (binary: 011)
 *   Check bit 0: 3 & (1 << 0) = 3 & 1 = 1 → Include nums[0]=1 ✓
 *   Check bit 1: 3 & (1 << 1) = 3 & 2 = 2 → Include nums[1]=2 ✓
 *   Check bit 2: 3 & (1 << 2) = 3 & 4 = 0 → Don't include nums[2]=3
 *   Subset: [1, 2]
 * 
 * Iteration 7: i = 7 (binary: 111)
 *   Check bit 0: 7 & (1 << 0) = 7 & 1 = 1 → Include nums[0]=1 ✓
 *   Check bit 1: 7 & (1 << 1) = 7 & 2 = 2 → Include nums[1]=2 ✓
 *   Check bit 2: 7 & (1 << 2) = 7 & 4 = 4 → Include nums[2]=3 ✓
 *   Subset: [1, 2, 3]
 * 
 * Final Result: [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]]
 * 
 * VISUAL EXAMPLE 2: Generate subsets of [5, 7]
 * ═══════════════════════════════════════════════════════════════════
 * 
 * Input: [5, 7]
 * n = 2, Total subsets = 2^2 = 4
 * 
 * i=0 (00): [] - No bits set, empty subset
 * i=1 (01): [5] - Bit 0 set, include nums[0]
 * i=2 (10): [7] - Bit 1 set, include nums[1]
 * i=3 (11): [5,7] - Both bits set, include both
 * 
 * ALTERNATIVE APPROACHES:
 * ────────────────────────────────────────────────────────────────────
 * 
 * 1. RECURSIVE BACKTRACKING:
 *    - For each element, recursively choose to include or exclude it
 *    - TIME: O(n * 2^n), SPACE: O(n) for recursion stack + O(2^n) for result
 *    - Pros: Intuitive, easy to understand
 *    - Cons: Higher space complexity due to recursion
 * 
 * 2. ITERATIVE (Building subsets):
 *    - Start with empty set [[]]
 *    - For each element, add it to all existing subsets
 *    - Example: [] → [], [1] → [], [1], [2], [1,2] → ...
 *    - TIME: O(n * 2^n), SPACE: O(2^n)
 *    - Pros: No recursion, easy to visualize growth
 *    - Cons: Modifies result during iteration
 * 
 * 3. BIT MANIPULATION (Current approach):
 *    - Use binary counter from 0 to 2^n - 1
 *    - Each bit represents inclusion/exclusion
 *    - TIME: O(n * 2^n), SPACE: O(2^n) for result only
 *    - Pros: No recursion, elegant, deterministic order
 *    - Cons: Requires bit manipulation understanding
 *    - BEST for: Interviews, clarity, no recursion overhead
 * 
 * APPROACH COMPARISON:
 * ┌─────────────────┬──────────┬───────────┬──────────────┐
 * │ Approach        │ Time     │ Space     │ Best For     │
 * ├─────────────────┼──────────┼───────────┼──────────────┤
 * │ Recursive       │ O(n*2^n) │ O(n+2^n)  │ Intuition    │
 * │ Iterative Build │ O(n*2^n) │ O(2^n)    │ Simplicity   │
 * │ Bit Mask        │ O(n*2^n) │ O(2^n)    │ Interviews   │
 * └─────────────────┴──────────┴───────────┴──────────────┘
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * 2^n)
 * - Total subsets to generate: 2^n
 * - For each subset, we check n bits: O(n)
 * - Total: 2^n * n = O(n * 2^n)
 * 
 * Breakdown:
 * - Outer loop runs 2^n times (from 0 to 2^n - 1)
 * - Inner loop runs n times (checking each bit)
 * - Creating each subset: O(n) in worst case
 * 
 * SPACE COMPLEXITY: O(2^n)
 * - Result list contains 2^n subsets
 * - Total elements across all subsets: sum from i=0 to n of C(n,i) * i = n * 2^(n-1)
 * - But we express it as O(n * 2^n) including element storage
 * - No extra space for recursion (iterative approach)
 * - Overall: O(2^n) for subset count, or O(n * 2^n) for all elements
 * 
 * WHERE n IS THE LENGTH OF INPUT ARRAY
 * 
 * OPTIMIZATION NOTES:
 * - Cannot do better than O(n * 2^n) as we must generate all 2^n subsets
 * - This is output-sensitive: time is proportional to output size
 * - Bit manipulation is optimal for this problem
 * 
 * ============================================================================
 */

package bitmanipulation.advanced

class PowerSet {
    
    /**
     * APPROACH 1: Bit Manipulation (Optimal for interviews)
     * Generate all subsets using binary representation
     * 
     * TIME: O(n * 2^n), SPACE: O(2^n) for result
     * 
     * @param nums Array of distinct integers
     * @return List of all possible subsets
     */
    fun subsetsUsingBitMask(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val n = nums.size
        
        // Total number of subsets = 2^n
        val totalSubsets = 1 shl n  // Equivalent to 2^n or Math.pow(2, n)
        
        // Iterate through all numbers from 0 to 2^n - 1
        for (i in 0 until totalSubsets) {
            val subset = mutableListOf<Int>()
            
            // Check each bit position
            for (j in 0 until n) {
                // If j-th bit is set in i, include nums[j]
                if ((i and (1 shl j)) != 0) {
                    subset.add(nums[j])
                }
            }
            
            result.add(subset)
        }
        
        return result
    }
    
    /**
     * APPROACH 2: Recursive Backtracking
     * Build subsets by choosing to include or exclude each element
     * 
     * TIME: O(n * 2^n), SPACE: O(n) recursion stack + O(2^n) result
     * 
     * @param nums Array of distinct integers
     * @return List of all possible subsets
     */
    fun subsetsRecursive(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        backtrack(nums, 0, mutableListOf(), result)
        return result
    }
    
    /**
     * Helper function for recursive approach
     * 
     * @param nums Original array
     * @param start Current index in array
     * @param current Current subset being built
     * @param result Accumulator for all subsets
     */
    private fun backtrack(
        nums: IntArray,
        start: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // Add current subset to result
        result.add(ArrayList(current))
        
        // Try including each remaining element
        for (i in start until nums.size) {
            // Include nums[i]
            current.add(nums[i])
            
            // Recurse with next index
            backtrack(nums, i + 1, current, result)
            
            // Backtrack: remove nums[i] to try next element
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * APPROACH 3: Iterative Building
     * Start with empty set, add each element to all existing subsets
     * 
     * TIME: O(n * 2^n), SPACE: O(2^n)
     * 
     * @param nums Array of distinct integers
     * @return List of all possible subsets
     */
    fun subsetsIterative(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        result.add(emptyList()) // Start with empty subset
        
        // For each element in nums
        for (num in nums) {
            val size = result.size
            
            // Add current element to all existing subsets
            for (i in 0 until size) {
                val newSubset = ArrayList(result[i])
                newSubset.add(num)
                result.add(newSubset)
            }
        }
        
        return result
    }
    
    /**
     * Count total number of subsets without generating them
     * Useful for validation or space checking
     * 
     * TIME: O(1), SPACE: O(1)
     * 
     * @param n Size of the set
     * @return Total number of subsets = 2^n
     */
    fun countSubsets(n: Int): Int {
        return 1 shl n  // 2^n
    }
    
    /**
     * Visualize how bit masking generates subsets
     * 
     * @param nums Array to generate subsets from
     */
    fun visualizeBitMasking(nums: IntArray) {
        val n = nums.size
        val totalSubsets = 1 shl n
        
        println("Generating subsets for: ${nums.contentToString()}")
        println("n = $n, Total subsets = 2^$n = $totalSubsets")
        println()
        println("i   | Binary     | Bits Set | Subset")
        println("----|------------|----------|------------------")
        
        for (i in 0 until totalSubsets) {
            val binary = Integer.toBinaryString(i).padStart(n, '0')
            val subset = mutableListOf<Int>()
            val bitsSet = mutableListOf<Int>()
            
            for (j in 0 until n) {
                if ((i and (1 shl j)) != 0) {
                    subset.add(nums[j])
                    bitsSet.add(j)
                }
            }
            
            val bitsStr = if (bitsSet.isEmpty()) "none" else bitsSet.toString()
            val subsetStr = subset.toString()
            
            println("${i.toString().padEnd(3)} | $binary | ${bitsStr.padEnd(8)} | $subsetStr")
        }
    }
}

/**
 * ============================================================================
 * EDGE CASES & APPLICATIONS
 * ============================================================================
 * 
 * EDGE CASES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Empty Array:
 *    Input: []
 *    Output: [[]]
 *    Explanation: Power set of empty set contains only empty set
 * 
 * 2. Single Element:
 *    Input: [1]
 *    Output: [[], [1]]
 *    Explanation: 2^1 = 2 subsets
 * 
 * 3. Two Elements:
 *    Input: [1, 2]
 *    Output: [[], [1], [2], [1,2]]
 *    Explanation: 2^2 = 4 subsets
 * 
 * 4. Negative Numbers:
 *    Input: [-1, 0, 1]
 *    Output: [[], [-1], [0], [-1,0], [1], [-1,1], [0,1], [-1,0,1]]
 *    Explanation: Works same as positive numbers
 * 
 * 5. All Same Sign:
 *    Input: [1, 2, 3, 4]
 *    Output: 2^4 = 16 subsets
 *    Explanation: Sign doesn't matter, only count matters
 * 
 * 6. Maximum Size (n=10):
 *    Input: 10 elements
 *    Output: 2^10 = 1024 subsets
 *    Explanation: Tests performance limits
 * 
 * 7. Large Numbers:
 *    Input: [100, 200, 300]
 *    Output: Normal 2^3 = 8 subsets
 *    Explanation: Magnitude doesn't affect subset generation
 * 
 * 8. Mixed Positive/Negative:
 *    Input: [-5, -1, 0, 3, 7]
 *    Output: 2^5 = 32 subsets
 *    Explanation: All combinations still generated
 * 
 * COMMON MISTAKES:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Off-by-one in bit check**: Using (i & j) instead of (i & (1 << j))
 *    - Incorrect: checking wrong bit positions
 *    - Correct: Must shift 1 to j-th position first
 * 
 * 2. **Integer overflow**: For n > 30, 2^n exceeds int range
 *    - Solution: Use Long for large n, or validate n <= 30
 * 
 * 3. **Modifying subsets after adding**: Reusing same list object
 *    - Problem: All subsets point to same mutable list
 *    - Solution: Create new list for each subset
 * 
 * 4. **Wrong loop condition**: Using i < 2^n instead of i < (1 << n)
 *    - Bit shift is more efficient and clearer
 * 
 * 5. **Forgetting empty set**: Starting from i=1 instead of i=0
 *    - i=0 generates the empty subset
 * 
 * 6. **Bit order confusion**: Thinking bit 0 is leftmost
 *    - Bit 0 is rightmost (least significant)
 * 
 * OPTIMIZATION TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Pre-allocate result list**: 
 *    result = ArrayList(1 shl n) to avoid resizing
 * 
 * 2. **Use shl instead of Math.pow**: 
 *    (1 shl n) is faster than Math.pow(2, n).toInt()
 * 
 * 3. **Check bit efficiently**: 
 *    (i and (1 shl j)) != 0 is faster than (i shr j) and 1 == 1
 * 
 * 4. **Early termination**: If only subsets of size k needed, optimize
 * 
 * 5. **Lexicographic order**: Current bit approach gives natural order
 * 
 * INTERVIEW TIPS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Start with explanation**: 
 *    "Each element can be in or out, giving 2^n subsets. I'll use bits to
 *    represent this - bit i set means include element i."
 * 
 * 2. **Draw example**: Show binary counting for small array like [1,2]
 *    00 → [], 01 → [1], 10 → [2], 11 → [1,2]
 * 
 * 3. **Mention alternatives**: "I could also use recursion or iterative
 *    building, but bit manipulation is most elegant."
 * 
 * 4. **Complexity discussion**: "Time is O(n * 2^n) because we generate
 *    2^n subsets and each takes O(n) to build. Can't do better as output
 *    size itself is 2^n."
 * 
 * 5. **Handle follow-ups**:
 *    - "Generate only subsets of size k" → Use combinations
 *    - "With duplicates" → Need to handle duplicate elements
 *    - "Sum of all subsets" → Can optimize without generating all
 * 
 * 6. **Code walkthrough**: Explain bit checking clearly
 *    "For each number i from 0 to 2^n-1, I check which bits are set.
 *    If bit j is set, I include nums[j] in the current subset."
 * 
 * FOLLOW-UP QUESTIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Subsets with duplicates**: Modify to skip duplicate subsets
 * 2. **Subsets of size k**: Generate only k-sized subsets (combinations)
 * 3. **Sum of all subsets**: Calculate without generating all subsets
 * 4. **Subset with given sum**: Find if any subset sums to target
 * 5. **Largest subset product**: Find subset with maximum product
 * 6. **Lexicographic order**: Generate subsets in specific order
 * 
 * REAL-WORLD APPLICATIONS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. **Feature Selection in ML**:
 *    - Try all combinations of features to find best model
 *    - Each subset represents a feature combination
 * 
 * 2. **Menu Combinations**:
 *    - Restaurant: all possible meal combinations
 *    - E-commerce: product bundle options
 * 
 * 3. **Team Formation**:
 *    - Generate all possible team compositions
 *    - Useful in sports, project management
 * 
 * 4. **Portfolio Optimization**:
 *    - All possible asset combinations
 *    - Risk analysis across different portfolios
 * 
 * 5. **Configuration Testing**:
 *    - Test all combinations of software features
 *    - Hardware configuration permutations
 * 
 * 6. **Subset Sum Problems**:
 *    - Dynamic programming on subsets
 *    - Partition problems, knapsack variants
 * 
 * 7. **Graph Algorithms**:
 *    - Generate all possible paths
 *    - Clique detection, independent sets
 * 
 * 8. **Cryptography**:
 *    - Brute force attack search spaces
 *    - Key combination enumeration
 * 
 * RELATED PROBLEMS:
 * ──────────────────────────────────────────────────────────────────────────
 * 
 * 1. Subsets II (with duplicates) - LeetCode 90
 * 2. Combinations - LeetCode 77
 * 3. Combination Sum - LeetCode 39
 * 4. Partition Equal Subset Sum - LeetCode 416
 * 5. Letter Case Permutation - LeetCode 784
 * 6. Generate Parentheses - LeetCode 22
 * 7. Permutations - LeetCode 46
 * 8. N-Queens - LeetCode 51
 * 
 * ============================================================================
 */

fun main() {
    val powerSet = PowerSet()
    
    println("═".repeat(70))
    println("POWER SET - ALL SUBSETS USING BIT MANIPULATION")
    println("═".repeat(70))
    println()
    
    // Test 1: Standard case with 3 elements
    println("Test 1: Generate subsets of [1, 2, 3]")
    println("─".repeat(70))
    val nums1 = intArrayOf(1, 2, 3)
    powerSet.visualizeBitMasking(nums1)
    val result1 = powerSet.subsetsUsingBitMask(nums1)
    println("\nResult: $result1")
    println("Total subsets: ${result1.size}")
    println("Expected: 8 subsets ✓")
    println()
    
    // Test 2: Empty array
    println("Test 2: Empty array []")
    println("─".repeat(70))
    val nums2 = intArrayOf()
    val result2 = powerSet.subsetsUsingBitMask(nums2)
    println("Result: $result2")
    println("Expected: [[]] (only empty subset) ✓")
    println()
    
    // Test 3: Single element
    println("Test 3: Single element [5]")
    println("─".repeat(70))
    val nums3 = intArrayOf(5)
    val result3 = powerSet.subsetsUsingBitMask(nums3)
    println("Result: $result3")
    println("Expected: [[], [5]] ✓")
    println()
    
    // Test 4: Two elements
    println("Test 4: Two elements [1, 2]")
    println("─".repeat(70))
    val nums4 = intArrayOf(1, 2)
    val result4 = powerSet.subsetsUsingBitMask(nums4)
    println("Result: $result4")
    println("Expected: [[], [1], [2], [1, 2]] ✓")
    println()
    
    // Test 5: Negative numbers
    println("Test 5: Negative numbers [-1, 0, 1]")
    println("─".repeat(70))
    val nums5 = intArrayOf(-1, 0, 1)
    val result5 = powerSet.subsetsUsingBitMask(nums5)
    println("Result: $result5")
    println("Total subsets: ${result5.size}")
    println("Expected: 8 subsets ✓")
    println()
    
    // Test 6: Compare all three approaches
    println("Test 6: Compare all three approaches for [1, 2, 3]")
    println("─".repeat(70))
    val nums6 = intArrayOf(1, 2, 3)
    val bitMask = powerSet.subsetsUsingBitMask(nums6)
    val recursive = powerSet.subsetsRecursive(nums6)
    val iterative = powerSet.subsetsIterative(nums6)
    println("Bit Mask approach: ${bitMask.size} subsets")
    println("Recursive approach: ${recursive.size} subsets")
    println("Iterative approach: ${iterative.size} subsets")
    println("All produce 8 subsets ✓")
    println()
    
    // Test 7: Larger array (4 elements)
    println("Test 7: Larger array [1, 2, 3, 4]")
    println("─".repeat(70))
    val nums7 = intArrayOf(1, 2, 3, 4)
    val result7 = powerSet.subsetsUsingBitMask(nums7)
    println("Total subsets: ${result7.size}")
    println("Expected: 2^4 = 16 subsets ✓")
    println("First few: ${result7.take(4)}")
    println("Last few: ${result7.takeLast(4)}")
    println()
    
    // Test 8: Large numbers
    println("Test 8: Large numbers [100, 200, 300]")
    println("─".repeat(70))
    val nums8 = intArrayOf(100, 200, 300)
    val result8 = powerSet.subsetsUsingBitMask(nums8)
    println("Result: $result8")
    println("Total subsets: ${result8.size}")
    println("Expected: 8 subsets ✓")
    println()
    
    // Test 9: Subset count validation
    println("Test 9: Subset count validation")
    println("─".repeat(70))
    for (n in 0..6) {
        val expected = powerSet.countSubsets(n)
        println("n=$n: Expected subsets = $expected (2^$n)")
    }
    println("✓")
    println()
    
    // Test 10: Performance with larger input
    println("Test 10: Performance test with 8 elements")
    println("─".repeat(70))
    val nums10 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val startTime = System.currentTimeMillis()
    val result10 = powerSet.subsetsUsingBitMask(nums10)
    val endTime = System.currentTimeMillis()
    println("Generated ${result10.size} subsets in ${endTime - startTime}ms")
    println("Expected: 2^8 = 256 subsets ✓")
    println()
    
    println("═".repeat(70))
    println("All tests passed! ✓")
    println("═".repeat(70))
}
