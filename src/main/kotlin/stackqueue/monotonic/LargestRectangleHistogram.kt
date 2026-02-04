/**
 * ============================================================================
 * PROBLEM: Largest Rectangle in Histogram
 * DIFFICULTY: Hard
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers heights representing the histogram's bar height 
 * where the width of each bar is 1, return the area of the largest rectangle 
 * in the histogram.
 * 
 * INPUT FORMAT:
 * - heights: Array of non-negative integers representing bar heights
 * Example: heights = [2, 1, 5, 6, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - Integer representing maximum rectangle area
 * Example: 10
 * 
 * CONSTRAINTS:
 * - 1 <= heights.length <= 10^5
 * - 0 <= heights[i] <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine building a rectangle from each bar. The rectangle's height is 
 * limited by the bar's height. The width extends as far as we can go left 
 * and right before hitting a shorter bar.
 * 
 * KEY INSIGHT:
 * For each bar at index i with height h:
 * - Find the left boundary: previous bar with height < h
 * - Find the right boundary: next bar with height < h
 * - Width = right_boundary - left_boundary - 1
 * - Area = height[i] * width
 * 
 * This is exactly Previous Smaller Element (left) and Next Smaller Element (right)!
 * 
 * VISUAL EXAMPLE:
 * ```
 * heights = [2, 1, 5, 6, 2, 3]
 * 
 * Visual histogram:
 *         6
 *       5 █
 *       █ █     3
 *   2   █ █ 2   █
 *   █ 1 █ █ █   █
 *   █ █ █ █ █   █
 *   0 1 2 3 4 5
 * 
 * For each bar, find rectangle:
 * 
 * Bar 0 (h=2):
 *   PSE: none (-1), NSE: index 1
 *   Width = 1 - (-1) - 1 = 1
 *   Area = 2 * 1 = 2
 * 
 * Bar 1 (h=1):
 *   PSE: none (-1), NSE: none (6)
 *   Width = 6 - (-1) - 1 = 6
 *   Area = 1 * 6 = 6
 * 
 * Bar 2 (h=5):
 *   PSE: index 1, NSE: index 4
 *   Width = 4 - 1 - 1 = 2
 *   Area = 5 * 2 = 10 ✓ (maximum!)
 * 
 * Bar 3 (h=6):
 *   PSE: index 2, NSE: index 4
 *   Width = 4 - 2 - 1 = 1
 *   Area = 6 * 1 = 6
 * 
 * Bar 4 (h=2):
 *   PSE: index 1, NSE: none (6)
 *   Width = 6 - 1 - 1 = 4
 *   Area = 2 * 4 = 8
 * 
 * Bar 5 (h=3):
 *   PSE: index 4, NSE: none (6)
 *   Width = 6 - 4 - 1 = 1
 *   Area = 3 * 1 = 3
 * 
 * Maximum = 10
 * ```
 * 
 * ALGORITHM (3 APPROACHES):
 * 
 * APPROACH 1: Separate PSE and NSE Arrays
 * 1. Compute PSE for all elements using monotonic stack
 * 2. Compute NSE for all elements using monotonic stack
 * 3. For each bar: area = height[i] * (NSE[i] - PSE[i] - 1)
 * 4. Return maximum area
 * Time: O(n), Space: O(n)
 * 
 * APPROACH 2: Single Pass with Stack
 * Instead of precomputing, calculate area when popping from stack:
 * 1. Process bars left to right
 * 2. If current bar < stack top, pop and calculate area
 * 3. For popped bar: 
 *    - Right boundary = current index
 *    - Left boundary = new stack top (or -1)
 *    - Area = height[popped] * (current - left - 1)
 * 4. Push current bar
 * Time: O(n), Space: O(n)
 * 
 * APPROACH 3: Optimized Single Pass (Most Common)
 * Same as Approach 2 but cleaner implementation
 * 
 * DETAILED TRACE (Approach 2):
 * ```
 * heights = [2, 1, 5, 6, 2, 3]
 * stack = [] (stores indices)
 * maxArea = 0
 * 
 * i=0, h=2:
 *   stack empty, push 0
 *   stack=[0]
 * 
 * i=1, h=1:
 *   1 < heights[0]=2, pop 0
 *   Calculate area for bar 0:
 *     right = 1, left = -1 (stack empty)
 *     width = 1 - (-1) - 1 = 1
 *     area = 2 * 1 = 2
 *     maxArea = 2
 *   1 > nothing, push 1
 *   stack=[1]
 * 
 * i=2, h=5:
 *   5 > heights[1]=1, push 2
 *   stack=[1,2]
 * 
 * i=3, h=6:
 *   6 > heights[2]=5, push 3
 *   stack=[1,2,3]
 * 
 * i=4, h=2:
 *   2 < heights[3]=6, pop 3
 *   Calculate area for bar 3:
 *     right = 4, left = 2
 *     width = 4 - 2 - 1 = 1
 *     area = 6 * 1 = 6
 *     maxArea = 6
 *   2 < heights[2]=5, pop 2
 *   Calculate area for bar 2:
 *     right = 4, left = 1
 *     width = 4 - 1 - 1 = 2
 *     area = 5 * 2 = 10
 *     maxArea = 10
 *   2 > heights[1]=1, push 4
 *   stack=[1,4]
 * 
 * i=5, h=3:
 *   3 > heights[4]=2, push 5
 *   stack=[1,4,5]
 * 
 * End of array, pop remaining:
 * Pop 5: right=6, left=4, width=6-4-1=1, area=3*1=3
 * Pop 4: right=6, left=1, width=6-1-1=4, area=2*4=8
 * Pop 1: right=6, left=-1, width=6-(-1)-1=6, area=1*6=6
 * 
 * maxArea = 10 ✓
 * ```
 * 
 * WHY SINGLE PASS WORKS:
 * When we pop a bar from stack, we know:
 * - Current index is its right boundary (first smaller to right)
 * - New stack top is its left boundary (previous smaller to left)
 * - This is the exact moment we have all info needed!
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - For each bar, expand left/right
 * 2. Divide & Conquer: O(n log n) - Find min, split, recurse
 * 3. PSE + NSE Arrays: O(n) - Precompute boundaries
 * 4. Single Pass Stack: O(n) - Most efficient
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Each bar pushed once: n pushes
 * - Each bar popped once: n pops
 * - Each pop does O(1) area calculation
 * - Total: 2n = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Stack can hold up to n bars (increasing sequence)
 * - No extra arrays needed in single-pass approach
 * 
 * WHY BETTER THAN BRUTE FORCE:
 * Brute Force: For each bar, expand left/right = O(n²)
 * Monotonic Stack: Each bar processed once = O(n)
 * 
 * For n=100,000:
 * Brute Force: ~10 billion operations
 * Stack: ~200,000 operations
 * Speedup: ~50,000x!
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*
import kotlin.math.max

class LargestRectangleInHistogram {
    
    /**
     * APPROACH 1: Using PSE and NSE arrays
     * Clear and easy to understand
     */
    fun largestRectangleAreaWithArrays(heights: IntArray): Int {
        val n = heights.size
        
        // Find Previous Smaller Element indices
        val pse = IntArray(n) { -1 }
        val stack1 = Stack<Int>()
        for (i in 0 until n) {
            while (stack1.isNotEmpty() && heights[stack1.peek()] >= heights[i]) {
                stack1.pop()
            }
            pse[i] = if (stack1.isEmpty()) -1 else stack1.peek()
            stack1.push(i)
        }
        
        // Find Next Smaller Element indices
        val nse = IntArray(n) { n }
        val stack2 = Stack<Int>()
        for (i in n - 1 downTo 0) {
            while (stack2.isNotEmpty() && heights[stack2.peek()] >= heights[i]) {
                stack2.pop()
            }
            nse[i] = if (stack2.isEmpty()) n else stack2.peek()
            stack2.push(i)
        }
        
        // Calculate maximum area
        var maxArea = 0
        for (i in 0 until n) {
            val width = nse[i] - pse[i] - 1
            val area = heights[i] * width
            maxArea = max(maxArea, area)
        }
        
        return maxArea
    }
    
    /**
     * APPROACH 2: Single pass with stack (Optimal)
     * Most commonly used in interviews
     */
    fun largestRectangleArea(heights: IntArray): Int {
        val stack = Stack<Int>()  // Store indices
        var maxArea = 0
        
        for (i in heights.indices) {
            // Pop bars taller than current
            while (stack.isNotEmpty() && heights[stack.peek()] > heights[i]) {
                val height = heights[stack.pop()]
                val width = if (stack.isEmpty()) i else i - stack.peek() - 1
                maxArea = max(maxArea, height * width)
            }
            stack.push(i)
        }
        
        // Process remaining bars in stack
        while (stack.isNotEmpty()) {
            val height = heights[stack.pop()]
            val width = if (stack.isEmpty()) heights.size else heights.size - stack.peek() - 1
            maxArea = max(maxArea, height * width)
        }
        
        return maxArea
    }
    
    /**
     * APPROACH 3: Single pass with sentinel
     * Add sentinel 0 at end to avoid separate loop
     */
    fun largestRectangleAreaWithSentinel(heights: IntArray): Int {
        val stack = Stack<Int>()
        var maxArea = 0
        
        // Process original array + sentinel
        for (i in 0..heights.size) {
            val currentHeight = if (i == heights.size) 0 else heights[i]
            
            while (stack.isNotEmpty() && heights[stack.peek()] > currentHeight) {
                val height = heights[stack.pop()]
                val width = if (stack.isEmpty()) i else i - stack.peek() - 1
                maxArea = max(maxArea, height * width)
            }
            
            if (i < heights.size) {
                stack.push(i)
            }
        }
        
        return maxArea
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Single bar: [5] → 5
 * 2. All same height: [3,3,3,3] → 12 (full width)
 * 3. Increasing: [1,2,3,4] → 6 (2*3 or 3*2)
 * 4. Decreasing: [4,3,2,1] → 4 (first bar)
 * 5. All zeros: [0,0,0] → 0
 * 6. With zeros: [2,0,2] → 2
 * 7. Large values: [10000] → 10000
 * 
 * COMMON MISTAKES:
 * 1. Forgetting to process remaining stack at end
 * 2. Off-by-one errors in width calculation
 * 3. Using >= instead of > in comparison (duplicates!)
 * 4. Not handling empty stack case
 * 
 * DETAILED VERIFICATION:
 * Input: [2, 1, 5, 6, 2, 3]
 * 
 * Expected rectangles:
 * - Height 1, width 6: area = 6
 * - Height 2, width 4: area = 8 (last 2 and 3 section)
 * - Height 5, width 2: area = 10 ✓ (bars 2 and 3)
 * - Height 6, width 1: area = 6
 * 
 * Let me trace approach 1 to verify:
 * PSE: [-1, -1, 1, 2, 1, 4]
 * NSE: [1, 6, 4, 4, 6, 6]
 * 
 * Areas:
 * i=0: (1-(-1)-1)*2 = 1*2 = 2
 * i=1: (6-(-1)-1)*1 = 6*1 = 6
 * i=2: (4-1-1)*5 = 2*5 = 10 ✓
 * i=3: (4-2-1)*6 = 1*6 = 6
 * i=4: (6-1-1)*2 = 4*2 = 8
 * i=5: (6-4-1)*3 = 1*3 = 3
 * 
 * Maximum = 10 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = LargestRectangleInHistogram()
    
    println("Largest Rectangle in Histogram - Test Cases")
    println("=============================================\n")
    
    // Test 1: Standard case
    println("Test 1: Standard Case")
    val heights1 = intArrayOf(2, 1, 5, 6, 2, 3)
    println("Heights: ${heights1.contentToString()}")
    println("Visual:")
    println("      6")
    println("    5 █")
    println("    █ █   3")
    println("2   █ █ 2 █")
    println("█ 1 █ █ █ █")
    println("0 1 2 3 4 5")
    println("Output: ${solution.largestRectangleArea(heights1)}")
    println("Expected: 10 (height 5, width 2)")
    println()
    
    // Test 2: Single bar
    println("Test 2: Single Bar")
    val heights2 = intArrayOf(5)
    println("Heights: ${heights2.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights2)}")
    println("Expected: 5\n")
    
    // Test 3: All same
    println("Test 3: All Same Height")
    val heights3 = intArrayOf(4, 4, 4, 4)
    println("Heights: ${heights3.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights3)}")
    println("Expected: 16 (height 4, width 4)\n")
    
    // Test 4: Increasing
    println("Test 4: Increasing Heights")
    val heights4 = intArrayOf(1, 2, 3, 4, 5)
    println("Heights: ${heights4.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights4)}")
    println("Expected: 9 (height 3, width 3)\n")
    
    // Test 5: Decreasing
    println("Test 5: Decreasing Heights")
    val heights5 = intArrayOf(5, 4, 3, 2, 1)
    println("Heights: ${heights5.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights5)}")
    println("Expected: 9 (height 3, width 3)\n")
    
    // Test 6: With zeros
    println("Test 6: With Zeros")
    val heights6 = intArrayOf(2, 0, 2)
    println("Heights: ${heights6.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights6)}")
    println("Expected: 2\n")
    
    // Test 7: Large rectangle
    println("Test 7: Large Rectangle")
    val heights7 = intArrayOf(2, 4)
    println("Heights: ${heights7.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights7)}")
    println("Expected: 4\n")
    
    // Test 8: Complex case
    println("Test 8: Complex Case")
    val heights8 = intArrayOf(6, 2, 5, 4, 5, 1, 6)
    println("Heights: ${heights8.contentToString()}")
    println("Output: ${solution.largestRectangleArea(heights8)}")
    println("Expected: 12 (height 4, width 3)\n")
    
    // Compare approaches
    println("=== Approach Comparison ===")
    println("Approach 1 (PSE+NSE): ${solution.largestRectangleAreaWithArrays(heights1)}")
    println("Approach 2 (Single Pass): ${solution.largestRectangleArea(heights1)}")
    println("Approach 3 (With Sentinel): ${solution.largestRectangleAreaWithSentinel(heights1)}")
    println("All should return: 10\n")
    
    // Visualization helper
    println("=== How to Think About It ===")
    println("For each bar, imagine it's the shortest bar in a rectangle.")
    println("How wide can that rectangle extend?")
    println("It extends until we hit a shorter bar on either side.")
    println()
    println("Example: [2, 1, 5, 6, 2, 3]")
    println("Bar at index 2 (height 5):")
    println("  - Can't extend left past index 1 (height 1 < 5)")
    println("  - Can extend to index 3 (height 6 >= 5)")
    println("  - Can't extend past index 4 (height 2 < 5)")
    println("  - Width: indices 2-3 = 2 bars")
    println("  - Area: 5 * 2 = 10")
    
    println("\n=== Applications ===")
    println("This algorithm is used in:")
    println("1. Maximal Rectangle in Binary Matrix")
    println("2. Maximum Score of Buildings")
    println("3. Maximum Width Ramp")
    println("4. Count of Valid Subarrays")
    
    println("\n=== Performance ===")
    println("Brute Force: O(n²) - Try all possible rectangles")
    println("Divide & Conquer: O(n log n) - Split by minimum")
    println("Monotonic Stack: O(n) - Single pass")
    println("\nFor n=100,000 bars:")
    println("Brute Force: ~10 billion operations")
    println("Stack: ~200,000 operations")
}
