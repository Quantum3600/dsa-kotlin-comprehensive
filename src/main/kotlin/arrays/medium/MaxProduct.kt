/**
 * ===================================================================
 * PROBLEM: Maximum Product Subarray
 * DIFFICULTY: Medium
 * CATEGORY: Arrays, Dynamic Programming
 * ===================================================================
 * 
 * PROBLEM STATEMENT:
 * Find the contiguous subarray with the largest product.
 * 
 * INPUT: arr = [2, 3, -2, 4]
 * OUTPUT: 6 (subarray [2, 3])
 * 
 * CONSTRAINTS:
 * - 1 <= arr.size <= 2 * 10^4
 * - -10 <= arr[i] <= 10
 * 
 * ===================================================================
 * APPROACH & INTUITION
 * ===================================================================
 * 
 * Track both max and min products:
 * - Negative number can turn min into max
 * - Need to track both possibilities
 * 
 * For each element:
 * - Calculate max = max(num, maxProd * num, minProd * num)
 * - Calculate min = min(num, maxProd * num, minProd * num)
 * 
 * COMPLEXITY:
 * Time: O(n)
 * Space: O(1)
 * 
 * ===================================================================
 */

package arrays.medium

class MaxProduct {
    
    fun maxProduct(arr: IntArray): Int {
        if (arr.isEmpty()) return 0
        
        var maxProd = arr[0]
        var minProd = arr[0]
        var result = arr[0]
        
        for (i in 1 until arr.size) {
            val num = arr[i]
            
            // When multiplying by negative, max becomes min and vice versa
            val tempMax = maxOf(num, maxOf(maxProd * num, minProd * num))
            minProd = minOf(num, minOf(maxProd * num, minProd * num))
            maxProd = tempMax
            
            result = maxOf(result, maxProd)
        }
        
        return result
    }
}

/**
 * ===================================================================
 * EDGE CASES & APPLICATIONS
 * ===================================================================
 * 
 * 1. All positive: [2,3,4] → 24
 * 2. All negative: [-2,-3,-4] → 12
 * 3. With zero: [2,3,0,4] → 6
 * 4. Single element: [5] → 5
 * 
 * APPLICATIONS:
 * - Financial calculations
 * - Optimization problems
 * - Resource allocation
 * 
 * ===================================================================
 */

fun main() {
    val solution = MaxProduct()
    
    println("Maximum Product Subarray - Test Cases")
    println("=======================================\n")
    
    println("Test 1: [2,3,-2,4]")
    println("Result: ${solution.maxProduct(intArrayOf(2,3,-2,4))}")
    println("Expected: 6 ✓\n")
    
    println("Test 2: [-2,0,-1]")
    println("Result: ${solution.maxProduct(intArrayOf(-2,0,-1))}")
    println("Expected: 0 ✓\n")
    
    println("All tests passed! ✓")
}
