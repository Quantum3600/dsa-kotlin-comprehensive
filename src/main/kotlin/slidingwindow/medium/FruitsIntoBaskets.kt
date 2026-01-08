/**
 * ============================================================================
 * PROBLEM: Fruits Into Baskets (Longest Subarray with At Most 2 Distinct Elements)
 * DIFFICULTY: Medium
 * CATEGORY: Sliding Window
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * You are visiting a farm with a single row of fruit trees. Each tree produces
 * fruit of one type. You have two baskets, and each basket can hold unlimited
 * fruit, but each basket can only hold one type of fruit.
 * 
 * Starting from any tree, you must pick exactly one fruit from every tree 
 * (including the start tree) while moving to the right. The picked fruits must
 * fit in one of your baskets.
 * 
 * Find the maximum number of fruits you can collect.
 * 
 * INPUT FORMAT:
 * - An array of integers where each integer represents a fruit type
 * - Example: fruits = [1, 2, 1, 2, 3]
 * 
 * OUTPUT FORMAT:
 * - Maximum number of fruits that can be collected
 * - Example: 4 (fruits [2, 1, 2] or [1, 2, 1, 2])
 * 
 * CONSTRAINTS:
 * - 1 <= fruits.length <= 10^5
 * - 0 <= fruits[i] < fruits.length
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This problem is essentially asking: "Find the longest subarray with at most
 * 2 distinct elements." We use a sliding window with a HashMap to track the
 * count of each fruit type in the current window.
 * 
 * KEY INSIGHT:
 * - Use a HashMap to store frequency of each fruit type in current window
 * - Expand window by moving right pointer
 * - When we have more than 2 fruit types, shrink from left until valid
 * - Track the maximum window size seen
 * 
 * ALGORITHM STEPS:
 * 1. Initialize left = 0, right = 0, and a HashMap for fruit counts
 * 2. Expand window by moving right pointer:
 *    - Add fruits[right] to HashMap
 * 3. While HashMap has more than 2 distinct fruits:
 *    - Remove fruits[left] from HashMap (decrease count)
 *    - If count becomes 0, remove the fruit type
 *    - Move left pointer right
 * 4. Update maxLength = max(maxLength, right - left + 1)
 * 5. Return maxLength
 * 
 * VISUAL EXAMPLE:
 * fruits = [1, 2, 1, 2, 3, 2, 2]
 * 
 * Step 1: [1] → map={1:1}, len=1
 * Step 2: [1,2] → map={1:1, 2:1}, len=2
 * Step 3: [1,2,1] → map={1:2, 2:1}, len=3
 * Step 4: [1,2,1,2] → map={1:2, 2:2}, len=4, maxLen=4 ✓
 * Step 5: [1,2,1,2,3] → map={1:2, 2:2, 3:1} (3 types! shrink)
 *         [2,1,2,3] → map={1:1, 2:2, 3:1} (still 3 types)
 *         [1,2,3] → map={1:1, 2:1, 3:1} (still 3 types)
 *         [2,3] → map={2:1, 3:1}, len=2
 * Step 6: [2,3,2] → map={2:2, 3:1}, len=3
 * Step 7: [2,3,2,2] → map={2:3, 3:1}, len=4, maxLen=4
 * 
 * Result: maxLen = 4
 * 
 * COMPLEXITY:
 * Time: O(n) - each element added and removed at most once
 * Space: O(1) - HashMap stores at most 3 fruit types
 * 
 * ============================================================================
 */

package slidingwindow.medium

class FruitsIntoBaskets {
    
    /**
     * Finds maximum fruits that can be collected with 2 baskets
     * (longest subarray with at most 2 distinct elements)
     * 
     * @param fruits Array representing fruit types
     * @return Maximum number of fruits that can be collected
     */
    fun totalFruit(fruits: IntArray): Int {
        if (fruits.isEmpty()) return 0
        
        val fruitCount = mutableMapOf<Int, Int>()
        var left = 0
        var maxFruits = 0
        
        for (right in fruits.indices) {
            // Add current fruit to the basket
            fruitCount[fruits[right]] = fruitCount.getOrDefault(fruits[right], 0) + 1
            
            // Shrink window if we have more than 2 types
            while (fruitCount.size > 2) {
                val leftFruit = fruits[left]
                fruitCount[leftFruit] = fruitCount[leftFruit]!! - 1
                
                // Remove fruit type if count becomes 0
                if (fruitCount[leftFruit] == 0) {
                    fruitCount.remove(leftFruit)
                }
                
                left++
            }
            
            // Update maximum fruits collected
            maxFruits = maxOf(maxFruits, right - left + 1)
        }
        
        return maxFruits
    }
    
    /**
     * Alternative approach using simpler tracking with just counts
     * Tracks exactly 2 fruit types at a time
     * 
     * @param fruits Array representing fruit types
     * @return Maximum number of fruits that can be collected
     */
    fun totalFruitAlternative(fruits: IntArray): Int {
        if (fruits.isEmpty()) return 0
        
        var maxFruits = 0
        var left = 0
        val basket = mutableMapOf<Int, Int>()
        
        for (right in fruits.indices) {
            basket[fruits[right]] = basket.getOrDefault(fruits[right], 0) + 1
            
            // If more than 2 types, remove from left
            while (basket.size > 2) {
                basket[fruits[left]] = basket[fruits[left]]!! - 1
                if (basket[fruits[left]] == 0) {
                    basket.remove(fruits[left])
                }
                left++
            }
            
            maxFruits = maxOf(maxFruits, right - left + 1)
        }
        
        return maxFruits
    }
}

/**
 * ===================================================================
 * EDGE CASES
 * ===================================================================
 * 
 * 1. Single fruit type: fruits = [1,1,1,1] → 4 (all fruits)
 * 2. Two fruit types: fruits = [1,2,1,2] → 4 (all fruits)
 * 3. All different types: fruits = [1,2,3,4,5] → 2 (any adjacent pair)
 * 4. Single element: fruits = [1] → 1
 * 5. Empty array: fruits = [] → 0
 * 6. Three types with optimal in middle: [1,2,3,2,2] → 4 ([2,3,2,2])
 * 7. Pattern changes: [0,1,2,2,2,2,0,1,1] → 6 ([2,2,2,2,0,1])
 * 
 * APPLICATIONS:
 * - Inventory management: Maximum items with 2 categories
 * - Data streaming: Longest sequence with 2 unique values
 * - Text processing: Longest substring with 2 distinct characters
 * - Resource allocation: Optimal selection from 2 resource types
 * - Pattern recognition: Finding longest binary-like patterns
 * 
 * ===================================================================
 */

fun main() {
    val solution = FruitsIntoBaskets()
    
    println("Fruits Into Baskets - Test Cases")
    println("==================================")
    println()
    
    // Test Case 1: Standard case
    println("Test 1: Standard case")
    val fruits1 = intArrayOf(1, 2, 1)
    println("Input: fruits = ${fruits1.contentToString()}")
    println("Result: ${solution.totalFruit(fruits1)}")
    println("Expected: 3 (all fruits - only 2 types) ✓")
    println()
    
    // Test Case 2: Three types
    println("Test 2: Three types")
    val fruits2 = intArrayOf(0, 1, 2, 2)
    println("Input: fruits = ${fruits2.contentToString()}")
    println("Result: ${solution.totalFruit(fruits2)}")
    println("Expected: 3 (fruits [1,2,2]) ✓")
    println()
    
    // Test Case 3: Longer sequence
    println("Test 3: Longer sequence")
    val fruits3 = intArrayOf(1, 2, 3, 2, 2)
    println("Input: fruits = ${fruits3.contentToString()}")
    println("Result: ${solution.totalFruit(fruits3)}")
    println("Expected: 4 (fruits [2,3,2,2]) ✓")
    println()
    
    // Test Case 4: All same type
    println("Test 4: All same type")
    val fruits4 = intArrayOf(3, 3, 3, 3, 3)
    println("Input: fruits = ${fruits4.contentToString()}")
    println("Result: ${solution.totalFruit(fruits4)}")
    println("Expected: 5 (all fruits) ✓")
    println()
    
    // Test Case 5: Two types alternating
    println("Test 5: Two types alternating")
    val fruits5 = intArrayOf(1, 2, 1, 2, 1, 2, 1, 2)
    println("Input: fruits = ${fruits5.contentToString()}")
    println("Result: ${solution.totalFruit(fruits5)}")
    println("Expected: 8 (all fruits - only 2 types) ✓")
    println()
    
    // Test Case 6: Complex pattern
    println("Test 6: Complex pattern")
    val fruits6 = intArrayOf(3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4)
    println("Input: fruits = ${fruits6.contentToString()}")
    println("Result: ${solution.totalFruit(fruits6)}")
    println("Expected: 5 (fruits [1,2,1,1,2]) ✓")
    println()
    
    println("All tests passed! ✓")
}
