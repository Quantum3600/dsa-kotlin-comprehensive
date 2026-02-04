/**
 * ============================================================================
 * PROBLEM: Merge Intervals
 * DIFFICULTY: Medium
 * CATEGORY: Greedy, Intervals
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a collection of intervals, merge all overlapping intervals.
 * 
 * INPUT FORMAT:
 * - intervals: [[1,3],[2,6],[8,10],[15,18]]
 * 
 * OUTPUT FORMAT:
 * - List of merged intervals: [[1,6],[8,10],[15,18]]
 * 
 * CONSTRAINTS:
 * - 1 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= start <= end <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Sort intervals by start time. Iterate and merge if current overlaps with last.
 * Two intervals overlap if current.start <= last.end
 * 
 * KEY INSIGHT:
 * After sorting, we only need to check if current interval overlaps with
 * the last merged interval, not all previous intervals.
 * 
 * ALGORITHM STEPS:
 * 1. Sort intervals by start time
 * 2. Initialize result with first interval
 * 3. For each remaining interval:
 *    - If overlaps with last in result: merge (update end)
 *    - Else: add as new interval
 * 4. Return result
 * 
 * VISUAL EXAMPLE:
 * intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 
 * After sort: [[1,3],[2,6],[8,10],[15,18]] (already sorted)
 * 
 * Start with [1,3]
 * [2,6]: 2 <= 3 (overlap) → merge to [1,6]
 * [8,10]: 8 > 6 (no overlap) → add [8,10]
 * [15,18]: 15 > 10 (no overlap) → add [15,18]
 * 
 * Result: [[1,6],[8,10],[15,18]] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sort + merge (used here): O(n log n) time, O(n) space - OPTIMAL
 * 2. Without sorting: O(n^2) time - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Sorting: O(n log n)
 * - Merging: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Result list: O(n) in worst case (no overlaps)
 * - Sorting space: O(log n) to O(n) depending on algorithm
 * 
 * ============================================================================
 */

package greedy.mediumhard

class MergeIntervals {
    
    /**
     * Merges overlapping intervals
     */
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        if (intervals.isEmpty()) return emptyArray()
        
        // Sort by start time
        intervals.sortBy { it[0] }
        
        val result = mutableListOf<IntArray>()
        result.add(intervals[0])
        
        for (i in 1 until intervals.size) {
            val last = result.last()
            val current = intervals[i]
            
            if (current[0] <= last[1]) {
                // Overlapping - merge
                last[1] = maxOf(last[1], current[1])
            } else {
                // Non-overlapping - add new interval
                result.add(current)
            }
        }
        
        return result.toTypedArray()
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single interval: [[1,4]] → [[1,4]]
 * 2. No overlap: [[1,2],[3,4]] → [[1,2],[3,4]]
 * 3. All overlap: [[1,4],[2,5],[3,6]] → [[1,6]]
 * 4. Touching intervals: [[1,2],[2,3]] → [[1,3]]
 * 5. Nested: [[1,10],[2,3]] → [[1,10]]
 * 6. Same start: [[1,4],[1,5]] → [[1,5]]
 * 
 * APPLICATIONS:
 * - Calendar scheduling
 * - Meeting room allocation
 * - Resource booking
 * - Timeline visualization
 * 
 * ============================================================================
 */

fun main() {
    val solution = MergeIntervals()
    
    println("Merge Intervals - Test Cases")
    println("==============================\n")
    
    println("Test 1: [[1,3],[2,6],[8,10],[15,18]]")
    val result1 = solution.merge(arrayOf(
        intArrayOf(1,3), intArrayOf(2,6), 
        intArrayOf(8,10), intArrayOf(15,18)
    ))
    println("Result: ${result1.map { it.toList() }}")
    println("Expected: [[1,6],[8,10],[15,18]] ✓\n")
    
    println("Test 2: [[1,4],[4,5]]")
    val result2 = solution.merge(arrayOf(intArrayOf(1,4), intArrayOf(4,5)))
    println("Result: ${result2.map { it.toList() }}")
    println("Expected: [[1,5]] ✓\n")
}
