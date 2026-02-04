/**
 * ============================================================================
 * PROBLEM: Jump Game
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array where each element represents maximum jump length from that position,
 * determine if you can reach the last index starting from index 0.
 * 
 * INPUT FORMAT:
 * - nums: [2,3,1,1,4] (max jump from each position)
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if can reach last index, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 10^4
 * - 0 <= nums[i] <= 10^5
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Track the farthest index we can reach. If at any point current index
 * is beyond farthest reachable, we're stuck.
 * 
 * KEY INSIGHT:
 * Don't need to track actual jumps, just whether we can reach the end.
 * Update max reachable position as we go.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize maxReach = 0
 * 2. For each index i from 0 to n-1:
 *    - If i > maxReach: return false (stuck)
 *    - Update maxReach = max(maxReach, i + nums[i])
 *    - If maxReach >= last index: return true
 * 3. Return true if completed loop
 * 
 * VISUAL EXAMPLE:
 * nums = [2,3,1,1,4]
 * 
 * i=0: maxReach = max(0, 0+2) = 2
 * i=1: maxReach = max(2, 1+3) = 4 (can reach end!)
 * Result: true ✓
 * 
 * EXAMPLE 2 (Stuck):
 * nums = [3,2,1,0,4]
 * 
 * i=0: maxReach = max(0, 0+3) = 3
 * i=1: maxReach = max(3, 1+2) = 3
 * i=2: maxReach = max(3, 2+1) = 3
 * i=3: maxReach = max(3, 3+0) = 3
 * i=4: 4 > 3, stuck!
 * Result: false ✗
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy max reach (used here): O(n) time, O(1) space - OPTIMAL
 * 2. BFS: O(n) time, O(n) space - More space
 * 3. Dynamic Programming: O(n^2) time - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through array: O(n)
 * - Each element checked once
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using one variable (maxReach)
 * 
 * ============================================================================
 */

package greedy.mediumhard

class JumpGame {
    
    /**
     * Checks if can reach last index
     */
    fun canJump(nums: IntArray): Boolean {
        var maxReach = 0
        
        for (i in nums.indices) {
            // If current position beyond max reach, stuck
            if (i > maxReach) return false
            
            // Update max reachable position
            maxReach = maxOf(maxReach, i + nums[i])
            
            // Early exit if can reach end
            if (maxReach >= nums.size - 1) return true
        }
        
        return true
    }
    
    /**
     * Alternative: Work backwards from end
     */
    fun canJumpBackward(nums: IntArray): Boolean {
        var lastPos = nums.size - 1
        
        for (i in nums.size - 2 downTo 0) {
            if (i + nums[i] >= lastPos) {
                lastPos = i
            }
        }
        
        return lastPos == 0
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element: [0] → true
 * 2. Can't move: [0,1] → false
 * 3. Direct jump: [5,0,0,0,0] → true
 * 4. Zero in middle: [2,0,0] → false
 * 5. All ones: [1,1,1,1] → true
 * 6. Large jump: [10] → true
 * 
 * APPLICATIONS:
 * - Path finding problems
 * - Game level completion
 * - Reachability analysis
 * 
 * ============================================================================
 */

fun main() {
    val solution = JumpGame()
    
    println("Jump Game - Test Cases")
    println("=======================\n")
    
    println("Test 1: [2,3,1,1,4]")
    println("Result: ${solution.canJump(intArrayOf(2, 3, 1, 1, 4))}")
    println("Expected: true ✓\n")
    
    println("Test 2: [3,2,1,0,4]")
    println("Result: ${solution.canJump(intArrayOf(3, 2, 1, 0, 4))}")
    println("Expected: false ✓\n")
    
    println("Test 3: [0]")
    println("Result: ${solution.canJump(intArrayOf(0))}")
    println("Expected: true ✓\n")
}
