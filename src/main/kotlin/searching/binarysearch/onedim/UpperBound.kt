/**
 * ============================================================================
 * PROBLEM:  Upper Bound
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the upper bound of a target value in a sorted array.  Upper bound is 
 * the smallest index i such that arr[i] > target.  If all elements are <= 
 * target, return n (array size).
 * 
 * INPUT FORMAT:
 * - A sorted array of integers:  arr = [1, 2, 4, 4, 5, 6, 8]
 * - A target integer: target = 4
 * 
 * OUTPUT FORMAT: 
 * - Index of upper bound:  4 (first index where arr[i] > 4, which is 5)
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
 * Think of finding the first person taller than 170cm in a height-sorted line.
 * You're looking for the first one that's STRICTLY greater, not equal! 
 * 
 * DIFFERENCE FROM LOWER BOUND:
 * - Lower bound: Find first element >= target (includes equal)
 * - Upper bound: Find first element > target (strictly greater)
 * 
 * KEY INSIGHT:
 * We need the LEFTMOST position where arr[i] > target.
 * Equal values are NOT included in the answer.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1, answer = n
 * 2. While left <= right:
 *    a. Calculate mid = left + (right - left) / 2
 *    b. If arr[mid] > target: 
 *       - This could be answer, store it
 *       - Check left side for smaller index
 *       - Set right = mid - 1
 *    c. If arr[mid] <= target: 
 *       - Upper bound must be on right
 *       - Set left = mid + 1
 * 3. Return answer
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 4, 4, 5, 6, 8], target = 4
 * 
 * Step 1: [1, 2, 4, 4, 5, 6, 8]
 *          L        M        R
 *          mid = 3, arr[3] = 4 > 4?  NO (equal)
 *          search right
 * 
 * Step 2: [5, 6, 8]
 *          L  M  R
 *          mid = 5, arr[5] = 6 > 4? YES
 *          ans = 5, search left
 * 
 * Step 3: [5]
 *          LMR
 *          mid = 4, arr[4] = 5 > 4? YES
 *          ans = 4, search left
 * 
 * Step 4: left > right, exit
 * 
 * OUTPUT: 4 ✓ (first index where element > 4, which is 5)
 * 
 * COMPARISON WITH LOWER BOUND:
 * arr = [1, 2, 4, 4, 4, 5, 6], target = 4
 * - Lower bound: 2 (first 4, arr[i] >= 4)
 * - Upper bound: 5 (first 5, arr[i] > 4)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search halves the search space each iteration
 * - Maximum log₂(n) iterations
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space used
 * - No recursion or additional data structures
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class UpperBound {
    
    /**
     * Find upper bound (smallest index where arr[i] > target)
     * 
     * @param arr Sorted array of integers
     * @param target Value to find upper bound for
     * @return Index of upper bound, or n if all elements <= target
     */
    fun upperBound(arr: IntArray, target: Int): Int {
        // Initialize search boundaries
        var left = 0
        var right = arr.size - 1
        
        // Default answer:  if all elements <= target
        // return array size (indicating position after last element)
        var answer = arr.size
        
        // Binary search for leftmost position where arr[i] > target
        while (left <= right) {
            // Calculate middle index (avoiding overflow)
            val mid = left + (right - left) / 2
            
            // If middle element is STRICTLY greater than target
            if (arr[mid] > target) {
                // Store this as potential answer
                answer = mid
                
                // But there might be a better (smaller) index on the left
                // Continue searching left half
                right = mid - 1
            } else {
                // arr[mid] <= target (includes equal case!)
                // Upper bound must be on the right side
                left = mid + 1
            }
        }
        
        // Return the leftmost index where arr[i] > target
        return answer
    }
    
    /**
     * Alternative implementation using STL-style
     * This matches C++ STL upper_bound behavior
     */
    fun upperBoundSTL(arr: IntArray, target: Int): Int {
        return arr.indexOfFirst { it > target }. let {
            if (it == -1) arr.size else it
        }
    }
    
    /**
     * Helper:  Count elements equal to target using bounds
     * Demonstrates practical use of lower and upper bounds
     */
    fun countOccurrences(arr: IntArray, target: Int): Int {
        val lb = LowerBound().lowerBound(arr, target)
        val ub = upperBound(arr, target)
        // If lower bound equals size, element doesn't exist
        return if (lb == arr.size) 0 else ub - lb
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Upper bound of existing element
 * INPUT: arr = [1, 2, 4, 4, 4, 5, 6], target = 4
 * 
 * Iteration 1:
 * - left = 0, right = 6, mid = 3
 * - arr[3] = 4 > 4? NO (equal)
 * - search right
 * - left = 4
 * 
 * Iteration 2:
 * - left = 4, right = 6, mid = 5
 * - arr[5] = 5 > 4? YES
 * - answer = 5, search left
 * - right = 4
 * 
 * Iteration 3:
 * - left = 4, right = 4, mid = 4
 * - arr[4] = 4 > 4? NO (equal)
 * - search right
 * - left = 5
 * 
 * left > right, exit
 * OUTPUT: 5 ✓ (first element after all 4's)
 * 
 * ---
 * 
 * Example 2: Target not in array
 * INPUT:  arr = [1, 2, 4, 6, 8], target = 5
 * 
 * Iteration 1:
 * - mid = 2, arr[2] = 4 > 5?  NO
 * - left = 3
 * 
 * Iteration 2:
 * - mid = 4, arr[4] = 8 > 5? YES
 * - answer = 4, right = 3
 * 
 * Iteration 3:
 * - mid = 3, arr[3] = 6 > 5? YES
 * - answer = 3, right = 2
 * 
 * left > right, exit
 * OUTPUT: 3 ✓ (index of 6, first element > 5)
 * 
 * ---
 * 
 * Example 3: All elements <= target
 * INPUT: arr = [1, 2, 3, 4], target = 10
 * 
 * All elements <= 10, no element > target
 * OUTPUT: 4 (array size) ✓
 * 
 * ---
 * 
 * Example 4: All elements > target
 * INPUT:  arr = [5, 6, 7, 8], target = 3
 * 
 * First element 5 > 3
 * OUTPUT: 0 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Return 0
 * 2. Single element arr=[5], target=3: Return 0 (5 > 3)
 * 3. Single element arr=[5], target=5: Return 1 (no element > 5)
 * 4. Single element arr=[5], target=7: Return 1
 * 5. All duplicates arr=[3,3,3,3], target=3: Return 4 (size)
 * 6. All elements less than target:  Return size
 * 7. All elements greater than target: Return 0
 * 8. Target at end of array: May return size or last index + 1
 * 9. Negative numbers: Works correctly
 * 10. Mixed positive/negative: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using >= instead of >
 * ❌ if (arr[mid] >= target) // This is lower bound, not upper bound!
 * ✅ if (arr[mid] > target) // Strictly greater for upper bound
 * 
 * MISTAKE 2: Confusing with lower bound
 * - Lower bound: arr[i] >= target (includes equal)
 * - Upper bound: arr[i] > target (excludes equal)
 * 
 * MISTAKE 3: Wrong pointer update for <= case
 * ❌ if (arr[mid] < target) left = mid + 1 // Forgets equal case
 * ✅ if (arr[mid] <= target) left = mid + 1 // Handles both < and =
 * 
 * MISTAKE 4: Not initializing answer properly
 * ❌ var answer = -1 // Wrong for "not found"
 * ✅ var answer = arr.size // Correct:  position after last element
 * 
 * MISTAKE 5: Thinking upper bound returns last occurrence
 * ❌ Upper bound of 4 in [1,2,4,4,5] is NOT 3 (last 4)
 * ✅ Upper bound is 4 (first element > 4, which is 5)
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. FINDING RANGE OF ELEMENTS:
 *    Count of x = upperBound(x) - lowerBound(x)
 *    arr = [1, 2, 4, 4, 4, 5]
 *    Count of 4's = upperBound(4) - lowerBound(4) = 5 - 2 = 3 ✓
 * 
 * 2. RANGE QUERIES:
 *    Find all elements in range [L, R]
 *    start = lowerBound(L)
 *    end = upperBound(R)
 *    Range = arr[start..end-1]
 * 
 * 3. INSERTION POSITION:
 *    To insert x and maintain sorted order with x at end of duplicates: 
 *    Insert at upperBound(x)
 * 
 * 4. IMPLEMENTING std::set OPERATIONS:
 *    - count(x): upperBound(x) - lowerBound(x) > 0
 *    - equal_range(x): [lowerBound(x), upperBound(x))
 * 
 * ============================================================================
 * LOWER BOUND vs UPPER BOUND COMPARISON
 * ============================================================================
 * 
 * arr = [1, 3, 3, 3, 5, 7], target = 3
 * 
 * Lower Bound (>=):
 * - Returns:  1 (first 3)
 * - Use: Find start of range
 * 
 * Upper Bound (>):
 * - Returns: 4 (first element after 3's, which is 5)
 * - Use: Find end of range
 * 
 * Count of 3's = 4 - 1 = 3 ✓
 * 
 * If target = 4 (not in array):
 * - Lower bound: 4 (would insert at index 4)
 * - Upper bound: 4 (same, no equal elements)
 * - Count:  4 - 4 = 0 ✓ (not present)
 * 
 * ============================================================================
 * WHEN TO USE UPPER BOUND
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Need first element STRICTLY greater than target
 * ✅ Finding end position of duplicate range
 * ✅ Implementing bisect_right (Python) equivalent
 * ✅ Counting occurrences (with lower bound)
 * ✅ Range deletion operations
 * 
 * DON'T USE WHEN: 
 * ❌ Need first element >= target (use lower bound)
 * ❌ Need last occurrence (use modified binary search)
 * ❌ Array is unsorted
 * ❌ Need ceiling (smallest element >= target)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val ub = UpperBound()
    
    println("=== Testing Upper Bound ===\n")
    
    // Test 1: Basic upper bound
    val arr1 = intArrayOf(1, 2, 4, 4, 5, 6, 8)
    println("Test 1: arr = ${arr1.contentToString()}")
    println("Upper bound of 4: ${ub.upperBound(arr1, 4)}")
    println("Expected: 4 (index of first element > 4, which is 5)\n")
    
    // Test 2: Target not in array (between elements)
    println("Test 2: arr = ${arr1.contentToString()}")
    println("Upper bound of 3: ${ub.upperBound(arr1, 3)}")
    println("Expected: 2 (index of 4, first element > 3)\n")
    
    // Test 3: Target smaller than all elements
    println("Test 3: arr = ${arr1.contentToString()}")
    println("Upper bound of 0: ${ub.upperBound(arr1, 0)}")
    println("Expected: 0\n")
    
    // Test 4: Target larger than or equal to all elements
    println("Test 4: arr = ${arr1.contentToString()}")
    println("Upper bound of 10: ${ub.upperBound(arr1, 10)}")
    println("Expected: 7 (array size)\n")
    
    // Test 5: All duplicates
    val arr2 = intArrayOf(3, 3, 3, 3, 3)
    println("Test 5: arr = ${arr2.contentToString()}")
    println("Upper bound of 3: ${ub.upperBound(arr2, 3)}")
    println("Expected: 5 (after all 3's)\n")
    
    // Test 6: Single element (equal)
    val arr3 = intArrayOf(5)
    println("Test 6: arr = ${arr3.contentToString()}")
    println("Upper bound of 5: ${ub.upperBound(arr3, 5)}")
    println("Expected: 1 (after the 5)\n")
    
    // Test 7: Single element (smaller target)
    println("Test 7: arr = ${arr3.contentToString()}")
    println("Upper bound of 3: ${ub.upperBound(arr3, 3)}")
    println("Expected: 0\n")
    
    // Test 8: Comparing lower and upper bound
    println("Test 8: Comparing bounds for ${arr1.contentToString()}")
    val target = 4
    val lb = LowerBound().lowerBound(arr1, target)
    val ubVal = ub.upperBound(arr1, target)
    println("Target: $target")
    println("Lower bound: $lb")
    println("Upper bound: $ubVal")
    println("Count of $target: ${ubVal - lb}")
    println("Expected count: 2\n")
    
    // Test 9: Negative numbers
    val arr4 = intArrayOf(-10, -5, -3, 0, 2, 5)
    println("Test 9: arr = ${arr4.contentToString()}")
    println("Upper bound of -3: ${ub.upperBound(arr4, -3)}")
    println("Expected: 3 (index of 0)\n")
    
    // Test 10: Count occurrences using bounds
    val arr5 = intArrayOf(1, 2, 2, 2, 3, 4, 4, 5)
    println("Test 10: Count occurrences in ${arr5.contentToString()}")
    println("Count of 2: ${ub.countOccurrences(arr5, 2)}")
    println("Expected: 3")
    println("Count of 4: ${ub. countOccurrences(arr5, 4)}")
    println("Expected: 2")
    println("Count of 6: ${ub.countOccurrences(arr5, 6)}")
    println("Expected:  0\n")
}
