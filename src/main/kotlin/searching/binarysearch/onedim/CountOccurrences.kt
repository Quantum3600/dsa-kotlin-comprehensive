/**
 * ============================================================================
 * PROBLEM: Count Occurrences in Sorted Array
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a sorted array and a target value, count the number of times the 
 * target appears in the array using O(log n) time complexity.
 * 
 * INPUT FORMAT:
 * - A sorted array of integers:  arr = [1, 2, 2, 2, 3, 4, 4, 5]
 * - A target integer: target = 2
 * 
 * OUTPUT FORMAT: 
 * - Count of target occurrences: 3
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^5
 * - Array is sorted in non-decreasing order
 * - -10^9 <= arr[i], target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of counting linearly (O(n)), we can use binary search to find: 
 * - First occurrence of target
 * - Last occurrence of target
 * - Count = last - first + 1
 * 
 * KEY INSIGHT:
 * This is a direct application of First and Last Occurrence problem! 
 * We use the formula: count = (last index - first index + 1)
 * 
 * ALTERNATIVE APPROACH:
 * Use Lower Bound and Upper Bound: 
 * - Lower bound = first position where element >= target
 * - Upper bound = first position where element > target
 * - Count = upper_bound - lower_bound
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 2, 2, 3, 4, 4, 5], target = 2
 *           ↑     ↑
 *        first  last
 *         (1)    (3)
 * 
 * Count = 3 - 1 + 1 = 3 ✓
 * 
 * Using bounds: 
 * arr = [1, 2, 2, 2, 3, 4, 4, 5]
 *           ↑       ↑
 *          LB      UB
 *         (1)     (4)
 * 
 * Count = 4 - 1 = 3 ✓
 * 
 * ALGORITHM (Using First/Last):
 * 1. Find first occurrence using binary search
 * 2. If not found, return 0
 * 3. Find last occurrence using binary search
 * 4. Return last - first + 1
 * 
 * ALGORITHM (Using Bounds):
 * 1. Find lower bound (first element >= target)
 * 2. Find upper bound (first element > target)
 * 3. If lower_bound == size or arr[lower_bound] != target, return 0
 * 4. Return upper_bound - lower_bound
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Finding first occurrence: O(log n)
 * - Finding last occurrence:  O(log n)
 * - Total: O(log n)
 * 
 * Alternative: O(log n)
 * - Lower bound:  O(log n)
 * - Upper bound: O(log n)
 * - Total: O(log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 * 
 * COMPARISON WITH LINEAR APPROACH:
 * Linear scan: O(n) time
 * Binary search: O(log n) time
 * For n = 1,000,000: ~1,000,000 vs ~20 operations! 
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class CountOccurrences {
    
    /**
     * Count occurrences using first and last position
     * 
     * @param arr Sorted array
     * @param target Value to count
     * @return Number of occurrences
     */
    fun countOccurrences(arr: IntArray, target: Int): Int {
        // Find first occurrence
        val first = findFirst(arr, target)
        
        // If not found, count is 0
        if (first == -1) {
            return 0
        }
        
        // Find last occurrence
        val last = findLast(arr, target)
        
        // Count = last - first + 1
        return last - first + 1
    }
    
    /**
     * Count occurrences using lower and upper bound
     * More elegant approach
     */
    fun countOccurrencesUsingBounds(arr:  IntArray, target: Int): Int {
        val lowerBound = findLowerBound(arr, target)
        
        // If lower bound is at end or element at lower bound is not target
        if (lowerBound == arr.size || arr[lowerBound] != target) {
            return 0
        }
        
        val upperBound = findUpperBound(arr, target)
        
        return upperBound - lowerBound
    }
    
    /**
     * Find first occurrence of target
     */
    private fun findFirst(arr: IntArray, target:  Int): Int {
        var left = 0
        var right = arr.size - 1
        var first = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            when {
                arr[mid] == target -> {
                    first = mid
                    right = mid - 1  // Continue searching left
                }
                arr[mid] < target -> left = mid + 1
                else -> right = mid - 1
            }
        }
        
        return first
    }
    
    /**
     * Find last occurrence of target
     */
    private fun findLast(arr:  IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var last = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            when {
                arr[mid] == target -> {
                    last = mid
                    left = mid + 1  // Continue searching right
                }
                arr[mid] < target -> left = mid + 1
                else -> right = mid - 1
            }
        }
        
        return last
    }
    
    /**
     * Find lower bound (first element >= target)
     */
    private fun findLowerBound(arr: IntArray, target:  Int): Int {
        var left = 0
        var right = arr.size - 1
        var answer = arr.size
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] >= target) {
                answer = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Find upper bound (first element > target)
     */
    private fun findUpperBound(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var answer = arr.size
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] > target) {
                answer = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Bonus: Count elements less than target
     */
    fun countLessThan(arr: IntArray, target: Int): Int {
        // Count of elements < target = lower_bound(target)
        return findLowerBound(arr, target)
    }
    
    /**
     * Bonus: Count elements greater than target
     */
    fun countGreaterThan(arr: IntArray, target: Int): Int {
        // Count of elements > target = n - upper_bound(target)
        return arr.size - findUpperBound(arr, target)
    }
    
    /**
     * Bonus: Count elements in range [low, high]
     */
    fun countInRange(arr: IntArray, low: Int, high:  Int): Int {
        val lowerBound = findLowerBound(arr, low)
        val upperBound = findUpperBound(arr, high)
        return upperBound - lowerBound
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Multiple occurrences
 * INPUT:  arr = [1, 2, 2, 2, 3, 4, 4, 5], target = 2
 * 
 * FINDING FIRST: 
 * - Binary search finds first occurrence at index 1
 * 
 * FINDING LAST: 
 * - Binary search finds last occurrence at index 3
 * 
 * COUNT:
 * - count = 3 - 1 + 1 = 3 ✓
 * 
 * USING BOUNDS:
 * - Lower bound (first >= 2): index 1
 * - Upper bound (first > 2): index 4
 * - count = 4 - 1 = 3 ✓
 * 
 * ---
 * 
 * Example 2: Single occurrence
 * INPUT: arr = [1, 2, 3, 4, 5], target = 3
 * 
 * FIRST:  2
 * LAST:  2
 * COUNT: 2 - 2 + 1 = 1 ✓
 * 
 * ---
 * 
 * Example 3: Not found
 * INPUT: arr = [1, 2, 3, 4, 5], target = 6
 * 
 * FIRST: -1 (not found)
 * COUNT: 0 ✓
 * 
 * ---
 * 
 * Example 4: All elements are target
 * INPUT: arr = [2, 2, 2, 2, 2], target = 2
 * 
 * FIRST: 0
 * LAST: 4
 * COUNT: 4 - 0 + 1 = 5 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Return 0
 * 2. Single element equal to target: Return 1
 * 3. Single element not equal:  Return 0
 * 4. All elements are target: Return n
 * 5. Target not in array: Return 0
 * 6. Target smaller than all: Return 0
 * 7. Target larger than all: Return 0
 * 8. Single occurrence: Return 1
 * 9. Multiple consecutive occurrences:  Correct count
 * 10. Negative numbers: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using linear scan
 * ❌ var count = 0; for (x in arr) if (x == target) count++
 * ✅ Use binary search for O(log n) instead of O(n)
 * 
 * MISTAKE 2: Wrong count formula
 * ❌ count = last - first  // Off by one! 
 * ✅ count = last - first + 1
 * 
 * MISTAKE 3: Not checking if element exists
 * ❌ Calculating count even when first == -1
 * ✅ if (first == -1) return 0
 * 
 * MISTAKE 4: Confusing bounds
 * - Lower bound: first element >= target (includes equal)
 * - Upper bound: first element > target (excludes equal)
 * - Count = upper - lower (NOT upper - lower + 1)
 * 
 * MISTAKE 5: Calling binary search multiple times inefficiently
 * ❌ Recalculating mid values unnecessarily
 * ✅ Use separate first/last methods or bounds methods
 * 
 * ============================================================================
 * WHY BOTH APPROACHES WORK
 * ============================================================================
 * 
 * APPROACH 1: First and Last
 * arr = [1, 2, 2, 2, 3]
 *           ↑     ↑
 *        first last
 *         (1)   (3)
 * 
 * Indices: 1, 2, 3
 * Count: 3 - 1 + 1 = 3 ✓
 * We ADD 1 because indices are inclusive
 * 
 * APPROACH 2: Lower and Upper Bound
 * arr = [1, 2, 2, 2, 3]
 *           ↑       ↑
 *          LB      UB
 *         (1)     (4)
 * 
 * LB points to first 2 (index 1)
 * UB points to 3 (index 4), first element > 2
 * Range [LB, UB) is half-open interval
 * Count:  4 - 1 = 3 ✓
 * We DON'T add 1 because upper bound is exclusive
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. DATABASE QUERIES:
 *    COUNT(*) WHERE value = target in sorted index
 *    O(log n) instead of full table scan
 * 
 * 2. ANALYTICS:
 *    Count occurrences of specific event in sorted log
 *    Efficient for time-series data
 * 
 * 3. DUPLICATE DETECTION:
 *    Check if element appears more than k times
 *    if (countOccurrences(arr, x) > k) { ... }
 * 
 * 4. FREQUENCY ANALYSIS:
 *    Find most common element in sorted data
 *    Check counts for different values
 * 
 * 5. VALIDATION:
 *    Verify uniqueness constraint
 *    if (countOccurrences(arr, x) > 1) error("Duplicate!")
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * - Find first and last position of element
 * - Count of smaller/larger elements
 * - Majority element
 * - Find duplicate number
 * - K-th smallest element
 * 
 * ============================================================================
 * VARIATIONS
 * ============================================================================
 * 
 * VARIATION 1: Count elements in range [L, R]
 * count = countInRange(arr, L, R)
 * Uses:  upper_bound(R) - lower_bound(L)
 * 
 * VARIATION 2: Count elements < target
 * count = countLessThan(arr, target)
 * Uses: lower_bound(target)
 * 
 * VARIATION 3: Count elements > target
 * count = countGreaterThan(arr, target)
 * Uses: n - upper_bound(target)
 * 
 * VARIATION 4: Count elements <= target
 * count = upper_bound(target)
 * 
 * VARIATION 5: Count elements >= target
 * count = n - lower_bound(target)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val co = CountOccurrences()
    
    println("=== Testing Count Occurrences ===\n")
    
    // Test array
    val arr = intArrayOf(1, 2, 2, 2, 3, 4, 4, 5)
    println("Array: ${arr. contentToString()}\n")
    
    // Test 1: Multiple occurrences
    println("Test 1: Count occurrences of 2")
    println("Result: ${co.countOccurrences(arr, 2)}")
    println("Expected: 3\n")
    
    // Test 2: Two occurrences
    println("Test 2: Count occurrences of 4")
    println("Result: ${co.countOccurrences(arr, 4)}")
    println("Expected: 2\n")
    
    // Test 3: Single occurrence
    println("Test 3: Count occurrences of 1")
    println("Result: ${co.countOccurrences(arr, 1)}")
    println("Expected: 1\n")
    
    // Test 4: Not found
    println("Test 4: Count occurrences of 6")
    println("Result: ${co.countOccurrences(arr, 6)}")
    println("Expected: 0\n")
    
    // Test 5: All elements are target
    val arr2 = intArrayOf(5, 5, 5, 5, 5)
    println("Test 5: arr = ${arr2.contentToString()}, target = 5")
    println("Result: ${co.countOccurrences(arr2, 5)}")
    println("Expected: 5\n")
    
    // Test 6: Using bounds method
    println("Test 6: Count using bounds method for target 2")
    println("Result: ${co.countOccurrencesUsingBounds(arr, 2)}")
    println("Expected: 3\n")
    
    // Test 7: Count elements less than target
    println("Test 7: Count elements < 3 in ${arr. contentToString()}")
    println("Result: ${co.countLessThan(arr, 3)}")
    println("Expected:  4 (1, 2, 2, 2)\n")
    
    // Test 8: Count elements greater than target
    println("Test 8: Count elements > 3 in ${arr.contentToString()}")
    println("Result:  ${co.countGreaterThan(arr, 3)}")
    println("Expected: 3 (4, 4, 5)\n")
    
    // Test 9: Count elements in range
    println("Test 9: Count elements in range [2, 4]")
    println("Result: ${co.countInRange(arr, 2, 4)}")
    println("Expected: 6 (2, 2, 2, 3, 4, 4)\n")
    
    // Test 10: Empty array
    val arr3 = intArrayOf()
    println("Test 10: Empty array, target = 1")
    println("Result: ${co.countOccurrences(arr3, 1)}")
    println("Expected: 0\n")
    
    // Test 11: Performance comparison demonstration
    println("Test 11: Performance with large array")
    val largeArr = IntArray(1000000) { it / 1000 }  // Many duplicates
    val target = 500
    
    val startTime = System.nanoTime()
    val count = co.countOccurrences(largeArr, target)
    val endTime = System.nanoTime()
    
    println("Array size: 1,000,000")
    println("Count of $target: $count")
    println("Time:  ${(endTime - startTime) / 1000} microseconds")
    println("(Binary search is extremely fast even for huge arrays! )\n")
}
