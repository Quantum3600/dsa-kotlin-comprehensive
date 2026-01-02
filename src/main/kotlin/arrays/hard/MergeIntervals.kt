/**
 * ============================================================================
 * PROBLEM: Merge Intervals
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of intervals where intervals[i] = [starti, endi], merge all
 * overlapping intervals and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 * 
 * INPUT FORMAT:
 * - Array of intervals: [[1,3], [2,6], [8,10], [15,18]]
 * 
 * OUTPUT FORMAT:
 * - Array of merged intervals: [[1,6], [8,10], [15,18]]
 * 
 * CONSTRAINTS:
 * - 1 <= intervals.length <= 10^4
 * - intervals[i].length == 2
 * - 0 <= starti <= endi <= 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * If we sort intervals by start time, we can merge them in one pass.
 * Two intervals overlap if: interval1.end >= interval2.start
 * 
 * KEY INSIGHT:
 * After sorting by start time:
 * - If current interval overlaps with previous, merge them
 * - Otherwise, add previous to result and start new interval
 * 
 * ALGORITHM STEPS:
 * 1. Sort intervals by start time
 * 2. Initialize result with first interval
 * 3. For each subsequent interval:
 *    - If overlaps with last in result: merge by updating end
 *    - Otherwise: add current interval to result
 * 
 * VISUAL EXAMPLE:
 * intervals = [[1,3], [2,6], [8,10], [15,18]]
 * 
 * After sort: [[1,3], [2,6], [8,10], [15,18]]
 * 
 * Start: result = [[1,3]]
 * 
 * Process [2,6]:
 *   [1,3] and [2,6] overlap (3 >= 2)
 *   Merge: [1, max(3,6)] = [1,6]
 *   result = [[1,6]]
 * 
 * Process [8,10]:
 *   [1,6] and [8,10] don't overlap (6 < 8)
 *   Add [8,10]
 *   result = [[1,6], [8,10]]
 * 
 * Process [15,18]:
 *   [8,10] and [15,18] don't overlap (10 < 15)
 *   Add [15,18]
 *   result = [[1,6], [8,10], [15,18]]
 * 
 * COMPLEXITY:
 * Time: O(n log n) - sorting dominates
 * Space: O(n) - for result array
 * 
 * ============================================================================
 */

package arrays.hard

class MergeIntervals {
    
    /**
     * Merges all overlapping intervals
     * 
     * @param intervals Array of intervals [start, end]
     * @return Array of merged intervals
     */
    fun merge(intervals: Array<IntArray>): Array<IntArray> {
        if (intervals.isEmpty()) return emptyArray()
        
        // Sort by start time
        intervals.sortBy { it[0] }
        
        val result = mutableListOf<IntArray>()
        result.add(intervals[0])
        
        for (i in 1 until intervals.size) {
            val current = intervals[i]
            val last = result.last()
            
            if (current[0] <= last[1]) {
                // Intervals overlap, merge them
                last[1] = maxOf(last[1], current[1])
            } else {
                // No overlap, add current interval
                result.add(current)
            }
        }
        
        return result.toTypedArray()
    }
    
    /**
     * Alternative using functional style
     */
    fun mergeFunctional(intervals: Array<IntArray>): Array<IntArray> {
        if (intervals.isEmpty()) return emptyArray()
        
        return intervals
            .sortedBy { it[0] }
            .fold(mutableListOf<IntArray>()) { acc, interval ->
                if (acc.isEmpty() || acc.last()[1] < interval[0]) {
                    acc.add(interval)
                } else {
                    acc.last()[1] = maxOf(acc.last()[1], interval[1])
                }
                acc
            }
            .toTypedArray()
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single interval: [[1,3]] → [[1,3]]
 * 2. No overlap: [[1,2], [3,4]] → [[1,2], [3,4]]
 * 3. All overlap: [[1,4], [2,3]] → [[1,4]]
 * 4. Adjacent intervals: [[1,2], [2,3]] → [[1,3]]
 * 5. Nested intervals: [[1,10], [2,3], [4,5]] → [[1,10]]
 * 6. Identical intervals: [[1,3], [1,3]] → [[1,3]]
 * 7. Reverse order input: [[8,10], [1,3]] → [[1,3], [8,10]]
 * 
 * APPLICATIONS:
 * 1. Meeting room scheduling
 * 2. Calendar event merging
 * 3. Resource allocation time slots
 * 4. Network packet merging
 * 5. Data compression (run-length encoding)
 * 6. Range query optimization
 * 7. Video timeline editing
 * 
 * ============================================================================
 */

fun main() {
    val solution = MergeIntervals()
    
    println("=== Merge Intervals Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val intervals1 = arrayOf(
        intArrayOf(1, 3),
        intArrayOf(2, 6),
        intArrayOf(8, 10),
        intArrayOf(15, 18)
    )
    println("Input: ${intervals1.map { it.contentToString() }}")
    val result1 = solution.merge(intervals1)
    println("Result: ${result1.map { it.contentToString() }}")
    println("Expected: [[1, 6], [8, 10], [15, 18]]\n")
    
    // Test 2: All overlap
    println("Test 2: All overlap")
    val intervals2 = arrayOf(
        intArrayOf(1, 4),
        intArrayOf(4, 5)
    )
    println("Input: ${intervals2.map { it.contentToString() }}")
    val result2 = solution.merge(intervals2)
    println("Result: ${result2.map { it.contentToString() }}")
    println("Expected: [[1, 5]]\n")
    
    // Test 3: No overlap
    println("Test 3: No overlap")
    val intervals3 = arrayOf(
        intArrayOf(1, 2),
        intArrayOf(3, 4),
        intArrayOf(5, 6)
    )
    println("Input: ${intervals3.map { it.contentToString() }}")
    val result3 = solution.merge(intervals3)
    println("Result: ${result3.map { it.contentToString() }}")
    println("Expected: [[1, 2], [3, 4], [5, 6]]\n")
    
    // Test 4: Nested intervals
    println("Test 4: Nested intervals")
    val intervals4 = arrayOf(
        intArrayOf(1, 10),
        intArrayOf(2, 3),
        intArrayOf(4, 5),
        intArrayOf(6, 7)
    )
    println("Input: ${intervals4.map { it.contentToString() }}")
    val result4 = solution.merge(intervals4)
    println("Result: ${result4.map { it.contentToString() }}")
    println("Expected: [[1, 10]]\n")
    
    // Test 5: Unsorted input
    println("Test 5: Unsorted input")
    val intervals5 = arrayOf(
        intArrayOf(8, 10),
        intArrayOf(1, 3),
        intArrayOf(2, 6)
    )
    println("Input: ${intervals5.map { it.contentToString() }}")
    val result5 = solution.merge(intervals5)
    println("Result: ${result5.map { it.contentToString() }}")
    println("Expected: [[1, 6], [8, 10]]\n")
    
    println("All tests completed!")
}
