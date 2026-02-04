/**
 * ============================================================================
 * PROBLEM: Sum of Subarray Ranges
 * DIFFICULTY: Medium
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an integer array nums. The range of a subarray is the 
 * difference between the largest and smallest element in the subarray.
 * Return the sum of all subarray ranges of nums.
 * 
 * INPUT FORMAT:
 * - nums: Array of integers
 * Example: nums = [1, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - Long integer representing sum of all subarray ranges
 * Example: 4
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 1000
 * - -10^9 <= nums[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Range = Max - Min
 * Sum of ranges = Sum of all maxes - Sum of all mins
 * 
 * KEY INSIGHT:
 * We can split this into two separate problems:
 * 1. Sum of subarray maximums
 * 2. Sum of subarray minimums
 * 
 * Answer = Sum(subarray maximums) - Sum(subarray minimums)
 * 
 * We already know how to solve "Sum of Subarray Minimums"!
 * For maximums, use the same approach but with:
 * - Previous Greater Element (PGE)
 * - Next Greater Element (NGE)
 * 
 * VISUAL EXAMPLE:
 * ```
 * nums = [1, 2, 3]
 * 
 * All subarrays with their ranges:
 * [1] → max=1, min=1, range=0
 * [1,2] → max=2, min=1, range=1
 * [1,2,3] → max=3, min=1, range=2
 * [2] → max=2, min=2, range=0
 * [2,3] → max=3, min=2, range=1
 * [3] → max=3, min=3, range=0
 * 
 * Sum of ranges = 0+1+2+0+1+0 = 4
 * 
 * Alternative calculation:
 * Sum of maxes = 1+2+3+2+3+3 = 14
 * Sum of mins = 1+1+1+2+2+3 = 10
 * Sum of ranges = 14 - 10 = 4 ✓
 * ```
 * 
 * DETAILED EXAMPLE:
 * ```
 * nums = [4, 3, 1, 2]
 * 
 * MINIMUMS:
 * Element 4 (index 0): PSE=-1, NSE=1
 *   Count: (0-(-1)) * (1-0) = 1
 *   Contribution: 4*1 = 4
 * 
 * Element 3 (index 1): PSE=-1, NSE=2
 *   Count: (1-(-1)) * (2-1) = 2
 *   Contribution: 3*2 = 6
 * 
 * Element 1 (index 2): PSE=-1, NSE=4
 *   Count: (2-(-1)) * (4-2) = 6
 *   Contribution: 1*6 = 6
 * 
 * Element 2 (index 3): PSE=2, NSE=4
 *   Count: (3-2) * (4-3) = 1
 *   Contribution: 2*1 = 2
 * 
 * Sum of mins = 4+6+6+2 = 18
 * 
 * MAXIMUMS:
 * Element 4 (index 0): PGE=-1, NGE=4
 *   Count: (0-(-1)) * (4-0) = 4
 *   Contribution: 4*4 = 16
 * 
 * Element 3 (index 1): PGE=0, NGE=4
 *   Count: (1-0) * (4-1) = 3
 *   Contribution: 3*3 = 9
 * 
 * Element 1 (index 2): PGE=1, NGE=3
 *   Count: (2-1) * (3-2) = 1
 *   Contribution: 1*1 = 1
 * 
 * Element 2 (index 3): PGE=2, NGE=4
 *   Count: (3-2) * (4-3) = 1
 *   Contribution: 2*1 = 2
 * 
 * Sum of maxes = 16+9+1+2 = 28
 * 
 * Sum of ranges = 28 - 18 = 10
 * ```
 * 
 * ALGORITHM:
 * 1. Calculate sum of all subarray minimums:
 *    a. Find PSE (Previous Smaller Element) for each element
 *    b. Find NSE (Next Smaller Element) for each element
 *    c. For each element: contribution = value * left_count * right_count
 * 
 * 2. Calculate sum of all subarray maximums:
 *    a. Find PGE (Previous Greater Element) for each element
 *    b. Find NGE (Next Greater Element) for each element
 *    c. For each element: contribution = value * left_count * right_count
 * 
 * 3. Return sum_of_maxes - sum_of_mins
 * 
 * HANDLING DUPLICATES:
 * Same as Sum of Subarray Minimums:
 * - For minimums: PSE uses >=, NSE uses >
 * - For maximums: PGE uses <=, NGE uses <
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - Generate all subarrays, compute ranges
 * 2. Monotonic Stack: O(n) - Compute max and min contributions separately
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Compute PSE: O(n)
 * - Compute NSE: O(n)
 * - Compute PGE: O(n)
 * - Compute NGE: O(n)
 * - Calculate contributions: O(n)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Four arrays (PSE, NSE, PGE, NGE): 4*O(n)
 * - Stacks: O(n)
 * - Total: O(n)
 * 
 * Can be optimized to O(1) extra space by computing contributions on-the-fly,
 * but code becomes more complex.
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class SumOfSubarrayRanges {
    
    /**
     * Main solution using contribution technique
     */
    fun subArrayRanges(nums: IntArray): Long {
        return sumOfMaximums(nums) - sumOfMinimums(nums)
    }
    
    /**
     * Calculate sum of all subarray minimums
     * Same logic as "Sum of Subarray Minimums" problem
     */
    private fun sumOfMinimums(nums: IntArray): Long {
        val n = nums.size
        
        // Previous Smaller Element
        val pse = IntArray(n) { -1 }
        val stack1 = Stack<Int>()
        for (i in 0 until n) {
            while (stack1.isNotEmpty() && nums[stack1.peek()] >= nums[i]) {
                stack1.pop()
            }
            pse[i] = if (stack1.isEmpty()) -1 else stack1.peek()
            stack1.push(i)
        }
        
        // Next Smaller Element
        val nse = IntArray(n) { n }
        val stack2 = Stack<Int>()
        for (i in n - 1 downTo 0) {
            while (stack2.isNotEmpty() && nums[stack2.peek()] > nums[i]) {
                stack2.pop()
            }
            nse[i] = if (stack2.isEmpty()) n else stack2.peek()
            stack2.push(i)
        }
        
        // Calculate sum
        var sum = 0L
        for (i in 0 until n) {
            val leftCount = (i - pse[i]).toLong()
            val rightCount = (nse[i] - i).toLong()
            sum += nums[i].toLong() * leftCount * rightCount
        }
        
        return sum
    }
    
    /**
     * Calculate sum of all subarray maximums
     * Mirror of sum of minimums, but using greater elements
     */
    private fun sumOfMaximums(nums: IntArray): Long {
        val n = nums.size
        
        // Previous Greater Element
        val pge = IntArray(n) { -1 }
        val stack1 = Stack<Int>()
        for (i in 0 until n) {
            while (stack1.isNotEmpty() && nums[stack1.peek()] <= nums[i]) {
                stack1.pop()
            }
            pge[i] = if (stack1.isEmpty()) -1 else stack1.peek()
            stack1.push(i)
        }
        
        // Next Greater Element
        val nge = IntArray(n) { n }
        val stack2 = Stack<Int>()
        for (i in n - 1 downTo 0) {
            while (stack2.isNotEmpty() && nums[stack2.peek()] < nums[i]) {
                stack2.pop()
            }
            nge[i] = if (stack2.isEmpty()) n else stack2.peek()
            stack2.push(i)
        }
        
        // Calculate sum
        var sum = 0L
        for (i in 0 until n) {
            val leftCount = (i - pge[i]).toLong()
            val rightCount = (nge[i] - i).toLong()
            sum += nums[i].toLong() * leftCount * rightCount
        }
        
        return sum
    }
    
    /**
     * Brute force solution for verification
     */
    fun subArrayRangesBruteForce(nums: IntArray): Long {
        val n = nums.size
        var sum = 0L
        
        for (i in 0 until n) {
            var min = nums[i]
            var max = nums[i]
            for (j in i until n) {
                min = minOf(min, nums[j])
                max = maxOf(max, nums[j])
                sum += (max - min).toLong()
            }
        }
        
        return sum
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single element: [5] → range=0
 * 2. All same: [3,3,3] → all ranges=0, sum=0
 * 3. Two elements: [1,3] → ranges: 0,2,0 → sum=2
 * 4. Increasing: [1,2,3] → sum=4
 * 5. Decreasing: [3,2,1] → sum=4
 * 6. With negatives: [-1,-2,-3]
 * 
 * DETAILED TRACE:
 * nums = [1, 2, 3]
 * 
 * MINIMUMS:
 * PSE: [-1, 0, 1]
 * NSE: [3, 3, 3]
 * 
 * Contributions:
 * i=0: (0-(-1))*(3-0)*1 = 1*3*1 = 3
 * i=1: (1-0)*(3-1)*2 = 1*2*2 = 4
 * i=2: (2-1)*(3-2)*3 = 1*1*3 = 3
 * Sum of mins = 3+4+3 = 10
 * 
 * MAXIMUMS:
 * PGE: [-1, -1, -1]
 * NGE: [1, 2, 3]
 * 
 * Contributions:
 * i=0: (0-(-1))*(1-0)*1 = 1*1*1 = 1
 * i=1: (1-(-1))*(2-1)*2 = 2*1*2 = 4
 * i=2: (2-(-1))*(3-2)*3 = 3*1*3 = 9
 * Sum of maxes = 1+4+9 = 14
 * 
 * Sum of ranges = 14 - 10 = 4 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = SumOfSubarrayRanges()
    
    println("Sum of Subarray Ranges - Test Cases")
    println("=====================================\n")
    
    // Test 1: Example case
    println("Test 1: Simple Example")
    val nums1 = intArrayOf(1, 2, 3)
    println("Input: ${nums1.contentToString()}")
    println("All subarrays with ranges:")
    println("  [1] → max=1, min=1, range=0")
    println("  [1,2] → max=2, min=1, range=1")
    println("  [1,2,3] → max=3, min=1, range=2")
    println("  [2] → max=2, min=2, range=0")
    println("  [2,3] → max=3, min=2, range=1")
    println("  [3] → max=3, min=3, range=0")
    println("Sum = 0+1+2+0+1+0 = 4")
    println("Output: ${solution.subArrayRanges(nums1)}")
    println("Expected: 4\n")
    
    // Test 2: Single element
    println("Test 2: Single Element")
    val nums2 = intArrayOf(5)
    println("Input: ${nums2.contentToString()}")
    println("Output: ${solution.subArrayRanges(nums2)}")
    println("Expected: 0\n")
    
    // Test 3: All same
    println("Test 3: All Same Elements")
    val nums3 = intArrayOf(3, 3, 3)
    println("Input: ${nums3.contentToString()}")
    println("All ranges are 0 (max=min in each subarray)")
    println("Output: ${solution.subArrayRanges(nums3)}")
    println("Expected: 0\n")
    
    // Test 4: Two elements
    println("Test 4: Two Elements")
    val nums4 = intArrayOf(1, 3)
    println("Input: ${nums4.contentToString()}")
    println("Subarrays: [1]→0, [1,3]→2, [3]→0")
    println("Sum = 2")
    println("Output: ${solution.subArrayRanges(nums4)}")
    println("Expected: 2\n")
    
    // Test 5: Decreasing
    println("Test 5: Decreasing Sequence")
    val nums5 = intArrayOf(3, 2, 1)
    println("Input: ${nums5.contentToString()}")
    println("Output: ${solution.subArrayRanges(nums5)}")
    println("Expected: 4\n")
    
    // Test 6: Mixed
    println("Test 6: Mixed Values")
    val nums6 = intArrayOf(4, 3, 1, 2)
    println("Input: ${nums6.contentToString()}")
    println("Output: ${solution.subArrayRanges(nums6)}")
    println("Brute Force: ${solution.subArrayRangesBruteForce(nums6)}")
    println()
    
    // Test 7: With negatives
    println("Test 7: With Negative Numbers")
    val nums7 = intArrayOf(-1, -2, -3)
    println("Input: ${nums7.contentToString()}")
    println("Output: ${solution.subArrayRanges(nums7)}")
    println("Brute Force: ${solution.subArrayRangesBruteForce(nums7)}")
    println()
    
    // Test 8: Larger example
    println("Test 8: Larger Example")
    val nums8 = intArrayOf(1, 4, 3, 2, 5)
    println("Input: ${nums8.contentToString()}")
    println("Output: ${solution.subArrayRanges(nums8)}")
    println("Brute Force: ${solution.subArrayRangesBruteForce(nums8)}")
    println()
    
    // Key insight
    println("=== Key Insight ===")
    println("Range = Max - Min")
    println("Sum of ranges = Sum(all maxes) - Sum(all mins)")
    println()
    println("This splits the problem into two:")
    println("1. Sum of Subarray Maximums (use PGE + NGE)")
    println("2. Sum of Subarray Minimums (use PSE + NSE)")
    println()
    
    println("=== Pattern Summary ===")
    println("┌────────────────────┬──────────────┬──────────────┐")
    println("│ Goal               │ Previous     │ Next         │")
    println("├────────────────────┼──────────────┼──────────────┤")
    println("│ Sum of Minimums    │ PSE (pop >=) │ NSE (pop >)  │")
    println("│ Sum of Maximums    │ PGE (pop <=) │ NGE (pop <)  │")
    println("└────────────────────┴──────────────┴──────────────┘")
    println()
    
    println("=== Performance ===")
    println("Brute Force: O(n²) - Generate all subarrays")
    println("Monotonic Stack: O(n) - Contribution technique")
    println("\nFor n=1000:")
    println("Brute Force: ~1 million operations")
    println("Monotonic Stack: ~4,000 operations")
    println("Speedup: ~250x faster!")
    
    println("\n=== Related Problems ===")
    println("1. Sum of Subarray Minimums (LeetCode 907)")
    println("2. Sum of Subarray Ranges (LeetCode 2104) ← This problem")
    println("3. Number of Subarrays with Bounded Maximum (LeetCode 795)")
}
