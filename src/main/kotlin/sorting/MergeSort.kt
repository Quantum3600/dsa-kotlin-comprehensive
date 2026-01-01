/**
 * ============================================================================
 * PROBLEM: Merge Sort
 * DIFFICULTY: Medium
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement merge sort algorithm to sort an array in ascending order.
 * Merge sort is a divide-and-conquer algorithm that divides the array into
 * two halves, recursively sorts them, and then merges the sorted halves.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [12, 11, 13, 5, 6, 7]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [5, 6, 7, 11, 12, 13]
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^6
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of sorting a deck of cards:
 * 1. Split deck into two piles
 * 2. Sort each pile separately (recursively)
 * 3. Merge the two sorted piles back together
 * 
 * WHY IT WORKS:
 * - Sorting 1 element is trivial (base case)
 * - Merging two sorted arrays is efficient: O(n)
 * - Recursively dividing reduces problem size by half each time
 * 
 * ALGORITHM STEPS:
 * 1. **Divide**: Split array into two halves at middle
 * 2. **Conquer**: Recursively sort both halves
 * 3. **Combine**: Merge the two sorted halves
 * 
 * MERGE PROCESS:
 * - Compare first elements of both arrays
 * - Take smaller element and add to result
 * - Repeat until one array is exhausted
 * - Copy remaining elements from other array
 * 
 * VISUAL EXAMPLE:
 * ```
 * [38, 27, 43, 3, 9, 82, 10]
 *          /              \
 *   [38, 27, 43, 3]    [9, 82, 10]
 *      /      \           /      \
 *  [38, 27]  [43, 3]   [9, 82]  [10]
 *   /   \     /   \     /   \      |
 * [38] [27] [43] [3]  [9] [82]  [10]
 *   \   /     \   /     \   /      |
 *  [27, 38]  [3, 43]   [9, 82]  [10]
 *      \      /           \      /
 *   [3, 27, 38, 43]    [9, 10, 82]
 *          \              /
 *   [3, 9, 10, 27, 38, 43, 82]
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n) in all cases
 * - Best Case: O(n log n)
 * - Average Case: O(n log n)
 * - Worst Case: O(n log n)
 * 
 * WHY O(n log n):
 * - Dividing array: log n levels of recursion
 * - Merging at each level: O(n) work
 * - Total: O(n) * log n = O(n log n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Need auxiliary array for merging: O(n)
 * - Recursion stack: O(log n)
 * - Total: O(n)
 * 
 * ADVANTAGES:
 * ✅ Guaranteed O(n log n) time (stable performance)
 * ✅ Stable sort (maintains relative order)
 * ✅ Predictable behavior
 * ✅ Good for linked lists (no random access needed)
 * 
 * DISADVANTAGES:
 * ❌ Requires O(n) extra space
 * ❌ Not in-place
 * ❌ Slower than quicksort in practice (more data movement)
 * 
 * ============================================================================
 */

package sorting

class MergeSort {
    
    /**
     * Merge sort main function
     * TIME: O(n log n), SPACE: O(n)
     * 
     * @param arr Array to sort (modified in-place)
     */
    fun mergeSort(arr: IntArray) {
        if (arr.size <= 1) return
        mergeSortHelper(arr, 0, arr.size - 1)
    }
    
    /**
     * Recursive helper function
     * Sorts arr[left...right]
     */
    private fun mergeSortHelper(arr: IntArray, left: Int, right: Int) {
        // Base case: single element is already sorted
        if (left >= right) return
        
        // Find middle point
        val mid = left + (right - left) / 2
        
        // Recursively sort first half
        mergeSortHelper(arr, left, mid)
        
        // Recursively sort second half
        mergeSortHelper(arr, mid + 1, right)
        
        // Merge the two sorted halves
        merge(arr, left, mid, right)
    }
    
    /**
     * Merge two sorted subarrays
     * Merges arr[left...mid] and arr[mid+1...right]
     */
    private fun merge(arr: IntArray, left: Int, mid: Int, right: Int) {
        // Create temp arrays for two halves
        val leftSize = mid - left + 1
        val rightSize = right - mid
        
        val leftArray = IntArray(leftSize)
        val rightArray = IntArray(rightSize)
        
        // Copy data to temp arrays
        for (i in 0 until leftSize) {
            leftArray[i] = arr[left + i]
        }
        for (i in 0 until rightSize) {
            rightArray[i] = arr[mid + 1 + i]
        }
        
        // Merge temp arrays back into arr[left...right]
        var i = 0  // Index for leftArray
        var j = 0  // Index for rightArray
        var k = left  // Index for merged array
        
        // Compare and merge
        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                arr[k] = leftArray[i]
                i++
            } else {
                arr[k] = rightArray[j]
                j++
            }
            k++
        }
        
        // Copy remaining elements of leftArray (if any)
        while (i < leftSize) {
            arr[k] = leftArray[i]
            i++
            k++
        }
        
        // Copy remaining elements of rightArray (if any)
        while (j < rightSize) {
            arr[k] = rightArray[j]
            j++
            k++
        }
    }
    
    /**
     * Merge sort returning new sorted array (non-destructive)
     */
    fun mergeSortNew(arr: IntArray): IntArray {
        val result = arr.copyOf()
        mergeSort(result)
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [38, 27, 43, 3]
 * 
 * CALL TREE:
 * 
 * mergeSort([38, 27, 43, 3], 0, 3)
 *   mid = 1
 *   
 *   mergeSort([38, 27, 43, 3], 0, 1)
 *     mid = 0
 *     
 *     mergeSort([38, 27, 43, 3], 0, 0)  // Base case
 *     mergeSort([38, 27, 43, 3], 1, 1)  // Base case
 *     
 *     merge([38, 27, 43, 3], 0, 0, 1)
 *       leftArray = [38]
 *       rightArray = [27]
 *       Compare: 27 < 38
 *       Result: [27, 38, 43, 3]
 *   
 *   mergeSort([27, 38, 43, 3], 2, 3)
 *     mid = 2
 *     
 *     mergeSort([27, 38, 43, 3], 2, 2)  // Base case
 *     mergeSort([27, 38, 43, 3], 3, 3)  // Base case
 *     
 *     merge([27, 38, 43, 3], 2, 2, 3)
 *       leftArray = [43]
 *       rightArray = [3]
 *       Compare: 3 < 43
 *       Result: [27, 38, 3, 43]
 *   
 *   merge([27, 38, 3, 43], 0, 1, 3)
 *     leftArray = [27, 38]
 *     rightArray = [3, 43]
 *     
 *     Compare: 3 < 27 → [3, ...]
 *     Compare: 27 < 43 → [3, 27, ...]
 *     Compare: 38 < 43 → [3, 27, 38, ...]
 *     Remaining: 43 → [3, 27, 38, 43]
 * 
 * FINAL: [3, 27, 38, 43] ✓
 * 
 * ============================================================================
 * COMPARISON WITH OTHER SORTS
 * ============================================================================
 * 
 * MERGE SORT vs QUICK SORT:
 * - Merge Sort: O(n log n) always, O(n) space, stable
 * - Quick Sort: O(n log n) average, O(1) space, not stable
 * - Use Merge Sort when: need stability, have extra memory
 * - Use Quick Sort when: need in-place sort, average case matters
 * 
 * MERGE SORT vs HEAP SORT:
 * - Merge Sort: O(n log n), O(n) space, stable
 * - Heap Sort: O(n log n), O(1) space, not stable
 * - Use Merge Sort when: need stability
 * - Use Heap Sort when: need in-place and guaranteed O(n log n)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = MergeSort()
    
    println("=== Merge Sort ===\n")
    
    // Test 1: Normal array
    val arr1 = intArrayOf(12, 11, 13, 5, 6, 7)
    println("Test 1: Normal Array")
    println("Before: ${arr1.contentToString()}")
    sorter.mergeSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [5, 6, 7, 11, 12, 13]\n")
    
    // Test 2: Already sorted
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: Already Sorted")
    println("Before: ${arr2.contentToString()}")
    sorter.mergeSort(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted")
    println("Before: ${arr3.contentToString()}")
    sorter.mergeSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Duplicates
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5)
    println("Test 4: With Duplicates")
    println("Before: ${arr4.contentToString()}")
    sorter.mergeSort(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 5, 6, 9]\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(42)
    println("Test 5: Single Element")
    println("Before: ${arr5.contentToString()}")
    sorter.mergeSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [42]\n")
    
    // Test 6: Negative numbers
    val arr6 = intArrayOf(-3, -1, -7, -4, -5, -2)
    println("Test 6: Negative Numbers")
    println("Before: ${arr6.contentToString()}")
    sorter.mergeSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [-7, -5, -4, -3, -2, -1]\n")
    
    // Test 7: Large array performance
    println("Test 7: Performance Test")
    val largeArr = IntArray(10000) { (0..10000).random() }
    
    val start = System.nanoTime()
    sorter.mergeSort(largeArr)
    val time = (System.nanoTime() - start) / 1000000
    
    println("Sorted 10,000 elements in $time ms")
    println("First 10: ${largeArr.take(10)}")
    println("Last 10: ${largeArr.takeLast(10)}")
    println("Is sorted: ${largeArr.contentEquals(largeArr.sortedArray())}\n")
    
    println("Merge Sort guarantees O(n log n) time in all cases!")
}
