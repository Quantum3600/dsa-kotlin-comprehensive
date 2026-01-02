/**
 * ============================================================================
 * PROBLEM: Three Sum
 * DIFFICULTY: Hard
 * CATEGORY: Arrays, Two Pointers
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
 * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
 * 
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [-1, 0, 1, 2, -1, -4]
 * 
 * OUTPUT FORMAT:
 * - A list of lists containing unique triplets that sum to zero
 * - Example: [[-1, -1, 2], [-1, 0, 1]]
 * 
 * CONSTRAINTS:
 * - 3 <= nums.length <= 3000
 * - -10^5 <= nums[i] <= 10^5
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The key insight is to convert this into a two-sum problem. For each element,
 * we find pairs of elements that sum to the negative of that element.
 * 
 * By sorting the array first, we can:
 * 1. Use two pointers to efficiently find pairs
 * 2. Skip duplicates easily
 * 3. Apply early termination optimizations
 * 
 * KEY INSIGHT:
 * If we fix one element nums[i], we need to find nums[j] + nums[k] = -nums[i]
 * This becomes a two-sum problem on the remaining sorted array.
 * 
 * ALGORITHM STEPS:
 * 1. Sort the array in ascending order
 * 2. Iterate through array (fix first element)
 * 3. Skip duplicate first elements to avoid duplicate triplets
 * 4. Use two pointers (left and right) on remaining array
 * 5. If sum == 0, add triplet and move both pointers
 * 6. If sum < 0, move left pointer right (need larger sum)
 * 7. If sum > 0, move right pointer left (need smaller sum)
 * 8. Skip duplicates for second and third elements
 * 
 * VISUAL EXAMPLE:
 * nums = [-1, 0, 1, 2, -1, -4]
 * 
 * Step 1: Sort → [-4, -1, -1, 0, 1, 2]
 * 
 * Step 2: Fix i=0 (nums[i]=-4), target=4
 *   [-4, -1, -1, 0, 1, 2]
 *     i   L           R
 *   sum = -1 + 2 = 1 < 4, move L
 *   No solution for i=0
 * 
 * Step 3: Fix i=1 (nums[i]=-1), need pairs summing to 1
 *   [-4, -1, -1, 0, 1, 2]
 *         i   L        R
 *   sum = -1 + 2 = 1 (total: -1 + -1 + 2 = 0) ✓
 *   Found: [-1, -1, 2]
 * 
 * Step 4: Continue with same i, move pointers
 *   [-4, -1, -1, 0, 1, 2]
 *         i      L  R
 *   sum = 0 + 1 = 1 (total: -1 + 0 + 1 = 0) ✓
 *   Found: [-1, 0, 1]
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n³) - Try all triplets
 * 2. Hash Set: O(n²) time, O(n) space - Use set for two-sum
 * 3. Two Pointers (used here): O(n²) time, O(1) space - OPTIMAL
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(n²)
 * - Sorting: O(n log n)
 * - Outer loop: O(n)
 * - Inner two-pointer scan: O(n) for each iteration
 * - Total: O(n log n) + O(n²) = O(n²)
 * 
 * SPACE COMPLEXITY: O(1) or O(n)
 * - O(1) if we don't count output space
 * - O(n) for sorting (depending on sort implementation)
 * - No additional data structures used
 * 
 * WHY THIS IS OPTIMAL:
 * We must examine at least O(n²) pairs to find all triplets.
 * The two-pointer technique allows us to do this efficiently
 * without using extra space for a hash set.
 * 
 * ============================================================================
 */

package arrays.hard

class ThreeSum {
    
    /**
     * Finds all unique triplets that sum to zero using two-pointer approach
     * 
     * @param nums The input array of integers
     * @return List of triplets that sum to zero
     */
    fun threeSum(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        
        // Edge case: need at least 3 elements
        if (nums.size < 3) return result
        
        // Sort the array to enable two-pointer technique and skip duplicates
        nums.sort()
        
        // Iterate through array, fixing first element
        for (i in 0 until nums.size - 2) {
            // Skip positive numbers as first element
            // If first element is positive, no way to get sum = 0
            if (nums[i] > 0) break
            
            // Skip duplicate first elements
            if (i > 0 && nums[i] == nums[i - 1]) continue
            
            // Two-pointer approach for remaining array
            var left = i + 1
            var right = nums.size - 1
            
            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                
                when {
                    sum == 0 -> {
                        // Found a triplet
                        result.add(listOf(nums[i], nums[left], nums[right]))
                        
                        // Skip duplicate second elements
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++
                        }
                        
                        // Skip duplicate third elements
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--
                        }
                        
                        // Move both pointers
                        left++
                        right--
                    }
                    sum < 0 -> {
                        // Sum too small, need larger value
                        left++
                    }
                    else -> {
                        // Sum too large, need smaller value
                        right--
                    }
                }
            }
        }
        
        return result
    }
    
    /**
     * Brute force approach - tries all possible triplets
     * Time: O(n³), Space: O(n) for set
     */
    fun threeSumBruteForce(nums: IntArray): List<List<Int>> {
        val result = mutableSetOf<List<Int>>()
        
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                for (k in j + 1 until nums.size) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        val triplet = listOf(nums[i], nums[j], nums[k]).sorted()
                        result.add(triplet)
                    }
                }
            }
        }
        
        return result.toList()
    }
    
    /**
     * Hash set approach for two-sum part
     * Time: O(n²), Space: O(n)
     */
    fun threeSumHashSet(nums: IntArray): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        nums.sort()
        
        for (i in 0 until nums.size - 2) {
            if (nums[i] > 0) break
            if (i > 0 && nums[i] == nums[i - 1]) continue
            
            val seen = mutableSetOf<Int>()
            var j = i + 1
            while (j < nums.size) {
                val complement = -nums[i] - nums[j]
                
                if (complement in seen) {
                    result.add(listOf(nums[i], complement, nums[j]))
                    // Skip duplicates
                    while (j + 1 < nums.size && nums[j] == nums[j + 1]) {
                        j++
                    }
                }
                seen.add(nums[j])
                j++
            }
        }
        
        return result
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * EXAMPLE 1: Standard case
 * INPUT: nums = [-1, 0, 1, 2, -1, -4]
 * 
 * Step 1: Sort
 * nums = [-4, -1, -1, 0, 1, 2]
 * 
 * Step 2: i=0, nums[i]=-4, target=4
 * Two pointers: L=1, R=5
 * sum = -1 + 2 = 1 < 4 → move L
 * sum = -1 + 2 = 1 < 4 → move L
 * sum = 0 + 2 = 2 < 4 → move L
 * sum = 1 + 2 = 3 < 4 → move L
 * L >= R, no solution
 * 
 * Step 3: i=1, nums[i]=-1, need pairs summing to 1
 * Two pointers: L=2, R=5
 * sum = -1 + 2 = 1 (total: -1 + -1 + 2 = 0) ✓
 * Found: [-1, -1, 2]
 * Skip duplicates at L
 * Move L to 3, R to 4
 * sum = 0 + 1 = 1 (total: -1 + 0 + 1 = 0) ✓
 * Found: [-1, 0, 1]
 * 
 * Step 4: i=2, nums[i]=-1 (duplicate, skip)
 * 
 * Step 5: i=3, nums[i]=0, need pairs summing to 0
 * Two pointers: L=4, R=5
 * sum = 1 + 2 = 3 > 0 → move R
 * L >= R, no solution
 * 
 * Step 6: i=4, nums[i]=1 > 0, break
 * 
 * RESULT: [[-1, -1, 2], [-1, 0, 1]]
 * 
 * ============================================================================
 * EDGE CASES & HOW THEY'RE HANDLED
 * ============================================================================
 * 
 * 1. Less than 3 elements: nums = [1, 2]
 *    - Size check returns empty list
 *    - Result: []
 * 
 * 2. No solution: nums = [1, 2, 3]
 *    - All positive, sum cannot be zero
 *    - First element check (nums[i] > 0) breaks early
 *    - Result: []
 * 
 * 3. All zeros: nums = [0, 0, 0, 0]
 *    - Found: [0, 0, 0]
 *    - Duplicates skipped
 *    - Result: [[0, 0, 0]]
 * 
 * 4. Multiple solutions: nums = [-2, 0, 0, 2, 2]
 *    - Sorting: [-2, 0, 0, 2, 2]
 *    - Found: [-2, 0, 2]
 *    - Duplicates handled
 *    - Result: [[-2, 0, 2]]
 * 
 * 5. All negative: nums = [-3, -2, -1]
 *    - No positive to balance
 *    - Result: []
 * 
 * 6. Duplicate results prevented: nums = [-1, -1, 2, 2]
 *    - Without duplicate skipping would find [-1, -1, 2] twice
 *    - Duplicate check prevents this
 *    - Result: [[-1, -1, 2]]
 * 
 * 7. Large numbers: nums = [-100000, 50000, 50000]
 *    - sum = 0 ✓
 *    - Result: [[-100000, 50000, 50000]]
 * 
 * ============================================================================
 * REAL WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **Financial Analysis**: Find transactions that balance to zero
 * 2. **Chemistry**: Find chemical combinations with neutral pH
 * 3. **Portfolio Balancing**: Find asset combinations with zero net change
 * 4. **Game Theory**: Find moves that result in neutral position
 * 5. **Load Balancing**: Distribute tasks to achieve zero imbalance
 * 6. **Accounting**: Find entries that balance accounts
 * 7. **Physics**: Find force combinations resulting in equilibrium
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * 1. **Not sorting first**: Makes two-pointer approach impossible
 * 2. **Not skipping duplicates**: Results in duplicate triplets
 * 3. **Wrong boundary conditions**: L < R not L <= R
 * 4. **Not breaking on positive first element**: Wastes iterations
 * 5. **Incorrect duplicate skipping**: Must check i > 0 before comparing
 * 6. **Moving only one pointer**: When sum == 0, move both
 * 7. **Not handling edge cases**: Empty or small arrays
 * 
 * ============================================================================
 * OPTIMIZATION TECHNIQUES
 * ============================================================================
 * 
 * 1. **Early termination**: Break when first element > 0
 * 2. **Skip duplicates**: At all three positions
 * 3. **Two-pointer efficiency**: O(n) inner loop instead of O(n²)
 * 4. **Sorted array advantage**: Enables all optimizations
 * 
 * ============================================================================
 */

fun main() {
    val solution = ThreeSum()
    
    println("=== Three Sum Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(-1, 0, 1, 2, -1, -4)
    println("Input: ${nums1.contentToString()}")
    println("Result: ${solution.threeSum(nums1)}")
    println("Expected: [[-1, -1, 2], [-1, 0, 1]]\n")
    
    // Test 2: No solution
    println("Test 2: No solution")
    val nums2 = intArrayOf(1, 2, 3)
    println("Input: ${nums2.contentToString()}")
    println("Result: ${solution.threeSum(nums2)}")
    println("Expected: []\n")
    
    // Test 3: All zeros
    println("Test 3: All zeros")
    val nums3 = intArrayOf(0, 0, 0, 0)
    println("Input: ${nums3.contentToString()}")
    println("Result: ${solution.threeSum(nums3)}")
    println("Expected: [[0, 0, 0]]\n")
    
    // Test 4: Minimal array
    println("Test 4: Minimal array")
    val nums4 = intArrayOf(0, 0, 0)
    println("Input: ${nums4.contentToString()}")
    println("Result: ${solution.threeSum(nums4)}")
    println("Expected: [[0, 0, 0]]\n")
    
    // Test 5: Two elements
    println("Test 5: Two elements")
    val nums5 = intArrayOf(0, 1)
    println("Input: ${nums5.contentToString()}")
    println("Result: ${solution.threeSum(nums5)}")
    println("Expected: []\n")
    
    // Test 6: Multiple duplicates
    println("Test 6: Multiple duplicates")
    val nums6 = intArrayOf(-2, 0, 0, 2, 2)
    println("Input: ${nums6.contentToString()}")
    println("Result: ${solution.threeSum(nums6)}")
    println("Expected: [[-2, 0, 2]]\n")
    
    // Test 7: Large range
    println("Test 7: Large range")
    val nums7 = intArrayOf(-4, -1, -1, 0, 1, 2)
    println("Input: ${nums7.contentToString()}")
    println("Result: ${solution.threeSum(nums7)}")
    println("Expected: [[-1, -1, 2], [-1, 0, 1]]\n")
    
    println("All tests completed!")
}
