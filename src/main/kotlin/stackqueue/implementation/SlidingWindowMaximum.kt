/**
 * ============================================================================
 * PROBLEM: Sliding Window Maximum
 * DIFFICULTY: Hard
 * CATEGORY: Stack & Queue Implementation (Deque)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers and a sliding window of size k, find the maximum
 * value in each window as it slides from left to right through the array.
 * 
 * INPUT FORMAT:
 * nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 * 
 * OUTPUT FORMAT:
 * [3, 3, 5, 5, 6, 7]
 * 
 * Explanation:
 * Window [1, 3, -1] → max = 3
 * Window [3, -1, -3] → max = 3
 * Window [-1, -3, 5] → max = 5
 * Window [-3, 5, 3] → max = 5
 * Window [5, 3, 6] → max = 6
 * Window [3, 6, 7] → max = 7
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^5
 * - -10^4 <= nums[i] <= 10^4
 * - 1 <= k <= nums.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Naive approach: For each window, find max → O(n*k)
 * Better: Keep track of potential maximums using a deque
 * 
 * KEY INSIGHT:
 * Use a **Monotonic Decreasing Deque**:
 * - Store indices (not values) in decreasing order of their values
 * - Front of deque = current maximum
 * - Remove elements outside current window
 * - Remove elements smaller than current (they'll never be max)
 * 
 * WHY DEQUE?
 * - Remove from both ends:  O(1)
 * - Can remove expired indices from front
 * - Can remove smaller elements from back
 * 
 * MONOTONIC DECREASING: 
 * - Deque maintains:  values[deque[0]] >= values[deque[1]] >= ... 
 * - Why?  Smaller elements can't be max if larger element is present
 * - Newer larger element makes older smaller elements useless
 * 
 * ALGORITHM STEPS:
 * 1. **Initialize** deque and result list
 * 
 * 2. **For each element:**
 *    a. Remove indices outside window (from front)
 *    b. Remove indices with smaller values (from back)
 *    c. Add current index to deque
 *    d. If window is full (i >= k-1), add front of deque to result
 * 
 * 3. **Return** result
 * 
 * VISUAL EXAMPLE:
 * ```
 * nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 * 
 * i=0, val=1:  deque=[0]                 window not full
 * i=1, val=3:  deque=[1]                 window not full (1>3, remove 0)
 * i=2, val=-1: deque=[1,2]               window full!  max=3 ← nums[1]
 * i=3, val=-3: deque=[1,2,3]             max=3 ← nums[1]
 * i=4, val=5:  deque=[4]                 max=5 ← nums[4] (5>all, clear deque)
 * i=5, val=3:  deque=[4,5]               max=5 ← nums[4]
 * i=6, val=6:  deque=[6]                 max=6 ← nums[6] (6>3, remove 5)
 * i=7, val=7:  deque=[7]                 max=7 ← nums[7] (7>6, remove 6)
 * 
 * Result: [3, 3, 5, 5, 6, 7]
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Each element added to deque once:  O(n)
 * - Each element removed from deque once: O(n)
 * - Total operations: O(2n) = O(n)
 * 
 * WHY O(n) not O(n*k):
 * - Each element processed at most twice (add and remove)
 * - Amortized O(1) per element
 * 
 * SPACE COMPLEXITY:  O(k)
 * - Deque stores at most k elements
 * - Result array:  O(n-k+1) but not counted as auxiliary space
 * 
 * ============================================================================
 * IMPLEMENTATION
 * ============================================================================
 */

fun maxSlidingWindow(nums: IntArray, k:  Int): IntArray {
    val n = nums.size
    val result = IntArray(n - k + 1)
    val deque = ArrayDeque<Int>()  // Store indices
    
    for (i in nums.indices) {
        // Step 1: Remove indices outside current window
        // Window range: [i-k+1, i]
        while (deque.isNotEmpty() && deque.first() < i - k + 1) {
            deque.removeFirst()
        }
        
        // Step 2: Remove indices whose values are smaller than current
        // (they can never be maximum in current or future windows)
        while (deque.isNotEmpty() && nums[deque.last()] < nums[i]) {
            deque.removeLast()
        }
        
        // Step 3: Add current index
        deque.addLast(i)
        
        // Step 4: If window is full, record maximum
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque.first()]
        }
    }
    
    return result
}

/**
 * ============================================================================
 * ALTERNATIVE IMPLEMENTATION:  Cleaner Version
 * ============================================================================
 */

fun maxSlidingWindowV2(nums: IntArray, k: Int): IntArray {
    if (nums.isEmpty() || k == 0) return intArrayOf()
    if (k == 1) return nums
    
    val deque = ArrayDeque<Int>()
    val result = mutableListOf<Int>()
    
    nums.forEachIndexed { i, num ->
        // Remove expired indices
        if (deque.isNotEmpty() && deque.first() <= i - k) {
            deque.removeFirst()
        }
        
        // Maintain decreasing order
        while (deque.isNotEmpty() && nums[deque.last()] < num) {
            deque.removeLast()
        }
        
        deque.addLast(i)
        
        // Add to result when window is ready
        if (i >= k - 1) {
            result. add(nums[deque.first()])
        }
    }
    
    return result.toIntArray()
}

/**
 * ============================================================================
 * USAGE EXAMPLES
 * ============================================================================
 */

fun main() {
    // Example 1: Basic case
    println("Example 1: Basic Sliding Window")
    val nums1 = intArrayOf(1, 3, -1, -3, 5, 3, 6, 7)
    val k1 = 3
    println("Input: ${nums1.contentToString()}, k=$k1")
    println("Output: ${maxSlidingWindow(nums1, k1).contentToString()}")
    // [3, 3, 5, 5, 6, 7]
    
    // Example 2: k = 1 (each element is max)
    println("\nExample 2: Window Size 1")
    val nums2 = intArrayOf(1, -1, 3, -3, 5)
    val k2 = 1
    println("Input: ${nums2.contentToString()}, k=$k2")
    println("Output: ${maxSlidingWindow(nums2, k2).contentToString()}")
    // [1, -1, 3, -3, 5]
    
    // Example 3: All elements same
    println("\nExample 3: All Same Elements")
    val nums3 = intArrayOf(7, 7, 7, 7)
    val k3 = 2
    println("Input: ${nums3.contentToString()}, k=$k3")
    println("Output: ${maxSlidingWindow(nums3, k3).contentToString()}")
    // [7, 7, 7]
    
    // Example 4:  Descending array
    println("\nExample 4: Descending Array")
    val nums4 = intArrayOf(9, 7, 5, 3, 1)
    val k4 = 3
    println("Input: ${nums4.contentToString()}, k=$k4")
    println("Output: ${maxSlidingWindow(nums4, k4).contentToString()}")
    // [9, 7, 5]
    
    // Example 5:  Ascending array
    println("\nExample 5: Ascending Array")
    val nums5 = intArrayOf(1, 3, 5, 7, 9)
    val k5 = 3
    println("Input: ${nums5.contentToString()}, k=$k5")
    println("Output: ${maxSlidingWindow(nums5, k5).contentToString()}")
    // [5, 7, 9]
}

/**
 * ============================================================================
 * ALTERNATIVE APPROACHES
 * ============================================================================
 * 
 * APPROACH 1: Brute Force
 * ```kotlin
 * fun maxSlidingWindowBruteForce(nums: IntArray, k: Int): IntArray {
 *     val result = IntArray(nums.size - k + 1)
 *     for (i in 0.. nums.size - k) {
 *         result[i] = nums. sliceArray(i until i + k).maxOrNull() ?: 0
 *     }
 *     return result
 * }
 * ```
 * Time: O(n*k), Space: O(1)
 * ❌ Too slow for large inputs
 * 
 * APPROACH 2: Max Heap/Priority Queue
 * - Store (value, index) in max heap
 * - Remove expired indices when processing
 * Time: O(n log k), Space: O(k)
 * ⚠️ Slower than deque, but easier to understand
 * 
 * APPROACH 3: Segment Tree
 * - Build segment tree for range max queries
 * - Query max for each window
 * Time: O(n log n), Space: O(n)
 * ⚠️ Overkill for this problem
 * 
 * WHY DEQUE IS BEST:
 * ✅ Optimal O(n) time
 * ✅ O(k) space
 * ✅ Amortized O(1) per operation
 * ✅ No sorting overhead
 * ✅ Standard for sliding window max/min problems
 * 
 * ============================================================================
 * SIMILAR PROBLEMS
 * ============================================================================
 * 
 * 1. **Sliding Window Minimum** - Same approach, reverse comparison
 * 2. **Longest Continuous Subarray** - Use similar deque technique
 * 3. **Jump Game VI** - DP with sliding window max
 * 4. **Constrained Subsequence Sum** - Sliding window with DP
 * 5. **Shortest Subarray with Sum >= K** - Monotonic deque
 * 
 * PATTERN: Monotonic Deque
 * - Maintain elements in monotonic order
 * - Remove elements that can't be answer
 * - Keep track of potential answers
 * 
 * ============================================================================
 * EDGE CASES & GOTCHAS
 * ============================================================================
 * 
 * 1. k = 1: Each element is its own max
 * 2. k = n: Single window, return max of array
 * 3. All elements same: All windows have same max
 * 4. Sorted ascending: Max always at right end
 * 5. Sorted descending: Max always at left end
 * 6. Negative numbers: Works the same way
 * 
 * COMMON MISTAKES:
 * ❌ Storing values instead of indices
 * ❌ Wrong window boundary check (i - k vs i - k + 1)
 * ❌ Not removing expired indices from front
 * ❌ Not maintaining monotonic property from back
 * ❌ Starting result too early (before window is full)
 * 
 * KEY POINTS:
 * ✅ Store indices, not values (need to check if expired)
 * ✅ Deque maintains monotonic decreasing ORDER
 * ✅ Front = current max, back = potential future max
 * ✅ Each element added and removed at most once
 * 
 * ============================================================================
 */
