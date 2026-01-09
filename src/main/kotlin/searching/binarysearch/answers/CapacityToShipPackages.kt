/**
 * ============================================================================
 * PROBLEM: Capacity To Ship Packages Within D Days
 * DIFFICULTY: Medium
 * CATEGORY: Binary Search, Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * A conveyor belt has packages that must be shipped from one port to another
 * within days days. The ith package has weight weights[i]. Each day, we load
 * packages in the order given onto the ship up to the ship's weight capacity.
 * Find the minimum ship capacity needed to ship all packages within days days.
 * 
 * INPUT FORMAT:
 * - weights: Array where weights[i] = weight of ith package
 * - days: Number of days to ship all packages
 * Example: weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], days = 5
 * 
 * OUTPUT FORMAT:
 * - Integer: Minimum ship capacity needed
 * Example: 15
 * 
 * CONSTRAINTS:
 * - 1 <= days <= weights.length <= 5 * 10^4
 * - 1 <= weights[i] <= 500
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * We need minimum capacity that allows shipping in given days. If capacity C
 * works, any capacity > C also works (monotonic). We can binary search on
 * the capacity value.
 * 
 * ALGORITHM STEPS:
 * 1. Set search space:
 *    low = max(weights) (must carry heaviest package)
 *    high = sum(weights) (carry all in one day)
 * 2. Binary search on capacity:
 *    - For each capacity, check if can ship in given days
 *    - If yes, try smaller capacity (search left)
 *    - If no, need larger capacity (search right)
 * 3. Return minimum working capacity
 * 
 * VISUAL EXAMPLE:
 * weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], days = 5
 * 
 * Try capacity = 15:
 * Day 1: 1+2+3+4+5 = 15 ✓
 * Day 2: 6+7 = 13 ✓
 * Day 3: 8 = 8 ✓
 * Day 4: 9 = 9 ✓
 * Day 5: 10 = 10 ✓
 * Total: 5 days ✓
 * 
 * Try capacity = 10:
 * Day 1: 1+2+3+4 = 10 ✓
 * Day 2: 5 = 5 ✓
 * Day 3: 6 = 6 ✓
 * Day 4: 7 = 7 ✓
 * Day 5: 8 = 8 ✓
 * Day 6: 9 = 9 ✗ Need 6 days!
 * 
 * Answer: 15
 * 
 * WHY BINARY SEARCH:
 * - Search space: [max(weights) to sum(weights)]
 * - Monotonic: If capacity C works, all C' > C also work
 * - Want minimum C that works
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n * log(sum - max))
 * - Binary search range: [max, sum]
 * - Iterations: O(log(sum - max))
 * - Each iteration checks shipment: O(n)
 * 
 * For n = 50,000, max = 500, sum = 25,000,000:
 * - Binary search: ~25 iterations
 * - Each check: 50,000 operations
 * - Total: ~1.25M operations
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only constant extra space
 */

package searching.binarysearch.answers

class CapacityToShipPackages {
    
    /**
     * Find minimum ship capacity to ship all packages within given days
     * 
     * @param weights Array of package weights
     * @param days Number of days available
     * @return Minimum ship capacity needed
     */
    fun solve(weights: IntArray, days: Int): Int {
        // Search space: [max weight, sum of all weights]
        // Must be able to carry heaviest package
        var low = weights.max()
        
        // Maximum capacity needed is sum (ship all in one day)
        var high = weights.sum()
        
        var answer = high
        
        // Binary search on capacity
        while (low <= high) {
            val mid = low + (high - low) / 2
            
            // Check if this capacity can ship all in given days
            if (canShip(weights, days, mid)) {
                // Capacity works, try smaller
                answer = mid
                high = mid - 1
            } else {
                // Capacity too small, need larger
                low = mid + 1
            }
        }
        
        return answer
    }
    
    /**
     * Check if packages can be shipped within given days with given capacity
     * Uses greedy approach: load as much as possible each day
     * 
     * @param weights Array of package weights
     * @param days Days available
     * @param capacity Ship capacity
     * @return true if can ship in time, false otherwise
     */
    private fun canShip(weights: IntArray, days: Int, capacity: Int): Boolean {
        var daysNeeded = 1
        var currentLoad = 0
        
        for (weight in weights) {
            // If adding this package exceeds capacity
            if (currentLoad + weight > capacity) {
                // Ship current load and start new day
                daysNeeded++
                currentLoad = weight
                
                // If we need more days than available, can't ship
                if (daysNeeded > days) {
                    return false
                }
            } else {
                // Add package to current day's load
                currentLoad += weight
            }
        }
        
        return true
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * INPUT: weights = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], days = 5
 * 
 * Step 1: Initialize
 * low = 10, high = 55
 * 
 * Binary Search:
 * 
 * Iteration 1: low=10, high=55
 * - mid = 32
 * - Can ship with capacity 32?
 *   Day 1: 1+2+3+4+5+6+7 = 28 ✓
 *   Day 2: 8+9+10 = 27 ✓
 *   Need 2 days <= 5 ✓
 * - answer = 32, high = 31
 * 
 * Iteration 2: low=10, high=31
 * - mid = 20
 * - Can ship with capacity 20?
 *   Day 1: 1+2+3+4+5 = 15 ✓
 *   Day 2: 6+7 = 13 ✓
 *   Day 3: 8+9 = 17 ✓
 *   Day 4: 10 = 10 ✓
 *   Need 4 days <= 5 ✓
 * - answer = 20, high = 19
 * 
 * Continue searching...
 * 
 * Final answer = 15
 * 
 * ============================================================================
 */

fun main() {
    val solver = CapacityToShipPackages()
    
    println("=== Testing Capacity To Ship Packages ===\n")
    
    // Test Case 1: Normal case
    val weights1 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val days1 = 5
    println("Test 1: weights = ${weights1.contentToString()}, days = $days1")
    println("Result: ${solver.solve(weights1, days1)}")
    println("Expected: 15\n")
    
    // Test Case 2: Ship in one day
    val weights2 = intArrayOf(3, 2, 2, 4, 1, 4)
    val days2 = 1
    println("Test 2: weights = ${weights2.contentToString()}, days = $days2")
    println("Result: ${solver.solve(weights2, days2)}")
    println("Expected: 16 (sum of all)\n")
    
    // Test Case 3: Each package on different day
    val weights3 = intArrayOf(3, 2, 2, 4, 1, 4)
    val days3 = 6
    println("Test 3: weights = ${weights3.contentToString()}, days = $days3")
    println("Result: ${solver.solve(weights3, days3)}")
    println("Expected: 4 (max weight)\n")
    
    // Test Case 4: 3 days
    val weights4 = intArrayOf(3, 2, 2, 4, 1, 4)
    val days4 = 3
    println("Test 4: weights = ${weights4.contentToString()}, days = $days4")
    println("Result: ${solver.solve(weights4, days4)}")
    println("Expected: 6\n")
    
    // Test Case 5: Single package
    val weights5 = intArrayOf(10)
    val days5 = 1
    println("Test 5: weights = ${weights5.contentToString()}, days = $days5")
    println("Result: ${solver.solve(weights5, days5)}")
    println("Expected: 10\n")
    
    // Test Case 6: All same weight
    val weights6 = intArrayOf(5, 5, 5, 5, 5)
    val days6 = 3
    println("Test 6: weights = ${weights6.contentToString()}, days = $days6")
    println("Result: ${solver.solve(weights6, days6)}")
    println("Expected: 10\n")
}
