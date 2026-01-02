/**
 * ============================================================================
 * PROBLEM: Four Sum
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Two Pointers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array nums of n integers, return an array of all unique quadruplets
 * [nums[a], nums[b], nums[c], nums[d]] such that:
 * - 0 <= a, b, c, d < n
 * - a, b, c, and d are distinct
 * - nums[a] + nums[b] + nums[c] + nums[d] == target
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [1, 0, -1, 0, -2, 2]
 * - A target sum: target = 0
 * 
 * OUTPUT FORMAT:
 * - A list of lists containing unique quadruplets
 * - Example: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 200
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * This is an extension of the 3Sum problem. We fix two elements and then
 * use the two-pointer technique on the remaining array to find pairs that
 * complete the quadruplet.
 * 
 * KEY INSIGHT:
 * Fix first element nums[i], then fix second element nums[j].
 * Now we need nums[k] + nums[l] = target - nums[i] - nums[j]
 * This becomes a 2Sum problem on the remaining sorted array.
 * 
 * ALGORITHM STEPS:
 * 1. Sort the array to enable two-pointer technique
 * 2. Use nested loops to fix first two elements (i, j)
 * 3. Skip duplicates for first and second elements
 * 4. Use two pointers (k, l) on remaining array
 * 5. Calculate sum of all four elements
 * 6. Adjust pointers based on comparison with target
 * 7. Skip duplicates for third and fourth elements
 * 
 * VISUAL EXAMPLE:
 * nums = [1, 0, -1, 0, -2, 2], target = 0
 * 
 * Step 1: Sort → [-2, -1, 0, 0, 1, 2]
 * 
 * Step 2: Fix i=0 (nums[i]=-2), j=1 (nums[j]=-1)
 *   Need: k + l = 0 - (-2) - (-1) = 3
 *   [-2, -1, 0, 0, 1, 2]
 *     i   j  k        l
 *   sum = 0 + 2 = 2 < 3, move k
 *   sum = 1 + 2 = 3 = target ✓
 *   Found: [-2, -1, 1, 2]
 * 
 * COMPLEXITY:
 * Time: O(n³) - two nested loops + two-pointer scan
 * Space: O(1) - excluding output
 * 
 * ============================================================================
 */

package arrays.hard

class FourSum {
    
    /**
     * Finds all unique quadruplets that sum to target
     * 
     * @param nums The input array of integers
     * @param target The target sum
     * @return List of quadruplets that sum to target
     */
    fun fourSum(nums: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        
        // Edge case: need at least 4 elements
        if (nums.size < 4) return result
        
        // Sort array to enable two-pointer technique
        nums.sort()
        
        // Fix first element
        for (i in 0 until nums.size - 3) {
            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) continue
            
            // Fix second element
            for (j in i + 1 until nums.size - 2) {
                // Skip duplicate second elements
                if (j > i + 1 && nums[j] == nums[j - 1]) continue
                
                // Two-pointer approach for remaining array
                var k = j + 1
                var l = nums.size - 1
                
                while (k < l) {
                    // Use long to prevent overflow
                    val sum = nums[i].toLong() + nums[j].toLong() + 
                              nums[k].toLong() + nums[l].toLong()
                    
                    when {
                        sum == target.toLong() -> {
                            result.add(listOf(nums[i], nums[j], nums[k], nums[l]))
                            
                            // Skip duplicate third elements
                            while (k < l && nums[k] == nums[k + 1]) k++
                            
                            // Skip duplicate fourth elements
                            while (k < l && nums[l] == nums[l - 1]) l--
                            
                            // Move both pointers
                            k++
                            l--
                        }
                        sum < target.toLong() -> k++
                        else -> l--
                    }
                }
            }
        }
        
        return result
    }
    
    /**
     * Alternative approach with early termination optimizations
     */
    fun fourSumOptimized(nums: IntArray, target: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        if (nums.size < 4) return result
        
        nums.sort()
        
        for (i in 0 until nums.size - 3) {
            if (i > 0 && nums[i] == nums[i - 1]) continue
            
            // Early termination: smallest possible sum too large
            if (nums[i].toLong() + nums[i + 1].toLong() + 
                nums[i + 2].toLong() + nums[i + 3].toLong() > target.toLong()) {
                break
            }
            
            // Skip if largest possible sum too small
            if (nums[i].toLong() + nums[nums.size - 3].toLong() + 
                nums[nums.size - 2].toLong() + nums[nums.size - 1].toLong() < target.toLong()) {
                continue
            }
            
            for (j in i + 1 until nums.size - 2) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue
                
                // Similar optimizations for j
                if (nums[i].toLong() + nums[j].toLong() + 
                    nums[j + 1].toLong() + nums[j + 2].toLong() > target.toLong()) {
                    break
                }
                
                if (nums[i].toLong() + nums[j].toLong() + 
                    nums[nums.size - 2].toLong() + nums[nums.size - 1].toLong() < target.toLong()) {
                    continue
                }
                
                var k = j + 1
                var l = nums.size - 1
                
                while (k < l) {
                    val sum = nums[i].toLong() + nums[j].toLong() + 
                              nums[k].toLong() + nums[l].toLong()
                    
                    when {
                        sum == target.toLong() -> {
                            result.add(listOf(nums[i], nums[j], nums[k], nums[l]))
                            while (k < l && nums[k] == nums[k + 1]) k++
                            while (k < l && nums[l] == nums[l - 1]) l--
                            k++
                            l--
                        }
                        sum < target.toLong() -> k++
                        else -> l--
                    }
                }
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
 * 1. Less than 4 elements: nums = [1, 2, 3]
 *    - Result: []
 * 
 * 2. All same, match target: nums = [0,0,0,0], target = 0
 *    - Result: [[0,0,0,0]]
 * 
 * 3. No solution: nums = [1,2,3,4], target = 100
 *    - Result: []
 * 
 * 4. Negative target: nums = [-5,-4,-3,-2], target = -14
 *    - Result: [[-5,-4,-3,-2]]
 * 
 * 5. Overflow prevention: Large numbers using Long
 * 
 * 6. Multiple solutions with duplicates handled
 * 
 * 7. Mix of positive and negative numbers
 * 
 * APPLICATIONS:
 * 1. Portfolio optimization with 4 assets
 * 2. Chemical equation balancing
 * 3. Resource allocation problems
 * 4. Combination problems in operations research
 * 5. Game theory - finding balanced strategies
 * 
 * ============================================================================
 */

fun main() {
    val solution = FourSum()
    
    println("=== Four Sum Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(1, 0, -1, 0, -2, 2)
    println("Input: ${nums1.contentToString()}, target = 0")
    println("Result: ${solution.fourSum(nums1, 0)}")
    println("Expected: [[-2, -1, 1, 2], [-2, 0, 0, 2], [-1, 0, 0, 1]]\n")
    
    // Test 2: All zeros
    println("Test 2: All zeros")
    val nums2 = intArrayOf(0, 0, 0, 0)
    println("Input: ${nums2.contentToString()}, target = 0")
    println("Result: ${solution.fourSum(nums2, 0)}")
    println("Expected: [[0, 0, 0, 0]]\n")
    
    // Test 3: No solution
    println("Test 3: No solution")
    val nums3 = intArrayOf(1, 2, 3, 4)
    println("Input: ${nums3.contentToString()}, target = 100")
    println("Result: ${solution.fourSum(nums3, 100)}")
    println("Expected: []\n")
    
    // Test 4: Negative target
    println("Test 4: Negative target")
    val nums4 = intArrayOf(-5, -4, -3, -2, -1)
    println("Input: ${nums4.contentToString()}, target = -14")
    println("Result: ${solution.fourSum(nums4, -14)}")
    println("Expected: [[-5, -4, -3, -2]]\n")
    
    // Test 5: Multiple solutions
    println("Test 5: Multiple solutions")
    val nums5 = intArrayOf(2, 2, 2, 2, 2)
    println("Input: ${nums5.contentToString()}, target = 8")
    println("Result: ${solution.fourSum(nums5, 8)}")
    println("Expected: [[2, 2, 2, 2]]\n")
    
    println("All tests completed!")
}
