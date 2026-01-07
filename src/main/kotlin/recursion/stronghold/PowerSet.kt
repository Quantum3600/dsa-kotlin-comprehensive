/**
 * ============================================================================
 * PROBLEM: Power Set (All Subsets)
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Stronghold
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a set of distinct integers, return all possible subsets (the power set).
 * The power set of a set is the set of all its subsets, including the empty
 * set and the set itself.
 * 
 * The solution set must not contain duplicate subsets. 
 * Return the solution in any order.
 * 
 * INPUT FORMAT:
 * - An array of distinct integers:  nums = [1, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - A list of lists containing all subsets
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10
 * - -10 <= nums[i] <= 10
 * - All numbers are distinct
 * 
 * EXAMPLES:
 * Input: nums = [1, 2, 3]
 * Output:  [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
 * 
 * Input: nums = [0]
 * Output: [[], [0]]
 * 
 * Input: nums = [1, 2]
 * Output: [[], [1], [2], [1,2]]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For each element in the array, we have TWO choices:
 * 1. Include it in the current subset
 * 2. Exclude it from the current subset
 * 
 * This creates a binary decision tree with 2^n leaf nodes (n = array size),
 * each representing a unique subset.
 * 
 * KEY INSIGHT:
 * Think of subsets as making binary choices:
 * - [1, 2, 3]:  For 1, include or exclude?  For 2?  For 3? 
 * - This gives us 2 × 2 × 2 = 8 subsets
 * 
 * VISUAL EXAMPLE (nums = [1, 2, 3]):
 * ```
 *                          []
 *                     /           \
 *               exclude 1        include 1
 *                  []               [1]
 *                /    \           /     \
 *            exc 2   inc 2    exc 2   inc 2
 *              []     [2]      [1]     [1,2]
 *             / \     / \      / \      / \
 *            e3 i3   e3 i3    e3 i3    e3 i3
 *           [] [3]  [2][2,3] [1][1,3][1,2][1,2,3]
 * ```
 * 
 * MULTIPLE APPROACHES:
 * 
 * APPROACH 1: BACKTRACKING (Recursive)
 * - At each index, make two recursive calls:
 *   a) Include current element
 *   b) Exclude current element (move to next)
 * - Base case: When index reaches end, add current subset
 * 
 * APPROACH 2: ITERATIVE (Cascading)
 * - Start with empty subset:  [[]]
 * - For each number, add it to all existing subsets
 * - Example: [] → [], [1] → [], [1], [2], [1,2] → ... 
 * 
 * APPROACH 3: BIT MANIPULATION
 * - For n elements, there are 2^n subsets
 * - Each subset corresponds to a binary number from 0 to 2^n - 1
 * - If bit i is set, include nums[i] in subset
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n × 2^n)
 * - Number of subsets: 2^n
 * - Average subset size: n/2
 * - Copying each subset: O(n/2) on average
 * - Total: O(n × 2^n)
 * 
 * WHY 2^n subsets: 
 * - Each element has 2 choices (include/exclude)
 * - n elements → 2^n combinations
 * 
 * SPACE COMPLEXITY: O(n × 2^n)
 * - Result storage: 2^n subsets, average size n/2
 * - Recursion stack: O(n) depth
 * - Current subset: O(n)
 * - Total: O(n × 2^n)
 * 
 * ============================================================================
 * APPROACH COMPARISON
 * ============================================================================
 * 
 * APPROACH 1: Backtracking (Implemented)
 * Time: O(n × 2^n), Space: O(n × 2^n)
 * ✅ Most intuitive
 * ✅ Easy to extend for combinations/permutations
 * ✅ Natural recursive structure
 * 
 * APPROACH 2: Iterative Cascading
 * Time: O(n × 2^n), Space: O(n × 2^n)
 * ✅ No recursion overhead
 * ✅ Easy to understand incrementally
 * ❌ Less flexible for variations
 * 
 * APPROACH 3: Bit Manipulation
 * Time: O(n × 2^n), Space: O(n × 2^n)
 * ✅ Elegant mathematical approach
 * ✅ Clearly shows 2^n pattern
 * ❌ Less intuitive for beginners
 * 
 * ============================================================================
 * EDGE CASES & COMMON MISTAKES
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Empty array: Return [[]] (empty set has one subset:  empty set itself)
 * 2. Single element: [[], [element]]
 * 3. Array with 10 elements: 1024 subsets (2^10)
 * 
 * COMMON MISTAKES: 
 * 1. Forgetting to include empty subset
 * 2. Modifying same list reference → All subsets point to same list
 * 3. Not creating new list when adding to result
 * 4. Confusing subsets with permutations or combinations
 * 5. Wrong backtracking → Not removing element after recursive call
 * 
 * SUBSET vs COMBINATION vs PERMUTATION:
 * - Subset: Any selection, order doesn't matter, can be empty
 * - Combination: Selection of k elements, order doesn't matter
 * - Permutation:  Arrangement of elements, order matters
 * 
 * Example: [1, 2, 3]
 * - Subsets:  8 total including []
 * - 2-Combinations: [1,2], [1,3], [2,3] (3 total)
 * - Permutations: 6 total (3!)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Subsets II (Medium) - With duplicate elements
 * 2. Combinations (Medium) - Subsets of specific size k
 * 3. Combination Sum (Medium) - Subsets with target sum
 * 4. Permutations (Medium) - All arrangements
 * 5. Generate Parentheses (Medium) - Similar backtracking
 * 6. Letter Combinations of Phone Number (Medium)
 * 
 * ============================================================================
 * LEARNING RESOURCES
 * ============================================================================
 * 
 * CONCEPTS DEMONSTRATED:
 * - Backtracking algorithm
 * - Binary decision trees
 * - Power set in set theory
 * - Exponential time complexity
 * - Bit manipulation techniques
 * 
 * PRACTICE PROGRESSION:
 * 1. Master this problem with small arrays (n ≤ 3)
 * 2. Try "Subsets II" with duplicates
 * 3. Move to "Combinations" (k-sized subsets)
 * 4. Practice "Combination Sum" variants
 * 5. Advanced: "Partition to K Equal Sum Subsets"
 * 
 * MATHEMATICAL INSIGHT:
 * - Power set cardinality:  |P(S)| = 2^|S|
 * - Sum of subset sizes: n × 2^(n-1)
 * - Empty set is subset of every set
 * - Every set is subset of itself
 */

package recursion.stronghold

class PowerSet {
    
    /**
     * Generates all subsets using backtracking approach.
     * 
     * @param nums Array of distinct integers
     * @return List of all subsets
     * 
     * Time Complexity:  O(n × 2^n)
     * Space Complexity: O(n × 2^n)
     */
    fun subsets(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        backtrack(nums, 0, current, result)
        return result
    }
    
    /**
     * Helper function for backtracking.
     * 
     * @param nums Input array
     * @param index Current index being considered
     * @param current Current subset being built
     * @param result List to store all subsets
     */
    private fun backtrack(
        nums: IntArray,
        index: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // Add current subset to result (works for all nodes in decision tree)
        result.add(ArrayList(current))
        
        // Try including each remaining element
        for (i in index until nums.size) {
            // Include nums[i]
            current.add(nums[i])
            
            // Recurse with next index
            backtrack(nums, i + 1, current, result)
            
            // Backtrack: exclude nums[i]
            current. removeAt(current.size - 1)
        }
    }
    
    /**
     * Alternative approach:  Iterative cascading.
     * Builds subsets incrementally by adding each element to existing subsets.
     * 
     * Time Complexity: O(n × 2^n)
     * Space Complexity: O(n × 2^n)
     */
    fun subsetsIterative(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        result.add(emptyList()) // Start with empty subset
        
        // For each number, add it to all existing subsets
        for (num in nums) {
            val size = result.size
            for (i in 0 until size) {
                val newSubset = ArrayList(result[i])
                newSubset.add(num)
                result.add(newSubset)
            }
        }
        
        return result
    }
    
    /**
     * Alternative approach: Bit manipulation.
     * Uses binary representation to generate all subsets.
     * 
     * Example: For [1,2,3]
     * - 000 (0) → []
     * - 001 (1) → [3]
     * - 010 (2) → [2]
     * - 011 (3) → [2,3]
     * - 100 (4) → [1]
     * - 101 (5) → [1,3]
     * - 110 (6) → [1,2]
     * - 111 (7) → [1,2,3]
     * 
     * Time Complexity: O(n × 2^n)
     * Space Complexity: O(n × 2^n)
     */
    fun subsetsBitManipulation(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val n = nums.size
        val totalSubsets = 1 shl n // 2^n
        
        // Generate all numbers from 0 to 2^n - 1
        for (mask in 0 until totalSubsets) {
            val subset = mutableListOf<Int>()
            
            // Check each bit position
            for (i in 0 until n) {
                // If bit i is set, include nums[i]
                if ((mask and (1 shl i)) != 0) {
                    subset.add(nums[i])
                }
            }
            
            result.add(subset)
        }
        
        return result
    }
    
    /**
     * Alternative approach: Include/Exclude recursion.
     * More explicit about the two choices at each step.
     * 
     * Time Complexity:  O(n × 2^n)
     * Space Complexity:  O(n × 2^n)
     */
    fun subsetsExplicitChoice(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        
        fun generateSubsets(index: Int, current: MutableList<Int>) {
            // Base case: processed all elements
            if (index == nums.size) {
                result.add(ArrayList(current))
                return
            }
            
            // Choice 1: Exclude current element
            generateSubsets(index + 1, current)
            
            // Choice 2: Include current element
            current.add(nums[index])
            generateSubsets(index + 1, current)
            current.removeAt(current.size - 1) // Backtrack
        }
        
        generateSubsets(0, mutableListOf())
        return result
    }
    
    /**
     * Generates k-sized subsets (combinations).
     * Useful variant:  Subsets of specific size. 
     * 
     * @param nums Input array
     * @param k Desired subset size
     * @return All k-sized subsets
     * 
     * Time Complexity:  O(n choose k) × k
     * Space Complexity: O(n choose k) × k
     */
    fun kSizedSubsets(nums: IntArray, k: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        fun backtrack(index: Int) {
            if (current.size == k) {
                result.add(ArrayList(current))
                return
            }
            
            for (i in index until nums.size) {
                current.add(nums[i])
                backtrack(i + 1)
                current.removeAt(current.size - 1)
            }
        }
        
        backtrack(0)
        return result
    }
}

fun main() {
    val generator = PowerSet()
    
    // Test Case 1: Basic example [1, 2, 3]
    println("Test Case 1: nums = [1, 2, 3]")
    val nums1 = intArrayOf(1, 2, 3)
    val result1 = generator.subsets(nums1)
    println("Count: ${result1.size} (expected: 8)")
    println("Subsets:  $result1")
    println()
    
    // Test Case 2: Single element
    println("Test Case 2: nums = [0]")
    val nums2 = intArrayOf(0)
    val result2 = generator. subsets(nums2)
    println("Expected: [[], [0]]")
    println("Actual: $result2")
    println()
    
    // Test Case 3: Two elements
    println("Test Case 3: nums = [1, 2]")
    val nums3 = intArrayOf(1, 2)
    val result3 = generator.subsets(nums3)
    println("Expected:  [[], [1], [2], [1,2]]")
    println("Actual:  $result3")
    println("Count: ${result3.size} (expected: 4)")
    println()
    
    // Test Case 4: Testing iterative approach
    println("Test Case 4:  Iterative approach with [1, 2, 3]")
    val result4 = generator.subsetsIterative(nums1)
    println("Count: ${result4.size} (expected: 8)")
    println("Subsets: $result4")
    println()
    
    // Test Case 5: Testing bit manipulation approach
    println("Test Case 5: Bit manipulation approach with [1, 2, 3]")
    val result5 = generator.subsetsBitManipulation(nums1)
    println("Count: ${result5.size} (expected: 8)")
    println("Subsets: $result5")
    println()
    
    // Test Case 6: Testing explicit choice approach
    println("Test Case 6: Explicit choice approach with [1, 2, 3]")
    val result6 = generator.subsetsExplicitChoice(nums1)
    println("Count: ${result6.size} (expected: 8)")
    println("Subsets: $result6")
    println()
    
    // Test Case 7:  Larger array
    println("Test Case 7: nums = [1, 2, 3, 4]")
    val nums7 = intArrayOf(1, 2, 3, 4)
    val result7 = generator. subsets(nums7)
    println("Count: ${result7.size} (expected: 16)")
    println("Sample subsets: ${result7.take(5)}")
    println()
    
    // Test Case 8: 2-sized subsets (combinations)
    println("Test Case 8: All 2-sized subsets of [1, 2, 3, 4]")
    val result8 = generator.kSizedSubsets(nums7, 2)
    println("Expected count: 6 (4 choose 2)")
    println("Actual count: ${result8.size}")
    println("Subsets: $result8")
    println()
    
    // Test Case 9: Empty set included? 
    println("Test Case 9:  Verify empty set inclusion")
    val result9 = generator.subsets(intArrayOf(1))
    println("Contains empty set: ${result9.any { it.isEmpty() }}")
    println()
    
    // Test Case 10: Power of 2 verification
    println("Test Case 10: Verify 2^n pattern")
    for (n in 1..6) {
        val nums = IntArray(n) { it + 1 }
        val count = generator.subsets(nums).size
        val expected = 1 shl n // 2^n
        println("n=$n:  $count subsets (expected: $expected) ✓")
    }
    println()
    
    // Test Case 11: Performance test
    println("Test Case 11: Performance test with n = 10")
    val nums11 = IntArray(10) { it + 1 }
    val startTime = System.currentTimeMillis()
    val result11 = generator.subsets(nums11)
    val endTime = System. currentTimeMillis()
    println("Count: ${result11.size} (expected: 1024)")
    println("Time taken: ${endTime - startTime}ms")
    
    // Test Case 12: 3-sized subsets
    println("\nTest Case 12: All 3-sized subsets of [1, 2, 3, 4, 5]")
    val nums12 = intArrayOf(1, 2, 3, 4, 5)
    val result12 = generator.kSizedSubsets(nums12, 3)
    println("Expected count: 10 (5 choose 3)")
    println("Actual count: ${result12.size}")
    println("Subsets: $result12")
}
