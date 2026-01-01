/**
 * ============================================================================
 * PROBLEM: Selection Sort
 * DIFFICULTY: Easy
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement selection sort algorithm to sort an array in ascending order.
 * Selection sort works by repeatedly finding the minimum element from the
 * unsorted portion and placing it at the beginning.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [64, 25, 12, 22, 11]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [11, 12, 22, 25, 64]
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^4
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Imagine arranging a deck of cards. Selection sort works like:
 * 1. Find the smallest card in the deck
 * 2. Place it in the first position
 * 3. Find the smallest card in remaining deck
 * 4. Place it in the second position
 * 5. Repeat until all cards are sorted
 * 
 * KEY INSIGHT:
 * We build the sorted array from left to right, always selecting the minimum
 * from the unsorted portion.
 * 
 * ALGORITHM STEPS:
 * 1. For i from 0 to n-2:
 *    a. Assume arr[i] is minimum
 *    b. Find actual minimum in arr[i+1...n-1]
 *    c. Swap minimum with arr[i]
 * 2. After n-1 passes, array is sorted
 * 
 * VISUAL EXAMPLE:
 * arr = [64, 25, 12, 22, 11]
 * 
 * Pass 1: Find min in [64, 25, 12, 22, 11] → 11
 *         Swap with 64 → [11, 25, 12, 22, 64]
 *                         ^^
 * Pass 2: Find min in [25, 12, 22, 64] → 12
 *         Swap with 25 → [11, 12, 25, 22, 64]
 *                             ^^
 * Pass 3: Find min in [25, 22, 64] → 22
 *         Swap with 25 → [11, 12, 22, 25, 64]
 *                                 ^^
 * Pass 4: Find min in [25, 64] → 25
 *         No swap needed → [11, 12, 22, 25, 64]
 *                                     ^^
 * Done! Last element is automatically in place.
 * 
 * COMPARISON WITH OTHER SORTS:
 * 1. Selection Sort: O(n²) time, O(1) space, fewer swaps than bubble
 * 2. Bubble Sort: O(n²) time, O(1) space, more swaps
 * 3. Insertion Sort: O(n²) time, O(1) space, better for nearly sorted
 * 4. Merge Sort: O(n log n) time, O(n) space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n²) in all cases
 * - Best Case: O(n²) - Even if sorted, still makes all comparisons
 * - Average Case: O(n²)
 * - Worst Case: O(n²) - Reverse sorted
 * 
 * WHY O(n²):
 * Pass 1: (n-1) comparisons
 * Pass 2: (n-2) comparisons
 * Pass 3: (n-3) comparisons
 * ...
 * Pass n-1: 1 comparison
 * Total: (n-1) + (n-2) + ... + 1 = n(n-1)/2 = O(n²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Sorts in-place
 * - Only uses temp variable for swapping
 * - No recursion, no extra arrays
 * 
 * ADVANTAGES OF SELECTION SORT:
 * ✅ Simple to understand and implement
 * ✅ O(1) space - sorts in-place
 * ✅ Fewer swaps than bubble sort (at most n swaps)
 * ✅ Good when write operations are costly
 * 
 * DISADVANTAGES:
 * ❌ O(n²) time even for sorted arrays
 * ❌ Not stable (relative order of equal elements may change)
 * ❌ Not adaptive (doesn't benefit from partially sorted data)
 * 
 * ============================================================================
 */

package sorting

class SelectionSort {
    
    /**
     * Selection sort implementation
     * TIME: O(n²), SPACE: O(1)
     * 
     * @param arr Array to sort (modified in-place)
     */
    fun selectionSort(arr: IntArray) {
        val n = arr.size
        
        // Outer loop: for each position from 0 to n-2
        // We don't need to sort the last element (it's automatically in place)
        for (i in 0 until n - 1) {
            // Assume current position has minimum
            var minIndex = i
            
            // Inner loop: find actual minimum in unsorted portion
            // Check elements from i+1 to end
            for (j in i + 1 until n) {
                // If we find a smaller element, update minIndex
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            
            // Swap minimum element with current position
            // Only swap if minimum is not already at current position
            if (minIndex != i) {
                val temp = arr[i]
                arr[i] = arr[minIndex]
                arr[minIndex] = temp
            }
        }
    }
    
    /**
     * Selection sort with Kotlin idioms
     * More concise but same algorithm
     */
    fun selectionSortKotlin(arr: IntArray) {
        for (i in arr.indices) {
            val minIndex = (i until arr.size).minByOrNull { arr[it] } ?: i
            if (minIndex != i) {
                arr[i] = arr[minIndex].also { arr[minIndex] = arr[i] }
            }
        }
    }
    
    /**
     * Find kth smallest element using selection sort approach
     * More efficient than fully sorting for small k
     * 
     * TIME: O(k*n), SPACE: O(1)
     */
    fun findKthSmallest(arr: IntArray, k: Int): Int? {
        if (k < 1 || k > arr.size) return null
        
        // Do k passes of selection sort
        for (i in 0 until k) {
            var minIndex = i
            for (j in i + 1 until arr.size) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j
                }
            }
            if (minIndex != i) {
                val temp = arr[i]
                arr[i] = arr[minIndex]
                arr[minIndex] = temp
            }
        }
        
        // After k passes, kth smallest is at index k-1
        return arr[k - 1]
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [64, 25, 12, 22, 11]
 * 
 * EXECUTION TRACE:
 * 
 * Initial: [64, 25, 12, 22, 11]
 * 
 * Pass 1 (i=0):
 * - minIndex = 0 (assume 64 is minimum)
 * - Check j=1: 25 < 64? YES → minIndex = 1
 * - Check j=2: 12 < 25? YES → minIndex = 2
 * - Check j=3: 22 < 12? NO
 * - Check j=4: 11 < 12? YES → minIndex = 4
 * - Swap arr[0] and arr[4]: [11, 25, 12, 22, 64]
 *                             ^^              ^^
 * 
 * Pass 2 (i=1):
 * - minIndex = 1 (assume 25 is minimum)
 * - Check j=2: 12 < 25? YES → minIndex = 2
 * - Check j=3: 22 < 12? NO
 * - Check j=4: 64 < 12? NO
 * - Swap arr[1] and arr[2]: [11, 12, 25, 22, 64]
 *                                 ^^  ^^
 * 
 * Pass 3 (i=2):
 * - minIndex = 2 (assume 25 is minimum)
 * - Check j=3: 22 < 25? YES → minIndex = 3
 * - Check j=4: 64 < 22? NO
 * - Swap arr[2] and arr[3]: [11, 12, 22, 25, 64]
 *                                     ^^  ^^
 * 
 * Pass 4 (i=3):
 * - minIndex = 3 (assume 25 is minimum)
 * - Check j=4: 64 < 25? NO
 * - No swap needed: [11, 12, 22, 25, 64]
 * 
 * FINAL: [11, 12, 22, 25, 64] ✓
 * 
 * ============================================================================
 * COMPARISON: BUBBLE VS SELECTION SORT
 * ============================================================================
 * 
 * Array: [64, 25, 12, 22, 11]
 * 
 * SELECTION SORT:
 * - Comparisons: (n-1) + (n-2) + ... + 1 = 10 comparisons
 * - Swaps: 4 swaps (one per pass)
 * - Always O(n²) comparisons
 * 
 * BUBBLE SORT:
 * - Comparisons: Up to n(n-1)/2 = 10 comparisons
 * - Swaps: Can be many more (every inversion)
 * - Can optimize with early termination
 * 
 * USE SELECTION SORT WHEN:
 * - Write operations are expensive
 * - You want predictable number of swaps
 * - Memory is extremely limited
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = SelectionSort()
    
    println("=== Selection Sort ===\n")
    
    // Test 1: Normal unsorted array
    val arr1 = intArrayOf(64, 25, 12, 22, 11)
    println("Test 1: Normal Array")
    println("Before: ${arr1.contentToString()}")
    sorter.selectionSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [11, 12, 22, 25, 64]\n")
    
    // Test 2: Already sorted
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: Already Sorted")
    println("Before: ${arr2.contentToString()}")
    sorter.selectionSort(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted")
    println("Before: ${arr3.contentToString()}")
    sorter.selectionSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Duplicates
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5)
    println("Test 4: With Duplicates")
    println("Before: ${arr4.contentToString()}")
    sorter.selectionSort(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 5, 6, 9]\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(42)
    println("Test 5: Single Element")
    println("Before: ${arr5.contentToString()}")
    sorter.selectionSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [42]\n")
    
    // Test 6: Negative numbers
    val arr6 = intArrayOf(-3, -1, -7, -4, -5, -2)
    println("Test 6: Negative Numbers")
    println("Before: ${arr6.contentToString()}")
    sorter.selectionSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [-7, -5, -4, -3, -2, -1]\n")
    
    // Test 7: Find kth smallest
    val arr7 = intArrayOf(7, 10, 4, 3, 20, 15)
    println("Test 7: Find 3rd Smallest")
    println("Array: ${arr7.contentToString()}")
    println("3rd smallest: ${sorter.findKthSmallest(arr7.copyOf(), 3)}")
    println("Expected: 7\n")
    
    // Test 8: Kotlin style
    val arr8 = intArrayOf(5, 2, 8, 1, 9)
    println("Test 8: Kotlin Style Sort")
    println("Before: ${arr8.contentToString()}")
    sorter.selectionSortKotlin(arr8)
    println("After:  ${arr8.contentToString()}")
    println("Expected: [1, 2, 5, 8, 9]\n")
}
