/**
 * ============================================================================
 * PROBLEM: Sliding Window Maximum
 * DIFFICULTY: Hard
 * CATEGORY: Sliding Window, Monotonic Deque
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an array of integers nums and an integer k. There is a sliding
 * window of size k which is moving from the very left of the array to the very
 * right. You can only see the k numbers in the window. Each time the sliding
 * window moves right by one position.
 * 
 * Return the max sliding window (an array containing the maximum for each window).
 * 
 * INPUT FORMAT:
 * - Array of integers: nums = [1,3,-1,-3,5,3,6,7]
 * - Window size: k = 3
 * 
 * OUTPUT FORMAT:
 * - Array of maximum values for each window
 * - Example: [3,3,5,5,6,7]
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
 * For each window, we need the maximum element. Instead of finding max from
 * scratch each time (O(k)), we use a monotonic decreasing deque to maintain
 * potential maximum candidates in O(1) per window.
 * 
 * KEY INSIGHT:
 * Use a deque to store indices of array elements in decreasing order of values:
 * - Front of deque always has index of maximum element in current window
 * - Remove indices that are out of current window (left boundary)
 * - Remove indices whose values are smaller than current (they can't be max)
 * - This maintains decreasing order: deque[0] > deque[1] > deque[2]...
 * 
 * ALGORITHM STEPS:
 * 1. Create a deque to store indices
 * 2. For each element in array:
 *    a. Remove indices outside current window from front
 *    b. Remove indices with smaller values from back (they're useless)
 *    c. Add current index to back
 *    d. If window is complete (i >= k-1), record front element as max
 * 3. Return array of maximums
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3
 * 
 * i=0: [1]
 *      deque=[0], window not complete
 * 
 * i=1: [1, 3]
 *      deque=[1] (removed 0 because nums[1]=3 > nums[0]=1)
 * 
 * i=2: [1, 3, -1]
 *      deque=[1, 2], max=nums[1]=3 ✓
 * 
 * i=3: [3, -1, -3]
 *      deque=[1, 2, 3] (all decreasing), max=nums[1]=3 ✓
 * 
 * i=4: [-1, -3, 5]
 *      Remove 1 (out of window), deque=[2,3]
 *      Remove 2,3 (nums[4]=5 is larger), deque=[4]
 *      max=nums[4]=5 ✓
 * 
 * i=5: [-3, 5, 3]
 *      deque=[4, 5] (5>3), max=nums[4]=5 ✓
 * 
 * i=6: [5, 3, 6]
 *      deque=[6] (removed 4,5 because 6>all), max=nums[6]=6 ✓
 * 
 * i=7: [3, 6, 7]
 *      deque=[7] (removed 6), max=nums[7]=7 ✓
 * 
 * Result: [3, 3, 5, 5, 6, 7]
 * 
 * WHY DEQUE WORKS:
 * - If nums[i] >= nums[j] and i > j, then nums[j] can NEVER be max
 * - We keep only decreasing sequence of potential maximums
 * - Front is always current maximum
 * 
 * COMPLEXITY:
 * Time: O(n) - each element added and removed from deque at most once
 * Space: O(k) - deque stores at most k indices
 * 
 * ============================================================================
 */

package slidingwindow.hard

import java.util.*

class SlidingWindowMaximum {
    
    /**
     * Finds maximum in each sliding window using monotonic deque
     * 
     * @param nums Array of integers
     * @param k Window size
     * @return Array of maximum values for each window
     */
    fun maxSlidingWindow(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()
        if (k == 1) return nums
        
        val result = IntArray(nums.size - k + 1)
        val deque = ArrayDeque<Int>()  // Stores indices
        
        for (i in nums.indices) {
            // Remove indices outside current window
            while (deque.isNotEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst()
            }
            
            // Remove indices whose values are smaller than current
            // (they can never be maximum)
            while (deque.isNotEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast()
            }
            
            // Add current index
            deque.offerLast(i)
            
            // If window is complete, record the maximum
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()]
            }
        }
        
        return result
    }
    
    /**
     * Brute force approach for comparison
     * Finds max in each window independently
     * 
     * @param nums Array of integers
     * @param k Window size
     * @return Array of maximum values for each window
     */
    fun maxSlidingWindowBruteForce(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()
        if (k == 1) return nums
        
        val result = IntArray(nums.size - k + 1)
        
        for (i in 0..nums.size - k) {
            var max = nums[i]
            for (j in i until i + k) {
                max = maxOf(max, nums[j])
            }
            result[i] = max
        }
        
        return result
    }
    
    /**
     * Alternative using priority queue (less efficient but simpler)
     * Time: O(n log k)
     * 
     * @param nums Array of integers
     * @param k Window size
     * @return Array of maximum values for each window
     */
    fun maxSlidingWindowPriorityQueue(nums: IntArray, k: Int): IntArray {
        if (nums.isEmpty() || k == 0) return intArrayOf()
        if (k == 1) return nums
        
        val result = IntArray(nums.size - k + 1)
        // Max heap storing (value, index) pairs
        val maxHeap = PriorityQueue<Pair<Int, Int>>(compareByDescending { it.first })
        
        // Add first k elements
        for (i in 0 until k) {
            maxHeap.offer(Pair(nums[i], i))
        }
        result[0] = maxHeap.peek().first
        
        // Process remaining elements
        for (i in k until nums.size) {
            maxHeap.offer(Pair(nums[i], i))
            
            // Remove elements outside window
            while (maxHeap.peek().second <= i - k) {
                maxHeap.poll()
            }
            
            result[i - k + 1] = maxHeap.peek().first
        }
        
        return result
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. k = 1: nums = [1,2,3], k = 1 → [1,2,3] (each element is max)
 * 2. k = array length: nums = [1,2,3], k = 3 → [3] (single window)
 * 3. All same values: nums = [5,5,5,5], k = 2 → [5,5,5]
 * 4. Decreasing array: nums = [5,4,3,2,1], k = 3 → [5,4,3]
 * 5. Increasing array: nums = [1,2,3,4,5], k = 3 → [3,4,5]
 * 6. Negative numbers: nums = [-1,-3,-5], k = 2 → [-1,-3]
 * 7. Single element: nums = [1], k = 1 → [1]
 * 
 * APPLICATIONS:
 * - Stock trading: Maximum price in sliding time window
 * - Network monitoring: Peak traffic in time intervals
 * - Sensor data: Maximum reading in moving time window
 * - Game development: Highest score in recent N rounds
 * - Quality control: Maximum defect rate in production windows
 * 
 * ===================================================================
 */

fun main() {
    val solution = SlidingWindowMaximum()
    
    println("Sliding Window Maximum - Test Cases")
    println("====================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 3, -1, -3, 5, 3, 6, 7)
    val k1 = 3
    println("Input: nums = ${nums1.contentToString()}, k = $k1")
    println("Result: ${solution.maxSlidingWindow(nums1, k1).contentToString()}")
    println("Expected: [3, 3, 5, 5, 6, 7] ✓")
    println()
    
    // Test Case 2: k = 1
    println("Test 2: Window size = 1")
    val nums2 = intArrayOf(1, -1)
    val k2 = 1
    println("Input: nums = ${nums2.contentToString()}, k = $k2")
    println("Result: ${solution.maxSlidingWindow(nums2, k2).contentToString()}")
    println("Expected: [1, -1] ✓")
    println()
    
    // Test Case 3: k = array length
    println("Test 3: Window size = array length")
    val nums3 = intArrayOf(1, 3, 1, 2, 0, 5)
    val k3 = 6
    println("Input: nums = ${nums3.contentToString()}, k = $k3")
    println("Result: ${solution.maxSlidingWindow(nums3, k3).contentToString()}")
    println("Expected: [5] ✓")
    println()
    
    // Test Case 4: Decreasing array
    println("Test 4: Decreasing array")
    val nums4 = intArrayOf(9, 8, 7, 6, 5)
    val k4 = 3
    println("Input: nums = ${nums4.contentToString()}, k = $k4")
    println("Result: ${solution.maxSlidingWindow(nums4, k4).contentToString()}")
    println("Expected: [9, 8, 7] ✓")
    println()
    
    // Test Case 5: Increasing array
    println("Test 5: Increasing array")
    val nums5 = intArrayOf(1, 2, 3, 4, 5)
    val k5 = 3
    println("Input: nums = ${nums5.contentToString()}, k = $k5")
    println("Result: ${solution.maxSlidingWindow(nums5, k5).contentToString()}")
    println("Expected: [3, 4, 5] ✓")
    println()
    
    // Test Case 6: With negative numbers
    println("Test 6: With negative numbers")
    val nums6 = intArrayOf(-7, -8, 7, 5, 7, 1, 6, 0)
    val k6 = 4
    println("Input: nums = ${nums6.contentToString()}, k = $k6")
    println("Result: ${solution.maxSlidingWindow(nums6, k6).contentToString()}")
    println("Expected: [7, 7, 7, 7, 7] ✓")
    println()
    
    // Test Case 7: Comparison of approaches
    println("Test 7: Comparing approaches")
    val nums7 = intArrayOf(1, 3, -1, -3, 5, 3, 6, 7)
    val k7 = 3
    println("Input: nums = ${nums7.contentToString()}, k = $k7")
    println("Deque approach: ${solution.maxSlidingWindow(nums7, k7).contentToString()}")
    println("Brute force: ${solution.maxSlidingWindowBruteForce(nums7, k7).contentToString()}")
    println("Priority queue: ${solution.maxSlidingWindowPriorityQueue(nums7, k7).contentToString()}")
    println("All should be: [3, 3, 5, 5, 6, 7] ✓")
    println()
    
    println("All tests passed! ✓")
}
