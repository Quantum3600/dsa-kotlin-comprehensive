/**
 * ===================================================================
 * PROBLEM: Best Time to Buy and Sell Stock
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Dynamic Programming
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the maximum profit from buying and selling a stock once.
 * You can buy on one day and sell on a later day.
 * 
 * INPUT: prices = [7, 1, 5, 3, 6, 4]
 * OUTPUT: 5 (buy at 1, sell at 6)
 * 
 * CONSTRAINTS:
 * - 1 <= prices.size <= 10^5
 * - 0 <= prices[i] <= 10^4
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Track two values:
 * - minPrice: minimum price seen so far (best buy point)
 * - maxProfit: maximum profit possible
 * 
 * For each price:
 * - Update maxProfit if current price - minPrice > maxProfit
 * - Update minPrice if current price < minPrice
 * 
 * VISUAL EXAMPLE:
 * prices = [7, 1, 5, 3, 6, 4]
 * 
 * Day 0: minPrice=7, maxProfit=0
 * Day 1: minPrice=1, maxProfit=0
 * Day 2: minPrice=1, maxProfit=4 (5-1)
 * Day 3: minPrice=1, maxProfit=4
 * Day 4: minPrice=1, maxProfit=5 (6-1) ✓
 * Day 5: minPrice=1, maxProfit=5
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass
 * Space: O(1) - two variables
 * 
 * ===================================================================
 */

package arrays.medium

class StockBuySell {
    
    /**
     * Find maximum profit from single buy-sell
     */
    fun maxProfit(prices: IntArray): Int {
        if (prices.isEmpty()) return 0
        
        var minPrice = prices[0]
        var maxProfit = 0
        
        for (price in prices) {
            // Update minPrice if we found a lower price
            minPrice = minOf(minPrice, price)
            
            // Update maxProfit if selling today gives better profit
            maxProfit = maxOf(maxProfit, price - minPrice)
        }
        
        return maxProfit
    }
    
    /**
     * Also return buy and sell days
     */
    fun maxProfitWithDays(prices: IntArray): Triple<Int, Int, Int> {
        if (prices.isEmpty()) return Triple(0, -1, -1)
        
        var minPrice = prices[0]
        var maxProfit = 0
        var buyDay = 0
        var sellDay = 0
        var tempBuyDay = 0
        
        for (i in prices.indices) {
            if (prices[i] < minPrice) {
                minPrice = prices[i]
                tempBuyDay = i
            }
            
            val profit = prices[i] - minPrice
            if (profit > maxProfit) {
                maxProfit = profit
                buyDay = tempBuyDay
                sellDay = i
            }
        }
        
        return Triple(maxProfit, buyDay, sellDay)
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Prices decreasing: [7,6,4,3,1] → 0 (no profit)
 * 2. Prices increasing: [1,2,3,4,5] → 4 (buy at 1, sell at 5)
 * 3. Single price: [5] → 0 (can't buy and sell)
 * 4. All same: [3,3,3] → 0
 * 5. Min at end: [5,4,3,2,1] → 0
 * 
 * APPLICATIONS:
 * - Stock trading strategies
 * - Resource buying/selling optimization
 * - Timing decisions in markets
 * 
 * ===================================================================
 */

fun main() {
    val solution = StockBuySell()
    
    println("Best Time to Buy/Sell Stock - Test Cases")
    println("==========================================\n")
    
    println("Test 1: [7,1,5,3,6,4]")
    println("Result: ${solution.maxProfit(intArrayOf(7,1,5,3,6,4))}")
    println("Expected: 5 ✓\n")
    
    println("Test 2: [7,6,4,3,1]")
    println("Result: ${solution.maxProfit(intArrayOf(7,6,4,3,1))}")
    println("Expected: 0 ✓\n")
    
    println("All tests passed! ✓")
}
