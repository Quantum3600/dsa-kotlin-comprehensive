/**
 * ============================================================================
 * PROBLEM: Next Greater Element I
 * DIFFICULTY: Easy
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given two arrays nums1 and nums2 where nums1 is a subset of nums2.
 * For each element in nums1, find the next greater element in nums2.
 * The next greater element of x in nums2 is the first greater element to 
 * the right of x in nums2. Return -1 if there is no greater element.
 * 
 * INPUT FORMAT:
 * - nums1: Array of integers (subset of nums2)
 * - nums2: Array of integers (all elements are unique)
 * Example: nums1 = [4, 1, 2], nums2 = [1, 3, 4, 2]
 * 
 * OUTPUT FORMAT:
 * - Array of integers where output[i] is the next greater element of nums1[i]
 * Example: [-1, 3, -1]
 * 
 * CONSTRAINTS:
 * - 1 <= nums1.length <= nums2.length <= 1000
 * - 0 <= nums1[i], nums2[i] <= 10^4
 * - All integers in nums1 and nums2 are unique
 * - All integers of nums1 also appear in nums2
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine standing in line at a theme park where heights matter. Each person
 * wants to know who's the first taller person behind them.
 * 
 * Instead of checking backwards from each position (inefficient), we can:
 * 1. Process right to left
 * 2. Maintain a stack of "candidates" (elements we've seen)
 * 3. For current element, pop smaller elements (they can't be NGE)
 * 4. Top of stack is the NGE!
 * 
 * KEY INSIGHT - MONOTONIC STACK:
 * A monotonic stack maintains elements in a specific order (increasing/decreasing).
 * Here we use a MONOTONIC DECREASING stack:
 * - Stack keeps elements in decreasing order from bottom to top
 * - When we see a new element, we pop all smaller elements
 * - This ensures we can quickly find the next greater element
 * 
 * ALGORITHM STEPS:
 * 1. Create a map to store NGE for each element in nums2
 * 2. Traverse nums2 from right to left
 * 3. For each element:
 *    a. Pop elements from stack while they are <= current element
 *    b. If stack is not empty, top is NGE, else -1
 *    c. Push current element to stack
 * 4. For each element in nums1, look up NGE from map
 * 
 * VISUAL EXAMPLE:
 * ```
 * nums2 = [1, 3, 4, 2]
 * 
 * Process from right to left:
 * 
 * i=3, num=2:
 *   Stack: []
 *   NGE[2] = -1
 *   Stack: [2]
 * 
 * i=2, num=4:
 *   Pop 2 (4 > 2)
 *   Stack: []
 *   NGE[4] = -1
 *   Stack: [4]
 * 
 * i=1, num=3:
 *   4 > 3, don't pop
 *   Stack: [4]
 *   NGE[3] = 4
 *   Stack: [4, 3]
 * 
 * i=0, num=1:
 *   3 > 1, don't pop
 *   Stack: [4, 3]
 *   NGE[1] = 3
 *   Stack: [4, 3, 1]
 * 
 * Map: {1->3, 3->4, 4->-1, 2->-1}
 * 
 * nums1 = [4, 1, 2]
 * Result: [NGE[4], NGE[1], NGE[2]] = [-1, 3, -1]
 * ```
 * 
 * WHY MONOTONIC STACK WORKS:
 * - Elements in stack are in decreasing order
 * - When we see element X, all smaller elements can't be NGE for X
 * - First element > X in stack is the NGE
 * - Time efficient: each element pushed/popped at most once
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - For each element, scan right to find next greater
 * 2. Monotonic Stack: O(n) - Current optimal approach
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n + m)
 * - n = nums2.length, m = nums1.length
 * - Process nums2: O(n) - each element pushed/popped once
 * - Build result: O(m) - lookup in map for each element in nums1
 * 
 * SPACE COMPLEXITY: O(n)
 * - HashMap to store NGE for all elements in nums2: O(n)
 * - Stack can grow up to O(n) in worst case (strictly decreasing array)
 * - Result array: O(m) but required for output
 * 
 * WHY EACH ELEMENT IS PUSHED/POPPED ONCE:
 * - Each element enters stack exactly once
 * - Each element can be popped at most once
 * - Total operations: 2n = O(n)
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class NextGreaterElement {
    
    /**
     * Finds next greater element for each element in nums1 from nums2
     * Uses monotonic decreasing stack for O(n) solution
     */
    fun nextGreaterElement(nums1: IntArray, nums2: IntArray): IntArray {
        // Map to store next greater element for each number in nums2
        val ngeMap = HashMap<Int, Int>()
        
        // Monotonic decreasing stack
        val stack = Stack<Int>()
        
        // Traverse nums2 from right to left
        for (i in nums2.indices.reversed()) {
            val current = nums2[i]
            
            // Pop elements smaller than or equal to current
            // They can never be NGE for current element
            while (stack.isNotEmpty() && stack.peek() <= current) {
                stack.pop()
            }
            
            // If stack is empty, no greater element exists
            // Otherwise, top of stack is the NGE
            ngeMap[current] = if (stack.isEmpty()) -1 else stack.peek()
            
            // Push current element for future elements
            stack.push(current)
        }
        
        // Build result for nums1 using the map
        return IntArray(nums1.size) { i -> ngeMap[nums1[i]] ?: -1 }
    }
    
    /**
     * Alternative: Process left to right
     * Different stack maintenance but same complexity
     */
    fun nextGreaterElementLeftToRight(nums1: IntArray, nums2: IntArray): IntArray {
        val ngeMap = HashMap<Int, Int>()
        val stack = Stack<Int>()
        
        // Process nums2 from left to right
        for (num in nums2) {
            // For all elements in stack smaller than current,
            // current is their NGE
            while (stack.isNotEmpty() && stack.peek() < num) {
                ngeMap[stack.pop()] = num
            }
            stack.push(num)
        }
        
        // Remaining elements in stack have no NGE
        while (stack.isNotEmpty()) {
            ngeMap[stack.pop()] = -1
        }
        
        return IntArray(nums1.size) { i -> ngeMap[nums1[i]] ?: -1 }
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. No greater element exists: nums1=[4], nums2=[1,2,3,4] → [-1]
 * 2. All elements greater: nums1=[1], nums2=[1,2,3,4] → [2]
 * 3. Single element: nums1=[5], nums2=[5] → [-1]
 * 4. Decreasing array: nums2=[5,4,3,2,1] → all -1
 * 5. Increasing array: nums2=[1,2,3,4,5] → each has next element
 * 
 * TRACE THROUGH EXAMPLE:
 * Input: nums1=[4,1,2], nums2=[1,3,4,2]
 * 
 * Step-by-step with stack:
 * i=3: num=2, stack=[], NGE[2]=-1, stack=[2]
 * i=2: num=4, pop 2, stack=[], NGE[4]=-1, stack=[4]
 * i=1: num=3, stack=[4], NGE[3]=4, stack=[4,3]
 * i=0: num=1, stack=[4,3], NGE[1]=3, stack=[4,3,1]
 * 
 * Result for nums1: [NGE[4], NGE[1], NGE[2]] = [-1, 3, -1] ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = NextGreaterElement()
    
    println("Next Greater Element I - Test Cases")
    println("=====================================\n")
    
    // Test 1: Basic case
    println("Test 1: Basic Case")
    val nums1_1 = intArrayOf(4, 1, 2)
    val nums2_1 = intArrayOf(1, 3, 4, 2)
    println("nums1: ${nums1_1.contentToString()}")
    println("nums2: ${nums2_1.contentToString()}")
    println("Output: ${solution.nextGreaterElement(nums1_1, nums2_1).contentToString()}")
    println("Expected: [-1, 3, -1]\n")
    
    // Test 2: All have NGE
    println("Test 2: All Have Next Greater")
    val nums1_2 = intArrayOf(2, 4)
    val nums2_2 = intArrayOf(1, 2, 3, 4)
    println("nums1: ${nums1_2.contentToString()}")
    println("nums2: ${nums2_2.contentToString()}")
    println("Output: ${solution.nextGreaterElement(nums1_2, nums2_2).contentToString()}")
    println("Expected: [3, -1]\n")
    
    // Test 3: Decreasing sequence
    println("Test 3: Decreasing Sequence")
    val nums1_3 = intArrayOf(5, 3, 1)
    val nums2_3 = intArrayOf(5, 4, 3, 2, 1)
    println("nums1: ${nums1_3.contentToString()}")
    println("nums2: ${nums2_3.contentToString()}")
    println("Output: ${solution.nextGreaterElement(nums1_3, nums2_3).contentToString()}")
    println("Expected: [-1, -1, -1]\n")
    
    // Test 4: Increasing sequence
    println("Test 4: Increasing Sequence")
    val nums1_4 = intArrayOf(1, 3, 5)
    val nums2_4 = intArrayOf(1, 2, 3, 4, 5)
    println("nums1: ${nums1_4.contentToString()}")
    println("nums2: ${nums2_4.contentToString()}")
    println("Output: ${solution.nextGreaterElement(nums1_4, nums2_4).contentToString()}")
    println("Expected: [2, 4, -1]\n")
    
    // Test 5: Single element
    println("Test 5: Single Element")
    val nums1_5 = intArrayOf(7)
    val nums2_5 = intArrayOf(7)
    println("nums1: ${nums1_5.contentToString()}")
    println("nums2: ${nums2_5.contentToString()}")
    println("Output: ${solution.nextGreaterElement(nums1_5, nums2_5).contentToString()}")
    println("Expected: [-1]\n")
    
    // Test 6: Alternative approach
    println("Test 6: Left-to-Right Approach")
    println("nums1: ${nums1_1.contentToString()}")
    println("nums2: ${nums2_1.contentToString()}")
    println("Output: ${solution.nextGreaterElementLeftToRight(nums1_1, nums2_1).contentToString()}")
    println("Expected: [-1, 3, -1]\n")
    
    // Performance demonstration
    println("=== Monotonic Stack Benefits ===")
    println("Without monotonic stack: O(n²) - check each element against all following")
    println("With monotonic stack: O(n) - each element pushed/popped once")
    println("\nFor nums2 with 1000 elements:")
    println("Brute force: ~1,000,000 comparisons")
    println("Monotonic stack: ~2,000 operations (push+pop)")
}
