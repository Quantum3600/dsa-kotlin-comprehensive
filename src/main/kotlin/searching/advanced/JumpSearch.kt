/**
 * ============================================================================
 * PROBLEM:  Jump Search
 * DIFFICULTY:  Medium
 * CATEGORY:  Searching - Advanced
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a **sorted** array of integers and a target value, find the index of the
 * target using jump search algorithm. If the target is not found, return -1.
 * 
 * INPUT FORMAT:
 * - A sorted array of integers:  arr = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
 * - A target integer:  target = 13
 * 
 * OUTPUT FORMAT:
 * - Index of target if found: 6
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
 * Imagine you're in a long hallway looking for room 450. Instead of checking
 * EVERY door (linear search) or using complex calculations (binary search),
 * you check every 10th door:  10, 20, 30...  When you see 500, you know to
 * go back and check rooms 400-500 one by one.
 * 
 * KEY INSIGHT:
 * Jump Search is a compromise between Linear Search and Binary Search: 
 * - Faster than linear search:  Makes large jumps
 * - Simpler than binary search: Only moves forward
 * - Better for systems where backward jumps are costly
 * 
 * OPTIMAL JUMP SIZE:
 * Mathematical analysis shows that √n is the OPTIMAL jump size! 
 * - Too small: Approaches linear search O(n)
 * - Too large: More linear searching after jump
 * - Just right: √n gives O(√n) complexity
 * 
 * ALGORITHM STEPS:
 * 1. Calculate jump step = √n
 * 2. Jump forward in steps of √n until arr[step] >= target
 * 3. Perform linear search backwards from current step
 * 4. Return index if found, else -1
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19], target = 13
 * n = 10, step = √10 ≈ 3
 * 
 * Step 1: Jump Phase
 * [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
 *  ^        ^           ^
 * pos=0   pos=3       pos=6
 * 
 * pos = 0:  arr[0] = 1 < 13, jump
 * pos = 3: arr[3] = 7 < 13, jump
 * pos = 6: arr[6] = 13 >= 13, STOP
 * 
 * Step 2: Linear Search from pos=3 to pos=6
 * [7, 9, 11, 13]
 *           ^
 * Found at index 6!
 * 
 * WHY √n IS OPTIMAL:
 * Total comparisons = (n/√n) jumps + √n linear checks
 *                   = √n + √n = 2√n = O(√n)
 * 
 * COMPARISON WITH OTHER ALGORITHMS:
 * 
 * Array: [1,2,3,... ,100], target = 85
 * 
 * Linear Search:    85 comparisons
 * Jump Search:      √100 + √100 ≈ 20 comparisons
 * Binary Search:    log₂(100) ≈ 7 comparisons
 * 
 * So why use Jump Search?
 * ✅ Simpler than binary search
 * ✅ Only moves forward (good for some hardware/cache)
 * ✅ Better than linear search
 * ✅ Works well with unbounded searches
 * ❌ Slower than binary search for random access
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Jump Search: O(√n) time, O(1) space
 * 2. Binary Search: O(log n) time, O(1) space - FASTER but more complex
 * 3. Linear Search: O(n) time, O(1) space - Simpler but slower
 * 4. Interpolation:  O(log log n) avg, but only for uniform data
 * 
 * ============================================================================
 * TIME & SPACE COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: 
 * - Jump Phase: O(√n) - We make n/√n = √n jumps
 * - Linear Phase: O(√n) - Linear search within one block of size √n
 * - Total:  O(√n) + O(√n) = O(√n)
 * 
 * Practical Examples:
 * n = 100   →  √n = 10    (vs log n = 7 for binary)
 * n = 10000 →  √n = 100   (vs log n = 14 for binary)
 * n = 1M    →  √n = 1000  (vs log n = 20 for binary)
 * 
 * SPACE COMPLEXITY:
 * - O(1): Only using constant extra space (step, prev, current)
 * 
 * ============================================================================
 * EDGE CASES & ERROR HANDLING
 * ============================================================================
 * 
 * 1. Empty array: Return -1
 * 2. Single element: Direct comparison
 * 3. Target at first position: Found in jump phase
 * 4. Target at last position: Found in linear phase
 * 5. Target not in array: Linear search returns -1
 * 6. Target smaller than arr[0]:  Handled by first check
 * 7. Target larger than arr[n-1]:  Handled by jump phase
 * 8. Duplicate elements: Returns first occurrence
 * 9. Array size is perfect square: √n is exact
 * 10. Array size is not perfect square: √n is rounded
 * 
 * ============================================================================
 * EXAMPLES
 * ============================================================================
 */

package searching.advanced

import kotlin.math.sqrt
import kotlin.math.min

/**
 * Performs jump search on a sorted array.
 * 
 * @param arr Sorted array of integers in ascending order
 * @param target Value to search for
 * @return Index of target if found, -1 otherwise
 */
fun jumpSearch(arr: IntArray, target:  Int): Int {
    val n = arr.size
    
    // Edge case: empty array
    if (n == 0) return -1
    
    // Calculate optimal jump step:  √n
    val step = sqrt(n.toDouble()).toInt()
    
    // Finding the block where target may be present
    var prev = 0
    var curr = step
    
    // Jump through blocks until we find a block that might contain target
    while (curr < n && arr[curr] < target) {
        prev = curr
        curr += step
        
        // If we've gone beyond array, adjust to last element
        if (curr >= n) {
            curr = n
        }
    }
    
    // Linear search in the identified block
    // Search from prev to min(curr, n-1)
    for (i in prev until min(curr, n)) {
        if (arr[i] == target) {
            return i
        }
        // Early termination if we've passed target
        if (arr[i] > target) {
            return -1
        }
    }
    
    return -1
}

/**
 * Alternative implementation with clearer separation of phases.
 */
fun jumpSearchVerbose(arr: IntArray, target: Int): Int {
    val n = arr.size
    if (n == 0) return -1
    
    // Calculate jump size
    val jumpSize = sqrt(n.toDouble()).toInt()
    
    // Phase 1: Jump to find the right block
    var blockStart = 0
    var blockEnd = jumpSize
    
    while (blockEnd < n && arr[blockEnd] < target) {
        blockStart = blockEnd
        blockEnd += jumpSize
    }
    
    // Adjust blockEnd if we've overshot
    if (blockEnd > n) {
        blockEnd = n
    }
    
    // Phase 2: Linear search within the block
    for (i in blockStart until blockEnd) {
        when {
            arr[i] == target -> return i
            arr[i] > target -> return -1  // Target would be here if it existed
        }
    }
    
    return -1
}

/**
 * Jump search with custom jump size (for experimentation).
 * 
 * @param customJumpSize Custom jump size (default is √n)
 */
fun jumpSearchCustom(arr: IntArray, target: Int, customJumpSize: Int = 0): Int {
    val n = arr.size
    if (n == 0) return -1
    
    // Use custom jump size or default to √n
    val jump = if (customJumpSize > 0) customJumpSize else sqrt(n.toDouble()).toInt()
    
    var prev = 0
    
    // Jump phase
    while (prev < n && arr[prev] < target) {
        val next = min(prev + jump, n - 1)
        if (arr[next] >= target) break
        prev = next
    }
    
    // Linear search phase
    val end = min(prev + jump, n)
    for (i in prev until end) {
        if (arr[i] == target) return i
        if (arr[i] > target) return -1
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
    val arr1 = intArrayOf(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
    val target1 = 13
    println("Example 1: Basic Jump Search")
    println("Array:  ${arr1.contentToString()}")
    println("Target: $target1")
    println("Jump size: √${arr1.size} = ${sqrt(arr1.size. toDouble()).toInt()}")
    println("Result: ${jumpSearch(arr1, target1)}")
    println("Expected: 6")
    println()
    
    // Example 2: Target at beginning
    val arr2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val target2 = 1
    println("Example 2: Target at Beginning")
    println("Array: ${arr2.contentToString()}")
    println("Target: $target2")
    println("Result: ${jumpSearch(arr2, target2)}")
    println("Expected: 0")
    println()
    
    // Example 3: Target at end
    val arr3 = intArrayOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
    val target3 = 100
    println("Example 3: Target at End")
    println("Array: ${arr3.contentToString()}")
    println("Target: $target3")
    println("Result: ${jumpSearch(arr3, target3)}")
    println("Expected: 9")
    println()
    
    // Example 4: Target not found
    val arr4 = intArrayOf(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
    val target4 = 15
    println("Example 4: Target Not Found")
    println("Array:  ${arr4.contentToString()}")
    println("Target: $target4")
    println("Result: ${jumpSearch(arr4, target4)}")
    println("Expected: -1")
    println()
    
    // Example 5: Large array
    val arr5 = IntArray(100) { it + 1 }
    val target5 = 85
    println("Example 5: Large Array")
    println("Array: [1, 2, 3, .. ., 100]")
    println("Target: $target5")
    println("Jump size: √100 = 10")
    println("Result: ${jumpSearch(arr5, target5)}")
    println("Expected: 84")
    println()
    
    // Example 6: Performance comparison with different jump sizes
    val arr6 = IntArray(10000) { it * 2 }
    val target6 = 10000
    println("Example 6:  Comparing Different Jump Sizes")
    println("Array size: 10000, Target: $target6")
    println("Optimal jump (√n = 100): ${jumpSearchCustom(arr6, target6, 100)}")
    println("Small jump (10): ${jumpSearchCustom(arr6, target6, 10)} (more like linear)")
    println("Large jump (1000): ${jumpSearchCustom(arr6, target6, 1000)} (more linear search)")
    println("Default (√n): ${jumpSearch(arr6, target6)}")
}

/**
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. SEQUENTIAL STORAGE SYSTEMS:
 *    - Tape drives (can't jump backward efficiently)
 *    - Sequential files
 *    - Linked lists (with some modifications)
 * 
 * 2. CACHE-FRIENDLY OPERATIONS:
 *    - Forward-only memory access patterns
 *    - CPU cache optimization
 *    - Streaming data
 * 
 * 3. EMBEDDED SYSTEMS:
 *    - Limited memory for recursion
 *    - Simpler implementation than binary search
 *    - Predictable memory access
 * 
 * 4. DATABASE INDEXING:
 *    - Index scanning with skip pointers
 *    - B+ tree traversal optimization
 * 
 * 5. NETWORK ROUTING:
 *    - Skip list implementations
 *    - Routing table lookups
 * 
 * 6. REAL-TIME SYSTEMS:
 *    - Predictable execution time
 *    - No recursive overhead
 * 
 * ============================================================================
 * ADVANTAGES OVER BINARY SEARCH
 * ============================================================================
 * 
 * 1. Simpler implementation (no recursion needed)
 * 2. Only forward jumps (better cache locality)
 * 3. Works well with sequential access storage
 * 4. Easier to understand and debug
 * 5. Can be interrupted and resumed
 * 6. Works with data structures that don't support random access
 * 
 * DISADVANTAGES:
 * 1. Slower than binary search: O(√n) vs O(log n)
 * 2. Still requires sorted array
 * 3. Not as widely known or used
 * 
 * ============================================================================
 * RELATED PROBLEMS
 * ============================================================================
 * 
 * 1. Search in Sorted Array
 * 2. Find Minimum in Rotated Sorted Array
 * 3. Search in Nearly Sorted Array
 * 4. Find Peak Element
 * 5. Skip List Implementation
 * 6. Square Root Decomposition
 * 
 * ============================================================================
 */
