/**
 * ============================================================================
 * PROBLEM: Second Largest Element in Array
 * DIFFICULTY: Easy
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers, find and return the second largest element in the array.
 * If the second largest element doesn't exist (all elements are same or array has only one element),
 * return -1.
 * 
 * INPUT FORMAT:
 * - An array of integers: arr = [12, 35, 1, 10, 34, 1]
 * 
 * OUTPUT FORMAT:
 * - A single integer representing the second largest element: 34
 * - Return -1 if second largest doesn't exist
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
 * To find the second largest element, we need to track two variables:
 * 1. The largest element we've seen so far
 * 2. The second largest element we've seen so far
 * 
 * As we iterate through the array:
 * - If we find an element larger than the current largest, we update both:
 *   * The old largest becomes the new second largest
 *   * The new element becomes the new largest
 * - If we find an element between largest and second largest, we only update second largest
 * 
 * VISUAL EXAMPLE:
 * arr = [12, 35, 1, 10, 34, 1]
 *        ↓
 *     largest = 12, secondLargest = -1
 *            ↓
 *         largest = 35, secondLargest = 12 (old largest)
 *               ↓
 *            largest = 35, secondLargest = 12 (no change, 1 < 12)
 *                  ↓
 *               largest = 35, secondLargest = 12 (no change, 10 < 12)
 *                      ↓
 *                   largest = 35, secondLargest = 34 (34 > 12 but < 35)
 *                         ↓
 *                      largest = 35, secondLargest = 34 (no change)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. One-pass approach (used here): O(n) time, O(1) space - OPTIMAL
 * 2. Two-pass approach: Find largest first, then second largest - O(n) time, O(1) space
 * 3. Using sorting: O(n log n) time, O(n) space - Not optimal
 * 4. Using heap: O(n log k) time, O(k) space - Overkill for this problem
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - We iterate through the array exactly once
 * - n is the number of elements in the array
 * - Each comparison and update operation takes O(1) time
 * - Total: O(n) * O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 * - We only use two extra variables (largest, secondLargest) regardless of input size
 * - No additional data structures created
 * - No recursive calls that would use stack space
 * 
 * WHY THIS IS OPTIMAL:
 * We must examine every element at least once to determine the second largest,
 * so we cannot do better than O(n) time. Using constant space is optimal.
 * 
 * ============================================================================
 */

package arrays.easy

class SecondLargest {
    
    /**
     * Finds the second largest element in the given array using one-pass approach
     * 
     * @param arr The input array of integers
     * @return The second largest integer in the array, or -1 if it doesn't exist
     */
    fun findSecondLargest(arr: IntArray): Int {
        // Edge case: Array with less than 2 elements cannot have second largest
        if (arr.size < 2) return -1
        
        // Initialize largest and secondLargest with minimum possible values
        // We use Int.MIN_VALUE so any actual element will be larger
        var largest = Int.MIN_VALUE
        var secondLargest = Int.MIN_VALUE
        
        // Iterate through each element in the array
        for (element in arr) {
            // Case 1: Current element is larger than our current largest
            // This means we have a new largest, and old largest becomes second largest
            if (element > largest) {
                // Before updating largest, save it as secondLargest
                // This ensures we don't lose track of the previous largest
                secondLargest = largest
                // Update largest to the new maximum
                largest = element
            }
            // Case 2: Current element is between largest and secondLargest
            // We found a new second largest, but largest remains unchanged
            else if (element > secondLargest && element < largest) {
                // Update only secondLargest since this element is smaller than largest
                // The condition element < largest ensures we don't count duplicates of largest
                secondLargest = element
            }
            // Case 3: element <= secondLargest
            // No update needed, continue to next element
        }
        
        // If secondLargest is still Int.MIN_VALUE, it means:
        // - All elements are the same, OR
        // - Array had only one unique element
        // In either case, return -1 as there's no valid second largest
        return if (secondLargest == Int.MIN_VALUE) -1 else secondLargest
    }
    
    /**
     * Alternative approach: Two-pass solution
     * First pass finds the largest, second pass finds the second largest
     * Same time complexity O(n), but requires two iterations
     */
    fun findSecondLargestTwoPass(arr: IntArray): Int {
        if (arr.size < 2) return -1
        
        // First pass: Find the largest element
        var largest = arr[0]
        for (element in arr) {
            if (element > largest) {
                largest = element
            }
        }
        
        // Second pass: Find largest element that's not equal to largest
        var secondLargest = Int.MIN_VALUE
        for (element in arr) {
            // Look for elements smaller than largest but larger than current secondLargest
            if (element != largest && element > secondLargest) {
                secondLargest = element
            }
        }
        
        return if (secondLargest == Int.MIN_VALUE) -1 else secondLargest
    }
    
    /**
     * Approach using sorting: Not optimal but straightforward
     * Time: O(n log n), Space: O(n) due to sorting
     */
    fun findSecondLargestUsingSorting(arr: IntArray): Int {
        if (arr.size < 2) return -1
        
        // Sort array in descending order
        // This creates a new sorted array (doesn't modify original)
        val sorted = arr.sortedArrayDescending()
        
        // Start from the second element and find first element different from largest
        for (i in 1 until sorted.size) {
            if (sorted[i] != sorted[0]) {
                return sorted[i]
            }
        }
        
        // All elements are same
        return -1
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: arr = [12, 35, 1, 10, 34, 1]
 * 
 * EXECUTION TRACE (One-Pass Approach):
 * 
 * Initial State:
 * - largest = Int.MIN_VALUE
 * - secondLargest = Int.MIN_VALUE
 * 
 * Iteration 1 (element = 12):
 * - 12 > Int.MIN_VALUE (largest)? YES
 * - secondLargest = Int.MIN_VALUE (old largest)
 * - largest = 12
 * State: largest = 12, secondLargest = Int.MIN_VALUE
 * 
 * Iteration 2 (element = 35):
 * - 35 > 12 (largest)? YES
 * - secondLargest = 12 (old largest)
 * - largest = 35
 * State: largest = 35, secondLargest = 12
 * 
 * Iteration 3 (element = 1):
 * - 1 > 35? NO
 * - 1 > 12 && 1 < 35? NO
 * - No update
 * State: largest = 35, secondLargest = 12
 * 
 * Iteration 4 (element = 10):
 * - 10 > 35? NO
 * - 10 > 12 && 10 < 35? NO (10 is not > 12)
 * - No update
 * State: largest = 35, secondLargest = 12
 * 
 * Iteration 5 (element = 34):
 * - 34 > 35? NO
 * - 34 > 12 && 34 < 35? YES
 * - secondLargest = 34
 * State: largest = 35, secondLargest = 34
 * 
 * Iteration 6 (element = 1):
 * - 1 > 35? NO
 * - 1 > 34 && 1 < 35? NO
 * - No update
 * State: largest = 35, secondLargest = 34
 * 
 * FINAL RESULT: 34
 * 
 * ============================================================================
 * EDGE CASES & HOW THEY'RE HANDLED
 * ============================================================================
 * 
 * 1. Empty array: arr = []
 *    - Size check catches this: arr.size < 2
 *    - Returns: -1
 * 
 * 2. Single element: arr = [5]
 *    - Size check catches this: arr.size < 2
 *    - Returns: -1
 * 
 * 3. All same elements: arr = [5, 5, 5, 5]
 *    - No element satisfies element < largest
 *    - secondLargest remains Int.MIN_VALUE
 *    - Returns: -1
 * 
 * 4. Two elements (same): arr = [5, 5]
 *    - After processing: largest = 5, secondLargest = Int.MIN_VALUE
 *    - Returns: -1
 * 
 * 5. Two elements (different): arr = [3, 7]
 *    - After processing: largest = 7, secondLargest = 3
 *    - Returns: 3
 * 
 * 6. Negative numbers: arr = [-1, -5, -3]
 *    - Works correctly: largest = -1, secondLargest = -3
 *    - Returns: -3
 * 
 * 7. Mix of positive/negative: arr = [-10, 5, -3, 8]
 *    - largest = 8, secondLargest = 5
 *    - Returns: 5
 * 
 * 8. Largest appears multiple times: arr = [1, 10, 10, 8]
 *    - The condition element < largest prevents counting duplicates
 *    - largest = 10, secondLargest = 8
 *    - Returns: 8
 * 
 * 9. Very large array with duplicates: arr = [1, 2, 2, 2, ..., 1000 elements]
 *    - Algorithm handles efficiently in O(n) time
 *    - Returns correct second largest
 * 
 * 10. Array in descending order: arr = [100, 90, 80, 70]
 *     - largest = 100, secondLargest = 90
 *     - Returns: 90
 * 
 * ============================================================================
 * WHEN TO USE THIS APPROACH
 * ============================================================================
 * 
 * USE WHEN:
 * - You need O(n) time complexity
 * - Memory is constrained (need O(1) space)
 * - Array is unsorted and should remain unsorted
 * - You want a single-pass solution for efficiency
 * - Handling duplicates is important
 * 
 * AVOID WHEN:
 * - Array is already sorted (can directly access second element)
 * - You need top K elements (use heap-based approach instead)
 * - Array is extremely large and distributed (need different strategy)
 * 
 * ============================================================================
 * REAL WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Gaming Leaderboards**: Finding silver medal winner
 * 2. **E-commerce**: Second most popular product
 * 3. **Stock Market**: Second highest price point
 * 4. **Sports**: Runner-up in race or competition
 * 5. **Data Analysis**: Finding second peak in data distribution
 * 6. **Resource Allocation**: Backup resource when primary is unavailable
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * 1. **Not handling duplicates**: Forgetting element < largest condition
 *    - Wrong: Counting duplicates of largest as second largest
 *    - Right: Ensure element is strictly less than largest
 * 
 * 2. **Wrong initialization**: Using 0 or first element instead of Int.MIN_VALUE
 *    - Problem: Fails when all elements are negative or less than initial value
 *    - Solution: Use Int.MIN_VALUE for proper comparison
 * 
 * 3. **Not checking array size**: Processing arrays with < 2 elements
 *    - Problem: May return incorrect results
 *    - Solution: Check size upfront and return -1
 * 
 * 4. **Forgetting to update secondLargest**: When updating largest
 *    - Problem: Lose track of previous largest value
 *    - Solution: Always save largest to secondLargest before updating
 * 
 * 5. **Using == instead of !=**: In condition checks
 *    - Problem: Logic errors in comparisons
 *    - Solution: Carefully think through comparison conditions
 * 
 * 6. **Not handling edge case return**: Returning Int.MIN_VALUE instead of -1
 *    - Problem: Confusing return value with actual element
 *    - Solution: Check if secondLargest is still Int.MIN_VALUE and return -1
 * 
 * ============================================================================
 * OPTIMIZATION NOTES
 * ============================================================================
 * 
 * The one-pass approach is already optimal in terms of time complexity O(n).
 * However, there are minor optimizations:
 * 
 * 1. **Early termination**: If we find the first two distinct elements early
 *    and remaining elements are smaller, we still need to check all elements
 *    (cannot optimize further)
 * 
 * 2. **Parallel processing**: For very large arrays, can split and process
 *    in parallel, then merge results
 * 
 * 3. **Memory access patterns**: Processing array sequentially provides good
 *    cache locality, which helps performance in practice
 * 
 * ============================================================================
 */

fun main() {
    val solution = SecondLargest()
    
    println("=== Second Largest Element Tests ===\n")
    
    // Test 1: Normal case with distinct elements
    println("Test 1: Normal case")
    val arr1 = intArrayOf(12, 35, 1, 10, 34, 1)
    println("Input: ${arr1.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr1)}")
    println("Expected: 34\n")
    
    // Test 2: All same elements
    println("Test 2: All same elements")
    val arr2 = intArrayOf(5, 5, 5, 5)
    println("Input: ${arr2.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr2)}")
    println("Expected: -1\n")
    
    // Test 3: Two elements (different)
    println("Test 3: Two elements (different)")
    val arr3 = intArrayOf(3, 7)
    println("Input: ${arr3.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr3)}")
    println("Expected: 3\n")
    
    // Test 4: Single element
    println("Test 4: Single element")
    val arr4 = intArrayOf(5)
    println("Input: ${arr4.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr4)}")
    println("Expected: -1\n")
    
    // Test 5: Negative numbers
    println("Test 5: Negative numbers")
    val arr5 = intArrayOf(-1, -5, -3, -2)
    println("Input: ${arr5.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr5)}")
    println("Expected: -2\n")
    
    // Test 6: Mix of positive and negative
    println("Test 6: Mix of positive and negative")
    val arr6 = intArrayOf(-10, 5, -3, 8, 12)
    println("Input: ${arr6.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr6)}")
    println("Expected: 8\n")
    
    // Test 7: Largest appears multiple times
    println("Test 7: Largest appears multiple times")
    val arr7 = intArrayOf(1, 10, 10, 8, 9)
    println("Input: ${arr7.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr7)}")
    println("Expected: 9\n")
    
    // Test 8: Array in descending order
    println("Test 8: Array in descending order")
    val arr8 = intArrayOf(100, 90, 80, 70, 60)
    println("Input: ${arr8.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr8)}")
    println("Expected: 90\n")
    
    // Test 9: Array in ascending order
    println("Test 9: Array in ascending order")
    val arr9 = intArrayOf(1, 2, 3, 4, 5)
    println("Input: ${arr9.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr9)}")
    println("Expected: 4\n")
    
    // Test 10: Large numbers
    println("Test 10: Large numbers")
    val arr10 = intArrayOf(1000000, 999999, 1, 999998)
    println("Input: ${arr10.contentToString()}")
    println("Second Largest: ${solution.findSecondLargest(arr10)}")
    println("Expected: 999999\n")
    
    // Comparison of approaches
    println("=== Comparing Different Approaches ===")
    val testArray = intArrayOf(12, 35, 1, 10, 34, 1)
    println("Test Array: ${testArray.contentToString()}")
    println("One-Pass: ${solution.findSecondLargest(testArray)}")
    println("Two-Pass: ${solution.findSecondLargestTwoPass(testArray)}")
    println("Using Sorting: ${solution.findSecondLargestUsingSorting(testArray)}")
}
