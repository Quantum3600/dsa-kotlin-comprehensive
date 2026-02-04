package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Subset Sum II (Print Unique Subsets)
 * DIFFICULTY: Medium
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array that may contain duplicates, return all possible unique subsets.
 * The solution set must not contain duplicate subsets.
 * 
 * INPUT FORMAT:
 * - An array of integers (may contain duplicates): [1, 2, 2]
 * 
 * OUTPUT FORMAT:
 * - List of all unique subsets (can be in any order)
 * Example: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 * 
 * CONSTRAINTS:
 * - 1 <= array.size <= 10
 * - -10 <= array[i] <= 10
 * 
 * EXAMPLES:
 * Input: arr = [1, 2, 2]
 * Output: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 * 
 * Input: arr = [0]
 * Output: [[], [0]]
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The challenge is handling duplicates. If we use simple pick/not-pick,
 * we'll get duplicate subsets like [1,2] appearing multiple times.
 * 
 * KEY INSIGHT:
 * Sort the array first! Then, when we choose NOT to pick an element,
 * skip all its duplicates. This prevents generating the same subset twice.
 * 
 * ALGORITHM STEPS:
 * 1. Sort the array to group duplicates together
 * 2. Use backtracking to generate subsets
 * 3. For each position, pick the element and recurse
 * 4. When backtracking, skip all duplicate elements
 * 5. Add current subset to result before exploring further
 * 
 * DUPLICATE HANDLING:
 * After trying to pick arr[i], when we backtrack and skip it,
 * we must also skip arr[i+1], arr[i+2]... if they equal arr[i].
 * 
 * VISUAL EXAMPLE:
 * Array: [1, 2, 2] (already sorted)
 * 
 *                      []
 *                    /  
 *                  [1]
 *                 /   
 *              [1,2]
 *              /   
 *          [1,2,2]
 * 
 * When backtracking from [1,2], we add 2.
 * When backtracking from [1], we skip the first 2 but explore the second:
 * 
 *                      []
 *                    /   \
 *                  [1]    [2]    (skip first 2, take second)
 *                 /       /
 *              [1,2]    [2,2]
 *              /    
 *          [1,2,2]
 * 
 * But wait! We need a different approach. Let me reconsider...
 * 
 * BETTER APPROACH:
 * At each index, we decide how many times to include the current element
 * (considering duplicates). Then skip to the next different element.
 * 
 * OR use the "index-based" approach where at each level, we iterate through
 * all remaining elements, but skip duplicates at the same recursion level.
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Use a Set to store unique subsets (less efficient)
 * 2. Iterative approach: For each element, add to existing subsets
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(2^n * n)
 * - Sorting: O(n log n)
 * - Generating subsets: O(2^n) in worst case (no duplicates)
 * - Copying each subset: O(n)
 * - Overall: O(n log n + 2^n * n) = O(2^n * n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion depth: O(n)
 * - Temporary subset storage: O(n)
 * - Output storage not counted
 */

class SubsetSumII {
    
    /**
     * Find all unique subsets
     * @param arr Input array (may contain duplicates)
     * @return List of all unique subsets
     */
    fun subsetsWithDup(arr: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        // Sort to handle duplicates
        arr.sort()
        
        // Generate subsets starting from index 0
        backtrack(arr, 0, current, result)
        
        return result
    }
    
    /**
     * Backtracking helper with duplicate handling
     * @param arr Sorted input array
     * @param start Starting index for this recursion level
     * @param current Current subset being built
     * @param result Collection of all unique subsets
     */
    private fun backtrack(
        arr: IntArray,
        start: Int,
        current: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        // Add current subset to result (even if empty)
        result.add(ArrayList(current))
        
        // Try adding each element starting from 'start'
        for (i in start until arr.size) {
            // Skip duplicates at the same recursion level
            // If arr[i] == arr[i-1] and we didn't pick arr[i-1], skip arr[i]
            if (i > start && arr[i] == arr[i - 1]) {
                continue
            }
            
            // Pick current element
            current.add(arr[i])
            
            // Recurse with next index
            backtrack(arr, i + 1, current, result)
            
            // Backtrack: remove the element
            current.removeAt(current.size - 1)
        }
    }
    
    /**
     * Alternative: Iterative approach
     * For each unique element, add it to existing subsets
     */
    fun subsetsWithDupIterative(arr: IntArray): List<List<Int>> {
        arr.sort()
        
        val result = mutableListOf<List<Int>>()
        result.add(emptyList())  // Start with empty subset
        
        var i = 0
        while (i < arr.size) {
            val currentNum = arr[i]
            
            // Count duplicates
            var count = 0
            while (i < arr.size && arr[i] == currentNum) {
                count++
                i++
            }
            
            // Get current size of result
            val currentSize = result.size
            
            // For each existing subset
            for (j in 0 until currentSize) {
                val existingSubset = ArrayList(result[j])
                
                // Add currentNum 'k' times (k = 1 to count)
                for (k in 1..count) {
                    existingSubset.add(currentNum)
                    result.add(ArrayList(existingSubset))
                }
            }
        }
        
        return result
    }
    
    /**
     * Using Set to handle duplicates (less efficient but simpler)
     */
    fun subsetsWithDupUsingSet(arr: IntArray): List<List<Int>> {
        val result = mutableSetOf<List<Int>>()
        val current = mutableListOf<Int>()
        
        arr.sort()
        generateWithSet(arr, 0, current, result)
        
        return result.toList()
    }
    
    private fun generateWithSet(
        arr: IntArray,
        index: Int,
        current: MutableList<Int>,
        result: MutableSet<List<Int>>
    ) {
        if (index == arr.size) {
            result.add(ArrayList(current))
            return
        }
        
        // Pick
        current.add(arr[index])
        generateWithSet(arr, index + 1, current, result)
        current.removeAt(current.size - 1)
        
        // Not pick
        generateWithSet(arr, index + 1, current, result)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: arr = [1, 2, 2] (after sorting)
 * 
 * Backtracking Tree:
 * 
 * backtrack(arr, 0, [], result)
 * │ Add [] to result → result = [[]]
 * │
 * ├─ i=0: arr[0]=1
 * │  │ current = [1]
 * │  │ backtrack(arr, 1, [1], result)
 * │  │ │ Add [1] to result → result = [[], [1]]
 * │  │ │
 * │  │ ├─ i=1: arr[1]=2
 * │  │ │  │ current = [1,2]
 * │  │ │  │ backtrack(arr, 2, [1,2], result)
 * │  │ │  │ │ Add [1,2] to result → result = [[], [1], [1,2]]
 * │  │ │  │ │
 * │  │ │  │ ├─ i=2: arr[2]=2
 * │  │ │  │ │  │ current = [1,2,2]
 * │  │ │  │ │  │ backtrack(arr, 3, [1,2,2], result)
 * │  │ │  │ │  │ │ Add [1,2,2] to result → result = [[], [1], [1,2], [1,2,2]]
 * │  │ │  │ │  │ │ No more elements, return
 * │  │ │  │ │  │ backtrack, remove 2 → current = [1,2]
 * │  │ │  │ │
 * │  │ │  │ backtrack, remove 2 → current = [1]
 * │  │ │
 * │  │ ├─ i=2: arr[2]=2
 * │  │ │  Skip! (i > start && arr[2] == arr[1])
 * │  │ │
 * │  │ backtrack, remove 1 → current = []
 * │
 * ├─ i=1: arr[1]=2
 * │  │ current = [2]
 * │  │ backtrack(arr, 2, [2], result)
 * │  │ │ Add [2] to result → result = [[], [1], [1,2], [1,2,2], [2]]
 * │  │ │
 * │  │ ├─ i=2: arr[2]=2
 * │  │ │  │ current = [2,2]
 * │  │ │  │ backtrack(arr, 3, [2,2], result)
 * │  │ │  │ │ Add [2,2] to result → result = [[], [1], [1,2], [1,2,2], [2], [2,2]]
 * │  │ │  │ │ No more elements, return
 * │  │ │  │ backtrack, remove 2 → current = [2]
 * │  │ │
 * │  │ backtrack, remove 2 → current = []
 * │
 * ├─ i=2: arr[2]=2
 * │  Skip! (i > start && arr[2] == arr[1])
 * 
 * Final Result: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 */

fun main() {
    val solver = SubsetSumII()
    
    // Test Case 1: Array with duplicates
    println("Test Case 1: arr = [1, 2, 2]")
    println("Backtracking: ${solver.subsetsWithDup(intArrayOf(1, 2, 2))}")
    println("Iterative: ${solver.subsetsWithDupIterative(intArrayOf(1, 2, 2))}")
    println("Expected: [[], [1], [1,2], [1,2,2], [2], [2,2]]")
    println()
    
    // Test Case 2: All duplicates
    println("Test Case 2: arr = [1, 1, 1]")
    println("Result: ${solver.subsetsWithDup(intArrayOf(1, 1, 1))}")
    println("Expected: [[], [1], [1,1], [1,1,1]]")
    println()
    
    // Test Case 3: No duplicates
    println("Test Case 3: arr = [1, 2, 3]")
    println("Result: ${solver.subsetsWithDup(intArrayOf(1, 2, 3))}")
    println("Expected: All 8 subsets")
    println()
    
    // Test Case 4: Single element
    println("Test Case 4: arr = [0]")
    println("Result: ${solver.subsetsWithDup(intArrayOf(0))}")
    println("Expected: [[], [0]]")
    println()
    
    // Test Case 5: With negative numbers
    println("Test Case 5: arr = [-1, 1, -1]")
    println("Result: ${solver.subsetsWithDup(intArrayOf(-1, 1, -1))}")
    println("Expected: Unique subsets with duplicates handled")
    println()
    
    // Test Case 6: Multiple duplicates
    println("Test Case 6: arr = [4, 4, 4, 1, 4]")
    println("Result: ${solver.subsetsWithDup(intArrayOf(4, 4, 4, 1, 4))}")
    println("Expected: Unique subsets")
    println()
    
    // Test Case 7: Comparison test
    println("Test Case 7: Verify all three methods give same result")
    val testArr = intArrayOf(2, 1, 2)
    val result1 = solver.subsetsWithDup(testArr.clone()).map { it.sorted() }.sortedBy { it.toString() }
    val result2 = solver.subsetsWithDupIterative(testArr.clone()).map { it.sorted() }.sortedBy { it.toString() }
    val result3 = solver.subsetsWithDupUsingSet(testArr.clone()).map { it.sorted() }.sortedBy { it.toString() }
    println("Backtracking: $result1")
    println("Iterative: $result2")
    println("Using Set: $result3")
    println("All equal: ${result1 == result2 && result2 == result3}")
}
