/**
 * ============================================================================
 * PROBLEM: Maximum Product Subarray
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Dynamic Programming
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums, find a contiguous non-empty subarray within
 * the array that has the largest product, and return the product.
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [2, 3, -2, 4]
 * 
 * OUTPUT FORMAT:
 * - An integer representing the maximum product
 * - Example: 6 (subarray [2, 3])
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 2 * 10^4
 * - -10 <= nums[i] <= 10
 * - The product of any subarray fits in a 32-bit integer
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * Unlike maximum sum, we need to track both maximum AND minimum products
 * because a negative number can flip a large negative product into a
 * large positive product.
 * 
 * KEY INSIGHT:
 * - A negative number * minimum (most negative) = maximum (most positive)
 * - We need to maintain both max and min at each position
 * - When we encounter a negative number, swap max and min
 * 
 * ALGORITHM STEPS:
 * 1. Initialize maxProduct, minProduct to first element
 * 2. Initialize result to first element
 * 3. For each element from index 1:
 *    - If negative, swap maxProduct and minProduct
 *    - Update maxProduct = max(num, maxProduct * num)
 *    - Update minProduct = min(num, minProduct * num)
 *    - Update result = max(result, maxProduct)
 * 
 * VISUAL EXAMPLE:
 * nums = [2, 3, -2, 4]
 * 
 * i=0: max=2, min=2, result=2
 * i=1: max=6, min=3, result=6  (2*3=6)
 * i=2: swap, max=max(-2, 3*-2)=-2, min=min(-2, 6*-2)=-12, result=6
 * i=3: max=4, min=-48, result=6
 * 
 * Result: 6
 * 
 * COMPLEXITY:
 * Time: O(n) - single pass through array
 * Space: O(1) - only three variables
 * 
 * ============================================================================
 */

package arrays.hard

import kotlin.math.max
import kotlin.math.min

class MaxProductSubarray {
    
    /**
     * Finds maximum product of contiguous subarray
     * 
     * @param nums The input array
     * @return Maximum product
     */
    fun maxProduct(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        
        var maxProduct = nums[0]
        var minProduct = nums[0]
        var result = nums[0]
        
        for (i in 1 until nums.size) {
            val num = nums[i]
            
            // When multiplying by negative number, max becomes min and vice versa
            if (num < 0) {
                val temp = maxProduct
                maxProduct = minProduct
                minProduct = temp
            }
            
            // Either continue current product or start fresh from current element
            maxProduct = max(num, maxProduct * num)
            minProduct = min(num, minProduct * num)
            
            // Update global maximum
            result = max(result, maxProduct)
        }
        
        return result
    }
    
    /**
     * Alternative approach without swap
     */
    fun maxProductAlternative(nums: IntArray): Int {
        if (nums.isEmpty()) return 0
        
        var result = nums[0]
        var maxProduct = nums[0]
        var minProduct = nums[0]
        
        for (i in 1 until nums.size) {
            val num = nums[i]
            
            // Calculate all possibilities
            val tempMax = max(num, max(maxProduct * num, minProduct * num))
            minProduct = min(num, min(maxProduct * num, minProduct * num))
            maxProduct = tempMax
            
            result = max(result, maxProduct)
        }
        
        return result
    }
    
    /**
     * Brute force approach - check all subarrays
     * Time: O(n²), Space: O(1)
     */
    fun maxProductBruteForce(nums: IntArray): Int {
        var result = nums[0]
        
        for (i in nums.indices) {
            var product = 1
            for (j in i until nums.size) {
                product *= nums[j]
                result = max(result, product)
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EDGE CASES
 * ============================================================================
 * 
 * 1. Single element: nums = [5] → 5
 * 2. All positive: nums = [2, 3, 4] → 24
 * 3. All negative (even count): nums = [-2, -3] → 6
 * 4. All negative (odd count): nums = [-2, -3, -4] → 12 ([-2,-3])
 * 5. Contains zero: nums = [2, 3, 0, 4, 5] → 20 ([4,5])
 * 6. Multiple zeros: nums = [0, 2, 0] → 2
 * 7. Mix with zeros: nums = [-2, 0, -1] → 0
 * 
 * APPLICATIONS:
 * 1. Stock price analysis (compound returns)
 * 2. Game score multipliers
 * 3. Investment portfolio optimization
 * 4. Manufacturing yield calculation
 * 5. Signal processing (amplitude products)
 * 
 * ============================================================================
 */

fun main() {
    val solution = MaxProductSubarray()
    
    println("=== Maximum Product Subarray Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(2, 3, -2, 4)
    println("Input: ${nums1.contentToString()}")
    println("Result: ${solution.maxProduct(nums1)}")
    println("Expected: 6\n")
    
    // Test 2: All negative (even)
    println("Test 2: All negative (even)")
    val nums2 = intArrayOf(-2, -3)
    println("Input: ${nums2.contentToString()}")
    println("Result: ${solution.maxProduct(nums2)}")
    println("Expected: 6\n")
    
    // Test 3: Contains zero
    println("Test 3: Contains zero")
    val nums3 = intArrayOf(2, 3, 0, 4, 5)
    println("Input: ${nums3.contentToString()}")
    println("Result: ${solution.maxProduct(nums3)}")
    println("Expected: 20\n")
    
    // Test 4: Single element
    println("Test 4: Single element")
    val nums4 = intArrayOf(-2)
    println("Input: ${nums4.contentToString()}")
    println("Result: ${solution.maxProduct(nums4)}")
    println("Expected: -2\n")
    
    // Test 5: Mix of positive and negative
    println("Test 5: Mix of positive and negative")
    val nums5 = intArrayOf(-2, 3, -4)
    println("Input: ${nums5.contentToString()}")
    println("Result: ${solution.maxProduct(nums5)}")
    println("Expected: 24\n")
    
    println("All tests completed!")
}
