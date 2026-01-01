/**
 * ============================================================================
 * PROBLEM: Bubble Sort
 * DIFFICULTY: Easy
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement the bubble sort algorithm to sort an array in ascending order.
 * Bubble sort repeatedly steps through the list, compares adjacent elements and
 * swaps them if they are in the wrong order. The pass through the list is repeated
 * until the list is sorted.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [64, 34, 25, 12, 22, 11, 90]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [11, 12, 22, 25, 34, 64, 90]
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
 * Think of bubbles rising to the surface of water. In each pass, the largest
 * unsorted element "bubbles up" to its correct position at the end. After first
 * pass, the largest element is at the end. After second pass, the second largest
 * is in its position, and so on.
 * 
 * WHY "BUBBLE"?
 * The larger elements gradually "bubble" their way to the end of the array through
 * repeated swapping with adjacent elements.
 * 
 * ALGORITHM STEPS:
 * 1. Start with i = 0 (represents pass number)
 * 2. For each pass, compare adjacent elements from start to (end - i - 1)
 * 3. If left element > right element, swap them
 * 4. After each pass, one more element is in correct position
 * 5. Repeat until no swaps are needed (array is sorted)
 * 
 * VISUAL EXAMPLE:
 * Pass 1: [64, 34, 25, 12, 22, 11, 90]
 *          ↓
 *         [34, 64, 25, 12, 22, 11, 90] (swap 64 & 34)
 *              ↓
 *         [34, 25, 64, 12, 22, 11, 90] (swap 64 & 25)
 *                  ↓
 *         [34, 25, 12, 64, 22, 11, 90] (swap 64 & 12)
 *                      ↓
 *         [34, 25, 12, 22, 64, 11, 90] (swap 64 & 22)
 *                          ↓
 *         [34, 25, 12, 22, 11, 64, 90] (swap 64 & 11)
 *                              ↓
 *         [34, 25, 12, 22, 11, 64, 90] (64 < 90, no swap)
 * Result: 90 is now in correct position!
 * 
 * OPTIMIZATION:
 * - If no swaps occur in a pass, array is already sorted
 * - Can terminate early instead of completing all passes
 * - Each pass reduces the range to check by 1
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Bubble Sort (implemented): O(n²) time, O(1) space - Simple but slow
 * 2. Selection Sort: O(n²) time, O(1) space - Fewer swaps than bubble
 * 3. Insertion Sort: O(n²) time, O(1) space - Better for nearly sorted
 * 4. Merge Sort: O(n log n) time, O(n) space - Optimal time complexity
 * 5. Quick Sort: O(n log n) average time, O(log n) space - Fastest in practice
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Best Case: O(n) - When array is already sorted
 *   * Only one pass is needed with no swaps
 *   * Early termination with optimized version
 * 
 * - Average Case: O(n²)
 *   * On average, need to check n elements in each of n passes
 *   * Total comparisons: n(n-1)/2
 * 
 * - Worst Case: O(n²) - When array is reverse sorted
 *   * Need maximum passes and swaps
 *   * First pass: n-1 comparisons
 *   * Second pass: n-2 comparisons
 *   * ...
 *   * Total: (n-1) + (n-2) + ... + 1 = n(n-1)/2 = O(n²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses a constant amount of extra space
 * - temp variable for swapping: O(1)
 * - loop variables: O(1)
 * - Sorts array in-place without creating new array
 * - No recursive calls, so no stack space
 * 
 * WHY O(n²) IS SLOW:
 * For n=1000, need up to 1,000,000 operations
 * For n=10,000, need up to 100,000,000 operations
 * This is why bubble sort is rarely used for large datasets
 * 
 * ============================================================================
 */

package sorting

class BubbleSort {
    
    /**
     * Sorts the array using bubble sort algorithm (basic version)
     * 
     * @param arr The array to be sorted (modified in-place)
     */
    fun bubbleSort(arr: IntArray) {
        // Get the length of array
        // 'n' represents total number of elements to sort
        val n = arr.size
        
        // Outer loop: represents each pass through the array
        // After pass i, the last i elements are in correct position
        // So we need n-1 passes maximum (after n-1 passes, nth element is automatically sorted)
        for (i in 0 until n - 1) {
            // Inner loop: compares and swaps adjacent elements
            // We go up to (n - i - 1) because last i elements are already sorted
            // j represents the current element being compared with j+1
            for (j in 0 until n - i - 1) {
                // Compare adjacent elements
                // If left element is greater than right element, they're in wrong order
                if (arr[j] > arr[j + 1]) {
                    // Swap the elements using a temporary variable
                    // temp stores arr[j] so we don't lose it during swap
                    val temp = arr[j]
                    // Move right element to left position
                    arr[j] = arr[j + 1]
                    // Move left element (stored in temp) to right position
                    arr[j + 1] = temp
                    // Swap complete: elements are now in correct relative order
                }
                // If arr[j] <= arr[j+1], they're already in correct order, no swap needed
            }
            // After this pass, the element at position (n-i-1) is in its final sorted position
        }
        // After all passes, array is completely sorted
    }
    
    /**
     * Optimized bubble sort with early termination
     * Stops early if no swaps occur in a pass (array is sorted)
     * 
     * @param arr The array to be sorted (modified in-place)
     */
    fun bubbleSortOptimized(arr: IntArray) {
        val n = arr.size
        
        // Outer loop for each pass
        for (i in 0 until n - 1) {
            // Flag to detect if any swap happened in this pass
            // If no swap happens, array is already sorted and we can stop
            var swapped = false
            
            // Inner loop for comparisons
            for (j in 0 until n - i - 1) {
                // Compare and swap if needed
                if (arr[j] > arr[j + 1]) {
                    // Swap elements
                    val temp = arr[j]
                    arr[j] = arr[j + 1]
                    arr[j + 1] = temp
                    // Mark that a swap occurred
                    swapped = true
                }
            }
            
            // Optimization: If no swap occurred in this pass, array is sorted
            // We can terminate early instead of continuing remaining passes
            if (!swapped) {
                break  // Exit the outer loop
            }
        }
    }
    
    /**
     * Kotlin idiomatic version using destructuring for swap
     * More concise but same algorithm
     */
    fun bubbleSortKotlinStyle(arr: IntArray) {
        val n = arr.size
        for (i in 0 until n - 1) {
            var swapped = false
            for (j in 0 until n - i - 1) {
                if (arr[j] > arr[j + 1]) {
                    // Kotlin's destructuring assignment for clean swap
                    arr[j] = arr[j + 1].also { arr[j + 1] = arr[j] }
                    swapped = true
                }
            }
            if (!swapped) break
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [5, 1, 4, 2, 8]
 * 
 * EXECUTION TRACE:
 * 
 * Initial: [5, 1, 4, 2, 8]
 * 
 * Pass 1 (i=0): Find largest and move to end
 * - Compare 5,1: 5>1, swap → [1, 5, 4, 2, 8]
 * - Compare 5,4: 5>4, swap → [1, 4, 5, 2, 8]
 * - Compare 5,2: 5>2, swap → [1, 4, 2, 5, 8]
 * - Compare 5,8: 5<8, no swap → [1, 4, 2, 5, 8]
 * Result: 8 is in final position
 * 
 * Pass 2 (i=1): Find second largest and move to second-last position
 * - Compare 1,4: 1<4, no swap → [1, 4, 2, 5, 8]
 * - Compare 4,2: 4>2, swap → [1, 2, 4, 5, 8]
 * - Compare 4,5: 4<5, no swap → [1, 2, 4, 5, 8]
 * Result: 5 is in final position
 * 
 * Pass 3 (i=2): Find third largest
 * - Compare 1,2: 1<2, no swap → [1, 2, 4, 5, 8]
 * - Compare 2,4: 2<4, no swap → [1, 2, 4, 5, 8]
 * Result: 4 is in final position
 * 
 * Pass 4 (i=3): Final pass
 * - Compare 1,2: 1<2, no swap → [1, 2, 4, 5, 8]
 * Result: 2 is in final position, 1 is automatically sorted
 * 
 * FINAL: [1, 2, 4, 5, 8] ✓
 * 
 * With optimization, would terminate after Pass 3 (no swaps occurred)
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array []: No passes needed, remains empty
 * 2. Single element [42]: Already sorted, returns as is
 * 3. Two elements [2, 1]: One pass, one swap → [1, 2]
 * 4. Already sorted [1, 2, 3]: Optimized version exits after first pass
 * 5. Reverse sorted [5, 4, 3, 2, 1]: Worst case, maximum swaps
 * 6. All same elements [5, 5, 5]: No swaps, early termination
 * 7. Negative numbers [-3, -1, -5]: Works correctly
 * 8. Mix of positive and negative [-2, 3, -1, 5]: Sorted correctly
 * 
 * ============================================================================
 * WHEN TO USE BUBBLE SORT
 * ============================================================================
 * 
 * GOOD FOR:
 * - Educational purposes (easy to understand and implement)
 * - Small datasets (< 50 elements)
 * - Nearly sorted arrays (with optimization)
 * - When memory is extremely limited (in-place, O(1) space)
 * - When simplicity is more important than speed
 * 
 * NOT GOOD FOR:
 * - Large datasets (very slow O(n²))
 * - Performance-critical applications
 * - Random or reverse-sorted data
 * 
 * BETTER ALTERNATIVES:
 * - For small arrays: Insertion Sort (same complexity, but faster in practice)
 * - For large arrays: Quick Sort, Merge Sort, Heap Sort (O(n log n))
 * - For nearly sorted: Insertion Sort or Tim Sort
 * - Built-in sort functions (use optimized algorithms like Tim Sort)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = BubbleSort()
    
    println("=== Testing Basic Bubble Sort ===\n")
    
    // Test 1: Normal unsorted array
    val arr1 = intArrayOf(64, 34, 25, 12, 22, 11, 90)
    println("Before: ${arr1.contentToString()}")
    sorter.bubbleSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [11, 12, 22, 25, 34, 64, 90]\n")
    
    // Test 2: Already sorted array
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Before: ${arr2.contentToString()}")
    sorter.bubbleSortOptimized(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5] (should terminate early)\n")
    
    // Test 3: Reverse sorted array
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Before: ${arr3.contentToString()}")
    sorter.bubbleSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Array with duplicates
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5)
    println("Before: ${arr4.contentToString()}")
    sorter.bubbleSort(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 5, 6, 9]\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(42)
    println("Before: ${arr5.contentToString()}")
    sorter.bubbleSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [42]\n")
    
    // Test 6: Two elements
    val arr6 = intArrayOf(2, 1)
    println("Before: ${arr6.contentToString()}")
    sorter.bubbleSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [1, 2]\n")
    
    // Test 7: Negative numbers
    val arr7 = intArrayOf(-3, -1, -7, -4, -5, -2)
    println("Before: ${arr7.contentToString()}")
    sorter.bubbleSort(arr7)
    println("After:  ${arr7.contentToString()}")
    println("Expected: [-7, -5, -4, -3, -2, -1]\n")
    
    // Test 8: Mix of positive and negative
    val arr8 = intArrayOf(3, -2, 0, -1, 5, -3)
    println("Before: ${arr8.contentToString()}")
    sorter.bubbleSortKotlinStyle(arr8)
    println("After:  ${arr8.contentToString()}")
    println("Expected: [-3, -2, -1, 0, 3, 5]\n")
}
