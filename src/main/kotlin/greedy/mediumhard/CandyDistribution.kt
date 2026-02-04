/**
 * ============================================================================
 * PROBLEM: Candy Distribution
 * DIFFICULTY: Hard
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * There are n children standing in a line. Each child has a rating.
 * You need to give candies to children following these requirements:
 * 1. Each child must have at least one candy
 * 2. Children with higher rating than neighbors get more candies
 * Return minimum number of candies needed.
 * 
 * INPUT FORMAT:
 * - ratings: [1, 0, 2] (rating of each child)
 * 
 * OUTPUT FORMAT:
 * - Integer: minimum candies needed (5)
 * 
 * CONSTRAINTS:
 * - 1 <= ratings.length <= 2 * 10^4
 * - 0 <= ratings[i] <= 2 * 10^4
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Two-pass greedy approach:
 * - Left-to-right: ensure right neighbor with higher rating gets more
 * - Right-to-left: ensure left neighbor with higher rating gets more
 * - Take maximum of both passes
 * 
 * KEY INSIGHT:
 * Can't solve in one pass because we need to satisfy both directions.
 * Each pass handles one direction constraint.
 * 
 * ALGORITHM STEPS:
 * 1. Initialize candies array with 1 for each child
 * 2. Left-to-right: if ratings[i] > ratings[i-1], candies[i] = candies[i-1] + 1
 * 3. Right-to-left: if ratings[i] > ratings[i+1], candies[i] = max(candies[i], candies[i+1] + 1)
 * 4. Sum all candies
 * 
 * VISUAL EXAMPLE:
 * ratings = [1, 0, 2]
 * 
 * Initial: candies = [1, 1, 1]
 * 
 * Left-to-right:
 * i=1: ratings[1]=0 < ratings[0]=1 → no change, candies=[1,1,1]
 * i=2: ratings[2]=2 > ratings[1]=0 → candies[2]=2, candies=[1,1,2]
 * 
 * Right-to-left:
 * i=1: ratings[1]=0 < ratings[2]=2 → no change, candies=[1,1,2]
 * i=0: ratings[0]=1 > ratings[1]=0 → candies[0]=max(1,2)=2, candies=[2,1,2]
 * 
 * Total: 2+1+2 = 5 ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Two-pass (used here): O(n) time, O(n) space - OPTIMAL
 * 2. Peak-valley counting: O(n) time, O(1) space - Complex
 * 3. Greedy with sorting: O(n log n) - Not optimal
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - First pass (left-to-right): O(n)
 * - Second pass (right-to-left): O(n)
 * - Final sum: O(n)
 * - Total: O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Candies array: O(n)
 * - Can be optimized to O(1) with peak-valley approach
 * 
 * ============================================================================
 */

package greedy.mediumhard

class CandyDistribution {
    
    /**
     * Calculates minimum candies needed
     */
    fun candy(ratings: IntArray): Int {
        val n = ratings.size
        if (n == 0) return 0
        if (n == 1) return 1
        
        val candies = IntArray(n) { 1 }
        
        // Left-to-right pass
        for (i in 1 until n) {
            if (ratings[i] > ratings[i - 1]) {
                candies[i] = candies[i - 1] + 1
            }
        }
        
        // Right-to-left pass
        for (i in n - 2 downTo 0) {
            if (ratings[i] > ratings[i + 1]) {
                candies[i] = maxOf(candies[i], candies[i + 1] + 1)
            }
        }
        
        return candies.sum()
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single child: [5] → 1
 * 2. All same ratings: [1,1,1] → 3
 * 3. Increasing: [1,2,3] → 6 (1+2+3)
 * 4. Decreasing: [3,2,1] → 6 (3+2+1)
 * 5. Valley: [2,1,2] → 4 (2+1+2)
 * 6. Peak: [1,2,1] → 4 (1+2+1)
 * 
 * APPLICATIONS:
 * - Fair resource distribution
 * - Reward allocation based on performance
 * - Salary distribution problems
 * 
 * ============================================================================
 */

fun main() {
    val solution = CandyDistribution()
    
    println("Candy Distribution - Test Cases")
    println("=================================\n")
    
    println("Test 1: [1,0,2]")
    println("Result: ${solution.candy(intArrayOf(1, 0, 2))}")
    println("Expected: 5 ✓\n")
    
    println("Test 2: [1,2,2]")
    println("Result: ${solution.candy(intArrayOf(1, 2, 2))}")
    println("Expected: 4 ✓\n")
    
    println("Test 3: [1,3,2,2,1]")
    println("Result: ${solution.candy(intArrayOf(1, 3, 2, 2, 1))}")
    println("Expected: 7 ✓\n")
}
