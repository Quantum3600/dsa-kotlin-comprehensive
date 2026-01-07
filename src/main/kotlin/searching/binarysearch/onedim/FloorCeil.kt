/**
 * ============================================================================
 * PROBLEM: Floor and Ceiling in Sorted Array
 * DIFFICULTY: Easy
 * CATEGORY: Searching - Binary Search 1D
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the floor and ceiling of a target value in a sorted array.
 * - **Floor**:  Largest element in array that is <= target
 * - **Ceiling**: Smallest element in array that is >= target
 * 
 * INPUT FORMAT:
 * - A sorted array of integers:  arr = [1, 2, 8, 10, 12, 19]
 * - A target integer: target = 5
 * 
 * OUTPUT FORMAT:
 * - Floor:  2 (largest element <= 5)
 * - Ceiling: 8 (smallest element >= 5)
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
 * Think of finding a parking spot:
 * - Floor: The last spot before your desired location
 * - Ceiling: The first spot at or after your desired location
 * 
 * KEY INSIGHTS:
 * 1. **Ceiling** = Lower Bound (first element >= target)
 * 2. **Floor** = Largest element < ceiling (or element at lower bound - 1)
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 2, 8, 10, 12, 19], target = 5
 * 
 *        1   2   8   10  12  19
 *            ↑       ↑
 *          floor  ceiling
 *         (2<=5)  (8>=5)
 * 
 * ALGORITHM FOR FLOOR:
 * 1. Initialize left = 0, right = n-1, floor = -1
 * 2. While left <= right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] <= target:
 *       - This is a candidate for floor
 *       - Try to find larger floor on right
 *       - left = mid + 1
 *    c. Else arr[mid] > target:
 *       - Floor must be on left
 *       - right = mid - 1
 * 3. Return floor
 * 
 * ALGORITHM FOR CEILING:
 * 1. Initialize left = 0, right = n-1, ceiling = -1
 * 2. While left <= right:
 *    a. mid = left + (right - left) / 2
 *    b. If arr[mid] >= target:
 *       - This is a candidate for ceiling
 *       - Try to find smaller ceiling on left
 *       - right = mid - 1
 *    c. Else arr[mid] < target:
 *       - Ceiling must be on right
 *       - left = mid + 1
 * 3. Return ceiling
 * 
 * SPECIAL CASE: Target exists in array
 * If target = 8 and arr = [1, 2, 8, 10, 12, 19]:
 * - Floor = 8 (largest element <= 8 is 8 itself)
 * - Ceiling = 8 (smallest element >= 8 is 8 itself)
 * - Floor == Ceiling when target exists! 
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(log n)
 * - Binary search for floor:  O(log n)
 * - Binary search for ceiling: O(log n)
 * - Total: O(log n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only constant extra space
 * - No recursion or additional structures
 * 
 * ============================================================================
 */

package searching.binarysearch.onedim

class FloorCeil {
    
    /**
     * Find floor of target (largest element <= target)
     * 
     * @param arr Sorted array
     * @param target Value to find floor for
     * @return Floor value, or -1 if no floor exists (all elements > target)
     */
    fun findFloor(arr:  IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var floor = -1  // -1 indicates no floor exists
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] <= target) {
                // This element could be the floor
                // Store it and try to find a larger one on the right
                floor = arr[mid]
                left = mid + 1
            } else {
                // arr[mid] > target, floor must be on left
                right = mid - 1
            }
        }
        
        return floor
    }
    
    /**
     * Find ceiling of target (smallest element >= target)
     * 
     * @param arr Sorted array
     * @param target Value to find ceiling for
     * @return Ceiling value, or -1 if no ceiling exists (all elements < target)
     */
    fun findCeiling(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var ceiling = -1  // -1 indicates no ceiling exists
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] >= target) {
                // This element could be the ceiling
                // Store it and try to find a smaller one on the left
                ceiling = arr[mid]
                right = mid - 1
            } else {
                // arr[mid] < target, ceiling must be on right
                left = mid + 1
            }
        }
        
        return ceiling
    }
    
    /**
     * Find both floor and ceiling in one pass
     * More efficient than calling both functions separately
     * 
     * @return Pair<Floor, Ceiling>
     */
    fun findFloorAndCeiling(arr: IntArray, target: Int): Pair<Int, Int> {
        // Use lower bound approach for ceiling
        val ceiling = findCeiling(arr, target)
        
        // If ceiling exists and equals target, floor also equals target
        if (ceiling == target) {
            return Pair(target, target)
        }
        
        // Otherwise find floor separately
        val floor = findFloor(arr, target)
        
        return Pair(floor, ceiling)
    }
    
    /**
     * Alternative:  Find floor using index instead of value
     */
    fun findFloorIndex(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr.size - 1
        var floorIndex = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] <= target) {
                floorIndex = mid
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        
        return floorIndex
    }
    
    /**
     * Alternative: Find ceiling using index instead of value
     */
    fun findCeilingIndex(arr: IntArray, target: Int): Int {
        var left = 0
        var right = arr. size - 1
        var ceilingIndex = -1
        
        while (left <= right) {
            val mid = left + (right - left) / 2
            
            if (arr[mid] >= target) {
                ceilingIndex = mid
                right = mid - 1
            } else {
                left = mid + 1
            }
        }
        
        return ceilingIndex
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Target between elements
 * INPUT: arr = [1, 2, 8, 10, 12, 19], target = 5
 * 
 * FINDING FLOOR:
 * Iteration 1: mid = 2, arr[2] = 8 > 5, search left
 * Iteration 2: mid = 0, arr[0] = 1 <= 5, floor = 1, search right
 * Iteration 3: mid = 1, arr[1] = 2 <= 5, floor = 2, search right
 * Exit:  floor = 2 ✓
 * 
 * FINDING CEILING: 
 * Iteration 1: mid = 2, arr[2] = 8 >= 5, ceiling = 8, search left
 * Iteration 2: mid = 0, arr[0] = 1 < 5, search right
 * Iteration 3: mid = 1, arr[1] = 2 < 5, search right
 * Exit:  ceiling = 8 ✓
 * 
 * OUTPUT: Floor = 2, Ceiling = 8 ✓
 * 
 * ---
 * 
 * Example 2: Target exists in array
 * INPUT: arr = [1, 2, 8, 10, 12, 19], target = 8
 * 
 * FLOOR:  8 (largest element <= 8 is 8 itself)
 * CEILING: 8 (smallest element >= 8 is 8 itself)
 * 
 * OUTPUT: Floor = 8, Ceiling = 8 ✓
 * 
 * ---
 * 
 * Example 3: Target smaller than all elements
 * INPUT:  arr = [10, 20, 30, 40], target = 5
 * 
 * FLOOR: -1 (no element <= 5)
 * CEILING: 10 (smallest element >= 5)
 * 
 * OUTPUT: Floor = -1, Ceiling = 10 ✓
 * 
 * ---
 * 
 * Example 4: Target larger than all elements
 * INPUT: arr = [10, 20, 30, 40], target = 50
 * 
 * FLOOR: 40 (largest element <= 50)
 * CEILING: -1 (no element >= 50)
 * 
 * OUTPUT: Floor = 40, Ceiling = -1 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Both return -1
 * 2. Single element equal to target: Both return that element
 * 3. Single element greater than target: Floor = -1, Ceiling = element
 * 4. Single element less than target: Floor = element, Ceiling = -1
 * 5. Target smaller than all:  Floor = -1, Ceiling = first element
 * 6. Target larger than all: Floor = last element, Ceiling = -1
 * 7. All duplicates equal to target: Both return target
 * 8. All duplicates less than target: Floor = duplicate, Ceiling = -1
 * 9. All duplicates greater than target: Floor = -1, Ceiling = duplicate
 * 10. Negative numbers: Works correctly
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * MISTAKE 1: Confusing floor and ceiling definitions
 * ❌ Floor:  smallest element >= target (this is ceiling!)
 * ✅ Floor:  LARGEST element <= target
 * ❌ Ceiling: largest element <= target (this is floor!)
 * ✅ Ceiling:  SMALLEST element >= target
 * 
 * MISTAKE 2: Wrong comparison for floor
 * ❌ if (arr[mid] < target) // Excludes equal case
 * ✅ if (arr[mid] <= target) // Includes equal
 * 
 * MISTAKE 3: Wrong comparison for ceiling
 * ❌ if (arr[mid] > target) // Excludes equal case
 * ✅ if (arr[mid] >= target) // Includes equal
 * 
 * MISTAKE 4: Wrong pointer update for floor
 * ❌ if (arr[mid] <= target) right = mid - 1 // Searches left, wrong!
 * ✅ if (arr[mid] <= target) left = mid + 1 // Searches right for larger
 * 
 * MISTAKE 5: Not handling "not found" case
 * ❌ Returning 0 or garbage value
 * ✅ Returning -1 or appropriate sentinel value
 * 
 * ============================================================================
 * RELATIONSHIP WITH OTHER CONCEPTS
 * ============================================================================
 * 
 * FLOOR vs LOWER BOUND:
 * - Floor: Largest element <= target (returns VALUE)
 * - Lower Bound: First element >= target (returns INDEX)
 * - If target exists, floor MAY equal target
 * - Lower bound WILL point to target if it exists
 * 
 * CEILING vs LOWER BOUND:
 * - Ceiling: Smallest element >= target (returns VALUE)
 * - Lower Bound: First element >= target (returns INDEX)
 * - These are conceptually THE SAME! 
 * - Ceiling = arr[lowerBound(target)]
 * 
 * CEILING vs UPPER BOUND:
 * - Ceiling: Smallest element >= target (includes equal)
 * - Upper Bound:  First element > target (excludes equal)
 * 
 * FLOOR vs UPPER BOUND: 
 * - If target exists:  floor = target
 * - If target doesn't exist: floor = arr[upperBound(target) - 1]
 * 
 * ============================================================================
 * PRACTICAL APPLICATIONS
 * ============================================================================
 * 
 * 1. INTERPOLATION: 
 *    Find two closest values to interpolate between
 *    floor and ceiling give you the bounding values
 * 
 * 2. RANGE QUERIES:
 *    Find elements in range [L, R]
 *    Start = ceiling of L
 *    End = floor of R
 * 
 * 3. NEAREST NEIGHBOR:
 *    To find closest element to target: 
 *    Compare |floor - target| vs |ceiling - target|
 * 
 * 4. DATABASE QUERIES:
 *    SELECT * FROM table WHERE value >= ceiling AND value <= floor
 * 
 * 5. SCHEDULING:
 *    Find available time slot
 *    Floor = latest start time <= desired time
 *    Ceiling = earliest start time >= desired time
 * 
 * ============================================================================
 * WHEN TO USE FLOOR/CEILING
 * ============================================================================
 * 
 * USE FLOOR WHEN:
 * ✅ Need largest value not exceeding limit
 * ✅ Finding previous/predecessor element
 * ✅ Lower bound in interpolation
 * ✅ Maximum value within constraint
 * 
 * USE CEILING WHEN:
 * ✅ Need smallest value meeting minimum requirement
 * ✅ Finding next/successor element
 * ✅ Upper bound in interpolation
 * ✅ Minimum value satisfying condition
 * 
 * USE BOTH WHEN:
 * ✅ Finding nearest neighbors
 * ✅ Interpolation or approximation
 * ✅ Range queries
 * ✅ Finding closest values
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val fc = FloorCeil()
    
    println("=== Testing Floor and Ceiling ===\n")
    
    // Test array
    val arr = intArrayOf(1, 2, 8, 10, 12, 19)
    println("Array: ${arr.contentToString()}\n")
    
    // Test 1: Target between elements
    println("Test 1: Target = 5 (between elements)")
    println("Floor: ${fc.findFloor(arr, 5)}")
    println("Ceiling: ${fc.findCeiling(arr, 5)}")
    println("Expected: Floor = 2, Ceiling = 8\n")
    
    // Test 2: Target exists in array
    println("Test 2: Target = 8 (exists in array)")
    println("Floor: ${fc.findFloor(arr, 8)}")
    println("Ceiling: ${fc.findCeiling(arr, 8)}")
    println("Expected: Floor = 8, Ceiling = 8\n")
    
    // Test 3: Target smaller than all
    println("Test 3: Target = 0 (smaller than all)")
    println("Floor: ${fc.findFloor(arr, 0)}")
    println("Ceiling:  ${fc.findCeiling(arr, 0)}")
    println("Expected: Floor = -1, Ceiling = 1\n")
    
    // Test 4: Target larger than all
    println("Test 4: Target = 20 (larger than all)")
    println("Floor: ${fc.findFloor(arr, 20)}")
    println("Ceiling:  ${fc.findCeiling(arr, 20)}")
    println("Expected: Floor = 19, Ceiling = -1\n")
    
    // Test 5: Using findFloorAndCeiling
    println("Test 5: Find both at once for target = 5")
    val (floor, ceiling) = fc.findFloorAndCeiling(arr, 5)
    println("Floor: $floor, Ceiling:  $ceiling")
    println("Expected: Floor = 2, Ceiling = 8\n")
    
    // Test 6: Find indices
    println("Test 6: Find indices for target = 5")
    val floorIdx = fc.findFloorIndex(arr, 5)
    val ceilIdx = fc.findCeilingIndex(arr, 5)
    println("Floor index: $floorIdx (value:  ${if (floorIdx != -1) arr[floorIdx] else "N/A"})")
    println("Ceiling index: $ceilIdx (value:  ${if (ceilIdx != -1) arr[ceilIdx] else "N/A"})")
    println("Expected: Floor index = 1 (value 2), Ceiling index = 2 (value 8)\n")
    
    // Test 7: Negative numbers
    val arr2 = intArrayOf(-10, -5, -3, 0, 5, 10)
    println("Test 7: Array with negatives:  ${arr2.contentToString()}")
    println("Target = -4")
    println("Floor: ${fc.findFloor(arr2, -4)}")
    println("Ceiling: ${fc.findCeiling(arr2, -4)}")
    println("Expected:  Floor = -5, Ceiling = -3\n")
    
    // Test 8: Finding closest element
    println("Test 8: Find closest element to 7")
    val target = 7
    val floorVal = fc.findFloor(arr, target)
    val ceilVal = fc.findCeiling(arr, target)
    val closest = if (floorVal == -1) ceilVal
                  else if (ceilVal == -1) floorVal
                  else if (Math.abs(floorVal - target) <= Math.abs(ceilVal - target)) floorVal
                  else ceilVal
    println("Floor: $floorVal, Ceiling: $ceilVal")
    println("Closest: $closest")
    println("Expected:  Closest = 8 (distance 1 vs 2 from floor)\n")
}
