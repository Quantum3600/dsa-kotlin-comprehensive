/**
 * ============================================================================
 * PROBLEM: Exponential Search
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a **sorted** array of integers and a target value, find the index of the
 * target in the array using exponential search. If the target is not found, return -1.
 * 
 * INPUT FORMAT: 
 * - A sorted array of integers: arr = [1, 2, 3, 4, 5, 10, 20, 40, 80, 100]
 * - A target integer: target = 40
 * 
 * OUTPUT FORMAT:
 * - Index of target if found: 7
 * - -1 if target not found
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - Array is sorted in ascending order
 * - -10^9 <= arr[i], target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * Imagine you're looking for a word in a dictionary but you don't know which
 * section it's in. Instead of checking every page, you check pages 1, 2, 4, 8,
 * 16...  (doubling each time) until you overshoot. Then you know it's in the
 * last range you checked! 
 * 
 * KEY INSIGHT:
 * Exponential search is particularly useful when:
 * 1. Target is closer to the beginning (performs better than binary search)
 * 2. Array size is unbounded or unknown
 * 3. You want to find the range first, then search within it
 * 
 * ALGORITHM STEPS:
 * 1. Check if first element is the target (arr[0] == target)
 * 2. Find range where target exists by doubling index:  1, 2, 4, 8, 16... 
 * 3. Once arr[i] >= target, we know target is in range [i/2, i]
 * 4. Perform binary search in this found range
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 3, 4, 5, 10, 20, 40, 80, 100], target = 40
 * 
 * Step 1: Check arr[0] = 1, not equal to 40
 * Step 2: Find range by exponential jumps
 *   i = 1: arr[1] = 2 < 40, continue
 *   i = 2: arr[2] = 3 < 40, continue
 *   i = 4: arr[4] = 5 < 40, continue
 *   i = 8: arr[8] = 80 > 40, STOP! 
 * 
 * Step 3: Binary search in range [4, 8]
 * arr[4.. 8] = [5, 10, 20, 40, 80]
 *              L       M        R
 * mid = 6, arr[6] = 20 < 40, go right
 * 
 * arr[7..8] = [40, 80]
 *              LM  R
 * mid = 7, arr[7] = 40 == 40, FOUND! 
 * 
 * WHY IT'S CALLED "EXPONENTIAL":
 * Search range grows exponentially:  2^0, 2^1, 2^2, 2^3...  (1, 2, 4, 8...)
 * 
 * COMPARISON WITH OTHER ALGORITHMS:
 * 1. vs Binary Search: 
 *    - Better when target is near beginning
 *    - Works with unbounded arrays
 * 2. vs Jump Search:
 *    - Exponential is better for elements near start
 *    - Jump search uses fixed steps
 * 3. vs Linear Search:
 *    - Much faster:  O(log n) vs O(n)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Exponential Search: O(log n) time, O(1) space - OPTIMAL for unbounded
 * 2. Binary Search: O(log n) time, O(1) space - Better for general case
 * 3. Jump Search: O(√n) time, O(1) space - Better for bounded searches
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Finding range: O(log i) where i is position of target
 * - Binary search in range: O(log i)
 * - Total: O(log i) where i is position of target
 * - Worst case: O(log n) when target is at end
 * 
 * SPACE COMPLEXITY:
 * - O(1): Only using constant extra space (variables i, left, right, mid)
 * 
 * ============================================================================
 * EDGE CASES & ERROR HANDLING
 * ============================================================================
 * 
 * 1. Empty array: Return -1
 * 2. Single element: Check if it matches target
 * 3. Target at first position:  Checked immediately
 * 4. Target at last position: Found during range finding
 * 5. Target not in array: Binary search returns -1
 * 6. All elements smaller than target: Handles with min()
 * 7. Duplicate elements: Returns first occurrence
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 */

package searching.advanced

/**
 * Performs exponential search on a sorted array. 
 * 
 * @param arr Sorted array of integers in ascending order
 * @param target Value to search for
 * @return Index of target if found, -1 otherwise
 */
fun exponentialSearch(arr: IntArray, target: Int): Int {
    val n = arr.size
    
    // Edge case: empty array
    if (n == 0) return -1
    
    // Check if target is at first position
    if (arr[0] == target) return 0
    
    // Find range for binary search by repeated doubling
    var i = 1
    while (i < n && arr[i] <= target) {
        i *= 2
    }
    
    // Perform binary search in found range [i/2, min(i, n-1)]
    return binarySearch(arr, i / 2, minOf(i, n - 1), target)
}

/**
 * Helper function:  Binary search in a given range.
 * 
 * @param arr Sorted array
 * @param left Left boundary of search range
 * @param right Right boundary of search range
 * @param target Value to search for
 * @return Index of target if found, -1 otherwise
 */
private fun binarySearch(arr: IntArray, left: Int, right: Int, target: Int): Int {
    var l = left
    var r = right
    
    while (l <= r) {
        val mid = l + (r - l) / 2
        
        when {
            arr[mid] == target -> return mid
            arr[mid] < target -> l = mid + 1
            else -> r = mid - 1
        }
    }
    
    return -1
}

/**
 * ============================================================================
 * EXAMPLE USAGE
 * ============================================================================
 */
fun main() {
    // Example 1: Basic usage
    val arr1 = intArrayOf(1, 2, 3, 4, 5, 10, 20, 40, 80, 100)
    val target1 = 40
    println("Example 1:")
    println("Array: ${arr1.contentToString()}")
    println("Target: $target1")
    println("Result: ${exponentialSearch(arr1, target1)}")
    println("Expected: 7")
    println()
    
    // Example 2: Target at beginning
    val arr2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target2 = 1
    println("Example 2:")
    println("Array: ${arr2.contentToString()}")
    println("Target: $target2")
    println("Result: ${exponentialSearch(arr2, target2)}")
    println("Expected: 0")
    println()
    
    // Example 3: Target not found
    val arr3 = intArrayOf(2, 4, 6, 8, 10, 12, 14, 16)
    val target3 = 5
    println("Example 3:")
    println("Array: ${arr3.contentToString()}")
    println("Target: $target3")
    println("Result: ${exponentialSearch(arr3, target3)}")
    println("Expected: -1")
    println()
    
    // Example 4: Large array
    val arr4 = IntArray(1000) { it * 2 }
    val target4 = 500
    println("Example 4:")
    println("Array: [0, 2, 4, .. ., 1998] (1000 elements)")
    println("Target: $target4")
    println("Result: ${exponentialSearch(arr4, target4)}")
    println("Expected:  250")
    println()
    
    // Example 5: Single element
    val arr5 = intArrayOf(42)
    val target5 = 42
    println("Example 5:")
    println("Array: ${arr5.contentToString()}")
    println("Target: $target5")
    println("Result: ${exponentialSearch(arr5, target5)}")
    println("Expected: 0")
}

/**
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. UNBOUNDED SEARCH: 
 *    - Searching in data streams
 *    - Finding elements in infinite lists
 *    - Database queries with unknown size
 * 
 * 2. CACHE-FRIENDLY:
 *    - Works well with CPU cache
 *    - Sequential access pattern initially
 * 
 * 3. FILE SYSTEMS:
 *    - Searching in sorted log files
 *    - Finding records in large sorted databases
 * 
 * 4. NETWORK PROTOCOLS:
 *    - Finding timeout values
 *    - Binary exponential backoff algorithms
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Search in Infinite Sorted Array
 * 2. Find Position of Element in Infinite Array
 * 3. Search in Rotated Sorted Array
 * 4. Find Peak Element
 * 5. Search Insert Position
 * 
 * ============================================================================
 */
