/**
 * ============================================================================
 * PROBLEM:  Find Peak Element
 * DIFFICULTY: Medium
 * CATEGORY: Searching - Binary Search 1D
 * LEETCODE: #162
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A peak element is an element that is strictly greater than its neighbors.
 * Given a 0-indexed integer array, find a peak element and return its index.
 * If the array contains multiple peaks, return the index of ANY peak. 
 * 
 * You may imagine that arr[-1] = arr[n] = -∞ (negative infinity).
 * You must write an algorithm that runs in O(log n) time.
 * 
 * INPUT FORMAT:
 * - An integer array:  arr = [1, 2, 3, 1]
 * 
 * OUTPUT FORMAT:
 * - Index of a peak element: 2 (element 3 is greater than neighbors 2 and 1)
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 1000
 * - -2^31 <= arr[i] <= 2^31 - 1
 * - arr[i] != arr[i+1] for all valid i
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Think of climbing a mountain - if you're going uphill, there MUST be a 
 * peak ahead!  You don't need to explore both directions, just go uphill.
 * 
 * KEY INSIGHT:
 * - If arr[mid] < arr[mid + 1]:  Peak is in RIGHT half (going uphill)
 * - If arr[mid] > arr[mid + 1]: Peak is in LEFT half (going downhill, peak before)
 * 
 * WHY THIS WORKS:
 * Since boundaries are -∞: 
 * - If we go uphill, we must reach a peak before hitting boundary
 * - If we go downhill, the peak we passed is in the opposite direction
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] < arr[mid + 1]:
 *       - Going uphill, peak is to the right
 *       - left = mid + 1
 *    c. Else:
 *       - Going downhill or at peak, peak is to the left (including mid)
 *       - right = mid
 * 3. Return left (or right, they're equal)
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 3, 1]
 * 
 * Step 1:    1   2   3   1
 *            L   M       R
 *            arr[mid]=2 < arr[mid+1]=3
 *            Going uphill!  Peak on right
 * 
 * Step 2:        3   1
 *                LM  R
 *                arr[mid]=3 > arr[mid+1]=1
 *                Going downhill! Peak on left (mid itself)
 * 
 * Step 3: left == right = 2, found peak at index 2 ✓
 * 
 * MULTIPLE PEAKS:
 * arr = [1, 3, 2, 4, 1]
 *          ↑       ↑
 *        peak1   peak2
 * 
 * Algorithm might return either 1 or 3 - both are valid!
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search halves the search space each iteration
 * - Maximum iterations: log₂(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space for pointers
 * - Iterative approach, no recursion
 * 
 * COMPARISON WITH LINEAR SEARCH:
 * Linear scan: O(n) - check each element
 * Binary search: O(log n) - much faster for large arrays
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class FindPeakElement {
    
    /**
     * Find a peak element in the array
     * 
     * @param arr Array of integers (no consecutive duplicates)
     * @return Index of any peak element
     */
    fun findPeakElement(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        while (left < right) {
            val mid = left + (right - left) / 2
            
            // Compare mid with its right neighbor
            if (arr[mid] < arr[mid + 1]) {
                // Going uphill, peak must be on the right
                // We exclude mid because it's smaller than mid+1
                left = mid + 1
            } else {
                // Going downhill or at peak
                // Peak could be at mid or to the left
                right = mid
            }
        }
        
        // When left == right, we've found a peak
        return left
    }
    
    /**
     * Alternative:  Compare with both neighbors explicitly
     * More intuitive but essentially the same logic
     */
    fun findPeakElementExplicit(arr: IntArray): Int {
        val n = arr.size
        
        // Edge cases
        if (n == 1) return 0
        if (arr[0] > arr[1]) return 0
        if (arr[n - 1] > arr[n - 2]) return n - 1
        
        // Search in the middle elements
        var left = 1
        var right = n - 2
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            // Check if mid is a peak
            if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
                return mid
            }
            
            // If left neighbor is greater, go left
            else if (arr[mid - 1] > arr[mid]) {
                right = mid - 1
            }
            
            // If right neighbor is greater, go right
            else {
                left = mid + 1
            }
        }
        
        return -1 // Should never reach here
    }
    
    /**
     * Recursive approach
     */
    fun findPeakElementRecursive(arr: IntArray, left: Int = 0, right: Int = arr.size - 1): Int {
        if (left == right) {
            return left
        }
        
        val mid = left + (right - left) / 2
        
        return if (arr[mid] < arr[mid + 1]) {
            // Go right
            findPeakElementRecursive(arr, mid + 1, right)
        } else {
            // Go left
            findPeakElementRecursive(arr, left, mid)
        }
    }
    
    /**
     * Linear search approach (for comparison)
     * O(n) time complexity
     */
    fun findPeakElementLinear(arr:  IntArray): Int {
        for (i in 0 until arr.size - 1) {
            if (arr[i] > arr[i + 1]) {
                return i
            }
        }
        // If no peak found, last element is peak
        return arr. size - 1
    }
    
    /**
     * Find all peaks in the array (not required, but useful)
     */
    fun findAllPeaks(arr: IntArray): List<Int> {
        val peaks = mutableListOf<Int>()
        
        for (i in arr.indices) {
            val leftSmaller = (i == 0 || arr[i] > arr[i - 1])
            val rightSmaller = (i == arr.size - 1 || arr[i] > arr[i + 1])
            
            if (leftSmaller && rightSmaller) {
                peaks. add(i)
            }
        }
        
        return peaks
    }
    
    /**
     * Verify if given index is a peak
     */
    fun isPeak(arr: IntArray, index: Int): Boolean {
        val n = arr.size
        
        if (index < 0 || index >= n) return false
        
        val leftOk = (index == 0 || arr[index] > arr[index - 1])
        val rightOk = (index == n - 1 || arr[index] > arr[index + 1])
        
        return leftOk && rightOk
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Single peak in middle
 * INPUT: arr = [1, 2, 3, 1]
 * 
 * Iteration 1:
 * - left = 0, right = 3, mid = 1
 * - arr[1] = 2, arr[2] = 3
 * - 2 < 3, go right
 * - left = 2
 * 
 * Iteration 2:
 * - left = 2, right = 3, mid = 2
 * - arr[2] = 3, arr[3] = 1
 * - 3 > 1, go left (or stay)
 * - right = 2
 * 
 * left == right = 2, exit
 * OUTPUT: 2 ✓
 * 
 * ---
 * 
 * Example 2: Multiple peaks
 * INPUT: arr = [1, 2, 1, 3, 5, 6, 4]
 * 
 * Possible peaks: index 1 (value 2), index 5 (value 6)
 * Algorithm will find ONE of them (likely 5)
 * OUTPUT: 5 or 1 (both valid) ✓
 * 
 * ---
 * 
 * Example 3: Peak at start
 * INPUT: arr = [5, 4, 3, 2, 1]
 * 
 * All elements decreasing
 * First element is peak
 * OUTPUT: 0 ✓
 * 
 * ---
 * 
 * Example 4: Peak at end
 * INPUT: arr = [1, 2, 3, 4, 5]
 * 
 * All elements increasing
 * Last element is peak
 * OUTPUT: 4 ✓
 * 
 * ---
 * 
 * Example 5: Single element
 * INPUT: arr = [1]
 * 
 * Single element is always a peak
 * OUTPUT: 0 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Single element: [1] → 0 (always a peak)
 * 2. Two elements increasing: [1, 2] → 1
 * 3. Two elements decreasing: [2, 1] → 0
 * 4. All increasing: [1,2,3,4,5] → 4 (last element)
 * 5. All decreasing: [5,4,3,2,1] → 0 (first element)
 * 6. Multiple peaks: Returns any one
 * 7. Peak at start:  Handled correctly
 * 8. Peak at end: Handled correctly
 * 9. Negative numbers: Works correctly
 * 10. Large values: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Using left <= right instead of left < right
 * ❌ while (left <= right) might need extra checks
 * ✅ while (left < right) is cleaner for this problem
 * 
 * MISTAKE 2: Not handling boundary conditions
 * ❌ Forgetting arr[-1] and arr[n] are considered -∞
 * ✅ Understanding boundaries ensures algorithm works
 * 
 * MISTAKE 3: Trying to find ALL peaks with binary search
 * ❌ Binary search finds ONE peak, not all
 * ✅ For all peaks, use linear scan O(n)
 * 
 * MISTAKE 4: Wrong pointer update
 * ❌ if (arr[mid] < arr[mid+1]) right = mid // Wrong direction! 
 * ✅ if (arr[mid] < arr[mid+1]) left = mid + 1 // Go uphill
 * 
 * MISTAKE 5: Comparing with mid-1 without bounds check
 * ❌ if (arr[mid] > arr[mid-1] && .. .) // mid could be 0! 
 * ✅ Check boundaries or use the simpler comparison with mid+1 only
 * 
 * ============================================================================
 * WHY ALGORITHM ALWAYS FINDS A PEAK
 * ============================================================================
 * 
 * PROOF BY CONTRADICTION:
 * Suppose we never find a peak. 
 * 
 * - We start with full array [0, n-1]
 * - At each step, we choose a direction based on slope
 * - If going uphill, we move right
 * - Eventually we hit right boundary (arr[n-1])
 * - Since arr[n] = -∞, arr[n-1] > arr[n]
 * - So arr[n-1] MUST be a peak!  Contradiction.
 * 
 * Similarly for going left - we hit arr[0] which is > arr[-1] = -∞
 * 
 * Therefore, algorithm ALWAYS finds a peak.
 * 
 * ============================================================================
 * WHY WE DON'T NEED TO EXPLORE BOTH SIDES
 * ============================================================================
 * 
 * INTUITION:
 * If I'm at position mid and going uphill (arr[mid] < arr[mid+1]):
 * - There MUST be a peak ahead (to the right)
 * - Because we'll either keep going up and hit boundary (boundary is peak)
 * - Or we'll eventually go down, meaning we passed a peak
 * 
 * We don't need to check the left side because: 
 * - We're guaranteed to find a peak on the right
 * - The problem only asks for ONE peak, not all peaks
 * - This makes binary search possible! 
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER PROBLEMS
 * ============================================================================
 * 
 * SIMILAR PROBLEMS:
 * - Find peak element in 2D array
 * - Find local maxima in array
 * - Find bitonic point (max in bitonic array)
 * - Mountain array peak index
 * 
 * APPLICATIONS:
 * 1. Signal processing (find peaks in waveforms)
 * 2. Data analysis (find local maxima in time series)
 * 3. Image processing (find bright spots)
 * 4. Stock market (find high points)
 * 5. Geography (find mountain peaks in elevation data)
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val fpe = FindPeakElement()
    
    println("=== Testing Find Peak Element ===\n")
    
    // Test 1: Peak in middle
    val arr1 = intArrayOf(1, 2, 3, 1)
    println("Test 1: arr = ${arr1.contentToString()}")
    val peak1 = fpe.findPeakElement(arr1)
    println("Peak at index:  $peak1 (value: ${arr1[peak1]})")
    println("Is peak? ${fpe.isPeak(arr1, peak1)}")
    println("Expected: index 2, value 3\n")
    
    // Test 2: Multiple peaks
    val arr2 = intArrayOf(1, 2, 1, 3, 5, 6, 4)
    println("Test 2: Multiple peaks - ${arr2.contentToString()}")
    val peak2 = fpe.findPeakElement(arr2)
    println("Peak at index: $peak2 (value: ${arr2[peak2]})")
    println("All peaks: ${fpe.findAllPeaks(arr2)}")
    println("Expected: index 1 or 5\n")
    
    // Test 3: Peak at start
    val arr3 = intArrayOf(5, 4, 3, 2, 1)
    println("Test 3: Peak at start - ${arr3.contentToString()}")
    val peak3 = fpe.findPeakElement(arr3)
    println("Peak at index: $peak3 (value: ${arr3[peak3]})")
    println("Expected: index 0\n")
    
    // Test 4: Peak at end
    val arr4 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 4: Peak at end - ${arr4.contentToString()}")
    val peak4 = fpe.findPeakElement(arr4)
    println("Peak at index: $peak4 (value: ${arr4[peak4]})")
    println("Expected: index 4\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(1)
    println("Test 5: Single element [1]")
    val peak5 = fpe.findPeakElement(arr5)
    println("Peak at index: $peak5")
    println("Expected: index 0\n")
    
    // Test 6: Two elements
    val arr6 = intArrayOf(1, 2)
    println("Test 6: Two elements ${arr6.contentToString()}")
    val peak6 = fpe.findPeakElement(arr6)
    println("Peak at index: $peak6 (value: ${arr6[peak6]})")
    println("Expected: index 1\n")
    
    // Test 7: Two elements decreasing
    val arr7 = intArrayOf(2, 1)
    println("Test 7: Two elements decreasing ${arr7.contentToString()}")
    val peak7 = fpe.findPeakElement(arr7)
    println("Peak at index: $peak7 (value: ${arr7[peak7]})")
    println("Expected: index 0\n")
    
    // Test 8: Explicit method
    println("Test 8: Using explicit method for ${arr1.contentToString()}")
    val peak8 = fpe. findPeakElementExplicit(arr1)
    println("Peak at index: $peak8")
    println("Expected: index 2\n")
    
    // Test 9: Recursive method
    println("Test 9: Using recursive method for ${arr1.contentToString()}")
    val peak9 = fpe. findPeakElementRecursive(arr1)
    println("Peak at index: $peak9")
    println("Expected: index 2\n")
    
    // Test 10: Linear search comparison
    println("Test 10: Linear search for ${arr2.contentToString()}")
    val peakLinear = fpe.findPeakElementLinear(arr2)
    println("Peak at index: $peakLinear (value: ${arr2[peakLinear]})")
    println("Note: Linear search finds first peak from left\n")
    
    // Test 11: Negative numbers
    val arr11 = intArrayOf(-10, -5, -3, -8, -12)
    println("Test 11: With negatives ${arr11.contentToString()}")
    val peak11 = fpe.findPeakElement(arr11)
    println("Peak at index: $peak11 (value: ${arr11[peak11]})")
    println("Expected: index 2 (value -3)\n")
    
    // Test 12: Complex case
    val arr12 = intArrayOf(1, 3, 20, 4, 1, 0)
    println("Test 12: Complex case ${arr12.contentToString()}")
    val peak12 = fpe.findPeakElement(arr12)
    println("Peak at index: $peak12 (value: ${arr12[peak12]})")
    println("All peaks: ${fpe.findAllPeaks(arr12)}")
    println("Expected: index 2\n")
}
