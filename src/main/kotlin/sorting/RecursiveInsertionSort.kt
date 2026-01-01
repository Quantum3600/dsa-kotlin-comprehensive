/**
 * ============================================================================
 * PROBLEM: Recursive Insertion Sort
 * DIFFICULTY: Easy
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement insertion sort using recursion instead of loops.
 * Insertion sort builds the final sorted array one item at a time,
 * inserting each element into its correct position in the sorted portion.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [12, 11, 13, 5, 6]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [5, 6, 11, 12, 13]
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^3 (limited by stack depth)
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of sorting playing cards:
 * - Pick first card (sorted)
 * - Pick second card, insert in correct position
 * - Pick third card, insert in correct position
 * - Continue until all cards sorted
 * 
 * Recursive version:
 * - Base case: Array of size 1 is sorted
 * - Recursive case:
 *   1. Recursively sort first n-1 elements
 *   2. Insert nth element in correct position
 * 
 * KEY INSIGHT:
 * If first n-1 elements are sorted, we just need to insert the nth element
 * in the correct position among those n-1 elements.
 * 
 * ALGORITHM STEPS:
 * 1. Base case: If n ≤ 1, return (sorted)
 * 2. Recursively sort first n-1 elements
 * 3. Insert nth element into its correct position
 * 
 * VISUAL EXAMPLE:
 * ```
 * Initial: [12, 11, 13, 5, 6]
 * 
 * insertionSort(arr, 1): [12] - base case, sorted
 * 
 * insertionSort(arr, 2):
 *   Sort first 1: [12]
 *   Insert 11: [11, 12]
 * 
 * insertionSort(arr, 3):
 *   Sort first 2: [11, 12]
 *   Insert 13: [11, 12, 13]
 * 
 * insertionSort(arr, 4):
 *   Sort first 3: [11, 12, 13]
 *   Insert 5: [5, 11, 12, 13]
 * 
 * insertionSort(arr, 5):
 *   Sort first 4: [5, 11, 12, 13]
 *   Insert 6: [5, 6, 11, 12, 13] ✓
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n²)
 * - Best Case: O(n) when already sorted
 * - Average Case: O(n²)
 * - Worst Case: O(n²) when reverse sorted
 * 
 * WHY O(n²):
 * - n recursive calls
 * - Each insertion can take up to O(n) time
 * - Total: O(n²)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion stack: O(n) depth
 * - Each recursive call uses O(1) space
 * - Total: O(n) space
 * 
 * Note: Iterative version uses O(1) space!
 * 
 * ADVANTAGES OF RECURSIVE VERSION:
 * ✅ Elegant and concise
 * ✅ Natural recursive structure
 * ✅ Good for learning recursion
 * ✅ Shows tail recursion optimization potential
 * 
 * DISADVANTAGES:
 * ❌ Uses O(n) stack space vs O(1) for iterative
 * ❌ Risk of stack overflow for large n
 * ❌ Slower due to function call overhead
 * ❌ Not practical for production use
 * 
 * ============================================================================
 */

package sorting

class RecursiveInsertionSort {
    
    /**
     * Recursive insertion sort
     * TIME: O(n²), SPACE: O(n)
     * 
     * @param arr Array to sort
     * @param n Number of elements to sort (default: full array)
     */
    fun insertionSort(arr: IntArray, n: Int = arr.size) {
        // Base case: array of size 0 or 1 is already sorted
        if (n <= 1) return
        
        // Recursively sort first n-1 elements
        insertionSort(arr, n - 1)
        
        // Insert the nth element (at index n-1) into sorted portion
        val last = arr[n - 1]
        var j = n - 2
        
        // Move elements greater than last one position ahead
        while (j >= 0 && arr[j] > last) {
            arr[j + 1] = arr[j]
            j--
        }
        
        // Insert last at its correct position
        arr[j + 1] = last
    }
    
    /**
     * Fully recursive insertion sort (no loops)
     * Uses helper function to replace while loop
     * TIME: O(n²), SPACE: O(n²) due to deeper recursion
     */
    fun insertionSortFullyRecursive(arr: IntArray, n: Int = arr.size) {
        // Base case
        if (n <= 1) return
        
        // Recursively sort first n-1 elements
        insertionSortFullyRecursive(arr, n - 1)
        
        // Insert nth element using recursive helper
        insertRecursive(arr, n - 1)
    }
    
    /**
     * Helper: Insert element at index into sorted portion recursively
     * Replaces the while loop
     */
    private fun insertRecursive(arr: IntArray, index: Int) {
        // Base case: at beginning or found correct position
        if (index <= 0 || arr[index] >= arr[index - 1]) {
            return
        }
        
        // Swap with previous element
        val temp = arr[index]
        arr[index] = arr[index - 1]
        arr[index - 1] = temp
        
        // Recursively move backwards
        insertRecursive(arr, index - 1)
    }
    
    /**
     * Tail-recursive version
     * Can be optimized by compiler to iterative
     */
    tailrec fun insertionSortTailRec(arr: IntArray, n: Int = arr.size) {
        // Base case
        if (n <= 1) return
        
        // Sort first n-1 elements
        insertionSortTailRec(arr, n - 1)
        
        // Insert nth element
        val last = arr[n - 1]
        var j = n - 2
        while (j >= 0 && arr[j] > last) {
            arr[j + 1] = arr[j]
            j--
        }
        arr[j + 1] = last
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [12, 11, 13, 5]
 * 
 * CALL TREE:
 * 
 * insertionSort(arr, 4)
 *   
 *   insertionSort(arr, 3)
 *     
 *     insertionSort(arr, 2)
 *       
 *       insertionSort(arr, 1)
 *         Base case: n=1, return
 *       
 *       Insert arr[1]=11 into [12]:
 *         last = 11, j = 0
 *         arr[0]=12 > 11, shift: [12, 12]
 *         arr[0] = 11: [11, 12, 13, 5]
 *     
 *     Insert arr[2]=13 into [11, 12]:
 *       last = 13, j = 1
 *       arr[1]=12 < 13, correct position
 *       Result: [11, 12, 13, 5]
 *   
 *   Insert arr[3]=5 into [11, 12, 13]:
 *     last = 5, j = 2
 *     arr[2]=13 > 5, shift: [11, 12, 13, 13]
 *     arr[1]=12 > 5, shift: [11, 12, 12, 13]
 *     arr[0]=11 > 5, shift: [11, 11, 12, 13]
 *     j = -1, insert at 0: [5, 11, 12, 13]
 * 
 * FINAL: [5, 11, 12, 13] ✓
 * 
 * ============================================================================
 * RECURSION PATTERN ANALYSIS
 * ============================================================================
 * 
 * BUBBLE SORT RECURSION:
 * - Sorts from end to beginning
 * - Each pass fixes one element at end
 * - Pattern: sort(n) = pass(n) + sort(n-1)
 * 
 * INSERTION SORT RECURSION:
 * - Sorts from beginning to end
 * - Each step extends sorted portion
 * - Pattern: sort(n) = sort(n-1) + insert(n)
 * 
 * MERGE SORT RECURSION:
 * - Divides in middle
 * - Sorts both halves
 * - Pattern: sort(n) = merge(sort(n/2), sort(n/2))
 * 
 * ============================================================================
 * WHEN TO USE RECURSIVE INSERTION SORT
 * ============================================================================
 * 
 * USE RECURSIVE VERSION:
 * ✓ Learning recursion concepts
 * ✓ Interview/academic exercises
 * ✓ Functional programming contexts
 * ✓ Small arrays (< 100 elements)
 * 
 * USE ITERATIVE VERSION:
 * ✓ Production code
 * ✓ Large arrays
 * ✓ Performance-critical applications
 * ✓ Memory-constrained environments
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = RecursiveInsertionSort()
    
    println("=== Recursive Insertion Sort ===\n")
    
    // Test 1: Normal array
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
    sorter.insertionSort(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted (Worst Case)")
    println("Before: ${arr3.contentToString()}")
    sorter.insertionSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Fully recursive version
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6)
    println("Test 4: Fully Recursive Version")
    println("Before: ${arr4.contentToString()}")
    sorter.insertionSortFullyRecursive(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 6, 9]\n")
    
    // Test 5: Tail recursive version
    val arr5 = intArrayOf(8, 3, 1, 7, 0, 10, 2)
    println("Test 5: Tail Recursive Version")
    println("Before: ${arr5.contentToString()}")
    sorter.insertionSortTailRec(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [0, 1, 2, 3, 7, 8, 10]\n")
    
    // Test 6: Single element
    val arr6 = intArrayOf(42)
    println("Test 6: Single Element")
    println("Before: ${arr6.contentToString()}")
    sorter.insertionSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [42]\n")
    
    // Test 7: Two elements
    val arr7 = intArrayOf(2, 1)
    println("Test 7: Two Elements")
    println("Before: ${arr7.contentToString()}")
    sorter.insertionSort(arr7)
    println("After:  ${arr7.contentToString()}")
    println("Expected: [1, 2]\n")
    
    // Test 8: Negative numbers
    val arr8 = intArrayOf(-3, -1, -7, -4, 0, 2)
    println("Test 8: Mixed Positive and Negative")
    println("Before: ${arr8.contentToString()}")
    sorter.insertionSort(arr8)
    println("After:  ${arr8.contentToString()}")
    println("Expected: [-7, -4, -3, -1, 0, 2]\n")
    
    // Test 9: Performance comparison
    println("Test 9: Recursion Depth Info")
    println("For array size n, recursion depth = n")
    println("Stack frame size: ~O(1) per call")
    println("Total stack space: O(n)")
    println("Recommended max size: < 1000 elements")
    println("Use iterative version for larger arrays!\n")
    
    println("Recursive insertion sort: Elegant but not practical for large arrays")
}
