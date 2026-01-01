/**
 * ============================================================================
 * PROBLEM: Check if Array is Sorted
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, check whether the array is sorted in non-decreasing order.
 * An array is considered sorted if for every pair of adjacent elements, 
 * arr[i] <= arr[i+1].
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [1, 2, 3, 4, 5]
 * 
 * OUTPUT FORMAT:
 * - A boolean: true if array is sorted, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * For an array to be sorted, each element must be less than or equal to the next element.
 * We can verify this by iterating through the array and checking each adjacent pair.
 * If we find any pair where arr[i] > arr[i+1], the array is not sorted.
 * 
 * KEY INSIGHT:
 * We only need to check adjacent elements. If all adjacent pairs are in order,
 * then the entire array is sorted due to transitivity property:
 * If a <= b and b <= c, then a <= c.
 * 
 * ALGORITHM STEPS:
 * 1. Handle edge case: array with 0 or 1 element is always sorted
 * 2. Iterate through array from index 0 to n-2
 * 3. For each index i, check if arr[i] > arr[i+1]
 * 4. If yes, return false immediately
 * 5. If we complete the loop without finding disorder, return true
 * 
 * VISUAL EXAMPLE (Sorted):
 * arr = [1, 2, 3, 4, 5]
 *        ↓  ↓
 *        1 <= 2 ✓
 *           ↓  ↓
 *           2 <= 3 ✓
 *              ↓  ↓
 *              3 <= 4 ✓
 *                 ↓  ↓
 *                 4 <= 5 ✓
 * Result: true
 * 
 * VISUAL EXAMPLE (Not Sorted):
 * arr = [1, 3, 2, 4]
 *        ↓  ↓
 *        1 <= 3 ✓
 *           ↓  ↓
 *           3 <= 2 ✗ (Found disorder!)
 * Result: false (stop checking)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Linear scan (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Using sorting comparison: O(n log n) time, O(n) space - Not optimal
 * 3. Using recursion: O(n) time, O(n) space (stack) - Same time, more space
 * 4. Using Kotlin stdlib: arr.zipWithNext().all { it.first <= it.second } - O(n), clean
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Best case: O(1) when first two elements are out of order
 * - Average case: O(n/2) when disorder is in middle
 * - Worst case: O(n) when array is sorted (check all pairs)
 * - n is the number of elements in the array
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using a loop counter variable
 * - No additional data structures
 * - No recursive calls
 * 
 * WHY THIS IS OPTIMAL:
 * In the worst case (sorted array), we must check all n-1 pairs to confirm
 * the array is sorted, so we cannot do better than O(n) time.
 * 
 * ============================================================================
 */

package arrays.easy

class CheckSorted {
    
    /**
     * Checks if array is sorted in non-decreasing order
     * 
     * @param arr The input array of integers
     * @return true if array is sorted, false otherwise
     */
    fun isSorted(arr: IntArray): Boolean {
        // Edge case: Arrays with 0 or 1 element are always sorted
        // No pairs to compare, so by definition they're in order
        if (arr.size <= 1) return true
        
        // Check each adjacent pair of elements
        // We iterate until arr.size - 1 because we compare arr[i] with arr[i+1]
        // If we go until arr.size, we'd get ArrayIndexOutOfBounds on arr[i+1]
        for (i in 0 until arr.size - 1) {
            // Compare current element with next element
            // If current is greater than next, array is not sorted
            if (arr[i] > arr[i + 1]) {
                // Found a pair that's out of order
                // No need to check further, return false immediately
                return false
            }
            // If arr[i] <= arr[i+1], continue to next pair
        }
        
        // If we've checked all pairs and found no disorder, array is sorted
        return true
    }
    
    /**
     * Checks if array is sorted in strictly increasing order
     * (no equal adjacent elements allowed)
     */
    fun isStrictlySorted(arr: IntArray): Boolean {
        if (arr.size <= 1) return true
        
        for (i in 0 until arr.size - 1) {
            // For strictly increasing, we need arr[i] < arr[i+1]
            // Equal values are not allowed
            if (arr[i] >= arr[i + 1]) {
                return false
            }
        }
        
        return true
    }
    
    /**
     * Checks if array is sorted in descending order
     */
    fun isSortedDescending(arr: IntArray): Boolean {
        if (arr.size <= 1) return true
        
        for (i in 0 until arr.size - 1) {
            // For descending order: each element should be >= next element
            if (arr[i] < arr[i + 1]) {
                return false
            }
        }
        
        return true
    }
    
    /**
     * Idiomatic Kotlin approach using range and all
     * Checks if all adjacent pairs are in order
     */
    fun isSortedKotlinWay(arr: IntArray): Boolean {
        if (arr.size <= 1) return true
        
        // Check all adjacent pairs using range and all function
        // Creates a range of indices and checks predicate for each
        return (0 until arr.size - 1).all { i -> arr[i] <= arr[i + 1] }
    }
    
    /**
     * Recursive approach - less efficient due to stack space
     */
    fun isSortedRecursive(arr: IntArray, index: Int = 0): Boolean {
        // Base case: reached end of array
        if (index >= arr.size - 1) return true
        
        // Check current pair
        if (arr[index] > arr[index + 1]) return false
        
        // Recursively check rest of array
        return isSortedRecursive(arr, index + 1)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * EXAMPLE 1: Sorted Array
 * INPUT: arr = [1, 2, 2, 3, 4]
 * 
 * Iteration 1 (i=0):
 * - arr[0] = 1, arr[1] = 2
 * - 1 > 2? NO
 * - Continue
 * 
 * Iteration 2 (i=1):
 * - arr[1] = 2, arr[2] = 2
 * - 2 > 2? NO (equal is okay for non-decreasing)
 * - Continue
 * 
 * Iteration 3 (i=2):
 * - arr[2] = 2, arr[3] = 3
 * - 2 > 3? NO
 * - Continue
 * 
 * Iteration 4 (i=3):
 * - arr[3] = 3, arr[4] = 4
 * - 3 > 4? NO
 * - Continue
 * 
 * Loop complete: All pairs in order
 * RESULT: true
 * 
 * ============================================================================
 * 
 * EXAMPLE 2: Unsorted Array
 * INPUT: arr = [1, 3, 2, 4]
 * 
 * Iteration 1 (i=0):
 * - arr[0] = 1, arr[1] = 3
 * - 1 > 3? NO
 * - Continue
 * 
 * Iteration 2 (i=1):
 * - arr[1] = 3, arr[2] = 2
 * - 3 > 2? YES ✗
 * - Return false immediately
 * 
 * RESULT: false
 * 
 * ============================================================================
 * EDGE CASES & HOW THEY'RE HANDLED
 * ============================================================================
 * 
 * 1. Empty array: arr = []
 *    - Size check: arr.size <= 1 returns true
 *    - Result: true (empty array is considered sorted)
 * 
 * 2. Single element: arr = [5]
 *    - Size check: arr.size <= 1 returns true
 *    - Result: true (single element is always sorted)
 * 
 * 3. All same elements: arr = [3, 3, 3, 3]
 *    - All comparisons: 3 <= 3 (true)
 *    - Result: true (non-decreasing allows equal elements)
 * 
 * 4. Two elements (sorted): arr = [1, 2]
 *    - One comparison: 1 <= 2
 *    - Result: true
 * 
 * 5. Two elements (unsorted): arr = [2, 1]
 *    - One comparison: 2 > 1
 *    - Result: false
 * 
 * 6. All negative numbers: arr = [-5, -3, -1]
 *    - -5 <= -3 (true), -3 <= -1 (true)
 *    - Result: true
 * 
 * 7. Mix of positive/negative: arr = [-3, 0, 5, 10]
 *    - All pairs in order
 *    - Result: true
 * 
 * 8. Descending order: arr = [5, 4, 3, 2, 1]
 *    - First comparison: 5 > 4
 *    - Result: false (for non-decreasing check)
 * 
 * 9. Almost sorted (one out of place): arr = [1, 2, 4, 3, 5]
 *    - When i=2: 4 > 3, return false
 *    - Result: false
 * 
 * 10. Sorted with duplicates at end: arr = [1, 2, 3, 3, 3]
 *     - All comparisons pass (3 <= 3 is true)
 *     - Result: true
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * - You need O(n) time complexity
 * - Memory is constrained (O(1) space)
 * - You want early termination on finding disorder
 * - Checking a property before further processing
 * - Validating input assumptions
 * 
 * AVOID WHEN:
 * - Array is very large and you can sample (statistical check)
 * - You need to know WHERE the disorder is (need different approach)
 * - Array is distributed across multiple machines
 * 
 * ============================================================================
 * REAL WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Data Validation**: Ensure timestamps are in chronological order
 * 2. **Database Indexing**: Verify index columns are properly ordered
 * 3. **Binary Search Prerequisite**: Confirm array can be binary searched
 * 4. **Sensor Readings**: Check if temperature/pressure readings are monotonic
 * 5. **Version Numbers**: Validate version progression (1.0, 1.1, 2.0, etc.)
 * 6. **Financial Data**: Verify transaction timestamps are in order
 * 7. **Log Files**: Ensure log entries are chronologically ordered
 * 8. **Game Leaderboards**: Verify scores are in descending order
 * 9. **Manufacturing**: Check if measurements follow quality trend
 * 10. **Network Packets**: Verify packet sequence numbers
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * 1. **Off-by-one error**: Iterating to arr.size instead of arr.size - 1
 *    - Problem: ArrayIndexOutOfBounds when accessing arr[i+1]
 *    - Solution: Use `i < arr.size - 1` or `i in 0 until arr.size - 1`
 * 
 * 2. **Using < instead of <=**: For non-decreasing order
 *    - Problem: Rejects arrays with equal adjacent elements
 *    - Solution: Use `arr[i] <= arr[i+1]` or check `arr[i] > arr[i+1]`
 * 
 * 3. **Not handling edge cases**: Empty or single-element arrays
 *    - Problem: May cause errors or incorrect results
 *    - Solution: Check size first
 * 
 * 4. **Comparing entire array**: Sorting and comparing with original
 *    - Problem: O(n log n) time and O(n) space
 *    - Solution: Use linear scan approach
 * 
 * 5. **Not short-circuiting**: Checking all pairs even after finding disorder
 *    - Problem: Unnecessary comparisons
 *    - Solution: Return false immediately when disorder found
 * 
 * 6. **Confusing non-decreasing with strictly increasing**
 *    - Problem: Wrong comparison operator
 *    - Solution: Understand the difference:
 *      * Non-decreasing: arr[i] <= arr[i+1] (duplicates allowed)
 *      * Strictly increasing: arr[i] < arr[i+1] (no duplicates)
 * 
 * ============================================================================
 * VARIATIONS & EXTENSIONS
 * ============================================================================
 * 
 * 1. **Find First Unsorted Index**: Return index where order breaks
 *    ```kotlin
 *    fun findFirstUnsorted(arr: IntArray): Int {
 *        for (i in 0 until arr.size - 1) {
 *            if (arr[i] > arr[i + 1]) return i
 *        }
 *        return -1 // Array is sorted
 *    }
 *    ```
 * 
 * 2. **Count Inversions**: How many pairs are out of order
 *    - More complex, O(n log n) with merge sort
 * 
 * 3. **Check Rotated Sorted Array**: [3,4,5,1,2] is rotated sorted
 *    - Find rotation point and check both halves
 * 
 * 4. **Almost Sorted**: Check if array is sorted with at most K swaps
 *    - More complex validation
 * 
 * 5. **Custom Comparator**: Check if sorted by custom rule
 *    ```kotlin
 *    fun isSortedBy(arr: IntArray, comparator: (Int, Int) -> Boolean): Boolean {
 *        for (i in 0 until arr.size - 1) {
 *            if (!comparator(arr[i], arr[i + 1])) return false
 *        }
 *        return true
 *    }
 *    ```
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Clarify the definition**: Ask if duplicates are allowed (non-decreasing vs strictly increasing)
 * 2. **Ask about array size**: Edge cases with empty or single elements
 * 3. **Discuss optimization**: Early termination on finding disorder
 * 4. **Mention variations**: Descending, strictly increasing, custom order
 * 5. **Time complexity**: Emphasize O(n) is optimal for this problem
 * 6. **Follow-up**: Be ready to find where the disorder occurs
 * 7. **Code clarity**: Simple, readable code is better than clever tricks
 * 
 * ============================================================================
 */

fun main() {
    val solution = CheckSorted()
    
    println("=== Check if Array is Sorted Tests ===\n")
    
    // Test 1: Sorted array
    println("Test 1: Sorted array")
    val arr1 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${arr1.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr1)}")
    println("Expected: true\n")
    
    // Test 2: Unsorted array
    println("Test 2: Unsorted array")
    val arr2 = intArrayOf(1, 3, 2, 4)
    println("Input: ${arr2.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr2)}")
    println("Expected: false\n")
    
    // Test 3: All same elements
    println("Test 3: All same elements")
    val arr3 = intArrayOf(5, 5, 5, 5)
    println("Input: ${arr3.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr3)}")
    println("Expected: true\n")
    
    // Test 4: Empty array
    println("Test 4: Empty array")
    val arr4 = intArrayOf()
    println("Input: ${arr4.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr4)}")
    println("Expected: true\n")
    
    // Test 5: Single element
    println("Test 5: Single element")
    val arr5 = intArrayOf(42)
    println("Input: ${arr5.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr5)}")
    println("Expected: true\n")
    
    // Test 6: Descending order
    println("Test 6: Descending order")
    val arr6 = intArrayOf(5, 4, 3, 2, 1)
    println("Input: ${arr6.contentToString()}")
    println("Is Sorted (ascending): ${solution.isSorted(arr6)}")
    println("Is Sorted (descending): ${solution.isSortedDescending(arr6)}")
    println("Expected: false (ascending), true (descending)\n")
    
    // Test 7: Sorted with duplicates
    println("Test 7: Sorted with duplicates")
    val arr7 = intArrayOf(1, 2, 2, 3, 3, 4)
    println("Input: ${arr7.contentToString()}")
    println("Is Sorted (non-decreasing): ${solution.isSorted(arr7)}")
    println("Is Strictly Sorted: ${solution.isStrictlySorted(arr7)}")
    println("Expected: true (non-decreasing), false (strictly)\n")
    
    // Test 8: Negative numbers
    println("Test 8: Negative numbers")
    val arr8 = intArrayOf(-5, -3, -1, 0, 2)
    println("Input: ${arr8.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr8)}")
    println("Expected: true\n")
    
    // Test 9: Almost sorted (one element out of place)
    println("Test 9: Almost sorted")
    val arr9 = intArrayOf(1, 2, 4, 3, 5)
    println("Input: ${arr9.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr9)}")
    println("Expected: false\n")
    
    // Test 10: Two elements
    println("Test 10: Two elements")
    val arr10a = intArrayOf(1, 2)
    val arr10b = intArrayOf(2, 1)
    println("Input 1: ${arr10a.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr10a)}")
    println("Input 2: ${arr10b.contentToString()}")
    println("Is Sorted: ${solution.isSorted(arr10b)}")
    println("Expected: true, false\n")
    
    // Comparison of different approaches
    println("=== Comparing Different Approaches ===")
    val testArray = intArrayOf(1, 2, 3, 4, 5)
    println("Test Array: ${testArray.contentToString()}")
    println("Standard approach: ${solution.isSorted(testArray)}")
    println("Kotlin way (zipWithNext): ${solution.isSortedKotlinWay(testArray)}")
    println("Recursive approach: ${solution.isSortedRecursive(testArray)}")
}
