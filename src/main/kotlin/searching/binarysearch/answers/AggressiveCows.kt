/**
 * ============================================================================
 * PROBLEM: Aggressive Cows
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of positions representing stalls and an integer c representing
 * the number of cows, place all cows in stalls such that the minimum distance
 * between any two cows is maximized.
 * 
 * You cannot place more than one cow in the same stall. 
 * 
 * INPUT FORMAT:
 * - stalls: Array of stall positions (unsorted)
 * - cows: Number of cows to place
 * Example: stalls = [1,2,8,4,9], cows = 3
 * 
 * OUTPUT FORMAT:
 * - Maximum possible minimum distance
 * Example: 3
 * Explanation: Place cows at positions 1, 4, and 8 (or 1, 4, 9)
 *              Distances: 4-1=3, 8-4=4 (or 9-4=5)
 *              Minimum distance: 3 (maximized)
 * 
 * CONSTRAINTS:
 * - 2 <= stalls.length <= 10^5
 * - 0 <= stalls[i] <= 10^9
 * - 2 <= cows <= stalls.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * We want to maximize the minimum distance.  Use binary search on the distance. 
 * For a given distance, check if we can place all cows such that any two cows
 * are at least that distance apart.
 * 
 * KEY INSIGHT:
 * - First, sort the stalls array
 * - Minimum possible distance: 1 (adjacent stalls)
 * - Maximum possible distance: stalls[n-1] - stalls[0] (first to last)
 * - For a given distance, greedily place cows (always place as soon as possible)
 * - Find the largest distance that allows placing all cows
 * 
 * ALGORITHM STEPS:
 * 1. Sort the stalls array
 * 2. Binary search: low = 1, high = stalls[n-1] - stalls[0]
 * 3. For each mid:
 *    - Try to place cows with minimum distance = mid
 *    - Place first cow at stalls[0]
 *    - Place next cow at first stall >= lastPosition + mid
 *    - If can place all cows: valid, try larger distance (low = mid + 1)
 *    - Else: distance too large, try smaller (high = mid - 1)
 * 4. Return high (maximum valid distance)
 * 
 * VISUAL EXAMPLE:
 * stalls = [1,2,8,4,9], cows = 3
 * After sorting: [1,2,4,8,9]
 * 
 * Try distance = 3:
 * - Place cow 1 at position 1
 * - Next position >= 1+3 = 4: place cow 2 at position 4
 * - Next position >= 4+3 = 7: place cow 3 at position 8
 * Placed 3 cows ✓
 * 
 * Try distance = 4:
 * - Place cow 1 at position 1
 * - Next position >= 1+4 = 5: place cow 2 at position 8
 * - Next position >= 8+4 = 12: no such position
 * Placed 2 cows < 3 ✗
 * 
 * Maximum valid distance = 3
 * 
 * Binary Search Process:
 * low=1, high=8 (9-1)
 * 
 * mid=4: can place 2 cows < 3, high=3
 * mid=2: can place 3 cows >= 3, low=3
 * mid=3: can place 3 cows >= 3, low=4
 * low > high, return high=3 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n log n + n * log d)
 * - Sorting: O(n log n)
 * - Binary search: O(log d) where d = max position - min position
 * - Each iteration checks placement: O(n)
 * - Total: O(n log n + n * log d)
 * 
 * SPACE COMPLEXITY: O(1) or O(n)
 * - O(1) if sorting in-place
 * - O(n) for sorting overhead
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class AggressiveCows {
    
    /**
     * Finds maximum possible minimum distance between cows
     * @param stalls Array of stall positions (unsorted)
     * @param cows Number of cows to place
     * @return Maximum minimum distance
     */
    fun aggressiveCows(stalls:  IntArray, cows: Int): Int {
        // Sort stalls first
        stalls.sort()
        
        var low = 1  // Minimum possible distance
        var high = stalls. last() - stalls.first()  // Maximum possible distance
        var result = 0
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            if (canPlaceCows(stalls, cows, mid)) {
                // Can place with distance mid, try larger distance
                result = mid
                low = mid + 1
            } else {
                // Distance too large, try smaller
                high = mid - 1
            }
        }
        
        return result
    }
    
    /**
     * Checks if we can place all cows with minimum distance minDist
     * @param stalls Sorted array of stall positions
     * @param cows Number of cows
     * @param minDist Minimum distance required
     * @return True if can place all cows, false otherwise
     */
    private fun canPlaceCows(stalls: IntArray, cows: Int, minDist: Int): Boolean {
        var cowsPlaced = 1  // Place first cow at first stall
        var lastPosition = stalls[0]
        
        for (i in 1 until stalls.size) {
            if (stalls[i] - lastPosition >= minDist) {
                // Can place cow here
                cowsPlaced++
                lastPosition = stalls[i]
                
                if (cowsPlaced == cows) {
                    return true  // All cows placed
                }
            }
        }
        
        return false  // Couldn't place all cows
    }
    
    /**
     * Returns the actual positions where cows are placed
     */
    fun aggressiveCowsWithPositions(stalls: IntArray, cows: Int): Pair<Int, List<Int>> {
        stalls.sort()
        val maxDist = aggressiveCows(stalls, cows)
        
        // Find the positions
        val positions = mutableListOf<Int>()
        positions.add(stalls[0])
        var lastPosition = stalls[0]
        
        for (i in 1 until stalls.size) {
            if (stalls[i] - lastPosition >= maxDist) {
                positions.add(stalls[i])
                lastPosition = stalls[i]
                
                if (positions.size == cows) break
            }
        }
        
        return Pair(maxDist, positions)
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: stalls = [1,2,8,4,9], cows = 3
 * 
 * After sorting: [1,2,4,8,9]
 * Initial: low=1, high=8
 * 
 * Iteration 1: mid=4
 * Try placing with minDist=4:
 * - Cow 1 at 1
 * - Next >= 5:  Cow 2 at 8
 * - Next >= 12: no position
 * Placed:  2 < 3, high=3
 * 
 * Iteration 2: mid=2
 * Try placing with minDist=2:
 * - Cow 1 at 1
 * - Next >= 3: Cow 2 at 4
 * - Next >= 6: Cow 3 at 8
 * Placed: 3 >= 3, result=2, low=3
 * 
 * Iteration 3: mid=3
 * Try placing with minDist=3:
 * - Cow 1 at 1
 * - Next >= 4: Cow 2 at 4
 * - Next >= 7: Cow 3 at 8
 * Placed: 3 >= 3, result=3, low=4
 * 
 * low > high, return result=3 ✓
 * 
 * Example 2: stalls = [0,3,4,7,10,9], cows = 4
 * 
 * After sorting: [0,3,4,7,9,10]
 * Initial: low=1, high=10
 * 
 * mid=5:
 * - Cow 1 at 0
 * - Next >= 5: Cow 2 at 7
 * - Next >= 12: no position
 * Placed: 2 < 4, high=4
 * 
 * mid=2:
 * - Cow 1 at 0
 * - Next >= 2: Cow 2 at 3
 * - Next >= 5: Cow 3 at 7
 * - Next >= 9: Cow 4 at 9
 * Placed: 4 >= 4, result=2, low=3
 * 
 * mid=3:
 * - Cow 1 at 0
 * - Next >= 3: Cow 2 at 3
 * - Next >= 6: Cow 3 at 7
 * - Next >= 10: Cow 4 at 10
 * Placed: 4 >= 4, result=3, low=4
 * 
 * mid=4:
 * - Cow 1 at 0
 * - Next >= 4: Cow 2 at 4
 * - Next >= 8: Cow 3 at 9
 * - Next >= 13: no position
 * Placed: 3 < 4, high=3
 * 
 * low > high, return result=3 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = AggressiveCows()
    
    // Test Case 1: Standard case
    println("Test 1: stalls=[1,2,8,4,9], cows=3")
    val (dist1, pos1) = solution.aggressiveCowsWithPositions(intArrayOf(1,2,8,4,9), 3)
    println("Result: $dist1")
    println("Positions: ${pos1.joinToString(", ")}")
    // Expected: 3
    
    // Test Case 2: More cows
    println("\nTest 2: stalls=[0,3,4,7,10,9], cows=4")
    val (dist2, pos2) = solution.aggressiveCowsWithPositions(intArrayOf(0,3,4,7,10,9), 4)
    println("Result: $dist2")
    println("Positions: ${pos2.joinToString(", ")}")
    // Expected: 3
    
    // Test Case 3: Two cows
    println("\nTest 3: stalls=[1,2,3,4,5], cows=2")
    val (dist3, pos3) = solution.aggressiveCowsWithPositions(intArrayOf(1,2,3,4,5), 2)
    println("Result: $dist3")
    println("Positions: ${pos3.joinToString(", ")}")
    // Expected: 4
    
    // Test Case 4: Minimum distance
    println("\nTest 4: stalls=[1,2,3], cows=3")
    println("Result: ${solution.aggressiveCows(intArrayOf(1,2,3), 3)}")
    // Expected: 1
    
    // Test Case 5: Large gaps
    println("\nTest 5: stalls=[1,100,200], cows=2")
    println("Result: ${solution.aggressiveCows(intArrayOf(1,100,200), 2)}")
    // Expected: 100 or 99 (depends on placement)
    
    // Detailed simulation for Test 1
    println("\n--- Simulation for [1,2,8,4,9] (sorted:  [1,2,4,8,9]), cows=3 ---")
    val stalls = intArrayOf(1,2,4,8,9)
    for (dist in 1..8) {
        var cows = 1
        var last = stalls[0]
        val positions = mutableListOf(last)
        
        for (i in 1 until stalls.size) {
            if (stalls[i] - last >= dist) {
                cows++
                last = stalls[i]
                positions.add(last)
            }
        }
        
        val status = if (cows >= 3) "✓" else "✗"
        println("Distance=$dist:  $cows cows ${positions.joinToString(", ", "[", "]")} $status")
    }
}
