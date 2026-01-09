/**
 * ============================================================================
 * PROBLEM: Interpolation Search
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a **sorted and uniformly distributed** array of integers and a target value,
 * find the index of the target using interpolation search. If the target is not found,
 * return -1.
 * 
 * INPUT FORMAT:
 * - A sorted array with uniform distribution: arr = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
 * - A target integer: target = 70
 * 
 * OUTPUT FORMAT:
 * - Index of target if found: 6
 * - -1 if target not found
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - Array is sorted in ascending order
 * - Array has uniform distribution (for optimal performance)
 * - -10^9 <= arr[i], target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think about how you search in a dictionary.  If you're looking for "zebra",
 * you don't open the middle page - you open near the end!  Interpolation search
 * uses the VALUE to estimate WHERE the target might be, not just the middle.
 * 
 * KEY INSIGHT:
 * Binary search always checks the middle.  But if we're looking for 90 in
 * [10, 20, 30, .. ., 100], we should check near the END, not the middle! 
 * Interpolation calculates a smart position based on the value.
 * 
 * FORMULA:
 * pos = left + [(target - arr[left]) * (right - left)] / (arr[right] - arr[left])
 * 
 * This formula estimates position based on how close target is to arr[left]
 * relative to arr[right].
 * 
 * ALGORITHM STEPS:
 * 1. Set left = 0 and right = n - 1
 * 2. While left <= right and target is in range [arr[left], arr[right]]:
 *    a. If left == right, check if arr[left] == target
 *    b. Calculate position using interpolation formula
 *    c. If arr[pos] == target, return pos (found!)
 *    d. If arr[pos] < target, search right half:  left = pos + 1
 *    e. If arr[pos] > target, search left half: right = pos - 1
 * 3. If loop ends, target not found, return -1
 * 
 * VISUAL EXAMPLE: 
 * arr = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], target = 70
 * 
 * Step 1: left = 0, right = 9
 * pos = 0 + [(70 - 10) * (9 - 0)] / (100 - 10)
 *     = 0 + [60 * 9] / 90
 *     = 0 + 540 / 90
 *     = 0 + 6 = 6
 * 
 * Check arr[6] = 70 == 70, FOUND in just 1 step!
 * (Binary search would take 3-4 steps)
 * 
 * COMPARISON: 
 * For arr = [10, 20, 30, 40, 50, 60, 70, 80, 90, 100], target = 70
 * 
 * Binary Search:
 * Step 1: Check middle (index 4): arr[4] = 50 < 70
 * Step 2: Check middle of right half (index 7): arr[7] = 80 > 70
 * Step 3: Check middle of [5,6] (index 5): arr[5] = 60 < 70
 * Step 4: Check index 6: arr[6] = 70 FOUND (4 comparisons)
 * 
 * Interpolation Search:
 * Step 1: Calculate pos = 6, arr[6] = 70 FOUND (1 comparison!)
 * 
 * WHY IT'S CALLED "INTERPOLATION": 
 * Uses linear interpolation (like in math/graphics) to estimate position
 * 
 * WHEN TO USE: 
 * ✅ Data is uniformly distributed (evenly spaced values)
 * ✅ Array is sorted
 * ✅ Data size is large
 * ❌ Data is NOT uniformly distributed (use binary search instead)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Interpolation Search: O(log log n) time BEST CASE, O(n) WORST CASE
 * 2. Binary Search: O(log n) time always - MORE RELIABLE
 * 3. Exponential Search: O(log n) time - Better for unbounded
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Best Case: O(1) - Target found immediately
 * - Average Case: O(log log n) - When data is uniformly distributed
 * - Worst Case: O(n) - When data is NOT uniformly distributed (e.g., [1,2,3,100])
 * 
 * Note: O(log log n) is extremely fast! 
 * For n = 1,000,000: log(n) ≈ 20, log(log(n)) ≈ 4
 * 
 * SPACE COMPLEXITY:
 * - O(1): Only using constant extra space
 * 
 * ============================================================================
 * EDGE CASES & ERROR HANDLING
 * ============================================================================
 * 
 * 1. Empty array: Return -1
 * 2. Single element: Direct comparison
 * 3. Target out of range: Check before loop
 * 4. Target smaller than arr[left]: Return -1
 * 5. Target larger than arr[right]: Return -1
 * 6. Division by zero: Handled by checking arr[right] != arr[left]
 * 7. Non-uniform distribution: Falls back to sequential behavior (O(n))
 * 8.  Duplicate elements: Returns first occurrence
 * 9. Negative numbers: Works correctly with formula
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 */

package searching.advanced

/**
 * Performs interpolation search on a sorted, uniformly distributed array.
 * 
 * @param arr Sorted array of integers in ascending order
 * @param target Value to search for
 * @return Index of target if found, -1 otherwise
 */
fun interpolationSearch(arr:  IntArray, target: Int): Int {
    val n = arr. size
    
    // Edge case: empty array
    if (n == 0) return -1
    
    var left = 0
    var right = n - 1
    
    while (left <= right && target >= arr[left] && target <= arr[right]) {
        // If only one element left
        if (left == right) {
            return if (arr[left] == target) left else -1
        }
        
        // Avoid division by zero
        if (arr[right] == arr[left]) {
            return if (arr[left] == target) left else -1
        }
        
        // Interpolation formula to estimate position
        val pos = left + ((target - arr[left]).toLong() * (right - left) / 
                         (arr[right] - arr[left])).toInt()
        
        // Safety check:  ensure pos is in valid range
        if (pos < left || pos > right) {
            return -1
        }
        
        when {
            arr[pos] == target -> return pos
            arr[pos] < target -> left = pos + 1
            else -> right = pos - 1
        }
    }
    
    return -1
}

/**
 * Interpolation search using floating-point calculation (alternative implementation).
 * More precise but slightly slower due to floating-point operations.
 */
fun interpolationSearchFloat(arr: IntArray, target:  Int): Int {
    val n = arr.size
    if (n == 0) return -1
    
    var left = 0
    var right = n - 1
    
    while (left <= right && target >= arr[left] && target <= arr[right]) {
        if (left == right) {
            return if (arr[left] == target) left else -1
        }
        
        if (arr[right] == arr[left]) {
            return if (arr[left] == target) left else -1
        }
        
        // Using double for more precise calculation
        val ratio = (target - arr[left]).toDouble() / (arr[right] - arr[left])
        val pos = left + (ratio * (right - left)).toInt()
        
        // Safety check
        if (pos < left || pos > right) {
            return -1
        }
        
        when {
            arr[pos] == target -> return pos
            arr[pos] < target -> left = pos + 1
            else -> right = pos - 1
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
    // Example 1: Uniform distribution (IDEAL case)
    val arr1 = intArrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
    val target1 = 70
    println("Example 1: Uniform Distribution (IDEAL)")
    println("Array: ${arr1.contentToString()}")
    println("Target:  $target1")
    println("Result: ${interpolationSearch(arr1, target1)}")
    println("Expected: 6")
    println()
    
    // Example 2: Target at beginning
    val arr2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target2 = 1
    println("Example 2: Target at Beginning")
    println("Array: ${arr2.contentToString()}")
    println("Target: $target2")
    println("Result:  ${interpolationSearch(arr2, target2)}")
    println("Expected: 0")
    println()
    
    // Example 3: Target not found
    val arr3 = intArrayOf(10, 20, 30, 40, 50)
    val target3 = 25
    println("Example 3: Target Not Found")
    println("Array: ${arr3.contentToString()}")
    println("Target: $target3")
    println("Result: ${interpolationSearch(arr3, target3)}")
    println("Expected: -1")
    println()
    
    // Example 4: Non-uniform distribution (WORST case)
    val arr4 = intArrayOf(1, 2, 3, 4, 1000)
    val target4 = 4
    println("Example 4: Non-uniform Distribution (degrades to linear)")
    println("Array: ${arr4.contentToString()}")
    println("Target: $target4")
    println("Result: ${interpolationSearch(arr4, target4)}")
    println("Expected: 3")
    println()
    
    // Example 5: Large uniform array
    val arr5 = IntArray(100) { (it + 1) * 10 }
    val target5 = 750
    println("Example 5: Large Uniform Array")
    println("Array: [10, 20, 30, .. ., 1000] (100 elements)")
    println("Target: $target5")
    println("Result: ${interpolationSearch(arr5, target5)}")
    println("Expected: 74")
    println()
    
    // Example 6: Negative numbers
    val arr6 = intArrayOf(-50, -40, -30, -20, -10, 0, 10, 20, 30, 40)
    val target6 = -20
    println("Example 6: With Negative Numbers")
    println("Array: ${arr6.contentToString()}")
    println("Target: $target6")
    println("Result: ${interpolationSearch(arr6, target6)}")
    println("Expected: 3")
}

/**
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. PHONE BOOKS / DICTIONARIES:
 *    - Names are uniformly distributed
 *    - Much faster than binary search
 * 
 * 2. NUMERICAL DATABASES:
 *    - Employee IDs (sequential)
 *    - Serial numbers
 *    - Timestamps
 * 
 * 3. IP ADDRESS LOOKUP:
 *    - IP ranges are uniformly distributed
 *    - Fast routing table lookups
 * 
 * 4. SCIENTIFIC DATA:
 *    - Sensor readings over time
 *    - Temperature logs
 *    - Evenly spaced measurements
 * 
 * 5. FINANCIAL DATA:
 *    - Stock prices at regular intervals
 *    - Time-series data with uniform sampling
 * 
 * ⚠️ WARNING:  Don't use with non-uniform data! 
 * Bad example: [1, 2, 3, 4, 10000] - will degrade to O(n)
 * Good example: [10, 20, 30, 40, 50] - achieves O(log log n)
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Search in a Sorted Array
 * 2. Find Closest Element in Sorted Array
 * 3. Search Insert Position
 * 4. Find First and Last Position
 * 5. Peak Element in Array
 * 
 * ============================================================================
 */
