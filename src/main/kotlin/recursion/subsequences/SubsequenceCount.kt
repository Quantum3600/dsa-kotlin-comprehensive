package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Subsequence Count
 * DIFFICULTY: Medium
 * CATEGORY: Recursion / Subsequences
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of integers and a target sum K, count the number of subsequences
 * of the array whose elements sum up to K.
 *
 * INPUT FORMAT:
 * - An integer array `arr`
 * - An integer `k` (target sum)
 *
 * OUTPUT FORMAT:
 * - An integer representing the count of valid subsequences.
 *
 * CONSTRAINTS:
 * - 1 <= arr.length <= 20 (for simple recursion)
 * - 1 <= arr[i] <= 1000
 * - 1 <= k <= 10^5
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 *
 * INTUITION:
 * Similar to finding all subsequences, we use the "Pick / Don't Pick" pattern.
 * Instead of storing the subsequences, the recursive function returns the count
 * of valid subsequences found in its subtree.
 *
 * ALGORITHM STEPS:
 * 1. Define recursive function `count(index, currentSum)`.
 * 2. Base Case: If `index` == length of array:
 *    - Return 1 if `currentSum == k` else 0.
 * 3. Recursive Step:
 *    - `left = count(index + 1, currentSum + arr[index])` (Pick)
 *    - `right = count(index + 1, currentSum)` (Don't Pick)
 *    - Return `left + right`.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(2^n)
 * - Space Complexity: O(n) for recursion stack.
 */

class SubsequenceCount {

    /**
     * Counts subsequences that sum up to K.
     *
     * @param nums The input array.
     * @param k The target sum.
     * @return The count of subsequences.
     */
    fun solve(nums: IntArray, k: Int): Int {
        return countSubsequences(0, nums, k, 0)
    }

    private fun countSubsequences(index: Int, nums: IntArray, target: Int, currentSum: Int): Int {
        // Base case
        if (index == nums.size) {
            return if (currentSum == target) 1 else 0
        }

        // Pick
        val l = countSubsequences(index + 1, nums, target, currentSum + nums[index])

        // Don't Pick
        val r = countSubsequences(index + 1, nums, target, currentSum)

        return l + r
    }
}

fun main() {
    val solver = SubsequenceCount()

    // Test Case 1
    println("Test Case 1: [1, 2, 1], k=2")
    println("Count: " + solver.solve(intArrayOf(1, 2, 1), 2))
    // Expected: 2 ([1, 1], [2])

    // Test Case 2
    println("\nTest Case 2: [1, 2, 3], k=3")
    println("Count: " + solver.solve(intArrayOf(1, 2, 3), 3))
    // Expected: 2 ([1, 2], [3])

    // Test Case 3: [1, 1, 1], k=2
    println("\nTest Case 3: [1, 1, 1], k=2")
    println("Count: " + solver.solve(intArrayOf(1, 1, 1), 2))
    // Expected: 3 (Index 0+1, 0+2, 1+2)
}
