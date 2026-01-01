/**
 * ============================================================================
 * PROBLEM: Binary Search
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a **sorted** array of integers and a target value, find the index of the
 * target in the array. If the target is not found, return -1.
 * 
 * INPUT FORMAT:
 * - A sorted array of integers: arr = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
 * - A target integer: target = 7
 * 
 * OUTPUT FORMAT:
 * - Index of target if found: 3
 * - -1 if target not found
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - Array is sorted in ascending order
 * - -10^9 <= arr[i], target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * When searching in a phone book, you don't start from the first page. You open
 * somewhere in the middle. If your name starts with 'M' and you see 'S', you know
 * to look in the left half. This is binary search!
 * 
 * KEY INSIGHT:
 * Since array is SORTED, we can eliminate half the search space in each step
 * by checking the middle element.
 * 
 * ALGORITHM STEPS:
 * 1. Set left pointer to start (0) and right pointer to end (n-1)
 * 2. While left <= right:
 *    a. Calculate mid = left + (right - left) / 2
 *    b. If arr[mid] == target, return mid (found!)
 *    c. If arr[mid] < target, target is in right half, set left = mid + 1
 *    d. If arr[mid] > target, target is in left half, set right = mid - 1
 * 3. If loop ends, target not found, return -1
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 3, 5, 7, 9, 11, 13, 15, 17, 19], target = 7
 * 
 * Step 1: [1, 3, 5, 7, 9, 11, 13, 15, 17, 19]
 *          L           M              R
 *          mid = 4, arr[4] = 9 > 7, go left
 * 
 * Step 2: [1, 3, 5, 7, 9]
 *          L     M     R
 *          mid = 2, arr[2] = 5 < 7, go right
 * 
 * Step 3: [7, 9]
 *          LM  R
 *          mid = 3, arr[3] = 7 == 7, FOUND!
 * 
 * WHY IT'S CALLED "BINARY":
 * Each step divides search space into 2 parts (bi = two)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search (iterative): O(log n) time, O(1) space - OPTIMAL
 * 2. Binary Search (recursive): O(log n) time, O(log n) space - Uses call stack
 * 3. Linear Search: O(n) time, O(1) space - Doesn't use sorted property
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(log n)
 * - Each iteration eliminates half the remaining elements
 * - If n = 16, max steps = 4 (log₂ 16 = 4)
 * - If n = 1024, max steps = 10 (log₂ 1024 = 10)
 * - If n = 1,000,000, max steps = 20 (log₂ 1,000,000 ≈ 20)
 * 
 * WHY log n:
 * n → n/2 → n/4 → n/8 → ... → 1
 * Number of divisions: log₂(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only uses 3 variables: left, right, mid
 * - No additional data structures
 * - Iterative approach doesn't use recursion stack
 * 
 * COMPARISON WITH LINEAR SEARCH:
 * For n = 1,000,000:
 * - Linear Search: Up to 1,000,000 comparisons
 * - Binary Search: Up to 20 comparisons
 * That's 50,000x faster!
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class BinarySearch {
    
    /**
     * Binary search - Iterative approach (RECOMMENDED)
     * 
     * @param arr Sorted array of integers
     * @param target Value to search for
     * @return Index of target if found, -1 otherwise
     */
    fun search(arr: IntArray, target: Int): Int {
        // Initialize left pointer at start of array
        // 'left' represents the leftmost boundary of our search space
        var left = 0
        
        // Initialize right pointer at end of array
        // 'right' represents the rightmost boundary of our search space
        var right = arr.size - 1
        
        // Continue searching while search space is valid
        // When left > right, we've exhausted all possibilities
        while (left <= right) {
            // Calculate middle index
            // We use left + (right - left) / 2 instead of (left + right) / 2
            // to avoid integer overflow when left and right are large
            // Example: left=2^30, right=2^30 → (left+right) could overflow
            val mid = left + (right - left) / 2
            
            // Check if middle element is our target
            if (arr[mid] == target) {
                // Found! Return the index
                return mid
            }
            
            // If middle element is less than target
            // Target must be in the right half of current search space
            // We eliminate left half including mid
            else if (arr[mid] < target) {
                // Move left boundary to mid + 1
                // We exclude mid because we already checked it
                left = mid + 1
            }
            
            // If middle element is greater than target
            // Target must be in the left half of current search space
            // We eliminate right half including mid
            else {
                // Move right boundary to mid - 1
                // We exclude mid because we already checked it
                right = mid - 1
            }
        }
        
        // If we exit the loop, target was not found in the array
        // Return -1 to indicate "not found"
        return -1
    }
    
    /**
     * Binary search - Recursive approach
     * More elegant but uses O(log n) space for call stack
     * 
     * @param arr Sorted array
     * @param target Value to find
     * @param left Left boundary (inclusive)
     * @param right Right boundary (inclusive)
     * @return Index of target or -1
     */
    fun searchRecursive(arr: IntArray, target: Int, left: Int = 0, right: Int = arr.size - 1): Int {
        // Base case: Invalid search space means target not found
        if (left > right) {
            return -1
        }
        
        // Calculate middle index
        val mid = left + (right - left) / 2
        
        // Target found at mid
        if (arr[mid] == target) {
            return mid
        }
        
        // Target in right half - search right
        if (arr[mid] < target) {
            return searchRecursive(arr, target, mid + 1, right)
        }
        
        // Target in left half - search left
        return searchRecursive(arr, target, left, mid - 1)
    }
    
    /**
     * Helper function to demonstrate binary search doesn't work on unsorted arrays
     * This will give incorrect results!
     */
    fun searchUnsorted(arr: IntArray, target: Int): Int {
        // Binary search requires sorted array
        // If array is unsorted, use linear search instead
        return search(arr, target)  // This will give wrong result!
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78], target = 23
 * 
 * EXECUTION TRACE:
 * 
 * Initial State:
 * - arr = [2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78]
 * - left = 0, right = 10, target = 23
 * 
 * Iteration 1:
 * - mid = 0 + (10-0)/2 = 5
 * - arr[5] = 23
 * - 23 == 23? YES! FOUND!
 * - Return 5
 * 
 * OUTPUT: 5 ✓
 * 
 * ---
 * 
 * Example 2: target = 56
 * 
 * Iteration 1:
 * - left = 0, right = 10
 * - mid = 5, arr[5] = 23
 * - 23 < 56? YES, search right
 * - left = 6, right = 10
 * 
 * Iteration 2:
 * - mid = 6 + (10-6)/2 = 8
 * - arr[8] = 56
 * - 56 == 56? YES! FOUND!
 * - Return 8
 * 
 * OUTPUT: 8 ✓
 * 
 * ---
 * 
 * Example 3: target = 100 (not in array)
 * 
 * Iteration 1:
 * - left = 0, right = 10, mid = 5
 * - arr[5] = 23 < 100, search right
 * - left = 6, right = 10
 * 
 * Iteration 2:
 * - mid = 8, arr[8] = 56 < 100, search right
 * - left = 9, right = 10
 * 
 * Iteration 3:
 * - mid = 9, arr[9] = 67 < 100, search right
 * - left = 10, right = 10
 * 
 * Iteration 4:
 * - mid = 10, arr[10] = 78 < 100, search right
 * - left = 11, right = 10
 * - left > right, exit loop
 * 
 * OUTPUT: -1 (not found) ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array []: left > right immediately, return -1
 * 2. Single element [42], target=42: Found at index 0
 * 3. Single element [42], target=10: Not found, return -1
 * 4. Target at start [1,2,3,4,5], target=1: Found at index 0
 * 5. Target at end [1,2,3,4,5], target=5: Found at index 4
 * 6. Target smaller than all: Returns -1
 * 7. Target larger than all: Returns -1
 * 8. Duplicate elements: Returns any valid index (usually first occurrence)
 * 9. Array with negative numbers: Works correctly
 * 10. Very large array: Still O(log n) - very fast!
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using (left + right) / 2
 * ❌ mid = (left + right) / 2  // Can overflow!
 * ✅ mid = left + (right - left) / 2  // Safe from overflow
 * 
 * MISTAKE 2: Wrong loop condition
 * ❌ while (left < right)  // Misses the case when left == right
 * ✅ while (left <= right)  // Correct
 * 
 * MISTAKE 3: Wrong pointer updates
 * ❌ left = mid or right = mid  // Infinite loop possible
 * ✅ left = mid + 1 or right = mid - 1  // Correct
 * 
 * MISTAKE 4: Using on unsorted array
 * ❌ Binary search on unsorted array gives wrong results!
 * ✅ Either sort first or use linear search
 * 
 * MISTAKE 5: Off-by-one errors
 * - Always use left + (right - left) / 2
 * - Remember: left and right are INCLUSIVE bounds
 * 
 * ============================================================================
 * WHEN TO USE BINARY SEARCH
 * ============================================================================
 * 
 * USE WHEN:
 * ✅ Array is sorted (or can be sorted efficiently)
 * ✅ Need to search many times (O(log n) per search)
 * ✅ Large dataset (binary search shines here)
 * ✅ Can afford O(1) space
 * ✅ Random access is O(1) (arrays, not linked lists)
 * 
 * DON'T USE WHEN:
 * ❌ Array is unsorted and can't be sorted
 * ❌ Only searching once (sorting cost > search benefit)
 * ❌ Small array (< 10 elements, linear search is fine)
 * ❌ Data structure doesn't support random access
 * ❌ Need to find multiple occurrences (requires modification)
 * 
 * ============================================================================
 * VARIATIONS OF BINARY SEARCH
 * ============================================================================
 * 
 * - Find first occurrence of element
 * - Find last occurrence of element
 * - Find floor (largest element ≤ target)
 * - Find ceiling (smallest element ≥ target)
 * - Search in rotated sorted array
 * - Search in 2D sorted matrix
 * - Find minimum in rotated sorted array
 * - Search in infinite array
 * - Binary search on answer (finding square root, etc.)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val bs = BinarySearch()
    
    println("=== Testing Binary Search ===\n")
    
    // Test array
    val arr = intArrayOf(2, 5, 8, 12, 16, 23, 38, 45, 56, 67, 78)
    println("Array: ${arr.contentToString()}\n")
    
    // Test 1: Element exists in middle
    println("Test 1: Search for 23")
    println("Result: ${bs.search(arr, 23)}")
    println("Expected: 5\n")
    
    // Test 2: Element at start
    println("Test 2: Search for 2")
    println("Result: ${bs.search(arr, 2)}")
    println("Expected: 0\n")
    
    // Test 3: Element at end
    println("Test 3: Search for 78")
    println("Result: ${bs.search(arr, 78)}")
    println("Expected: 10\n")
    
    // Test 4: Element not in array (too small)
    println("Test 4: Search for 1")
    println("Result: ${bs.search(arr, 1)}")
    println("Expected: -1\n")
    
    // Test 5: Element not in array (too large)
    println("Test 5: Search for 100")
    println("Result: ${bs.search(arr, 100)}")
    println("Expected: -1\n")
    
    // Test 6: Element not in array (in range)
    println("Test 6: Search for 30")
    println("Result: ${bs.search(arr, 30)}")
    println("Expected: -1\n")
    
    // Test 7: Single element array (found)
    val singleArr = intArrayOf(42)
    println("Test 7: Single element [42], search for 42")
    println("Result: ${bs.search(singleArr, 42)}")
    println("Expected: 0\n")
    
    // Test 8: Single element array (not found)
    println("Test 8: Single element [42], search for 10")
    println("Result: ${bs.search(singleArr, 10)}")
    println("Expected: -1\n")
    
    // Test 9: Using recursive version
    println("Test 9: Recursive search for 56")
    println("Result: ${bs.searchRecursive(arr, 56)}")
    println("Expected: 8\n")
    
    // Test 10: Large array performance demonstration
    val largeArr = IntArray(1000000) { it * 2 }  // [0, 2, 4, 6, ..., 1999998]
    println("Test 10: Search in array of 1 million elements")
    val startTime = System.nanoTime()
    val result = bs.search(largeArr, 999998)
    val endTime = System.nanoTime()
    println("Found at index: $result")
    println("Time taken: ${(endTime - startTime) / 1000} microseconds")
    println("(Binary search is VERY fast even for huge arrays!)\n")
}
