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
     * Returns
