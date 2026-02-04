/**
 * ============================================================================
 * PROBLEM: Previous Smaller Element
 * DIFFICULTY: Easy
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, for each element find the previous smaller 
 * element to its left. If no smaller element exists, return -1.
 * 
 * INPUT FORMAT:
 * - arr: Array of integers
 * Example: arr = [4, 10, 5, 8, 20, 15, 3, 12]
 * 
 * OUTPUT FORMAT:
 * - Array where output[i] is the previous smaller element of arr[i]
 * Example: [-1, 4, 4, 5, 8, 8, -1, 3]
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
 * Similar to Next Smaller Element, but now we look LEFT instead of right.
 * 
 * Think of it as standing in line and asking:
 * "Who is the first shorter person to my left?"
 * 
 * KEY INSIGHT - PROCESS LEFT TO RIGHT:
 * Unlike "next" problems where we go right-to-left, for "previous" problems
 * we process left-to-right since we're looking backwards.
 * 
 * MONOTONIC INCREASING STACK:
 * - Maintain elements in increasing order from bottom to top
 * - When we see current element, pop all elements >= current
 * - After popping, top of stack is the previous smaller element
 * - Then push current element
 * 
 * WHY THIS WORKS:
 * 1. We process left to right, so stack contains previous elements
 * 2. We want smaller elements, so keep stack increasing
 * 3. Pop larger/equal elements as they can't be PSE
 * 4. After popping, top is largest element < current = PSE!
 * 5. Push current for future elements
 * 
 * ALGORITHM STEPS:
 * 1. Initialize result array
 * 2. Create monotonic increasing stack
 * 3. Traverse array from left to right:
 *    a. Pop all elements >= current from stack
 *    b. If stack empty, PSE = -1, else PSE = stack.top
 *    c. Push current element
 * 4. Return result
 * 
 * VISUAL EXAMPLE:
 * ```
 * arr = [4, 10, 5, 8, 20, 15, 3, 12]
 * 
 * Process left to right:
 * 
 * i=0, val=4:
 *   stack=[]
 *   PSE[0] = -1
 *   stack=[4]
 * 
 * i=1, val=10:
 *   4 < 10, don't pop
 *   stack=[4]
 *   PSE[1] = 4
 *   stack=[4, 10]
 * 
 * i=2, val=5:
 *   10 >= 5, pop 10
 *   4 < 5, don't pop
 *   stack=[4]
 *   PSE[2] = 4
 *   stack=[4, 5]
 * 
 * i=3, val=8:
 *   5 < 8, don't pop
 *   stack=[4, 5]
 *   PSE[3] = 5
 *   stack=[4, 5, 8]
 * 
 * i=4, val=20:
 *   8 < 20, don't pop
 *   stack=[4, 5, 8]
 *   PSE[4] = 8
 *   stack=[4, 5, 8, 20]
 * 
 * i=5, val=15:
 *   20 >= 15, pop 20
 *   8 < 15, don't pop
 *   stack=[4, 5, 8]
 *   PSE[5] = 8
 *   stack=[4, 5, 8, 15]
 * 
 * i=6, val=3:
 *   15 >= 3, pop 15
 *   8 >= 3, pop 8
 *   5 >= 3, pop 5
 *   4 >= 3, pop 4
 *   stack=[]
 *   PSE[6] = -1
 *   stack=[3]
 * 
 * i=7, val=12:
 *   3 < 12, don't pop
 *   stack=[3]
 *   PSE[7] = 3
 *   stack=[3, 12]
 * 
 * Result: [-1, 4, 4, 5, 8, 8, -1, 3] ✓
 * ```
 * 
 * COMPARISON - FOUR VARIATIONS:
 * 
 * Next Greater Element (NGE):
 * - Direction: Right
 * - Stack: Decreasing
 * - Process: Right to Left
 * 
 * Next Smaller Element (NSE):
 * - Direction: Right
 * - Stack: Increasing
 * - Process: Right to Left
 * 
 * Previous Greater Element (PGE):
 * - Direction: Left
 * - Stack: Decreasing
 * - Process: Left to Right
 * 
 * Previous Smaller Element (PSE):
 * - Direction: Left
 * - Stack: Increasing
 * - Process: Left to Right
 * 
 * PATTERN:
 * - Looking for "Greater" → Decreasing Stack
 * - Looking for "Smaller" → Increasing Stack
 * - Looking "Next" (Right) → Process Right to Left
 * - Looking "Previous" (Left) → Process Left to Right
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - For each element, scan all left elements
 * 2. Monotonic Stack: O(n) - Current optimal approach
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through array: O(n)
 * - Each element pushed once: n pushes
 * - Each element popped at most once: n pops
 * - Total operations: n + n + n = 3n = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result array: O(n) - required for output
 * - Stack: O(n) - worst case (strictly increasing array)
 * - Total: O(n)
 * 
 * WHY EACH ELEMENT POPPED ONCE:
 * Once an element is popped, it never returns to stack.
 * Maximum pops = n (total elements)
 * This makes the amortized time per element O(1).
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*

class PreviousSmallerElement {
    
    /**
     * Finds previous smaller element for each element in array
     * Uses monotonic increasing stack with left-to-right traversal
     */
    fun previousSmallerElement(arr: IntArray): IntArray {
        val n = arr.size
        val result = IntArray(n)
        val stack = Stack<Int>()  // Store actual values
        
        // Traverse from left to right
        for (i in arr.indices) {
            // Pop all elements >= current
            while (stack.isNotEmpty() && stack.peek() >= arr[i]) {
                stack.pop()
            }
            
            // If stack empty, no previous smaller element
            result[i] = if (stack.isEmpty()) -1 else stack.peek()
            
            // Push current element for future elements
            stack.push(arr[i])
        }
        
        return result
    }
    
    /**
     * Returns both values and indices of previous smaller elements
     * Useful for problems needing positions
     */
    fun previousSmallerElementWithIndices(arr: IntArray): Pair<IntArray, IntArray> {
        val n = arr.size
        val values = IntArray(n) { -1 }
        val indices = IntArray(n) { -1 }
        val stack = Stack<Int>()  // Store indices
        
        for (i in arr.indices) {
            // Pop indices where values >= current
            while (stack.isNotEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop()
            }
            
            if (stack.isNotEmpty()) {
                indices[i] = stack.peek()
                values[i] = arr[stack.peek()]
            }
            
            stack.push(i)
        }
        
        return Pair(values, indices)
    }
    
    /**
     * Variant: Previous Smaller or Equal
     * Changes condition to strictly less than
     */
    fun previousSmallerOrEqual(arr: IntArray): IntArray {
        val n = arr.size
        val result = IntArray(n)
        val stack = Stack<Int>()
        
        for (i in arr.indices) {
            // Pop only elements > current (not >=)
            while (stack.isNotEmpty() && stack.peek() > arr[i]) {
                stack.pop()
            }
            
            result[i] = if (stack.isEmpty()) -1 else stack.peek()
            stack.push(arr[i])
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
 * 1. Single element: [5] → [-1]
 * 2. Increasing sequence: [1,2,3,4,5] → [-1,-1,-1,-1,-1] (first elements)
 *    Wait, let me recalculate:
 *    [1,2,3,4,5]
 *    1: no previous smaller → -1
 *    2: previous smaller is 1 → 1
 *    3: previous smaller is 2 → 2
 *    4: previous smaller is 3 → 3
 *    5: previous smaller is 4 → 4
 *    Result: [-1,1,2,3,4]
 * 
 * 3. Decreasing sequence: [5,4,3,2,1] → [-1,-1,-1,-1,-1]
 * 4. All same: [3,3,3,3] → [-1,-1,-1,-1]
 * 5. Two elements: [5,3] → [-1,-1]
 * 6. With negatives: [-5, 3, -2, 8] → [-1, -5, -5, -2]
 * 
 * DETAILED TRACE:
 * Input: [4, 10, 5, 8, 20, 15, 3, 12]
 * 
 * i=0, val=4: stack=[], PSE=-1, stack=[4]
 * i=1, val=10: 4<10, PSE=4, stack=[4,10]
 * i=2, val=5: pop 10, 4<5, PSE=4, stack=[4,5]
 * i=3, val=8: 5<8, PSE=5, stack=[4,5,8]
 * i=4, val=20: 8<20, PSE=8, stack=[4,5,8,20]
 * i=5, val=15: pop 20, 8<15, PSE=8, stack=[4,5,8,15]
 * i=6, val=3: pop 15,8,5,4, stack=[], PSE=-1, stack=[3]
 * i=7, val=12: 3<12, PSE=3, stack=[3,12]
 * 
 * Result: [-1, 4, 4, 5, 8, 8, -1, 3] ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = PreviousSmallerElement()
    
    println("Previous Smaller Element - Test Cases")
    println("=======================================\n")
    
    // Test 1: Comprehensive example
    println("Test 1: Comprehensive Example")
    val arr1 = intArrayOf(4, 10, 5, 8, 20, 15, 3, 12)
    println("Input: ${arr1.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr1).contentToString()}")
    println("Expected: [-1, 4, 4, 5, 8, 8, -1, 3]")
    println("Explanation:")
    println("  4: no previous smaller → -1")
    println("  10: previous smaller is 4 → 4")
    println("  5: previous smaller is 4 (skip 10) → 4")
    println("  8: previous smaller is 5 → 5")
    println("  20: previous smaller is 8 → 8")
    println("  15: previous smaller is 8 (skip 20) → 8")
    println("  3: no previous smaller → -1")
    println("  12: previous smaller is 3 → 3\n")
    
    // Test 2: Increasing sequence
    println("Test 2: Increasing Sequence")
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${arr2.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr2).contentToString()}")
    println("Expected: [-1, 1, 2, 3, 4]")
    println("Explanation: Each element's PSE is its immediate predecessor\n")
    
    // Test 3: Decreasing sequence
    println("Test 3: Decreasing Sequence")
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Input: ${arr3.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr3).contentToString()}")
    println("Expected: [-1, -1, -1, -1, -1]")
    println("Explanation: No previous smaller in decreasing sequence\n")
    
    // Test 4: All same
    println("Test 4: All Same Elements")
    val arr4 = intArrayOf(5, 5, 5, 5)
    println("Input: ${arr4.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr4).contentToString()}")
    println("Expected: [-1, -1, -1, -1]\n")
    
    // Test 5: With negatives
    println("Test 5: With Negative Numbers")
    val arr5 = intArrayOf(-5, 3, -2, 8, 1)
    println("Input: ${arr5.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr5).contentToString()}")
    println("Expected: [-1, -5, -5, -2, -2]\n")
    
    // Test 6: Single element
    println("Test 6: Single Element")
    val arr6 = intArrayOf(42)
    println("Input: ${arr6.contentToString()}")
    println("Output: ${solution.previousSmallerElement(arr6).contentToString()}")
    println("Expected: [-1]\n")
    
    // Test 7: With indices
    println("Test 7: Get Indices of PSE")
    val (values, indices) = solution.previousSmallerElementWithIndices(arr1)
    println("Input: ${arr1.contentToString()}")
    println("PSE Values: ${values.contentToString()}")
    println("PSE Indices: ${indices.contentToString()}")
    println("Expected Values: [-1, 4, 4, 5, 8, 8, -1, 3]")
    println("Expected Indices: [-1, 0, 0, 2, 3, 3, -1, 6]\n")
    
    // Test 8: Previous Smaller or Equal
    println("Test 8: Previous Smaller or Equal")
    val arr8 = intArrayOf(4, 5, 2, 25, 7, 5, 4)
    println("Input: ${arr8.contentToString()}")
    println("PSE: ${solution.previousSmallerElement(arr8).contentToString()}")
    println("PSE or Equal: ${solution.previousSmallerOrEqual(arr8).contentToString()}")
    println()
    
    // Summary table
    println("=== Monotonic Stack Pattern Summary ===")
    println("┌─────────────────┬───────────┬──────────────┬─────────────────┐")
    println("│ Problem         │ Direction │ Stack Type   │ Process Order   │")
    println("├─────────────────┼───────────┼──────────────┼─────────────────┤")
    println("│ Next Greater    │ Right     │ Decreasing   │ Right to Left   │")
    println("│ Next Smaller    │ Right     │ Increasing   │ Right to Left   │")
    println("│ Previous Greater│ Left      │ Decreasing   │ Left to Right   │")
    println("│ Previous Smaller│ Left      │ Increasing   │ Left to Right   │")
    println("└─────────────────┴───────────┴──────────────┴─────────────────┘")
    
    println("\n=== Applications ===")
    println("Previous Smaller Element is used in:")
    println("1. Largest Rectangle in Histogram")
    println("2. Stock Span Problem")
    println("3. Maximum Area Rectangle in Binary Matrix")
    println("4. Trapping Rain Water")
    println("5. Sum of Subarray Minimums")
    
    println("\n=== Performance ===")
    println("Brute Force: O(n²) - Check all previous elements")
    println("Monotonic Stack: O(n) - Each element processed once")
    println("Space: O(n) for stack and result")
}
