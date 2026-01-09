/**
 * ============================================================================
 * PROBLEM:  Minimum Number of Days to Make m Bouquets
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT: 
 * You are given an integer array bloomDay, an integer m and an integer k.
 * 
 * You want to make m bouquets. To make a bouquet, you need to use k adjacent
 * flowers from the garden. The garden consists of n flowers, the ith flower
 * will bloom in the bloomDay[i] and can only be used in one bouquet.
 * 
 * Return the minimum number of days you need to wait to make m bouquets.
 * If it is impossible to make m bouquets, return -1.
 * 
 * INPUT FORMAT:
 * - bloomDay: Array where bloomDay[i] is day when flower i blooms
 * - m: Number of bouquets needed
 * - k: Number of adjacent flowers per bouquet
 * Example: bloomDay = [1,10,3,10,2], m = 3, k = 1
 * 
 * OUTPUT FORMAT: 
 * - Minimum days to wait, or -1 if impossible
 * Example: 3
 * 
 * CONSTRAINTS:
 * - bloomDay.length == n
 * - 1 <= n <= 10^5
 * - 1 <= bloomDay[i] <= 10^9
 * - 1 <= m <= 10^6
 * - 1 <= k <= n
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to find the minimum day when we can make m bouquets of k adjacent
 * flowers. As days increase, more flowers bloom, so more bouquets possible.
 * This monotonic property allows binary search on the day.
 * 
 * KEY INSIGHT:
 * - If m * k > n:  impossible (need more flowers than available)
 * - Search space: [min(bloomDay), max(bloomDay)]
 * - For a given day, count how many bouquets we can make
 * - Find minimum day where bouquets >= m
 * 
 * ALGORITHM STEPS:
 * 1. Check if m * k > n, return -1 if impossible
 * 2. Binary search on days:  low = min(bloomDay), high = max(bloomDay)
 * 3. For each mid day:
 *    - Count consecutive bloomed flowers
 *    - When k consecutive flowers found, increment bouquet count
 *    - If bouquets >= m:  try earlier day (high = mid - 1)
 *    - Else: need more days (low = mid + 1)
 * 4. Return low (minimum valid day)
 * 
 * VISUAL EXAMPLE: 
 * bloomDay = [1,10,3,10,2], m = 3, k = 1
 * Indices:     0  1  2  3  4
 * 
 * Each bouquet needs k=1 flower (any single flower)
 * Need m=3 bouquets
 * 
 * Day 1:  Flower 0 blooms → [B,_,_,_,_] → 1 bouquet
 * Day 2: Flower 4 blooms → [B,_,_,_,B] → 2 bouquets
 * Day 3: Flower 2 blooms → [B,_,B,_,B] → 3 bouquets ✓
 * 
 * Answer: 3
 * 
 * Example 2: bloomDay = [1,10,3,10,2], m = 3, k = 2
 * Need 3 bouquets of 2 adjacent flowers each = 6 flowers
 * But only 5 flowers available → impossible → -1
 * 
 * Example 3: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
 * 
 * Day 7: [B,B,B,B,_,B,B]
 * Adjacent groups: [0-2] (3), [1-3] (3), [5-6] (2)
 * Can make 2 bouquets from [0-2] and [5-6]?  No, [5-6] only has 2. 
 * Can make 2 bouquets from [0-2] and [1-3]? They overlap!
 * Actually:  First bouquet [0-2], second bouquet can't use [3] as we need
 * to continue from where we left.  Next group [5-6] only has 2.
 * At day 7: only 1 bouquet possible
 * 
 * Day 12: [B,B,B,B,B,B,B] → 2 bouquets ✓ ([0-2], [3-5])
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log d)
 * - Binary search: O(log d) where d = max - min bloom day
 * - Each iteration counts bouquets: O(n)
 * - Total: O(n * log d)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class MinDaysToMakeBouquets {
    
    /**
     * Finds minimum days to make m bouquets of k adjacent flowers
     * @param bloomDay Array of bloom days
     * @param m Number of bouquets needed
     * @param k Flowers per bouquet (must be adjacent)
     * @return Minimum days, or -1 if impossible
     */
    fun minDays(bloomDay: IntArray, m: Int, k: Int): Int {
        val n = bloomDay.size
        
        // Impossible if need more flowers than available
        if (m. toLong() * k > n) return -1
        
        var low = bloomDay. minOrNull() ?: 0
        var high = bloomDay. maxOrNull() ?: 0
        var result = -1
        
        while (low <= high) {
            val mid = low + (high - low) / 2
            val bouquets = countBouquets(bloomDay, mid, k)
            
            if (bouquets >= m) {
                result = mid  // mid works, try earlier day
                high = mid - 1
            } else {
                low = mid + 1  // Need more days
            }
        }
        
        return result
    }
    
    /**
     * Counts how many bouquets can be made by given day
     * @param bloomDay Array of bloom days
     * @param day Current day to check
     * @param k Flowers per bouquet
     * @return Number of bouquets possible
     */
    private fun countBouquets(bloomDay: IntArray, day: Int, k: Int): Int {
        var bouquets = 0
        var consecutive = 0
        
        for (bloom in bloomDay) {
            if (bloom <= day) {
                // Flower has bloomed
                consecutive++
                if (consecutive == k) {
                    // Made a bouquet
                    bouquets++
                    consecutive = 0  // Reset for next bouquet
                }
            } else {
                // Flower not bloomed yet, break consecutive chain
                consecutive = 0
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
 * Example 1: bloomDay = [1,10,3,10,2], m = 3, k = 1
 * 
 * Check:  3 * 1 = 3 <= 5 ✓ (possible)
 * Initial: low=1, high=10
 * 
 * Iteration 1: mid=5
 * Day 5: bloomed = [1,_,3,_,2] → indices [0,2,4]
 * Consecutive count: 1,0,1,0,1 → bouquets = 3
 * 3 >= 3, result=5, high=4
 * 
 * Iteration 2: mid=2
 * Day 2: bloomed = [1,_,_,_,2] → indices [0,4]
 * Consecutive:  1,0,0,0,1 → bouquets = 2
 * 2 < 3, low=3
 * 
 * Iteration 3: mid=3
 * Day 3: bloomed = [1,_,3,_,2] → indices [0,2,4]
 * Consecutive: 1,0,1,0,1 → bouquets = 3
 * 3 >= 3, result=3, high=2
 * 
 * low > high, return result=3 ✓
 * 
 * Example 2: bloomDay = [7,7,7,7,12,7,7], m = 2, k = 3
 * 
 * Check: 2 * 3 = 6 <= 7 ✓ (possible)
 * Initial: low=7, high=12
 * 
 * Mid=9 (or similar):
 * Day 9: bloomed = [7,7,7,7,_,7,7]
 * Consecutive tracking: 
 * - Index 0-3: consecutive=4, make bouquet at 3, consecutive=1
 * - Index 4: not bloomed, consecutive=0
 * - Index 5-6: consecutive=2
 * Total bouquets: 1 < 2
 * 
 * Need day 12: 
 * Day 12: all bloomed [7,7,7,7,12,7,7]
 * Consecutive:  4 (make bouquet, reset), 3 (make bouquet)
 * Total bouquets: 2 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = MinDaysToMakeBouquets()
    
    // Test Case 1: Each bouquet needs 1 flower
    println("Test 1: bloomDay=[1,10,3,10,2], m=3, k=1")
    println("Result: ${solution.minDays(intArrayOf(1,10,3,10,2), 3, 1)}")  
    // Expected: 3
    
    // Test Case 2: Impossible case
    println("\nTest 2: bloomDay=[1,10,3,10,2], m=3, k=2")
    println("Result: ${solution.minDays(intArrayOf(1,10,3,10,2), 3, 2)}")  
    // Expected: -1
    
    // Test Case 3: Need adjacent flowers
    println("\nTest 3: bloomDay=[7,7,7,7,12,7,7], m=2, k=3")
    println("Result: ${solution.minDays(intArrayOf(7,7,7,7,12,7,7), 2, 3)}")  
    // Expected: 12
    
    // Test Case 4: All same day
    println("\nTest 4: bloomDay=[1,1,1,1], m=2, k=2")
    println("Result: ${solution.minDays(intArrayOf(1,1,1,1), 2, 2)}")  
    // Expected: 1
    
    // Test Case 5: Sequential bloom days
    println("\nTest 5: bloomDay=[1,2,3,4,5,6], m=2, k=2")
    println("Result: ${solution.minDays(intArrayOf(1,2,3,4,5,6), 2, 2)}")  
    // Expected: 4
    
    // Detailed simulation
    println("\n--- Simulation for [1,10,3,10,2], m=3, k=1 ---")
    val bloom = intArrayOf(1,10,3,10,2)
    for (day in 1..10) {
        val status = bloom.map { if (it <= day) "B" else "_" }.joinToString("")
        val count = bloom.count { it <= day }
        println("Day $day: $status → $count bouquets")
    }
}
