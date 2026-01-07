/**
 * ============================================================================
 * PROBLEM:  Search in Rotated Sorted Array
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE: #33
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A sorted array has been rotated at an unknown pivot.  Search for a target 
 * value in this rotated sorted array.  If found, return its index, otherwise 
 * return -1.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * All values in the array are UNIQUE.
 * 
 * INPUT FORMAT:
 * - A rotated sorted array:  arr = [4, 5, 6, 7, 0, 1, 2]
 * - A target integer: target = 0
 * 
 * OUTPUT FORMAT:
 * - Index of target if found:  4
 * - -1 if not found
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 5000
 * - -10^4 <= arr[i] <= 10^4
 * - All values are unique
 * - Array was originally sorted in ascending order then rotated
 * - -10^4 <= target <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Original:  [0, 1, 2, 4, 5, 6, 7]
 * Rotated:   [4, 5, 6, 7, 0, 1, 2]  (rotated at index 4)
 *            ↑sorted↑  ↑sorted↑
 * 
 * KEY INSIGHT:
 * Even though the array is rotated, at least ONE half is always sorted! 
 * - If arr[left] <= arr[mid]:  Left half is sorted
 * - Else: Right half is sorted
 * 
 * We can determine which half is sorted, then check if target lies in that 
 * sorted half.  If yes, search there. If no, search the other half.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left <= right:
 *    a. Calculate mid
 *    b. If arr[mid] == target:  return mid (found!)
 *    c. Check which half is sorted:
 *       
 *       If LEFT half is sorted (arr[left] <= arr[mid]):
 *         - If target is in range [arr[left], arr[mid]]: 
 *           Search left half (right = mid - 1)
 *         - Else:  Search right half (left = mid + 1)
 *       
 *       If RIGHT half is sorted: 
 *         - If target is in range [arr[mid], arr[right]]:
 *           Search right half (left = mid + 1)
 *         - Else: Search left half (right = mid - 1)
 * 3. Return -1 (not found)
 * 
 * VISUAL EXAMPLE:
 * arr = [4, 5, 6, 7, 0, 1, 2], target = 0
 * 
 * Step 1: [4, 5, 6, 7, 0, 1, 2]
 *          L        M        R
 *          arr[L]=4, arr[M]=7, arr[R]=2
 *          Left half [4,5,6,7] is sorted (4 <= 7)
 *          Is 0 in [4, 7]? NO
 *          Search right half
 * 
 * Step 2: [0, 1, 2]
 *          L  M  R
 *          arr[L]=0, arr[M]=1, arr[R]=2
 *          Left half [0,1] is sorted (0 <= 1)
 *          Is 0 in [0, 1]? YES
 *          Search left half
 * 
 * Step 3: [0]
 *          LMR
 *          arr[M] = 0 == target
 *          Found at index 4!  ✓
 * 
 * WHY THIS WORKS:
 * At any point, one half MUST be sorted. In that sorted half, we can 
 * easily check if target falls in the range using simple comparison.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Each iteration eliminates half the search space
 * - Same as regular binary search
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * - No recursion in iterative version
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class SearchRotatedArray {
    
    /**
     * Search for target in rotated sorted array
     * 
     * @param arr Rotated sorted array with unique elements
     * @param target Value to search for
     * @return Index of target, or -1 if not found
     */
    fun search(arr: IntArray, target:  Int): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            // Found target
            if (arr[mid] == target) {
                return mid
            }
            
            // Determine which half is sorted
            if (arr[left] <= arr[mid]) {
                // Left half is sorted:  [left ...  mid]
                
                // Check if target is in the sorted left half
                if (target >= arr[left] && target < arr[mid]) {
                    // Target is in left sorted half
                    right = mid - 1
                } else {
                    // Target is in right half (might be sorted or rotated)
                    left = mid + 1
                }
            } else {
                // Right half is sorted: [mid ... right]
                
                // Check if target is in the sorted right half
                if (target > arr[mid] && target <= arr[right]) {
                    // Target is in right sorted half
                    left = mid + 1
                } else {
                    // Target is in left half (might be sorted or rotated)
                    right = mid - 1
                }
            }
        }
        
        // Target not found
        return -1
    }
    
    /**
     * Recursive approach for search in rotated array
     */
    fun searchRecursive(arr: IntArray, target: Int, left: Int = 0, right: Int = arr.size - 1): Int {
        // Base case
        if (left > right) {
            return -1
        }
        
        val mid = left + (right - left) / 2
        
        // Found target
        if (arr[mid] == target) {
            return mid
        }
        
        // Check which half is sorted
        if (arr[left] <= arr[mid]) {
            // Left half is sorted
            if (target >= arr[left] && target < arr[mid]) {
                // Search left
                return searchRecursive(arr, target, left, mid - 1)
            } else {
                // Search right
                return searchRecursive(arr, target, mid + 1, right)
            }
        } else {
            // Right half is sorted
            if (target > arr[mid] && target <= arr[right]) {
                // Search right
                return searchRecursive(arr, target, mid + 1, right)
            } else {
                // Search left
                return searchRecursive(arr, target, left, mid - 1)
            }
        }
    }
    
    /**
     * Alternative:  Find pivot first, then do binary search
     * Less efficient (2 binary searches) but easier to understand
     */
    fun searchUsingPivot(arr: IntArray, target: Int): Int {
        val pivot = findPivot(arr)
        
        // If no rotation, do regular binary search
        if (pivot == 0) {
            return binarySearch(arr, 0, arr.size - 1, target)
        }
        
        // Decide which half to search
        if (target >= arr[0]) {
            // Search in left sorted part
            return binarySearch(arr, 0, pivot - 1, target)
        } else {
            // Search in right sorted part
            return binarySearch(arr, pivot, arr.size - 1, target)
        }
    }
    
    private fun findPivot(arr:  IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] > arr[right]) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        
        return left
    }
    
    private fun binarySearch(arr:  IntArray, left: Int, right: Int, target: Int): Int {
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
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Target in rotated part
 * INPUT: arr = [4, 5, 6, 7, 0, 1, 2], target = 0
 * 
 * Iteration 1:
 * - left = 0, right = 6, mid = 3
 * - arr[mid] = 7 != 0
 * - arr[0] = 4 <= arr[3] = 7, so left half sorted
 * - Is 0 in [4, 7)? NO
 * - Search right:  left = 4
 * 
 * Iteration 2:
 * - left = 4, right = 6, mid = 5
 * - arr[mid] = 1 != 0
 * - arr[4] = 0 <= arr[5] = 1, so left half sorted
 * - Is 0 in [0, 1)? YES
 * - Search left: right = 4
 * 
 * Iteration 3:
 * - left = 4, right = 4, mid = 4
 * - arr[mid] = 0 == 0
 * - FOUND at index 4! ✓
 * 
 * OUTPUT: 4 ✓
 * 
 * ---
 * 
 * Example 2: Target in sorted part
 * INPUT: arr = [4, 5, 6, 7, 0, 1, 2], target = 5
 * 
 * Iteration 1:
 * - mid = 3, arr[mid] = 7
 * - Left half [4,5,6,7] sorted
 * - Is 5 in [4, 7)? YES
 * - Search left
 * 
 * Iteration 2:
 * - mid = 1, arr[mid] = 5
 * - FOUND at index 1! ✓
 * 
 * OUTPUT: 1 ✓
 * 
 * ---
 * 
 * Example 3: Target not in array
 * INPUT: arr = [4, 5, 6, 7, 0, 1, 2], target = 3
 * 
 * Binary search completes without finding 3
 * OUTPUT: -1 ✓
 * 
 * ---
 * 
 * Example 4: No rotation
 * INPUT: arr = [1, 2, 3, 4, 5], target = 3
 * 
 * Array is already sorted, works like regular binary search
 * OUTPUT:  2 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. No rotation: [1, 2, 3, 4, 5] - Works as regular binary search
 * 2. Full rotation: [5, 1, 2, 3, 4] - Handles correctly
 * 3. Single element: [1] - Returns 0 if match, -1 otherwise
 * 4. Two elements rotated: [2, 1] - Handles correctly
 * 5. Target at pivot:  Finds correctly
 * 6. Target before pivot: Finds correctly
 * 7. Target after pivot: Finds correctly
 * 8. Target not in array: Returns -1
 * 9. All negative numbers: Works correctly
 * 10. Mixed positive/negative: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not checking arr[left] <= arr[mid] correctly
 * ❌ if (arr[left] < arr[mid])  // Fails when left == mid
 * ✅ if (arr[left] <= arr[mid])  // Handles equal case
 * 
 * MISTAKE 2: Wrong range check for sorted half
 * ❌ if (target >= arr[left] && target <= arr[mid])  // Includes mid
 * ✅ if (target >= arr[left] && target < arr[mid])   // Excludes mid (already checked)
 * 
 * MISTAKE 3: Confusing which half to search
 * - If target IN sorted half range → search sorted half
 * - If target NOT in sorted half range → search other half
 * 
 * MISTAKE 4: Trying to find pivot first
 * ❌ Two binary searches (find pivot, then search)
 * ✅ One binary search with conditional logic
 * 
 * MISTAKE 5: Not handling single element case
 * Edge case: arr = [1], target = 1
 * Must return 0, not -1
 * 
 * ============================================================================
 * WHY ONE HALF IS ALWAYS SORTED
 * ============================================================================
 * 
 * ROTATION CONCEPT:
 * Original:  [0, 1, 2, 3, 4, 5, 6]
 * Rotate 3: [4, 5, 6, 0, 1, 2, 3]
 *            ↑sorted↑  ↑sorted↑
 * 
 * At any mid point: 
 * - If arr[left] <= arr[mid]: Left is continuous (sorted)
 * - Else: Right must be continuous (sorted)
 * 
 * Why? Because there's only ONE rotation point (pivot).
 * The array is split into two sorted subarrays. 
 * 
 * IMPOSSIBLE CASE:
 * Both halves unsorted would require multiple rotation points,
 * which contradicts the problem:  array is rotated ONCE.
 * 
 * ============================================================================
 * VISUALIZATION OF DIFFERENT ROTATIONS
 * ============================================================================
 * 
 * Original: [1, 2, 3, 4, 5, 6, 7]
 * 
 * Rotate 0: [1, 2, 3, 4, 5, 6, 7] (no rotation)
 * Rotate 1: [2, 3, 4, 5, 6, 7, 1]
 * Rotate 2: [3, 4, 5, 6, 7, 1, 2]
 * Rotate 3: [4, 5, 6, 7, 1, 2, 3]
 * Rotate 4: [5, 6, 7, 1, 2, 3, 4]
 * Rotate 5: [6, 7, 1, 2, 3, 4, 5]
 * Rotate 6: [7, 1, 2, 3, 4, 5, 6]
 * 
 * In EACH case, one half is always sorted! 
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. CIRCULAR BUFFERS:
 *    Ring buffer with sorted data that wraps around
 * 
 * 2. CACHE SYSTEMS:
 *    LRU cache with rotation for recency
 * 
 * 3. LOAD BALANCING:
 *    Round-robin server selection with sorted server IDs
 * 
 * 4. TIME ZONES:
 *    Searching sorted events that wrap around midnight
 * 
 * 5. CIRCULAR QUEUES:
 *    Priority queue implementations with rotation
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - Find minimum in rotated sorted array
 * - Search in rotated sorted array II (with duplicates)
 * - Find rotation count
 * - Check if array is rotated sorted
 * - Median of rotated sorted arrays
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sra = SearchRotatedArray()
    
    println("=== Testing Search in Rotated Sorted Array ===\n")
    
    val arr = intArrayOf(4, 5, 6, 7, 0, 1, 2)
    println("Array: ${arr.contentToString()}\n")
    
    // Test 1: Target in rotated part
    println("Test 1: Search for 0")
    println("Result: ${sra.search(arr, 0)}")
    println("Expected: 4\n")
    
    // Test 2: Target in sorted part
    println("Test 2: Search for 5")
    println("Result: ${sra.search(arr, 5)}")
    println("Expected: 1\n")
    
    // Test 3: Target not found
    println("Test 3: Search for 3")
    println("Result: ${sra.search(arr, 3)}")
    println("Expected: -1\n")
    
    // Test 4: Target at beginning
    println("Test 4: Search for 4")
    println("Result: ${sra.search(arr, 4)}")
    println("Expected: 0\n")
    
    // Test 5: Target at end
    println("Test 5: Search for 2")
    println("Result: ${sra.search(arr, 2)}")
    println("Expected: 6\n")
    
    // Test 6: No rotation
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 6: No rotation - ${arr2.contentToString()}, search for 3")
    println("Result: ${sra.search(arr2, 3)}")
    println("Expected: 2\n")
    
    // Test 7: Single element found
    val arr3 = intArrayOf(1)
    println("Test 7: Single element [1], search for 1")
    println("Result: ${sra.search(arr3, 1)}")
    println("Expected: 0\n")
    
    // Test 8: Single element not found
    println("Test 8: Single element [1], search for 0")
    println("Result: ${sra.search(arr3, 0)}")
    println("Expected: -1\n")
    
    // Test 9: Two elements
    val arr4 = intArrayOf(3, 1)
    println("Test 9: Two elements ${arr4.contentToString()}, search for 1")
    println("Result: ${sra.search(arr4, 1)}")
    println("Expected: 1\n")
    
    // Test 10: Using recursive method
    println("Test 10: Recursive search in ${arr. contentToString()}, target = 0")
    println("Result: ${sra.searchRecursive(arr, 0)}")
    println("Expected: 4\n")
    
    // Test 11: Using pivot method
    println("Test 11: Pivot method in ${arr.contentToString()}, target = 5")
    println("Result: ${sra.searchUsingPivot(arr, 5)}")
    println("Expected: 1\n")
    
    // Test 12: Negative numbers
    val arr5 = intArrayOf(4, 5, 6, 7, -2, -1, 0, 1, 2)
    println("Test 12: With negatives ${arr5.contentToString()}, search for -1")
    println("Result: ${sra.search(arr5, -1)}")
    println("Expected:  5\n")
}
