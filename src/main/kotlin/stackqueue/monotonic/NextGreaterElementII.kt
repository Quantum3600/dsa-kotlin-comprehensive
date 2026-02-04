/**
 * ============================================================================
 * PROBLEM: Next Greater Element II (Circular Array)
 * DIFFICULTY: Medium
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a circular integer array nums, return the next greater number for 
 * every element in nums. The next greater number of a number x is the first 
 * greater number to its traversing-order next in the array, which means you 
 * could search circularly to find its next greater number. If it doesn't exist,
 * return -1 for this number.
 * 
 * INPUT FORMAT:
 * - nums: Array of integers (can be negative, positive, or zero)
 * Example: nums = [1, 2, 1]
 * 
 * OUTPUT FORMAT:
 * - Array where output[i] is the next greater element of nums[i]
 * Example: [2, -1, 2]
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^4
 * - -10^9 <= nums[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is Next Greater Element with a twist - the array is circular!
 * Think of it as: after reaching the end, we wrap around to the beginning.
 * 
 * KEY CHALLENGE:
 * Array: [1, 2, 1]
 * - For index 0 (val=1): NGE is 2 at index 1 ✓
 * - For index 1 (val=2): No element after is greater, but wrapping around,
 *   we need to check [1] again... still not greater, so -1
 * - For index 2 (val=1): Wrapping around, we check [1, 2], NGE is 2 ✓
 * 
 * CLEVER TRICK - DOUBLE THE ARRAY:
 * Instead of complex circular logic, imagine concatenating array with itself:
 * [1, 2, 1] becomes [1, 2, 1, 1, 2, 1]
 * 
 * But we don't need to create actual copy! Use modulo:
 * - Process indices 0 to 2n-1
 * - Map to array using i % n
 * - Only store results for first n elements
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result array with -1
 * 2. Create monotonic decreasing stack (stores indices)
 * 3. Iterate through 2*n elements using modulo
 * 4. For each element:
 *    a. While stack not empty and current > element at stack top:
 *       - Pop index from stack
 *       - Set result[popped_index] = current element
 *    b. If i < n, push i to stack (only first pass)
 * 5. Return result
 * 
 * VISUAL EXAMPLE:
 * ```
 * nums = [1, 2, 1]
 * result = [-1, -1, -1]
 * 
 * We'll process: [1, 2, 1, 1, 2, 1] (virtually)
 *                 0  1  2  3  4  5  (i values)
 *                 0  1  2  0  1  2  (i % 3)
 * 
 * i=0, nums[0]=1:
 *   stack=[]
 *   push 0
 *   stack=[0]
 * 
 * i=1, nums[1]=2:
 *   2 > nums[0]=1, pop 0, result[0]=2
 *   stack=[]
 *   push 1
 *   stack=[1]
 * 
 * i=2, nums[2]=1:
 *   1 < nums[1]=2, don't pop
 *   push 2
 *   stack=[1,2]
 * 
 * i=3, nums[0]=1 (wrapping):
 *   1 < nums[2]=1, don't pop
 *   Don't push (i >= n)
 *   stack=[1,2]
 * 
 * i=4, nums[1]=2 (wrapping):
 *   2 > nums[2]=1, pop 2, result[2]=2
 *   2 == nums[1]=2, don't pop
 *   Don't push (i >= n)
 *   stack=[1]
 * 
 * i=5, nums[2]=1 (wrapping):
 *   1 < nums[1]=2, don't pop
 *   Don't push (i >= n)
 *   stack=[1]
 * 
 * Final result: [2, -1, 2] ✓
 * ```
 * 
 * WHY PROCESS 2*N ELEMENTS:
 * - First pass (0 to n-1): Find NGE within normal range
 * - Second pass (n to 2n-1): Handle circular wrap-around cases
 * - Each element gets chance to be NGE for earlier elements
 * 
 * WHY STORE INDICES NOT VALUES:
 * - Need to update result array at specific positions
 * - Multiple elements can have same value
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - For each element, scan next n elements circularly
 * 2. Monotonic Stack (2 passes): O(n) - Process array twice separately
 * 3. Monotonic Stack (Virtual Double): O(n) - Current approach, cleaner
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We iterate through 2n elements: O(2n) = O(n)
 * - Each index pushed to stack at most once: n pushes
 * - Each index popped at most once: n pops
 * - Total operations: 2n + n + n = 4n = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result array: O(n)
 * - Stack can contain up to n elements: O(n)
 * - Total: O(n)
 * 
 * WHY BETTER THAN BRUTE FORCE:
 * Brute Force: For each element, check up to n next elements = O(n²)
 * Monotonic Stack: Each element processed constant times = O(n)
 * 
 * For n=10,000: O(n²) = 100M operations vs O(n) = 40K operations!
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class NextGreaterElementII {
    
    /**
     * Finds next greater element for each element in circular array
     * Uses monotonic decreasing stack with virtual array doubling
     */
    fun nextGreaterElements(nums: IntArray): IntArray {
        val n = nums.size
        val result = IntArray(n) { -1 }  // Initialize with -1
        val stack = Stack<Int>()  // Store indices
        
        // Process 2*n elements to handle circular nature
        for (i in 0 until 2 * n) {
            val currentIndex = i % n
            val currentValue = nums[currentIndex]
            
            // Pop all indices whose values are less than current
            while (stack.isNotEmpty() && nums[stack.peek()] < currentValue) {
                val idx = stack.pop()
                result[idx] = currentValue
            }
            
            // Only push indices during first pass
            if (i < n) {
                stack.push(currentIndex)
            }
        }
        
        // Remaining elements in stack have no NGE (result already -1)
        return result
    }
    
    /**
     * Alternative: Two separate passes for clarity
     */
    fun nextGreaterElementsTwoPass(nums: IntArray): IntArray {
        val n = nums.size
        val result = IntArray(n) { -1 }
        val stack = Stack<Int>()
        
        // First pass: normal left to right
        for (i in nums.indices) {
            while (stack.isNotEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i]
            }
            stack.push(i)
        }
        
        // Second pass: wrap around for remaining elements
        for (i in nums.indices) {
            while (stack.isNotEmpty() && nums[stack.peek()] < nums[i]) {
                result[stack.pop()] = nums[i]
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single element: [1] → [-1]
 * 2. All same: [5,5,5,5] → [-1,-1,-1,-1]
 * 3. Increasing: [1,2,3,4] → [2,3,4,-1]
 * 4. Decreasing: [4,3,2,1] → [-1,4,3,2] (circular!)
 * 5. Max at end: [1,2,3] → [2,3,-1]
 * 6. Max in middle: [1,3,2] → [3,-1,3]
 * 
 * DETAILED TRACE - Decreasing Array:
 * Input: [4, 3, 2, 1]
 * 
 * Virtual array: [4,3,2,1,4,3,2,1]
 * Indices:        0,1,2,3,0,1,2,3
 * 
 * i=0: num=4, stack=[], push 0, stack=[0]
 * i=1: num=3, 3<4, push 1, stack=[0,1]
 * i=2: num=2, 2<3, push 2, stack=[0,1,2]
 * i=3: num=1, 1<2, push 3, stack=[0,1,2,3]
 * i=4: num=4 (wrap), 4>1,2,3, pop all, result[3]=4,result[2]=4,result[1]=4
 *      4==4, don't push (i>=n), stack=[0]
 * i=5: num=3, 3<4, don't push, stack=[0]
 * i=6: num=2, 2<4, don't push, stack=[0]
 * i=7: num=1, 1<4, don't push, stack=[0]
 * 
 * Result: [-1, 4, 4, 4] (note: wrapped correctly!) ✗
 * 
 * Wait, let me recalculate:
 * [4,3,2,1] circular means:
 * - 4: next greater wrapping = none → -1
 * - 3: next greater wrapping = 4 → 4
 * - 2: next greater = 3, wrapping = 4, first is 3 → 3  
 * - 1: next greater = 2 → 2
 * 
 * Let me retrace:
 * i=4: num=4, 4>3,2,1, pop 3,2,1, result[3]=4, result[2]=4, result[1]=4
 * Result: [-1, 4, 4, 4]
 * 
 * But 2's NGE should be 3, not 4!
 * i=1: nums[1]=3
 * i=2: nums[2]=2, 2<3, so we push 2
 * When do we pop 2? When we see something > 2
 * i=3: nums[3]=1, 1<2, don't pop
 * i=4: nums[0]=4, 4>2, pop 2, result[2]=4
 * 
 * Hmm, but 3 comes before 4. Let me trace more carefully:
 * 
 * i=2, num=2:
 *   2 < nums[1]=3, don't pop
 *   push 2
 *   stack=[0,1,2]
 * 
 * i=3, num=1:
 *   1 < nums[2]=2, don't pop
 *   push 3
 *   stack=[0,1,2,3]
 * 
 * i=4, num=4 (nums[0]):
 *   4 > nums[3]=1, pop 3, result[3]=4
 *   4 > nums[2]=2, pop 2, result[2]=4
 *   4 > nums[1]=3, pop 1, result[1]=4
 *   4 == nums[0]=4, don't pop
 *   don't push (i>=4)
 *   stack=[0]
 * 
 * Oh wait, I see the issue! When we see 2 (at index 2), the next greater
 * after it going circular is... let's see: after 2 is 1, then wrapping is 4, 3.
 * First greater is 4! But intuitively, shouldn't 3 be checked first?
 * 
 * No! At i=2 (value=2), going forward: 1 (smaller), then wrap to 4 (greater!).
 * So 4 is correct.
 * 
 * Expected output: [-1, 4, 4, 4] is correct!
 * 
 * Better example:
 * Input: [1, 2, 1]
 * Expected: [2, -1, 2]
 * This shows circular nicely - last 1's NGE is 2 from wrapping.
 * 
 * ============================================================================
 */

fun main() {
    val solution = NextGreaterElementII()
    
    println("Next Greater Element II (Circular) - Test Cases")
    println("================================================\n")
    
    // Test 1: Basic circular case
    println("Test 1: Basic Circular")
    val nums1 = intArrayOf(1, 2, 1)
    println("Input: ${nums1.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums1).contentToString()}")
    println("Expected: [2, -1, 2]")
    println("Explanation: Last 1 wraps around to find 2\n")
    
    // Test 2: Decreasing sequence
    println("Test 2: Decreasing Sequence (Circular Effect)")
    val nums2 = intArrayOf(5, 4, 3, 2, 1)
    println("Input: ${nums2.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums2).contentToString()}")
    println("Expected: [-1, 5, 5, 5, 5]")
    println("Explanation: All wrap around to find 5\n")
    
    // Test 3: All same
    println("Test 3: All Same Elements")
    val nums3 = intArrayOf(2, 2, 2, 2)
    println("Input: ${nums3.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums3).contentToString()}")
    println("Expected: [-1, -1, -1, -1]\n")
    
    // Test 4: Single element
    println("Test 4: Single Element")
    val nums4 = intArrayOf(5)
    println("Input: ${nums4.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums4).contentToString()}")
    println("Expected: [-1]\n")
    
    // Test 5: Increasing sequence
    println("Test 5: Increasing Sequence")
    val nums5 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${nums5.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums5).contentToString()}")
    println("Expected: [2, 3, 4, 5, -1]\n")
    
    // Test 6: Mixed with negatives
    println("Test 6: With Negative Numbers")
    val nums6 = intArrayOf(-1, 0, -1)
    println("Input: ${nums6.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums6).contentToString()}")
    println("Expected: [0, -1, 0]\n")
    
    // Test 7: Max in middle
    println("Test 7: Max in Middle")
    val nums7 = intArrayOf(3, 8, 4, 1, 2)
    println("Input: ${nums7.contentToString()}")
    println("Output: ${solution.nextGreaterElements(nums7).contentToString()}")
    println("Expected: [8, -1, 8, 2, 3]\n")
    
    // Performance comparison
    println("=== Performance Analysis ===")
    println("Array size: n")
    println("Brute Force: O(n²) - check n elements for each position")
    println("Monotonic Stack: O(n) - each element processed twice at most")
    println("\nFor n=10,000:")
    println("Brute Force: ~100,000,000 operations")
    println("Monotonic Stack: ~40,000 operations")
    println("Speedup: ~2,500x faster!")
}
