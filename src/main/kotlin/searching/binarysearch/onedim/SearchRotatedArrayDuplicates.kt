/**
 * ============================================================================
 * PROBLEM:  Search in Rotated Sorted Array II (With Duplicates)
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE: #81
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A sorted array that may contain duplicates has been rotated at an unknown 
 * pivot.  Search for a target value in this rotated sorted array.  Return true 
 * if found, false otherwise.
 * 
 * The key difference from version I:  Array may contain DUPLICATES.
 * 
 * INPUT FORMAT:
 * - A rotated sorted array with duplicates:  arr = [2, 5, 6, 0, 0, 1, 2]
 * - A target integer: target = 0
 * 
 * OUTPUT FORMAT:
 * - true if target exists, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 5000
 * - -10^4 <= arr[i] <= 10^4
 * - Array may contain duplicates
 * - Array was originally sorted then rotated
 * - -10^4 <= target <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is like the rotated array problem, but duplicates make it TRICKY! 
 * 
 * THE PROBLEM WITH DUPLICATES:
 * arr = [1, 0, 1, 1, 1]
 *        L     M     R
 * 
 * arr[L] = arr[M] = arr[R] = 1
 * We CAN'T tell which half is sorted!  
 * 
 * SOLUTION:
 * When arr[left] == arr[mid] == arr[right]: 
 * - We can't determine which half is sorted
 * - Skip the duplicates by moving pointers: 
 *   left++ and right--
 * - This worst case becomes O(n)
 * 
 * Otherwise, use the same logic as rotated array without duplicates. 
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left <= right:
 *    a. Calculate mid
 *    b. If arr[mid] == target:  return true
 *    
 *    c. Handle duplicates:
 *       If arr[left] == arr[mid] == arr[right]:
 *         left++, right--
 *         continue
 *    
 *    d.  Determine which half is sorted:
 *       If arr[left] <= arr[mid]:  (left half sorted)
 *         - If target in [arr[left], arr[mid]): search left
 *         - Else: search right
 *       Else:  (right half sorted)
 *         - If target in (arr[mid], arr[right]]:  search right
 *         - Else: search left
 * 3. Return false (not found)
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 5, 6, 0, 0, 1, 2], target = 0
 * 
 * Step 1: [2, 5, 6, 0, 0, 1, 2]
 *          L        M        R
 *          arr[L]=2, arr[M]=0, arr[R]=2
 *          Not all equal, so we can proceed
 *          Right half might be sorted?  Check arr[M] <= arr[R]: 0 <= 2
 *          Is target in (0, 2]? NO (we need 0, but range is exclusive of mid)
 *          Actually arr[M] == 0 == target, FOUND!  ✓
 * 
 * WORST CASE EXAMPLE:
 * arr = [1, 1, 1, 1, 1, 1, 1], target = 2
 * All elements same, need to check each:  O(n)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Best Case: O(log n) - No duplicates or few duplicates
 * - Average Case: O(log n) - Reasonable number of duplicates
 * - Worst Case: O(n) - All elements are duplicates
 * 
 * WHY O(n) WORST CASE?
 * When arr[left] == arr[mid] == arr[right], we must skip elements. 
 * In worst case (all duplicates), we might check all n elements.
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class SearchRotatedArrayDuplicates {
    
    /**
     * Search for target in rotated sorted array with duplicates
     * 
     * @param arr Rotated sorted array (may contain duplicates)
     * @param target Value to search for
     * @return true if found, false otherwise
     */
    fun search(arr:  IntArray, target: Int): Boolean {
        var left = 0
        var right = arr.size - 1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            // Found target
            if (arr[mid] == target) {
                return true
            }
            
            // Handle duplicates:  Can't determine which half is sorted
            // This is the KEY difference from version without duplicates
            if (arr[left] == arr[mid] && arr[mid] == arr[right]) {
                // Skip duplicates from both ends
                left++
                right--
                continue
            }
            
            // Determine which half is sorted (same logic as version I)
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                
                if (target >= arr[left] && target < arr[mid]) {
                    // Target is in left sorted half
                    right = mid - 1
                } else {
                    // Target is in right half
                    left = mid + 1
                }
            } else {
                // Right half is sorted
                
                if (target > arr[mid] && target <= arr[right]) {
                    // Target is in right sorted half
                    left = mid + 1
                } else {
                    // Target is in left half
                    right = mid - 1
                }
            }
        }
        
        return false
    }
    
    /**
     * Alternative:  More explicit duplicate handling
     */
    fun searchAlternative(arr: IntArray, target: Int): Boolean {
        var left = 0
        var right = arr.size - 1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] == target) {
                return true
            }
            
            // Skip duplicates on left
            while (left < mid && arr[left] == arr[mid]) {
                left++
            }
            
            // Skip duplicates on right
            while (right > mid && arr[right] == arr[mid]) {
                right--
            }
            
            // Now apply standard rotated array logic
            if (arr[left] <= arr[mid]) {
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1
                } else {
                    left = mid + 1
                }
            } else {
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1
                } else {
                    right = mid - 1
                }
            }
        }
        
        return false
    }
    
    /**
     * Find index of target (returns -1 if not found)
     * Same logic but returns index instead of boolean
     */
    fun searchIndex(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] == target) {
                return mid
            }
            
            if (arr[left] == arr[mid] && arr[mid] == arr[right]) {
                left++
                right--
                continue
            }
            
            if (arr[left] <= arr[mid]) {
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1
                } else {
                    left = mid + 1
                }
            } else {
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1
                } else {
                    right = mid - 1
                }
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
 * Example 1: With duplicates, target exists
 * INPUT: arr = [2, 5, 6, 0, 0, 1, 2], target = 0
 * 
 * Iteration 1:
 * - left = 0, right = 6, mid = 3
 * - arr[mid] = 0 == target
 * - FOUND! Return true ✓
 * 
 * OUTPUT: true ✓
 * 
 * ---
 * 
 * Example 2: Worst case - all duplicates
 * INPUT:  arr = [1, 1, 1, 1, 1, 1], target = 2
 * 
 * Iteration 1:
 * - arr[left] = arr[mid] = arr[right] = 1
 * - Skip:  left++, right--
 * 
 * Iteration 2:
 * - Still all 1's, keep skipping
 * 
 * Eventually check all elements, none match 2
 * OUTPUT: false ✓
 * Time: O(n) in this case
 * 
 * ---
 * 
 * Example 3: Duplicates but can determine sorted half
 * INPUT: arr = [1, 0, 1, 1, 1], target = 0
 * 
 * Iteration 1:
 * - left = 0, right = 4, mid = 2
 * - arr[mid] = 1
 * - arr[left] = 1, arr[mid] = 1, arr[right] = 1
 * - All equal! Skip:  left = 1, right = 3
 * 
 * Iteration 2:
 * - left = 1, right = 3, mid = 2
 * - arr[mid] = 1
 * - arr[left] = 0, arr[mid] = 1, not all equal
 * - arr[left] = 0 <= arr[mid] = 1, left half sorted
 * - Is 0 in [0, 1)? YES
 * - Search left:  right = 1
 * 
 * Iteration 3:
 * - left = 1, right = 1, mid = 1
 * - arr[mid] = 0 == target
 * - FOUND! ✓
 * 
 * OUTPUT: true ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. All duplicates: O(n) time, returns correct answer
 * 2. No duplicates: Same as rotated array I, O(log n)
 * 3. Single element: Returns true/false correctly
 * 4. Two elements both same: Handles correctly
 * 5. Target equals duplicate elements: Finds it
 * 6. Duplicates at boundaries: Skips correctly
 * 7. Duplicates in middle: Handles correctly
 * 8. No rotation with duplicates: Works correctly
 * 9. Empty array (not in constraints): Would return false
 * 10. Target not in array: Returns false
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not handling duplicates special case
 * ❌ Using same logic as version I without duplicate check
 * ✅ Add check:  if (arr[left] == arr[mid] == arr[right])
 * 
 * MISTAKE 2: Only incrementing left or only decrementing right
 * ❌ if (arr[left] == arr[mid]) left++  // Might miss target
 * ✅ if (arr[left] == arr[mid] == arr[right]) { left++; right--; }
 * 
 * MISTAKE 3: Not using continue after skipping
 * ❌ Skipping but then applying normal logic
 * ✅ Skip and continue to next iteration
 * 
 * MISTAKE 4: Wrong boundary checks
 * Remember: arr[left] <= arr[mid] not arr[left] < arr[mid]
 * The <= is crucial for handling duplicates properly
 * 
 * MISTAKE 5: Expecting O(log n) in all cases
 * ❌ Assuming always O(log n)
 * ✅ Understanding worst case is O(n) with duplicates
 * 
 * ============================================================================
 * KEY DIFFERENCES FROM VERSION I (No Duplicates)
 * ============================================================================
 * 
 * VERSION I (Unique elements):
 * - Always O(log n) time
 * - Can always determine which half is sorted
 * - No ambiguity
 * 
 * VERSION II (With duplicates):
 * - O(log n) average, O(n) worst case
 * - Sometimes can't determine sorted half
 * - Must skip duplicates
 * 
 * THE CRITICAL CASE:
 * arr = [1, 3, 1, 1, 1]
 *        L  M        R
 * 
 * arr[L] = 1, arr[M] = 1, arr[R] = 1
 * Is left half [1,3,1] sorted? NO
 * Is right half [1,1,1] sorted? YES
 * But we can't tell just from arr[L], arr[M], arr[R]! 
 * 
 * Solution: Skip the duplicates at boundaries
 * 
 * ============================================================================
 * WHY WORST CASE IS O(n)
 * ============================================================================
 * 
 * WORST CASE ARRAY:
 * [1, 1, 1, 1, 1, 1, 1, 1, 1, 1], target = 2
 * 
 * Every iteration: 
 * - arr[left] == arr[mid] == arr[right] = 1
 * - Must skip:  left++, right--
 * - Each iteration only eliminates 2 elements
 * 
 * Total iterations: n/2 = O(n)
 * 
 * This is UNAVOIDABLE with duplicates!  
 * We can't do better than O(n) in worst case because we might need 
 * to check every element to ensure target isn't hidden among duplicates. 
 * 
 * ============================================================================
 * WHEN TO USE THIS VS LINEAR SEARCH
 * ============================================================================
 * 
 * USE BINARY SEARCH WHEN:
 * ✅ Few duplicates (most elements unique)
 * ✅ Average case matters more than worst case
 * ✅ Array size is large
 * ✅ Want to leverage sorted property when possible
 * 
 * USE LINEAR SEARCH WHEN:
 * ✅ Many duplicates
 * ✅ Small array (< 100 elements)
 * ✅ Simplicity preferred over optimization
 * ✅ Worst case performance critical
 * 
 * REALITY: 
 * For this problem with duplicates, binary search gives better 
 * average case (O(log n)) even though worst case is same (O(n)).
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. LOG FILES WITH TIMESTAMPS: 
 *    Rotated log files where timestamps may repeat
 * 
 * 2. CIRCULAR BUFFERS:
 *    Ring buffers with duplicate priority values
 * 
 * 3. DISTRIBUTED SYSTEMS:
 *    Searching replicated data across rotated partitions
 * 
 * 4. VERSION CONTROL:
 *    Finding commits in rotated branch with duplicate hashes
 * 
 * 5. TIME-SERIES DATA:
 *    Searching measurements where values may repeat
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - Search in rotated sorted array (without duplicates)
 * - Find minimum in rotated sorted array II
 * - Find rotation count in array with duplicates
 * - Remove duplicates from sorted array
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val srad = SearchRotatedArrayDuplicates()
    
    println("=== Testing Search in Rotated Sorted Array with Duplicates ===\n")
    
    // Test 1: Basic case with duplicates
    val arr1 = intArrayOf(2, 5, 6, 0, 0, 1, 2)
    println("Test 1: arr = ${arr1.contentToString()}, target = 0")
    println("Result: ${srad.search(arr1, 0)}")
    println("Expected: true\n")
    
    // Test 2: Target not found
    println("Test 2: arr = ${arr1.contentToString()}, target = 3")
    println("Result: ${srad.search(arr1, 3)}")
    println("Expected: false\n")
    
    // Test 3: All duplicates except target
    val arr2 = intArrayOf(1, 0, 1, 1, 1)
    println("Test 3: arr = ${arr2.contentToString()}, target = 0")
    println("Result:  ${srad.search(arr2, 0)}")
    println("Expected: true\n")
    
    // Test 4: Worst case - all same elements
    val arr3 = intArrayOf(1, 1, 1, 1, 1, 1, 1)
    println("Test 4: Worst case - ${arr3.contentToString()}, target = 2")
    println("Result: ${srad. search(arr3, 2)}")
    println("Expected: false (O(n) time in this case)\n")
    
    // Test 5: All same, target exists
    val arr4 = intArrayOf(1, 1, 1, 1, 1)
    println("Test 5: arr = ${arr4.contentToString()}, target = 1")
    println("Result: ${srad.search(arr4, 1)}")
    println("Expected: true\n")
    
    // Test 6: Duplicates at boundaries
    val arr5 = intArrayOf(1, 1, 2, 3, 1, 1)
    println("Test 6: arr = ${arr5.contentToString()}, target = 3")
    println("Result: ${srad.search(arr5, 3)}")
    println("Expected: true\n")
    
    // Test 7: No rotation
    val arr6 = intArrayOf(1, 1, 2, 2, 3, 3)
    println("Test 7: No rotation - ${arr6.contentToString()}, target = 2")
    println("Result: ${srad. search(arr6, 2)}")
    println("Expected: true\n")
    
    // Test 8: Single element
    val arr7 = intArrayOf(1)
    println("Test 8: Single element [1], target = 1")
    println("Result: ${srad.search(arr7, 1)}")
    println("Expected: true\n")
    
    // Test 9: Two elements same
    val arr8 = intArrayOf(1, 1)
    println("Test 9: Two same elements ${arr8.contentToString()}, target = 1")
    println("Result: ${srad.search(arr8, 1)}")
    println("Expected: true\n")
    
    // Test 10: Get index instead of boolean
    println("Test 10: Find index in ${arr1.contentToString()}, target = 0")
    println("Result: ${srad.searchIndex(arr1, 0)}")
    println("Expected: 3 or 4 (any valid index)\n")
    
    // Test 11: Complex case
    val arr9 = intArrayOf(3, 1, 2, 3, 3, 3, 3)
    println("Test 11: Complex - ${arr9.contentToString()}, target = 2")
    println("Result: ${srad.search(arr9, 2)}")
    println("Expected: true\n")
    
    // Test 12: Using alternative method
    println("Test 12: Alternative method - ${arr2.contentToString()}, target = 0")
    println("Result: ${srad.searchAlternative(arr2, 0)}")
    println("Expected: true\n")
}
