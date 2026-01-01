/**
 * ============================================================================
 * PROBLEM: Largest Element in Array
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, find and return the largest element in the array.
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [1, 5, 3, 9, 2]
 * 
 * OUTPUT FORMAT:
 * - A single integer representing the largest element: 9
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To find the largest element, we need to compare each element with the current
 * maximum we've seen so far. We start by assuming the first element is the largest,
 * then iterate through the array updating our maximum whenever we find a larger value.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize max variable with first element
 * 2. Iterate through each element in the array
 * 3. For each element, compare with current max
 * 4. If element > max, update max
 * 5. Return max after iteration completes
 * 
 * VISUAL EXAMPLE:
 * arr = [1, 5, 3, 9, 2]
 *        ↓
 *     max = 1
 *           ↓
 *        max = 5 (updated)
 *              ↓
 *           max = 5 (no change)
 *                 ↓
 *              max = 9 (updated)
 *                    ↓
 *                 max = 9 (no change)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Linear scan (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Using sorting: O(n log n) time, O(1) space - Not optimal
 * 3. Using built-in maxOrNull(): O(n) time, O(1) space - Same complexity
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We iterate through the array exactly once
 * - n is the number of elements in the array
 * - Each comparison operation takes O(1) time
 * - Total: O(n) * O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - We only use one extra variable (max) regardless of input size
 * - No additional data structures created
 * - No recursive calls that would use stack space
 * 
 * WHERE THIS IS OPTIMAL:
 * This is the optimal solution because we must examine every element at least
 * once to determine the maximum, so we cannot do better than O(n) time.
 * 
 * ============================================================================
 */

package arrays.easy

class LargestElement {
    
    /**
     * Finds the largest element in the given array
     * 
     * @param arr The input array of integers
     * @return The largest integer in the array
     * @throws IllegalArgumentException if array is empty
     */
    fun findLargest(arr: IntArray): Int {
        // Edge case: Check if array is empty
        // We throw exception because there's no valid answer for empty array
        require(arr.isNotEmpty()) { "Array cannot be empty" }
        
        // Initialize max with the first element of array
        // We start with first element as our baseline for comparison
        // Alternative: Could use Int.MIN_VALUE but first element is more intuitive
        var max = arr[0]
        
        // Iterate through array starting from second element (index 1)
        // We start from index 1 because we already considered arr[0] as initial max
        // 'i' represents current index we're examining
        for (i in 1 until arr.size) {
            // Get current element for readability
            // 'currentElement' stores the value we're comparing against max
            val currentElement = arr[i]
            
            // Compare current element with our current maximum
            // If current element is greater, we've found a new maximum
            if (currentElement > max) {
                // Update max to the new larger value
                // This ensures max always holds the largest value we've seen so far
                max = currentElement
            }
            // If currentElement <= max, we don't need to do anything
            // max remains unchanged and we continue to next element
        }
        
        // After checking all elements, max contains the largest value
        // Return this value to the caller
        return max
    }
    
    /**
     * Alternative approach using Kotlin's built-in function
     * Same time and space complexity, but more concise
     */
    fun findLargestBuiltIn(arr: IntArray): Int {
        require(arr.isNotEmpty()) { "Array cannot be empty" }
        // maxOrNull() returns the largest element or null if empty
        // !! asserts non-null (safe because we checked isEmpty above)
        return arr.maxOrNull()!!
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [1, 5, 3, 9, 2]
 * 
 * EXECUTION TRACE:
 * 
 * Initial State:
 * - arr = [1, 5, 3, 9, 2]
 * - max = 1 (arr[0])
 * 
 * Iteration 1 (i=1):
 * - currentElement = arr[1] = 5
 * - Compare: 5 > 1? YES
 * - Update: max = 5
 * - State: max = 5
 * 
 * Iteration 2 (i=2):
 * - currentElement = arr[2] = 3
 * - Compare: 3 > 5? NO
 * - No update
 * - State: max = 5
 * 
 * Iteration 3 (i=3):
 * - currentElement = arr[3] = 9
 * - Compare: 9 > 5? YES
 * - Update: max = 9
 * - State: max = 9
 * 
 * Iteration 4 (i=4):
 * - currentElement = arr[4] = 2
 * - Compare: 2 > 9? NO
 * - No update
 * - State: max = 9
 * 
 * Loop ends, return max = 9
 * 
 * OUTPUT: 9 ✓
 * 
 * ============================================================================
 * EDGE CASES HANDLED
 * ============================================================================
 * 
 * 1. Empty array: Throws IllegalArgumentException
 * 2. Single element [42]: Returns 42
 * 3. All elements same [7, 7, 7]: Returns 7
 * 4. Negative numbers [-5, -2, -10]: Returns -2
 * 5. Large numbers: Works up to Int.MAX_VALUE
 * 6. Largest at start [9, 1, 2, 3]: Returns 9
 * 7. Largest at end [1, 2, 3, 9]: Returns 9
 * 
 * ============================================================================
 * TEST CASES
 * ============================================================================
 */

fun main() {
    val solver = LargestElement()
    
    // Test case 1: Normal case with positive numbers
    println("Test 1: ${solver.findLargest(intArrayOf(1, 5, 3, 9, 2))}")  // Expected: 9
    
    // Test case 2: Single element
    println("Test 2: ${solver.findLargest(intArrayOf(42))}")  // Expected: 42
    
    // Test case 3: Negative numbers
    println("Test 3: ${solver.findLargest(intArrayOf(-5, -2, -10))}")  // Expected: -2
    
    // Test case 4: All elements same
    println("Test 4: ${solver.findLargest(intArrayOf(7, 7, 7))}")  // Expected: 7
    
    // Test case 5: Largest at beginning
    println("Test 5: ${solver.findLargest(intArrayOf(100, 1, 2, 3))}")  // Expected: 100
    
    // Test case 6: Largest at end
    println("Test 6: ${solver.findLargest(intArrayOf(1, 2, 3, 100))}")  // Expected: 100
    
    // Test case 7: Mix of positive and negative
    println("Test 7: ${solver.findLargest(intArrayOf(-1, 0, 1, 2))}")  // Expected: 2
    
    // Test case 8: Using built-in method
    println("Test 8: ${solver.findLargestBuiltIn(intArrayOf(1, 5, 3, 9, 2))}")  // Expected: 9
    
    // Test case 9: Edge case - would throw exception
    // Uncomment to see exception:
    // println("Test 9: ${solver.findLargest(intArrayOf())}")
}
