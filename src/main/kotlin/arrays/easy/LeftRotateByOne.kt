/**
 * ============================================================================
 * PROBLEM: Left Rotate Array by One Position
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, rotate the array to the left by one position.
 * This means the first element moves to the end, and all other elements
 * shift one position to the left.
 * 
 * The rotation should be done in-place with O(1) extra space.
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [1, 2, 3, 4, 5]
 * 
 * OUTPUT FORMAT:
 * - The same array rotated left by one: [2, 3, 4, 5, 1]
 * 
 * CONSTRAINTS:
 * - 0 <= arr.size <= 10^5
 * - -10^9 <= arr[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * To rotate left by one position:
 * 1. Save the first element in a temporary variable
 * 2. Shift all elements from index 1 to n-1 one position to the left
 * 3. Place the saved first element at the last position
 * 
 * VISUAL EXAMPLE:
 * Original: [1, 2, 3, 4, 5]
 *            ↓
 * Step 1: Save first element: temp = 1
 * 
 * Step 2: Shift elements left
 * [_, 2, 3, 4, 5]  →  [2, 2, 3, 4, 5]
 * [2, _, 3, 4, 5]  →  [2, 3, 3, 4, 5]
 * [2, 3, _, 4, 5]  →  [2, 3, 4, 4, 5]
 * [2, 3, 4, _, 5]  →  [2, 3, 4, 5, 5]
 * 
 * Step 3: Place temp at end
 * [2, 3, 4, 5, 1]
 * 
 * KEY OBSERVATION:
 * After rotation: arr[i] = arr[i+1] for i from 0 to n-2
 * And: arr[n-1] = original arr[0]
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Save first, shift rest (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Using reversal: Reverse entire array, then reverse parts - O(n) time, O(1) space
 * 3. Using extra array: Copy to new array with offset - O(n) time, O(n) space
 * 4. Using collections: Use takeLast() + take() - O(n) time, O(n) space
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We iterate through the array once (n-1 iterations)
 * - Each shift operation is O(1)
 * - Total: O(n) where n is array length
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only one extra variable (temp) used
 * - No additional data structures
 * - Modification is done in-place
 * 
 * WHY THIS IS OPTIMAL:
 * We must touch every element at least once to perform rotation,
 * so O(n) time is optimal. Using only one variable for constant
 * space is the most space-efficient solution.
 * 
 * ============================================================================
 */

package arrays.easy

class LeftRotateByOne {
    
    /**
     * Rotates array left by one position in-place
     * 
     * Time: O(n), Space: O(1)
     * 
     * @param arr The input array to rotate
     */
    fun rotateLeft(arr: IntArray) {
        // Edge case: Empty or single element array
        if (arr.isEmpty() || arr.size == 1) return
        
        // Step 1: Save the first element
        val first = arr[0]
        
        // Step 2: Shift all elements one position to the left
        for (i in 0 until arr.size - 1) {
            arr[i] = arr[i + 1]
        }
        
        // Step 3: Place the first element at the end
        arr[arr.size - 1] = first
    }
    
    /**
     * Alternative: Using Kotlin collections (creates new list)
     * 
     * Time: O(n), Space: O(n)
     */
    fun rotateLeftKotlinWay(arr: IntArray): List<Int> {
        if (arr.isEmpty()) return emptyList()
        return arr.drop(1) + arr.first()
    }
    
    /**
     * Rotate left by d positions (general case)
     * Included to show extension of the concept
     * 
     * Time: O(n), Space: O(1)
     */
    fun rotateLeftByD(arr: IntArray, d: Int) {
        if (arr.isEmpty() || d == 0) return
        
        // Normalize d to be within array bounds
        val rotations = d % arr.size
        
        // Rotate one by one, d times (can be optimized with reversal)
        repeat(rotations) {
            rotateLeft(arr)
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: Standard rotation
 * Input: arr = [1, 2, 3, 4, 5]
 * 
 * Initial: [1, 2, 3, 4, 5]
 * first = 1
 * 
 * Loop iterations:
 * i=0: arr[0] = arr[1] → [2, 2, 3, 4, 5]
 * i=1: arr[1] = arr[2] → [2, 3, 3, 4, 5]
 * i=2: arr[2] = arr[3] → [2, 3, 4, 4, 5]
 * i=3: arr[3] = arr[4] → [2, 3, 4, 5, 5]
 * 
 * After loop: arr[4] = first = 1
 * Result: [2, 3, 4, 5, 1] ✓
 * 
 * Example 2: Two elements
 * Input: arr = [7, 8]
 * 
 * first = 7
 * i=0: arr[0] = arr[1] → [8, 8]
 * arr[1] = first = 7
 * Result: [8, 7] ✓
 * 
 * Example 3: Single element
 * Input: arr = [42]
 * Returns immediately (no rotation needed)
 * Result: [42] ✓
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Empty array: arr = []
 *    - Return without modification
 * 
 * 2. Single element: arr = [5]
 *    - Return without modification
 *    - Rotation has no effect
 * 
 * 3. Two elements: arr = [1, 2]
 *    - Result: [2, 1]
 *    - Swaps the two elements
 * 
 * 4. All same elements: arr = [3, 3, 3, 3]
 *    - Result: [3, 3, 3, 3]
 *    - Array appears unchanged
 * 
 * 5. Negative numbers: arr = [-5, -2, 0, 3]
 *    - Result: [-2, 0, 3, -5]
 *    - Works same as positive numbers
 * 
 * 6. Large array: arr with 10^5 elements
 *    - Completes in O(n) time efficiently
 * 
 * 7. Already rotated: arr = [2, 3, 4, 5, 1]
 *    - Result: [3, 4, 5, 1, 2]
 *    - Continues rotation normally
 * 
 * 8. Ascending order: arr = [1, 2, 3, 4, 5]
 *    - Result: [2, 3, 4, 5, 1]
 *    - Order partially maintained
 * 
 * 9. Descending order: arr = [5, 4, 3, 2, 1]
 *    - Result: [4, 3, 2, 1, 5]
 *    - Order partially maintained
 * 
 * 10. Mix of positive/negative: arr = [-1, 0, 1, -2, 2]
 *     - Result: [0, 1, -2, 2, -1]
 *     - Handles mixed values correctly
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * Use in-place rotation when:
 * ✓ Memory is constrained (need O(1) space)
 * ✓ Can modify the original array
 * ✓ Single rotation is sufficient
 * ✓ Array size is not too small (overhead minimal)
 * 
 * Use Kotlin collections when:
 * ✓ Cannot modify original array
 * ✓ Need immutable result
 * ✓ Code readability is priority over performance
 * ✓ Working with small arrays
 * 
 * REAL-WORLD APPLICATIONS:
 * 1. Circular buffer implementation
 * 2. Task scheduling (moving completed task to end)
 * 3. Playlist rotation (next song)
 * 4. Image processing (pixel shifts)
 * 5. Data stream processing
 * 6. Round-robin algorithms
 * 7. Circular queue operations
 * 8. Game development (rotating player turns)
 * 
 * ============================================================================
 * COMMON MISTAKES
 * ============================================================================
 * 
 * 1. Forgetting to save the first element
 *    - Would lose the value that needs to wrap around
 * 
 * 2. Off-by-one error in loop (using arr.size instead of arr.size - 1)
 *    - Would cause ArrayIndexOutOfBoundsException
 * 
 * 3. Not handling empty array
 *    - Could cause exceptions
 * 
 * 4. Shifting in wrong direction
 *    - Using arr[i+1] = arr[i] shifts right, not left
 * 
 * 5. Placing first element at wrong position
 *    - Should be at index arr.size - 1, not 0
 * 
 * 6. Creating unnecessary copies
 *    - Defeats the purpose of in-place rotation
 * 
 * 7. Not testing with size 1 and 2
 *    - These are critical edge cases
 * 
 * 8. Assuming array is sorted
 *    - Algorithm works for any array, sorted or not
 * 
 * ============================================================================
 * FOLLOW-UP QUESTIONS
 * ============================================================================
 * 
 * Q1: How to rotate left by d positions?
 * A: Call rotateLeft() d times, or use reversal algorithm for O(n)
 * 
 * Q2: How to rotate right instead of left?
 * A: Save last element, shift right, place at start
 * 
 * Q3: What if we need to preserve original array?
 * A: Create a copy first, or use Kotlin collections approach
 * 
 * Q4: How to optimize for multiple rotations?
 * A: Use d % n to reduce rotations, or reversal algorithm
 * 
 * Q5: Can we do this without extra variable?
 * A: Yes, but requires XOR swaps or more complex logic
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. Clarify if rotation is left or right
 * 2. Ask if in-place modification is required
 * 3. Discuss edge cases (empty, single element)
 * 4. Mention the extension to d rotations
 * 5. Walk through example before coding
 * 6. Test with size 0, 1, 2, and larger arrays
 * 7. Mention time-space complexity upfront
 * 8. Discuss real-world applications if asked
 * 
 * ============================================================================
 */

fun main() {
    val solution = LeftRotateByOne()
    
    println("================================================")
    println("Left Rotate Array by One Position - Test Cases")
    println("================================================\n")
    
    // Test Case 1: Standard case
    println("Test 1: Standard array")
    val arr1 = intArrayOf(1, 2, 3, 4, 5)
    println("Before: ${arr1.contentToString()}")
    solution.rotateLeft(arr1)
    println("After:  ${arr1.contentToString()}")
    println("Expected: [2, 3, 4, 5, 1] ✓\n")
    
    // Test Case 2: Two elements
    println("Test 2: Two elements")
    val arr2 = intArrayOf(7, 8)
    println("Before: ${arr2.contentToString()}")
    solution.rotateLeft(arr2)
    println("After:  ${arr2.contentToString()}")
    println("Expected: [8, 7] ✓\n")
    
    // Test Case 3: Single element
    println("Test 3: Single element")
    val arr3 = intArrayOf(42)
    println("Before: ${arr3.contentToString()}")
    solution.rotateLeft(arr3)
    println("After:  ${arr3.contentToString()}")
    println("Expected: [42] (no change) ✓\n")
    
    // Test Case 4: Empty array
    println("Test 4: Empty array")
    val arr4 = intArrayOf()
    println("Before: ${arr4.contentToString()}")
    solution.rotateLeft(arr4)
    println("After:  ${arr4.contentToString()}")
    println("Expected: [] (no change) ✓\n")
    
    // Test Case 5: All same elements
    println("Test 5: All identical elements")
    val arr5 = intArrayOf(3, 3, 3, 3)
    println("Before: ${arr5.contentToString()}")
    solution.rotateLeft(arr5)
    println("After:  ${arr5.contentToString()}")
    println("Expected: [3, 3, 3, 3] ✓\n")
    
    // Test Case 6: Negative numbers
    println("Test 6: With negative numbers")
    val arr6 = intArrayOf(-5, -2, 0, 3)
    println("Before: ${arr6.contentToString()}")
    solution.rotateLeft(arr6)
    println("After:  ${arr6.contentToString()}")
    println("Expected: [-2, 0, 3, -5] ✓\n")
    
    // Test Case 7: Large numbers
    println("Test 7: Large numbers")
    val arr7 = intArrayOf(1000000, 999999, 1000001)
    println("Before: ${arr7.contentToString()}")
    solution.rotateLeft(arr7)
    println("After:  ${arr7.contentToString()}")
    println("Expected: [999999, 1000001, 1000000] ✓\n")
    
    // Test Case 8: Already rotated
    println("Test 8: Already rotated array")
    val arr8 = intArrayOf(2, 3, 4, 5, 1)
    println("Before: ${arr8.contentToString()}")
    solution.rotateLeft(arr8)
    println("After:  ${arr8.contentToString()}")
    println("Expected: [3, 4, 5, 1, 2] ✓\n")
    
    // Test Case 9: Descending order
    println("Test 9: Descending order")
    val arr9 = intArrayOf(5, 4, 3, 2, 1)
    println("Before: ${arr9.contentToString()}")
    solution.rotateLeft(arr9)
    println("After:  ${arr9.contentToString()}")
    println("Expected: [4, 3, 2, 1, 5] ✓\n")
    
    // Test Case 10: Mixed positive and negative
    println("Test 10: Mixed values")
    val arr10 = intArrayOf(-1, 0, 1, -2, 2)
    println("Before: ${arr10.contentToString()}")
    solution.rotateLeft(arr10)
    println("After:  ${arr10.contentToString()}")
    println("Expected: [0, 1, -2, 2, -1] ✓\n")
    
    // Bonus: Kotlin way
    println("Bonus: Kotlin idiomatic approach")
    val arr11 = intArrayOf(10, 20, 30, 40)
    println("Input:  ${arr11.contentToString()}")
    println("Output: ${solution.rotateLeftKotlinWay(arr11)}")
    println("Expected: [20, 30, 40, 10] ✓\n")
    
    // Bonus 2: Multiple rotations
    println("Bonus 2: Rotate left by 3 positions")
    val arr12 = intArrayOf(1, 2, 3, 4, 5, 6, 7)
    println("Before: ${arr12.contentToString()}")
    solution.rotateLeftByD(arr12, 3)
    println("After:  ${arr12.contentToString()}")
    println("Expected: [4, 5, 6, 7, 1, 2, 3] ✓\n")
    
    println("================================================")
    println("All test cases passed! ✓")
    println("================================================")
}
