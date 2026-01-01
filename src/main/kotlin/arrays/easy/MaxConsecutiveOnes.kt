/**
 * ===================================================================
 * PROBLEM: Max Consecutive Ones
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a binary array, find the maximum number of consecutive 1s.
 * 
 * INPUT: arr = [1, 1, 0, 1, 1, 1]
 * OUTPUT: 3 (three consecutive 1s)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - arr[i] is either 0 or 1
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Track two variables:
 * - current: current streak of consecutive 1s
 * - max: maximum streak seen so far
 * 
 * For each element:
 * - If 1: increment current streak
 * - If 0: update max if needed, reset current
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 1, 0, 1, 1, 1]
 *        ↓
 * i=0: arr[0]=1 → current=1, max=1
 *           ↓
 * i=1: arr[1]=1 → current=2, max=2
 *              ↓
 * i=2: arr[2]=0 → current=0, max=2
 *                 ↓
 * i=3: arr[3]=1 → current=1, max=2
 *                    ↓
 * i=4: arr[4]=1 → current=2, max=2
 *                       ↓
 * i=5: arr[5]=1 → current=3, max=3 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - only two variables
 * 
 * ===================================================================
 */

package arrays.easy

class MaxConsecutiveOnes {
    
    fun findMaxConsecutiveOnes(arr: IntArray): Int {
        var current = 0
        var max = 0
        
        for (num in arr) {
            if (num == 1) {
                current++
                max = maxOf(max, current)
            } else {
                current = 0
            }
        }
        
        return max
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All ones: [1, 1, 1] → 3
 * 2. All zeros: [0, 0, 0] → 0
 * 3. Single one: [1] → 1
 * 4. Single zero: [0] → 0
 * 5. Ones at start: [1, 1, 0, 0] → 2
 * 6. Ones at end: [0, 0, 1, 1] → 2
 * 7. Alternating: [1, 0, 1, 0] → 1
 * 
 * APPLICATIONS:
 * - Finding longest winning streak
 * - Analyzing uptime/downtime
 * - Pattern recognition in binary data
 * 
 * ===================================================================
 */

fun main() {
    val solution = MaxConsecutiveOnes()
    
    println("Max Consecutive Ones - Test Cases")
    println("===================================\n")
    
    println("Test 1: [1,1,0,1,1,1]")
    println("Result: ${solution.findMaxConsecutiveOnes(intArrayOf(1,1,0,1,1,1))}")
    println("Expected: 3 ✓\n")
    
    println("Test 2: [1,0,1,1,0,1]")
    println("Result: ${solution.findMaxConsecutiveOnes(intArrayOf(1,0,1,1,0,1))}")
    println("Expected: 2 ✓\n")
    
    println("Test 3: [1,1,1,1]")
    println("Result: ${solution.findMaxConsecutiveOnes(intArrayOf(1,1,1,1))}")
    println("Expected: 4 ✓\n")
    
    println("All tests passed! ✓")
}
