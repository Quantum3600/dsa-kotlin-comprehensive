/**
 * ============================================================================
 * PROBLEM: Fractional Knapsack
 * DIFFICULTY: Easy
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given weights and values of n items, put these items in a knapsack of 
 * capacity W to get maximum total value. You can break items (take fractions).
 * 
 * INPUT FORMAT:
 * - weights: [10, 20, 30] (weight of each item)
 * - values: [60, 100, 120] (value of each item)
 * - capacity: 50 (knapsack capacity)
 * 
 * OUTPUT FORMAT:
 * - Double: maximum value achievable (240.0)
 * 
 * CONSTRAINTS:
 * - 1 <= n <= 10^5
 * - 1 <= weights[i], values[i] <= 10^4
 * - 1 <= capacity <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - prioritize items with highest value-to-weight ratio.
 * Take as much as possible of the most valuable items per unit weight.
 * 
 * ALGORITHM STEPS:
 * 1. Calculate value-to-weight ratio for each item
 * 2. Sort items by ratio in descending order
 * 3. Greedily pick items starting from highest ratio
 * 4. If item fits completely, take all of it
 * 5. If item doesn't fit, take fractional part
 * 
 * VISUAL EXAMPLE:
 * weights=[10,20,30], values=[60,100,120], capacity=50
 * 
 * Ratios: [60/10=6.0, 100/20=5.0, 120/30=4.0]
 * Sorted: [(10,60,6.0), (20,100,5.0), (30,120,4.0)]
 * 
 * Take item 0: weight=10, value=60, remaining=40
 * Take item 1: weight=20, value=100, remaining=20
 * Take item 2: weight=20/30, value=(20/30)*120=80, remaining=0
 * Total value: 60 + 100 + 80 = 240.0 ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy by ratio (used here): O(n log n) - OPTIMAL
 * 2. Dynamic Programming: Not needed (works for 0/1 knapsack)
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n log n)
 * - Creating items list: O(n)
 * - Sorting by ratio: O(n log n)
 * - Iterating through items: O(n)
 * - Total: O(n log n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Storing items with ratios: O(n)
 * 
 * ============================================================================
 */

package greedy.easy

class FractionalKnapsack {
    
    data class Item(val weight: Int, val value: Int, val ratio: Double)
    
    /**
     * Calculates maximum value for fractional knapsack
     */
    fun getMaxValue(weights: IntArray, values: IntArray, capacity: Int): Double {
        require(weights.size == values.size) { "Weights and values must have same length" }
        
        val n = weights.size
        val items = mutableListOf<Item>()
        
        // Create items with value-to-weight ratios
        for (i in 0 until n) {
            val ratio = values[i].toDouble() / weights[i]
            items.add(Item(weights[i], values[i], ratio))
        }
        
        // Sort by ratio in descending order
        items.sortByDescending { it.ratio }
        
        var remainingCapacity = capacity
        var totalValue = 0.0
        
        for (item in items) {
            if (remainingCapacity >= item.weight) {
                // Take entire item
                totalValue += item.value
                remainingCapacity -= item.weight
            } else {
                // Take fractional part
                val fraction = remainingCapacity.toDouble() / item.weight
                totalValue += item.value * fraction
                break // Knapsack is full
            }
        }
        
        return totalValue
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Capacity 0: weights=[10], values=[60], capacity=0 → 0.0
 * 2. Single item fits: weights=[10], values=[60], capacity=20 → 60.0
 * 3. Single item partial: weights=[10], values=[60], capacity=5 → 30.0
 * 4. All items fit: weights=[10,20], values=[60,100], capacity=100 → 160.0
 * 5. Same ratios: weights=[10,20], values=[20,40], capacity=15 → 30.0
 * 
 * APPLICATIONS:
 * - Resource allocation with divisibility
 * - Investment portfolio optimization
 * - Continuous resource problems
 * 
 * ============================================================================
 */

fun main() {
    val solution = FractionalKnapsack()
    
    println("Fractional Knapsack - Test Cases")
    println("==================================\n")
    
    println("Test 1: weights=[10,20,30], values=[60,100,120], capacity=50")
    println("Result: ${solution.getMaxValue(intArrayOf(10, 20, 30), intArrayOf(60, 100, 120), 50)}")
    println("Expected: 240.0 ✓\n")
    
    println("Test 2: weights=[10,40,20,30], values=[60,40,100,120], capacity=50")
    println("Result: ${solution.getMaxValue(intArrayOf(10, 40, 20, 30), intArrayOf(60, 40, 100, 120), 50)}")
    println("Expected: 240.0 ✓\n")
}
