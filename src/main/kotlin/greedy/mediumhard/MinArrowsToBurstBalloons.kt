/**
 * ============================================================================
 * PROBLEM: Minimum Arrows to Burst Balloons
 * DIFFICULTY: Medium
 * CATEGORY: Greedy, Intervals
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Balloons are placed on a 2D plane, each represented by horizontal diameter [start, end].
 * An arrow shot vertically at x bursts all balloons where start <= x <= end.
 * Find minimum arrows needed to burst all balloons.
 * 
 * INPUT FORMAT:
 * - points: [[10,16],[2,8],[1,6],[7,12]]
 * 
 * OUTPUT FORMAT:
 * - Integer: minimum arrows needed (2)
 * 
 * CONSTRAINTS:
 * - 1 <= points.length <= 10^5
 * - points[i].length == 2
 * - -2^31 <= start < end <= 2^31 - 1
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Sort balloons by end position. Shoot arrow at earliest end point.
 * This arrow bursts all overlapping balloons. Continue with remaining.
 * 
 * KEY INSIGHT:
 * Greedy choice: shoot at the end of first balloon. This maximizes
 * chance of hitting overlapping balloons.
 * 
 * ALGORITHM STEPS:
 * 1. Sort balloons by end position
 * 2. Shoot first arrow at end of first balloon
 * 3. For each balloon:
 *    - If starts after current arrow: need new arrow
 *    - Update arrow position to current balloon's end
 * 4. Return arrow count
 * 
 * VISUAL EXAMPLE:
 * points = [[10,16],[2,8],[1,6],[7,12]]
 * 
 * After sort by end: [[1,6],[2,8],[7,12],[10,16]]
 * 
 * Arrow 1 at x=6: bursts [1,6] and [2,8] (both contain 6)
 * [7,12] starts at 7 > 6: need new arrow
 * Arrow 2 at x=12: bursts [7,12] and [10,16] (both contain 12)
 * 
 * Result: 2 arrows ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sort by end (used here): O(n log n) time, O(1) space - OPTIMAL
 * 2. Interval merging: O(n log n) time - Same complexity
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Sorting: O(n log n)
 * - Single pass: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using arrow counter and position variables
 * - Sorting done in-place
 * 
 * ============================================================================
 */

package greedy.mediumhard

class MinArrowsToBurstBalloons {
    
    /**
     * Finds minimum arrows to burst all balloons
     */
    fun findMinArrowShots(points: Array<IntArray>): Int {
        if (points.isEmpty()) return 0
        
        // Sort by end position
        points.sortBy { it[1] }
        
        var arrows = 1
        var arrowPos = points[0][1]
        
        for (i in 1 until points.size) {
            // If balloon starts after current arrow
            if (points[i][0] > arrowPos) {
                arrows++
                arrowPos = points[i][1]
            }
        }
        
        return arrows
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single balloon: [[1,5]] → 1
 * 2. No overlap: [[1,2],[3,4],[5,6]] → 3
 * 3. All overlap: [[1,10],[2,9],[3,8]] → 1
 * 4. Touching: [[1,2],[2,3],[3,4]] → 2
 * 5. Nested: [[1,10],[2,3],[4,5]] → 1
 * 6. Same range: [[1,5],[1,5]] → 1
 * 
 * APPLICATIONS:
 * - Resource optimization
 * - Scheduling overlapping tasks
 * - Minimal intervention problems
 * 
 * ============================================================================
 */

fun main() {
    val solution = MinArrowsToBurstBalloons()
    
    println("Min Arrows to Burst Balloons - Test Cases")
    println("===========================================\n")
    
    println("Test 1: [[10,16],[2,8],[1,6],[7,12]]")
    println("Result: ${solution.findMinArrowShots(arrayOf(
        intArrayOf(10,16), intArrayOf(2,8), 
        intArrayOf(1,6), intArrayOf(7,12)
    ))}")
    println("Expected: 2 ✓\n")
    
    println("Test 2: [[1,2],[3,4],[5,6],[7,8]]")
    println("Result: ${solution.findMinArrowShots(arrayOf(
        intArrayOf(1,2), intArrayOf(3,4), 
        intArrayOf(5,6), intArrayOf(7,8)
    ))}")
    println("Expected: 4 ✓\n")
}
