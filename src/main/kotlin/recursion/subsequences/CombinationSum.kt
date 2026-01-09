package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Combination Sum
 * DIFFICULTY: Medium
 * CATEGORY: Recursion / Backtracking
 * ============================================================================
 *
 * PROBLEM STATEMENT:
 * Given an array of distinct integers `candidates` and a target integer `target`,
 * return a list of all unique combinations of `candidates` where the chosen
 * numbers sum to `target`. You may return the combinations in any order.
 *
 * The same number may be chosen from `candidates` an unlimited number of times.
 * Two combinations are unique if the frequency of at least one of the chosen
 * numbers is different.
 *
 * INPUT FORMAT:
 * - An integer array `candidates` (distinct integers)
 * - An integer `target`
 *
 * OUTPUT FORMAT:
 * - A list of lists of integers
 *
 * CONSTRAINTS:
 * - 1 <= candidates.length <= 30
 * - 2 <= candidates[i] <= 40
 * - All elements of candidates are distinct.
 * - 1 <= target <= 40
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 *
 * INTUITION:
 * Since we can pick the same element multiple times, the "Pick" step in our
 * recursion should not increment the index. We only increment the index when
 * we decide "Don't Pick" (meaning we are done with this element entirely).
 *
 * ALGORITHM STEPS:
 * 1. Function `find(index, target, currentList)`.
 * 2. Base Cases:
 *    - If `index == candidates.size`:
 *      - If `target == 0`: Add currentList to result.
 *      - Return.
 * 3. Recursive Steps:
 *    - Pick: If `candidates[index] <= target`:
 *      - Add `candidates[index]` to `currentList`.
 *      - Call `find(index, target - candidates[index], currentList)`. (Stay at same index)
 *      - Backtrack: Remove last element.
 *    - Don't Pick:
 *      - Call `find(index + 1, target, currentList)`.
 *
 * COMPLEXITY ANALYSIS:
 * - Time Complexity: O(2^t * k) roughly, where t is target value (since min element is at least 1, max depth is target). Actually it's exponential.
 * - Space Complexity: O(target) for stack depth (in worst case if min element is 1).
 */

class CombinationSum {

    fun solve(candidates: IntArray, target: Int): List<List<Int>> {
        val result = ArrayList<List<Int>>()
        findCombinations(0, candidates, target, ArrayList(), result)
        return result
    }

    private fun findCombinations(
        index: Int,
        candidates: IntArray,
        target: Int,
        current: ArrayList<Int>,
        result: MutableList<List<Int>>
    ) {
        if (index == candidates.size) {
            if (target == 0) {
                result.add(ArrayList(current))
            }
            return
        }

        // Logic: Pick the element (if valid)
        if (candidates[index] <= target) {
            current.add(candidates[index])
            // Stay at same index because we can reuse the element
            findCombinations(index, candidates, target - candidates[index], current, result)
            current.removeAt(current.size - 1)
        }

        // Logic: Don't pick the element (move to next)
        findCombinations(index + 1, candidates, target, current, result)
    }
}

fun main() {
    val solver = CombinationSum()

    // Test Case 1: [2,3,6,7], target=7
    println("Test Case 1: [2,3,6,7], target=7")
    println(solver.solve(intArrayOf(2, 3, 6, 7), 7))
    // Expected: [[2,2,3], [7]]

    // Test Case 2: [2,3,5], target=8
    println("\nTest Case 2: [2,3,5], target=8")
    println(solver.solve(intArrayOf(2, 3, 5), 8))
    // Expected: [[2,2,2,2], [2,3,3], [3,5]]

    // Test Case 3: [2], target=1
    println("\nTest Case 3: [2], target=1")
    println(solver.solve(intArrayOf(2), 1))
    // Expected: []
}
