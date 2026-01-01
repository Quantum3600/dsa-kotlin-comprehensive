/**
 * ============================================================================
 * PROBLEM: Two Sum
 * DIFFICULTY: Medium
 * CATEGORY: Arrays
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an array of integers `nums` and an integer `target`, return indices of
 * the two numbers such that they add up to target. You may assume that each
 * input would have exactly one solution, and you may not use the same element twice.
 * 
 * You can return the answer in any order.
 * 
 * INPUT FORMAT:
 * - An array of integers: nums = [2, 7, 11, 15]
 * - A target sum: target = 9
 * 
 * OUTPUT FORMAT:
 * - An array of two indices: [0, 1]
 * - Because nums[0] + nums[1] = 2 + 7 = 9
 * 
 * CONSTRAINTS:
 * - 2 <= nums.length <= 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * - Only one valid answer exists
 * 
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * The key insight is that for each number `x` in the array, we're looking for
 * another number `y` where `x + y = target`, which means `y = target - x`.
 * 
 * NAIVE APPROACH (Brute Force):
 * Check every possible pair of numbers:
 * - For each element, check it against all other elements
 * - If sum equals target, return their indices
 * - Time: O(n²), Space: O(1)
 * 
 * OPTIMIZED APPROACH (Hash Map):
 * Use a hash map to store numbers we've seen and their indices:
 * - For each number x, calculate complement = target - x
 * - Check if complement exists in hash map
 * - If yes, return current index and complement's index
 * - If no, store x and its index in hash map
 * - Time: O(n), Space: O(n)
 * 
 * WHY HASH MAP WORKS:
 * Hash maps provide O(1) average lookup time, so we can check if the
 * complement exists in constant time. This reduces the problem from O(n²)
 * nested loops to O(n) single pass.
 * 
 * VISUAL EXAMPLE:
 * nums = [2, 7, 11, 15], target = 9
 * 
 * Step 1: i=0, num=2
 *   complement = 9 - 2 = 7
 *   Is 7 in map? NO
 *   Add: map = {2: 0}
 * 
 * Step 2: i=1, num=7
 *   complement = 9 - 7 = 2
 *   Is 2 in map? YES (at index 0)
 *   Return: [0, 1] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Hash Map (one-pass): O(n) time, O(n) space - OPTIMAL for unsorted
 * 2. Brute Force: O(n²) time, O(1) space - Simple but slow
 * 3. Two Pointers (requires sorting): O(n log n) time, O(1) space - Loses original indices
 * 4. Binary Search: O(n log n) time, O(n) space - Not better than hash map
 * 
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * HASH MAP APPROACH:
 * TIME COMPLEXITY: O(n)
 * - Single pass through the array
 * - Hash map operations (get, put) are O(1) average
 * - n is the number of elements
 * - Total: O(n) * O(1) = O(n)
 * 
 * SPACE COMPLEXITY: O(n)
 * - Hash map can store up to n elements in worst case
 * - When solution is at the end, we store all previous elements
 * 
 * BRUTE FORCE APPROACH:
 * TIME COMPLEXITY: O(n²)
 * - Nested loops: for each element, check all other elements
 * - Outer loop: n iterations
 * - Inner loop: n-1, n-2, ..., 1 iterations
 * - Total: n * (n-1) / 2 = O(n²)
 * 
 * SPACE COMPLEXITY: O(1)
 * - Only using loop variables
 * 
 * ============================================================================
 */

package arrays.medium

class TwoSum {
    
    /**
     * Finds two indices whose values sum to target using Hash Map
     * OPTIMAL APPROACH - O(n) time, O(n) space
     * 
     * @param nums Array of integers
     * @param target Target sum
     * @return Array of two indices that sum to target
     */
    fun twoSum(nums: IntArray, target: Int): IntArray {
        // Hash map to store: number -> its index
        // Key: number we've seen
        // Value: index where we saw it
        val map = HashMap<Int, Int>()
        
        // Iterate through array with index
        for (i in nums.indices) {
            // Current number
            val num = nums[i]
            
            // Calculate complement: what number do we need to reach target?
            // If num + complement = target, then complement = target - num
            val complement = target - num
            
            // Check if complement exists in our map
            // If yes, we've found our pair!
            if (map.containsKey(complement)) {
                // Return the indices: complement's index and current index
                // map[complement]!! gets the index where we saw the complement
                // i is our current index
                return intArrayOf(map[complement]!!, i)
            }
            
            // Complement not found yet
            // Store current number and its index for future lookups
            // If later we find a number that needs 'num' as complement,
            // we can quickly retrieve this index
            map[num] = i
        }
        
        // Problem guarantees exactly one solution, so we should never reach here
        // This is just to satisfy Kotlin's return type requirement
        throw IllegalArgumentException("No solution found")
    }
    
    /**
     * Brute force approach - checking all pairs
     * O(n²) time, O(1) space
     * Simple but inefficient
     */
    fun twoSumBruteForce(nums: IntArray, target: Int): IntArray {
        // Check every possible pair
        for (i in nums.indices) {
            // For each element, check all elements after it
            // j starts from i+1 to avoid using same element twice
            // and to avoid checking duplicate pairs
            for (j in i + 1 until nums.size) {
                // If this pair sums to target, we found it
                if (nums[i] + nums[j] == target) {
                    return intArrayOf(i, j)
                }
            }
        }
        
        throw IllegalArgumentException("No solution found")
    }
    
    /**
     * Two-pass hash map approach
     * First pass: build the map
     * Second pass: find complements
     * Same O(n) time and O(n) space, but requires two passes
     */
    fun twoSumTwoPass(nums: IntArray, target: Int): IntArray {
        // First pass: store all numbers and their indices
        val map = HashMap<Int, Int>()
        for (i in nums.indices) {
            map[nums[i]] = i
        }
        
        // Second pass: find complements
        for (i in nums.indices) {
            val complement = target - nums[i]
            // Check if complement exists AND it's not the same element
            if (map.containsKey(complement) && map[complement] != i) {
                return intArrayOf(i, map[complement]!!)
            }
        }
        
        throw IllegalArgumentException("No solution found")
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * EXAMPLE 1: Standard Case
 * INPUT: nums = [2, 7, 11, 15], target = 9
 * 
 * Using Hash Map Approach:
 * 
 * Initial: map = {}
 * 
 * Iteration 1 (i=0):
 * - num = nums[0] = 2
 * - complement = 9 - 2 = 7
 * - Is 7 in map? NO (map is empty)
 * - Add to map: map = {2: 0}
 * 
 * Iteration 2 (i=1):
 * - num = nums[1] = 7
 * - complement = 9 - 7 = 2
 * - Is 2 in map? YES (at index 0)
 * - FOUND! Return [0, 1]
 * 
 * RESULT: [0, 1]
 * Verification: nums[0] + nums[1] = 2 + 7 = 9 ✓
 * 
 * ============================================================================
 * 
 * EXAMPLE 2: Solution at End
 * INPUT: nums = [3, 2, 4], target = 6
 * 
 * Iteration 1 (i=0):
 * - num = 3, complement = 6 - 3 = 3
 * - Is 3 in map? NO
 * - map = {3: 0}
 * 
 * Iteration 2 (i=1):
 * - num = 2, complement = 6 - 2 = 4
 * - Is 4 in map? NO
 * - map = {3: 0, 2: 1}
 * 
 * Iteration 3 (i=2):
 * - num = 4, complement = 6 - 4 = 2
 * - Is 2 in map? YES (at index 1)
 * - FOUND! Return [1, 2]
 * 
 * RESULT: [1, 2]
 * Verification: nums[1] + nums[2] = 2 + 4 = 6 ✓
 * 
 * ============================================================================
 * 
 * EXAMPLE 3: Negative Numbers
 * INPUT: nums = [-3, 4, 3, 90], target = 0
 * 
 * Iteration 1 (i=0):
 * - num = -3, complement = 0 - (-3) = 3
 * - Is 3 in map? NO
 * - map = {-3: 0}
 * 
 * Iteration 2 (i=1):
 * - num = 4, complement = 0 - 4 = -4
 * - Is -4 in map? NO
 * - map = {-3: 0, 4: 1}
 * 
 * Iteration 3 (i=2):
 * - num = 3, complement = 0 - 3 = -3
 * - Is -3 in map? YES (at index 0)
 * - FOUND! Return [0, 2]
 * 
 * RESULT: [0, 2]
 * Verification: nums[0] + nums[2] = -3 + 3 = 0 ✓
 * 
 * ============================================================================
 * EDGE CASES & HOW THEY'RE HANDLED
 * ============================================================================
 * 
 * 1. Minimum array size: nums = [1, 2], target = 3
 *    - Works correctly: returns [0, 1]
 * 
 * 2. Same number used twice (but different indices): nums = [3, 3], target = 6
 *    - Iteration 1: map = {3: 0}
 *    - Iteration 2: complement = 3, found at index 0
 *    - Returns: [0, 1] ✓
 * 
 * 3. Cannot use same element twice: nums = [3, 2, 4], target = 6
 *    - When i=0, num=3, complement=3
 *    - 3 not in map yet, so can't use same element
 *    - Correctly finds [1, 2] later
 * 
 * 4. Negative target: nums = [1, -2, 3], target = -1
 *    - Works: complement = -1 - 1 = -2
 *    - Returns: [0, 1]
 * 
 * 5. Large numbers: nums = [1000000000, 1000000000], target = 2000000000
 *    - No overflow in complement calculation
 *    - Works correctly
 * 
 * 6. Zero in array: nums = [0, 4, 3, 0], target = 0
 *    - Works: finds the two zeros at indices [0, 3]
 * 
 * 7. Solution at beginning: nums = [2, 7, 11, 15], target = 9
 *    - Early termination after checking just 2 elements
 *    - Efficient
 * 
 * 8. All negative: nums = [-1, -2, -3, -4, -5], target = -8
 *    - Works: -3 + -5 = -8, returns [2, 4]
 * 
 * 9. Mix positive/negative: nums = [-1, 5, 3, -7], target = -4
 *    - Works: 3 + -7 = -4, returns [2, 3]
 * 
 * 10. Large array: nums = [1, 2, 3, ..., 10000], target = 19999
 *     - O(n) time ensures fast processing
 *     - Finds correct pair efficiently
 * 
 * ============================================================================
 * WHEN TO USE EACH APPROACH
 * ============================================================================
 * 
 * USE HASH MAP WHEN:
 * - You need optimal O(n) time
 * - Memory is not a constraint
 * - Array is unsorted
 * - Need to preserve original indices
 * - This is the standard interview answer
 * 
 * USE BRUTE FORCE WHEN:
 * - Array is very small (< 100 elements)
 * - Memory is extremely limited
 * - Code simplicity is priority
 * - Quick prototype needed
 * 
 * USE TWO POINTERS WHEN:
 * - Array is already sorted (or can be sorted)
 * - Don't need to return indices (just check if pair exists)
 * - Want O(1) space
 * - Follow-up problems like 3Sum or 4Sum
 * 
 * ============================================================================
 * REAL WORLD APPLICATIONS
 * ============================================================================
 * 
 * 1. **E-commerce**: Find products that together cost exactly budget
 * 2. **Finance**: Find two transactions that sum to specific amount
 * 3. **Chemistry**: Find two compounds that react to form target
 * 4. **Networking**: Find pair of data packets with specific total size
 * 5. **Gaming**: Find two items that together give exact power level
 * 6. **Scheduling**: Find two time slots that sum to available time
 * 7. **Resource Allocation**: Pair resources that together meet requirement
 * 8. **Data Analysis**: Find correlated data points
 * 9. **Cryptography**: Pattern matching in security protocols
 * 10. **Logistics**: Pairing shipments that fit container capacity
 * 
 * ============================================================================
 * COMMON MISTAKES TO AVOID
 * ============================================================================
 * 
 * 1. **Using same element twice**: nums = [3, 2, 4], target = 6
 *    - Wrong: Returning [0, 0] when nums[0] + nums[0] = 6
 *    - Right: Use j = i+1 or check map[complement] != i
 * 
 * 2. **Not checking if key exists**: Directly accessing map[complement]
 *    - Problem: NullPointerException if key doesn't exist
 *    - Solution: Use containsKey() first
 * 
 * 3. **Integer overflow**: Large numbers causing overflow
 *    - Problem: target - num might overflow
 *    - Solution: Use Long if dealing with edge values
 * 
 * 4. **Wrong return order**: Returning [larger, smaller] when [smaller, larger] expected
 *    - Usually doesn't matter (problem says "any order")
 *    - But clarify with interviewer
 * 
 * 5. **Not handling duplicates**: nums = [3, 3], target = 6
 *    - Wrong approach might skip duplicates
 *    - Right: Hash map handles this correctly
 * 
 * 6. **Forgetting to add to map**: Only checking, not storing
 *    - Problem: Won't find solution if complement comes later
 *    - Solution: Always add current element to map
 * 
 * 7. **Using wrong data structure**: Array instead of HashMap
 *    - Problem: O(n) lookup time instead of O(1)
 *    - Solution: Use HashMap/HashSet appropriately
 * 
 * ============================================================================
 * FOLLOW-UP QUESTIONS & VARIATIONS
 * ============================================================================
 * 
 * 1. **3Sum**: Find three numbers that sum to target
 *    - Extension using hash map or two pointers
 *    - O(n²) time complexity
 * 
 * 2. **4Sum**: Find four numbers that sum to target
 *    - Similar approach, O(n³) time
 * 
 * 3. **Two Sum II - Sorted Array**: Input array is sorted
 *    - Use two pointers: O(n) time, O(1) space
 * 
 * 4. **Two Sum - Count Pairs**: Count all pairs that sum to target
 *    - Need to handle duplicates carefully
 * 
 * 5. **Two Sum - All Pairs**: Return all pairs (multiple solutions)
 *    - Store all matching pairs in result list
 * 
 * 6. **Two Sum - Closest**: Find pair with sum closest to target
 *    - Track minimum difference
 * 
 * 7. **Two Sum - Less Than K**: Find pairs with sum < target
 *    - Count all valid pairs
 * 
 * 8. **Two Sum - Data Structure Design**: Implement add and find
 *    - Design class with add(number) and find(target) methods
 * 
 * ============================================================================
 * INTERVIEW TIPS
 * ============================================================================
 * 
 * 1. **Start with brute force**: Show you understand the problem
 * 2. **Optimize**: Explain why hash map improves time complexity
 * 3. **Draw example**: Visual walkthrough helps interviewer follow
 * 4. **Discuss trade-offs**: Time vs space complexity
 * 5. **Edge cases**: Mention and handle them explicitly
 * 6. **Code quality**: Clean, readable code with good variable names
 * 7. **Test**: Walk through your code with the example
 * 8. **Follow-ups**: Be ready for 3Sum, 4Sum variations
 * 9. **Clarify assumptions**: One solution exists? Can use same element?
 * 10. **Time yourself**: This should take 15-20 minutes in interview
 * 
 * ============================================================================
 */

fun main() {
    val solution = TwoSum()
    
    println("=== Two Sum Tests ===\n")
    
    // Test 1: Standard case
    println("Test 1: Standard case")
    val nums1 = intArrayOf(2, 7, 11, 15)
    val target1 = 9
    println("Input: nums = ${nums1.contentToString()}, target = $target1")
    println("Output: ${solution.twoSum(nums1, target1).contentToString()}")
    println("Expected: [0, 1]\n")
    
    // Test 2: Solution at end
    println("Test 2: Solution at end")
    val nums2 = intArrayOf(3, 2, 4)
    val target2 = 6
    println("Input: nums = ${nums2.contentToString()}, target = $target2")
    println("Output: ${solution.twoSum(nums2, target2).contentToString()}")
    println("Expected: [1, 2]\n")
    
    // Test 3: Same number twice (different indices)
    println("Test 3: Same number twice")
    val nums3 = intArrayOf(3, 3)
    val target3 = 6
    println("Input: nums = ${nums3.contentToString()}, target = $target3")
    println("Output: ${solution.twoSum(nums3, target3).contentToString()}")
    println("Expected: [0, 1]\n")
    
    // Test 4: Negative numbers
    println("Test 4: Negative numbers")
    val nums4 = intArrayOf(-3, 4, 3, 90)
    val target4 = 0
    println("Input: nums = ${nums4.contentToString()}, target = $target4")
    println("Output: ${solution.twoSum(nums4, target4).contentToString()}")
    println("Expected: [0, 2]\n")
    
    // Test 5: Large array
    println("Test 5: Large array")
    val nums5 = intArrayOf(1, 5, 3, 8, 2, 9, 4, 7, 6)
    val target5 = 13
    println("Input: nums = ${nums5.contentToString()}, target = $target5")
    println("Output: ${solution.twoSum(nums5, target5).contentToString()}")
    println("Expected: [2, 7] or [4, 5] (7 + 6 = 13 or 9 + 4 = 13)\n")
    
    // Test 6: Negative target
    println("Test 6: Negative target")
    val nums6 = intArrayOf(1, -2, 3, -4)
    val target6 = -1
    println("Input: nums = ${nums6.contentToString()}, target = $target6")
    println("Output: ${solution.twoSum(nums6, target6).contentToString()}")
    println("Expected: [0, 1] (1 + -2 = -1)\n")
    
    // Test 7: Zero in array
    println("Test 7: Zero in array")
    val nums7 = intArrayOf(0, 4, 3, 0)
    val target7 = 0
    println("Input: nums = ${nums7.contentToString()}, target = $target7")
    println("Output: ${solution.twoSum(nums7, target7).contentToString()}")
    println("Expected: [0, 3]\n")
    
    // Test 8: All negative
    println("Test 8: All negative")
    val nums8 = intArrayOf(-1, -2, -3, -4, -5)
    val target8 = -8
    println("Input: nums = ${nums8.contentToString()}, target = $target8")
    println("Output: ${solution.twoSum(nums8, target8).contentToString()}")
    println("Expected: [2, 4] (-3 + -5 = -8)\n")
    
    // Comparison of approaches
    println("=== Comparing Different Approaches ===")
    val testNums = intArrayOf(2, 7, 11, 15)
    val testTarget = 9
    println("Test: nums = ${testNums.contentToString()}, target = $testTarget")
    println("Hash Map (optimal): ${solution.twoSum(testNums, testTarget).contentToString()}")
    println("Brute Force: ${solution.twoSumBruteForce(testNums, testTarget).contentToString()}")
    println("Two Pass: ${solution.twoSumTwoPass(testNums, testTarget).contentToString()}")
    
    // Performance note
    println("\n=== Performance Note ===")
    println("Hash Map: O(n) time, O(n) space - OPTIMAL")
    println("Brute Force: O(n²) time, O(1) space - Simple but slow")
    println("Two Pass: O(n) time, O(n) space - Same as hash map but two passes")
}
