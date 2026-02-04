/**
 * ============================================================================
 * PROBLEM: Minimum Number of Coins
 * DIFFICULTY: Easy
 * CATEGORY: Greedy
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an infinite supply of Indian currency denominations, find the minimum
 * number of coins needed to make a given amount.
 * Denominations: [1, 2, 5, 10, 20, 50, 100, 200, 500, 2000]
 * 
 * INPUT FORMAT:
 * - amount: 121 (target amount)
 * 
 * OUTPUT FORMAT:
 * - List<Int>: coins used [100, 20, 1]
 * 
 * CONSTRAINTS:
 * - 1 <= amount <= 10^9
 * - Standard Indian currency denominations
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Greedy approach - always pick the largest denomination that doesn't exceed
 * remaining amount. This works for canonical coin systems like Indian currency.
 * 
 * KEY INSIGHT:
 * Indian denominations form a canonical system where greedy approach gives
 * optimal solution. Start from largest and work down to smallest.
 * 
 * ALGORITHM STEPS:
 * 1. Sort denominations in descending order
 * 2. For each denomination:
 *    - Take as many coins of this denomination as possible
 *    - Reduce amount by value taken
 * 3. Continue until amount becomes 0
 * 4. Return list of coins used
 * 
 * VISUAL EXAMPLE:
 * amount = 121
 * denominations = [2000, 500, 200, 100, 50, 20, 10, 5, 2, 1]
 * 
 * Try 2000: 121 < 2000, skip
 * Try 500: 121 < 500, skip
 * Try 200: 121 < 200, skip
 * Try 100: 121 >= 100, take 1 coin → remaining=21, coins=[100]
 * Try 50: 21 < 50, skip
 * Try 20: 21 >= 20, take 1 coin → remaining=1, coins=[100,20]
 * Try 10: 1 < 10, skip
 * Try 5: 1 < 5, skip
 * Try 2: 1 < 2, skip
 * Try 1: 1 >= 1, take 1 coin → remaining=0, coins=[100,20,1]
 * 
 * Result: [100, 20, 1] (3 coins) ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Greedy (used here): O(n) time - OPTIMAL for canonical systems
 * 2. Dynamic Programming: O(amount * n) - Needed for non-canonical systems
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n)
 * - n is number of denominations (constant = 10 for Indian currency)
 * - For each denomination, we do constant work
 * - Total: O(10) = O(1) for fixed denominations
 * - General case: O(n) where n is number of denominations
 * 
 * SPACE COMPLEXITY: O(k)
 * - k is number of coins needed in result
 * - In worst case (all 1s), k = amount
 * - Typically much smaller
 * 
 * ============================================================================
 */

package greedy.easy

class MinimumCoins {
    
    /**
     * Finds minimum coins needed using greedy approach
     */
    fun minCoins(amount: Int): List<Int> {
        require(amount > 0) { "Amount must be positive" }
        
        val denominations = intArrayOf(2000, 500, 200, 100, 50, 20, 10, 5, 2, 1)
        val result = mutableListOf<Int>()
        var remaining = amount
        
        for (coin in denominations) {
            while (remaining >= coin) {
                result.add(coin)
                remaining -= coin
            }
            
            if (remaining == 0) break
        }
        
        return result
    }
    
    /**
     * Returns count of minimum coins needed
     */
    fun minCoinsCount(amount: Int): Int {
        require(amount > 0) { "Amount must be positive" }
        
        val denominations = intArrayOf(2000, 500, 200, 100, 50, 20, 10, 5, 2, 1)
        var count = 0
        var remaining = amount
        
        for (coin in denominations) {
            count += remaining / coin
            remaining %= coin
            
            if (remaining == 0) break
        }
        
        return count
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Amount is 1: 1 → [1]
 * 2. Exact denomination: 100 → [100]
 * 3. Multiple same coins: 15 → [10,5]
 * 4. Large amount: 5555 → [2000,2000,1000,500,50,5]
 * 5. All small coins: 4 → [2,2]
 * 
 * APPLICATIONS:
 * - ATM cash dispensing
 * - Vending machines
 * - Currency exchange
 * - Payment systems
 * 
 * NOTE:
 * This greedy approach works for canonical coin systems (like Indian, US currency)
 * but may not give optimal solution for arbitrary denominations.
 * Example: denominations=[1,3,4], amount=6 → greedy gives [4,1,1] (3 coins)
 * but optimal is [3,3] (2 coins). Use DP for non-canonical systems.
 * 
 * ============================================================================
 */

fun main() {
    val solution = MinimumCoins()
    
    println("Minimum Coins - Test Cases")
    println("============================\n")
    
    println("Test 1: amount=121")
    println("Result: ${solution.minCoins(121)}")
    println("Count: ${solution.minCoinsCount(121)}")
    println("Expected: [100, 20, 1], count=3 ✓\n")
    
    println("Test 2: amount=43")
    println("Result: ${solution.minCoins(43)}")
    println("Count: ${solution.minCoinsCount(43)}")
    println("Expected: [20, 20, 2, 1], count=4 ✓\n")
    
    println("Test 3: amount=1000")
    println("Result: ${solution.minCoins(1000)}")
    println("Count: ${solution.minCoinsCount(1000)}")
    println("Expected: [500, 500], count=2 ✓\n")
}
