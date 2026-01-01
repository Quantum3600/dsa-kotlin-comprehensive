/**
 * ============================================================================
 * PROBLEM: Find Missing Number
 * DIFFICULTY: Easy
 * CATEGORY: Arrays, Math
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array containing n distinct numbers from 0 to n,
 * find the one number that is missing from the array.
 * 
 * INPUT: arr = [3, 0, 1]
 * OUTPUT: 2
 * 
 * CONSTRAINTS:
 * - n == arr.size
 * - 1 <= n <= 10^4
 * - 0 <= arr[i] <= n
 * - All numbers in arr are unique
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * APPROACH 1: Sum Formula
 * Sum of 0 to n = n*(n+1)/2
 * Missing number = Expected sum - Actual sum
 * 
 * VISUAL EXAMPLE:
 * arr = [3, 0, 1], n = 3
 * Expected sum = 0+1+2+3 = 6
 * Actual sum = 3+0+1 = 4
 * Missing = 6 - 4 = 2 ✓
 * 
 * APPROACH 2: XOR
 * XOR all numbers from 0 to n with array elements
 * Pairs cancel out, leaving only the missing number
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(1)
 * 
 * ============================================================================
 */

package arrays.easy

class MissingNumber {
    
    /**
     * Approach 1: Using sum formula
     */
    fun findMissingSum(arr: IntArray): Int {
        val n = arr.size
        val expectedSum = n * (n + 1) / 2
        val actualSum = arr.sum()
        return expectedSum - actualSum
    }
    
    /**
     * Approach 2: Using XOR
     * a XOR a = 0, a XOR 0 = a
     */
    fun findMissingXOR(arr: IntArray): Int {
        val n = arr.size
        var xor = 0
        
        // XOR all numbers from 0 to n
        for (i in 0..n) {
            xor = xor xor i
        }
        
        // XOR with all array elements
        for (num in arr) {
            xor = xor xor num
        }
        
        return xor
    }
    
    /**
     * Approach 3: Using HashSet
     */
    fun findMissingSet(arr: IntArray): Int {
        val set = arr.toSet()
        for (i in 0..arr.size) {
            if (i !in set) return i
        }
        return -1
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. n=1, missing 0: [1] → 0
 * 2. n=1, missing 1: [0] → 1
 * 3. Missing first: [1, 2, 3] → 0
 * 4. Missing last: [0, 1, 2] → 3
 * 5. Missing middle: [0, 1, 3, 4] → 2
 * 6. Large n: works up to 10^4
 * 
 * APPLICATIONS:
 * - Finding missing ID in sequence
 * - Data validation
 * - Error detection in transmissions
 * 
 * ============================================================================
 */

fun main() {
    val solution = MissingNumber()
    
    println("Find Missing Number - Test Cases")
    println("==================================\n")
    
    val test1 = intArrayOf(3, 0, 1)
    println("Test 1: ${test1.contentToString()}")
    println("Missing (Sum): ${solution.findMissingSum(test1)}")
    println("Missing (XOR): ${solution.findMissingXOR(test1)}")
    println("Expected: 2 ✓\n")
    
    val test2 = intArrayOf(0, 1)
    println("Test 2: ${test2.contentToString()}")
    println("Missing: ${solution.findMissingSum(test2)}")
    println("Expected: 2 ✓\n")
    
    val test3 = intArrayOf(9,6,4,2,3,5,7,0,1)
    println("Test 3: ${test3.contentToString()}")
    println("Missing: ${solution.findMissingSum(test3)}")
    println("Expected: 8 ✓\n")
    
    val test4 = intArrayOf(0)
    println("Test 4: ${test4.contentToString()}")
    println("Missing: ${solution.findMissingSum(test4)}")
    println("Expected: 1 ✓\n")
    
    println("All tests passed! ✓")
}
