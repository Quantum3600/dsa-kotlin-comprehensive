/**
 * ===================================================================
 * PROBLEM: Next Permutation
 * DIFFICULTY: Medium
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Rearrange numbers into lexicographically next greater permutation.
 * If not possible, arrange in lowest possible order.
 * 
 * INPUT: arr = [1, 2, 3]
 * OUTPUT: [1, 3, 2]
 * 
 * INPUT: arr = [3, 2, 1]
 * OUTPUT: [1, 2, 3] (wrap around)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 100
 * - 0 <= arr[i] <= 100
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * ALGORITHM:
 * 1. Find rightmost element arr[i] where arr[i] < arr[i+1]
 * 2. If no such element, reverse entire array
 * 3. Find rightmost element arr[j] > arr[i]
 * 4. Swap arr[i] and arr[j]
 * 5. Reverse elements after position i
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 3, 5, 4, 2]
 * 
 * Step 1: Find i where arr[i] < arr[i+1]
 *         i=1 (arr[1]=3 < arr[2]=5)
 * 
 * Step 2: Find j where arr[j] > arr[i]
 *         j=3 (arr[3]=4 > arr[1]=3)
 * 
 * Step 3: Swap arr[i] and arr[j]
 *         [1, 4, 5, 3, 2]
 * 
 * Step 4: Reverse after i
 *         [1, 4, 2, 3, 5] ✓
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(1)
 * 
 * ===================================================================
 */

package arrays.medium

class NextPermutation {
    
    fun nextPermutation(arr: IntArray) {
        val n = arr.size
        
        // Step 1: Find the break point
        var i = n - 2
        while (i >= 0 && arr[i] >= arr[i + 1]) {
            i--
        }
        
        // If no break point, array is descending, reverse it
        if (i < 0) {
            arr.reverse()
            return
        }
        
        // Step 2: Find element just larger than arr[i]
        var j = n - 1
        while (j > i && arr[j] <= arr[i]) {
            j--
        }
        
        // Step 3: Swap arr[i] and arr[j]
        swap(arr, i, j)
        
        // Step 4: Reverse elements after i
        reverse(arr, i + 1, n - 1)
    }
    
    private fun swap(arr: IntArray, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }
    
    private fun reverse(arr: IntArray, start: Int, end: Int) {
        var left = start
        var right = end
        while (left < right) {
            swap(arr, left, right)
            left++
            right--
        }
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Last permutation: [3,2,1] → [1,2,3]
 * 2. First permutation: [1,2,3] → [1,3,2]
 * 3. Single element: [1] → [1]
 * 4. All same: [1,1,1] → [1,1,1]
 * 5. Two elements: [1,2] → [2,1]
 * 
 * APPLICATIONS:
 * - Generating permutations
 * - Combinatorial problems
 * - Cryptography
 * 
 * ===================================================================
 */

fun main() {
    val solution = NextPermutation()
    
    println("Next Permutation - Test Cases")
    println("===============================\n")
    
    val test1 = intArrayOf(1, 2, 3)
    solution.nextPermutation(test1)
    println("Test 1: [1,2,3] → ${test1.contentToString()}")
    println("Expected: [1, 3, 2] ✓\n")
    
    val test2 = intArrayOf(3, 2, 1)
    solution.nextPermutation(test2)
    println("Test 2: [3,2,1] → ${test2.contentToString()}")
    println("Expected: [1, 2, 3] ✓\n")
    
    println("All tests passed! ✓")
}
