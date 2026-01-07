/**
 * ============================================================================
 * PROBLEM:  Find Minimum in Rotated Sorted Array
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE: #153
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Suppose an array of length n sorted in ascending order is rotated between 
 * 1 and n times. Find the minimum element in this rotated sorted array.
 * 
 * You must write an algorithm that runs in O(log n) time.
 * All values in the array are UNIQUE.
 * 
 * INPUT FORMAT:
 * - A rotated sorted array:  arr = [4, 5, 6, 7, 0, 1, 2]
 * 
 * OUTPUT FORMAT:
 * - Minimum element: 0
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 5000
 * - -5000 <= arr[i] <= 5000
 * - All values are unique
 * - Array was sorted then rotated 1 to n times
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Original:  [0, 1, 2, 4, 5, 6, 7]
 * Rotated:   [4, 5, 6, 7, 0, 1, 2]
 *                       ↑ minimum (pivot point)
 * 
 * KEY INSIGHT:
 * The minimum element is at the "rotation point" (pivot).
 * - Everything before pivot:  > arr[right]
 * - Everything after pivot: <= arr[right]
 * 
 * The minimum is the ONLY element where arr[i] < arr[i-1]. 
 * 
 * BINARY SEARCH LOGIC:
 * Compare arr[mid] with arr[right]: 
 * - If arr[mid] > arr[right]:  Minimum is in RIGHT half (left = mid + 1)
 * - If arr[mid] < arr[right]: Minimum is in LEFT half including mid (right = mid)
 * 
 * VISUAL EXAMPLE:
 * arr = [4, 5, 6, 7, 0, 1, 2]
 *        ↑ larger ↑ ↑smaller↑
 * 
 * Step 1: mid = 3, arr[mid] = 7
 *         7 > 2 (arr[right]), minimum in right
 * 
 * Step 2: mid = 5, arr[mid] = 1
 *         1 < 2 (arr[right]), minimum in left (including mid)
 * 
 * Step 3: mid = 4, arr[mid] = 0
 *         0 < 1 (arr[right]), minimum in left (including mid)
 * 
 * Step 4: left == right, found minimum = 0 ✓
 * 
 * WHY COMPARE WITH arr[right]?
 * - In a rotated array, the minimum divides two sorted parts
 * - Elements before minimum are > rightmost element
 * - Elements after minimum (including minimum) are <= rightmost element
 * - This gives us the direction to search! 
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right:  // Note: left < right, not left <= right
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] > arr[right]: 
 *       - Minimum is in right half
 *       - left = mid + 1
 *    c.  Else:  (arr[mid] < arr[right])
 *       - Minimum is in left half (including mid)
 *       - right = mid
 * 3. Return arr[left] (or arr[right], they're equal)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search eliminates half the space each iteration
 * - Maximum iterations: log₂(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space for pointers
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class FindMinRotated {
    
    /**
     * Find minimum element in rotated sorted array (no duplicates)
     * 
     * @param arr Rotated sorted array with unique elements
     * @return Minimum element value
     */
    fun findMin(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        // Binary search for minimum
        while (left < right) {  // Note: left < right (not <=)
            val mid = left + (right - left) / 2
            
            // Compare mid with right to determine which half contains minimum
            if (arr[mid] > arr[right]) {
                // Minimum is in right half
                // arr[mid] is part of the larger sorted portion
                // Example: [4, 5, 6, 7, 0, 1, 2], mid=7 > right=2
                left = mid + 1
            } else {
                // Minimum is in left half (including mid)
                // arr[mid] could be the minimum or minimum is to its left
                // Example: [4, 5, 6, 7, 0, 1, 2], mid=0 < right=2
                right = mid
            }
        }
        
        // When left == right, we've found the minimum
        return arr[left]
    }
    
    /**
     * Find index of minimum element (pivot index)
     */
    fun findMinIndex(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] > arr[right]) {
                left = mid + 1
            } else {
                right = mid
            }
        }
        
        return left
    }
    
    /**
     * Alternative approach:  Comparing with arr[left]
     * Less intuitive but also works
     */
    fun findMinAlternative(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        // If array is not rotated (already sorted)
        if (arr[left] < arr[right]) {
            return arr[left]
        }
        
        while (left < right) {
            val mid = left + (right - left) / 2
            
            // Check if mid is the minimum (pivot point)
            if (mid < arr. size - 1 && arr[mid] > arr[mid + 1]) {
                return arr[mid + 1]
            }
            
            // Check if mid-1 is the maximum (before pivot)
            if (mid > 0 && arr[mid - 1] > arr[mid]) {
                return arr[mid]
            }
            
            // Determine which half to search
            if (arr[mid] > arr[left]) {
                // Mid is in the left sorted portion
                left = mid + 1
            } else {
                // Mid is in the right sorted portion
                right = mid
            }
        }
        
        return arr[left]
    }
    
    /**
     * Check if array is rotated (not in original sorted order)
     */
    fun isRotated(arr: IntArray): Boolean {
        return arr[0] > arr[arr.size - 1]
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Standard rotation
 * INPUT: arr = [4, 5, 6, 7, 0, 1, 2]
 * 
 * Iteration 1:
 * - left = 0, right = 6, mid = 3
 * - arr[3] = 7, arr[6] = 2
 * - 7 > 2, minimum in right half
 * - left = 4
 * 
 * Iteration 2:
 * - left = 4, right = 6, mid = 5
 * - arr[5] = 1, arr[6] = 2
 * - 1 < 2, minimum in left half (including mid)
 * - right = 5
 * 
 * Iteration 3:
 * - left = 4, right = 5, mid = 4
 * - arr[4] = 0, arr[5] = 1
 * - 0 < 1, minimum in left half (including mid)
 * - right = 4
 * 
 * left == right = 4, exit
 * OUTPUT: arr[4] = 0 ✓
 * 
 * ---
 * 
 * Example 2: No rotation (sorted array)
 * INPUT: arr = [1, 2, 3, 4, 5]
 * 
 * Iteration 1:
 * - left = 0, right = 4, mid = 2
 * - arr[2] = 3, arr[4] = 5
 * - 3 < 5, minimum in left half
 * - right = 2
 * 
 * Iteration 2:
 * - left = 0, right = 2, mid = 1
 * - arr[1] = 2, arr[2] = 3
 * - 2 < 3, minimum in left half
 * - right = 1
 * 
 * Iteration 3:
 * - left = 0, right = 1, mid = 0
 * - arr[0] = 1, arr[1] = 2
 * - 1 < 2, minimum in left half
 * - right = 0
 * 
 * left == right = 0, exit
 * OUTPUT: arr[0] = 1 ✓
 * 
 * ---
 * 
 * Example 3: Rotated by 1 (minimum at end)
 * INPUT: arr = [2, 3, 4, 5, 1]
 * 
 * Binary search will converge to index 4
 * OUTPUT: 1 ✓
 * 
 * ---
 * 
 * Example 4: Single element
 * INPUT: arr = [1]
 * 
 * left = right = 0, no iterations
 * OUTPUT: 1 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. No rotation (sorted): [1,2,3,4,5] → 1
 * 2. Rotated by 1: [2,3,4,5,1] → 1
 * 3. Rotated by n-1: [2,1] → 1
 * 4. Single element: [1] → 1
 * 5. Two elements:  [2,1] → 1
 * 6. All negative: [-5,-4,-3,-2,-1] → -5
 * 7. Mixed positive/negative: [3,4,5,-2,-1,0,1,2] → -2
 * 8. Minimum at start: [1,2,3,4,5] → 1
 * 9. Minimum at end: [2,3,4,5,1] → 1
 * 10. Minimum in middle: [4,5,6,1,2,3] → 1
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using left <= right instead of left < right
 * ❌ while (left <= right)  // Can cause infinite loop
 * ✅ while (left < right)   // Correct for finding minimum
 * 
 * Why?  When left == right, we've found the minimum! 
 * 
 * MISTAKE 2: Comparing arr[mid] with arr[left]
 * ❌ if (arr[mid] > arr[left])  // Less intuitive, trickier logic
 * ✅ if (arr[mid] > arr[right])  // Clear:  minimum is in right if true
 * 
 * MISTAKE 3: Setting right = mid - 1 when arr[mid] < arr[right]
 * ❌ right = mid - 1  // Might skip the minimum! 
 * ✅ right = mid      // mid could be the minimum
 * 
 * MISTAKE 4: Not handling no-rotation case
 * The algorithm handles it automatically, but checking first can optimize: 
 * if (arr[0] < arr[n-1]) return arr[0]
 * 
 * MISTAKE 5: Trying to find minimum in one comparison
 * ❌ return Math.min(arr[0], arr[n-1])  // Wrong!  Minimum might be in middle
 * ✅ Use binary search to find pivot point
 * 
 * ============================================================================
 * WHY left < right, NOT left <= right? 
 * ============================================================================
 * 
 * USING left < right:
 * - Loop exits when left == right
 * - At this point, left (and right) point to the minimum
 * - Clean termination condition
 * 
 * USING left <= right:
 * - Would need to check arr[mid] == target explicitly
 * - For minimum finding, there's no specific target
 * - Can cause infinite loop if not careful with pointer updates
 * 
 * EXAMPLE WHY left <= right FAILS:
 * arr = [2, 1]
 * 
 * Iteration 1:
 * - left = 0, right = 1, mid = 0
 * - arr[0] = 2 > arr[1] = 1
 * - left = mid + 1 = 1
 * 
 * Now left == right == 1
 * With left <= right, we'd iterate again: 
 * - mid = 1
 * - arr[1] = 1, arr[1] = 1
 * - 1 > 1?  NO
 * - right = mid = 1
 * - INFINITE LOOP!
 * 
 * With left < right, we exit when left == right = 1 ✓
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER PROBLEMS
 * ============================================================================
 * 
 * THIS PROBLEM:
 * - Find minimum in rotated sorted array
 * 
 * RELATED: 
 * - Find minimum in rotated sorted array II (with duplicates)
 * - Search in rotated sorted array
 * - Find rotation count (same as find min index)
 * - Find peak element
 * 
 * APPLICATIONS:
 * 1. Finding pivot in quicksort
 * 2. Detecting rotation in circular sorted data
 * 3. Finding discontinuity in time-series data
 * 4. Load balancing (find least loaded server)
 * 
 * ============================================================================
 * FIND ROTATION COUNT
 * ============================================================================
 * 
 * OBSERVATION:
 * The index of minimum element = number of rotations! 
 * 
 * Original: [0, 1, 2, 3, 4, 5, 6]
 * Rotate 3:  [4, 5, 6, 0, 1, 2, 3]
 *                    ↑ index 3 = 3 rotations
 * 
 * So:  rotationCount = findMinIndex(arr)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val fmr = FindMinRotated()
    
    println("=== Testing Find Minimum in Rotated Sorted Array ===\n")
    
    // Test 1: Standard rotated array
    val arr1 = intArrayOf(4, 5, 6, 7, 0, 1, 2)
    println("Test 1: arr = ${arr1.contentToString()}")
    println("Minimum: ${fmr.findMin(arr1)}")
    println("Expected: 0\n")
    
    // Test 2: No rotation
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: No rotation - ${arr2.contentToString()}")
    println("Minimum: ${fmr.findMin(arr2)}")
    println("Expected: 1\n")
    
    // Test 3: Rotated by 1
    val arr3 = intArrayOf(2, 3, 4, 5, 1)
    println("Test 3: Rotated by 1 - ${arr3.contentToString()}")
    println("Minimum: ${fmr.findMin(arr3)}")
    println("Expected:  1\n")
    
    // Test 4: Single element
    val arr4 = intArrayOf(1)
    println("Test 4: Single element [1]")
    println("Minimum:  ${fmr.findMin(arr4)}")
    println("Expected: 1\n")
    
    // Test 5: Two elements
    val arr5 = intArrayOf(2, 1)
    println("Test 5: Two elements ${arr5.contentToString()}")
    println("Minimum: ${fmr.findMin(arr5)}")
    println("Expected: 1\n")
    
    // Test 6: Find index
    println("Test 6: Find minimum index in ${arr1.contentToString()}")
    println("Index:  ${fmr.findMinIndex(arr1)}")
    println("Expected: 4\n")
    
    // Test 7: Negative numbers
    val arr7 = intArrayOf(3, 4, 5, -2, -1, 0, 1, 2)
    println("Test 7: With negatives ${arr7.contentToString()}")
    println("Minimum: ${fmr.findMin(arr7)}")
    println("Expected: -2\n")
    
    // Test 8: All negative
    val arr8 = intArrayOf(-2, -1, -5, -4, -3)
    println("Test 8: All negative ${arr8.contentToString()}")
    println("Minimum: ${fmr.findMin(arr8)}")
    println("Expected: -5\n")
    
    // Test 9: Check if rotated
    println("Test 9: Is ${arr1.contentToString()} rotated? ")
    println("Result: ${fmr.isRotated(arr1)}")
    println("Expected: true")
    println("Is ${arr2.contentToString()} rotated?")
    println("Result: ${fmr.isRotated(arr2)}")
    println("Expected: false\n")
    
    // Test 10: Rotation count
    println("Test 10: Rotation count in ${arr1.contentToString()}")
    val rotations = fmr.findMinIndex(arr1)
    println("Rotations: $rotations")
    println("Expected: 4 (rotated 4 times)\n")
    
    // Test 11: Alternative method
    println("Test 11: Alternative method for ${arr1.contentToString()}")
    println("Minimum: ${fmr.findMinAlternative(arr1)}")
    println("Expected: 0\n")
}
