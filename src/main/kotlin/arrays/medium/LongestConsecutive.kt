/**
 * ===================================================================
 * PROBLEM: Longest Consecutive Sequence
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, HashSet
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the length of the longest consecutive elements sequence.
 * Must run in O(n) time.
 * 
 * INPUT: arr = [100, 4, 200, 1, 3, 2]
 * OUTPUT: 4 (sequence [1, 2, 3, 4])
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use HashSet:
 * 1. Add all elements to set
 * 2. For each element, check if it's start of sequence (num-1 not in set)
 * 3. If start, count consecutive numbers
 * 
 * VISUAL EXAMPLE:
 * arr = [100, 4, 200, 1, 3, 2]
 * set = {100, 4, 200, 1, 3, 2}
 * 
 * Check 100: 99 not in set → start of sequence
 *   100 → length 1
 * 
 * Check 4: 3 in set → not start, skip
 * 
 * Check 1: 0 not in set → start of sequence
 *   1,2,3,4 → length 4 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - each element checked at most twice
 * Space: O(n) - HashSet
 * 
 * ===================================================================
 */

package arrays.medium

class LongestConsecutive {
    
    fun longestConsecutive(arr: IntArray): Int {
        if (arr.isEmpty()) return 0
        
        val numSet = arr.toSet()
        var maxLength = 0
        
        for (num in numSet) {
            // Only start counting if num is start of sequence
            if (num - 1 !in numSet) {
                var currentNum = num
                var currentLength = 1
                
                // Count consecutive numbers
                while (currentNum + 1 in numSet) {
                    currentNum++
                    currentLength++
                }
                
                maxLength = maxOf(maxLength, currentLength)
            }
        }
        
        return maxLength
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Empty array: [] → 0
 * 2. Single element: [1] → 1
 * 3. No consecutive: [1, 3, 5] → 1
 * 4. All consecutive: [1,2,3,4] → 4
 * 5. Duplicates: [1,2,2,3] → 3
 * 6. Negative numbers: [-1,0,1] → 3
 * 
 * APPLICATIONS:
 * - Finding streaks in data
 * - Database query optimization
 * - Range queries
 * 
 * ===================================================================
 */

fun main() {
    val solution = LongestConsecutive()
    
    println("Longest Consecutive Sequence - Test Cases")
    println("===========================================\n")
    
    println("Test 1: [100,4,200,1,3,2]")
    println("Result: ${solution.longestConsecutive(intArrayOf(100,4,200,1,3,2))}")
    println("Expected: 4 ✓\n")
    
    println("Test 2: [0,3,7,2,5,8,4,6,0,1]")
    println("Result: ${solution.longestConsecutive(intArrayOf(0,3,7,2,5,8,4,6,0,1))}")
    println("Expected: 9 ✓\n")
    
    println("All tests passed! ✓")
}
