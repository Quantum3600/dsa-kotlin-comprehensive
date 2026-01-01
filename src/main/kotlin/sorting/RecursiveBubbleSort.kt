/**
 * ============================================================================
 * PROBLEM: Recursive Bubble Sort
 * DIFFICULTY: Easy
 * CATEGORY: Sorting
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Implement bubble sort using recursion instead of loops.
 * Bubble sort repeatedly compares adjacent elements and swaps them if they
 * are in the wrong order, with largest element "bubbling" to the end.
 * 
 * INPUT FORMAT:
 * - An unsorted array of integers: arr = [5, 1, 4, 2, 8]
 * 
 * OUTPUT FORMAT:
 * - The same array sorted in ascending order: [1, 2, 4, 5, 8]
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
 * Regular bubble sort uses nested loops:
 * - Outer loop: n-1 passes
 * - Inner loop: Compare and swap adjacent elements
 * 
 * Recursive version:
 * - Base case: Array of size 1 is sorted
 * - Recursive case:
 *   1. One pass to bubble largest to end
 *   2. Recursively sort remaining n-1 elements
 * 
 * KEY INSIGHT:
 * After one complete pass, the largest element is guaranteed to be at the end.
 * So we can recursively sort the first n-1 elements.
 * 
 * ALGORITHM STEPS:
 * 1. Base case: If n ≤ 1, return (sorted)
 * 2. Make one pass through array:
 *    - Swap adjacent elements if out of order
 * 3. Recursively sort first n-1 elements
 * 
 * VISUAL EXAMPLE:
 * ```
 * Initial: [5, 1, 4, 2, 8]
 * 
 * Pass 1 (n=5):
 *   Compare 5,1 → [1, 5, 4, 2, 8]
 *   Compare 5,4 → [1, 4, 5, 2, 8]
 *   Compare 5,2 → [1, 4, 2, 5, 8]
 *   Compare 5,8 → [1, 4, 2, 5, 8]
 *   8 is now in correct position
 * 
 * Recursive call (n=4):
 *   Sort [1, 4, 2, 5]
 *   After pass: [1, 2, 4, 5]
 *   5 is in correct position
 * 
 * ... and so on until base case
 * 
 * Final: [1, 2, 4, 5, 8] ✓
 * ```
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n²)
 * - Best Case: O(n) with optimization
 * - Average Case: O(n²)
 * - Worst Case: O(n²)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Recursion stack: O(n) depth
 * - Each recursive call uses O(1) space
 * - Total: O(n) space
 * 
 * Note: This is WORSE than iterative bubble sort which uses O(1) space!
 * 
 * ADVANTAGES OF RECURSIVE VERSION:
 * ✅ Educational value (understanding recursion)
 * ✅ Shows recursion can replace iteration
 * ✅ Cleaner conceptual model
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

class RecursiveBubbleSort {
    
    /**
     * Recursive bubble sort
     * TIME: O(n²), SPACE: O(n)
     * 
     * @param arr Array to sort
     * @param n Size of array to consider (default: full array)
     */
    fun bubbleSort(arr: IntArray, n: Int = arr.size) {
        // Base case: array of size 1 is already sorted
        if (n <= 1) return
        
        // One pass of bubble sort
        // After this pass, largest element is at end
        for (i in 0 until n - 1) {
            if (arr[i] > arr[i + 1]) {
                // Swap adjacent elements
                val temp = arr[i]
                arr[i] = arr[i + 1]
                arr[i + 1] = temp
            }
        }
        
        // Recursively sort first n-1 elements
        bubbleSort(arr, n - 1)
    }
    
    /**
     * Fully recursive bubble sort (no loops at all)
     * Uses helper function to replace inner loop
     * TIME: O(n²), SPACE: O(n²) due to deeper recursion
     */
    fun bubbleSortFullyRecursive(arr: IntArray, n: Int = arr.size) {
        // Base case
        if (n <= 1) return
        
        // One pass using recursion (replaces inner loop)
        bubblePass(arr, n, 0)
        
        // Recursively sort first n-1 elements
        bubbleSortFullyRecursive(arr, n - 1)
    }
    
    /**
     * Helper: Perform one bubble pass recursively
     * Replaces the inner loop
     */
    private fun bubblePass(arr: IntArray, n: Int, index: Int) {
        // Base case: reached end of pass
        if (index >= n - 1) return
        
        // Compare and swap if needed
        if (arr[index] > arr[index + 1]) {
            val temp = arr[index]
            arr[index] = arr[index + 1]
            arr[index + 1] = temp
        }
        
        // Move to next pair
        bubblePass(arr, n, index + 1)
    }
    
    /**
     * Optimized recursive bubble sort
     * Stops early if array becomes sorted
     * TIME: O(n²) worst, O(n) best, SPACE: O(n)
     */
    fun bubbleSortOptimized(arr: IntArray, n: Int = arr.size): Boolean {
        // Base case
        if (n <= 1) return true
        
        // Track if any swap occurred
        var swapped = false
        
        // One pass
        for (i in 0 until n - 1) {
            if (arr[i] > arr[i + 1]) {
                val temp = arr[i]
                arr[i] = arr[i + 1]
                arr[i + 1] = temp
                swapped = true
            }
        }
        
        // If no swap, array is sorted
        if (!swapped) return true
        
        // Recursively sort first n-1 elements
        return bubbleSortOptimized(arr, n - 1)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [5, 1, 4, 2, 8]
 * 
 * CALL TREE:
 * 
 * bubbleSort(arr, 5)
 *   Pass: [5,1,4,2,8] → [1,5,4,2,8] → [1,4,5,2,8] → [1,4,2,5,8] → [1,4,2,5,8]
 *   Result: [1, 4, 2, 5, 8]  (8 in position)
 *   
 *   bubbleSort(arr, 4)
 *     Pass: [1,4,2,5] → [1,4,2,5] → [1,2,4,5] → [1,2,4,5]
 *     Result: [1, 2, 4, 5, 8]  (5 in position)
 *     
 *     bubbleSort(arr, 3)
 *       Pass: [1,2,4] → [1,2,4] → [1,2,4]
 *       Result: [1, 2, 4, 5, 8]  (4 in position)
 *       
 *       bubbleSort(arr, 2)
 *         Pass: [1,2] → [1,2]
 *         Result: [1, 2, 4, 5, 8]  (2 in position)
 *         
 *         bubbleSort(arr, 1)
 *           Base case reached
 * 
 * FINAL: [1, 2, 4, 5, 8] ✓
 * 
 * RECURSION DEPTH: 4 (array size - 1)
 * 
 * ============================================================================
 * COMPARISON: RECURSIVE VS ITERATIVE
 * ============================================================================
 * 
 * ITERATIVE BUBBLE SORT:
 * - Time: O(n²)
 * - Space: O(1)
 * - No stack overflow risk
 * - Faster in practice
 * - RECOMMENDED for production
 * 
 * RECURSIVE BUBBLE SORT:
 * - Time: O(n²)
 * - Space: O(n)
 * - Stack overflow for large n
 * - Slower due to overhead
 * - Good for LEARNING recursion
 * 
 * ============================================================================
 * WHY USE RECURSIVE VERSION?
 * ============================================================================
 * 
 * 1. **Educational**: Learn recursion concepts
 * 2. **Pattern Recognition**: See how loops convert to recursion
 * 3. **Functional Programming**: More functional style
 * 4. **Interview Practice**: May be asked in interviews
 * 
 * NOT for production: Use iterative version instead!
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val sorter = RecursiveBubbleSort()
    
    println("=== Recursive Bubble Sort ===\n")
    
    // Test 1: Normal array
    val arr1 = intArrayOf(5, 1, 4, 2, 8)
    println("Test 1: Normal Array")
    println("Before: ${arr1.contentToString()}")
    sorter.bubbleSort(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [1, 2, 4, 5, 8]\n")
    
    // Test 2: Already sorted
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: Already Sorted (with optimization)")
    println("Before: ${arr2.contentToString()}")
    sorter.bubbleSortOptimized(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 3: Reverse sorted
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Reverse Sorted")
    println("Before: ${arr3.contentToString()}")
    sorter.bubbleSort(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [1, 2, 3, 4, 5]\n")
    
    // Test 4: Fully recursive version
    val arr4 = intArrayOf(3, 1, 4, 1, 5, 9, 2, 6)
    println("Test 4: Fully Recursive Version")
    println("Before: ${arr4.contentToString()}")
    sorter.bubbleSortFullyRecursive(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [1, 1, 2, 3, 4, 5, 6, 9]\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(42)
    println("Test 5: Single Element")
    println("Before: ${arr5.contentToString()}")
    sorter.bubbleSort(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [42]\n")
    
    // Test 6: Two elements
    val arr6 = intArrayOf(2, 1)
    println("Test 6: Two Elements")
    println("Before: ${arr6.contentToString()}")
    sorter.bubbleSort(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [1, 2]\n")
    
    // Test 7: Negative numbers
    val arr7 = intArrayOf(-3, -1, -7, -4)
    println("Test 7: Negative Numbers")
    println("Before: ${arr7.contentToString()}")
    sorter.bubbleSort(arr7)
    println("After:  ${arr7.contentToString()}")
    println("Expected: [-7, -4, -3, -1]\n")
    
    // Test 8: Stack depth warning
    println("Test 8: Stack Depth Warning")
    println("Recursive bubble sort uses O(n) stack space")
    println("For n=100, stack depth = 100")
    println("For n=1000, stack depth = 1000")
    println("Risk of StackOverflowError for very large arrays!\n")
    
    println("Note: Use iterative bubble sort for production code!")
}
