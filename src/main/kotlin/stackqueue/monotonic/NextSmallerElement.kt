/**
 * ============================================================================
 * PROBLEM: Next Smaller Element
 * DIFFICULTY: Easy
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, for each element find the next smaller element
 * to its right. If no smaller element exists, return -1.
 * 
 * INPUT FORMAT:
 * - arr: Array of integers
 * Example: arr = [4, 8, 5, 2, 25]
 * 
 * OUTPUT FORMAT:
 * - Array where output[i] is the next smaller element of arr[i]
 * Example: [2, 5, 2, -1, -1]
 * 
 * CONSTRAINTS:
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is the complement of Next Greater Element - now we're looking for
 * smaller elements instead of greater ones.
 * 
 * Think of it as: "Who is the first shorter person to my right?"
 * 
 * KEY INSIGHT - MONOTONIC INCREASING STACK:
 * Unlike NGE which uses decreasing stack, NSE uses INCREASING stack:
 * - Stack maintains elements in increasing order from bottom to top
 * - When we see new element, we pop all GREATER elements
 * - First element <= current is the NSE
 * 
 * WHY INCREASING STACK:
 * - We want to find smaller elements
 * - Keep potentially useful (smaller) elements in stack
 * - Remove larger elements as they can't be NSE for current
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result array with -1
 * 2. Create monotonic increasing stack (stores indices or values)
 * 3. Traverse array from right to left:
 *    a. Pop elements from stack while they are >= current element
 *    b. If stack not empty, top is NSE, else -1
 *    c. Push current element to stack
 * 4. Return result
 * 
 * VISUAL EXAMPLE:
 * ```
 * arr = [4, 8, 5, 2, 25]
 * 
 * Process from right to left:
 * 
 * i=4, val=25:
 *   stack=[]
 *   NSE[4] = -1
 *   stack=[25]
 * 
 * i=3, val=2:
 *   2 < 25, don't pop
 *   stack=[25]
 *   NSE[3] = -1 (no element < 2 in stack)
 *   Pop 25 (2 < 25, should pop!)
 *   Actually, let me reconsider...
 * 
 * Wait, for SMALLER we want increasing stack:
 * 
 * i=4, val=25:
 *   stack=[]
 *   NSE[4] = -1
 *   stack=[25]
 * 
 * i=3, val=2:
 *   25 > 2, pop 25
 *   stack=[]
 *   NSE[3] = -1
 *   stack=[2]
 * 
 * i=2, val=5:
 *   2 < 5, don't pop
 *   stack=[2]
 *   NSE[2] = 2
 *   stack=[2, 5]
 * 
 * i=1, val=8:
 *   5 < 8, don't pop
 *   stack=[2, 5]
 *   NSE[1] = 5
 *   stack=[2, 5, 8]
 * 
 * i=0, val=4:
 *   8 > 4, pop 8
 *   5 > 4, pop 5
 *   2 < 4, don't pop
 *   stack=[2]
 *   NSE[0] = 2
 *   stack=[2, 4]
 * 
 * Result: [2, 5, 2, -1, -1] ✓
 * ```
 * 
 * COMPARISON WITH NEXT GREATER ELEMENT:
 * 
 * Next Greater Element (NGE):
 * - Uses monotonic DECREASING stack
 * - Pop elements SMALLER than current
 * - Find first GREATER element
 * 
 * Next Smaller Element (NSE):
 * - Uses monotonic INCREASING stack  
 * - Pop elements GREATER than current
 * - Find first SMALLER element
 * 
 * Pattern: To find greater → decreasing stack, to find smaller → increasing stack
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - For each element, scan all elements to right
 * 2. Monotonic Stack: O(n) - Current optimal approach
 * 3. Left to Right processing: O(n) - Alternative implementation
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through array: O(n)
 * - Each element pushed once: n pushes
 * - Each element popped at most once: n pops
 * - Total: 2n + n = 3n = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result array: O(n) - required for output
 * - Stack: O(n) - worst case all elements in increasing order
 * - Total: O(n)
 * 
 * AMORTIZED ANALYSIS:
 * Even though inner while loop seems to make it O(n²), each element
 * can only be popped once, making total pop operations = n.
 * This is called amortized constant time per operation.
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class NextSmallerElement {
    
    /**
     * Finds next smaller element for each element in array
     * Uses monotonic increasing stack
     */
    fun nextSmallerElement(arr: IntArray): IntArray {
        val n = arr.size
        val result = IntArray(n) { -1 }
        val stack = Stack<Int>()  // Store actual values
        
        // Traverse from right to left
        for (i in arr.indices.reversed()) {
            // Pop all elements greater than or equal to current
            while (stack.isNotEmpty() && stack.peek() >= arr[i]) {
                stack.pop()
            }
            
            // If stack not empty, top is the next smaller element
            result[i] = if (stack.isEmpty()) -1 else stack.peek()
            
            // Push current element
            stack.push(arr[i])
        }
        
        return result
    }
    
    /**
     * Alternative: Store indices instead of values
     * Useful when we need positions of NSE
     */
    fun nextSmallerElementWithIndices(arr: IntArray): Pair<IntArray, IntArray> {
        val n = arr.size
        val values = IntArray(n) { -1 }
        val indices = IntArray(n) { -1 }
        val stack = Stack<Int>()  // Store indices
        
        for (i in arr.indices.reversed()) {
            while (stack.isNotEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop()
            }
            
            if (stack.isNotEmpty()) {
                values[i] = arr[stack.peek()]
                indices[i] = stack.peek()
            }
            
            stack.push(i)
        }
        
        return Pair(values, indices)
    }
    
    /**
     * Left to right processing
     * For each element, find what it's the NSE for
     */
    fun nextSmallerElementLeftToRight(arr: IntArray): IntArray {
        val n = arr.size
        val result = IntArray(n) { -1 }
        val stack = Stack<Int>()  // Store indices
        
        for (i in arr.indices) {
            // Current element is NSE for all larger elements in stack
            while (stack.isNotEmpty() && arr[stack.peek()] > arr[i]) {
                val idx = stack.pop()
                result[idx] = arr[i]
            }
            stack.push(i)
        }
        
        // Remaining elements have no NSE (result already -1)
        return result
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single element: [5] → [-1]
 * 2. Increasing sequence: [1,2,3,4] → [-1,-1,-1,-1]
 * 3. Decreasing sequence: [4,3,2,1] → [3,2,1,-1]
 * 4. All same: [5,5,5,5] → [-1,-1,-1,-1]
 * 5. With negatives: [5, -1, 3, -2] → [-1, -2, -2, -1]
 * 6. Alternating: [5, 1, 6, 2] → [1, -1, 2, -1]
 * 
 * DETAILED TRACE:
 * Input: [4, 8, 5, 2, 25]
 * 
 * Right to left:
 * i=4: val=25, stack=[], result[4]=-1, stack=[25]
 * i=3: val=2, pop 25 (25>2), stack=[], result[3]=-1, stack=[2]
 * i=2: val=5, 2<5 keep, stack=[2], result[2]=2, stack=[2,5]
 * i=1: val=8, 5<8 keep, stack=[2,5], result[1]=5, stack=[2,5,8]
 * i=0: val=4, pop 8 (8>4), pop 5 (5>4), 2<4 keep
 *      stack=[2], result[0]=2, stack=[2,4]
 * 
 * Result: [2, 5, 2, -1, -1] ✓
 * 
 * Verification:
 * - arr[0]=4: next smaller to right is 2 at index 3 ✓
 * - arr[1]=8: next smaller to right is 5 at index 2 ✓
 * - arr[2]=5: next smaller to right is 2 at index 3 ✓
 * - arr[3]=2: no smaller element to right ✓
 * - arr[4]=25: no smaller element to right ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = NextSmallerElement()
    
    println("Next Smaller Element - Test Cases")
    println("===================================\n")
    
    // Test 1: Basic case
    println("Test 1: Basic Case")
    val arr1 = intArrayOf(4, 8, 5, 2, 25)
    println("Input: ${arr1.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr1).contentToString()}")
    println("Expected: [2, 5, 2, -1, -1]")
    println("Explanation:")
    println("  4 → 2 (first smaller to right)")
    println("  8 → 5 (first smaller to right)")
    println("  5 → 2 (first smaller to right)")
    println("  2 → -1 (no smaller)")
    println("  25 → -1 (no smaller)\n")
    
    // Test 2: Decreasing sequence
    println("Test 2: Decreasing Sequence")
    val arr2 = intArrayOf(5, 4, 3, 2, 1)
    println("Input: ${arr2.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr2).contentToString()}")
    println("Expected: [4, 3, 2, 1, -1]")
    println("Explanation: Each element has next element as smaller\n")
    
    // Test 3: Increasing sequence
    println("Test 3: Increasing Sequence")
    val arr3 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${arr3.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr3).contentToString()}")
    println("Expected: [-1, -1, -1, -1, -1]")
    println("Explanation: No smaller elements to right in increasing sequence\n")
    
    // Test 4: All same
    println("Test 4: All Same Elements")
    val arr4 = intArrayOf(3, 3, 3, 3)
    println("Input: ${arr4.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr4).contentToString()}")
    println("Expected: [-1, -1, -1, -1]\n")
    
    // Test 5: With negatives
    println("Test 5: With Negative Numbers")
    val arr5 = intArrayOf(5, -1, 3, -2, 7)
    println("Input: ${arr5.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr5).contentToString()}")
    println("Expected: [-1, -2, -2, -1, -1]\n")
    
    // Test 6: Single element
    println("Test 6: Single Element")
    val arr6 = intArrayOf(10)
    println("Input: ${arr6.contentToString()}")
    println("Output: ${solution.nextSmallerElement(arr6).contentToString()}")
    println("Expected: [-1]\n")
    
    // Test 7: With indices
    println("Test 7: Get Indices of NSE")
    val arr7 = intArrayOf(4, 8, 5, 2, 25)
    val (values, indices) = solution.nextSmallerElementWithIndices(arr7)
    println("Input: ${arr7.contentToString()}")
    println("NSE Values: ${values.contentToString()}")
    println("NSE Indices: ${indices.contentToString()}")
    println("Expected Values: [2, 5, 2, -1, -1]")
    println("Expected Indices: [3, 2, 3, -1, -1]\n")
    
    // Test 8: Left to right approach
    println("Test 8: Left-to-Right Approach")
    println("Input: ${arr1.contentToString()}")
    println("Output: ${solution.nextSmallerElementLeftToRight(arr1).contentToString()}")
    println("Expected: [2, 5, 2, -1, -1]\n")
    
    // Comparison table
    println("=== Stack Type Comparison ===")
    println("┌──────────────────────┬─────────────────────┬─────────────────────┐")
    println("│ Goal                 │ Stack Type          │ Pop Condition       │")
    println("├──────────────────────┼─────────────────────┼─────────────────────┤")
    println("│ Next Greater Element │ Monotonic Decreasing│ stack.top < current │")
    println("│ Next Smaller Element │ Monotonic Increasing│ stack.top > current │")
    println("│ Prev Greater Element │ Monotonic Decreasing│ stack.top ≤ current │")
    println("│ Prev Smaller Element │ Monotonic Increasing│ stack.top ≥ current │")
    println("└──────────────────────┴─────────────────────┴─────────────────────┘")
    
    println("\n=== Performance ===")
    println("For array of size n:")
    println("Brute Force: O(n²) time, O(1) space")
    println("Monotonic Stack: O(n) time, O(n) space")
    println("\nFor n=100,000:")
    println("Brute Force: ~10 billion comparisons")
    println("Monotonic Stack: ~300,000 operations")
    println("Speedup: ~33,000x faster!")
}
