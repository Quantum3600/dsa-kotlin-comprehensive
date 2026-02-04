/**
 * ============================================================================
 * PROBLEM: Insert Interval
 * DIFFICULTY: Medium
 * CATEGORY: Greedy, Intervals
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a set of non-overlapping intervals sorted by start time, insert a new
 * interval and merge if necessary. Return the result with no overlaps.
 * 
 * INPUT FORMAT:
 * - intervals: [[1,3],[6,9]] (list of [start, end] intervals)
 * - newInterval: [2,5] (interval to insert)
 * 
 * OUTPUT FORMAT:
 * - List of intervals: [[1,5],[6,9]]
 * 
 * CONSTRAINTS:
 * - 0 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= start <= end <= 10^5
 * - Intervals are sorted and non-overlapping
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Three phases:
 * 1. Add all intervals that end before new interval starts
 * 2. Merge all overlapping intervals with new interval
 * 3. Add all intervals that start after new interval ends
 * 
 * KEY INSIGHT:
 * Two intervals [a,b] and [c,d] overlap if b >= c and d >= a
 * 
 * ALGORITHM STEPS:
 * 1. Add intervals ending before newInterval.start
 * 2. Merge overlapping intervals:
 *    - Update start to min(interval.start, newInterval.start)
 *    - Update end to max(interval.end, newInterval.end)
 * 3. Add merged interval
 * 4. Add remaining intervals
 * 
 * VISUAL EXAMPLE:
 * intervals = [[1,3],[6,9]], newInterval = [2,5]
 * 
 * Phase 1 (before new): 
 * [1,3] ends at 3, newInterval starts at 2 → overlap
 * 
 * Phase 2 (merge):
 * [1,3] overlaps [2,5] → merge to [1,5]
 * [6,9] starts at 6 > 5 → no overlap
 * 
 * Phase 3 (after new):
 * Add [6,9]
 * 
 * Result: [[1,5],[6,9]] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Three-phase (used here): O(n) time, O(n) space - OPTIMAL
 * 2. Binary search + merge: O(n) time - Same complexity
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - Single pass through intervals: O(n)
 * - Each interval processed once
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result list: O(n) in worst case
 * 
 * ============================================================================
 */

package greedy.mediumhard

class InsertInterval {
    
    /**
     * Inserts new interval and merges overlapping intervals
     */
    fun insert(intervals: Array<IntArray>, newInterval: IntArray): Array<IntArray> {
        val result = mutableListOf<IntArray>()
        var i = 0
        val n = intervals.size
        
        // Phase 1: Add all intervals ending before newInterval starts
        while (i < n && intervals[i][1] < newInterval[0]) {
            result.add(intervals[i])
            i++
        }
        
        // Phase 2: Merge overlapping intervals
        var mergedStart = newInterval[0]
        var mergedEnd = newInterval[1]
        
        while (i < n && intervals[i][0] <= newInterval[1]) {
            mergedStart = minOf(mergedStart, intervals[i][0])
            mergedEnd = maxOf(mergedEnd, intervals[i][1])
            i++
        }
        
        result.add(intArrayOf(mergedStart, mergedEnd))
        
        // Phase 3: Add remaining intervals
        while (i < n) {
            result.add(intervals[i])
            i++
        }
        
        return result.toTypedArray()
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty intervals: [], new=[5,7] → [[5,7]]
 * 2. No overlap: [[1,2],[3,5]], new=[6,8] → [[1,2],[3,5],[6,8]]
 * 3. Insert at start: [[3,5],[6,9]], new=[1,2] → [[1,2],[3,5],[6,9]]
 * 4. Complete overlap: [[1,5]], new=[2,3] → [[1,5]]
 * 5. Merge all: [[1,2],[3,5],[6,7]], new=[1,10] → [[1,10]]
 * 6. Touch boundary: [[1,5]], new=[5,7] → [[1,7]]
 * 
 * APPLICATIONS:
 * - Calendar event scheduling
 * - Resource booking systems
 * - Timeline management
 * 
 * ============================================================================
 */

fun main() {
    val solution = InsertInterval()
    
    println("Insert Interval - Test Cases")
    println("==============================\n")
    
    println("Test 1: [[1,3],[6,9]], new=[2,5]")
    val result1 = solution.insert(arrayOf(intArrayOf(1,3), intArrayOf(6,9)), intArrayOf(2,5))
    println("Result: ${result1.map { it.toList() }}")
    println("Expected: [[1,5],[6,9]] ✓\n")
    
    println("Test 2: [[1,2],[3,5],[6,7],[8,10],[12,16]], new=[4,8]")
    val result2 = solution.insert(
        arrayOf(intArrayOf(1,2), intArrayOf(3,5), intArrayOf(6,7), 
                intArrayOf(8,10), intArrayOf(12,16)), 
        intArrayOf(4,8)
    )
    println("Result: ${result2.map { it.toList() }}")
    println("Expected: [[1,2],[3,10],[12,16]] ✓\n")
}
