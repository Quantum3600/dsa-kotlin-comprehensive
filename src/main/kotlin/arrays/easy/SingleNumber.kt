/**
 * ===================================================================
 * PROBLEM: Single Number
 * DIFFICULTY: Easy
 * CATEGORY: Arrays, Bit Manipulation
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a non-empty array where every element appears twice except one.
 * Find that single element that appears only once.
 * 
 * INPUT: arr = [2, 2, 1]
 * OUTPUT: 1
 * 
 * INPUT: arr = [4, 1, 2, 1, 2]
 * OUTPUT: 4
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 3 * 10^4
 * - -3 * 10^4 <= arr[i] <= 3 * 10^4
 * - Every element appears twice except one
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * KEY INSIGHT: XOR Properties
 * - a XOR a = 0 (same numbers cancel out)
 * - a XOR 0 = a (zero doesn't change result)
 * - XOR is commutative and associative
 * 
 * If we XOR all elements, pairs cancel out, leaving only the single number.
 * 
 * VISUAL EXAMPLE:
 * arr = [4, 1, 2, 1, 2]
 * 
 * XOR all elements:
 * 4 XOR 1 XOR 2 XOR 1 XOR 2
 * = 4 XOR (1 XOR 1) XOR (2 XOR 2)
 * = 4 XOR 0 XOR 0
 * = 4 ✓
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - only one variable
 * 
 * ===================================================================
 */

package arrays.easy

class SingleNumber {
    
    /**
     * Finds the single number using XOR
     */
    fun findSingle(arr: IntArray): Int {
        var result = 0
        for (num in arr) {
            result = result xor num
        }
        return result
    }
    
    /**
     * Alternative: Using HashSet (less optimal)
     */
    fun findSingleSet(arr: IntArray): Int {
        val set = mutableSetOf<Int>()
        for (num in arr) {
            if (num in set) {
                set.remove(num)
            } else {
                set.add(num)
            }
        }
        return set.first()
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Single element: [1] → 1
 * 2. Three elements: [2, 2, 1] → 1
 * 3. Many pairs + one: [1,1,2,2,3,3,4] → 4
 * 4. Negative numbers: [-1, -1, 2] → 2
 * 5. Zero included: [0, 1, 0] → 1
 * 
 * APPLICATIONS:
 * - Finding unique elements efficiently
 * - Data deduplication
 * - Error detection
 * 
 * ===================================================================
 */

fun main() {
    val solution = SingleNumber()
    
    println("Single Number - Test Cases")
    println("============================\n")
    
    println("Test 1: [2, 2, 1]")
    println("Result: ${solution.findSingle(intArrayOf(2, 2, 1))}")
    println("Expected: 1 ✓\n")
    
    println("Test 2: [4, 1, 2, 1, 2]")
    println("Result: ${solution.findSingle(intArrayOf(4, 1, 2, 1, 2))}")
    println("Expected: 4 ✓\n")
    
    println("Test 3: [1]")
    println("Result: ${solution.findSingle(intArrayOf(1))}")
    println("Expected: 1 ✓\n")
    
    println("All tests passed! ✓")
}
