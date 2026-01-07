/**
 * ============================================================================
 * PROBLEM:  Find First and Last Position of Element in Sorted Array
 * DIFFICULTY: Medium
 * CATEGORY:  Searching - Binary Search 1D
 * LEETCODE: #34
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers sorted in ascending order, find the starting 
 * and ending position of a given target value.
 * 
 * If target is not found in the array, return [-1, -1]. 
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * INPUT FORMAT:
 * - A sorted array:  arr = [5, 7, 7, 8, 8, 10]
 * - A target:  target = 8
 * 
 * OUTPUT FORMAT:
 * - Array containing [first_index, last_index]:  [3, 4]
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * - Array is sorted in non-decreasing order
 * - -10^9 <= target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of finding a word's range in a dictionary:
 * - First occurrence: Where the word starts
 * - Last occurrence: Where the word ends
 * We need TWO binary searches! 
 * 
 * KEY INSIGHT:
 * - First occurrence = Lower Bound (first element >= target)
 * - Last occurrence = Upper Bound - 1 (last element <= target)
 * 
 * OR use modified binary search:
 * - First:  When found, continue searching LEFT
 * - Last: When found, continue searching RIGHT
 * 
 * ALGORITHM FOR FIRST OCCURRENCE:
 * 1. Initialize left = 0, right = n-1, first = -1
 * 2. While left <= right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] == target: 
 *       - Store mid as potential first
 *       - Continue searching LEFT for earlier occurrence
 *       - right = mid - 1
 *    c. If arr[mid] < target:  left = mid + 1
 *    d. If arr[mid] > target: right = mid - 1
 * 3. Return first
 * 
 * ALGORITHM FOR LAST OCCURRENCE: 
 * 1. Initialize left = 0, right = n-1, last = -1
 * 2. While left <= right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] == target:
 *       - Store mid as potential last
 *       - Continue searching RIGHT for later occurrence
 *       - left = mid + 1
 *    c. If arr[mid] < target: left = mid + 1
 *    d. If arr[mid] > target: right = mid - 1
 * 3. Return last
 * 
 * VISUAL EXAMPLE:
 * arr = [5, 7, 7, 8, 8, 10], target = 8
 * 
 * FINDING FIRST: 
 * Step 1: mid = 2, arr[2] = 7 < 8, search right
 * Step 2: mid = 4, arr[4] = 8 == 8, first = 4, search left
 * Step 3: mid = 3, arr[3] = 8 == 8, first = 3, search left
 * Step 4: left > right, exit
 * First = 3 ✓
 * 
 * FINDING LAST:
 * Step 1: mid = 2, arr[2] = 7 < 8, search right
 * Step 2: mid = 4, arr[4] = 8 == 8, last = 4, search right
 * Step 3: mid = 5, arr[5] = 10 > 8, search left
 * Step 4: left > right, exit
 * Last = 4 ✓
 * 
 * OUTPUT: [3, 4] ✓
 * 
 * WHY TWO SEPARATE SEARCHES?
 * - First: Move left when found (find leftmost)
 * - Last: Move right when found (find rightmost)
 * - Different directions = different searches needed
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - First occurrence: O(log n)
 * - Last occurrence: O(log n)
 * - Total: O(log n) + O(log n) = O(log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * - No recursion or additional structures
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class FirstLastOccurrence {
    
    /**
     * Find first and last position of target
     * 
     * @param arr Sorted array
     * @param target Value to search for
     * @return IntArray of [first, last] or [-1, -1] if not found
     */
    fun searchRange(arr: IntArray, target:  Int): IntArray {
        val first = findFirst(arr, target)
        
        // If first not found, target doesn't exist
        if (first == -1) {
            return intArrayOf(-1, -1)
        }
        
        val last = findLast(arr, target)
        return intArrayOf(first, last)
    }
    
    /**
     * Find first occurrence of target
     * Uses lower bound concept with exact match verification
     */
    private fun findFirst(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr. size - 1
        var first = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            when {
                arr[mid] == target -> {
                    // Found target, but keep looking left for first occurrence
                    first = mid
                    right = mid - 1
                }
                arr[mid] < target -> {
                    // Target is on the right
                    left = mid + 1
                }
                else -> {
                    // arr[mid] > target, search left
                    right = mid - 1
                }
            }
        }
        
        return first
    }
    
    /**
     * Find last occurrence of target
     * Searches right when target is found
     */
    private fun findLast(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var last = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            when {
                arr[mid] == target -> {
                    // Found target, but keep looking right for last occurrence
                    last = mid
                    left = mid + 1
                }
                arr[mid] < target -> {
                    // Target is on the right
                    left = mid + 1
                }
                else -> {
                    // arr[mid] > target, search left
                    right = mid - 1
                }
            }
        }
        
        return last
    }
    
    /**
     * Alternative:  Using lower and upper bound
     * More elegant but requires understanding of bounds
     */
    fun searchRangeUsingBounds(arr: IntArray, target: Int): IntArray {
        val lb = LowerBound()
        val ub = UpperBound()
        
        val first = lb.lowerBound(arr, target)
        
        // Check if target actually exists
        if (first == arr.size || arr[first] != target) {
            return intArrayOf(-1, -1)
        }
        
        val last = ub.upperBound(arr, target) - 1
        
        return intArrayOf(first, last)
    }
    
    /**
     * Count occurrences of target
     * Useful application of first and last position
     */
    fun countOccurrences(arr: IntArray, target: Int): Int {
        val range = searchRange(arr, target)
        
        if (range[0] == -1) {
            return 0
        }
        
        return range[1] - range[0] + 1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Multiple occurrences
 * INPUT:  arr = [5, 7, 7, 8, 8, 10], target = 8
 * 
 * FINDING FIRST OCCURRENCE:
 * Iteration 1:
 * - left = 0, right = 5, mid = 2
 * - arr[2] = 7 < 8
 * - left = 3
 * 
 * Iteration 2:
 * - left = 3, right = 5, mid = 4
 * - arr[4] = 8 == 8
 * - first = 4, right = 3 (search left)
 * 
 * Iteration 3:
 * - left = 3, right = 3, mid = 3
 * - arr[3] = 8 == 8
 * - first = 3, right = 2 (search left)
 * 
 * left > right, exit with first = 3
 * 
 * FINDING LAST OCCURRENCE:
 * Iteration 1:
 * - left = 0, right = 5, mid = 2
 * - arr[2] = 7 < 8
 * - left = 3
 * 
 * Iteration 2:
 * - left = 3, right = 5, mid = 4
 * - arr[4] = 8 == 8
 * - last = 4, left = 5 (search right)
 * 
 * Iteration 3:
 * - left = 5, right = 5, mid = 5
 * - arr[5] = 10 > 8
 * - right = 4
 * 
 * left > right, exit with last = 4
 * 
 * OUTPUT: [3, 4] ✓
 * 
 * ---
 * 
 * Example 2: Target not in array
 * INPUT: arr = [5, 7, 7, 8, 8, 10], target = 6
 * 
 * FINDING FIRST: 
 * Binary search completes without finding 6
 * first = -1
 * 
 * OUTPUT: [-1, -1] ✓
 * 
 * ---
 * 
 * Example 3: Single occurrence
 * INPUT: arr = [1, 2, 3, 4, 5], target = 3
 * 
 * FIRST:  2
 * LAST: 2
 * OUTPUT: [2, 2] ✓
 * 
 * ---
 * 
 * Example 4: All elements are target
 * INPUT: arr = [1, 1, 1, 1, 1], target = 1
 * 
 * FIRST:  0
 * LAST:  4
 * OUTPUT: [0, 4] ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Return [-1, -1]
 * 2. Single element equal to target: Return [0, 0]
 * 3. Single element not equal:  Return [-1, -1]
 * 4. Target at start:  Return [0, last_index]
 * 5. Target at end: Return [first_index, n-1]
 * 6. All elements are target: Return [0, n-1]
 * 7. Target appears once: Return [index, index]
 * 8. Target smaller than all:  Return [-1, -1]
 * 9. Target larger than all: Return [-1, -1]
 * 10. Consecutive duplicates: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using single binary search
 * ❌ Regular binary search finds ANY occurrence, not first/last
 * ✅ Need TWO separate searches with different directions
 * 
 * MISTAKE 2: Wrong pointer update for first occurrence
 * ❌ if (arr[mid] == target) return mid  // Returns any occurrence
 * ✅ if (arr[mid] == target) { first = mid; right = mid - 1; }
 * 
 * MISTAKE 3: Wrong pointer update for last occurrence
 * ❌ if (arr[mid] == target) { last = mid; right = mid - 1; }
 * ✅ if (arr[mid] == target) { last = mid; left = mid + 1; }
 * 
 * MISTAKE 4: Not checking if target exists before finding last
 * ❌ Always searching for last even if first = -1
 * ✅ Check first == -1 before searching for last
 * 
 * MISTAKE 5: Off-by-one in count calculation
 * ❌ count = last - first  // Missing one element! 
 * ✅ count = last - first + 1
 * 
 * ============================================================================
 * KEY DIFFERENCES:  FIRST vs LAST
 * ============================================================================
 * 
 * FIRST OCCURRENCE: 
 * - When arr[mid] == target: Move LEFT (right = mid - 1)
 * - Looking for leftmost/earliest occurrence
 * - Same as lower bound (if exact match exists)
 * 
 * LAST OCCURRENCE:
 * - When arr[mid] == target: Move RIGHT (left = mid + 1)
 * - Looking for rightmost/latest occurrence
 * - Same as upper bound - 1 (if exact match exists)
 * 
 * BOTH: 
 * - When arr[mid] < target: Move right (left = mid + 1)
 * - When arr[mid] > target: Move left (right = mid - 1)
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. COUNT OCCURRENCES:
 *    count = last - first + 1
 *    O(log n) instead of O(n) linear scan
 * 
 * 2. RANGE DELETION:
 *    Delete all occurrences of target
 *    Remove elements from first to last
 * 
 * 3. DATABASE QUERIES:
 *    Find all records with specific value
 *    Return range [first, last] for efficient access
 * 
 * 4. TIME-SERIES DATA:
 *    Find first and last occurrence of event
 *    Useful for log analysis
 * 
 * 5. DUPLICATE ANALYSIS:
 *    Check if element appears exactly once
 *    If first == last, single occurrence
 * 
 * ============================================================================
 * COMPARISON WITH LINEAR SEARCH
 * ============================================================================
 * 
 * LINEAR SEARCH APPROACH:
 * ```kotlin
 * fun linearSearchRange(arr: IntArray, target: Int): IntArray {
 *     var first = -1
 *     var last = -1
 *     for (i in arr.indices) {
 *         if (arr[i] == target) {
 *             if (first == -1) first = i
 *             last = i
 *         }
 *     }
 *     return intArrayOf(first, last)
 * }
 * ```
 * Time: O(n), Space: O(1)
 * 
 * BINARY SEARCH APPROACH (THIS):
 * Time: O(log n), Space: O(1)
 * 
 * For n = 1,000,000:
 * - Linear:  ~1,000,000 operations
 * - Binary: ~20 operations
 * That's 50,000x faster!
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - Count occurrences in sorted array
 * - Search insert position
 * - Find K closest elements
 * - Find smallest letter greater than target
 * - Time-based key-value store
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val flo = FirstLastOccurrence()
    
    println("=== Testing First and Last Occurrence ===\n")
    
    // Test 1: Multiple occurrences
    val arr1 = intArrayOf(5, 7, 7, 8, 8, 10)
    println("Test 1: arr = ${arr1.contentToString()}, target = 8")
    val result1 = flo.searchRange(arr1, 8)
    println("Result: [${result1[0]}, ${result1[1]}]")
    println("Expected: [3, 4]\n")
    
    // Test 2: Target not found
    println("Test 2: arr = ${arr1.contentToString()}, target = 6")
    val result2 = flo.searchRange(arr1, 6)
    println("Result: [${result2[0]}, ${result2[1]}]")
    println("Expected: [-1, -1]\n")
    
    // Test 3: Single occurrence
    val arr3 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 3: arr = ${arr3.contentToString()}, target = 3")
    val result3 = flo. searchRange(arr3, 3)
    println("Result: [${result3[0]}, ${result3[1]}]")
    println("Expected: [2, 2]\n")
    
    // Test 4: All elements are target
    val arr4 = intArrayOf(1, 1, 1, 1, 1)
    println("Test 4: arr = ${arr4.contentToString()}, target = 1")
    val result4 = flo.searchRange(arr4, 1)
    println("Result: [${result4[0]}, ${result4[1]}]")
    println("Expected: [0, 4]\n")
    
    // Test 5: Target at start
    val arr5 = intArrayOf(2, 2, 3, 4, 5)
    println("Test 5: arr = ${arr5.contentToString()}, target = 2")
    val result5 = flo.searchRange(arr5, 2)
    println("Result:  [${result5[0]}, ${result5[1]}]")
    println("Expected: [0, 1]\n")
    
    // Test 6: Target at end
    val arr6 = intArrayOf(1, 2, 3, 5, 5)
    println("Test 6: arr = ${arr6.contentToString()}, target = 5")
    val result6 = flo.searchRange(arr6, 5)
    println("Result: [${result6[0]}, ${result6[1]}]")
    println("Expected:  [3, 4]\n")
    
    // Test 7: Empty array
    val arr7 = intArrayOf()
    println("Test 7: arr = ${arr7.contentToString()}, target = 0")
    val result7 = flo.searchRange(arr7, 0)
    println("Result: [${result7[0]}, ${result7[1]}]")
    println("Expected:  [-1, -1]\n")
    
    // Test 8: Count occurrences
    val arr8 = intArrayOf(1, 2, 2, 2, 3, 4, 4, 5)
    println("Test 8: Count occurrences in ${arr8.contentToString()}")
    println("Count of 2: ${flo.countOccurrences(arr8, 2)}")
    println("Expected: 3")
    println("Count of 4: ${flo. countOccurrences(arr8, 4)}")
    println("Expected: 2")
    println("Count of 6: ${flo.countOccurrences(arr8, 6)}")
    println("Expected:  0\n")
    
    // Test 9: Using bounds method
    println("Test 9: Using bounds method for ${arr1.contentToString()}, target = 8")
    val result9 = flo.searchRangeUsingBounds(arr1, 8)
    println("Result: [${result9[0]}, ${result9[1]}]")
    println("Expected: [3, 4]\n")
    
    // Test 10: Large range
    val arr10 = IntArray(1000) { 5 }  // 1000 fives
    println("Test 10: Array of 1000 fives, search for 5")
    val result10 = flo.searchRange(arr10, 5)
    println("Result: [${result10[0]}, ${result10[1]}]")
    println("Expected: [0, 999]\n")
}
