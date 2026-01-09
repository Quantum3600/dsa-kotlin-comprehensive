/**
 * ============================================================================
 * PROBLEM: Aggressive Cows
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an array of positions of stalls and a number of cows. The stalls
 * are located at different positions along a straight line. You need to assign
 * the cows to the stalls such that the minimum distance between any two cows is
 * as large as possible. Return the largest minimum distance.
 * 
 * INPUT FORMAT:
 * - stalls: Array of integers representing positions of stalls (not necessarily sorted)
 * - cows: Integer representing number of cows to place
 * Example: stalls = [1, 2, 4, 8, 9], cows = 3
 * 
 * OUTPUT FORMAT:
 * - Integer: The largest minimum distance between any two cows
 * Example: 3 (cows placed at positions 1, 4, and 8 or 1, 4, and 9)
 * 
 * CONSTRAINTS:
 * - 2 <= stalls.size <= 10^5
 * - 2 <= cows <= stalls.size
 * - 0 <= stalls[i] <= 10^9
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Instead of trying all possible arrangements of cows, we can use binary search
 * on the answer. The key insight is that if we can place cows with a minimum
 * distance D, then we can also place them with any distance less than D.
 * This monotonic property makes binary search applicable.
 * 
 * ALGORITHM STEPS:
 * 1. Sort the stalls array (to place cows in order)
 * 2. Set search space: low = 1 (minimum possible distance)
 *                      high = stalls[n-1] - stalls[0] (maximum possible distance)
 * 3. Binary search on the answer:
 *    - For each mid value, check if we can place all cows with minimum distance = mid
 *    - If yes, try for larger distance (search right)
 *    - If no, try for smaller distance (search left)
 * 4. The answer is the largest distance for which placement is possible
 * 
 * VISUAL EXAMPLE:
 * stalls = [1, 2, 4, 8, 9], cows = 3
 * 
 * After sorting: [1, 2, 4, 8, 9]
 * 
 * Try distance = 3:
 * Place cow 1 at stall[0] = 1
 * Next cow needs position >= 1+3 = 4, place at stall[2] = 4
 * Next cow needs position >= 4+3 = 7, place at stall[3] = 8
 * Success! All 3 cows placed with min distance 3.
 * 
 * Try distance = 4:
 * Place cow 1 at stall[0] = 1
 * Next cow needs position >= 1+4 = 5, place at stall[3] = 8
 * Next cow needs position >= 8+4 = 12, no valid stall!
 * Failed! Can't place all cows with min distance 4.
 * 
 * Answer = 3 (largest successful distance)
 * 
 * WHY BINARY SEARCH:
 * - Search space: All possible minimum distances [1 to max_distance]
 * - Monotonic property: If we can place cows with distance D, we can also
 *   place them with any distance < D
 * - If we can't place cows with distance D, we can't place them with any
 *   distance > D either
 * - This monotonicity allows us to binary search on the distance
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n + n log(max_distance))
 * - Sorting stalls: O(n log n)
 * - Binary search iterations: O(log(max_distance)) where max_distance = stalls[n-1] - stalls[0]
 * - Each iteration checks placement: O(n)
 * - Total: O(n log n) + O(n log(max_distance))
 * 
 * For n = 10^5 and max_distance = 10^9:
 * - Sorting: ~100,000 * 17 = 1.7 million operations
 * - Binary search: ~30 iterations (log₂(10^9) ≈ 30)
 * - Each check: 100,000 operations
 * - Total: ~1.7M + 3M = ~4.7M operations (very efficient!)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space for variables
 * - Sorting is done in-place (if we modify original array)
 * - No additional data structures needed
 */

package searching.binarysearch.answers

class AggressiveCows {
    
    /**
     * Find the largest minimum distance between cows
     * 
     * @param stalls Array of stall positions
     * @param cows Number of cows to place
     * @return Largest minimum distance possible
     */
    fun solve(stalls: IntArray, cows: Int): Int {
        // Edge case: If we have only 2 cows, place at first and last stall
        if (cows == 2) {
            return stalls.max() - stalls.min()
        }
        
        // Step 1: Sort stalls to place cows in sequential order
        // Sorting allows us to use greedy placement strategy
        stalls.sort()
        
        // Step 2: Define search space for binary search
        // Minimum possible distance is 1 (adjacent stalls)
        var low = 1
        
        // Maximum possible distance is gap between first and last stall
        // Can't have larger distance than total range
        var high = stalls[stalls.size - 1] - stalls[0]
        
        // Variable to store the answer (largest valid distance found)
        var answer = 0
        
        // Step 3: Binary search on the answer
        // We search for the largest distance that allows placing all cows
        while (low <= high) {
            // Calculate middle distance to try
            val mid = low + (high - low) / 2
            
            // Step 4: Check if we can place all cows with minimum distance = mid
            if (canPlaceCows(stalls, cows, mid)) {
                // If placement is possible with distance mid,
                // store this as potential answer and try for larger distance
                answer = mid
                low = mid + 1  // Search in right half for larger distance
            } else {
                // If placement not possible with distance mid,
                // we need to try smaller distance
                high = mid - 1  // Search in left half for smaller distance
            }
        }
        
        return answer
    }
    
    /**
     * Check if we can place all cows with given minimum distance
     * Uses greedy approach: place each cow as soon as possible
     * 
     * @param stalls Sorted array of stall positions
     * @param cows Number of cows to place
     * @param minDistance Minimum distance to maintain between cows
     * @return true if all cows can be placed, false otherwise
     */
    private fun canPlaceCows(stalls: IntArray, cows: Int, minDistance: Int): Boolean {
        // Place first cow at the first stall (greedy choice)
        // This gives maximum flexibility for placing remaining cows
        var cowsPlaced = 1
        var lastPosition = stalls[0]
        
        // Try to place remaining cows
        for (i in 1 until stalls.size) {
            // Check if current stall is at least minDistance away from last placed cow
            if (stalls[i] - lastPosition >= minDistance) {
                // Place a cow at this stall
                cowsPlaced++
                lastPosition = stalls[i]
                
                // If all cows are placed, we're done
                if (cowsPlaced == cows) {
                    return true
                }
            }
        }
        
        // If we exit loop without placing all cows, placement failed
        return false
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: stalls = [1, 2, 8, 4, 9], cows = 3
 * 
 * Step 1: Sort stalls
 * stalls = [1, 2, 4, 8, 9]
 * 
 * Step 2: Initialize search space
 * low = 1, high = 9 - 1 = 8
 * 
 * Binary Search Iterations:
 * 
 * Iteration 1: low=1, high=8
 * - mid = 1 + (8-1)/2 = 4
 * - Check if we can place with distance 4:
 *   - Place cow at 1
 *   - Next position >= 1+4=5, place at 8
 *   - Next position >= 8+4=12, place at 9? No, 9-8=1 < 4
 *   - Only 2 cows placed, return false
 * - answer = 0, high = 3
 * 
 * Iteration 2: low=1, high=3
 * - mid = 1 + (3-1)/2 = 2
 * - Check if we can place with distance 2:
 *   - Place cow at 1
 *   - Next position >= 1+2=3, place at 4
 *   - Next position >= 4+2=6, place at 8
 *   - All 3 cows placed, return true
 * - answer = 2, low = 3
 * 
 * Iteration 3: low=3, high=3
 * - mid = 3 + (3-3)/2 = 3
 * - Check if we can place with distance 3:
 *   - Place cow at 1
 *   - Next position >= 1+3=4, place at 4
 *   - Next position >= 4+3=7, place at 8
 *   - All 3 cows placed, return true
 * - answer = 3, low = 4
 * 
 * Iteration 4: low=4, high=3
 * - low > high, exit loop
 * 
 * OUTPUT: 3
 * 
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Two cows: Maximum distance = last_stall - first_stall
 * 2. Cows equal to stalls: Must use every stall, answer = minimum gap
 * 3. All stalls at same position: Distance = 0
 * 4. Large gaps between stalls: Handle with large search space
 * 5. Minimum distance = 1: Always possible if cows <= stalls
 * 
 * ============================================================================
 */

fun main() {
    val solver = AggressiveCows()
    
    println("=== Testing Aggressive Cows ===\n")
    
    // Test Case 1: Normal case
    val stalls1 = intArrayOf(1, 2, 4, 8, 9)
    val cows1 = 3
    println("Test 1: stalls = ${stalls1.contentToString()}, cows = $cows1")
    println("Result: ${solver.solve(stalls1.clone(), cows1)}")
    println("Expected: 3 (place at 1, 4, 8 or 1, 4, 9)\n")
    
    // Test Case 2: Only two cows
    val stalls2 = intArrayOf(1, 2, 8, 4, 9)
    val cows2 = 2
    println("Test 2: stalls = ${stalls2.contentToString()}, cows = $cows2")
    println("Result: ${solver.solve(stalls2.clone(), cows2)}")
    println("Expected: 8 (place at 1 and 9)\n")
    
    // Test Case 3: More stalls than needed
    val stalls3 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val cows3 = 4
    println("Test 3: stalls = ${stalls3.contentToString()}, cows = $cows3")
    println("Result: ${solver.solve(stalls3.clone(), cows3)}")
    println("Expected: 3 (place at 1, 4, 7, 10)\n")
    
    // Test Case 4: Large gaps
    val stalls4 = intArrayOf(0, 100, 200, 300, 400)
    val cows4 = 3
    println("Test 4: stalls = ${stalls4.contentToString()}, cows = $cows4")
    println("Result: ${solver.solve(stalls4.clone(), cows4)}")
    println("Expected: 200 (place at 0, 200, 400)\n")
    
    // Test Case 5: All cows must be placed (cows = stalls)
    val stalls5 = intArrayOf(1, 5, 9, 11)
    val cows5 = 4
    println("Test 5: stalls = ${stalls5.contentToString()}, cows = $cows5")
    println("Result: ${solver.solve(stalls5.clone(), cows5)}")
    println("Expected: 2 (minimum gap in sorted array)\n")
    
    // Test Case 6: Unsorted input
    val stalls6 = intArrayOf(10, 1, 5, 8, 15, 2)
    val cows6 = 3
    println("Test 6: stalls = ${stalls6.contentToString()}, cows = $cows6")
    println("Result: ${solver.solve(stalls6.clone(), cows6)}")
    println("Expected: 7 (after sorting: [1, 2, 5, 8, 10, 15], place at 1, 8, 15)\n")
}
