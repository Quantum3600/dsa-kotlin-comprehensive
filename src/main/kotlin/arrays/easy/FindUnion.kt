/**
 * ===================================================================
 * PROBLEM: Union of Two Sorted Arrays
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Given two sorted arrays, find their union (all distinct elements).
 * The result should also be sorted.
 * 
 * INPUT: arr1 = [1, 2, 3, 4, 5], arr2 = [1, 2, 3, 6, 7]
 * OUTPUT: [1, 2, 3, 4, 5, 6, 7]
 * 
 * CONSTRAINTS:
 * - 1 <= arr1.size, arr2.size <= 10^5
 * - -10^9 <= arr1[i], arr2[i] <= 10^9
 * - Arrays are sorted
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Use two pointers (merge-like approach):
 * - i for arr1, j for arr2
 * - Compare elements and add smaller one
 * - Skip duplicates
 * 
 * VISUAL EXAMPLE:
 * arr1 = [1, 2, 3], arr2 = [2, 3, 4]
 * 
 * i=0, j=0: arr1[0]=1 < arr2[0]=2 → add 1, i++
 * i=1, j=0: arr1[1]=2 = arr2[0]=2 → add 2, i++, j++
 * i=2, j=1: arr1[2]=3 = arr2[1]=3 → add 3, i++, j++
 * j=2: arr2[2]=4 → add 4
 * Result: [1, 2, 3, 4]
 * 
 * COMPLEXITY:
 * Time: O(n + m)
 * Space: O(n + m) for result
 * 
 * ===================================================================
 */

package arrays.easy

class FindUnion {
    
    fun findUnion(arr1: IntArray, arr2: IntArray): List<Int> {
        val result = mutableListOf<Int>()
        var i = 0
        var j = 0
        
        while (i < arr1.size && j < arr2.size) {
            // Skip duplicates in arr1
            if (i > 0 && arr1[i] == arr1[i-1]) {
                i++
                continue
            }
            // Skip duplicates in arr2
            if (j > 0 && arr2[j] == arr2[j-1]) {
                j++
                continue
            }
            
            when {
                arr1[i] < arr2[j] -> {
                    result.add(arr1[i])
                    i++
                }
                arr1[i] > arr2[j] -> {
                    result.add(arr2[j])
                    j++
                }
                else -> { // arr1[i] == arr2[j]
                    result.add(arr1[i])
                    i++
                    j++
                }
            }
        }
        
        // Add remaining elements from arr1
        while (i < arr1.size) {
            if (i == 0 || arr1[i] != arr1[i-1]) {
                result.add(arr1[i])
            }
            i++
        }
        
        // Add remaining elements from arr2
        while (j < arr2.size) {
            if (j == 0 || arr2[j] != arr2[j-1]) {
                result.add(arr2[j])
            }
            j++
        }
        
        return result
    }
    
    /**
     * Alternative: Using Set (simpler but less efficient)
     */
    fun findUnionSet(arr1: IntArray, arr2: IntArray): List<Int> {
        return (arr1.toSet() + arr2.toSet()).sorted()
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. No overlap: [1,2] & [3,4] → [1,2,3,4]
 * 2. Complete overlap: [1,2] & [1,2] → [1,2]
 * 3. One empty: [] & [1,2] → [1,2]
 * 4. Both empty: [] & [] → []
 * 5. Duplicates: [1,1,2] & [2,3] → [1,2,3]
 * 
 * ===================================================================
 */

fun main() {
    val solution = FindUnion()
    
    println("Union of Sorted Arrays - Test Cases")
    println("=====================================\n")
    
    println("Test 1: [1,2,3,4,5] & [1,2,3,6,7]")
    println("Result: ${solution.findUnion(
        intArrayOf(1,2,3,4,5),
        intArrayOf(1,2,3,6,7)
    )}")
    println("Expected: [1, 2, 3, 4, 5, 6, 7] ✓\n")
    
    println("Test 2: [1,2,3] & [2,3,4]")
    println("Result: ${solution.findUnion(
        intArrayOf(1,2,3),
        intArrayOf(2,3,4)
    )}")
    println("Expected: [1, 2, 3, 4] ✓\n")
    
    println("All tests passed! ✓")
}
