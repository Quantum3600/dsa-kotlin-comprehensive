package recursion.subsequences

/**
 * ============================================================================
 * PROBLEM: Partition to K Equal Sum Subsets
 * DIFFICULTY: Hard
 * CATEGORY: Recursion - Subsequences
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * Given an integer array nums and an integer k, return true if it is possible
 * to divide this array into k non-empty subsets whose sums are all equal.
 * 
 * INPUT FORMAT:
 * - An array of integers: [4, 3, 2, 3, 5, 2, 1]
 * - Number of subsets k: 4
 * 
 * OUTPUT FORMAT:
 * - Boolean: true if partitioning is possible, false otherwise
 * 
 * CONSTRAINTS:
 * - 1 <= k <= nums.length <= 16
 * - 1 <= nums[i] <= 10^4
 * - The frequency of each element is in the range [1, 4]
 * 
 * EXAMPLES:
 * Input: nums = [4,3,2,3,5,2,1], k = 4
 * Output: true
 * Explanation: [5], [1,4], [2,3], [2,3]
 * 
 * Input: nums = [1,2,3,4], k = 3
 * Output: false
 */

/**
 * ============================================================================
 * APPROACH & INTUITION
 * ============================================================================
 * 
 * INTUITION:
 * If we can partition into k equal-sum subsets, then:
 * - Total sum must be divisible by k
 * - Each subset must have sum = total_sum / k
 * - No element can be greater than target sum
 * 
 * TWO MAIN APPROACHES:
 * 
 * APPROACH 1: Number-Centric (Try to place each number into a bucket)
 * - For each number, try placing it in one of k buckets
 * - If a bucket reaches target sum, it's complete
 * - Continue until all numbers are placed
 * 
 * APPROACH 2: Bucket-Centric (Fill each bucket completely before next)
 * - Fill bucket 1 completely, then bucket 2, etc.
 * - For each bucket, try all combinations of unused numbers
 * - When a bucket is full, move to next bucket
 * 
 * We'll implement both approaches!
 * 
 * ALGORITHM (Bucket-Centric):
 * 1. Calculate target = sum(nums) / k
 * 2. Sort nums in descending order (optimization)
 * 3. Use backtracking to fill buckets:
 *    - Track which numbers are used (bitmask or boolean array)
 *    - For current bucket, try adding each unused number
 *    - When bucket reaches target, move to next bucket
 *    - If bucket exceeds target, backtrack
 * 4. Base case: All buckets filled successfully
 * 
 * KEY OPTIMIZATIONS:
 * 1. Sort descending: Try larger numbers first (fail faster)
 * 2. Skip duplicate values in same recursion level
 * 3. If current number can't fit, skip all smaller numbers
 * 4. Use memoization with bitmask for visited states
 * 
 * VISUAL EXAMPLE:
 * nums = [4,3,2,3,5,2,1], k = 4
 * sum = 20, target = 5
 * Sorted: [5,4,3,3,2,2,1]
 * 
 * Bucket 1: [5] ✓
 * Bucket 2: [4,1] ✓
 * Bucket 3: [3,2] ✓
 * Bucket 4: [3,2] ✓
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Dynamic Programming with bitmask: O(2^n * n)
 * 2. Branch and Bound with pruning
 * 3. Meet in the middle for larger n
 */

/**
 * ============================================================================
 * COMPLEXITY ANALYSIS
 * ============================================================================
 * 
 * TIME COMPLEXITY: O(k * 2^n)
 * - In worst case, we try every subset of numbers for each bucket
 * - For each of k buckets, we explore 2^n possibilities
 * - With memoization: O(2^n) states, each taking O(n) to process
 * - Overall: O(k * 2^n) without memo, O(n * 2^n) with memo
 * 
 * SPACE COMPLEXITY: O(n + 2^n)
 * - Recursion depth: O(n)
 * - Memoization map: O(2^n) states
 * - Used array: O(n)
 */

class PartitionKSubsets {
    
    /**
     * Check if array can be partitioned into k equal sum subsets
     * @param nums Input array
     * @param k Number of subsets
     * @return True if partitioning possible, false otherwise
     */
    fun canPartitionKSubsets(nums: IntArray, k: Int): Boolean {
        val sum = nums.sum()
        
        // Early termination checks
        if (sum % k != 0) return false
        
        val target = sum / k
        
        // If any element exceeds target, impossible
        if (nums.any { it > target }) return false
        
        // Sort in descending order for optimization
        nums.sortDescending()
        
        // Track which numbers are used
        val used = BooleanArray(nums.size)
        
        // Start filling buckets
        return canPartition(nums, used, 0, k, 0, target)
    }
    
    /**
     * Recursive backtracking to fill buckets
     * @param nums Array of numbers
     * @param used Tracks which numbers are already used
     * @param bucketIndex Current bucket being filled (0 to k-1)
     * @param k Total number of buckets
     * @param bucketSum Current sum of current bucket
     * @param target Target sum for each bucket
     */
    private fun canPartition(
        nums: IntArray,
        used: BooleanArray,
        bucketIndex: Int,
        k: Int,
        bucketSum: Int,
        target: Int
    ): Boolean {
        // BASE CASE: All buckets filled successfully
        if (bucketIndex == k) {
            return true
        }
        
        // Current bucket is full, move to next bucket
        if (bucketSum == target) {
            // Start next bucket from index 0 with sum 0
            return canPartition(nums, used, bucketIndex + 1, k, 0, target)
        }
        
        // Try adding each unused number to current bucket
        for (i in nums.indices) {
            // Skip if already used
            if (used[i]) continue
            
            // Skip if it exceeds target
            if (bucketSum + nums[i] > target) continue
            
            // OPTIMIZATION: Skip duplicates at same level
            // If we just tried a number and it didn't work,
            // trying the same number again won't help
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue
            }
            
            // Mark as used
            used[i] = true
            
            // Recurse with updated bucket sum
            if (canPartition(nums, used, bucketIndex, k, bucketSum + nums[i], target)) {
                return true
            }
            
            // Backtrack
            used[i] = false
            
            // OPTIMIZATION: If this is the first number in bucket and it failed,
            // no point trying other numbers (they'll face same situation)
            if (bucketSum == 0) {
                break
            }
        }
        
        return false
    }
    
    /**
     * Alternative: Using bitmask instead of boolean array
     * More efficient for memoization
     */
    fun canPartitionKSubsetsBitmask(nums: IntArray, k: Int): Boolean {
        val sum = nums.sum()
        if (sum % k != 0) return false
        
        val target = sum / k
        if (nums.any { it > target }) return false
        
        nums.sortDescending()
        
        val memo = mutableMapOf<String, Boolean>()
        
        return canPartitionMemo(nums, 0, 0, k, 0, target, memo)
    }
    
    private fun canPartitionMemo(
        nums: IntArray,
        mask: Int,
        bucketIndex: Int,
        k: Int,
        bucketSum: Int,
        target: Int,
        memo: MutableMap<String, Boolean>
    ): Boolean {
        // BASE CASE
        if (bucketIndex == k) return true
        
        if (bucketSum == target) {
            // Move to next bucket, reset sum
            return canPartitionMemo(nums, mask, bucketIndex + 1, k, 0, target, memo)
        }
        
        // Check memo
        val key = "$mask,$bucketSum"
        if (memo.containsKey(key)) {
            return memo[key]!!
        }
        
        // Try each number
        for (i in nums.indices) {
            // Skip if used (bit is set)
            if ((mask and (1 shl i)) != 0) continue
            
            // Skip if exceeds target
            if (bucketSum + nums[i] > target) continue
            
            // Mark as used
            val newMask = mask or (1 shl i)
            
            // Recurse
            if (canPartitionMemo(nums, newMask, bucketIndex, k, bucketSum + nums[i], target, memo)) {
                memo[key] = true
                return true
            }
            
            // Early termination
            if (bucketSum == 0) break
        }
        
        memo[key] = false
        return false
    }
    
    /**
     * Alternative: Number-centric approach
     * Try to assign each number to a bucket
     */
    fun canPartitionNumberCentric(nums: IntArray, k: Int): Boolean {
        val sum = nums.sum()
        if (sum % k != 0) return false
        
        val target = sum / k
        if (nums.any { it > target }) return false
        
        nums.sortDescending()
        
        val buckets = IntArray(k)
        
        return assignNumbers(nums, 0, buckets, target)
    }
    
    private fun assignNumbers(
        nums: IntArray,
        index: Int,
        buckets: IntArray,
        target: Int
    ): Boolean {
        // BASE CASE: All numbers assigned
        if (index == nums.size) {
            // Check if all buckets have target sum
            return buckets.all { it == target }
        }
        
        val num = nums[index]
        
        // Try putting this number in each bucket
        for (i in buckets.indices) {
            // Skip if adding this number exceeds target
            if (buckets[i] + num > target) continue
            
            // Add to bucket
            buckets[i] += num
            
            // Recurse with next number
            if (assignNumbers(nums, index + 1, buckets, target)) {
                return true
            }
            
            // Backtrack
            buckets[i] -= num
            
            // OPTIMIZATION: If this bucket is empty, no point trying
            // other empty buckets (they're all identical)
            if (buckets[i] == 0) break
        }
        
        return false
    }
}

/**
 * ============================================================================
 * EXAMPLE WALKTHROUGH
 * ============================================================================
 * 
 * Input: nums = [4,3,2,3,5,2,1], k = 4
 * 
 * Step 1: Validate
 * sum = 4+3+2+3+5+2+1 = 20
 * 20 % 4 = 0 ✓
 * target = 20 / 4 = 5
 * max(nums) = 5 <= 5 ✓
 * 
 * Step 2: Sort descending
 * nums = [5,4,3,3,2,2,1]
 * 
 * Step 3: Backtracking
 * used = [F,F,F,F,F,F,F]
 * 
 * Fill Bucket 0 (target = 5):
 *   Try nums[0]=5:
 *     used = [T,F,F,F,F,F,F]
 *     bucketSum = 5 = target ✓
 *     Move to Bucket 1
 * 
 * Fill Bucket 1 (target = 5):
 *   Try nums[1]=4:
 *     used = [T,T,F,F,F,F,F]
 *     bucketSum = 4
 *     Try nums[2]=3:
 *       4+3=7 > 5, skip
 *     Try nums[4]=2:
 *       4+2=6 > 5, skip
 *     Try nums[6]=1:
 *       used = [T,T,F,F,F,F,T]
 *       bucketSum = 5 = target ✓
 *       Move to Bucket 2
 * 
 * Fill Bucket 2 (target = 5):
 *   Try nums[2]=3:
 *     used = [T,T,T,F,F,F,T]
 *     bucketSum = 3
 *     Try nums[3]=3:
 *       3+3=6 > 5, skip
 *     Try nums[4]=2:
 *       used = [T,T,T,F,T,F,T]
 *       bucketSum = 5 = target ✓
 *       Move to Bucket 3
 * 
 * Fill Bucket 3 (target = 5):
 *   Try nums[3]=3:
 *     used = [T,T,T,T,T,F,T]
 *     bucketSum = 3
 *     Try nums[5]=2:
 *       used = [T,T,T,T,T,T,T]
 *       bucketSum = 5 = target ✓
 *       bucketIndex = 4 = k ✓
 *       Return TRUE
 * 
 * Result: true
 * Subsets: [5], [4,1], [3,2], [3,2]
 */

fun main() {
    val solver = PartitionKSubsets()
    
    // Test Case 1: Possible partitioning
    println("Test Case 1: nums = [4,3,2,3,5,2,1], k = 4")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(4, 3, 2, 3, 5, 2, 1), 4)}")
    println("Expected: true (subsets: [5], [4,1], [3,2], [3,2])")
    println()
    
    // Test Case 2: Impossible partitioning
    println("Test Case 2: nums = [1,2,3,4], k = 3")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(1, 2, 3, 4), 3)}")
    println("Expected: false (sum=10, 10%3≠0)")
    println()
    
    // Test Case 3: k = 1 (always true)
    println("Test Case 3: nums = [1,2,3], k = 1")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(1, 2, 3), 1)}")
    println("Expected: true (all elements in one subset)")
    println()
    
    // Test Case 4: k = n (each element in own subset)
    println("Test Case 4: nums = [2,2,2,2], k = 4")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(2, 2, 2, 2), 4)}")
    println("Expected: true (each element forms a subset)")
    println()
    
    // Test Case 5: Element larger than target
    println("Test Case 5: nums = [10,1,1,1,1], k = 2")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(10, 1, 1, 1, 1), 2)}")
    println("Expected: false (10 > target=7)")
    println()
    
    // Test Case 6: Edge case
    println("Test Case 6: nums = [1,1,1,1,2,2,2,2], k = 4")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(1, 1, 1, 1, 2, 2, 2, 2), 4)}")
    println("Expected: true (target=3, subsets: [2,1], [2,1], [2,1], [2,1])")
    println()
    
    // Test Case 7: Compare implementations
    println("Test Case 7: Compare different implementations")
    val nums = intArrayOf(4, 3, 2, 3, 5, 2, 1)
    val k = 4
    val result1 = solver.canPartitionKSubsets(nums, k)
    val result2 = solver.canPartitionNumberCentric(nums, k)
    println("Bucket-centric: $result1")
    println("Number-centric: $result2")
    println("Match: ${result1 == result2}")
    println()
    
    // Test Case 8: Complex case
    println("Test Case 8: nums = [3,3,10,2,6,5,10,6,8,3,2,1,6,10,7,2], k = 6")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(3,3,10,2,6,5,10,6,8,3,2,1,6,10,7,2), 6)}")
    println("Expected: true")
    println()
    
    // Test Case 9: All same values
    println("Test Case 9: nums = [5,5,5,5], k = 2")
    println("Result: ${solver.canPartitionKSubsets(intArrayOf(5, 5, 5, 5), 2)}")
    println("Expected: true (subsets: [5,5], [5,5])")
}
