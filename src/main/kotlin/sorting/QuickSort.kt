/**
 * ============================================================================
 * PROBLEM: Quick Sort
 * DIFFICULTY: Medium
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement quick sort algorithm to sort an array in ascending order.
 * Quick sort is a divide-and-conquer algorithm that selects a 'pivot' element
 * and partitions the array around it, recursively sorting the partitions.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [10, 7, 8, 9, 1, 5]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [1, 5, 7, 8, 9, 10]
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
 * Think of organizing books by height:
 * 1. Pick a random book (pivot)
 * 2. Put all shorter books to left, taller to right
 * 3. Now pivot is in correct position!
 * 4. Repeat for left and right groups
 * 
 * KEY INSIGHT:
 * After partitioning, the pivot is in its final sorted position.
 * Everything to its left is smaller, everything to right is larger.
 * 
 * ALGORITHM STEPS:
 * 1. **Choose pivot**: Last element (or any strategy)
 * 2. **Partition**: Rearrange so elements < pivot go left,
 *    elements > pivot go right
 * 3. **Recursively sort**: Left and right partitions
 * 
 * PARTITION PROCESS (Hoare/Lomuto):
 * - Lomuto scheme (used here): Simpler, pivot at end
 * - Hoare scheme: More efficient, bidirectional scan
 * 
 * VISUAL EXAMPLE (Lomuto):
 * ```
 * [10, 7, 8, 9, 1, 5]  pivot = 5
 *   i
 *   j
 * 
 * j=10: 10>5, skip
 * j=7:  7>5, skip
 * j=8:  8>5, skip
 * j=9:  9>5, skip
 * j=1:  1<5, swap with i, i++
 * [1, 7, 8, 9, 10, 5]
 *     i
 * Place pivot at i:
 * [1, 5, 8, 9, 10, 7]
 *     ↑ correct position!
 * 
 * Now recursively sort [1] and [8, 9, 10, 7]
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:
 * - Best Case: O(n log n) - Pivot divides evenly
 * - Average Case: O(n log n) - Random pivots
 * - Worst Case: O(n²) - Already sorted, always bad pivot
 * 
 * WHY O(n log n) average:
 * - Partitioning: O(n) work at each level
 * - Recursion depth: O(log n) on average
 * - Total: O(n) * O(log n) = O(n log n)
 * 
 * WHY O(n²) worst:
 * - Pivot always smallest/largest
 * - Recursion depth becomes O(n)
 * - Total: O(n) * O(n) = O(n²)
 * 
 * SPACE COMPLEXITY: O(log n)
 * - Recursion stack: O(log n) average, O(n) worst
 * - In-place sorting: O(1) auxiliary space
 * 
 * ADVANTAGES:
 * ✅ Fast in practice (often fastest)
 * ✅ In-place sorting (O(1) space)
 * ✅ Cache-friendly
 * ✅ Good average case O(n log n)
 * 
 * DISADVANTAGES:
 * ❌ Not stable (relative order may change)
 * ❌ Worst case O(n²)
 * ❌ Performance depends on pivot choice
 * 
 * ============================================================================
 */

package sorting

class QuickSort {
    
    /**
     * Quick sort main function
     * TIME: O(n log n) average, O(n²) worst
     * SPACE: O(log n) average, O(n) worst
     * 
     * @param arr Array to sort (modified in-place)
     */
    fun quickSort(arr: IntArray) {
        if (arr.size <= 1) return
        quickSortHelper(arr, 0, arr.size - 1)
    }
    
    /**
     * Recursive helper function
     * Sorts arr[low...high]
     */
    private fun quickSortHelper(arr: IntArray, low: Int, high: Int) {
        // Base case: single element or empty
        if (low >= high) return
        
        // Partition array and get pivot index
        val pivotIndex = partition(arr, low, high)
        
        // Recursively sort left partition
        quickSortHelper(arr, low, pivotIndex - 1)
        
        // Recursively sort right partition
        quickSortHelper(arr, pivotIndex + 1, high)
    }
    
    /**
     * Lomuto partition scheme
     * Chooses last element as pivot
     * Returns index of pivot after partitioning
     */
    private fun partition(arr: IntArray, low: Int, high: Int): Int {
        // Choose last element as pivot
        val pivot = arr[high]
        
        // Index of smaller element
        // Indicates right position for pivot
        var i = low - 1
        
        // Traverse array
        for (j in low until high) {
            // If current element is smaller than pivot
            if (arr[j] < pivot) {
                // Increment index of smaller element
                i++
                // Swap arr[i] and arr[j]
                val temp = arr[i]
                arr[i] = arr[j]
                arr[j] = temp
            }
        }
        
        // Place pivot in correct position
        i++
        val temp = arr[i]
        arr[i] = arr[high]
        arr[high] = temp
        
        return i
    }
    
    /**
     * Quick sort with random pivot (avoids worst case)
     * TIME: O(n log n) expected
     */
    fun quickSortRandom(arr: IntArray) {
        if (arr.size <= 1) return
        quickSortRandomHelper(arr, 0, arr.size - 1)
    }
    
    private fun quickSortRandomHelper(arr: IntArray, low: Int, high: Int) {
        if (low >= high) return
        
        // Choose random pivot and swap with last element
        val randomIndex = (low..high).random()
        val temp = arr[randomIndex]
        arr[randomIndex] = arr[high]
        arr[high] = temp
        
        val pivotIndex = partition(arr, low, high)
        quickSortRandomHelper(arr, low, pivotIndex - 1)
        quickSortRandomHelper(arr, pivotIndex + 1, high)
    }
    
    /**
     * Quick sort with median-of-three pivot
     * Better performance on partially sorted data
     */
    fun quickSortMedian(arr: IntArray) {
        if (arr.size <= 1) return
        quickSortMedianHelper(arr, 0, arr.size - 1)
    }
    
    private fun quickSortMedianHelper(arr: IntArray, low: Int, high: Int) {
        if (low >= high) return
        
        // Find median of first, middle, last
        val mid = low + (high - low) / 2
        
        // Sort these three elements
        if (arr[mid] < arr[low]) {
            arr[mid] = arr[low].also { arr[low] = arr[mid] }
        }
        if (arr[high] < arr[low]) {
            arr[high] = arr[low].also { arr[low] = arr[high] }
        }
        if (arr[high] < arr[mid]) {
            arr[high] = arr[mid].also { arr[mid] = arr[high] }
        }
        
        // Use middle as pivot (swap to end)
        arr[mid] = arr[high].also { arr[high] = arr[mid] }
        
        val pivotIndex = partition(arr, low, high)
        quickSortMedianHelper(arr, low, pivotIndex - 1)
        quickSortMedianHelper(arr, pivotIndex + 1, high)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH - Lomuto Partition
 * ============================================================================
 * 
 * INPUT: arr = [10, 7, 8, 9, 1, 5]
 * 
 * FIRST CALL: quickSort(arr, 0, 5)
 * 
 * PARTITION:
 * pivot = 5 (last element)
 * i = -1
 * 
 * j=0: arr[0]=10, 10>5, skip
 * j=1: arr[1]=7, 7>5, skip
 * j=2: arr[2]=8, 8>5, skip
 * j=3: arr[3]=9, 9>5, skip
 * j=4: arr[4]=1, 1<5
 *   i++ → i=0
 *   Swap arr[0] and arr[4]: [1, 7, 8, 9, 10, 5]
 * 
 * Place pivot:
 * i++ → i=1
 * Swap arr[1] and arr[5]: [1, 5, 8, 9, 10, 7]
 * 
 * Return pivotIndex = 1
 * 
 * NOW:
 * - Left: [1] (index 0)
 * - Pivot: 5 (index 1) ← IN CORRECT POSITION
 * - Right: [8, 9, 10, 7] (indices 2-5)
 * 
 * RECURSIVE CALLS:
 * quickSort([1], 0, 0) → base case, return
 * quickSort([8, 9, 10, 7], 2, 5) → continue sorting
 * 
 * Eventually: [1, 5, 7, 8, 9, 10] ✓
 * 
 * ============================================================================
 * PIVOT SELECTION STRATEGIES
 * ============================================================================
 * 
 * 1. **Last Element** (Lomuto): Simple but worst case on sorted
 * 2. **Random**: Avoids worst case, expected O(n log n)
 * 3. **Median-of-Three**: Good for partially sorted
 * 4. **First Element**: Simple but worst case on sorted
 * 5. **True Median**: Optimal but expensive to find
 * 
 * ============================================================================
 * COMPARISON WITH MERGE SORT
 * ============================================================================
 * 
 * QUICK SORT:
 * - Average: O(n log n)
 * - Worst: O(n²)
 * - Space: O(log n)
 * - In-place: Yes
 * - Stable: No
 * - Practical: Fastest average case
 * 
 * MERGE SORT:
 * - All cases: O(n log n)
 * - Space: O(n)
 * - In-place: No
 * - Stable: Yes
 * - Predictable performance
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = QuickSort()
    
    println("=== Quick Sort ===\n")
    
    // Test 1: Normal array
    val arr1 = intArrayOf(10, 7, 8, 9, 1, 5)
    println("Test 1: Normal Array")
    println("Before: ${arr1.contentToString()}")
    sorter.quickSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [1, 5, 7, 8, 9, 10]\n")
    
    // Test 2: Already sorted (worst case for basic pivot)
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: Already Sorted")
    println("Before: ${arr2.contentToString()}")
    sorter.quickSortRandom(arr2)  // Use random to avoid O(n²)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted")
    println("Before: ${arr3.contentToString()}")
    sorter.quickSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Duplicates
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6, 5)
    println("Test 4: With Duplicates")
    println("Before: ${arr4.contentToString()}")
    sorter.quickSort(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 5, 6, 9]\n")
    
    // Test 5: Negative numbers
    val arr5 = intArrayOf(-3, -1, -7, -4, -5, -2)
    println("Test 5: Negative Numbers")
    println("Before: ${arr5.contentToString()}")
    sorter.quickSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [-7, -5, -4, -3, -2, -1]\n")
    
    // Test 6: Compare pivot strategies
    println("Test 6: Pivot Strategy Comparison")
    val testArr = IntArray(1000) { (0..1000).random() }
    
    var arr6a = testArr.copyOf()
    var start = System.nanoTime()
    sorter.quickSort(arr6a)
    var time = (System.nanoTime() - start) / 1000
    println("Standard pivot: $time μs")
    
    var arr6b = testArr.copyOf()
    start = System.nanoTime()
    sorter.quickSortRandom(arr6b)
    time = (System.nanoTime() - start) / 1000
    println("Random pivot: $time μs")
    
    var arr6c = testArr.copyOf()
    start = System.nanoTime()
    sorter.quickSortMedian(arr6c)
    time = (System.nanoTime() - start) / 1000
    println("Median-of-three: $time μs\n")
    
    // Test 7: Large array
    println("Test 7: Performance Test")
    val largeArr = IntArray(10000) { (0..10000).random() }
    
    val perfStart = System.nanoTime()
    sorter.quickSortRandom(largeArr)
    val perfTime = (System.nanoTime() - perfStart) / 1000000
    
    println("Sorted 10,000 elements in $perfTime ms")
    println("First 10: ${largeArr.take(10)}")
    println("Last 10: ${largeArr.takeLast(10)}")
    println("Is sorted: ${largeArr.contentEquals(largeArr.sortedArray())}\n")
    
    println("Quick Sort is typically fastest in practice!")
}
