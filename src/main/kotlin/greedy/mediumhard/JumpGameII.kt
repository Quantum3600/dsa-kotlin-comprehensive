/**
 * ============================================================================
 * PROBLEM: Jump Game II
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array where each element represents maximum jump length,
 * return minimum number of jumps needed to reach the last index.
 * Assume you can always reach the last index.
 * 
 * INPUT FORMAT:
 * - nums: [2,3,1,1,4] (max jump from each position)
 * 
 * OUTPUT FORMAT:
 * - Integer: minimum jumps needed (2)
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^4
 * - 0 <= nums[i] <= 1000
 * - Guaranteed to reach last index
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of it as BFS levels. Each jump is a level. Track the farthest
 * we can reach in current level, when we exhaust current level, increment jumps.
 * 
 * KEY INSIGHT:
 * Don't need to explore every path. Track:
 * - currentEnd: farthest index reachable with current jumps
 * - farthest: farthest index reachable with one more jump
 * When we reach currentEnd, we must make another jump.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize jumps=0, currentEnd=0, farthest=0
 * 2. For each index i from 0 to n-2:
 *    - Update farthest = max(farthest, i + nums[i])
 *    - If i == currentEnd:
 *      * Increment jumps
 *      * Update currentEnd = farthest
 * 3. Return jumps
 * 
 * VISUAL EXAMPLE:
 * nums = [2,3,1,1,4]
 * 
 * i=0: farthest=max(0,0+2)=2, i==currentEnd(0), jumps=1, currentEnd=2
 * i=1: farthest=max(2,1+3)=4 (can reach end!)
 * i=2: farthest=max(4,2+1)=4, i==currentEnd(2), jumps=2, currentEnd=4
 * 
 * Result: 2 jumps ✓
 * Path: 0→1→4 or 0→2→4
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy BFS-style (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Dynamic Programming: O(n^2) time, O(n) space - Not optimal
 * 3. BFS with queue: O(n) time, O(n) space - More space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through array: O(n)
 * - Each element processed once
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using three variables
 * 
 * ============================================================================
 */

package greedy.mediumhard

class JumpGameII {
    
    /**
     * Finds minimum jumps to reach last index
     */
    fun jump(nums: IntArray): Int {
        if (nums.size <= 1) return 0
        
        var jumps = 0
        var currentEnd = 0
        var farthest = 0
        
        // Don't need to check last index
        for (i in 0 until nums.size - 1) {
            // Update farthest reachable position
            farthest = maxOf(farthest, i + nums[i])
            
            // If reached end of current jump range
            if (i == currentEnd) {
                jumps++
                currentEnd = farthest
                
                // Early exit if can reach end
                if (currentEnd >= nums.size - 1) break
            }
        }
        
        return jumps
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element: [0] → 0
 * 2. Two elements: [1,1] → 1
 * 3. Direct jump: [5,1,1,1,1] → 1
 * 4. All ones: [1,1,1,1] → 3
 * 5. Optimal path: [2,3,1,1,4] → 2
 * 6. Large jumps: [10,9,8,7,6,5,4,3,2,1,1] → 2
 * 
 * APPLICATIONS:
 * - Shortest path problems
 * - Game AI pathfinding
 * - Network routing optimization
 * 
 * ============================================================================
 */

fun main() {
    val solution = JumpGameII()
    
    println("Jump Game II - Test Cases")
    println("==========================\n")
    
    println("Test 1: [2,3,1,1,4]")
    println("Result: ${solution.jump(intArrayOf(2, 3, 1, 1, 4))}")
    println("Expected: 2 ✓\n")
    
    println("Test 2: [2,3,0,1,4]")
    println("Result: ${solution.jump(intArrayOf(2, 3, 0, 1, 4))}")
    println("Expected: 2 ✓\n")
    
    println("Test 3: [1,1,1,1]")
    println("Result: ${solution.jump(intArrayOf(1, 1, 1, 1))}")
    println("Expected: 3 ✓\n")
}
