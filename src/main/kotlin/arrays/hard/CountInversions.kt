/**
 * ============================================================================
 * PROBLEM: Count Inversions
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Divide and Conquer, Merge Sort
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, count the number of inversions in the array.
 * An inversion is a pair of indices (i, j) such that i < j and arr[i] > arr[j].
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [5, 3, 2, 4, 1]
 * 
 * OUTPUT FORMAT:
 * - Number of inversions: 8
 *   Inversions: (5,3), (5,2), (5,4), (5,1), (3,2), (3,1), (2,1), (4,1)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.length <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Use modified merge sort. During the merge step, when an element from the
 * right half is placed before an element from the left half, it forms
 * inversions with all remaining elements in the left half.
 * 
 * KEY INSIGHT:
 * When merging two sorted halves:
 * - If left[i] > right[j], then left[i] to left[mid] are all > right[j]
 * - This gives us (mid - i + 1) inversions at once
 * 
 * ALGORITHM STEPS:
 * 1. Divide array into two halves
 * 2. Recursively count inversions in left half
 * 3. Recursively count inversions in right half
 * 4. Count inversions during merge step
 * 5. Return total inversions
 * 
 * VISUAL EXAMPLE:
 * arr = [5, 3, 2, 4, 1]
 * 
 * Split: [5, 3, 2] | [4, 1]
 * 
 * Left [5, 3, 2]:
 *   Split: [5] | [3, 2]
 *   [3, 2]: inversions = 1 (3,2)
 *   Merge [5] and [2, 3]: inversions = 2 (5,2), (5,3)
 *   Result: [2, 3, 5], inversions = 3
 * 
 * Right [4, 1]:
 *   inversions = 1 (4,1)
 *   Result: [1, 4]
 * 
 * Merge [2, 3, 5] and [1, 4]:
 *   1 < 2: inversions += 3 (2,1), (3,1), (5,1)
 *   2 < 4: no inversions
 *   3 < 4: no inversions
 *   4 < 5: inversions += 1 (5,4)
 *   Result: [1, 2, 3, 4, 5], inversions = 4
 * 
 * Total: 3 + 1 + 4 = 8 inversions
 * 
 * COMPLEXITY:
 * Time: O(n log n) - same as merge sort
 * Space: O(n) - for temporary arrays
 * 
 * ============================================================================
 */

package arrays.hard

class CountInversions {
    
    /**
     * Counts inversions using modified merge sort
     * 
     * @param arr The input array
     * @return Number of inversions
     */
    fun countInversions(arr: IntArray): Long {
        if (arr.size <= 1) return 0
        val temp = IntArray(arr.size)
        return mergeSortAndCount(arr, temp, 0, arr.size - 1)
    }
    
    /**
     * Performs merge sort and counts inversions
     */
    private fun mergeSortAndCount(
        arr: IntArray,
        temp: IntArray,
        left: Int,
        right: Int
    ): Long {
        if (left >= right) return 0
        
        val mid = left + (right - left) / 2
        
        // Count inversions in left and right halves
        var inversions = 0L
        inversions += mergeSortAndCount(arr, temp, left, mid)
        inversions += mergeSortAndCount(arr, temp, mid + 1, right)
        
        // Count inversions during merge
        inversions += mergeAndCount(arr, temp, left, mid, right)
        
        return inversions
    }
    
    /**
     * Merges two sorted halves and counts inversions
     */
    private fun mergeAndCount(
        arr: IntArray,
        temp: IntArray,
        left: Int,
        mid: Int,
        right: Int
    ): Long {
        var i = left      // Starting index of left subarray
        var j = mid + 1   // Starting index of right subarray
        var k = left      // Starting index for merged array
        var inversions = 0L
        
        // Merge the two halves
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++]
            } else {
                // arr[i] > arr[j], so all elements from i to mid are > arr[j]
                // This creates (mid - i + 1) inversions
                temp[k++] = arr[j++]
                inversions += (mid - i + 1).toLong()
            }
        }
        
        // Copy remaining elements from left half
        while (i <= mid) {
            temp[k++] = arr[i++]
        }
        
        // Copy remaining elements from right half
        while (j <= right) {
            temp[k++] = arr[j++]
        }
        
        // Copy sorted elements back to original array
        for (idx in left..right) {
            arr[idx] = temp[idx]
        }
        
        return inversions
    }
    
    /**
     * Brute force approach - check all pairs
     * Time: O(n²), Space: O(1)
     */
    fun countInversionsBruteForce(arr: IntArray): Long {
        var count = 0L
        for (i in arr.indices) {
            for (j in i + 1 until arr.size) {
                if (arr[i] > arr[j]) {
                    count++
                }
            }
        }
        return count
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Sorted array: [1,2,3,4,5] → 0 inversions
 * 2. Reverse sorted: [5,4,3,2,1] → 10 inversions (n*(n-1)/2)
 * 3. Single element: [1] → 0 inversions
 * 4. Two elements (sorted): [1,2] → 0 inversions
 * 5. Two elements (reversed): [2,1] → 1 inversion
 * 6. All same: [3,3,3,3] → 0 inversions
 * 7. Duplicates: [3,1,3,1] → 4 inversions
 * 
 * APPLICATIONS:
 * 1. Measuring similarity between rankings
 * 2. Sorting analysis (how far from sorted)
 * 3. Collaborative filtering (recommendation systems)
 * 4. DNA sequence analysis
 * 5. Music playlist similarity
 * 6. Statistical correlation analysis
 * 7. Machine learning (distance metrics)
 * 
 * ============================================================================
 */

fun main() {
    val solution = CountInversions()
    
    println("=== Count Inversions Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val arr1 = intArrayOf(5, 3, 2, 4, 1)
    println("Input: ${arr1.contentToString()}")
    println("Result: ${solution.countInversions(arr1.clone())}")
    println("Expected: 8\n")
    
    // Test 2: Sorted array
    println("Test 2: Sorted array")
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${arr2.contentToString()}")
    println("Result: ${solution.countInversions(arr2.clone())}")
    println("Expected: 0\n")
    
    // Test 3: Reverse sorted
    println("Test 3: Reverse sorted")
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Input: ${arr3.contentToString()}")
    println("Result: ${solution.countInversions(arr3.clone())}")
    println("Expected: 10\n")
    
    // Test 4: Single element
    println("Test 4: Single element")
    val arr4 = intArrayOf(1)
    println("Input: ${arr4.contentToString()}")
    println("Result: ${solution.countInversions(arr4.clone())}")
    println("Expected: 0\n")
    
    // Test 5: Two elements reversed
    println("Test 5: Two elements reversed")
    val arr5 = intArrayOf(2, 1)
    println("Input: ${arr5.contentToString()}")
    println("Result: ${solution.countInversions(arr5.clone())}")
    println("Expected: 1\n")
    
    // Test 6: Duplicates
    println("Test 6: With duplicates")
    val arr6 = intArrayOf(2, 4, 1, 3, 5)
    println("Input: ${arr6.contentToString()}")
    println("Result: ${solution.countInversions(arr6.clone())}")
    println("Expected: 3\n")
    
    // Comparison of approaches
    println("Comparing approaches:")
    val testArr = intArrayOf(3, 1, 2)
    println("Array: ${testArr.contentToString()}")
    println("Merge sort approach: ${solution.countInversions(testArr.clone())}")
    println("Brute force approach: ${solution.countInversionsBruteForce(testArr)}")
    
    println("\nAll tests completed!")
}
