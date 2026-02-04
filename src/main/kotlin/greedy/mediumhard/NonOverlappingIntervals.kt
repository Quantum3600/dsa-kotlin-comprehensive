/**
 * ============================================================================
 * PROBLEM: Non-overlapping Intervals
 * DIFFICULTY: Medium
 * CATEGORY: Greedy, Intervals
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a collection of intervals, find minimum number of intervals to remove
 * to make the rest non-overlapping.
 * 
 * INPUT FORMAT:
 * - intervals: [[1,2],[2,3],[3,4],[1,3]]
 * 
 * OUTPUT FORMAT:
 * - Integer: minimum removals needed (1)
 * 
 * CONSTRAINTS:
 * - 1 <= intervals.length <= 10^5
 * - intervals[i].length == 2
 * - -5 * 10^4 <= start < end <= 5 * 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Similar to activity selection. Sort by end time and keep intervals
 * that end earliest. Count overlaps and remove them.
 * 
 * KEY INSIGHT:
 * Maximum non-overlapping intervals = n - minimum removals
 * Greedy: always keep interval that ends first.
 * 
 * ALGORITHM STEPS:
 * 1. Sort intervals by end time
 * 2. Track last kept interval's end
 * 3. For each interval:
 *    - If start >= last end: keep it (non-overlapping)
 *    - Else: remove it (overlapping)
 * 4. Return removal count
 * 
 * VISUAL EXAMPLE:
 * intervals = [[1,2],[2,3],[3,4],[1,3]]
 * 
 * After sort by end: [[1,2],[2,3],[1,3],[3,4]]
 * 
 * Keep [1,2]: lastEnd=2
 * [2,3]: 2>=2 ✓ keep, lastEnd=3
 * [1,3]: 1<3 ✗ remove (overlaps)
 * [3,4]: 3>=3 ✓ keep, lastEnd=4
 * 
 * Removals: 1 ✓
 * Kept: [1,2],[2,3],[3,4]
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sort by end (used here): O(n log n) time, O(1) space - OPTIMAL
 * 2. Sort by start: O(n log n) time - More complex logic
 * 3. Dynamic Programming: O(n^2) time - Not optimal
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
 * - Only using counter variables
 * - Sorting done in-place
 * 
 * ============================================================================
 */

package greedy.mediumhard

class NonOverlappingIntervals {
    
    /**
     * Returns minimum intervals to remove
     */
    fun eraseOverlapIntervals(intervals: Array<IntArray>): Int {
        if (intervals.isEmpty()) return 0
        
        // Sort by end time
        intervals.sortBy { it[1] }
        
        var removals = 0
        var lastEnd = intervals[0][1]
        
        for (i in 1 until intervals.size) {
            if (intervals[i][0] < lastEnd) {
                // Overlapping - remove current interval
                removals++
            } else {
                // Non-overlapping - update last end
                lastEnd = intervals[i][1]
            }
        }
        
        return removals
    }
    
    /**
     * Alternative: Returns maximum non-overlapping intervals
     */
    fun maxNonOverlapping(intervals: Array<IntArray>): Int {
        if (intervals.isEmpty()) return 0
        
        intervals.sortBy { it[1] }
        
        var count = 1
        var lastEnd = intervals[0][1]
        
        for (i in 1 until intervals.size) {
            if (intervals[i][0] >= lastEnd) {
                count++
                lastEnd = intervals[i][1]
            }
        }
        
        return count
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single interval: [[1,2]] → 0
 * 2. No overlap: [[1,2],[2,3]] → 0
 * 3. All overlap: [[1,2],[1,2],[1,2]] → 2
 * 4. Nested: [[1,100],[11,22],[1,11],[2,12]] → 2
 * 5. Adjacent: [[1,2],[2,3],[3,4]] → 0
 * 6. Complete overlap: [[1,10],[2,3],[4,5]] → 2
 * 
 * APPLICATIONS:
 * - Interval scheduling
 * - Resource allocation
 * - Meeting room optimization
 * - Task scheduling
 * 
 * ============================================================================
 */

fun main() {
    val solution = NonOverlappingIntervals()
    
    println("Non-overlapping Intervals - Test Cases")
    println("========================================\n")
    
    println("Test 1: [[1,2],[2,3],[3,4],[1,3]]")
    println("Removals: ${solution.eraseOverlapIntervals(arrayOf(
        intArrayOf(1,2), intArrayOf(2,3), 
        intArrayOf(3,4), intArrayOf(1,3)
    ))}")
    println("Expected: 1 ✓\n")
    
    println("Test 2: [[1,2],[1,2],[1,2]]")
    println("Removals: ${solution.eraseOverlapIntervals(arrayOf(
        intArrayOf(1,2), intArrayOf(1,2), intArrayOf(1,2)
    ))}")
    println("Expected: 2 ✓\n")
    
    println("Test 3: [[1,2],[2,3]]")
    println("Removals: ${solution.eraseOverlapIntervals(arrayOf(
        intArrayOf(1,2), intArrayOf(2,3)
    ))}")
    println("Expected: 0 ✓\n")
}
