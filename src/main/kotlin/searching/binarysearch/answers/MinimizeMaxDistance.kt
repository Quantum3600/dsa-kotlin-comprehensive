/**
 * ============================================================================
 * PROBLEM: Minimize Max Distance to Gas Station
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an array stations representing positions of gas stations on
 * a highway, and an integer k representing the number of new gas stations you
 * can add. Find the minimum possible value of the maximum distance between
 * adjacent gas stations after adding k new stations optimally.
 * 
 * INPUT FORMAT:
 * - stations: Sorted array of gas station positions
 * - k: Number of new stations to add
 * Example: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 9
 * 
 * OUTPUT FORMAT:
 * - Double: Minimum possible maximum distance (with precision 10^-6)
 * Example: 0.5
 * 
 * CONSTRAINTS:
 * - 2 <= stations.length <= 10^5
 * - 0 <= stations[i] <= 10^8
 * - stations is sorted in strictly increasing order
 * - 1 <= k <= 10^6
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We want to minimize the maximum distance between adjacent stations. If we
 * can achieve max distance D, we can also achieve any distance > D (monotonic).
 * Binary search on the answer (maximum distance).
 * 
 * For a given max distance D, we can calculate minimum stations needed to
 * ensure no gap exceeds D. For a gap of length L, we need ceil(L/D) - 1
 * stations to make all segments <= D.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = 0 (minimum possible distance)
 *    high = max gap in original array
 * 2. Binary search on maximum distance:
 *    - For each mid, calculate stations needed
 *    - If stations needed <= k, try smaller distance (search left)
 *    - If stations needed > k, need larger distance (search right)
 * 3. Return minimum valid distance
 * 
 * VISUAL EXAMPLE:
 * stations = [1, 2, 3, 4, 5], k = 4
 * 
 * Original gaps: all are 1
 * 
 * Try max distance = 0.5:
 * For each gap of 1, need ceil(1/0.5) - 1 = 1 station
 * Total: 4 stations needed <= 4 ✓
 * 
 * Result: We can achieve max distance 0.5
 * 
 * WHY BINARY SEARCH:
 * - Search space: [0 to max_gap]
 * - Monotonic: If distance D works, D+ε also works
 * - Want minimum D that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(max_gap / precision))
 * - Binary search with precision 10^-6
 * - Each iteration calculates stations needed: O(n)
 * 
 * For n = 10^5, max_gap = 10^8:
 * - Binary search: ~50 iterations
 * - Each check: 100,000 operations
 * - Total: ~5M operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

import kotlin.math.ceil

class MinimizeMaxDistance {
    
    /**
     * Find minimum possible maximum distance after adding k stations
     * 
     * @param stations Sorted array of station positions
     * @param k Number of new stations to add
     * @return Minimum possible maximum distance
     */
    fun solve(stations: IntArray, k: Int): Double {
        // Find initial maximum gap
        var maxGap = 0.0
        for (i in 1 until stations.size) {
            maxGap = maxOf(maxGap, (stations[i] - stations[i-1]).toDouble())
        }
        
        // Binary search on the answer
        var low = 0.0
        var high = maxGap
        val precision = 1e-6
        
        // Continue until we reach desired precision
        while (high - low > precision) {
            val mid = (low + high) / 2.0
            
            // Calculate stations needed to achieve max distance = mid
            val stationsNeeded = countStationsNeeded(stations, mid)
            
            if (stationsNeeded <= k) {
                // Can achieve this distance, try smaller
                high = mid
            } else {
                // Need more stations than we have, try larger distance
                low = mid
            }
        }
        
        return low
    }
    
    /**
     * Calculate minimum stations needed to ensure max distance <= maxDist
     * 
     * @param stations Array of station positions
     * @param maxDist Maximum allowed distance between adjacent stations
     * @return Number of new stations needed
     */
    private fun countStationsNeeded(stations: IntArray, maxDist: Double): Int {
        var count = 0
        
        for (i in 1 until stations.size) {
            val gap = stations[i] - stations[i-1]
            
            // For a gap of length 'gap', to make all segments <= maxDist,
            // we need to divide it into ceil(gap/maxDist) parts
            // This requires ceil(gap/maxDist) - 1 new stations
            val stationsInGap = ceil(gap / maxDist).toInt() - 1
            count += stationsInGap
        }
        
        return count
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: stations = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 9
 * 
 * Original gaps: all are 1
 * max_gap = 1
 * 
 * Binary Search for max distance:
 * low = 0, high = 1
 * 
 * Try mid = 0.5:
 * Each gap of 1 needs: ceil(1/0.5) - 1 = 1 station
 * 9 gaps total, need 9 stations <= 9 ✓
 * high = 0.5
 * 
 * Try mid = 0.25:
 * Each gap of 1 needs: ceil(1/0.25) - 1 = 3 stations
 * 9 gaps total, need 27 stations > 9 ✗
 * low = 0.25
 * 
 * Continue until precision reached...
 * Answer ≈ 0.5
 * 
 * ============================================================================
 */

fun main() {
    val solver = MinimizeMaxDistance()
    
    println("=== Testing Minimize Max Distance to Gas Station ===\n")
    
    // Test Case 1: Uniform gaps
    val stations1 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val k1 = 9
    println("Test 1: stations = ${stations1.contentToString()}, k = $k1")
    println("Result: ${"%.6f".format(solver.solve(stations1, k1))}")
    println("Expected: ~0.500000\n")
    
    // Test Case 2: Non-uniform gaps
    val stations2 = intArrayOf(1, 2, 3, 4, 5)
    val k2 = 4
    println("Test 2: stations = ${stations2.contentToString()}, k = $k2")
    println("Result: ${"%.6f".format(solver.solve(stations2, k2))}")
    println("Expected: ~0.500000\n")
    
    // Test Case 3: Large gap
    val stations3 = intArrayOf(1, 13)
    val k3 = 3
    println("Test 3: stations = ${stations3.contentToString()}, k = $k3")
    println("Result: ${"%.6f".format(solver.solve(stations3, k3))}")
    println("Expected: ~3.000000\n")
    
    // Test Case 4: Two stations
    val stations4 = intArrayOf(0, 10)
    val k4 = 1
    println("Test 4: stations = ${stations4.contentToString()}, k = $k4")
    println("Result: ${"%.6f".format(solver.solve(stations4, k4))}")
    println("Expected: ~5.000000\n")
    
    // Test Case 5: Multiple gaps
    val stations5 = intArrayOf(1, 5, 10)
    val k5 = 2
    println("Test 5: stations = ${stations5.contentToString()}, k = $k5")
    println("Result: ${"%.6f".format(solver.solve(stations5, k5))}")
    println("Expected: ~2.500000\n")
    
    // Test Case 6: Many stations to add
    val stations6 = intArrayOf(0, 100)
    val k6 = 9
    println("Test 6: stations = ${stations6.contentToString()}, k = $k6")
    println("Result: ${"%.6f".format(solver.solve(stations6, k6))}")
    println("Expected: ~10.000000\n")
}
