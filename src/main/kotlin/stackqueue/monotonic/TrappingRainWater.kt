/**
 * ============================================================================
 * PROBLEM: Trapping Rain Water
 * DIFFICULTY: Hard
 * CATEGORY: Stack/Queue - Monotonic Stack
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given n non-negative integers representing an elevation map where the width 
 * of each bar is 1, compute how much water it can trap after raining.
 * 
 * INPUT FORMAT:
 * - height: Array of non-negative integers representing elevation
 * Example: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 
 * OUTPUT FORMAT:
 * - Integer representing total water trapped
 * Example: 6
 * 
 * CONSTRAINTS:
 * - n == height.length
 * - 1 <= n <= 2 * 10^4
 * - 0 <= height[i] <= 10^5
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Water can be trapped at position i if there are taller bars on both sides.
 * The amount of water at position i depends on:
 * - Height of tallest bar to the left
 * - Height of tallest bar to the right
 * - Water level = min(left_max, right_max)
 * - Water at i = max(0, water_level - height[i])
 * 
 * VISUAL EXAMPLE:
 * ```
 * height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * 
 *              3
 *          2   █       2
 *      1   █   █   1   █   1
 *  0   █   █   █   █   █   █
 *  ━━━━━━━━━━━━━━━━━━━━━━━━━
 *  0 1 2 3 4 5 6 7 8 9 10 11
 * 
 * With water (W = water):
 *              3
 *          2   █   W W 2
 *      1 W █ W █ W █ W █ W 1
 *  0 W █ W █ W █ W █ W █ W █
 *  ━━━━━━━━━━━━━━━━━━━━━━━━━
 *  0 1 2 3 4 5 6 7 8 9 10 11
 * 
 * Water at each index:
 * 0: 0 (no left wall)
 * 1: 0 (filled)
 * 2: 1 (trapped between 1 and 2)
 * 3: 0 (filled)
 * 4: 1 (trapped between 2 and 3)
 * 5: 2 (trapped between 2 and 3)
 * 6: 1 (trapped between 2 and 3)
 * 7: 0 (filled)
 * 8: 0 (filled)
 * 9: 1 (trapped between 3 and 2)
 * 10: 0 (filled)
 * 11: 0 (no right wall)
 * 
 * Total: 0+0+1+0+1+2+1+0+0+1+0+0 = 6
 * ```
 * 
 * FOUR DIFFERENT APPROACHES:
 * 
 * APPROACH 1: Brute Force
 * For each position, find left_max and right_max by scanning
 * Time: O(n²), Space: O(1)
 * 
 * APPROACH 2: Precompute Left/Right Max Arrays
 * 1. Precompute left_max[i] = max height from 0 to i
 * 2. Precompute right_max[i] = max height from i to n-1
 * 3. Water at i = min(left_max[i], right_max[i]) - height[i]
 * Time: O(n), Space: O(n)
 * 
 * APPROACH 3: Two Pointers (Most Elegant)
 * Use two pointers from both ends, track left_max and right_max
 * Time: O(n), Space: O(1)
 * 
 * APPROACH 4: Monotonic Stack (Related to NSE/PSE)
 * Calculate water horizontally layer by layer when popping
 * Time: O(n), Space: O(n)
 * 
 * ============================================================================
 * APPROACH 2: PRECOMPUTE ARRAYS (EASIEST TO UNDERSTAND)
 * ============================================================================
 * 
 * ALGORITHM:
 * 1. Compute left_max[i]: Maximum height from index 0 to i
 *    left_max[i] = max(left_max[i-1], height[i])
 * 
 * 2. Compute right_max[i]: Maximum height from index i to n-1
 *    right_max[i] = max(right_max[i+1], height[i])
 * 
 * 3. For each position:
 *    water_level = min(left_max[i], right_max[i])
 *    water[i] = max(0, water_level - height[i])
 * 
 * TRACE:
 * height =    [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 * left_max =  [0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3]
 * right_max = [3, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 1]
 * water_level=[0, 1, 1, 2, 2, 2, 2, 3, 2, 2, 2, 1]
 * water =     [0, 0, 1, 0, 1, 2, 1, 0, 0, 1, 0, 0]
 * Total = 6
 * 
 * ============================================================================
 * APPROACH 3: TWO POINTERS (OPTIMAL - NO EXTRA SPACE)
 * ============================================================================
 * 
 * KEY INSIGHT:
 * We don't need complete left_max and right_max arrays!
 * At any position, water trapped depends on the MINIMUM of left_max and right_max.
 * 
 * ALGORITHM:
 * 1. Use two pointers: left and right
 * 2. Track left_max and right_max seen so far
 * 3. If left_max < right_max:
 *    - Water at left = left_max - height[left] (guaranteed by left_max)
 *    - Move left pointer
 * 4. Else:
 *    - Water at right = right_max - height[right]
 *    - Move right pointer
 * 
 * WHY IT WORKS:
 * If left_max < right_max, we KNOW that even if there's a taller bar
 * on the right, the water level at left is limited by left_max.
 * 
 * ============================================================================
 * APPROACH 4: MONOTONIC STACK
 * ============================================================================
 * 
 * DIFFERENT PERSPECTIVE: Calculate water horizontally layer by layer
 * 
 * Instead of "how much water at each position vertically?",
 * think "when we find a taller bar, water fills the valley before it"
 * 
 * ALGORITHM:
 * 1. Maintain stack of indices (monotonic decreasing by height)
 * 2. When current bar >= stack top:
 *    a. Pop the valley (bottom of trapped water)
 *    b. If stack not empty, calculate trapped water:
 *       - Left boundary = stack.peek()
 *       - Right boundary = current
 *       - Height = min(height[left], height[right]) - height[valley]
 *       - Width = right - left - 1
 *       - Water += height * width
 * 3. Push current index
 * 
 * VISUAL (Stack Approach):
 * ```
 * When we see height 3 at index 7:
 *         3
 *     2   █
 *     █ W █  <- This water (W) gets calculated
 *   0 1 2 3 4 5 6 7
 * 
 * Valley at index 2 (height 0)
 * Left wall at index 3 (height 2)
 * Right wall at index 7 (height 3)
 * Water height: min(2, 3) - 0 = 2
 * Width: 7 - 3 - 1 = 3
 * Water: 2 * 3 = 6 (but need to subtract bars...)
 * 
 * Actually, process layer by layer:
 * Valley 2 (h=0), left 1 (h=1), right 3 (h=2)
 * Height: min(1,2) - 0 = 1
 * Width: 3 - 1 - 1 = 1
 * Water: 1 * 1 = 1
 * ```
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²), O(1) - Scan for each position
 * 2. Precompute Arrays: O(n), O(n) - Easy to understand
 * 3. Two Pointers: O(n), O(1) - Most optimal
 * 4. Monotonic Stack: O(n), O(n) - Different perspective
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * APPROACH 2 (Precompute Arrays):
 * TIME: O(n) - Three passes (left_max, right_max, calculate water)
 * SPACE: O(n) - Two auxiliary arrays
 * 
 * APPROACH 3 (Two Pointers):
 * TIME: O(n) - Single pass with two pointers
 * SPACE: O(1) - Only tracking left_max and right_max
 * 
 * APPROACH 4 (Monotonic Stack):
 * TIME: O(n) - Each element pushed/popped once
 * SPACE: O(n) - Stack storage
 * 
 * RECOMMENDATION: Two Pointers for interviews (optimal + clean)
 * 
 * ============================================================================
 */

package stackqueue.monotonic

import java.util.*
import kotlin.math.max
import kotlin.math.min

class TrappingRainWater {
    
    /**
     * APPROACH 1: Brute Force (for understanding)
     * For each position, scan left and right to find max heights
     */
    fun trapBruteForce(height: IntArray): Int {
        val n = height.size
        var totalWater = 0
        
        for (i in 1 until n - 1) {  // Skip first and last
            // Find max height to the left
            var leftMax = 0
            for (j in 0..i) {
                leftMax = max(leftMax, height[j])
            }
            
            // Find max height to the right
            var rightMax = 0
            for (j in i until n) {
                rightMax = max(rightMax, height[j])
            }
            
            // Water at position i
            totalWater += min(leftMax, rightMax) - height[i]
        }
        
        return totalWater
    }
    
    /**
     * APPROACH 2: Precompute Left and Right Max Arrays
     * Easy to understand and implement
     */
    fun trapWithArrays(height: IntArray): Int {
        val n = height.size
        if (n < 3) return 0
        
        // Compute left max for each position
        val leftMax = IntArray(n)
        leftMax[0] = height[0]
        for (i in 1 until n) {
            leftMax[i] = max(leftMax[i - 1], height[i])
        }
        
        // Compute right max for each position
        val rightMax = IntArray(n)
        rightMax[n - 1] = height[n - 1]
        for (i in n - 2 downTo 0) {
            rightMax[i] = max(rightMax[i + 1], height[i])
        }
        
        // Calculate water at each position
        var totalWater = 0
        for (i in 0 until n) {
            val waterLevel = min(leftMax[i], rightMax[i])
            totalWater += waterLevel - height[i]
        }
        
        return totalWater
    }
    
    /**
     * APPROACH 3: Two Pointers (OPTIMAL)
     * Most efficient solution - O(n) time, O(1) space
     */
    fun trap(height: IntArray): Int {
        if (height.isEmpty()) return 0
        
        var left = 0
        var right = height.size - 1
        var leftMax = 0
        var rightMax = 0
        var totalWater = 0
        
        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side
                if (height[left] >= leftMax) {
                    leftMax = height[left]
                } else {
                    totalWater += leftMax - height[left]
                }
                left++
            } else {
                // Process right side
                if (height[right] >= rightMax) {
                    rightMax = height[right]
                } else {
                    totalWater += rightMax - height[right]
                }
                right--
            }
        }
        
        return totalWater
    }
    
    /**
     * APPROACH 4: Monotonic Stack
     * Calculate water horizontally layer by layer
     */
    fun trapWithStack(height: IntArray): Int {
        var totalWater = 0
        val stack = Stack<Int>()  // Store indices
        
        for (i in height.indices) {
            // While current bar is taller than stack top
            while (stack.isNotEmpty() && height[i] > height[stack.peek()]) {
                val valley = stack.pop()
                
                if (stack.isEmpty()) break
                
                // Calculate trapped water
                val left = stack.peek()
                val distance = i - left - 1
                val boundedHeight = min(height[left], height[i]) - height[valley]
                totalWater += distance * boundedHeight
            }
            
            stack.push(i)
        }
        
        return totalWater
    }
}

/**
 * ============================================================================
 * EDGE CASES & TESTING
 * ============================================================================
 * 
 * EDGE CASES:
 * 1. Empty array: [] → 0
 * 2. Single bar: [5] → 0
 * 3. Two bars: [3, 2] → 0
 * 4. Flat: [2, 2, 2] → 0
 * 5. No trap: [3, 2, 1] → 0 (descending)
 * 6. No trap: [1, 2, 3] → 0 (ascending)
 * 7. Simple trap: [3, 0, 3] → 3
 * 8. Multiple traps: [3, 0, 2, 0, 4] → 7
 * 
 * DETAILED TRACE (Two Pointers):
 * height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 * 
 * Initial: left=0, right=11, leftMax=0, rightMax=0, water=0
 * 
 * i=0: h[0]=0 < h[11]=1
 *   h[0]=0 >= leftMax=0, leftMax=0
 *   left=1, water=0
 * 
 * i=1: h[1]=1 < h[11]=1... equal, go to else
 *   h[11]=1 >= rightMax=0, rightMax=1
 *   right=10, water=0
 * 
 * ... (continuing)
 * 
 * Final water = 6 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = TrappingRainWater()
    
    println("Trapping Rain Water - Test Cases")
    println("==================================\n")
    
    // Test 1: Standard example
    println("Test 1: Standard Example")
    val height1 = intArrayOf(0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1)
    println("Height: ${height1.contentToString()}")
    println("Visual:")
    println("        3")
    println("    2   █   W W 2")
    println("1 W █ W █ W █ W █ W 1")
    println("█ W █ W █ W █ W █ W █")
    println("Output: ${solution.trap(height1)}")
    println("Expected: 6\n")
    
    // Test 2: Simple trap
    println("Test 2: Simple Trap")
    val height2 = intArrayOf(3, 0, 2, 0, 4)
    println("Height: ${height2.contentToString()}")
    println("Output: ${solution.trap(height2)}")
    println("Expected: 7 (3+2+2 water units)\n")
    
    // Test 3: No water trapped
    println("Test 3: No Water (Descending)")
    val height3 = intArrayOf(5, 4, 3, 2, 1)
    println("Height: ${height3.contentToString()}")
    println("Output: ${solution.trap(height3)}")
    println("Expected: 0\n")
    
    // Test 4: Ascending
    println("Test 4: No Water (Ascending)")
    val height4 = intArrayOf(1, 2, 3, 4, 5)
    println("Height: ${height4.contentToString()}")
    println("Output: ${solution.trap(height4)}")
    println("Expected: 0\n")
    
    // Test 5: Multiple peaks
    println("Test 5: Multiple Peaks")
    val height5 = intArrayOf(4, 2, 0, 3, 2, 5)
    println("Height: ${height5.contentToString()}")
    println("Output: ${solution.trap(height5)}")
    println("Expected: 9\n")
    
    // Test 6: V-shape
    println("Test 6: V-Shape")
    val height6 = intArrayOf(3, 0, 3)
    println("Height: ${height6.contentToString()}")
    println("Output: ${solution.trap(height6)}")
    println("Expected: 3\n")
    
    // Test 7: Flat
    println("Test 7: Flat")
    val height7 = intArrayOf(2, 2, 2, 2)
    println("Height: ${height7.contentToString()}")
    println("Output: ${solution.trap(height7)}")
    println("Expected: 0\n")
    
    // Compare all approaches
    println("=== Approach Comparison ===")
    println("Brute Force: ${solution.trapBruteForce(height1)}")
    println("With Arrays: ${solution.trapWithArrays(height1)}")
    println("Two Pointers: ${solution.trap(height1)}")
    println("Monotonic Stack: ${solution.trapWithStack(height1)}")
    println("All should return: 6\n")
    
    // Performance comparison
    println("=== Performance Comparison ===")
    println("┌──────────────────┬─────────────┬──────────────┐")
    println("│ Approach         │ Time        │ Space        │")
    println("├──────────────────┼─────────────┼──────────────┤")
    println("│ Brute Force      │ O(n²)       │ O(1)         │")
    println("│ Precompute Arrays│ O(n)        │ O(n)         │")
    println("│ Two Pointers     │ O(n)        │ O(1) ← BEST  │")
    println("│ Monotonic Stack  │ O(n)        │ O(n)         │")
    println("└──────────────────┴─────────────┴──────────────┘")
    
    println("\n=== Key Insights ===")
    println("1. Water at position i = min(left_max, right_max) - height[i]")
    println("2. Two pointers: Process from smaller side")
    println("3. Why it works: Smaller side determines water level")
    println("4. Stack: Calculate horizontally layer by layer")
    
    println("\n=== Interview Tips ===")
    println("✓ Start with brute force, explain the optimization")
    println("✓ Two pointers is preferred (optimal space)")
    println("✓ Explain why we move from smaller side")
    println("✓ Handle edge cases: empty, single bar, flat")
}
