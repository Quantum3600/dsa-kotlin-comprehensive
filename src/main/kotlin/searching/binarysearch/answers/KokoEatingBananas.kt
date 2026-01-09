/**
 * ============================================================================
 * PROBLEM: Koko Eating Bananas
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has
 * piles[i] bananas. The guards have gone and will come back in h hours.
 * 
 * Koko can decide her bananas-per-hour eating speed k. Each hour, she chooses
 * a pile and eats k bananas from that pile. If the pile has less than k bananas,
 * she eats all of them and won't eat any more bananas during that hour.
 * 
 * Return the minimum integer k such that she can eat all the bananas within h hours.
 * 
 * INPUT FORMAT:
 * - piles: Array where piles[i] is the number of bananas in pile i
 * - h: Number of hours before guards return
 * Example: piles = [3,6,7,11], h = 8
 * 
 * OUTPUT FORMAT:
 * - Minimum eating speed k
 * Example: 4
 * 
 * CONSTRAINTS:
 * - 1 <= piles.length <= 10^4
 * - piles.length <= h <= 10^9
 * - 1 <= piles[i] <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to find the minimum speed k that allows eating all bananas in h hours.
 * For a given speed k, time needed = sum of ceil(pile[i] / k) for all piles.
 * 
 * As k increases, time decreases (inverse relationship).
 * This monotonic property allows binary search on the speed.
 * 
 * KEY INSIGHT:
 * - Minimum possible k: 1 banana/hour
 * - Maximum possible k: max(piles) (eat largest pile in 1 hour)
 * - If k >= max(piles), time = number of piles
 * - Find smallest k where time <= h
 * 
 * ALGORITHM STEPS:
 * 1. Set search space: low = 1, high = max(piles)
 * 2. While low < high:
 *    - mid = (low + high) / 2
 *    - Calculate hours needed with speed mid
 *    - If hours <= h: answer could be mid or smaller, high = mid
 *    - If hours > h: speed too slow, low = mid + 1
 * 3. Return low (minimum valid speed)
 * 
 * VISUAL EXAMPLE: 
 * piles = [3, 6, 7, 11], h = 8
 * 
 * Speed k=1: 3+6+7+11 = 27 hours > 8 (too slow)
 * Speed k=2: 2+3+4+6 = 15 hours > 8
 * Speed k=3: 1+2+3+4 = 10 hours > 8
 * Speed k=4: 1+2+2+3 = 8 hours = 8 ✓ (valid!)
 * Speed k=5: 1+2+2+3 = 8 hours = 8
 * Speed k=11: 1+1+1+1 = 4 hours < 8
 * 
 * Minimum valid speed = 4
 * 
 * Detailed for k=4:
 * - Pile 3: ceil(3/4) = 1 hour
 * - Pile 6: ceil(6/4) = 2 hours
 * - Pile 7: ceil(7/4) = 2 hours
 * - Pile 11: ceil(11/4) = 3 hours
 * Total: 8 hours ✓
 * 
 * Binary Search Process:
 * [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
 *  L                               H
 *                 M=6
 * hours=6 <= 8, try smaller:  high=6
 * 
 * [1, 2, 3, 4, 5, 6]
 *  L              H
 *        M=3
 * hours=10 > 8, need faster: low=4
 * 
 * [4, 5, 6]
 *  L     H
 *  M=5
 * hours=8 <= 8, try smaller: high=5
 * 
 * [4, 5]
 *  L  H
 *  M=4
 * hours=8 <= 8, try smaller: high=4
 * 
 * L=H=4, return 4 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log m)
 * - Binary search: O(log m) where m = max(piles)
 * - Each iteration calculates hours: O(n)
 * - Total: O(n * log m)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

import kotlin.math.ceil

class KokoEatingBananas {
    
    /**
     * Finds minimum eating speed to finish all bananas in h hours
     * @param piles Array of banana piles
     * @param h Hours available
     * @return Minimum eating speed
     */
    fun minEatingSpeed(piles: IntArray, h: Int): Int {
        var low = 1
        var high = piles.maxOrNull() ?: 1
        
        while (low < high) {
            val mid = low + (high - low) / 2
            val hoursNeeded = calculateHours(piles, mid)
            
            if (hoursNeeded <= h) {
                // Speed mid works, try smaller speed
                high = mid
            } else {
                // Speed too slow, need faster
                low = mid + 1
            }
        }
        
        return low
    }
    
    /**
     * Calculates total hours needed to eat all bananas at given speed
     * @param piles Array of banana piles
     * @param speed Eating speed (bananas per hour)
     * @return Total hours needed
     */
    private fun calculateHours(piles: IntArray, speed: Int): Long {
        var hours = 0L
        for (pile in piles) {
            // Ceiling division: (pile + speed - 1) / speed
            hours += (pile + speed - 1) / speed
        }
        return hours
    }
    
    /**
     * Alternative using Kotlin's ceil function
     */
    private fun calculateHoursAlternative(piles: IntArray, speed: Int): Long {
        return piles.sumOf { pile ->
            ceil(pile.toDouble() / speed).toLong()
        }
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: piles = [3,6,7,11], h = 8
 * 
 * Initial: low=1, high=11
 * 
 * Iteration 1: mid=6
 * hours = ceil(3/6) + ceil(6/6) + ceil(7/6) + ceil(11/6)
 *       = 1 + 1 + 2 + 2 = 6 <= 8
 * high = 6
 * 
 * Iteration 2: mid=3
 * hours = ceil(3/3) + ceil(6/3) + ceil(7/3) + ceil(11/3)
 *       = 1 + 2 + 3 + 4 = 10 > 8
 * low = 4
 * 
 * Iteration 3: mid=5
 * hours = ceil(3/5) + ceil(6/5) + ceil(7/5) + ceil(11/5)
 *       = 1 + 2 + 2 + 3 = 8 <= 8
 * high = 5
 * 
 * Iteration 4: mid=4
 * hours = ceil(3/4) + ceil(6/4) + ceil(7/4) + ceil(11/4)
 *       = 1 + 2 + 2 + 3 = 8 <= 8
 * high = 4
 * 
 * low == high = 4, return 4 ✓
 * 
 * Example 2: piles = [30,11,23,4,20], h = 5
 * 
 * Number of piles = 5, h = 5
 * Need to finish each pile in 1 hour
 * Minimum speed = max(piles) = 30
 * 
 * Binary search will converge to 30
 * 
 * Example 3: piles = [30,11,23,4,20], h = 6
 * 
 * Can now take 6 hours for 5 piles
 * One pile can take 2 hours
 * 
 * Speed = 23: 
 * - 30: 2 hours
 * - 11: 1 hour
 * - 23: 1 hour
 * - 4: 1 hour
 * - 20: 1 hour
 * Total: 6 hours ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = KokoEatingBananas()
    
    // Test Case 1: Standard case
    println("Test 1: piles=[3,6,7,11], h=8")
    println("Result: ${solution. minEatingSpeed(intArrayOf(3,6,7,11), 8)}")  
    // Expected: 4
    
    // Test Case 2: Must finish each pile in 1 hour
    println("\nTest 2: piles=[30,11,23,4,20], h=5")
    println("Result: ${solution.minEatingSpeed(intArrayOf(30,11,23,4,20), 5)}")  
    // Expected: 30
    
    // Test Case 3: More time available
    println("\nTest 3: piles=[30,11,23,4,20], h=6")
    println("Result:  ${solution.minEatingSpeed(intArrayOf(30,11,23,4,20), 6)}")  
    // Expected: 23
    
    // Test Case 4: Large h
    println("\nTest 4: piles=[1,1,1,1], h=10")
    println("Result: ${solution.minEatingSpeed(intArrayOf(1,1,1,1), 10)}")  
    // Expected: 1
    
    // Test Case 5: Single pile
    println("\nTest 5: piles=[1000000000], h=2")
    println("Result: ${solution.minEatingSpeed(intArrayOf(1000000000), 2)}")  
    // Expected: 500000000
    
    // Detailed walkthrough
    println("\n--- Detailed Walkthrough for [3,6,7,11], h=8 ---")
    val piles = intArrayOf(3,6,7,11)
    for (speed in 1..11) {
        val hours = piles.sumOf { (it + speed - 1) / speed }
        val status = if (hours <= 8) "✓" else "✗"
        println("Speed=$speed: hours=$hours $status")
    }
}
