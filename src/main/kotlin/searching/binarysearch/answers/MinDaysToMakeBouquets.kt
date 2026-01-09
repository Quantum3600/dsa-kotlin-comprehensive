/**
 * ============================================================================
 * PROBLEM: Minimum Days to Make M Bouquets
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are given an array bloomDay where bloomDay[i] is the day that the ith
 * flower blooms. You need to make m bouquets, each requiring k adjacent flowers.
 * Find the minimum number of days you need to wait to make m bouquets. If it's
 * impossible, return -1.
 * 
 * INPUT FORMAT:
 * - bloomDay: Array where bloomDay[i] = day ith flower blooms
 * - m: Number of bouquets needed
 * - k: Number of adjacent flowers per bouquet
 * Example: bloomDay = [1, 10, 3, 10, 2], m = 3, k = 1
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimum days to wait, or -1 if impossible
 * Example: 3 (by day 3, flowers at indices 0, 2, 4 have bloomed)
 * 
 * CONSTRAINTS:
 * - bloomDay.length == n
 * - 1 <= n <= 10^5
 * - 1 <= bloomDay[i] <= 10^9
 * - 1 <= m <= 10^6
 * - 1 <= k <= n
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We want minimum days to make m bouquets. If we can make them by day D,
 * we can also make them by any day > D (monotonic). Binary search on days.
 * 
 * KEY INSIGHT:
 * For a given day, count how many bouquets we can make by checking adjacent
 * bloomed flowers. Use greedy approach: make bouquet as soon as k adjacent
 * flowers are available.
 * 
 * ALGORITHM STEPS:
 * 1. Check if possible: if m * k > n, return -1
 * 2. Set search space:
 *    low = min(bloomDay) (earliest any flower blooms)
 *    high = max(bloomDay) (wait for all flowers)
 * 3. Binary search on days:
 *    - For each day, count bouquets possible
 *    - If bouquets >= m, try earlier day (search left)
 *    - If bouquets < m, need more days (search right)
 * 4. Return minimum valid day
 * 
 * VISUAL EXAMPLE:
 * bloomDay = [1, 10, 3, 10, 2], m = 3, k = 1
 * 
 * Day 3:
 * Flowers: [bloomed(1), not(10), bloomed(3), not(10), bloomed(2)]
 * Indices:  0          1          2          3          4
 * Bouquets from: [0], [2], [4] = 3 bouquets ✓
 * 
 * Day 2:
 * Flowers: [bloomed(1), not(10), not(3), not(10), bloomed(2)]
 * Bouquets from: [0], [4] = 2 bouquets < 3 ✗
 * 
 * Answer: 3
 * 
 * WHY BINARY SEARCH:
 * - Search space: [min(bloomDay) to max(bloomDay)]
 * - Monotonic: If D days work, D+1 days also work
 * - Want minimum D that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(max - min))
 * - Binary search range: [min, max] of bloomDay
 * - Iterations: O(log(max - min))
 * - Each iteration counts bouquets: O(n)
 * 
 * For n = 10^5, max = 10^9:
 * - Binary search: ~30 iterations
 * - Each check: 100,000 operations
 * - Total: ~3M operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class MinDaysToMakeBouquets {
    
    /**
     * Find minimum days to make m bouquets of k adjacent flowers
     * 
     * @param bloomDay Array where bloomDay[i] = day ith flower blooms
     * @param m Number of bouquets needed
     * @param k Adjacent flowers per bouquet
     * @return Minimum days, or -1 if impossible
     */
    fun solve(bloomDay: IntArray, m: Int, k: Int): Int {
        // Check if it's possible at all
        // We need m * k flowers total
        val n = bloomDay.size.toLong()
        if (m.toLong() * k > n) {
            return -1  // Not enough flowers
        }
        
        // Search space: [earliest bloom to latest bloom]
        var low = bloomDay.min()
        var high = bloomDay.max()
        
        var answer = -1
        
        // Binary search on days
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Count bouquets possible by day mid
            val bouquets = countBouquets(bloomDay, mid, k)
            
            if (bouquets >= m) {
                // Enough bouquets, try earlier day
                answer = mid
                high = mid - 1
            } else {
                // Not enough bouquets, need more days
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Count how many bouquets can be made by given day
     * Uses greedy approach: make bouquet as soon as k adjacent flowers bloom
     * 
     * @param bloomDay Array of bloom days
     * @param day Current day
     * @param k Flowers per bouquet
     * @return Number of bouquets possible
     */
    private fun countBouquets(bloomDay: IntArray, day: Int, k: Int): Int {
        var bouquets = 0
        var adjacentCount = 0
        
        for (bloom in bloomDay) {
            if (bloom <= day) {
                // Flower has bloomed
                adjacentCount++
                
                // If we have k adjacent bloomed flowers, make a bouquet
                if (adjacentCount == k) {
                    bouquets++
                    adjacentCount = 0  // Reset for next bouquet
                }
            } else {
                // Flower hasn't bloomed yet, break the adjacency
                adjacentCount = 0
            }
        }
        
        return bouquets
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: bloomDay = [1, 10, 3, 10, 2], m = 3, k = 1
 * 
 * Step 1: Check if possible
 * - Need 3 * 1 = 3 flowers
 * - Have 5 flowers ✓
 * 
 * Step 2: Initialize
 * low = 1, high = 10
 * 
 * Binary Search:
 * 
 * Iteration 1: low=1, high=10
 * - mid = 5
 * - Flowers bloomed by day 5: [1, x, 3, x, 2]
 * - Adjacent groups: [1], [3], [2] = 3 bouquets >= 3 ✓
 * - answer = 5, high = 4
 * 
 * Iteration 2: low=1, high=4
 * - mid = 2
 * - Flowers bloomed by day 2: [1, x, x, x, 2]
 * - Adjacent groups: [1], [2] = 2 bouquets < 3 ✗
 * - low = 3
 * 
 * Iteration 3: low=3, high=4
 * - mid = 3
 * - Flowers bloomed by day 3: [1, x, 3, x, 2]
 * - Adjacent groups: [1], [3], [2] = 3 bouquets >= 3 ✓
 * - answer = 3, high = 2
 * 
 * Iteration 4: low=3, high=2
 * - low > high, exit
 * 
 * OUTPUT: 3
 * 
 * ============================================================================
 */

fun main() {
    val solver = MinDaysToMakeBouquets()
    
    println("=== Testing Min Days to Make Bouquets ===\n")
    
    // Test Case 1: Each bouquet needs 1 flower
    val bloomDay1 = intArrayOf(1, 10, 3, 10, 2)
    val m1 = 3
    val k1 = 1
    println("Test 1: bloomDay = ${bloomDay1.contentToString()}, m = $m1, k = $k1")
    println("Result: ${solver.solve(bloomDay1, m1, k1)}")
    println("Expected: 3\n")
    
    // Test Case 2: Each bouquet needs 2 adjacent flowers
    val bloomDay2 = intArrayOf(1, 10, 3, 10, 2)
    val m2 = 3
    val k2 = 2
    println("Test 2: bloomDay = ${bloomDay2.contentToString()}, m = $m2, k = $k2")
    println("Result: ${solver.solve(bloomDay2, m2, k2)}")
    println("Expected: -1 (impossible)\n")
    
    // Test Case 3: Normal case
    val bloomDay3 = intArrayOf(7, 7, 7, 7, 12, 7, 7)
    val m3 = 2
    val k3 = 3
    println("Test 3: bloomDay = ${bloomDay3.contentToString()}, m = $m3, k = $k3")
    println("Result: ${solver.solve(bloomDay3, m3, k3)}")
    println("Expected: 12\n")
    
    // Test Case 4: All flowers bloom on same day
    val bloomDay4 = intArrayOf(5, 5, 5, 5, 5, 5)
    val m4 = 2
    val k4 = 3
    println("Test 4: bloomDay = ${bloomDay4.contentToString()}, m = $m4, k = $k4")
    println("Result: ${solver.solve(bloomDay4, m4, k4)}")
    println("Expected: 5\n")
    
    // Test Case 5: Single bouquet
    val bloomDay5 = intArrayOf(1, 2, 3, 4, 5)
    val m5 = 1
    val k5 = 3
    println("Test 5: bloomDay = ${bloomDay5.contentToString()}, m = $m5, k = $k5")
    println("Result: ${solver.solve(bloomDay5, m5, k5)}")
    println("Expected: 3\n")
    
    // Test Case 6: Not enough flowers
    val bloomDay6 = intArrayOf(1, 2, 3)
    val m6 = 2
    val k6 = 2
    println("Test 6: bloomDay = ${bloomDay6.contentToString()}, m = $m6, k = $k6")
    println("Result: ${solver.solve(bloomDay6, m6, k6)}")
    println("Expected: -1 (need 4 flowers but only have 3)\n")
}
