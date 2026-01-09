package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Subsequence Sum K
 * DIFFICULTY: Medium
 * CATEGORY: Recursion / Subsequences
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of integers and a target sum K, find all subsequences of the
 * array whose elements sum up to K.
 *
 * A subsequence is a sequence that can be derived from another sequence by
 * deleting some or no elements without changing the order of the remaining
 * elements.
 *
 * INPUT FORMAT:
 * - An integer array `arr`
 * - An integer `k` (target sum)
 *
 * OUTPUT FORMAT:
 * - A list of lists, where each inner list represents a subsequence summing to K.
 *
 * CONSTRAINTS:
 * - 1 <= arr.length <= 20 (typically small for recursion)
 * - 1 <= arr[i] <= 1000
 * - 1 <= k <= 10^5
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 *
 * INTUITION:
 * The problem asks for all subsequences with a specific sum. Since we need to
 * explore all possible subsequences to check their sum, recursion is a natural
 * choice. Specifically, for each element in the array, we have two choices:
 * 1. Pick the element and add it to our current subsequence.
 * 2. Don't pick the element and move to the next.
 *
 * This "Pick / Don't Pick" pattern allows us to generate the power set (all
 * subsequences) and filter for those that satisfy the condition (sum == k).
 *
 * ALGORITHM STEPS:
 * 1. Define a recursive function `find(index, currentSubsequence, currentSum)`.
 * 2. Base Case: If `index` reaches the end of the array:
 *    - Check if `currentSum == k`.
 *    - If yes, add a copy of `currentSubsequence` to the result list.
 *    - Return.
 * 3. Recursive Step (Pick):
 *    - Add `arr[index]` to `currentSubsequence`.
 *    - Add `arr[index]` to `currentSum`.
 *    - Call `find(index + 1, ...)`
 *    - Backtrack: Remove `arr[index]` from `currentSubsequence` and subtract from `currentSum`.
 * 4. Recursive Step (Don't Pick):
 *    - Call `find(index + 1, ...)` without adding the current element.
 *
 * VISUAL EXAMPLE:
 * arr = [1, 2, 1], k = 2
 *
 *                   f(0, [], 0)
 *                  /           \
 *       Pick 1    /             \ Don't Pick
 *          f(1, [1], 1)       f(1, [], 0)
 *          /      \            /      \
 *    Pick 2 /      \ No 2   Pick 2 /      \ No 2
 * f(2,[1,2],3) f(2,[1],1) f(2,[2],2)  f(2,[],0)
 *      |          |          |          |
 *     Stop       ...       MATCH       ...
 *
 * ALTERNATIVE APPROACHES:
 * - Bit Manipulation: Iterate from 0 to 2^n - 1, using bits to decide inclusion.
 *   This is iterative but essentially does the same work (O(2^n)).
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 *
 * TIME COMPLEXITY: O(2^n * k) or O(2^n)
 * - For every element, we have 2 choices (pick or don't pick), leading to O(2^n) recursion nodes.
 * - Copying the data structure into the result takes linear time proportional to the length of the subsequence (k is sometimes used to denote length or average length).
 *
 * SPACE COMPLEXITY: O(n)
 * - The recursion stack space is O(n) (depth of the tree).
 * - We also store the current subsequence which takes O(n) space.
 * - Note: Output space is not typically counted in auxiliary space complexity.
 */

class SubsequenceSumK {

    /**
     * Finds all subsequences that sum up to K.
     *
     * @param nums The input array of integers.
     * @param k The target sum.
     * @return A list of lists containing the subsequences.
     */
    fun solve(nums: IntArray, k: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        val currentSubsequence = ArrayList<Int>()
        findSubsequences(0, nums, k, currentSubsequence, 0, result)
        return result
    }

    /**
     * Recursive helper function to find subsequences.
     *
     * @param index Current index in the array we are considering.
     * @param nums The input array.
     * @param target The target sum.
     * @param current The current subsequence being built.
     * @param currentSum The sum of elements in the current subsequence.
     * @param result The list to store valid subsequences.
     */
    private fun findSubsequences(
        index: Int,
        nums: IntArray,
        target: Int,
        current: ArrayList<Int>,
        currentSum: Int,
        result: MutableList<List<Int>>
    ) {
        // Base case: If we have reached the end of the array
        if (index == nums.size) {
            if (currentSum == target) {
                // Add a copy of the current subsequence to the result
                result.add(ArrayList(current))
            }
            return
        }

        // Optimization: If currentSum already exceeds target and numbers are positive, we could prune.
        // However, problem statement doesn't specify non-negative only, so we stick to general logic unless constrained.
        // Assuming general integers (could be negative), we can't prune easily based on sum > target.

        // Logic: Pick
        current.add(nums[index])
        findSubsequences(index + 1, nums, target, current, currentSum + nums[index], result)

        // Backtrack: Remove the last added element to explore the "Don't Pick" path
        current.removeAt(current.size - 1)

        // Logic: Don't Pick
        findSubsequences(index + 1, nums, target, current, currentSum, result)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 *
 * Input: arr = [1, 2, 1], k = 2
 *
 * 1. Start at index 0. Current: [], Sum: 0.
 * 2. Pick arr[0]=1. Current: [1], Sum: 1.
 *    2.1. Recurse index 1.
 *    2.2. Pick arr[1]=2. Current: [1, 2], Sum: 3.
 *         2.2.1. Recurse index 2.
 *         2.2.2. Pick arr[2]=1. Current: [1, 2, 1], Sum: 4. Index=3 -> End. Sum!=k. Return.
 *         2.2.3. Backtrack (remove 1). Don't Pick arr[2]. Current: [1, 2], Sum: 3. Index=3 -> End. Sum!=k. Return.
 *    2.3. Backtrack (remove 2). Don't Pick arr[1]. Current: [1], Sum: 1.
 *         2.3.1. Recurse index 2.
 *         2.3.2. Pick arr[2]=1. Current: [1, 1], Sum: 2.
 *                - Index=3 -> End. Sum==k! Add [1, 1] to result. Return.
 *         2.3.3. Don't Pick arr[2]. Current: [1], Sum: 1. Index=3 -> End. Sum!=k. Return.
 * 3. Backtrack (remove 1). Don't Pick arr[0]. Current: [], Sum: 0.
 *    3.1. Recurse index 1.
 *    3.2. Pick arr[1]=2. Current: [2], Sum: 2.
 *         - Recurse index 2... eventually finds [2] matches k=2.
 *
 * Result: [[1, 1], [2]]
 */

fun main() {
    val solver = SubsequenceSumK()

    // Test Case 1: Standard case
    println("Test Case 1: [1, 2, 1], k=2")
    val input1 = intArrayOf(1, 2, 1)
    val result1 = solver.solve(input1, 2)
    println("Result: $result1")
    // Expected: [[1, 1], [2]] (Order might vary depending on implementation, but contents same)

    // Test Case 2: No solution
    println("\nTest Case 2: [1, 2, 3], k=7")
    val input2 = intArrayOf(1, 2, 3)
    val result2 = solver.solve(input2, 7)
    println("Result: $result2")
    // Expected: []

    // Test Case 3: Empty array
    println("\nTest Case 3: [], k=0")
    val input3 = intArrayOf()
    val result3 = solver.solve(input3, 0)
    println("Result: $result3")
    // Expected: [[]] (Empty subsequence sums to 0)

    // Test Case 4: Zero elements involved
    println("\nTest Case 4: [3, 0, 1], k=3")
    val input4 = intArrayOf(3, 0, 1)
    val result4 = solver.solve(input4, 3)
    println("Result: $result4")
    // Expected: [[3], [3, 0]] - depending on if we treat 0 as unique element in subsequence logic

    // Test Case 5: Negative numbers
    println("\nTest Case 5: [4, -1, 1], k=4")
    val input5 = intArrayOf(4, -1, 1)
    val result5 = solver.solve(input5, 4)
    println("Result: $result5")
    // Expected: [[4], [4, -1, 1]]
}
