/**
 * ============================================================================
 * PROBLEM:  Minimize Maximum Distance to Gas Station
 * DIFFICULTY: Hard
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an integer array stations representing the positions of gas
 * stations along a highway, and an integer k representing the number of new
 * gas stations you can add. The positions are in sorted order.
 * 
 * Add k new gas stations to minimize the maximum distance between adjacent
 * gas stations. Return the smallest possible maximum distance.
 * 
 * The answer is accepted if it is within 10^-6 of the actual answer.
 * 
 * INPUT FORMAT:
 * - stations:  Sorted array of station positions
 * - k: Number of new stations to add
 * Example: stations = [1,2,3,4,5,6,7,8,9,10], k = 9
 * 
 * OUTPUT FORMAT:
 * - Minimized maximum distance (within 10^-6 precision)
 * Example: 0.5
 * 
 * CONSTRAINTS:
 * - 10 <= stations.length <= 2 * 10^4
 * - 0 <= stations[i] <= 10^8
 * - stations is sorted in strictly increasing order
 * - 1 <= k <= 10^6
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We want to minimize the maximum gap between adjacent stations. Use binary
 * search on the answer (maximum distance). For a given max distance, check
 * if we can add k stations such that no gap exceeds that distance.
 * 
 * KEY INSIGHT:
 * - If gap between station i and i+1 is D, and max allowed distance is d: 
 *   - Stations needed = ceil(D/d) - 1 = floor(D/d) if D is exact multiple
 *   - Or:  (int)((D - epsilon) / d) where epsilon is very small
 *   - Or: (int)(D / d) if we want stations at equal intervals
 * 
 * - Search space: [0, max gap in original array]
 * - Binary search with floating point precision
 * 
 * ALGORITHM STEPS:
 * 1. Calculate initial max gap in stations array
 * 2. Binary search:  low = 0, high = max gap
 * 3. For each mid (candidate max distance):
 *    - Calculate how many stations needed to ensure no gap > mid
 *    - For each gap:  stations_needed += (int)(gap / mid)
 *    - If total stations <= k:  valid, try smaller distance (high = mid)
 *    - Else: need larger max distance (low = mid)
 * 4. Continue until precision reached (typically 1e-6)
 * 
 * VISUAL EXAMPLE:
 * stations = [1,2,3,4,5,6,7,8,9,10], k = 9
 * Gaps: all are 1 unit
 * Total 9 gaps of 1 unit each
 * 
 * With 9 new stations optimally placed:
 * Can split each gap in half
 * Max distance = 0.5
 * 
 * Example placement:
 * Original: 1___2___3___4___5___6___7___8___9___10
 * New:       1_*_2_*_3_*_4_*_5_*_6_*_7_*_8_*_9_*_10
 * Each gap is now 0.5 ✓
 * 
 * Example 2: stations = [1,5,10], k = 2
 * Gaps: [4, 5]
 * Max gap = 5
 * 
 * Try maxDist = 2. 5:
 * - Gap 1-5 (length 4): need ceil(4/2.5)-1 = 2-1 = 1 station
 * - Gap 5-10 (length 5): need ceil(5/2.5)-1 = 2-1 = 1 station
 * Total:  2 stations = k ✓
 * 
 * Placement:  1___3___5___7.5___10
 * Max distance:  2.5 (but can we do better?)
 * 
 * Try maxDist = 2.0:
 * - Gap 1-5: need ceil(4/2)-1 = 2-1 = 1 station
 * - Gap 5-10: need ceil(5/2)-1 = 3-1 = 2 stations
 * Total: 3 stations > 2 ✗
 * 
 * So answer is between 2.0 and 2.5, converges to ~2.5
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY:  O(n * log(max_gap / precision))
 * - Binary search: O(log(max_gap / 1e-6))
 * - Each iteration calculates stations needed:  O(n)
 * 
 * SPACE COMPLEXITY:  O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch. answers

import kotlin.math.ceil

class MinimizeMaxDistance {
    
    /**
     * Finds minimum possible maximum distance between adjacent stations
     * @param stations Sorted array of station positions
     * @param k Number of new stations to add
     * @return Minimized maximum distance
     */
    fun minmaxGasDist(stations: IntArray, k: Int): Double {
        val n = stations.size
        
        // Find initial maximum gap
        var maxGap = 0.0
        for (i in 1 until n) {
            maxGap = maxOf(maxGap, (stations[i] - stations[i - 1]).toDouble())
        }
        
        // Binary search on the answer
        var low = 0.0
        var high = maxGap
        val precision = 1e-6
        
        while (high - low > precision) {
            val mid = (low + high) / 2.0
            val stationsNeeded = countStationsNeeded(stations, mid)
            
            if (stationsNeeded <= k) {
                // Can achieve max distance = mid
                high = mid
            } else {
                // Need larger max distance
                low = mid
            }
        }
        
        return low
    }
    
    /**
     * Counts how many new stations needed to ensure max gap <= maxDist
     * @param stations Sorted array of positions
     * @param maxDist Maximum allowed distance
     * @return Number of stations needed
     */
    private fun countStationsNeeded(stations:  IntArray, maxDist: Double): Int {
        var count = 0
        
        for (i in 1 until stations.size) {
            val gap = stations[i] - stations[i - 1]
            
            // Number of stations needed for this gap
            // If gap = 10 and maxDist = 3, need ceil(10/3) - 1 = 4 - 1 = 3 stations
            // This creates 4 segments of length <= 3
            val stationsForGap = ((gap / maxDist).toInt())
            count += stationsForGap
        }
        
        return count
    }
    
    /**
     * Alternative with explicit ceiling calculation
     */
    private fun countStationsNeededAlt(stations: IntArray, maxDist: Double): Int {
        var count = 0
        
        for (i in 1 until stations.size) {
            val gap = (stations[i] - stations[i - 1]).toDouble()
            
            if (gap > maxDist) {
                // Number of new stations = ceil(gap / maxDist) - 1
                val segments = ceil(gap / maxDist).toInt()
                count += segments - 1
            }
        }
        
        return count
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: stations = [1,2,3,4,5,6,7,8,9,10], k = 9
 * 
 * Initial gaps:  all 1.0
 * maxGap = 1.0
 * 
 * Binary search:  low=0.0, high=1.0
 * 
 * Iteration 1: mid=0.5
 * Count stations for each gap:
 * - Each gap of 1.0: need floor(1.0/0.5) = 2 segments - 1 = 1 station
 * - 9 gaps × 1 station = 9 stations
 * 9 <= 9 ✓, high=0.5
 * 
 * Iteration 2: mid=0.25
 * Count stations: 
 * - Each gap:  need floor(1.0/0.25) = 4 segments - 1 = 3 stations
 * - 9 gaps × 3 stations = 27 stations
 * 27 > 9 ✗, low=0.25
 * 
 * Continue binary search...
 * Converges to 0.5 ✓
 * 
 * Example 2: stations = [1,5,10], k = 2
 * 
 * Gaps: [4, 5]
 * maxGap = 5.0
 * low=0.0, high=5.0
 * 
 * mid=2.5:
 * - Gap 4:  floor(4/2.5) = 1 station
 * - Gap 5: floor(5/2.5) = 2 stations
 * Total: 3 > 2 ✗, low=2.5
 * 
 * mid=3.75:
 * - Gap 4: floor(4/3.75) = 1 station
 * - Gap 5: floor(5/3.75) = 1 station
 * Total: 2 <= 2 ✓, high=3.75
 * 
 * Continue...
 * Converges to ~2.5 ✓
 * 
 * Example 3: stations = [1,13,17,23], k = 5
 * 
 * Gaps: [12, 4, 6]
 * maxGap = 12.0
 * 
 * Need to distribute 5 stations optimally
 * Gap 12 needs most attention
 * 
 * Optimal:  Put 3 in gap of 12, 1 in gap of 6, 1 in gap of 4
 * Gap 12 → 4 segments of 3 each
 * Gap 6 → 2 segments of 3 each
 * Gap 4 → 2 segments of 2 each
 * Max = 3. 0
 * 
 * Or: Put more in gap 12
 * Gap 12 → 5 segments of 2. 4 (needs 4 stations)
 * Gap 6 → 1 segment (needs 0 stations)
 * Gap 4 → 1 segment (needs 0 stations)
 * But only used 4 stations, have 1 left
 * 
 * Binary search will find optimal around 2.4-3.0
 * 
 * ============================================================================
 */

fun main() {
    val solution = MinimizeMaxDistance()
    
    // Test Case 1: Uniform gaps
    println("Test 1: stations=[1,2,3,4,5,6,7,8,9,10], k=9")
    val result1 = solution.minmaxGasDist(intArrayOf(1,2,3,4,5,6,7,8,9,10), 9)
    println("Result: ${"%.6f".format(result1)}")
    // Expected: 0.5
    
    // Test Case 2: Non-uniform gaps
    println("\nTest 2: stations=[1,5,10], k=2")
    val result2 = solution.minmaxGasDist(intArrayOf(1,5,10), 2)
    println("Result: ${"%.6f".format(result2)}")
    // Expected: ~2.5
    
    // Test Case 3: Large gap
    println("\nTest 3: stations=[1,13,17,23], k=5")
    val result3 = solution. minmaxGasDist(intArrayOf(1,13,17,23), 5)
    println("Result: ${"%. 6f".format(result3)}")
    // Expected: ~3.0 or less
    
    // Test Case 4: Many stations for one gap
    println("\nTest 4: stations=[0,10], k=5")
    val result4 = solution.minmaxGasDist(intArrayOf(0,10), 5)
    println("Result: ${"%.6f".format(result4)}")
    // Expected: ~1.666...  (10/6)
    
    // Test Case 5: No need for many stations
    println("\nTest 5: stations=[1,2,3,4,5], k=100")
    val result5 = solution.minmaxGasDist(intArrayOf(1,2,3,4,5), 100)
    println("Result: ${"%.6f".format(result5)}")
    // Expected: very small, close to 0
    
    // Detailed simulation for Test 2
    println("\n--- Simulation for [1,5,10], k=2 ---")
    val stations = intArrayOf(1,5,10)
    val testDistances = listOf(5.0, 4.0, 3.0, 2.5, 2.0, 1.5)
    
    for (dist in testDistances) {
        var count = 0
        println("\nTrying maxDist = $dist:")
        for (i in 1 until stations.size) {
            val gap = stations[i] - stations[i - 1]
            val needed = (gap. toDouble() / dist).toInt()
            count += needed
            println("  Gap ${stations[i-1]}-${stations[i]} (length $gap): needs $needed stations")
        }
        val status = if (count <= 2) "✓" else "✗"
        println("  Total:  $count stations $status")
    }
}
