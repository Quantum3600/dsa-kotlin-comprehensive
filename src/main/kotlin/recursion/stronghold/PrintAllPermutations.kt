/**
 * ============================================================================
 * PROBLEM: Print All Permutations
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Stronghold
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of distinct integers, return all possible permutations.
 * A permutation is an arrangement of all elements in a specific order.
 * 
 * The order of permutations in the result doesn't matter, but each permutation
 * must contain all elements exactly once.
 * 
 * INPUT FORMAT:
 * - An array of distinct integers:  nums = [1, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - A list of lists containing all permutations
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 6
 * - -10 <= nums[i] <= 10
 * - All integers in nums are unique
 * 
 * EXAMPLES:
 * Input: nums = [1, 2, 3]
 * Output:  [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
 * 
 * Input:  nums = [0, 1]
 * Output: [[0,1], [1,0]]
 * 
 * Input: nums = [1]
 * Output: [[1]]
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of filling n positions with n distinct numbers: 
 * - Position 1: n choices
 * - Position 2: n-1 choices (one already used)
 * - Position 3: n-2 choices
 * - ...
 * - Total: n!  permutations
 * 
 * KEY INSIGHT:
 * At each step, try placing each unused number and recursively
 * generate permutations for remaining positions.
 * 
 * VISUAL EXAMPLE (nums = [1, 2, 3]):
 * ```
 *                           []
 *                    /       |       \
 *                  [1]      [2]      [3]
 *                 /  \      / \      / \
 *            [1,2][1,3] [2,1][2,3] [3,1][3,2]
 *               |    |    |    |    |    |
 *          [1,2,3][1,3,2][2,1,3][2,3,1][3,1,2][3,2,1]
 * 
 * Each path from root to leaf = one permutation
 * Total permutations = 3! = 6
 * ```
 * 
 * MULTIPLE APPROACHES:
 * 
 * APPROACH 1: BACKTRACKING WITH USED ARRAY
 * - Track which elements are used (boolean array)
 * - At each step, try all unused elements
 * - Mark as used → Recurse → Unmark (backtrack)
 * 
 * APPROACH 2: SWAPPING
 * - Swap current element with each position
 * - Recursively generate permutations for rest
 * - Swap back (backtrack)
 * - No extra space for tracking used elements
 * 
 * APPROACH 3: ITERATIVE
 * - Start with single permutation [[first]]
 * - For each remaining element, insert into all positions
 * - Example: [1] → [2,1], [1,2] → [3,2,1], [2,3,1], [2,1,3], etc.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n!  × n)
 * - Number of permutations: n! 
 * - Building each permutation: O(n)
 * - Total: O(n! × n)
 * 
 * WHY n! : 
 * - First position: n choices
 * - Second position: n-1 choices
 * - Third position: n-2 choices
 * - ... 
 * - Total: n × (n-1) × (n-2) × ... × 1 = n!
 * 
 * FACTORIAL GROWTH:
 * - n=3: 6 permutations
 * - n=4: 24 permutations
 * - n=5: 120 permutations
 * - n=6: 720 permutations
 * - n=10: 3,628,800 permutations! 
 * 
 * SPACE COMPLEXITY: O(n!  × n)
 * - Result storage: n! permutations, each of size n
 * - Recursion stack: O(n) depth
 * - Current permutation: O(n)
 * - Used array: O(n)
 * - Total: O(n! × n)
 * 
 * ============================================================================
 * APPROACH COMPARISON
 * ============================================================================
 * 
 * APPROACH 1: Backtracking with Used Array (Implemented)
 * Time: O(n!  × n), Space: O(n! × n)
 * ✅ Easy to understand
 * ✅ Clear separation of used/unused
 * ✅ Easy to extend for variations
 * ❌ Extra O(n) space for used array
 * 
 * APPROACH 2: Swapping
 * Time: O(n!  × n), Space: O(n! × n)
 * ✅ No extra used array needed
 * ✅ In-place swapping
 * ❌ Modifies input array
 * ❌ Harder to understand for beginners
 * 
 * APPROACH 3: Iterative
 * Time: O(n! × n²), Space: O(n! × n)
 * ✅ No recursion
 * ❌ Slower (extra copy operations)
 * ❌ More complex code
 * 
 * ============================================================================
 * EDGE CASES & COMMON MISTAKES
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single element:  [[element]]
 * 2. Two elements: 2 permutations
 * 3. Empty array: [[]] (one empty permutation)
 * 
 * COMMON MISTAKES: 
 * 1. Not backtracking → Used array not reset properly
 * 2. Forgetting to create new list → All permutations point to same list
 * 3. Wrong base case → Permutation added before completion
 * 4. Not handling duplicates → If input has duplicates (different problem)
 * 5. Confusing with subsets/combinations
 * 
 * PERMUTATION vs SUBSET vs COMBINATION:
 * - Permutation: ALL elements, ORDER matters
 *   [1,2,3]:  6 permutations
 * - Subset: ANY number of elements, order doesn't matter
 *   [1,2,3]: 8 subsets
 * - Combination: SPECIFIC number (k) elements, order doesn't matter
 *   [1,2,3] choose 2: 3 combinations
 * 
 * FACTORIAL vs EXPONENTIAL vs POLYNOMIAL:
 * - Permutations: O(n!) - Factorial
 * - Subsets: O(2^n) - Exponential
 * - Combinations C(n,k): O(n!/(k!(n-k)!)) - Between polynomial and exponential
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Permutations II (Medium) - With duplicate elements
 * 2. Next Permutation (Medium) - Find next lexicographic permutation
 * 3. Permutation Sequence (Hard) - Find kth permutation
 * 4. Letter Case Permutation (Medium) - Different constraint
 * 5. Subsets (Medium) - Related backtracking problem
 * 6. Combinations (Medium) - k-sized selections
 * 
 * ============================================================================
 * LEARNING RESOURCES
 * ============================================================================
 * 
 * CONCEPTS DEMONSTRATED:
 * - Backtracking algorithm
 * - Factorial time complexity
 * - Recursion tree analysis
 * - State tracking (used array)
 * - In-place algorithms (swapping approach)
 * 
 * PRACTICE PROGRESSION:
 * 1. Master this problem with
