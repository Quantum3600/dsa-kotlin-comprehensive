/**
 * ============================================================================
 * PROBLEM:  Capacity To Ship Packages Within D Days
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search on Answers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A conveyor belt has packages that must be shipped from one port to another
 * within days days. The ith package on the conveyor belt has a weight of
 * weights[i]. Each day, we load the ship with packages on the conveyor belt
 * (in the order given by weights). We may not load more weight than the
 * maximum weight capacity of the ship. 
 * 
 * Return the least weight capacity of the ship that will result in all the
 * packages being shipped within days days.
 * 
 * INPUT FORMAT:
 * - weights: Array of package weights
 * - days: Number of days to ship all packages
 * Example: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
 * 
 * OUTPUT FORMAT: 
 * - Minimum ship capacity
 * Example: 15
 * 
 * CONSTRAINTS:
 * - 1 <= days <= weights.length <= 5 * 10^4
 * - 1 <= weights[i] <= 500
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION: 
 * We need to find the minimum ship capacity that allows shipping all packages
 * in 'days' days. As capacity increases, fewer days needed (inverse relationship).
 * This monotonic property allows binary search on capacity.
 * 
 * KEY INSIGHT:
 * - Minimum capacity: max(weights) (must carry heaviest package)
 * - Maximum capacity:  sum(weights) (carry everything in 1 day)
 * - For given capacity, simulate loading and count days needed
 * - Find minimum capacity where days needed <= given days
 * 
 * ALGORITHM STEPS:
 * 1. Set search space: low = max(weights), high = sum(weights)
 * 2. While low < high:
 *    - mid = (low + high) / 2
 *    - Calculate days needed with capacity mid
 *    - If days <= given days: capacity works, try smaller (high = mid)
 *    - Else:  capacity too small, need larger (low = mid + 1)
 * 3. Return low (minimum valid capacity)
 * 
 * VISUAL EXAMPLE:
 * weights = [1,2,3,4,5,6,7,8,9,10], days = 5
 * 
 * Total weight = 55
 * Heaviest = 10
 * 
 * Capacity = 15: 
 * Day 1: 1+2+3+4+5 = 15 ✓
 * Day 2: 6+7 = 13 < 15 ✓, can't add 8 (13+8=21>15)
 * Day 3: 8 < 15 ✓, can't add 9
 * Day 4: 9 < 15 ✓, can't add 10
 * Day 5: 10 ✓
 * Total: 5 days = required days ✓
 * 
 * Capacity = 14:
 * Day 1: 1+2+3+4 = 10, can't add 5 (would be 15>14)
 * Day 2: 5+6 = 11, can't add 7
 * Day 3: 7, can't add 8
 * Day 4: 8, can't add 9
 * Day 5: 9, can't add 10
 * Day 6: 10
 * Total: 6 days > 5 ✗
 * 
 * So minimum capacity = 15
 * 
 * Binary Search Process:
 * low=10, high=55
 * 
 * mid=32:  days=2 <= 5, try smaller, high=32
 * mid=21: days=3 <= 5, try smaller, high=21
 * mid=15: days=5 <= 5, try smaller, high=15
 * mid=12: days=6 > 5, need larger, low=13
 * mid=14: days=6 > 5, need larger, low=15
 * low==high=15, return 15 ✓
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search: O(log(sum - max)) where sum and max from weights
 * - Each iteration simulates loading: O(n)
 * - Total: O(n * log(sum - max))
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using constant extra space
 * 
 * ============================================================================
 */

package searching.binarysearch.answers

class CapacityToShipPackages {
    
    /**
     * Finds minimum ship capacity to ship all packages in given days
     * @param weights Array of package weights
     * @param days Number of days available
     * @return Minimum ship capacity
     */
    fun shipWithinDays(weights: IntArray, days: Int): Int {
        var low = weights. maxOrNull() ?: 0  // Must carry heaviest package
        var high = weights.sum()  // Carry all in one day
        
        while (low < high) {
            val mid = low + (high - low) / 2
            val daysNeeded = calculateDays(weights, mid)
            
            if (daysNeeded <= days) {
                // Capacity mid works, try smaller capacity
                high = mid
            } else {
                // Capacity too small, need larger
                low = mid + 1
            }
        }
        
        return low
    }
    
    /**
     * Calculates how many days needed with given capacity
     * @param weights Array of package weights
     * @param capacity Ship capacity
     * @return Number of days needed
     */
    private fun calculateDays(weights:  IntArray, capacity: Int): Int {
        var days = 1
        var currentLoad = 0
        
        for (weight in weights) {
            if (currentLoad + weight <= capacity) {
                // Can add this package to current day
                currentLoad += weight
            } else {
                // Need a new day for this package
                days++
                currentLoad = weight
            }
        }
        
        return days
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Example 1: weights = [1,2,3,4,5,6,7,8,9,10], days = 5
 * 
 * Initial: low=10, high=55
 * 
 * Iteration 1: mid=32
 * Simulate: 
 * - Day 1: 1+2+3+4+5+6+7+8 = 32
 * - Day 2: 9+10 = 19
 * Days needed: 2 <= 5, high=32
 * 
 * Iteration 2: mid=21
 * Simulate:
 * - Day 1: 1+2+3+4+5+6 = 21
 * - Day 2: 7+8 = 15
 * - Day 3: 9+10 = 19
 * Days needed: 3 <= 5, high=21
 * 
 * Iteration 3: mid=15
 * Simulate:
 * - Day 1: 1+2+3+4+5 = 15
 * - Day 2: 6+7 = 13
 * - Day 3: 8
 * - Day 4: 9
 * - Day 5: 10
 * Days needed: 5 <= 5, high=15
 * 
 * Iteration 4: mid=12
 * Simulate:
 * - Day 1: 1+2+3+4 = 10
 * - Day 2: 5+6 = 11
 * - Day 3: 7
 * - Day 4: 8
 * - Day 5: 9
 * - Day 6: 10
 * Days needed: 6 > 5, low=13
 * 
 * Iteration 5: mid=14
 * Simulate:  (similar to mid=12)
 * Days needed: 6 > 5, low=15
 * 
 * low == high = 15, return 15 ✓
 * 
 * Example 2: weights = [3,2,2,4,1,4], days = 3
 * 
 * low=4, high=16
 * 
 * mid=10: 
 * - Day 1: 3+2+2 = 7
 * - Day 2: 4+1+4 = 9
 * Days:  2 <= 3, high=10
 * 
 * mid=7:
 * - Day 1: 3+2+2 = 7
 * - Day 2: 4+1 = 5
 * - Day 3: 4
 * Days: 3 <= 3, high=7
 * 
 * mid=5:
 * - Day 1: 3+2 = 5
 * - Day 2: 2+4 = 6 > 5, can't fit
 * - Day 2: 2
 * - Day 3: 4+1 = 5
 * - Day 4: 4
 * Days: 4 > 3, low=6
 * 
 * mid=6:
 * - Day 1: 3+2 = 5
 * - Day 2: 2+4 = 6
 * - Day 3: 1+4 = 5
 * Days: 3 <= 3, high=6
 * 
 * low == high = 6, return 6 ✓
 * 
 * ============================================================================
 */

fun main() {
    val solution = CapacityToShipPackages()
    
    // Test Case 1: Standard case
    println("Test 1: weights=[1,2,3,4,5,6,7,8,9,10], days=5")
    println("Result: ${solution.shipWithinDays(intArrayOf(1,2,3,4,5,6,7,8,9,10), 5)}")
    // Expected: 15
    
    // Test Case 2: Mixed weights
    println("\nTest 2: weights=[3,2,2,4,1,4], days=3")
    println("Result: ${solution.shipWithinDays(intArrayOf(3,2,2,4,1,4), 3)}")
    // Expected: 6
    
    // Test Case 3: Ship in 1 day
    println("\nTest 3: weights=[1,2,3,4,5], days=1")
    println("Result: ${solution.shipWithinDays(intArrayOf(1,2,3,4,5), 1)}")
    // Expected: 15 (sum of all)
    
    // Test Case 4: One package per day
    println("\nTest 4: weights=[10,20,30,40], days=4")
    println("Result: ${solution.shipWithinDays(intArrayOf(10,20,30,40), 4)}")
    // Expected: 40 (max weight)
    
    // Test Case 5: All same weight
    println("\nTest 5: weights=[5,5,5,5,5], days=2")
    println("Result: ${solution.shipWithinDays(intArrayOf(5,5,5,5,5), 2)}")
    // Expected: 15
    
    // Detailed simulation for Test 1
    println("\n--- Simulation for [1,2,3,4,5,6,7,8,9,10], days=5 ---")
    val weights = intArrayOf(1,2,3,4,5,6,7,8,9,10)
    for (capacity in listOf(10, 12, 14, 15, 20, 32)) {
        var days = 1
        var load = 0
        val dayLoads = mutableListOf<MutableList<Int>>()
        dayLoads.add(mutableListOf())
        
        for (w in weights) {
            if (load + w <= capacity) {
                load += w
                dayLoads.last().add(w)
            } else {
                days++
                load = w
                dayLoads.add(mutableListOf(w))
            }
        }
        
        val status = if (days <= 5) "✓" else "✗"
        println("Capacity=$capacity:  $days days $status")
        dayLoads.forEachIndexed { i, loads ->
            println("  Day ${i+1}: ${loads.joinToString("+")} = ${loads.sum()}")
        }
    }
}
