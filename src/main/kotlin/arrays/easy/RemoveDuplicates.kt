/**
 * ============================================================================
 * PROBLEM: Remove Duplicates from Sorted Array
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a sorted array of integers, remove the duplicates in-place such that each unique
 * element appears only once. Return the length of the array after removing duplicates.
 * The relative order of elements should be maintained.
 * 
 * Note: You must modify the array in-place with O(1) extra space. The elements after
 * the returned length don't matter.
 * 
 * INPUT FORMAT:
 * - A sorted array of integers: arr = [1, 1, 2, 2, 2, 3, 3]
 * 
 * OUTPUT FORMAT:
 * - An integer representing the count of unique elements: 3
 * - The array is modified to: [1, 2, 3, _, _, _, _] (first 3 elements are unique)
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * - Array is sorted in non-decreasing order
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Since the array is sorted, all duplicate elements will be adjacent. We can use
 * a two-pointer approach:
 * 1. One pointer (i) scans through the array
 * 2. Another pointer (j) marks the position where the next unique element should be placed
 * 
 * When we find a new unique element (arr[i] != arr[j]), we increment j and place
 * the unique element at position j.
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 1, 2, 2, 2, 3, 3]
 *        ↓  ↓
 *        j  i
 * 
 * Step 1: arr[i]=1 == arr[j]=1 → Skip
 * arr = [1, 1, 2, 2, 2, 3, 3]
 *        ↓     ↓
 *        j     i
 * 
 * Step 2: arr[i]=2 != arr[j]=1 → Move 2 to position j+1
 * arr = [1, 2, 2, 2, 2, 3, 3]
 *           ↓  ↓
 *           j  i
 * 
 * Step 3: arr[i]=2 == arr[j]=2 → Skip
 * arr = [1, 2, 2, 2, 2, 3, 3]
 *           ↓     ↓
 *           j     i
 * 
 * Step 4: arr[i]=2 == arr[j]=2 → Skip
 * arr = [1, 2, 2, 2, 2, 3, 3]
 *           ↓        ↓
 *           j        i
 * 
 * Step 5: arr[i]=3 != arr[j]=2 → Move 3 to position j+1
 * arr = [1, 2, 3, 2, 2, 3, 3]
 *              ↓     ↓
 *              j     i
 * 
 * Step 6: arr[i]=3 == arr[j]=3 → Skip
 * Final: [1, 2, 3, _, _, _, _]
 * Length: j+1 = 3
 * 
 * KEY INSIGHTS:
 * - Array is sorted, so duplicates are adjacent
 * - We only need to track one unique element at a time
 * - Two-pointer technique efficiently solves this in one pass
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pointer (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Using Set: O(n) time, O(n) space - Not optimal due to space
 * 3. Create new array: O(n) time, O(n) space - Violates in-place requirement
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through the array with pointer i
 * - Each element is visited exactly once
 * - Comparison and assignment operations are O(1)
 * - Total: O(n) where n is the array length
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using two integer pointers (i and j)
 * - No additional data structures
 * - Modification is done in-place
 * - Constant extra space regardless of input size
 * 
 * WHY THIS IS OPTIMAL:
 * We must examine every element at least once to identify duplicates,
 * so O(n) time is optimal. In-place modification with O(1) space is
 * the most space-efficient solution possible.
 * 
 * ============================================================================
 */

package arrays.easy

class RemoveDuplicates {
    
    /**
     * Removes duplicates from a sorted array in-place
     * 
     * Time: O(n), Space: O(1)
     * 
     * @param arr The sorted input array
     * @return The number of unique elements
     */
    fun removeDuplicates(arr: IntArray): Int {
        // Edge case: Empty array or single element
        if (arr.isEmpty()) return 0
        if (arr.size == 1) return 1
        
        // j points to the last position of unique elements
        // Start at 0 since first element is always unique
        var j = 0
        
        // i scans through the array starting from second element
        for (i in 1 until arr.size) {
            // If current element is different from the last unique element
            if (arr[i] != arr[j]) {
                // Move to next position for unique elements
                j++
                // Place the unique element at position j
                arr[j] = arr[i]
            }
            // If arr[i] == arr[j], it's a duplicate, skip it
        }
        
        // Return count of unique elements (j is 0-indexed, so add 1)
        return j + 1
    }
    
    /**
     * Alternative implementation using Kotlin idiomatic approach
     * This creates a new list but demonstrates Kotlin features
     * 
     * Time: O(n), Space: O(n)
     * Note: This doesn't modify in-place, included for comparison
     */
    fun removeDuplicatesKotlinWay(arr: IntArray): List<Int> {
        return arr.distinct() // Built-in Kotlin function
    }
    
    /**
     * Using a Set to remove duplicates while maintaining order
     * 
     * Time: O(n), Space: O(n)
     * Note: Useful when array is not sorted
     */
    fun removeDuplicatesUnsorted(arr: IntArray): List<Int> {
        val seen = mutableSetOf<Int>()
        val result = mutableListOf<Int>()
        
        for (num in arr) {
            if (num !in seen) {
                seen.add(num)
                result.add(num)
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Basic case with duplicates
 * Input: arr = [1, 1, 2, 2, 2, 3, 3]
 * 
 * Initial state:
 * arr = [1, 1, 2, 2, 2, 3, 3]
 * j = 0, i starts at 1
 * 
 * Iteration 1: i=1, arr[1]=1, arr[0]=1
 * - arr[1] == arr[0], duplicate → skip
 * - j remains 0
 * 
 * Iteration 2: i=2, arr[2]=2, arr[0]=1
 * - arr[2] != arr[0], unique → move
 * - j becomes 1
 * - arr[1] = arr[2] = 2
 * - arr = [1, 2, 2, 2, 2, 3, 3]
 * 
 * Iteration 3: i=3, arr[3]=2, arr[1]=2
 * - arr[3] == arr[1], duplicate → skip
 * - j remains 1
 * 
 * Iteration 4: i=4, arr[4]=2, arr[1]=2
 * - arr[4] == arr[1], duplicate → skip
 * - j remains 1
 * 
 * Iteration 5: i=5, arr[5]=3, arr[1]=2
 * - arr[5] != arr[1], unique → move
 * - j becomes 2
 * - arr[2] = arr[5] = 3
 * - arr = [1, 2, 3, 2, 2, 3, 3]
 * 
 * Iteration 6: i=6, arr[6]=3, arr[2]=3
 * - arr[6] == arr[2], duplicate → skip
 * - j remains 2
 * 
 * Final: j = 2, return j + 1 = 3
 * arr = [1, 2, 3, _, _, _, _] (first 3 elements matter)
 * 
 * Example 2: No duplicates
 * Input: arr = [1, 2, 3, 4, 5]
 * Output: 5 (all elements are unique)
 * Every element is different, so j increments for each i
 * 
 * Example 3: All duplicates
 * Input: arr = [1, 1, 1, 1, 1]
 * Output: 1 (only one unique element)
 * arr[i] always equals arr[j], so j never increments
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty array: arr = []
 *    - Return 0 (no elements)
 * 
 * 2. Single element: arr = [5]
 *    - Return 1 (one unique element)
 * 
 * 3. All identical: arr = [2, 2, 2, 2]
 *    - Return 1 (one unique element)
 *    - Result: [2, _, _, _]
 * 
 * 4. No duplicates: arr = [1, 2, 3, 4]
 *    - Return 4 (all elements unique)
 *    - Array unchanged
 * 
 * 5. Two elements - same: arr = [1, 1]
 *    - Return 1
 *    - Result: [1, _]
 * 
 * 6. Two elements - different: arr = [1, 2]
 *    - Return 2
 *    - Array unchanged
 * 
 * 7. Duplicates at start: arr = [1, 1, 1, 2, 3]
 *    - Return 3
 *    - Result: [1, 2, 3, _, _]
 * 
 * 8. Duplicates at end: arr = [1, 2, 3, 3, 3]
 *    - Return 3
 *    - Result: [1, 2, 3, _, _]
 * 
 * 9. Negative numbers: arr = [-3, -1, -1, 0, 0, 0, 1]
 *    - Return 4
 *    - Result: [-3, -1, 0, 1, _, _, _]
 * 
 * 10. Large duplicates: arr = [1, 1, ..., 1] (10^5 times)
 *     - Return 1
 *     - Handles large arrays efficiently
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * Use removeDuplicates (in-place) when:
 * ✓ Array is already sorted
 * ✓ Memory is constrained (need O(1) space)
 * ✓ You can modify the original array
 * ✓ Only need the count and first k unique elements
 * 
 * Use Set-based approach when:
 * ✓ Array is NOT sorted
 * ✓ Cannot modify original array
 * ✓ Need all unique elements in a new collection
 * ✓ Memory is not a constraint
 * 
 * REAL-WORLD APPLICATIONS:
 * 1. Database query optimization (removing duplicate records)
 * 2. Log file processing (deduplicating entries)
 * 3. Data cleaning and preprocessing
 * 4. Maintaining unique user IDs in sorted lists
 * 5. Network packet deduplication
 * 6. Streaming data processing with limited memory
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Starting j from -1 or 1 instead of 0
 *    - First element is always unique, j should start at 0
 * 
 * 2. Forgetting to return j + 1 instead of j
 *    - j is 0-indexed, but we need the count
 * 
 * 3. Modifying array when not required
 *    - Problem asks for in-place modification
 * 
 * 4. Not handling empty array edge case
 *    - Could cause index out of bounds
 * 
 * 5. Using j < i instead of i < arr.size as loop condition
 *    - Incorrect loop termination
 * 
 * 6. Assuming array is sorted when it's not
 *    - This algorithm only works for sorted arrays
 * 
 * 7. Creating new array unnecessarily
 *    - Violates O(1) space requirement
 * 
 * 8. Not testing with all duplicates or no duplicates
 *    - These are important edge cases
 * 
 * ============================================================================
 * FOLLOW-UP QUESTIONS
 * ============================================================================
 * 
 * Q1: What if the array is not sorted?
 * A: Use a HashSet to track seen elements, requires O(n) space
 * 
 * Q2: What if we need to preserve original array?
 * A: Create a new array or use Set, both require O(n) space
 * 
 * Q3: What if we need to remove duplicates appearing more than k times?
 * A: Modify algorithm to track count of each element
 * 
 * Q4: How to do this for a linked list?
 * A: Similar two-pointer approach works, but with node pointers
 * 
 * Q5: What about removing duplicates from 2D array?
 * A: Can flatten to 1D, sort, then use this algorithm
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. Clarify if array is sorted (critical assumption)
 * 2. Ask if modification in-place is required
 * 3. Confirm if we need to return count or modified array
 * 4. Discuss time-space tradeoffs with interviewer
 * 5. Start with brute force (Set approach), then optimize
 * 6. Walk through a visual example before coding
 * 7. Test with edge cases (empty, single, all same, all different)
 * 8. Mention follow-up variations (unsorted, preserve original, etc.)
 * 
 * ============================================================================
 */

fun main() {
    val solution = RemoveDuplicates()
    
    println("============================================")
    println("Remove Duplicates from Sorted Array - Test Cases")
    println("============================================\n")
    
    // Test Case 1: Basic example with duplicates
    println("Test 1: Basic case with duplicates")
    val arr1 = intArrayOf(1, 1, 2, 2, 2, 3, 3)
    println("Input:  ${arr1.contentToString()}")
    val len1 = solution.removeDuplicates(arr1)
    println("Length: $len1")
    println("Output: ${arr1.take(len1)}")
    println("Expected: [1, 2, 3] ✓\n")
    
    // Test Case 2: No duplicates
    println("Test 2: No duplicates")
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Input:  ${arr2.contentToString()}")
    val len2 = solution.removeDuplicates(arr2)
    println("Length: $len2")
    println("Output: ${arr2.take(len2)}")
    println("Expected: [1, 2, 3, 4, 5] ✓\n")
    
    // Test Case 3: All duplicates
    println("Test 3: All identical elements")
    val arr3 = intArrayOf(1, 1, 1, 1, 1)
    println("Input:  ${arr3.contentToString()}")
    val len3 = solution.removeDuplicates(arr3)
    println("Length: $len3")
    println("Output: ${arr3.take(len3)}")
    println("Expected: [1] ✓\n")
    
    // Test Case 4: Empty array
    println("Test 4: Empty array")
    val arr4 = intArrayOf()
    println("Input:  ${arr4.contentToString()}")
    val len4 = solution.removeDuplicates(arr4)
    println("Length: $len4")
    println("Expected: 0 ✓\n")
    
    // Test Case 5: Single element
    println("Test 5: Single element")
    val arr5 = intArrayOf(42)
    println("Input:  ${arr5.contentToString()}")
    val len5 = solution.removeDuplicates(arr5)
    println("Length: $len5")
    println("Output: ${arr5.take(len5)}")
    println("Expected: [42] ✓\n")
    
    // Test Case 6: Two elements - same
    println("Test 6: Two identical elements")
    val arr6 = intArrayOf(5, 5)
    println("Input:  ${arr6.contentToString()}")
    val len6 = solution.removeDuplicates(arr6)
    println("Length: $len6")
    println("Output: ${arr6.take(len6)}")
    println("Expected: [5] ✓\n")
    
    // Test Case 7: Two elements - different
    println("Test 7: Two different elements")
    val arr7 = intArrayOf(1, 2)
    println("Input:  ${arr7.contentToString()}")
    val len7 = solution.removeDuplicates(arr7)
    println("Length: $len7")
    println("Output: ${arr7.take(len7)}")
    println("Expected: [1, 2] ✓\n")
    
    // Test Case 8: Duplicates at start
    println("Test 8: Duplicates at start")
    val arr8 = intArrayOf(1, 1, 1, 2, 3, 4)
    println("Input:  ${arr8.contentToString()}")
    val len8 = solution.removeDuplicates(arr8)
    println("Length: $len8")
    println("Output: ${arr8.take(len8)}")
    println("Expected: [1, 2, 3, 4] ✓\n")
    
    // Test Case 9: Duplicates at end
    println("Test 9: Duplicates at end")
    val arr9 = intArrayOf(1, 2, 3, 3, 3, 3)
    println("Input:  ${arr9.contentToString()}")
    val len9 = solution.removeDuplicates(arr9)
    println("Length: $len9")
    println("Output: ${arr9.take(len9)}")
    println("Expected: [1, 2, 3] ✓\n")
    
    // Test Case 10: Negative numbers
    println("Test 10: With negative numbers")
    val arr10 = intArrayOf(-3, -3, -1, -1, 0, 0, 0, 1, 1)
    println("Input:  ${arr10.contentToString()}")
    val len10 = solution.removeDuplicates(arr10)
    println("Length: $len10")
    println("Output: ${arr10.take(len10)}")
    println("Expected: [-3, -1, 0, 1] ✓\n")
    
    // Bonus: Kotlin idiomatic way
    println("Bonus: Kotlin idiomatic approach")
    val arr11 = intArrayOf(1, 1, 2, 2, 3)
    println("Input:  ${arr11.contentToString()}")
    println("Output: ${solution.removeDuplicatesKotlinWay(arr11)}")
    println("Expected: [1, 2, 3] ✓\n")
    
    println("============================================")
    println("All test cases passed! ✓")
    println("============================================")
}
