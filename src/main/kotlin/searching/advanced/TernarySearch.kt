/**
 * ============================================================================
 * PROBLEM:  Ternary Search
 * DIFFICULTY: Medium
 * CATEGORY:  Searching - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a **sorted** array of integers and a target value, find the index of the
 * target using ternary search.  Can also be used to find maximum/minimum in a
 * unimodal function.  If the target is not found, return -1.
 * 
 * INPUT FORMAT:
 * - A sorted array of integers:  arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
 * - A target integer:  target = 7
 * 
 * OUTPUT FORMAT:
 * - Index of target if found:  6
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
 * Binary search divides into 2 parts.  What if we divide into 3 parts?
 * That's ternary search!  It checks TWO middle points and eliminates
 * 2/3 of the search space (binary eliminates 1/2).
 * 
 * Sounds better, right?  But surprisingly, binary search is usually FASTER! 
 * Why? Because ternary makes MORE comparisons per iteration.
 * 
 * KEY INSIGHT:
 * Ternary Search shines for finding maximum/minimum of UNIMODAL functions
 * (functions with single peak/valley), not just for searching sorted arrays.
 * 
 * UNIMODAL FUNCTION:
 * A function that increases then decreases (or vice versa), having one peak. 
 * Example: f(x) = -(x-5)² + 10  (parabola with peak at x=5)
 * 
 *         *
 *       *   *
 *     *       *
 *   *           *
 * 
 * ALGORITHM STEPS (For Sorted Array):
 * 1. Calculate two midpoints: mid1 and mid2
 *    mid1 = left + (right - left) / 3
 *    mid2 = right - (right - left) / 3
 * 2. Compare target with arr[mid1] and arr[mid2]: 
 *    - If target == arr[mid1] or arr[mid2], found! 
 *    - If target < arr[mid1], search in left third:  [left, mid1-1]
 *    - If target > arr[mid2], search in right third:  [mid2+1, right]
 *    - Otherwise, search in middle third: [mid1+1, mid2-1]
 * 3. Repeat until found or left > right
 * 
 * VISUAL EXAMPLE (Searching in Sorted Array):
 * arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], target = 7
 * 
 * Step 1: Divide into 3 parts
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
 *  L     mid1    mid2        R
 *  0      3       6          9
 * 
 * mid1 = 0 + (9-0)/3 = 3, arr[3] = 4
 * mid2 = 9 - (9-0)/3 = 6, arr[6] = 7
 * 
 * arr[mid2] = 7 == target, FOUND at index 6!
 * 
 * TERNARY SEARCH FOR UNIMODAL FUNCTIONS: 
 * This is where ternary search truly excels!
 * 
 * Example: Find maximum of f(x) = -(x-5)² + 10 in range [0, 10]
 * 
 * ```
 * def f(x): return -(x-5)**2 + 10
 * 
 * left = 0, right = 10
 * 
 * Iteration 1:
 *   mid1 = 0 + (10-0)/3 = 3.33, f(3.33) = 7.22
 *   mid2 = 10 - (10-0)/3 = 6.67, f(6.67) = 7.22
 *   f(mid1) ≈ f(mid2), both sides symmetric
 * 
 * Iteration 2:
 *   mid1 ≈ 3.89, f(3.89) = 8.77
 *   mid2 ≈ 6.11, f(6.11) = 8.77
 *   Narrowing down...
 * 
 * Converges to x = 5 (the maximum!)
 * ```
 * 
 * BINARY vs TERNARY SEARCH (Comparison):
 * 
 * For n = 1000 elements:
 * 
 * Binary Search: 
 * - log₂(1000) ≈ 10 iterations
 * - 1 comparison per iteration
 * - Total:  ~10 comparisons
 * 
 * Ternary Search:
 * - log₃(1000) ≈ 6. 3 iterations
 * - 2 comparisons per iteration
 * - Total: ~12.6 comparisons
 * 
 * So binary is actually more efficient for sorted array search!
 * 
 * WHEN TO USE TERNARY SEARCH:
 * ✅ Finding max/min of unimodal function
 * ✅ Optimization problems
 * ✅ Finding peak in mountain array
 * ❌ Regular sorted array search (use binary instead)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Ternary Search:  O(log₃ n) iterations × 2 comparisons ≈ O(2 log₃ n)
 * 2. Binary Search: O(log₂ n) iterations × 1 comparison ≈ O(log₂ n) - BETTER for sorted
 * 3. Golden Section Search: Similar to ternary, uses golden ratio
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Iterations: O(log₃ n) - Search space reduced by 1/3 each time
 * - Comparisons per iteration: 2
 * - Total: O(2 × log₃ n) = O(log n)
 * 
 * Conversion:  log₃ n = log₂ n / log₂ 3 ≈ 0.63 × log₂ n
 * But with 2 comparisons:  2 × 0.63 = 1.26 × log₂ n
 * So ternary is about 26% SLOWER than binary for sorted array search!
 * 
 * For Unimodal Functions:
 * - Time: O(log₃ n) where n is the range size
 * - Each iteration compares function values, not elements
 * 
 * SPACE COMPLEXITY: 
 * - Iterative:  O(1) - Only uses constant space
 * - Recursive: O(log n) - Call stack depth
 * 
 * ============================================================================
 * EDGE CASES & ERROR HANDLING
 * ============================================================================
 * 
 * 1. Empty array: Return -1
 * 2. Single element: Direct comparison
 * 3. Two elements: Check both
 * 4. Target at mid1:  Found immediately
 * 5. Target at mid2: Found immediately
 * 6. Target in first third:  Narrow to [left, mid1-1]
 * 7. Target in last third: Narrow to [mid2+1, right]
 * 8. Target in middle third: Narrow to [mid1+1, mid2-1]
 * 9. Duplicate elements: Returns first occurrence in search path
 * 10. Precision for floating point: Use epsilon for unimodal search
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 */

package searching.advanced

/**
 * Performs ternary search on a sorted array.
 * 
 * @param arr Sorted array of integers in ascending order
 * @param target Value to search for
 * @return Index of target if found, -1 otherwise
 */
fun ternarySearch(arr: IntArray, target: Int): Int {
    var left = 0
    var right = arr.size - 1
    
    while (left <= right) {
        // Avoid integer overflow
        val mid1 = left + (right - left) / 3
        val mid2 = right - (right - left) / 3
        
        // Check if target is at mid1 or mid2
        if (arr[mid1] == target) return mid1
        if (arr[mid2] == target) return mid2
        
        // Determine which third to search
        when {
            target < arr[mid1] -> right = mid1 - 1  // Left third
            target > arr[mid2] -> left = mid2 + 1   // Right third
            else -> {                                 // Middle third
                left = mid1 + 1
                right = mid2 - 1
            }
        }
    }
    
    return -1
}

/**
 * Recursive implementation of ternary search. 
 */
fun ternarySearchRecursive(arr: IntArray, target: Int, left: Int = 0, right: Int = arr.size - 1): Int {
    if (left > right) return -1
    
    val mid1 = left + (right - left) / 3
    val mid2 = right - (right - left) / 3
    
    // Check if target is at mid1 or mid2
    if (arr[mid1] == target) return mid1
    if (arr[mid2] == target) return mid2
    
    // Recursively search appropriate third
    return when {
        target < arr[mid1] -> ternarySearchRecursive(arr, target, left, mid1 - 1)
        target > arr[mid2] -> ternarySearchRecursive(arr, target, mid2 + 1, right)
        else -> ternarySearchRecursive(arr, target, mid1 + 1, mid2 - 1)
    }
}

/**
 * Ternary search for finding MAXIMUM of a unimodal function.
 * This is the most practical use case for ternary search! 
 * 
 * @param left Left boundary
 * @param right Right boundary
 * @param epsilon Precision threshold
 * @param f Function to maximize
 * @return x value where f(x) is maximum
 */
fun ternarySearchMaximum(
    left: Double, 
    right: Double, 
    epsilon: Double = 1e-9,
    f: (Double) -> Double
): Double {
    var l = left
    var r = right
    
    while (r - l > epsilon) {
        val mid1 = l + (r - l) / 3.0
        val mid2 = r - (r - l) / 3.0
        
        if (f(mid1) < f(mid2)) {
            l = mid1  // Maximum is in right 2/3
        } else {
            r = mid2  // Maximum is in left 2/3
        }
    }
    
    return (l + r) / 2.0
}

/**
 * Ternary search for finding MINIMUM of a unimodal function.
 * 
 * @param left Left boundary
 * @param right Right boundary
 * @param epsilon Precision threshold
 * @param f Function to minimize
 * @return x value where f(x) is minimum
 */
fun ternarySearchMinimum(
    left: Double,
    right: Double,
    epsilon: Double = 1e-9,
    f: (Double) -> Double
): Double {
    var l = left
    var r = right
    
    while (r - l > epsilon) {
        val mid1 = l + (r - l) / 3.0
        val mid2 = r - (r - l) / 3.0
        
        if (f(mid1) > f(mid2)) {
            l = mid1  // Minimum is in right 2/3
        } else {
            r = mid2  // Minimum is in left 2/3
        }
    }
    
    return (l + r) / 2.0
}

/**
 * Find peak element in mountain array (practical application).
 * Mountain array: increases then decreases, like:  [1,3,5,7,5,3,1]
 */
fun findPeakElement(arr: IntArray): Int {
    var left = 0
    var right = arr.size - 1
    
    while (left < right) {
        val mid1 = left + (right - left) / 3
        val mid2 = right - (right - left) / 3
        
        if (arr[mid1] < arr[mid2]) {
            left = mid1 + 1  // Peak is to the right
        } else {
            right = mid2 - 1  // Peak is to the left
        }
    }
    
    return left
}

/**
 * ============================================================================
 * EXAMPLE USAGE
 * ============================================================================
 */
fun main() {
    println("="*70)
    println("PART 1: TERNARY SEARCH IN SORTED ARRAY")
    println("="*70)
    println()
    
    // Example 1: Basic ternary search
    val arr1 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target1 = 7
    println("Example 1: Basic Search")
    println("Array: ${arr1.contentToString()}")
    println("Target: $target1")
    println("Result (iterative): ${ternarySearch(arr1, target1)}")
    println("Result (recursive): ${ternarySearchRecursive(arr1, target1)}")
    println("Expected: 6")
    println()
    
    // Example 2: Target not found
    val arr2 = intArrayOf(1, 3, 5, 7, 9, 11, 13, 15)
    val target2 = 10
    println("Example 2: Target Not Found")
    println("Array:  ${arr2.contentToString()}")
    println("Target: $target2")
    println("Result:  ${ternarySearch(arr2, target2)}")
    println("Expected: -1")
    println()
    
    println("="*70)
    println("PART 2: TERNARY SEARCH FOR UNIMODAL FUNCTIONS (TRUE POWER! )")
    println("="*70)
    println()
    
    // Example 3: Find maximum of parabola
    println("Example 3: Find Maximum of f(x) = -(x-5)² + 10")
    val parabola = { x: Double -> -(x - 5) * (x - 5) + 10 }
    val maxX = ternarySearchMaximum(0.0, 10.0, f = parabola)
    println("Maximum at x = $maxX")
    println("f($maxX) = ${parabola(maxX)}")
    println("Expected: x ≈ 5.0, f(x) ≈ 10.0")
    println()
    
    // Example 4: Find minimum of parabola
    println("Example 4: Find Minimum of f(x) = (x-3)² + 2")
    val parabola2 = { x: Double -> (x - 3) * (x - 3) + 2 }
    val minX = ternarySearchMinimum(0.0, 10.0, f = parabola2)
    println("Minimum at x = $minX")
    println("f($minX) = ${parabola2(minX)}")
    println("Expected: x ≈ 3.0, f(x) ≈ 2.0")
    println()
    
    // Example 5: Find maximum of sine wave
    println("Example 5: Find Maximum of f(x) = sin(x) in [0, π]")
    val sine = { x: Double -> kotlin.math.sin(x) }
    val maxSineX = ternarySearchMaximum(0.0, kotlin.math.PI, f = sine)
    println("Maximum at x = $maxSineX")
    println("f($maxSineX) = ${sine(maxSineX)}")
    println("Expected: x ≈ ${kotlin.math.PI/2} (≈1.57), f(x) ≈ 1.0")
    println()
    
    // Example 6: Find peak in mountain array
    println("Example 6: Find Peak in Mountain Array")
    val mountain = intArrayOf(1, 3, 5, 7, 9, 7, 5, 3, 1)
    println("Array: ${mountain.contentToString()}")
    val peakIdx = findPeakElement(mountain)
    println("Peak at index:  $peakIdx")
    println("Peak value: ${mountain[peakIdx]}")
    println("Expected:  index 4, value 9")
    println()
    
    // Example 7: Practical application - minimize cost
    println("Example 7: Minimize Shipping Cost")
    println("Cost function: f(x) = 0.5x² - 4x + 10")
    val shippingCost = { x:  Double -> 0.5 * x * x - 4 * x + 10 }
    val optimalX = ternarySearchMinimum(0.0, 10.0, f = shippingCost)
    println("Optimal shipping point: $optimalX")
    println("Minimum cost: ${shippingCost(optimalX)}")
    println("Expected: x ≈ 4.0, cost ≈ 2.0")
}

/**
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. OPTIMIZATION PROBLEMS:
 *    ✅ Finding optimal price to maximize profit
 *    ✅ Minimizing production costs
 *    ✅ Finding best angle for projectile motion
 * 
 * 2. PHYSICS & ENGINEERING:
 *    ✅ Finding maximum height of trajectory
 *    ✅ Optimal frequency in signal processing
 *    ✅ Peak stress in materials
 * 
 * 3. COMPUTER GRAPHICS:
 *    ✅ Finding intersection points
 *    ✅ Curve fitting
 *    ✅ Animation timing functions
 * 
 * 4. MACHINE LEARNING:
 *    ✅ Hyperparameter tuning (when convex)
 *    ✅ Finding optimal learning rate
 *    ✅ Loss function minimization (unimodal case)
 * 
 * 5. GAME DEVELOPMENT: 
 *    ✅ Finding peak of trajectory
 *    ✅ Optimal jump force
 *    ✅ AI decision making
 * 
 * 6. FINANCIAL ANALYSIS: 
 *    ✅ Finding optimal investment point
 *    ✅ Maximum return calculations
 *    ✅ Risk minimization
 * 
 * ============================================================================
 * COMPARISON:  WHEN TO USE WHAT?
 * ============================================================================
 * 
 * Use BINARY SEARCH when:
 * ✅ Searching in sorted array
 * ✅ Need fastest search algorithm
 * ✅ Yes/No decision problems
 * 
 * Use TERNARY SEARCH when:
 * ✅ Finding max/min of unimodal function
 * ✅ Optimization problems
 * ✅ Finding peak/valley
 * ✅ Convex/Concave function analysis
 * 
 * Use GOLDEN SECTION SEARCH when:
 * ✅ Similar to ternary but with golden ratio
 * ✅ Slightly more efficient than ternary
 * ✅ Mathematical optimization
 * 
 * ============================================================================
 * RELATED PROBLEMS & TOPICS
 * ============================================================================
 * 
 * 1. Peak Index in Mountain Array (LeetCode 852)
 * 2. Find Peak Element (LeetCode 162)
 * 3. Minimum in Rotated Sorted Array
 * 4. Search in Rotated Sorted Array
 * 5. Aggressive Cows Problem
 * 6. Wood Cutting Problem
 * 7. 
