/**
 * ============================================================================
 * PROBLEM: Koko Eating Bananas
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Koko loves to eat bananas. There are n piles of bananas, the ith pile has
 * piles[i] bananas. Koko can decide her bananas-per-hour eating speed k.
 * Each hour, she chooses a pile and eats k bananas from it. If the pile has
 * fewer than k bananas, she eats all of them and won't eat from another pile
 * during that hour. Return the minimum k such that Koko can eat all bananas
 * within h hours.
 * 
 * INPUT FORMAT:
 * - piles: Array where piles[i] = number of bananas in ith pile
 * - h: Hours available to eat all bananas
 * Example: piles = [3, 6, 7, 11], h = 8
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimum eating speed k (bananas per hour)
 * Example: 4 (takes 1+2+2+3 = 8 hours)
 * 
 * CONSTRAINTS:
 * - 1 <= piles.length <= 10^4
 * - piles.length <= h <= 10^9
 * - 1 <= piles[i] <= 10^9
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need to find minimum eating speed. If Koko can finish with speed k,
 * she can also finish with any speed > k. This monotonic property allows
 * binary search on the eating speed.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = 1 (minimum possible speed)
 *    high = max(piles) (eating fastest pile in 1 hour)
 * 2. Binary search on speed:
 *    - For each mid speed, calculate total hours needed
 *    - If hours <= h, try smaller speed (search left)
 *    - If hours > h, need faster speed (search right)
 * 3. Return minimum speed that works
 * 
 * VISUAL EXAMPLE:
 * piles = [3, 6, 7, 11], h = 8
 * 
 * Try speed = 6:
 * Pile 0: ceil(3/6) = 1 hour
 * Pile 1: ceil(6/6) = 1 hour
 * Pile 2: ceil(7/6) = 2 hours
 * Pile 3: ceil(11/6) = 2 hours
 * Total: 6 hours <= 8 ✓ Try slower
 * 
 * Try speed = 3:
 * Pile 0: ceil(3/3) = 1 hour
 * Pile 1: ceil(6/3) = 2 hours
 * Pile 2: ceil(7/3) = 3 hours
 * Pile 3: ceil(11/3) = 4 hours
 * Total: 10 hours > 8 ✗ Too slow
 * 
 * Try speed = 4:
 * Pile 0: ceil(3/4) = 1 hour
 * Pile 1: ceil(6/4) = 2 hours
 * Pile 2: ceil(7/4) = 2 hours
 * Pile 3: ceil(11/4) = 3 hours
 * Total: 8 hours == 8 ✓
 * 
 * Answer: 4
 * 
 * WHY BINARY SEARCH:
 * - Search space: [1 to max(piles)]
 * - Monotonic: If speed k works, all speeds > k also work
 * - Want minimum k that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(max))
 * - Binary search range: [1, max(piles)]
 * - Iterations: O(log(max(piles)))
 * - Each iteration: O(n) to calculate total hours
 * 
 * For n = 10^4, max = 10^9:
 * - Binary search: ~30 iterations
 * - Each check: 10,000 operations
 * - Total: ~300,000 operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class KokoEatingBananas {
    
    /**
     * Find minimum eating speed to finish all bananas in h hours
     * 
     * @param piles Array of banana pile sizes
     * @param h Hours available
     * @return Minimum eating speed (bananas/hour)
     */
    fun solve(piles: IntArray, h: Int): Int {
        // Search space: [1 to max pile size]
        // Minimum speed is 1 banana/hour
        var low = 1
        
        // Maximum useful speed is max pile size
        // (eating faster won't help as Koko eats one pile per hour max)
        var high = piles.max()
        
        var answer = high
        
        // Binary search on eating speed
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Calculate hours needed with this speed
            val hoursNeeded = calculateHours(piles, mid)
            
            if (hoursNeeded <= h) {
                // This speed works, try slower
                answer = mid
                high = mid - 1
            } else {
                // Too slow, need faster speed
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Calculate total hours needed to eat all piles at given speed
     * 
     * @param piles Array of pile sizes
     * @param speed Bananas per hour
     * @return Total hours needed
     */
    private fun calculateHours(piles: IntArray, speed: Int): Long {
        var totalHours = 0L
        
        for (pile in piles) {
            // For each pile, calculate hours needed
            // Use ceiling division: ceil(pile/speed) = (pile + speed - 1) / speed
            // This avoids floating point operations
            totalHours += (pile + speed - 1) / speed
        }
        
        return totalHours
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: piles = [3, 6, 7, 11], h = 8
 * 
 * Step 1: Initialize
 * low = 1, high = 11
 * 
 * Binary Search:
 * 
 * Iteration 1: low=1, high=11
 * - mid = 6
 * - Hours: ceil(3/6) + ceil(6/6) + ceil(7/6) + ceil(11/6)
 *        = 1 + 1 + 2 + 2 = 6 <= 8 ✓
 * - answer = 6, high = 5
 * 
 * Iteration 2: low=1, high=5
 * - mid = 3
 * - Hours: ceil(3/3) + ceil(6/3) + ceil(7/3) + ceil(11/3)
 *        = 1 + 2 + 3 + 4 = 10 > 8 ✗
 * - low = 4
 * 
 * Iteration 3: low=4, high=5
 * - mid = 4
 * - Hours: ceil(3/4) + ceil(6/4) + ceil(7/4) + ceil(11/4)
 *        = 1 + 2 + 2 + 3 = 8 <= 8 ✓
 * - answer = 4, high = 3
 * 
 * Iteration 4: low=4, high=3
 * - low > high, exit
 * 
 * OUTPUT: 4
 * 
 * ============================================================================
 */

fun main() {
    val solver = KokoEatingBananas()
    
    println("=== Testing Koko Eating Bananas ===\n")
    
    // Test Case 1: Normal case
    val piles1 = intArrayOf(3, 6, 7, 11)
    val h1 = 8
    println("Test 1: piles = ${piles1.contentToString()}, h = $h1")
    println("Result: ${solver.solve(piles1, h1)}")
    println("Expected: 4\n")
    
    // Test Case 2: Plenty of time
    val piles2 = intArrayOf(30, 11, 23, 4, 20)
    val h2 = 5
    println("Test 2: piles = ${piles2.contentToString()}, h = $h2")
    println("Result: ${solver.solve(piles2, h2)}")
    println("Expected: 30 (one pile per hour at max speed)\n")
    
    // Test Case 3: More time available
    val piles3 = intArrayOf(30, 11, 23, 4, 20)
    val h3 = 6
    println("Test 3: piles = ${piles3.contentToString()}, h = $h3")
    println("Result: ${solver.solve(piles3, h3)}")
    println("Expected: 23\n")
    
    // Test Case 4: Single pile
    val piles4 = intArrayOf(10)
    val h4 = 10
    println("Test 4: piles = ${piles4.contentToString()}, h = $h4")
    println("Result: ${solver.solve(piles4, h4)}")
    println("Expected: 1\n")
    
    // Test Case 5: All piles same size
    val piles5 = intArrayOf(5, 5, 5, 5)
    val h5 = 10
    println("Test 5: piles = ${piles5.contentToString()}, h = $h5")
    println("Result: ${solver.solve(piles5, h5)}")
    println("Expected: 2\n")
    
    // Test Case 6: Large piles
    val piles6 = intArrayOf(1000000000)
    val h6 = 2
    println("Test 6: piles = ${piles6.contentToString()}, h = $h6")
    println("Result: ${solver.solve(piles6, h6)}")
    println("Expected: 500000000\n")
}
