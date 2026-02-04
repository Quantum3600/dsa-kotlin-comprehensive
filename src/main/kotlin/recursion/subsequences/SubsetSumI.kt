package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Subset Sum I (Print All Subset Sums)
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, find all possible sums of subsets of the array.
 * Return the sums in sorted order.
 * 
 * A subset is any combination of elements from the array (including empty set).
 * 
 * INPUT FORMAT:
 * - An array of integers: [2, 3]
 * 
 * OUTPUT FORMAT:
 * - List of all possible subset sums in sorted order
 * Example: [0, 2, 3, 5]
 * 
 * CONSTRAINTS:
 * - 1 <= array.size <= 15
 * - 0 <= array[i] <= 10^4
 * 
 * EXAMPLES:
 * Input: arr = [2, 3]
 * Output: [0, 2, 3, 5]
 * Explanation:
 * - {} → 0
 * - {2} → 2
 * - {3} → 3
 * - {2, 3} → 5
 * 
 * Input: arr = [5, 2, 1]
 * Output: [0, 1, 2, 3, 5, 6, 7, 8]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of generating all subsets and then calculating their sums,
 * we can directly calculate sums while recursing. At each element:
 * - Pick it: Add to current sum
 * - Don't pick it: Keep current sum same
 * 
 * KEY INSIGHT:
 * We don't need to store the actual subsets, just their sums.
 * This saves memory compared to generating all subsets first.
 * 
 * ALGORITHM STEPS:
 * 1. Start with index 0 and sum 0
 * 2. At each index:
 *    - Make pick choice: recurse with sum + arr[index]
 *    - Make not-pick choice: recurse with same sum
 * 3. Base case: When index reaches array length, add current sum to result
 * 4. Sort the result before returning
 * 
 * VISUAL EXAMPLE:
 * Array: [2, 3]
 * 
 *                  (idx=0, sum=0)
 *                 /              \
 *         Pick 2                  Don't pick 2
 *      (idx=1, sum=2)           (idx=1, sum=0)
 *        /        \               /           \
 *   Pick 3    Skip 3         Pick 3       Skip 3
 * (sum=5)     (sum=2)       (sum=3)       (sum=0)
 * 
 * Leaf nodes: 5, 2, 3, 0
 * Sorted: [0, 2, 3, 5]
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Bit Masking: Generate all 2^n subsets using bits (0 to 2^n-1)
 * 2. Iterative: Use a list and for each element, double the list with new sums
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n + 2^n * log(2^n)) = O(2^n * n)
 * - Generating all sums: O(2^n) - binary tree with 2^n leaf nodes
 * - Sorting the result: O(2^n * log(2^n)) = O(2^n * n)
 * - Overall: O(2^n * n)
 * 
 * SPACE COMPLEXITY: O(2^n)
 * - Result list stores 2^n sums
 * - Recursion stack: O(n) depth
 * - Overall: O(2^n) for output storage
 */

class SubsetSumI {
    
    /**
     * Find all possible subset sums
     * @param arr Input array (non-negative integers)
     * @return List of all subset sums in sorted order
     */
    fun subsetSums(arr: IntArray): List<Int> {
        val result = mutableListOf<Int>()  // Stores all subset sums
        
        // Generate all sums starting from index 0 with sum 0
        generateSums(arr, 0, 0, result)
        
        // Sort the result before returning
        result.sort()
        
        return result
    }
    
    /**
     * Recursive helper to generate all subset sums
     * @param arr Input array
     * @param index Current position in array
     * @param currentSum Sum accumulated so far
     * @param result Collection of all subset sums
     */
    private fun generateSums(
        arr: IntArray,
        index: Int,
        currentSum: Int,
        result: MutableList<Int>
    ) {
        // BASE CASE: Processed all elements
        if (index == arr.size) {
            result.add(currentSum)  // Add the current subset sum
            return
        }
        
        // RECURSIVE CASE 1: Pick current element
        // Add current element to sum and recurse
        generateSums(arr, index + 1, currentSum + arr[index], result)
        
        // RECURSIVE CASE 2: Don't pick current element
        // Keep sum same and recurse
        generateSums(arr, index + 1, currentSum, result)
    }
    
    /**
     * Alternative: Iterative approach
     * For each element, create new sums by adding it to existing sums
     */
    fun subsetSumsIterative(arr: IntArray): List<Int> {
        // Start with sum 0 (empty subset)
        val sums = mutableListOf(0)
        
        // For each element in array
        for (num in arr) {
            // Get current size (important: size changes in loop)
            val currentSize = sums.size
            
            // For each existing sum, create a new sum by adding current number
            for (i in 0 until currentSize) {
                sums.add(sums[i] + num)
            }
        }
        
        // Sort and return
        sums.sort()
        return sums
    }
    
    /**
     * Bit manipulation approach
     * Use binary representation to generate all subsets
     */
    fun subsetSumsBitMask(arr: IntArray): List<Int> {
        val n = arr.size
        val result = mutableListOf<Int>()
        
        // There are 2^n possible subsets
        val totalSubsets = 1 shl n  // Same as 2^n
        
        // Generate each subset using its binary representation
        for (mask in 0 until totalSubsets) {
            var sum = 0
            
            // Check each bit in mask
            for (i in 0 until n) {
                // If i-th bit is set, include arr[i] in subset
                if ((mask and (1 shl i)) != 0) {
                    sum += arr[i]
                }
            }
            
            result.add(sum)
        }
        
        result.sort()
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: arr = [2, 3]
 * 
 * Recursion Tree:
 * 
 * generateSums(arr, 0, 0, result)
 * ├─ Pick arr[0]=2 → generateSums(arr, 1, 2, result)
 * │  ├─ Pick arr[1]=3 → generateSums(arr, 2, 5, result)
 * │  │  └─ Base case: Add 5 to result → result = [5]
 * │  └─ Skip arr[1] → generateSums(arr, 2, 2, result)
 * │     └─ Base case: Add 2 to result → result = [5, 2]
 * │
 * └─ Skip arr[0] → generateSums(arr, 1, 0, result)
 *    ├─ Pick arr[1]=3 → generateSums(arr, 2, 3, result)
 *    │  └─ Base case: Add 3 to result → result = [5, 2, 3]
 *    └─ Skip arr[1] → generateSums(arr, 2, 0, result)
 *       └─ Base case: Add 0 to result → result = [5, 2, 3, 0]
 * 
 * After sorting: [0, 2, 3, 5]
 * 
 * Iterative Approach Walkthrough:
 * Initial: sums = [0]
 * 
 * Process arr[0] = 2:
 *   Existing sums: [0]
 *   Add 2 to each: 0+2=2
 *   sums = [0, 2]
 * 
 * Process arr[1] = 3:
 *   Existing sums: [0, 2]
 *   Add 3 to each: 0+3=3, 2+3=5
 *   sums = [0, 2, 3, 5]
 * 
 * Result: [0, 2, 3, 5]
 */

fun main() {
    val solver = SubsetSumI()
    
    // Test Case 1: Basic case
    println("Test Case 1: arr = [2, 3]")
    println("Recursive: ${solver.subsetSums(intArrayOf(2, 3))}")
    println("Iterative: ${solver.subsetSumsIterative(intArrayOf(2, 3))}")
    println("Bit Mask: ${solver.subsetSumsBitMask(intArrayOf(2, 3))}")
    println("Expected: [0, 2, 3, 5]")
    println()
    
    // Test Case 2: Three elements
    println("Test Case 2: arr = [5, 2, 1]")
    println("Result: ${solver.subsetSums(intArrayOf(5, 2, 1))}")
    println("Expected: [0, 1, 2, 3, 5, 6, 7, 8]")
    println()
    
    // Test Case 3: Single element
    println("Test Case 3: arr = [3]")
    println("Result: ${solver.subsetSums(intArrayOf(3))}")
    println("Expected: [0, 3]")
    println()
    
    // Test Case 4: All same elements
    println("Test Case 4: arr = [1, 1, 1]")
    println("Result: ${solver.subsetSums(intArrayOf(1, 1, 1))}")
    println("Expected: [0, 1, 1, 1, 2, 2, 2, 3]")
    println()
    
    // Test Case 5: With zero
    println("Test Case 5: arr = [0, 1, 2]")
    println("Result: ${solver.subsetSums(intArrayOf(0, 1, 2))}")
    println("Expected: [0, 0, 1, 1, 2, 2, 3, 3]")
    println()
    
    // Test Case 6: Larger numbers
    println("Test Case 6: arr = [10, 20, 30]")
    println("Result: ${solver.subsetSums(intArrayOf(10, 20, 30))}")
    println("Expected: [0, 10, 20, 30, 30, 40, 50, 60]")
    println()
    
    // Test Case 7: Performance comparison
    println("Test Case 7: Performance comparison with larger array")
    val largeArr = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    
    var start = System.currentTimeMillis()
    val recursiveResult = solver.subsetSums(largeArr)
    var time = System.currentTimeMillis() - start
    println("Recursive: ${recursiveResult.size} sums in ${time}ms")
    
    start = System.currentTimeMillis()
    val iterativeResult = solver.subsetSumsIterative(largeArr)
    time = System.currentTimeMillis() - start
    println("Iterative: ${iterativeResult.size} sums in ${time}ms")
    
    start = System.currentTimeMillis()
    val bitMaskResult = solver.subsetSumsBitMask(largeArr)
    time = System.currentTimeMillis() - start
    println("Bit Mask: ${bitMaskResult.size} sums in ${time}ms")
}
