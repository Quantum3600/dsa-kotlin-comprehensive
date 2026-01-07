/**
 * ============================================================================
 * PROBLEM: Lower Bound
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the lower bound of a target value in a sorted array.  Lower bound is 
 * the smallest index i such that arr[i] >= target.  If all elements are 
 * smaller than target, return n (array size).
 * 
 * INPUT FORMAT:
 * - A sorted array of integers: arr = [1, 2, 4, 4, 5, 6, 8]
 * - A target integer: target = 4
 * 
 * OUTPUT FORMAT: 
 * - Index of lower bound:  2 (first index where arr[i] >= 4)
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
 * Think of finding the first person in a line whose height is >= 170cm.
 * If everyone is sorted by height, you can use binary search! 
 * 
 * DIFFERENCE FROM REGULAR BINARY SEARCH:
 * - Regular binary search: Find exact match
 * - Lower bound: Find first element >= target (may not be exact match)
 * 
 * KEY INSIGHT:
 * We need to find the LEFTMOST position where arr[i] >= target.
 * Even if we find an exact match, there might be duplicates to the left! 
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1, answer = n (default if not found)
 * 2. While left <= right:
 *    a. Calculate mid = left + (right - left) / 2
 *    b. If arr[mid] >= target: 
 *       - This could be answer, store it
 *       - But check left side for smaller index
 *       - Set right = mid - 1
 *    c. If arr[mid] < target:
 *       - Target must be on right
 *       - Set left = mid + 1
 * 3. Return answer
 * 
 * VISUAL EXAMPLE: 
 * arr = [1, 2, 4, 4, 5, 6, 8], target = 4
 * 
 * Step 1: [1, 2, 4, 4, 5, 6, 8]
 *          L        M        R
 *          mid = 3, arr[3] = 4 >= 4
 *          ans = 3, search left for better answer
 * 
 * Step 2: [1, 2, 4]
 *          L  M  R
 *          mid = 1, arr[1] = 2 < 4
 *          search right
 * 
 * Step 3: [4]
 *          LMR
 *          mid = 2, arr[2] = 4 >= 4
 *          ans = 2, search left
 * 
 * Step 4: left > right, exit
 * 
 * OUTPUT: 2 ✓ (first index where element >= 4)
 * 
 * WHY NOT JUST USE BINARY SEARCH?
 * Regular binary search stops at any occurrence of target.
 * Lower bound specifically needs the LEFTMOST valid position.
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log n)
 * - Binary search eliminates half the space each iteration
 * - Same as regular binary search
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only uses constant extra space (left, right, mid, answer)
 * - No recursion, no extra data structures
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class LowerBound {
    
    /**
     * Find lower bound (smallest index where arr[i] >= target)
     * 
     * @param arr Sorted array of integers
     * @param target Value to find lower bound for
     * @return Index of lower bound, or n if all elements < target
     */
    fun lowerBound(arr: IntArray, target: Int): Int {
        // Initialize search boundaries
        var left = 0
        var right = arr.size - 1
        
        // Default answer:  if all elements are smaller than target
        // return array size (indicating insertion position at end)
        var answer = arr.size
        
        // Binary search for leftmost position where arr[i] >= target
        while (left <= right) {
            // Calculate middle index (avoiding overflow)
            val mid = left + (right - left) / 2
            
            // If middle element is >= target, it's a potential answer
            if (arr[mid] >= target) {
                // Store this as potential answer
                answer = mid
                
                // But there might be a better (smaller) index on the left
                // Continue searching left half
                right = mid - 1
            } else {
                // arr[mid] < target
                // Lower bound must be on the right side
                left = mid + 1
            }
        }
        
        // Return the leftmost index where arr[i] >= target
        return answer
    }
    
    /**
     * Alternative implementation using STL-style (returns iterator position)
     * This matches C++ STL lower_bound behavior
     */
    fun lowerBoundSTL(arr: IntArray, target: Int): Int {
        return arr.indexOfFirst { it >= target }. let {
            if (it == -1) arr.size else it
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Finding lower bound of existing element
 * INPUT: arr = [1, 2, 4, 4, 4, 5, 6], target = 4
 * 
 * Iteration 1:
 * - left = 0, right = 6, mid = 3
 * - arr[3] = 4 >= 4?  YES
 * - answer = 3, search left
 * - right = 2
 * 
 * Iteration 2:
 * - left = 0, right = 2, mid = 1
 * - arr[1] = 2 >= 4? NO
 * - search right
 * - left = 2
 * 
 * Iteration 3:
 * - left = 2, right = 2, mid = 2
 * - arr[2] = 4 >= 4? YES
 * - answer = 2, search left
 * - right = 1
 * 
 * left > right, exit
 * OUTPUT: 2 ✓ (first occurrence of 4)
 * 
 * ---
 * 
 * Example 2: Target not in array (between elements)
 * INPUT: arr = [1, 2, 4, 6, 8], target = 5
 * 
 * Iteration 1:
 * - mid = 2, arr[2] = 4 < 5
 * - left = 3
 * 
 * Iteration 2:
 * - mid = 3, arr[3] = 6 >= 5
 * - answer = 3, right = 2
 * 
 * left > right, exit
 * OUTPUT: 3 ✓ (index of 6, first element >= 5)
 * 
 * ---
 * 
 * Example 3: Target larger than all elements
 * INPUT: arr = [1, 2, 3, 4], target = 10
 * 
 * All elements < 10, binary search eliminates all indices
 * OUTPUT: 4 (array size) ✓
 * 
 * ---
 * 
 * Example 4: Target smaller than all elements
 * INPUT:  arr = [5, 6, 7, 8], target = 3
 * 
 * First element 5 >= 3
 * OUTPUT: 0 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Return 0 (insert at position 0)
 * 2. Single element arr=[5], target=3: Return 0
 * 3. Single element arr=[5], target=5: Return 0 (first occurrence)
 * 4. Single element arr=[5], target=7: Return 1 (insert at end)
 * 5. All duplicates arr=[3,3,3,3], target=3: Return 0
 * 6. Target at start: Return 0
 * 7. Target at end: Return last index or size
 * 8. Target smaller than all: Return 0
 * 9. Target larger than all: Return size
 * 10.  Negative numbers: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Forgetting to initialize answer
 * ❌ var answer:  Int (uninitialized)
 * ✅ var answer = arr.size (default for "not found")
 * 
 * MISTAKE 2: Wrong condition check
 * ❌ if (arr[mid] == target) // Only checks exact match
 * ✅ if (arr[mid] >= target) // Checks >= for lower bound
 * 
 * MISTAKE 3: Not searching left after finding valid position
 * ❌ if (arr[mid] >= target) return mid // Returns any valid position
 * ✅ if (arr[mid] >= target) { answer = mid; right = mid - 1 }
 * 
 * MISTAKE 4: Confusing with upper bound
 * - Lower bound: arr[i] >= target (first element NOT LESS than target)
 * - Upper bound: arr[i] > target (first element GREATER than target)
 * 
 * MISTAKE 5: Wrong return for "not found"
 * ❌ return -1 // Wrong!  Lower bound should return insertion position
 * ✅ return arr.size // Correct: insert at end
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER ALGORITHMS
 * ============================================================================
 * 
 * LOWER BOUND vs UPPER BOUND:
 * arr = [1, 2, 4, 4, 4, 5], target = 4
 * - Lower bound: 2 (first 4)
 * - Upper bound: 5 (first element > 4, which is 5)
 * 
 * LOWER BOUND vs BINARY SEARCH:
 * - Binary search:  Finds ANY occurrence (or -1)
 * - Lower bound:  Finds FIRST valid position (or size)
 * 
 * APPLICATIONS:
 * 1. Finding first occurrence of element
 * 2. Finding insertion position to maintain sorted order
 * 3. Range queries (with upper bound)
 * 4. Implementing std::set operations
 * 5. Binary search on answer problems
 * 
 * ============================================================================
 * WHEN TO USE LOWER BOUND
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Need to find first element >= target
 * ✅ Need insertion position in sorted array
 * ✅ Finding start of range in sorted array
 * ✅ Implementing bisect_left (Python) equivalent
 * ✅ Need to count elements >= target
 * 
 * DON'T USE WHEN: 
 * ❌ Need last element <= target (use floor instead)
 * ❌ Need first element > target (use upper bound instead)
 * ❌ Array is unsorted
 * ❌ Just need to check existence (regular binary search)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val lb = LowerBound()
    
    println("=== Testing Lower Bound ===\n")
    
    // Test 1: Basic lower bound
    val arr1 = intArrayOf(1, 2, 4, 4, 5, 6, 8)
    println("Test 1: arr = ${arr1.contentToString()}")
    println("Lower bound of 4: ${lb.lowerBound(arr1, 4)}")
    println("Expected: 2 (first occurrence)\n")
    
    // Test 2: Target not in array (between elements)
    println("Test 2: arr = ${arr1.contentToString()}")
    println("Lower bound of 3: ${lb.lowerBound(arr1, 3)}")
    println("Expected: 2 (index of 4, first element >= 3)\n")
    
    // Test 3: Target smaller than all elements
    println("Test 3: arr = ${arr1.contentToString()}")
    println("Lower bound of 0: ${lb.lowerBound(arr1, 0)}")
    println("Expected: 0\n")
    
    // Test 4: Target larger than all elements
    println("Test 4: arr = ${arr1.contentToString()}")
    println("Lower bound of 10: ${lb. lowerBound(arr1, 10)}")
    println("Expected: 7 (array size)\n")
    
    // Test 5: All duplicates
    val arr2 = intArrayOf(3, 3, 3, 3, 3)
    println("Test 5: arr = ${arr2.contentToString()}")
    println("Lower bound of 3: ${lb. lowerBound(arr2, 3)}")
    println("Expected: 0\n")
    
    // Test 6: Single element (found)
    val arr3 = intArrayOf(5)
    println("Test 6: arr = ${arr3.contentToString()}")
    println("Lower bound of 5: ${lb.lowerBound(arr3, 5)}")
    println("Expected: 0\n")
    
    // Test 7: Single element (not found, smaller)
    println("Test 7: arr = ${arr3.contentToString()}")
    println("Lower bound of 3: ${lb.lowerBound(arr3, 3)}")
    println("Expected: 0\n")
    
    // Test 8: Single element (not found, larger)
    println("Test 8: arr = ${arr3.contentToString()}")
    println("Lower bound of 10: ${lb.lowerBound(arr3, 10)}")
    println("Expected: 1\n")
    
    // Test 9: Negative numbers
    val arr4 = intArrayOf(-10, -5, -3, 0, 2, 5)
    println("Test 9: arr = ${arr4.contentToString()}")
    println("Lower bound of -4: ${lb.lowerBound(arr4, -4)}")
    println("Expected: 2 (index of -3)\n")
    
    // Test 10: Use case - finding insert position
    println("Test 10: Insert position demonstration")
    println("arr = ${arr1.contentToString()}")
    val insertPos = lb.lowerBound(arr1, 7)
    println("To insert 7 and keep array sorted, insert at index:  $insertPos")
    println("Expected: 6 (between 6 and 8)\n")
}
