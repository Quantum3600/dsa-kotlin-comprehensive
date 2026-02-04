package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Print All Subsequences with Sum K
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers and a target sum K, print all subsequences
 * of the array that have a sum equal to K.
 * 
 * A subsequence is a sequence that can be derived from the array by deleting
 * some or no elements without changing the order of the remaining elements.
 * 
 * INPUT FORMAT:
 * - An array of integers: [1, 2, 3]
 * - A target sum K: 3
 * 
 * OUTPUT FORMAT:
 * - List of all subsequences with sum K
 * Example: [[1, 2], [3]]
 * 
 * CONSTRAINTS:
 * - 1 <= array.size <= 20
 * - -100 <= array[i] <= 100
 * - -1000 <= K <= 1000
 * 
 * EXAMPLES:
 * Input: arr = [1, 2, 1], K = 2
 * Output: [[1, 1], [2]]
 * 
 * Input: arr = [1, 2, 3], K = 3
 * Output: [[1, 2], [3]]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For every element in the array, we have two choices:
 * 1. Include it in the current subsequence
 * 2. Exclude it from the current subsequence
 * 
 * This creates a binary tree of decisions. At each node, we make one of these
 * choices and recursively process the remaining elements.
 * 
 * ALGORITHM STEPS:
 * 1. Start with index 0 and empty subsequence
 * 2. At each index, we have two branches:
 *    - Pick: Add current element to subsequence and reduce target sum
 *    - Not Pick: Skip current element, keep target sum same
 * 3. Base case: When we reach end of array
 *    - If sum equals K, we found a valid subsequence
 * 4. Backtrack: Remove last added element before returning
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 2, 1], K = 2
 * 
 *                     []
 *                    /  \
 *                 [1]    []
 *                /  \    /  \
 *            [1,2] [1] [2]  []
 *             / \   |   |    |
 *        [1,2,1][1,2][1,1][2,1][1][]
 * 
 * Check sum at leaf nodes:
 * - [1,1] = 2 ✓
 * - [2] = 2 ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Iterative with bit masking (2^n combinations)
 * 2. Dynamic Programming (if only count is needed)
 * 3. Pruning: Stop exploring if current sum exceeds K (optimization)
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n)
 * - At each index, we make 2 recursive calls (pick/not pick)
 * - Total recursive calls form a binary tree of height n
 * - Total nodes in tree = 2^0 + 2^1 + ... + 2^n = 2^(n+1) - 1 ≈ O(2^n)
 * - Each leaf node may require O(n) to copy the subsequence
 * - Overall: O(n * 2^n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth: O(n) - maximum depth of recursion tree
 * - Temporary list storage: O(n) - stores one subsequence at a time
 * - Output storage not counted in auxiliary space
 * - No extra data structures needed
 */

class SubsequenceSumK {
    
    /**
     * Find all subsequences with sum equal to K
     * @param arr Input array
     * @param k Target sum
     * @return List of all valid subsequences
     */
    fun findSubsequences(arr: IntArray, k: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()  // Stores all valid subsequences
        val current = mutableListOf<Int>()       // Stores current subsequence being built
        
        // Start recursion from index 0 with target sum k
        findSubsequencesHelper(arr, 0, k, current, result)
        
        return result
    }
    
    /**
     * Recursive helper function to explore all subsequences
     * @param arr Input array
     * @param index Current position in array
     * @param targetSum Remaining sum needed
     * @param current Current subsequence being built
     * @param result Collection of all valid subsequences
     */
    private fun findSubsequencesHelper(
        arr: IntArray,
        index: Int,
        targetSum: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASE: Reached end of array
        if (index == arr.size) {
            // If remaining sum is 0, we found a valid subsequence
            if (targetSum == 0) {
                result.add(ArrayList(current))  // Add copy of current list
            }
            return
        }
        
        // RECURSIVE CASE 1: Pick current element
        // Add element to current subsequence
        current.add(arr[index])
        // Recurse with reduced target sum
        findSubsequencesHelper(arr, index + 1, targetSum - arr[index], current, result)
        // Backtrack: Remove the added element
        current.removeAt(current.size - 1)
        
        // RECURSIVE CASE 2: Don't pick current element
        // Skip current element and move to next
        findSubsequencesHelper(arr, index + 1, targetSum, current, result)
    }
    
    /**
     * Optimized version with pruning
     * Stops exploring branches where sum already exceeds target (only for positive numbers)
     */
    fun findSubsequencesOptimized(arr: IntArray, k: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        findSubsequencesOptimizedHelper(arr, 0, k, current, result)
        
        return result
    }
    
    private fun findSubsequencesOptimizedHelper(
        arr: IntArray,
        index: Int,
        targetSum: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // BASE CASE
        if (index == arr.size) {
            if (targetSum == 0) {
                result.add(ArrayList(current))
            }
            return
        }
        
        // PRUNING: If all elements are positive and targetSum < 0, stop exploring
        // This optimization works only for arrays with all positive numbers
        if (targetSum < 0 && arr.all { it > 0 }) {
            return
        }
        
        // PICK current element
        current.add(arr[index])
        findSubsequencesOptimizedHelper(arr, index + 1, targetSum - arr[index], current, result)
        current.removeAt(current.size - 1)
        
        // DON'T PICK current element
        findSubsequencesOptimizedHelper(arr, index + 1, targetSum, current, result)
    }
    
    /**
     * Print only ONE subsequence with sum K
     * Returns true if found, false otherwise
     */
    fun printOneSubsequence(arr: IntArray, k: Int): Boolean {
        val current = mutableListOf<Int>()
        return printOneHelper(arr, 0, k, current)
    }
    
    private fun printOneHelper(
        arr: IntArray,
        index: Int,
        targetSum: Int,
        current: MutableList<Int>
    ): Boolean {
        // BASE CASE
        if (index == arr.size) {
            if (targetSum == 0) {
                println(current)  // Print the subsequence
                return true       // Signal that we found one
            }
            return false
        }
        
        // PICK current element
        current.add(arr[index])
        if (printOneHelper(arr, index + 1, targetSum - arr[index], current)) {
            return true  // If found in this branch, stop searching
        }
        current.removeAt(current.size - 1)
        
        // DON'T PICK current element
        if (printOneHelper(arr, index + 1, targetSum, current)) {
            return true  // If found in this branch, stop searching
        }
        
        return false  // Not found in either branch
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: arr = [1, 2, 1], K = 2
 * 
 * Initial Call: findSubsequencesHelper(arr, 0, 2, [], result)
 * 
 * Step 1: index=0, arr[0]=1, targetSum=2
 *   Pick 1: current=[1], targetSum=1
 *     Step 2: index=1, arr[1]=2, targetSum=1
 *       Pick 2: current=[1,2], targetSum=-1
 *         Step 3: index=2, arr[2]=1, targetSum=-1
 *           Pick 1: current=[1,2,1], targetSum=-2
 *             index=3: targetSum≠0, return
 *           Don't pick: current=[1,2], targetSum=-1
 *             index=3: targetSum≠0, return
 *       Don't pick 2: current=[1], targetSum=1
 *         Step 3: index=2, arr[2]=1, targetSum=1
 *           Pick 1: current=[1,1], targetSum=0
 *             index=3: targetSum=0 ✓ Add [1,1] to result
 *           Don't pick: current=[1], targetSum=1
 *             index=3: targetSum≠0, return
 *   Don't pick 1: current=[], targetSum=2
 *     Step 2: index=1, arr[1]=2, targetSum=2
 *       Pick 2: current=[2], targetSum=0
 *         Step 3: index=2, arr[2]=1, targetSum=0
 *           index=3: targetSum=0 ✓ But current=[2,1], skip
 *           Don't pick: current=[2], targetSum=0
 *             index=3: targetSum=0 ✓ Add [2] to result
 *       Don't pick 2: current=[], targetSum=2
 *         Step 3: index=2, arr[2]=1, targetSum=2
 *           Pick 1: current=[1], targetSum=1
 *             index=3: targetSum≠0, return
 *           Don't pick: current=[], targetSum=2
 *             index=3: targetSum≠0, return
 * 
 * Final Result: [[1,1], [2]]
 */

fun main() {
    val solver = SubsequenceSumK()
    
    // Test Case 1: Normal case with multiple subsequences
    println("Test Case 1: arr = [1, 2, 1], K = 2")
    val result1 = solver.findSubsequences(intArrayOf(1, 2, 1), 2)
    println("All subsequences: $result1")  // Expected: [[1, 1], [2]]
    println()
    
    // Test Case 2: Single element equals target
    println("Test Case 2: arr = [1, 2, 3], K = 3")
    val result2 = solver.findSubsequences(intArrayOf(1, 2, 3), 3)
    println("All subsequences: $result2")  // Expected: [[1, 2], [3]]
    println()
    
    // Test Case 3: No valid subsequence
    println("Test Case 3: arr = [1, 2, 3], K = 10")
    val result3 = solver.findSubsequences(intArrayOf(1, 2, 3), 10)
    println("All subsequences: $result3")  // Expected: []
    println()
    
    // Test Case 4: Empty array
    println("Test Case 4: arr = [], K = 0")
    val result4 = solver.findSubsequences(intArrayOf(), 0)
    println("All subsequences: $result4")  // Expected: [[]]
    println()
    
    // Test Case 5: With negative numbers
    println("Test Case 5: arr = [1, -1, 2], K = 2")
    val result5 = solver.findSubsequences(intArrayOf(1, -1, 2), 2)
    println("All subsequences: $result5")  // Expected: [[1, -1, 2], [2]]
    println()
    
    // Test Case 6: Print only one subsequence
    println("Test Case 6: Print one subsequence for arr = [1, 2, 1], K = 2")
    val found = solver.printOneSubsequence(intArrayOf(1, 2, 1), 2)
    println("Found: $found")  // Expected: true, prints [1, 1]
    println()
    
    // Test Case 7: Large sum with small numbers
    println("Test Case 7: arr = [2, 3, 5], K = 8")
    val result7 = solver.findSubsequences(intArrayOf(2, 3, 5), 8)
    println("All subsequences: $result7")  // Expected: [[2, 3, 5], [3, 5]]
    println()
}
