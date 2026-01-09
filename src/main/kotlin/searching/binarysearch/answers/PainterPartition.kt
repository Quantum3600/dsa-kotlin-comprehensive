/**
 * ============================================================================
 * PROBLEM: Painter's Partition Problem
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array boards where boards[i] represents the length of the ith board,
 * and K painters. Each painter takes 1 unit time to paint 1 unit of board.
 * A painter can only paint continuous sections of boards. Find the minimum time
 * needed to paint all boards if K painters work simultaneously.
 * 
 * INPUT FORMAT:
 * - boards: Array where boards[i] = length of ith board
 * - painters: Number of painters (K)
 * Example: boards = [10, 20, 30, 40], painters = 2
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimum time to paint all boards
 * Example: 60 (Painter 1: [10,20,30]=60, Painter 2: [40]=40)
 * 
 * CONSTRAINTS:
 * - 1 <= boards.length <= 10^5
 * - 1 <= painters <= boards.length
 * - 1 <= boards[i] <= 10^5
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is identical to Book Allocation problem! We want to distribute boards
 * among painters to minimize maximum time. If max time T works, any time > T
 * also works (monotonic). Binary search on the answer.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = max(boards) (at least one painter must paint longest board)
 *    high = sum(boards) (one painter paints all)
 * 2. Binary search on maximum time:
 *    - For each mid, check if allocation possible with max time = mid
 *    - If yes, try smaller time (search left)
 *    - If no, need larger time (search right)
 * 3. Return minimum valid time
 * 
 * VISUAL EXAMPLE:
 * boards = [10, 20, 30, 40], painters = 2
 * 
 * Try max time = 60:
 * Painter 1: [10, 20, 30] = 60 ✓
 * Painter 2: [40] = 40 ✓
 * Need 2 painters ✓
 * 
 * Try max time = 50:
 * Painter 1: [10, 20] = 30 ✓
 * Painter 2: [30] = 30 ✓
 * Painter 3: [40] = 40 ✓
 * Need 3 painters ✗ (only have 2)
 * 
 * Answer: 60
 * 
 * WHY BINARY SEARCH:
 * - Search space: [max(boards) to sum(boards)]
 * - Monotonic: If time T works, T+1 also works
 * - Want minimum T that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search range: [max, sum]
 * - Iterations: O(log(sum - max))
 * - Each iteration checks allocation: O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class PainterPartition {
    
    /**
     * Find minimum time to paint all boards with K painters
     * 
     * @param boards Array of board lengths
     * @param painters Number of painters
     * @return Minimum time needed
     */
    fun solve(boards: IntArray, painters: Int): Int {
        // Edge case: More painters than boards
        if (painters >= boards.size) {
            return boards.max()
        }
        
        // Search space: [max board length, sum of all lengths]
        var low = boards.max()
        var high = boards.sum()
        
        var answer = high
        
        // Binary search on maximum time
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Check if allocation possible with this max time
            if (canAllocate(boards, painters, mid)) {
                // Possible, try smaller time
                answer = mid
                high = mid - 1
            } else {
                // Not possible, need more time
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Check if boards can be painted by given painters with max time per painter
     * 
     * @param boards Array of board lengths
     * @param painters Number of painters available
     * @param maxTime Maximum time any painter should work
     * @return true if allocation possible, false otherwise
     */
    private fun canAllocate(boards: IntArray, painters: Int, maxTime: Int): Boolean {
        var paintersNeeded = 1
        var currentTime = 0
        
        for (board in boards) {
            // If adding this board exceeds max time for current painter
            if (currentTime + board > maxTime) {
                // Assign to next painter
                paintersNeeded++
                currentTime = board
                
                // If we need more painters than available
                if (paintersNeeded > painters) {
                    return false
                }
            } else {
                // Add board to current painter
                currentTime += board
            }
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: boards = [10, 20, 30, 40], painters = 2
 * 
 * Step 1: Initialize
 * low = 40, high = 100
 * 
 * Binary Search:
 * 
 * Iteration 1: low=40, high=100
 * - mid = 70
 * - Can allocate with max time 70?
 *   Painter 1: 10+20+30 = 60 ✓
 *   Painter 2: 40 = 40 ✓
 *   Yes! 2 painters needed
 * - answer = 70, high = 69
 * 
 * Iteration 2: low=40, high=69
 * - mid = 54
 * - Can allocate with max time 54?
 *   Painter 1: 10+20 = 30 ✓
 *   Painter 2: 30 = 30 ✓
 *   Painter 3: 40 = 40 ✗ Need 3 painters!
 *   No!
 * - low = 55
 * 
 * Continue searching...
 * Final answer = 60
 * 
 * ============================================================================
 */

fun main() {
    val solver = PainterPartition()
    
    println("=== Testing Painter's Partition ===\n")
    
    // Test Case 1: Normal case
    val boards1 = intArrayOf(10, 20, 30, 40)
    val painters1 = 2
    println("Test 1: boards = ${boards1.contentToString()}, painters = $painters1")
    println("Result: ${solver.solve(boards1, painters1)}")
    println("Expected: 60\n")
    
    // Test Case 2: Each painter gets one board
    val boards2 = intArrayOf(10, 20, 30, 40)
    val painters2 = 4
    println("Test 2: boards = ${boards2.contentToString()}, painters = $painters2")
    println("Result: ${solver.solve(boards2, painters2)}")
    println("Expected: 40\n")
    
    // Test Case 3: One painter paints all
    val boards3 = intArrayOf(10, 20, 30, 40)
    val painters3 = 1
    println("Test 3: boards = ${boards3.contentToString()}, painters = $painters3")
    println("Result: ${solver.solve(boards3, painters3)}")
    println("Expected: 100\n")
    
    // Test Case 4: Large array
    val boards4 = intArrayOf(5, 10, 15, 20, 25, 30, 35, 40)
    val painters4 = 3
    println("Test 4: boards = ${boards4.contentToString()}, painters = $painters4")
    println("Result: ${solver.solve(boards4, painters4)}")
    println("Expected: 60\n")
    
    // Test Case 5: All boards same length
    val boards5 = intArrayOf(20, 20, 20, 20)
    val painters5 = 2
    println("Test 5: boards = ${boards5.contentToString()}, painters = $painters5")
    println("Result: ${solver.solve(boards5, painters5)}")
    println("Expected: 40\n")
    
    // Test Case 6: Single board
    val boards6 = intArrayOf(100)
    val painters6 = 1
    println("Test 6: boards = ${boards6.contentToString()}, painters = $painters6")
    println("Result: ${solver.solve(boards6, painters6)}")
    println("Expected: 100\n")
}
