/**
 * ============================================================================
 * PROBLEM: Sum of Subarray Minimums
 * DIFFICULTY: Medium
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers arr, find the sum of min(b), where b ranges over 
 * every (contiguous) subarray of arr. Since the answer may be large, return 
 * the answer modulo 10^9 + 7.
 * 
 * INPUT FORMAT:
 * - arr: Array of integers
 * Example: arr = [3, 1, 2, 4]
 * 
 * OUTPUT FORMAT:
 * - Integer representing sum of all subarray minimums modulo 10^9 + 7
 * Example: 17
 * 
 * CONSTRAINTS:
 * - 1 <= arr.length <= 3 * 10^4
 * - 1 <= arr[i] <= 3 * 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of generating all subarrays (O(n²)), think differently:
 * "For each element arr[i], in how many subarrays is it the minimum?"
 * 
 * KEY INSIGHT:
 * If arr[i] is minimum in a subarray, that subarray must:
 * 1. Include arr[i]
 * 2. Not include any element smaller than arr[i] between boundaries
 * 
 * CRITICAL QUESTION:
 * For element at index i:
 * - How far left can we extend while arr[i] remains minimum?
 * - How far right can we extend while arr[i] remains minimum?
 * 
 * ANSWER: Use Previous Smaller and Next Smaller Element!
 * - Left boundary: Previous Smaller Element (PSE)
 * - Right boundary: Next Smaller Element (NSE)
 * 
 * CONTRIBUTION FORMULA:
 * For element arr[i]:
 * - Left count: Number of elements between PSE and i (inclusive of i)
 * - Right count: Number of elements between i and NSE (inclusive of i)
 * - Total subarrays where arr[i] is minimum = left_count * right_count
 * - Contribution = arr[i] * left_count * right_count
 * 
 * VISUAL EXAMPLE:
 * ```
 * arr = [3, 1, 2, 4]
 * 
 * All subarrays and their minimums:
 * [3] → min = 3
 * [3,1] → min = 1
 * [3,1,2] → min = 1
 * [3,1,2,4] → min = 1
 * [1] → min = 1
 * [1,2] → min = 1
 * [1,2,4] → min = 1
 * [2] → min = 2
 * [2,4] → min = 2
 * [4] → min = 4
 * 
 * Sum = 3 + 1 + 1 + 1 + 1 + 1 + 1 + 2 + 2 + 4 = 17
 * 
 * Now let's use contribution method:
 * 
 * Element at index 0 (value=3):
 *   PSE: none (-1)
 *   NSE: index 1
 *   Left count: 0 - (-1) = 1
 *   Right count: 1 - 0 = 1
 *   Contribution: 3 * 1 * 1 = 3
 *   Subarrays where 3 is min: [3]
 * 
 * Element at index 1 (value=1):
 *   PSE: none (-1)
 *   NSE: none (4)
 *   Left count: 1 - (-1) = 2
 *   Right count: 4 - 1 = 3
 *   Contribution: 1 * 2 * 3 = 6
 *   Subarrays where 1 is min: [3,1], [1], [3,1,2], [1,2], [3,1,2,4], [1,2,4]
 *   Count = 6 ✓
 * 
 * Element at index 2 (value=2):
 *   PSE: index 1
 *   NSE: none (4)
 *   Left count: 2 - 1 = 1
 *   Right count: 4 - 2 = 2
 *   Contribution: 2 * 1 * 2 = 4
 *   Subarrays where 2 is min: [2], [2,4]
 * 
 * Element at index 3 (value=4):
 *   PSE: index 2
 *   NSE: none (4)
 *   Left count: 3 - 2 = 1
 *   Right count: 4 - 3 = 1
 *   Contribution: 4 * 1 * 1 = 4
 *   Subarrays where 4 is min: [4]
 * 
 * Total = 3 + 6 + 4 + 4 = 17 ✓
 * ```
 * 
 * HANDLING DUPLICATES - CRITICAL:
 * What if arr = [2, 2]?
 * 
 * If we use:
 * - PSE: strictly smaller
 * - NSE: strictly smaller
 * 
 * Both 2's would count subarray [2,2] twice!
 * 
 * SOLUTION: Use different conditions:
 * - PSE: Previous element < current (strictly less)
 * - NSE: Next element <= current (less or equal)
 * OR
 * - PSE: Previous element <= current (less or equal)
 * - NSE: Next element < current (strictly less)
 * 
 * This ensures each subarray counted exactly once.
 * 
 * ALGORITHM:
 * 1. Compute PSE for each element (store indices)
 *    - Monotonic increasing stack
 *    - Pop elements >= current
 * 
 * 2. Compute NSE for each element (store indices)
 *    - Monotonic increasing stack
 *    - Pop elements > current (note: > not >=, for duplicates)
 * 
 * 3. For each element:
 *    left_count = i - PSE[i]
 *    right_count = NSE[i] - i
 *    contribution = arr[i] * left_count * right_count
 *    sum += contribution
 * 
 * 4. Return sum % MOD
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - Generate all subarrays, find min
 * 2. Monotonic Stack: O(n) - Count contributions using PSE/NSE
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Compute PSE: O(n) - each element pushed/popped once
 * - Compute NSE: O(n) - each element pushed/popped once
 * - Calculate contributions: O(n) - single pass
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - PSE array: O(n)
 * - NSE array: O(n)
 * - Stack: O(n) worst case
 * - Total: O(n)
 * 
 * OPTIMIZATION: Can compute in single pass without arrays
 * - Calculate contribution when popping from stack
 * - Reduces space slightly but same complexity
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class SumOfSubarrayMinimums {
    
    private val MOD = 1_000_000_007
    
    /**
     * APPROACH 1: Using PSE and NSE arrays
     * Clear and easy to understand
     */
    fun sumSubarrayMins(arr: IntArray): Int {
        val n = arr.size
        
        // Compute Previous Smaller Element indices
        val pse = IntArray(n) { -1 }
        val stack1 = Stack<Int>()
        for (i in 0 until n) {
            // Pop elements >= current (to handle duplicates correctly)
            while (stack1.isNotEmpty() && arr[stack1.peek()] >= arr[i]) {
                stack1.pop()
            }
            pse[i] = if (stack1.isEmpty()) -1 else stack1.peek()
            stack1.push(i)
        }
        
        // Compute Next Smaller Element indices
        val nse = IntArray(n) { n }
        val stack2 = Stack<Int>()
        for (i in n - 1 downTo 0) {
            // Pop elements > current (note: > not >=, for duplicates)
            while (stack2.isNotEmpty() && arr[stack2.peek()] > arr[i]) {
                stack2.pop()
            }
            nse[i] = if (stack2.isEmpty()) n else stack2.peek()
            stack2.push(i)
        }
        
        // Calculate sum of contributions
        var sum = 0L
        for (i in 0 until n) {
            val leftCount = (i - pse[i]).toLong()
            val rightCount = (nse[i] - i).toLong()
            val contribution = (arr[i].toLong() * leftCount % MOD * rightCount % MOD) % MOD
            sum = (sum + contribution) % MOD
        }
        
        return sum.toInt()
    }
    
    /**
     * APPROACH 2: Optimized single pass
     * Calculate contribution when popping from stack
     */
    fun sumSubarrayMinsOptimized(arr: IntArray): Int {
        val n = arr.size
        val stack = Stack<Int>()
        var sum = 0L
        
        for (i in 0..n) {
            // Use sentinel value 0 at the end
            val current = if (i == n) 0 else arr[i]
            
            while (stack.isNotEmpty() && arr[stack.peek()] >= current) {
                val mid = stack.pop()
                val left = if (stack.isEmpty()) -1 else stack.peek()
                val right = i
                
                val leftCount = (mid - left).toLong()
                val rightCount = (right - mid).toLong()
                val contribution = (arr[mid].toLong() * leftCount % MOD * rightCount % MOD) % MOD
                sum = (sum + contribution) % MOD
            }
            
            if (i < n) {
                stack.push(i)
            }
        }
        
        return sum.toInt()
    }
    
    /**
     * Helper: Brute force for verification
     */
    fun sumSubarrayMinsBruteForce(arr: IntArray): Int {
        val n = arr.size
        var sum = 0L
        
        for (i in 0 until n) {
            var min = arr[i]
            for (j in i until n) {
                min = minOf(min, arr[j])
                sum = (sum + min) % MOD
            }
        }
        
        return sum.toInt()
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single element: [5] → 5
 * 2. All same: [3,3,3] → 3*(1+2+3) = 18
 * 3. Increasing: [1,2,3] → 1*6 + 2*2 + 3*1 = 13
 * 4. Decreasing: [3,2,1] → 3*1 + 2*2 + 1*6 = 13
 * 5. With duplicates: [2,2,2,2] → Need careful handling
 * 
 * DUPLICATE HANDLING EXAMPLE:
 * arr = [2, 2]
 * 
 * Subarrays: [2], [2,2], [2]
 * Minimums: 2, 2, 2
 * Sum = 6
 * 
 * Using our approach:
 * Index 0 (val=2):
 *   PSE: -1 (use >=, so doesn't include next 2)
 *   NSE: 1 (use >, so includes next 2)
 *   Wait, let me recalculate...
 * 
 * For PSE: pop if arr[stack.peek()] >= arr[i]
 * For NSE: pop if arr[stack.peek()] > arr[i]
 * 
 * Index 0 (val=2):
 *   PSE: -1
 *   NSE: 2 (no next smaller)
 *   left_count: 0-(-1) = 1
 *   right_count: 2-0 = 2
 *   contribution: 2*1*2 = 4
 * 
 * Index 1 (val=2):
 *   PSE: 0 (2 >= 2, popped nothing, so PSE is 0)
 *   Wait, with >=: stack=[0], when i=1, arr[0]=2 >= arr[1]=2, pop 0
 *   PSE: -1
 *   
 * Hmm, I need to trace more carefully. Let me reconsider...
 * 
 * For i=0: stack=[], push 0, stack=[0]
 * For i=1: arr[0]=2 >= arr[1]=2, pop 0, PSE[1]=-1, push 1
 * 
 * Index 0: PSE=-1
 * Index 1: PSE=-1
 * 
 * NSE (right to left):
 * For i=1: stack=[], push 1, stack=[1]
 * For i=0: arr[1]=2 > arr[0]=2? No! So don't pop, NSE[0]=1, push 0
 * 
 * Index 0: NSE=1
 * Index 1: NSE=2
 * 
 * Contributions:
 * Index 0: (0-(-1)) * (1-0) * 2 = 1*1*2 = 2 (counts [2])
 * Index 1: (1-(-1)) * (2-1) * 2 = 2*1*2 = 4 (counts [2,2] and [2])
 * 
 * Total = 6 ✓
 * 
 * DETAILED TRACE:
 * arr = [3, 1, 2, 4]
 * 
 * PSE (left to right, pop >=):
 * i=0: stack=[], PSE[0]=-1, stack=[0]
 * i=1: 3>=1 pop 0, stack=[], PSE[1]=-1, stack=[1]
 * i=2: 1>=2? no, PSE[2]=1, stack=[1,2]
 * i=3: 2>=4? no, PSE[3]=2, stack=[1,2,3]
 * PSE = [-1, -1, 1, 2]
 * 
 * NSE (right to left, pop >):
 * i=3: stack=[], NSE[3]=4, stack=[3]
 * i=2: 4>2? yes, pop 3, stack=[], NSE[2]=4, stack=[2]
 * i=1: 2>1? yes, pop 2, stack=[], NSE[1]=4, stack=[1]
 * i=0: 1>3? no, NSE[0]=1, stack=[1,0]
 * NSE = [1, 4, 4, 4]
 * 
 * Contributions:
 * i=0: (0-(-1))*(1-0)*3 = 1*1*3 = 3
 * i=1: (1-(-1))*(4-1)*1 = 2*3*1 = 6
 * i=2: (2-1)*(4-2)*2 = 1*2*2 = 4
 * i=3: (3-2)*(4-3)*4 = 1*1*4 = 4
 * Total = 17 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = SumOfSubarrayMinimums()
    
    println("Sum of Subarray Minimums - Test Cases")
    println("=======================================\n")
    
    // Test 1: Example case
    println("Test 1: Example Case")
    val arr1 = intArrayOf(3, 1, 2, 4)
    println("Input: ${arr1.contentToString()}")
    println("All subarrays:")
    println("  [3] → min=3")
    println("  [3,1] → min=1")
    println("  [3,1,2] → min=1")
    println("  [3,1,2,4] → min=1")
    println("  [1] → min=1")
    println("  [1,2] → min=1")
    println("  [1,2,4] → min=1")
    println("  [2] → min=2")
    println("  [2,4] → min=2")
    println("  [4] → min=4")
    println("Sum = 3+1+1+1+1+1+1+2+2+4 = 17")
    println("Output: ${solution.sumSubarrayMins(arr1)}")
    println("Expected: 17\n")
    
    // Test 2: Single element
    println("Test 2: Single Element")
    val arr2 = intArrayOf(5)
    println("Input: ${arr2.contentToString()}")
    println("Output: ${solution.sumSubarrayMins(arr2)}")
    println("Expected: 5\n")
    
    // Test 3: All same
    println("Test 3: All Same Elements")
    val arr3 = intArrayOf(3, 3, 3)
    println("Input: ${arr3.contentToString()}")
    println("Subarrays: [3], [3,3], [3,3,3], [3], [3,3], [3]")
    println("Sum = 3*6 = 18")
    println("Output: ${solution.sumSubarrayMins(arr3)}")
    println("Expected: 18\n")
    
    // Test 4: Increasing
    println("Test 4: Increasing Sequence")
    val arr4 = intArrayOf(1, 2, 3)
    println("Input: ${arr4.contentToString()}")
    println("1 is min in: [1], [1,2], [1,2,3] → 3 times")
    println("2 is min in: [2], [2,3] → 2 times")
    println("3 is min in: [3] → 1 time")
    println("Sum = 1*3 + 2*2 + 3*1 = 10")
    println("Output: ${solution.sumSubarrayMins(arr4)}")
    println("Expected: 10\n")
    
    // Test 5: Decreasing
    println("Test 5: Decreasing Sequence")
    val arr5 = intArrayOf(3, 2, 1)
    println("Input: ${arr5.contentToString()}")
    println("Output: ${solution.sumSubarrayMins(arr5)}")
    println("Expected: 10\n")
    
    // Test 6: With duplicates
    println("Test 6: With Duplicates")
    val arr6 = intArrayOf(2, 2, 2)
    println("Input: ${arr6.contentToString()}")
    println("Output: ${solution.sumSubarrayMins(arr6)}")
    println("Brute Force: ${solution.sumSubarrayMinsBruteForce(arr6)}")
    println()
    
    // Test 7: Larger example
    println("Test 7: Larger Example")
    val arr7 = intArrayOf(11, 81, 94, 43, 3)
    println("Input: ${arr7.contentToString()}")
    println("Output: ${solution.sumSubarrayMins(arr7)}")
    println("Brute Force: ${solution.sumSubarrayMinsBruteForce(arr7)}")
    println()
    
    // Test 8: Compare approaches
    println("Test 8: Compare Approaches")
    println("Input: ${arr1.contentToString()}")
    println("Approach 1 (PSE+NSE): ${solution.sumSubarrayMins(arr1)}")
    println("Approach 2 (Optimized): ${solution.sumSubarrayMinsOptimized(arr1)}")
    println("Brute Force: ${solution.sumSubarrayMinsBruteForce(arr1)}")
    println()
    
    // Key insight explanation
    println("=== Key Insight ===")
    println("Instead of generating O(n²) subarrays, we ask:")
    println("\"In how many subarrays is element arr[i] the minimum?\"")
    println()
    println("For element at index i:")
    println("  Left extension: Until we hit smaller element (PSE)")
    println("  Right extension: Until we hit smaller element (NSE)")
    println("  Count = (i - PSE[i]) * (NSE[i] - i)")
    println("  Contribution = arr[i] * count")
    println()
    
    println("=== Handling Duplicates ===")
    println("If arr = [2, 2], subarray [2,2] should be counted once!")
    println("Solution: Use different conditions for PSE and NSE")
    println("  PSE: pop if >= (left boundary exclusive for duplicates)")
    println("  NSE: pop if > (right boundary inclusive for duplicates)")
    println()
    
    println("=== Performance ===")
    println("Brute Force: O(n²) time - generate all subarrays")
    println("Monotonic Stack: O(n) time - count contributions")
    println("\nFor n=30,000 (problem limit):")
    println("Brute Force: ~900 million operations")
    println("Monotonic Stack: ~90,000 operations")
    println("Speedup: ~10,000x faster!")
    
    println("\n=== Applications ===")
    println("This technique is used in:")
    println("1. Sum of Subarray Ranges")
    println("2. Number of Subarrays with Bounded Maximum")
    println("3. Count Subarrays with Fixed Bounds")
}
