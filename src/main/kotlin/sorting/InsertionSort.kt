/**
 * ============================================================================
 * PROBLEM: Insertion Sort
 * DIFFICULTY: Easy
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement insertion sort algorithm to sort an array in ascending order.
 * Insertion sort builds the final sorted array one item at a time by inserting
 * each element into its correct position in the already sorted portion.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [12, 11, 13, 5, 6]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [5, 6, 11, 12, 13]
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
 * Think of sorting playing cards in your hand:
 * 1. Pick up first card - it's "sorted" (1 card)
 * 2. Pick up second card - insert it in correct position
 * 3. Pick up third card - insert it among the first two
 * 4. Continue until all cards are sorted
 * 
 * KEY INSIGHT:
 * At any point, the left portion is sorted, and we insert the next element
 * into its correct position by shifting larger elements to the right.
 * 
 * ALGORITHM STEPS:
 * 1. Start with second element (index 1)
 * 2. For each element:
 *    a. Store current element as "key"
 *    b. Compare with elements to its left
 *    c. Shift larger elements one position right
 *    d. Insert key at correct position
 * 3. Repeat until array is sorted
 * 
 * VISUAL EXAMPLE:
 * arr = [12, 11, 13, 5, 6]
 * 
 * Pass 1 (i=1, key=11):
 *   [12, 11, 13, 5, 6]
 *    ^^  ^^
 *   12 > 11, shift 12 right
 *   [11, 12, 13, 5, 6]
 * 
 * Pass 2 (i=2, key=13):
 *   [11, 12, 13, 5, 6]
 *            ^^
 *   13 is already in place
 * 
 * Pass 3 (i=3, key=5):
 *   [11, 12, 13, 5, 6]
 *                ^^
 *   Shift 13, 12, 11 right
 *   [5, 11, 12, 13, 6]
 * 
 * Pass 4 (i=4, key=6):
 *   [5, 11, 12, 13, 6]
 *                   ^^
 *   Shift 13, 12, 11 right, insert after 5
 *   [5, 6, 11, 12, 13] ✓
 * 
 * COMPARISON WITH OTHER SORTS:
 * 1. Insertion Sort: O(n²) worst, O(n) best, ADAPTIVE, STABLE
 * 2. Bubble Sort: O(n²) always, not adaptive (without optimization)
 * 3. Selection Sort: O(n²) always, NOT adaptive, NOT stable
 * 4. Merge Sort: O(n log n) always, NOT adaptive
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Best Case: O(n) - Already sorted array
 *   * Only n-1 comparisons, no shifts
 *   * Example: [1, 2, 3, 4, 5]
 * 
 * - Average Case: O(n²)
 *   * On average, each element shifts halfway through sorted portion
 *   * Total: ~n²/4 comparisons and shifts
 * 
 * - Worst Case: O(n²) - Reverse sorted array
 *   * Maximum comparisons and shifts
 *   * Pass 1: 1 shift, Pass 2: 2 shifts, ..., Pass n-1: n-1 shifts
 *   * Total: 1 + 2 + ... + (n-1) = n(n-1)/2 = O(n²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Sorts in-place
 * - Only uses temp variable for key
 * - No recursion, no extra arrays
 * 
 * ADVANTAGES OF INSERTION SORT:
 * ✅ Simple to implement
 * ✅ Efficient for small datasets (< 50 elements)
 * ✅ ADAPTIVE: O(n) for nearly sorted arrays
 * ✅ STABLE: Maintains relative order of equal elements
 * ✅ ONLINE: Can sort data as it arrives
 * ✅ In-place: O(1) extra space
 * 
 * DISADVANTAGES:
 * ❌ O(n²) time for large unsorted datasets
 * ❌ Many comparisons and shifts for reverse sorted
 * 
 * ============================================================================
 */

package sorting

class InsertionSort {
    
    /**
     * Insertion sort implementation
     * TIME: O(n²) worst, O(n) best, SPACE: O(1)
     * 
     * @param arr Array to sort (modified in-place)
     */
    fun insertionSort(arr: IntArray) {
        val n = arr.size
        
        // Start from second element (index 1)
        // First element is trivially sorted
        for (i in 1 until n) {
            // Store current element as key to be inserted
            val key = arr[i]
            
            // Start comparing with element to the left
            var j = i - 1
            
            // Move elements greater than key one position ahead
            // This creates space for inserting key
            while (j >= 0 && arr[j] > key) {
                // Shift element to the right
                arr[j + 1] = arr[j]
                j--
            }
            
            // Insert key at correct position
            // j+1 is the position where key should be inserted
            arr[j + 1] = key
        }
    }
    
    /**
     * Insertion sort with Kotlin idioms
     * More concise but same logic
     */
    fun insertionSortKotlin(arr: IntArray) {
        for (i in 1 until arr.size) {
            val key = arr[i]
            var j = i - 1
            
            // Shift elements using also
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[j + 1] = key
        }
    }
    
    /**
     * Recursive insertion sort
     * TIME: O(n²), SPACE: O(n) for recursion stack
     */
    fun insertionSortRecursive(arr: IntArray, n: Int = arr.size) {
        // Base case: array of size 1 is sorted
        if (n <= 1) return
        
        // Sort first n-1 elements
        insertionSortRecursive(arr, n - 1)
        
        // Insert last element at correct position in sorted array
        val key = arr[n - 1]
        var j = n - 2
        
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = key
    }
    
    /**
     * Insertion sort with binary search optimization
     * Reduces comparisons but not shifts
     * TIME: O(n²) shifts still, but O(n log n) comparisons
     */
    fun insertionSortBinary(arr: IntArray) {
        for (i in 1 until arr.size) {
            val key = arr[i]
            
            // Find position using binary search
            var left = 0
            var right = i - 1
            
            while (left <= right) {
                val mid = left + (right - left) / 2
                if (arr[mid] > key) {
                    right = mid - 1
                } else {
                    left = mid + 1
                }
            }
            
            // left now has the position where key should be inserted
            // Shift all elements from left to i-1 one position right
            var j = i - 1
            while (j >= left) {
                arr[j + 1] = arr[j]
                j--
            }
            arr[left] = key
        }
    }
    
    /**
     * Count number of shifts needed (inversions)
     * Useful for analyzing how unsorted the array is
     */
    fun countShifts(arr: IntArray): Int {
        val temp = arr.copyOf()
        var shifts = 0
        
        for (i in 1 until temp.size) {
            val key = temp[i]
            var j = i - 1
            
            while (j >= 0 && temp[j] > key) {
                temp[j + 1] = temp[j]
                shifts++
                j--
            }
            temp[j + 1] = key
        }
        
        return shifts
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [12, 11, 13, 5, 6]
 * 
 * Initial: [12, 11, 13, 5, 6]
 *           ^^
 *          sorted portion (size 1)
 * 
 * Pass 1 (i=1, key=11):
 * - Compare 11 with 12: 11 < 12
 * - Shift 12 right: [12, 12, 13, 5, 6]
 * - Insert 11: [11, 12, 13, 5, 6]
 *               ^^^^^^^^^^
 *              sorted (size 2)
 * 
 * Pass 2 (i=2, key=13):
 * - Compare 13 with 12: 13 > 12
 * - No shift needed
 * - [11, 12, 13, 5, 6]
 *     ^^^^^^^^^^^^^^
 *    sorted (size 3)
 * 
 * Pass 3 (i=3, key=5):
 * - Compare 5 with 13: 5 < 13, shift 13
 * - Compare 5 with 12: 5 < 12, shift 12
 * - Compare 5 with 11: 5 < 11, shift 11
 * - Insert 5 at start: [5, 11, 12, 13, 6]
 *                       ^^^^^^^^^^^^^^^^^^
 *                      sorted (size 4)
 * 
 * Pass 4 (i=4, key=6):
 * - Compare 6 with 13: 6 < 13, shift 13
 * - Compare 6 with 12: 6 < 12, shift 12
 * - Compare 6 with 11: 6 < 11, shift 11
 * - Compare 6 with 5: 6 > 5, stop
 * - Insert 6 after 5: [5, 6, 11, 12, 13]
 * 
 * FINAL: [5, 6, 11, 12, 13] ✓
 * 
 * ============================================================================
 * WHEN TO USE INSERTION SORT
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Array is small (< 50 elements)
 * ✅ Array is nearly sorted
 * ✅ Need stable sort
 * ✅ Data arrives online (streaming)
 * ✅ Memory is limited
 * 
 * DON'T USE WHEN:
 * ❌ Large arrays (use Quick/Merge Sort)
 * ❌ Completely random data
 * ❌ Need guaranteed O(n log n)
 * 
 * REAL-WORLD USAGE:
 * - Used in hybrid sorting algorithms (TimSort, IntroSort)
 * - Default for small subarrays in Quick Sort
 * - Sorting small lists in production systems
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = InsertionSort()
    
    println("=== Insertion Sort ===\n")
    
    // Test 1: Normal unsorted array
    val arr1 = intArrayOf(12, 11, 13, 5, 6)
    println("Test 1: Normal Array")
    println("Before: ${arr1.contentToString()}")
    sorter.insertionSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [5, 6, 11, 12, 13]\n")
    
    // Test 2: Already sorted
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: Already Sorted (Best Case)")
    println("Before: ${arr2.contentToString()}")
    val shifts2 = sorter.countShifts(arr2)
    sorter.insertionSort(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Shifts: $shifts2 (O(n) best case!)")
    println("Expected: [1, 2, 3, 4, 5], 0 shifts\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted (Worst Case)")
    println("Before: ${arr3.contentToString()}")
    val shifts3 = sorter.countShifts(arr3)
    sorter.insertionSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Shifts: $shifts3 (O(n²) worst case)")
    println("Expected: [1, 2, 3, 4, 5], 10 shifts\n")
    
    // Test 4: Duplicates
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5)
    println("Test 4: With Duplicates")
    println("Before: ${arr4.contentToString()}")
    sorter.insertionSort(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 5, 6, 9]\n")
    
    // Test 5: Negative numbers
    val arr5 = intArrayOf(-3, -1, -7, -4, -5, -2)
    println("Test 5: Negative Numbers")
    println("Before: ${arr5.contentToString()}")
    sorter.insertionSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [-7, -5, -4, -3, -2, -1]\n")
    
    // Test 6: Nearly sorted
    val arr6 = intArrayOf(1, 2, 3, 7, 4, 5, 6, 8, 9)
    println("Test 6: Nearly Sorted")
    println("Before: ${arr6.contentToString()}")
    val shifts6 = sorter.countShifts(arr6)
    sorter.insertionSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Shifts: $shifts6 (very few!)")
    println("Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9]\n")
    
    // Test 7: Recursive version
    val arr7 = intArrayOf(8, 3, 1, 7, 0, 10, 2)
    println("Test 7: Recursive Insertion Sort")
    println("Before: ${arr7.contentToString()}")
    sorter.insertionSortRecursive(arr7)
    println("After:  ${arr7.contentToString()}")
    println("Expected: [0, 1, 2, 3, 7, 8, 10]\n")
    
    // Test 8: Binary search optimization
    val arr8 = intArrayOf(5, 2, 8, 1, 9, 3, 7)
    println("Test 8: Binary Insertion Sort")
    println("Before: ${arr8.contentToString()}")
    sorter.insertionSortBinary(arr8)
    println("After:  ${arr8.contentToString()}")
    println("Expected: [1, 2, 3, 5, 7, 8, 9]\n")
    
    println("=== Insertion Sort is ADAPTIVE! ===")
    println("Best for nearly sorted or small arrays!")
}
