/**
 * ============================================================================
 * PROBLEM:  Find Number of Rotations in Rotated Sorted Array
 * DIFFICULTY: Easy-Medium
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given a rotated sorted array, find how many times the array has been rotated.
 * The rotation count is equal to the index of the minimum element. 
 * 
 * INPUT FORMAT:
 * - A rotated sorted array:  arr = [4, 5, 6, 7, 0, 1, 2]
 * 
 * OUTPUT FORMAT:
 * - Number of rotations: 4
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 5000
 * - -5000 <= arr[i] <= 5000
 * - All values are unique
 * - Array was sorted then rotated
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Original: [0, 1, 2, 3, 4, 5, 6]  (0 rotations)
 * Rotate 1: [1, 2, 3, 4, 5, 6, 0]  (minimum at index 6)
 * Rotate 2: [2, 3, 4, 5, 6, 0, 1]  (minimum at index 5)
 * Rotate 3: [3, 4, 5, 6, 0, 1, 2]  (minimum at index 4)
 * Rotate 4: [4, 5, 6, 0, 1, 2, 3]  (minimum at index 3)
 * 
 * Pattern:  Index of minimum = Number of rotations! 
 * 
 * KEY INSIGHT:
 * This problem is EXACTLY the same as "Find Minimum in Rotated Sorted Array"
 * but instead of returning the value, we return the index.
 * 
 * WHY?
 * - When we rotate an array k times, the element at index k becomes the first
 * - So the original first element (minimum) is now at index k
 * - Therefore:  rotation_count = index_of_minimum
 * 
 * ALGORITHM:
 * Use the same binary search as finding minimum, but return index instead of value. 
 * 
 * STEPS:
 * 1. Initialize left = 0, right = n-1
 * 2. While left < right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] > arr[right]: 
 *       - Minimum (and rotation point) is in right half
 *       - left = mid + 1
 *    c. Else:
 *       - Minimum is in left half (including mid)
 *       - right = mid
 * 3. Return left (index of minimum = rotation count)
 * 
 * VISUAL EXAMPLE:
 * arr = [15, 18, 2, 3, 6, 12]
 * 
 * This is [2, 3, 6, 12, 15, 18] rotated LEFT by 2
 * Or equivalently, rotated RIGHT by 4
 * 
 * Minimum element:  2 at index 2
 * Rotation count: 2 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search through the array
 * - Each iteration eliminates half the search space
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space for pointers
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class FindRotationCount {
    
    /**
     * Find the number of rotations in rotated sorted array
     * Returns the index of the minimum element
     * 
     * @param arr Rotated sorted array with unique elements
     * @return Number of rotations (index of minimum)
     */
    fun findRotationCount(arr: IntArray): Int {
        var left = 0
        var right = arr.size - 1
        
        // If array is not rotated (already sorted)
        if (arr[left] < arr[right]) {
            return 0
        }
        
        // Binary search for the rotation point (minimum element)
        while (left < right) {
            val mid = left + (right - left) / 2
            
            // If mid element is greater than rightmost element,
            // then minimum is in the right half
            if (arr[mid] > arr[right]) {
                left = mid + 1
            } else {
                // Minimum is in the left half (including mid)
                right = mid
            }
        }
        
        // When left == right, we've found the rotation point
        // The index itself is the number of rotations
        return left
    }
    
    /**
     * Alternative:  Find rotation count by checking each position
     * Less efficient but more intuitive
     */
    fun findRotationCountLinear(arr: IntArray): Int {
        // The rotation point is where arr[i] < arr[i-1]
        for (i in 1 until arr.size) {
            if (arr[i] < arr[i - 1]) {
                return i
            }
        }
        // No rotation found, array is sorted
        return 0
    }
    
    /**
     * Find rotation count with explicit minimum finding
     */
    fun findRotationCountExplicit(arr: IntArray): Int {
        var left = 0
        var right = arr. size - 1
        var minIndex = 0
        
        while (left <= right) {
            // If this portion is already sorted
            if (arr[left] <= arr[right]) {
                if (arr[left] < arr[minIndex]) {
                    minIndex = left
                }
                break
            }
            
            val mid = left + (right - left) / 2
            
            // Update minIndex if needed
            if (arr[mid] < arr[minIndex]) {
                minIndex = mid
            }
            
            // Check neighbors of mid
            val next = (mid + 1) % arr.size
            val prev = (mid - 1 + arr.size) % arr.size
            
            // If mid is the minimum
            if (arr[mid] <= arr[next] && arr[mid] <= arr[prev]) {
                return mid
            }
            
            // Decide which half to search
            if (arr[mid] >= arr[left]) {
                // Left half is sorted, go right
                left = mid + 1
            } else {
                // Right half is sorted, go left
                right = mid - 1
            }
        }
        
        return minIndex
    }
    
    /**
     * Verify the rotation count by reconstructing original array
     */
    fun verifyRotationCount(arr: IntArray, rotations: Int): Boolean {
        val n = arr.size
        // Check if rotating back gives us a sorted array
        for (i in 0 until n - 1) {
            val currentIdx = (i + rotations) % n
            val nextIdx = (i + 1 + rotations) % n
            if (arr[currentIdx] > arr[nextIdx]) {
                return false
            }
        }
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Standard rotation
 * INPUT: arr = [15, 18, 2, 3, 6, 12]
 * 
 * Original was:  [2, 3, 6, 12, 15, 18]
 * Rotated 2 times to get: [15, 18, 2, 3, 6, 12]
 * 
 * Binary Search:
 * Iteration 1:
 * - left = 0, right = 5, mid = 2
 * - arr[2] = 2, arr[5] = 12
 * - 2 < 12, minimum in left half (including mid)
 * - right = 2
 * 
 * Iteration 2:
 * - left = 0, right = 2, mid = 1
 * - arr[1] = 18, arr[2] = 2
 * - 18 > 2, minimum in right half
 * - left = 2
 * 
 * left == right = 2, exit
 * OUTPUT: 2 ✓
 * 
 * ---
 * 
 * Example 2: No rotation
 * INPUT: arr = [1, 2, 3, 4, 5]
 * 
 * Array is already sorted
 * arr[0] = 1 < arr[4] = 5
 * OUTPUT: 0 ✓
 * 
 * ---
 * 
 * Example 3: Maximum rotation
 * INPUT: arr = [5, 1, 2, 3, 4]
 * 
 * Original:  [1, 2, 3, 4, 5]
 * Rotated 4 times (or 1 time in reverse)
 * Minimum at index 1
 * OUTPUT: 1 ✓
 * 
 * ---
 * 
 * Example 4: Single element
 * INPUT: arr = [1]
 * 
 * No rotation possible
 * OUTPUT: 0 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. No rotation: [1,2,3,4,5] → 0
 * 2. Rotate by 1: [5,1,2,3,4] → 1
 * 3. Rotate by n-1: [2,3,4,5,1] → 4
 * 4. Single element: [1] → 0
 * 5. Two elements sorted: [1,2] → 0
 * 6. Two elements rotated:  [2,1] → 1
 * 7. All negative: [-5,-4,-3,-2,-1] → 0 (sorted)
 * 8. Mixed values: [3,4,5,-2,-1,0,1,2] → 3
 * 9. Large rotation: [4,5,6,7,8,9,0,1,2,3] → 6
 * 10. Minimum at start: [1,2,3,4] → 0
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Confusing left and right rotation
 * Left rotation by k = Right rotation by (n-k)
 * We typically count left rotations (standard convention)
 * 
 * MISTAKE 2: Using left <= right instead of left < right
 * ❌ while (left <= right) can cause issues
 * ✅ while (left < right) is correct for this problem
 * 
 * MISTAKE 3: Not handling sorted array case
 * ❌ Not checking if arr[0] < arr[n-1]
 * ✅ Early return 0 if already sorted
 * 
 * MISTAKE 4: Returning wrong value
 * ❌ return arr[left] // Returns minimum value, not count
 * ✅ return left // Returns index (rotation count)
 * 
 * MISTAKE 5: Confusing rotation direction
 * [1,2,3,4,5] → [4,5,1,2,3] is 3 LEFT rotations
 * But could also be 2 RIGHT rotations
 * We conventionally count left rotations
 * 
 * ============================================================================
 * UNDERSTANDING ROTATION
 * ============================================================================
 * 
 * LEFT ROTATION:
 * Original: [1, 2, 3, 4, 5]
 * Rotate left 1: [2, 3, 4, 5, 1]
 * Rotate left 2: [3, 4, 5, 1, 2]
 * Rotate left 3: [4, 5, 1, 2, 3]
 * 
 * RIGHT ROTATION:
 * Original: [1, 2, 3, 4, 5]
 * Rotate right 1: [5, 1, 2, 3, 4]
 * Rotate right 2: [4, 5, 1, 2, 3]
 * Rotate right 3: [3, 4, 5, 1, 2]
 * 
 * Notice: Left rotation by k = Right rotation by (n-k)
 * For n=5: Left 3 = Right 2
 * 
 * ============================================================================
 * MATHEMATICAL PROPERTY
 * ============================================================================
 * 
 * FORMULA: 
 * If array is rotated k times:
 * - arr[(i + k) % n] contains the element originally at index i
 * - Minimum element (originally at 0) is now at index k
 * - Therefore: rotation_count = index_of_minimum
 * 
 * VERIFICATION:
 * Original: [0, 1, 2, 3, 4]  (indices match values for clarity)
 * Rotate 3:  [3, 4, 0, 1, 2]
 * 
 * Element 0 (originally at index 0) is now at index 2
 * Element 1 (originally at index 1) is now at index 3
 * Element 2 (originally at index 2) is now at index 4
 * Element 3 (originally at index 3) is now at index 0
 * Element 4 (originally at index 4) is now at index 1
 * 
 * Pattern: new_index = (old_index + n - k) % n
 * Or: old_index = (new_index + k) % n
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER PROBLEMS
 * ============================================================================
 * 
 * SAME PROBLEM AS: 
 * - Find minimum in rotated sorted array (return index not value)
 * - Find pivot in rotated array
 * 
 * RELATED PROBLEMS:
 * - Search in rotated sorted array
 * - Check if array is rotated version of another
 * - Restore rotated array to original
 * 
 * APPLICATIONS:
 * 1. Log file analysis (find when logs rotated)
 * 2. Circular buffer management
 * 3. Time series data alignment
 * 4. Version control (find branch point)
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. LOG ROTATION:
 *    Server logs rotated daily.  Find how many days ago log started.
 * 
 * 2. CIRCULAR BUFFER:
 *    Find the head position in a circular buffer. 
 * 
 * 3. DATA RECOVERY:
 *    Reconstruct original data from rotated backup.
 * 
 * 4. NETWORK PACKETS:
 *    Packets received out of order, find starting packet.
 * 
 * 5. TIME ZONE CONVERSION:
 *    Find offset between time zones in cyclic time system.
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val frc = FindRotationCount()
    
    println("=== Testing Find Rotation Count ===\n")
    
    // Test 1: Standard rotation
    val arr1 = intArrayOf(15, 18, 2, 3, 6, 12)
    println("Test 1: arr = ${arr1.contentToString()}")
    val count1 = frc.findRotationCount(arr1)
    println("Rotation count:  $count1")
    println("Expected: 2")
    println("Verification: ${frc.verifyRotationCount(arr1, count1)}\n")
    
    // Test 2: No rotation
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    println("Test 2: No rotation - ${arr2.contentToString()}")
    val count2 = frc.findRotationCount(arr2)
    println("Rotation count: $count2")
    println("Expected: 0\n")
    
    // Test 3: Rotated by 1
    val arr3 = intArrayOf(5, 1, 2, 3, 4)
    println("Test 3: arr = ${arr3.contentToString()}")
    val count3 = frc.findRotationCount(arr3)
    println("Rotation count: $count3")
    println("Expected: 1\n")
    
    // Test 4: Rotated by n-1
    val arr4 = intArrayOf(2, 3, 4, 5, 1)
    println("Test 4: arr = ${arr4.contentToString()}")
    val count4 = frc. findRotationCount(arr4)
    println("Rotation count:  $count4")
    println("Expected: 4\n")
    
    // Test 5: Single element
    val arr5 = intArrayOf(1)
    println("Test 5: Single element [1]")
    val count5 = frc.findRotationCount(arr5)
    println("Rotation count: $count5")
    println("Expected: 0\n")
    
    // Test 6: Two elements
    val arr6 = intArrayOf(2, 1)
    println("Test 6: Two elements ${arr6.contentToString()}")
    val count6 = frc.findRotationCount(arr6)
    println("Rotation count: $count6")
    println("Expected: 1\n")
    
    // Test 7: Large array
    val arr7 = intArrayOf(4, 5, 6, 7, 0, 1, 2)
    println("Test 7: arr = ${arr7.contentToString()}")
    val count7 = frc.findRotationCount(arr7)
    println("Rotation count: $count7")
    println("Expected: 4\n")
    
    // Test 8: Negative numbers
    val arr8 = intArrayOf(3, 4, 5, -2, -1, 0, 1, 2)
    println("Test 8: With negatives ${arr8.contentToString()}")
    val count8 = frc.findRotationCount(arr8)
    println("Rotation count: $count8")
    println("Expected: 3\n")
    
    // Test 9: Using linear method (for comparison)
    println("Test 9: Linear method for ${arr1.contentToString()}")
    val countLinear = frc.findRotationCountLinear(arr1)
    println("Rotation count:  $countLinear")
    println("Expected: 2\n")
    
    // Test 10: Demonstrate reconstruction
    println("Test 10: Reconstruct original array")
    val rotated = intArrayOf(4, 5, 6, 7, 0, 1, 2)
    val rotCount = frc.findRotationCount(rotated)
    println("Rotated:  ${rotated.contentToString()}")
    println("Rotation count:  $rotCount")
    
    // Reconstruct original
    val original = IntArray(rotated.size)
    for (i in rotated.indices) {
        original[i] = rotated[(i + rotCount) % rotated.size]
    }
    println("Original (reconstructed): ${original.contentToString()}")
    println("Expected: [0, 1, 2, 4, 5, 6, 7]\n")
}
