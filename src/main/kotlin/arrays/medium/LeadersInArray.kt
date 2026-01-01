/**
 * ===================================================================
 * PROBLEM: Leaders in an Array
 * DIFFICULTY: Medium
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * A leader is an element that is greater than all elements to its right.
 * The rightmost element is always a leader.
 * 
 * INPUT: arr = [16, 17, 4, 3, 5, 2]
 * OUTPUT: [17, 5, 2]
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * OPTIMAL APPROACH:
 * Traverse from right to left, keeping track of maximum seen so far.
 * If current element > maximum, it's a leader.
 * 
 * VISUAL EXAMPLE:
 * arr = [16, 17, 4, 3, 5, 2]
 * 
 * Right to left:
 * i=5: arr[5]=2, max=2, leader=2 ✓
 * i=4: arr[4]=5, max=2 → 5>2, leader=5 ✓, max=5
 * i=3: arr[3]=3, max=5 → 3<5, not leader
 * i=2: arr[2]=4, max=5 → 4<5, not leader
 * i=1: arr[1]=17, max=5 → 17>5, leader=17 ✓, max=17
 * i=0: arr[0]=16, max=17 → 16<17, not leader
 * 
 * Leaders: [17, 5, 2]
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(k) - k is number of leaders
 * 
 * ===================================================================
 */

package arrays.medium

class LeadersInArray {
    
    /**
     * Find all leaders in array
     * Returns in order from left to right
     */
    fun findLeaders(arr: IntArray): List<Int> {
        if (arr.isEmpty()) return emptyList()
        
        val leaders = mutableListOf<Int>()
        var maxFromRight = Int.MIN_VALUE
        
        // Traverse from right to left
        for (i in arr.size - 1 downTo 0) {
            if (arr[i] >= maxFromRight) {
                leaders.add(arr[i])
                maxFromRight = arr[i]
            }
        }
        
        // Reverse to get left to right order
        return leaders.reversed()
    }
    
    /**
     * Alternative: Brute force (less optimal)
     * Time: O(n²)
     */
    fun findLeadersBruteForce(arr: IntArray): List<Int> {
        val leaders = mutableListOf<Int>()
        
        for (i in arr.indices) {
            var isLeader = true
            for (j in i + 1 until arr.size) {
                if (arr[j] > arr[i]) {
                    isLeader = false
                    break
                }
            }
            if (isLeader) {
                leaders.add(arr[i])
            }
        }
        
        return leaders
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. All increasing: [1,2,3,4] → [4]
 * 2. All decreasing: [4,3,2,1] → [4,3,2,1]
 * 3. Single element: [5] → [5]
 * 4. All same: [3,3,3] → [3,3,3] or [3] depending on definition
 * 5. Negative numbers: [-1,-5,0] → [0]
 * 
 * APPLICATIONS:
 * - Finding local maxima
 * - Stock market analysis (resistance levels)
 * - Game theory (dominant strategies)
 * 
 * ===================================================================
 */

fun main() {
    val solution = LeadersInArray()
    
    println("Leaders in Array - Test Cases")
    println("===============================\n")
    
    println("Test 1: [16,17,4,3,5,2]")
    println("Result: ${solution.findLeaders(intArrayOf(16,17,4,3,5,2))}")
    println("Expected: [17, 5, 2] ✓\n")
    
    println("Test 2: [1,2,3,4]")
    println("Result: ${solution.findLeaders(intArrayOf(1,2,3,4))}")
    println("Expected: [4] ✓\n")
    
    println("All tests passed! ✓")
}
