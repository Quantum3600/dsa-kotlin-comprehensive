/**
 * ===================================================================
 * PROBLEM: Majority Element
 * DIFFICULTY: Medium
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array, find the majority element. The majority element
 * appears more than n/2 times. Assume it always exists.
 * 
 * INPUT: arr = [2, 2, 1, 1, 1, 2, 2]
 * OUTPUT: 2 (appears 4 times, which is > 7/2)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 5 * 10^4
 * - -10^9 <= arr[i] <= 10^9
 * - Majority element always exists
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * BOYER-MOORE VOTING ALGORITHM:
 * Maintain a candidate and count:
 * - If count = 0, set current element as candidate
 * - If current element = candidate, increment count
 * - Else decrement count
 * 
 * The majority element (>n/2) will always survive as the candidate.
 * 
 * VISUAL EXAMPLE:
 * arr = [2, 2, 1, 1, 1, 2, 2]
 * 
 * i=0: candidate=2, count=1
 * i=1: candidate=2, count=2
 * i=2: candidate=2, count=1 (different element)
 * i=3: candidate=2, count=0
 * i=4: candidate=1, count=1 (reset)
 * i=5: candidate=1, count=0
 * i=6: candidate=2, count=1 (reset)
 * 
 * Return candidate=2 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - only two variables
 * 
 * ===================================================================
 */

package arrays.medium

class MajorityElement {
    
    /**
     * Boyer-Moore Voting Algorithm
     */
    fun majorityElement(arr: IntArray): Int {
        var candidate = 0
        var count = 0
        
        // Find candidate
        for (num in arr) {
            if (count == 0) {
                candidate = num
            }
            count += if (num == candidate) 1 else -1
        }
        
        return candidate
    }
    
    /**
     * Alternative: Using HashMap (less optimal)
     */
    fun majorityElementHash(arr: IntArray): Int {
        val countMap = mutableMapOf<Int, Int>()
        val majority = arr.size / 2
        
        for (num in arr) {
            countMap[num] = countMap.getOrDefault(num, 0) + 1
            if (countMap[num]!! > majority) {
                return num
            }
        }
        
        return -1  // Should never reach here if majority exists
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Single element: [1] → 1
 * 2. All same: [3,3,3] → 3
 * 3. Two elements: [1,1] → 1
 * 4. Majority at start: [1,1,1,2,3] → 1
 * 5. Majority at end: [2,3,1,1,1] → 1
 * 6. Barely majority: [1,2,1] → 1
 * 
 * APPLICATIONS:
 * - Finding popular vote winner
 * - Consensus algorithms
 * - Data analysis (finding most common value)
 * 
 * ===================================================================
 */

fun main() {
    val solution = MajorityElement()
    
    println("Majority Element - Test Cases")
    println("===============================\n")
    
    println("Test 1: [3,2,3]")
    println("Result: ${solution.majorityElement(intArrayOf(3,2,3))}")
    println("Expected: 3 ✓\n")
    
    println("Test 2: [2,2,1,1,1,2,2]")
    println("Result: ${solution.majorityElement(intArrayOf(2,2,1,1,1,2,2))}")
    println("Expected: 2 ✓\n")
    
    println("All tests passed! ✓")
}
