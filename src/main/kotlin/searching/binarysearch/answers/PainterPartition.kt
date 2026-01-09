/**
 * ============================================================================
 * PROBLEM: Painter's Partition Problem
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array where each element represents the length of a board and an
 * integer k representing the number of painters available. Each painter takes
 * 1 unit of time to paint 1 unit of the board. Find the minimum time required
 * to paint all boards if any painter will only paint contiguous sections of
 * boards.
 * 
 * Constraints:
 * - A painter can only paint contiguous boards
 * - All painters work simultaneously
 * - Minimize the maximum time taken by any painter
 * 
 * INPUT FORMAT:
 * - boards: Array where boards[i] = length of board i
 * - k: Number of painters
 * Example: boards = [10, 20, 30, 40], k = 2
 * 
 * OUTPUT FORMAT:
 * - Minimum time required
 * Example: 60
 * Explanation: Painter 1 paints [10, 20, 30] = 60 units
 *              Painter 2 paints [40] = 40 units
 *              Maximum time = 60 (minimized)
 * 
 * CONSTRAINTS:
 * - 1 <= boards.length <= 10^5
 * - 1 <= boards[i] <= 10^6
 * - 1 <= k <= boards.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is identical to the Book Allocation problem!  We're minimizing the
 * maximum work assigned to any painter. Use binary search on the answer
 * (maximum time per painter).
 * 
 * KEY INSIGHT:
 * - Minimum time: max(boards) (one painter must paint the longest board)
 * - Maximum time: sum(boards) (one painter paints everything)
 * - For a given max time, greedily assign boards to painters
 * - Find the smallest max time that allows valid assignment
 * 
 * ALGORITHM STEPS:
 * 1. If k > n: each painter gets at most one board, return max(boards)
 * 2. Binary search: low = max(boards), high = sum(boards)
 * 3. For each mid:
 *    - Check if we can assign boards such that no painter takes > mid time
 *    - Use greedy: assign boards to current painter until exceeds mid
 *    - If painters needed <= k: valid, try smaller time (high = mid)
 *    - Else: need more time (low = mid + 1)
 * 4. Return high (minimum valid time)
 * 
 * VISUAL EXAMPLE:
 * boards = [10, 20, 30, 40], k = 2
 * 
 * Assignment with maxTime = 60:
 * Painter 1: 10 (10), add 20 (30), add 30 (60) ✓ [can't add 40]
 * Painter 2: 40 (40) ✓
 * Painters used: 2 <= 2 ✓
 * 
 * Assignment with maxTime = 50:
 * Painter 1: 10 (10), add 20 (30), add 30 (60) > 50 ✗
 * Painter 1: 10 (10), add 20 (30) ✓
 * Painter 2: 30 (30) ✓
 * Painter 3: 40 (40) ✓
 * Painters used: 3 > 2 ✗
 * 
 * Binary Search Process:
 * [40, 41, .. ., 100]
 *  L              H
 * 
 * Similar to Book Allocation, converges to 60 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search: O(log(sum - max))
 * - Each iteration checks assignment: O(n)
 * - Total: O(n * log(sum - max))
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class PainterPartition {
    
    /**
     * Finds minimum time to paint all boards with k painters
     * @param boards Array of board lengths
     * @param k Number of painters
     * @return Minimum time required
     */
    fun minTimeToPaint(boards: IntArray, k:  Int): Int {
        val n = boards.size
        
        // If painters >= boards, each gets at most one board
        if (k >= n) return boards.maxOrNull() ?: 0
        
        var low = boards. maxOrNull() ?: 0  // At least max board
        var high = boards.sum()  // At most all boards
        var result = high
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val paintersNeeded = countPainters(boards, mid)
            
            if (paintersNeeded <= k) {
                // Can complete with maxTime = mid
                result = mid
                high = mid - 1  // Try to minimize further
            } else {
                // Need more time per painter
                low = mid + 1
            }
        }
        
        return result
    }
    
    /**
     * Counts minimum painters needed if each can paint at most maxTime units
     * @param boards Array of board lengths
     * @param maxTime Maximum time per painter
     * @return Number of painters needed
     */
    private fun countPainters(boards: IntArray, maxTime: Int): Int {
        var painters = 1
        var currentTime = 0
        
        for (board in boards) {
            if (currentTime + board <= maxTime) {
                // Assign this board to current painter
                currentTime += board
            } else {
                // Need a new painter for this board
                painters++
                currentTime = board
            }
        }
        
        return painters
    }
    
    /**
     * Alternative:  returns the actual partition
     */
    fun minTimeToPaintWithPartition(boards: IntArray, k: Int): Pair<Int, List<List<Int>>> {
        val minTime = minTimeToPaint(boards, k)
        
        // Reconstruct the partition
        val partition = mutableListOf<MutableList<Int>>()
        partition.add(mutableListOf())
        var currentTime = 0
        
        for (board in boards) {
            if (currentTime + board <= minTime) {
                currentTime += board
                partition.last().add(board)
            } else {
                partition.add(mutableListOf(board))
                currentTime = board
            }
        }
        
        return Pair(minTime, partition)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: boards = [10, 20, 30, 40], k = 2
 * 
 * Initial: low=40, high=100
 * 
 * Iteration 1: mid=70
 * Painter assignment:
 * - Painter 1: 10+20+30 = 60 <= 70 ✓
 * - Painter 2: 40 <= 70 ✓
 * Painters: 2 <= 2, result=70, high=69
 * 
 * Iteration 2: mid=54
 * Painter assignment:
 * - Painter 1: 10+20 = 30, add 30 = 60 > 54
 * - Painter 1: 10+20 = 30 <= 54 ✓
 * - Painter 2: 30 <= 54 ✓
 * - Painter 3: 40 <= 54 ✓
 * Painters: 3 > 2, low=55
 * 
 * Continue until converges to 60 ✓
 * 
 * Example 2: boards = [5, 10, 15, 20, 25], k = 3
 * 
 * low=25, high=75
 * 
 * mid=50:
 * - Painter 1: 5+10+15 = 30 <= 50 ✓
 * - Painter 2: 20+25 = 45 <= 50 ✓
 * Painters: 2 <= 3, result=50, high=49
 * 
 * mid=37:
 * - Painter 1: 5+10+15 = 30 <= 37 ✓
 * - Painter 2: 20 <= 37 ✓
 * - Painter 3: 25 <= 37 ✓
 * Painters:  3 <= 3, result=37, high=36
 * 
 * mid=30:
 * - Painter 1: 5+10+15 = 30 <= 30 ✓
 * - Painter 2: 20 <= 30 ✓
 * - Painter 3: 25 <= 30 ✓
 * Painters: 3 <= 3, result=30, high=29
 * 
 * mid=27:
 * - Painter 1: 5+10 = 15, add 15 = 30 > 27
 * - Painter 1: 5+10 = 15 <= 27 ✓
 * - Painter 2: 15 <= 27 ✓
 * - Painter 3: 20 <= 27 ✓
 * - Painter 4: 25 <= 27 ✓
 * Painters: 4 > 3, low=28
 * 
 * Eventually:  result = 30 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = PainterPartition()
    
    // Test Case 1: Standard case
    println("Test 1: boards=[10,20,30,40], k=2")
    val (time1, partition1) = solution.minTimeToPaintWithPartition(intArrayOf(10,20,30,40), 2)
    println("Result: $time1")
    partition1.forEachIndexed { i, boards ->
        println("  Painter ${i+1}: ${boards.joinToString("+")} = ${boards.sum()}")
    }
    // Expected: 60
    
    // Test Case 2: More painters
    println("\nTest 2: boards=[5,10,15,20,25], k=3")
    val (time2, partition2) = solution.minTimeToPaintWithPartition(intArrayOf(5,10,15,20,25), 3)
    println("Result: $time2")
    partition2.forEachIndexed { i, boards ->
        println("  Painter ${i+1}: ${boards. joinToString("+")} = ${boards.sum()}")
    }
    // Expected: 30
    
    // Test Case 3: Painters = boards
    println("\nTest 3: boards=[1,2,3,4,5], k=5")
    println("Result: ${solution.minTimeToPaint(intArrayOf(1,2,3,4,5), 5)}")
    // Expected: 5
    
    // Test Case 4: One painter
    println("\nTest 4: boards=[10,20,30], k=1")
    println("Result: ${solution.minTimeToPaint(intArrayOf(10,20,30), 1)}")
    // Expected: 60
    
    // Test Case 5: All same boards
    println("\nTest 5: boards=[100,100,100,100], k=2")
    println("Result: ${solution.minTimeToPaint(intArrayOf(100,100,100,100), 2)}")
    // Expected: 200
    
    // Comparison with Book Allocation
    println("\n--- Painter's Partition is identical to Book Allocation ---")
    println("Same problem, different context:")
    println("- Books → Boards")
    println("- Students → Painters")
    println("- Pages → Paint units")
}
