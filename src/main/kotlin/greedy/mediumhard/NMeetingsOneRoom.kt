/**
 * ============================================================================
 * PROBLEM: N Meetings in One Room
 * DIFFICULTY: Medium
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given start and end times of n meetings, find maximum number of meetings
 * that can be accommodated in one meeting room. A meeting room can hold
 * only one meeting at a time.
 * 
 * INPUT FORMAT:
 * - start: [1, 3, 0, 5, 8, 5] (start times)
 * - end: [2, 4, 6, 7, 9, 9] (end times)
 * 
 * OUTPUT FORMAT:
 * - Integer: maximum meetings (4)
 * - Optional: List of meeting indices
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 0 <= start[i] < end[i] <= 10^5
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - always pick meeting that ends earliest.
 * This leaves maximum time for subsequent meetings.
 * 
 * KEY INSIGHT:
 * Activity selection problem. Sort by end time and greedily pick
 * non-overlapping meetings.
 * 
 * ALGORITHM STEPS:
 * 1. Create meeting objects with (start, end, index)
 * 2. Sort meetings by end time
 * 3. Pick first meeting
 * 4. For remaining meetings:
 *    - If start >= last end time: pick this meeting
 *    - Update last end time
 * 5. Return count and indices
 * 
 * VISUAL EXAMPLE:
 * start = [1, 3, 0, 5, 8, 5]
 * end =   [2, 4, 6, 7, 9, 9]
 * 
 * Meetings with indices:
 * (1,2,0), (3,4,1), (0,6,2), (5,7,3), (8,9,4), (5,9,5)
 * 
 * After sort by end time:
 * (1,2,0), (3,4,1), (0,6,2), (5,7,3), (8,9,4), (5,9,5)
 * 
 * Pick (1,2,0): lastEnd=2
 * (3,4,1): 3>=2 ✓ pick, lastEnd=4
 * (0,6,2): 0<4 ✗ skip (overlaps)
 * (5,7,3): 5>=4 ✓ pick, lastEnd=7
 * (8,9,4): 8>=7 ✓ pick, lastEnd=9
 * (5,9,5): 5<9 ✗ skip (overlaps)
 * 
 * Result: 4 meetings [0,1,3,4] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sort by end time (used here): O(n log n) - OPTIMAL
 * 2. Dynamic Programming: O(n^2) - Not optimal
 * 3. Brute force: O(2^n) - Not feasible
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Creating meetings: O(n)
 * - Sorting: O(n log n)
 * - Selecting meetings: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Storing meetings: O(n)
 * - Result list: O(k) where k <= n
 * 
 * ============================================================================
 */

package greedy.mediumhard

class NMeetingsOneRoom {
    
    data class Meeting(val start: Int, val end: Int, val index: Int)
    
    /**
     * Returns maximum number of meetings possible
     */
    fun maxMeetings(start: IntArray, end: IntArray): Int {
        val n = start.size
        val meetings = mutableListOf<Meeting>()
        
        // Create meeting objects
        for (i in 0 until n) {
            meetings.add(Meeting(start[i], end[i], i))
        }
        
        // Sort by end time
        meetings.sortBy { it.end }
        
        var count = 1
        var lastEnd = meetings[0].end
        
        for (i in 1 until n) {
            if (meetings[i].start >= lastEnd) {
                count++
                lastEnd = meetings[i].end
            }
        }
        
        return count
    }
    
    /**
     * Returns list of meeting indices that can be scheduled
     */
    fun maxMeetingsIndices(start: IntArray, end: IntArray): List<Int> {
        val n = start.size
        val meetings = mutableListOf<Meeting>()
        
        for (i in 0 until n) {
            meetings.add(Meeting(start[i], end[i], i + 1)) // 1-indexed
        }
        
        meetings.sortBy { it.end }
        
        val result = mutableListOf<Int>()
        result.add(meetings[0].index)
        var lastEnd = meetings[0].end
        
        for (i in 1 until n) {
            if (meetings[i].start >= lastEnd) {
                result.add(meetings[i].index)
                lastEnd = meetings[i].end
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single meeting: start=[1], end=[2] → 1
 * 2. No overlap: start=[1,2,3], end=[2,3,4] → 3
 * 3. All overlap: start=[1,1,1], end=[2,2,2] → 1
 * 4. Nested: start=[1,2], end=[10,3] → 2 (pick shorter)
 * 5. Same start: start=[1,1], end=[2,3] → 2
 * 6. Same end: start=[1,2], end=[3,3] → 2
 * 
 * APPLICATIONS:
 * - Meeting room scheduling
 * - Activity selection
 * - Resource allocation
 * - Event planning
 * 
 * ============================================================================
 */

fun main() {
    val solution = NMeetingsOneRoom()
    
    println("N Meetings in One Room - Test Cases")
    println("=====================================\n")
    
    println("Test 1: start=[1,3,0,5,8,5], end=[2,4,6,7,9,9]")
    println("Count: ${solution.maxMeetings(
        intArrayOf(1, 3, 0, 5, 8, 5),
        intArrayOf(2, 4, 6, 7, 9, 9)
    )}")
    println("Meetings: ${solution.maxMeetingsIndices(
        intArrayOf(1, 3, 0, 5, 8, 5),
        intArrayOf(2, 4, 6, 7, 9, 9)
    )}")
    println("Expected: 4 meetings ✓\n")
    
    println("Test 2: start=[10,12,20], end=[20,25,30]")
    println("Count: ${solution.maxMeetings(
        intArrayOf(10, 12, 20),
        intArrayOf(20, 25, 30)
    )}")
    println("Expected: 2 meetings ✓\n")
}
