/**
 * ============================================================================
 * PROBLEM:  Search Insert Position
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE: #35
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a sorted array of distinct integers and a target value, return the 
 * index if the target is found. If not, return the index where it would be 
 * if it were inserted in order.
 * 
 * You must write an algorithm with O(log n) runtime complexity.
 * 
 * INPUT FORMAT:
 * - A sorted array of distinct integers:  arr = [1, 3, 5, 6]
 * - A target integer: target = 5
 * 
 * OUTPUT FORMAT: 
 * - Index where target is or should be inserted: 2
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^4
 * - -10^4 <= arr[i] <= 10^4
 * - Array contains distinct values sorted in ascending order
 * - -10^4 <= target <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is essentially finding the **Lower Bound**! 
 * - If target exists: return its index
 * - If target doesn't exist: return position where it should be inserted
 * 
 * KEY INSIGHT:
 * Lower bound gives us exactly what we need: 
 * - First position where element >= target
 * - This is either the target itself, or the insertion position
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1, answer = n
 * 2. While left <= right:
 *    a. Calculate mid = left + (right - left) / 2
 *    b. If arr[mid] >= target:
 *       - Could be the answer, store it
 *       - Search left for better position
 *       - right = mid - 1
 *    c. Else (arr[mid] < target):
 *       - Insert position must be on right
 *       - left = mid + 1
 * 3. Return answer
 * 
 * VISUAL EXAMPLE 1: Target exists
 * arr = [1, 3, 5, 6], target = 5
 * 
 * Step 1: [1, 3, 5, 6]
 *          L  M     R
 *          mid = 1, arr[1] = 3 < 5
 *          search right
 * 
 * Step 2: [5, 6]
 *          LM  R
 *          mid = 2, arr[2] = 5 >= 5
 *          answer = 2, search left
 * 
 * Step 3: left > right, exit
 * OUTPUT: 2 ✓ (found at index 2)
 * 
 * VISUAL EXAMPLE 2: Target doesn't exist
 * arr = [1, 3, 5, 6], target = 2
 * 
 * Step 1: mid = 1, arr[1] = 3 >= 2
 *         answer = 1, search left
 * 
 * Step 2: mid = 0, arr[0] = 1 < 2
 *         search right
 * 
 * Step 3: left > right, exit
 * OUTPUT: 1 ✓ (should insert at index 1, between 1 and 3)
 * 
 * VISUAL EXAMPLE 3: Insert at end
 * arr = [1, 3, 5, 6], target = 7
 * 
 * All elements < 7, so answer remains n = 4
 * OUTPUT: 4 ✓ (insert at end)
 * 
 * WHY THIS IS LOWER BOUND:
 * - Lower bound finds first position where arr[i] >= target
 * - If target exists, this is its position
 * - If not, this is where it should be inserted to maintain order
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search divides search space in half each iteration
 * - Maximum iterations: log₂(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra variables
 * - No recursion or additional structures
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class SearchInsertPosition {
    
    /**
     * Find the index where target is found or should be inserted
     * 
     * @param arr Sorted array of distinct integers
     * @param target Value to search or insert
     * @return Index of target or insertion position
     */
    fun searchInsert(arr: IntArray, target:  Int): Int {
        var left = 0
        var right = arr.size - 1
        
        // Default answer:  insert at end if all elements < target
        var answer = arr.size
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            // If middle element >= target
            if (arr[mid] >= target) {
                // This could be the answer (either exact match or insert position)
                answer = mid
                
                // But check if there's a better position on the left
                right = mid - 1
            } else {
                // arr[mid] < target, look on the right
                left = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Alternative approach: Direct comparison with exact match check first
     * This is slightly more intuitive but essentially the same
     */
    fun searchInsertAlternative(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            when {
                arr[mid] == target -> return mid  // Found exact match
                arr[mid] < target -> left = mid + 1  // Search right
                else -> right = mid - 1  // Search left
            }
        }
        
        // At this point, left is the insert position
        // Because when loop exits, left = right + 1
        // and left points to first element > target
        return left
    }
    
    /**
     * Using stdlib lower bound concept
     * This uses the same logic as C++ STL lower_bound
     */
    fun searchInsertUsingLowerBound(arr:  IntArray, target: Int): Int {
        return LowerBound().lowerBound(arr, target)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Target exists in middle
 * INPUT: arr = [1, 3, 5, 6], target = 5
 * 
 * Iteration 1:
 * - left = 0, right = 3, mid = 1
 * - arr[1] = 3 < 5
 * - left = 2
 * 
 * Iteration 2:
 * - left = 2, right = 3, mid = 2
 * - arr[2] = 5 >= 5
 * - answer = 2, right = 1
 * 
 * left > right, exit
 * OUTPUT: 2 ✓
 * 
 * ---
 * 
 * Example 2: Target should be inserted in middle
 * INPUT: arr = [1, 3, 5, 6], target = 2
 * 
 * Iteration 1:
 * - mid = 1, arr[1] = 3 >= 2
 * - answer = 1, right = 0
 * 
 * Iteration 2:
 * - mid = 0, arr[0] = 1 < 2
 * - left = 1
 * 
 * left > right, exit
 * OUTPUT: 1 ✓ (insert between 1 and 3)
 * 
 * ---
 * 
 * Example 3: Target should be inserted at start
 * INPUT: arr = [1, 3, 5, 6], target = 0
 * 
 * Iteration 1:
 * - mid = 1, arr[1] = 3 >= 0
 * - answer = 1, right = 0
 * 
 * Iteration 2:
 * - mid = 0, arr[0] = 1 >= 0
 * - answer = 0, right = -1
 * 
 * left > right, exit
 * OUTPUT:  0 ✓
 * 
 * ---
 * 
 * Example 4: Target should be inserted at end
 * INPUT: arr = [1, 3, 5, 6], target = 7
 * 
 * Iteration 1:
 * - mid = 1, arr[1] = 3 < 7
 * - left = 2
 * 
 * Iteration 2:
 * - mid = 2, arr[2] = 5 < 7
 * - left = 3
 * 
 * Iteration 3:
 * - mid = 3, arr[3] = 6 < 7
 * - left = 4
 * 
 * left > right, exit, answer = 4 (default)
 * OUTPUT: 4 ✓
 * 
 * ---
 * 
 * Example 5: Single element array
 * INPUT: arr = [1], target = 0
 * 
 * Iteration 1:
 * - mid = 0, arr[0] = 1 >= 0
 * - answer = 0, right = -1
 * 
 * OUTPUT: 0 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array (not in constraints): Would return 0
 * 2. Single element, target equals element: Return 0
 * 3. Single element, target < element: Return 0
 * 4. Single element, target > element: Return 1
 * 5. Target smaller than all elements: Return 0
 * 6. Target larger than all elements: Return n
 * 7. Target equals first element: Return 0
 * 8. Target equals last element: Return n-1
 * 9. Negative numbers: Works correctly
 * 10. Large arrays: O(log n) ensures efficiency
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Not initializing answer properly
 * ❌ var answer = 0  // Wrong for inserting at end
 * ✅ var answer = arr.size  // Correct default for end insertion
 * 
 * MISTAKE 2: Wrong condition
 * ❌ if (arr[mid] == target) return mid; else ...   
 *    // Doesn't handle insert position correctly
 * ✅ if (arr[mid] >= target) answer = mid; right = mid - 1
 * 
 * MISTAKE 3: In alternative approach, returning wrong variable
 * ❌ return right  // Wrong!  right is one less than insert position
 * ✅ return left  // Correct! left is the insert position
 * 
 * MISTAKE 4: Not understanding why left is the answer
 * When loop exits with no match: 
 * - left = right + 1
 * - All elements before left are < target
 * - All elements from left onwards are >= target
 * - Therefore left is the insert position! 
 * 
 * MISTAKE 5: Trying to use regular binary search
 * ❌ Regular binary search returns -1 if not found
 * ✅ This problem needs the insertion position, not -1
 * 
 * ============================================================================
 * WHY LEFT IS THE INSERT POSITION (Detailed Explanation)
 * ============================================================================
 * 
 * At any point in binary search: 
 * - Elements in [0, left) are all < target
 * - Elements in (right, n-1] are all >= target
 * 
 * When loop exits (left > right):
 * - left = right + 1
 * - All elements before left are < target
 * - Element at left (if exists) is >= target
 * - Therefore, left is the correct insert position! 
 * 
 * Example: arr = [1, 3, 5, 6], target = 2
 * After search: 
 * - [1] are < 2 (indices 0)
 * - [3, 5, 6] are >= 2 (indices 1, 2, 3)
 * - Insert position = 1 (between 1 and 3)
 * - left = 1 ✓
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER PROBLEMS
 * ============================================================================
 * 
 * THIS PROBLEM IS EXACTLY: 
 * - Lower Bound
 * - C++ STL lower_bound()
 * - Python bisect.bisect_left()
 * 
 * SIMILAR PROBLEMS:
 * - Find First and Last Position of Element
 * - First Bad Version
 * - Valid Perfect Square
 * 
 * APPLICATIONS:
 * 1. Maintaining sorted order in dynamic array
 * 2. Database index insertion
 * 3. Event scheduling (insert event at correct time)
 * 4. Merge operations in merge sort
 * 5. Building balanced trees
 * 
 * ============================================================================
 * VARIATIONS
 * ============================================================================
 * 
 * VARIATION 1: Array with duplicates
 * - Still works!  Returns first position >= target
 * 
 * VARIATION 2: Insert and maintain order
 * ```kotlin
 * val pos = searchInsert(arr, target)
 * arr = arr.copyOf(arr.size + 1)
 * System.arraycopy(arr, pos, arr, pos + 1, arr.size - pos - 1)
 * arr[pos] = target
 * ```
 * 
 * VARIATION 3: Count elements less than target
 * - Answer = searchInsert(arr, target)
 * - This gives count of elements < target
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sip = SearchInsertPosition()
    
    println("=== Testing Search Insert Position ===\n")
    
    val arr = intArrayOf(1, 3, 5, 6)
    println("Array: ${arr.contentToString()}\n")
    
    // Test 1: Target exists
    println("Test 1: Search for 5")
    println("Result: ${sip.searchInsert(arr, 5)}")
    println("Expected: 2 (found at index 2)\n")
    
    // Test 2: Insert in middle
    println("Test 2: Search for 2")
    println("Result: ${sip.searchInsert(arr, 2)}")
    println("Expected: 1 (insert between 1 and 3)\n")
    
    // Test 3: Insert at start
    println("Test 3: Search for 0")
    println("Result: ${sip.searchInsert(arr, 0)}")
    println("Expected: 0 (insert at beginning)\n")
    
    // Test 4: Insert at end
    println("Test 4: Search for 7")
    println("Result: ${sip.searchInsert(arr, 7)}")
    println("Expected: 4 (insert at end)\n")
    
    // Test 5: Another middle position
    println("Test 5: Search for 4")
    println("Result: ${sip.searchInsert(arr, 4)}")
    println("Expected: 2 (insert before 5)\n")
    
    // Test 6: First element
    println("Test 6: Search for 1")
    println("Result: ${sip. searchInsert(arr, 1)}")
    println("Expected:  0 (found at index 0)\n")
    
    // Test 7: Last element
    println("Test 7: Search for 6")
    println("Result: ${sip.searchInsert(arr, 6)}")
    println("Expected: 3 (found at index 3)\n")
    
    // Test 8: Single element array
    val arr2 = intArrayOf(1)
    println("Test 8: Single element [1], search for 0")
    println("Result: ${sip.searchInsert(arr2, 0)}")
    println("Expected: 0\n")
    
    // Test 9: Alternative method
    println("Test 9: Using alternative method for target 2")
    println("Result: ${sip.searchInsertAlternative(arr, 2)}")
    println("Expected:  1\n")
    
    // Test 10: Negative numbers
    val arr3 = intArrayOf(-10, -5, 0, 5, 10)
    println("Test 10: Array with negatives:  ${arr3.contentToString()}")
    println("Search for -3")
    println("Result: ${sip.searchInsert(arr3, -3)}")
    println("Expected: 2 (insert before 0)\n")
    
    // Test 11: Demonstrate insert operation
    println("Test 11: Demonstrate actual insertion")
    var mutableArr = intArrayOf(1, 3, 5, 6).toMutableList()
    val target = 2
    val pos = sip.searchInsert(mutableArr. toIntArray(), target)
    mutableArr. add(pos, target)
    println("Original:  [1, 3, 5, 6]")
    println("After inserting 2 at position $pos:  ${mutableArr}")
    println("Expected: [1, 2, 3, 5, 6]\n")
}
